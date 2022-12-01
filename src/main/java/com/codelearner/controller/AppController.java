package com.codelearner.controller;

import com.codelearner.common.Role;
import com.codelearner.domain.Problem;
import com.codelearner.domain.User;
import com.codelearner.helper.FileOperations;
import com.codelearner.request.FileSaveRequest;
import com.codelearner.request.LoginUserRequest;
import com.codelearner.request.ProblemCreateRequest;
import com.codelearner.request.RegisterUserRequest;
import com.codelearner.request.UpdateRequest;
import com.codelearner.response.CodeDetailsWrapper;
import com.codelearner.response.CompileResponse;
import com.codelearner.response.LoginResponse;
import com.codelearner.response.ProblemCode;
import com.codelearner.response.ProblemResponseWrapper;
import com.codelearner.service.MongoService;
import com.codelearner.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/")
public class AppController {
	
	@Autowired
	private MongoService mongoService;
	
	@Autowired
	private FileOperations fileOperations;

	@Autowired
	private UserService userService;
	
	@Autowired
	private Mapper dozerMapper;


	/**
	 * Register a new user.
	 *
	 * @param registerUserRequest
	 */
	@RequestMapping(value="/registerUser", method = RequestMethod.POST)
	public void registerUser(@RequestBody RegisterUserRequest registerUserRequest){
		userService.registerUser(registerUserRequest);
	}

	/**
	 * Login user.
	 *
	 * @param loginUserRequest
	 */
	@RequestMapping(value="/loginUser", method = RequestMethod.POST)
	public LoginResponse loginUser(@RequestBody LoginUserRequest loginUserRequest, HttpServletRequest request){
		LoginResponse response = new LoginResponse();
		response.setStatus("failure");
		String userId = (String) request.getSession().getAttribute("userId");
		if (userId != null) {
			response.setStatus("success");
			response.setUserId(userId);
			response.setRole((String)request.getSession().getAttribute("role"));
			return response;
		}
		User user = userService.login(loginUserRequest);
		if (user != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("userId", user.getUserId());
			session.setAttribute("role", user.getRole());
			response.setStatus("success");
			response.setUserId(user.getUserId());
			response.setRole(user.getRole());
		}
		return response;
	}

	/**
	 * Saves the user entered code snippet as a Java file.
	 * Save the file in Mongo DB
	 *
	 * @param problem
	 */
	@RequestMapping(value="/submitCode", method = RequestMethod.POST)
    public void submitProblem(@RequestBody ProblemCreateRequest problem, HttpServletRequest request){
		FileSaveRequest fileSaveRequest = dozerMapper.map(problem, FileSaveRequest.class);
		fileSaveRequest.setCode(fileOperations.createFile(problem.getCode(), problem.getClassName(), problem.getLanguage()));

		String userId = (String) request.getSession().getAttribute("userId");
		String userRole = (String) request.getSession().getAttribute("role");
		fileSaveRequest.setParticipants(userService.getAllStudents());
        String objectId = mongoService.saveCodeFile(fileSaveRequest, userRole);

    }

	/**
	 * Compile the code snippet and return the output if there are no errors
	 * Return the errors if found
	 *
	 * @param problem
	 * @return
	 */
	@RequestMapping(value="/compileCode", method = RequestMethod.POST)
	public CompileResponse compileCode(@RequestBody ProblemCreateRequest problem, HttpServletRequest request) {
		String userRole = (String) request.getSession().getAttribute("role");
		CompileResponse compiler = new CompileResponse();
		List<String> output = null;
		if (Role.Student.name().equals(userRole) || problem.getAutoGraderCode() == null) {
			File codeFile = fileOperations.createFile(problem.getCode(), problem.getClassName(), problem.getLanguage());
			if (StringUtils.isNotBlank(problem.getLanguage()) && problem.getLanguage().equals("Java")) {
				output = fileOperations.syntaxChecker(codeFile.getAbsolutePath());
				if (CollectionUtils.isNotEmpty(output)) {
					compiler.setCompiledOutput(output);
					compiler.setFlag(false);
				} else {
					compiler.setCompiledOutput(fileOperations.executeCode(codeFile));
					compiler.setFlag(true);

				}
			}
		} else if (Role.Instructor.name().equals(userRole)) {
			File codeFile = fileOperations.createFile(problem.getCode(), problem.getClassName(), problem.getLanguage());
			if (StringUtils.isNotBlank(problem.getLanguage()) && problem.getLanguage().equals("Java")) {
				try {
					Files.deleteIfExists(Paths.get(codeFile.getParentFile().getAbsolutePath() + "/" + codeFile.getName().substring(0, codeFile.getName().indexOf(".")) + ".class"));
					fileOperations.runProcess("javac " + codeFile.getAbsolutePath());
					output = fileOperations.runProcess("java -cp " + codeFile.getParentFile().getAbsolutePath() + " " +  codeFile.getName().substring(0, codeFile.getName().indexOf(".")));
					compiler.setCompiledOutput(output);
					compiler.setFlag(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return compiler;
	}

	/**
	 * Fetch the list of programming problems
	 *
	 * @param lang
	 * @return
	 */
	@RequestMapping(value="/fetchProblemsByLanguage", method = RequestMethod.GET)
	public ProblemResponseWrapper fetchProblems(@RequestParam(value = "lang") final String lang) {
		ProblemResponseWrapper problems = new ProblemResponseWrapper();
		problems.setProblems(mongoService.fetchProblemsByLanguage(lang));
		return problems;
	}


	
	
}
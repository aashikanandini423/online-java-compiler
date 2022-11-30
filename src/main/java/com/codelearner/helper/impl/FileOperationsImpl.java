package com.codelearner.helper.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.codelearner.service.MongoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codelearner.helper.FileOperations;

@Component
public class FileOperationsImpl implements FileOperations {

	@Autowired
	private MongoService mongoService;

	/**
	 * Create a source file in the given language given the list of code lines
	 *
	 * @param codeLines
	 * @param className
	 * @param language
	 * @return
	 */
	@Override
	public File createFile(List<String> codeLines, String className, String language) {
		File codeFile = null;
		try {
			if (StringUtils.isNotBlank(language)) {
				if (language.equals("Java")) {
					codeFile = new File("/tmp/" + className +".java");
				} else if (language.equals("Python")) {
					codeFile = new File("/tmp/" + className +".py");
				}
			}
		  	FileOutputStream fos = new FileOutputStream(codeFile);
		  	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		    for (String line : codeLines) {
	    	  bw.write(line);
	    	  bw.newLine();
		    }
		    bw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return codeFile;
		
	}
	
	/*
	* Check the syntax and return compiler errors if any.
	* Uses java compiler and diagnostics tool
	*
	*/
	@Override
	public List<String> syntaxChecker(String file) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromStrings(Arrays.asList(file));

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();

        List<String> messages = new ArrayList<String>();
        Formatter formatter = new Formatter();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            messages.add(diagnostic.getKind() + ":\t Line [" + diagnostic.getLineNumber() + "] \t Position [" + diagnostic.getPosition() + "]\t" + diagnostic.getMessage(Locale.ROOT) + "\n");
        }

        return messages;
    }

	/**
	 * Compile and run the generated source file
	 *
	 * @param file
	 * @return
	 */
	@Override
	public List<String> executeCode(File file) {
		List<String> output = null;
		
		try {
			Files.deleteIfExists(Paths.get(file.getParentFile().getAbsolutePath() + "/" + file.getName().substring(0, file.getName().indexOf(".")) + ".class"));
			runProcess("javac " + file.getAbsolutePath());
		    output = runProcess("java -cp " + file.getParentFile().getAbsolutePath() + " " +  file.getName().substring(0, file.getName().indexOf(".")));		   
		} catch(Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	/**
	 * Get the program output from InputStream
	 *
	 * @param ins
	 * @return
	 * @throws Exception
	 */
	private List<String> getOutput(InputStream ins) throws Exception {
		List<String> results = new ArrayList<String>();
	    String line = null;
	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	        results.add(line);
	    }
	    return results;
	}

	/**
	* Execute a system process from the jvm
	*/
	@Override
	public List<String> runProcess(String command) throws Exception {
		Process pro = Runtime.getRuntime().exec(command, null, new File("/tmp"));

	    List<String> output = new ArrayList<>();
	    if (null != pro.getErrorStream()) {
	    	output = getOutput(pro.getErrorStream());
	    } 
	    if (null != pro.getInputStream()) {
	    	output.addAll(getOutput(pro.getInputStream()));
	    }
	    pro.waitFor();
	    return output;	
	}
	
}

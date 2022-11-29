package com.codelearner.response;

import java.util.List;

public class CodeDetailsWrapper {

	private ProblemCode problem;
	private List<ProblemCode> answers;

	public ProblemCode getProblem() {
		return problem;
	}

	public void setProblem(ProblemCode problem) {
		this.problem = problem;
	}

	public List<ProblemCode> getAnswers() {
		return answers;
	}

	public void setAnswers(List<ProblemCode> answers) {
		this.answers = answers;
	}
}

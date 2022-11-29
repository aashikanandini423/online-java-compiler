package com.codelearner.response;


import java.util.List;

public class CompileResponse {

	private List<String> compiledOutput;
	private boolean flag;

	public List<String> getCompiledOutput() {
		return compiledOutput;
	}

	public void setCompiledOutput(List<String> compiledOutput) {
		this.compiledOutput = compiledOutput;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}

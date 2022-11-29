package com.codelearner.response;

import com.codelearner.domain.MetaData;

public class ProblemResponse {

	private String problemId;
	private MetaData metadata;

	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

	public MetaData getMetadata() {
		return metadata;
	}

	public void setMetadata(MetaData metadata) {
		this.metadata = metadata;
	}
}

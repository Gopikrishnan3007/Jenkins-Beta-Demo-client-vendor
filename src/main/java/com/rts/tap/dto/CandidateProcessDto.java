package com.rts.tap.dto;

import java.util.List;
 
public class CandidateProcessDto {
	private List<Long> candidateIds;
	private Long recruitmentProcessId;
 
	public CandidateProcessDto() {
		super();
	}
 
	public CandidateProcessDto(List<Long> candidateIds, Long recruitmentProcessId) {
		super();
		this.candidateIds = candidateIds;
		this.recruitmentProcessId = recruitmentProcessId;
	}
 
	public List<Long> getCandidateIds() {
		return candidateIds;
	}
 
	public void setCandidateIds(List<Long> candidateIds) {
		this.candidateIds = candidateIds;
	}
 
	public Long getRecruitmentProcessId() {
		return recruitmentProcessId;
	}
 
	public void setRecruitmentProcessId(Long recruitmentProcessId) {
		this.recruitmentProcessId = recruitmentProcessId;
	}
 
}
 
 
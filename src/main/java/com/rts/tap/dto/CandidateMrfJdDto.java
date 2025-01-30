package com.rts.tap.dto;
 
public class CandidateMrfJdDto {
 
	private Long mrfJdId;
 
	private String status;
 
	public CandidateMrfJdDto() {
		super();
	}
 
	public CandidateMrfJdDto(Long mrfJdId, String status) {
		this.mrfJdId = mrfJdId;
		this.status = status;
	}
 
	public Long getMrfJdId() {
		return mrfJdId;
	}
 
	public void setMrfJdId(Long mrfJdId) {
		this.mrfJdId = mrfJdId;
	}
 
	public String getStatus() {
		return status;
	}
 
	public void setStatus(String status) {
		this.status = status;
	}
}
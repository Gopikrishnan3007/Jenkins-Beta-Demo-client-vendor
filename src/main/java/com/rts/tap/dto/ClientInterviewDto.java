package com.rts.tap.dto;
 
import java.time.LocalDate;

import java.time.LocalTime;
 
public class ClientInterviewDto {
 
	private String interviewTitle;
 
	private LocalDate interviewDate;
 
	private LocalTime interviewFromTime;
 
	private LocalTime interviewToTime;
 
	private String meetingUrl;
 
	private String candidateStatus;
 
	private String others;
 
	private String mrfRequiredTechnology;
 
	private String firstName;
 
	private int level;
 
	private String currentCTC;
 
	private String expectedCTC;
 
	private Long requirementId;
 
	private Long subRequirementId;
 
	public ClientInterviewDto() {

		super();

	}
 
	public String getInterviewTitle() {

		return interviewTitle;

	}
 
	public void setInterviewTitle(String interviewTitle) {

		this.interviewTitle = interviewTitle;

	}
 
	public LocalDate getInterviewDate() {

		return interviewDate;

	}
 
	public void setInterviewDate(LocalDate interviewDate) {

		this.interviewDate = interviewDate;

	}
 
	public LocalTime getInterviewFromTime() {

		return interviewFromTime;

	}
 
	public void setInterviewFromTime(LocalTime interviewFromTime) {

		this.interviewFromTime = interviewFromTime;

	}
 
	public LocalTime getInterviewToTime() {

		return interviewToTime;

	}
 
	public void setInterviewToTime(LocalTime interviewToTime) {

		this.interviewToTime = interviewToTime;

	}
 
	public String getMeetingUrl() {

		return meetingUrl;

	}
 
	public void setMeetingUrl(String meetingUrl) {

		this.meetingUrl = meetingUrl;

	}
 
	public String getCandidateStatus() {

		return candidateStatus;

	}
 
	public void setCandidateStatus(String candidateStatus) {

		this.candidateStatus = candidateStatus;

	}
 
	
 
	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getMrfRequiredTechnology() {

		return mrfRequiredTechnology;

	}
 
	public void setMrfRequiredTechnology(String mrfRequiredTechnology) {

		this.mrfRequiredTechnology = mrfRequiredTechnology;

	}
 
	public String getFirstName() {

		return firstName;

	}
 
	public void setFirstName(String firstName) {

		this.firstName = firstName;

	}
 
	public int getLevel() {

		return level;

	}
 
	public void setLevel(int level) {

		this.level = level;

	}
 
	public String getCurrentCTC() {

		return currentCTC;

	}
 
	public void setCurrentCTC(String currentCTC) {

		this.currentCTC = currentCTC;

	}
 
	public String getExpectedCTC() {

		return expectedCTC;

	}
 
	public void setExpectedCTC(String expectedCTC) {

		this.expectedCTC = expectedCTC;

	}
 
	public Long getRequirementId() {

		return requirementId;

	}
 
	public void setRequirementId(Long requirementId) {

		this.requirementId = requirementId;

	}
 
	public Long getSubRequirementId() {

		return subRequirementId;

	}
 
	public void setSubRequirementId(Long subRequirementId) {

		this.subRequirementId = subRequirementId;

	}
 
}

 
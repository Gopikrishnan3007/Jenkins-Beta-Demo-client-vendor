package com.rts.tap.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class InterviewDto {

	private Long interviewId;

	private String interviewTitle;

	private LocalDate interviewDate;

	private LocalTime interviewFromTime;

	private LocalTime interviewToTime;

	private String meetingUrl;

	private String candidateStatus;

	private String Others;

	private String candidateInterviewRescheduleReason;

	private String interviewerInterviewRescheduleReason;

	public InterviewDto() {
		super();
	}

	public String getCandidateInterviewRescheduleReason() {
		return candidateInterviewRescheduleReason;
	}

	public void setCandidateInterviewRescheduleReason(String candidateInterviewRescheduleReason) {
		this.candidateInterviewRescheduleReason = candidateInterviewRescheduleReason;
	}

	public String getInterviewerInterviewRescheduleReason() {
		return interviewerInterviewRescheduleReason;
	}

	public void setInterviewerInterviewRescheduleReason(String interviewerInterviewRescheduleReason) {
		this.interviewerInterviewRescheduleReason = interviewerInterviewRescheduleReason;
	}

	public Long getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(Long interviewId) {
		this.interviewId = interviewId;
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
		return Others;
	}

	public void setOthers(String others) {
		Others = others;
	}

}

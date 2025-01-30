package com.rts.tap.dto;
 
import java.util.List;
 
import org.springframework.web.multipart.MultipartFile;
 
public class CandidateDto {

	private String firstName;

	private String lastName;

	private String gender;

	private String mobileNumber;

	private String email;

	private String totalExperience;

	private String relevantExperience;

	private String currentCompany;

	private String currentLocation;

	private String currentCTC;

	private String expectedCTC;

	private String employementMode;

	private String previousRole;

	private String noticePeriod;

	private String relocate;

	private String Education;

	private String resume;

	private String source;

	private long sourceId;

	private String Status;

	private String skill;

	private String location;

	private String panNumber;

	private Long MrfJdId;

	private List<String> certifications;

	private String visaDetails;

	private Boolean Document;

	private MultipartFile candidateResume;

	private MultipartFile candidateProfileImage;

	private Boolean isMatchingMrf = false;
 
	public MultipartFile getCandidateResume() {

		return candidateResume;

	}
 
	public void setCandidateResume(MultipartFile candidateResume) {

		this.candidateResume = candidateResume;

	}
	
	public Boolean getDocument() {

		return Document;

	}
 
	public MultipartFile getCandidateProfileImage() {

		return candidateProfileImage;

	}
 
	public void setCandidateProfileImage(MultipartFile candidateProfileImage) {

		this.candidateProfileImage = candidateProfileImage;

	}
 
	public void setDocument(Boolean document) {

		Document = document;

	}
 
	public String getFirstName() {

		return firstName;

	}
 
	public void setFirstName(String firstName) {

		this.firstName = firstName;

	}
 
	public String getLastName() {

		return lastName;

	}
 
	public void setLastName(String lastName) {

		this.lastName = lastName;

	}
 
	public String getGender() {

		return gender;

	}
 
	public void setGender(String gender) {

		this.gender = gender;

	}
 
	public String getMobileNumber() {

		return mobileNumber;

	}
 
	public void setMobileNumber(String mobileNumber) {

		this.mobileNumber = mobileNumber;

	}
 
	public String getEmail() {

		return email;

	}
 
	public void setEmail(String email) {

		this.email = email;

	}
 
	public String getTotalExperience() {

		return totalExperience;

	}
 
	public void setTotalExperience(String totalExperience) {

		this.totalExperience = totalExperience;

	}
 
	public String getRelevantExperience() {

		return relevantExperience;

	}
 
	public void setRelevantExperience(String relevantExperience) {

		this.relevantExperience = relevantExperience;

	}
 
	public String getCurrentCompany() {

		return currentCompany;

	}
 
	public void setCurrentCompany(String currentCompany) {

		this.currentCompany = currentCompany;

	}
 
	public String getCurrentLocation() {

		return currentLocation;

	}
 
	public void setCurrentLocation(String currentLocation) {

		this.currentLocation = currentLocation;

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
 
	public String getEmployementMode() {

		return employementMode;

	}
 
	public void setEmployementMode(String employementMode) {

		this.employementMode = employementMode;

	}
 
	public String getPreviousRole() {

		return previousRole;

	}
 
	public void setPreviousRole(String previousRole) {

		this.previousRole = previousRole;

	}
 
	public String getNoticePeriod() {

		return noticePeriod;

	}
 
	public void setNoticePeriod(String noticePeriod) {

		this.noticePeriod = noticePeriod;

	}
 
	public String getRelocate() {

		return relocate;

	}
 
	public void setRelocate(String relocate) {

		this.relocate = relocate;

	}
 
	public String getEducation() {

		return Education;

	}
 
	public void setEducation(String education) {

		Education = education;

	}
 
	public String getResume() {

		return resume;

	}
 
	public void setResume(String resume) {

		this.resume = resume;

	}
 
	public String getSource() {

		return source;

	}
 
	public void setSource(String source) {

		this.source = source;

	}
 
	public long getSourceId() {

		return sourceId;

	}
 
	public void setSourceId(long sourceId) {

		this.sourceId = sourceId;

	}
 
	public String getStatus() {

		return Status;

	}
 
	public void setStatus(String status) {

		Status = status;

	}
 
	public String getSkill() {

		return skill;

	}
 
	public void setSkill(String skill) {

		this.skill = skill;

	}
 
	public String getLocation() {

		return location;

	}
 
	public void setLocation(String location) {

		this.location = location;

	}
 
	public String getPanNumber() {

		return panNumber;

	}
 
	public void setPanNumber(String panNumber) {

		this.panNumber = panNumber;

	}
 
	public Long getMrfJdId() {

		return MrfJdId;

	}
 
	public void setMrfJdId(Long mrfJdId) {

		MrfJdId = mrfJdId;

	}
 
	public List<String> getCertifications() {

		return certifications;

	}
 
	public void setCertifications(List<String> certifications) {

		this.certifications = certifications;

	}
 
	public String getVisaDetails() {

		return visaDetails;

	}
 
	public void setVisaDetails(String visaDetails) {

		this.visaDetails = visaDetails;

	}
 
	public Boolean getIsMatchingMrf() {

		return isMatchingMrf;

	}
 
	public void setIsMatchingMrf(Boolean isMatchingMrf) {

		this.isMatchingMrf = isMatchingMrf;

	}
 
}

 
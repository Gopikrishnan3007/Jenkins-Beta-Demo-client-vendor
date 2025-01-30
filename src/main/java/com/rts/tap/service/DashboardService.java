package com.rts.tap.service;

import java.util.List;

import com.rts.tap.dto.ClientCandidatedto;
import com.rts.tap.dto.ClientInterviewDto;

public interface DashboardService {

	public Long HiredCandidatesByClientId(Long clientId);

	public Long ShortlistedCandidatesByClientId(Long clientId);

	public Long HiredCandidateList(Long requirementId);

	public Long ShortListedCandidateList(Long requirementId);

	public List<ClientCandidatedto> hiredPeopleData(Long clientId);

	public List<ClientCandidatedto> shortListedPeople(Long clientId);

	public List<ClientCandidatedto> shortListedByRequirment(Long requirementId);

	public List<ClientCandidatedto> hiredByRequirement(Long requirementId);
	
	public List<ClientInterviewDto> getAllClientInterviews(Long clientId);

	public Long getClientPartnerId(Long clientId);

}

package com.rts.tap.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rts.tap.constants.APIConstants;
import com.rts.tap.dto.ClientCandidatedto;

@FeignClient(name = "candidate-service",path = APIConstants.CLIENT_REQUESTMAPPING_API)
//@FeignClient(name = "api-service",path = APIConstants.CLIENT_REQUESTMAPPING_API)

public interface CandidateInterface {

	@GetMapping(path = APIConstants.GET_CLIENT_CANDIDATE_HIRED)
	Long getHiredCountByClient(@PathVariable("clientId") Long clientId);
	
	
	@GetMapping(path = APIConstants.GET_CLIENT_CANDIDATE_SHORTLISTED)
	public Long getShortlistedCountByClient(@PathVariable("clientId") Long clientId);
	
	@GetMapping(path = APIConstants.GET_CLIENT_HIRED)
	public Long getHiredCandidatesByClient(@PathVariable("requirementId") Long requirementId);
	
	
	@GetMapping(path = APIConstants.GET_CLIENT_SHORTLISTED)
	public Long getShortListedCandidatesByClient(@PathVariable("requirementId") Long requirementId);
	
	@GetMapping(path = APIConstants.GET_CANDIDATE_HIRED)
	public List<ClientCandidatedto> getHiredCandidates(@PathVariable("clientId") Long clientId);
	
	@GetMapping(path = APIConstants.GET_CANDIDATE_SHORTLISTED)
	public List<ClientCandidatedto> getShortlist(@PathVariable("clientId") Long clientId);
	
	@GetMapping(path = APIConstants.GET_CANDIDATE_SHORTLISTED_REQUIREMENT)
	public List<ClientCandidatedto> getShortlistRequirement(@PathVariable("requirementId") Long requirementId);
	
	@GetMapping(path = APIConstants.GET_CANDIDATE_HIRED_REQUIREMENT)
	public List<ClientCandidatedto> getHiredRequirement(@PathVariable("requirementId") Long requirementId);
	
	
}
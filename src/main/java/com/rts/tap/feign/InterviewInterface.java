package com.rts.tap.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rts.tap.constants.APIConstants;
import com.rts.tap.model.Interview;

@FeignClient(name = "interview-service",path = APIConstants.BASE_URL)
//@FeignClient(name = "api-service",path = APIConstants.BASE_URL)
public interface InterviewInterface {
	
	@GetMapping(path = APIConstants.GET_INTERVIEWS_BY_CLIENT)
	public List<Interview> getInterviewByClient(@RequestParam("clientId") Long clientId);
}

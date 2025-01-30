package com.rts.tap.feign;

import java.io.IOException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rts.tap.constants.APIConstants;
import com.rts.tap.dto.CandidateDto;

import jakarta.mail.MessagingException;

@FeignClient(name = "candidate-service", path = APIConstants.BASE_URL)
public interface CandidateCommunicationInterface {

	@PostMapping(path = "/candidates" + APIConstants.BULK_CANDIDATE_ADD)
	public ResponseEntity<String> addBulkCandidate(@ModelAttribute CandidateDto candidateDto)
			throws MessagingException, IOException;

	@PostMapping(value = "/applyjob", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addCandidate(@ModelAttribute CandidateDto candidateDto,
			@RequestParam("candidateResume") MultipartFile candidateResume) throws MessagingException, IOException;
}

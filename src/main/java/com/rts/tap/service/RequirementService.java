package com.rts.tap.service;

import java.util.HashMap;
import java.util.List;

import com.rts.tap.dto.RequirementDTO;
import com.rts.tap.model.MRF;
import com.rts.tap.model.Requirement;

import jakarta.mail.MessagingException;

public interface RequirementService {

	public String createRequirement(Requirement requirement, Long clientId);

	public String removeRequirement(Long requirementId);

	public List<Requirement> findAllRequirements();

	public String updateRequirement(RequirementDTO requirementdto);

	public List<Requirement> RequirementsByClient(Long clientId);

	public Integer requirementCount(Long clientId);

	public List<MRF> HiredCandidateList(Long requirementId);

	public List<Requirement> getRequirementsByClientId(Long clientId);

	public Requirement findRequirementsById(Long requirementId);

	public List<Requirement> getRequirementsByClientPartnerId(Long clientPartnerId);

	public List<Requirement> getRequirementsByBUHeadId(Long buHeadId);

	public HashMap<Long, Double> getBudgetMappingByRequirementId(Long requirementId);

	public RequirementDTO getRequirement(Long requirementId);

	String sendRenegotiationEmail(Long requirementId, String Content) throws MessagingException;

	public String getRenegotiationEmailContent(Long requirementId) throws MessagingException;

}

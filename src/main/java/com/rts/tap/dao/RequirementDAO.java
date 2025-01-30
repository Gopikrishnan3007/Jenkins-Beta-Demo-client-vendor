package com.rts.tap.dao;

import java.util.List;

import com.rts.tap.dto.RequirementDTO;
import com.rts.tap.model.MRF;
import com.rts.tap.model.Requirement;

public interface RequirementDAO {

	public String addRequirement(Requirement requirement, Long clientId);

	public String deleteRequirement(Long requirementId);

	public List<Requirement> getAllRequirements();

	public List<Requirement> getAllRequirementsByClient(Long clientId);

	public Integer requirementCount(Long clientId);

	public List<MRF> hiredCandidates(Long requirementId);

	public List<Requirement> getAllRequirementsByClientId(Long clientId);

	public Requirement getRequirementById(Long requirementId);

	public List<Requirement> getAllRequirementsByClientPartnerId(Long clientPartnerId);

	List<Requirement> getAllRequirementsByBUHeadId(Long buHeadId);

	public Requirement getRequirement(Long requirementId);

	public String updateRequirement(RequirementDTO requirementDTO);

}

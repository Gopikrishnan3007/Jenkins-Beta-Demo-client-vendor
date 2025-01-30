package com.rts.tap.daoimplementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rts.tap.constants.MessageConstants;
import com.rts.tap.dao.RequirementDAO;
import com.rts.tap.dto.RequirementDTO;
import com.rts.tap.dto.SubRequirementDTO;
import com.rts.tap.model.Client;
import com.rts.tap.model.MRF;
import com.rts.tap.model.Requirement;
import com.rts.tap.model.SubRequirements;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class RequirementDAOImp implements RequirementDAO {

	private EntityManager entityManager;

	public RequirementDAOImp(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public String addRequirement(Requirement requirementDTO, Long clientId) {
		if (requirementDTO == null) {
			return MessageConstants.ADD_REQUIREMENT_FAILURE;
		}

		if (clientId == null) {
			return "Client ID cannot be null";
		}

		Client client = entityManager.find(Client.class, clientId);
		if (client != null) {
			requirementDTO.setClient(client);

		} else {
			return "Client Not Found";
		}

		if (requirementDTO.getSubrequirement() != null) {
			for (SubRequirements subRequirement : requirementDTO.getSubrequirement()) {
				if (subRequirement.getRole() == null) {
					return "subrequirements required";
				} else {
					entityManager.persist(subRequirement);
				}
			}
		}

		entityManager.persist(requirementDTO);
		return MessageConstants.ADD_REQUIREMENT_SUCCESS;
	}

	@Override
	public String updateRequirement(RequirementDTO requirementDTO) {
	    if (isRequirementDTOValid(requirementDTO)) {
	        Requirement existingRequirement = findExistingRequirement(requirementDTO.getRequirementId());

	        if (existingRequirement != null) {
	            updateExistingRequirement(existingRequirement, requirementDTO);
	            updateSubRequirements(requirementDTO);

	            entityManager.merge(existingRequirement);
	            return MessageConstants.UPDATE_SUCCESS;
	        } else {
	            return "Requirement not found";
	        }
	    }
	    return MessageConstants.UPDATE_FAILURE;
	}

	private boolean isRequirementDTOValid(RequirementDTO requirementDTO) {
	    return requirementDTO != null && requirementDTO.getRequirementId() != null;
	}

	private Requirement findExistingRequirement(Long requirementId) {
	    return entityManager.find(Requirement.class, requirementId);
	}

	private void updateExistingRequirement(Requirement existingRequirement, RequirementDTO requirementDTO) {
	    existingRequirement.setTotalRequiredResourceCount(requirementDTO.getTotalRequiredResourceCount());
	    existingRequirement.setTimeline(requirementDTO.getTimeline());
	    existingRequirement.setBudget(requirementDTO.getBudget());
	}

	private void updateSubRequirements(RequirementDTO requirementDTO) {
	    if (requirementDTO.getSubRequirements() != null && !requirementDTO.getSubRequirements().isEmpty()) {
	        for (SubRequirementDTO subRequirementDTO : requirementDTO.getSubRequirements()) {
	            updateSubRequirement(subRequirementDTO);
	        }
	    }
	}

	private void updateSubRequirement(SubRequirementDTO subRequirementDTO) {
	    SubRequirements existingSubRequirement = entityManager.find(SubRequirements.class, subRequirementDTO.getSubRequirementId());
	    if (existingSubRequirement != null) {
	        existingSubRequirement.setRole(subRequirementDTO.getRole());
	        existingSubRequirement.setResourceCount(subRequirementDTO.getResourceCount());
	    }
	}

	
	@Override
	public String deleteRequirement(Long requirementId) {
		Requirement requirement = entityManager.find(Requirement.class, requirementId);
		if (requirement != null) {
			entityManager.remove(requirement);
			return MessageConstants.DELETE_SUCCESS;
		}
		return MessageConstants.DELETE_FAILURE;
	}

	@Override
	public List<Requirement> getAllRequirements() {
		return entityManager.createQuery("SELECT r FROM Requirement r", Requirement.class)
				.getResultList();

	}

	@Override
	public List<Requirement> getAllRequirementsByClient(Long clientId) {
		if (clientId == null) {
			return new ArrayList<>();
		}

		List<Requirement> requirements = entityManager
				.createQuery("SELECT r FROM Requirement r WHERE r.client.clientId = :clientId", Requirement.class)
				.setParameter("clientId", clientId).getResultList();

		List<Requirement> requirementDTOs = new ArrayList<>();
		for (Requirement requirement : requirements) {
			Requirement dto = requirement;
			requirementDTOs.add(dto);
		}

		return requirementDTOs;
	}

	@Override
	public Integer requirementCount(Long clientId) {
		if (clientId == null) {
			return 0;
		}

		Long count = entityManager
				.createQuery("SELECT COUNT(r) FROM Requirement r WHERE r.client.clientId = :clientId1", Long.class)
				.setParameter("clientId1", clientId).getSingleResult();

		return count.intValue();
	}

	@Override
	public List<MRF> hiredCandidates(Long mrfId) {
		String hql = "SELECT mc FROM MRF mc WHERE mc.mrfId = :mrfId";
		Query query = entityManager.createQuery(hql);
		query.setParameter("mrfId", mrfId);

		@SuppressWarnings("unchecked")
		List<MRF> hiredCandidates = query.getResultList();
		return hiredCandidates;
	}

	@Override
	public List<Requirement> getAllRequirementsByClientId(Long clientId) {
		Client client = entityManager.find(Client.class, clientId);
		if (client != null) {
			return entityManager
					.createQuery("SELECT r FROM Requirement r WHERE r.client.clientId = :clientId2", Requirement.class)
					.setParameter("clientId2", clientId).getResultList();
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public Requirement getRequirementById(Long requirementId) {
		Requirement requirement = entityManager.find(Requirement.class, requirementId);
		if (requirement != null) {
			return requirement;
		} else {
			return null;
		}

	}

	@Override
	public List<Requirement> getAllRequirementsByClientPartnerId(Long clientPartnerId) {
		String jpql = "SELECT r FROM Requirement r JOIN r.client c WHERE c.clientPartner.employeeId = :clientPartnerId";
		TypedQuery<Requirement> query = entityManager.createQuery(jpql, Requirement.class);
		query.setParameter("clientPartnerId", clientPartnerId);

		return query.getResultList();
	}

	@Override
	public List<Requirement> getAllRequirementsByBUHeadId(Long buHeadId) {
		String jpql = "SELECT r FROM Requirement r JOIN r.client c JOIN c.clientPartner cp WHERE cp.managerId = :buHeadId";
		TypedQuery<Requirement> query = entityManager.createQuery(jpql, Requirement.class);
		query.setParameter("buHeadId", buHeadId);

		return query.getResultList();
	}

	@Override
	public Requirement getRequirement(Long requirementId) {
		return entityManager.find(Requirement.class, requirementId);
	}

}

package com.rts.tap.serviceimplementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rts.tap.constants.MessageConstants;
import com.rts.tap.dao.RequirementDAO;
import com.rts.tap.dto.RequirementDTO;
import com.rts.tap.dto.SubRequirementDTO;
import com.rts.tap.exception.BudgetNotFoundException;
import com.rts.tap.exception.ClientNotFoundException;
import com.rts.tap.exception.EmailSendingException;
import com.rts.tap.exception.RequirementNotFoundException;
import com.rts.tap.feign.BudgetInterface;
import com.rts.tap.model.Budget;
import com.rts.tap.model.Client;
import com.rts.tap.model.MRF;
import com.rts.tap.model.Requirement;
import com.rts.tap.model.SubRequirements;
import com.rts.tap.service.RequirementService;
import com.rts.tap.utils.EmailUtil;

import jakarta.mail.MessagingException;

@Service
public class RequirementServiceImp implements RequirementService {

    private static final Logger logger = LoggerFactory.getLogger(RequirementServiceImp.class);

    private final RequirementDAO requirementDAO;
    private final BudgetInterface budgetInterface;
    private final EmailUtil emailUtil;

    public RequirementServiceImp(RequirementDAO requirementDAO, BudgetInterface budgetInterface, EmailUtil emailUtil) {
        this.requirementDAO = requirementDAO;
        this.budgetInterface = budgetInterface;
        this.emailUtil = emailUtil;
    }

    @Override
    public String createRequirement(Requirement requirement, Long clientId) {
        logger.info(MessageConstants.LOG_CREATING_REQUIREMENT, clientId);
        try {
            String result = requirementDAO.addRequirement(requirement, clientId);
            logger.info(MessageConstants.LOG_REQUIREMENT_CREATED, result);
            return result;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_CREATING_REQUIREMENT, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String updateRequirement(RequirementDTO requirementDTO) {
        logger.info(MessageConstants.LOG_UPDATING_REQUIREMENT, requirementDTO.getRequirementId());
        try {
            String result = requirementDAO.updateRequirement(requirementDTO);
            logger.info(MessageConstants.LOG_REQUIREMENT_UPDATED, result);
            return result;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_UPDATING_REQUIREMENT, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String removeRequirement(Long requirementId) {
        logger.info(MessageConstants.LOG_REMOVING_REQUIREMENT, requirementId);
        try {
            String result = requirementDAO.deleteRequirement(requirementId);
            logger.info(MessageConstants.LOG_REQUIREMENT_DELETED, result);
            return result;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_REMOVING_REQUIREMENT, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Requirement> findAllRequirements() {
        logger.info(MessageConstants.LOG_FETCHING_ALL_REQUIREMENTS);
        try {
            List<Requirement> requirements = requirementDAO.getAllRequirements();
            logger.info(MessageConstants.LOG_RETRIEVED_REQUIREMENTS, requirements.size());
            return requirements;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_REQUIREMENTS, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Requirement> RequirementsByClient(Long clientId) {
        logger.info(MessageConstants.LOG_FETCHING_REQUIREMENTS_BY_CLIENT, clientId);
        try {
            List<Requirement> requirements = requirementDAO.getAllRequirementsByClient(clientId);
            logger.info(MessageConstants.LOG_RETRIEVED_REQUIREMENTS_FOR_CLIENT, requirements.size(), clientId);
            return requirements;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_REQUIREMENTS_FOR_CLIENT, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Integer requirementCount(Long clientId) {
        logger.info(MessageConstants.LOG_COUNTING_REQUIREMENTS_FOR_CLIENT, clientId);
        try {
            Integer count = requirementDAO.requirementCount(clientId);
            logger.info(MessageConstants.LOG_COUNTED_REQUIREMENTS_FOR_CLIENT, count, clientId);
            return count;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_COUNTING_REQUIREMENTS, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<MRF> HiredCandidateList(Long requirementId) {
        logger.info(MessageConstants.LOG_FETCHING_HIRED_CANDIDATES, requirementId);
        try {
            List<MRF> hiredCandidates = requirementDAO.hiredCandidates(requirementId);
            logger.info(MessageConstants.LOG_RETRIEVED_HIRED_CANDIDATES, hiredCandidates.size(), requirementId);
            return hiredCandidates;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_HIRED_CANDIDATES, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Requirement> getRequirementsByClientId(Long clientId) {
        logger.info(MessageConstants.LOG_FETCHING_REQUIREMENTS_FOR_CLIENT_ID, clientId);
        try {
            return requirementDAO.getAllRequirementsByClientId(clientId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_REQUIREMENTS_FOR_CLIENT, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Requirement findRequirementsById(Long requirementId) {
        logger.info(MessageConstants.LOG_FETCHING_REQUIREMENT_BY_ID, requirementId);
        try {
            Requirement requirement = requirementDAO.getRequirementById(requirementId);
            if (requirement == null) {
                throw new RequirementNotFoundException(MessageConstants.ERROR_REQUIREMENT_NOT_FOUND + requirementId);
            }
            return requirement;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_REQUIREMENT, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Requirement> getRequirementsByClientPartnerId(Long clientPartnerId) {
        logger.info(MessageConstants.LOG_FETCHING_REQUIREMENTS_FOR_CLIENT_PARTNER, clientPartnerId);
        try {
            return requirementDAO.getAllRequirementsByClientPartnerId(clientPartnerId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_REQUIREMENTS_FOR_CLIENT_PARTNER, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Requirement> getRequirementsByBUHeadId(Long buHeadId) {
        logger.info(MessageConstants.LOG_FETCHING_REQUIREMENTS_FOR_BU_HEAD, buHeadId);
        try {
            return requirementDAO.getAllRequirementsByBUHeadId(buHeadId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_REQUIREMENTS_FOR_BU_HEAD, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public RequirementDTO getRequirement(Long requirementId) {
        logger.info(MessageConstants.LOG_FETCHING_REQUIREMENT_DETAIL, requirementId);
        try {
            Requirement requirement = requirementDAO.getRequirement(requirementId);
            if (requirement == null) {
                throw new RequirementNotFoundException(MessageConstants.ERROR_REQUIREMENT_NOT_FOUND + requirementId);
            }
            RequirementDTO requirementDTO = new RequirementDTO();
            requirementDTO.setRequirementId(requirement.getRequirementId());
            requirementDTO.setTotalRequiredResourceCount(requirement.getTotalRequiredResourceCount());
            
            requirementDTO.setTimeline(requirement.getTimeline());
            requirementDTO.setBudget(requirement.getBudget());

            List<SubRequirementDTO> subRequirements = new ArrayList<>();
            for (SubRequirements subRequirement : requirement.getSubrequirement()) {
                SubRequirementDTO subRequirementDTO = new SubRequirementDTO();
                subRequirementDTO.setSubRequirementId(subRequirement.getSubRequirementId());
                subRequirementDTO.setRole(subRequirement.getRole());
                subRequirementDTO.setResourceCount(subRequirement.getResourceCount());
                subRequirements.add(subRequirementDTO);
            }
            requirementDTO.setSubRequirements(subRequirements);
            logger.info(MessageConstants.LOG_RETRIEVED_REQUIREMENT_DTO, requirementId);
            return requirementDTO;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_REQUIREMENT, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public HashMap<Long, Double> getBudgetMappingByRequirementId(Long requirementId) {
        logger.info(MessageConstants.LOG_FETCHING_BUDGET_MAPPING, requirementId);
        try {
            HashMap<Long, Double> budgetMap = new HashMap<>();
            List<Budget> budgets = budgetInterface.getBudgetsByRequirementId(requirementId);
            if (budgets == null || budgets.isEmpty()) {
                throw new BudgetNotFoundException(MessageConstants.ERROR_BUDGET_NOT_FOUND + requirementId);
            }
            for (Budget budget : budgets) {
                Long subRequirementId = budget.getSubRequirementId() != null
                        ? budget.getSubRequirementId().getSubRequirementId()
                        : null;
                Double budgetForResource = budget.getBudgetForResource();
                if (subRequirementId != null) {
                    budgetMap.put(subRequirementId, budgetForResource);
                }
            }
            logger.info(MessageConstants.LOG_RETRIEVED_BUDGET_MAPPING, requirementId);
            return budgetMap;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_BUDGET_MAPPING, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String sendRenegotiationEmail(Long requirementId, String content) {
        logger.info(MessageConstants.LOG_SENDING_RENEGOTIATION_EMAIL, requirementId);
        try {
            Requirement requirement = requirementDAO.getRequirementById(requirementId);
            if (requirement == null) {
                throw new RequirementNotFoundException(MessageConstants.ERROR_REQUIREMENT_NOT_FOUND + requirementId);
            }
            if (requirement.getClient() == null) {
                throw new ClientNotFoundException(MessageConstants.ERROR_CLIENT_NOT_FOUND + requirementId);
            }
            Client client = requirement.getClient();
            String clientEmail = client.getClientEmail();
            emailUtil.sendHtmlEmail(clientEmail, MessageConstants.BUDGET_REQUEST, content);
            logger.info(MessageConstants.LOG_RENEGOTIATION_EMAIL_SENT, clientEmail);
            return MessageConstants.SUCCESS_RENEGOTIATION_EMAIL_SENT;
        } catch (MessagingException e) {
            logger.error(MessageConstants.ERROR_SENDING_EMAIL, e.getMessage());
            throw new EmailSendingException(MessageConstants.ERROR_SENDING_EMAIL + e.getMessage());
        }
    }

    @Override
    public String getRenegotiationEmailContent(Long requirementId) {
        logger.info(MessageConstants.LOG_FETCHING_RENEGOTIATION_EMAIL_CONTENT, requirementId);
        try {
            Requirement requirement = requirementDAO.getRequirementById(requirementId);
            if (requirement == null) {
                throw new RequirementNotFoundException(MessageConstants.ERROR_REQUIREMENT_NOT_FOUND + requirementId);
            }
            if (requirement.getClient() == null) {
                throw new ClientNotFoundException(MessageConstants.ERROR_CLIENT_NOT_FOUND + requirementId);
            }
            Client client = requirement.getClient();
            List<SubRequirements> allSubRequirements = requirement.getSubrequirement();
            HashMap<Long, Double> suggestedBudget = getBudgetMappingByRequirementId(requirementId);
            String clientEmail = client.getClientEmail();
            String clientName = client.getClientName();
            String organizationName = requirement.getClient().getClientOrganization().getOrganizationName();
            double budget = requirement.getBudget();
            String emailContent = emailUtil.createRenegotiationEmail(clientEmail, clientName, organizationName, budget,
                    allSubRequirements, suggestedBudget);
            logger.info(MessageConstants.LOG_RETRIEVED_EMAIL_CONTENT_FOR_RENEGOTIATION, requirementId);
            return emailContent;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_EMAIL_CONTENT, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
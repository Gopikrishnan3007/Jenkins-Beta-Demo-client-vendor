package com.rts.tap.serviceimplementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rts.tap.constants.MessageConstants;
import com.rts.tap.dao.SubRequirementDAO;
import com.rts.tap.exception.SubRequirementException;
import com.rts.tap.exception.SubRequirementNotFoundException;
import com.rts.tap.model.SubRequirements;
import com.rts.tap.service.SubRequirementService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubRequirementServiceImpl implements SubRequirementService {

    private final SubRequirementDAO dao;

    public SubRequirementServiceImpl(SubRequirementDAO dao) {
        this.dao = dao;
    }

    @Override
    public String addSubRequirement(SubRequirements subRequirements) {
        if (subRequirements == null) {
            throw new SubRequirementNotFoundException(MessageConstants.SUB_REQUIREMENT_CANNOT_BE_NULL);
        }

        try {
            dao.addSubRequirement(subRequirements);
            return MessageConstants.SUB_REQUIREMENT_ADDED_SUCCESSFULLY;
        } catch (Exception e) {
            throw new SubRequirementException(MessageConstants.ERROR_ADDING_SUB_REQUIREMENT, e);
        }
    }

    @Override
    public List<SubRequirements> viewAllSubRequirements() {
        try {
            List<SubRequirements> subRequirementsList = dao.viewAllSubRequirements();
            if (subRequirementsList == null || subRequirementsList.isEmpty()) {
                throw new SubRequirementNotFoundException(MessageConstants.NO_SUB_REQUIREMENTS_FOUND);
            }
            return subRequirementsList;
        } catch (Exception e) {
            throw new SubRequirementException(MessageConstants.ERROR_FETCHING_SUB_REQUIREMENTS, e);
        }
    }
}
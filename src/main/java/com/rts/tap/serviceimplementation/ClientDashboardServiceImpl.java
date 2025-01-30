package com.rts.tap.serviceimplementation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rts.tap.constants.MessageConstants;
import com.rts.tap.dao.ClientDashboardDAO;
import com.rts.tap.dto.ClientCandidatedto;
import com.rts.tap.dto.ClientInterviewDto;
import com.rts.tap.exception.CandidateDataFetchException;
import com.rts.tap.exception.ClientNotFoundException;
import com.rts.tap.exception.InterviewDataFetchException;
import com.rts.tap.feign.CandidateInterface;
import com.rts.tap.feign.InterviewInterface;
import com.rts.tap.model.Interview;
import com.rts.tap.service.DashboardService;

@Service
public class ClientDashboardServiceImpl implements DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(ClientDashboardServiceImpl.class);

    private ClientDashboardDAO clientDAO;
    private CandidateInterface candidateInterface;
    private InterviewInterface interviewInterface;

    public ClientDashboardServiceImpl(ClientDashboardDAO clientDAO, CandidateInterface candidateInterface,
            InterviewInterface interviewInterface) {
        this.clientDAO = clientDAO;
        this.candidateInterface = candidateInterface;
        this.interviewInterface = interviewInterface;
    }

    @Override
    public Long HiredCandidatesByClientId(Long clientId) {
        try {
            return candidateInterface.getHiredCountByClient(clientId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_HIRED_CANDIDATE_COUNT, e);
            throw new CandidateDataFetchException(MessageConstants.ERROR_FETCHING_HIRED_CANDIDATE_COUNT_FOR_CLIENT, e);
        }
    }

    @Override
    public Long ShortlistedCandidatesByClientId(Long clientId) {
        try {
            return candidateInterface.getShortlistedCountByClient(clientId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_SHORTLISTED_CANDIDATE_COUNT, e);
            throw new CandidateDataFetchException(MessageConstants.ERROR_FETCHING_SHORTLISTED_CANDIDATE_COUNT_FOR_CLIENT, e);
        }
    }

    @Override
    public Long HiredCandidateList(Long requirementId) {
        try {
            return candidateInterface.getHiredCandidatesByClient(requirementId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_HIRED_CANDIDATE_LIST, e);
            throw new CandidateDataFetchException(MessageConstants.ERROR_FETCHING_HIRED_CANDIDATE_LIST_FOR_REQUIREMENT, e);
        }
    }

    @Override
    public Long ShortListedCandidateList(Long requirementId) {
        try {
            return candidateInterface.getShortListedCandidatesByClient(requirementId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_SHORTLISTED_CANDIDATE_LIST, e);
            throw new CandidateDataFetchException(MessageConstants.ERROR_FETCHING_SHORTLISTED_CANDIDATE_LIST_FOR_REQUIREMENT, e);
        }
    }

    @Override
    public List<ClientCandidatedto> hiredPeopleData(Long clientId) {
        try {
            return candidateInterface.getHiredCandidates(clientId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_HIRED_PEOPLE_DATA, e);
            throw new CandidateDataFetchException(MessageConstants.ERROR_FETCHING_HIRED_PEOPLE_DATA_FOR_CLIENT, e);
        }
    }

    @Override
    public List<ClientCandidatedto> shortListedPeople(Long clientId) {
        try {
            return candidateInterface.getShortlist(clientId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_SHORTLISTED_PEOPLE, e);
            throw new CandidateDataFetchException(MessageConstants.ERROR_FETCHING_SHORTLISTED_PEOPLE_FOR_CLIENT, e);
        }
    }

    @Override
    public List<ClientCandidatedto> shortListedByRequirment(Long requirementId) {
        try {
            return candidateInterface.getShortlistRequirement(requirementId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_SHORTLISTED_CANDIDATES_BY_REQUIREMENT, e);
            throw new CandidateDataFetchException(MessageConstants.ERROR_FETCHING_SHORTLISTED_CANDIDATES_BY_REQUIREMENT_ID, e);
        }
    }

    @Override
    public List<ClientCandidatedto> hiredByRequirement(Long requirementId) {
        try {
            return candidateInterface.getHiredRequirement(requirementId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_HIRED_CANDIDATES_BY_REQUIREMENT, e);
            throw new CandidateDataFetchException(MessageConstants.ERROR_FETCHING_HIRED_CANDIDATES_BY_REQUIREMENT_ID, e);
        }
    }

    @Override
    public List<ClientInterviewDto> getAllClientInterviews(Long clientId) {
        if (clientId == null) {
            logger.warn(MessageConstants.CLIENT_ID_IS_NULL);
            return new ArrayList<>();
        }

        List<ClientInterviewDto> dtoList = new ArrayList<>();

        try {
            List<Interview> interviews = interviewInterface.getInterviewByClient(clientId);
            for (Interview inter : interviews) {
                ClientInterviewDto dto = new ClientInterviewDto();
                dto.setCandidateStatus(inter.getCandidateStatus());
                dto.setCurrentCTC(inter.getCandidate().getCurrentCTC());
                dto.setExpectedCTC(inter.getCandidate().getExpectedCTC());
                dto.setInterviewDate(inter.getInterviewDate());
                dto.setInterviewFromTime(inter.getInterviewFromTime());
                dto.setInterviewToTime(inter.getInterviewToTime());
                dto.setInterviewTitle(inter.getInterviewTitle());
                dto.setMeetingUrl(inter.getMeetingUrl());
                dto.setOthers(inter.getOthers());
                dto.setLevel(inter.getRecruitmentProcess().getLevel());
                dto.setFirstName(inter.getCandidate().getFirstName());
                dto.setMrfRequiredTechnology(inter.getRecruitmentProcess().getMrf().getMrfRequiredTechnology());
                dto.setRequirementId(inter.getRecruitmentProcess().getMrf().getRequirement().getRequirementId());
                dto.setSubRequirementId(inter.getRecruitmentProcess().getMrf().getSubRequirements().getSubRequirementId());

                dtoList.add(dto);
            }
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_ALL_INTERVIEWS, e);
            throw new InterviewDataFetchException(MessageConstants.ERROR_FETCHING_ALL_INTERVIEWS_FOR_CLIENT, e);
        }

        return dtoList;
    }

    @Override
    public Long getClientPartnerId(Long clientId) {
        try {
            return clientDAO.getClientPartnerId(clientId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_CLIENT_PARTNER_ID, e);
            throw new ClientNotFoundException(MessageConstants.ERROR_FETCHING_CLIENT_PARTNER_ID_FOR_CLIENT, e);
        }
    }
}
package com.rts.tap.serviceimplementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rts.tap.constants.MessageConstants;
import com.rts.tap.dao.ClientDao;
import com.rts.tap.dto.ClientRequestDto;
import com.rts.tap.exception.ClientNotFoundException;
import com.rts.tap.exception.ClientPersistenceException;
import com.rts.tap.feign.EmployeeInterface;
import com.rts.tap.model.Client;
import com.rts.tap.model.Client.ClientActivationStatus;
import com.rts.tap.model.ClientOrganization;
import com.rts.tap.model.Employee;
import com.rts.tap.service.ClientService;
import com.rts.tap.utils.DateUtils;
import com.rts.tap.utils.EmailUtil;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDAO;
    private final EmailUtil emailUtil;
    private final EmployeeInterface employeeInterface;

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    public ClientServiceImpl(ClientDao clientDAO, EmailUtil emailUtil, EmployeeInterface employeeInterface) {
        super();
        this.clientDAO = clientDAO;
        this.emailUtil = emailUtil;
        this.employeeInterface = employeeInterface;
    }

    @Override
    public Client saveClient(Client client, MultipartFile organizationLogo) throws IOException {
        try {
            byte[] organizationLogoBytes = organizationLogo.getBytes();
            ClientOrganization clientOrganization = client.getClientOrganization();

            if (clientOrganization != null) {
                clientOrganization.setOrganizationLogo(organizationLogoBytes);
                clientDAO.saveClientOrganization(clientOrganization);
            }

            client.setClientOrganization(clientOrganization);
            client.setCreatedAt(DateUtils.getCurrentDate());
            logger.info(MessageConstants.SAVING_CLIENT, client.getClientName());
            return clientDAO.saveClient(client);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_SAVING_CLIENT, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_SAVING_CLIENT, e); 
        }
    }

    @Override
    public void deleteClient(Long id) {
        try {
            logger.info(MessageConstants.DELETING_CLIENT_BY_ID, id);
            clientDAO.deleteClient(id);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_DELETING_CLIENT, id, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_DELETING_CLIENT, e);
        }
    }

    @Override
    public List<Client> viewAllClients() {
        try {
            logger.info(MessageConstants.FETCHING_ALL_CLIENTS);
            return clientDAO.viewAllClients();
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_ALL_CLIENTS, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_FETCHING_CLIENTS, e);
        }
    }

    @Override
    public Client viewClientById(Long id) {
        try {
            logger.info(MessageConstants.FETCHING_CLIENT_BY_ID, id);
            Client client = clientDAO.viewClientById(id);
            if (client == null) {
                throw new ClientNotFoundException(MessageConstants.CLIENT_NOT_FOUND_BY_ID + id);
            }
            return client;
        } catch (ClientNotFoundException e) {
            throw e; // Re-throw to allow the caller to handle this
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_CLIENT_BY_ID, id, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_FETCHING_CLIENT_BY_ID + id, e);
        }
    }

    @Override
    public Client updateClientDetails(Long clientId, Client client) {
        try {
            logger.info(MessageConstants.UPDATING_CLIENT_DETAILS, clientId);
            Client existingClient = clientDAO.viewClientById(clientId);
            if (existingClient == null) {
                throw new ClientNotFoundException(MessageConstants.CLIENT_NOT_FOUND_BY_ID + clientId);
            }

            // Update fields only if they are not null
            if (client.getClientName() != null) {
                existingClient.setClientName(client.getClientName());
            }
            if (client.getClientPosition() != null) {
                existingClient.setClientPosition(client.getClientPosition());
            }
            if (client.getClientMobile() != null) {
                existingClient.setClientMobile(client.getClientMobile());
            }
            if (client.getClientEmail() != null) {
                existingClient.setClientEmail(client.getClientEmail());
            }

            ClientOrganization existingClientOrganization = existingClient.getClientOrganization();
            ClientOrganization updatedClientOrganization = client.getClientOrganization();

            if (updatedClientOrganization.getOrganizationEmail() != null) {
                existingClientOrganization.setOrganizationEmail(updatedClientOrganization.getOrganizationEmail());
            }
            if (updatedClientOrganization.getOrganizationAddress() != null) {
                existingClientOrganization.setOrganizationAddress(updatedClientOrganization.getOrganizationAddress());
            }
            if (updatedClientOrganization.getOrganizationContactNumber() != null) {
                existingClientOrganization.setOrganizationContactNumber(updatedClientOrganization.getOrganizationContactNumber());
            }
            if (updatedClientOrganization.getOrganizationIndustry() != null) {
                existingClientOrganization.setOrganizationIndustry(updatedClientOrganization.getOrganizationIndustry());
            }

            existingClient.setClientStatus("Pending");
            existingClient.setActivationStatus(ClientActivationStatus.INACTIVE);

            logger.info(MessageConstants.CLIENT_UPDATED_SUCCESS, clientId);
            return clientDAO.updateClientDetails(existingClient);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_UPDATING_CLIENT_DETAILS, clientId, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_UPDATING_CLIENT_DETAILS, e);
        }
    }

    @Override
    public Client changeClientActivationStatus(Long clientId) {
        try {
            logger.info(MessageConstants.CHANGING_ACTIVATION_STATUS_CLIENT_ID, clientId);
            Client existingClient = clientDAO.viewClientById(clientId);
            if (existingClient == null) {
                throw new ClientNotFoundException(MessageConstants.CLIENT_NOT_FOUND_BY_ID + clientId);
            }

            existingClient.setActivationStatus(existingClient.getActivationStatus() == ClientActivationStatus.ACTIVE ? ClientActivationStatus.INACTIVE : ClientActivationStatus.ACTIVE);
            clientDAO.updateClientDetails(existingClient);
            logger.info(MessageConstants.UPDATED_ACTIVATION_STATUS, clientId);

            return existingClient;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_CHANGING_ACTIVATION_STATUS, clientId, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_CHANGING_ACTIVATION_STATUS, e);
        }
    }

    @Override
    public Client viewClientByMrfId(Long mrfId) {
        try {
            logger.info(MessageConstants.FETCHING_CLIENT_BY_MRF_ID, mrfId);
            Client client = clientDAO.viewClientByMrfId(mrfId);
            if (client == null) {
                throw new ClientNotFoundException(MessageConstants.CLIENT_NOT_FOUND_BY_MRF_ID + mrfId);
            }
            return client;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_CLIENT_BY_MRF_ID, mrfId, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_FETCHING_CLIENT_BY_MRF_ID, e);
        }
    }

    @Override
    public Long getClientIdByEmail(String email) {
        try {
            logger.info(MessageConstants.FETCHING_CLIENT_ID_BY_EMAIL, email);
            return clientDAO.getClientIdByEmail(email);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_CLIENT_ID_BY_EMAIL, email, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_FETCHING_CLIENT_ID_BY_EMAIL, e);
        }
    }

    @Override
    public List<Employee> getClientPartnerByBU(Long buId) {
        try {
            logger.info(MessageConstants.FETCHING_CLIENT_PARTNERS_BY_BU_ID, buId);
            return employeeInterface.getClientPartnerByBU(buId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_CLIENT_PARTNERS_BY_BU_ID, buId, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_FETCHING_CLIENT_PARTNERS, e);
        }
    }

    private String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 12);
    }

    @Override
    public Client updateClientForCredentials(Long id, String status, String reason) {
        try {
            logger.info(MessageConstants.UPDATING_CLIENT_FOR_CREDENTIALS, id, status, reason);
            Client client = clientDAO.viewClientById(id);
            if (client == null) {
                throw new ClientNotFoundException(MessageConstants.CLIENT_NOT_FOUND_BY_ID + id);
            }

            String password = generateRandomPassword();
            Employee clientPartner = client.getClientPartner();
            String organizationName = client.getClientOrganization() != null ? client.getClientOrganization().getOrganizationName() : "N/A";

            if (status.equalsIgnoreCase("Approved")) {
                client.setClientStatus("Approved");
                client.setActivationStatus(ClientActivationStatus.ACTIVE);
                client.setReason(reason);
                Client clientOne = clientDAO.createCredentialForClient(client, password);
                emailUtil.sendApprovalEmail(client.getClientEmail(), password, organizationName);
                String partnerEmail = clientPartner.getEmployeeEmail();
                emailUtil.sendApprovalNotificationEmail(partnerEmail, client.getClientName(), organizationName);
                return clientOne;
            } else {
                client.setClientStatus("Rejected");
                client.setActivationStatus(ClientActivationStatus.INACTIVE);
                client.setReason(reason);
                String partnerEmail = clientPartner.getEmployeeEmail();
                emailUtil.sendRejectionNotificationEmail(partnerEmail, client.getClientName(), organizationName, reason);
                return clientDAO.saveClient(client);
            }
        } catch (ClientNotFoundException e) {
            throw e; // Re-throw to allow the caller to handle this
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_UPDATING_CLIENT_FOR_CREDENTIALS, id, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_UPDATING_CLIENT_FOR_CREDENTIALS, e);
        }
    }

    @Override
    public List<Client> viewAllClientsByClientPartnerId(long clientPartnerId) {
        try {
            logger.info(MessageConstants.FETCHING_ALL_CLIENTS_BY_CLIENT_PARTNER_ID, clientPartnerId);
            return clientDAO.viewAllClientsByClientPartnerId(clientPartnerId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FETCHING_ALL_CLIENTS_BY_CLIENT_PARTNER_ID, clientPartnerId, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_FETCHING_CLIENTS_BY_CLIENT_PARTNER_ID, e);
        }
    }

    @Override
    public boolean clientEmailExists(String email) throws Exception {
        try {
            logger.info(MessageConstants.CHECKING_CLIENT_EMAIL_EXISTS, email);
            return clientDAO.clientEmailExists(email);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_CHECKING_CLIENT_EMAIL_EXISTS, email, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_CHECKING_CLIENT_EMAIL_EXISTS, e);
        }
    }

    @Override
    public ClientOrganization findOrganizationById(Long organizationId) {
        try {
            logger.info(MessageConstants.FINDING_ORGANIZATION_BY_ID, organizationId);
            return clientDAO.findOrgnizationId(organizationId);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_FINDING_ORGANIZATION_BY_ID, organizationId, e.getMessage());
            throw new ClientPersistenceException(MessageConstants.FAILED_FINDING_ORGANIZATION, e);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> addClient(String clientJson, MultipartFile organizationLogo) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClientRequestDto request = objectMapper.readValue(clientJson, ClientRequestDto.class);
        ClientOrganization organization = request.getOrganization();

        List<String> errorMessages = new ArrayList<>();
        List<Client> savedClients = new ArrayList<>();

        for (Client client : request.getClients()) {
            client.setClientOrganization(organization);
            client.setClientId(null);

            if (clientEmailExists(client.getClientEmail())) {
                String errorMessage = String.format(MessageConstants.ERROR_SAVING_CLIENT_EMAIL_EXISTS,
                        client.getClientName(), client.getClientEmail());
                errorMessages.add(errorMessage);
                logger.warn(errorMessage);
                continue;
            }

            try {
                logger.info(MessageConstants.ATTEMPTING_TO_SAVE_CLIENT, client.getClientName());
                saveClient(client, organizationLogo);
                savedClients.add(client);
                logger.info(MessageConstants.SUCCESSFULLY_SAVED_CLIENT, client.getClientName());
            } catch (DataIntegrityViolationException ex) {
                String errorMessage = String.format(MessageConstants.ERROR_SAVING_CLIENT, client.getClientName(),
                        ex.getMessage());
                errorMessages.add(errorMessage);
                logger.error(errorMessage);
            } catch (Exception e) {
                String errorMessage = String.format(MessageConstants.UNEXPECTED_ERROR_SAVING_CLIENT, client.getClientName(),
                        e.getMessage());
                errorMessages.add(errorMessage);
                logger.error(errorMessage);
            }
        }

        Map<String, Object> response = new HashMap<>();

        if (!errorMessages.isEmpty()) {
            response.put("status", "error");
            response.put("messages", errorMessages);
            return ResponseEntity.badRequest().body(response);
        }

        response.put("status", "success");
        response.put("message", MessageConstants.ALL_CLIENTS_PROCESSED_SUCCESSFULLY);
        response.put("savedClients", savedClients);

        return ResponseEntity.ok(response);
    }
}
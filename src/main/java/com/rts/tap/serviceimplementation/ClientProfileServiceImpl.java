package com.rts.tap.serviceimplementation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rts.tap.dao.ClientDao;
import com.rts.tap.dao.ClientProfileDAO;
import com.rts.tap.exception.ClientConflictException;
import com.rts.tap.exception.ClientNotFoundException;
import com.rts.tap.exception.ClientUpdateException;
import com.rts.tap.exception.InvalidClientDataException;
import com.rts.tap.model.Client;
import com.rts.tap.service.ClientProfileService;
import com.rts.tap.constants.MessageConstants;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientProfileServiceImpl implements ClientProfileService {

    private ClientProfileDAO clientProfileDAO;
    private ClientDao clientDao;
    private static final Logger logger = LoggerFactory.getLogger(ClientProfileServiceImpl.class);

    public ClientProfileServiceImpl(ClientProfileDAO clientProfileDAO, ClientDao clientDao) {
        super();
        this.clientProfileDAO = clientProfileDAO;
        this.clientDao = clientDao;
    }

    @Override
    public Client getClientById(Long clientId) {
        try {
            return clientProfileDAO.getClientById(clientId);
        } catch (Exception e) {
            logger.error(MessageConstants.CLIENT_NOT_FOUND_WITH_ID, clientId, e);
            throw new ClientNotFoundException(MessageConstants.CLIENT_NOT_FOUND_WITH_ID + clientId);
        }
    }

    @Override
    public Client getClientByEmail(String clientEmail) {
        try {
            return clientProfileDAO.getClientByEmail(clientEmail);
        } catch (Exception e) {
            logger.error(MessageConstants.CLIENT_NOT_FOUND_WITH_EMAIL, clientEmail, e);
            throw new ClientNotFoundException(MessageConstants.CLIENT_NOT_FOUND_WITH_EMAIL + clientEmail);
        }
    }

    @Override
    public String updateClientById(Long clientId, String clientPosition, String clientMobile) {
        try {
            Client client = clientProfileDAO.getClientById(clientId);

            if (client == null) {
                throw new ClientNotFoundException(MessageConstants.CLIENT_NOT_FOUND_WITH_ID + clientId);
            }
            if (clientPosition != null && clientPosition.isEmpty()) {
                throw new InvalidClientDataException(MessageConstants.CLIENT_POSITION_CANNOT_BE_EMPTY);
            }
            if (clientMobile != null && clientMobile.isEmpty()) {
                throw new InvalidClientDataException(MessageConstants.CLIENT_MOBILE_CANNOT_BE_EMPTY);
            }
            if (clientPosition != null) {
                client.setClientPosition(clientPosition);
            }
            if (clientMobile != null) {
                client.setClientMobile(clientMobile);
            }

            Client updatedClient = clientProfileDAO.updateClientById(clientId, client.getClientPosition(), client.getClientMobile());
            if (updatedClient == null) {
                throw new ClientUpdateException(MessageConstants.CLIENT_UPDATE_FAILED);
            }
            return MessageConstants.CLIENT_UPDATED_SUCCESS;
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_UPDATING_CLIENT_BY_ID, clientId, e);
            if (e instanceof ClientNotFoundException) {
                throw e;
            }
            throw new ClientUpdateException(MessageConstants.ERROR_UPDATING_CLIENT_DETAILS);
        }
    }

    @Override
    public ResponseEntity<String> updateClientByEmail(String clientEmail, String clientPosition, String clientMobile) {
        try {
            Client client = clientProfileDAO.getClientByEmail(clientEmail);

            if (client == null) {
                throw new ClientNotFoundException(MessageConstants.CLIENT_NOT_FOUND_WITH_EMAIL + clientEmail);
            }
            if (clientPosition != null && clientPosition.isEmpty()) {
                throw new InvalidClientDataException(MessageConstants.CLIENT_POSITION_CANNOT_BE_EMPTY);
            }
            if (clientMobile != null && clientMobile.isEmpty()) {
                throw new InvalidClientDataException(MessageConstants.CLIENT_MOBILE_CANNOT_BE_EMPTY);
            }
            if (clientPosition != null) {
                client.setClientPosition(clientPosition);
            }
            if (clientMobile != null) {
                client.setClientMobile(clientMobile);
            }

            Client updatedClient = clientProfileDAO.updateClientByEmail(clientEmail, client.getClientPosition(), client.getClientMobile());
            if (updatedClient == null) {
                throw new ClientUpdateException(MessageConstants.CLIENT_UPDATE_FAILED);
            }
            return ResponseEntity.ok(MessageConstants.CLIENT_UPDATED_SUCCESS);
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_UPDATING_CLIENT_BY_EMAIL, clientEmail, e);
            if (e instanceof ClientNotFoundException) {
                return new ResponseEntity<>(MessageConstants.CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(MessageConstants.ERROR_UPDATING_CLIENT_DETAILS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Client> viewAllClients() {
        try {
            return clientDao.viewAllClients();
        } catch (Exception e) {
            logger.error(MessageConstants.ERROR_VIEWING_ALL_CLIENTS, e);
            throw new ClientConflictException(MessageConstants.ERROR_VIEWING_ALL_CLIENTS);
        }
    }
}
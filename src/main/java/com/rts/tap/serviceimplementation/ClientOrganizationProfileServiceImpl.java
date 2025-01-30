package com.rts.tap.serviceimplementation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rts.tap.constants.MessageConstants;
import com.rts.tap.dao.ClientOrganizationProfileDAO;
import com.rts.tap.exception.ClientOrganizationNotFoundException;
import com.rts.tap.exception.FileSizeExceededException;
import com.rts.tap.exception.InvalidFileTypeException;
import com.rts.tap.exception.OrganizationLogoException;
import com.rts.tap.model.ClientOrganization;
import com.rts.tap.service.ClientOrganizationProfileService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientOrganizationProfileServiceImpl implements ClientOrganizationProfileService {

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png");
    private static final long MAX_FILE_SIZE = 153600; // 150 KB in bytes

    private ClientOrganizationProfileDAO clientOrganizationProfileDAO;

    public ClientOrganizationProfileServiceImpl(ClientOrganizationProfileDAO clientOrganizationProfileDAO) {
        super();
        this.clientOrganizationProfileDAO = clientOrganizationProfileDAO;
    }

    @Override
    public String updateOrganizationLogo(Long clientId, MultipartFile organizationLogo) {
        if (organizationLogo == null || organizationLogo.isEmpty()) {
            throw new OrganizationLogoException(MessageConstants.ORGANIZATION_LOGO_MISSING);
        }
        if (organizationLogo.getSize() > MAX_FILE_SIZE) {
            throw new FileSizeExceededException(MessageConstants.FILE_SIZE_EXCEEDS_LIMIT);
        }
        if (!ALLOWED_CONTENT_TYPES.contains(organizationLogo.getContentType())) {
            throw new InvalidFileTypeException(MessageConstants.INVALID_FILE_TYPE);
        }
        
        try {
            ClientOrganization clientOrganization = clientOrganizationProfileDAO.findClientOrganizationByClientId(clientId);
            if (clientOrganization == null) {
                throw new ClientOrganizationNotFoundException(MessageConstants.CLIENT_ORGANIZATION_NOT_FOUND);
            }
            byte[] logoBytes = organizationLogo.getBytes();
            clientOrganization.setOrganizationLogo(logoBytes);
            clientOrganizationProfileDAO.updateOrganizationLogo(clientOrganization);
            return MessageConstants.ORGANIZATION_LOGO_UPDATED_SUCCESS;
        } catch (IOException e) {
            throw new OrganizationLogoException(MessageConstants.ERROR_UPDATING_ORGANIZATION_LOGO + e.getMessage());
        }
    }
}
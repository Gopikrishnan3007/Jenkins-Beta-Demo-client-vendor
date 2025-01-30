package com.rts.tap.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rts.tap.constants.APIConstants;
import com.rts.tap.constants.MessageConstants;
import com.rts.tap.dto.ClientCandidatedto;
import com.rts.tap.dto.ClientInterviewDto;
import com.rts.tap.dto.RequirementDTO;
import com.rts.tap.model.Client;
import com.rts.tap.model.Client.ClientActivationStatus;
import com.rts.tap.model.Employee;
import com.rts.tap.model.Requirement;
import com.rts.tap.model.SubRequirements;
import com.rts.tap.service.ClientOrganizationProfileService;
import com.rts.tap.service.ClientProfileService;
import com.rts.tap.service.ClientService;
import com.rts.tap.service.DashboardService;
import com.rts.tap.service.RequirementService;
import com.rts.tap.service.SubRequirementService;

import jakarta.mail.MessagingException;

@RequestMapping(APIConstants.CLIENT_REQUESTMAPPING_API)
@RestController
@CrossOrigin(APIConstants.FRONT_END_URL)
public class ClientController {

    private DashboardService clientService;
    private ClientService service;
    private ClientOrganizationProfileService clientOrganizationProfileService;
    private ClientProfileService clientProfileService;
    private RequirementService requirementService;
    private SubRequirementService subRequirementService;

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    public ClientController(DashboardService clientService, ClientService service,
            ClientOrganizationProfileService clientOrganizationProfileService,
            ClientProfileService clientProfileService, RequirementService requirementService,
            SubRequirementService subRequirementService) {
        super();
        this.clientService = clientService;
        this.service = service;
        this.clientOrganizationProfileService = clientOrganizationProfileService;
        this.clientProfileService = clientProfileService;
        this.requirementService = requirementService;
        this.subRequirementService = subRequirementService;
    }

    @GetMapping(APIConstants.GET_CLIENT_CANDIDATE_HIRED)
    public ResponseEntity<Long> getHiredCountByClient(@PathVariable("clientId") Long clientId) {
        Long count = clientService.HiredCandidatesByClientId(clientId);
        logger.info("Retrieved hired count for client ID {}: {}", clientId, count);
        return ResponseEntity.ok(count);
    }

    @GetMapping(APIConstants.GET_CLIENT_CANDIDATE_SHORTLISTED)
    public ResponseEntity<Long> getShortlistedCountByClient(@PathVariable("clientId") Long clientId) {
        Long count = clientService.ShortlistedCandidatesByClientId(clientId);
        logger.info("Retrieved shortlisted count for client ID {}: {}", clientId, count);
        return ResponseEntity.ok(count);
    }

    @GetMapping(APIConstants.GET_CLIENT_HIRED)
    public ResponseEntity<Long> getHiredCandidatesByrequirement(@PathVariable("requirementId") Long requirementId) {
        Long count = clientService.HiredCandidateList(requirementId);
        logger.info("Retrieved hired candidates count for requirement ID {}: {}", requirementId, count);
        return ResponseEntity.ok(count);
    }

    @GetMapping(APIConstants.GET_CLIENT_SHORTLISTED)
    public ResponseEntity<Long> getShortListedCandidatesByClient(@PathVariable("requirementId") Long requirementId) {
        Long count = clientService.ShortListedCandidateList(requirementId);
        logger.info("Retrieved shortlisted candidates count for requirement ID {}: {}", requirementId, count);
        return ResponseEntity.ok(count);
    }

    @GetMapping(APIConstants.GET_CANDIDATE_HIRED)
    public ResponseEntity<List<ClientCandidatedto>> getHiredCandidates(@PathVariable("clientId") Long clientId) {
        List<ClientCandidatedto> hiredCandidates = clientService.hiredPeopleData(clientId);
        logger.info("Retrieved hired candidates data for client ID {}: size {}", clientId, hiredCandidates.size());
        return ResponseEntity.ok(hiredCandidates);
    }

    @GetMapping(APIConstants.GET_CANDIDATE_SHORTLISTED)
    public ResponseEntity<List<ClientCandidatedto>> getShortlist(@PathVariable("clientId") Long clientId) {
        List<ClientCandidatedto> shortlistedCandidates = clientService.shortListedPeople(clientId);
        logger.info("Retrieved shortlisted candidates data for client ID {}: size {}", clientId, shortlistedCandidates.size());
        return ResponseEntity.ok(shortlistedCandidates);
    }

    @GetMapping(APIConstants.GET_CANDIDATE_SHORTLISTED_REQUIREMENT)
    public ResponseEntity<List<ClientCandidatedto>> getShortlistRequirement(@PathVariable("requirementId") Long requirementId) {
        List<ClientCandidatedto> shortlistedCandidates = clientService.shortListedByRequirment(requirementId);
        logger.info("Retrieved shortlisted candidates for requirement ID {}: size {}", requirementId, shortlistedCandidates.size());
        return ResponseEntity.ok(shortlistedCandidates);
    }

    @GetMapping(APIConstants.GET_CANDIDATE_HIRED_REQUIREMENT)
    public ResponseEntity<List<ClientCandidatedto>> getHiredRequirement(@PathVariable("requirementId") Long requirementId) {
        List<ClientCandidatedto> hiredCandidates = clientService.hiredByRequirement(requirementId);
        logger.info("Retrieved hired candidates for requirement ID {}: size {}", requirementId, hiredCandidates.size());
        return ResponseEntity.ok(hiredCandidates);
    }

    @GetMapping(APIConstants.CLIENT_ID_BY_EMAIL)
    public ResponseEntity<Long> getClientIdByEmail(@RequestParam("email") String email) {
        Long id = service.getClientIdByEmail(email);
        logger.info("Retrieved client ID by email {}: {}", email, id);
        return ResponseEntity.ok(id);
    }

    @GetMapping(APIConstants.CLIENT_INTERVIEWS)
    public ResponseEntity<List<ClientInterviewDto>> getClientInterviews(@RequestParam("clientId") Long clientId) {
        List<ClientInterviewDto> interview = clientService.getAllClientInterviews(clientId);
        logger.info("Retrieved client interviews for client ID {}: size {}", clientId, interview.size());
        return ResponseEntity.ok(interview);
    }

    @GetMapping(APIConstants.GET_CLIENT_PARTNER_ID_BY_CLIENT)
    public ResponseEntity<Long> getClientPartnerId(@RequestParam("clientId") Long clientId) {
        Long clientPartnerId = clientService.getClientPartnerId(clientId);
        logger.info("Retrieved client partner ID for client ID {}: {}", clientId, clientPartnerId);
        return ResponseEntity.ok(clientPartnerId);
    }

    @PatchMapping(path = APIConstants.UPDATE_CLIENT_ORGANIZATION_LOGO_BY_ID)
    public ResponseEntity<String> updateClientOrganizationLogo(@PathVariable("clientId") Long clientId,
            @RequestParam("organizationLogo") MultipartFile organizationLogo) {
        String response = clientOrganizationProfileService.updateOrganizationLogo(clientId, organizationLogo);
        if (response.equals("Organization logo updated successfully!")) {
            logger.info("Successfully updated organization logo for client ID {}.", clientId);
            return ResponseEntity.ok(response);
        } else {
            logger.warn("Failed to update organization logo for client ID {}: {}", clientId, response);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping(path = APIConstants.GET_CLIENT_BY_ID)
    public ResponseEntity<Client> getClientDetailsById(@PathVariable("clientId") Long clientId) {
        Client response = clientProfileService.getClientById(clientId);
        logger.info("Retrieved client details by ID {}: {}", clientId, response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = APIConstants.GET_CLIENT_BY_EMAIL)
    public ResponseEntity<Client> getClientDetailsByEmail(@PathVariable("clientEmail") String clientEmail) {
        Client response = clientProfileService.getClientByEmail(clientEmail);
        logger.info("Retrieved client details by email {}: {}", clientEmail, response);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = APIConstants.UPDATE_CLIENT_PROFILE_BY_ID)
    public ResponseEntity<String> updateClientDetails(@PathVariable("clientId") Long clientId,
            @RequestBody Client updateClient) {
        String response = clientProfileService.updateClientById(clientId, updateClient.getClientPosition(),
                updateClient.getClientMobile());
        logger.info("Updated client ID {}: {}", clientId, response);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = APIConstants.UPDATE_CLIENT_PROFILE_BY_EMAIL)
    public ResponseEntity<String> updateClientDetails(@PathVariable("clientEmail") String clientEmail,
            @RequestBody Client updateClient) {
        ResponseEntity<String> response = clientProfileService.updateClientByEmail(clientEmail,
                updateClient.getClientPosition(), updateClient.getClientMobile());
        logger.info("Updated client by email {}: {}", clientEmail, response.getBody());
        return response;
    }

    @GetMapping(APIConstants.GET_ALL_CLIENTS)
    public ResponseEntity<List<Client>> viewAllClients() {
        List<Client> clients = clientProfileService.viewAllClients();
        logger.info("Retrieved all clients: size {}", clients.size());
        return ResponseEntity.ok(clients);
    }

    @PostMapping(path = APIConstants.REQUIREMENT_ADD_API)
    public ResponseEntity<String> createRequirement(@RequestBody Requirement requirementDTO) {
        logger.info("Creating requirement for client ID: {}", requirementDTO.getClient().getClientId());
        Long clientId = requirementDTO.getClient().getClientId();
        String response = requirementService.createRequirement(requirementDTO, clientId);
        logger.info("Successfully created requirement with response: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(path = APIConstants.REQUIREMENT_DELETE_API)
    public ResponseEntity<String> deleteRequirement(@PathVariable("requirementId") Long requirementId) {
        logger.info("Attempting to delete requirement with ID: {}", requirementId);
        try {
            String res = requirementService.removeRequirement(requirementId);
            logger.info("Successfully deleted requirement with ID: {}", requirementId);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            logger.error("Error deleting requirement with ID {}: {}", requirementId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting requirement: " + e.getMessage());
        }
    }

    @GetMapping(path = APIConstants.REQUIREMENT_GETALL_REQUIREMENT_API)
    public ResponseEntity<List<Requirement>> getAllRequirements() {
        logger.info("Fetching all requirements");
        List<Requirement> requirements = requirementService.findAllRequirements();
        return ResponseEntity.ok(requirements); // Returns 200 OK with the list
    }

    @GetMapping(path = APIConstants.REQUIREMENT_REQUIREMENTBY_CLIENT_API)
    public ResponseEntity<List<Requirement>> findRequirementsByClientId(@PathVariable("clientId") Long clientId) {
        logger.info("Fetching requirements for client ID: {}", clientId);
        List<Requirement> requirements = requirementService.RequirementsByClient(clientId);
        return ResponseEntity.ok(requirements);
    }

    @GetMapping(path = APIConstants.REQUIREMENT_COUNT_CLIENT_API)
    public ResponseEntity<Integer> clientRequirementCount(@PathVariable("clientId") Long clientId) {
        logger.info("Getting requirement count for client ID: {}", clientId);
        Integer count = requirementService.requirementCount(clientId);
        return ResponseEntity.ok(count);
    }

    @GetMapping(path = APIConstants.REQUIREMENT_LIST_BY_CLIENT_API)
    public ResponseEntity<List<Requirement>> clientRequirements(@PathVariable("clientId") Long clientId) {
        logger.info("Fetching requirements for client ID: {}", clientId);
        List<Requirement> requirements = requirementService.getRequirementsByClientId(clientId);
        return ResponseEntity.ok(requirements);
    }

    @GetMapping(path = APIConstants.REQUIREMENT_GET_REQUIREMENTBYID_API)
    public ResponseEntity<Requirement> getRequirementsById(@PathVariable("requirementId") Long requirementId) {
        logger.info("Fetching requirement by ID: {}", requirementId);
        Requirement requirements = requirementService.findRequirementsById(requirementId);
        return ResponseEntity.ok(requirements);
    }

    @GetMapping(APIConstants.GET_REQUIREMENT_BY_CLIENTPARTNERID)
    public ResponseEntity<List<Requirement>> viewAllRequirementByClientPartnerId(@PathVariable("clientPartnerId") Long clientPartnerId) {
        logger.info("Fetching all requirements for client partner ID: {}", clientPartnerId);
        List<Requirement> requirements = requirementService.getRequirementsByClientPartnerId(clientPartnerId);
        return ResponseEntity.ok(requirements);
    }

    @GetMapping(path = APIConstants.GET_REQUIREMENTS_BY_BUHEADID)
    public ResponseEntity<List<Requirement>> getRequirementsByBUHeadId(@PathVariable("buHeadId") Long buHeadId) {
        logger.info("Fetching requirements for BU Head ID: {}", buHeadId);
        List<Requirement> requirements = requirementService.getRequirementsByBUHeadId(buHeadId);
        return ResponseEntity.ok(requirements);
    }

    @GetMapping(path = APIConstants.REQUIREMENT_GET_BY_ID_API)
    public ResponseEntity<RequirementDTO> getRequirement(@PathVariable Long requirementId) {
        logger.info("Fetching requirement details for ID: {}", requirementId);
        RequirementDTO requirementDTO = requirementService.getRequirement(requirementId);
        return ResponseEntity.status(HttpStatus.OK).body(requirementDTO);
    }

    @PutMapping(path = APIConstants.REQUIREMENT_UPDATE_API)
    public ResponseEntity<String> updateRequirement(@RequestBody RequirementDTO requirementDTO) {
        logger.info("Updating requirement with ID: {}", requirementDTO.getRequirementId());
        String response = requirementService.updateRequirement(requirementDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(APIConstants.RENEGOTIATIONEMAIL)
    public ResponseEntity<String> sendRenegotiationEmail(@PathVariable Long requirementId, @RequestBody String content)
            throws MessagingException {
        logger.info("Sending renegotiation email for requirement ID: {}", requirementId);
        requirementService.sendRenegotiationEmail(requirementId, content);
        return ResponseEntity.ok("Renegotiation email sent successfully.");
    }

    @GetMapping(APIConstants.GET_RENEGOTIATION_EMAIL_CONTENT)
    public ResponseEntity<String> getRenegotiationEmailContent(@PathVariable Long requirementId) {
        logger.info("Fetching renegotiation email content for requirement ID: {}", requirementId);
        try {
            String emailContent = requirementService.getRenegotiationEmailContent(requirementId);
            return ResponseEntity.ok(emailContent);
        } catch (Exception e) {
            logger.error("Failed to fetch email content for requirement ID {}: {}", requirementId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch email content.");
        }
    }

    @PostMapping(path = APIConstants.ADD_SUB_REQUIREMENTS)
    public ResponseEntity<String> createSubRequirement(@RequestBody SubRequirements subRequirements) {
        String response = subRequirementService.addSubRequirement(subRequirements);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = APIConstants.VIEW_SUB_REQUIREMENTS)
    public ResponseEntity<List<SubRequirements>> getAllSubRequirements() {
        List<SubRequirements> subRequirements = subRequirementService.viewAllSubRequirements();
        return ResponseEntity.ok(subRequirements);
    }

    @PostMapping(APIConstants.ADD_CLIENTS)
    public ResponseEntity<Map<String, Object>> saveClients(@RequestParam("client") String clientJson,
            @RequestParam("organizationLogo") MultipartFile organizationLogo) throws Exception {
        return service.addClient(clientJson, organizationLogo);
    }

    @GetMapping(APIConstants.GET_CLIENTBYID)
    public ResponseEntity<Client> viewClientById(@PathVariable("id") Long id) {
        logger.info("Fetching client by ID: {}", id);
        Client client = service.viewClientById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping(APIConstants.GET_CLIENT_BY_MRFID)
    public ResponseEntity<Client> viewClientNameById(@PathVariable("mrfId") Long mrfId) {
        logger.info("Fetching client by MRF ID: {}", mrfId);
        Client client = service.viewClientById(mrfId);
        return ResponseEntity.ok(client);
    }

    @GetMapping(APIConstants.GET_CLIENTPARTNER_BY_BU)
    public ResponseEntity<List<Employee>> getClientPartnerByBU(@PathVariable("buId") Long buId) {
        logger.info("Fetching client partners for BU ID: {}", buId);
        List<Employee> employee = service.getClientPartnerByBU(buId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping(APIConstants.UPDATE_CLIENT_STATUS)
    public ResponseEntity<String> updateClient(@PathVariable("id") Long id, @RequestParam String status,
            @RequestParam String reason) {
        logger.info("Updating client ID {} with status: {} and reason: {}", id, status, reason);
        service.updateClientForCredentials(id, status, reason);
        return ResponseEntity.ok(MessageConstants.CLIENT_UPDATED_SUCCESS);
    }

    @PutMapping(APIConstants.UPDATE_CLIENT_DETAILS)
    public ResponseEntity<String> updateClient(@PathVariable Long clientId, @RequestBody Client client) {
        logger.info("Updating client ID: {}", clientId);
        Client resultClient = service.updateClientDetails(clientId, client);
        if (resultClient != null) {
            logger.info("Successfully updated client ID: {}", clientId);
            return ResponseEntity.ok(MessageConstants.SUCCESSFULLY_UPDATING_CLIENT_DETAILS);
        } else {
            logger.warn("Failed to update client ID: {}", clientId);
            return ResponseEntity.ok(MessageConstants.FAILED_UPDATING_CLIENT_DETAILS);
        }
    }

    @PutMapping(APIConstants.UPDATE_CLIENT_ACTIVATION_STATUS)
    public ResponseEntity<ClientActivationStatus> updateClientActivationStatus(@PathVariable Long clientId) {
        logger.info("Updating activation status for client ID: {}", clientId);
        Client updatedClient = service.changeClientActivationStatus(clientId);
        return ResponseEntity.ok(updatedClient.getActivationStatus());
    }

    @GetMapping(APIConstants.GET_CLIENT_BY_CLIENTPARTNERID)
    public ResponseEntity<List<Client>> viewAllClientsByClientPartnerId(
            @PathVariable("clientPartnerId") Long clientPartnerId) {
        logger.info("Fetching all clients for client partner ID: {}", clientPartnerId);
        List<Client> clients = service.viewAllClientsByClientPartnerId(clientPartnerId);
        return ResponseEntity.ok(clients);
    }
}
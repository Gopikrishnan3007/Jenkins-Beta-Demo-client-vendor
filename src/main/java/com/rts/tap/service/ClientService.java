package com.rts.tap.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rts.tap.model.Client;
import com.rts.tap.model.ClientOrganization;
import com.rts.tap.model.Employee;

public interface ClientService {

	Client saveClient(Client client, MultipartFile organizationLogo) throws IOException;

	void deleteClient(Long id);

	List<Client> viewAllClients();

	Client viewClientById(Long id);

	Client updateClientDetails(Long clientId, Client client);

	Client changeClientActivationStatus(Long clientId);

	Client viewClientByMrfId(Long mrfId);

	public Long getClientIdByEmail(String email);

	boolean clientEmailExists(String email) throws Exception;

	List<Employee> getClientPartnerByBU(Long buId);

	Client updateClientForCredentials(Long id, String status, String reason);

	List<Client> viewAllClientsByClientPartnerId(long clientPartnerId);

	ClientOrganization findOrganizationById(Long clientOrganizationId);
	
	public ResponseEntity<Map<String, Object>> addClient(String clientJson, MultipartFile organizationLogo)throws Exception;
	
	
	

}

package com.rts.tap.dao;

import java.util.List;

import com.rts.tap.model.Client;
import com.rts.tap.model.ClientOrganization;

public interface ClientDao {

	Client saveClient(Client client);

	void deleteClient(Long id);

	List<Client> viewAllClients();

	Client viewClientById(Long id);

	ClientOrganization saveClientOrganization(ClientOrganization clientOrganization);

	Client viewClientByMrfId(Long mrfId);

	Long getClientIdByEmail(String email);

	Client updateClientDetails(Client client);

	Client getClientById(Long clientId);

	Client createCredentialForClient(Client client, String encodedPwd);

	List<Client> viewAllClientsByClientPartnerId(Long clientPartnerId);

	boolean clientEmailExists(String email);

	ClientOrganization findOrgnizationId(Long id);

}

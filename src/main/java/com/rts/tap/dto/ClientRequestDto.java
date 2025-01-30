package com.rts.tap.dto;

import java.util.List;

import com.rts.tap.model.Client;
import com.rts.tap.model.ClientOrganization;

public class ClientRequestDto {
	private List<Client> clients;
	private ClientOrganization organization;

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public ClientOrganization getOrganization() {
		return organization;
	}

	public void setOrganization(ClientOrganization organization) {
		this.organization = organization;
	}
}

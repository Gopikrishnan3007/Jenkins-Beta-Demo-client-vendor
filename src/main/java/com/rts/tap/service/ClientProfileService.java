package com.rts.tap.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rts.tap.model.Client;

public interface ClientProfileService {
	public Client getClientById(Long clientId);
	public Client getClientByEmail(String clientEmail);
	
	public String updateClientById(Long clientId, String clientPosition, String clientMobile);
	public ResponseEntity<String> updateClientByEmail(String clientEmail, String clientPosition, String clientMobile);
	public List<Client> viewAllClients();
}

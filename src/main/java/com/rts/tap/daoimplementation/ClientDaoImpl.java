package com.rts.tap.daoimplementation;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rts.tap.dao.ClientDao;
import com.rts.tap.model.Client;
import com.rts.tap.model.ClientOrganization;
import com.rts.tap.model.ThirdPartyCredentitals;
import com.rts.tap.model.ThirdPartyRole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ClientDaoImpl implements ClientDao {

	private EntityManager entityManager;

	public ClientDaoImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public Client saveClient(Client client) {
		entityManager.persist(client);
		return client;
	}

	@Override
	public ClientOrganization saveClientOrganization(ClientOrganization clientOrganization) {
		entityManager.persist(clientOrganization);
		return clientOrganization;
	}

	@Override
	public void deleteClient(Long id) {
		Client client = entityManager.find(Client.class, id);
		if (client != null) {
			entityManager.remove(client);
		}
	}

	@Override
	public List<Client> viewAllClients() {
		TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c", Client.class);
		return query.getResultList();
	}

	@Override
	public Client viewClientById(Long id) {
		return entityManager.find(Client.class, id);
	}

	@Override
	public Client updateClientDetails(Client client) {

		return entityManager.merge(client);
	}

	@Override
	public Client viewClientByMrfId(Long mrfId) {
		String hql = "select m.requirement.client from MRF m where m.mrfId = :mrfId";
		Query query = entityManager.createQuery(hql);
		query.setParameter("mrfId", mrfId);
		return (Client) query.getSingleResult();
	}

	@Override
	public Long getClientIdByEmail(String email) {
		String hql = "select c.clientId from Client c where c.clientEmail  = :email";
		Query q = entityManager.createQuery(hql);
		q.setParameter("email", email);
		return (Long) q.getSingleResult();
	}

	@Override
	public Client getClientById(Long clientId) {
		return entityManager.find(Client.class, clientId);
	}

	@Override
	public Client createCredentialForClient(Client client, String password) {

		ThirdPartyCredentitals credentials = new ThirdPartyCredentitals();

		if (client.getThirdPartyCredentitals() == null) {

			credentials.setEmail(client.getClientEmail());
			credentials.setPassword(password);
			credentials.setIsPasswordChanged(false);
		}
		ThirdPartyRole role;
		try {
			String hql = "select r from ThirdPartyRole r where role = :client";
			Query query = entityManager.createQuery(hql).setParameter("client", "Client");
			role = (ThirdPartyRole) query.getSingleResult();
		} catch (NoResultException e) {

			role = new ThirdPartyRole();
			role.setRole("Client");
			entityManager.persist(role);
		}

		credentials.setRole(role);

		entityManager.persist(credentials);

		client.setThirdPartyCredentitals(credentials);

		entityManager.persist(client);

		return client;
	}

	@Override
	public List<Client> viewAllClientsByClientPartnerId(Long clientPartnerId) {
		TypedQuery<Client> query = entityManager.createQuery(
				"SELECT c FROM Client c WHERE c.clientPartner.employeeId = :clientPartnerId", Client.class);

		query.setParameter("clientPartnerId", clientPartnerId);

		return query.getResultList();
	}

	@Override
	public boolean clientEmailExists(String email) {
		Long count = entityManager.createQuery("SELECT COUNT(c) FROM Client c WHERE c.clientEmail = :email", Long.class)
				.setParameter("email", email).getSingleResult();
		return count > 0;
	}

	@Override
	public ClientOrganization findOrgnizationId(Long id) {
		TypedQuery<ClientOrganization> query = entityManager.createQuery(
				"SELECT co FROM ClientOrganization co WHERE co.clientOrganizationId = :id", ClientOrganization.class);
		query.setParameter("id", id);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}

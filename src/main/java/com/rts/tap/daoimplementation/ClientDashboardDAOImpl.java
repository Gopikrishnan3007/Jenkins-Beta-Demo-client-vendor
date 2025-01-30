package com.rts.tap.daoimplementation;

import org.springframework.stereotype.Repository;

import com.rts.tap.dao.ClientDashboardDAO;

import jakarta.persistence.EntityManager;

@Repository

public class ClientDashboardDAOImpl implements ClientDashboardDAO {

	private EntityManager entityManager;

	public ClientDashboardDAOImpl(EntityManager entityManager) {

		this.entityManager = entityManager;

	}
	@Override
	public Long getClientPartnerId(Long clientId) {
		if (clientId == null) {
			return 0L;
		}

		String hql = "SELECT cp.employeeId FROM Client c JOIN c.clientPartner cp WHERE c.clientId = :clientId";
		return entityManager.createQuery(hql, Long.class).setParameter("clientId", clientId)
				.getSingleResult();
	}

}

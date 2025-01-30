package com.rts.tap.daoimplementation;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rts.tap.dao.SubRequirementDAO;
import com.rts.tap.model.SubRequirements;

import jakarta.persistence.EntityManager;

@Repository
public class SubRequirementDAOImpl implements SubRequirementDAO {

	private EntityManager entityManager;

	public SubRequirementDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void addSubRequirement(SubRequirements subRequirements) {
		entityManager.merge(subRequirements);
	}

	@Override
	public List<SubRequirements> viewAllSubRequirements() {
		String hql = "From SubRequirements";
		return entityManager.createQuery(hql,SubRequirements.class).getResultList();
	}

}

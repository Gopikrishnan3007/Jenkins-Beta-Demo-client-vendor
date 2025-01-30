package com.rts.tap.dto;
 
import java.sql.Date;

import java.util.List;
 
 
public class RequirementDTO {

    private Long requirementId;

    private int totalRequiredResourceCount;

    private String timeline;

    private double budget;

    private Date createdAt;

    private List<SubRequirementDTO> subRequirements;

	public Long getRequirementId() {

		return requirementId;

	}

	public void setRequirementId(Long requirementId) {

		this.requirementId = requirementId;

	}


	public int getTotalRequiredResourceCount() {

		return totalRequiredResourceCount;

	}

	public void setTotalRequiredResourceCount(int totalRequiredResourceCount) {

		this.totalRequiredResourceCount = totalRequiredResourceCount;

	}

	public String getTimeline() {

		return timeline;

	}

	public void setTimeline(String timeline) {

		this.timeline = timeline;

	}

	public double getBudget() {

		return budget;

	}

	public void setBudget(double budget) {

		this.budget = budget;

	}

	public Date getCreatedAt() {

		return createdAt;

	}

	public void setCreatedAt(Date createdAt) {

		this.createdAt = createdAt;

	}

	public List<SubRequirementDTO> getSubRequirements() {

		return subRequirements;

	}

	public void setSubRequirements(List<SubRequirementDTO> subRequirements) {

		this.subRequirements = subRequirements;

	}
 
    

}

 
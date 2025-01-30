package com.rts.tap.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rts.tap.constants.APIConstants;
import com.rts.tap.model.Budget;

@FeignClient(name = "budget-service",path = APIConstants.BASE_URL)
//@FeignClient(name = "api-service",path = APIConstants.BASE_URL)


public interface BudgetInterface {

	@GetMapping(path = APIConstants.GET_BUDGET_BY_REQUIREMENTID_URL)
	public List<Budget> getBudgetsByRequirementId(@PathVariable Long requirementId);
}

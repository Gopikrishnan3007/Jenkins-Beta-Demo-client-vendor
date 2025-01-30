package com.rts.tap.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rts.tap.constants.APIConstants;
import com.rts.tap.model.Employee;

@FeignClient(name = "employee-service",path = APIConstants.BASE_URL)
//@FeignClient(name = "api-service",path = APIConstants.BASE_URL)

public interface EmployeeInterface {

	@GetMapping(path = APIConstants.GET_CLIENTPARTNER_BU)
    public List<Employee> getClientPartnerByBU(@PathVariable Long buId);
}

package com.rts.tap.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity; //keep this import as it might be used elsewhere
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rts.tap.constants.APIConstants;
import com.rts.tap.dto.CandidateDto;
import com.rts.tap.dto.VendorRemainingDaysDTO;
import com.rts.tap.model.Candidate;
import com.rts.tap.model.MRF;
import com.rts.tap.model.MRFVendor;

@FeignClient(name = "BACKEND-API", path = APIConstants.VENDOR_URL)
public interface VendorCommunicateInterface {
    
    @GetMapping(APIConstants.VENDOR_GET_COUNT_ASSIGNED_MRF) // completed
    public Long getCountOfAssignedMrfByVendorId(@PathVariable("vendorId") Long vendorId);
    
    @GetMapping(path = APIConstants.VENDOR_GET_COUNT_COMPLETED_MRF) // completed
    public Long getCountOfCompletedMrfByVendorId(Long vendorId);
    
    @GetMapping(path = APIConstants.VENDOR_GET_ALL_MRF_COUNT) // completed
    public Long getCountOfAllMrfByVendorId(@PathVariable("vendorId") Long vendorId);
    
    @GetMapping(path = APIConstants.VENDOR_GET_ALL_MRF_ASSIGNED_FOR_VENDOR) // completed s
    public String getAllMrfAssignedForVendor(@PathVariable("vendorId") Long vendorId);
    
    @GetMapping(path = APIConstants.VENDOR_GET_ALL_MRFVENDORDETAILS_BY_VENDORID) // completed s
    public String findAllMrfVendorDetailsByVendorId(@PathVariable("vendorId") Long vendorId);
    
    @GetMapping(path = APIConstants.VENDOR_GET_ALL_MRF_DETAILS_BY_VENDOR_AND_MRF_ID) // completed s
    public String getMrfByVendorAndMrfId(@PathVariable Long vendorId, @PathVariable Long mrfId);
    
    //this method is not working, if we change to list return type then it will work fine
    @GetMapping(path = APIConstants.VENDOR_GET_CANDIDATE_DETAILS_BY_VENDOR_AND_MRF_ID) // completed //to show candidate
    public String getCandidateByVendorAndMrfId(@RequestParam Long vendorId, @RequestParam Long mrfId);
    
    @GetMapping(path = APIConstants.VENDOR_GET_CANDIDATE_COUNT_BY_VENDOR_AND_MRF_ID) // completed s
    public String getCountOfCandidateByVendorAndMrfId(@RequestParam Long vendorId,
            @RequestParam Long mrfId);
    
    @GetMapping(path = APIConstants.VENDOR_GET_CANDIDATE_COUNT_BY_VENDOR_ID) // completed s
    public String getCountOfCandidateByVendorId(@RequestParam Long vendorId) ;
    
    @GetMapping(path = APIConstants.VENDOR_GET_HIRED_AND_JOINED_CANDIDATE_DETAILS_BY_VENDOR_ID) // completed s
    public String getHiredAndJoinedCandidatesAssignedByVendorId(@RequestParam Long vendorId);
    
    @GetMapping(path = APIConstants.VENDOR_GET_HIRED_CANDIDATE_COUNT_BY_VENDOR_ID) // completed s
    public String getCountOfHiredCandidateByVendorId(@RequestParam Long vendorId) ;
    
    @GetMapping(path = APIConstants.VENDOR_GET_JOINED_CANDIDATE_COUNT_BY_VENDOR_ID) // completed s
    public String getCountOfJoinedCandidateByVendorId(@RequestParam Long vendorId) ;
    
    @GetMapping(path = APIConstants.VENDOR_GET_ALL_CANDIDATE_BY_MRFJD_AND_VENDOR_ID) // completed S
    public String getAllCandidatesAssignedByVendorAndMrfId(@RequestParam Long vendorId,
            @RequestParam Long mrfJdId) ;
    
    //return empty list
    @GetMapping(path = APIConstants.VENDOR_GET_REMAINING_DAYS) // completed
    public String getRemainingDays(@PathVariable Long vendorId) ;
    
    @GetMapping(path = APIConstants.GET_ALL_CANDIDATES_LIST_BY_VENDOR_ID)
    public String getAllCandidatesByVendorId(@RequestParam Long vendorId);
    
    @GetMapping(path = APIConstants.MONTHLY_CANDIDATE_DATA)
    public String getVendorCandidateCountByMonth(@RequestParam Long vendorId,
            @RequestParam int year);
    
    @GetMapping(path = APIConstants.GET_RECRUITING_MANAGER_ID)
    public String getRecruitingManagerId(@RequestParam Long vendorId, @RequestParam Long mrfId) ;
    
}
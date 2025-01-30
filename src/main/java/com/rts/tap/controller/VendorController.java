package com.rts.tap.controller;

import java.util.List;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rts.tap.constants.APIConstants;
import com.rts.tap.constants.MessageConstants;
import com.rts.tap.dto.CandidateDto;
import com.rts.tap.dto.VendorDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rts.tap.dto.VendorRemainingDaysDTO;
import com.rts.tap.model.Candidate;
import com.rts.tap.model.MRF;
import com.rts.tap.model.MRFVendor;
import com.rts.tap.model.Vendor;
import com.rts.tap.service.VendorService;

import jakarta.mail.MessagingException; 

@RestController
@CrossOrigin(origins = APIConstants.CROSS_ORIGIN_URL, allowCredentials = "true")
@RequestMapping(APIConstants.VENDOR_URL)
public class VendorController {

    private VendorService vendorService;
    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    public ResponseEntity<Vendor> doAddNewVendor(@RequestBody VendorDto vendorDto) throws MessagingException {
        logger.info("Entering doAddNewVendor with VendorDto: {}", vendorDto);
        Vendor vendor = vendorService.addNewVendor(vendorDto);
        logger.info("Vendor added: {}", vendor);
        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }

    @GetMapping(APIConstants.VENDOR_GET_BY_ID)
    public ResponseEntity<VendorDto> getVendorById(@PathVariable Long id) {
        logger.info("Getting vendor by ID: {}", id);
        VendorDto vendorDto = vendorService.getVendorById(id);
        logger.info("Vendor fetched: {}", vendorDto);
        return ResponseEntity.ok(vendorDto);
    }

    @GetMapping(APIConstants.VENDOR_GET_ALL)
    public ResponseEntity<List<VendorDto>> getAllVendors() {
        logger.info("Fetching all vendors");
        List<VendorDto> vendors = vendorService.getAllVendors();
        logger.info("Total vendors fetched: {}", vendors.size());
        return ResponseEntity.ok(vendors);
    }

    @PutMapping(APIConstants.VENDOR_UPDATE)
    public ResponseEntity<Vendor> doUpdateVendor(@PathVariable Long id, @RequestBody VendorDto vendor) {
        logger.info("Updating vendor with ID: {} and data: {}", id, vendor);
        try {
            Vendor updatedVendor = vendorService.updateVendor(id, vendor);
            if (updatedVendor == null) {
                logger.warn("Vendor with ID: {} not found for update", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            logger.info("Vendor updated successfully: {}", updatedVendor);
            return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating vendor with ID: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(APIConstants.VENDOR_DELETE)
    public ResponseEntity<String> doDeleteVendor(@PathVariable Long id) {
        logger.info("Deleting vendor with ID: {}", id);
        try {
            vendorService.deleteVendor(id);
            logger.info("Vendor successfully deleted: {}", id);
            return ResponseEntity.ok().body(MessageConstants.VENDOR_DELETED_SUCCESS);
        } catch (Exception e) {
            logger.error("Error deleting vendor with ID: {}", id, e);
            return ResponseEntity.ok().body(MessageConstants.VENDOR_DELETED_FAILED);
        }
    }

    @GetMapping(path = APIConstants.VENDOR_GET_COUNT_ASSIGNED_MRF)
    public Long getCountOfAssignedMrfByVendorId(@PathVariable("vendorId") Long vendorId) {
        logger.info("Fetching count of assigned MRFs for vendor ID: {}", vendorId);
        return vendorService.getCountOfAssignedMrfByVendorId(vendorId);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_COUNT_COMPLETED_MRF)
    public Long getCountOfCompletedMrfByVendorId(Long vendorId) {
        logger.info("Fetching count of completed MRFs for vendor ID: {}", vendorId);
        return vendorService.getCountOfCompletedMrfByVendorId(vendorId);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_ALL_MRF_COUNT)
    public Long getCountOfAllMrfByVendorId(@PathVariable("vendorId") Long vendorId) {
        logger.info("Fetching count of all MRFs for vendor ID: {}", vendorId);
        return vendorService.getCountOfAllMrfByVendorId(vendorId);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_ALL_MRF_ASSIGNED_FOR_VENDOR)
    public ResponseEntity<List<MRFVendor>> getAllMrfAssignedForVendor(@PathVariable("vendorId") Long vendorId) {
        logger.info("Fetching all MRFs assigned for vendor ID: {}", vendorId);
        List<MRFVendor> mrf = vendorService.getAllMrfAssignedForVendor(vendorId);
        if (mrf == null || mrf.isEmpty()) {
            logger.warn("No MRFs found assigned for vendor ID: {}", vendorId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mrf);
        } else {
            logger.info("Total MRFs found for vendor ID: {}: {}", vendorId, mrf.size());
            return ResponseEntity.status(HttpStatus.OK).body(mrf);
        }
    }

    @GetMapping(path = APIConstants.VENDOR_GET_ALL_MRFVENDORDETAILS_BY_VENDORID)
    public ResponseEntity<List<MRFVendor>> findAllMrfVendorDetailsByVendorId(@PathVariable("vendorId") Long vendorId) {
        logger.info("Fetching all MRF vendor details for vendor ID: {}", vendorId);
        List<MRFVendor> mrf = vendorService.findAllMRFVendorDetailsByVendorId(vendorId);
        if (mrf == null || mrf.isEmpty()) {
            logger.warn("No MRF vendor details found for vendor ID: {}", vendorId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mrf);
        } else {
            logger.info("Total MRF vendor details found for vendor ID: {}: {}", vendorId, mrf.size());
            return ResponseEntity.status(HttpStatus.OK).body(mrf);
        }
    }

    @GetMapping(path = APIConstants.VENDOR_GET_ALL_MRF_DETAILS_BY_VENDOR_AND_MRF_ID)
    public ResponseEntity<MRF> getMrfByVendorAndMrfId(@PathVariable Long vendorId, @PathVariable Long mrfId) {
        logger.info("Fetching MRF details for vendor ID: {} and MRF ID: {}", vendorId, mrfId);
        MRF mrf = vendorService.getMRFDetailsByVendorIdAndMrfId(vendorId, mrfId);
        if (mrf == null) {
            logger.warn("No MRF details found for vendor ID: {} and MRF ID: {}", vendorId, mrfId);
            return ResponseEntity.notFound().build();
        } else {
            logger.info("MRF details found: {}", mrf);
            return ResponseEntity.ok(mrf);
        }
    }

    @GetMapping(path = APIConstants.VENDOR_GET_CANDIDATE_DETAILS_BY_VENDOR_AND_MRF_ID)
    public ResponseEntity<Candidate> getCandidateByVendorAndMrfId(@RequestParam Long vendorId,
            @RequestParam Long mrfId) {
        logger.info("Fetching candidate details for vendor ID: {} and MRF ID: {}", vendorId, mrfId);
        Candidate candidate = vendorService.getCandidateByVendorAndMrfId(vendorId, mrfId);
        logger.info("Candidate details fetched: {}", candidate);
        return ResponseEntity.ok(candidate);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_CANDIDATE_COUNT_BY_VENDOR_AND_MRF_ID)
    public ResponseEntity<Long> getCountOfCandidateByVendorAndMrfId(@RequestParam Long vendorId,
            @RequestParam Long mrfId) {
        logger.info("Fetching candidate count for vendor ID: {} and MRF ID: {}", vendorId, mrfId);
        Long count = vendorService.getCountOfCandidateByVendorAndMrfId(vendorId, mrfId);
        logger.info("Total candidates found: {}", count);
        return ResponseEntity.ok(count);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_CANDIDATE_COUNT_BY_VENDOR_ID)
    public ResponseEntity<Long> getCountOfCandidateByVendorId(@RequestParam Long vendorId) {
        logger.info("Fetching candidate count for vendor ID: {}", vendorId);
        Long count = vendorService.getCountOfCandidateByVendorId(vendorId);
        logger.info("Total candidates found: {}", count);
        return ResponseEntity.ok(count);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_HIRED_AND_JOINED_CANDIDATE_DETAILS_BY_VENDOR_ID)
    public ResponseEntity<List<Candidate>> getHiredAndJoinedCandidatesAssignedByVendorId(@RequestParam Long vendorId) {
        logger.info("Fetching hired and joined candidates for vendor ID: {}", vendorId);
        List<Candidate> candidate = vendorService.getHiredAndJoinedCandidatesAssignedByVendorId(vendorId);
        logger.info("Total hired and joined candidates found: {}", candidate.size());
        return ResponseEntity.ok(candidate);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_HIRED_CANDIDATE_COUNT_BY_VENDOR_ID)
    public ResponseEntity<Long> getCountOfHiredCandidateByVendorId(@RequestParam Long vendorId) {
        logger.info("Fetching count of hired candidates for vendor ID: {}", vendorId);
        Long count = vendorService.getCountOfHiredCandidateByVendorId(vendorId);
        logger.info("Total hired candidates found: {}", count);
        return ResponseEntity.ok(count);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_JOINED_CANDIDATE_COUNT_BY_VENDOR_ID)
    public ResponseEntity<Long> getCountOfJoinedCandidateByVendorId(@RequestParam Long vendorId) {
        logger.info("Fetching count of joined candidates for vendor ID: {}", vendorId);
        Long count = vendorService.getCountOfJoinedCandidateByVendorId(vendorId);
        logger.info("Total joined candidates found: {}", count);
        return ResponseEntity.ok(count);
    }

    @PatchMapping(path = APIConstants.UPDATE_MRF_STATUS_BY_VENDOR_ID)
    public ResponseEntity<String> updateMRFStatusByVendor(@RequestParam Long vendorId, @RequestParam Long mrfId,
            @RequestParam String status) {
        logger.info("Updating MRF status for vendor ID: {} and MRF ID: {} to status: {}", vendorId, mrfId, status);
        String response = vendorService.updateMRFStatus(vendorId, mrfId, status);
        logger.info("MRF status updated with response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_ALL_CANDIDATE_BY_MRFJD_AND_VENDOR_ID)
    public ResponseEntity<List<Candidate>> getAllCandidatesAssignedByVendorAndMrfId(@RequestParam Long vendorId,
            @RequestParam Long mrfJdId) {
        logger.info("Fetching all candidates assigned for vendor ID: {} and MRF JD ID: {}", vendorId, mrfJdId);
        List<Candidate> candidate = (List<Candidate>) vendorService.getAllCandidatesAssignedByVendorAndMrfJdId(vendorId,
                mrfJdId);
        logger.info("Total candidates found: {}", candidate.size());
        return ResponseEntity.ok(candidate);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_REMAINING_DAYS)
    public ResponseEntity<List<VendorRemainingDaysDTO>> getRemainingDays(@PathVariable Long vendorId) {
        logger.info("Fetching remaining days for vendor ID: {}", vendorId);
        List<VendorRemainingDaysDTO> mrfDetails = vendorService.getRemainingDays(vendorId);
        logger.info("Remaining days fetched: {}", mrfDetails);
        return ResponseEntity.ok(mrfDetails);
    }

    @PutMapping(path = APIConstants.VENDOR_RESET_PASSWORD_BY_EMAIL)
    public ResponseEntity<String> resetVendorPassword1(@RequestParam String email, @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        logger.info("Resetting password for vendor with email: {}", email);
        try {
            String response = vendorService.resetPassword(email, oldPassword, newPassword);
            logger.info("Password reset successful for email: {}", email);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Password reset failed for email: {} - {}", email, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = APIConstants.GET_ALL_CANDIDATES_LIST_BY_VENDOR_ID)
    public ResponseEntity<List<Candidate>> getAllCandidatesByVendorId(@RequestParam Long vendorId) {
        logger.info("Fetching all candidates for vendor ID: {}", vendorId);
        List<Candidate> candidateList = vendorService.getAllCandidatesByVendorId(vendorId);
        logger.info("Total candidates found: {}", candidateList.size());
        return ResponseEntity.ok(candidateList);
    }

    @GetMapping(APIConstants.VENDOR_ID_BY_EMAIL)
    public ResponseEntity<Long> getVendorId(@RequestParam("email") String email) {
        logger.info("Fetching vendor ID for email: {}", email);
        Long id = vendorService.getVendorIdByEmail(email);
        logger.info("Vendor ID fetched: {}", id);
        return ResponseEntity.ok(id);
    }

    @GetMapping(path = APIConstants.VENDOR_GET_ALL_MRFVENDORDETAILS_BY_RECRUITNIGMANAGERID) 
    public ResponseEntity<List<MRFVendor>> findAllMrfVendorDetailsByRMId(@PathVariable("rmId") Long rmId) {
        logger.info("Fetching all MRF vendor details by recruiting manager ID: {}", rmId);
        List<MRFVendor> mrfvendor = vendorService.findAllMRFVendorDetailsByRmId(rmId);
        if (mrfvendor == null || mrfvendor.isEmpty()) {
            logger.warn("No MRF vendors found for recruiting manager ID: {}", rmId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mrfvendor);
        } else {
            logger.info("Total MRF vendors found for RM ID: {}: {}", rmId, mrfvendor.size());
            return ResponseEntity.status(HttpStatus.OK).body(mrfvendor);
        }
    }

    @PatchMapping("/updatevendor/{vendorId}")
    public ResponseEntity<String> updateVendorPartial(@PathVariable("vendorId") Long vendorId,
            @RequestParam(required = false) String address, @RequestParam(required = false) String contactName,
            @RequestParam(required = false) String contactNumber, @RequestParam(required = false) String websiteUrl,
            @RequestParam(required = false) MultipartFile vendorOrganizationLogo) throws IOException {
        logger.info("Partially updating vendor with ID: {}. Address: {}, Contact Name: {}, Contact Number: {}, Website URL: {}", 
                vendorId, address, contactName, contactNumber, websiteUrl);
        String response = vendorService.updateVendorProfileDetails(vendorId, address, contactName, contactNumber,
                websiteUrl, vendorOrganizationLogo);
        logger.info("Vendor profile details updated with response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = APIConstants.MONTHLY_CANDIDATE_DATA)
    public ResponseEntity<List<Object[]>> getVendorCandidateCountByMonth(@RequestParam Long vendorId,
            @RequestParam int year) {
        logger.info("Fetching vendor candidate count for vendor ID: {} and year: {}", vendorId, year);
        List<Object[]> response = vendorService.monthwiseCandidateData(vendorId, year);
        logger.info("Vendor candidate count fetched for year {}: {}", year, response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = APIConstants.GET_RECRUITING_MANAGER_ID)
    public ResponseEntity<Long> getRecruitingManagerId(@RequestParam Long vendorId, @RequestParam Long mrfId) {
        logger.info("Fetching recruiting manager ID for vendor ID: {} and MRF ID: {}", vendorId, mrfId);
        Long employeeId = vendorService.getRecruitingManagerId(vendorId, mrfId);
        logger.info("Recruiting manager ID fetched: {}", employeeId);
        return ResponseEntity.ok(employeeId);
    }	
    
    @GetMapping(path = APIConstants.GET_RM_VENDOR_DETAILS)
    public ResponseEntity<Vendor> getVendorDetailsById(@RequestParam Long vendorId) {
        logger.info("Fetching vendor details for vendor ID: {}", vendorId);
        Vendor vendor = vendorService.findVendorById(vendorId);
        logger.info("Vendor details fetched: {}", vendor);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping(path = APIConstants.GET_ALL_RM_VENDORS)
    public ResponseEntity<List<Vendor>> getAllVendorDetails() {
        logger.info("Fetching all vendor details");
        List<Vendor> vendors = vendorService.getAllVendorsByVendors();
        logger.info("Total vendor details fetched: {}", vendors.size());
        return ResponseEntity.ok(vendors);
    }

    @GetMapping(path = APIConstants.GET_ALL_RM_ASSIGNED_VENDORS)
    public ResponseEntity<List<MRFVendor>> getallAssignedVendorDetails() {
        logger.info("Fetching all assigned vendor details");
        List<MRFVendor> assignedVendors = vendorService.getAllAssignedVendors();
        logger.info("Total assigned vendor details fetched: {}", assignedVendors.size());
        return ResponseEntity.ok(assignedVendors);
    }
    @PostMapping(path = APIConstants.BULK_CANDIDATE_ADD)
    public ResponseEntity<String> addBulkCandidate(@ModelAttribute CandidateDto candidateDto) throws MessagingException, IOException{
    	return vendorService.addBulkCandidate(candidateDto);
    }
}
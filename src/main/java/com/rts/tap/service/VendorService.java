package com.rts.tap.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rts.tap.dto.CandidateDto;
import com.rts.tap.dto.VendorDto;
import com.rts.tap.dto.VendorRemainingDaysDTO;
import com.rts.tap.model.Candidate;
import com.rts.tap.model.MRF;
import com.rts.tap.model.MRFVendor;
import com.rts.tap.model.Vendor;

import jakarta.mail.MessagingException;

public interface VendorService {
	public Vendor addNewVendor(VendorDto vendor) throws MessagingException;

	public VendorDto getVendorById(Long id);

	public List<VendorDto> getAllVendors();

	public Vendor updateVendor(Long id, VendorDto vendor);

	public String deleteVendor(Long id);

	public String generateVendorUsername(String name);

	public String generateStrongPassword();

	public VendorDto dologin(VendorDto vendorDto);

	void saveAllCandidates(List<CandidateDto> candidateDTOs);

	List<Date> getCandidateCountBySourceAndYear(Long sourceId);

	// Team-D - Nagarjun N S
	public Long getCountOfAssignedMrfByVendorId(Long vendorId);

	public Long getCountOfCompletedMrfByVendorId(Long vendorId);

	public Long getCountOfAllMrfByVendorId(Long vendorId);

	public List<MRFVendor> findAllMRFVendorDetailsByVendorId(Long vendorId); // Rectified

	public MRF getMRFDetailsByVendorIdAndMrfId(Long vendorId, Long mrfId); // Rectified

	public Candidate getCandidateByVendorAndMrfId(Long vendorId, Long mrfId); // Rectified

	public Long getCountOfCandidateByVendorAndMrfId(Long vendorId, Long mrfId);// REc

	public Long getCountOfCandidateByVendorId(Long vendorId); // rec

	public List<Candidate> getHiredAndJoinedCandidatesAssignedByVendorId(Long vendorId); // Rec

	public Long getCountOfHiredCandidateByVendorId(Long vendorId); // in prog

	public Long getCountOfJoinedCandidateByVendorId(Long vendorId); // in prog

//		public List<MRFVendor> getAllMrfByVendorId(Long vendorId, Long mrfId);

	public String updateMRFStatus(Long vendorId, Long mrfId, String status);// rec

	public List<MRFVendor> getAllMrfAssignedForVendor(Long vendorId);// rec

//		public List<MrfDetailsForVendorDTO> getAllMrfDetailsAssignedForVendor(Long vendorId);//rec no need will see later

	public List<VendorRemainingDaysDTO> getRemainingDays(Long vendorId);

//		public Long getCountOfRemainingDays(Long mrfId,);

	String resetPassword(String email, String oldPassword, String newPassword);

	public List<Candidate> getAllCandidatesByVendorId(Long vendorId);

	public Long getVendorIdByEmail(String email);

	public List<MRFVendor> findAllMRFVendorDetailsByRmId(Long rmId);

	public List<Candidate> getAllCandidatesAssignedByVendorAndMrfJdId(Long vendorId, Long mrfJdId);

	public Vendor findVendorById(Long vendorId);

	public String updateVendorProfileDetails(Long vendorId, String address, String contactName, String contactNumber,
			String websiteUrl, MultipartFile vendorOrganizationLogo) throws IOException;

	public List<Vendor> getAllVendorsByVendors();

	public List<MRFVendor> getAllAssignedVendors();

	public List<Object[]> monthwiseCandidateData(Long vendorId, int year);

	public Long getRecruitingManagerId(Long vendorId, Long mrfId);
	
	public ResponseEntity<String> addBulkCandidate(CandidateDto candidateDto) throws MessagingException, IOException;
}

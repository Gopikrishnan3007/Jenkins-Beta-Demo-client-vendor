package com.rts.tap.serviceimplementation;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rts.tap.constants.MessageConstants;
import com.rts.tap.dao.VendorDao;
import com.rts.tap.dto.CandidateDto;
import com.rts.tap.dto.VendorDto;
import com.rts.tap.dto.VendorRemainingDaysDTO;
import com.rts.tap.exception.VendorNotFoundException;
import com.rts.tap.feign.CandidateCommunicationInterface;
import com.rts.tap.feign.VendorCommunicateInterface;
import com.rts.tap.model.Candidate;
import com.rts.tap.model.MRF;
import com.rts.tap.model.MRFVendor;
import com.rts.tap.model.Vendor;
import com.rts.tap.service.VendorService;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * author: Jeevarajan, Vashanth version: v1.0 updated at: 04-11-2024
 **/

@Service
@Transactional
public class VendorServiceImplementation implements VendorService {

	private static final Logger logger = LoggerFactory.getLogger(VendorServiceImplementation.class);

	private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png");

	private final VendorDao vendorDao;

	private EntityManager entityManager;

	private VendorCommunicateInterface vendorInterface;

	private CandidateCommunicationInterface candidateCommunicationInterface;

	

	public VendorServiceImplementation(VendorDao vendorDao, EntityManager entityManager,
			VendorCommunicateInterface vendorInterface,
			CandidateCommunicationInterface candidateCommunicationInterface) {
		super();
		this.vendorDao = vendorDao;
		this.entityManager = entityManager;
		this.vendorInterface = vendorInterface;
		this.candidateCommunicationInterface = candidateCommunicationInterface;
	}

	@Override
	public Vendor addNewVendor(VendorDto vendor) throws MessagingException {
		try {
			logger.info("Adding new vendor: {}", vendor);
			vendor.setUsername(generateVendorUsername(vendor.getOrganizationName()));
			vendor.setPassword(generateStrongPassword());
			Vendor savedVendor = vendorDao.save(vendor);
			logger.info("Vendor added successfully: {}", savedVendor);
			return savedVendor;
		} catch (Exception e) {
			logger.error("Error adding new vendor: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public VendorDto getVendorById(Long id) {
		try {
			logger.info("Fetching vendor by ID: {}", id);
			Vendor vendor = vendorDao.findById(id);
			if (vendor == null) {
				logger.error("Vendor not found for ID: {}", id);
				throw new VendorNotFoundException(MessageConstants.VENDOR_NOT_FOUND);
			}
			VendorDto vendorDto = vendorDtoConvertion(vendor);
			logger.info("Fetched vendor: {}", vendorDto);
			return vendorDto;
		} catch (Exception e) {
			logger.error("Error fetching vendor by ID: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<VendorDto> getAllVendors() {
		try {
			logger.info("Fetching all vendors");
			List<VendorDto> vendors = vendorDao.findAllVendor().stream().map(vendor -> {
				return vendorDtoConvertion(vendor);
			}).collect(Collectors.toList());
			logger.info("Total vendors fetched: {}", vendors.size());
			return vendors;
		} catch (Exception e) {
			logger.error("Error fetching all vendors: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Vendor updateVendor(Long id, VendorDto vendor) {
		try {
			logger.info("Updating vendor with ID: {}", id);
			Vendor existingVendor = vendorDao.findById(id);
			if (existingVendor == null) {
				logger.error("Vendor not found for ID: {}", id);
				throw new VendorNotFoundException(MessageConstants.VENDOR_NOT_FOUND);
			}
			Vendor updatedVendor = vendorDao.updateVendor(id, vendor);
			logger.info("Vendor updated successfully: {}", updatedVendor);
			return updatedVendor;
		} catch (Exception e) {
			logger.error("Error updating vendor: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public String deleteVendor(Long id) {
		try {
			logger.info("Deleting vendor with ID: {}", id);
			vendorDao.deleteById(id);
			logger.info("Vendor deleted successfully: {}", id);
			return MessageConstants.VENDOR_DELETED_SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to delete vendor with ID: {}", id, e);
			return MessageConstants.VENDOR_DELETED_FAILED;
		}
	}

	// this method will convert Vendor entity to vendor DTO
	public VendorDto vendorDtoConvertion(Vendor vendor) {
		try {
			VendorDto vendorObject = new VendorDto();
			vendorObject.setVendorId(vendor.getVendorId());
			vendorObject.setOrganizationName(vendor.getOrganizationName());
			vendorObject.setUsername(vendor.getThirdPartyCredentitals().getUsername());
			vendorObject.setContactName(vendor.getContactName());
			vendorObject.setContactNumber(vendor.getContactNumber());
			vendorObject.setAddress(vendor.getAddress());
			vendorObject.setEmail(vendor.getThirdPartyCredentitals().getEmail());
			vendorObject.setPassword(vendor.getThirdPartyCredentitals().getPassword());
			vendorObject.setWebsiteUrl(vendor.getWebsiteUrl());
			vendorObject.setTaxIdentifyNumber(vendor.getTaxIdentifyNumber());
			vendorObject.setIsPasswordChanged(vendor.getIsPasswordChanged());
			vendorObject.setRole(vendor.getThirdPartyCredentitals().getRole().getRole());
			return vendorObject;
		} catch (Exception e) {
			logger.error("Error converting vendor to DTO: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public String generateVendorUsername(String name) {
		try {
			logger.info("Generating username for vendor with name: {}", name);
			String prefix = name.length() >= 3 ? name.substring(0, 3).toLowerCase() : name.toLowerCase();
			Random random = new Random();
			StringBuilder digits = new StringBuilder();
			for (int i = 0; i < 5; i++) {
				digits.append(random.nextInt(10)); // Generates a digit between 0 and 9
			}
			String username = prefix + digits.toString();
			logger.info("Generated username: {}", username);
			return username;
		} catch (Exception e) {
			logger.error("Error generating vendor username: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public String generateStrongPassword() {
		try {
			logger.info("Generating strong password");
			SecureRandom random = new SecureRandom();
			StringBuilder password = new StringBuilder(12);
			for (int i = 0; i < 12; i++) {
				int index = random.nextInt(MessageConstants.CHARACTERS.length());
				password.append(MessageConstants.CHARACTERS.charAt(index));
			}
			logger.info("Generated strong password");
			return password.toString();
		} catch (Exception e) {
			logger.error("Error generating strong password: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public VendorDto dologin(VendorDto vendorDto) {
		try {
			logger.info("User login attempt for vendor: {}", vendorDto);
			Vendor vendor = vendorDao.login(vendorDto);
			VendorDto loggedInVendorDto = vendorDtoConvertion(vendor);
			logger.info("Login successful for vendor: {}", loggedInVendorDto);
			return loggedInVendorDto;
		} catch (Exception e) {
			logger.error("Error during vendor login: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<Date> getCandidateCountBySourceAndYear(Long sourceId) {
		try {
			logger.info("Fetching candidate count by source ID: {} and year", sourceId);
			List<Date> results = vendorDao.getAssignedDatesBySourceAndYear(sourceId);
			logger.info("Fetched candidate count results: {}", results);
			return results;
		} catch (Exception e) {
			logger.error("Error fetching candidate count: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public void saveAllCandidates(List<CandidateDto> candidateDTOs) {
		try {
			logger.info("Saving all candidates: {}", candidateDTOs);
			List<Candidate> candidates = candidateDTOs.stream().map(this::convertToEntity).collect(Collectors.toList());
			vendorDao.saveAll(candidates);
			logger.info("All candidates saved successfully");
		} catch (Exception e) {
			logger.error("Error saving all candidates: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	// Convert DTO to Entity
	private Candidate convertToEntity(CandidateDto candidateDTO) {
		try {
			logger.info("Converting candidate DTO to entity: {}", candidateDTO);
			Candidate candidate = new Candidate();
			candidate.setFirstName(candidateDTO.getFirstName());
			candidate.setLastName(candidateDTO.getLastName());
			candidate.setMobileNumber(candidateDTO.getMobileNumber());
			candidate.setEmail(candidateDTO.getEmail());
			candidate.setTotalExperience(candidateDTO.getTotalExperience());
			candidate.setResume(candidateDTO.getResume());
			candidate.setSource(candidateDTO.getSource());
			candidate.setSourceId(candidateDTO.getSourceId());
			candidate.setSkill(candidateDTO.getSkill());
			candidate.setLocation(candidateDTO.getLocation());
			candidate.setPanNumber(candidateDTO.getPanNumber());

			logger.info("Converted candidate entity: {}", candidate);
			return candidate;
		} catch (Exception e) {
			logger.error("Error converting candidate DTO to entity: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	// Code by Team-D - Nagarjun N S

	@Override
	public Long getCountOfAssignedMrfByVendorId(Long vendorId) {
		try {
			logger.info("Getting count of assigned MRFs for vendor ID: {}", vendorId);
			return vendorInterface.getCountOfAssignedMrfByVendorId(vendorId);
		} catch (Exception e) {
			logger.error("Error getting count of assigned MRFs: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Long getCountOfCompletedMrfByVendorId(Long vendorId) {
		try {
			logger.info("Getting count of completed MRFs for vendor ID: {}", vendorId);
			return vendorDao.getCountOfCompletedMrfByVendorId(vendorId);
		} catch (Exception e) {
			logger.error("Error getting count of completed MRFs: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Long getCountOfAllMrfByVendorId(Long vendorId) {
		try {
			logger.info("Getting count of all MRFs for vendor ID: {}", vendorId);
			return vendorDao.getCountOfAllMrfByVendorId(vendorId);
		} catch (Exception e) {
			logger.error("Error getting count of all MRFs: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<MRFVendor> findAllMRFVendorDetailsByVendorId(Long vendorId) {
		try {
			logger.info("Finding all MRF vendor details for vendor ID: {}", vendorId);
			return vendorDao.findAllMRFVendorDetailsByVendorId(vendorId);
		} catch (Exception e) {
			logger.error("Error finding all MRF vendor details: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public MRF getMRFDetailsByVendorIdAndMrfId(Long vendorId, Long mrfId) {
		try {
			logger.info("Fetching MRF details for vendor ID: {} and MRF ID: {}", vendorId, mrfId);
			MRF mrf = vendorDao.getMRFDetailsByVendorIdAndMrfId(vendorId, mrfId);
			if(mrf == null) {
				
			}	
			return mrf;
		} catch (Exception e) {
			logger.error("Error fetching MRF details: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Candidate getCandidateByVendorAndMrfId(Long vendorId, Long mrfId) {
		try {
			logger.info("Fetching candidate for vendor ID: {} and MRF ID: {}", vendorId, mrfId);
			if (vendorId == null || mrfId == null || vendorId == 0 || mrfId == 0) {
				logger.error("Vendor Id and MRF Id are needed");
				throw new IllegalArgumentException("Vendor Id and MRF Id are needed");
			} else {
				Candidate candidate = vendorDao.getCandidateByVendorAndMrfId(vendorId, mrfId);
				logger.info("Fetched candidate: {}", candidate);
				return candidate;
			}
		} catch (Exception e) {
			logger.error("Error fetching candidate by vendor and MRF ID: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Long getCountOfCandidateByVendorAndMrfId(Long vendorId, Long mrfId) {
		try {
			logger.info("Getting count of candidates for vendor ID: {} and MRF ID: {}", vendorId, mrfId);
			if (vendorId == null || mrfId == null || vendorId == 0 || mrfId == 0) {
				logger.error("Vendor Id and MRF Id are needed");
				throw new IllegalArgumentException("Vendor Id and MRF Id are needed");
			} else {
				return vendorDao.getCountOfCandidateByVendorAndMrfId(vendorId, mrfId);
			}
		} catch (Exception e) {
			logger.error("Error getting count of candidates by vendor and MRF ID: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Long getCountOfCandidateByVendorId(Long vendorId) {
		try {
			logger.info("Getting count of candidates for vendor ID: {}", vendorId);
			if (vendorId == null || vendorId == 0) {
				logger.error("Vendor ID is needed");
				throw new IllegalArgumentException("Vendor ID is needed");
			} else {
				return vendorDao.getCountOfCandidateByVendorId(vendorId);
			}
		} catch (Exception e) {
			logger.error("Error getting count of candidates by vendor ID: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<Candidate> getHiredAndJoinedCandidatesAssignedByVendorId(Long vendorId) {
		try {
			logger.info("Fetching hired and joined candidates for vendor ID: {}", vendorId);
			if (vendorId == null || vendorId == 0) {
				logger.error("Vendor ID is needed");
				throw new IllegalArgumentException("Vendor ID is needed");
			} else {
				return vendorDao.getHiredAndJoinedCandidatesAssignedByVendorId(vendorId);
			}
		} catch (Exception e) {
			logger.error("Error fetching hired and joined candidates: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Long getCountOfHiredCandidateByVendorId(Long vendorId) {
		try {
			logger.info("Getting count of hired candidates for vendor ID: {}", vendorId);
			if (vendorId == null || vendorId == 0) {
				logger.error("Vendor ID is needed");
				throw new IllegalArgumentException("Vendor ID is needed");
			} else {
				return vendorDao.getCountOfHiredCandidateByVendorId(vendorId);
			}
		} catch (Exception e) {
			logger.error("Error getting count of hired candidates: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Long getCountOfJoinedCandidateByVendorId(Long vendorId) {
		try {
			logger.info("Getting count of joined candidates for vendor ID: {}", vendorId);
			if (vendorId == null || vendorId == 0) {
				logger.error("Vendor ID is needed");
				throw new IllegalArgumentException("Vendor ID is needed");
			} else {
				return vendorDao.getCountOfJoinedCandidateByVendorId(vendorId);
			}
		} catch (Exception e) {
			logger.error("Error getting count of joined candidates: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<MRFVendor> getAllMrfAssignedForVendor(Long vendorId) {
		try {
			logger.info("Fetching all MRFs assigned for vendor ID: {}", vendorId);
			if (vendorId == null || vendorId == 0) {
				logger.error("Vendor ID is needed");
				throw new IllegalArgumentException("Vendor ID is needed");
			} else {
				return vendorDao.getAllMrfAssignedForVendor(vendorId);
			}
		} catch (Exception e) {
			logger.error("Error fetching all MRFs assigned for vendor: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public String updateMRFStatus(Long vendorId, Long mrfId, String status) {
		try {
			logger.info("Updating MRF status for vendor ID: {} and MRF ID: {} to status: {}", vendorId, mrfId, status);
			return vendorDao.updateMRFStatus(vendorId, mrfId, status);
		} catch (Exception e) {
			logger.error("Error updating MRF status: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<VendorRemainingDaysDTO> getRemainingDays(Long vendorId) {
		try {
			logger.info("Getting remaining days for vendor ID: {}", vendorId);
			List<MRFVendor> mrfVendors = vendorDao.getRemainingDays(vendorId);
			List<VendorRemainingDaysDTO> mrfDetailsList = new ArrayList<>();
			for (MRFVendor mrfVendor : mrfVendors) {
				VendorRemainingDaysDTO dto = new VendorRemainingDaysDTO();
				dto.setMrfId(mrfVendor.getMrf().getMrfId());
				dto.setProbableDesignation(mrfVendor.getMrf().getProbableDesignation());
				dto.setMrf(mrfVendor.getMrf());
				dto.setAssignedCount(mrfVendor.getAssignedCount());
				dto.setAchievedCount(mrfVendor.getAchievedCount());

				LocalDate closureDate = mrfVendor.getMrf().getMrfCriteria().getClosureDate().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate assignedDate = mrfVendor.getAssignedDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();

				long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), closureDate);
				if (remainingDays > 0) {
					dto.setRemainingDays(remainingDays);
					dto.setAssignedDate(assignedDate);
					mrfDetailsList.add(dto);
				}
			}
			logger.info("Remaining days for vendor ID {} fetched: {}", vendorId, mrfDetailsList);
			return mrfDetailsList;
		} catch (Exception e) {
			logger.error("Error getting remaining days for vendor: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public String resetPassword(String email, String oldPassword, String newPassword) {
		try {
			logger.info("Resetting password for vendor with email: {}", email);
			Vendor vendor = vendorDao.findByEmail(email);

			if (vendor == null) {
				logger.error("Vendor not found for email: {}", email);
				throw new IllegalArgumentException("Vendor not found for ID: " + email);
			}

			if (oldPassword.equals(newPassword)) {
				logger.error("Old password and new password must not be the same");
				throw new IllegalArgumentException("Old password and new password should not be the same");
			}

			if (!oldPassword.equals(vendor.getThirdPartyCredentitals().getPassword())) {
				logger.error("Old password is incorrect for email: {}", email);
				throw new IllegalArgumentException("Old password is incorrect");
			}

			vendor.getThirdPartyCredentitals().setPassword(newPassword);
			entityManager.merge(vendor);
			logger.info("Password updated successfully for email: {}", email);
			return "Password updated successfully";
		} catch (Exception e) {
			logger.error("Error resetting password: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<Candidate> getAllCandidatesByVendorId(Long vendorId) {
		try {
			logger.info("Fetching all candidates for vendor ID: {}", vendorId);
			return vendorDao.getAllCandidatesByVendorId(vendorId);
		} catch (Exception e) {
			logger.error("Error fetching all candidates by vendor ID: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Long getVendorIdByEmail(String email) {
		try {
			logger.info("Fetching vendor ID for email: {}", email);
			return vendorDao.getVendorIdByEmail(email);
		} catch (Exception e) {
			logger.error("Error fetching vendor ID by email: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<MRFVendor> findAllMRFVendorDetailsByRmId(Long rmId) {
		try {
			logger.info("Finding all MRF vendor details for RM ID: {}", rmId);
			return vendorDao.findAllMRFvendorDetailsByRmId(rmId);
		} catch (Exception e) {
			logger.error("Error finding all MRF vendor details: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<Candidate> getAllCandidatesAssignedByVendorAndMrfJdId(Long vendorId, Long mrfJdId) {
		try {
			logger.info("Fetching all candidates assigned for vendor ID: {} and MRF JD ID: {}", vendorId, mrfJdId);
			return vendorDao.getAllCandidatesAssignedByVendorAndMrfJdId(vendorId, mrfJdId);
		} catch (Exception e) {
			logger.error("Error fetching all candidates assigned: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Vendor findVendorById(Long vendorId) {
		try {
			logger.info("Finding vendor by ID: {}", vendorId);
			return vendorDao.findVendorById(vendorId);
		} catch (Exception e) {
			logger.error("Error finding vendor by ID: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public String updateVendorProfileDetails(Long vendorId, String address, String contactName, String contactNumber,
			String websiteUrl, MultipartFile vendorOrganizationLogo) throws IOException {
		try {
			logger.info("Updating vendor profile details for vendor ID: {}", vendorId);
			Vendor vendor = vendorDao.findById(vendorId);

			if (vendor == null) {
				logger.error("Vendor not found for ID: {}", vendorId);
				return "Vendor Not found";
			}

			if (address != null) {
				vendor.setAddress(address);
			}
			if (contactName != null) {
				vendor.setContactName(contactName);
			}
			if (contactNumber != null) {
				vendor.setContactNumber(contactNumber);
			}
			if (websiteUrl != null) {
				vendor.setWebsiteUrl(websiteUrl);
			}

			if (vendorOrganizationLogo != null) {
				if (vendorOrganizationLogo.getSize() > 153600) {
					logger.error("File size exceeds the limit of 150 KB for vendor ID: {}", vendorId);
					return "File size exceeds the limit of 150 KB.";
				}
				if (!ALLOWED_CONTENT_TYPES.contains(vendorOrganizationLogo.getContentType())) {
					logger.error("Invalid file type for vendor ID: {}. Only JPG, JPEG, and PNG files are allowed.",
							vendorId);
					return "Invalid file type. Only JPG, JPEG, and PNG files are allowed.";
				}

				byte[] logoBytes = vendorOrganizationLogo.getBytes();
				vendor.setVendorOrganizationLogo(logoBytes);
			}
			vendorDao.updateVendorProfile(vendor);
			logger.info("Vendor profile updated successfully for vendor ID: {}", vendorId);
			return "Vendor profile updated successfully";
		} catch (Exception e) {
			logger.error("Error updating vendor profile details: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<Vendor> getAllVendorsByVendors() {
		try {
			logger.info("Fetching all vendor details");
			return vendorDao.getAllVendors();
		} catch (Exception e) {
			logger.error("Error fetching all vendor details: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public List<MRFVendor> getAllAssignedVendors() {
		try {
			logger.info("Fetching all assigned vendor details");
			return vendorDao.getAllAssignedVendors();
		} catch (Exception e) {
			logger.error("Error fetching all assigned vendor details: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	public List<Object[]> monthwiseCandidateData(Long vendorId, int year) {
		try {
			logger.info("Fetching month-wise candidate data for vendor ID: {} and year: {}", vendorId, year);
			return vendorDao.monthwiseCandidateData(vendorId, year);
		} catch (Exception e) {
			logger.error("Error fetching month-wise candidate data: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}

	@Override
	public Long getRecruitingManagerId(Long vendorId, Long mrfId) {
		try {
			logger.info("Getting recruiting manager ID for vendor ID: {} and MRF ID: {}", vendorId, mrfId);
			return vendorDao.getRecruitingManagerId(vendorId, mrfId);
		} catch (Exception e) {
			logger.error("Error getting recruiting manager ID: {}", e.getMessage(), e);
			throw e; // rethrowing exception
		}
	}
	
	@Override
	public ResponseEntity<String> addBulkCandidate(CandidateDto candidateDto) throws MessagingException, IOException {
		return candidateCommunicationInterface.addBulkCandidate(candidateDto);
	}

	

	
}
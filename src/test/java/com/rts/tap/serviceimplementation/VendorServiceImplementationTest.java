package com.rts.tap.serviceimplementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.rts.tap.dao.VendorDao;
import com.rts.tap.dto.VendorRemainingDaysDTO;
import com.rts.tap.model.Candidate;
import com.rts.tap.model.MRF;
import com.rts.tap.model.MRFCriteria;
import com.rts.tap.model.MRFVendor;
import com.rts.tap.model.Vendor;

class VendorServiceImplementationTest {

    @InjectMocks
    private VendorServiceImplementation vendorService;

    @Mock
    private VendorDao vendorDao;

    @Mock
    private Vendor vendor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    

    @Test
    void testGetCountOfCandidateByVendorId_NullVendorId() {
        assertThrows(IllegalArgumentException.class, () -> vendorService.getCountOfCandidateByVendorId(null));
    }
    
    @Test
    void testGetCountOfCandidateByVendorId_ZeroVendorId() {
        assertThrows(IllegalArgumentException.class, () -> vendorService.getCountOfCandidateByVendorId(0L));
    }

    @Test
    void testGetCountOfCandidateByVendorId_ValidId() {
        Long vendorId = 1L;
        when(vendorDao.getCountOfCandidateByVendorId(vendorId)).thenReturn(10L);

        Long count = vendorService.getCountOfCandidateByVendorId(vendorId);

        assertEquals(10L, count);
        verify(vendorDao).getCountOfCandidateByVendorId(vendorId);
    }

    @Test
    void testGetHiredAndJoinedCandidatesAssignedByVendorId_NullVendorId() {
        assertThrows(IllegalArgumentException.class, () -> vendorService.getHiredAndJoinedCandidatesAssignedByVendorId(null));
    }

    @Test
    void testGetHiredAndJoinedCandidatesAssignedByVendorId_ZeroVendorId() {
        assertThrows(IllegalArgumentException.class, () -> vendorService.getHiredAndJoinedCandidatesAssignedByVendorId(0L));
    }

    @Test
    void testGetHiredAndJoinedCandidatesAssignedByVendorId_ValidId() {
        Long vendorId = 1L;
        List<Candidate> expectedList = Collections.emptyList();

        when(vendorDao.getHiredAndJoinedCandidatesAssignedByVendorId(vendorId)).thenReturn(expectedList);

        List<Candidate> actualList = vendorService.getHiredAndJoinedCandidatesAssignedByVendorId(vendorId);

        assertEquals(expectedList, actualList);
        verify(vendorDao).getHiredAndJoinedCandidatesAssignedByVendorId(vendorId);
    }

    @Test
    void testGetCountOfHiredCandidateByVendorId_NullVendorId() {
        assertThrows(IllegalArgumentException.class, () -> vendorService.getCountOfHiredCandidateByVendorId(null));
    }

    @Test
    void testGetCountOfHiredCandidateByVendorId_ZeroVendorId() {
        assertThrows(IllegalArgumentException.class, () -> vendorService.getCountOfHiredCandidateByVendorId(0L));
    }

    @Test
    void testGetCountOfHiredCandidateByVendorId_ValidId() {
        Long vendorId = 1L;
        when(vendorDao.getCountOfHiredCandidateByVendorId(vendorId)).thenReturn(8L);

        Long count = vendorService.getCountOfHiredCandidateByVendorId(vendorId);

        assertEquals(8L, count);
        verify(vendorDao).getCountOfHiredCandidateByVendorId(vendorId);
    }

    @Test
    void testGetCountOfJoinedCandidateByVendorId_NullVendorId() {
        assertThrows(IllegalArgumentException.class, () -> vendorService.getCountOfJoinedCandidateByVendorId(null));
    }

    @Test
    void testGetCountOfJoinedCandidateByVendorId_ZeroVendorId() {
        assertThrows(IllegalArgumentException.class, () -> vendorService.getCountOfJoinedCandidateByVendorId(0L));
    }

    @Test
    void testGetCountOfJoinedCandidateByVendorId_ValidId() {
        Long vendorId = 1L;
        when(vendorDao.getCountOfJoinedCandidateByVendorId(vendorId)).thenReturn(3L);

        Long count = vendorService.getCountOfJoinedCandidateByVendorId(vendorId);

        assertEquals(3L, count);
        verify(vendorDao).getCountOfJoinedCandidateByVendorId(vendorId);
    }

    @Test
    void testGetRemainingDays_ValidId() {
        Long vendorId = 1L;
        
        // Set up a mock MRFVendor with realistic data
        MRFVendor mockMrfVendor = new MRFVendor();
        mockMrfVendor.setAssignedDate(new Date());
        MRF mrf = new MRF();
        mrf.setMrfCriteria(new MRFCriteria());
        // Set closure date to 10 days from now
        LocalDate closureDate = LocalDate.now().plusDays(10);
        mrf.getMrfCriteria().setClosureDate(Date.from(closureDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        mockMrfVendor.setMrf(mrf);
        
        List<MRFVendor> mrfVendors = Collections.singletonList(mockMrfVendor);
        
        when(vendorDao.getRemainingDays(vendorId)).thenReturn(mrfVendors);

        List<VendorRemainingDaysDTO> result = vendorService.getRemainingDays(vendorId);

        assertNotNull(result);
        assertFalse(result.isEmpty()); // Ensure that some DTOs are returned
        VendorRemainingDaysDTO dto = result.get(0);
        assertEquals(mockMrfVendor.getMrf().getMrfId(), dto.getMrfId());
        assertEquals(10L, dto.getRemainingDays());
        verify(vendorDao).getRemainingDays(vendorId);
    }

    @Test
    void testGetRemainingDays_NoRemainingDays() {
        Long vendorId = 1L;
        
        // Set up a mock MRFVendor with an overdue closure date
        MRFVendor mockMrfVendor = new MRFVendor();
        mockMrfVendor.setAssignedDate(new Date());
        MRF mrf = new MRF();
        mrf.setMrfCriteria(new MRFCriteria());
        // Set closure date to yesterday
        LocalDate closureDate = LocalDate.now().minusDays(1);
        mrf.getMrfCriteria().setClosureDate(Date.from(closureDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        mockMrfVendor.setMrf(mrf);
        
        List<MRFVendor> mrfVendors = Collections.singletonList(mockMrfVendor);
        
        when(vendorDao.getRemainingDays(vendorId)).thenReturn(mrfVendors);

        List<VendorRemainingDaysDTO> result = vendorService.getRemainingDays(vendorId);

        assertNotNull(result);
        assertTrue(result.isEmpty()); // No DTOs should be returned
        verify(vendorDao).getRemainingDays(vendorId);
    }

    @Test
    void testUpdateVendorProfileDetails_VendorNotFound() throws Exception {
        Long vendorId = 1L;
        String invalidAddress = "New Address";
        String contactName = "New Contact";
        String contactNumber = "1234567890";
        String websiteUrl = "http://example.com";
        MultipartFile logo = mock(MultipartFile.class);

        when(vendorDao.findById(vendorId)).thenReturn(null);  // Simulate vendor not found

        String result = vendorService.updateVendorProfileDetails(vendorId, invalidAddress, contactName, contactNumber, websiteUrl, logo);

        assertEquals("Vendor Not found", result);
    }

    @Test
    void testUpdateVendorProfileDetails_Success() throws Exception {
        Long vendorId = 1L;
        String newAddress = "New Address";
        String contactName = "New Contact";
        String contactNumber = "1234567890";
        String websiteUrl = "http://example.com";
        byte[] logoBytes = new byte[10];
        MultipartFile logo = mock(MultipartFile.class);

        when(vendorDao.findById(vendorId)).thenReturn(vendor);
        when(logo.getSize()).thenReturn(100L);
        when(logo.getContentType()).thenReturn("image/jpeg");
        when(logo.getBytes()).thenReturn(logoBytes);

        String result = vendorService.updateVendorProfileDetails(vendorId, newAddress, contactName, contactNumber, websiteUrl, logo);

        assertEquals("Vendor profile updated successfully", result);
        verify(vendorDao).updateVendorProfile(vendor);
    }

    @Test
    void testUpdateVendorProfileDetails_InvalidFileType() throws Exception {
        Long vendorId = 1L;
        String newAddress = "New Address";
        String contactName = "New Contact";
        String contactNumber = "1234567890";
        String websiteUrl = "http://example.com";
        MultipartFile logo = mock(MultipartFile.class);

        when(vendorDao.findById(vendorId)).thenReturn(vendor);
        when(logo.getSize()).thenReturn(100L);
        when(logo.getContentType()).thenReturn("application/pdf");

        String result = vendorService.updateVendorProfileDetails(vendorId, newAddress, contactName, contactNumber, websiteUrl, logo);

        assertEquals("Invalid file type. Only JPG, JPEG, and PNG files are allowed.", result);
        verify(vendorDao, never()).updateVendorProfile(vendor);
    }

    @Test
    void testUpdateVendorProfileDetails_FileSizeExceeds() throws Exception {
        Long vendorId = 1L;
        String newAddress = "New Address";
        String contactName = "New Contact";
        String contactNumber = "1234567890";
        String websiteUrl = "http://example.com";
        MultipartFile logo = mock(MultipartFile.class);

        when(vendorDao.findById(vendorId)).thenReturn(vendor);
        when(logo.getSize()).thenReturn(160000L);
        when(logo.getContentType()).thenReturn("image/jpeg");

        String result = vendorService.updateVendorProfileDetails(vendorId, newAddress, contactName, contactNumber, websiteUrl, logo);

        assertEquals("File size exceeds the limit of 150 KB.", result);
        verify(vendorDao, never()).updateVendorProfile(vendor);
    }
}
package com.rts.tap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalException extends Throwable {

	private static final long serialVersionUID = 1L;
	
	@ExceptionHandler(BudgetNotFoundException.class)
	public ResponseEntity<String> handleBudgetNotFoundException(BudgetNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CandidateDataFetchException.class)
	public ResponseEntity<String> handleCandidateDataFetchException(CandidateDataFetchException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ClientConflictException.class)
	public ResponseEntity<String> handleClientConflictException(ClientConflictException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ClientEmailAlreadyExistsException.class)
	public ResponseEntity<String> handleClientEmailAlreadyExistsException(ClientEmailAlreadyExistsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ClientNotFoundException.class)
	public ResponseEntity<String> handleCandidateNotFoundException(ClientNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ClientOrganizationNotFoundException.class)
	public ResponseEntity<String> handleClientOrganizationNotFoundException(ClientOrganizationNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ClientPersistenceException.class)
	public ResponseEntity<String> handleClientPersistenceException(ClientPersistenceException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ClientUpdateException.class)
	public ResponseEntity<String> handleClientUpdateException(ClientUpdateException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmailSendingException.class)
	public ResponseEntity<String> handleEmailSendingException(EmailSendingException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FileSizeExceededException.class)
	public ResponseEntity<String> handleFileSizeExceededException(FileSizeExceededException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InterviewDataFetchException.class)
	public ResponseEntity<String> handleInterviewDataFetchException(InterviewDataFetchException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidClientDataException.class)
	public ResponseEntity<String> handleInvalidClientDataException(InvalidClientDataException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidFileTypeException.class)
	public ResponseEntity<String> handleInvalidFileTypeException(InvalidFileTypeException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OrganizationLogoException.class)
	public ResponseEntity<String> handleOrganizationLogoException(OrganizationLogoException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RequirementNotFoundException.class)
	public ResponseEntity<String> handleRequirementNotFoundException(RequirementNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SubRequirementException.class)
	public ResponseEntity<String> handleSubRequirementException(SubRequirementException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SubRequirementNotFoundException.class)
	public ResponseEntity<String> handleSubRequirementNotFoundException(SubRequirementNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	
	
}
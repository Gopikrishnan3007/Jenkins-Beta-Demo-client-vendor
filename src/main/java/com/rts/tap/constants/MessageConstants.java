

package com.rts.tap.constants;

public class MessageConstants {
	
	private MessageConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
	
	//--------------------------------------------------------------------------------------------------------------------------------------------
	//Client Service
	
	
	//ClientDashboardServiceImpl
	public static final String ERROR_FETCHING_HIRED_CANDIDATE_COUNT = "Error fetching hired candidate count";
    public static final String ERROR_FETCHING_HIRED_CANDIDATE_COUNT_FOR_CLIENT = "Unable to fetch hired candidates count for client id: ";
    public static final String ERROR_FETCHING_SHORTLISTED_CANDIDATE_COUNT = "Error fetching shortlisted candidate count";
    public static final String ERROR_FETCHING_SHORTLISTED_CANDIDATE_COUNT_FOR_CLIENT = "Unable to fetch shortlisted candidates count for client id: ";
    public static final String ERROR_FETCHING_HIRED_CANDIDATE_LIST = "Error fetching hired candidate list";
    public static final String ERROR_FETCHING_HIRED_CANDIDATE_LIST_FOR_REQUIREMENT = "Unable to fetch hired candidate list for requirement id: ";
    public static final String ERROR_FETCHING_SHORTLISTED_CANDIDATE_LIST = "Error fetching shortlisted candidate list";
    public static final String ERROR_FETCHING_SHORTLISTED_CANDIDATE_LIST_FOR_REQUIREMENT = "Unable to fetch shortlisted candidate list for requirement id: ";
    public static final String ERROR_FETCHING_HIRED_PEOPLE_DATA = "Error fetching hired people data";
    public static final String ERROR_FETCHING_HIRED_PEOPLE_DATA_FOR_CLIENT = "Unable to fetch hired people data for client id: ";
    public static final String ERROR_FETCHING_SHORTLISTED_PEOPLE = "Error fetching shortlisted people";
    public static final String ERROR_FETCHING_SHORTLISTED_PEOPLE_FOR_CLIENT = "Unable to fetch shortlisted people for client id: ";
    public static final String ERROR_FETCHING_SHORTLISTED_CANDIDATES_BY_REQUIREMENT = "Error fetching shortlisted candidates by requirement";
    public static final String ERROR_FETCHING_SHORTLISTED_CANDIDATES_BY_REQUIREMENT_ID = "Unable to fetch shortlisted candidates by requirement id: ";
    public static final String ERROR_FETCHING_HIRED_CANDIDATES_BY_REQUIREMENT = "Error fetching hired candidates by requirement";
    public static final String ERROR_FETCHING_HIRED_CANDIDATES_BY_REQUIREMENT_ID = "Unable to fetch hired candidates by requirement id: ";
    public static final String ERROR_FETCHING_ALL_INTERVIEWS = "Error fetching all interviews";
    public static final String ERROR_FETCHING_ALL_INTERVIEWS_FOR_CLIENT = "Unable to fetch all interviews for client id: ";
    public static final String ERROR_FETCHING_CLIENT_PARTNER_ID = "Error fetching client partner id";
    public static final String ERROR_FETCHING_CLIENT_PARTNER_ID_FOR_CLIENT = "Unable to fetch client partner id for client id: ";
    public static final String CLIENT_ID_IS_NULL = "Client ID is null";
    
    
    //ClientOrganizationProfileServiceImpl
    public static final String ORGANIZATION_LOGO_MISSING = "Organization logo is missing";
    public static final String FILE_SIZE_EXCEEDS_LIMIT = "File size exceeds the limit of 150 KB.";
    public static final String INVALID_FILE_TYPE = "Invalid file type. Only JPG, JPEG, and PNG files are allowed.";
    public static final String CLIENT_ORGANIZATION_NOT_FOUND = "Client organization not found";
    public static final String ORGANIZATION_LOGO_UPDATED_SUCCESS = "Organization logo updated successfully!";
    public static final String ERROR_UPDATING_ORGANIZATION_LOGO = "Error updating organization logo: ";
    
    
    //ClientProfileServiceImpl
    public static final String CLIENT_NOT_FOUND_WITH_ID = "Client not found with ID: ";
    public static final String CLIENT_NOT_FOUND_WITH_EMAIL = "Client not found with email: ";
    public static final String CLIENT_POSITION_CANNOT_BE_EMPTY = "Client position cannot be empty";
    public static final String CLIENT_MOBILE_CANNOT_BE_EMPTY = "Client mobile number cannot be empty";
    public static final String CLIENT_UPDATE_FAILED = "Failed to update client with ID: ";
    public static final String ERROR_UPDATING_CLIENT_BY_ID = "Error updating client by ID: {}";
    public static final String ERROR_UPDATING_CLIENT_BY_EMAIL = "Error updating client by email: {}";
    public static final String ERROR_UPDATING_CLIENT_DETAILS = "An error occurred while updating client details";
    public static final String CLIENT_NOT_FOUND = "Client not found";
    public static final String ERROR_VIEWING_ALL_CLIENTS = "Failed to view all clients";
    
    //ClientServiceImpl
    public static final String CLIENT_UPDATED_SUCCESS = "Client updated successfully!";
    public static final String SUCCESSFULLY_UPDATING_CLIENT_DETAILS = "Client Details Updated Successfully";
    public static final String FAILED_UPDATING_CLIENT_DETAILS = "Failed to update Client Details";

    public static final String ADD_REQUIREMENT_FAILURE = "Add Requirement Failure";
    public static final String ADD_REQUIREMENT_SUCCESS = "Add Requirement Success";
    public static final String DELETE_SUCCESS = "Delete Success";
    public static final String DELETE_FAILURE = "Delete Failure";
    public static final String UPDATE_SUCCESS = "Update Success";
    public static final String UPDATE_FAILURE = "Update Failure";
    
    public static final String SAVING_CLIENT = "Saving client: {}";
    public static final String ERROR_SAVING_CLIENT = "Error saving client '{}': {}";
    public static final String FAILED_SAVING_CLIENT = "Failed to save client";
    public static final String DELETING_CLIENT_BY_ID = "Deleting client with ID: {}";
    public static final String ERROR_DELETING_CLIENT = "Error deleting client with ID {}: {}";
    public static final String FAILED_DELETING_CLIENT = "Failed to delete client";
    
    public static final String FETCHING_ALL_CLIENTS = "Fetching all clients";
    public static final String ERROR_FETCHING_ALL_CLIENTS = "Error fetching all clients: {}";
    public static final String FAILED_FETCHING_CLIENTS = "Failed to fetch clients";
    
    public static final String FETCHING_CLIENT_BY_ID = "Fetching client by ID: {}";
    public static final String ERROR_FETCHING_CLIENT_BY_ID = "Error fetching client by ID {}: {}";
    public static final String FAILED_FETCHING_CLIENT_BY_ID = "Failed to fetch client by ID: ";
    
    public static final String CLIENT_NOT_FOUND_BY_ID = "Client not found for ID: ";
    public static final String UPDATING_CLIENT_DETAILS = "Updating client details for ID: {}";
    public static final String CLIENT_NOT_FOUND_BY_MRF_ID = "Client not found for MRF ID: ";
    public static final String FETCHING_CLIENT_BY_MRF_ID = "Fetching client by MRF ID: {}";
    public static final String ERROR_FETCHING_CLIENT_BY_MRF_ID = "Error fetching client by MRF ID {}: {}";
    
    public static final String FETCHING_CLIENT_ID_BY_EMAIL = "Fetching client ID by email: {}";
    public static final String ERROR_FETCHING_CLIENT_ID_BY_EMAIL = "Error fetching client ID by email {}: {}";
    public static final String FAILED_FETCHING_CLIENT_ID_BY_EMAIL = "Failed to fetch client ID by email";
    
    public static final String FETCHING_CLIENT_PARTNERS_BY_BU_ID = "Fetching client partners for BU ID: {}";
    public static final String ERROR_FETCHING_CLIENT_PARTNERS_BY_BU_ID = "Error fetching client partners by BU ID {}: {}";
    public static final String FAILED_FETCHING_CLIENT_PARTNERS = "Failed to fetch client partners";
    
    public static final String UPDATING_CLIENT_FOR_CREDENTIALS = "Updating client for ID: {} with status: {} and reason: {}";
    public static final String ERROR_UPDATING_CLIENT_FOR_CREDENTIALS = "Error updating client for credentials ID {}: {}";
    public static final String FAILED_UPDATING_CLIENT_FOR_CREDENTIALS = "Failed to update client for credentials";
    
    public static final String CHECKING_CLIENT_EMAIL_EXISTS = "Checking if client email exists: {}";
    public static final String ERROR_CHECKING_CLIENT_EMAIL_EXISTS = "Error checking if client email exists {}: {}";
    public static final String FAILED_CHECKING_CLIENT_EMAIL_EXISTS = "Failed to check if client email exists";
    
    public static final String FINDING_ORGANIZATION_BY_ID = "Finding organization by ID: {}";
    public static final String ERROR_FINDING_ORGANIZATION_BY_ID = "Error finding organization by ID {}: {}";
    public static final String FAILED_FINDING_ORGANIZATION = "Failed to find organization";
    
    public static final String ATTEMPTING_TO_SAVE_CLIENT = "Attempting to save client: {}";
    public static final String SUCCESSFULLY_SAVED_CLIENT = "Successfully saved client: {}";
    
    public static final String ERROR_SAVING_CLIENT_EMAIL_EXISTS = "Error saving client '{}': Email '{}' already exists.";
    public static final String UNEXPECTED_ERROR_SAVING_CLIENT = "Unexpected error saving client '{}': {}";
    public static final String ALL_CLIENTS_PROCESSED_SUCCESSFULLY = "All clients processed successfully.";
    
    public static final String FETCHING_ALL_CLIENTS_BY_CLIENT_PARTNER_ID = "Fetching all clients for client partner ID: {}";
    public static final String ERROR_FETCHING_ALL_CLIENTS_BY_CLIENT_PARTNER_ID = "Error fetching all clients for client partner ID {}: {}";
    public static final String FAILED_FETCHING_CLIENTS_BY_CLIENT_PARTNER_ID = "Failed to fetch clients for client partner ID";
    
    public static final String CHANGING_ACTIVATION_STATUS_CLIENT_ID = "Changing activation status for client ID: {}";
    public static final String UPDATED_ACTIVATION_STATUS = "Updated activation status for client ID: {}";
    public static final String ERROR_CHANGING_ACTIVATION_STATUS = "Error changing activation status for client ID {}: {}";
    public static final String FAILED_CHANGING_ACTIVATION_STATUS = "Failed to change activation status";  
    public static final String FAILED_FETCHING_CLIENT_BY_MRF_ID = "Failed to fetch client by MRF ID";
    
    //RequirementServiceImp
    public static final String LOG_CREATING_REQUIREMENT = "Creating requirement for client ID: {}";
    public static final String LOG_REQUIREMENT_CREATED = "Requirement created with result: {}";
    public static final String LOG_UPDATING_REQUIREMENT = "Updating requirement with ID: {}";
    public static final String LOG_REQUIREMENT_UPDATED = "Requirement updated with result: {}";
    public static final String LOG_REMOVING_REQUIREMENT = "Removing requirement with ID: {}";
    public static final String LOG_REQUIREMENT_DELETED = "Requirement deleted with result: {}";
    public static final String LOG_FETCHING_ALL_REQUIREMENTS = "Fetching all requirements";
    public static final String LOG_RETRIEVED_REQUIREMENTS = "Retrieved {} requirements";
    public static final String LOG_FETCHING_REQUIREMENTS_BY_CLIENT = "Fetching requirements for client ID: {}";
    public static final String LOG_RETRIEVED_REQUIREMENTS_FOR_CLIENT = "Retrieved {} requirements for client ID: {}";
    public static final String LOG_COUNTING_REQUIREMENTS_FOR_CLIENT = "Counting requirements for client ID: {}";
    public static final String LOG_COUNTED_REQUIREMENTS_FOR_CLIENT = "Counted {} requirements for client ID: {}";
    public static final String LOG_FETCHING_HIRED_CANDIDATES = "Fetching hired candidates for requirement ID: {}";
    public static final String LOG_RETRIEVED_HIRED_CANDIDATES = "Retrieved {} hired candidates for requirement ID: {}";
    public static final String LOG_FETCHING_REQUIREMENTS_FOR_CLIENT_ID = "Fetching requirements for client ID: {}";
    public static final String LOG_FETCHING_REQUIREMENTS_FOR_CLIENT_PARTNER = "Fetching requirements for client partner ID: {}";
    public static final String LOG_FETCHING_REQUIREMENTS_FOR_BU_HEAD = "Fetching requirements for BU Head ID: {}";
    public static final String LOG_FETCHING_REQUIREMENT_BY_ID = "Fetching requirement by ID: {}";
    public static final String LOG_RETRIEVED_REQUIREMENT_DTO = "Retrieved requirement DTO for ID: {}";
    public static final String LOG_FETCHING_REQUIREMENT_DETAIL = "Fetching requirement detail for ID: {}";
    public static final String LOG_FETCHING_BUDGET_MAPPING = "Fetching budget mapping for requirement ID: {}";
    public static final String LOG_RETRIEVED_BUDGET_MAPPING = "Retrieved budget mapping for requirement ID: {}";
    public static final String LOG_SENDING_RENEGOTIATION_EMAIL = "Sending renegotiation email for requirement ID: {}";
    public static final String LOG_RENEGOTIATION_EMAIL_SENT = "Renegotiation email sent successfully to: {}";
    public static final String LOG_FETCHING_RENEGOTIATION_EMAIL_CONTENT = "Fetching content for renegotiation email for requirement ID: {}";
    public static final String LOG_RETRIEVED_EMAIL_CONTENT_FOR_RENEGOTIATION = "Retrieved email content for renegotiation for requirement ID: {}";
    public static final String BUDGET_REQUEST = "Budget Renegotiation Request";


    public static final String ERROR_CREATING_REQUIREMENT = "Error creating requirement: {}";
    public static final String ERROR_UPDATING_REQUIREMENT = "Error updating requirement: {}";
    public static final String ERROR_REMOVING_REQUIREMENT = "Error removing requirement: {}";
    public static final String ERROR_FETCHING_REQUIREMENTS = "Error fetching requirements: {}";
    public static final String ERROR_FETCHING_REQUIREMENTS_FOR_CLIENT = "Error fetching requirements for client: {}";
    public static final String ERROR_COUNTING_REQUIREMENTS = "Error counting requirements: {}";
    public static final String ERROR_FETCHING_HIRED_CANDIDATES = "Error fetching hired candidates: {}";
    public static final String ERROR_FETCHING_REQUIREMENT = "Error fetching requirement: {}";
    public static final String ERROR_FETCHING_REQUIREMENTS_FOR_CLIENT_PARTNER = "Error fetching requirements for client partner: {}";
    public static final String ERROR_FETCHING_REQUIREMENTS_FOR_BU_HEAD = "Error fetching requirements for BU Head: {}";
    public static final String ERROR_BUDGET_NOT_FOUND = "Budget not found for requirement ID: ";
    public static final String ERROR_REQUIREMENT_NOT_FOUND = "Requirement not found with ID: ";
    public static final String ERROR_CLIENT_NOT_FOUND = "Client not found for requirement ID: ";
    public static final String ERROR_SENDING_EMAIL = "Error sending email: ";
    public static final String ERROR_FETCHING_EMAIL_CONTENT = "Error fetching email content: {}";
    public static final String ERROR_FETCHING_BUDGET_MAPPING = "Retrieved budget mapping for requirement ID:";

    public static final String SUCCESS_RENEGOTIATION_EMAIL_SENT = "Renegotiation email sent successfully.";
    
    
    //SubRequirementServiceImpl
    public static final String SUB_REQUIREMENT_CANNOT_BE_NULL = "SubRequirement cannot be null";
    public static final String SUB_REQUIREMENT_ADDED_SUCCESSFULLY = "Sub Requirement added successfully";
    public static final String NO_SUB_REQUIREMENTS_FOUND = "No sub requirements found";
    public static final String ERROR_ADDING_SUB_REQUIREMENT = "Error adding sub requirement";
    public static final String ERROR_FETCHING_SUB_REQUIREMENTS = "Error retrieving sub requirements";
    
    //---------------------------------------------------------------------------------------------------------------------------------------------
}

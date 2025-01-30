package com.rts.tap.constants;

public class APIConstants {
	
	private APIConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
	
	public static final String BASE_URL = "/tap";
	public static final String FRONT_END_URL = "http://localhost:3000";

	public static final String CLIENT_REQUESTMAPPING_API = BASE_URL + "/client";
	public static final String CANDIATE_REQUESTMAPPING_API = BASE_URL + "/candidates";
	public static final String EMPLOYEE_REQUESTMAPPING_API = BASE_URL + "/employee";
	
	

	public static final String GET_CLIENT_CANDIDATE_HIRED = "/hiredCount/{clientId}";
	public static final String GET_CLIENT_CANDIDATE_SHORTLISTED = "/shortListedCount/{clientId}";
	public static final String GET_CLIENT_HIRED = "/hired/{requirementId}";
	public static final String GET_CLIENT_SHORTLISTED = "/shortlisted/{requirementId}";
	public static final String GET_CANDIDATE_HIRED = "/hiredCandidate/{clientId}";
	public static final String GET_CANDIDATE_SHORTLISTED = "/short-listed/{clientId}";
	public static final String GET_CANDIDATE_SHORTLISTED_REQUIREMENT = "/short-listed-requirement/{requirementId}";
	public static final String GET_CANDIDATE_HIRED_REQUIREMENT = "/hired-requirement/{requirementId}";
	public static final String CLIENT_ID_BY_EMAIL = "/client-email";
	public static final String CLIENT_INTERVIEWS = "/client-interviews";
	public static final String GET_CLIENT_PARTNER_ID_BY_CLIENT = "/client-partner-id";
	public static final String UPDATE_CLIENT_ORGANIZATION_LOGO_BY_ID = "/client-logo-update-by-id/{clientId}/logo";
	public static final String GET_CLIENT_BY_ID = "/client-profile-by-id/{clientId}";
	public static final String GET_CLIENT_BY_EMAIL = "/client-profile-by-email/{clientEmail}";
	public static final String UPDATE_CLIENT_PROFILE_BY_ID = "/client-profile-update-by-id/{clientId}";
	public static final String UPDATE_CLIENT_PROFILE_BY_EMAIL = "/client-profile-update-by-email/{clientEmail}";
	public static final String GET_ALL_CLIENTS = "/clients";
	public static final String REQUIREMENT_ADD_API = "/requirement";

	public static final String REQUIREMENT_DELETE_API = "/delete/{requirementId}";
	public static final String REQUIREMENT_GETALL_REQUIREMENT_API = "/allRequirements";

	public static final String REQUIREMENT_REQUIREMENTBY_CLIENT_API = "/requirementByClientId/{clientId}";
	public static final String REQUIREMENT_COUNT_CLIENT_API = "/requirementCount/{clientId}";

	public static final String REQUIREMENT_LIST_BY_CLIENT_API = "/requirement-by-client/{clientId}";
	public static final String REQUIREMENT_GET_REQUIREMENTBYID_API = "/requirement/{requirementId}";
	public static final String GET_REQUIREMENT_BY_CLIENTPARTNERID = "/getRequirementByClientPartnerId/{clientPartnerId}";
	public static final String GET_REQUIREMENTS_BY_BUHEADID = "/getRequirementsByBuHeadId/{buHeadId}";
	public static final String REQUIREMENT_GET_BY_ID_API = "/getrequirement/{requirementId}";
	public static final String REQUIREMENT_UPDATE_API = "/updateRequirement";

	public static final String RENEGOTIATIONEMAIL = "/renegotiate/{requirementId}";
	public static final String GET_RENEGOTIATION_EMAIL_CONTENT = "/emailContent/{requirementId}";
	public static final String ADD_SUB_REQUIREMENTS = "/add-sub-requirements";
	public static final String VIEW_SUB_REQUIREMENTS = "/list-sub-requirements";

	public static final String ADD_CLIENTS = "/addClient";

	public static final String GET_CLIENTBYID = "/getClient/{id}";
	public static final String GET_CLIENT_BY_MRFID = "/viewClientNameByMrfId/{mrfId}";

	public static final String GET_CLIENTPARTNER_BY_BU = "/getallclientpartnerbybu/{buId}";
	public static final String UPDATE_CLIENT_STATUS = "/UpdateCLientApprove/{id}";

	public static final String UPDATE_CLIENT_DETAILS = "/updateClient/{clientId}";
	public static final String UPDATE_CLIENT_ACTIVATION_STATUS = "/updateClientActivationStatus/{clientId}";
	public static final String GET_CLIENT_BY_CLIENTPARTNERID = "/getAllClientsByClientPartnerId/{clientPartnerId}";
	
	public static final String GET_BUDGET_BY_REQUIREMENTID_URL = "/budget/getBudgetByRequirementId/{requirementId}";
	public static final String GET_CLIENTPARTNER_BU = "/getClientPartner/bu/{buId}";
	public static final String GET_INTERVIEWS_BY_CLIENT = "/interviews/client";

}

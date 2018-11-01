package uk.bisel.czi.webservice;

public class Error {

	private String status;
	private String errorType;
	private String message;
	private String query;
	
	
	public Error(String status, String message, String errorType, String query) {
		super();
		this.status = status;
		this.message = message;
		this.errorType = errorType.replaceAll("class ", "");
		this.query = query;
	}

	public String getStatus() {
		return status;
	}
	public String getErrorType() {		
		return errorType;
	}		
	public String getMessage() {
		return message;
	}
	public String getQuery() {
		return query;
	}	
}

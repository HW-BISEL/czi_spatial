package uk.bizel.czi.webservice;

import com.google.gson.JsonElement;

public class Result {
	private String status;
	private String query; 
	private JsonElement result;
	public Result(String status, String query, JsonElement result) {
		super();
		this.status = status;
		this.query = query;
		this.result = result;
	}
	public String getStatus() {
		return status;
	}
	public String getQuery() {
		return query;
	}
	public JsonElement getResult() {
		return result;
	}
}

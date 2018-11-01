package uk.bisel.czi.logging;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LogToDBMS {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int unixTimeStamp;

	private String ipAddress;

	private String queryURL;

	public LogToDBMS() {
		// TODO Auto-generated constructor stub
	}

	public LogToDBMS(int unixTimeStamp, String ipAddress, String queryURL) {
		super();
		this.unixTimeStamp = unixTimeStamp;
		this.ipAddress = ipAddress;
		this.queryURL = queryURL;
	}

	public Long getId() {
		return id;
	}

	public int getUnixTimeStamp() {
		return unixTimeStamp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getQueryURL() {
		return queryURL;
	}

}

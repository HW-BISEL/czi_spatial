package uk.bizel.czi.webservice;

import static org.junit.Assert.*;

import org.junit.Test;

public class ErrorTest {

	@Test
	public void test() {
		Error obj = new Error("status", "message", "errorType","query");
		
		assertEquals("the status", "status", obj.getStatus());
		assertEquals("the message", "message", obj.getMessage());
		assertEquals("the errorType", "errorType", obj.getErrorType());
		assertEquals("the query", "query", obj.getQuery());
		
	}

}

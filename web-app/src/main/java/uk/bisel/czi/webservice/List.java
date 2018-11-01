package uk.bisel.czi.webservice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import uk.bisel.czi.data.NotADao;
import uk.bisel.czi.logging.WriteToLog;
import uk.bisel.czi.webservice.exceptions.PathException;

@WebServlet("/list/*")
public class List extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */	
    public List() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
				
		WriteToLog log = new WriteToLog();
		log.write(request.getRemoteAddr(), request.getRequestURL().toString());		
		
		String unprocessedPath = request.getPathInfo();
		String[] pathElements = unprocessedPath.split("/");
		
		if (pathElements.length != 2) {
			throw new PathException("URL should contain exactly 1 element: mappings/regions/points");
		}
			
		NotADao dao = new NotADao();
		Gson gson = new GsonBuilder().create();
		JsonElement element = null;
		
		if (pathElements[1].equalsIgnoreCase("mappings")) {			
			element = gson.toJsonTree(dao.getAllImageMappings());

		} else if (pathElements[1].equalsIgnoreCase("regions")) {			
			element = gson.toJsonTree(dao.getAllRegions());

		} else if (pathElements[1].equalsIgnoreCase("points")) {			
			element = gson.toJsonTree(dao.getAllPoints());

		} else {
			throw new PathException(
					"URL should contain 1 of: mappings/regions/points");
		}		
					
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		response.addHeader("Access-Control-Max-Age", "86400");
		response.addHeader("Content-Type", "application/json");
		response.setStatus(200);
		PrintWriter out = response.getWriter();		
		ResultOutputFormat result = new ResultOutputFormat("success", request.getRequestURL().toString(), element);
		out.println(gson.toJson(result));		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}	

}

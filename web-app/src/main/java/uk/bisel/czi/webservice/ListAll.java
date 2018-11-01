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
import uk.bisel.czi.model.Image2PositionMapping;

/**
 * Servlet implementation class ListAll
 */
@WebServlet("/all")
public class ListAll extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */	
    public ListAll() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				
//		WriteToLog log = new WriteToLog();
//		log.write(request.getRemoteAddr(), request.getRequestURL().toString());				
		
		NotADao dao = new NotADao();
		Image2PositionMapping[] allMappings = dao.getAllImageMappings();
		Gson gson = new GsonBuilder().create();
		JsonElement element = gson.toJsonTree(allMappings);				
		
		
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

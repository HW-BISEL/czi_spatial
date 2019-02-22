package uk.bisel.czi.webservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uk.bisel.czi.data.NotADao;
import uk.bisel.czi.model.Species;
import uk.bisel.czi.webservice.exceptions.PathException;

/**
 * Servlet implementation class MappingService
 */
@WebServlet("/mapping/*")
public class MappingService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MappingService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String unprocessedPath = request.getPathInfo();
		String[] pathElements = unprocessedPath.split("/");
		if (pathElements.length != 3) {
			throw new PathException("URL should species1 and species2 and a point in species1; eg, /mapping/mouse/human?point=15");
		}

		Gson gson = new GsonBuilder().create();
		JsonElement element = null;
		NotADao dao = new NotADao();

		ArrayList<String> allSpecies = new ArrayList<>();
		for(Species species : Species.values()) {
			allSpecies.add(species.toString());
		}
		
		short species2Point = -1;
		
		if (allSpecies.contains(pathElements[1].toUpperCase()) && allSpecies.contains(pathElements[2].toUpperCase())) {
			Species species1 = null;			
			species1 = Species.valueOf(pathElements[1].toUpperCase());			
			if(species1 == null) throw new PathException("A SPECIES must be provided; eg, /mapping/mouse/human?point=15");	
		
			Species species2 = null;			
			species2 = Species.valueOf(pathElements[2].toUpperCase());			
			if(species2 == null) throw new PathException("A 2nd SPECIES must be provided; eg, /mapping/mouse/human?point=15");	
			
			String p1 = request.getParameter("point");
			if(p1 == null) throw new PathException("A point must be provided; eg, /mapping/mouse/human?point=15");
			
			species2Point = dao.mapping(species1, (short) Integer.parseInt(p1), species2);
			
			JsonObject mapping = new JsonObject();
			mapping.addProperty("species1", species1.toString());
			mapping.addProperty("position1", (short) Integer.parseInt(p1));
			mapping.addProperty("species2", species2.toString());
			mapping.addProperty("position2", species2Point);			
			element = gson.toJsonTree(mapping);
			
			
		} else {
			throw new PathException("Not a valid species; eg, /mapping/mouse/human?point=15");
		}
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		response.addHeader("Access-Control-Max-Age", "86400");
		response.addHeader("Content-Type", "application/json");
		ResultOutputFormat result = new ResultOutputFormat("success", request.getRequestURL()+"?"+request.getQueryString(), element);
		PrintWriter out = response.getWriter();		
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

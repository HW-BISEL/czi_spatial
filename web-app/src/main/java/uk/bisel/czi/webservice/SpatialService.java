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

import uk.bisel.czi.data.NotADao;
import uk.bisel.czi.model.Image2PositionMapping;
import uk.bisel.czi.model.Species;
import uk.bisel.czi.webservice.exceptions.PathException;


/**
 * Servlet implementation class SearchByPosition
 */
@WebServlet("/query/*")
public class SpatialService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SpatialService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		String unprocessedPath = request.getPathInfo();
		String[] pathElements = unprocessedPath.split("/");
		if (pathElements.length < 3 || pathElements.length > 4) {
			throw new PathException("URL should contain type of query and image or position(s) to be searched for.");
		}

		Gson gson = new GsonBuilder().create();
		JsonElement element = null;
		NotADao dao = new NotADao();

		ArrayList<String> allSpecies = new ArrayList<>();
		for(Species species : Species.values()) {
			allSpecies.add(species.toString());
		}
				
		if (allSpecies.contains(pathElements[1].toUpperCase())) {
			Species species = null;
			switch (pathElements[1].toUpperCase()) {
				case "HUMAN":
					species = Species.HUMAN;
					break;
				case "MOUSE":
					species = Species.MOUSE;
					break;
				case "RAT":
					species = Species.RAT;
					break;
			}
			
			if(species == null) throw new PathException("A SPECIES must be provided; eg, /query/mouse/pointQuery?point=13");
				
			if(pathElements[2].equalsIgnoreCase("searchByPosition")) {
				
				String p1 = request.getParameter("point");
				if(p1 == null) throw new PathException("A point must be provided; eg, /query/mouse/searchByPosition?point=13");
				Image2PositionMapping[] results = dao.getImagesAtPosition((short) Integer.parseInt(p1), species);
				element = gson.toJsonTree(results);				
				
			} else if(pathElements[2].equalsIgnoreCase("searchByRange")) {
				
				String p1 = request.getParameter("point1");
				if(p1 == null) throw new PathException("2 points must be provided; eg, query/mouse/searchByRange?point1=13&point2=20");	
				String p2 = request.getParameter("point2");
				if(p2 == null) throw new PathException("A 2nd point must be provided;eg, query/mouse/searchByRange?point1=13&point2=20");
				Image2PositionMapping[] results = dao.getImagesFromRange((short) Integer.parseInt(p1), (short) Integer.parseInt(p2), species);
				element = gson.toJsonTree(results);
				
			} else if(pathElements[2].equalsIgnoreCase("searchByComponent")) {
				
				String c = request.getParameter("component").trim();
				if(c == null) throw new PathException("Must specify a component; eg, query/mouse/searchByComponent?component=anal canal");
				Image2PositionMapping[] results = dao.getImagesFromRegion(species, c);
				element = gson.toJsonTree(results);
				
			} else {
				throw new PathException("Invalid query type specified must be ONE of searchByPosition, searchByRange OR searchByComponent");
			}
		} else {
			throw new PathException("Not a valid species; eg, /query/mouse/searchByPosition?point=13");
		}
		
//		if (pathElements[1].equalsIgnoreCase("searchbyposition")) {
//			if (pathElements.length == 2)
//				throw new PathException("No position provided; should be /searchByPosition/position.");
//			if (pathElements.length > 3)
//				throw new PathException(
//						"Only 1 position should be provided (eg, /searchByPosition/position). Try searchByRange.");
//
//			Image2PositionMapping[] results = dao.getImagesAtPosition((short) Integer.parseInt(pathElements[2]));
//			element = gson.toJsonTree(results);
//
//		} else if (pathElements[1].equalsIgnoreCase("searchbyimage")) {
//			if (pathElements.length == 2)
//				throw new PathException("No imageId provided; should be /searchByImageId/imageId.");
//			if (pathElements.length > 3)
//				throw new PathException("Only 1 imageId should be provided (eg, /searchByImageId/imageId).");
//
//			PositionResult results = new PositionResult(dao.getPositionsFromImage(pathElements[2]));
//			element = gson.toJsonTree(results);
//
//		} else if (pathElements[1].equalsIgnoreCase("searchByRange")) {
//			if (pathElements.length != 4) {
//				throw new PathException(
//						"Exactly 2 positions should be provided (eg, /searchByRange/startPosition/endPosition).");
//			}
//
//			Image2PositionMapping[] results = dao.getImagesFromRange((short) Integer.parseInt(pathElements[2]),
//					(short) Integer.parseInt(pathElements[3]));
//			element = gson.toJsonTree(results);
//
//		} else if (pathElements[1].equalsIgnoreCase("searchByComponent")) {
//			if (pathElements.length == 2)
//				throw new PathException("No position provided; should be /searchByComponent/component.");
//			if (pathElements.length > 3)
//				throw new PathException("Only 1 component should be provided (eg, /searchByComponent/sigmoid).");
//
//			element = gson.toJsonTree(dao.getImagesFromRegion(pathElements[2]));
//		} else {
//			throw new PathException(
//					"URL should contain type (searchByPosition/searchByRange/searchByImage/searchByComponent) of query in second position.");
//		}

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		response.addHeader("Access-Control-Max-Age", "86400");
		response.addHeader("Content-Type", "application/json");
		ResultOutputFormat result = new ResultOutputFormat("success", request.getRequestURL()+"?"+request.getQueryString(), element);
		PrintWriter out = response.getWriter();
		out.println(gson.toJson(result));
//		for(String pathElement : pathElements) {
//			out.println(pathElement);
//		}
//		String value = request.getParameter("point");
//		out.println(value);
//		out.println(output);
		
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public class PositionResult {
		private short[] positions;

		public PositionResult(short[] positions) {
			super();
			this.positions = positions;
		}

		public short[] getPositions() {
			return positions;
		}
	}

}

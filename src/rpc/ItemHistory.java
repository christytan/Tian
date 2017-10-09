package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;


/**
 * Servlet implementation class ItemHistory
 */
@WebServlet("/history")

// to handle the request that associates a user to an event

public class ItemHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBConnection conn = DBConnectionFactory.getDBConnection();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemHistory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//return the favorite selection that user favorite
		String userId = request.getParameter("user_id");
		//get favorite item from the db
		Set<Item> items = conn.getFavoriteItems(userId);
		//set jsonarray
		JSONArray array = new JSONArray();
		//store item to jsonobj
		for (Item item : items) {
			JSONObject obj = item.toJSONObject();//change the item to the jsonobj
			try {
				obj.append("favorite", true);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			array.put(obj);//add obj into the jsonarray
		}
		//output
		RpcHelper.writeJsonArray(response, array);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
				JSONObject input = RpcHelper.readJsonObject(request);
				String userId = input.getString("user_id");
				JSONArray array = (JSONArray) input.get("favorite");

				List<String> histories = new ArrayList<>();
				for (int i = 0; i < array.length(); i++) {
					String itemId = (String) array.get(i);
					histories.add(itemId);
				}
				conn.setFavoriteItems(userId, histories);
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
			} catch (JSONException e) {
				e.printStackTrace();
			}

	}
	//help unset the favorite items in the db.
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
				JSONObject input = RpcHelper.readJsonObject(request);
				String userId = input.getString("user_id");
				JSONArray array = (JSONArray) input.get("favorite");
				List<String> histories = new ArrayList<>();
				for (int i = 0; i < array.length(); i++) {
					String itemId = (String) array.get(i);
					histories.add(itemId);
				}
				conn.unsetFavoriteItems(userId, histories);
				RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}


}

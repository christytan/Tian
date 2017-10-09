package rpc;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class RpcHelper {
	// get a JSONObject from http request. (convert string type request to {key:value} pair = jsonobj)
		public static JSONObject readJsonObject(HttpServletRequest request) {
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				reader.close();
				return new JSONObject(sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// output JSONObject.
		public static void writeJsonObject(HttpServletResponse response, JSONObject obj) {
			try {
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(obj);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//output JSONArray.
		public static void writeJsonArray(HttpServletResponse response, JSONArray array) {
			try {
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(array);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}

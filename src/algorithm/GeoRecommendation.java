package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;

public class GeoRecommendation implements Recommendation {

	@Override
	public List<Item> recommendItems(String userId, double lat, double lon) {
		// TODO Auto-generated method stub
		DBConnection conn = DBConnectionFactory.getDBConnection();
		Set<String> favoriteItems = conn.getFavoriteItemIds(userId);//get userid -> itemids
		
		Set<String> allCategories = new HashSet<>();
		for (String item : favoriteItems) {
			allCategories.addAll(conn.getCategories(item));//itemid -> category of items
		}
		/* get all the item result based on userid favorite category */
		Set<Item> recommendedItems = new HashSet<>();
		for (String category : allCategories) {
			List<Item> items = conn.searchItems(userId, lat, lon, category);
			recommendedItems.addAll(items);
		}
		allCategories.remove("Undefined");
		if (allCategories.isEmpty()) {
			allCategories.add("");
		}
		
		/* filter the repeated items that already in users' favorite list */
		List<Item> filteredItems = new ArrayList<>();
		for (Item item : recommendedItems) {
			if (!favoriteItems.contains(item.getItemId())) {
				filteredItems.add(item);
			}
		}
		/* rank the recommendation based on event geo-location s*/
		Collections.sort(filteredItems, new Comparator<Item>() {

			@Override
			public int compare(Item item1, Item item2) {
				// TODO Auto-generated method stub
				double distance1 = getDistance(item1.getLatitude(), item1.getLongitude(), lat, lon);
				double distance2 = getDistance(item2.getLatitude(), item2.getLongitude(), lat, lon);
				return (int) (distance1 - distance2);
			}
			
		});
		return filteredItems;
	}
	
	private static double getDistance(double lat1, double lon1, double lat2, double lon2) {
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.sin(dlat / 2 / 180 * Math.PI) * Math.sin(dlat / 2 / 180 * Math.PI)
				+ Math.cos(lat1 / 180 * Math.PI) * Math.cos(lat2 / 180 * Math.PI)
				* Math.sin(dlon / 2 / 180 * Math.PI) * Math.sin(dlon / 2 / 180 * Math.PI);
		 double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    // Radius of earth in miles.
		    double R = 3961;
		    return R * c;
 
		
	}
	
	
}

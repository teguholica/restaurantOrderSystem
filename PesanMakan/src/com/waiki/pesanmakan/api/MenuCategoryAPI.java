package com.waiki.pesanmakan.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.waiki.pesanmakan.api.obj.MenuCategories;

public class MenuCategoryAPI extends HttpConnect {
	
	private String TAG = "PesanMakan";
	
	private String SERVER = "http://192.168.137.1/pesanmakan/api/";
	private String FORMAT = "/format/json";
	
	private String GET_ALL = SERVER+"menu_category/get_all"+FORMAT;
	
	public ArrayList<MenuCategories> getAll(){
		ArrayList<MenuCategories> result = new ArrayList<MenuCategories>();
		try {
			Log.d(TAG, "execute API : "+GET_ALL);
			String response = httpGet(GET_ALL);
			Log.d(TAG, "response API : "+response);
			JSONArray responseArr = new JSONArray(response);
			Log.d(TAG, "menu category total : "+responseArr.length());
			for(int i = 0;i < responseArr.length();i++){
				JSONObject obj = responseArr.getJSONObject(i);
				Log.d(TAG, obj.getString("name"));
				result.add(new MenuCategories(obj.getString("name")));
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
		return result;
	}

}

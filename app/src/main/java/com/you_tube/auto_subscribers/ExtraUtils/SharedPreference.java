package com.you_tube.auto_subscribers.ExtraUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

	public static final String PREFS_NAME = "PRODUCT_APP";
	public static final String FAVORITES = "Product_Favorite";

	public SharedPreference() {
		super();
	}

	// This four methods are used for maintaining favorites.
	public void saveFavorites(Context context, List<UploadData> favorites) {
		SharedPreferences settings;
		Editor editor;
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = settings.edit();
		Gson gson = new Gson();
		String jsonFavorites = gson.toJson(favorites);
		editor.putString(FAVORITES, jsonFavorites);
		//editor.commit();
		editor.apply();
	}

	public void addFavorite(Context context, UploadData product) {
		List<UploadData> favorites = getFavorites(context);
		if (favorites == null)
			favorites = new ArrayList<>();
		favorites.add(product);
		saveFavorites(context, favorites);
	}

	public void removeFavorite(Context context, UploadData product) {
		List<UploadData> favorites = getFavorites(context);
		if (favorites != null) {
			for (UploadData product1 : favorites) {
				if (product1.getId().equals(product.getId()))
				{
					favorites.remove(product1);
					saveFavorites(context, favorites);
					break;
				}

			}
		}


//		if (favorites != null) {
//			//favoritesa = new ArrayList<>();
//			favorites.remove(product);
//			Toast.makeText(context, "xxxx" + favorites, Toast.LENGTH_LONG).show();
//			saveFavorites(context, favorites);
//		}

	}


	public ArrayList<UploadData> getFavorites(Context context) {
		SharedPreferences settings;
		List<UploadData> favorites;

		settings = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);

		if (settings.contains(FAVORITES)) {
			String jsonFavorites = settings.getString(FAVORITES, null);
			Gson gson = new Gson();
			UploadData[] favoriteItems = gson.fromJson(jsonFavorites,
					UploadData[].class);

			favorites = Arrays.asList(favoriteItems);
			favorites = new ArrayList<>(favorites);
		} else
			return null;

		return (ArrayList<UploadData>) favorites;
	}


}
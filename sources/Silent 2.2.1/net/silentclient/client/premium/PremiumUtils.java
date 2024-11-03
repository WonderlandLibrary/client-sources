package net.silentclient.client.premium;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.silentclient.client.utils.Requests;
import net.silentclient.client.utils.types.PremiumCosmeticsResponse;

public class PremiumUtils {
	public static PremiumCosmeticsResponse getPremiumCosmetics() {
		try {
			String content = Requests.get("https://api.silentclient.net/premium_cosmetics");
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			PremiumCosmeticsResponse response = gson.fromJson(content, PremiumCosmeticsResponse.class);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

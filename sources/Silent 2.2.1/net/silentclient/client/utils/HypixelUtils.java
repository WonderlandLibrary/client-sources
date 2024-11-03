package net.silentclient.client.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.EnumChatFormatting;
import net.silentclient.client.Client;
import net.silentclient.client.utils.types.HypixelProxyResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HypixelUtils {
	private static final Map<UUID, String> levelCache = new HashMap<>();
	public static boolean isLoading = false;
	
	public static String getHypixelLevel(boolean isMainPlayer, String name, UUID id) {
		if((name.contains(EnumChatFormatting.OBFUSCATED.toString()) && !isMainPlayer)) {
			return null;
		}
		
		if(levelCache.containsKey(id)) {
			String result = levelCache.get(id);
			if(result.isEmpty() || result.equals("null")) {
				return null;
			}
			
			return result;
		} else if(!isLoading) {
			isLoading = true;
			(new Thread() {
				public void run() {
					try {
						String content = Requests.get("https://hypixel.silentclient.net/uuid/" + id.toString() + ".json");
						GsonBuilder builder = new GsonBuilder();
						Gson gson = builder.create();

						HypixelProxyResponse response = gson.fromJson(content, HypixelProxyResponse.class);
						
						if(response != null && !response.error) {
							levelCache.put(id, Integer.toString(response.level));
						} else {
							levelCache.put(id, "null");
						}
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							Client.logger.catching(e);
						}
						HypixelUtils.isLoading = false;
					} catch (Exception e) {
						Client.logger.catching(e);
						levelCache.put(id, "null");
					}
				}
			}).start();
		}
		
		return null;
	}
}

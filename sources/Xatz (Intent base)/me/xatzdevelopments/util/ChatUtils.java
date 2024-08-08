package me.xatzdevelopments.util;

import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

public class ChatUtils {

	 public static void sendMessage(final String message) {
	        final JsonObject jsonObject = new JsonObject();
	        jsonObject.addProperty("text", message);
	        Minecraft.getMinecraft().thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent(jsonObject.toString()));
	    }
	
}

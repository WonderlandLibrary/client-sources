package com.kilo.users;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

import com.google.common.base.Charsets;
import com.kilo.manager.DatabaseManager;
import com.mojang.authlib.GameProfile;

public class UserHandler {

	public static Map<UUID, Player> players = new LinkedHashMap<UUID, Player>();
	
	public static void add(GameProfile gameProfile, boolean hasHead) {
		players.put(gameProfile.getId(), new Player(gameProfile, hasHead));
	}
	
	public static void remove(GameProfile gameProfile) {
		if (players.containsKey(gameProfile.getId())) {
			players.remove(gameProfile.getId());
		}
	}
	
	public static Player getFromUsername(String s) {
		Player u = null;
		for(Player user : players.values()) {
			if (user.gameProfile.getName().equalsIgnoreCase(s)) {
				u = user;
				break;
			}
		}
		return u;
	}
}

package com.kilo.users;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import net.minecraft.util.ResourceLocation;

import com.kilo.manager.DatabaseManager;
import com.kilo.render.TextureImage;
import com.kilo.util.Resources;
import com.mojang.authlib.GameProfile;

public class Player {

	public UUID uuid;
	public GameProfile gameProfile;
	public TextureImage head;
	
	public Player(GameProfile gameProfile, boolean hasHead) {
		this.gameProfile = gameProfile;
		this.uuid = gameProfile.getId();

		if (hasHead) {
			this.head = Resources.downloadTexture(String.format(DatabaseManager.head, gameProfile.getName(), "128"));
		}
	}
}

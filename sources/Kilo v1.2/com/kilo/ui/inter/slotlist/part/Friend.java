package com.kilo.ui.inter.slotlist.part;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.kilo.manager.DatabaseManager;
import com.kilo.render.TextureImage;
import com.kilo.util.Resources;

public class Friend {

	public String kiloname, mcname, status, ip;
	public TextureImage head;
	
	public Friend(String kn, String n) {
		this(kn, n, "Offline", "");
	}
	
	public Friend(String kn, String n, String s, String i) {
		kiloname = kn;
		mcname = n;
		status = s;
		ip = i;

		head = Resources.downloadTexture(String.format(DatabaseManager.head, n, "128"));
	}
}

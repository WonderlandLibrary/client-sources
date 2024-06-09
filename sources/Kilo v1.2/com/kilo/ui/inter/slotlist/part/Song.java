package com.kilo.ui.inter.slotlist.part;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.kilo.manager.DatabaseManager;
import com.kilo.render.TextureImage;
import com.kilo.util.Resources;

public class Song {

	public int id, duration;
	public String title, artwork;
	public TextureImage image;
	public boolean starred;
	
	public Song(int i, String t, String a, int d, boolean s) {
		id = i;
		title = t;
		artwork = a;
		duration = d;
		starred = s;
		
		if (a != null) {
			image = Resources.downloadTexture(a);
		}
	}
}
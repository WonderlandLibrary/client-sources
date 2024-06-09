package com.kilo.render;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureImage {

	public byte[] pixels;
	public Texture texture;
	public String location;
	
	public Texture getTexture() {
    	if (texture == null) {
    		if (pixels != null) {
    			try {
    				ByteArrayInputStream bias = new ByteArrayInputStream(pixels);
    				texture = TextureLoader.getTexture("PNG", bias);
    			} catch (Exception e) {
    			}
    		}
    	}
		return texture;
	}
}

package com.kilo.render;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.TrueTypeFont;

public class Fonts {
	
	private static final String standard = "roboto";
	private static final String rounded = "comfortaa";

	public static final TrueTypeFont ttfStandard12 = loadTTF(standard+"-bold.ttf", Font.PLAIN, 12);
	public static final TrueTypeFont ttfStandard14 = loadTTF(standard+"-regular.ttf", Font.PLAIN, 14);
	public static final TrueTypeFont ttfStandard20 = loadTTF(standard+"-regular.ttf", Font.PLAIN, 20);
	public static final TrueTypeFont ttfStandard25 = loadTTF(standard+"-regular.ttf", Font.PLAIN, 25);

	public static final TrueTypeFont ttfRoundedBold10 = loadTTF(rounded+"-bold.ttf", Font.PLAIN, 10);
	public static final TrueTypeFont ttfRoundedBold12 = loadTTF(rounded+"-bold.ttf", Font.PLAIN, 12);
	public static final TrueTypeFont ttfRoundedBold14 = loadTTF(rounded+"-bold.ttf", Font.PLAIN, 14);
	public static final TrueTypeFont ttfRoundedBold16 = loadTTF(rounded+"-bold.ttf", Font.PLAIN, 16);
	public static final TrueTypeFont ttfRoundedBold20 = loadTTF(rounded+"-bold.ttf", Font.PLAIN, 20);
	public static final TrueTypeFont ttfRoundedBold25 = loadTTF(rounded+"-bold.ttf", Font.PLAIN, 25);
	public static final TrueTypeFont ttfRoundedBold40 = loadTTF(rounded+"-bold.ttf", Font.PLAIN, 40);
	public static final TrueTypeFont ttfRoundedBold50 = loadTTF(rounded+"-bold.ttf", Font.PLAIN, 50);
	
	public static final TrueTypeFont ttfRounded10 = loadTTF(rounded+"-regular.ttf", Font.PLAIN, 10);
	public static final TrueTypeFont ttfRounded12 = loadTTF(rounded+"-regular.ttf", Font.PLAIN, 12);
	public static final TrueTypeFont ttfRounded14 = loadTTF(rounded+"-regular.ttf", Font.PLAIN, 14);
	public static final TrueTypeFont ttfRounded16 = loadTTF(rounded+"-regular.ttf", Font.PLAIN, 16);
	public static final TrueTypeFont ttfRounded20 = loadTTF(rounded+"-regular.ttf", Font.PLAIN, 20);
	public static final TrueTypeFont ttfRounded25 = loadTTF(rounded+"-regular.ttf", Font.PLAIN, 25);
	public static final TrueTypeFont ttfRounded40 = loadTTF(rounded+"-regular.ttf", Font.PLAIN, 40);
	public static final TrueTypeFont ttfRounded50 = loadTTF(rounded+"-regular.ttf", Font.PLAIN, 50);

	public static TrueTypeFont loadTTF(String path, int style, float size) {
		path = "/assets/kilo/fonts/"+path;
		try {
			InputStream inputStream	= Fonts.class.getResourceAsStream(path);
	
			Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			font = font.deriveFont(style, size);
			return new TrueTypeFont(font, true);
	 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new TrueTypeFont(new Font("Tahoma", Font.PLAIN, (int)size), true);
	}
}

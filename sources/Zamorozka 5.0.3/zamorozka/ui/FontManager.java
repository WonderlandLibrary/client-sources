package zamorozka.ui;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontManager {

	public MinecraftFontRenderer hud = null;

	public MinecraftFontRenderer hud1 = null;

	public MinecraftFontRenderer arraylist = null;

	public MinecraftFontRenderer mainMenu = null;

	public MinecraftFontRenderer chat = null;

	public MinecraftFontRenderer chat1 = null;

	public MinecraftFontRenderer arraylist2 = null;

	public MinecraftFontRenderer arraylist3 = null;

	public MinecraftFontRenderer arraylist4 = null;

	public MinecraftFontRenderer arraylist5 = null;

	public MinecraftFontRenderer arraylist6 = null;

	public MinecraftFontRenderer Quicksand = null;

	public MinecraftFontRenderer QuicksandLight = null;
	
	public MinecraftFontRenderer QuicksandLight1 = null;

	private static String fontName = "Impact";

	public MinecraftFontRenderer Tahoma = null;

	public MinecraftFontRenderer Tahoma1 = null;

	public MinecraftFontRenderer Tahoma2 = null;

	public void loadFonts() throws FontFormatException, IOException {
		File font_file1 = new File("Logos.ttf");
		Font font2 = Font.createFont(Font.PLAIN, font_file1);
		Font f = font2.deriveFont(40f);

		hud = new MinecraftFontRenderer(loadFonts2(), true, true);
		arraylist = new MinecraftFontRenderer(loadFonts3(), true, true);
		Tahoma = new MinecraftFontRenderer(new Font("Tahoma", Font.PLAIN, 35), true, true);
		Tahoma1 = new MinecraftFontRenderer(new Font("Tahoma", Font.PLAIN, 25), true, true);
		Tahoma2 = new MinecraftFontRenderer(new Font("Tahoma", Font.PLAIN, 15), true, true);
		mainMenu = new MinecraftFontRenderer(new Font(fontName, Font.PLAIN, 50), true, true);
		chat = new MinecraftFontRenderer(new Font("Verdana", Font.PLAIN, 17), true, true);
		chat1 = new MinecraftFontRenderer(new Font("Verdana", Font.PLAIN, 20), true, true);
		chat1 = new MinecraftFontRenderer(new Font("Verdana", Font.PLAIN, 20), true, true);
		Quicksand = new MinecraftFontRenderer(loadFonts9(), true, true);
		QuicksandLight = new MinecraftFontRenderer(loadFonts8(), true, true);
		QuicksandLight1 = new MinecraftFontRenderer(loadFontsLight(), true, true);
		arraylist2 = new MinecraftFontRenderer(new Font("Verdana", Font.PLAIN, 20), true, true);
		arraylist3 = new MinecraftFontRenderer(loadFonts4(), true, true);
		arraylist4 = new MinecraftFontRenderer(loadFonts5(), true, true);
		arraylist5 = new MinecraftFontRenderer(loadFonts6(), true, true);
		arraylist6 = new MinecraftFontRenderer(loadFonts7(), true, true);

	}

	public Font loadFonts1() throws FontFormatException, IOException {

		File font_file11 = new File("Fonts.ttf");
		Font font23 = Font.createFont(Font.PLAIN, font_file11);
		Font f12 = font23.deriveFont(20f);
		return f12;
	}

	public Font loadFonts2() throws FontFormatException, IOException {
		File font_file1 = new File("Logos.ttf");
		Font font2 = Font.createFont(Font.PLAIN, font_file1);
		Font f = font2.deriveFont(30f);
		return f;
	}

	public Font loadFonts3() throws FontFormatException, IOException {
		File font_Bebas = new File("Bebas Neue.ttf");
		Font Bebas = Font.createFont(Font.PLAIN, font_Bebas);
		Font Bebas1 = Bebas.deriveFont(20f);
		return Bebas1;
	}

	public Font loadFonts4() throws FontFormatException, IOException {
		File font_Bebas = new File("Bebas Neue.ttf");
		Font Bebas = Font.createFont(Font.PLAIN, font_Bebas);
		Font Bebas1 = Bebas.deriveFont(50f);
		return Bebas1;
	}

	public Font loadFonts5() throws FontFormatException, IOException {
		File font_Bebas = new File("Bebas Neue.ttf");
		Font Bebas = Font.createFont(Font.PLAIN, font_Bebas);
		Font Bebas1 = Bebas.deriveFont(30f);
		return Bebas1;
	}

	public Font loadFonts6() throws FontFormatException, IOException {
		File font_Bebas = new File("Bebas Neue.ttf");
		Font Bebas = Font.createFont(Font.PLAIN, font_Bebas);
		Font Bebas1 = Bebas.deriveFont(25f);
		return Bebas1;
	}

	public Font loadFonts7() throws FontFormatException, IOException {
		File font_Bebas = new File("Bebas Neue.ttf");
		Font Bebas = Font.createFont(Font.PLAIN, font_Bebas);
		Font Bebas1 = Bebas.deriveFont(23f);
		return Bebas1;
	}

	public Font loadFonts8() throws FontFormatException, IOException {
		File font_Bebas = new File("Quicksand-Light.ttf");
		Font Bebas = Font.createFont(Font.PLAIN, font_Bebas);
		Font Bebas1 = Bebas.deriveFont(36f);
		return Bebas1;
	}
	
	public Font loadFontsLight() throws FontFormatException, IOException {
		File font_Bebas = new File("Quicksand-Light.ttf");
		Font Bebas = Font.createFont(Font.PLAIN, font_Bebas);
		Font Bebas1 = Bebas.deriveFont(30f);
		return Bebas1;
	}

	public Font loadFonts9() throws FontFormatException, IOException {
		File font_Bebas = new File("Quicksand-Bold.ttf");
		Font Bebas = Font.createFont(Font.PLAIN, font_Bebas);
		Font Bebas1 = Bebas.deriveFont(24f);
		return Bebas1;
	}

	public static String getFontName() {

		return fontName;
	}

	public static void setFontName(String fontName) {

		FontManager.fontName = fontName;
	}

}

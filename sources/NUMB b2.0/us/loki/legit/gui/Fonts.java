package us.loki.legit.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import com.mojang.realmsclient.dto.RealmsServer.McoServerComparator;

import net.minecraft.client.Minecraft;

public class Fonts {

	public static UnicodeFontRenderer smallKrypton;
	public static UnicodeFontRenderer menuKrypton;
	public static UnicodeFontRenderer arrayKrypton;
	public static UnicodeFontRenderer hotbarKrypton;
	public static UnicodeFontRenderer clFont;

	public static void loadFonts() {

		InputStream is = Fonts.class.getResourceAsStream("fonts/Comfortaa.ttf");
		InputStream is1 = Fonts.class.getResourceAsStream("fonts/timeburnerbold.ttf");

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		Font font1 = null;
		try {
			font1 = Font.createFont(Font.TRUETYPE_FONT, is1);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		smallKrypton = new UnicodeFontRenderer(font.deriveFont(20F));
		menuKrypton = new UnicodeFontRenderer(font.deriveFont(150F));
		arrayKrypton = new UnicodeFontRenderer(font.deriveFont(40F));
		hotbarKrypton = new UnicodeFontRenderer(font.deriveFont(60F));

		clFont = new UnicodeFontRenderer(font.deriveFont(20f));

		if (Minecraft.getMinecraft().gameSettings.language != null) {
			Fonts.menuKrypton.setUnicodeFlag(true);
			Fonts.smallKrypton.setUnicodeFlag(true);
			Fonts.arrayKrypton.setUnicodeFlag(true);
			Fonts.hotbarKrypton.setUnicodeFlag(true);

			Fonts.smallKrypton.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
			Fonts.menuKrypton.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
			Fonts.arrayKrypton.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
			Fonts.hotbarKrypton
					.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());

			Fonts.clFont.setUnicodeFlag(true);
			Fonts.clFont.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
		}
	}

	public static enum FontType {
		EMBOSS_BOTTOM, EMBOSS_TOP, NORMAL, OUTLINE_THIN, SHADOW_THICK, SHADOW_THIN;
	}
}

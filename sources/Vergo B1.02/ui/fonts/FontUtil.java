package xyz.vergoclient.ui.fonts;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import xyz.vergoclient.ui.guis.GuiAltManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class FontUtil {
	
	/*public static JelloFontRenderer getTextFieldFont(boolean password){
		return password ? jelloFontGui : mc.currentScreen instanceof GuiAltManager ? jelloFontGui : jelloFontAddAlt;
	}*/
	
	private static Font getFont(float size, boolean bold, ResourceLocation fontFile) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(fontFile).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}
	
	private static Font getMinecraftFont(float size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Vergo/font/MinecraftRegular-Bmg3.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}
	
	private static Font getArial(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Vergo/font/arial.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}

	private static Font getBakbak(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Vergo/font/BakbakOne-Regular.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}

	private static Font getJura(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Vergo/font/jura.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}

	private static Font getComfortaa(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Vergo/font/Comfortaa-Medium.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}

	private static Font getNeurialGrotesk(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Vergo/font/NeurialGrotesk-Light.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}

	private static Font getTahoma(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Vergo/font/Tahoma-Regular-font.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}

	private static Font getUbuntu(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Vergo/font/Ubuntu-Light.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}

	private static Font getBoldUbuntu(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Vergo/font/Ubuntu-Bold.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}

	private static Font getKanit(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Vergo/font/Kanit-Light.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}

	public static Minecraft mc = Minecraft.getMinecraft();
	public static FontRenderer fr = mc.fontRendererObj;
	/*public static JelloFontRenderer jelloFont = JelloFontRenderer.createFontRenderer(getJelloFont(20, false));
	public static JelloFontRenderer jelloFontScale = JelloFontRenderer.createFontRenderer(getJelloFont(24, false));
	public static JelloFontRenderer jelloFontAddAlt = JelloFontRenderer.createFontRenderer(getJelloFont(24, false));
	public static JelloFontRenderer jelloFontGui = JelloFontRenderer.createFontRenderer(getJelloFont(25, false));
	public static JelloFontRenderer jelloFontGuiBigger = JelloFontRenderer.createFontRenderer(getJelloFont(32, false));
	public static JelloFontRenderer jelloFontDuration = JelloFontRenderer.createFontRenderer(getJelloFont(13, false));
	public static JelloFontRenderer jelloFontMusic = JelloFontRenderer.createFontRenderer(getJelloFont((float) (12f), false));
	public static JelloFontRenderer jelloFontAddAlt2 = JelloFontRenderer.createFontRenderer(getJelloFont(35, false));
	public static JelloFontRenderer jelloFontAddAlt3 = JelloFontRenderer.createFontRenderer(getJelloFont(19, false));
	public static JelloFontRenderer jelloFontRegular = JelloFontRenderer.createFontRenderer(getJelloFontRegular(20));
	public static JelloFontRenderer jelloFontRegularBig = JelloFontRenderer.createFontRenderer(getJelloFontRegular(24));
	public static JelloFontRenderer jelloFontBoldSmall = JelloFontRenderer.createFontRenderer(getJelloFont(19, true));
	public static JelloFontRenderer jelloFontMarker = JelloFontRenderer.createFontRenderer(getJelloFont(19, false));
	public static JelloFontRenderer jelloFontSmall = JelloFontRenderer.createFontRenderer(getJelloFont(14, false));
	public static JelloFontRenderer jelloFontSmallerest = JelloFontRenderer.createFontRenderer(getJelloFont(6, false));
	public static JelloFontRenderer jelloFontSmallPassword = JelloFontRenderer.createFontRenderer(getJelloFont(16, false));
	public static JelloFontRenderer jelloFontBig = JelloFontRenderer.createFontRenderer(getJelloFont(41, true));
	public static JelloFontRenderer jelloFontMedium = JelloFontRenderer.createFontRenderer(getJelloFont(25, false));
	public static JelloFontRenderer font = JelloFontRenderer.createFontRenderer(getJelloFontRegular(18));
	public static JelloFontRenderer fontBig = JelloFontRenderer.createFontRenderer(getJelloFontRegular(33));
	public static JelloFontRenderer fontSmall = JelloFontRenderer.createFontRenderer(getJelloFontRegular(14));
	
	public static JelloFontRenderer spicyClickGuiFont = JelloFontRenderer.createFontRenderer(getJelloFont(23, false));*/
	public static JelloFontRenderer minecraftFont = JelloFontRenderer.createFontRenderer(getMinecraftFont(18));
	
	public static JelloFontRenderer arialBigger = JelloFontRenderer.createFontRenderer(getArial(41));
	public static JelloFontRenderer arialBig = JelloFontRenderer.createFontRenderer(getArial(24));
	public static JelloFontRenderer arialSlightlyLargerThanRegular = JelloFontRenderer.createFontRenderer(getArial(19));
	public static JelloFontRenderer arialRegular = JelloFontRenderer.createFontRenderer(getArial(17));
	public static JelloFontRenderer arialMedium = JelloFontRenderer.createFontRenderer(getArial(14));
	public static JelloFontRenderer arialSmall = JelloFontRenderer.createFontRenderer(getArial(10));

	public static JelloFontRenderer bakakakBigger = JelloFontRenderer.createFontRenderer(getBakbak(41));
	public static JelloFontRenderer bakakakmedium = JelloFontRenderer.createFontRenderer(getBakbak(12));
	public static JelloFontRenderer bakakakBig = JelloFontRenderer.createFontRenderer(getBakbak(25));
	public static JelloFontRenderer bakakakBiggish = JelloFontRenderer.createFontRenderer(getBakbak(35));
	public static JelloFontRenderer bakakRegular = JelloFontRenderer.createFontRenderer(getBakbak(17));

	public static JelloFontRenderer juraNormal = JelloFontRenderer.createFontRenderer(getJura(18));

	public static JelloFontRenderer comfortaaHuge = JelloFontRenderer.createFontRenderer(getComfortaa(28));
	public static JelloFontRenderer comfortaaNormal = JelloFontRenderer.createFontRenderer(getComfortaa(18));
	public static JelloFontRenderer comfortaaSmall = JelloFontRenderer.createFontRenderer(getComfortaa(14));
	public static JelloFontRenderer comfortaaSmaller = JelloFontRenderer.createFontRenderer(getComfortaa(6));

	public static JelloFontRenderer neurialGrotesk = JelloFontRenderer.createFontRenderer(getNeurialGrotesk(18));
	public static JelloFontRenderer neurialGroteskBig = JelloFontRenderer.createFontRenderer(getNeurialGrotesk(25));

	public static JelloFontRenderer ubuntuNormal = JelloFontRenderer.createFontRenderer(getUbuntu(18));
	public static JelloFontRenderer ubuntuBig = JelloFontRenderer.createFontRenderer(getUbuntu(24));

	public static JelloFontRenderer ubuntuNormalBold = JelloFontRenderer.createFontRenderer(getBoldUbuntu(18));
	public static JelloFontRenderer ubuntuBigBold = JelloFontRenderer.createFontRenderer(getBoldUbuntu(24));

	public static JelloFontRenderer tahomaFont = JelloFontRenderer.createFontRenderer(getTahoma(24));

	public static JelloFontRenderer kanitNormal = JelloFontRenderer.createFontRenderer(getKanit(18));
	public static JelloFontRenderer kanitBig = JelloFontRenderer.createFontRenderer(getKanit(24));

}

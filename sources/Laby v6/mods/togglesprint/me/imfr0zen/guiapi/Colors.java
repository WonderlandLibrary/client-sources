package mods.togglesprint.me.imfr0zen.guiapi;

import java.nio.ByteOrder;

public final class Colors {

	public static final int OUTLINE_COLOR = 0xFF363636;
	public static final int BG_COLOR = 0xCC1F1F1F;
	public static final int FONT_COLOR = 0xFFADADAD;
	
	public static int buttonColor;
	public static int buttonColorLight;
	public static int buttonColorDark;

	private static final int redOffset, greenOffset, blueOffset, alphaOffset;
	
	static {
		boolean big = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
		redOffset = big ? 8 : 16;
		greenOffset = big ? 16 : 8;
		blueOffset = big ? 24 : 0;
		alphaOffset = big ? 0 : 24;
	}

	public static void setButtonColor(int r, int g, int b, int a) {
		buttonColor = getARGB(r, g, b, a);
		buttonColorDark = getARGB(r <= 25 ? r : r - 25, g <= 25 ? g : g - 25, b <= 25 ? b : b - 25, a);
		buttonColorLight = getARGB(r >= 230 ? 255 : r + 25, g >= 230 ? 255 : g + 25, b >= 230 ? 255 : b + 25, a);
	}

	public static int[] getARGB(int color) {
		int a = (color >> 24) & 0xFF;
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = (color >> 0) & 0xFF;

		return new int[] { a, r, g, b };
	}

	public static int getARGB(int r, int g, int b, int a) {
		r = (r & 0xff) << redOffset;
		g = (g & 0xff) << greenOffset;
		b = (b & 0xff) << blueOffset;
		a = (a & 0xff) << alphaOffset;

		return r | g | b | a;
	}

}

package lunadevs.luna.utils;

import java.awt.Color;
import java.util.Random;

import lunadevs.luna.main.Luna;

public class Colors {
	
	public static int colors;
	  
	  public static Color rainbow(long offset, float fade)
	  {
	    float hue = (float)(System.nanoTime() - 100L) / 1.0E10F % 1.0F;
	    long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
	    Color c = new Color((int)color);
	    return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
	  }
	  
	  public static int getColor()
	  {
	    int color = 16735581;
	    Luna ophis = new Luna();
	    Random random = new Random();
	    int module = random.nextInt(8);
	    if (module < 2) {
	      color = 13054257;
	    }
	    if ((module < 4) && (module > 2)) {
	      color = 15152951;
	    }
	    if ((module < 8) && (module > 4)) {
	      color = 16021624;
	    }
	    return color;
	  }
	

	/**
	 * Dank faith
	 */
	
	/*
	 * For Minecraft, Color class coded by Jay/FaithStyle, fucking easy For
	 * anyone who needs it.
	 */
	

	public static String DARK_RED = "§4";
	public static String NORMAL_RED = "§c";
	public static String GOLD = "§6";
	public static String YELLOW = "§e";
	public static String DARK_GREEN = "§2";
	public static String NORMAL_GREEN = "§a";
	public static String AQUA = "§b";
	public static String DARK_AQUA = "§3";
	public static String DARK_BLUE = "§1";
	public static String BLUE = "§9";
	public static String LIGHT_PURPLE = "§d";
	public static String DARK_PURPLE = "§5";
	public static String WHITE = "§f";
	public static String GRAY = "§7";
	public static String DARK_GRAY = "§8";
	public static String BLACK = "§0";

	public static String RESET = "§r";

	public static String BOLD = "§l";
	public static String ITALIC = "§o";
	public static String UNDERLINE = "§n";
	public static String STRIKE = "§m";
	public static String JUMBLED = "§k";

	/*
	 * Added 2 RainbowEffects.
	 */
	
	public static Color rainbowEffectAlt(long offset, float fade){
		float hue = (float) (System.nanoTime() + offset) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
		Color c = new Color((int) color);
		 return new Color(c.getRed()/255.0F*fade, c.getGreen()/255.0F*fade, c.getBlue()/255.0F*fade, c.getAlpha()/255.0F);
	}
	
	public static int rainbowEffect(int speed, int offset) {
		float hue = (System.currentTimeMillis() + offset) % speed;
		hue /= speed;
		return Color.getHSBColor(hue, 1f, 1f).getRGB();
	}

	/*
	 * End of code..
	 */

}

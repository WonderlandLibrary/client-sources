package net.silentclient.client.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.silentclient.client.Client;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.Setting;

import java.awt.*;
import java.util.ArrayList;

public class ColorUtils {
	
	public static SimpleAnimation[] animation = {
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
			
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
			new SimpleAnimation(0.0F), new SimpleAnimation(0.0F), new SimpleAnimation(0.0F)
	};
	
	public static Color getChromaColor(double x, double y, double offsetScale) {
	    float v = 2000.0F;
	    return new Color(Color.HSBtoRGB((float)((System.currentTimeMillis() - x * 10.0D * offsetScale - y * 10.0D * offsetScale) % v) / v, 0.8F, 0.8F));
	}
	
	public static void setColor(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }
    
    public static void setColor(int color) {
    	setColor(color, (float) (color >> 24 & 255) / 255.0F);
    }

	public static void resetColor() {
		setColor(-1);
	}

	public static ArrayList<Color> getLatestColors(boolean ignoreOpacity) {
		ArrayList<Color> colors = new ArrayList<>();

		for(Mod mod : Client.getInstance().getModInstances().getMods()) {
			for(Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
				if(setting.isColor() && !setting.isChroma()) {
					Color color = !ignoreOpacity ? setting.getValColor() : new Color(setting.getValColor().getRed(), setting.getValColor().getBlue(), setting.getValColor().getGreen());
					if(!colors.contains(color)) {
						colors.add(color);
					}
				}
			}
		}

		return colors;
	}


}

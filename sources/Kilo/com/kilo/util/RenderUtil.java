package com.kilo.util;

import java.awt.Color;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Project;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.src.Config;
import net.minecraft.util.Vec3;

import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.all.Freecam;
import com.kilo.mod.all.Xray;

public class RenderUtil {

	private static final Minecraft mc = Minecraft.getMinecraft();

	public static double[] entityRenderPos(Entity e) {
		float p_147936_2_ = mc.timer.renderPartialTicks;
		
        double x = (e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)p_147936_2_)-mc.getRenderManager().viewerPosX;
        double y = (e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)p_147936_2_)-mc.getRenderManager().viewerPosY;
        double z = (e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)p_147936_2_)-mc.getRenderManager().viewerPosZ;
        
        return new double[] {x, y, z};
	}

	public static double[] entityWorldPos(Entity e) {
		float p_147936_2_ = mc.timer.renderPartialTicks;
		
        double x = (e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)p_147936_2_);
        double y = (e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)p_147936_2_);
        double z = (e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)p_147936_2_);
        
        return new double[] {x, y, z};
	}
	
	public static double[] renderPos(Vec3 vec) {
		float p_147936_2_ = mc.timer.renderPartialTicks;
		
        double x = vec.xCoord-mc.getRenderManager().viewerPosX;
        double y = vec.yCoord-mc.getRenderManager().viewerPosY;
        double z = vec.zCoord-mc.getRenderManager().viewerPosZ;
        
        return new double[] {x, y, z};
	}

	public static boolean transparentEntity(int id) {
		return id == -1 || id == -2;
	}
	
	public static boolean renderFar() {
		return ModuleManager.get("freecam").active ||
				ModuleManager.get("xray").active;
	}
	
	public static String hsvToRgb(float hue, float saturation, float value) {
	    int h = (int)(hue * 6);
	    float f = hue * 6 - h;
	    float p = value * (1 - saturation);
	    float q = value * (1 - f * saturation);
	    float t = value * (1 - (1 - f) * saturation);

	    switch (h) {
	      case 0: return rgbToString(value, t, p);
	      case 1: return rgbToString(q, value, p);
	      case 2: return rgbToString(p, value, t);
	      case 3: return rgbToString(p, q, value);
	      case 4: return rgbToString(t, p, value);
	      case 5: return rgbToString(value, p, q);
	      default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
	    }
	}

	public static String rgbToString(float r, float g, float b) {
	    String rs = Integer.toHexString((int)(r * 256));
	    String gs = Integer.toHexString((int)(g * 256));
	    String bs = Integer.toHexString((int)(b * 256));
	    return rs + gs + bs;
	}
	
	public static Color getRainbow(float fade) {
		float hue = (float)(System.nanoTime()/5000000000f)%1;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1f, 1f))), 16);
		Color c = new Color((int)color);
		return new Color((c.getRed()/255f)*fade, (c.getGreen()/255f)*fade, (c.getBlue()/255f)*fade, c.getAlpha()/255f);
	}
	
	public static void setupFarRender() {
		mc.entityRenderer.setupCameraTransformExt(mc.timer.renderPartialTicks, 2);
	}
	
	public static void enableLighting() {
        GlStateManager.enableLight(0);
        GlStateManager.enableLight(1);
	}
	
	public static void disableLighting() {
        GlStateManager.disableLight(0);
        GlStateManager.disableLight(1);
	}
}

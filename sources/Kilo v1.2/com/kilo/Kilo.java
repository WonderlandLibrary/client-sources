package com.kilo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;
import com.kilo.manager.DatabaseManager;
import com.kilo.manager.NotificationManager;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.util.InventoryUtil;
import com.kilo.music.MusicHandler;
import com.kilo.ui.UIHandler;
import com.kilo.users.UserControl;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;

public class Kilo {

	private static Kilo kilo;
	private final Minecraft mc;
	public final String NAME = "KiLO";
	public final String VERSION_NAME = "v1.2";

	public boolean canHack = true;
	
	public UserControl client;
	
	public Kilo(Minecraft m) {
		mc = m;
		kilo = this;
		
		try {
			Resources.loadTextures();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String clientID = Config.loadClientID();
		try {
			if (clientID.length() > 0) {
				String[] clientDetails = ServerUtil.getClientDetails(clientID);
				if (clientDetails.length == 8) {
					client = new UserControl(clientDetails[0], clientDetails[1], clientDetails[2], clientDetails[3], clientDetails[4], clientDetails[5], clientDetails[6], clientDetails[7]);
				} else {
					client = null;
				}
			} else {
				client = null;
			}
		} catch (Exception e) {
			client = new UserControl(clientID, "User", "", "false", "", "verified", "", "30");
		}
		
		ModuleManager.updateHacks();
		Config.loadFiles();
		Config.saveFiles();
	}
	
	public static Kilo kilo() {
		return kilo;
	}
	
	public void update() {
		UIHandler.update();
		
		if (MusicHandler.player != null && MusicHandler.player.isStopped()) {
			if (MusicHandler.currentSongList != null) {
				MusicHandler.next();
			} else {
				MusicHandler.stop();
			}
		}
		
		NotificationManager.update();
		
		if (mc == null || mc.theWorld == null || mc.thePlayer == null) { return; }

		InventoryUtil.tick();
		
		for(Module module : ModuleManager.list()) {
			if (mc.theWorld == null || mc.thePlayer == null) { continue; }
			module.active = ModuleManager.enabledList().contains(module);
			module.update();
		}
	}
	
	public void render2D() {
		ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		
		float scale = scaledRes.getScaleFactor() / (float)Math.pow(scaledRes.getScaleFactor(), 2);
		GL11.glScalef(scale, scale, scale);
		
		GlStateManager.disableDepth();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 0, 1000);
		UIHandler.render();
		GlStateManager.popMatrix();
		GlStateManager.enableDepth();
		
		try {
			mc.getTextureManager().bindTexture(Gui.icons);
		} catch (Exception e) {}
	}
	
	public void render3D() {
		if (mc == null || mc.theWorld == null || mc.thePlayer == null) { return; }
		
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
        
		for(Module m : ModuleManager.enabledList()) {
			m.render3D();
		}
		
		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
	}
	
	public void windowResize() {
		UIHandler.windowResize();
	}

	public void mouseClick(int mx, int my, int b) {
		UIHandler.mouseClick(mx, my, b);
	}
	
	public void mouseRelease(int mx, int my, int b) {
		UIHandler.mouseRelease(mx, my, b);
	}
	
	public void mouseScroll(int s) {
		UIHandler.mouseScroll(s);
	}
	
	public void keyboardPress(int key) {
		UIHandler.keyboardPress(key);
	}
	
	public void keyboardRelease(int key) {
		UIHandler.keyboardRelease(key);
	}
	
	public void keyTyped(int key, char keyChar) {
		UIHandler.keyTyped(key, keyChar);
	}
	
	public void shutdown() {
        Config.saveFiles();
	}
}

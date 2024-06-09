package net.minecraft.client.main.neptune.UI;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.dto.RealmsServer.McoServerComparator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Wrapper;
import net.minecraft.client.main.neptune.Events.EventRender2D;
import net.minecraft.client.main.neptune.Events.EventTick;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Mod.Mods;
import net.minecraft.client.main.neptune.Mod.Collection.Movement.Sprint;
import net.minecraft.client.main.neptune.Utils.ClientUtils;
import net.minecraft.client.main.neptune.Utils.InvUtils;
import net.minecraft.client.main.neptune.Utils.RenderUtils;
import net.minecraft.client.main.neptune.Utils.TimeMeme;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;

public class UIRenderer extends GuiIngame {

	public int colors;
	public int delay = 500;
	public int c2 = 0;
	public TimeMeme timer;
	
	public UIRenderer(Minecraft mcIn) {
		super(mcIn);
		this.timer = new TimeMeme();
	}

	public void renderUI(float var1337) {
		super.renderUI(var1337);
		renderEuphoriaUI();
	//	renderDanks();
	}
	

	private static float hue = 0;

	private void renderEuphoriaUI() {
		if(!Neptune.getWinter().legit.isEnabled()) {
			GL11.glPushMatrix();
			Color color = new Color(224, 247, 255, 255);
			int c = color.getRGB();
			int yCount = 2;
			int gCount = 2;
	        GL11.glPushMatrix();
	        GL11.glScalef(1, 1, 1);
	        GL11.glScalef(1.7f, 1.7f, 1.7f);
			Wrapper.fr.drawStringWithShadow("N", 1, 1f,0xFFFFFFFF);
			GL11.glPopMatrix();
	        GL11.glPushMatrix();
	        GL11.glScalef(1.0f, 1.0f, 1.0f);
			Wrapper.fr.drawStringWithShadow("eptune", 11f, 6,0xFFFFFFFF);
			GL11.glPopMatrix();
	        GL11.glPushMatrix();
	        GL11.glScalef(0.65f, 0.65f, 0.65f);
			Wrapper.fr.drawStringWithShadow("(b1 - 1.8)", 17f, 2,0x999999);
			//Wrapper.fr.drawStringWithShadow("Version: " + "§7" + this.mc.getCurrentServerData().gameVersion, 2, 21,0x999999);
			GL11.glPopMatrix();
			Wrapper.fr.drawStringWithShadow("X: " + (int)mc.thePlayer.posX, mc.displayWidth / 2 - (Wrapper.fr.getStringWidth("X: " + (int)mc.thePlayer.posX) + 1), mc.displayHeight / 2 - 29, -1);
			Wrapper.fr.drawStringWithShadow("Y: " + (int)mc.thePlayer.posY, mc.displayWidth / 2 - (Wrapper.fr.getStringWidth("Y: " + (int)mc.thePlayer.posY) + 1), mc.displayHeight / 2 - 19, -1);
			Wrapper.fr.drawStringWithShadow("Z: " + (int)mc.thePlayer.posZ, mc.displayWidth / 2 - (Wrapper.fr.getStringWidth("Z: " + (int)mc.thePlayer.posZ) + 1), mc.displayHeight / 2 - 9, -1);
			if(mc.thePlayer.isEntityAlive()) {
			//Wrapper.fr.drawStringWithShadow("Ping: " + "§7" + this.mc.getNetHandler().func_175102_a(this.mc.thePlayer.getUniqueID()).getResponseTime() + "ms", 2, mc.displayHeight / 2 - 20, 11184810);
            //Wrapper.fr.drawStringWithShadow("Version: " + "§7" + this.mc.getCurrentServerData().gameVersion, 2, mc.displayHeight / 2 - 10, 11184810);
			}
	        GL11.glPopMatrix();
	        //Wrapper.fu_robotochat.drawStringWithShadow(Winter.getName(), 1, 1, new Color(255, 255, 255, 255).getRGB());
			//String timeStamp = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
			//Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("FPS: " + Minecraft.getMinecraft().debugFPS, 2, 12, 0x999999);
			ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth,
					Minecraft.getMinecraft().displayHeight);
			EventRender2D event = new EventRender2D();
			Memeager.call(event);
	        final List<Mod> sortex = new ArrayList<Mod>();
	        for (final Mod mod : Neptune.getWinter().theMods.getMods()) {
	            sortex.add(mod);
	        }
	        Collections.sort(sortex, new Comparator<Mod>() {
	            @Override
	            public int compare(final Mod mod1, final Mod mod2) {
	                final String s1 = String.valueOf(mod1.getRenderName());
	                final String s2 = String.valueOf(mod2.getRenderName());
	                final int cmp = Minecraft.getMinecraft().fontRendererObj.getStringWidth(s2) - Minecraft.getMinecraft().fontRendererObj.getStringWidth(s1);
	                return (cmp != 0) ? cmp : s2.compareTo(s1);
	            }
	        });
	        for (final Mod mod2 : sortex) {
				if (mod2.isEnabled() && mod2.getName() != "Hud" && mod2.getName() != "Commands") {
					Wrapper.fr.drawStringWithShadow(mod2.getRenderName(), 2, yCount + 13, new Color(58, 169, 207, 255).getRGB());
					yCount += 9.5;
					
				}
			}
	        
			
			int yBottom = res.getScaledHeight() - 15;
			int[] pos = { Minecraft.getMinecraft().thePlayer.getPosition().getX(),
					Minecraft.getMinecraft().thePlayer.getPosition().getY(),
					Minecraft.getMinecraft().thePlayer.getPosition().getZ() };
			} else if(Neptune.getWinter().legit.isEnabled()) {
				Display.setTitle("Minecraft 1.8");
			}
			Color color = new Color(224, 247, 255, 255);
			int c = color.getRGB();
		Neptune.getWinter().getGuiMgr().update();
		Neptune.getWinter().getKeybindGuiMgr().update();
		hue += 0.25;
		if (hue > 255) {
			hue = 0;
		}
		GL11.glPopMatrix();
	}
	
	
    public static void renderDanks() { //USE THIS METHOD TO RENDER CUSTOM FONTS

    }
	public static Minecraft mc = Minecraft.getMinecraft();
}

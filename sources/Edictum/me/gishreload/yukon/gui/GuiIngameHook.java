package me.gishreload.yukon.gui;

import org.lwjgl.opengl.GL11;

import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class GuiIngameHook extends GuiIngame {

	public GuiIngameHook(Minecraft mcIn) {
		super(mcIn);
	}
	
	public void renderGameOverlay(float partialTicks) {
		String client_name = "\u00a7lEdictumClient";
		String client_name2 = "\u00a7l 1.10";
		String client_nameramm = "\u00a7l\u00a70Ramm";
		String client_nameramm2 = "\u00a7lstein";
		super.renderGameOverlay(partialTicks);
		ScaledResolution scaledresolution = new ScaledResolution(this.mc);
		this.mc.entityRenderer.setupOverlayRendering();
		GlStateManager.enableBlend();
		
		if(Meanings.Gui){
				//drawRect(1, 1, 40, 14, 0x80000000);
			int var22 = MathHelper.floor_double(this.mc.thePlayer.posX);
	        int var18 = MathHelper.floor_double(this.mc.thePlayer.posY);
	        int var17 = MathHelper.floor_double(this.mc.thePlayer.posZ);
	        if(Meanings.coordinates){
	        drawString(mc.fontRendererObj, String.format("x: %.5f", new Object[] { Double.valueOf(this.mc.thePlayer.posX), Integer.valueOf(var22), Integer.valueOf(var22 >> 4), Integer.valueOf(var22 & 0xF) }), 107, 4, 14737632);
	        drawString(mc.fontRendererObj, String.format("y: %.3f", new Object[] { Double.valueOf(this.mc.thePlayer.boundingBox.minY), Double.valueOf(this.mc.thePlayer.posY) }), 107, 14, 14737632);
	        drawString(mc.fontRendererObj, String.format("z: %.5f", new Object[] { Double.valueOf(this.mc.thePlayer.posZ), Integer.valueOf(var17), Integer.valueOf(var17 >> 4), Integer.valueOf(var17 & 0xF) }), 107, 24, 14737632);
	        }
			if(!Meanings.radar){
				if(!Meanings.rammstein){
			    Minecraft.fontRendererObj.drawString(client_name, 1, 1, WatermarkRenderer.colorProvider.getColor());
			    Minecraft.fontRendererObj.drawString(client_name2, 81, 1, 0xFFFFFF);
			    WatermarkRenderer.colorProvider.update();
				}
			    if(Meanings.rammstein){
				    Minecraft.fontRendererObj.drawStringWithShadow(client_nameramm, 1, 1, 0xFFFFFF);
				    Minecraft.fontRendererObj.drawStringWithShadow(client_nameramm2, 25, 1, WatermarkRenderer.colorProvider.getColor());
				    WatermarkRenderer.colorProvider.update();
			    }
				if(Meanings.fps){
			mc.fontRendererObj.drawString("FPS: " + String.valueOf(Minecraft.getMinecraft().getDebugFPS()), 70, 1, 0xFF0000);
				}
			int count = 0;  
			if (Meanings.radar)
	        {
	            GL11.glPushMatrix();
	            GL11.glTranslatef(50.0F, 50.0F, 0.0F);
	            this.drawGradientRect(-48, -48, 50, 50, -16777216, Integer.MIN_VALUE);
	            drawRect(-1, -1, 1, 1, -1);
	            GL11.glRotatef(-this.mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
	            this.draw_radar_2();
	            GL11.glPopMatrix();
			
		for (Module m : Edictum.getModules()) {
			if (m.isToggled()) {
				if(Meanings.vertical){
					Meanings.gorizontal = false;
					mc.fontRendererObj.drawString(m.getName(), 1, 104 + (count * 10), 0xFFFFFF);
					count++;
				}
				if(Meanings.gorizontal){
					Meanings.vertical = false;
					mc.fontRendererObj.drawString(m.getName(), 1 + (count * 50), 104, 0xFFFFFF);
					count++;
				}
			}
		}
	        }
				else if(!Meanings.radar)
				{
					for (Module m : Edictum.getModules()) {
						if (m.isToggled()) {
							if(Meanings.vertical){
								Meanings.gorizontal = false;
								mc.fontRendererObj.drawString(m.getName(), GuiMainMenu.width - 80, 1 + (count * 10), 0xFFFFFF);
								count++;
							}
							if(Meanings.gorizontal){
								Meanings.vertical = false;
								mc.fontRendererObj.drawString(m.getName(), 1 + (count * 50), 10, 0xFFFFFF);
								count++;
							}
						}
					}
						}
		}
		}
		UIRenderer.renderAndUpdateFrames();
		drawTabGui();
		}

	public void drawTabGui(){
		if(Meanings.Gui){
		drawRect(0, 11, 40, 22, Edictum.tabManager.getCurrentTab()==0 ? 0xff676c6e : 0xff0d0d0d);
		mc.fontRendererObj.drawString("Combat", 1, 12, 0xFFFFFF);
		drawRect(0, 22, 40, 22+12, Edictum.tabManager.getCurrentTab()==1 ? 0xff676c6e : 0xff0d0d0d);
		mc.fontRendererObj.drawString("Render", 1, 24, 0xFFFFFF);
		drawRect(0, 22+12, 40, 22+12+12, Edictum.tabManager.getCurrentTab()==2 ? 0xff676c6e : 0xff0d0d0d);
		mc.fontRendererObj.drawString("Player", 1, 36, 0xFFFFFF);
		drawRect(0, 22+12+12, 40, 22+12+12+12, Edictum.tabManager.getCurrentTab()==3 ? 0xff676c6e : 0xff0d0d0d);
		mc.fontRendererObj.drawString("ACheat", 1, 48, 0xFFFFFF);
		drawRect(0, 22+12+12+12, 40, 22+12+12+12+12, Edictum.tabManager.getCurrentTab()==4 ? 0xff676c6e : 0xff0d0d0d);
		mc.fontRendererObj.drawString("Other", 1, 60, 0xFFFFFF);
		
		if(Edictum.tabManager.getTabs().get(0).isExpanded()){
				drawRect(40, 11, 105, 22, Edictum.tabManager.getCurrentCombatMod()==0 ? 0xff676c6e : 0xff0d0d0d);
				mc.fontRendererObj.drawString("Killaura", 41, 12, 0xFFFFFF);
				drawRect(40, 22, 105, 22+12, Edictum.tabManager.getCurrentCombatMod()==1 ? 0xff676c6e : 0xff0d0d0d);
				mc.fontRendererObj.drawString("MobAura", 41, 24, 0xFFFFFF);
				drawRect(40, 22+12, 105, 22+12+12, Edictum.tabManager.getCurrentCombatMod()==2 ? 0xff676c6e : 0xff0d0d0d);
				mc.fontRendererObj.drawString("Aimbot", 41, 36, 0xFFFFFF);
				drawRect(40, 22+12+12, 105, 22+12+12+12, Edictum.tabManager.getCurrentCombatMod()==3 ? 0xff676c6e : 0xff0d0d0d);
				mc.fontRendererObj.drawString("Criticals", 41, 48, 0xFFFFFF);
				drawRect(40, 22+12+12+12, 105, 22+12+12+12+12, Edictum.tabManager.getCurrentCombatMod()==4 ? 0xff676c6e : 0xff0d0d0d);
				mc.fontRendererObj.drawString("JumpCrits", 41, 60, 0xFFFFFF);
				drawRect(40, 22+12+12+12+12, 105, 22+12+12+12+12+12, Edictum.tabManager.getCurrentCombatMod()==5 ? 0xff676c6e : 0xff0d0d0d);
				mc.fontRendererObj.drawString("FastBow", 41, 60+12, 0xFFFFFF);
				drawRect(40, 22+12+12+12+12+12, 105, 22+12+12+12+12+12+12, Edictum.tabManager.getCurrentCombatMod()==6 ? 0xff676c6e : 0xff0d0d0d);
				mc.fontRendererObj.drawString("Regen", 41, 60+12+12, 0xFFFFFF);
				drawRect(40, 22+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentCombatMod()==7 ? 0xff676c6e : 0xff0d0d0d);
				mc.fontRendererObj.drawString("NoVelocity", 41, 60+12+12+12, 0xFFFFFF);
				drawRect(40, 22+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentCombatMod()==8 ? 0xff676c6e : 0xff0d0d0d);
				mc.fontRendererObj.drawString("AntiFire", 41, 60+12+12+12+12, 0xFFFFFF);
				drawRect(40, 22+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentCombatMod()==9 ? 0xff676c6e : 0xff0d0d0d);
				mc.fontRendererObj.drawString("AntiPotion", 41, 60+12+12+12+12+12, 0xFFFFFF);
		}
		if(Edictum.tabManager.getTabs().get(1).isExpanded()){
			drawRect(40, 11+12, 105, 22+12, Edictum.tabManager.getCurrentRenderMod()==0 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("ChestESP", 41, 12+12, 0xFFFFFF);
			drawRect(40, 22+12, 105, 22+12+12, Edictum.tabManager.getCurrentRenderMod()==1 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("MobESP", 41, 24+12, 0xFFFFFF);
			drawRect(40, 22+12+12, 105, 22+12+12+12, Edictum.tabManager.getCurrentRenderMod()==2 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Xray", 41, 36+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12, 105, 22+12+12+12+12, Edictum.tabManager.getCurrentRenderMod()==3 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("PlayerESP", 41, 48+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12, 105, 22+12+12+12+12+12, Edictum.tabManager.getCurrentRenderMod()==4 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("ItemESP", 41, 60+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12, 105, 22+12+12+12+12+12+12, Edictum.tabManager.getCurrentRenderMod()==5 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("ArrowESP", 41, 60+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentRenderMod()==6 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Tracers", 41, 60+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentRenderMod()==7 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Light", 41, 60+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentRenderMod()==8 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Glowing", 41, 60+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentRenderMod()==9 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Radar", 41, 60+12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentRenderMod()==10 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("NoScboard", 41, 60+12+12+12+12+12+12+12, 0xFFFFFF);
		}
		if(Edictum.tabManager.getTabs().get(2).isExpanded()){
			drawRect(40, 11+12+12, 105, 22+12+12, Edictum.tabManager.getCurrentPlayerMod()==0 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Flight", 41, 12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12, 105, 22+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==1 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Freecam", 41, 24+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12, 105, 22+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==2 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Step", 41, 36+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12, 105, 22+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==3 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("IceSpeed", 41, 48+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12, 105, 22+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==4 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("SpeedMine", 41, 60+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==5 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Sprint", 41, 60+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==6 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("NoFall", 41, 60+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==7 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("BoatFlight", 41, 60+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==8 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("AutoArmor", 41, 60+12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==9 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("NoSlowDown", 41, 60+12+12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==10 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("ChestStealer", 41, 60+12+12+12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==11 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("InvMove", 41, 60+12+12+12+12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==12 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("SpeedHack", 41, 60+12+12+12+12+12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==13 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Scaffold", 41, 60+12+12+12+12+12+12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==14 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("NoEmptiness", 41, 60+12+12+12+12+12+12+12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12+12+12+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12+12+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentPlayerMod()==15 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Teleport", 41, 60+12+12+12+12+12+12+12+12+12+12+12+12+12, 0xFFFFFF);
		}
		if(Edictum.tabManager.getTabs().get(3).isExpanded()){
			drawRect(40, 11+12+12+12, 105, 22+12+12+12, Edictum.tabManager.getCurrentAnticheatMod()==0 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("AAC", 41, 12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12, 105, 22+12+12+12+12, Edictum.tabManager.getCurrentAnticheatMod()==1 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("NCP", 41, 12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12, 105, 22+12+12+12+12+12, Edictum.tabManager.getCurrentAnticheatMod()==2 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("Reflex", 41, 12+12+12+12+12+12, 0xFFFFFF);
	}
		if(Edictum.tabManager.getTabs().get(4).isExpanded()){
			drawRect(40, 22+12+12+12, 105, 22+12+12+12+12, Edictum.tabManager.getCurrentOtherMod()==0 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("CrashTag", 41, 12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12, 105, 22+12+12+12+12+12, Edictum.tabManager.getCurrentOtherMod()==1 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("RightClick", 41, 12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12, 105, 22+12+12+12+12+12+12, Edictum.tabManager.getCurrentOtherMod()==2 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("LeftClick", 41, 12+12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentOtherMod()==3 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("ExploitBed", 41, 12+12+12+12+12+12+12+12, 0xFFFFFF);
			drawRect(40, 22+12+12+12+12+12+12+12, 105, 22+12+12+12+12+12+12+12+12, Edictum.tabManager.getCurrentOtherMod()==4 ? 0xff676c6e : 0xff0d0d0d);
			mc.fontRendererObj.drawString("UpListAura", 41, 12+12+12+12+12+12+12+12+12, 0xFFFFFF);
	}
		}
	}
}

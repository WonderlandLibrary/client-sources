package Hydro.ui;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.gui.*;
import org.lwjgl.opengl.GL11;

import Hydro.altManager.GuiAltManager;
import Hydro.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class MainMenu extends GuiScreen {
	
	public ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
	
	public MainMenu() {
		
	}
	
	public void initGui() {
		
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		//BackGround image
		mc.getTextureManager().bindTexture(new ResourceLocation("Hydro/backround.png"));
		drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
		
		this.drawGradientRect(0, height - 100, width, height, 0x00000000, 0xff000000);
		
		//Hydro logo
		//mc.getTextureManager().bindTexture(new ResourceLocation("Hydro/HydroLogo.png"));
		GL11.glPushMatrix();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		//drawModalRectWithCustomSizedTexture(mc.displayWidth/2/2 - 70, 4, 0, 0, 150, 150, 150, 150);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
				
		//Changelog
		
		
		String[] buttons = { "Singleplayer", "Multiplayer", "Settings", "Exit" };

		if(mc.displayWidth >= 1008) {
			buttons = new String[] { "Singleplayer", "Multiplayer", "Alt Manager", "Settings", "Exit" };
		}
		//Buttons
		
		int count = 0;
		for(String name : buttons) {
			double x = (width/buttons.length) * count + (width/buttons.length)/2f + 8 - FontUtil.regular.getStringWidth(name)/2;
			double y = height - 20;
			
			boolean hovered = (mouseX >= x && mouseY >= y && mouseX < x + FontUtil.regular.getStringWidth(name) + 2 && mouseY < y + FontUtil.regular.getHeight() + 2);
			
			FontUtil.regular.drawCenteredString(name, (width/buttons.length) * count + (width/buttons.length)/2f + 8, height - 20, hovered ? Color.gray.getRGB() : -1);
			count++;
		}
	}
	
	private void addChangelog(String name, int y) {
		FontUtil.arrayList.drawString(EnumChatFormatting.BLACK + "[" + EnumChatFormatting.DARK_GREEN + "+" + EnumChatFormatting.BLACK + "]" + EnumChatFormatting.BLACK + " " + name, 6, y, -1);
	}
	
	private void fixChangelog(String name, int y) {
		FontUtil.arrayList.drawString(EnumChatFormatting.BLACK + "[" + EnumChatFormatting.YELLOW + "!" + EnumChatFormatting.BLACK + "]" + EnumChatFormatting.BLACK + " " + name, 6, y, -1);
	}
	
	private void removeChangelog(String name, int y) {
		FontUtil.arrayList.drawString(EnumChatFormatting.BLACK + "[" + EnumChatFormatting.RED + "-" + EnumChatFormatting.BLACK + "]" + EnumChatFormatting.BLACK + " " + name, 6, y, -1);
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		//Buttons
		String[] buttons = { "Singleplayer", "Multiplayer", "Settings", "Exit" };

		if(mc.displayWidth >= 1008) {
			buttons = new String[] { "Singleplayer", "Multiplayer", "Alt Manager", "Settings", "Exit" };
		}		
		int count = 0;
		for(String name : buttons) {
			double x = (width/buttons.length) * count + (width/buttons.length)/2f + 8 - FontUtil.regular.getStringWidth(name)/2;
			double y = height - 20;
			
			if(mouseX >= x && mouseY >= y && mouseX < x + FontUtil.regular.getStringWidth(name) + 2 && mouseY < y + FontUtil.regular.getHeight() + 2) {
				switch(name) {
					case "Singleplayer":
						mc.displayGuiScreen(new GuiSelectWorld(this));
						break;
					case "Multiplayer":
						mc.displayGuiScreen(new GuiMultiplayer(this));
						break;
					case "Alt Manager":
						mc.displayGuiScreen(new GuiAltManager());
						break;
					case "Settings":
						mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
						break;
					case "Exit":
						mc.shutdownMinecraftApplet();
						break;
				}
			}
			
			count++;
		}
	}
	
	public void onGuiClosed() {
		
	}
	
}

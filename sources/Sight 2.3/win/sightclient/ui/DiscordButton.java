package win.sightclient.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URI;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import win.sightclient.utils.TimerUtils;

public class DiscordButton extends GuiButton {

	private TimerUtils timer = new TimerUtils();
	private String text = "";
	
	private long lastUpdate = System.currentTimeMillis();
	private long totalTime;
	
	public DiscordButton(int buttonId, int x, int y) {
		super(buttonId, x, y, "");
	}

	@Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		this.width = 50;
		this.height = 50;
    	if (this.visible) {
    		GL11.glColor4f(1F, 1F, 1F, 1F);
    		this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + 50 && mouseY < this.yPosition + 50;
    		float speed = 250f;
    		if (this.hovered) {
    			totalTime += (System.currentTimeMillis() - this.lastUpdate);
    			if (totalTime > speed) {
    				totalTime = (long) speed;
    			}
    			this.lastUpdate = System.currentTimeMillis();
    		} else {
    			totalTime -= (System.currentTimeMillis() - this.lastUpdate);
    			if (totalTime < 0) {
    				totalTime = 0;
    			}
    			this.lastUpdate = System.currentTimeMillis();
    		}
    		
    		if (!this.text.equals("") && timer.hasReached(2500)) {
    			this.text = "";
    			this.totalTime = 0;
    		}
    		float distance = (float)totalTime / speed;
    		String sayText = (!text.equals("") && !timer.hasReached(2500) ? text : "Join the Discord!");
    		float drawWidth = (mc.fontRendererObj.getStringWidth(sayText) + 6) * distance;
    		
    		Gui.drawRect(this.xPosition + 49, this.yPosition + 25 - (mc.fontRendererObj.FONT_HEIGHT / 2), this.xPosition + 49 + drawWidth, this.yPosition + 25 - (mc.fontRendererObj.FONT_HEIGHT / 2) + 12, new Color(0, 0, 0, 150).getRGB());
    		
    		if (distance == 1) {
    			mc.fontRendererObj.drawString(sayText, this.xPosition + 52, this.yPosition + 25 - (mc.fontRendererObj.FONT_HEIGHT / 2) + 2, -1);
    		} else {
    			float x = 1;
    			for (char c : sayText.toCharArray()) {
    				if (x <= drawWidth - 6) {
    					mc.fontRendererObj.drawString(c + "", this.xPosition + 51 + x, this.yPosition + 25 - (mc.fontRendererObj.FONT_HEIGHT / 2) + 2, -1);
    					x += mc.fontRendererObj.getCharWidth(c);
    				}
    			}
    		}
    		
    		
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("sight/discord.png"));
			GlStateManager.enableAlpha();
			Gui.drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, 0, 0, 50, 50, 50, 50);
			GlStateManager.disableAlpha();
			
    		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    	}
    }
    
    @Override
    public void mouseClicked(int x, int y) {
    	if (!this.hovered) {
    		return;
    	}
		String url = "https://discord.gg/GxcmXNN";
		try {
			Desktop.getDesktop().browse(new URI(url));
			this.timer.reset();
			this.text = "Opened the invite link in your browser.";
			this.totalTime = 0;
		} catch (Exception e) {
			e.printStackTrace();
			StringSelection selection = new StringSelection(url);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			this.timer.reset();
			this.text = "Copied the link to your clipboard.";
			this.totalTime = 0;
		}
    }
}

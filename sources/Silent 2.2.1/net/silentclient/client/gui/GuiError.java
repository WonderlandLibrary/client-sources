package net.silentclient.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.silentclient.client.Client;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.font.SilentFontRenderer;

import java.awt.*;
import java.io.IOException;

public class GuiError extends GuiScreen {
	private final String error;
	
	
	public GuiError(String error) {
		this.error = error;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.disableAlpha();
		Client.backgroundPanorama.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
		if(Client.getInstance().getGlobalSettings().isLite()) {
			this.drawGradientRect(0, 0, this.width, this.height, new Color(0, 0, 0, 127).getRGB(), new Color(0, 0, 0, 200).getRGB());
		} else {
			this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
		}

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
        Client.getInstance().getSilentFontRenderer().drawString("Error", (this.width / 2) - (Client.getInstance().getSilentFontRenderer().getStringWidth("Error", 14, SilentFontRenderer.FontType.HEADER) / 2), this.height / 3 - 3, 14, SilentFontRenderer.FontType.HEADER);
        Client.getInstance().getSilentFontRenderer().drawString(error, (this.width / 2) - (Client.getInstance().getSilentFontRenderer().getStringWidth(error, 14, SilentFontRenderer.FontType.TITLE) / 2), this.height / 2 - 3, 14, SilentFontRenderer.FontType.TITLE);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void initGui() {
		Client.backgroundPanorama.updateWidthHeight(this.width, this.height);
		this.buttonList.add(new Button(1, (this.width / 2) - (98 / 2), this.height - 30, 98, 20, "Quit Game"));
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 1) {
			this.mc.shutdown();
		}
	}
	
	public void updateScreen()
    {
		Client.backgroundPanorama.tickPanorama();
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}

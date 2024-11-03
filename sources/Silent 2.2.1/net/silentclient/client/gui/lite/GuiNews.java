package net.silentclient.client.gui.lite;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.PromoController;
import net.silentclient.client.utils.TimerUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class GuiNews extends SilentScreen {
	private TimerUtils logoTimer = new TimerUtils();
	private int logoY = 0;
	private final boolean firstTime;

	public GuiNews() {
		this.firstTime = false;
	}

	public GuiNews(boolean firstTime) {
		this.firstTime = firstTime;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		PromoController.getResponse().update();
		MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
		GlStateManager.disableAlpha();
		Client.backgroundPanorama.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
		if(Client.getInstance().getGlobalSettings().isLite()) {
			this.drawGradientRect(0, 0, this.width, this.height, new Color(0, 0, 0, 127).getRGB(), new Color(0, 0, 0, 200).getRGB());
		} else {
			this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
		}

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if(PromoController.getResponse().getPanels().size() > 1) {
			if(MouseUtils.isInside(mouseX, mouseY, 40, this.height / 2 - 10, 20, 20) || MouseUtils.isInside(mouseX, mouseY, this.width - 40 - 20, this.height / 2 - 10, 20, 20)) {
				cursorType = MouseCursorHandler.CursorType.POINTER;
			}
			RenderUtil.drawImage(new ResourceLocation("silentclient/icons/back.png"), 40, this.height / 2 - 10, 20, 20);
			RenderUtil.drawImage(new ResourceLocation("silentclient/icons/next.png"), this.width - 40 - 20, this.height / 2 - 10, 20, 20);
		}

		if(PromoController.getResponse().getCurrentPanel() != null) {
			if(PromoController.getResponse().getCurrentPanel().getImageLocation() != null) {
				RenderUtil.drawImage(PromoController.getResponse().getCurrentPanel().getImageLocation(), (this.width / 2) - (300 / 2), (this.height / 2) - (168 / 2) - 10, 300, 168, false);
				if(MouseUtils.isInside(mouseX, mouseY, (this.width / 2) - (300 / 2), (this.height / 2) - (168 / 2) - 10, 300, 168)) {
					cursorType = MouseCursorHandler.CursorType.POINTER;
				}
			} else {
				PromoController.getResponse().getCurrentPanel().loadImage();
			}
		}

		super.drawScreen(mouseX, mouseY, partialTicks);

		if(firstTime) {
			ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
			mc.getTextureManager().bindTexture(new ResourceLocation("silentclient/splash.png"));
			Gui.drawScaledCustomSizeModalRect(0, logoY, 0, 0, 1920, 1080, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 1920, 1080);

			if (this.logoY > -scaledResolution.getScaledHeight() && logoTimer.delay(1)) {
				this.logoY -= 30;
				logoTimer.reset();
			}
		}

		Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);
	}
	
	@Override
	public void initGui() {
		defaultCursor = false;
		Client.backgroundPanorama.updateWidthHeight(this.width, this.height);
		this.buttonList.add(new Button(1, (this.width / 2) - (98 / 2), this.height - 30, 98, 20, "Exit"));
		super.initGui();
		PromoController.getResponse().setPanelIndex(0);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 1) {
			mc.displayGuiScreen(new LiteMainMenu());
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if(PromoController.getResponse().getPanels().size() > 1) {
			if(MouseUtils.isInside(mouseX, mouseY, 40, this.height / 2 - 10, 20, 20)) {
				PromoController.getResponse().prevPanel();
				return;
			}

			if(MouseUtils.isInside(mouseX, mouseY, this.width - 40 - 20, this.height / 2 - 10, 20, 20)) {
				PromoController.getResponse().nextPanel();
				return;
			}
		}
		if(MouseUtils.isInside(mouseX, mouseY, (this.width / 2) - (300 / 2), (this.height / 2) - (168 / 2) - 10, 300, 168) && PromoController.getResponse().getCurrentPanel() != null && PromoController.getResponse().getCurrentPanel().getImageLocation() != null) {
			try {
	    		Class<?> oclass = Class.forName("java.awt.Desktop");
	            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
	            oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(PromoController.getResponse().getCurrentPanel().getRedirectUrl())});
	    	} catch (Throwable err) {
	    		err.printStackTrace();
	    	}
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

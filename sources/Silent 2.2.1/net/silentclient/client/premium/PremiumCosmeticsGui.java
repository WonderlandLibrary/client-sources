package net.silentclient.client.premium;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.font.SilentFontRenderer.FontType;
import net.silentclient.client.gui.lite.GuiNews;
import net.silentclient.client.gui.silentmainmenu.MainMenuConcept;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.types.PremiumCosmeticsResponse;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

public class PremiumCosmeticsGui extends SilentScreen {
	private final PremiumCosmeticsResponse response;
	
	public PremiumCosmeticsGui(PremiumCosmeticsResponse response) {
		this.response = response;
	}
	
	@Override
	public void initGui() {
		Client.backgroundPanorama.updateWidthHeight(this.width, this.height);
		this.buttonList.add(new Button(1, (this.width / 2) - (98 / 2), this.height - 30, 98, 20, "Understood"));
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
		ScaledResolution scaledResolution = new ScaledResolution(mc);
		super.drawScreen(mouseX, mouseY, partialTicks);
		String month = "";
		
		switch(LocalDate.now().getMonthValue()) {
		case 1:
			month = "January";
			break;
		case 2:
			month = "February";
			break;
		case 3:
			month = "March";
			break;
		case 4:
			month = "April";
			break;
		case 5:
			month = "May";
			break;
		case 6:
			month = "June";
			break;
		case 7:
			month = "Jule";
			break;
		case 8:
			month = "August";
			break;
		case 9:
			month = "September";
			break;
		case 10:
			month = "October";
			break;
		case 11:
			month = "November";
			break;
		case 12:
			month = "December";
			break;
		}
				
		Client.getInstance().getSilentFontRenderer().drawString((scaledResolution.getScaledWidth() / 2) - (Client.getInstance().getSilentFontRenderer().getStringWidth(month, 18, FontType.HEADER) / 2), 10, month, 18, FontType.HEADER);
		RenderUtil.drawImage(new ResourceLocation(Client.getInstance().getAccount().isPremiumPlus() ? "silentclient/premium/premium_plus.png" : "silentclient/premium/premium.png"), (scaledResolution.getScaledWidth() / 2) - 44.5F, 30, 89, Client.getInstance().getAccount().isPremiumPlus() ? 19.3F : 21.34F);
		Client.getInstance().getSilentFontRenderer().drawCenteredString("Hey " + Minecraft.getMinecraft().getSession().getUsername() + "! Thanks for being awesome and continuing", (scaledResolution.getScaledWidth() / 2), 50, 8, FontType.HEADER);
		Client.getInstance().getSilentFontRenderer().drawCenteredString("your Premium" + (Client.getInstance().getAccount().isPremiumPlus() ? "+" : "") + " subscription. New Exclusive cosmetic items", (scaledResolution.getScaledWidth() / 2), 60, 8, FontType.HEADER);
		Client.getInstance().getSilentFontRenderer().drawCenteredString("are available to you. You can access using the cosmetics menu", (scaledResolution.getScaledWidth() / 2), 70, 8, FontType.HEADER);
		Client.getInstance().getSilentFontRenderer().drawCenteredString(" in the main menu or in game. Have an awesome day!", (scaledResolution.getScaledWidth() / 2), 80, 8, FontType.HEADER);
		
		if(response.cosmetics.size() != 0) {
			Client.getInstance().getSilentFontRenderer().drawCenteredString(response.cosmetics.get(0).getItem().getName(), (scaledResolution.getScaledWidth() / 2), ((scaledResolution.getScaledHeight() / 2) - 9) - (response.cosmetics.size() > 1 ? 16 : 0), 18, FontType.HEADER);
			if(response.cosmetics.size() > 1) {
				Client.getInstance().getSilentFontRenderer().drawCenteredString("and", (scaledResolution.getScaledWidth() / 2), ((scaledResolution.getScaledHeight() / 2) - 6), 12, FontType.TITLE);
				Client.getInstance().getSilentFontRenderer().drawCenteredString(response.cosmetics.get(1).getItem().getName(), (scaledResolution.getScaledWidth() / 2), ((scaledResolution.getScaledHeight() / 2) - 9) + 16, 18, FontType.HEADER);
			}
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(button.id == 1) {
			Client.getInstance().getAccount().claimPremiumCosmetics();
			mc.displayGuiScreen(Client.getInstance().getGlobalSettings().isLite() ? new GuiNews() : new MainMenuConcept());
		}
	}
	
	@Override
	public void updateScreen() {
		Client.backgroundPanorama.tickPanorama();
	}
}

package net.augustus.ui;

import java.awt.Color;
import java.io.IOException;

import net.augustus.Augustus;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiUpdate extends GuiScreen{

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution sr = new ScaledResolution(mc);
		Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), 0xff303030);
        Augustus.getInstance().getLoriousFontService().getComfortaa38().drawCenteredString("This copy of Gugustus is outdated.", (float)(sr.getScaledWidth() / 2.0), (float)(sr.getScaledHeight() / 2.0 - 20.0 - (sr.getScaledHeight() / 8)), -1, true, new Color(255, 255, 255), new Color(255, 255, 255));
        Augustus.getInstance().getLoriousFontService().getComfortaa38().drawCenteredString("Latest Version: b" + Augustus.getInstance().latestVersion, (float)(sr.getScaledWidth() / 2.0), (float)(sr.getScaledHeight() / 2.0 - 20.0 - (sr.getScaledHeight() / 8) + Augustus.getInstance().getLoriousFontService().getComfortaa38().getHeight()), -1, true, new Color(0, 255, 0), new Color(0, 255, 0));
        Augustus.getInstance().getLoriousFontService().getComfortaa38().drawCenteredString("Currect Version: " + Augustus.getInstance().getVersion(), (float)(sr.getScaledWidth() / 2.0), (float)(sr.getScaledHeight() / 2.0 - 20.0 - (sr.getScaledHeight() / 8) + (Augustus.getInstance().getLoriousFontService().getComfortaa38().getHeight() * 2)), -1, true, new Color(255, 0, 0), new Color(255, 0, 0));
        Augustus.getInstance().getLoriousFontService().getComfortaa38().drawCenteredString("Click anywhere to exit...", (float)(sr.getScaledWidth() / 2.0), (float)(sr.getScaledHeight() / 2.0 - 20.0 - (sr.getScaledHeight() / 8) + (Augustus.getInstance().getLoriousFontService().getComfortaa38().getHeight() * 3)), -1, true, new Color(255, 255, 255), new Color(255, 255, 255));
        Augustus.getInstance().getLoriousFontService().getComfortaa38().drawCenteredString("Discord: moddingmc_", (float)(sr.getScaledWidth() / 2.0), (float)(sr.getScaledHeight() / 2.0 - 20.0 - (sr.getScaledHeight() / 8) + (Augustus.getInstance().getLoriousFontService().getComfortaa38().getHeight() * 6)), -1, true, new Color(255, 255, 255), new Color(255, 255, 255));
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		Augustus.getInstance().showedUpdate = true;
		mc.displayGuiScreen(new GuiMainMenu());
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
	}

	public void initGui() {
	}
	
	

}

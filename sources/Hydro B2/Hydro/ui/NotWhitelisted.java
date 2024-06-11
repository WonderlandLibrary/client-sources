package Hydro.ui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import Hydro.util.HWID;
import Hydro.util.font.FontUtil;
import net.minecraft.client.gui.GuiScreen;

public class NotWhitelisted extends GuiScreen {
	
	public String hwid = "Please wait";
	
	public NotWhitelisted() {
	
	}
	
	@Override
	public void initGui() {
		try {
			hwid = HWID.getHWID();
		} catch (Exception e) {
			hwid = "ERROR";
		}
		String hwid1 = hwid;
		StringSelection stringSelection = new StringSelection(hwid1);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		FontUtil.large.drawStringWithShadow("You are not whitelisted!", mc.displayWidth/2/2 - 125, 10, Color.red.getRGB());
		FontUtil.regular.drawStringWithShadow("Join the discord for support. https://discord.gg/2Hsy6eYRKS", mc.displayWidth/2/2 - 180, 45, Color.green.getRGB());
		FontUtil.regular.drawString("You may be asked for your HWID, It is copied to your clipboard!", mc.displayWidth/2/2 - 165, 65, Color.white.getRGB());
		FontUtil.regular.drawString(hwid, 350, 100, Color.yellow.getRGB());
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
	}
	
	@Override
	public void onGuiClosed() {
		
	}

}

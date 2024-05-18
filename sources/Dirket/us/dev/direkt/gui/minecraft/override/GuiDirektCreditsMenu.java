package us.dev.direkt.gui.minecraft.override;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiDirektCreditsMenu extends GuiScreen {
	private GuiScreen parentScreen;

	public GuiDirektCreditsMenu(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	public boolean doesGuiPauseGame() {
		return true;
	}

	public void initGui() {
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 6 + 168, "Done"));
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 0)
			this.mc.displayGuiScreen(this.parentScreen);
	}

	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();

		this.drawCenteredString(this.fontRendererObj, "Credits", this.width / 2, 30, 0x00FFFB);

		this.drawCenteredString(this.fontRendererObj, "\247bDirekt \2477is the product of many hard working developers.", this.width / 2, this.height / 6 + 20, 0xAAAAAA);
		this.drawCenteredString(this.fontRendererObj, "\247bPrimary Developers:\2477 \247cBFCE\2477, \2475Jordin\2477, \247eFoundry\2477, \247cImport\2477,\2476 and KNOX for making this menu\2477 \2470Meckimp", this.width / 2, this.height / 6 + 35, 0xAAAAAA);
		this.drawCenteredString(this.fontRendererObj, "\247bMinecraft\2477 by \247fMojang AB / Microsoft\2477. They own everything.", this.width / 2, this.height / 6 + 50, 0xAAAAAA);
		this.drawCenteredString(this.fontRendererObj, "\247bDirekt\2477 is powered by \247eOptifine\2477, by \2476sp614x.", this.width / 2, this.height / 6 + 65, 0xAAAAAA);
		this.drawCenteredString(this.fontRendererObj, "\247bDirekt\2477's font rendering engine is powered by \247eBetterFonts\2477, by \2476thvortex.", this.width / 2, this.height / 6 + 80, 0xAAAAAA);
		this.drawCenteredString(this.fontRendererObj, "\247bYou\2477: paying for this client, allowing us to have mud to drink. Thank you!", this.width / 2, this.height / 6 + 110, 0xFFFFFF);

		this.drawCenteredString(this.fontRendererObj, "Send any and all questions, comments, or reports to - \247fsupport@direkt.tech", this.width / 2, this.height / 6 + 140, 0xCCCCCC);

		super.drawScreen(par1, par2, par3);
	}
}
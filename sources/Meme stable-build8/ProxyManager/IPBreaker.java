package ProxyManager;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.arithmo.gui.altmanager.Colors;
import me.arithmo.gui.altmanager.RenderingUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;

public class IPBreaker extends GuiScreen {
	GuiTextField username;

	public IPBreaker(GuiMainMenu guiMainMenu) {

	}

	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 2: {
			mc.displayGuiScreen(new GuiMainMenu());
			break;
		}
		}
	}

	public void drawScreen(int i, int j, float f) {
		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		RenderingUtil.rectangle(0.0D, 0.0D, res.getScaledWidth(), res.getScaledHeight(), Colors.getColor(0));
		this.username.drawTextBox();
		drawCenteredString(this.fontRendererObj, "IP spoof thingy lol", this.width / 2, 20, -1);
		if (this.username.getText().isEmpty()) {
			drawString(this.mc.fontRendererObj, "IP (prefix with 10.)", this.width / 2 - 96, 66, -7829368);
		}
		super.drawScreen(i, j, f);
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 116 + 36, "Done"));
		this.username = new GuiTextField(this.eventButton, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
	}

	protected void keyTyped(char par1, int par2) {
		this.username.textboxKeyTyped(par1, par2);
		if ((par1 == '\t') && ((this.username.isFocused()))) {
			this.username.setFocused(!this.username.isFocused());
		}
		if (par1 == '\r') {
			actionPerformed((GuiButton) this.buttonList.get(0));
		}
		if (par2 == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(new GuiMainMenu());
		}
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		try {
			super.mouseClicked(par1, par2, par3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.username.mouseClicked(par1, par2, par3);
	}
}

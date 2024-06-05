package zamorozka.gui;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import zamorozka.main.Zamorozka;
import zamorozka.ui.font.Fonts;

import org.lwjgl.input.Keyboard;

public class GuiOfflineName extends GuiScreen {

	private GuiScreen parentScreen;
	private GuiTextField usernameTextField;

	public GuiOfflineName(GuiScreen guiscreen) {
		this.parentScreen = guiscreen;
	}

	public void updateScreen() {
		this.usernameTextField.updateCursorCounter();
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}
		if (guibutton.id == 1) {
			mc.displayGuiScreen(this.parentScreen);
		}
		if (guibutton.id == 0) {
			mc.session = new Session(this.usernameTextField.getText(), "", "", "");
			mc.displayGuiScreen(this.parentScreen);
		}
		if (guibutton.id == 7) {
			mc.displayGuiScreen(new GuiName(parentScreen));
		}
		if (guibutton.id == 6) {
			mc.displayGuiScreen(new GuiPremN(parentScreen));
		}

	}

	protected void keyTyped(char c, int i) {
		this.usernameTextField.textboxKeyTyped(c, i);
		if ((c == '\t') && (this.usernameTextField.isFocused())) {
			this.usernameTextField.setFocused(false);
		}
		if (c == '\r') {
			actionPerformed((GuiButton) this.buttonList.get(0));
		}
	}

	protected void mouseClicked(int i, int j, int k) throws IOException {
		super.mouseClicked(i, j, k);
		this.usernameTextField.mouseClicked(i, j, k);
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		buttonList.add(new GuiButton(6, this.width / 8 + 780 / 2, 34, "User/Pass"));
		buttonList.add(new GuiButton(7, this.width / 8 + 780 / 2, 10, "Email:Password"));
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Done"));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
		this.usernameTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
	}

	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		ScaledResolution s1 = new ScaledResolution(this.mc);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("altfon.jpg"));
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, s1.getScaledWidth(), s1.getScaledHeight(), s1.getScaledWidth(), s1.getScaledHeight());
		drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);
		drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);
		drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);
		drawGradientRect(0, 0, width, height, 0x02044A0, 0x570044A0);

		drawString(this.fontRendererObj, "Nick/Никнейм", this.width / 2 - 100, 104, 10526880);
		drawString(this.fontRendererObj, "", this.width / 2, this.height / 4 - 40 + 20, 16777215);
		this.usernameTextField.drawTextBox();
		super.drawScreen(i, j, f);
	}

}

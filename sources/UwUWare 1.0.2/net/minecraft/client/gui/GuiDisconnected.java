package net.minecraft.client.gui;

import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.UI.altmanager.AltLoginThread;
import Ascii4UwUWareClient.UI.altmanager.AltManager;
import Ascii4UwUWareClient.UI.altmanager.GuiAltManager;
import Ascii4UwUWareClient.UI.altmanager.GuiTheAltening;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class GuiDisconnected extends GuiScreen {
	private String reason;
	private IChatComponent message;
	private List<String> multilineMessage;
	private final GuiScreen parentScreen;
	private int field_175353_i;
	private long reconnectTime;
	public static ServerData serverData;
	public boolean stop;
	public boolean enabled;

	public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, IChatComponent chatComp) {
		this.parentScreen = screen;
		this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
		this.message = chatComp;
	}


	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is the
	 * equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when
	 * the GUI is displayed and when the window resizes, the buttonList is cleared
	 * beforehand.
	 */
	public void initGui() {
		this.buttonList.clear();
		this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(),
				this.width - 50);
		this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100,
				this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT,
				I18n.format("gui.toMenu", new Object[0])));
		this.buttonList.add(new GuiButton(6969, this.width / 2 - 100,
				this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 30,
				I18n.format("Altening Gen new alt", new Object[0])));
		if (Client.instance.isAutoRelogTheAltening())
			reconnectTime = System.currentTimeMillis() + 1500;
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for
	 * buttons)
	 */
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			this.mc.displayGuiScreen(this.parentScreen);
		}else if (button.id == 6969){
			if (Client.instance.APIKey == null) return;
			GuiTheAltening.theAltening(true, this);

		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		mc.fontRendererObj.drawCenteredString((GuiTheAltening.thread == null) ? "" : AltLoginThread.getStatus(), this.width / 2, 29, -1);

		this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2,
				this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
		int i = this.height / 2 - this.field_175353_i / 2;

		if (this.multilineMessage != null) {
			for (String s : this.multilineMessage) {
				this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
				i += this.fontRendererObj.FONT_HEIGHT;
			}
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}

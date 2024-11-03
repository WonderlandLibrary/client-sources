package net.silentclient.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.silentclient.client.gui.multiplayer.SilentMultiplayerGui;

import java.io.IOException;

public class GuiMultiplayerInGame extends SilentMultiplayerGui {

	public GuiMultiplayerInGame(GuiScreen parentScreen) {
		super(parentScreen);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 1) {
			disconnect();
		}

		super.actionPerformed(button);
	}

	@Override
	public void connectToSelected() {
		disconnect();
		super.connectToSelected();
	}

	private void disconnect() {
		if(this.mc.theWorld != null) {
			this.mc.theWorld.sendQuittingDisconnectingPacket();
			this.mc.loadWorld(null);
			this.mc.displayGuiScreen(null);
			this.setParentScreen(null);
		}
	}

}

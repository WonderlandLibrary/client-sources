package club.strifeclient.ui.implementations.imgui;

import club.strifeclient.Client;
import club.strifeclient.module.implementations.visual.ClickGUI;
import club.strifeclient.ui.implementations.imgui.implementations.IImGuiInterface;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiImGui extends GuiScreen {

	@Getter @Setter private GuiScreen parent;
	@Getter @Setter
	private IImGuiInterface guiInterface;

	private ClickGUI clickGUI;
	private boolean didParentPauseGame;

	public GuiImGui() {
		clickGUI = Client.INSTANCE.getModuleManager().getModule(ClickGUI.class);
		final ClickGUI.ImGuiMode guiMode = ((ClickGUI.ImGuiMode) clickGUI.getSettingByName("GUI Mode").getValue());
		guiInterface = guiMode.getGuiInterface();
	}

	@Override
	public boolean doesGuiPauseGame() {
		if(parent != null) {
			didParentPauseGame = parent.doesGuiPauseGame();
			parent.setDoesGuiPauseGame(false);
		}
		return false;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		if (parent != null) parent.drawScreen(mouseX, mouseY, partialTicks);

		Client.INSTANCE.getImGuiRenderer().render();
	}

	@Override
	public void keyTyped(final char typedChar, final int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		if (parent != null) parent.keyTyped(typedChar, keyCode);
	}

	@Override
	public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		if (parent != null) parent.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	public void actionPerformed(final GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (parent != null) parent.actionPerformed(button);
		System.out.println("action performed");
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (parent != null) {
			parent.onGuiClosed();
			parent.setDoesGuiPauseGame(didParentPauseGame);
		}
	}
}

package mods.togglesprint.me.imfr0zen.guiapi;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

/**
 * 
 * @author ImFr0zen
 * 
 */
public class ClickGui extends GuiScreen {

	/**
	 * If you want to use another FontRenderer, change this.
	 */
	public static final FontRenderer FONTRENDERER = Minecraft.getMinecraft().fontRendererObj;
	public static int currentId = 0;

	
	protected ArrayList<Frame> frames = new ArrayList<Frame>();

	public ClickGui() {
		currentId = 0;
	}

	/**
	 * Adds a Frame
	 * 
	 * @param frame
	 */
	protected void addFrame(Frame frame) {
		if (!frames.contains(frame)) {
			frames.add(frame);
		}
	}

	@Override
	public void initGui() {
		for (Frame frame : frames) {
			frame.init();
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		for (Frame frame : frames) {
			frame.render(mouseX, mouseY);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for (Frame frame : frames) {
			frame.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);

		for (Frame frame : frames) {
			frame.keyTyped(keyCode, typedChar);
		}
	}
}

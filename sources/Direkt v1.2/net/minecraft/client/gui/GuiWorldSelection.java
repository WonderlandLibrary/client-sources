package net.minecraft.client.gui;

import java.io.IOException;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import net.minecraft.client.resources.I18n;

public class GuiWorldSelection extends GuiScreen implements GuiYesNoCallback {
	private static final Logger LOGGER = LogManager.getLogger();

	/** The screen to return to when this closes (always Main Menu). */
	protected GuiScreen prevScreen;
	protected String title = "Select world";

	/**
	 * Tooltip displayed a world whose version is different from this client's
	 */
	private String worldVersTooltip;
	private GuiButton deleteButton;
	private GuiButton selectButton;
	private GuiButton renameButton;
	private GuiButton copyButton;
	private GuiListWorldSelection selectionList;

	public GuiWorldSelection(GuiScreen screenIn) {
		this.prevScreen = screenIn;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the window resizes, the buttonList is cleared beforehand.
	 */
	@Override
	public void initGui() {
		this.title = I18n.format("selectWorld.title", new Object[0]);
		this.selectionList = new GuiListWorldSelection(this, this.mc, this.width, this.height, 32, this.height - 64, 36);
		this.postInit();
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.selectionList.handleMouseInput();
	}

	public void postInit() {
		this.selectButton = this.func_189646_b(new GuiButton(1, (this.width / 2) - 154, this.height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
		this.func_189646_b(new GuiButton(3, (this.width / 2) + 4, this.height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
		this.renameButton = this.func_189646_b(new GuiButton(4, (this.width / 2) - 154, this.height - 28, 72, 20, I18n.format("selectWorld.edit", new Object[0])));
		this.deleteButton = this.func_189646_b(new GuiButton(2, (this.width / 2) - 76, this.height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
		this.copyButton = this.func_189646_b(new GuiButton(5, (this.width / 2) + 4, this.height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
		this.func_189646_b(new GuiButton(0, (this.width / 2) + 82, this.height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
		this.selectButton.enabled = false;
		this.deleteButton.enabled = false;
		this.renameButton.enabled = false;
		this.copyButton.enabled = false;
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
	 */
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			GuiListWorldSelectionEntry guilistworldselectionentry = this.selectionList.getSelectedWorld();

			if (button.id == 2) {
				if (guilistworldselectionentry != null) {
					guilistworldselectionentry.deleteWorld();
				}
			} else if (button.id == 1) {
				if (guilistworldselectionentry != null) {
					guilistworldselectionentry.joinWorld();
				}
			} else if (button.id == 3) {
				this.mc.displayGuiScreen(new GuiCreateWorld(this));
			} else if (button.id == 4) {
				if (guilistworldselectionentry != null) {
					guilistworldselectionentry.editWorld();
				}
			} else if (button.id == 0) {
				this.mc.displayGuiScreen(this.prevScreen);
			} else if ((button.id == 5) && (guilistworldselectionentry != null)) {
				guilistworldselectionentry.recreateWorld();
			}
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.worldVersTooltip = null;
		this.selectionList.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);

		if (this.worldVersTooltip != null) {
			this.drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.worldVersTooltip)), mouseX, mouseY);
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.selectionList.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Called when a mouse button is released.
	 */
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		this.selectionList.mouseReleased(mouseX, mouseY, state);
	}

	/**
	 * Called back by selectionList when we call its drawScreen method, from ours.
	 */
	public void setVersionTooltip(String p_184861_1_) {
		this.worldVersTooltip = p_184861_1_;
	}

	public void selectWorld(@Nullable GuiListWorldSelectionEntry entry) {
		boolean flag = entry != null;
		this.selectButton.enabled = flag;
		this.deleteButton.enabled = flag;
		this.renameButton.enabled = flag;
		this.copyButton.enabled = flag;
	}
}

package com.darkcart.xdolf.altmanager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.util.Session;

import java.io.IOException;

import com.darkcart.xdolf.Wrapper;

public class GuiAltList extends GuiScreen {
	public String dispErrorString = "";
	public boolean deleteMenuOpen = false;

	public GuiAltList() {
		Wrapper.getFileManager().loadAlts();
	}

	public FontRenderer getLocalFontRenderer() {
		return this.fontRendererObj;
	}

	public void onGuiClosed() {
		Wrapper.getFileManager().saveAlts();
		super.onGuiClosed();
	}

	private SlotAlt tSlot;

	public void initGui() {
		buttonList.clear();
		buttonList.add(new GuiButton(5, width / 2 - 105, height - 47, 100, 20,
				"Direct Login"));
		buttonList.add(new GuiButton(2, width / 2 + 5, height - 47, 100, 20,
				"Login"));

		buttonList.add(new GuiButton(1, width / 2 - 105, height - 26, 66, 20,
				"Add"));
		buttonList.add(new GuiButton(3, width / 2 - 33, height - 26, 66, 20,
				"Remove"));
		buttonList.add(new GuiButton(4, width / 2 + 39, height - 26, 66, 20,
				"Cancel"));

		tSlot = new SlotAlt(Minecraft.getMinecraft(), this);
		tSlot.registerScrollButtons(7, 8);
	}

	/**
	 * Handles mouse input.
	 */
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.tSlot.handleMouseInput();
	}

	@Override
	public void confirmClicked(boolean flag, int i1) {
		super.confirmClicked(flag, i1);
		if (deleteMenuOpen) {
			deleteMenuOpen = false;

			if (flag) {
				Manager.altList.remove(i1);
				Wrapper.getFileManager().saveAlts();
			}

			this.mc.displayGuiScreen(this);
		}
	}

	public void actionPerformed(GuiButton button) {
		try {
			super.actionPerformed(button);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (button.id == 1) {
			GuiAltAdd gaa = new GuiAltAdd(this);
			this.mc.displayGuiScreen(gaa);
		}
		if (button.id == 2) {
			try {
				Alt a1 = Manager.altList.get(tSlot.getSelected());
				if (a1.isPremium()) {
					try {
						Session s = YggdrasilPayload.loginPassword(
								a1.getUsername(), a1.getPassword());
						if (s != null) {
							Minecraft.getMinecraft().session = s;
						}
						Manager.altScreen.dispErrorString = "";
					} catch (Exception error) {
						dispErrorString = "".concat("\247cBad Login \2477(")
								.concat(a1.getUsername()).concat(")");
					}
				} else {
					Minecraft.getMinecraft().session = YggdrasilPayload
							.loginCrack(a1.getUsername());
					Manager.altScreen.dispErrorString = "";
				}
			} catch (Exception e) {
			}
		}
		if (button.id == 3) {
			try {
				String s1 = "Delete the alt: "
						+ "\""
						+ Manager.altList.get(tSlot.getSelected())
								.getUsername() + "\"" + "?";
				String s3 = "Delete";
				String s4 = "Cancel";
				GuiYesNo guiyesno = new GuiYesNo(this, s1, "", s3, s4,
						tSlot.getSelected());
				deleteMenuOpen = true;
				this.mc.displayGuiScreen(guiyesno);
			} catch (Exception e) {
			}
		}
		if (button.id == 4) {
			this.mc.displayGuiScreen(new GuiMainMenu());
		}
		if (button.id == 5) {
			GuiDirectLogin gdl = new GuiDirectLogin(this);
			this.mc.displayGuiScreen(new GuiDirectLogin(this));
		}
	}

	public void updateScreen() {
		super.updateScreen();
	}

	public void drawScreen(int i, int j, float f) {
		tSlot.drawScreen(i, j, f);
		drawCenteredString(this.fontRendererObj, "Alts", width / 2, 13,
				0xFFFFFF);
		this.fontRendererObj.drawStringWithShadow(
				"Playing as " + Minecraft.getMinecraft().session.getUsername(),
				3, 3, 0xFFFFFF);
		this.fontRendererObj.drawStringWithShadow(dispErrorString, 3, 13,
				0xFFFFFF);
		super.drawScreen(i, j, f);
	}
}
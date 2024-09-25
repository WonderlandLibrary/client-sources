package none.ui.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.world.HWID;
import none.Client;
import none.utils.XYGui;

public class GuiHWIDLogin extends GuiScreen {

	public ArrayList<XYGui> pos = new ArrayList<>();

	public GuiTextField textgui = null;
	public GuiTextField hwidtext = null;
	public GuiScreen backGui = null;
	public boolean newedgui = false;

	public GuiHWIDLogin(GuiScreen backGui) {
		this.backGui = backGui;
	}

	public void newButton() {
		newedgui = true;
		pos.add(new XYGui("Login", 100, 107, 150, 137, mc.fontRendererObj));
		pos.add(new XYGui("GetHWID", 160, 107, 210, 137, mc.fontRendererObj));
		pos.add(new XYGui("Back", 100, 147, 150, 177, mc.fontRendererObj));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution res = new ScaledResolution(mc);
		if (!newedgui) {
			newButton();
		}
		if (textgui == null) {
			textgui = new GuiTextField(-1, mc.fontRendererObj, 10, 10, 240, 14);
		}
		if (hwidtext == null) {
			hwidtext = new GuiTextField(-10, mc.fontRendererObj, 10, 30, 240, 14);
			hwidtext.setVisible(false);
		}
		drawDefaultBackground();
		if (!pos.isEmpty())
			for (XYGui gui : pos) {
				gui.drawButton(mouseX, mouseY, partialTicks);
			}

		textgui.drawTextBox();
		hwidtext.drawTextBox();
		Client.instance.notification.render();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (textgui != null) {
			textgui.mouseClicked(mouseX, mouseY, mouseButton);
		}

		if (hwidtext != null) {
			hwidtext.mouseClicked(mouseX, mouseY, mouseButton);
		}
		if (!pos.isEmpty())
			for (XYGui gui : pos) {
				if (gui.onClicked(mouseX, mouseY)) {
					if (mouseButton == 0) {
						if (gui.getName().equalsIgnoreCase("Login")) {
							if (textgui != null && !textgui.getText().isEmpty())
								if (textgui.getText().contains(HWID.format())) {
									HWID.save(HWID.format());
									Client.instance.notification.show(Client.notification("HWID", "Finish Login"));
								} else {
									Client.instance.notification.show(Client.notification("HWID", "Invaild Key"));
								}
						} else if (gui.getName().equalsIgnoreCase("Back")) {
							mc.displayGuiScreen(backGui);
							Client.instance.notification.show(Client.notification("HWID", "See You"));
						} else if (gui.getName().equalsIgnoreCase("GetHWID")) {
							hwidtext.setVisible(true);
							try {
								hwidtext.setText(HWID.getHWID());
								Client.instance.notification
										.show(Client.notification("HWID", "Give HWID to Developer."));
							} catch (NoSuchAlgorithmException e) {
								e.printStackTrace();
								Client.instance.notification.show(Client.notification("HWID", e.getMessage()));
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
								Client.instance.notification.show(Client.notification("HWID", e.getMessage()));
							}
						}
					}
				}
			}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (textgui != null) {
			textgui.textboxKeyTyped(typedChar, keyCode);
		}

		if (hwidtext != null) {
			hwidtext.textboxKeyTyped(typedChar, keyCode);
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void onGuiClosed() {
		textgui = null;
		hwidtext = null;
		super.onGuiClosed();
	}
}

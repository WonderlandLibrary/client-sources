package zamorozka.gui;

import java.awt.Image;


import java.awt.event.KeyEvent;

import java.io.IOException;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import de.Hero.clickgui.util.FontUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.resources.I18n;

public class GuiMain extends GuiScreen {

	private GuiScreen parentScreen;
	private float mousePosx;
	private float mousePosY;

	public GuiMain(GuiScreen guiscreen) {
		this.parentScreen = guiscreen;
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void updateScreen() {

	}

	protected void actionPerformed(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}
		if (guibutton.id == 0) {
			this.mc.displayGuiScreen(this.parentScreen);
		} else if (guibutton.id == 1) {
			this.mc.displayGuiScreen(new GuiAlt(this.parentScreen));
		} else if (guibutton.id == 5) {
			this.mc.displayGuiScreen(new GuiName(this.parentScreen));
		} else if (guibutton.id == 2) {
			this.mc.displayGuiScreen(new GuiProxy(this.parentScreen));
		} else if (guibutton.id == 3) {
			this.mc.displayGuiScreen(new IpCheck(this.parentScreen));

		} else if (guibutton.id == 4) {
			this.mc.displayGuiScreen(new GuiBungeeIpNick(this.parentScreen));

		}

		else if (guibutton.id == 21) {
			this.mc.displayGuiScreen(new GuiScreenResourcePacks(this.parentScreen));

		}
		if (guibutton.id == 233) {
			this.mc.displayGuiScreen(new GuiChat(this));
		}
		if (guibutton.id == 229) {
			Sys.openURL("https://www.donationalerts.com/r/shalopay_");
		}
		if (guibutton.id == 230) {
			Sys.openURL("http://www.youtube.com/c/Øàëîïàé");
		}
		if (guibutton.id == 2310) {
			this.mc.displayGuiScreen(new TestClickGui());
		}
	}

	protected void mouseClicked(int i, int j, int k) throws IOException {
		super.mouseClicked(i, j, k);
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		this.buttonList.clear();
		this.buttonList.add(new GuiButton(1, this.width / -200 + 15, 10, 130, 20 * 1, "AltManager"));
		this.buttonList.add(new GuiButton(6, this.width / -200 + 15, 35, 130, 20 * 1, "ClickGui"));
		this.buttonList.add(new GuiButton(2, this.width / -200 + 15, 60, 130, 20 * 1, "Proxy"));
		this.buttonList.add(new GuiButton(3, this.width / -200 + 15, 85, 130, 20 * 1, "You Ip"));
		this.buttonList.add(new GuiButton(4, this.width / -200 + 15, 110, 130, 20 * 1, "Uuid"));
		this.buttonList.add(new GuiButton(229, this.width / -200 + 15, 165 - 5, 130, 20, I18n.format("Donate", new Object[0])));
		this.buttonList.add(new GuiButton(230, this.width / -200 + 15, 169 + 20, 130, 20 * 1, I18n.format("You§4Tube", new Object[0])));

		this.buttonList.add(new GuiButton(0, this.width / -200 + 15, 324, 130, 20 * 1, "Cancel"));
	}

	public void drawScreen(int i, int j, float f) {

		drawDefaultBackground();
		drawRect(0, 1000, 150, 0, Integer.MIN_VALUE);
		FontUtil.drawTotalCenteredStringWithShadow("Zamorozka main", this.width - FontUtil.getStringWidth("Zamorozka main") - 250, 5, 0xFFFFFFFD);
		GL11.glPushMatrix();
		GL11.glScalef(5.0F, 5.0F, 1.0F);
		GL11.glPopMatrix();
		;
		FontUtil.drawTotalCenteredStringWithShadow("Telegram-https://t.me/oymine_Crash", this.width - FontUtil.getStringWidth("Telegram-https://t.me/oymine_Crash") - 180, this.height - 10, -1);

		super.drawScreen(i, j, f);
	}
}
package me.swezedcode.client.gui.altmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.swezedcode.client.gui.other.CustomButton;
import me.swezedcode.client.utils.render.ColorUtils;
import me.swezedcode.client.utils.render.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiAltManager extends GuiScreen {
	private GuiButton login;
	private GuiButton remove;
	private GuiButton rename;
	public static AltLoginThread loginThread;
	private int offset;
	public Alt selectedAlt;
	private String status;

	public GuiAltManager() {
		this.selectedAlt = null;
		this.status = EnumChatFormatting.GRAY + "Idle...";
	}

	public void actionPerformed(final GuiButton button) throws IOException {
		switch (button.id) {
		case 0: {
			if (this.loginThread == null) {
				this.mc.displayGuiScreen(null);
				break;
			}
			if (!this.loginThread.getStatus().equals(EnumChatFormatting.AQUA + "Logging in...")
					&& !this.loginThread.getStatus().equals(
							EnumChatFormatting.RED + "Do not hit back!" + EnumChatFormatting.AQUA + " Logging in...")) {
				this.mc.displayGuiScreen(null);
				break;
			}
			this.loginThread.setStatus(
					EnumChatFormatting.RED + "Do not hit back!" + EnumChatFormatting.AQUA + " Logging in...");
			break;
		}
		case 1: {
			final String user = this.selectedAlt.getUsername();
			final String pass = this.selectedAlt.getPassword();
			(this.loginThread = new AltLoginThread(user, pass)).start();
			break;
		}
		case 2: {
			if (this.loginThread != null) {
				this.loginThread = null;
			}
			AltManager.registry.remove(this.selectedAlt);
			this.status = "§aRemoved.";
			try {
				GuiAddAlt.saveAlts();
			} catch (Exception ex) {
			}
			this.selectedAlt = null;
			break;
		}
		case 3: {
			this.mc.displayGuiScreen(new GuiAddAlt(this));
			break;
		}
		case 4: {
			this.mc.displayGuiScreen(new GuiAltLogin(this));
			break;
		}
		case 5: {
			final ArrayList<Alt> registry = AltManager.registry;
			final Random random = new Random();
			final Alt randomAlt = registry.get(random.nextInt(AltManager.registry.size()));
			final String user2 = randomAlt.getUsername();
			final String pass2 = randomAlt.getPassword();
			(this.loginThread = new AltLoginThread(user2, pass2)).start();
			break;
		}
		case 6: {
			this.mc.displayGuiScreen(new GuiRenameAlt(this));
			break;
		}
		case 7: {
			final Alt lastAlt = AltManager.lastAlt;
			if (lastAlt != null) {
				final String user3 = lastAlt.getUsername();
				final String pass3 = lastAlt.getPassword();
				(this.loginThread = new AltLoginThread(user3, pass3)).start();
				break;
			}
			if (this.loginThread == null) {
				this.status = "§cThere is no last used alt!";
				break;
			}
			this.loginThread.setStatus("§cThere is no last used alt!");
			break;
		}
		case 8: {
			AltManager.registry.clear();
			GuiAddAlt.loadAlts();
			this.status = "§bReloaded!";
			break;
		}
		}
	}

	@Override
	public void drawScreen(final int par1, final int par2, final float par3) {
		if (Mouse.hasWheel()) {
			final int wheel = Mouse.getDWheel();
			if (wheel < 0) {
				this.offset += 26;
				if (this.offset < 0) {
					this.offset = 0;
				}
			} else if (wheel > 0) {
				this.offset -= 26;
				if (this.offset < 0) {
					this.offset = 0;
				}
			}
		}

		ScaledResolution scaledRes = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/background/index.jpg"));
		Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(),
				scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(),
				scaledRes.getScaledHeight());

		this.drawString(this.fontRendererObj, "Current User: " + this.mc.session.getUsername(), 10, 10, 14540253);
		final FontRenderer fontRendererObj = this.fontRendererObj;
		final StringBuilder sb = new StringBuilder("Account Manager - ");

		this.drawCenteredString(this.fontRendererObj,
				(this.loginThread == null) ? this.status : this.loginThread.getStatus(), this.width / 2, 20, -1);

		RenderUtils.drawBorderedRect(50.0f, 33.0f, this.width - 50, this.height - 50, 1.0f,
				ColorUtils.RGBtoHEX(255, 255, 255, 150), ColorUtils.RGBtoHEX(5, 5, 5, 150));
		GL11.glPushMatrix();
		this.prepareScissorBox(0.0f, 33.0f, this.width, this.height - 50);
		GL11.glEnable(3089);
		int y = 38;
		for (final Alt alt : AltManager.registry) {
			if (this.isAltInArea(y)) {
				String name;
				if (alt.getMask().equals("")) {
					name = alt.getUsername();
				} else {
					name = alt.getMask();
				}
				String pass;
				if (alt.getPassword().equals("")) {
					pass = "§cCracked";
				} else {
					pass = alt.getPassword().replaceAll(".", "*");
				}
				ColorUtils colorUtils;
				if (alt == this.selectedAlt) {
					if (this.isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
						RenderUtils.drawBorderedRect(52.0f, y - this.offset - 4, this.width - 52, y - this.offset + 20,
								1.0f, -16777216, -2142943931);
					} else if (this.isMouseOverAlt(par1, par2, y - this.offset)) {
						RenderUtils.drawBorderedRect(52.0f, y - this.offset - 4, this.width - 52, y - this.offset + 20,
								1.0f, 16777216, -2142088622);
					} else {
						RenderUtils.drawBorderedRect(52.0f, y - this.offset - 4, this.width - 52, y - this.offset + 20,
								1.0f, 16777216, -2144259791);
					}
				} else if (this.isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
					RenderUtils.drawBorderedRect(52.0f, y - this.offset - 4, this.width - 52, y - this.offset + 20,
							1.0f, 16777216, -2146101995);
				} else if (this.isMouseOverAlt(par1, par2, y - this.offset)) {
					RenderUtils.drawBorderedRect(52.0f, y - this.offset - 4, this.width - 52, y - this.offset + 20,
							1.0f, 16777216, -2145180893);
				}
				this.drawCenteredString(this.fontRendererObj, name, this.width / 2, y - this.offset, -1);
				this.drawCenteredString(this.fontRendererObj, pass, this.width / 2, y - this.offset + 10, 5592405);
				y += 26;
			}
		}
		GL11.glDisable(3089);
		GL11.glPopMatrix();
		super.drawScreen(par1, par2, par3);
		if (this.selectedAlt == null) {
			this.login.enabled = false;
			this.remove.enabled = false;
			this.rename.enabled = false;
		} else {
			this.login.enabled = true;
			this.remove.enabled = true;
			this.rename.enabled = true;
		}
		if (Keyboard.isKeyDown(200)) {
			this.offset -= 26;
			if (this.offset < 0) {
				this.offset = 0;
			}
		} else if (Keyboard.isKeyDown(208)) {
			this.offset += 26;
			if (this.offset < 0) {
				this.offset = 0;
			}
		}
	}

	@Override
	public void initGui() {
		this.buttonList.add(new CustomButton(0, this.width / 2 + 116, this.height - 24, 75, 20, "Cancel"));
		this.buttonList.add(this.login = new CustomButton(1, this.width / 2 - 122, this.height - 48, 100, 20, "Login"));
		this.buttonList.add(this.remove = new CustomButton(2, this.width / 2 - 40, this.height - 24, 70, 20, "Remove"));
		this.buttonList.add(new CustomButton(3, this.width / 2 + 4 + 86, this.height - 48, 100, 20, "Add"));
		this.buttonList.add(new CustomButton(4, this.width / 2 - 16, this.height - 48, 100, 20, "Direct Login"));
		this.buttonList.add(new CustomButton(5, this.width / 2 - 122, this.height - 24, 78, 20, "Random"));
		this.buttonList.add(this.rename = new CustomButton(6, this.width / 2 + 38, this.height - 24, 70, 20, "Edit"));
		this.buttonList.add(new CustomButton(7, this.width / 2 - 189, this.height - 24, 60, 20, "Last Alt"));
		this.buttonList.add(new CustomButton(8, this.width / 2 - 189, this.height - 48, 60, 20, "Load Alts"));
		this.login.enabled = false;
		this.remove.enabled = false;
		this.rename.enabled = false;
	}

	private boolean isAltInArea(final int y) {
		return y - this.offset <= this.height - 50;
	}

	private boolean isMouseOverAlt(final int x, final int y, final int y1) {
		return x >= 52 && y >= y1 - 4 && x <= this.width - 52 && y <= y1 + 20 && x >= 0 && y >= 33 && x <= this.width
				&& y <= this.height - 50;
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		if (this.offset < 0) {
			this.offset = 0;
		}
		int y = 38 - this.offset;
		for (final Alt alt : AltManager.registry) {
			if (this.isMouseOverAlt(par1, par2, y)) {
				if (alt == this.selectedAlt) {
					this.actionPerformed((GuiButton) this.buttonList.get(1));
					return;
				}
				this.selectedAlt = alt;
			}
			y += 26;
		}
		try {
			super.mouseClicked(par1, par2, par3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
		final ScaledResolution scale = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		final int factor = scale.getScaleFactor();
		GL11.glScissor((int) (x * factor), (int) ((scale.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor),
				(int) ((y2 - y) * factor));
	}
}

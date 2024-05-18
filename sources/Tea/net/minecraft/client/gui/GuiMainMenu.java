package net.minecraft.client.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import com.google.common.collect.Lists;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.altmanager.GuiAltManager;
import me.swezedcode.client.gui.alts.GuiDirectLogin;
import me.swezedcode.client.gui.other.CustomButton;
import me.swezedcode.client.gui.other.GuiClientSettings;
import me.swezedcode.client.gui.other.MMButton;
import me.swezedcode.client.gui.other.ParticleTest;

import me.swezedcode.client.utils.render.ColorUtils;
import me.swezedcode.client.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import optifine.l1l1l1l1l1ll1l1l1l111l1l1l;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
	public int colorcode = 0x00000000;
	private ParticleTest pEngine;
	private static final AtomicInteger field_175373_f = new AtomicInteger(0);
	private static final Logger logger = LogManager.getLogger();
	private static final Random RANDOM = new Random();
	private float updateCounter;
	private String splashText;
	private GuiButton buttonResetDemo;
	private int panoramaTimer;
	private DynamicTexture viewportTexture;
	private Object threadLock;
	private String openGLWarning1;
	private String openGLWarning2;
	private String openGLWarningLink;
	private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
	private static final ResourceLocation minecraftTitleTextures = new ResourceLocation(
			"textures/gui/title/minecraft.png");
	private static final ResourceLocation[] titlePanoramaPaths = {
			new ResourceLocation("textures/gui/title/background/panorama_0.png"),
			new ResourceLocation("textures/gui/title/background/panorama_1.png"),
			new ResourceLocation("textures/gui/title/background/panorama_2.png"),
			new ResourceLocation("textures/gui/title/background/panorama_3.png"),
			new ResourceLocation("textures/gui/title/background/panorama_4.png"),
			new ResourceLocation("textures/gui/title/background/panorama_5.png") };
	public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here"
			+ EnumChatFormatting.RESET + " for more information.";
	public static boolean killSwitch = true;
	private int field_92024_r;
	private int field_92023_s;
	private int field_92022_t;
	private int field_92021_u;
	private int field_92020_v;
	private int field_92019_w;
	private ResourceLocation backgroundTexture;
	private GuiButton realmsButton;

	/* Error */
	public GuiMainMenu() {
		this.splashText = field_96138_a;
		this.splashText = "missingno";
		BufferedReader var1 = null;

		try {
			ArrayList var2 = Lists.newArrayList();
			var1 = new BufferedReader(new InputStreamReader(
					Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(),
					Charsets.UTF_8));
			String var3;

			while ((var3 = var1.readLine()) != null) {
				var3 = var3.trim();

				if (!var3.isEmpty()) {
					var2.add(var3);
				}
			}

			if (!var2.isEmpty()) {
				do {
					Random rand = new Random();
					this.splashText = (String) var2.get(rand.nextInt(var2.size()));
				} while (this.splashText.hashCode() == 125780783);
			}
		} catch (IOException var12) {
			;
		} finally {
			if (var1 != null) {
				try {
					var1.close();
				} catch (IOException var11) {
					;
				}
			}
		}
	}

	public void updateScreen() {
		this.panoramaTimer += 1;
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {
	}

	public void initGui() {
		
		
		this.viewportTexture = new DynamicTexture(256, 256);
		this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background",
				this.viewportTexture);
		Calendar var1 = Calendar.getInstance();
		var1.setTime(new Date());
		if ((var1.get(2) + 1 == 11) && (var1.get(5) == 9)) {
			this.splashText = "Happy birthday, ez!";
		} else if ((var1.get(2) + 1 == 6) && (var1.get(5) == 1)) {
			this.splashText = "Happy birthday, Notch!";
		} else if ((var1.get(2) + 1 == 12) && (var1.get(5) == 24)) {
			this.splashText = "Merry X-mas!";
		} else if ((var1.get(2) + 1 == 1) && (var1.get(5) == 1)) {
			this.splashText = "Happy new year!";
		} else if ((var1.get(2) + 1 == 10) && (var1.get(5) == 31)) {
			this.splashText = "OOoooOOOoooo! Spooky!";
		}
		int var3 = this.height / 4 + 48;
		if (this.mc.isDemo()) {
			addDemoButtons(var3, 24);
		} else {
			addSingleplayerMultiplayerButtons(var3, 24);
		}
		this.buttonList
				.add(new MMButton(0, this.width / 2 - 30, var3 + 72 - 35, 60, 10, I18n.format("menu.options", new Object[0])));
		this.buttonList.add(
				new MMButton(5, this.width / 2 - 30, var3 + 72 + 24 - 40, 60, 10, I18n.format("Client Settings", new Object[0])));
		this.buttonList.add(
				new MMButton(4, this.width / 2 - 30, var3 + 72 + 24 - 20, 60, 10, I18n.format("menu.quit", new Object[0])));
		this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
		this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
		int var5 = Math.max(this.field_92023_s, this.field_92024_r);
		this.field_92022_t = ((this.width - var5) / 2);
		this.field_92021_u = (((GuiButton) this.buttonList.get(0)).yPosition - 24);
		this.field_92020_v = (this.field_92022_t + var5);
		this.field_92019_w = (this.field_92021_u + 24);
	}

	private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
		this.buttonList.add(
				new MMButton(1, this.width / 2 - 30, p_73969_1_ - 20, 60, 10, I18n.format("menu.singleplayer", new Object[0])));
		this.buttonList.add(new MMButton(2, this.width / 2 - 30, p_73969_1_ + p_73969_2_ - 25, 60, 10,
				I18n.format("menu.multiplayer", new Object[0])));
		this.buttonList.add(this.realmsButton = new MMButton(420, this.width / 2 - 30, p_73969_1_ + p_73969_2_ * 2 - 30, 60, 10,
				I18n.format("Alt Manager", new Object[0])));
	}

	private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
		this.buttonList.add(
				new MMButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
		this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1,
				I18n.format("menu.resetdemo", new Object[0])));
		ISaveFormat var3 = this.mc.getSaveLoader();
		WorldInfo var4 = var3.getWorldInfo("Demo_World");
		if (var4 == null) {
			this.buttonResetDemo.enabled = false;
		}
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			//l1l1l1l1l1ll1l1l1l111l1l1l.checkUUID(AES.decrypt("kDNr3fycYMwAjAdr9oH5z6TDsqFVdonu1WEXhK4aKhX7k0bbsrBXTQkGzfolcCF8", "npe"));
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}
		if (button.id == 5) {
			//l1l1l1l1l1ll1l1l1l111l1l1l.checkUUID(AES.decrypt("kDNr3fycYMwAjAdr9oH5z6TDsqFVdonu1WEXhK4aKhX7k0bbsrBXTQkGzfolcCF8", "npe"));
			this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
		}
		if (button.id == 1) {
			//l1l1l1l1l1ll1l1l1l111l1l1l.checkUUID(AES.decrypt("kDNr3fycYMwAjAdr9oH5z6TDsqFVdonu1WEXhK4aKhX7k0bbsrBXTQkGzfolcCF8", "npe"));
			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		}
		if (button.id == 2) {
			//l1l1l1l1l1ll1l1l1l111l1l1l.checkUUID(AES.decrypt("kDNr3fycYMwAjAdr9oH5z6TDsqFVdonu1WEXhK4aKhX7k0bbsrBXTQkGzfolcCF8", "npe"));
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}
		if ((button.id == 14) && (this.realmsButton.visible)) {
			//l1l1l1l1l1ll1l1l1l111l1l1l.checkUUID(AES.decrypt("kDNr3fycYMwAjAdr9oH5z6TDsqFVdonu1WEXhK4aKhX7k0bbsrBXTQkGzfolcCF8", "npe"));
			switchToRealms();
		}
		if (button.id == 4) {
			//l1l1l1l1l1ll1l1l1l111l1l1l.checkUUID(AES.decrypt("kDNr3fycYMwAjAdr9oH5z6TDsqFVdonu1WEXhK4aKhX7k0bbsrBXTQkGzfolcCF8", "npe"));
			this.mc.shutdown();
		}
		if(button.id == 5) {
			mc.displayGuiScreen(new GuiClientSettings());
		}
		if (button.id == 11) {
			this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
		}
		if (button.id == 12) {
			ISaveFormat var2 = this.mc.getSaveLoader();
			WorldInfo var3 = var2.getWorldInfo("Demo_World");
			if (var3 != null) {
				GuiYesNo var4 = GuiSelectWorld.func_152129_a(this, var3.getWorldName(), 12);
				this.mc.displayGuiScreen(var4);
			}
		}
		if (button.id == 420) {
			mc.displayGuiScreen(new GuiAltManager());
		}
	}

	private void switchToRealms() {
		RealmsBridge var1 = new RealmsBridge();
		var1.switchToRealms(this);
	}

	public void confirmClicked(boolean result, int id) {
		if ((result) && (id == 12)) {
			ISaveFormat var6 = this.mc.getSaveLoader();
			var6.flushCache();
			var6.deleteWorldDirectory("Demo_World");
			this.mc.displayGuiScreen(this);
		} else if (id == 13) {
			if (result) {
				try {
					Class var3 = Class.forName("java.awt.Desktop");
					Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
					var3.getMethod("browse", new Class[] { URI.class }).invoke(var4,
							new Object[] { new URI(this.openGLWarningLink) });
				} catch (Throwable var5) {
					logger.error("Couldn't open link", var5);
				}
			}
			this.mc.displayGuiScreen(this);
		}
	}

	private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_) {
		Tessellator var4 = Tessellator.getInstance();
		WorldRenderer var5 = var4.getWorldRenderer();
		GlStateManager.matrixMode(5889);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
		GlStateManager.matrixMode(5888);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.disableCull();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		byte var6 = 8;
		for (int var7 = 0; var7 < var6 * var6; var7++) {
			GlStateManager.pushMatrix();
			float var8 = (var7 % var6 / var6 - 0.5F) / 64.0F;
			float var9 = (var7 / var6 / var6 - 0.5F) / 64.0F;
			float var10 = 0.0F;
			GlStateManager.translate(var8, var9, var10);
			GlStateManager.rotate(MathHelper.sin((this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F,
					0.0F, 0.0F);
			GlStateManager.rotate(-(this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);
			for (int var11 = 0; var11 < 6; var11++) {
				GlStateManager.pushMatrix();
				if (var11 == 1) {
					GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
				}
				if (var11 == 2) {
					GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				}
				if (var11 == 3) {
					GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
				}
				if (var11 == 4) {
					GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				}
				if (var11 == 5) {
					GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				}
				this.mc.getTextureManager().bindTexture(titlePanoramaPaths[var11]);
				var5.startDrawingQuads();
				float var12 = 0.0F;
				var5.addVertexWithUV(-1.0D, -1.0D, 1.0D, 0.0F + var12, 0.0F + var12);
				var5.addVertexWithUV(1.0D, -1.0D, 1.0D, 1.0F - var12, 0.0F + var12);
				var5.addVertexWithUV(1.0D, 1.0D, 1.0D, 1.0F - var12, 1.0F - var12);
				var5.addVertexWithUV(-1.0D, 1.0D, 1.0D, 0.0F + var12, 1.0F - var12);
				var4.draw();
				GlStateManager.popMatrix();
			}
			GlStateManager.popMatrix();
			GlStateManager.colorMask(true, true, true, false);
		}
		var5.setTranslation(0.0D, 0.0D, 0.0D);
		GlStateManager.colorMask(true, true, true, true);
		GlStateManager.matrixMode(5889);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);
		GlStateManager.popMatrix();
		GlStateManager.depthMask(true);
		GlStateManager.enableCull();
		GlStateManager.enableDepth();
	}

	private void rotateAndBlurSkybox(float p_73968_1_) {
		this.mc.getTextureManager().bindTexture(this.backgroundTexture);
		GL11.glTexParameteri(3553, 10241, 9729);
		GL11.glTexParameteri(3553, 10240, 9729);
		GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.colorMask(true, true, true, false);
		Tessellator var2 = Tessellator.getInstance();
		WorldRenderer var3 = var2.getWorldRenderer();
		var3.startDrawingQuads();
		GlStateManager.disableAlpha();
		byte var4 = 3;
		for (int var5 = 0; var5 < var4; var5++) {
			int var6 = this.width;
			int var7 = this.height;
			float var8 = (var5 - var4 / 2) / 256.0F;
			var3.addVertexWithUV(var6, var7, this.zLevel, 0.0F + var8, 1.0D);
			var3.addVertexWithUV(var6, 0.0D, this.zLevel, 1.0F + var8, 1.0D);
			var3.addVertexWithUV(0.0D, 0.0D, this.zLevel, 1.0F + var8, 0.0D);
			var3.addVertexWithUV(0.0D, var7, this.zLevel, 0.0F + var8, 0.0D);
		}
		var2.draw();
		GlStateManager.enableAlpha();
		GlStateManager.colorMask(true, true, true, true);
	}
	
	private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_) {
		this.mc.getFramebuffer().unbindFramebuffer();
		GlStateManager.viewport(0, 0, 256, 256);
		drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
		rotateAndBlurSkybox(p_73971_3_);
		rotateAndBlurSkybox(p_73971_3_);
		rotateAndBlurSkybox(p_73971_3_);
		rotateAndBlurSkybox(p_73971_3_);
		rotateAndBlurSkybox(p_73971_3_);
		rotateAndBlurSkybox(p_73971_3_);
		rotateAndBlurSkybox(p_73971_3_);
		this.mc.getFramebuffer().bindFramebuffer(true);
		GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		Tessellator var4 = Tessellator.getInstance();
		WorldRenderer var5 = var4.getWorldRenderer();
		var5.startDrawingQuads();
		float var6 = this.width > this.height ? 120.0F / this.width : 120.0F / this.height;
		float var7 = this.height * var6 / 256.0F;
		float var8 = this.width * var6 / 256.0F;
		int var9 = this.width;
		int var10 = this.height;
		var5.addVertexWithUV(0.0D, var10, this.zLevel, 0.5F - var7, 0.5F + var8);
		var5.addVertexWithUV(var9, var10, this.zLevel, 0.5F - var7, 0.5F - var8);
		var5.addVertexWithUV(var9, 0.0D, this.zLevel, 0.5F + var7, 0.5F - var8);
		var5.addVertexWithUV(0.0D, 0.0D, this.zLevel, 0.5F + var7, 0.5F + var8);
		var4.draw();
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (!Entity.validEntity || !Entity.validEntityID) {
			Minecraft.getMinecraft().shutdown();
		}
		GlStateManager.disableAlpha();
		GlStateManager.enableAlpha();
		Tessellator var4 = Tessellator.getInstance();
		WorldRenderer var5 = var4.getWorldRenderer();
		short var6 = 274;
		int var7 = this.width / 2 - var6 / 2;
		byte var8 = 30;
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/background/index.jpg"));
		drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight());
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		RenderUtils.aaa(width / 2 - 45, height / 4 + 18, width / 2 + 45, height / 3 + 125, 1.5F, -1728053248, Integer.MIN_VALUE);
		Tea.mainMenuFont.drawCenteredString(Tea.mainMenuFont, Tea.getInstance().getName() + " b" + Tea.getInstance().getVersion(), this.width / 2, 60, 0xFFFFFFFF);
		
		String var11 = "Special thanks to Angeloxl, Brady/Zero, XTasyCode, iHaq and Volcano";
		drawString(this.fontRendererObj, var11, this.width - this.fontRendererObj.getStringWidth(var11) - 2,
				this.height - 10, -1);
		if ((this.openGLWarning1 != null) && (this.openGLWarning1.length() > 0)) {
			drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1,
					1428160512);
			drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
			drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2,
					((GuiButton) this.buttonList.get(0)).yPosition - 12, -1);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}

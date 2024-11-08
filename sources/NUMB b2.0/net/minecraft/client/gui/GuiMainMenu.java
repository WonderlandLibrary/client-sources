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
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import com.google.common.collect.Lists;

import de.vitox.particle.Particle;
import de.vitox.particle.ParticleGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import us.loki.legit.Client;
import us.loki.legit.altmanager.GuiAltManager;
import us.loki.legit.gui.Info;
import us.loki.legit.gui.GuiButtonDark;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
	public static boolean update;

	private static final AtomicInteger field_175373_f = new AtomicInteger(0);
	private static final Logger logger = LogManager.getLogger();
	private static final Random field_175374_h = new Random();

	// TODO Particle
	private ParticleGenerator particles;
	private Random random = new Random();

	/** Counts the number of screen updates. */
	private float updateCounter;

	/** The splash message. */
	private String splashText;
	private GuiButton buttonResetDemo;

	/** Timer used to rotate the panorama, increases every tick. */
	private int panoramaTimer;

	/**
	 * Texture allocated for the current viewport of the main menu's panorama
	 * background.
	 */
	private DynamicTexture viewportTexture;
	private boolean field_175375_v = true;
	private final Object field_104025_t = new Object();
	private String field_92025_p;
	private String field_146972_A;
	private String field_104024_v;
	private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
	private static final ResourceLocation background = new ResourceLocation("loki/20-2.jpg");
	private static final ResourceLocation background2 = new ResourceLocation("loki/tqtxnif.jpg");
	private static final ResourceLocation minecraftTitleTextures = new ResourceLocation(
			"textures/gui/title/minecraft.png");

	/** An array of all the paths to the panorama pictures. */
	private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] {
			new ResourceLocation("textures/gui/title/background/panorama_0.png"),
			new ResourceLocation("textures/gui/title/background/panorama_1.png"),
			new ResourceLocation("textures/gui/title/background/panorama_2.png"),
			new ResourceLocation("textures/gui/title/background/panorama_3.png"),
			new ResourceLocation("textures/gui/title/background/panorama_4.png"),
			new ResourceLocation("textures/gui/title/background/panorama_5.png") };
	public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here"
			+ EnumChatFormatting.RESET + " for more information.";
	private int field_92024_r;
	private int field_92023_s;
	private int field_92022_t;
	private int field_92021_u;
	private int field_92020_v;
	private int field_92019_w;
	private ResourceLocation field_110351_G;
	private GuiButton field_175372_K;
	private static final String __OBFID = "CL_00001154";

	public GuiMainMenu() {
		this.field_146972_A = field_96138_a;
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
					this.splashText = (String) var2.get(field_175374_h.nextInt(var2.size()));
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

		this.updateCounter = field_175374_h.nextFloat();
		this.field_92025_p = "";

		if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
			this.field_92025_p = I18n.format("title.oldgl1", new Object[0]);
			this.field_146972_A = I18n.format("title.oldgl2", new Object[0]);
			this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
		}
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		++this.panoramaTimer;
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is the
	 * equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		this.viewportTexture = new DynamicTexture(256, 256);
		this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
		Calendar var1 = Calendar.getInstance();
		var1.setTime(new Date());

		if (var1.get(2) + 1 == 11 && var1.get(5) == 9) {
			this.splashText = "Happy birthday, ez!";
		} else if (var1.get(2) + 1 == 6 && var1.get(5) == 1) {
			this.splashText = "Happy birthday, Notch!";
		} else if (var1.get(2) + 1 == 12 && var1.get(5) == 24) {
			this.splashText = "Merry X-mas!";
		} else if (var1.get(2) + 1 == 1 && var1.get(5) == 1) {
			this.splashText = "Happy new year!";
		} else if (var1.get(2) + 1 == 10 && var1.get(5) == 31) {
			this.splashText = "OOoooOOOoooo! Spooky!";
		}

		boolean var2 = true;
		int var3 = this.height / 4 + 48;

		if (this.mc.isDemo()) {
			this.addDemoButtons(var3, 24);
		} else {
			this.addSingleplayerMultiplayerButtons(var3, 24);
		}
		this.buttonList.add(
				new GuiButtonDark(5, width / 2 - 100, var3 + 70, 98, 20, I18n.format("Language", new Object[0])));
		this.buttonList.add(new GuiButtonDark(0, this.width / 2 - 100, var3 + 80 + 12, 98, 20,
				I18n.format("Options", new Object[0])));
		this.buttonList.add(new GuiButtonDark(4, this.width / 2 + 2, var3 + 80 + 12, 98, 20,
				I18n.format("Quit", new Object[0])));
		this.buttonList.add(new GuiButtonDark(996, this.width / 2 + 2, var3 + 58 + 12, 98, 20,
				I18n.format("Info", new Object[0])));
		Object var4 = this.field_104025_t;

		synchronized (this.field_104025_t) {
			this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
			this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
			int var5 = Math.max(this.field_92023_s, this.field_92024_r);
			this.field_92022_t = (this.width - var5) / 2;
			this.field_92021_u = ((GuiButton) this.buttonList.get(0)).yPosition - 24;
			this.field_92020_v = this.field_92022_t + var5;
			this.field_92019_w = this.field_92021_u + 24;
		}
		// TODO Particle
		this.particles = new ParticleGenerator(100, this.width, this.height);

	}

	/**
	 * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have
	 * bought the game.
	 */
	private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
		this.buttonList.add(
				new GuiButtonDark(1, this.width / 2 - 100, p_73969_1_, I18n.format("Singleplayer", new Object[0])));
		this.buttonList.add(new GuiButtonDark(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1,
				I18n.format("Multiplayer", new Object[0])));
		this.buttonList.add(this.field_175372_K = new GuiButtonDark(69, this.width / 2 - 100,
				p_73969_1_ + p_73969_2_ * 2, I18n.format("Alt Manager", new Object[0])));
	}

	/**
	 * Adds Demo buttons on Main Menu for players who are playing Demo.
	 */
	private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
		this.buttonList
				.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
		this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1,
				I18n.format("menu.resetdemo", new Object[0])));
		ISaveFormat var3 = this.mc.getSaveLoader();
		WorldInfo var4 = var3.getWorldInfo("Demo_World");

		if (var4 == null) {
			this.buttonResetDemo.enabled = false;
		}
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 996) {
			try
	        {
	          Class<?> oclass = Class.forName("java.awt.Desktop");
	          Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
	          oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, 
	            new Object[] { new URI("https://pastebin.com/raw/DwAdCP0r") });
	        }
	        catch (Throwable throwable)
	        {
	          throwable.printStackTrace();
	        }
		}
		if (button.id == 69) {
			this.mc.displayGuiScreen(new GuiAltManager());
		}
		if (button.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}

		if (button.id == 5) {
			this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
		}

		if (button.id == 1) {
			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		}

		if (button.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

		if (button.id == 14 && this.field_175372_K.visible) {
			this.switchToRealms();
		}

		if (button.id == 4) {
			this.mc.shutdown();
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
	}

	private void switchToRealms() {
		RealmsBridge var1 = new RealmsBridge();
		var1.switchToRealms(this);
	}

	public void confirmClicked(boolean result, int id) {
		if (result && id == 12) {
			ISaveFormat var6 = this.mc.getSaveLoader();
			var6.flushCache();
			var6.deleteWorldDirectory("Demo_World");
			this.mc.displayGuiScreen(this);
		} else if (id == 13) {
			if (result) {
				try {
					Class var3 = Class.forName("java.awt.Desktop");
					Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
					var3.getMethod("browse", new Class[] { URI.class }).invoke(var4,
							new Object[] { new URI(this.field_104024_v) });
				} catch (Throwable var5) {
					logger.error("Couldn\'t open link", var5);
				}
			}

			this.mc.displayGuiScreen(this);
		}
	}

	/**
	 * Draws the main menu panorama
	 */
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

		for (int var7 = 0; var7 < var6 * var6; ++var7) {
			GlStateManager.pushMatrix();
			float var8 = ((float) (var7 % var6) / (float) var6 - 0.5F) / 64.0F;
			float var9 = ((float) (var7 / var6) / (float) var6 - 0.5F) / 64.0F;
			float var10 = 0.0F;
			GlStateManager.translate(var8, var9, var10);
			GlStateManager.rotate(MathHelper.sin(((float) this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F,
					1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-((float) this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);

			for (int var11 = 0; var11 < 6; ++var11) {
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
				var5.func_178974_a(16777215, 255 / (var7 + 1));
				float var12 = 0.0F;
				var5.addVertexWithUV(-1.0D, -1.0D, 1.0D, (double) (0.0F + var12), (double) (0.0F + var12));
				var5.addVertexWithUV(1.0D, -1.0D, 1.0D, (double) (1.0F - var12), (double) (0.0F + var12));
				var5.addVertexWithUV(1.0D, 1.0D, 1.0D, (double) (1.0F - var12), (double) (1.0F - var12));
				var5.addVertexWithUV(-1.0D, 1.0D, 1.0D, (double) (0.0F + var12), (double) (1.0F - var12));
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

	/**
	 * Rotate and blurs the skybox view in the main menu
	 */
	private void rotateAndBlurSkybox(float p_73968_1_) {
		this.mc.getTextureManager().bindTexture(this.field_110351_G);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.colorMask(true, true, true, false);
		Tessellator var2 = Tessellator.getInstance();
		WorldRenderer var3 = var2.getWorldRenderer();
		var3.startDrawingQuads();
		GlStateManager.disableAlpha();
		byte var4 = 3;

		for (int var5 = 0; var5 < var4; ++var5) {
			var3.func_178960_a(1.0F, 1.0F, 1.0F, 1.0F / (float) (var5 + 1));
			int var6 = this.width;
			int var7 = this.height;
			float var8 = (float) (var5 - var4 / 2) / 256.0F;
			var3.addVertexWithUV((double) var6, (double) var7, (double) this.zLevel, (double) (0.0F + var8), 1.0D);
			var3.addVertexWithUV((double) var6, 0.0D, (double) this.zLevel, (double) (1.0F + var8), 1.0D);
			var3.addVertexWithUV(0.0D, 0.0D, (double) this.zLevel, (double) (1.0F + var8), 0.0D);
			var3.addVertexWithUV(0.0D, (double) var7, (double) this.zLevel, (double) (0.0F + var8), 0.0D);
		}

		var2.draw();
		GlStateManager.enableAlpha();
		GlStateManager.colorMask(true, true, true, true);
	}

	/**
	 * Renders the skybox in the main menu
	 */
	private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_) {
		this.mc.getFramebuffer().unbindFramebuffer();
		GlStateManager.viewport(0, 0, 256, 256);
		this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
		this.rotateAndBlurSkybox(p_73971_3_);
		this.rotateAndBlurSkybox(p_73971_3_);
		this.rotateAndBlurSkybox(p_73971_3_);
		this.rotateAndBlurSkybox(p_73971_3_);
		this.rotateAndBlurSkybox(p_73971_3_);
		this.rotateAndBlurSkybox(p_73971_3_);
		this.rotateAndBlurSkybox(p_73971_3_);
		this.mc.getFramebuffer().bindFramebuffer(true);
		GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		Tessellator var4 = Tessellator.getInstance();
		WorldRenderer var5 = var4.getWorldRenderer();
		var5.startDrawingQuads();
		float var6 = this.width > this.height ? 120.0F / (float) this.width : 120.0F / (float) this.height;
		float var7 = (float) this.height * var6 / 256.0F;
		float var8 = (float) this.width * var6 / 256.0F;
		var5.func_178960_a(1.0F, 1.0F, 1.0F, 1.0F);
		int var9 = this.width;
		int var10 = this.height;
		var5.addVertexWithUV(0.0D, (double) var10, (double) this.zLevel, (double) (0.5F - var7),
				(double) (0.5F + var8));
		var5.addVertexWithUV((double) var9, (double) var10, (double) this.zLevel, (double) (0.5F - var7),
				(double) (0.5F - var8));
		var5.addVertexWithUV((double) var9, 0.0D, (double) this.zLevel, (double) (0.5F + var7), (double) (0.5F - var8));
		var5.addVertexWithUV(0.0D, 0.0D, (double) this.zLevel, (double) (0.5F + var7), (double) (0.5F + var8));
		var4.draw();
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		ScaledResolution sl = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("loki/20-2.jpg"));
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, sl.getScaledWidth(), sl.getScaledHeight(),
				sl.getScaledWidth(), sl.getScaledHeight());
		Gui.drawRect(0, 0, width, height, 0xFF00FF);
		Double scale = 3.0;
		GL11.glPushMatrix();
		GlStateManager.scale(scale, scale, scale);
		this.drawCenteredString(this.fontRendererObj, "NUMB", width / 6, 20, 0x00FF00);
		GlStateManager.scale(1.0 / scale, 1.0 / scale, 1.0 / scale);
		GL11.glPopMatrix();

		String var10 = "§kfuckwindows §R§7§Lmade by§R§b§L loki §R§kfuckwindows";

		this.drawString(this.fontRendererObj, var10, 2, this.height - 10, -1);
		String var11 = "Copyright Mojang AB & loki.";
		this.drawString(this.fontRendererObj, var11, this.width - this.fontRendererObj.getStringWidth(var11) - 2,
				this.height - 10, -1);

		if (this.field_92025_p != null && this.field_92025_p.length() > 0) {
			drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1,
					1428160512);
			this.drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
			this.drawString(this.fontRendererObj, this.field_146972_A, (this.width - this.field_92024_r) / 2,
					((GuiButton) this.buttonList.get(0)).yPosition - 12, -1);
		}
		// TODO Particle

		for (Particle p : particles.particles) {
			for (Particle p2 : particles.particles) {
				int xx = (int) (MathHelper.cos(0.1F * (p.x + p.k)) * 10.0F);
				int xx2 = (int) (MathHelper.cos(0.1F * (p2.x + p2.k)) * 10.0F);

				boolean mouseOver = (mouseX >= p.x + xx - 95) && (mouseY >= p.y - 90) && (mouseX <= p.x)
						&& (mouseY <= p.y);

				if (mouseOver) {
					if (mouseY >= p.y - 80 && mouseX >= p2.x - 100 && mouseY >= p2.y && mouseY <= p2.y + 70
							&& mouseX <= p2.x) {

						int maxDistance = 100;

						final int xDif = p.x - mouseX;
						final int yDif = p.y - mouseY;
						final int distance = (int) Math.sqrt(xDif * xDif + yDif + yDif);

						final int xDif1 = p2.x - mouseX;
						final int yDif1 = p2.y - mouseY;
						final int distance2 = (int) Math.sqrt(xDif1 * xDif1 + yDif1 + yDif1);

						if (distance < maxDistance && distance2 < maxDistance) {

							GL11.glPushMatrix();
							GL11.glEnable(GL11.GL_LINE_SMOOTH);
							GL11.glDisable(GL11.GL_DEPTH_TEST);
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							GL11.glDisable(GL11.GL_TEXTURE_2D);
							GL11.glDepthMask(false);
							GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
							GL11.glEnable(GL11.GL_BLEND);
							GL11.glLineWidth(1.5F);
							GL11.glBegin(GL11.GL_LINES);

							GL11.glVertex2d(p.x + xx, p.y);
							GL11.glVertex2d(p2.x + xx2, p2.y);
							GL11.glEnd();
							GL11.glPopMatrix();

							if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
								if (p2.x > mouseX) {
									p2.y -= random.nextInt(5);
								}
								if (p2.y < mouseY) {
									p2.x += random.nextInt(5);
								}

							}

						}
					}
				}
			}

		}

		this.particles.drawParticles();

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void renderBackground(int par1, int par2) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		Tessellator var3 = Tessellator.instance;
		var3.getWorldRenderer().startDrawingQuads();
		var3.getWorldRenderer().addVertexWithUV(0.0D, (double) par2, -90.0D, 0.0D, 1.0D);
		var3.getWorldRenderer().addVertexWithUV((double) par1, (double) par2, -90.0D, 1.0D, 1.0D);
		var3.getWorldRenderer().addVertexWithUV((double) par1, 0.0D, -90.0D, 1.0D, 0.0D);
		var3.getWorldRenderer().addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var3.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		Object var4 = this.field_104025_t;

		synchronized (this.field_104025_t) {
			if (this.field_92025_p.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v
					&& mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
				GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
				var5.disableSecurityWarning();
				this.mc.displayGuiScreen(var5);
			}
		}
	}
}

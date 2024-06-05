package xyz.vergoclient.ui.guis;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.security.account.AccountUtils;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.ui.guis.altManager.pages.DirectLogin;
import xyz.vergoclient.util.main.ChatUtils;
import xyz.vergoclient.util.main.DisplayUtils;
import xyz.vergoclient.util.main.FormattingUtil;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
	private static final AtomicInteger field_175373_f = new AtomicInteger(0);
	private static final Logger logger = LogManager.getLogger();
	private static final Random field_175374_h = new Random();

	/** Counts the number of screen updates. */
	private float updateCounter;

	/** The splash message. */
	private String splashText;
	private GuiButton buttonResetDemo;

	/** Timer used to rotate the panorama, increases every tick. */
	private int panoramaTimer;

	/**
	 * Texture allocated for the current viewport of the main menu's panorama background.
	 */
	private DynamicTexture viewportTexture;
	private boolean field_175375_v = true;
	private final Object field_104025_t = new Object();
	private String field_92025_p;
	private String field_146972_A;
	private String field_104024_v;
	private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
	private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");

	/** An array of all the paths to the panorama pictures. */
	private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] {new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
	public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
	private int field_92024_r;
	private int field_92023_s;
	private int field_92022_t;
	private int field_92021_u;
	private int field_92020_v;
	private int field_92019_w;
	private ResourceLocation field_110351_G;
	private GuiButton field_175372_K;
	private static final String __OBFID = "CL_00001154";

	public GuiMainMenu()
	{
		DisplayUtils.setCustomTitle("Vergo " + Vergo.version + " | Main Menu");

		this.field_146972_A = field_96138_a;
		this.splashText = "oop";
		BufferedReader var1 = null;

		try
		{
			ArrayList var2 = Lists.newArrayList();
			var1 = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
			String var3;

			while ((var3 = var1.readLine()) != null)
			{
				var3 = var3.trim();

				if (!var3.isEmpty())
				{
					var2.add(var3);
				}
			}

			if (!var2.isEmpty())
			{
				do
				{
					this.splashText = (String)var2.get(field_175374_h.nextInt(var2.size()));
				}
				while (this.splashText.hashCode() == 125780783);
			}
		}
		catch (IOException var12)
		{
			;
		}
		finally
		{
			if (var1 != null)
			{
				try
				{
					var1.close();
				}
				catch (IOException var11)
				{
					;
				}
			}
		}

		this.updateCounter = field_175374_h.nextFloat();
		this.field_92025_p = "";

		/*if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported())
		{
			this.field_92025_p = I18n.format("title.oldgl1", new Object[0]);
			this.field_146972_A = I18n.format("title.oldgl2", new Object[0]);
			this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
		}*/
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		++this.panoramaTimer;
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in single-player
	 */
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char typedChar, int keyCode) throws IOException {}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		this.viewportTexture = new DynamicTexture(256, 256);
		this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
		Calendar var1 = Calendar.getInstance();
		var1.setTime(new Date());

		if (var1.get(2) + 1 == 11 && var1.get(5) == 9)
		{
			this.splashText = "Happy birthday, ez!";
		}
		else if (var1.get(2) + 1 == 6 && var1.get(5) == 1)
		{
			this.splashText = "Happy birthday, Notch!";
		}
		else if (var1.get(2) + 1 == 12 && var1.get(5) == 24)
		{
			this.splashText = "Merry X-mas!";
		}
		else if (var1.get(2) + 1 == 1 && var1.get(5) == 1)
		{
			this.splashText = "Happy new year!";
		}
		else if (var1.get(2) + 1 == 10 && var1.get(5) == 31)
		{
			this.splashText = "OOoooOOOoooo! Spooky!";
		}

		boolean var2 = true;
		int var3 = this.height / 4 + 48;

		if (this.mc.isDemo())
		{
			this.addDemoButtons(var3, 24);
		}
		else
		{
			this.addSingleplayerMultiplayerButtons(var3, 24);
		}

		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, height / 2 + 85, 98, 20, I18n.format("menu.options", new Object[0])));
		this.buttonList.add(new GuiButton(4, this.width / 2 + 2, height / 2 + 85, 98, 20, I18n.format("menu.quit", new Object[0])));
		Object var4 = this.field_104025_t;

		synchronized (this.field_104025_t)
		{
			this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
			this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
			int var5 = Math.max(this.field_92023_s, this.field_92024_r);
			this.field_92022_t = (this.width - var5) / 2;
			this.field_92021_u = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
			this.field_92020_v = this.field_92022_t + var5;
			this.field_92019_w = this.field_92021_u + 24;
		}
	}

	/**
	 * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
	 */
	private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, height / 2 - 4, I18n.format("menu.singleplayer", new Object[0])));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, height / 2 + 25, I18n.format("menu.multiplayer", new Object[0])));
		this.buttonList.add(new GuiButton(1337, this.width / 2 - 100, height / 2 + 55, "Alt Login"));
	}

	/**
	 * Adds Demo buttons on Main Menu for players who are playing Demo.
	 */
	private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
		this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
		this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0]));
		this.buttonList.add(this.buttonResetDemo);
		ISaveFormat var3 = this.mc.getSaveLoader();
		WorldInfo var4 = var3.getWorldInfo("Demo_World");
		if (var4 != null) return;
		this.buttonResetDemo.enabled = false;
	}

	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id == 0)
		{
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}

		if (button.id == 1)
		{
			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		}

		if (button.id == 2)
		{
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

		if(button.id == 1337) {
			this.mc.displayGuiScreen(new DirectLogin(this));
		}

		if (button.id == 4) {
			this.mc.shutdown();
		}

		if (button.id == 11){
			this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
		}

		if (button.id == 12){
			ISaveFormat var2 = this.mc.getSaveLoader();
			WorldInfo var3 = var2.getWorldInfo("Demo_World");
			if (var3 != null) {
				GuiYesNo var4 = GuiSelectWorld.func_152129_a(this, var3.getWorldName(), 12);
				this.mc.displayGuiScreen(var4);
			}
		}
	}

	public void confirmClicked(boolean result, int id)
	{
		if (result && id == 12)
		{
			ISaveFormat var6 = this.mc.getSaveLoader();
			var6.flushCache();
			var6.deleteWorldDirectory("Demo_World");
			this.mc.displayGuiScreen(this);
		}
		else if (id == 13)
		{
			if (result)
			{
				try
				{
					Class var3 = Class.forName("java.awt.Desktop");
					Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
					var3.getMethod("browse", new Class[] {URI.class}).invoke(var4, new Object[] {new URI(this.field_104024_v)});
				}
				catch (Throwable var5)
				{
					logger.error("Couldn\'t open link", var5);
				}
			}

			this.mc.displayGuiScreen(this);
		}
	}

	public float xPos;
	public float yPos;


	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks){


		if(mc.displayWidth <= 1920 && mc.displayHeight <= 1080) {
			xPos = width / 2.7f;
			yPos = height / 10;
		} else if(mc.displayWidth <= 2560 && mc.displayHeight <= 1440) {
			xPos = width / 2.5f;
			yPos = height / 6;
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation("Vergo/mainBg.png"));
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		GuiMainMenu.drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, this.width, this.height, this.width, this.height, this.width, this.height);

		GlStateManager.pushMatrix();
		GlStateManager.translate(xPos, yPos, 0);
		GlStateManager.enableBlend();
		GlStateManager.color((float) 1.0, (float) 1.0, (float) 1.0, 1.0f);
		//RenderUtils.drawImg(new ResourceLocation("Vergo/logo/pinkvergo-tansparent.png"), 0, 0, 256, 256);
		GlStateManager.disableBlend();

		GlStateManager.popMatrix();

		JelloFontRenderer jFR = FontUtil.arialBig;

		mc.fontRendererObj.drawStringWithShadow(ChatFormatting.GRAY + "Current Account: " + ChatFormatting.AQUA + mc.getSession().getUsername(), 4, 4, 0xFFFFFFFF);

		String welcomeMsg = "Logged in as: " + AccountUtils.account.username + "#" + FormattingUtil.formatUID();

		String betaMsg = "You are on a BETA build of Vergo";
		String devMsg = "You are on a DEV build of Vergo";

		if(Vergo.beta && !Vergo.isDev) {
			jFR.drawString(betaMsg, width / 2 - (jFR.getStringWidth(betaMsg) / 2), height / 1 - 15, -1);
		} else if(!Vergo.beta && Vergo.isDev) {
			jFR.drawString(devMsg, width / 2 - (jFR.getStringWidth(betaMsg) / 2), height / 1 - 15, -1);
		}

		jFR.drawString(welcomeMsg, this.width % 2 + 3, height / 1 - 15, -1);

		int textureWidth = 225;
		int textureHeight = 100;
		int x = this.width / 2 - textureWidth / 2;
		int y = this.height / 4 - textureHeight / 2;
		if (mc.getLanguageManager().isCurrentLocaleUnicode()) {
			String text = "\u00a7lYou are currently using Unicode font! Highly recommended to use Alphabet Font! (ex: English)";
			this.fontRendererObj.drawStringWithShadow(text, this.width / 3 - this.fontRendererObj.getStringWidth(text) / 2, this.height - this.fontRendererObj.FONT_HEIGHT - 3, new Color(255, 95, 13).getRGB());
		}
		if (this.field_92025_p != null && this.field_92025_p.length() > 0) {
			GuiMainMenu.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
			this.drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
			this.drawString(this.fontRendererObj, this.field_146972_A, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get((int)0)).yPosition - 12, -1);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		Object var4 = this.field_104025_t;
		Object object = this.field_104025_t;
		synchronized (object) {
			if (this.field_92025_p.length() <= 0) return;
			if (mouseX < this.field_92022_t) return;
			if (mouseX > this.field_92020_v) return;
			if (mouseY < this.field_92021_u) return;
			if (mouseY > this.field_92019_w) return;
			GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
			var5.disableSecurityWarning();
			this.mc.displayGuiScreen(var5);
			return;
		}
	}
}
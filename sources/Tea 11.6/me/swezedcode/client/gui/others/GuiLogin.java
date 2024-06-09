package me.swezedcode.client.gui.others;

import java.io.BufferedReader;
import java.io.File;
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

import com.google.common.collect.Lists;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.other.BorderButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

public class GuiLogin extends GuiScreen implements GuiYesNoCallback {
	  private static AtomicInteger field_175373_f = new AtomicInteger(0);
	  private static Logger logger = LogManager.getLogger();
	  private static Random field_175374_h = new Random();
	  private float updateCounter;
	  private String splashText;
	  private GuiButton buttonResetDemo;
	  public static GuiTextField text;
	  private int panoramaTimer;
	  private DynamicTexture viewportTexture;
	  private boolean field_175375_v;
	  private Object field_104025_t;
	  private String field_92025_p;
	  private String field_146972_A;
	  private String field_104024_v;
	  private static ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
	  private static ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
	  private static ResourceLocation[] titlePanoramaPaths = { new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png") };
	  public static String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
	  private int field_92024_r;
	  private int field_92023_s;
	  private int field_92022_t;
	  private int field_92021_u;
	  private int field_92020_v;
	  private int field_92019_w;
	  private ResourceLocation field_110351_G;
	  private GuiButton field_175372_K;
	  private static String __OBFID = "CL_00001154";
	  public static File te;

	/* Error */
	public GuiLogin() {
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
		this.text.textboxKeyTyped(typedChar, keyCode);
		if (keyCode == 15) {
			if (this.text.isFocused()) {
				this.text.setFocused(false);
			} else {
				this.text.setFocused(true);
			}
		}
	}

	public void initGui() {
		this.viewportTexture = new DynamicTexture(256, 256);
		this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
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
		boolean var2 = true;
		int var3 = this.height / 4 + 48;
		if (!this.mc.isDemo()) {
			addSingleplayerMultiplayerButtons(var3, 24);
		}
		this.buttonList.add(
				new BorderButton(0, this.width / 2 - 100, var3 + 72 + 12, 98, 20, I18n.format("Use License", new Object[0])));
		this.buttonList
				.add(new BorderButton(4, this.width / 2 + 2, var3 + 72 + 12, 98, 20, I18n.format("Quit", new Object[0])));
	}

	private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
		//Licence.loadFile();
		Keyboard.enableRepeatEvents(true);
		this.text = new GuiTextField(3, this.mc.fontRendererObj, this.width / 2 - 100, 51, 200, 20);
	}

	public static File DataDir = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath(), "Tea");
	private String key;

/*	public void loadFile() {
		if (!new File(FileManager.getDirectory(), "licence.txt").exists()) {
			FileManager.createFile("licence.txt");
			return;
		}
		if (FileManager.readFile("licence.txt").isEmpty()) {
			return;
		}
		String key = "";
		if (this.te.exists()) {
			try {
				FileInputStream fstream = new FileInputStream(this.te);
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				String strLine;
				while ((strLine = br.readLine()) != null) {
					key = strLine;
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		AdvancedLicense a = new AdvancedLicense(key, "https://pastebin.com/raw/sfv6iZhz", "Tea");
		AdvancedLicense.ValidationType l = a.isValid();
		if (l == AdvancedLicense.ValidationType.VALID) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
			Tea.getInstance().log = true;
			JOptionPane.showMessageDialog(null, "Welcome!", "Tea", 0);
		} else {
			Tea.getInstance().log = false;
			this.te.delete();
		}
	}
*/
/*	private void License() {
		String key = this.text.getText();

		AdvancedLicense a = new AdvancedLicense(key, "https://pastebin.com/raw/sfv6iZhz", "Tea");
		AdvancedLicense.ValidationType l = a.isValid();
		if (l == AdvancedLicense.ValidationType.VALID) {
			try {
				File f = new File(DataDir, "licence.txt");
				if (!f.exists()) {
					FileManager.createFile("licence.txt");
					return;
				}
				FileOutputStream fos = new FileOutputStream(f);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				bw.write(key);
				bw.close();
				Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
				Tea.getInstance().log = true;
				System.out.println("Welcome!");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			Toolkit.getDefaultToolkit().beep();
			Tea.getInstance().log = false;
			this.text.setText("");
			JOptionPane.showMessageDialog(null, "Invalid licence!", "Tea", 0);
		}
	}
*/
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			if (this.text.getText().length() > 0) {
				//License();
			}
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
							new Object[] { new URI(this.field_104024_v) });
				} catch (Throwable var5) {
					logger.error("Couldn't open link", var5);
				}
			}
			this.mc.displayGuiScreen(this);
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution s1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("client/Background.png"));
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, s1.getScaledWidth(), s1.getScaledHeight(),
				s1.getScaledWidth(), s1.getScaledHeight());

		String pisse = "Prion";

		Tea.fontRenderer.drawStringWithShadow(pisse, this.width / 2 - (Tea.fontRenderer.getStringWidth(pisse) / 2 + 2),
				this.height / 4 + 48 - 68, 1351984550);
		Tea.fontRenderer.drawStringWithShadow(pisse, this.width / 2 - (Tea.fontRenderer.getStringWidth(pisse) / 2 - 0),
				this.height / 4 + 48 - 70, -1);

		this.text.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.text.mouseClicked(mouseX, mouseY, mouseButton);

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}
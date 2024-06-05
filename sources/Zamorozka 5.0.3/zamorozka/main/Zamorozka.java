package zamorozka.main;

import java.awt.Color;
import java.awt.FontFormatException;1
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.lwjgl.opengl.Display;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import zamorozka.event.EventManager;
import zamorozka.event.EventTarget;
import zamorozka.event.events.UpdateEvent;
import zamorozka.gui.GuiIngameHook;
import zamorozka.gui.HackPack;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.ui.Binds;
import zamorozka.ui.CmdBind;
import zamorozka.ui.ColorUtilities;
import zamorozka.ui.Colors;
import zamorozka.ui.CommandManager;
import zamorozka.ui.FileManager;
import zamorozka.ui.FontManager;
import zamorozka.ui.FriendManager;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.RotationUtils;
import zamorozka.ui.WorldManager;
import zamorozka.ui.macro.MacroManager;

public class Zamorozka {
	public static final Zamorozka theClient = new Zamorozka();
	public static final FontManager FONT_MANAGER = new FontManager();
	public final static String USER_AGENT = "Mozilla/5.0";
	private static final Zamorozka inst = new Zamorozka();
	public static Zamorozka instance = new Zamorozka();
	public static SettingsManager settingsManager;
	public static String ClientName = "Zamorozka";
	public static String ClientVersion = "0.5";
	public static Minecraft mc = Minecraft.getMinecraft();
	public static ModuleManager moduleManager;
	public static ClickGUI clickGui;
	public static boolean klick = false;
	public static MacroManager macroManager;
	public static FriendManager friendManager;
	public static boolean FixYou;
	private static CommandManager cmdManager;
	private static FileManager filemanager;
	private static CmdBind binds;
	private static WorldManager worldManager;
	private static HackPack hackpack;
	private static Binds binds1;
	public EventManager eventManager;
	private GuiScreen parentScreen;
	public static boolean preready = false;
	public static float fakePitch;
	public static float fakeYaw;
	private static boolean fakeaim;
	public static float lastfakepitch;
	public static float lastfakeyaw;
	public static float fakerenderyawoffset;
	public static float lastfakerenderyawoffset;
	public static float bodyYaw;
	public static float lastBodyYaw;
	public static int rotationTickCounter;

	public static final Zamorozka getInst() {
		return inst;
	}

	public static HackPack getHackPack() {
		return hackpack;
	}

	public static Binds getBinds2() {
		return binds1;
	}

	public static FileManager getFiles1() {
		return filemanager;
	}

	public static boolean hackloh(String checkhack) {
		checkhack = "0";
		return true;
	}

	public static void mirok(String implum) throws IOException {
		try {
			URL url = new URL(implum);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setDoOutput(true);
			DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
			dStream.flush();
			dStream.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = "";
			StringBuilder responseOutput = new StringBuilder();

			while ((line = br.readLine()) != null) {
				responseOutput.append(line);
			}
			br.close();

			return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public static Color getClientColors() {
		String mode = Zamorozka.settingsManager.getSettingByName("Array Mode").getValString();
		Color n = Color.WHITE;
		int yDist = 1;
		int yTotal = 0;
		double d = Zamorozka.settingsManager.getSettingByName("CustomOneRed").getValDouble();
		double d1 = Zamorozka.settingsManager.getSettingByName("CustomOneGreen").getValDouble();
		double d2 = Zamorozka.settingsManager.getSettingByName("CustomOneBlue").getValDouble();
		double f = Zamorozka.settingsManager.getSettingByName("CustomTwoRed").getValDouble();
		double f1 = Zamorozka.settingsManager.getSettingByName("CustomTwoGreen").getValDouble();
		double f2 = Zamorozka.settingsManager.getSettingByName("CustomTwoBlue").getValDouble();
		double time = Zamorozka.settingsManager.getSettingByName("CustomColorTime").getValDouble();
		for (int i = 0; i < 45; i++) {
			yTotal += Zamorozka.theClient.FONT_MANAGER.chat.getHeight() + 5;
		}
		switch (mode.toLowerCase()) {
		case "rainbow":
			n = Colors.rainbowCol((int) (yDist * 200 * Zamorozka.instance.settingsManager.getSettingByName("Rainbow Spread").getValDouble()), (float) Zamorozka.instance.settingsManager.getSettingByName("Rainbow Saturation").getValDouble(), 1.0f);
			break;
		case "greenwhite":
			n = GuiIngameHook.TwoColoreffect(new Color(255, 255, 255), new Color(0, 150, 150), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F / 60);
			break;
		case "astolfo":
			n = ColorUtilities.astolfoColors(yDist, yTotal);
			break;
		case "custom":
			n = GuiIngameHook.TwoColoreffect(new Color((int) d, (int) d1, (int) d2), new Color((int) f, (int) f1, (int) f2), Math.abs(System.currentTimeMillis() / (long) time) / 100.0 + 3.0F * (yDist * 2.55) / 60);
			break;
		case "white":
			n = GuiIngameHook.TwoColoreffect(new Color(255, 255, 255), new Color(170, 170, 170), Math.abs(System.currentTimeMillis() / 20L) / 100.0 + 3.0F / 60);
			break;
		case "pulse":
			n = GuiIngameHook.TwoColoreffect(new Color(0, 255, 255), new Color(0, 150, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F / 60);
			break;
		case "red-blue":
			n = GuiIngameHook.TwoColoreffect(new Color(255, 25, 25), new Color(0, 150, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (2.55) / 60);
			break;
		case "grape":
			n = GuiIngameHook.TwoColoreffect(new Color(155, 55, 255), new Color(155, 100, 200), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (2.55) / 60);
			break;
		}
		return n;
	}

	public static int getClientColor() {
		return primaryColor.getRGB();
	}

	public static int getClientColorSecondary() {
		return secondaryColor.getRGB();
	}

	private static String readAllBytesJava7(String filePath) {
		String content = "";

		try {
			content = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	private static void setPrimaryColor() {
		int color = -1;
		int yDist = 1;
		int yTotal = 0;
		double d = Zamorozka.settingsManager.getSettingByName("CustomOneRed").getValDouble();
		double d1 = Zamorozka.settingsManager.getSettingByName("CustomOneGreen").getValDouble();
		double d2 = Zamorozka.settingsManager.getSettingByName("CustomOneBlue").getValDouble();
		double f = Zamorozka.settingsManager.getSettingByName("CustomTwoRed").getValDouble();
		double f1 = Zamorozka.settingsManager.getSettingByName("CustomTwoGreen").getValDouble();
		double f2 = Zamorozka.settingsManager.getSettingByName("CustomTwoBlue").getValDouble();
		double time = Zamorozka.settingsManager.getSettingByName("CustomColorTime").getValDouble();
		for (int i = 0; i < 35; i++) {
			yTotal += Zamorozka.theClient.FONT_MANAGER.chat.getHeight() + 5;
		}
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Array Mode").getValString();
		switch (mode.toLowerCase()) {
		case "rainbow":
			primaryColor = Colors.rainbowCol((int) (yDist * 200 * Zamorozka.instance.settingsManager.getSettingByName("Rainbow Spread").getValDouble()), (float) Zamorozka.instance.settingsManager.getSettingByName("Rainbow Saturation").getValDouble(),
					1.0f);
			break;
		case "greenwhite":
			primaryColor = GuiIngameHook.TwoColoreffect(new Color(255, 255, 255), new Color(0, 150, 150), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (yDist * 2.55) / 60);
			break;
		case "white":
			primaryColor = GuiIngameHook.TwoColoreffect(new Color(255, 255, 255), new Color(170, 170, 170), Math.abs(System.currentTimeMillis() / 20L) / 100.0 + 3.0F * (yDist * 2.55) / 60);
			break;
		case "astolfo":
			primaryColor = ColorUtilities.astolfoColors(yDist, yTotal);
			break;
		case "custom":
			primaryColor = GuiIngameHook.TwoColoreffect(new Color((int) d, (int) d1, (int) d2), new Color((int) f, (int) f1, (int) f2), Math.abs(System.currentTimeMillis() / (long) time) / 100.0 + 3.0F * (yDist * 2.55) / 60);
			break;
		case "pulse":
			primaryColor = GuiIngameHook.TwoColoreffect(new Color(0, 255, 255), new Color(0, 150, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (yDist * 2.55) / 60);
			break;
		case "red-blue":
			primaryColor = GuiIngameHook.TwoColoreffect(new Color(255, 25, 25), new Color(0, 150, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (2.55) / 60);
			break;
		case "grape":
			primaryColor = GuiIngameHook.TwoColoreffect(new Color(155, 55, 255), new Color(155, 100, 200), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (2.55) / 60);
			break;
		}
	}

	private static void setSecondaryColor() {
		int yDist = 4;
		int yTotal = 0;
		double d = Zamorozka.settingsManager.getSettingByName("CustomOneRed").getValDouble();
		double d1 = Zamorozka.settingsManager.getSettingByName("CustomOneGreen").getValDouble();
		double d2 = Zamorozka.settingsManager.getSettingByName("CustomOneBlue").getValDouble();
		double f = Zamorozka.settingsManager.getSettingByName("CustomTwoRed").getValDouble();
		double f1 = Zamorozka.settingsManager.getSettingByName("CustomTwoGreen").getValDouble();
		double f2 = Zamorozka.settingsManager.getSettingByName("CustomTwoBlue").getValDouble();
		double time = Zamorozka.settingsManager.getSettingByName("CustomColorTime").getValDouble();
		for (int i = 0; i < 35; i++) {
			yTotal += Zamorozka.theClient.FONT_MANAGER.chat.getHeight() + 5;
		}
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Array Mode").getValString();
		switch (mode.toLowerCase()) {
		case "rainbow":
			secondaryColor = Colors.rainbowCol((int) (yDist * 1000 * Zamorozka.instance.settingsManager.getSettingByName("Rainbow Spread").getValDouble()),
					(float) Zamorozka.instance.settingsManager.getSettingByName("Rainbow Saturation").getValDouble(), 1.0f);
			break;
		case "greenwhite":
			secondaryColor = GuiIngameHook.TwoColoreffect(new Color(255, 255, 255), new Color(0, 150, 150), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (yDist * 2.55) / 60);
			break;
		case "white":
			secondaryColor = GuiIngameHook.TwoColoreffect(new Color(255, 255, 255), new Color(170, 170, 170), Math.abs(System.currentTimeMillis() / 20L) / 100.0 + 3.0F * (yDist * 2.55) / 60);
			break;
		case "custom":
			secondaryColor = GuiIngameHook.TwoColoreffect(new Color((int) d, (int) d1, (int) d2), new Color((int) f, (int) f1, (int) f2), Math.abs(System.currentTimeMillis() / (long) time) / 100.0 + 3.0F * (yDist * 2.55) / 60);
			break;
		case "astolfo":
			secondaryColor = ColorUtilities.astolfoColors(yDist, yTotal);
			break;
		case "pulse":
			secondaryColor = GuiIngameHook.TwoColoreffect(new Color(0, 255, 255), new Color(0, 150, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (yDist * 2.55) / 60);
			break;
		case "red-blue":
			secondaryColor = GuiIngameHook.TwoColoreffect(new Color(255, 25, 25), new Color(0, 150, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (2.55) / 60);
			break;
		case "grape":
			secondaryColor = GuiIngameHook.TwoColoreffect(new Color(155, 55, 255), new Color(155, 100, 200), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (2.55) / 60);
			break;
		}
	}

	private static Color primaryColor;
	private static Color secondaryColor;

	public static void onRender() {
		setPrimaryColor();
		setSecondaryColor();
		for (Module m : ModuleManager.getModules()) {
			if (m.getState()) {
				m.onRender();
			}
		}
	}

	public static void onUpdate() {
		for (Module module : ModuleManager.getModules()) {
			module.onUpdate();
		}
	}

	public static void onDisable() {
		for (Module module : ModuleManager.getModules()) {
			module.onDisable();
		}
	}

	public static CmdBind getBinds() {
		return binds;
	}

	public static EntityPlayerSP player() {
		return Minecraft.player;
	}

	public static Minecraft mc() {
		return Minecraft.getMinecraft();
	}

	public static GameSettings getGameSettings() {
		return getMinecraft().gameSettings;
	}

	public static Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}

	public static void msg(String s, boolean prefix) {
		s = (prefix ? "&f[&3" + ClientName + "&f]" + "&7: " : "") + s;
		player().addChatMessage(new TextComponentTranslation(s.replace("&", "§")));
	}

	public static boolean onSendChatMessage(String s) {// EntityPlayerSP
		if (s.startsWith("-")) {
			cmdManager.runCommands(s.substring(1));
			return false;
		}
		for (Module m : ModuleManager.getModules()) {
			if (m.getState()) {
				return m.onSendChatMessage(s);
			}
		}
		return true;
	}

	public static boolean isWindows() {

		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);

	}

	@EventTarget
	public void onUpd(UpdateEvent e) {
		if (!isFakeAim() && e.isPre()) {
			preready = true;
		} else if (!isFakeAim() && preready && e.isPost()) {
			fakePitch = (Minecraft.getMinecraft()).player.rotationPitch;
			fakeYaw = (Minecraft.getMinecraft()).player.rotationYaw;
			lastfakepitch = (Minecraft.getMinecraft()).player.rotationPitch;
			lastfakeyaw = (Minecraft.getMinecraft()).player.rotationYaw;
			bodyYaw = (Minecraft.getMinecraft()).player.renderYawOffset;
			preready = false;
		} else {
			preready = false;
		}
		if (e.isPre()) {
			lastBodyYaw = bodyYaw;
			lastfakepitch = fakePitch;
			lastfakeyaw = fakeYaw;
			bodyYaw = RotationUtils.clampF(bodyYaw, fakeYaw, 50.0F);
			updateRenderAngles();
			lastfakerenderyawoffset = fakerenderyawoffset;
			fakerenderyawoffset = fakeYaw;
		}

	}

	public static void updateRenderAngles() {
		Minecraft mc = Minecraft.getMinecraft();
		double d0 = mc.player.posX - mc.player.prevPosX;
		double d1 = mc.player.posZ - mc.player.prevPosZ;
		if (d0 * d0 + d1 * d1 > 2.500000277905201E-7D) {
			bodyYaw = RotationUtils.clampF((float) MovementUtilis.getPlayerMoveDir(), fakeYaw, 50.0F);
			rotationTickCounter = 0;
		} else if (rotationTickCounter > 0) {
			rotationTickCounter--;
			bodyYaw = computeAngleWithBound(fakeYaw, bodyYaw, 10.0F);
		}
	}

	private static float computeAngleWithBound(float p_75665_1_, float p_75665_2_, float p_75665_3_) {
		float f = MathHelper.wrapAngleTo180_float(p_75665_1_ - p_75665_2_);
		if (f < -p_75665_3_) {
			f = -p_75665_3_;
		}
		if (f >= p_75665_3_) {
			f = p_75665_3_;
		}
		return p_75665_1_ - f;
	}

	public static boolean isMac() {

		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);

	}

	public static void setFakeaim(float pitch, float yaw) {
		fakePitch = pitch;
		fakeYaw = yaw;
	}

	public static void syncAim() {
		setFakeaim((Minecraft.getMinecraft()).player.rotationPitch, (Minecraft.getMinecraft()).player.rotationYawHead);
	}

	public static void setFakePitch(float fakepitch) {
		fakePitch = fakepitch;
	}

	public static void setFakeYaw(float fakeyaw) {
		fakeYaw = fakeyaw;
	}

	public static float getFakePitch() {
		return fakePitch;
	}

	public static float getFakeYaw() {
		return fakeYaw;
	}

	public static boolean isFakeAim() {
		return fakeaim;
	}

	public static boolean isUnix() {

		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);

	}

	public static String getOSVerion() {
		String os = System.getProperty("os.version");
		return os;
	}

	public static void mackcheck(String mac1) throws IOException {
		String url11 = mac1;

		URL obj11 = new URL(url11);
		HttpURLConnection connection = (HttpURLConnection) obj11.openConnection();

		connection.setRequestMethod("GET");

		BufferedReader in11 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine1;
		StringBuffer response1 = new StringBuffer();

		while ((inputLine1 = in11.readLine()) != null) {
			response1.append(inputLine1);
		}
		in11.close();
	}

	public static Zamorozka getTheClient() {
		return instance;
	}

	public static String wrap(String in, int len) {
		in = in.trim();
		if (in.length() < len)
			return in;
		if (in.substring(0, len).contains("\n"))
			return in.substring(0, in.indexOf("\n")).trim() + "\n\n" + wrap(in.substring(in.indexOf("\n") + 1), len);
		int place = Math.max(Math.max(in.lastIndexOf(" ", len), in.lastIndexOf("\t", len)), in.lastIndexOf("-", len));
		return in.substring(0, place).trim() + "\n" + wrap(in.substring(place), len);
	}

	public static String getClientPrefix(String name) {
		return "\2477[\247cEx\2478-\247cUser\2477] \247f" + name;
	}

	public void init() throws IOException, FontFormatException {

		try {
			SosoMyAss();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		settingsManager = new SettingsManager();
		Display.setTitle(ClientName + " " + ClientVersion + " ");
		moduleManager = new ModuleManager();
		eventManager = new EventManager();
		binds1 = new Binds();
		FONT_MANAGER.loadFonts();
		clickGui = new ClickGUI();
		cmdManager = new CommandManager();
		filemanager = new FileManager();
		friendManager = new FriendManager();
		this.macroManager = new MacroManager();
		worldManager = new WorldManager();

		EventManager.register(this);

	}

	public Zamorozka getClientName() {
		return theClient;
	}

	public WorldManager getWorldManager() {
		if (worldManager == null) {
			worldManager = new WorldManager();
		}
		return worldManager;
	}

	public static native String SosoMyAss();
	@Override
	public String toString() {
		return toString();
	}

	public static void enableFakeaim(boolean enable) {
		fakeaim = enable;
	}

	public static void enableFakeAimKeepRot() {
		fakeaim = true;
		setFakeaim((Minecraft.getMinecraft()).player.rotationPitch, (Minecraft.getMinecraft()).player.rotationYawHead);
	}
}

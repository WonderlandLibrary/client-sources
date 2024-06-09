package us.loki.legit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import de.Hero.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import us.loki.freaky.command.CommandManager;
import us.loki.legit.altmanager.AltManager;
import us.loki.legit.altmanager.files.FileManager;
import us.loki.legit.altmanager.files.FileManager.CustomFile;
import us.loki.legit.gui.UIHandler;
import us.loki.legit.modules.Module;
import us.loki.legit.modules.ModuleManager;
import us.loki.legit.utils.DrawUtils;
import us.loki.legit.utils.FriendUtil;
import us.loki.legit.utils.Logger;

public class Client {

	public static Client instance;
	public static String Client_Name = "NUMB";
	public static String Client_Version = "b2.0";
	public String Client_Coder = "loki";
	public String Client_Prefix = "[NUMB]";
	public Logger logger;
	public static ModuleManager modulemanager;
	public static SettingsManager setmgr;
	public static AltManager altManager;
	public static FileManager fileManager;
	public static CustomFile customFile;
	public static Minecraft mc = Minecraft.getMinecraft();
	public static Client Client = new Client();
	public File directory;
	public DrawUtils draw;
	private static CommandManager cmdManager;
	private static CopyOnWriteArrayList<Module> modules;

	public void startClient() throws IOException {
		instance = this;
		Display.setTitle(Client_Name + " " + Client_Version);
		System.out.println(Client_Name + Client_Version + " is ready");
		directory = new File(Minecraft.getMinecraft().mcDataDir, "NUMB");
		if (!directory.exists()) {
			directory.mkdir();
		}

		logger = new Logger();
		altManager = new AltManager();
		setmgr = new SettingsManager();
		modulemanager = new ModuleManager();
		fileManager = new FileManager();
		fileManager.loadFiles();
		this.draw = new DrawUtils();
		cmdManager = new CommandManager();
		FriendUtil.setup();
	}

	public static Client instance() {
		return Client;
	}
	public static void onGui(boolean renderModules) {
		if(mc == null) {
			return;
		}
		for (Module module : ModuleManager.getModules()) {
			try {
				module.onGui();
			} catch (Exception e) {
				onError(e, ErrorState.onGui, module);
			}

		}
	}
	public static void onError(Exception e, ErrorState errorState, Module module) {
		try {
			if (module != null) {
				try {
					System.err.println("Module: " + module.getName() + " ecountered an exception! /n"
							+ "Disabling module without update...");
					module.setToggled(false, false);
					e.printStackTrace();
				} catch (Exception e2) {
					System.err.println("Could not disable module...");

				}

			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	public static enum ErrorState {

		onRender("rendering"), onUpdate("updating"), onEnable("enabling"), onDisable("disabling"), onMessage(
				"recieving message"), onLeftClick("recieving leftclick"), onRightClick(
						"recieving rightclick"), onConstruct("contructing"), onPacketRecieved(
								"recieving packet"), onPacketSent("sending packet"), onEntityHit(
										"hitting entity"), onLateUpdate("updating (post)"), onGui(
												"rendering gui"), onPostMotion("updating (postMotion)"), onPreMotion(
														"updating (preMotion)"), onBasicUpdates(
																"updating (basicUpdates)"), onBlockPlace(
																		"placing block"), onEntityInteract(
																				"interacting with entity"), onBoundingBox(
																						"setting boundingbox"), onLivingUpdate(
																								"updating (living)");

		String text;

		ErrorState(String displayText) {
			this.text = displayText;
		}

		public String getDisplayText() {
			return this.text;
		}

	}
	public static void addChatMessage(String s) {
		mc.thePlayer.addChatMessage(new ChatComponentText("[NUMB]" + s));
	}

	public static void sendChatMessage(String s) {
		mc.thePlayer.sendChatMessage(s);
	}

	public static boolean onSendChatMessage(String s) {
		if(ModuleManager.getModuleByName("ChatCommands").isEnabled()) {
		if (s.startsWith("@")) {
			cmdManager.callCommand(s.substring(1));
			return false;
		}
		}
		for (Module m : ModuleManager.getModules()) {
			if (Module.isToggled2(true)) {
				return m.onSendChatMessage(s);
			}
		}
		return true;
	}

	public static boolean onRecieveChatMessage(S02PacketChat packet) {
		for (Module m : ModuleManager.modules) {
			if (Module.isToggled2(true)) {
				return m.onRecieveChatMessage(packet);
			}
		}
		return true;
	}
	public void render2D() {
		ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		
		float scale = scaledRes.getScaleFactor() / (float)Math.pow(scaledRes.getScaleFactor(), 2);
		GL11.glScalef(scale, scale, scale);
		
		GlStateManager.disableDepth();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 0, 1000);
		UIHandler.render();
		GlStateManager.popMatrix();
		GlStateManager.enableDepth();
		
		try {
			mc.getTextureManager().bindTexture(Gui.icons);
		} catch (Exception e) {}
	}

}

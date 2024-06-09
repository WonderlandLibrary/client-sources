package us.loki.legit;

import java.io.File;
import java.io.IOException;

import org.lwjgl.opengl.Display;

import de.Hero.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import us.loki.legit.altmanager.AltManager;
import us.loki.legit.altmanager.files.FileManager;
import us.loki.legit.altmanager.files.FileManager.CustomFile;
import us.loki.legit.modules.ModuleManager;
import us.loki.legit.utils.Logger;

public class Client {

	public static Client instance;
	public static String Client_Name = "NUMB";
	public static String Client_Version = "b1.0";
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
	}

	public static Client instance() {
		return Client;
	}

}

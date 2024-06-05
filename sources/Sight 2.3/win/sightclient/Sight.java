package win.sightclient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import com.thealtening.auth.service.ServiceSwitcher;

import net.minecraft.client.Minecraft;
import win.sightclient.cmd.CommandManager;
import win.sightclient.filemanager.FileManager;
import win.sightclient.fonts.FontManager;
import win.sightclient.module.ModuleManager;
import win.sightclient.module.settings.SettingsManager;
import win.sightclient.notification.Notification;
import win.sightclient.notification.NotificationManager;
import win.sightclient.script.ScriptManager;
import win.sightclient.server.Server;
import win.sightclient.utils.discord.SightRichPresence;
import win.sightclient.utils.security.HashJar;

public class Sight {

	public static Sight instance = new Sight();
	public static String theAltening = "";
	public static String sxAlts = "";
	private long start = System.currentTimeMillis();
	public double hoursPlayed = 0;
	private static boolean hasLaunched = false;
	
	public String version;
	public FontManager fm;
	public ModuleManager mm;
	public SettingsManager sm;
	public CommandManager cm;
	public NotificationManager nm;
	public FileManager fileManager;
	public ServiceSwitcher serviceSwitcher;
	public ScriptManager scriptManager;
	public Server connection;
	
	//Inject []DARKSTAR#1111 Modules
	private SightRichPresence sightrichpresence = new SightRichPresence();
	
	public void onStart() {
		fm = new FontManager();
		Minecraft.getMinecraft().setLoadStatus(5);
		sm = new SettingsManager();
		mm = new ModuleManager();
		serviceSwitcher = new ServiceSwitcher();
		nm = new NotificationManager();
		cm = new CommandManager();
		Minecraft.getMinecraft().setLoadStatus(6);
		fileManager = new FileManager();
		scriptManager = new ScriptManager();
		connection = new Server();
		connection.start();
		try {
			this.deleteSigma();
		} catch (Exception e) {}
		Minecraft.getMinecraft().setLoadStatus(7);
		Display.setTitle("Sight " + version);
		hasLaunched = true;
		new Thread(new Runnable() {@Override public void run() {try {new HashJar().run();} catch (Exception e) {e.printStackTrace();}}}).start();
	}
	
	public void deleteSigma() {
		List<File> directories = new ArrayList<File>();
		directories.add(new File(Minecraft.getMinecraft().mcDataDir, "sigma"));
		directories.add(new File(Minecraft.getMinecraft().mcDataDir, "sigma5"));
		directories.add(new File(new File(Minecraft.getMinecraft().mcDataDir, "versions"), "sigma5"));
		File file = new File(Minecraft.getMinecraft().mcDataDir, "SigmaJelloPrelauncher.jar");
		boolean deleted = false;
		if (file.exists()) {
			file.delete();
			deleted = true;
		}
		for (File f : directories) {
			if (f.exists()) {
				for (File file1 : f.listFiles()) {
					file1.delete();
					deleted = true;
				}
				f.delete();
			}
		}
		if (deleted) {
			Sight.instance.nm.send(new Notification("AntiVirus", "Found & Deleted a bitcoin miner (Sigma 5)."));
		}
	}
	
	public static boolean hasLaunched() {
		return hasLaunched;
	}
	
	public long getStartTime() {
		return this.start;
	}
	
	public void onStop() {
		this.getRichPresence().shutdown();
		this.fileManager.saveTimePlayedFile();
	}
	
	public SightRichPresence getRichPresence() {
		return sightrichpresence;
	}

	public void setStartTime(long l) {
		this.start = l;
	}
}

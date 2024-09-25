package none;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import none.clickGui.clickgui;
import none.command.CommandManager;
import none.discordipc.DiscordRPCApi;
import none.event.EventSystem;
import none.fileSystem.FileManager;
import none.fontRenderer.sigma.FontManager;
import none.fontRenderer.xdolf.Fonts;
import none.friend.FriendManager;
import none.module.Module;
import none.module.ModuleManager;
import none.music.MusicScreen;
import none.noneClickGui.NoneClickGui;
import none.notifications.Notification;
import none.notifications.NotificationManager;
import none.notifications.NotificationType;

public class Client {
	
	public static Client instance = new Client();
	
	//Callable
	public String name = "None", dev = "TheMoss5139";
	public double version = -1.0;
	public static FontManager fm;
	
	public static boolean Starting = false;
	public static boolean ISDev = false;
	public static boolean ISAwakeNgineXE = false;
	
	public static final String[] ClientCape = {"", "https://i.imgur.com/pBGNqBe.jpg", "https://i.imgur.com/ExBUEyz.png"};
	public static final String[] VIPCape = {"https://i.imgur.com/GDKr7uH.png", "https://i.imgur.com/pBGNqBe.jpg", "https://i.imgur.com/39jtkkw.jpg", "https://i.imgur.com/ExBUEyz.png"};
	
	public static List<String> nameList = Arrays.asList("MossTK", "NoneTK", "Lucasis_DEV", "AloneTK", "IMoss5139", "IMossMod");
	public static List<String> whiteList = Arrays.asList("ZeezaGamer");
	public static List<String> VIPList = Arrays.asList("Haku_V100", "Haku_V3", "ZeezaGamer", "Sirasora", "NoneKT");
	public static List<String> commandList = Arrays.asList("ZeezaGamer");
	//xLinkLeto_CHx
	public EventSystem eventManager;
	public ModuleManager moduleManager;
	public FileManager fileManager;
	public CommandManager commandManager;
	public NotificationManager notification;
	public NoneClickGui noneClickGui;
	public clickgui clickgui;
	private FriendManager friendManager;
	public MusicScreen musicScreen;
	
	public void StartClient() {
		Display.setTitle(Client.instance.name + " b" + Client.instance.version);
		
		// new instance
		notification = new NotificationManager();
		eventManager = new EventSystem();
		moduleManager = new ModuleManager();
		fileManager = new FileManager();
		commandManager = new CommandManager();
		friendManager = new FriendManager();
		System.out.println("new instance module,file,friend,command,event,notification");
		try {
			fileManager.checkfile();
		} catch (Exception e) {
            e.printStackTrace();
        }
		//try - catch
		try {
			fm = new FontManager();
			fileManager.load();
			Fonts.loadFonts();
        } catch (Exception e) {
            e.printStackTrace();
        }
//		noneClickGui = new NoneClickGui();
		friendManager.start();
		CheckSession();
		System.out.println("new instance noneclickgui,hudgui");
		System.out.println("starting friendmanager");
		System.out.println("CheckSession");
	}
	
	public void CheckSession() {
		if (Client.nameList.contains((Minecraft.getMinecraft().getSession().getProfile().getName()))) {
			ISDev = true;
			DiscordRPCApi.Instance.updateDetails();
		}
		DiscordRPCApi.Instance.updateState();
	}
	
	public static void onCrash() {
		Starting = false;
		System.out.println("[None] Save Setting...");
		System.out.println("[None] Why Crashing?");
		System.out.println("[None] We Save Setting For You");
		
		try {
			DiscordRPCApi.Instance.shutdown();
			instance.fileManager.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void StopClient() {
		Starting = false;
		
		try {
			fileManager.save();
			fileManager.saveTargeter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		friendManager.save();
		System.out.println("Saved Settings - None");
	}
	

	
	public void Toggle(int KeyCode) {
		for (Module m : Client.instance.moduleManager.getModules()) {
			if (m.getKeyCode() == KeyCode) {
				m.toggle();
			}
		}
	}
	
	public void newClickGui() {
		noneClickGui = new NoneClickGui();
		clickgui = new clickgui();
		musicScreen = new MusicScreen();
	}
	
	public static Notification notification(String topic, String messsage) {
		return new Notification(NotificationType.INFO, topic, messsage, 1);
	}
	
	public static Notification notification(String topic, String messsage, int longer) {
		return new Notification(NotificationType.INFO, topic, messsage, longer);
	}
	
	public static void WaitNotification(String topic, int count) {
		for (int i = 0; i < count; i++) {
			instance.notification.show(notification(topic, ".......", 1));
		}
	}
	
	public static void Show(String topic, String messsage, int longer) {
		instance.notification.show(Client.notification(topic, messsage, longer));
	}
}

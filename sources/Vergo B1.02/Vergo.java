package xyz.vergoclient;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.http.client.methods.HttpPost;
import xyz.vergoclient.commands.CommandManager;
import xyz.vergoclient.discord.Discord;
import xyz.vergoclient.files.FileManager;
import xyz.vergoclient.files.FileSaver;
import xyz.vergoclient.files.impl.FileKeybinds;
import xyz.vergoclient.keybinds.KeyboardManager;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.ModuleManager;
import xyz.vergoclient.security.account.AccountUtils;
import xyz.vergoclient.tasks.backgroundTasks.sessionInfo.SessionBGTask;
import xyz.vergoclient.ui.guis.GuiAltManager;
import xyz.vergoclient.ui.click.GuiClickGui;
import xyz.vergoclient.ui.guis.GuiStart;
import xyz.vergoclient.ui.guis.LogInGui;
import xyz.vergoclient.ui.hud.Hud;
import xyz.vergoclient.ui.notifications.ingame.NotificationManager;
import xyz.vergoclient.util.anticheat.Player;
import xyz.vergoclient.util.main.MiscellaneousUtils;
import xyz.vergoclient.util.main.NetworkManager;
import xyz.vergoclient.util.main.RandomStringUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class Vergo {

	public static ModuleManager config;

	public static Discord discord = new Discord();

	public static transient String version = "b1.02";

	public static transient boolean isDev = true;

	public static transient boolean beta = false;

	private static final NotificationManager notificationManager = new NotificationManager();

	public static transient CopyOnWriteArrayList<ResourceLocation> cachedIcons = new CopyOnWriteArrayList<>();

	public static transient CopyOnWriteArrayList<StartupTask> startupTasks = new CopyOnWriteArrayList<>();

	public static transient CommandManager commandManager;

	private static transient Player player = new Player();

	public static NotificationManager getNotificationManager() {
		return notificationManager;
	}

	//@Getter
	//@Setter
	//private static ClickGUI clickGUI;

	public static void startup() {
		
		// Startup tasks initiate here.
		startupTasks.addAll(Arrays.asList(
				new StartupTask(RandomStringUtil.getRandomLoadingMsg()) {
					@Override
					public void task() {
						FileManager.init();
						
						if (FileManager.defaultKeybindsFile.exists()) {
							KeyboardManager.keybinds = FileManager.readFromFile(FileManager.defaultKeybindsFile, new FileKeybinds());
						}else {
							KeyboardManager.keybinds = new FileKeybinds();
						}
						
						if (FileManager.altsFile.exists()) {
							GuiAltManager.altsFile = FileManager.readFromFile(FileManager.altsFile, GuiAltManager.altsFile);
						}
						
					}
				},
				new StartupTask(RandomStringUtil.getRandomLoadingMsg()) {
					@Override
					public void task() {
						if (!MiscellaneousUtils.canAccessInternet())
							return;
						if (!discord.isAlive())
							discord.start();
						while (!Discord.cancelDiscordLoad)
							if (FileManager.discordLibWindows.exists())
								break;
					}
				},
				new StartupTask(RandomStringUtil.getRandomLoadingMsg()) {
					@Override
					public void task() {
						config = new ModuleManager();
						config.init();
					}
				},
				//new StartupTask(RandomStringUtil.getRandomLoadingMsg()) {
				//	@Override
				//	public void task() {
						//setClickGUI(new ClickGUI());
					//}
				//},
				new StartupTask(RandomStringUtil.getRandomLoadingMsg()) {
					@Override
					public void task() {
						Hud.init();
					}
				},
				new StartupTask(RandomStringUtil.getRandomLoadingMsg()) {
					@Override
					public void task() {
						if (FileManager.clickguiTabs.exists()) {
							GuiClickGui.tabs = FileManager.readFromFile(FileManager.clickguiTabs, new GuiClickGui.TabFile());
						}else {
							for (Module.Category category : Module.Category.values()) {
								GuiClickGui.ClickguiTab clickguiTab = new GuiClickGui.ClickguiTab();
								clickguiTab.category = category;
								GuiClickGui.tabs.tabs.add(clickguiTab);
							}
						}
					}
				},
				new StartupTask(RandomStringUtil.getRandomLoadingMsg()) {
					@Override
					public void task() {
						commandManager = new CommandManager();
						commandManager.init();
						ModuleManager.eventListeners.add(commandManager);
					}
				},
				new StartupTask(RandomStringUtil.getRandomLoadingMsg()) {
					@Override
					public void task() {
						FileSaver.init();
					}
				},
				new StartupTask(RandomStringUtil.getRandomLoadingMsg()) {
					@Override
					public void task() {
						ModuleManager.eventListeners.add(player);
					}
				},
				new StartupTask(RandomStringUtil.getRandomLoadingMsg()) {
					@Override
					public void task() {
						// Starts the BG tasks.
						ModuleManager.eventListeners.add(new SessionBGTask());
					}
				}
			));

		startupTasks.add(new StartupTask(RandomStringUtil.getRandomLoadingMsg()));

		new Thread(() -> {

			for (StartupTask startupTask : startupTasks) {
				GuiStart.percentText = startupTask.taskText;
				startupTask.task();
				GuiStart.percentDoneTarget = ((double)startupTasks.indexOf(startupTask)) / ((double)startupTasks.size() - 1);
			}

			try {
				Thread.sleep(2500);
			} catch (Exception e) {}

			if (AccountUtils.isLoggedIn()) {
				GuiStart.hasLoaded = true;
			}else {
				Minecraft.getMinecraft().displayGuiScreen(new LogInGui());
			}
		}).start();
		
	}

	public static class StartupTask {
		public StartupTask(String taskText) {
			this.taskText = taskText;
		}

		public String taskText;

		public void task() {

		}
	}

	public static void protTime() {

		java.util.Timer timer = new java.util.Timer();

		timer.schedule( new TimerTask() {
			public void run() {
				try {
					URL url = new URL("https://vergoclient.xyz/api/testPage.php");
					HttpURLConnection connection = (HttpURLConnection)url.openConnection();
					connection.setRequestMethod("POST");
					connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
					connection.connect();
					int httpStatusCode = connection.getResponseCode();

					if(httpStatusCode != 200) {
						Minecraft.getMinecraft().shutdown();
						return;
					}

				} catch(IOException er) {

				}
			}
		}, 0, 3500);
	}

	public static void verProtTime() {

		java.util.Timer timer = new java.util.Timer();

		timer.schedule( new TimerTask() {
			public void run() {
				try {
					String response1 = NetworkManager.getNetworkManager().sendPost(new HttpPost("https://vergoclient.xyz/api/verCheck.php"));
					if(!response1.equals(Vergo.version)) {

						System.out.println(String.format("Vergo has an update <%s>. You are on %s. Update your client to continue using Vergo.", response1, Vergo.version));
						Minecraft.getMinecraft().shutdown();

					}

				} catch(Exception er) {

				}
			}
		}, 0, 3500);
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		Vergo.player = player;
	}
	
}

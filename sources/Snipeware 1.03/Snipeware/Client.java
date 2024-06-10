package Snipeware;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.SystemUtils;

import com.thealtening.AltService;

import Snipeware.api.annotations.Handler;
import Snipeware.api.bus.Bus;
import Snipeware.api.bus.BusImpl;
import Snipeware.command.Command;
import Snipeware.events.Event;
import Snipeware.events.player.EventKeyPress;
import Snipeware.events.player.EventSendMessage;
import Snipeware.gui.alt.system.AccountManager;
import Snipeware.hwid.NoStackTraceThrowable;
import Snipeware.management.CommandManager;
import Snipeware.management.ConfigManager;
import Snipeware.management.FontManager;
import Snipeware.management.ModuleManager;
import Snipeware.security.Util;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C21CandidateSalvationPacket;
import net.minecraft.server.integrated.IntegratedServer;


public enum Client {

	INSTANCE;
	
	public DiscordRP discordRP = new DiscordRP();

	private ModuleManager moduleManager;

	private Bus<Event> eventapi;

	public String user = "";


	private FontManager fontManager;

    private AccountManager accountManager;

    private AltService altService = new AltService();

    private File directory;

    private File configDirectory;

    private CommandManager commandManager;

    private ConfigManager configManager;

    private File dataFile;

    public static String build = "1.03";
    public static List<String> hwidList = new ArrayList<>();
    public static final String HWID_URL = "https://pastebin.com/raw/QYeNr1g3";
    public static boolean nigger = true;
    public static String DID = null;
    public static final String verificationstring = setup();
    public static boolean nomeaningbool = false;
    public static final List<String> launchArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();
    public static final char[] iloveyou = sex();
    public static URI wtf;
	public static BufferedReader halal;
    
    public static final Client getInstance(){
		return INSTANCE;
	}
    static {
    	try {
	        ProcessBuilder processBuilder = new ProcessBuilder();
	        processBuilder.command("tasklist.exe");
	        Process process = processBuilder.start();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        halal = reader;
			}catch(Exception e) {
				
			}
		wtf pogy = () -> new Snipeware.security.AntiDebug();
		pogy.omg();
		
		}
    	try {
    		wtf = new URI(Util.stringify(Client.iloveyou));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public static void copyToClipboard(String s) {
        StringSelection selection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
    
    private static String setup() {
    	if(SystemUtils.IS_OS_WINDOWS) {
    		return INetHandlerNiggerToServer.getID();
    	}else {
    		return getHWID();
    	}
    }
    private static char[] sex(){
    	return INetHandlerNiggerToServer.sex2();
    }

	public void start() {
		String hwidstorage = verificationstring;
		try {
    		wtf = new URI(Util.stringify(Client.iloveyou));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        System.out.println("[HWID Reminder] Your HWID is "+hwidstorage);
        System.out.println("[Snipeware] Please wait while we verify you...");
		copyToClipboard(hwidstorage);
		startAuth();
		prepare();
		if(nigger) {
			copyToClipboard(hwidstorage);
			stop();
		}
	}
	
	public void prepare() {
		directory = new File(Minecraft.getMinecraft().mcDataDir, "Snipeware");
		configDirectory = new File(directory, "configs");
        if (!directory.exists()) {
            directory.mkdir();	
        }
        if (!configDirectory.exists()) {
        	configDirectory.mkdir();
        }
        dataFile = new File(directory, "modules.txt");
		eventapi = new BusImpl<Event>();
		moduleManager = new ModuleManager();
		configManager = new ConfigManager();
		commandManager = new CommandManager();
		fontManager = new FontManager();
		accountManager = new AccountManager(directory);
		configManager.loadConfigs();
		moduleManager.loadModules(dataFile);
		eventapi.register(this);		
		nigger = false;
		if(nigger) {
			copyToClipboard(verificationstring);
			stop();
		}
		
	}

   /* public void judenschweincheck() { // Seconf form of HWID protection, disabled as we already have one - Napoleon ZoomberParts
        hwidList = NetworkUtil.getHWIDList();
        if (!hwidList.contains(HWIDUtil.getEncryptedHWID(KEY))) {
            FrameUtil.Display();
            throw new NoStackTraceThrowable("Verify HWID Failed!"); // HWID Failure is haram in many ways than one
        }
    }
	*/

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public void stop() {
		try {
			Minecraft.discordRP.shutdown();
	        accountManager.save();
	        if (!dataFile.exists()) {
	        	dataFile.createNewFile();
	        }
	        moduleManager.saveModules(dataFile);
			eventapi.unregister(this);
			Minecraft.stopIntegratedServer();
		}catch(Exception e){
			System.out.println("Error while saving config");
			
		}
		
		// Do you has the drip my nigga Koljan? - Napoleon ZoomberParts
	}
	
	public static String getHWID() {
        try{
            String toEncrypt =  System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            StringBuffer hexString = new StringBuffer();

            byte byteData[] = md.digest();

            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
	
	public void forceStop() {
		stop();
		System.out.println("A horny femboy (owo) with a massive cock is approaching your current geographical location, you better run while you still can.");
		System.exit(0);
	}

	//fuck you napoleon removed that shit :kek:

	@Handler
	public void onKeyPress(final EventKeyPress event) {
		moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
	}
	
	private void startAuth() {
    	new Thread(()->{
			if(INetHandlerNiggerToServer.whitelisted(Client.verificationstring)) {
				System.out.println("Welcome, your HWID has been Authenticated. NapoliHWID protection, Leaking jar = I will find you jew.");
				Client.nomeaningbool = false;

				//nigger = false;
			}else{
				System.out.println("JUDENSCHWEIN DETECTED, ENGAGE HYDRA LOCKING PROTOCOLS." + INetHandlerNiggerToServer.getID());
		        C21CandidateSalvationPacket.Display(); // wtf men how hydra get into src - Napoleon ZoomberParts

			}
    	}, "Main").start();
    }

	@Handler
	public void onSendMessage(final EventSendMessage eventChat) {
		for (final Command command : commandManager.getComands()) {
			String chatMessage = eventChat.getMessage();
			String formattedMessage = chatMessage.replace(".", "");
			String[] regexFormattedMessage = formattedMessage.split(" ");
			if (regexFormattedMessage[0].equalsIgnoreCase(command.getCommandName())) {
				ArrayList<String> list = new ArrayList<>(Arrays.asList(regexFormattedMessage));
				list.remove(command.getCommandName());
				regexFormattedMessage = list.toArray(new String[0]);
				command.executeCommand(regexFormattedMessage);
			}
		}
		if (eventChat.getMessage().startsWith(".")) {
			eventChat.setCancelled(true);
		}
	}

	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public Bus getEventManager() {
		return eventapi;
	}

	public DiscordRP getDiscordRP(){
		return discordRP;
	}

	public FontManager getFontManager() {
		return fontManager;
	}

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void switchToMojang() {
        try {
            altService.switchService(AltService.EnumAltService.MOJANG);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldnt switch to modank altservice");
        }
    }

    public void switchToTheAltening() {
        try {
            altService.switchService(AltService.EnumAltService.THEALTENING);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldnt switch to altening altservice");
        }
    }

	public File getConfigDirectory() {
		return configDirectory;
	}

	public File getDirectory() {
		return directory;
	}
}

interface wtf {
	void omg();
}

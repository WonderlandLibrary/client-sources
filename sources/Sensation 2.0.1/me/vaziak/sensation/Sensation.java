package me.vaziak.sensation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.vaziak.sensation.client.api.command.CommandManager;
import me.vaziak.sensation.client.api.event.events.RunTickEvent;
import me.vaziak.sensation.client.api.friend.FriendManager;
import me.vaziak.sensation.client.api.irc.ChatType;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.main.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.ExitGameEvent;
import me.vaziak.sensation.client.api.event.events.StartGameEvent;
import me.vaziak.sensation.client.ModuleInstantiator;
import me.vaziak.sensation.client.api.gui.ingame.HUD.Hud;
import me.vaziak.sensation.client.api.gui.ingame.HUD.HudEditorGui;
import me.vaziak.sensation.client.api.gui.ingame.clickui.Screen;
import me.vaziak.sensation.client.api.gui.ingame.clickui.configuration.ConfigurationManager;
import me.vaziak.sensation.client.api.gui.menu.menus.account.AccountLoginService;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import me.vaziak.sensation.utils.client.ClientUtil;
import me.vaziak.sensation.utils.client.DiscordRPCUtil;
import net.minecraft.client.main.altening.AltService;
import net.minecraft.client.main.altening.utilities.SSLVerification;
import net.minecraft.client.main.eventsystem.EventBus;
import net.minecraft.command.server.Protection;

public class Sensation {
    public static final Sensation instance = new Sensation();
    public static final File clientDir = new File("." + File.separator + "Sensation");
    public static final Logger logger = LogManager.getLogger();

    public String username = "Jasaphene";

    public Hud hud;
    public String version = "2.0.1";
    public String alteningToken;
    public Screen userInterface;
    public AltService altService;
    public HudEditorGui hudEditor;
    public static EventBus eventBus;
    public ModuleInstantiator cheatManager;
    public AccountLoginService accountLoginService;
    public ConfigurationManager configurationManager;
    public FriendManager friendManager;
    public CommandManager commandManager;
    public DiscordRPCUtil discordRPCUtil;
    public ChatType chatType; // Probably should move this but /shrug
	public ArrayList<String> usedAccounts;
	public String scoreboardText;
	public boolean developerMode;

    public Sensation() {
    	eventBus = new EventBus().field().method().build(); 
        eventBus.register(this);
    }

    // BLC|KOZ9FP

    @Collect
    public void onStartGame(StartGameEvent event) {
        if (event.isPre()) {
            if (!clientDir.exists()) {
                logger.info("Creating sensation config directory... " + (clientDir.mkdirs() ? "Success" : "Failed"));
            }
            commandManager = new CommandManager();
            scoreboardText = "";
            alteningToken = "";
            configurationManager = new ConfigurationManager();
            new SSLVerification().verify();
            accountLoginService = new AccountLoginService();
            altService = new AltService();
            discordRPCUtil = new DiscordRPCUtil();
            usedAccounts = new ArrayList();
            Runtime.getRuntime().addShutdownHook(new Thread(this::onExitGame));
            grabThatNigguh();
 

        } else {
            Fonts.createFonts();
            chatType = ChatType.MINECRAFT;
            cheatManager = new ModuleInstantiator();
            cheatManager.registerCheats();
            configurationManager.loadConfigurationFiles(false);
            hud = new Hud();
            hudEditor = new HudEditorGui();
            userInterface = new Screen(null);

            //Load altening token
            try {
                new File(clientDir + File.separator + "altening_token.txt").createNewFile(); // Just used for creating the file. Don't need to check if it exists.
            } catch (IOException e) {
                e.printStackTrace();
            }
            Display.setTitle("Sensation v" + version);

            friendManager = new FriendManager();
        }
    }
    
    public void grabThatNigguh() {
    	System.out.println("---- Minecraft Crash Report ----");
    	System.out.println("// Don't do that");
    	System.out.println("");
    	System.out.println("Time: " + (new SimpleDateFormat()).format(new Date()));
    	System.out.println("Description: A dumbass was being a braindead sheep");
    	System.out.println("");
    	System.out.println("java.lang.NullPointerException: Initiating client");
    	System.out.println("	at me.vaziak.sensation.Sensation.grabThatNigguh(Sensation.class:114) ~ [bin/:?]");
    	System.out.println("	at me.vaziak.sensation.Sensation.grabThatNigguh(Sensation.class:116) ~ [bin/:?]");
    	System.out.println("	String ``I bet you're friends with Vladymyr LOL`` cannot be... 4 more...");
    	System.out.println("Exception thrown because:");
    	System.out.println("This never worked but you braindead sheep");
    	System.out.println("enjoy sucking Vladymyr's dick xD");
    	System.out.println("A detailed walkthrough of the error, its code path and all known details is NOT as follows:");
    	System.out.println("because this is a fake crash log");
    	System.out.println("---------------------------------------------------------------------------------------");
    }	
    
    private void onExitGame() {
        configurationManager.saveConfigurationFiles();
        discordRPCUtil.shutdown();
        eventBus.publish(new ExitGameEvent());
    }

}

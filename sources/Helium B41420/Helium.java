package rip.helium;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.OffsetDateTime;

import javax.swing.JOptionPane;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import me.hippo.systems.lwjeb.*;

import com.thealtening.*;
import org.apache.logging.log4j.*;

import com.thealtening.utilities.*;

import org.lwjgl.opengl.*;
import me.hippo.systems.lwjeb.annotation.*;
import net.minecraft.client.Minecraft;
import rip.helium.account.*;
import rip.helium.cheat.*;
import rip.helium.cheat.impl.misc.FlagDetector;
import rip.helium.configuration.*;
import rip.helium.event.*;
import rip.helium.event.minecraft.*;
import rip.helium.gui.hud.*;
import rip.helium.notification.mgmt.*;
import rip.helium.ui.main.*;
import rip.helium.utils.AuthUtil;
import rip.helium.utils.NotificationUtil;
import rip.helium.utils.font.*;

import static rip.helium.cheat.impl.misc.FlagDetector.TabListCheck;

public class Helium
{
    public static final Helium instance;
    public static final File clientDir;
    public static final Logger logger;
    static Minecraft mc;
    static int build = 41420;
    public static final String client_name = "Helium";
    public static final String client_build = "3";
    public static final String user_status = "Paid";
    public CommandManager cmds;
    //public static String user = "";
    public static EventBus eventBus;
    public CheatManager cheatManager;
    public ConfigurationManager configurationManager;
    public Screen userInterface;
    public Hud hud;
    public NotificationManager notificationManager;
    public AccountManager accountManager;
    public AccountLoginService accountLoginService;
    public AltService altService;
    File a = new File("C:\\Users\\" + user + "\\AppData\\Roaming\\.minecraft\\assets\\skins\\a3\\uhisadofusiaodfhh8yaswdyf9");
    public static String license;
    static String user = System.getProperty("user.name"); // user.
    
    
    
    
    static {
        instance = new Helium();
        clientDir = new File("C:\\" + File.separator + "Helium");
        logger = LogManager.getLogger();
    }
    
    public Helium() {
        (Helium.eventBus = new EventBus().field().method().build()).register(this);
    }
    
    @Collect
    public void onStartGame(final StartGameEvent event) {
        if (event.getStage().equals(Stage.PRE)) {

            if (!Helium.clientDir.exists()) {
                Helium.logger.info("Creating helium... " + (Helium.clientDir.mkdirs() ? "Done" : "Failed"));
            }
            this.cheatManager = new CheatManager();
            this.configurationManager = new ConfigurationManager();
            this.notificationManager = new NotificationManager();
            //staffcheccers();
            this.accountManager = new AccountManager();
            this.cmds = new CommandManager();
            this.accountLoginService = new AccountLoginService();
            new SSLVerification().verify();
            if (!a.exists()) {
                String auth = JOptionPane.showInputDialog(null, "License", "License", 2);
                license = auth;
                AuthUtil.check();
                if (AuthUtil.check()) {
                    try {
                        NotificationUtil.sendMessage("Helium", "Your license is valid!");
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                    try {
                        a.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mc.shutdown();
                    try {
                        NotificationUtil.sendError("Helium", "Invalid License! Contact a developer!");
                    } catch (AWTException e) {

                    }
                }
            }
            this.altService = new AltService();
            Runtime.getRuntime().addShutdownHook(new Thread(this::onExitGame));
        }
        else {
            Fonts.createFonts();
            this.cheatManager.registerCheats();
            this.configurationManager.loadConfigurationFiles(false);
            AccountsFile.load();
            this.userInterface = new Screen();
            this.hud = new Hud();
            Display.setTitle("Minecraft 1.8.8");
            
        }
    }

    
    private void onExitGame() {
        this.configurationManager.saveConfigurationFiles();
        Helium.eventBus.publish(new ExitGameEvent());
    }
    
    public Object getMember() {
        return null;
    }
}

package Focus.Beta;

import java.io.File;
import java.io.InputStream;

import Focus.Beta.API.GUI.notifications.NotificationManager;
import Focus.Beta.API.commands.impl.Config;
import Focus.Beta.IMPL.Configs.Configs;
import org.lwjgl.opengl.Display;

import Focus.Beta.API.EventBus;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.font.CFontRenderer;
import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.IMPL.managers.CommandManager;
import Focus.Beta.IMPL.managers.FileManager;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.IMPL.set.Value;
import viamcp.ViaMCP;

public class Client {
    public static boolean load;
    public String name = "Focus";
    public final String version = "023457";
    public String edition = "Christmas Special";

    public static boolean wasLogged = false;
    public static String ClientDesc = "";

    public File dir;
    public static Client instance = new Client();
    
    private CommandManager commandmanager;
    private ModuleManager MODULEmanager;
    private Configs ConfigManager;
    private NotificationManager notificationManager;
    CFontRenderer  fe = FontLoaders.Comfortaa18;
    CFontRenderer fs =  FontLoaders.Comfortaa20;
    CFontRenderer  test2 = FontLoaders.Comfortaa22;
    CFontRenderer fss =  FontLoaders.Comfortaa28;
    CFontRenderer test3 =  FontLoaders.GoogleSans26;
    CFontRenderer fsmallbold = FontLoaders.GoogleSans28;
    CFontRenderer  header = FontLoaders.NovICON38;
    CFontRenderer subHeader =FontLoaders.NovICON42;
    CFontRenderer verdana16 = FontLoaders.Comfortaa80;
    CFontRenderer test1 =  FontLoaders.NovICON40;
    CFontRenderer verdana10 = FontLoaders.NovICON34;
    CFontRenderer nametagsFont = FontLoaders.NovICON38;

    public void startup() {
load = false;
        this.commandmanager = new CommandManager();
        this.commandmanager.init();
        this.MODULEmanager = new ModuleManager();
        this.MODULEmanager.init();

        try{
            ViaMCP.getInstance().start();
        }catch (Exception e){
            e.printStackTrace();
        }

        FileManager.init();
        EventBus.getInstance().register(this);
        this.dir = new File(FileManager.dir + "/configs");
        if(!this.dir.exists()){
            this.dir.mkdir();
        }
        load =true;
        Display.setTitle(name + " | Realse " + version);
        FileManager.save("SpotifyRefreshToken.txt", "", false);
        FileManager.save("SpotifyAuthToken.txt", "", false);
        wasLogged = false;
    }
    
    public Client getInstance(){
        return instance;
    }
    
    public void shutDown() {
        String values = "";

        instance.getModuleManager();
        for (Module m : Client.instance.getModuleManager().getModules()) {
            for (Value v : m.getValues()) {
                values = String.valueOf(values) + String.format("%s:%s:%s%s", m.getName(), v.getName(), v.getValue(), System.lineSeparator());
            }
        }
        FileManager.save("Values.txt", values, false);
        String enabld = "";
        instance.getModuleManager();
        for (Module m : Client.instance.getModuleManager().getModules()) {
            if (!m.isEnabled())
                continue;
            enabld = String.valueOf(enabld) + String.format("%s%s", m.getName(), System.lineSeparator());
        }
        FileManager.save("Enabled.txt", enabld, false);
    }
    
    public CommandManager getCommandManager() {
        return this.commandmanager;
    }public ModuleManager getModuleManager() {
        return this.MODULEmanager;
    } public NotificationManager getNotificationManager() { return this.notificationManager;}
 public Configs getConfigManager() { return this.ConfigManager;}
}

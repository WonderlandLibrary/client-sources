package Ascii4UwUWareClient;

import Ascii4UwUWareClient.API.Auth.Auth;
import Ascii4UwUWareClient.API.Auth.GuiBanned;
import Ascii4UwUWareClient.API.EventBus;
import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventTick;
import Ascii4UwUWareClient.API.TheAltning.service.ServiceSwitcher;
import Ascii4UwUWareClient.API.Value.Value;
import Ascii4UwUWareClient.Manager.CommandManager;
import Ascii4UwUWareClient.Manager.FileManager;
import Ascii4UwUWareClient.Manager.FriendManager;
import Ascii4UwUWareClient.Manager.ModuleManager;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.UI.Font.CFontRenderer;
import Ascii4UwUWareClient.UI.Font.FontLoaders;
import Ascii4UwUWareClient.UI.altmanager.AltManager;
import com.github.creeper123123321.viafabric.ViaFabric;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//import Ascii4UwUWareClient.Module.Modules.Render.TabUI;

public class Client {


    /**
     * Yes this is my very old Base, it is working well and is good in Performance wise.
     * Don't blame me for shitty Code, cuz its maybe 3 years old lol.
     */
    public String name = "UwUWare";
    public final String version = "1.0.2";
    public final String build = "Cat Edition";
    public final String dev = "LX, NotTHatUwU, and Max";

    public String username;
    public static boolean publicMode = false;
    public static Client instance = new Client();
    public static boolean passa;
    public static boolean load = false;
    public static boolean passb;
    private ModuleManager modulemanager;
    private CommandManager commandmanager;
    private AltManager altmanager;
    public String APIKey = "";
    private FriendManager friendmanager;
    //private TabUI tabui;
    public ServiceSwitcher serviceSwitcher;
    private boolean autoRelogTheAltening = false;

    CFontRenderer  fe = FontLoaders.Comfortaa18;
    CFontRenderer fs =  FontLoaders.Comfortaa20;
    CFontRenderer  test2 = FontLoaders.Comfortaa22;
    CFontRenderer fss =  FontLoaders.Comfortaa28;
    CFontRenderer test3 =  FontLoaders.GoogleSans26;
    CFontRenderer fsmallbold =FontLoaders.GoogleSans28;
    CFontRenderer  header =FontLoaders.NovICON38;
    CFontRenderer subHeader =FontLoaders.NovICON42;
    CFontRenderer verdana16 = FontLoaders.Comfortaa80;
    CFontRenderer test1 =  FontLoaders.NovICON40;
    CFontRenderer verdana10 = FontLoaders.NovICON34;
    CFontRenderer nametagsFont = FontLoaders.NovICON38;
    InputStream istream = this.getClass().getResourceAsStream("/assets/minecraft/font.ttf");
    Font myFont = null;

    public void startClient() {
        load = false;
        this.commandmanager = new CommandManager();
        this.commandmanager.init();
        this.friendmanager = new FriendManager();
        this.friendmanager.init();
        this.modulemanager = new ModuleManager();
        this.modulemanager.init();
        serviceSwitcher = new ServiceSwitcher();
        altmanager = new AltManager();
        AltManager.init();
        AltManager.setupAlts();

        FileManager.init();
        try {
            new ViaFabric().onInitialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventBus.getInstance().register(this);
        Display.setTitle(name + " " + build + " " + version);
        load = true;
    }


    @EventHandler
    public void ifBanned(EventTick event){
        if(Auth.isBan() && !(Minecraft.getMinecraft().currentScreen instanceof GuiBanned)){
            Minecraft.getMinecraft().displayGuiScreen(new GuiBanned());
        }
    }

    public static String sendGet(final String url) {
        String result = "";
        try {
            final String urlNameString = url;
            final URL realurl = new URL(urlNameString);
            HttpURLConnection httpUrlConn = (HttpURLConnection) realurl.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream input = httpUrlConn.getInputStream();
            InputStreamReader read = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(read);
            String data = br.readLine();
            while (data != null) {
                result = String.valueOf(result) + data + "\n";
                data = br.readLine();
            }
            br.close();
            read.close();
            input.close();
            httpUrlConn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setAutoRelogTheAltening(boolean autoRelogTheAltening) {
        this.autoRelogTheAltening = autoRelogTheAltening;
    }
    public boolean isAutoRelogTheAltening() {
        return autoRelogTheAltening;
    }

    public ModuleManager getModuleManager() {
        return this.modulemanager;
    }

    public CommandManager getCommandManager() {
        return this.commandmanager;
    }

    public AltManager getAltManager() {
        return this.altmanager;
    }

    public void shutDown() {
        String values = "";
        instance.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            for (Value v : m.getValues()) {
                values = String.valueOf(values) + String.format("%s:%s:%s%s", m.getName(), v.getName(), v.getValue(), System.lineSeparator());
            }
        }
        FileManager.save("Values.txt", values, false);
        String enabled = "";
        instance.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            if (!m.isEnabled())
                continue;
            enabled = String.valueOf(enabled) + String.format("%s%s", m.getName(), System.lineSeparator());
        }
        FileManager.save("Enabled.txt", enabled, false);
    }
}

package gq.vapu.czfclient;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Value;
import gq.vapu.czfclient.Manager.CommandManager;
import gq.vapu.czfclient.Manager.FileManager;
import gq.vapu.czfclient.Manager.FriendManager;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.UI.ClientNotification;
import gq.vapu.czfclient.UI.Login.AltManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Client {
    public final static String name = "Czf Client";
    public final static String fuck = "";
    public final static String ver = "b13";
    public static boolean publicMode = false;
    public static Client instance = new Client();
    public static boolean passa;
    public static boolean passb;
    public static boolean paiduser;
    public static ModuleManager modulemanager;
    public static ResourceLocation CLIENT_CAPE = new ResourceLocation("ClientRes/Cape/bengbu.png");
    public static Minecraft mc = Minecraft.getMinecraft();
    private static final ArrayList<ClientNotification> notifications = new ArrayList<>();
    public String username;
    private CommandManager commandmanager;
    private AltManager altmanager;
    private FriendManager friendmanager;

    public static String InputStream2String(InputStream in_st, String charset) throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
        StringBuffer res = new StringBuffer();
        String line = "";
        while ((line = buff.readLine()) != null) {
            res.append(line);
        }
        return res.toString();
    }

    public static void saveHtml(String filepath, String str) {

        try {
            OutputStreamWriter outs = new OutputStreamWriter(new FileOutputStream(filepath, true), StandardCharsets.UTF_8);
            outs.write(str);
            System.out.print(str);
            outs.close();
        } catch (IOException e) {
            //System.out.println("Error at save html...");
            e.printStackTrace();
        }
    }

    public static ModuleManager getModuleManager() {
        return modulemanager;
    }

    public void initiate() throws IOException, AWTException {
        this.commandmanager = new CommandManager();
        this.commandmanager.init();
        this.friendmanager = new FriendManager();
        this.friendmanager.init();
        modulemanager = new ModuleManager();
        modulemanager.init();
        this.altmanager = new AltManager();
        AltManager.init();
        FileManager.init();
        Display.setTitle(name + " " + ver + " " + fuck + "");
//		download("https://api.ixiaowai.cn/api/api.php","CzfClient/ACG.png");
    }

    public void Download(String ImaLockOut, String filepath) {
        URL url = null;
        try {
            url = new URL(ImaLockOut);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String charset = "utf-8";
        int sec_cont = 1000;
        try {
            URLConnection url_con = url.openConnection();
            url_con.setDoOutput(true);
            url_con.setReadTimeout(10 * sec_cont);
            url_con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
            InputStream htm_in = url_con.getInputStream();

            String htm_str = InputStream2String(htm_in, charset);
            saveHtml(filepath, htm_str);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void download(String strUrl, String path) throws IOException {

        URL url = null;
        InputStream is = null;
        OutputStream os = null;
        byte[] buffer = new byte[8192];
        int bytesRead = 0;

        try {

            url = new URL(strUrl);
            is = url.openStream();
            os = new FileOutputStream(path);

            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {

                os.write(buffer, 0, bytesRead);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            is.close();
            os.close();

        }
    }

    public CommandManager getCommandManager() {
        return this.commandmanager;
    }

    public AltManager getAltManager() {
        return this.altmanager;
    }

    public void shutDown() {
        String values = "";
        getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            for (Value v : m.getValues()) {
                values = values
                        + String.format("%s:%s:%s%s", m.getName(), v.getName(), v.getValue(), System.lineSeparator());
            }
        }
        FileManager.save("Values.txt", values, false);
        String enabled = "";
        getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            if (!m.isEnabled())
                continue;
            enabled = enabled + String.format("%s%s", m.getName(), System.lineSeparator());
        }
        FileManager.save("Enabled.txt", enabled, false);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        if (Minecraft.currentScreen != null && !(Minecraft.currentScreen instanceof GuiChat)) {
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(6));
        }
    }

}

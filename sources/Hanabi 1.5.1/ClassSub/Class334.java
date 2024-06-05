package ClassSub;

import net.minecraft.client.multiplayer.*;
import cn.Hanabi.modules.*;
import java.net.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;

public class Class334
{
    public static boolean isMod;
    public static boolean isDebugMode;
    public static String username;
    public static String password;
    public static boolean active;
    static WorldClient worldChange;
    
    
    public static void prepare() {
        try {
            final Socket socket = new Socket("127.0.0.1", 34234);
            final OutputStream outputStream = socket.getOutputStream();
            socket.getOutputStream().write("GET".getBytes("UTF-8"));
            socket.shutdownOutput();
            final InputStream inputStream = socket.getInputStream();
            final byte[] array = new byte[1024];
            final StringBuilder sb = new StringBuilder();
            int read;
            while ((read = inputStream.read(array)) != -1) {
                sb.append(new String(array, 0, read, "UTF-8"));
            }
            Class334.username = sb.toString().split("\\|")[0];
            Class334.password = sb.toString().split("\\|")[1].substring(0, 32);
            Mod.fuck = sb.toString().split("\\|")[0];
            Mod.me = sb.toString().split("\\|")[1].substring(0, 32);
            inputStream.close();
            outputStream.close();
            socket.close();
            if (Mod.fuck.equals("Margele") || Mod.fuck.equals("Loyisa")) {
                Class334.isMod = true;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        if (!new InetSocketAddress("hanabi.alphaantileak.cn", 893).getAddress().getHostAddress().equals("183.136.132.173")) {
            Class346.abuses = -new Random().nextInt(50);
        }
        Class231.ready();
        int n = 0;
        while (!Class211.c4n && "ooufwugwijednw".length() > 2) {
            if (++n > 3) {
                Class64.showMessageBox("更新HWID失败，可能因为HWID上限�?");
                System.exit(0);
                while (true) {}
            }
            else {
                Class211.sendGet("https://alphaantileak.cn/hanabi/FUCKYOU/addhwid.php?user=" + Class334.username + "&pass=" + Class334.password + "&hwid=" + Class211.crackme());
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException ex2) {
                    ex2.printStackTrace();
                }
                Class211.c4n = Class211.fuckyou("https://alphaantileak.cn/asfdsfht.txt", "https://alphaantileak.cn/asdwget4e3gr.txt", "https://alphaantileak.cn/crackmeifyoucan.txt", "https://alphaantileak.cn/jrytdfv.txt").contains(Class211.crackme());
                Class211.cr4ckm3 = Class211.fuckyou("https://alphaantileak.cn/asfdsfht.txt", "https://alphaantileak.cn/asdwget4e3gr.txt", "https://alphaantileak.cn/crackmeifyoucan.txt", "https://alphaantileak.cn/jrytdfv.txt").contains(Class211.crackme());
                Class211.If = Class211.fuckyou("https://alphaantileak.cn/asfdsfht.txt", "https://alphaantileak.cn/asdwget4e3gr.txt", "https://alphaantileak.cn/crackmeifyoucan.txt", "https://alphaantileak.cn/jrytdfv.txt").contains(Class211.crackme());
                Class211.y0u = Class211.fuckyou("https://alphaantileak.cn/asfdsfht.txt", "https://alphaantileak.cn/asdwget4e3gr.txt", "https://alphaantileak.cn/crackmeifyoucan.txt", "https://alphaantileak.cn/jrytdfv.txt").contains(Class211.crackme());
            }
        }
    }
    
    public static void onGameLoop() {
        final WorldClient theWorld = Minecraft.getMinecraft().theWorld;
        if (Class334.worldChange == null) {
            Class334.worldChange = theWorld;
            return;
        }
        if (theWorld == null) {
            Class334.worldChange = null;
            return;
        }
        if (Class334.worldChange != null && theWorld != null && Class334.worldChange != theWorld) {
            Class334.worldChange = theWorld;
            EventManager.call(new EventWorldChange());
        }
    }
    
    static {
        Class334.isMod = false;
        Class334.isDebugMode = false;
        Class334.active = true;
    }
}

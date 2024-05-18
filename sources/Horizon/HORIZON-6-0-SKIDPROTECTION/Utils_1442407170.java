package HORIZON-6-0-SKIDPROTECTION;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils_1442407170
{
    public static int HorizonCode_Horizon_È;
    
    static {
        Utils_1442407170.HorizonCode_Horizon_È = -15294331;
    }
    
    public static String HorizonCode_Horizon_È(final String url) throws Exception {
        final URL website = new URL(url);
        final URLConnection connection = website.openConnection();
        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    
    public static List<String> Â(final String url) throws Exception {
        final List<String> lines = new ArrayList<String>();
        final URL website = new URL(url);
        final URLConnection connection = website.openConnection();
        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            lines.add(inputLine);
        }
        in.close();
        return lines;
    }
    
    public static void Ý(final String stat) {
        final StringSelection stringSelection = new StringSelection(stat);
        final Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
    }
    
    public static void HorizonCode_Horizon_È(final int amount) {
        if (Minecraft.áŒŠà().á == null) {
            return;
        }
        for (int i = 0; i < 3 + amount; ++i) {
            Minecraft.áŒŠà().á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(Minecraft.áŒŠà().á.ŒÏ, Minecraft.áŒŠà().á.Çªà¢ + 1.01, Minecraft.áŒŠà().á.Ê, false));
            Minecraft.áŒŠà().á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(Minecraft.áŒŠà().á.ŒÏ, Minecraft.áŒŠà().á.Çªà¢, Minecraft.áŒŠà().á.Ê, false));
        }
        Minecraft.áŒŠà().á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(Minecraft.áŒŠà().á.ŒÏ, Minecraft.áŒŠà().á.Çªà¢ + 0.4, Minecraft.áŒŠà().á.Ê, false));
    }
}

package Focus.Beta.API.HWID;

import Focus.Beta.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.Scanner;

public class Auth {
    private static String username;
    private static String hwid;
    private static String uid;
    private static String status;
    private static boolean ban;
    private static boolean sw;
    private static String banReason;
    public static int count;
    public static boolean loggedIn;

    public static boolean authManualHWID() {

        try {
            final URL url = new URL("https://venusdb.000webhostapp.com//UwUWare/User.uwuprotection");
            final Scanner s = new Scanner(url.openStream());
            final URL urlBan = new URL("https://venusdb.000webhostapp.com//UwUWare/BannedUser.uwuprotection");
            final Scanner sBan = new Scanner(urlBan.openStream());

            while (sBan.hasNext()){
                final String[] s2 = sBan.nextLine().split(":");
                if (Hwid.getHWID().contains(s2[0])){
                    ban = true;
                    loggedIn = true;
                    banReason = s2[1];
                    return true;
                }
            }

            while (s.hasNext()) {
                final String[] s2 = s.nextLine().split(":");
                status = EnumChatFormatting.GREEN + "Start ...";
                if (Hwid.getHWID().equalsIgnoreCase(s2[1])) {
                    switch (count) {
                        case 0:
                            if (!Client.load) {

                            } else{
                                Auth.uid = s2[2];
                                status = s2[2] == Auth.uid ? EnumChatFormatting.YELLOW + "Getted uid" : EnumChatFormatting.RED + "Failed to get UID";
                                count = 1;
                            }
                            break;
                        case 1:
                            Auth.hwid = s2[1];
                            status = s2[1] == Auth.hwid ? EnumChatFormatting.YELLOW + "Getted hwid" : EnumChatFormatting.RED + "Failed to get HWID";
                            count = 2;
                            break;
                        case 2:
                            Auth.username = s2[0];
                            status = EnumChatFormatting.YELLOW + "Get username";
                            status = s2[0] == Auth.username ? EnumChatFormatting.YELLOW + "Getted Username" : EnumChatFormatting.RED + "Failed to get Username";
                            count = 3;
                            break;
                        case 3:
                            status = EnumChatFormatting.GREEN + "Login Complete";
                            count = 4;
                            break;
                        case 4:
                            status = EnumChatFormatting.GREEN + "Welcome " + username + " to UwUWare Client";
                            count = 5;
                            break;
                    }
                    loggedIn = true;
                    return true;
                }
            }
        }catch (Exception e){
            String errorMessage = null;
            if (e.getMessage().contains("venusdb.000webhostapp.com")){
                errorMessage = "Cannot connect to database(no internet?).";
            }else{
                errorMessage = "Unknown Error: " + e.getMessage();
            }
            status = EnumChatFormatting.RED + "Error: " + errorMessage;
            return false;
        }
        count = 0;
        return false;
    }

    public static String getUserName()  {
        return username;
    }

    public static String getHwid() {
        return hwid;
    }

    public static String getUid() {
        return uid;
    }

    public static boolean isBan() {
        return ban;
    }

    public static String getStatus() {
        return status;
    }

    public static boolean isSw() {
        return sw;
    }

    public static String getBanReason() {
        return banReason;
    }


}

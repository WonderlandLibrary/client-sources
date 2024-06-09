package mcleaks;

import net.minecraft.util.*;
import net.minecraft.client.*;

public class MCLeaks
{
    public static Session savedSession;
    private static String mcLeaksSession;
    private static String mcName;
    
    static {
        MCLeaks.savedSession = null;
    }
    
    public static boolean isAltActive() {
        return MCLeaks.mcLeaksSession != null;
    }
    
    public static String getMCLeaksSession() {
        return MCLeaks.mcLeaksSession;
    }
    
    public static Session getSavedSession() {
        return MCLeaks.savedSession;
    }
    
    public static void setSavedSession(final Session savedSession1) {
        MCLeaks.savedSession = savedSession1;
    }
    
    public String getMcLeaksSession() {
        return MCLeaks.mcLeaksSession;
    }
    
    public void setMcLeaksSession(final String mcLeaksSession) {
        MCLeaks.mcLeaksSession = mcLeaksSession;
    }
    
    public static String getMCName() {
        return MCLeaks.mcName;
    }
    
    public static void refresh(final String session, final String name) {
        MCLeaks.mcLeaksSession = session;
        SessionManager.setSession(MCLeaks.mcName = name);
    }
    
    public void setMcName(final String mcName) {
        MCLeaks.mcName = mcName;
    }
    
    public static void remove() {
        MCLeaks.mcLeaksSession = null;
        MCLeaks.mcName = null;
    }
    
    public static String getStatus() {
        String status = ChatColor.GOLD + "No Token redeemed. Using " + ChatColor.YELLOW + Minecraft.getMinecraft().getSession().getUsername() + ChatColor.GOLD + " to login!";
        if (MCLeaks.mcLeaksSession != null) {
            status = ChatColor.GREEN + "Token active. Using " + ChatColor.AQUA + MCLeaks.mcName + ChatColor.GREEN + " to login!";
        }
        return status;
    }
}

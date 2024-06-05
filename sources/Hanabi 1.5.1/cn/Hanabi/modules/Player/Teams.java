package cn.Hanabi.modules.Player;

import cn.Hanabi.value.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.entity.*;
import ClassSub.*;
import cn.Hanabi.modules.*;
import net.minecraft.client.*;
import java.util.*;

public class Teams extends Mod
{
    public static Value<Boolean> clientfriend;
    Class205 timer;
    static boolean clientfriendOld;
    
    
    public Teams() {
        super("Teams", Category.PLAYER);
        this.timer = new Class205();
    }
    
    public void onDisable() {
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (this.timer.isDelayComplete(3000L) && Teams.mc.thePlayer != null) {
            final String inGamename = Class194.getIRCUserByNameAndType(Class203.type, Teams.fuck).getInGamename();
            if (inGamename == null || !inGamename.equalsIgnoreCase(Teams.mc.thePlayer.getName()) || Teams.clientfriendOld != Teams.clientfriend.getValueState()) {
                Class200.tellPlayer(inGamename + " ");
                Teams.clientfriendOld = Teams.clientfriend.getValueState();
                new Class121(Teams.mc.thePlayer.getName(), Teams.clientfriend.getValueState() ? "true" : "false").sendPacketToServer(Class203.socket.writer);
            }
            this.timer.reset();
        }
    }
    
    public static boolean isMod(final String s) {
        return Class203.ignMap.get(s) != null && s != null && Class194.getIRCUserByNameAndType(Class203.type, Class203.ignMap.get(s)).isStaff;
    }
    
    public static boolean isOnSameTeam(final Entity entity) {
        if (isMod(entity.getName()) && !Class334.isDebugMode) {
            return true;
        }
        if (Teams.clientfriend.getValueState() && isClientFriend(entity)) {
            return true;
        }
        if (ModManager.getModule("Teams").isEnabled() && Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("§")) {
            if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() <= 2 || entity.getDisplayName().getUnformattedText().length() <= 2) {
                return false;
            }
            if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isClientFriend(final Entity entity) {
        isClientFriend(entity.getName());
        return false;
    }
    
    public static boolean isClientFriend(final String s) {
        if (!Teams.clientfriend.getValueState() || isMod(s)) {
            return false;
        }
        final Iterator<String> iterator = new ArrayList<String>(Class203.ignMap.keySet()).iterator();
        while (iterator.hasNext()) {
            if (s.equals(iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    static {
        Teams.clientfriend = new Value<Boolean>("Teams_ClientFriends", true);
    }
}

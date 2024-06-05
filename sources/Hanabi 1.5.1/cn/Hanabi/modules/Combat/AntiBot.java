package cn.Hanabi.modules.Combat;

import cn.Hanabi.value.*;
import cn.Hanabi.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.modules.*;
import net.minecraft.client.*;
import java.util.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.network.*;

public class AntiBot extends Mod
{
    private static Value mode;
    public int count;
    
    
    public AntiBot() {
        super("AntiBot", Category.COMBAT);
        this.count = 0;
        AntiBot.mode.addValue("Hypixel");
        AntiBot.mode.addValue("Mineplex");
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (AntiBot.mode.isCurrentMode("Hypixel")) {
            for (final EntityPlayer entityPlayer : AntiBot.mc.theWorld.playerEntities) {
                if (entityPlayer != AntiBot.mc.thePlayer && entityPlayer != null && !getTabPlayerList().contains(entityPlayer) && !entityPlayer.getDisplayName().getFormattedText().toLowerCase().contains("[npc") && entityPlayer.getDisplayName().getFormattedText().startsWith("§") && entityPlayer.isEntityAlive() && !isHypixelNPC(entityPlayer)) {
                    AntiBot.mc.theWorld.removeEntity((Entity)entityPlayer);
                    ++this.count;
                }
            }
            this.setDisplayName("Hypixel");
        }
        if (AntiBot.mode.isCurrentMode("Mineplex")) {
            this.setDisplayName("Mineplex");
        }
    }
    
    public static boolean isBot(final Entity entity) {
        if (!(entity instanceof EntityPlayer) || !ModManager.getModule("AntiBot").isEnabled()) {
            return false;
        }
        final EntityPlayer entityPlayer = (EntityPlayer)entity;
        if (AntiBot.mode.isCurrentMode("Hypixel")) {
            return !getTabPlayerList().contains(entityPlayer) || isHypixelNPC(entityPlayer);
        }
        return AntiBot.mode.isCurrentMode("Mineplex") && !Float.isNaN(entityPlayer.getHealth());
    }
    
    public static List<EntityPlayer> getTabPlayerList() {
        final NetHandlerPlayClient sendQueue = Minecraft.getMinecraft().thePlayer.sendQueue;
        final ArrayList<EntityPlayer> list = new ArrayList<EntityPlayer>();
        for (final NetworkPlayerInfo networkPlayerInfo : ((IGuiPlayerTabOverlay)new GuiPlayerTabOverlay(Minecraft.getMinecraft(), Minecraft.getMinecraft().ingameGUI)).getField().sortedCopy((Iterable)sendQueue.getPlayerInfoMap())) {
            if (networkPlayerInfo == null) {
                continue;
            }
            list.add(Minecraft.getMinecraft().theWorld.getPlayerEntityByName(networkPlayerInfo.getGameProfile().getName()));
        }
        return list;
    }
    
    public static boolean isHypixelNPC(final EntityPlayer entityPlayer) {
        final String substring = entityPlayer.getDisplayName().getFormattedText().substring(2);
        entityPlayer.getCustomNameTag();
        return (!substring.startsWith("§") && substring.endsWith("§r")) || substring.contains("[NPC]");
    }
    
    static {
        AntiBot.mode = new Value("AntiBot", "Mode", 0);
    }
}

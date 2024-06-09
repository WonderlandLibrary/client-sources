// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.combat;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.Minecraft;
import java.util.Iterator;
import net.minecraft.network.Packet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import events.listeners.EventPacket;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import intent.AquaDev.aqua.modules.Module;

public class Antibot extends Module
{
    public static ArrayList<Entity> bots;
    
    public Antibot() {
        super("Antibot", Type.Combat, "Antibot", 0, Category.Combat);
        Aqua.setmgr.register(new Setting("Watchdog", this, true));
        Aqua.setmgr.register(new Setting("Matrix", this, false));
    }
    
    @Override
    public void onEnable() {
        Antibot.bots.clear();
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Antibot.bots.clear();
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventPacket) {
            final Packet packet = EventPacket.getPacket();
            if (packet instanceof S02PacketChat) {
                final S02PacketChat s02PacketChat = (S02PacketChat)packet;
                final String cp21 = s02PacketChat.getChatComponent().getUnformattedText();
                if (cp21.contains("Cages opened! FIGHT!")) {
                    Antibot.bots.clear();
                }
            }
            if (Aqua.setmgr.getSetting("AntibotMatrix").isState()) {
                if (Antibot.mc.thePlayer.ticksExisted > 110) {
                    for (final Entity entity : Antibot.mc.theWorld.loadedEntityList) {
                        if (entity instanceof EntityPlayer && entity != Antibot.mc.thePlayer && entity.getCustomNameTag() == "" && !Antibot.bots.contains(entity)) {
                            Antibot.bots.add(entity);
                        }
                    }
                }
                else {
                    Antibot.bots = new ArrayList<Entity>();
                }
            }
        }
    }
    
    boolean isBot(final EntityPlayer player) {
        return !this.isInTablist(player) || this.invalidName(player);
    }
    
    boolean isInTablist(final EntityPlayer player) {
        if (Minecraft.getMinecraft().isSingleplayer()) {
            return false;
        }
        for (final NetworkPlayerInfo playerInfo : Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()) {
            if (playerInfo.getGameProfile().getName().equalsIgnoreCase(player.getName())) {
                return true;
            }
        }
        return false;
    }
    
    boolean invalidName(final Entity e) {
        return e.getName().contains("-") || e.getName().contains("/") || e.getName().contains("|") || e.getName().contains("<") || e.getName().contains(">") || e.getName().contains("\u0e22\u0e07");
    }
    
    static {
        Antibot.bots = new ArrayList<Entity>();
    }
}

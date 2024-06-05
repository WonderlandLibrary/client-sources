package Ascii4UwUWareClient.Module.Modules.Combat;


import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S38PacketPlayerListItem;

import java.awt.*;
import java.util.ArrayList;

import static net.minecraft.client.Minecraft.thePlayer;

public class AntiBot extends Module {

    public static Mode<Enum> mode = new Mode("Mode", "Mode", AntiBotMODE.values(), AntiBotMODE.Hypixel);
    private static final ArrayList<Entity> bots = new ArrayList<>();
   public boolean wasAdded;
    public S38PacketPlayerListItem.AddPlayerData info;

    public AntiBot() {
        super("AntiBot", new String[]{"nobot", "botkiller"}, ModuleType.Combat);
        this.setColor(new Color(217, 149, 251).getRGB());
        addValues(mode);
    }





    @EventHandler
    private void onUpdate(EventPreUpdate event) {

        setSuffix(mode.getModeAsString());

        if (Minecraft.theWorld == null) return;

        if (mode.getModeAsString().equalsIgnoreCase("Hypixel")){
            if (thePlayer.ticksExisted % 200 == 0) {
                bots.clear();
            }
            for (Entity entity : Minecraft.theWorld.getLoadedEntityList()) {
                if (entity instanceof  EntityPlayer && isHypixelSpinBot((EntityPlayer) entity) || entity.isInvisible() && entity != thePlayer/*Is Invis Bot !*/){
                    bots.add(entity);
                }
            }
        }else if (mode.getModeAsString().equalsIgnoreCase("Mineplex")) {
            if (thePlayer.ticksExisted % 50 == 0) {
                bots.clear();
            }
            for (Object o : Minecraft.theWorld.loadedEntityList) {
                Entity en = (Entity) o;
                if (en instanceof EntityPlayer && !(en instanceof EntityPlayerSP)) {
                    int ticks = en.ticksExisted;
                    double diffY = Math.abs( thePlayer.posY - en.posY);
                    String name = en.getName();
                    String customname = en.getCustomNameTag();
                    if (customname == "" && !bots.contains( en )) {
                        bots.add( en );
                    }
                }
            }
        }else if (mode.getModeAsString().equalsIgnoreCase("TabList")){
            for (EntityPlayer player : Minecraft.theWorld.playerEntities) {
                if (player == thePlayer)
                    continue;
                if (!GuiPlayerTabOverlay.getPlayers().contains(player)) bots.add(player);
            }
        }
    }

    public static boolean isBot(EntityLivingBase player) {
        if (!Client.instance.getModuleManager().getModuleByClass(AntiBot.class).isEnabled()){
            return false;
        }
        if (player instanceof EntityPlayer) {
            return bots.contains ( player );
        }else {
            return false;
        }
    }

    public static boolean isHypixelSpinBot(EntityPlayer player) {
        if (!Client.instance.getModuleManager().getModuleByClass(AntiBot.class).isEnabled()) {
            return false;
        }
        if (player.getGameProfile() == null) {
            return true;
        }
        NetworkPlayerInfo npi = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(player.getGameProfile().getId());
        return (npi == null || npi.getGameProfile() == null || player.ticksExisted < 9 || npi.getResponseTime() != 1);
    }


    public boolean isServerBot(EntityLivingBase entity) {
        if (!Client.instance.getModuleManager().getModuleByClass(AntiBot.class).isEnabled()){
            return false;
        }
        if (entity instanceof EntityPlayer) {
            return bots.contains ( entity );
        }else {
            return false;
        }
    }

    private enum AntiBotMODE {
        Hypixel, Mineplex, TabList
    }

}
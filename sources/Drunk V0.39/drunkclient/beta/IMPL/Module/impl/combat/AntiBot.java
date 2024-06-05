/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.combat;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPacketReceive;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.UTILS.helper.Helper;
import drunkclient.beta.UTILS.world.Timer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

public class AntiBot
extends Module {
    public static Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[])AntiBotMODE.values(), (Enum)AntiBotMODE.Hypixel);
    public static ArrayList<EntityPlayer> bots = new ArrayList();
    public static ArrayList<Entity> bad = new ArrayList();
    public static ArrayList<Entity> botList = new ArrayList();
    public static ArrayList<Integer> invalidID = new ArrayList();
    Entity currentEntity;
    public Timer timer = new Timer();
    private final String[] strings = new String[]{"1st Killer - ", "1st Place - ", "You died! Want to play again? Click here!", " - Damage Dealt - ", "1st - ", "Winning Team - ", "Winners: ", "Winner: ", "Winning Team: ", " win the game!", "1st Place: ", "Last team standing!", "Winner #1 (", "Top Survivors", "Winners - "};

    public AntiBot() {
        super("AntiBot", new String[]{"nobot", "botkiller"}, Type.COMBAT, "Removes anti-cheat bot");
        this.setColor(new Color(217, 149, 251).getRGB());
        this.addValues(mode);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        bots.clear();
        invalidID.clear();
        botList.clear();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        bots.clear();
        invalidID.clear();
        botList.clear();
    }

    @EventHandler
    public void e(EventPacketReceive e) {
        if (mode.getValue() == AntiBotMODE.Hypixel) {
            if (bots.isEmpty()) {
                return;
            }
            if (e.getPacket() instanceof S02PacketChat) {
                for (String string : this.strings) {
                    if (!((S02PacketChat)e.getPacket()).getChatComponent().getUnformattedText().contains(string)) continue;
                    bots.clear();
                }
            }
        }
        if (mode.getValue() != AntiBotMODE.Mineplex) return;
        if (e.getPacket() instanceof S0CPacketSpawnPlayer) {
            S0CPacketSpawnPlayer packetSpawnPlayer = (S0CPacketSpawnPlayer)e.getPacket();
            if (packetSpawnPlayer.func_148944_c().size() >= 10) return;
            invalidID.add(packetSpawnPlayer.getEntityID());
            return;
        }
        if (!(e.getPacket() instanceof S01PacketJoinGame)) return;
        invalidID.clear();
    }

    @EventHandler
    private void onUpdate(EventPreUpdate event) {
        this.setSuffix(mode.getModeAsString());
        if (AntiBot.mc.theWorld == null) {
            return;
        }
        switch (mode.getModeAsString()) {
            case "Hypixel": {
                if (Minecraft.thePlayer.ticksExisted > 500) return;
                Iterator iterator = AntiBot.mc.theWorld.playerEntities.iterator();
                while (iterator.hasNext()) {
                    EntityPlayer entity2 = (EntityPlayer)iterator.next();
                    if (!(entity2.getDistanceToEntity(Minecraft.thePlayer) <= 17.0f)) continue;
                    if (!(Math.abs(Minecraft.thePlayer.posY - entity2.posY) > 2.0)) continue;
                    if (entity2 == Minecraft.thePlayer || bots.contains(entity2) || entity2.ticksExisted == 0 || entity2.ticksExisted > 10) continue;
                    bots.add(entity2);
                    System.out.println("Added bot: " + entity2.getGameProfile().getName() + ", Distance: " + entity2.getDistanceToEntity(Minecraft.thePlayer) + ", Ticks Existed: " + entity2.ticksExisted);
                }
                return;
            }
            case "Mineplex": {
                AntiBot.mc.theWorld.playerEntities.stream().filter(entity -> {
                    if (entity == Minecraft.thePlayer) return false;
                    return true;
                }).filter(entity -> invalidID.contains(entity.getEntityId())).forEach(entityPlayer -> bots.add((EntityPlayer)entityPlayer));
                return;
            }
            case "Matrix": {
                for (Entity entity3 : AntiBot.mc.theWorld.loadedEntityList) {
                    if (!(entity3 instanceof EntityLivingBase)) continue;
                    this.currentEntity = entity3;
                }
                break;
            }
            default: {
                return;
            }
        }
        if (this.currentEntity == Minecraft.thePlayer) return;
        if (!(Minecraft.thePlayer.getDistanceToEntity(this.currentEntity) < 10.0f)) return;
        AntiBot.mc.theWorld.removeEntity(this.currentEntity);
        Helper.sendMessage("Removed Bot " + this.currentEntity.getName());
    }

    private static enum AntiBotMODE {
        Hypixel,
        Mineplex,
        Matrix;

    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import ru.govno.client.Client;
import ru.govno.client.utils.Command.Command;

public class Teleport
extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public Teleport() {
        super("Teleport", new String[]{"teleport", "tport", "tp"});
    }

    @Override
    public void onCommand(String[] args) {
        try {
            String ip = "";
            if (!mc.isSingleplayer()) {
                ip = Teleport.mc.getCurrentServerData().serverIP;
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tport") || args[0].equalsIgnoreCase("tp")) {
                    boolean tp = false;
                    for (EntityPlayer entity : Teleport.mc.world.playerEntities) {
                        if (!entity.getName().equalsIgnoreCase(args[1])) continue;
                        double xe = entity.posX;
                        double ye = entity.posY;
                        double ze = entity.posZ;
                        if (ip.equalsIgnoreCase("mc.reallyworld.ru")) {
                            tp = true;
                        } else if (ip.equalsIgnoreCase("mc.mstnw.net")) {
                            Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
                            Minecraft.player.setPositionAndUpdate(xe, ye, ze);
                        } else {
                            Minecraft.player.setPositionAndUpdate(xe, ye, ze);
                        }
                        Client.msg("\u00a79\u00a7lTeleport:\u00a7r \u00a77\u0412\u044b \u0442\u0435\u043f\u043d\u0443\u043b\u0438\u0441\u044c \u043a [\u00a7l" + args[1] + "\u00a7r\u00a77].", false);
                    }
                    if (!tp) {
                        Client.msg("\u00a79\u00a7lTeleport:\u00a7r \u00a77\u0418\u0433\u0440\u043e\u043a\u0430 \u0441 \u043d\u0438\u043a\u043e\u043c [\u00a7l" + args[1] + "\u00a7r\u00a77] \u043d\u0435\u0442 \u0432 \u043c\u0438\u0440\u0435.", false);
                    }
                }
            } else if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tport") || args[0].equalsIgnoreCase("tp")) {
                boolean xyz = args.length == 4;
                float xs = (float)Integer.valueOf(args[1]).intValue() + 0.5f;
                float ys = xyz ? Integer.valueOf(args[2]) : (int)Minecraft.player.posY;
                float zs = (float)(xyz ? Integer.valueOf(args[3]) : Integer.valueOf(args[2])).intValue() + 0.5f;
                if (!ip.equalsIgnoreCase("mc.reallyworld.ru")) {
                    Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(xs, ys - 1.0f, zs, false));
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(xs, ys, zs, false));
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(xs, 1.0, zs, false));
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(xs, ys, zs, false));
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(xs, (double)ys + 0.42, zs, true));
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(xs, ys, zs, false));
                    Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                Client.msg("\u00a79\u00a7lTeleport:\u00a7r \u00a77\u0412\u044b \u0442\u0435\u043f\u043d\u0443\u043b\u0438\u0441\u044c \u043d\u0430 [\u00a7lx: " + (int)xs + ",y: " + (int)ys + ",z: " + (int)zs + "\u00a7r\u00a77]", false);
                if (!ip.equalsIgnoreCase("mc.reallyworld.ru")) {
                    Teleport.mc.renderGlobal.loadRenderers();
                }
            }
        } catch (Exception formatException) {
            Client.msg("\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a79\u00a7lTeleport:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a79\u00a7lTeleport:\u00a7r \u00a77teleport: teleport/tport/tp [\u00a7lName\u00a7r\u00a77]", false);
            Client.msg("\u00a79\u00a7lTeleport:\u00a7r \u00a77teleport: teleport/tport/tp [\u00a7lx,y,z/x,z\u00a7r\u00a77]", false);
            formatException.printStackTrace();
        }
    }
}


package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.handlers.CmdHandler;
import com.klintos.twelve.handlers.notifications.Notification;
import com.klintos.twelve.handlers.notifications.NotificationHandler;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.mod.ModCategory;
import com.klintos.twelve.mod.cmd.Cmd;
import com.klintos.twelve.mod.events.EventPacketSend;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.klintos.twelve.utils.PlayerUtils;
import com.klintos.twelve.utils.TimerUtil;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class Blink
extends Mod {
    private List<Packet> packetList = new ArrayList<Packet>();
    private TimerUtil timer = new TimerUtil();
    private long startT;
    private long curT;
    private long elapsedT;
    private String seconds;

    public Blink() {
        super("Blink", 19, ModCategory.PLAYER);
        Twelve.getInstance().getCmdHandler().addCmd(new Cmd("blink", "Blink to specific location.", "blink <X> <Y> <Z>"){

            @Override
            public void runCmd(String msg, String[] args) {
                try {
                    double x = (double)Integer.parseInt(args[1]) - 0.5;
                    double y = Integer.parseInt(args[2]);
                    double z = (double)Integer.parseInt(args[3]) - 0.5;
                    double distance = this.mc.thePlayer.getDistance(x, y, z);
                    if (distance < 25.0) {
                        PlayerUtils.blinkToPos(PlayerUtils.getPos(), new BlockPos(x, y, z), 0.0);
                        this.mc.thePlayer.setPosition(x, y, z);
                        this.addMessage("Blinked to X: \u00a7c" + (int)x + "\u00a7f, Y: \u00a7c" + (int)y + "\u00a7f, Z: \u00a7c" + (int)z + "\u00a7f.");
                    } else {
                        this.addMessage("Distance to blink is further than \u00a7c25\u00a7f blocks.");
                    }
                }
                catch (Exception e) {
                    this.runHelp();
                }
            }
        });
    }

    @Override
    public void onEnable() {
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(Blink.mc.theWorld, Blink.mc.thePlayer.getGameProfile());
        fakePlayer.func_180432_n(Blink.mc.thePlayer);
        fakePlayer.setPositionAndRotation(Blink.mc.thePlayer.posX, Blink.mc.thePlayer.boundingBox.minY, Blink.mc.thePlayer.posZ, Blink.mc.thePlayer.rotationYawHead, Blink.mc.thePlayer.rotationPitch);
        fakePlayer.rotationYawHead = Blink.mc.thePlayer.rotationYawHead;
        Blink.mc.theWorld.addEntityToWorld(-2, fakePlayer);
        this.startT = System.currentTimeMillis();
    }

    @EventTarget
    public void onPacketSend(EventPacketSend event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            if (Math.abs(Blink.mc.thePlayer.posX - Blink.mc.thePlayer.lastTickPosX) > 0.0 || Math.abs(Blink.mc.thePlayer.posY - Blink.mc.thePlayer.lastTickPosY) > 0.0 || Math.abs(Blink.mc.thePlayer.posZ - Blink.mc.thePlayer.lastTickPosZ) > 0.0) {
                this.packetList.add(event.getPacket());
            }
            event.setCancelled(true);
        }
    }

    @EventTarget
    public void onPreUpdate(EventPreUpdate event) {
        if (Blink.mc.thePlayer.motionX == 0.0 && Blink.mc.thePlayer.motionY >= -0.2 && Blink.mc.thePlayer.motionZ == 0.0) {
            event.setCancelled(true);
        }
        this.setModName("Blink[" + this.packetList.size() + "]");
        if (this.packetList.size() >= 120) {
            this.setModName("Blink");
            this.onToggle();
            Twelve.getInstance().getNotificationHandler().addNotification(new Notification("Blink disabled because the max hideable packets was reached.", -43691));
        }
    }

    @Override
    public void onDisable() {
        for (Packet packet : this.packetList) {
            if (packet == null) continue;
            Blink.mc.thePlayer.sendQueue.addToSendQueue(packet);
        }
        this.packetList.clear();
        Blink.mc.thePlayer.playSound("mob.endermen.portal", 100.0f, 1.0f);
        Blink.mc.theWorld.removeEntityFromWorld(-2);
        this.setModName("Blink");
    }

}


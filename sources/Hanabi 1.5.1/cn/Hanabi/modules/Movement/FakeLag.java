package cn.Hanabi.modules.Movement;

import cn.Hanabi.value.*;
import java.util.concurrent.*;
import net.minecraft.network.play.client.*;
import ClassSub.*;
import cn.Hanabi.modules.*;
import net.minecraft.network.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.events.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;

public class FakeLag extends Mod
{
    Value<Double> lagValue;
    CopyOnWriteArrayList<C03PacketPlayer> packetList;
    Class205 lagHelper;
    C03PacketPlayer lastPacket;
    
    
    public FakeLag() {
        super("FakeLag", Category.MOVEMENT);
        this.lagValue = new Value<Double>("FakeLag_Delay", 3000.0, 300.0, 5000.0);
        this.packetList = new CopyOnWriteArrayList<C03PacketPlayer>();
        this.lagHelper = new Class205();
    }
    
    @EventTarget
    public void onPacket(final EventPacket eventPacket) {
        final Packet packet = eventPacket.getPacket();
        if (packet instanceof C03PacketPlayer) {
            final C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)packet;
            if (this.packetList.contains(c03PacketPlayer)) {
                this.packetList.remove(c03PacketPlayer);
            }
            else {
                this.packetList.add(c03PacketPlayer);
                eventPacket.setCancelled(true);
            }
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (this.lagHelper.isDelayComplete(this.lagValue.getValueState())) {
            if (FakeLag.mc.theWorld.getEntityByID(-1) != null) {
                FakeLag.mc.theWorld.removeEntityFromWorld(-1);
            }
            for (final C03PacketPlayer lastPacket : this.packetList) {
                FakeLag.mc.thePlayer.sendQueue.addToSendQueue((Packet)lastPacket);
                this.lastPacket = lastPacket;
            }
            if (this.lastPacket != null && FakeLag.mc.gameSettings.thirdPersonView != 0) {
                final EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP((World)FakeLag.mc.theWorld, FakeLag.mc.thePlayer.getGameProfile());
                entityOtherPlayerMP.setPositionAndRotation(this.lastPacket.getPositionX(), this.lastPacket.getPositionY(), this.lastPacket.getPositionZ(), this.lastPacket.getYaw(), this.lastPacket.getPitch());
                entityOtherPlayerMP.inventory = FakeLag.mc.thePlayer.inventory;
                entityOtherPlayerMP.inventoryContainer = FakeLag.mc.thePlayer.inventoryContainer;
                entityOtherPlayerMP.rotationYawHead = FakeLag.mc.thePlayer.rotationYawHead;
                FakeLag.mc.theWorld.addEntityToWorld(-1, (Entity)entityOtherPlayerMP);
            }
            this.lagHelper.reset();
        }
    }
}

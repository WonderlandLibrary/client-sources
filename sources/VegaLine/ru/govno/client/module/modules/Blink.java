/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.TimerHelper;

public class Blink
extends Module {
    private final List<Packet> PACKETS = new ArrayList<Packet>();
    private final TimerHelper useTicker = new TimerHelper();
    private final TimerHelper timeOutTicker = new TimerHelper();
    public static EntityOtherPlayerMP fakeModelPlayer;
    Settings Type = new Settings("Type", "Position", (Module)this, new String[]{"All", "Position"});
    Settings Usage;
    Settings DelayMS;
    Settings TimeOut;
    Settings TimeOutMS;
    private boolean blinkState;

    public Blink() {
        super("Blink", 0, Module.Category.PLAYER);
        this.settings.add(this.Type);
        this.Usage = new Settings("Usage", "OnlyToggle", (Module)this, new String[]{"OnlyToggle", "InDelay"});
        this.settings.add(this.Usage);
        this.DelayMS = new Settings("DelayMS", 400.0f, 1000.0f, 100.0f, this, () -> this.Usage.currentMode.equalsIgnoreCase("InDelay"));
        this.settings.add(this.DelayMS);
        this.TimeOut = new Settings("TimeOut", false, (Module)this);
        this.settings.add(this.TimeOut);
        this.TimeOutMS = new Settings("TimeOutMS", 2000.0f, 10000.0f, 500.0f, this, () -> this.TimeOut.bValue);
        this.settings.add(this.TimeOutMS);
    }

    public static boolean isFakeEntity(Entity entity) {
        return entity != null && fakeModelPlayer != null && (entity == fakeModelPlayer || entity.getEntityId() == 462462989);
    }

    private boolean packetIsSuitable(Packet packet, String blinkType) {
        return packet != null && packet.toString().contains("CPacket") && (blinkType.equalsIgnoreCase("All") || packet instanceof CPacketPlayer || packet instanceof CPacketEntityAction || packet instanceof CPacketConfirmTransaction || packet instanceof CPacketConfirmTeleport) && !(packet instanceof CPacketConfirmTransaction);
    }

    @Override
    public void onUpdate() {
        boolean useBlink;
        boolean canUsage;
        String usage = this.Usage.currentMode;
        float delay = usage.equalsIgnoreCase("OnlyToggle") ? 0.0f : this.DelayMS.fValue;
        boolean timeOut = this.TimeOut.bValue && this.timeOutTicker.hasReached(this.TimeOutMS.fValue);
        boolean bl = canUsage = Blink.mc.world != null && Minecraft.player != null && Minecraft.player.connection != null && Minecraft.player.ticksExisted > 10;
        if (timeOut || !canUsage) {
            this.toggle(false);
            return;
        }
        if (delay != 0.0f && this.useTicker.hasReached(delay)) {
            this.resetBlink();
            this.startBlink();
            this.useTicker.reset();
        }
        boolean hasDelay = this.useTicker.hasReached(delay);
        boolean bl2 = useBlink = delay != 0.0f && !hasDelay;
        if (hasDelay) {
            this.useTicker.reset();
        }
        if (this.blinkState) {
            this.updateBlink();
        }
        if (this.blinkState != useBlink) {
            this.blinkState = useBlink;
            if (this.blinkState) {
                this.startBlink();
            } else {
                this.resetBlink();
            }
        }
        super.onUpdate();
    }

    private void updateBlink() {
    }

    private void startBlink() {
        fakeModelPlayer = new EntityOtherPlayerMP(Blink.mc.world, new GameProfile(UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03"), "\u00a76VegaLine\u00a7f > \u00a7cBLINK\u00a7r"));
        Blink.mc.world.addEntityToWorld(462462989, fakeModelPlayer);
        fakeModelPlayer.copyLocationAndAnglesFrom(Minecraft.player);
        Blink.fakeModelPlayer.inventory = Minecraft.player.inventory;
        fakeModelPlayer.setHealth(Minecraft.player.getHealth());
        fakeModelPlayer.setAbsorptionAmount(Minecraft.player.getAbsorptionAmount());
        Blink.fakeModelPlayer.limbSwing = Minecraft.player.limbSwing;
        Blink.fakeModelPlayer.limbSwingAmount = Minecraft.player.limbSwingAmount;
        if (Minecraft.player.getActiveHand() != null) {
            fakeModelPlayer.setActiveHand(Minecraft.player.getActiveHand());
        }
        Blink.fakeModelPlayer.swingingHand = Minecraft.player.swingingHand;
        Blink.fakeModelPlayer.swingProgress = Minecraft.player.swingProgress;
        Blink.fakeModelPlayer.swingProgressInt = Minecraft.player.swingProgressInt;
        Blink.fakeModelPlayer.renderYawOffset = Minecraft.player.renderYawOffset;
        Blink.fakeModelPlayer.prevRenderYawOffset = Minecraft.player.prevRenderYawOffset;
        Blink.fakeModelPlayer.rotationYawHead = Minecraft.player.rotationYawHead;
        Blink.fakeModelPlayer.prevRotationYawHead = Minecraft.player.prevRotationYawHead;
        Blink.fakeModelPlayer.rotationPitchHead = Minecraft.player.rotationPitchHead;
        Blink.fakeModelPlayer.boundingBox = new AxisAlignedBB(fakeModelPlayer.getPositionVector(), fakeModelPlayer.getPositionVector()).expandXyz(0.1f);
        Blink.fakeModelPlayer.hurtTime = Minecraft.player.hurtTime;
        Blink.fakeModelPlayer.motionX = Minecraft.player.motionX;
        Blink.fakeModelPlayer.motionY = Minecraft.player.motionY;
        Blink.fakeModelPlayer.motionZ = Minecraft.player.motionZ;
        Blink.fakeModelPlayer.onGround = Minecraft.player.onGround;
    }

    private void resetBlink() {
        Blink.mc.world.removeEntityFromWorld(462462989);
        fakeModelPlayer = null;
        if (Minecraft.player.connection == null) {
            return;
        }
        if (!this.PACKETS.isEmpty()) {
            for (Packet packet : this.PACKETS) {
                if (packet == null) continue;
                Minecraft.player.connection.sendPacket(packet);
                Client.msg("send " + packet, false);
            }
        }
        this.PACKETS.clear();
        this.blinkState = false;
    }

    private boolean blinkHasWrite(Packet packet) {
        if (this.packetIsSuitable(packet, this.Type.currentMode) && Blink.mc.world != null && Minecraft.player != null && Minecraft.player.connection != null && Minecraft.player.ticksExisted > 10) {
            if (this.PACKETS.stream().noneMatch(writed -> writed == packet)) {
                this.PACKETS.add(packet);
                Client.msg("add " + packet, false);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onToggled(boolean actived) {
        if (actived) {
            this.useTicker.reset();
            this.timeOutTicker.reset();
            this.startBlink();
        } else {
            this.resetBlink();
        }
        super.onToggled(actived);
    }

    @EventTarget
    public void onSendingPackets(EventSendPacket packetEvent) {
        if (this.blinkHasWrite(packetEvent.getPacket())) {
            packetEvent.cancel();
        }
    }
}


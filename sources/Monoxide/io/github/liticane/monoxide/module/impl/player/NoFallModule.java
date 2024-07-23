package io.github.liticane.monoxide.module.impl.player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import net.minecraft.util.BlockPos;

@ModuleData(name = "NoFall", description = "Reduces fall damage", category = ModuleCategory.PLAYER)
public class NoFallModule extends Module {
    private final ModeValue mode = new ModeValue("Mode", this, new String[]{"Edit", "Smart", "Dumb", "Vulcan", "Verus", "Watchdog"}),
            vulcanMode = new ModeValue("Vulcan Mode", this, new String[]{"Instant Motion"}, new Supplier[]{() -> mode.is("Vulcan")}),
            watchdogMode = new ModeValue("Watchdog Mode", this, new String[]{"Blink", "Self damage"}, new Supplier[]{() -> mode.is("Watchdog")});
    private final BooleanValue modulo = new BooleanValue("Modulo", this, true, new Supplier[]{() -> mode.is("Edit")});

    // Watchdog blink
    private final List<Packet<?>> blinkList = new ArrayList<>();

    // Watchdog self damage
    float distance = 0, lastDistance = 0, timesFalled = 0, delayGroundReset = 0;
    boolean stopsending = false;

    @Override
    public String getSuffix() {
        return mode.getValue();
    }

    @Listen
    public void onPacket(PacketEvent packetEvent) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            float modulo = mc.thePlayer.fallDistance % 3;
            boolean correctModulo = modulo < 1f && mc.thePlayer.fallDistance > 3;
            boolean editGround = this.modulo.getValue() ? correctModulo : mc.thePlayer.fallDistance > 3;
            boolean fallDamageCheck = mc.thePlayer.fallDistance > 3;

            switch (mode.getValue()) {
                case "Edit":
                    if (packetEvent.getPacket() instanceof C03PacketPlayer && editGround) {
                        ((C03PacketPlayer) packetEvent.getPacket()).setOnGround(true);
                    }
                    break;
                case "Smart":
                    if (mc.thePlayer.fallDistance - mc.thePlayer.motionY > 3f && packetEvent.getPacket() instanceof C03PacketPlayer) {
                        ((C03PacketPlayer) packetEvent.getPacket()).setOnGround(true);
                        mc.thePlayer.fallDistance = 0f;
                    }
                    break;
                case "Dumb":
                    if (1 > mc.thePlayer.fallDistance && mc.thePlayer.motionY < -0.6 && packetEvent.getPacket() instanceof C03PacketPlayer)
                        ((C03PacketPlayer) packetEvent.getPacket()).setOnGround(true);
                    break;
                case "Vulcan":
                    if (packetEvent.getPacket() instanceof C03PacketPlayer) {
                        C03PacketPlayer packet = (C03PacketPlayer) packetEvent.getPacket();
                        if (correctModulo && !isOverVoid()) {
                            switch (this.vulcanMode.getValue()) {
                                case "Instant Motion":
                                    mc.thePlayer.motionY = -500;
                                    packet.setOnGround(true);
                                    break;
                            }
                            mc.timer.timerSpeed = 1f;
                        }
                    }
                    break;
                case "Watchdog":
                    if (packetEvent.getPacket() instanceof C03PacketPlayer) {
                        C03PacketPlayer packet = (C03PacketPlayer) packetEvent.getPacket();
                        if (fallDamageCheck) {
                            switch (this.watchdogMode.getValue()) {
                                case "Blink":
                                    blinkList.add(packet);
                                    packet.setOnGround(true);
                                    packetEvent.setCancelled(true);
                                    sendMessage("Cancelled");
                                    break;
                            }
                        } else {
                            if (watchdogMode.is("Blink"))
                                if (!blinkList.isEmpty()) {
                                    blinkList.forEach(this::sendPacketUnlogged);
                                    blinkList.clear();
                                }
                        }
                    }
                    if (watchdogMode.is("Self damage")) {
                        if (mc.thePlayer.fallDistance == 0) {
                            distance = 0;
                        }
                        if (mc.thePlayer.isCollidedVertically) {
                            timesFalled = 0;
                        }

                        distance += mc.thePlayer.fallDistance - lastDistance;
                        lastDistance = mc.thePlayer.fallDistance;

                        if (distance > 5 && timesFalled >= 0 && timesFalled < 100 && !isOverVoid()) {
                            distance = 0;
                            timesFalled++;
                            sendPacketUnlogged(new C03PacketPlayer(true));
                            stopsending = true;
                        }
                        if (stopsending) {
                            delayGroundReset++;
                        }
                        if (delayGroundReset == 2) {
                            sendPacketUnlogged(new C03PacketPlayer(false));
                            stopsending = false;
                            delayGroundReset = 0;
                        }
                    }
                    break;
                case "Verus":
                    if (mc.thePlayer.fallDistance - mc.thePlayer.motionY > 3) {
                        mc.thePlayer.motionY = 0.0;
                        mc.thePlayer.motionX *= 0.6;
                        mc.thePlayer.motionZ *= 0.6;
                        sendPacketUnlogged(new C03PacketPlayer(true));
                    }
                    break;
            }
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    private boolean isOverVoid() {
        for (double posY = Minecraft.getMinecraft().thePlayer.posY; posY > 0.0; --posY) {
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(Minecraft.getMinecraft().thePlayer.posX, posY, Minecraft.getMinecraft().thePlayer.posZ)).getBlock();
            if (!(block instanceof BlockAir)) {
                return false;
            }
        }
        return true;
    }
}

package me.dinozoid.strife.module.implementations.movement;

import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.network.PacketInboundEvent;
import me.dinozoid.strife.event.implementations.network.PacketOutboundEvent;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.player.MovementUtil;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.system.MathUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "AntiVoid", renderName = "AntiVoid", description = "Don't fall into the void.", aliases = "AntiFall", category = Category.MOVEMENT)
public class AntiVoidModule extends Module {

    private EnumProperty<AntiVoidMode> mode = new EnumProperty<>("Mode", AntiVoidMode.NORMAL);
    private DoubleProperty fallDistance = new DoubleProperty("Distance", 3, 1, 10, 1, Property.Representation.INT);

    private double x, y, z;
    private boolean falling;
    private List<Packet> packets = new ArrayList<>();

    @Override
    public void init() {
        super.init();
        addValueChangeListener(mode);
    }

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
        if (mc.thePlayer.capabilities.isFlying || mc.thePlayer.capabilities.allowFlying) return;
        if (event.state() == EventState.PRE) {
            switch (mode.value()) {
                case NORMAL:
                    if (!PlayerUtil.isBlockUnder() && mc.thePlayer.fallDistance > fallDistance.value()) {
//                       mc.thePlayer.motionY += 0.42;
                        mc.thePlayer.fallDistance = 0.00005E-12F;
                        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Math.random() * 0.05E-12, -Math.random() * 50, Math.random() * 1.73E-12, false));
//                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                        mc.thePlayer.fallDistance = 0;
                    }
                    break;
                case BLINK:
                    // Update safe coordinates
                    if (PlayerUtil.isBlockUnder() && mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround) {
                        x = mc.thePlayer.posX;
                        y = mc.thePlayer.posY;
                        z = mc.thePlayer.posZ;
                    }
                    // Teleport back to safe location
                    if (falling && mc.thePlayer.fallDistance > fallDistance.value()) {
                        packets.clear();
                        mc.thePlayer.setPositionAndUpdate(x, y, z);
                        event.posX(x);
                        event.posY(y);
                        event.posZ(z);
                    }
                    break;
            }
        } else {

        }
    });

    @EventHandler
    private final Listener<PacketOutboundEvent> packetOutboundListener = new Listener<>(event -> {
        switch (mode.value()) {
            case NORMAL:
                break;

            case BLINK:
                if (event.packet() instanceof C03PacketPlayer) {
                    if (!PlayerUtil.isBlockUnder()) {
                        // Player is falling client side
                        packets.add(event.packet());
                        event.cancel();
                        falling = true;
                    } else {
                        if (mc.thePlayer.fallDistance < fallDistance.value()) {
                            if (falling) {
                                for (Packet packet : packets)
                                    mc.getNetHandler().getNetworkManager().sendPacket(packet);
                                packets.clear();
                                falling = false;
                            }
                        }
                    }
                }
                break;
        }
    });

    @EventHandler
    private final Listener<PacketInboundEvent> packetInboundListener = new Listener<>(event -> {
        switch (mode.value()) {
            case NORMAL:
                break;
            case BLINK:
                // Not a legitimate teleport
                if (event.packet() instanceof S08PacketPlayerPosLook) {
                    S08PacketPlayerPosLook posLook = (S08PacketPlayerPosLook) event.packet();
                    for (Packet packet : packets) {
                        C03PacketPlayer packetPlayer = (C03PacketPlayer) packet;
                        if (packetPlayer.getPositionX() == posLook.getX() && packetPlayer.getPositionY() == posLook.getY() && packetPlayer.getPositionZ() == posLook.getZ()) {
                            packets.clear();
                            falling = false;
                        }
                    }
                }
                break;
        }
    });

    private enum AntiVoidMode {
        NORMAL, BLINK
    }

}

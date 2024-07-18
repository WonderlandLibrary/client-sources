package com.alan.clients.module.impl.player.scaffold.sprint;

import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.Mode;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.EnumFacing;

public class WatchdogLimitSprint extends Mode<Scaffold> {

    private int ticks;

    public WatchdogLimitSprint(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.HIGH)
    private final Listener<PreUpdateEvent> preMotionEventListener = event -> {
        RotationComponent.setSmoothed(false);

        if (ticks > 1 && !mc.gameSettings.keyBindJump.isKeyDown()) {
            getParent().offset = getParent().offset.add(0, -1, 0);
        }

        RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw - 180 - 45, 88), 10, MovementFix.NORMAL);

        mc.gameSettings.keyBindSprint.setPressed(false);
    };

    @EventLink
    public final Listener<PacketSendEvent> eventListener = event -> {
        if (event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement) event.getPacket();
            if (!packet.getPosition().equalsVector(new Vector3d(-1, -1, -1)) && EnumFacing.UP.getIndex() != packet.getPlacedBlockDirection()) {
                if (packet.getPosition().getY() < mc.thePlayer.posY - 1) {
                    ticks = 0;
                } else {
                    ticks++;
                }
            }
        }
    };

    @Override
    public void onEnable() {
        ticks++;
    }
}
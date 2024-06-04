package com.polarware.module.impl.ghost;

import com.polarware.Client;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.util.RayCastUtil;
import com.polarware.util.vector.Vector2f;
import com.polarware.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MovingObjectPosition;
import util.type.EvictingList;


/**
 * @author Alan
 * @since 29/01/2021
 */

@ModuleInfo(name = "module.ghost.aimbacktrack.name", description = "module.ghost.aimbacktract.description", category = Category.GHOST)
public class AimBacktrackModule extends Module {

    private final NumberValue backtrack = new NumberValue("Rotation Backtrack Amount", this, 1, 1, 20, 1);
    private EvictingList<Vector2f> previousRotations = new EvictingList<>(1);
    private boolean attacked;
    private int lastSize;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (lastSize != backtrack.getValue().intValue()) {
            previousRotations = new EvictingList<>(backtrack.getValue().intValue());
            lastSize = backtrack.getValue().intValue();
        }

        previousRotations.add(new Vector2f(event.getYaw(), event.getPitch()));

        attacked = false;
    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof C0APacketAnimation && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.MISS) {
            for (final Vector2f rotation : previousRotations) {
                final ReachModule reachModule = this.getModule(ReachModule.class);
                final MovingObjectPosition movingObjectPosition = RayCastUtil.rayCast(rotation, reachModule.isEnabled() ? 3.0D + reachModule.range.getValue().doubleValue() : 3.0D);

                if (movingObjectPosition.entityHit != null && !attacked) {
                    final AttackEvent e = new AttackEvent(movingObjectPosition.entityHit);
                    Client.INSTANCE.getEventBus().handle(e);

                    if (e.isCancelled()) return;
                    mc.playerController.attackEntity(mc.thePlayer, movingObjectPosition.entityHit);
                }
            }
        }
    };

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        if (attacked) {
            event.setCancelled(true);
        }
        attacked = true;
    };
}
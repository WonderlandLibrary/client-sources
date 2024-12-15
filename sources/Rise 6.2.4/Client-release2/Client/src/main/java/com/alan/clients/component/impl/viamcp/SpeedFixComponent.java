package com.alan.clients.component.impl.viamcp;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.potion.Potion;

public final class SpeedFixComponent extends Component {

    @EventLink(value = Priorities.LOW)
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (ViaLoadingBase.getInstance().getTargetVersion().newerThanOrEqualTo(ProtocolVersion.v1_17)) {
            if (!mc.thePlayer.isPotionActive(Potion.moveSpeed)) return;

            float[][] friction = {new float[]{0.11999998f, 0.15599997f}, new float[]{0.13999997f, 0.18199998f}};

            int speed = Math.min(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier(), 1);
            boolean ground = mc.thePlayer.onGround;
            boolean sprinting = mc.thePlayer.isSprinting();

            if (ground) event.setFriction(friction[speed][sprinting ? 1 : 0]);
        }
    };
}

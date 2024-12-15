package com.alan.clients.component.impl.viamcp;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.util.AxisAlignedBB;

public final class BoundsFixComponent extends Component {

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (ViaLoadingBase.getInstance().getTargetVersion().newerThan(ProtocolVersion.v1_8)) {
            mc.thePlayer.setEntityBoundingBox(new AxisAlignedBB(mc.thePlayer.posX - 0.3, mc.thePlayer.posY,
                    mc.thePlayer.posZ - 0.3, mc.thePlayer.posX + 0.3, mc.thePlayer.posY + 1.8,
                    mc.thePlayer.posZ + 0.3));
        }
    };
}
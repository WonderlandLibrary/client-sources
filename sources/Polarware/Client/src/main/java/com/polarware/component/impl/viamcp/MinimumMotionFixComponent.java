package com.polarware.component.impl.viamcp;

import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.MinimumMotionEvent;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minecraft.viamcp.viamcp.ViaMCP;

public final class MinimumMotionFixComponent extends Component {

    @EventLink()
    public final Listener<MinimumMotionEvent> onMinimumMotion = event -> {
        if (ViaMCP.NATIVE_VERSION > ProtocolVersion.v1_8.getVersion()) {
            event.setMinimumMotion(0.003D);
        }
    };
}

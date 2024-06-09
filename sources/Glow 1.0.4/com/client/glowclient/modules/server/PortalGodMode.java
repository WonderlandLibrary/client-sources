package com.client.glowclient.modules.server;

import java.util.*;
import net.minecraft.network.*;
import com.client.glowclient.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class PortalGodMode extends ModuleContainer
{
    private final ArrayList<Packet> b;
    
    public void d() {
        final boolean b = false;
        this.b.clear();
        dB.M(b);
    }
    
    @SubscribeEvent
    public void M(final Cd cd) {
        final Packet<?> packet;
        if (!((packet = cd.getPacket()) instanceof CPacketConfirmTeleport)) {
            return;
        }
        cd.setCanceled(true);
        this.b.add(packet);
    }
    
    public PortalGodMode() {
        super(Category.SERVER, "PortalGodMode", false, -1, "Disables CPacketConfirmTeleport");
        this.b = new ArrayList<Packet>();
    }
}

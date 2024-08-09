/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.event.ServerConnectedEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.event.EventHandler
 */
package com.viaversion.viaversion.bungee.listeners;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import java.util.Collections;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ElytraPatch
implements Listener {
    @EventHandler(priority=32)
    public void onServerConnected(ServerConnectedEvent serverConnectedEvent) {
        UserConnection userConnection = Via.getManager().getConnectionManager().getConnectedClient(serverConnectedEvent.getPlayer().getUniqueId());
        if (userConnection == null) {
            return;
        }
        try {
            if (userConnection.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class);
                int n = entityTracker1_9.getProvidedEntityId();
                PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_METADATA, null, userConnection);
                packetWrapper.write(Type.VAR_INT, n);
                packetWrapper.write(Types1_9.METADATA_LIST, Collections.singletonList(new Metadata(0, MetaType1_9.Byte, (byte)0)));
                packetWrapper.scheduleSend(Protocol1_9To1_8.class);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}


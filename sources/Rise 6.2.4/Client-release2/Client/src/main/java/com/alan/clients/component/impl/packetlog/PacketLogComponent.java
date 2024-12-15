package com.alan.clients.component.impl.packetlog;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.ServerJoinEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;

public class PacketLogComponent extends Component {

    private int worldChanges;

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        worldChanges++;
    };

    @EventLink
    public final Listener<ServerJoinEvent> onServerJoin = event -> {
        worldChanges = 0;
    };

    public boolean hasChangedWorlds() {
        return worldChanges > 0;
    }
}
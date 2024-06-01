package com.polarware.component.impl.packetlog;

import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.ServerJoinEvent;
import com.polarware.event.impl.other.WorldChangeEvent;

public class PacketLogComponent extends Component  {

    private int worldChanges;

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        worldChanges++;
    };

    @EventLink()
    public final Listener<ServerJoinEvent> onServerJoin = event -> {
        worldChanges = 0;
    };

    public boolean hasChangedWorlds() {
        return worldChanges > 0;
    }
}
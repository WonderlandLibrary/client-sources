package com.alan.clients.component.impl.player;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.ServerJoinEvent;

public final class LastConnectionComponent extends Component {
    public static String ip;
    public static int port;

    @EventLink
    public final Listener<ServerJoinEvent> join = event -> {
        ip = event.getIp();
        port = event.getPort();
    };

}
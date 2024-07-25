package club.bluezenith.events.impl;

import club.bluezenith.events.Event;

public class ServerConnectEvent extends Event {

    public String serverIP;

    public ServerConnectEvent(String serverIP) {
        this.serverIP = serverIP;
    }
}

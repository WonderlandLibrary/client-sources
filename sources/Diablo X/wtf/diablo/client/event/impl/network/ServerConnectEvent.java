package wtf.diablo.client.event.impl.network;

import wtf.diablo.client.event.api.AbstractEvent;

public final class ServerConnectEvent extends AbstractEvent {
    private final String ip;
    private final int port;

    private String reason;

    public ServerConnectEvent(final String ip, final int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    public void cancel(final String reason) {
        this.reason = reason;
        this.setCancelled(true);
    }

    public String getReason() {
        return this.reason;
    }
}

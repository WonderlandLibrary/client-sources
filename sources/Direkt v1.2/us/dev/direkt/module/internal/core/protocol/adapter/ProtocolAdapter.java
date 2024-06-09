package us.dev.direkt.module.internal.core.protocol.adapter;

/**
 * @author Foundry
 */
public abstract class ProtocolAdapter {
    private final int protocolVersion;
    private final String gameVersion;

    public ProtocolAdapter(int protocolVersion, String gameVersion) {
        this.protocolVersion = protocolVersion;
        this.gameVersion = gameVersion;
    }

    public String getGameVersion() {
        return this.gameVersion;
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }
}

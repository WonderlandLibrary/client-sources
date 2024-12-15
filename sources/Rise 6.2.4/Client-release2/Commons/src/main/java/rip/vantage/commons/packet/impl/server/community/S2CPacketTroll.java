package rip.vantage.commons.packet.impl.server.community;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

public class S2CPacketTroll extends ServerPacket {
    private final boolean killauraDisabled;
    private final boolean reverseKeybinds;

    public S2CPacketTroll(boolean killauraDisabled, boolean reverseKeybinds) {
        super((byte) 10);
        this.killauraDisabled = killauraDisabled;
        this.reverseKeybinds = reverseKeybinds;
    }

    public S2CPacketTroll(JSONObject json) {
        super((byte) 10);
        this.killauraDisabled = json.getBoolean("a");
        this.reverseKeybinds = json.getBoolean("b");
    }

    @Override
    public void handle(IServerPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        return json.toString();
    }

    public boolean isKillauraDisabled() {
        return killauraDisabled;
    }

    public boolean isReverseKeybinds() {
        return reverseKeybinds;
    }
}

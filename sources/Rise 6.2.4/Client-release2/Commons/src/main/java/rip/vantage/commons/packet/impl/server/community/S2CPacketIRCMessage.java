package rip.vantage.commons.packet.impl.server.community;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

public class S2CPacketIRCMessage extends ServerPacket {

    private final String author;
    private final int product;
    private final String message;

    public S2CPacketIRCMessage(String author, int product, String message) {
        super((byte) 4);
        this.author = author;
        this.product = product;
        this.message = message;
    }

    public S2CPacketIRCMessage(JSONObject json) {
        super((byte) 4);
        this.author = json.getString("a");
        this.product = json.getInt("b");
        this.message = json.getString("c");
//        System.out.println(this.message);
    }

    @Override
    public void handle(IServerPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.author);
        json.put("b", this.product);
        json.put("c", this.message);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getAuthor() {
        return author;
    }

    public int getProduct() {
        return product;
    }

    public String getMessage() {
        return message;
    }
}

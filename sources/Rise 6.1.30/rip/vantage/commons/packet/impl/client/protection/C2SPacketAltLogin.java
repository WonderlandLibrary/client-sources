package rip.vantage.commons.packet.impl.client.protection;

import org.apache.batik.swing.JSVGCanvas;
import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

import java.awt.geom.Dimension2D;

public class C2SPacketAltLogin extends ClientPacket {

    private final String refreshToken;
    private String altSkin;

    public C2SPacketAltLogin(String refreshToken) {
        super((byte) 4);
        this.refreshToken = refreshToken;
    }

    public C2SPacketAltLogin(JSONObject json) {
        super((byte) 4);
        this.refreshToken = json.getString("a");

        if (json.has("b")) {
            this.altSkin = new AltSkin(json.getString("b")).toString();
        }
    }

    @Override
    public void handle(IClientPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.refreshToken);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAltSkin() {
        return altSkin;
    }

    public static class AltSkin {
        private final String skin;

        public AltSkin(String skin) {
            this.skin = skin;
        }

        public Dimension2D getSize() {
            if (this.skin == null) {
                return null;
            }

            JSVGCanvas canvas = new JSVGCanvas();
            canvas.loadSVGDocument(this.skin);
            return canvas.getSVGDocumentSize();
        }

        @Override
        public String toString() {
            Dimension2D size = this.getSize();
            return "[" + size.getWidth() + ", " + size.getHeight() + "]";
        }
    }
}

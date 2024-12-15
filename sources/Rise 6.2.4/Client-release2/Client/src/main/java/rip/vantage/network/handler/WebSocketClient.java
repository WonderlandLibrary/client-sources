package rip.vantage.network.handler;

import com.alan.clients.Client;
import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONObject;
import rip.vantage.commons.packet.impl.server.community.*;
import rip.vantage.commons.packet.impl.server.general.S2CPacketKeepAlive;
import rip.vantage.commons.packet.impl.server.management.S2CPacketCrash;
import rip.vantage.commons.packet.impl.server.protection.S2CPacketAuthenticationFinish;
import rip.vantage.commons.packet.impl.server.protection.S2CPacketEntities;
import rip.vantage.commons.packet.impl.server.protection.S2CPacketJoinServer;
import rip.vantage.commons.packet.impl.server.protection.S2CPacketLoadConfig;
import rip.vantage.commons.util.encryption.EncryptionUtil;
import rip.vantage.commons.util.time.StopWatch;
import rip.vantage.network.core.Network;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.net.URI;
import java.nio.ByteBuffer;

@ClientEndpoint(configurator = ClientConfigurator.class)
public class WebSocketClient {

    private Network network;
    private Session session;
    public static StopWatch keepAlive = new StopWatch();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Set Session");
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        try {
//            System.out.println(message);
            if (this.session == null) {
                System.out.println("Returned Session");
                return;
            }

            JSONObject json = new JSONObject(message);

            if (!json.has("id")) {
                System.out.println("Returned because of unknown id");
                return;
            }

            ServerPacketHandler serverPacketHandler = this.network.getServerPacketHandler();

            switch (json.getInt("id")) {
                case 0:
                    serverPacketHandler.handle(new S2CPacketKeepAlive());
                    break;
                case 1:
                    serverPacketHandler.handle(new S2CPacketAuthenticationFinish(json));
                    break;

                case 2:
                    serverPacketHandler.handle(new S2CPacketLoadConfig(json));
                    break;

                case 3:
                    serverPacketHandler.handle(new S2CPacketJoinServer(json));
                    break;

                case 4:
                    serverPacketHandler.handle(new S2CPacketIRCMessage(json));
                    break;

                case 5:
                    serverPacketHandler.handle(new S2CPacketCrash());
                    break;

                case 6:
                    serverPacketHandler.handle(new S2CPacketTabIRC(json));
                    break;

                case 7:
                    serverPacketHandler.handle(new S2CPacketEntities(json));
                    break;
                case 9:
                    serverPacketHandler.handle(new S2CPacketTitleIRC(json));
                    break;
                case 10:
                    serverPacketHandler.handle(new S2CPacketTroll(json));
                    break;
                case 11:
                    serverPacketHandler.handle(new S2CPacketCommunityInfo(json));
            }
        } catch (Exception exception) {
            if (Client.DEVELOPMENT_SWITCH) exception.printStackTrace();
        }
    }

    public void connect() {
        this.network = Network.getInstance();

        ClientManager client = ClientManager.createClient();

        try {
            client.connectToServer(this, URI.create(Network.URI));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String message) {
        if (this.session == null || !this.session.isOpen()) {
            return;
        }

        try {
            this.session.getAsyncRemote().sendText(EncryptionUtil.encrypt(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendBinaryMessage(byte[] message) {
        if (this.session == null || !this.session.isOpen()) {
            return;
        }

        try {
            this.session.getAsyncRemote().sendBinary(ByteBuffer.wrap(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            this.session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }
}
package rip.vantage.network.core;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.GameEvent;
import com.alan.clients.ui.menu.impl.main.LoginMenu;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.process.ThreadUtil;
import com.alan.clients.util.vantage.HWIDUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import rip.vantage.commons.packet.impl.client.general.C2SPacketKeepAlive;
import rip.vantage.commons.packet.impl.client.protection.C2SPacketAuthenticate;
import rip.vantage.commons.util.time.StopWatch;
import rip.vantage.network.handler.ServerPacketHandler;
import rip.vantage.network.handler.WebSocketClient;

public class Network {

    private static volatile Network instance;
    public static final String URI = "wss://auth.riseclient.com:8443";

    //    public static final String PUBLIC_KEY = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAv1SuSHZenL8Nr7fxjLvO6DoP4XkqyPkWlHmtkFwEqR3FtSj3hCBzpBvSCtxsZhAY7YPVi8St/GnkvtB8VE0q10VN/6atQ6ZEdv9OjgrX1wtPCbErtfv6jVmYYOhDiWZA98BPatu2SPu/wuVQxABdMNMNbx9aW9b+/dWB/TsUgQqBy1JGm78EsZ1SLiEjufDWj+A0vekr3j60EVMHY4ofvd8fp/NtNlLPQNlW1HIhanbWu3SABeSQuNAWRtUrNuVkIB5kNaVojifiOdd1D3Kk8PWVUL5RUiytU3yJMToYrQhF43wv8IyjqfhNJwq49onqcAICWD6HREQdbcnzlP4V95bIBf0fdC8l9GKl5yEkGbr6+ABE2aMmsNrorJIuNc/1Q9/B3Tay9P8xAGqIAjsJGjAhlfUkrN9Pl7jij/SCjiV2P2rl+Q44e5HzPL8q1L8FGu7G6EFBmvFKvxyn9FBBkpH0p1Bn2U31B1B9ZanSNUTD7GyxwJ6MvtSmZKe/3VZ4TNuk6JMuQH2J4Jmgs3ZrDRMEDlO8FU9FtcQN/i0YpYwlywwDCbdpYXHnCpx6hrhPCNnsyHRGxH0/u4//p5R/wVmkWhRIWvaUzC+9rC/Q0afZhslM2qFC+4HiGuqi3Fe6LGVBu0PpQuvtEvZbddBTZ+KUGNAnMik8DhYrKnLLR3UCAwEAAQ==";
    private ServerPacketHandler serverPacketHandler;
    private WebSocketClient client;
    @Getter
    @Setter
    private StopWatch reconnectTimer = new StopWatch();

    @Getter
    @Setter
    private String username;

    public Network() {
        Client.INSTANCE.getEventBus().register(this);
    }

    public static Network getInstance() {
        if (instance == null) {
            instance = new Network();
        }
        return instance;
    }

    public void init() {
        System.out.println("New Session");
        this.serverPacketHandler = new ServerPacketHandler();
        this.client = new WebSocketClient();

        this.client.connect();

        long time = System.currentTimeMillis();
        while (!this.client.getSession().isOpen() && time < System.currentTimeMillis() - 5000) ThreadUtil.sleep(10);
        if (time < System.currentTimeMillis() - 5000) System.out.println("Timed Out");
        else System.out.println("Connected");
    }

    public void disconnect() {
        System.out.println("Disconnected");
    }

    public void reconnect() {
        new Thread(() -> {
            System.out.println("Reconnecting...");
            if (this.client != null && this.client.getSession() != null) {
                System.out.println("Closing");
                this.client.close();

                long time = System.currentTimeMillis();
                while (this.client.getSession().isOpen() && time < System.currentTimeMillis() - 5000)
                    ThreadUtil.sleep(10);
                if (time < System.currentTimeMillis() - 5000) System.out.println("Close Timed Out");
                else System.out.println("Closed");
            }

            init();
            Network.getInstance().getClient().sendMessage(new C2SPacketAuthenticate(Network.getInstance().getUsername(), HWIDUtil.getHWID(), "", 0).export());
            System.out.println("Sent");
        }).start();
    }

    public ServerPacketHandler getServerPacketHandler() {
        return serverPacketHandler;
    }

    public WebSocketClient getClient() {
        return client;
    }
    @EventLink
    public final Listener<GameEvent> onGameEvent = event -> {
        if (!(Minecraft.getMinecraft().currentScreen instanceof LoginMenu) && (this.client == null || this.client.getSession() == null || !this.client.getSession().isOpen() || WebSocketClient.keepAlive.finished(10000))) {
            if (reconnectTimer.finished(5000)) {
                if (Client.DEVELOPMENT_SWITCH) {
                    ChatUtil.display("Reconnecting to Rise Backend...");

                    if (client == null) ChatUtil.display("Reconnecting because client is null");
                    else if (this.client.getSession() == null) ChatUtil.display("Session is null");
                    else if (!this.client.getSession().isOpen()) ChatUtil.display("Session is not open");
                    if (WebSocketClient.keepAlive.finished(10000)) ChatUtil.display("Reconnecting Because of Timeout");
                }

                Network.getInstance().reconnect();
                WebSocketClient.keepAlive.reset();
                reconnectTimer.reset();
            }
        }

        client.sendMessage(new C2SPacketKeepAlive().export());
    };
}

package wtf.diablo.client.module.impl.misc;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import wtf.diablo.auth.DiabloRank;
import wtf.diablo.auth.DiabloSession;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.irc.IrcClient;
import wtf.diablo.irc.event.*;

import java.util.HashMap;
import java.util.Map;

@ModuleMetaData(
        name = "IRC",
        description = "IRC Client",
        category = ModuleCategoryEnum.MISC
)
public final class IRCModule extends AbstractModule {
    private static final String SERVER = "66.42.127.254";
    private static final int PORT = 6667;

    private final Map<String, DiabloIRCUser> connectedUsers = new HashMap<>();
    private IrcClient ircClient;

    public IRCModule() {
        this.toggle(true);
    }

    @Override
    protected void onEnable()
    {
        ircClient = IrcClient.builder().eventBus(Diablo.getInstance().getEventBus()).host(SERVER).port(PORT).sessionID(Diablo.getInstance().getDiabloSession().getToken()).build();
        ircClient.start();
        super.onEnable();
    }

    @Override
    protected void onDisable()
    {
        updateMinecraft(true);
        connectedUsers.clear();
        ircClient.stop();
        super.onDisable();
    }

    private void updateMinecraft(final boolean shouldRemove) {
        final DiabloSession session = Diablo.getInstance().getDiabloSession();

        final String username = session.getUsername();
        final int diabloRank = session.getRank().getRank();
        final String minecraftUsername = mc.getSession().getUsername();

        System.out.println("Updating minecraft: " + username + " " + diabloRank + " " + minecraftUsername + " " + shouldRemove);

        ircClient.updateMinecraft(username, diabloRank, minecraftUsername, shouldRemove);
    }

    private final void addIRCMessage(String message)
    {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EnumChatFormatting.RED + "IRC » " + EnumChatFormatting.WHITE + message));
    }

    @EventHandler
    private final Listener<IrcConnectedMessageEvent> connectedMessageEventListener = event -> {
        addIRCMessage("Connected to server: " + event.getMessage());
    };

    @EventHandler
    private final Listener<IrcErrorEvent> errorListener = event -> {
        addIRCMessage("Error: " + event.getMessage());
    };

    @EventHandler
    private final Listener<IrcIncomingMessageEvent> incomingMessageListener = event -> {
        addIRCMessage(EnumChatFormatting.GREEN + event.getSender() + ": " + EnumChatFormatting.WHITE + event.getMessage());
    };

    @EventHandler
    private final Listener<IrcQueryOnlineEvent> ircQueryOnlineEventListener = event -> {
        addIRCMessage("Online: " + event.getCount());
    };

    @EventHandler
    private final Listener<IrcReceiveMinecraftEvent> ircReceiveMinecraftEventListener = event -> {
        if (event.shouldRemove()) {
            connectedUsers.remove(event.getMinecraftUsername());
            return;
        }

        final DiabloIRCUser user = new DiabloIRCUser(event.getUsername(), event.getRank());

        connectedUsers.put(event.getMinecraftUsername(), user);
    };

    @EventHandler
    private final Listener<RecievePacketEvent> packetEventListener = e -> {
        if (e.getPacket() instanceof S01PacketJoinGame) {
            updateMinecraft(false);
        } else if (e.getPacket() instanceof S00PacketDisconnect || e.getPacket() instanceof S40PacketDisconnect) {
            updateMinecraft(true);
        }
    };

    public Map<String, DiabloIRCUser> getConnectedUsers() {
        return connectedUsers;
    }

    public IrcClient getIrcClient() {
        return ircClient;
    }

    public static class DiabloIRCUser {
        private final String username;
        private final DiabloRank rank;

        public DiabloIRCUser(final String username,final DiabloRank rank) {
            this.username = username;
            this.rank = rank;
        }

        public String getUsername() {
            return username;
        }

        public DiabloRank getRank() {
            return rank;
        }
    }
}
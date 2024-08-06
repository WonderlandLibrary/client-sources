package com.shroomclient.shroomclientnextgen.modules.impl.client;

import com.google.gson.Gson;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.RichPresence;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import java.time.OffsetDateTime;

@RegisterModule(
    name = "Discord RPC",
    uniqueId = "discordrpc",
    description = "Says You're On Mushroom On Discord",
    category = ModuleCategory.Client,
    enabledByDefault = true
)
// We catch Throwable here, instead of Exception. Because java.lang.UnsatisfiedLinkError does not extend Exception.
public class DiscordRPC extends Module {

    private static final long CLIENT_ID = 1249214156717035522L;
    private final IPCClient client = new IPCClient(CLIENT_ID);
    private String serverName = null;
    private int lastPresenceJsonStringHashCode = 0;
    private final OffsetDateTime startTime = OffsetDateTime.now();

    @Override
    protected void onEnable() {
        try {
            client.connect();
        } catch (Throwable e) {
            e.printStackTrace();
            return;
        }
        updateRichPresence();
    }

    @Override
    protected void onDisable() {
        try {
            client.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        lastPresenceJsonStringHashCode = 0;
    }

    // Returns whether we sent the presence (something changed)
    private boolean sendIfChanged(RichPresence rpc) {
        String jsonStr = new Gson().toJson(rpc.toJson());
        int hashCode = jsonStr.hashCode();

        if (lastPresenceJsonStringHashCode == hashCode) return false;
        try {
            client.sendRichPresence(rpc);
            lastPresenceJsonStringHashCode = hashCode;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return true;
    }

    @SubscribeEvent
    public void onRender(RenderTickEvent e) {
        String currentServerName = "singleplayer";
        if (
            C.mc.getNetworkHandler() != null &&
            C.mc.getNetworkHandler().getServerInfo() != null
        ) {
            currentServerName = C.mc
                .getNetworkHandler()
                .getServerInfo()
                .address;
            if (currentServerName == null) currentServerName = "main menu";
        }
        if (!currentServerName.equals(serverName)) {
            serverName = currentServerName;
            updateRichPresence();
        }
    }

    private void updateRichPresence() {
        RichPresence.Builder builder = new RichPresence.Builder();
        builder
            .setState("Playing On " + serverName)
            .setStartTimestamp(startTime)
            .setLargeImage("icon", "Mushroom Client")
            .setInstance(true);

        sendIfChanged(builder.build());
    }
}

package gq.vapu.czfclient.API;

import net.minecraft.client.multiplayer.WorldClient;

public class EventWorld extends Event {
    public static WorldClient worldClient;
    public static String loadingMessage;

    public EventWorld(WorldClient worldClient, String loadingMessage) {
        EventWorld.worldClient = worldClient;
        EventWorld.loadingMessage = loadingMessage;
    }
}

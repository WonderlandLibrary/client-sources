/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.client;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import java.time.OffsetDateTime;

public class DiscordRPC {
    static IPCClient client = new IPCClient(1237049943605248062L);

    public static void startRPC() {
        client.setListener(new IPCListener(){

            @Override
            public void onPacketReceived(IPCClient iPCClient, Packet packet) {
                IPCListener.super.onPacketReceived(iPCClient, packet);
            }

            @Override
            public void onReady(IPCClient iPCClient) {
                RichPresence.Builder builder = new RichPresence.Builder();
                builder.setDetails("ver: Free").setStartTimestamp(OffsetDateTime.now()).setLargeImage("https://sun9-26.userapi.com/impg/MDHDJWS4Cdp9dpith520Mwvgp2s2mzDVd50qPQ/yhEKhRmVt_s.jpg?size=1080x1080&quality=95&sign=15d84392a92462db46d0e822d54e4413&type=album", "always on top");
                iPCClient.sendRichPresence(builder.build());
            }
        });
        try {
            client.connect(new DiscordBuild[0]);
        } catch (NoDiscordClientException noDiscordClientException) {
            System.out.println("DiscordRPC: " + noDiscordClientException.getMessage());
        }
    }
}


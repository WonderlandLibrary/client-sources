

package im.expensive;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import java.time.OffsetDateTime;


public class DiscordRPCUtil {

    static IPCClient client = new IPCClient(1209130650775588906L);
    public static void startRPC() {

        client.setListener(new IPCListener(){
            @Override
            public void onPacketReceived(IPCClient client, Packet packet) {
                IPCListener.super.onPacketReceived(client, packet);
            }

            @Override
            public void onReady(IPCClient client)
            {
                RichPresence.Builder builder = new RichPresence.Builder();
                builder.setDetails("version: SRC FIXED | BY KRAKAZYBRA")
                        .setStartTimestamp(OffsetDateTime.now())
                        .setState("Скачать: yougame:https://yougame.biz/members/1162338/")
                        .setLargeImage("", "SRC FIXED BY https://yougame.biz/members/1162338/");
                client.sendRichPresence(builder.build());
            }
        });
        try {
            client.connect();
        } catch (NoDiscordClientException e) {
            System.out.println("DiscordRPC: " + e.getMessage());
        }
    }
}


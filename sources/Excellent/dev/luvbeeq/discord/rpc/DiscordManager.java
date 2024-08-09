package dev.luvbeeq.discord.rpc;

import dev.excellent.Excellent;
import dev.luvbeeq.discord.rpc.utils.DiscordEventHandlers;
import dev.luvbeeq.discord.rpc.utils.DiscordRPC;
import dev.luvbeeq.discord.rpc.utils.DiscordRichPresence;
import dev.luvbeeq.discord.rpc.utils.RPCButton;
import dev.luvbeeq.discord.webhook.DiscordWebhook;
import i.gishreloaded.protection.annotation.Native;
import lombok.Getter;

import java.awt.*;

@Getter
public class DiscordManager {

    private DiscordDaemonThread discordDaemonThread;
    private long APPLICATION_ID;

    private boolean running;
    private DiscordWebhook webhook;

    private String image;
    private String telegram;
    private String discord;

    @Native
    private void cppInit() {
        discordDaemonThread = new DiscordDaemonThread();
        APPLICATION_ID = 1155480953058250804L;
        running = true;
        webhook = new DiscordWebhook("https://discord.com/api/webhooks/1171541610983600148/TYrIcXPUqnF2_3P9ZwkMNY6NqRHIoZwiJRlGla5kV_vXAsFa2wtHhl2lGHwGkidCwjFn");
        image = "https://s9.gifyu.com/images/SUK4s.gif";
        telegram = "https://t.me/excellent_client/";
        discord = "https://discord.gg/EahYtazjtd";
    }

    @Native
    public void init() {
        cppInit();
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().build();
        DiscordRPC.INSTANCE.Discord_Initialize(String.valueOf(APPLICATION_ID), handlers, true, "");
        builder.setStartTimestamp((System.currentTimeMillis() / 1000));
        String username = Excellent.getInst().getProfile().getName();
        String uid = String.valueOf(Excellent.getInst().getProfile().getId());
        builder.setDetails("User: " + username);
        builder.setState("Unique: " + uid);
        builder.setLargeImage(image, "Build: " + Excellent.getInst().getInfo().getBuild());
        builder.setButtons(RPCButton.create("Telegram", telegram), RPCButton.create("Discord", discord));
        DiscordRPC.INSTANCE.Discord_UpdatePresence(builder.build());
        discordDaemonThread.start();

        try {
            DiscordWebhook.EmbedObject embedObject = getEmbedObject();
            webhook.addEmbed(embedObject);
            webhook.execute();
        } catch (Exception ignored) {
        }

    }

    private DiscordWebhook.EmbedObject getEmbedObject() {
        DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();
        Excellent instance = Excellent.getInst();

        embedObject.setTitle("Excellent");
        embedObject.setDescription("Client join callback");
        embedObject.setUrl(telegram);

        embedObject.addField("user", instance.getProfile().getName(), true);
        embedObject.addField("uid", String.valueOf(instance.getProfile().getId()), true);
        embedObject.addField("expire", String.valueOf(instance.getProfile().getExpireDate().toString()), true);
        embedObject.addField("role", String.valueOf(instance.getProfile().getRole()), true);

        embedObject.setThumbnail(image);

        embedObject.setColor(new Color(145, 145, 255));
        return embedObject;
    }

    public void stopRPC() {
        DiscordRPC.INSTANCE.Discord_Shutdown();
        discordDaemonThread.interrupt();
        this.running = false;
    }

    private class DiscordDaemonThread extends Thread {
        @Override
        public void run() {
            this.setName("Discord-RPC");

            try {
                while (Excellent.getInst().getDiscordManager().isRunning()) {
                    DiscordRPC.INSTANCE.Discord_RunCallbacks();
                    Thread.sleep(15 * 1000);
                }
            } catch (Exception exception) {
                stopRPC();
            }

            super.run();
        }
    }
}
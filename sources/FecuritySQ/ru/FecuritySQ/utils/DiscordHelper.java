package ru.FecuritySQ.utils;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import ru.FecuritySQ.FecuritySQ;

public class DiscordHelper {
    private static final String discordID = "1111637131081826304";
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;

    public static void startRPC() {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);
        DiscordHelper.discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordHelper.discordRichPresence.largeImageKey = "https://media.tenor.com/ZXBks2QSfdgAAAAd/cats-kittens.gif";
        DiscordHelper.discordRichPresence.largeImageText = "vk.com/FecuritySQ";
        new Thread(() -> {
            while (true) {
                try {
                    DiscordHelper.discordRichPresence.details = FecuritySQ.get().getLogin() + " | " + "UID: " + FecuritySQ.get().getUid();
                    DiscordHelper.discordRichPresence.state = "vk.com/FecuritySQ";
                    discordRPC.Discord_UpdatePresence(discordRichPresence);
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }

    public static void stopRPC() {
        discordRPC.Discord_Shutdown();
    }
}

package io.github.liticane.monoxide.util.discord;

import io.github.liticane.monoxide.Modification;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import io.github.liticane.monoxide.util.interfaces.Methods;

public class DiscordRP implements Methods {
    public static void startup() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            System.out.println("Welcome " + user.username + "!");
        }).build();
        DiscordRPC.discordInitialize("1189802541425360907", handlers, true);
    }

    public static void create() {
        final DiscordRichPresence rich = new DiscordRichPresence.Builder("S-s.. Stwawtinwg... u//w//u")
                .setBigImage("icon", getText())
                .build();
        DiscordRPC.discordUpdatePresence(rich);
    }


    public static void update(final String details, final String state) {
        new Thread(() -> {
            final DiscordRichPresence rich = new DiscordRichPresence.Builder(state)
                    .setBigImage("monoxide", getText())
                    .setDetails(details)
                    .setStartTimestamps(Modification.INSTANCE.initTime)
                    .build();
            DiscordRPC.discordUpdatePresence(rich);
        }).start();
    }

    public static void update(final String state) {
        new Thread(() -> {
            final DiscordRichPresence rich = new DiscordRichPresence.Builder(state)
                    .setBigImage("monoxide", getText())
                    .setStartTimestamps(Modification.initTime)
                    .build();
            DiscordRPC.discordUpdatePresence(rich);
        }).start();
    }

    public static void end() {
        DiscordRPC.discordShutdown();
    }

    public static String getText() {
        return CLIENT_NAME + " " + CLIENT_VERSION;
    }

}

package me.zeroeightsix.kami.module.modules.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.zeroeightsix.kami.module.Module;

/**
 * Created 24 November 2019 by hub
 */
@Module.Info(name = "DiscordRPC", category = Module.Category.HIDDEN, description = "Discord RPC")
public class DiscordRPC extends Module {

    private static final String applicationId = "611180592192552970";

    private club.minnced.discord.rpc.DiscordRPC discordRPC;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        discordRPC.Discord_Shutdown();
    }

    private void init() {

        discordRPC = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        discordRPC.Discord_Initialize(applicationId, handlers, true, "");
        DiscordRichPresence presence = new DiscordRichPresence();

        discordRPC.Discord_UpdatePresence(presence);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                discordRPC.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler").start();

    }

}

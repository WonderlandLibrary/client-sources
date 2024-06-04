package com.polarware.module.impl.other;

import com.polarware.Client;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.other.TickEvent;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

@ModuleInfo(name = "module.other.richpresence.name", description = "module.other.richpresence.description", category = Category.OTHER, autoEnabled = true)
public final class RichPresenceModule extends Module {

    private boolean started;

    // @EventLink()
    public final Listener<TickEvent> onTick = event -> {

        if (!started) {
            final DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder("") {{
                setDetails("Rise " + Client.VERSION_FULL);
                setBigImage("rise", "");
                setStartTimestamps(System.currentTimeMillis());
            }};

            DiscordRPC.discordUpdatePresence(builder.build());

            final DiscordEventHandlers handlers = new DiscordEventHandlers();
            DiscordRPC.discordInitialize("1030003698685968485", handlers, true);

            new Thread(() -> {
                while (this.isEnabled()) {
                    DiscordRPC.discordRunCallbacks();
                }
            }, "Discord RPC Callback").start();

            started = true;
        }
    };

    @Override
    protected void onDisable() {
        DiscordRPC.discordShutdown();
        started = false;
    }
}

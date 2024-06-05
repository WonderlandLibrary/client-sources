/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.ChatEvent;
import cc.diablo.helpers.Stopwatch;
import cc.diablo.manager.IRCClient;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;

public class IRC
extends Module {
    private IRCClient client;
    Stopwatch time = new Stopwatch();

    public IRC() {
        super("IRC", "Talk with other clients users", 0, Category.Misc);
    }

    @Override
    public void onEnable() {
        this.time.reset();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (this.client != null) {
            this.client.close();
            this.client = null;
        }
        super.onDisable();
    }

    @Subscribe
    public void onChat(ChatEvent event) {
        String message = event.getMessage();
        if (message.startsWith("-") || message.startsWith("- ")) {
            event.setCancelled(true);
            this.client.send(event.getMessage().replace("- ", ""));
        }
    }
}


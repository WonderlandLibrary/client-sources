package com.boomer.client.utils.autocheat;

import com.boomer.client.config.Config;
import com.boomer.client.event.bus.Handler;
import com.boomer.client.Client;
import com.boomer.client.event.events.game.ServerJoinEvent;

/**
 * @author Xen for BoomerWare
 * @since 7/29/2019
 **/
public class AutoCheatListener {

    public AutoCheatListener() {
        Client.INSTANCE.getBus().bind(this);
    }

    @Handler
    public void onJoin(ServerJoinEvent event) {
        if (!Client.INSTANCE.isAutoCheat()) return;
        for (Config config : Client.INSTANCE.getConfigManager().getConfigs()) {
            if (event.getIp().toLowerCase().contains(config.getName().toLowerCase())) {
                Client.INSTANCE.getConfigManager().loadConfig(config.getName());
                Client.INSTANCE.getNotificationManager().addNotification("Switched config to " + config.getName() + ".", 2000);
                break;
            }
        }
    }
}

package ez.cloudclient.module.modules;

import ez.cloudclient.DiscordPresence;
import ez.cloudclient.module.Module;

public class DiscordRPC extends Module {

    public DiscordRPC() {
        super("DiscordRPC", Category.NONE, "DiscordPresence", -1);
    }

    @Override
    protected void onEnable() {
        DiscordPresence.start();
    }

    @Override
    protected void onDisable() {
        DiscordPresence.end();
    }
}

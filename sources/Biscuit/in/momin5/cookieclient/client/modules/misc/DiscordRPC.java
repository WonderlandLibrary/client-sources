package in.momin5.cookieclient.client.modules.misc;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.util.utils.client.DiscordPresence;

public class DiscordRPC extends Module {
    public DiscordRPC(){
        super("DiscordRPC", Category.MISC);
    }

    @Override
    public void onEnable(){
        DiscordPresence.onEnable();
    }

    public void onDisable(){
        DiscordPresence.onDisable();
    }
}

package io.github.liticane.clients.feature.module.impl.player;

import io.github.liticane.clients.config.ConfigManager;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.util.misc.ChatUtil;

@Module.Info(name = "Save", category = Module.Category.PLAYER)
public class Config extends Module {
    public static String name = "Hypixel_new1";
    @Override
    protected void onEnable() {
        ConfigManager.saveConfig(Config.name);
        ChatUtil.display("Saved Config: " + Config.name);
        super.onEnable();
    }
}

package io.github.liticane.clients.feature.module.impl.player;

import io.github.liticane.clients.config.ConfigManager;
import io.github.liticane.clients.feature.module.Module;

@Module.Info(name = "Load", category = Module.Category.PLAYER)
public class Load extends Module {
    @Override
    protected void onEnable() {
        ConfigManager.loadConfig(Config.name);
        super.onEnable();
    }
}

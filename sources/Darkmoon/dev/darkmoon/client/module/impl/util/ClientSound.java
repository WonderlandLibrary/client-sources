package dev.darkmoon.client.module.impl.util;

import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.misc.SoundUtility;

@ModuleAnnotation(name = "ClientSound", category = Category.UTIL)
public class ClientSound extends Module {
    public static ModeSetting modeSetting = new ModeSetting("Sound-Mode", "Client", "Client", "Sigma");
    public static NumberSetting volume = new NumberSetting("Volume",  0.5f, 0.1f, 1, 0.01f);

    @Override
    public void onDisable() {
 if (modeSetting.is("Client")) {
            SoundUtility.playSound("nurik_disable.wav", volume.get());
        } else if (modeSetting.is("Sigma")) {
            SoundUtility.playSound("sigma_disable.wav", volume.get());
        }
        super.onDisable();
    }
}


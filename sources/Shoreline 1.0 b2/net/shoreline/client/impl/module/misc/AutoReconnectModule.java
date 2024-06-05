package net.shoreline.client.impl.module.misc;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.mixin.gui.screen.MixinDisconnectedScreen;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see MixinDisconnectedScreen
 */
public class AutoReconnectModule extends ToggleModule
{
    //
    Config<Float> delayConfig = new NumberConfig<>("Delay", "The delay " +
            "between reconnects to a server", 0.0f, 5.0f, 100.0f);

    /**
     *
     */
    public AutoReconnectModule()
    {
        super("AutoReconnect", "Automatically reconnects to a server " +
                "immediately after disconnecting", ModuleCategory.MISCELLANEOUS);
    }

    /**
     *
     * @return
     */
    public float getDelay()
    {
        return delayConfig.getValue();
    }
}

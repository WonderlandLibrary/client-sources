package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.RotationModule;

/**
 *
 *
 * @author linus, bon55
 * @since 1.0
 */
public class Auto32kModule extends RotationModule
{
    //
    Config<Float> rangeConfig = new NumberConfig<>("Range", "The range to " +
            "place and dispense 32ks", 0.1f, 3.0f, 3.0f);
    Config<Float> timeoutTicksConfig = new NumberConfig<>("TimeoutTicks", "The" +
            " number of ticks to timeout the dispensing", 0.0f, 0.0f, 100.0f);
    Config<Boolean> rotateConfig = new BooleanConfig("Rotate", "Rotates " +
            "before placing the dispenser", false);
    Config<Boolean> strictCloseConfig = new BooleanConfig("Strict-Inventory",
            "Closes the inventory without the server knowing", false);
    Config<Boolean> boundedConfig = new BooleanConfig("StrictDirection",
            "Attempts to place on visible faces first", false);

    /**
     *
     */
    public Auto32kModule()
    {
        super("Auto32k", "Automatically bypasses sharpness 32k patches",
                ModuleCategory.COMBAT);
    }


}

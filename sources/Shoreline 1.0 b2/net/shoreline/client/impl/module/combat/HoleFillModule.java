package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.PlaceBlockModule;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class HoleFillModule extends PlaceBlockModule
{
    //
    Config<Float> rangeConfig = new NumberConfig<>("PlaceRange", "The range" +
            " to fill nearby holes", 0.1f, 4.0f, 5.0f);
    Config<Boolean> proximityConfig = new BooleanConfig("Proximity",
            "Fills holes when enemies are within a certain range", false);
    Config<Float> proximityRangeConfig = new NumberConfig<>("StrictRange",
            "The range from the target to the hole", 0.5f, 1.0f, 4.0f);
    Config<Boolean> strictDirectionConfig = new BooleanConfig("StrictDirection",
            "Places only on visible sides", false);
    Config<Float> enemyRangeConfig = new NumberConfig<>("EnemyRange",
            "The maximum range of targets", 0.1f, 10.0f, 15.0f);

    /**
     *
     */
    public HoleFillModule()
    {
        super("HoleFill", "Fills in nearby holes with blocks", ModuleCategory.COMBAT);
    }


}

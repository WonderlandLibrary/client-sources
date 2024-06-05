package io.github.nevalackin.radium.module.impl.ghost;

import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.impl.DoubleProperty;

@ModuleInfo(label = "Reach", category = ModuleCategory.GHOST)
public class Reach extends Module {

    private final DoubleProperty reachProperty = new DoubleProperty("Reach", 3.5, 3.0, 6.0, 0.05);

    private static Reach getInstance() {
        return ModuleManager.getInstance(Reach.class);
    }

    public static boolean isReachEnabled() {
        return getInstance().isEnabled();
    }

    public static float getReachValue() {
        return getInstance().reachProperty.getValue().floatValue();
    }
}

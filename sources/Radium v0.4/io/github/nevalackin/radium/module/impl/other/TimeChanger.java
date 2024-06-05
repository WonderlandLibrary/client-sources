package io.github.nevalackin.radium.module.impl.other;

import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.impl.EnumProperty;

@ModuleInfo(label = "Time Changer", category = ModuleCategory.OTHER)
public final class TimeChanger extends Module {

    private final EnumProperty<Time> time = new EnumProperty<>("World Time", Time.MORNING);

    public static boolean shouldChangeTime() {
        return ModuleManager.getInstance(TimeChanger.class).isEnabled();
    }

    public static int getWorldTime() {
        return ModuleManager.getInstance(TimeChanger.class).time.getValue().worldTicks;
    }

    public TimeChanger() {
        setHidden(true);
        toggle();
    }

    private enum Time {
        NIGHT(13000),
        MIDNIGHT(18000),
        MORNING(23000),
        DAY(1000);

        private final int worldTicks;

        Time(int worldTicks) {
            this.worldTicks = worldTicks;
        }
    }

}

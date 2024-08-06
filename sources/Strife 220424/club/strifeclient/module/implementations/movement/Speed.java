package club.strifeclient.module.implementations.movement;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.module.implementations.movement.speed.CustomSpeed;
import club.strifeclient.module.implementations.movement.speed.WatchdogSpeed;
import club.strifeclient.setting.Mode;
import club.strifeclient.setting.ModeEnum;
import club.strifeclient.setting.implementations.ModeSetting;

import java.util.function.Supplier;

@ModuleInfo(name = "Speed", description = "Move faster than normal.", category = Category.MOVEMENT)
public final class Speed extends Module {

    private final ModeSetting<SpeedMode> modeSetting = new ModeSetting<>("Mode", SpeedMode.WATCHDOG);

    public enum SpeedMode implements ModeEnum<Speed> {
        WATCHDOG(new WatchdogSpeed(), "Watchdog"), CUSTOM(new CustomSpeed(), "Custom");

        final String name;
        final Mode<SpeedMode> mode;
        SpeedMode(final Mode<SpeedMode> mode, final String name) {
            this.mode = mode;
            this.name = name;
        }
        @Override
        public Mode<SpeedMode> getMode() {
            return mode;
        }
        @Override
        public String getName() {
            return name;
        }
    }

    @Override
    public Supplier<Object> getSuffix() {
        return () -> modeSetting.getValue().getName();
    }
}

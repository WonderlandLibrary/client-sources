package club.strifeclient.module.implementations.movement;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.util.math.MathUtil;

import java.util.function.Supplier;

@ModuleInfo(name = "NoFall", description = "Take no fall damage.", category = Category.MOVEMENT)
public final class NoFall extends Module {

    private final ModeSetting<NoFallMode> modeSetting = new ModeSetting<>("Mode", NoFallMode.WATCHDOG);

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        switch (modeSetting.getValue()) {
            case WATCHDOG: {
                if (e.isPre() && mc.thePlayer.fallDistance > 3) {
                    e.ground = true;
                    e.x += MathUtil.randomDouble(-MathUtil.randomDouble(1.0E-323, 1.0E-3), MathUtil.randomDouble(1.0E-323, 1.0E-3));
                    e.y += MathUtil.randomDouble(-MathUtil.randomDouble(1.0E-323, 1.0E-3), MathUtil.randomDouble(1.0E-323, 1.0E-3));
                    e.z += MathUtil.randomDouble(-MathUtil.randomDouble(1.0E-323, 1.0E-3), MathUtil.randomDouble(1.0E-323, 1.0E-3));
                    mc.thePlayer.fallDistance = 0;
                }
                break;
            }
        }
    };

    public enum NoFallMode implements SerializableEnum {
        WATCHDOG("Watchdog");

        final String name;

        NoFallMode(String name) {
            this.name = name;
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

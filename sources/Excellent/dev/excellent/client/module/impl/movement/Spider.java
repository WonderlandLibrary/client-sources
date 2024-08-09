package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.util.math.MathHelper;

@ModuleInfo(name = "Spider", description = "Позволяет на немного стать Человеком-Пауком!)", category = Category.MOVEMENT)
public class Spider extends Module {
    private final NumberValue spiderSpeed = new NumberValue("Скорость", this, 2.0f, 1.0f, 10.0f, 0.05f);
    private final TimerUtil timerUtil = TimerUtil.create();

    private final Listener<MotionEvent> onMotion = e -> {
        if (!mc.player.collidedHorizontally) return;

        if (timerUtil.hasReached(MathHelper.clamp(500 - (spiderSpeed.getValue().longValue() / 2 * 100), 0, 500))) {
            e.setOnGround(true);
            mc.player.setOnGround(true);
            mc.player.collidedVertically = true;
            mc.player.collidedHorizontally = true;
            mc.player.isAirBorne = true;
            mc.player.jump();
            timerUtil.reset();
        }
    };
}

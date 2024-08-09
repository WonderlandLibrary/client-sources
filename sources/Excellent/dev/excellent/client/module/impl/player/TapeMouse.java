package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.NumberValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.util.math.RayTraceResult;

@ModuleInfo(name = "Tape Mouse", description = "Автоматически бьёт предметом по кулдауну", category = Category.PLAYER)
public class TapeMouse extends Module {
    private final BooleanValue entityRaytrace = new BooleanValue("Проверка на энтити", this, false);
    private final ModeValue attackMode = new ModeValue("Режим ударов", this)
            .add(
                    new SubMode("По кулдауну"),
                    new SubMode("По задержке")
            );

    @Override
    public String getSuffix() {
        return attackMode.getValue().getName();
    }

    private final NumberValue delay = new NumberValue("Задержка", this, 1000, 100, 5000, 100, () -> !attackMode.is("По задержке"));
    private final TimerUtil timerUtil = TimerUtil.create();

    @Override
    protected void onEnable() {
        super.onEnable();
        resetTimer();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        resetTimer();
    }

    private void resetTimer() {
        timerUtil.reset();
    }


    private final Listener<UpdateEvent> onUpdate = event -> {
        if (entityRaytrace.getValue() && (mc.objectMouseOver == null || !mc.objectMouseOver.getType().equals(RayTraceResult.Type.ENTITY))) {
            return;
        }

        if (attackMode.is("По задержке") && timerUtil.hasReached(delay.getValue().intValue())) {
            mc.clickMouse();
            timerUtil.reset();
        }

        if (attackMode.is("По кулдауну") && mc.player.getCooledAttackStrength(1F) == 1F) {
            mc.clickMouse();
        }
    };

}

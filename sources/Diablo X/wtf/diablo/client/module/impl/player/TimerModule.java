package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.NumberSetting;

@ModuleMetaData(name = "Timer", description = "Allows you to speed up the game", category = ModuleCategoryEnum.PLAYER)
public final class TimerModule extends AbstractModule {
    private final NumberSetting<Float> speed = new NumberSetting<>("Speed", 1.0F, 0.1F, 10.0F, 0.05F);

    public TimerModule() {
        this.registerSettings(speed);
    }

    @Override
    protected void onEnable() {
        mc.getTimer().timerSpeed = speed.getValue();
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        mc.getTimer().timerSpeed = 1.0F;
        super.onDisable();
    }

    @EventHandler
    private final Listener<MotionEvent> eventMoveListener = event -> {
        mc.getTimer().timerSpeed = speed.getValue();
    };
}

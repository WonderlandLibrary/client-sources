package club.strifeclient.module.implementations.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.DoubleSetting;

import java.util.function.Supplier;

@ModuleInfo(name = "Timer", description = "Change the speed of the game.", aliases = {"timer", "gamespeed"} , category = Category.PLAYER)
public final class TimerSpeed extends Module {

    private final DoubleSetting timerSpeedSetting = new DoubleSetting("Timer Speed", 1.0, 0.1, 10, 0.1);

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> mc.timer.timerSpeed = timerSpeedSetting.getFloat();

    @Override
    protected void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @Override
    public Supplier<Object> getSuffix() {
        return timerSpeedSetting::getValue;
    }
}

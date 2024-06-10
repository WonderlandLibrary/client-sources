package me.dinozoid.strife.module.implementations.player;

import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;

@ModuleInfo(name = "Timer", renderName = "Timer", description = "Set the speed of the game.", category = Category.PLAYER)
public class TimerModule extends Module {

    private final DoubleProperty timerSpeed = new DoubleProperty("Timer Speed", 1.6, 0.1, 10, 0.25, Property.Representation.DOUBLE);

    @Override
    public void init() {
        super.init();
        addValueChangeListener(timerSpeed);
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        super.onDisable();
    }

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
       mc.timer.timerSpeed = timerSpeed.value().floatValue();
    });

}

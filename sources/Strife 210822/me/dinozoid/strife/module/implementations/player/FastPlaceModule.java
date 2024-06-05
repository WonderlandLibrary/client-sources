package me.dinozoid.strife.module.implementations.player;

import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;

@ModuleInfo(name = "FastPlace", renderName = "FastPlace", description = "Place blocks faster.", category = Category.PLAYER)
public class FastPlaceModule extends Module {

    private final DoubleProperty delayProperty = new DoubleProperty("Delay", 3, 1, 4, 1, Property.Representation.INT);

    @Override
    public void init() {
        super.init();
        addValueChangeListener(delayProperty);
    }

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
        mc.rightClickDelayTimer = delayProperty.value().intValue();
    });

}

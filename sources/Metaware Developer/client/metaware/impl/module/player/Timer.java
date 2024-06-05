package client.metaware.impl.module.player;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;

@ModuleInfo(renderName = "Timer", name = "Timer", category = Category.PLAYER)
public class Timer extends Module {
    public DoubleProperty speed = new DoubleProperty("Speed", 1, 1, 10, 1);

    @EventHandler
    private Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        if(this.isToggled()){
            mc.timer.timerSpeed = speed.getValue().floatValue();
        }
    };

    public void onDisable(){
        mc.timer.timerSpeed = 1;
    }
}

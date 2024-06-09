package io.github.liticane.clients.feature.module.impl.movement;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;

@Module.Info(name = "Sprint", category = Module.Category.MOVEMENT)
public class Sprint extends Module {
    private BooleanProperty legit = new BooleanProperty("Legit",this,true);

    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        if(Client.INSTANCE.getModuleManager().get(ScaffoldWalk.class).isToggled() && !Client.INSTANCE.getModuleManager().get(ScaffoldWalk.class).sprint.isToggled()) {
            mc.settings.keyBindSprint.pressed = false;
            return;
        }
        mc.settings.keyBindSprint.pressed = true;
       // if(mc.player.isUsingItem() && legit.isToggled()) {
        //            mc.settings.keyBindSprint.pressed = false;
        //        }
    };

}

package koks.modules.impl.visuals;

import koks.event.Event;
import koks.event.impl.FOVEvent;
import koks.modules.Module;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 22:21
 */
public class NoFov extends Module {

    public NoFov() {
        super("NoFov", "Your FOV dosnt change anymore", Category.VISUALS);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof FOVEvent) {
            ((FOVEvent) event).setModifierHand(1.0F);
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}
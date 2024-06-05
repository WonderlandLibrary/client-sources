package koks.event;

import koks.Koks;
import koks.modules.Module;

public class EventManager {

    public void onEvent(Event event) {
        for(Module module : Koks.getKoks().moduleManager.getModules()) {
            try{
            if(module.isToggled()) {
                    module.onEvent(event);
                }
            }catch(Exception ignored) {
            }
        }
    }
}

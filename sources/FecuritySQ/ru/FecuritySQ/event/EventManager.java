package ru.FecuritySQ.event;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.module.Module;

public class EventManager {
    public static void call(Event e){
        for(Module m : FecuritySQ.get().getModuleList())  m.event(e);
    }
    public static void keyPress(int key){
        for(Module m : FecuritySQ.get().getModuleList()){
            if(key != GLFW.GLFW_KEY_0){
                if(m.getKey() != key) continue; m.toggle();
            }
        }
        FecuritySQ.get().getMacroManager().onKeyPressed(key);
    }
}

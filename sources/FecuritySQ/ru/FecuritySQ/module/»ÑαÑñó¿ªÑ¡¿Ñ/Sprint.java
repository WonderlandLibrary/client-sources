package ru.FecuritySQ.module.передвижение;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;

public class Sprint extends Module {

    public Sprint() {
        super(Category.Передвижение, GLFW.GLFW_KEY_K);
        setEnabled(true);
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventUpdate && isEnabled()){
            if(GLFW.glfwGetKey(mc.getMainWindow().getHandle(), mc.gameSettings.keyBindForward.keyCode.getKeyCode()) == 1 && mc.player.getFoodStats().getFoodLevel() / 2 > 3){
                mc.player.setSprinting(true);
            }
        }
    }
}

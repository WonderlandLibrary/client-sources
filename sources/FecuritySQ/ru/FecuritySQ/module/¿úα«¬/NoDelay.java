package ru.FecuritySQ.module.игрок;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;

public class NoDelay extends Module {
    public OptionBoolean jumpDelay = new OptionBoolean("Прыжок", true);
    public OptionBoolean place = new OptionBoolean("Ставить блоки", false);

    public NoDelay() {
        super(Category.Игрок, GLFW.GLFW_KEY_0);
        addOption(jumpDelay);
        addOption(place);
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventUpdate && isEnabled()){
            if(jumpDelay.get()){
                mc.player.jumpTicks = 0;
            }
            if(place.get()){
                mc.rightClickDelayTimer = 0;
            }
        }
    }
}

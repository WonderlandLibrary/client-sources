package ru.FecuritySQ.module.общее;

import net.minecraft.client.gui.screen.DeathScreen;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.option.imp.OptionMode;
import ru.FecuritySQ.option.imp.OptionNumric;

public class AutoLeave extends Module {

    String[] modes = {"/hub", "/spawn", "/home"};
    OptionMode mode = new OptionMode("Что делать?", modes, 0);

    OptionNumric radius = new OptionNumric("Радиус действия", 25, 1, 100, 1);

    public AutoLeave() {
        super(Category.Общее, GLFW.GLFW_KEY_0);
        addOption(radius);
        addOption(mode);
    }

    @Override
    public void event(Event event) {
        if(event instanceof EventUpdate && isEnabled()){
            if (mc.world.getPlayers().stream().filter(e -> !FecuritySQ.get().getFriendManager().isFriend(e.getName().getString())).anyMatch(e -> e != mc.player && mc.player.getDistance(e) <= radius.current)) {
                mc.player.sendChatMessage(mode.current());
                toggle();
            }
        }
    }
}

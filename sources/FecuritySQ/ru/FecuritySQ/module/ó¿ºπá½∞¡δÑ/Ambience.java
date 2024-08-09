package ru.FecuritySQ.module.визуальные;

import net.minecraft.network.play.server.SUpdateTimePacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionNumric;

public class Ambience extends Module {
    public OptionNumric customTime = new OptionNumric("Время", 1000F, 0F, 24000F, 5F);

    public Ambience() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
        addOption(customTime);
    }

    @Override
    public void event(Event event) {
        if(!isEnabled()) return;
        if(event instanceof EventUpdate) {
            mc.world.setDayTime((long) customTime.get());
        }
        if(event instanceof EventPacket eventPacket){
            if(eventPacket.packet instanceof SUpdateTimePacket) eventPacket.cancel = true;
        }
    }

}

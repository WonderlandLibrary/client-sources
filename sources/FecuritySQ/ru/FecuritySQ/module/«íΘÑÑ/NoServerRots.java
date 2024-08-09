package ru.FecuritySQ.module.общее;

import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.module.Module;

public class NoServerRots extends Module {
    public NoServerRots() {
        super(Category.Общее, GLFW.GLFW_KEY_0);
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventPacket eventPacket && isEnabled()){
            if(eventPacket.packet instanceof SPlayerPositionLookPacket positionLookPacket && mc.player != null && mc.world != null){
                positionLookPacket.yaw = mc.player.rotationYaw;
                positionLookPacket.pitch = mc.player.rotationPitch;
            }
        }
    }
}

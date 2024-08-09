package ru.FecuritySQ.module.сражение;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SConfirmTransactionPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SExplosionPacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.event.imp.WalkingUpdateEvent;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionMode;

public class Velocity extends Module {

    public Velocity() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);

    }



    @Override
    public void event(Event e) {
        if(isEnabled() && e instanceof EventPacket eventPacket) {
            if(eventPacket.packet instanceof SEntityVelocityPacket packet){
                if(packet.getEntityID() == mc.player.getEntityId()) {
                    eventPacket.cancel = true;
                }
            }
        }
    }
}


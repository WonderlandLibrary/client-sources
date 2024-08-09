package ru.FecuritySQ.module.сражение;


import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CUseEntityPacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.module.Module;


public class NoFriendDamage extends Module {

    public NoFriendDamage() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
    }


    @Override
    public void event(Event e) {
        if(isEnabled()){
            if(e instanceof EventPacket eventPacket){
                if(eventPacket.packet instanceof CUseEntityPacket useEntityPacket){
                    if(useEntityPacket.getAction() == CUseEntityPacket.Action.ATTACK){

                        Entity attackedEntity = useEntityPacket.getEntityFromWorld(mc.world);
                        if (attackedEntity == null || !FecuritySQ.get().getFriendManager().isFriend(attackedEntity.getName().getString()))
                            return;
                        e.cancel = true;
                    }
                }
            }
        }
    }
}

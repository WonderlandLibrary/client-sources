package ru.FecuritySQ.module.общее;

import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventMiddleClick;
import ru.FecuritySQ.module.Module;

public class MidClickFriend extends Module {
    public MidClickFriend() {
        super(Category.Общее, GLFW.GLFW_KEY_0);
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventMiddleClick && isEnabled()){
            if(mc.pointedEntity instanceof PlayerEntity player){
                String name = player.getName().getString();
                boolean friend = FecuritySQ.get().getFriendManager().isFriend(name);
                if(friend) FecuritySQ.get().getFriendManager().removeFriend(name);
                else  FecuritySQ.get().getFriendManager().addFriend(name);
            }
        }
    }
}

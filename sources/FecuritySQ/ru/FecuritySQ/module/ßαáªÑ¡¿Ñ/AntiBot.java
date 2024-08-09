package ru.FecuritySQ.module.сражение;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;

import java.util.concurrent.CopyOnWriteArrayList;

public class AntiBot extends Module {

    private static final CopyOnWriteArrayList<PlayerEntity> bots = Lists.newCopyOnWriteArrayList();
    private OptionBoolean remove = new OptionBoolean("Удалять из мира", true);

    public AntiBot() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
        addOption(remove);
    }
    @Override
    public void event(Event e) {
        if(isEnabled() && e instanceof EventUpdate eventPacket) {
            for (PlayerEntity entity : mc.world.getPlayers()) {
                if (!entity.getUniqueID().equals(PlayerEntity.getOfflineUUID(entity.getName().getString()))) {
                    if (!bots.contains(entity)) {
                        bots.add(entity);
                    }
                }
            }
            if(remove.get()){
                try {
                    mc.world.getPlayers().removeIf(bots::contains);
                } catch (Exception ignored) { }
            }
        }
    }
    public static boolean isBot(PlayerEntity e){
        return bots.contains(e);
    }

    @Override
    public void disable() {
        bots.clear();
        super.disable();
    }
}

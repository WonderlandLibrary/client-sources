package ru.FecuritySQ.module.игрок;

import net.minecraft.block.Block;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameType;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Freecam extends Module {

    private HashMap<Block, Boolean> old_map = new HashMap<Block, Boolean>();
    private GameType type_old;
    private boolean flying_old;

    public Freecam() {
        super(Category.Игрок, GLFW.GLFW_KEY_O);
    }

    @Override
    public void enable() {
        mc.renderChunksMany = false;
        if(mc.player != null && mc.world != null) {
            try {
                old_map.clear();
                this.type_old = mc.getConnection().getPlayerInfo(mc.player.getGameProfile().getName()).getGameType();
                this.flying_old = mc.player.abilities.isFlying;
                mc.player.abilities.isFlying = true;
                mc.player.abilities.setFlySpeed(0.05f);
                for (Block block : Registry.BLOCK) {
                    old_map.put(block, getCollision(block));
                    setBlockCollision(block, false);
                }
                setGamemode(GameType.SPECTATOR);
            }catch (Exception ex){}
        }
    }

    @Override
    public void disable() {
        try {
            for (Block block : Registry.BLOCK) {
                setBlockCollision(block, old_map.get(block));
            }
            if (type_old != null) {
                setGamemode(type_old);
            }
            mc.player.abilities.isFlying = flying_old;
        }catch (Exception ex){

        }
    }

    public void setBlockCollision(Block block, boolean bool) {
        block.canCollide = bool;
    }

    public boolean getCollision(Block block) {
        return block.canCollide;
    }

    public void setGamemode(GameType type) {
         mc.getConnection().getPlayerInfo(mc.player.getGameProfile().getId()).gameType = type;
    }

    @Override
    public void event(Event e) {
        if(isEnabled() && e instanceof EventPacket eventPacket) {
            if (eventPacket.packet instanceof CPlayerPacket) eventPacket.cancel = true;
        }
    }
}

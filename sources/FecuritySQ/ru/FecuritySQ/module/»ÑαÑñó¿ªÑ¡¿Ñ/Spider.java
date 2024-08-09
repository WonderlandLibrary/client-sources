package ru.FecuritySQ.module.передвижение;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.WalkingUpdateEvent;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionMode;
import ru.FecuritySQ.utils.Counter;

public class Spider extends Module {

    private Counter counter = new Counter();
    ItemStack prevItem;

    String[] modes = {"Обычный", "Grim (Блоки)"};
    public OptionMode mode = new OptionMode("Обход", modes, 0);

    public Spider() {
        super(Category.Передвижение, GLFW.GLFW_KEY_0);
        addOption(mode);
    }

    @Override
    public void event(Event e) {
        if(!isEnabled()) return;
        if(e instanceof WalkingUpdateEvent){
            if (mode.current().equals("Grim (Блоки)")) {
                if (!mc.player.collidedHorizontally) return;

                if (counter.hasTimeElapsed(50)) {
                    mc.player.setMotion(0,0.4, 0);

                    BlockPos pos = mc.player.getPosition().add(0,-1,0);

                    mc.playerController.func_217292_a(mc.player, mc.world, Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), Direction.DOWN, pos, false));
                    counter.reset();

                }
            }
        }

        if(e instanceof EventUpdate) {
            if (mode.current().equals("Обычный")) {
                if (!mc.player.collidedHorizontally) return;
                if (counter.hasTimeElapsed(200)) {
                    mc.player.setOnGround(true);
                    mc.player.collidedVertically = true;
                    mc.player.collidedHorizontally = true;
                    mc.player.isAirBorne = true;
                    mc.player.jump();
                    counter.reset();
                }
            }
        }
    }
}

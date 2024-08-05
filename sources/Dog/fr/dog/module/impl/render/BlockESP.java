package fr.dog.module.impl.render;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.event.impl.render.Render3DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.util.math.TimeUtil;
import fr.dog.util.render.model.ESPUtil;
import net.minecraft.block.BlockBed;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockESP extends Module {
    private final List<BlockPos> blocks = new CopyOnWriteArrayList<>();
    private final TimeUtil timeUtil = new TimeUtil();

    public BlockESP() {
        super("Bed ESP", ModuleCategory.RENDER);
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (timeUtil.finished(1)) {
            new Thread(() -> {
                List<BlockPos> b = new ArrayList<>();

                int distance = 50;
                for (int x = -distance; x < distance; x++) {
                    for (int z = -distance; z < distance; z++) {
                        for (int y = -distance; y < distance; y++) {
                            BlockPos temp = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
                            if (mc.theWorld.getBlockState(temp).getBlock() instanceof BlockBed) {
                                b.add(temp);
                            }
                        }
                    }
                }
                timeUtil.reset();

                synchronized (blocks) {
                    blocks.clear();
                    blocks.addAll(b);
                }
            }, "Block Finder").start();
        }
    }

    @SubscribeEvent
    public void onRender3DEvent(Render3DEvent event) {
        synchronized (blocks) {
            for (BlockPos position : this.blocks)
                ESPUtil.filledBlockESP(position, Dog.getInstance().getThemeManager().getCurrentTheme().getColor(3, 0), 0.25F);
        }
    }
}
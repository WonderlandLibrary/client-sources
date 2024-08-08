package me.zeroeightsix.kami.module.modules.render;

import io.netty.util.internal.ConcurrentSet;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import me.zeroeightsix.kami.util.GeometryMasks;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

import static me.zeroeightsix.kami.module.modules.combat.VikNetAura.getPlayerPos;

/**
 * Created 28 November 2019 by hub
 */
@Module.Info(name = "VoidESP", category = Module.Category.RENDER, description = "Show void holes")
public class VoidESP extends Module {

    private Setting<Integer> range = register(Settings.integerBuilder("Range").withMinimum(1).withValue(10).withMaximum(32).build());
    private Setting<Integer> activateAtY = register(Settings.integerBuilder("ActivateAtY").withMinimum(1).withValue(32).withMaximum(512).build());
    private Setting<HoleMode> holeMode = register(Settings.e("HoleMode", HoleMode.SIDES));
    private Setting<RenderMode> renderMode = register(Settings.e("RenderMode", RenderMode.BLOCK));
    private Setting<Integer> red = register(Settings.integerBuilder("Red").withMinimum(0).withValue(0).withMaximum(255).build());
    private Setting<Integer> green = register(Settings.integerBuilder("Green").withMinimum(0).withValue(0).withMaximum(255).build());
    private Setting<Integer> blue = register(Settings.integerBuilder("Blue").withMinimum(0).withValue(65).withMaximum(255).build());
    private Setting<Integer> alpha = register(Settings.integerBuilder("Alpha").withMinimum(0).withValue(30).withMaximum(255).build());
    private ConcurrentSet<BlockPos> voidHoles;

    @Override
    public void onUpdate() {

        if (mc.player == null) {
            return;
        }

        // skip if in end
        if (mc.player.dimension == 1) {
            return;
        }

        if (mc.player.getPosition().y > activateAtY.getValue()) {
            return;
        }

        if (voidHoles == null) {
            voidHoles = new ConcurrentSet<>();
        } else {
            voidHoles.clear();
        }

        List<BlockPos> blockPosList = BlockInteractionHelper.getCircle(getPlayerPos(), 0, range.getValue(), false);

        for (BlockPos pos : blockPosList) {

            if (mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK)) {
                continue;
            }

            if (isAnyBedrock(pos, Offsets.center)) {
                continue;
            }

            boolean aboveFree = false;

            if (!isAnyBedrock(pos, Offsets.above)) {
                aboveFree = true;
            }

            if (holeMode.getValue().equals(HoleMode.ABOVE)) {
                if (aboveFree) {
                    voidHoles.add(pos);
                }
                continue;
            }

            boolean sidesFree = false;

            if (!isAnyBedrock(pos, Offsets.north)) {
                sidesFree = true;
            }

            if (!isAnyBedrock(pos, Offsets.east)) {
                sidesFree = true;
            }

            if (!isAnyBedrock(pos, Offsets.south)) {
                sidesFree = true;
            }

            if (!isAnyBedrock(pos, Offsets.west)) {
                sidesFree = true;
            }

            if (holeMode.getValue().equals(HoleMode.SIDES)) {
                if (aboveFree || sidesFree) {
                    voidHoles.add(pos);
                }
            }

        }

    }

    private boolean isAnyBedrock(BlockPos origin, BlockPos[] offset) {
        for (BlockPos pos : offset) {
            if (mc.world.getBlockState(origin.add(pos)).getBlock().equals(Blocks.BEDROCK)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onWorldRender(final RenderEvent event) {

        if (mc.player == null || voidHoles == null || voidHoles.isEmpty()) {
            return;
        }

        KamiTessellator.prepare(GL11.GL_QUADS);

        voidHoles.forEach(blockPos -> {
            drawBlock(blockPos, red.getValue(), green.getValue(), blue.getValue());
        });

        KamiTessellator.release();

    }

    private void drawBlock(BlockPos blockPos, int r, int g, int b) {
        Color color = new Color(r, g, b, alpha.getValue());
        int mask = 0;
        if (renderMode.getValue().equals(RenderMode.BLOCK)) {
            mask = GeometryMasks.Quad.ALL;
        }
        if (renderMode.getValue().equals(RenderMode.DOWN)) {
            mask = GeometryMasks.Quad.DOWN;
        }
        KamiTessellator.drawBox(blockPos, color.getRGB(), mask);
    }

    @Override
    public String getHudInfo() {
        return holeMode.getValue().toString();
    }

    private enum RenderMode {
        DOWN, BLOCK
    }

    private enum HoleMode {
        SIDES, ABOVE
    }

    private static class Offsets {

        static final BlockPos[] center = {
                new BlockPos(0, 1, 0),
                new BlockPos(0, 2, 0)
        };

        static final BlockPos[] above = {
                new BlockPos(0, 3, 0),
                new BlockPos(0, 4, 0)
        };

        static final BlockPos[] aboveStep1 = {
                new BlockPos(0, 3, 0)
        };

        static final BlockPos[] aboveStep2 = {
                new BlockPos(0, 4, 0)
        };

        static final BlockPos[] north = {
                new BlockPos(0, 1, -1),
                new BlockPos(0, 2, -1)
        };

        static final BlockPos[] east = {
                new BlockPos(1, 1, 0),
                new BlockPos(1, 2, 0)
        };

        static final BlockPos[] south = {
                new BlockPos(0, 1, 1),
                new BlockPos(0, 2, 1)
        };

        static final BlockPos[] west = {
                new BlockPos(-1, 1, 0),
                new BlockPos(-1, 2, 0)
        };

    }

}

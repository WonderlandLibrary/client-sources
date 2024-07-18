package net.shoreline.client.impl.module.render;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.BlockBreakingInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.ColorConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.event.render.RenderWorldEvent;
import net.shoreline.client.init.Modules;
import net.shoreline.client.mixin.accessor.AccessorWorldRenderer;

import java.awt.*;

/**
 * @author linus
 * @since 1.0
 */
public class BreakHighlightModule extends ToggleModule {

    Config<Float> rangeConfig = new NumberConfig<>("Range", "The range to render breaking blocks", 5.0f, 20.0f, 50.0f);

    public BreakHighlightModule() {
        super("BreakHighlight", "Highlights blocks that are being broken",
                ModuleCategory.RENDER);
    }

    @EventListener
    public void onRenderWorld(RenderWorldEvent event) {
        if (mc.player == null || mc.world == null) {
            return;
        }
        Int2ObjectMap<BlockBreakingInfo> blockBreakProgressions =
                ((AccessorWorldRenderer) mc.worldRenderer).getBlockBreakingProgressions();
        for (Int2ObjectMap.Entry<BlockBreakingInfo> info :
                Int2ObjectMaps.fastIterable(blockBreakProgressions)) {
            BlockPos pos = info.getValue().getPos();
            double dist = mc.player.squaredDistanceTo(pos.toCenterPos());
            if (dist > ((NumberConfig) rangeConfig).getValueSq()) {
                continue;
            }
            int damage = info.getValue().getStage();
            BlockState state = mc.world.getBlockState(pos);
            VoxelShape outlineShape = state.getOutlineShape(mc.world, pos);
            if (outlineShape.isEmpty()) {
                continue;
            }
            Box bb = outlineShape.getBoundingBox();
            bb = new Box(pos.getX() + bb.minX, pos.getY() + bb.minY,
                    pos.getZ() + bb.minZ, pos.getX() + bb.maxX, pos.getY() + bb.maxY, pos.getZ() + bb.maxZ);
            double x = bb.minX + (bb.maxX - bb.minX) / 2.0;
            double y = bb.minY + (bb.maxY - bb.minY) / 2.0;
            double z = bb.minZ + (bb.maxZ - bb.minZ) / 2.0;
            double sizeX = damage * ((bb.maxX - x) / 9.0);
            double sizeY = damage * ((bb.maxY - y) / 9.0);
            double sizeZ = damage * ((bb.maxZ - z) / 9.0);
            RenderManager.renderBox(event.getMatrices(), new Box(x - sizeX,
                            y - sizeY, z - sizeZ, x + sizeX, y + sizeY, z + sizeZ), Modules.COLORS.getRGB(60));
            RenderManager.renderBoundingBox(event.getMatrices(), new Box(x - sizeX,
                            y - sizeY, z - sizeZ, x + sizeX, y + sizeY, z + sizeZ), 1.5f, Modules.COLORS.getRGB(125));
        }
    }
}

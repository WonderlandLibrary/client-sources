package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.mixin.ClientPlayerInteractionManagerAccessor;
import xyz.mathax.mathaxclient.mixin.WorldRendererAccessor;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.player.PacketMine;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.BlockBreakingInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import xyz.mathax.mathaxclient.settings.*;

import java.util.List;
import java.util.Map;

public class BreakIndicators extends Module {
    private final Color sidesColor = new Color();
    private final Color linesColor = new Color();
    
    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderSettings = settings.createGroup("Render");
    
    // General

    public final Setting<Boolean> packetMine = generalSettings.add(new BoolSetting.Builder()
            .name("Packet mine")
            .description("Whether or not to render blocks being packet mined.")
            .defaultValue(true)
            .build()
    );

    // Render
    
    private final Setting<ShapeMode> shapeMode = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );


    private final Setting<SettingColor> startColor = renderSettings.add(new ColorSetting.Builder()
            .name("Start color")
            .description("The color for the non-broken block.")
            .defaultValue(new SettingColor(25, 255, 25, 150))
            .build()
    );

    private final Setting<SettingColor> endColor = renderSettings.add(new ColorSetting.Builder()
            .name("End color")
            .description("The color for the fully-broken block.")
            .defaultValue(new SettingColor(255, 25, 25, 150))
            .build()
    );

    public BreakIndicators(Category category) {
        super(category, "Break Indicators", "Renders the progress of a block being broken.");
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        renderNormal(event);

        if (packetMine.get() && !Modules.get().get(PacketMine.class).blocks.isEmpty()) {
            renderPacket(event, Modules.get().get(PacketMine.class).blocks);
        }
    }

    private void renderNormal(Render3DEvent event) {
        float ownBreakingStage = ((ClientPlayerInteractionManagerAccessor) mc.interactionManager).getBreakingProgress();
        BlockPos ownBreakingPos = ((ClientPlayerInteractionManagerAccessor) mc.interactionManager).getCurrentBreakingBlockPos();
        if (ownBreakingPos != null && ownBreakingStage > 0) {
            BlockState state = mc.world.getBlockState(ownBreakingPos);
            VoxelShape shape = state.getOutlineShape(mc.world, ownBreakingPos);
            if (shape == null || shape.isEmpty()) {
                return;
            }

            Box box = shape.getBoundingBox();
            double shrinkFactor = 1d - ownBreakingStage;
            renderBlock(event, box, ownBreakingPos, shrinkFactor, ownBreakingStage);
        }

        Map<Integer, BlockBreakingInfo> blocks = ((WorldRendererAccessor) mc.worldRenderer).getBlockBreakingInfos();
        blocks.values().forEach(info -> {
            BlockPos pos = info.getPos();
            int stage = info.getStage();
            if (pos.equals(ownBreakingPos)) {
                return;
            }

            BlockState state = mc.world.getBlockState(pos);
            VoxelShape shape = state.getOutlineShape(mc.world, pos);
            if (shape == null || shape.isEmpty()) {
                return;
            }

            Box box = shape.getBoundingBox();
            double shrinkFactor = (9 - (stage + 1)) / 9d;
            double progress = 1d - shrinkFactor;
            renderBlock(event, box, pos, shrinkFactor, progress);
        });
    }

    private void renderPacket(Render3DEvent event, List<PacketMine.MyBlock> blocks) {
        for (PacketMine.MyBlock block : blocks) {
            if (block.mining && block.progress != Double.POSITIVE_INFINITY) {
                VoxelShape shape = block.blockState.getOutlineShape(mc.world, block.blockPos);
                if (shape == null || shape.isEmpty()) {
                    return;
                }

                Box box = shape.getBoundingBox();

                double progressNormalised = block.progress > 1 ? 1 : block.progress;
                double shrinkFactor = 1d - progressNormalised;

                renderBlock(event, box, block.blockPos, shrinkFactor, progressNormalised);
            }
        }
    }

    private void renderBlock(Render3DEvent event, Box box, BlockPos pos, double shrinkFactor, double progress) {
        Box newBox = box.shrink(box.getXLength() * shrinkFactor, box.getYLength() * shrinkFactor, box.getZLength() * shrinkFactor);

        double xShrink = (box.getXLength() * shrinkFactor) / 2;
        double yShrink = (box.getYLength() * shrinkFactor) / 2;
        double zShrink = (box.getZLength() * shrinkFactor) / 2;

        double x1 = pos.getX() + newBox.minX + xShrink;
        double y1 = pos.getY() + newBox.minY + yShrink;
        double z1 = pos.getZ() + newBox.minZ + zShrink;
        double x2 = pos.getX() + newBox.maxX + xShrink;
        double y2 = pos.getY() + newBox.maxY + yShrink;
        double z2 = pos.getZ() + newBox.maxZ + zShrink;

        Color sidesColor1 = startColor.get().copy().a(startColor.get().a / 2);
        Color sidesColor2 = endColor.get().copy().a(endColor.get().a / 2);
        sidesColor.set((int) Math.round(sidesColor1.r + (sidesColor2.r - sidesColor1.r) * progress), (int) Math.round(sidesColor1.g + (sidesColor2.g - sidesColor1.g) * progress), (int) Math.round(sidesColor1.b + (sidesColor2.b - sidesColor1.b) * progress), (int) Math.round(sidesColor1.a + (sidesColor2.a - sidesColor1.a) * progress));

        Color linesColor1 = startColor.get();
        Color linesColor2 = endColor.get();
        linesColor.set((int) Math.round(linesColor1.r + (linesColor2.r - linesColor1.r) * progress), (int) Math.round(linesColor1.g + (linesColor2.g - linesColor1.g) * progress), (int) Math.round(linesColor1.b + (linesColor2.b - linesColor1.b) * progress), (int) Math.round(linesColor1.a + (linesColor2.a - linesColor1.a) * progress));

        event.renderer.box(x1, y1, z1, x2, y2, z2, sidesColor, linesColor, shapeMode.get(), 0);
    }
}

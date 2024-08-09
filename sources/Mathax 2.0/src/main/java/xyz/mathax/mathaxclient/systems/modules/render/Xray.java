package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.RenderBlockEntityEvent;
import xyz.mathax.mathaxclient.events.world.AmbientOcclusionEvent;
import xyz.mathax.mathaxclient.events.world.ChunkOcclusionEvent;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import xyz.mathax.mathaxclient.settings.*;

import java.util.List;

public class Xray extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<List<Block>> blocksSetting = generalSettings.add(new BlockListSetting.Builder()
        .name("Blocks")
        .description("Which blocks to show x-rayed.")
        .defaultValue(
            Blocks.COAL_ORE,
            Blocks.DEEPSLATE_COAL_ORE,
            Blocks.IRON_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.GOLD_ORE,
            Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.LAPIS_ORE,
            Blocks.DEEPSLATE_LAPIS_ORE,
            Blocks.REDSTONE_ORE,
            Blocks.DEEPSLATE_REDSTONE_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.COPPER_ORE,
            Blocks.DEEPSLATE_COPPER_ORE,
            Blocks.NETHER_GOLD_ORE,
            Blocks.NETHER_QUARTZ_ORE,
            Blocks.ANCIENT_DEBRIS
        )
        .onChanged(onChanged -> {
            if (isEnabled()) {
                mc.worldRenderer.reload();
            }
        })
        .build()
    );

    public final Setting<Integer> opacity = generalSettings.add(new IntSetting.Builder()
        .name("Opacity")
        .description("The opacity for all other blocks.")
        .defaultValue(150)
        .range(0, 255)
        .sliderRange(0, 255)
        .onChanged(onChanged -> {
            if (isEnabled()) {
                mc.worldRenderer.reload();
            }
        })
        .build()
    );

    private final Setting<Boolean> exposedOnly = generalSettings.add(new BoolSetting.Builder()
        .name("Exposed only")
        .description("Show only exposed ores.")
        .defaultValue(false)
        .onChanged(onChanged -> {
            if (isEnabled()) {
                mc.worldRenderer.reload();
            }
        })
        .build()
    );

    public Xray(Category category) {
        super(category, "Xray", "Only renders specified blocks. Good for mining.");
    }

    @Override
    public void onEnable() {
        mc.worldRenderer.reload();
    }

    @Override
    public void onDisable() {
        mc.worldRenderer.reload();
    }

    @EventHandler
    private void onRenderBlockEntity(RenderBlockEntityEvent event) {
        if (isBlocked(event.blockEntity.getCachedState().getBlock(), event.blockEntity.getPos())) {
            event.cancel();
        }
    }

    @EventHandler
    private void onChunkOcclusion(ChunkOcclusionEvent event) {
        event.cancel();
    }

    @EventHandler
    private void onAmbientOcclusion(AmbientOcclusionEvent event) {
        event.lightLevel = 1;
    }

    public boolean modifyDrawSide(BlockState state, BlockView view, BlockPos pos, Direction facing, boolean returns) {
        if (!returns && !isBlocked(state.getBlock(), pos)) {
            BlockPos adjPos = pos.offset(facing);
            BlockState adjState = view.getBlockState(adjPos);
            return adjState.getCullingFace(view , adjPos,  facing.getOpposite()) != VoxelShapes.fullCube() || adjState.getBlock() != state.getBlock() || BlockUtils.isExposed(adjPos);
        }

        return returns;
    }

    public boolean isBlocked(Block block, BlockPos blockPos) {
        return !(blocksSetting.get().contains(block) && (!exposedOnly.get() || (blockPos == null || BlockUtils.isExposed(blockPos))));
    }

    public static int getAlpha(BlockState state, BlockPos pos) {
        WallHack wallHack = Modules.get().get(WallHack.class);
        Xray xray = Modules.get().get(Xray.class);
        if (wallHack.isEnabled() && wallHack.blocksSetting.get().contains(state.getBlock())) {
            int alpha;
            if (xray.isEnabled()) {
                alpha = xray.opacity.get();
            } else {
                alpha = wallHack.opacitySetting.get();
            }

            return alpha;
        } else if (xray.isEnabled() && !wallHack.isEnabled() && xray.isBlocked(state.getBlock(), pos)) {
            return xray.opacity.get();
        }

        return -1;
    }
}

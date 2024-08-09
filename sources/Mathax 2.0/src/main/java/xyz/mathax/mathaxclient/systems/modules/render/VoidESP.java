package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.misc.Pool;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.Dimension;
import xyz.mathax.mathaxclient.utils.world.MatHaxDirection;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import xyz.mathax.mathaxclient.settings.*;

import java.util.ArrayList;
import java.util.List;

public class VoidESP extends Module {
    private static final Direction[] SIDES = {
            Direction.EAST,
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST
    };

    private final BlockPos.Mutable blockPos = new BlockPos.Mutable();

    private final Pool<Void> voidHolePool = new Pool<>(Void::new);
    private final List<Void> voidHoles = new ArrayList<>();

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Boolean> airOnlySetting = generalSettings.add(new BoolSetting.Builder()
            .name("Air only")
            .description("Check bedrock only for air blocks.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> horizontalRadiusSetting = generalSettings.add(new IntSetting.Builder()
            .name("Horizontal radius")
            .description("Horizontal radius in which to search for holes.")
            .defaultValue(64)
            .min(0)
            .sliderRange(0, 256)
            .build()
    );

    private final Setting<Integer> holeHeightSetting = generalSettings.add(new IntSetting.Builder()
            .name("Hole height")
            .description("The minimum hole height to be rendered.")
            .defaultValue(1)
            .min(1)
            .sliderRange(1, 5)
            .build()
    );

    private final Setting<Boolean> netherRoofSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Nether roof")
            .description("Check for holes in nether roof.")
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

    private final Setting<SettingColor> sideColor = renderSettings.add(new ColorSetting.Builder()
            .name("Fill color")
            .description("The color that fills holes in the void.")
            .defaultValue(new SettingColor(225, 25, 25, 75))
            .build()
    );

    private final Setting<SettingColor> lineColor = renderSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The color to draw lines of holes to the void.")
            .defaultValue(new SettingColor(225, 25, 255))
            .build()
    );

    public VoidESP(Category category) {
        super(category, "Void ESP", "Renders holes in bedrock layers that lead to the void.");
    }

    private boolean isBlockWrong(BlockPos blockPos) {
        Chunk chunk = mc.world.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4, ChunkStatus.FULL, false);
        if (chunk == null) {
            return true;
        }

        Block block = chunk.getBlockState(blockPos).getBlock();
        if (airOnlySetting.get()) {
            return block != Blocks.AIR;
        }

        return block == Blocks.BEDROCK;
    }

    private boolean isHole(BlockPos.Mutable blockPos, boolean nether) {
        for (int i = 0; i < holeHeightSetting.get(); i++) {
            blockPos.setY(nether ? 127 - i : mc.world.getBottomY());
            if (isBlockWrong(blockPos)) {
                return false;
            }
        }

        return true;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        voidHoles.clear();

        if (PlayerUtils.getDimension() == Dimension.End) {
            return;
        }

        int px = mc.player.getBlockPos().getX();
        int pz = mc.player.getBlockPos().getZ();
        int radius = horizontalRadiusSetting.get();
        for (int x = px - radius; x <= px + radius; x++) {
            for (int z = pz - radius; z <= pz + radius; z++) {
                blockPos.set(x, mc.world.getBottomY(), z);
                if (isHole(blockPos, false)) {
                    voidHoles.add(voidHolePool.get().set(blockPos.set(x, mc.world.getBottomY(), z), false));
                }

                if (netherRoofSetting.get() && PlayerUtils.getDimension() == Dimension.Nether) {
                    blockPos.set(x, 127, z);
                    if (isHole(blockPos, true)) {
                        voidHoles.add(voidHolePool.get().set(blockPos.set(x, 127, z), true));
                    }
                }
            }
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        for (Void voidHole : voidHoles) {
            voidHole.render(event);
        }
    }

    private class Void {
        private int x, y, z;
        private int excludeDir;

        public Void set(BlockPos.Mutable blockPos, boolean nether) {
            x = blockPos.getX();
            y = blockPos.getY();
            z = blockPos.getZ();
            excludeDir = 0;
            for (Direction side : SIDES) {
                blockPos.set(x + side.getOffsetX(), y, z + side.getOffsetZ());
                if (isHole(blockPos, nether)) {
                    excludeDir |= MatHaxDirection.get(side);
                }
            }

            return this;
        }

        public void render(Render3DEvent event) {
            event.renderer.box(x, y, z, x + 1, y + 1, z + 1, sideColor.get(), lineColor.get(), shapeMode.get(), excludeDir);
        }
    }
}
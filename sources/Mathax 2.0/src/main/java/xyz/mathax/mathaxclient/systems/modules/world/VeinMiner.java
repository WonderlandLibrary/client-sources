package xyz.mathax.mathaxclient.systems.modules.world;

import com.google.common.collect.Sets;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.player.StartBreakingBlockEvent;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.misc.Pool;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.settings.ListMode;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VeinMiner extends Module {
    private final Pool<MyBlock> blockPool = new Pool<>(MyBlock::new);
    private final List<MyBlock> blocks = new ArrayList<>();

    private final List<BlockPos> foundBlockPositions = new ArrayList<>();

    private final Set<Vec3i> blockNeighbours = Sets.newHashSet(
            new Vec3i(1, -1, 1), new Vec3i(0, -1, 1), new Vec3i(-1, -1, 1),
            new Vec3i(1, -1, 0), new Vec3i(0, -1, 0), new Vec3i(-1, -1, 0),
            new Vec3i(1, -1, -1), new Vec3i(0, -1, -1), new Vec3i(-1, -1, -1),

            new Vec3i(1, 0, 1), new Vec3i(0, 0, 1), new Vec3i(-1, 0, 1),
            new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0),
            new Vec3i(1, 0, -1), new Vec3i(0, 0, -1), new Vec3i(-1, 0, -1),

            new Vec3i(1, 1, 1), new Vec3i(0, 1, 1), new Vec3i(-1, 1, 1),
            new Vec3i(1, 1, 0), new Vec3i(0, 1, 0), new Vec3i(-1, 1, 0),
            new Vec3i(1, 1, -1), new Vec3i(0, 1, -1), new Vec3i(-1, 1, -1)
    );

    private int tick = 0;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup listSettings = settings.createGroup("List");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Integer> depthSetting = generalSettings.add(new IntSetting.Builder()
            .name("Depth")
            .description("Amount of iterations used to scan for similar blocks.")
            .defaultValue(3)
            .min(1)
            .sliderRange(1, 15)
            .build()
    );

    private final Setting<Integer> delaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Delay")
            .description("Delay between mining blocks.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Send rotation packets to the server when mining.")
            .defaultValue(true)
            .build()
    );

    // List

    private final Setting<ListMode> listModeSetting = listSettings.add(new EnumSetting.Builder<ListMode>()
            .name("List mode")
            .description("Selection mode.")
            .defaultValue(ListMode.Whitelist)
            .build()
    );

    private final Setting<List<Block>> whitelistSetting = listSettings.add(new BlockListSetting.Builder()
            .name("Whitelist")
            .description("The blocks you want to use.")
            .visible(() -> listModeSetting.get() == ListMode.Whitelist)
            .defaultValue(
                    Blocks.STONE,
                    Blocks.DIRT,
                    Blocks.GRASS
            )
            .build()
    );

    private final Setting<List<Block>> blacklistSetting = listSettings.add(new BlockListSetting.Builder()
            .name("Blacklist")
            .description("The blocks you don't want to use.")
            .visible(() -> listModeSetting.get() == ListMode.Blacklist)
            .defaultValue(new ArrayList<>())
            .build()
    );

    // Render

    private final Setting<Boolean> swingHandSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Swing hand")
            .description("Swing hand client side.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> renderSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Whether or not to render the block being mined.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> shapeModeSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The color of the sides of the blocks being rendered.")
            .defaultValue(new SettingColor(205, 0, 0, 75))
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The color of the lines of the blocks being rendered.")
            .defaultValue(new SettingColor(205, 0, 0))
            .build()
    );

    public VeinMiner(Category category) {
        super(category, "Vein Miner", "Mines all nearby blocks with this type");
    }

    @Override
    public void onDisable() {
        for (MyBlock block : blocks) {
            blockPool.free(block);
        }

        blocks.clear();

        foundBlockPositions.clear();
    }

    private boolean isMiningBlock(BlockPos pos) {
        for (MyBlock block : blocks) {
            if (block.blockPos.equals(pos)) {
                return true;
            }
        }

        return false;
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent event) {
        BlockState state = mc.world.getBlockState(event.blockPos);
        if (state.getHardness(mc.world, event.blockPos) < 0) {
            return;
        }

        if (listModeSetting.get() == ListMode.Whitelist && !whitelistSetting.get().contains(state.getBlock())) {
            return;
        }

        if (listModeSetting.get() == ListMode.Blacklist && blacklistSetting.get().contains(state.getBlock())) {
            return;
        }

        foundBlockPositions.clear();

        if (!isMiningBlock(event.blockPos)) {
            MyBlock block = blockPool.get();
            block.set(event);
            blocks.add(block);
            mineNearbyBlocks(block.originalBlock.asItem(), event.blockPos, event.direction, depthSetting.get());
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        blocks.removeIf(MyBlock::shouldRemove);

        if (!blocks.isEmpty()) {
            if (tick < delaySetting.get() && !blocks.get(0).mining) {
                tick++;
                return;
            }

            tick = 0;
            blocks.get(0).mine();
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (renderSetting.get()) {
            for (MyBlock block : blocks) {
                block.render(event);
            }
        }
    }

    private class MyBlock {
        public BlockPos blockPos;
        public Direction direction;
        public Block originalBlock;

        public boolean mining;

        public void set(StartBreakingBlockEvent event) {
            this.blockPos = event.blockPos;
            this.direction = event.direction;
            this.originalBlock = mc.world.getBlockState(blockPos).getBlock();
            this.mining = false;
        }

        public void set(BlockPos pos, Direction dir) {
            this.blockPos = pos;
            this.direction = dir;
            this.originalBlock = mc.world.getBlockState(pos).getBlock();
            this.mining = false;
        }

        public boolean shouldRemove() {
            return mc.world.getBlockState(blockPos).getBlock() != originalBlock || Utils.distance(mc.player.getX() - 0.5, mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ() - 0.5, blockPos.getX() + direction.getOffsetX(), blockPos.getY() + direction.getOffsetY(), blockPos.getZ() + direction.getOffsetZ()) > mc.interactionManager.getReachDistance();
        }

        public void mine() {
            if (!mining) {
                mc.player.swingHand(Hand.MAIN_HAND);
                mining = true;
            }

            if (rotateSetting.get()) {
                Rotations.rotate(Rotations.getYaw(blockPos), Rotations.getPitch(blockPos), 50, this::updateBlockBreakingProgress);
            } else {
                updateBlockBreakingProgress();
            }
        }

        private void updateBlockBreakingProgress() {
            BlockUtils.breakBlock(blockPos, swingHandSetting.get());
        }

        public void render(Render3DEvent event) {
            VoxelShape shape = mc.world.getBlockState(blockPos).getOutlineShape(mc.world, blockPos);
            double x1 = blockPos.getX();
            double y1 = blockPos.getY();
            double z1 = blockPos.getZ();
            double x2 = blockPos.getX() + 1;
            double y2 = blockPos.getY() + 1;
            double z2 = blockPos.getZ() + 1;
            if (!shape.isEmpty()) {
                x1 = blockPos.getX() + shape.getMin(Direction.Axis.X);
                y1 = blockPos.getY() + shape.getMin(Direction.Axis.Y);
                z1 = blockPos.getZ() + shape.getMin(Direction.Axis.Z);
                x2 = blockPos.getX() + shape.getMax(Direction.Axis.X);
                y2 = blockPos.getY() + shape.getMax(Direction.Axis.Y);
                z2 = blockPos.getZ() + shape.getMax(Direction.Axis.Z);
            }

            event.renderer.box(x1, y1, z1, x2, y2, z2, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
        }
    }

    private void mineNearbyBlocks(Item item, BlockPos blockPos, Direction direction, int depth) {
        if (depth<=0) {
            return;
        }

        if (foundBlockPositions.contains(blockPos)) {
            return;
        }

        foundBlockPositions.add(blockPos);

        if (Utils.distance(mc.player.getX() - 0.5, mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ() - 0.5, blockPos.getX(), blockPos.getY(), blockPos.getZ()) > mc.interactionManager.getReachDistance()) {
            return;
        }

        for (Vec3i neighbourOffset: blockNeighbours) {
            BlockPos neighbor = blockPos.add(neighbourOffset);
            if (mc.world.getBlockState(neighbor).getBlock().asItem() == item) {
                MyBlock block = blockPool.get();
                block.set(neighbor, direction);
                blocks.add(block);
                mineNearbyBlocks(item, neighbor, direction, depth-1);
            }
        }
    }

    @Override
    public String getInfoString() {
        return listModeSetting.get().toString() + " (" + (listModeSetting.get() == ListMode.Whitelist ? whitelistSetting.get().size() : blacklistSetting.get().size()) + ")";
    }
}
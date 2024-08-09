package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.misc.Pool;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.BlockIterator;
import xyz.mathax.mathaxclient.utils.world.BlockUtils;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AutoWither extends Module {
    private final Pool<Wither> witherPool = new Pool<>(Wither::new);
    private final ArrayList<Wither> withers = new ArrayList<>();

    private Wither wither;

    private int witherTicksWaited, blockTicksWaited;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Integer> horizontalRadiusSetting = generalSettings.add(new IntSetting.Builder()
            .name("Horizontal radius")
            .description("Horizontal radius for placement.")
            .defaultValue(4)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<Integer> verticalRadiusSetting = generalSettings.add(new IntSetting.Builder()
            .name("Vertical radius")
            .description("Vertical radius for placement.")
            .defaultValue(3)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<Priority> prioritySetting = generalSettings.add(new EnumSetting.Builder<Priority>()
            .name("Priority")
            .description("Which withers to prioritize.")
            .defaultValue(Priority.Random)
            .build()
    );

    private final Setting<Integer> witherDelaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Wither delay")
            .description("Delay in ticks between wither placements")
            .defaultValue(1)
            .min(1)
            .sliderRange(1, 10)
            .build()
    );

    private final Setting<Integer> blockDelaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Block delay")
            .description("Delay in ticks between block placements")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 10)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Whether or not to rotate while building")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> turnOffSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Turn off")
            .description("Turn off automatically after building a single wither.")
            .defaultValue(true)
            .build()
    );

    // Render

    private final Setting<Boolean> renderSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Whether or not to render the block being mined.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> shapeModeSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("Determines how the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The side color of the target block rendering.")
            .defaultValue(new SettingColor(Color.MATHAX, 75))
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The line color of the target block rendering.")
            .defaultValue(new SettingColor(Color.MATHAX))
            .build()
    );

    public AutoWither(Category category) {
        super(category, "Auto Wither", "Automatically builds withers.");
    }

    @Override
    public void onDisable() {
        wither = null;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (wither == null) {
            if (witherTicksWaited < witherDelaySetting.get() - 1) {
                return;
            }

            for (Wither wither : withers) {
                witherPool.free(wither);
            }

            withers.clear();

            BlockIterator.register(horizontalRadiusSetting.get(), verticalRadiusSetting.get(), (blockPos, blockState) -> {
                Direction direction = Direction.fromRotation(Rotations.getYaw(blockPos)).getOpposite();
                if (isValidSpawn(blockPos, direction)) {
                    withers.add(witherPool.get().set(blockPos, direction));
                }
            });
        }
    }

    @EventHandler
    private void onPostTick(TickEvent.Post event) {
        if (wither == null) {
            if (witherTicksWaited < witherDelaySetting.get() - 1) {
                witherTicksWaited++;
                return;
            }


            if (withers.isEmpty()) {
                return;
            }

            switch (prioritySetting.get()) {
                case Closest -> withers.sort(Comparator.comparingDouble(w -> PlayerUtils.distanceTo(w.foot)));
                case Furthest -> {
                    withers.sort((w1, w2) -> {
                        int sort = Double.compare(PlayerUtils.distanceTo(w1.foot), PlayerUtils.distanceTo(w2.foot));
                        if (sort == 0) return 0;
                        return sort > 0 ? -1 : 1;
                    });
                }
                case Random -> Collections.shuffle(withers);
            }

            wither = withers.get(0);
        }

        FindItemResult findSoulSand = InvUtils.findInHotbar(Items.SOUL_SAND);
        if (!findSoulSand.found()) {
            findSoulSand = InvUtils.findInHotbar(Items.SOUL_SOIL);
        }

        FindItemResult findWitherSkull = InvUtils.findInHotbar(Items.WITHER_SKELETON_SKULL);
        if (!findSoulSand.found() || !findWitherSkull.found()) {
            error("Not enough resources in hotbar, disabling...");
            forceToggle(false);
            return;
        }

        if (blockDelaySetting.get() == 0) {
            BlockUtils.place(wither.foot, findSoulSand, rotateSetting.get(), -50);
            BlockUtils.place(wither.foot.up(), findSoulSand, rotateSetting.get(), -50);
            BlockUtils.place(wither.foot.up().offset(wither.axis, -1), findSoulSand, rotateSetting.get(), -50);
            BlockUtils.place(wither.foot.up().offset(wither.axis, 1), findSoulSand, rotateSetting.get(), -50);

            BlockUtils.place(wither.foot.up().up(), findWitherSkull, rotateSetting.get(), -50);
            BlockUtils.place(wither.foot.up().up().offset(wither.axis, -1), findWitherSkull, rotateSetting.get(), -50);
            BlockUtils.place(wither.foot.up().up().offset(wither.axis, 1), findWitherSkull, rotateSetting.get(), -50);

            if (turnOffSetting.get()) {
                wither = null;
                forceToggle(false);
            }
        } else {
            if (blockTicksWaited < blockDelaySetting.get() - 1) {
                blockTicksWaited++;
                return;
            }

            switch (wither.stage) {
                case 0 -> {
                    if (BlockUtils.place(wither.foot, findSoulSand, rotateSetting.get(), -50)) {
                        wither.stage++;
                    }
                }
                case 1 -> {
                    if (BlockUtils.place(wither.foot.up(), findSoulSand, rotateSetting.get(), -50)) {
                        wither.stage++;
                    }
                }
                case 2 -> {
                    if (BlockUtils.place(wither.foot.up().offset(wither.axis, -1), findSoulSand, rotateSetting.get(), -50)) {
                        wither.stage++;
                    }
                }
                case 3 -> {
                    if (BlockUtils.place(wither.foot.up().offset(wither.axis, 1), findSoulSand, rotateSetting.get(), -50)) {
                        wither.stage++;
                    }
                }
                case 4 -> {
                    if (BlockUtils.place(wither.foot.up().up(), findWitherSkull, rotateSetting.get(), -50)) {
                        wither.stage++;
                    }
                }
                case 5 -> {
                    if (BlockUtils.place(wither.foot.up().up().offset(wither.axis, -1), findWitherSkull, rotateSetting.get(), -50)) {
                        wither.stage++;
                    }
                }
                case 6 -> {
                    if (BlockUtils.place(wither.foot.up().up().offset(wither.axis, 1), findWitherSkull, rotateSetting.get(), -50)) {
                        wither.stage++;
                    }
                }
                case 7 -> {
                    if (turnOffSetting.get()) {
                        wither = null;
                        forceToggle(false);
                    }
                }
            }
        }


        witherTicksWaited = 0;
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (wither == null || !renderSetting.get()) {
            return;
        }

        event.renderer.box(wither.foot, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
        event.renderer.box(wither.foot.up(), sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
        event.renderer.box(wither.foot.up().offset(wither.axis, -1), sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
        event.renderer.box(wither.foot.up().offset(wither.axis, 1), sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);

        BlockPos midHead = wither.foot.up().up();
        BlockPos leftHead = wither.foot.up().up().offset(wither.axis, -1);
        BlockPos rightHead = wither.foot.up().up().offset(wither.axis, 1);

        event.renderer.box((double) midHead.getX() + 0.2, (double) midHead.getX(), (double) midHead.getX() + 0.2, (double) midHead.getX() + 0.8, (double) midHead.getX() + 0.7, (double) midHead.getX() + 0.8, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
        event.renderer.box((double) leftHead.getX() + 0.2, (double) leftHead.getX(), (double) leftHead.getX() + 0.2, (double) leftHead.getX() + 0.8, (double) leftHead.getX() + 0.7, (double) leftHead.getX() + 0.8, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
        event.renderer.box((double) rightHead.getX() + 0.2, (double) rightHead.getX(), (double) rightHead.getX() + 0.2, (double) rightHead.getX() + 0.8, (double) rightHead.getX() + 0.7, (double) rightHead.getX() + 0.8, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
    }

    private boolean isValidSpawn(BlockPos blockPos, Direction direction) {
        if (blockPos.getY() > 252) {
            return false;
        }

        int widthX = 0;
        int widthZ = 0;

        if (direction == Direction.EAST || direction == Direction.WEST) {
            widthZ = 1;
        }

        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            widthX = 1;
        }

        BlockPos.Mutable blockPos1 = new BlockPos.Mutable();
        for (int x = blockPos.getX() - widthX; x <= blockPos.getX() + widthX; x++) {
            for (int z = blockPos.getZ() - widthZ; z <= blockPos.getZ(); z++) {
                for (int y = blockPos.getY(); y <= blockPos.getY() + 2; y++) {
                    blockPos1.set(x, y, z);
                    if (!mc.world.getBlockState(blockPos1).getMaterial().isReplaceable()) {
                        return false;
                    }

                    if (!mc.world.canPlace(Blocks.STONE.getDefaultState(), blockPos1, ShapeContext.absent())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static class Wither {
        public BlockPos.Mutable foot = new BlockPos.Mutable();

        public Direction facing;
        public Direction.Axis axis;

        public int stage;

        public Wither set(BlockPos pos, Direction dir) {
            this.foot.set(pos);
            this.facing = dir;
            this.axis = dir.getAxis();
            this.stage = 0;

            return this;
        }
    }

    public enum Priority {
        Closest("Closest"),
        Furthest("Furthest"),
        Random("Random");

        private final String name;

        Priority(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
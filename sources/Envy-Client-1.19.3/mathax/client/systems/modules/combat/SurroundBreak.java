package mathax.client.systems.modules.combat;

import mathax.client.MatHax;
import mathax.client.eventbus.EventHandler;
import mathax.client.eventbus.EventPriority;
import mathax.client.events.render.Render3DEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.renderer.ShapeMode;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.player.PlayerUtils;
import mathax.client.utils.render.color.SettingColor;
import mathax.client.utils.world.BlockUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;

public class SurroundBreak extends Module {
    private final List<BlockPos> placePositions = new ArrayList<>();

    private PlayerEntity target;

    private int renderTimer;
    private int delay;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");

    // General

    private final Setting<Double> placeRange = sgGeneral.add(new DoubleSetting.Builder()
        .name("place-range")
        .description("Determines the radius crystals can be placed in.")
        .defaultValue(4.5)
        .sliderRange(0, 10)
        .build()
    );

    private final Setting<Integer> delaySetting = sgGeneral.add(new IntSetting.Builder()
        .name("place-delay")
        .description("The delay to wait to place a crystal in ticks.")
        .defaultValue(1)
        .sliderRange(0, 10)
        .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
        .name("rotate")
        .description("Rotates server-side towards the crystals being placed.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> checkEntity = sgGeneral.add(new BoolSetting.Builder()
        .name("check-entity")
        .description("Check if placing intersects with entities.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> swapBack = sgGeneral.add(new BoolSetting.Builder()
        .name("swap-back")
        .description("Swaps back to your previous slot after placing.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> direct = sgGeneral.add(new BoolSetting.Builder()
        .name("direct")
        .description("Places a crystal right next to target's surround.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> diagonal = sgGeneral.add(new BoolSetting.Builder()
        .name("diagonal")
        .description("Places a crystal diagonal to target's surround.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> horizontal = sgGeneral.add(new BoolSetting.Builder()
        .name("horizontal")
        .description("Places a crystal horizontal to target's surround.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> below = sgGeneral.add(new BoolSetting.Builder()
        .name("1-below")
        .description("Places a crystal 1 block below to target's surround.")
        .defaultValue(true)
        .build()
    );

    // Render

    private final Setting<Boolean> swing = sgRender.add(new BoolSetting.Builder()
        .name("swing")
        .description("Swings your hand client side when placing or interacting.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder()
        .name("render")
        .description("Renders a block overlay where the crystal will be placed.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> renderTime = sgRender.add(new IntSetting.Builder()
        .name("render-time")
        .description("How long to render for.")
        .defaultValue(10)
        .min(0)
        .sliderRange(0, 30)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("How the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
        .name("side-color")
        .description("The color of the sides of the blocks being rendered.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b, 75))
        .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
        .name("line-color")
        .description("The color of the lines of the blocks being rendered.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b))
        .build()
    );

    public SurroundBreak() {
        super(Categories.Combat, Items.OBSIDIAN, "surround-break", "Automatically places a crystal next to Crystal Aura target's surround.");
    }

    @Override
    public boolean onActivate() {
        if (!placePositions.isEmpty()) placePositions.clear();
        target = null;
        renderTimer = 0;
        delay = 0;
        return false;
    }

    @EventHandler(priority = EventPriority.MEDIUM + 60)
    private void onTick(TickEvent.Pre event) {
        CrystalAura crystalAura = Modules.get().get(CrystalAura.class);

        if (crystalAura.isActive()) target = crystalAura.getPlayerTarget();
        if (target == null || !crystalAura.isActive()) return;

        FindItemResult crystal = InvUtils.findInHotbar(Items.END_CRYSTAL);
        if (!crystal.found()) return;

        if (!PlayerUtils.isSurrounded(target)) return;

        placePositions.clear();

        findPlacePos(target);

        if (delay >= delaySetting.get() && placePositions.size() > 0) {
            BlockPos blockPos = placePositions.get(placePositions.size() - 1);
            if (PlayerUtils.distanceTo(blockPos) > placeRange.get()) return;

            if (BlockUtils.place(blockPos, crystal, rotate.get(), 50, swing.get(), checkEntity.get(), swapBack.get())) placePositions.remove(blockPos);

            delay = 0;
        } else delay++;

        if (renderTimer > 0) renderTimer--;

        renderTimer = renderTime.get();
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (renderTimer > 0 && render.get()) for (BlockPos pos : placePositions) event.renderer.box(pos.down(), sideColor.get(), lineColor.get(), shapeMode.get(), 0);
    }

    private void add(BlockPos blockPos) {
        double x = blockPos.up().getX();
        double y = blockPos.up().getY();
        double z = blockPos.up().getZ();

        if (!placePositions.contains(blockPos) && (mc.world.getOtherEntities(null, new Box(x, y, z, x + 1D, y + 2D, z + 1D)).isEmpty() && mc.world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ())).isAir() && (mc.world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ())).isOf(Blocks.BEDROCK) || mc.world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ())).isOf(Blocks.OBSIDIAN)))) placePositions.add(blockPos);
    }

    private boolean isBedRock(BlockPos pos) {
        return mc.world.getBlockState(pos).isOf(Blocks.BEDROCK);
    }

    private void findPlacePos(PlayerEntity target) {
        placePositions.clear();
        BlockPos targetPos = target.getBlockPos();

        if (direct.get()) {
            if (!isBedRock(targetPos.add(1, 0, 0))) add(targetPos.add(2, 0, 0));
            if (!isBedRock(targetPos.add(0, 0, 1))) add(targetPos.add(0, 0, 2));
            if (!isBedRock(targetPos.add(-1, 0, 0))) add(targetPos.add(-2, 0, 0));
            if (!isBedRock(targetPos.add(0, 0, -1))) add(targetPos.add(0, 0, -2));
        } else if (diagonal.get()) {
            if (!isBedRock(targetPos.add(1, 0, 0))) {
                add(targetPos.add(2, 0, 1));
                add(targetPos.add(2, 0, -1));
            }

            if (!isBedRock(targetPos.add(-1, 0, 0))) {
                add(targetPos.add(-2, 0, 1));
                add(targetPos.add(-2, 0, -1));
            }

            if (!isBedRock(targetPos.add(0, 0, 1))) {
                add(targetPos.add(1, 0, 2));
                add(targetPos.add(-1, 0, 2));
            }

            if (!isBedRock(targetPos.add(0, 0, -1))) {
                add(targetPos.add(1, 0, -2));
                add(targetPos.add(-1, 0, -2));
            }
        } else if (horizontal.get()) {
            if (!isBedRock(targetPos.add(1, 0, 0)) && !isBedRock(targetPos.add(0, 0, 1))) add(targetPos.add(1, 0, 1));
            if (!isBedRock(targetPos.add(-1, 0, 0)) && !isBedRock(targetPos.add(0, 0, 1))) add(targetPos.add(-1, 0, 1));
            if (!isBedRock(targetPos.add(-1, 0, 0)) && !isBedRock(targetPos.add(0, 0, -1))) add(targetPos.add(-1, 0, -1));
            if (!isBedRock(targetPos.add(1, 0, 0)) && !isBedRock(targetPos.add(0, 0, -1))) add(targetPos.add(1, 0, -1));
        } else if (below.get()) {
            if (!isBedRock(targetPos.add(1, 0, 0))) add(targetPos.add(2, -1, 0));
            if (!isBedRock(targetPos.add(0, 0, 1))) add(targetPos.add(0, -1, 2));
            if (!isBedRock(targetPos.add(-1, 0, 0))) add(targetPos.add(-2, -1, 0));
            if (!isBedRock(targetPos.add(0, 0, -1))) add(targetPos.add(0, -1, -2));

            if (!isBedRock(targetPos.add(1, 0, 0)) && !isBedRock(targetPos.add(0, 0, 1))) add(targetPos.add(1, -1, 1));
            if (!isBedRock(targetPos.add(-1, 0, 0)) && !isBedRock(targetPos.add(0, 0, 1))) add(targetPos.add(-1, -1, 1));
            if (!isBedRock(targetPos.add(-1, 0, 0)) && !isBedRock(targetPos.add(0, 0, -1))) add(targetPos.add(-1, -1, -1));
            if (!isBedRock(targetPos.add(1, 0, 0)) && !isBedRock(targetPos.add(0, 0, -1))) add(targetPos.add(1, -1, -1));

            if (!isBedRock(targetPos.add(1, 0, 0))) {
                add(targetPos.add(2, -1, 1));
                add(targetPos.add(2, -1, -1));
            }

            if (!isBedRock(targetPos.add(-1, 0, 0))) {
                add(targetPos.add(-2, -1, 1));
                add(targetPos.add(-2, -1, -1));
            }

            if (!isBedRock(targetPos.add(0, 0, 1))) {
                add(targetPos.add(1, -1, 2));
                add(targetPos.add(-1, -1, 2));
            }

            if (!isBedRock(targetPos.add(0, 0, -1))) {
                add(targetPos.add(1, -1, -2));
                add(targetPos.add(-1, -1, -2));
            }
        }
    }
}

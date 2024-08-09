package mathax.client.systems.modules.misc;

/*import fi.dy.masa.litematica.world.SchematicWorldHandler;
import fi.dy.masa.litematica.world.WorldSchematic;
import mathax.client.MatHax;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.render.Render3DEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.renderer.ShapeMode;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.render.color.Color;
import mathax.client.utils.render.color.SettingColor;
import mathax.client.utils.world.BlockIterator;
import mathax.client.utils.world.BlockUtils;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
public class LitematicaPrinter extends Module {
    private int timer, placed = 0;
    private int usedSlot = -1;

    private final List<Pair<Integer, BlockPos>> placedFade = new ArrayList<>();

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");

    // General

    private final Setting<Integer> printingRange = sgGeneral.add(new IntSetting.Builder()
        .name("printing-range")
        .description("Printing block place range.")
        .defaultValue(2)
        .range(1, 6)
        .sliderRange(1, 6)
        .build()
    );

    private final Setting<Integer> printingDelay = sgGeneral.add(new IntSetting.Builder()
        .name("printing-delay")
        .description("Delay between printing blocks in ticks.")
        .defaultValue(2)
        .range(0, 100)
        .sliderRange(0, 50)
        .build()
    );

    private final Setting<Integer> bpt = sgGeneral.add(new IntSetting.Builder()
        .name("blocks/tick")
        .description("How many blocks to place in 1 tick.")
        .defaultValue(5)
        .range(1, 100)
        .sliderRange(1, 100)
        .build()
    );

    private final Setting<Boolean> advanced = sgGeneral.add(new BoolSetting.Builder()
        .name("advanced")
        .description("Respects block rotation (Places blocks in weird places only in singleplayer, multiplayer works fine).")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
        .name("rotate")
        .description("Rotates towards the blocks being placed.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> silent = sgGeneral.add(new BoolSetting.Builder()
        .name("silent-swap")
        .description("Returns back to old slot.")
        .defaultValue(false)
        .build()
    );

    // Render

    private final Setting<Boolean> swing = sgRender.add(new BoolSetting.Builder()
        .name("swing")
        .description("Swings your hand client side when placing.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder()
        .name("render")
        .description("Renders currently placed blocks.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> fadeTime = sgRender.add(new IntSetting.Builder()
        .name("fade-time")
        .description("Render fading time in ticks.")
        .defaultValue(10)
        .range(1, 1000)
        .sliderRange(1, 100)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("Determines how the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
        .name("side-color")
        .description("The side color of the placed block.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b, 50))
        .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
        .name("line-color")
        .description("The line color of the placed block.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b))
        .build()
    );

    public LitematicaPrinter() {
        super(Categories.Misc, Items.COMMAND_BLOCK, "litematica-printer", "Prints Litematica schematics.");
    }

    @Override
    public void onDeactivate() {
        placedFade.clear();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) {
            placedFade.clear();
            return;
        }

        placedFade.forEach(s -> s.setLeft(s.getLeft()-1));
        placedFade.removeIf(s -> s.getLeft() <= 0);

        WorldSchematic worldSchematic = SchematicWorldHandler.getSchematicWorld();
        if (worldSchematic == null) {
            placedFade.clear();
            toggle();
            return;
        }

        if (timer >= printingDelay.get()) {
            BlockIterator.register(printingRange.get(), printingRange.get(), (pos, blockState) -> {
                if (!mc.player.getBlockPos().isWithinDistance(pos, printingRange.get()) || !blockState.isAir()) return;
                BlockState required = worldSchematic.getBlockState(pos);

                if (!required.isAir() && blockState.getBlock() != required.getBlock()) {
                    if (swichItem(required.getBlock().asItem(), () -> place(required, pos, advanced.get(), swing.get()))) {
                        placed++;
                        if (render.get()) placedFade.add(new Pair<>(fadeTime.get(), new BlockPos(pos)));
                        if (placed >= bpt.get()) {
                            BlockIterator.disableCurrent();
                            placed = 0;
                        }

                        timer = 0;
                    }
                }
            });
        } else timer++;
    }

    public boolean place(BlockState required, BlockPos pos, boolean advanced, boolean swing) {
        if (mc.player == null || mc.world == null) return false;

        if (mc.world.isAir(pos) || mc.world.getBlockState(pos).getMaterial().isLiquid()) {
            Direction direction = direction(required);

            if (!advanced || direction == Direction.UP) return BlockUtils.place(pos, Hand.MAIN_HAND, mc.player.getInventory().selectedSlot, rotate.get(), 0, swing, true, silent.get());
            else return BlockUtils.placePrinter(pos, direction, swing);
        } else return false;
    }

    private boolean swichItem(Item item, Supplier<Boolean> action) {
        if (mc.player == null) return false;
        int a = mc.player.getInventory().selectedSlot;
        FindItemResult result = InvUtils.find(item);
        if (mc.player.getMainHandStack().getItem() == item) {
            if (action.get()) {
                usedSlot = mc.player.getInventory().selectedSlot;
                return true;
            } else return false;
        } else if (usedSlot != -1 && mc.player.getInventory().getStack(usedSlot).getItem() == item) {
            InvUtils.swap(usedSlot, silent.get());
            if (action.get()) {
                InvUtils.swap(a, silent.get());
                return true;
            } else {
                InvUtils.swap(a, silent.get());
                return false;
            }
        } else if (result.found()) {
            if (result.isHotbar()) {
                InvUtils.swap(result.slot(), silent.get());
                if (action.get()) {
                    usedSlot = mc.player.getInventory().selectedSlot;
                    InvUtils.swap(a, silent.get());
                    return true;
                } else {
                    InvUtils.swap(a, silent.get());
                    return false;
                }
            } else if (result.isMain()){
                FindItemResult empty = InvUtils.findEmpty();
                if (empty.found() && empty.isHotbar()) {
                    InvUtils.move().from(result.slot()).toHotbar(empty.slot());
                    InvUtils.swap(empty.slot(), silent.get());
                    if (action.get()) {
                        usedSlot = mc.player.getInventory().selectedSlot;
                        InvUtils.swap(a, silent.get());
                        return true;
                    } else {
                        InvUtils.swap(a, silent.get());
                        return false;
                    }
                } else if (usedSlot != -1) {
                    InvUtils.move().from(result.slot()).toHotbar(usedSlot);
                    InvUtils.swap(usedSlot, silent.get());
                    if (action.get()) {
                        InvUtils.swap(a, silent.get());
                        return true;
                    } else {
                        InvUtils.swap(a, silent.get());
                        return false;
                    }
                } else return false;
            } else return false;
        } else return false;
    }

    private Direction direction(BlockState state) {
        if (state.contains(Properties.FACING)) return state.get(Properties.FACING);
        else if (state.contains(Properties.AXIS)) return Direction.from(state.get(Properties.AXIS), Direction.AxisDirection.POSITIVE);
        else if (state.contains(Properties.HORIZONTAL_AXIS)) return Direction.from(state.get(Properties.HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE);
        else return Direction.UP;
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        placedFade.forEach(block -> event.renderer.box(block.getRight(), new Color(sideColor.get().r, sideColor.get().g, sideColor.get().b, (int) (((float) block.getLeft() / (float) fadeTime.get()) * sideColor.get().a)), new Color(lineColor.get().r, lineColor.get().g, lineColor.get().b, (int) (((float) block.getLeft() / (float) fadeTime.get()) * lineColor.get().a)), shapeMode.get(), 0));
    }
}*/

public class LitematicaPrinter {}

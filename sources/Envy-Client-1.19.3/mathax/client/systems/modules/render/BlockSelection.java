package mathax.client.systems.modules.render;

import mathax.client.MatHax;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.render.Render3DEvent;
import mathax.client.renderer.ShapeMode;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.render.color.SettingColor;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

/*/---------------------------------------------------------------------------------------------------------------------/*/
/*/ Cool mode used from Karasic Meteor Addon                                                                            /*/
/*/ https://github.com/Kiriyaga7615/karasic/blob/main/src/main/java/bedtrap/kiriyaga/karasic/modules/blockrenderer.java /*/
/*/---------------------------------------------------------------------------------------------------------------------/*/

public class BlockSelection extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgColors = settings.createGroup("Colors");

    // General

    private final Setting<Boolean> cool = sgGeneral.add(new BoolSetting.Builder()
        .name("cool")
        .description("Makes the block selection cool.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> advanced = sgGeneral.add(new BoolSetting.Builder()
        .name("advanced")
        .description("Shows a more advanced outline on different types of shape blocks.")
        .defaultValue(true)
        .visible(() -> !cool.get())
        .build()
    );

    private final Setting<Boolean> oneSide = sgGeneral.add(new BoolSetting.Builder()
        .name("single-side")
        .description("Only renders the side you are looking at.")
        .defaultValue(false)
        .visible(() -> !cool.get())
        .build()
    );

    private final Setting<Boolean> hideInside = sgGeneral.add(new BoolSetting.Builder()
        .name("hide-when-inside")
        .description("Hide selection when inside target block.")
        .defaultValue(true)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgGeneral.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("Determines how the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .build()
    );

    // Colors

    private final Setting<SettingColor> sideColor = sgColors.add(new ColorSetting.Builder()
        .name("side")
        .description("The side color.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b, 75))
        .build()
    );

    private final Setting<SettingColor> sideTwoColor = sgColors.add(new ColorSetting.Builder()
        .name("side-2")
        .description("The second side color.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_BACKGROUND_COLOR.r, MatHax.INSTANCE.MATHAX_BACKGROUND_COLOR.g, MatHax.INSTANCE.MATHAX_BACKGROUND_COLOR.b, 75))
        .visible(cool::get)
        .build()
    );

    private final Setting<SettingColor> lineColor = sgColors.add(new ColorSetting.Builder()
        .name("line")
        .description("The line color.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_COLOR.r, MatHax.INSTANCE.MATHAX_COLOR.g, MatHax.INSTANCE.MATHAX_COLOR.b))
        .build()
    );

    private final Setting<SettingColor> lineTwoColor = sgColors.add(new ColorSetting.Builder()
        .name("line-2")
        .description("The second line color.")
        .defaultValue(new SettingColor(MatHax.INSTANCE.MATHAX_BACKGROUND_COLOR.r, MatHax.INSTANCE.MATHAX_BACKGROUND_COLOR.g, MatHax.INSTANCE.MATHAX_BACKGROUND_COLOR.b))
        .visible(cool::get)
       .build()
    );

    public BlockSelection() {
        super(Categories.Render, Items.WHITE_STAINED_GLASS, "block-selection", "Modifies how your block selection is rendered.");
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (mc.crosshairTarget == null || !(mc.crosshairTarget instanceof BlockHitResult result)) return;

        if (hideInside.get() && result.isInsideBlock()) return;

        if (cool.get()) {
            BlockPos pos = result.getBlockPos();

            BlockState state = mc.world.getBlockState(pos);
            VoxelShape shape = state.getOutlineShape(mc.world, pos);

            if (shape.isEmpty()) return;

            renderCool(event,pos);
            return;
        }

        BlockPos bp = result.getBlockPos();
        Direction side = result.getSide();

        BlockState state = mc.world.getBlockState(bp);
        VoxelShape shape = state.getOutlineShape(mc.world, bp);

        if (shape.isEmpty()) return;
        Box box = shape.getBoundingBox(); //bosses are cool

        if (oneSide.get()) {
            if (side == Direction.UP || side == Direction.DOWN) {
                event.renderer.sideHorizontal(bp.getX() + box.minX, bp.getY() + (side == Direction.DOWN ? box.minY : box.maxY), bp.getZ() + box.minZ, bp.getX() + box.maxX, bp.getZ() + box.maxZ, sideColor.get(), lineColor.get(), shapeMode.get());
            } else if (side == Direction.SOUTH || side == Direction.NORTH) {
                double z = side == Direction.NORTH ? box.minZ : box.maxZ;
                event.renderer.sideVertical(bp.getX() + box.minX, bp.getY() + box.minY, bp.getZ() + z, bp.getX() + box.maxX, bp.getY() + box.maxY, bp.getZ() + z, sideColor.get(), lineColor.get(), shapeMode.get());
            } else {
                double x = side == Direction.WEST ? box.minX : box.maxX;
                event.renderer.sideVertical(bp.getX() + x, bp.getY() + box.minY, bp.getZ() + box.minZ, bp.getX() + x, bp.getY() + box.maxY, bp.getZ() + box.maxZ, sideColor.get(), lineColor.get(), shapeMode.get());
            }
        } else {
            if (advanced.get()) {
                if (shapeMode.get() == ShapeMode.Both || shapeMode.get() == ShapeMode.Lines) {
                    shape.forEachEdge((minX, minY, minZ, maxX, maxY, maxZ) -> event.renderer.line(bp.getX() + minX, bp.getY() + minY, bp.getZ() + minZ, bp.getX() + maxX, bp.getY() + maxY, bp.getZ() + maxZ, lineColor.get()));
                }

                if (shapeMode.get() == ShapeMode.Both || shapeMode.get() == ShapeMode.Sides) {
                    for (Box b : shape.getBoundingBoxes()) {
                        render(event, bp, b);
                    }
                }
            } else {
                render(event, bp, box);
            }
        }
    }

    private void render(Render3DEvent event, BlockPos bp, Box box) {
        event.renderer.box(bp.getX() + box.minX, bp.getY() + box.minY, bp.getZ() + box.minZ, bp.getX() + box.maxX, bp.getY() + box.maxY, bp.getZ() + box.maxZ, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
    }

    // Lines
    private void renderCool(Render3DEvent event, BlockPos pos) {
        if (shapeMode.get() == ShapeMode.Lines || shapeMode.get() == ShapeMode.Both) {
            // Sides
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY() + 1, pos.getZ() + 0.02, lineColor.get(), lineTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 0.02, pos.getY() + 1, pos.getZ(), lineColor.get(), lineTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX() + 1, pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 0.02, lineColor.get(), lineTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX() + 1, pos.getY(), pos.getZ(), pos.getX() + 0.98, pos.getY() + 1, pos.getZ(), lineColor.get(), lineTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY(), pos.getZ() + 1, pos.getX(), pos.getY() + 1, pos.getZ() + 0.98, lineColor.get(), lineTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY(), pos.getZ() + 1, pos.getX() + 0.02, pos.getY() + 1, pos.getZ() + 1, lineColor.get(), lineTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX() + 1, pos.getY(), pos.getZ() + 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 0.98, lineColor.get(), lineTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX() + 1, pos.getY(), pos.getZ() + 1, pos.getX() + 0.98, pos.getY() + 1, pos.getZ() + 1, lineColor.get(), lineTwoColor.get());

            // Up
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 0.98, pos.getZ(), lineColor.get(), lineColor.get());
            event.renderer.quadHorizontal(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getZ() + 0.02, lineColor.get());
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX(), pos.getY() + 0.98, pos.getZ() + 1, lineColor.get(), lineColor.get());
            event.renderer.quadHorizontal(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 0.02, pos.getZ() + 1, lineColor.get());
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY() + 1, pos.getZ() + 1, pos.getX() + 1, pos.getY() + 0.98, pos.getZ() + 1, lineColor.get(), lineColor.get());
            event.renderer.quadHorizontal(pos.getX(), pos.getY() + 1, pos.getZ() + 1, pos.getX() + 1, pos.getZ() + 0.98, lineColor.get());
            event.renderer.gradientQuadVertical(pos.getX() + 1, pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 0.98, pos.getZ() + 1, lineColor.get(), lineColor.get());
            event.renderer.quadHorizontal(pos.getX() + 1, pos.getY() + 1, pos.getZ(), pos.getX() + 0.98, pos.getZ() + 1, lineColor.get());

            // Down
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.02, pos.getZ(), lineTwoColor.get(), lineTwoColor.get());
            event.renderer.quadHorizontal(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getZ() + 0.02, lineTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY() + 0.02, pos.getZ() + 1, lineTwoColor.get(), lineTwoColor.get());
            event.renderer.quadHorizontal(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 0.02, pos.getZ() + 1, lineTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY(), pos.getZ() + 1, pos.getX() + 1, pos.getY() + 0.02, pos.getZ() + 1, lineTwoColor.get(), lineTwoColor.get());
            event.renderer.quadHorizontal(pos.getX(), pos.getY(), pos.getZ() + 1, pos.getX() + 1, pos.getZ() + 0.98, lineTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX() + 1, pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.02, pos.getZ() + 1, lineTwoColor.get(), lineTwoColor.get());
            event.renderer.quadHorizontal(pos.getX() + 1, pos.getY(), pos.getZ(), pos.getX() + 0.98, pos.getZ() + 1, lineTwoColor.get());
        }


        // Sides
        if (shapeMode.get() == ShapeMode.Sides || shapeMode.get() == ShapeMode.Both) {
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ(), sideColor.get(), sideTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY() + 1, pos.getZ() + 1, sideColor.get(), sideTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX() + 1, pos.getY(), pos.getZ() + 1, pos.getX() + 1, pos.getY() + 1, pos.getZ(), sideColor.get(), sideTwoColor.get());
            event.renderer.gradientQuadVertical(pos.getX() + 1, pos.getY(), pos.getZ() + 1, pos.getX(), pos.getY() + 1, pos.getZ() + 1, sideColor.get(), sideTwoColor.get());
            event.renderer.quadHorizontal(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getZ() + 1, sideColor.get());
            event.renderer.quadHorizontal(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getZ() + 1, sideTwoColor.get());
        }
    }
}

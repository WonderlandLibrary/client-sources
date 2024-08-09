package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import xyz.mathax.mathaxclient.settings.*;

public class BlockSelection extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Boolean> advancedSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Advanced")
            .description("Show a more advanced outline on different types of shape blocks.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> oneSideSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Single side")
            .description("Only renders the side you are looking at.")
            .defaultValue(false)
            .build()
    );

    private final Setting<ShapeMode> shapeModeSetting = generalSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The side color.")
            .defaultValue(new SettingColor(Color.WHITE, 75))
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The line color.")
            .defaultValue(new SettingColor())
            .build()
    );

    private final Setting<Boolean> hideInsideSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Hide when inside block")
            .description("Hide selection when inside target block.")
            .defaultValue(true)
            .build()
    );

    public BlockSelection(Category category) {
        super(category, "Block Selection", "Modifies how your block selection is rendered.");
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (mc.crosshairTarget == null || !(mc.crosshairTarget instanceof BlockHitResult result)) {
            return;
        }

        if (hideInsideSetting.get() && result.isInsideBlock()) {
            return;
        }

        BlockPos blockPos = result.getBlockPos();
        Direction side = result.getSide();
        VoxelShape shape = mc.world.getBlockState(blockPos).getOutlineShape(mc.world, blockPos);
        if (shape.isEmpty()) {
            return;
        }

        Box box = shape.getBoundingBox();
        if (oneSideSetting.get()) {
            if (side == Direction.UP || side == Direction.DOWN) {
                event.renderer.sideHorizontal(blockPos.getX() + box.minX, blockPos.getY() + (side == Direction.DOWN ? box.minY : box.maxY), blockPos.getZ() + box.minZ, blockPos.getX() + box.maxX, blockPos.getZ() + box.maxZ, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get());
            } else if (side == Direction.SOUTH || side == Direction.NORTH) {
                double z = side == Direction.NORTH ? box.minZ : box.maxZ;
                event.renderer.sideVertical(blockPos.getX() + box.minX, blockPos.getY() + box.minY, blockPos.getZ() + z, blockPos.getX() + box.maxX, blockPos.getY() + box.maxY, blockPos.getZ() + z, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get());
            } else {
                double x = side == Direction.WEST ? box.minX : box.maxX;
                event.renderer.sideVertical(blockPos.getX() + x, blockPos.getY() + box.minY, blockPos.getZ() + box.minZ, blockPos.getX() + x, blockPos.getY() + box.maxY, blockPos.getZ() + box.maxZ, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get());
            }
        } else {
            if (advancedSetting.get()) {
                if (shapeModeSetting.get() == ShapeMode.Both || shapeModeSetting.get() == ShapeMode.Lines) {
                    shape.forEachEdge((minX, minY, minZ, maxX, maxY, maxZ) -> {
                        event.renderer.line(blockPos.getX() + minX, blockPos.getY() + minY, blockPos.getZ() + minZ, blockPos.getX() + maxX, blockPos.getY() + maxY, blockPos.getZ() + maxZ, lineColorSetting.get());
                    });
                }

                if (shapeModeSetting.get() == ShapeMode.Both || shapeModeSetting.get() == ShapeMode.Sides) {
                    for (Box box1 : shape.getBoundingBoxes()) {
                        render(event, blockPos, box1);
                    }
                }
            } else {
                render(event, blockPos, box);
            }
        }
    }

    private void render(Render3DEvent event, BlockPos bp, Box box) {
        event.renderer.box(bp.getX() + box.minX, bp.getY() + box.minY, bp.getZ() + box.minZ, bp.getX() + box.maxX, bp.getY() + box.maxY, bp.getZ() + box.maxZ, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get(), 0);
    }
}

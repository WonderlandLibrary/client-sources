package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.render.MeshVertexConsumerProvider;
import xyz.mathax.mathaxclient.utils.render.RenderUtils;
import xyz.mathax.mathaxclient.utils.render.SimpleBlockRenderer;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.render.postprocess.PostProcessShaders;
import xyz.mathax.mathaxclient.utils.world.MatHaxDirection;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.*;
import net.minecraft.block.enums.ChestType;
import xyz.mathax.mathaxclient.renderer.*;
import xyz.mathax.mathaxclient.settings.*;

import java.util.List;

public class StorageESP extends Module {
    private final Color lineColor = new Color(0, 0, 0, 0);
    private final Color sideColor = new Color(0, 0, 0, 0);

    private boolean render;

    private int count;

    private final Mesh mesh;
    private final MeshVertexConsumerProvider vertexConsumerProvider;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("Rendering mode.")
            .defaultValue(Mode.Shader)
            .build()
    );

    private final Setting<List<BlockEntityType<?>>> storageBlocksSetting = generalSettings.add(new StorageBlockListSetting.Builder()
            .name("Storage blocks")
            .description("Select the storage blocks to display.")
            .defaultValue(StorageBlockListSetting.STORAGE_BLOCKS)
            .build()
    );

    private final Setting<Boolean> tracersSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Tracers")
            .description("Draw tracers to storage blocks.")
            .defaultValue(false)
            .build()
    );

    public final Setting<ShapeMode> shapeModeSetting = generalSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    public final Setting<Integer> fillOpacitySetting = generalSettings.add(new IntSetting.Builder()
            .name("Fill opacity")
            .description("The opacity of the shape fill.")
            .visible(() -> shapeModeSetting.get() != ShapeMode.Lines)
            .defaultValue(50)
            .range(0, 255)
            .sliderRange(0, 255)
            .build()
    );

    public final Setting<Integer> outlineWidthSetting = generalSettings.add(new IntSetting.Builder()
            .name("Width")
            .description("The width of the shader outline.")
            .visible(() -> modeSetting.get() == Mode.Shader)
            .defaultValue(1)
            .range(1, 10)
            .sliderRange(1, 5)
            .build()
    );

    public final Setting<Double> glowMultiplierSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Glow multiplier")
            .description("Multiplier for the glow effect.")
            .visible(() -> modeSetting.get() == Mode.Shader)
            .decimalPlaces(3)
            .defaultValue(3.5)
            .min(0)
            .sliderRange(0, 10)
            .build()
    );

    private final Setting<SettingColor> chestSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Chest")
            .description("The color of chests.")
            .defaultValue(new SettingColor(255, 160, 0, 255))
            .build()
    );

    private final Setting<SettingColor> trappedChestSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Trapped chest")
            .description("The color of trapped chests.")
            .defaultValue(new SettingColor(255, 0, 0, 255))
            .build()
    );

    private final Setting<SettingColor> barrelSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Barrel")
            .description("The color of barrels.")
            .defaultValue(new SettingColor(255, 160, 0, 255))
            .build()
    );

    private final Setting<SettingColor> shulkerSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Shulker")
            .description("The color of Shulker Boxes.")
            .defaultValue(new SettingColor(255, 160, 0, 255))
            .build()
    );

    private final Setting<SettingColor> enderChestSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Ender chest")
            .description("The color of Ender Chests.")
            .defaultValue(new SettingColor(120, 0, 255, 255))
            .build()
    );

    private final Setting<SettingColor> otherSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Other")
            .description("The color of furnaces, dispenders, droppers and hoppers.")
            .defaultValue(new SettingColor(140, 140, 140, 255))
            .build()
    );

    private final Setting<Double> fadeDistanceSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Fade distance")
            .description("The distance at which the color will fade.")
            .defaultValue(6)
            .min(0)
            .sliderRange(0, 12)
            .build()
    );

    public StorageESP(Category category) {
        super(category, "Storage ESP", "Renders all specified storage blocks.");

        mesh = new ShaderMesh(Shaders.POS_COLOR, DrawMode.Triangles, Mesh.Attrib.Vec3, Mesh.Attrib.Color);
        vertexConsumerProvider = new MeshVertexConsumerProvider(mesh);
    }

    private void getBlockEntityColor(BlockEntity blockEntity) {
        render = false;

        if (!storageBlocksSetting.get().contains(blockEntity.getType())) {
            return;
        }

        if (blockEntity instanceof TrappedChestBlockEntity) {
            lineColor.set(trappedChestSetting.get()); // Must come before ChestBlockEntity as it is the superclass of TrappedChestBlockEntity
        } else if (blockEntity instanceof ChestBlockEntity) {
            lineColor.set(chestSetting.get());
        } else if (blockEntity instanceof BarrelBlockEntity) {
            lineColor.set(barrelSetting.get());
        } else if (blockEntity instanceof ShulkerBoxBlockEntity) {
            lineColor.set(shulkerSetting.get());
        } else if (blockEntity instanceof EnderChestBlockEntity) {
            lineColor.set(enderChestSetting.get());
        } else if (blockEntity instanceof AbstractFurnaceBlockEntity || blockEntity instanceof DispenserBlockEntity || blockEntity instanceof HopperBlockEntity) {
            lineColor.set(otherSetting.get());
        } else {
            return;
        }

        render = true;

        if (shapeModeSetting.get() == ShapeMode.Sides || shapeModeSetting.get() == ShapeMode.Both) {
            sideColor.set(lineColor);
            sideColor.a = fillOpacitySetting.get();
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        count = 0;

        if (modeSetting.get() == Mode.Shader) {
            mesh.begin();
        }

        for (BlockEntity blockEntity : Utils.blockEntities()) {
            getBlockEntityColor(blockEntity);

            if (render) {
                double dist = mc.player.squaredDistanceTo(blockEntity.getPos().getX() + 0.5, blockEntity.getPos().getY() + 0.5, blockEntity.getPos().getZ() + 0.5);
                double a = 1;
                if (dist <= fadeDistanceSetting.get() * fadeDistanceSetting.get()) {
                    a = dist / (fadeDistanceSetting.get() * fadeDistanceSetting.get());
                }

                int prevLineA = lineColor.a;
                int prevSideA = sideColor.a;

                lineColor.a *= a;
                sideColor.a *= a;

                if (tracersSetting.get() && a >= 0.075) {
                    event.renderer.line(RenderUtils.center.x, RenderUtils.center.y, RenderUtils.center.z, blockEntity.getPos().getX() + 0.5, blockEntity.getPos().getY() + 0.5, blockEntity.getPos().getZ() + 0.5, lineColor);
                }

                if (modeSetting.get() == Mode.Box && a >= 0.075) {
                    renderBox(event, blockEntity);
                }

                lineColor.a = prevLineA;
                sideColor.a = prevSideA;

                if (modeSetting.get() == Mode.Shader) {
                    renderShader(event, blockEntity);
                }

                count++;
            }
        }

        if (modeSetting.get() == Mode.Shader) {
            PostProcessShaders.STORAGE_OUTLINE.endRender(() -> mesh.render(event.matrixStack));
        }
    }

    private void renderBox(Render3DEvent event, BlockEntity blockEntity) {
        double x1 = blockEntity.getPos().getX();
        double y1 = blockEntity.getPos().getY();
        double z1 = blockEntity.getPos().getZ();

        double x2 = blockEntity.getPos().getX() + 1;
        double y2 = blockEntity.getPos().getY() + 1;
        double z2 = blockEntity.getPos().getZ() + 1;

        int excludeDir = 0;
        if (blockEntity instanceof ChestBlockEntity) {
            BlockState state = mc.world.getBlockState(blockEntity.getPos());
            if ((state.getBlock() == Blocks.CHEST || state.getBlock() == Blocks.TRAPPED_CHEST) && state.get(ChestBlock.CHEST_TYPE) != ChestType.SINGLE) {
                excludeDir = MatHaxDirection.get(ChestBlock.getFacing(state));
            }
        }

        if (blockEntity instanceof ChestBlockEntity || blockEntity instanceof EnderChestBlockEntity) {
            double a = 1.0 / 16.0;
            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.WEST)) {
                x1 += a;
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.NORTH)) {
                z1 += a;
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.EAST)) {
                x2 -= a;
            }

            y2 -= a * 2;
            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.SOUTH)) {
                z2 -= a;
            }
        }

        event.renderer.box(x1, y1, z1, x2, y2, z2, sideColor, lineColor, shapeModeSetting.get(), excludeDir);
    }

    private void renderShader(Render3DEvent event, BlockEntity blockEntity) {
        vertexConsumerProvider.setColor(lineColor);
        SimpleBlockRenderer.renderWithBlockEntity(blockEntity, event.tickDelta, vertexConsumerProvider);
    }

    public boolean isShader() {
        return isEnabled() && modeSetting.get() == Mode.Shader;
    }

    @Override
    public String getInfoString() {
        return Integer.toString(count);
    }

    public enum Mode {
        Box("Box"),
        Shader("Shader");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
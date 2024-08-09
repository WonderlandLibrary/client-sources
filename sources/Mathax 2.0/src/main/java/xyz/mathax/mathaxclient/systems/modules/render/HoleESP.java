package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.AbstractBlockAccessor;
import xyz.mathax.mathaxclient.renderer.Renderer3D;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.utils.misc.Pool;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.world.BlockIterator;
import xyz.mathax.mathaxclient.utils.world.MatHaxDirection;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import xyz.mathax.mathaxclient.settings.*;

import java.util.ArrayList;
import java.util.List;

public class HoleESP extends Module {
    private final Pool<Hole> holePool = new Pool<>(Hole::new);
    private final List<Hole> holes = new ArrayList<>();

    private final byte NULL = 0;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Integer> horizontalRadiusSetting = generalSettings.add(new IntSetting.Builder()
            .name("Horizontal-radius")
            .description("Horizontal radius in which to search for holes.")
            .defaultValue(10)
            .sliderRange(0, 32)
            .build()
    );

    private final Setting<Integer> verticalRadiusSetting = generalSettings.add(new IntSetting.Builder()
            .name("Vertical radius")
            .description("Vertical radius in which to search for holes.")
            .defaultValue(5)
            .min(0)
            .sliderMax(32)
            .build()
    );

    private final Setting<Integer> holeHeightSetting = generalSettings.add(new IntSetting.Builder()
            .name("Min height")
            .description("Minimum hole height required to be rendered.")
            .defaultValue(3)
            .min(1)
            .sliderRange(1, 5)
            .build()
    );

    private final Setting<Boolean> doublesSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Doubles")
            .description("Highlight double holes that can be stood across.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> ignoreOwnSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore own")
            .description("Ignore rendering the hole you are currently standing in.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> websSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Webs")
            .description("Whether to show holes that have webs inside of them.")
            .defaultValue(false)
            .build()
    );

    // Render

    private final Setting<ShapeMode> shapeModeSetting = renderSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<Double> heightSetting = renderSettings.add(new DoubleSetting.Builder()
            .name("Height")
            .description("The height of rendering.")
            .defaultValue(0.2)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    private final Setting<Boolean> topQuadSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Top quad")
            .description("Whether to render a quad at the top of the hole.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> bottomQuadSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Bottom quad")
            .description("Whether to render a quad at the bottom of the hole.")
            .defaultValue(false)
            .build()
    );

    private final Setting<SettingColor> bedrockColorTopSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Bedrock top")
            .description("The top color for holes that are completely bedrock.")
            .defaultValue(new SettingColor(100, 255, 0, 200))
            .build()
    );

    private final Setting<SettingColor> bedrockColorBottomSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Bedrock bottom")
            .description("The bottom color for holes that are completely bedrock.")
            .defaultValue(new SettingColor(100, 255, 0))
            .build()
    );

    private final Setting<SettingColor> obsidianColorTopSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Obsidian top")
            .description("The top color for holes that are completely obsidian.")
            .defaultValue(new SettingColor(255, 0, 0, 200))
            .build()
    );

    private final Setting<SettingColor> obsidianColorBottomSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Obsidian bottom")
            .description("The bottom color for holes that are completely obsidian.")
            .defaultValue(new SettingColor(255, 0, 0))
            .build()
    );

    private final Setting<SettingColor> mixedColorTopSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Mixed top")
            .description("The top color for holes that have mixed bedrock and obsidian.")
            .defaultValue(new SettingColor(255, 125, 0, 200))
            .build()
    );

    private final Setting<SettingColor> mixedColorBottomSetting = renderSettings.add(new ColorSetting.Builder()
            .name("Mixed bottom")
            .description("The bottom color for holes that have mixed bedrock and obsidian.")
            .defaultValue(new SettingColor(255, 125, 0))
            .build()
    );

    public HoleESP(Category category) {
        super(category, "Hole ESP", "Displays holes that you will take less damage in.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        for (Hole hole : holes) {
            holePool.free(hole);
        }

        holes.clear();

        BlockIterator.register(horizontalRadiusSetting.get(), verticalRadiusSetting.get(), (blockPos, blockState) -> {
            if (!validHole(blockPos)) {
                return;
            }

            int bedrock = 0, obsidian = 0;
            Direction air = null;
            for (Direction direction : Direction.values()) {
                if (direction == Direction.UP) {
                    continue;
                }

                BlockState state = mc.world.getBlockState(blockPos.offset(direction));
                if (state.getBlock() == Blocks.BEDROCK) {
                    bedrock++;
                } else if (state.getBlock() == Blocks.OBSIDIAN) {
                    obsidian++;
                } else if (direction == Direction.DOWN) {
                    return;
                } else if (validHole(blockPos.offset(direction)) && air == null) {
                    for (Direction directionValue : Direction.values()) {
                        if (directionValue == direction.getOpposite() || directionValue == Direction.UP) {
                            continue;
                        }

                        BlockState blockState1 = mc.world.getBlockState(blockPos.offset(direction).offset(directionValue));
                        if (blockState1.getBlock() == Blocks.BEDROCK) {
                            bedrock++;
                        } else if (blockState1.getBlock() == Blocks.OBSIDIAN) {
                            obsidian++;
                        } else {
                            return;
                        }
                    }

                    air = direction;
                }
            }

            if (obsidian + bedrock == 5 && air == null) {
                holes.add(holePool.get().set(blockPos, obsidian == 5 ? Hole.Type.Obsidian : (bedrock == 5 ? Hole.Type.Bedrock : Hole.Type.Mixed), NULL));
            } else if (obsidian + bedrock == 8 && doublesSetting.get() && air != null) {
                holes.add(holePool.get().set(blockPos, obsidian == 8 ? Hole.Type.Obsidian : (bedrock == 8 ? Hole.Type.Bedrock : Hole.Type.Mixed), MatHaxDirection.get(air)));
            }
        });
    }

    private boolean validHole(BlockPos pos) {
        if ((ignoreOwnSetting.get() && (mc.player.getBlockPos().equals(pos)))) {
            return false;
        }

        if (!websSetting.get() && mc.world.getBlockState(pos).getBlock() == Blocks.COBWEB) {
            return false;
        }

        if (((AbstractBlockAccessor) mc.world.getBlockState(pos).getBlock()).isCollidable()) {
            return false;
        }

        for (int i = 0; i < holeHeightSetting.get(); i++) {
            if (((AbstractBlockAccessor) mc.world.getBlockState(pos.up(i)).getBlock()).isCollidable()) {
                return false;
            }
        }

        return true;
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        for (HoleESP.Hole hole : holes) {
            hole.render(event.renderer, shapeModeSetting.get(), heightSetting.get(), topQuadSetting.get(), bottomQuadSetting.get());
        }
    }

    private static class Hole {
        public BlockPos.Mutable blockPos = new BlockPos.Mutable();

        public byte exclude;

        public Type type;

        public Hole set(BlockPos blockPos, Type type, byte exclude) {
            this.blockPos.set(blockPos);
            this.exclude = exclude;
            this.type = type;

            return this;
        }

        public Color getTopColor() {
            return switch (this.type) {
                case Obsidian -> Modules.get().get(HoleESP.class).obsidianColorTopSetting.get();
                case Bedrock -> Modules.get().get(HoleESP.class).bedrockColorTopSetting.get();
                default -> Modules.get().get(HoleESP.class).mixedColorTopSetting.get();
            };
        }

        public Color getBottomColor() {
            return switch (this.type) {
                case Obsidian -> Modules.get().get(HoleESP.class).obsidianColorBottomSetting.get();
                case Bedrock -> Modules.get().get(HoleESP.class).bedrockColorBottomSetting.get();
                default -> Modules.get().get(HoleESP.class).mixedColorBottomSetting.get();
            };
        }

        public void render(Renderer3D renderer, ShapeMode mode, double height, boolean topQuad, boolean bottomQuad) {
            int x = blockPos.getX();
            int y = blockPos.getY();
            int z = blockPos.getZ();

            Color top = getTopColor();
            Color bottom = getBottomColor();
            int originalTopA = top.a;
            int originalBottompA = bottom.a;
            if (mode.lines()) {
                if (MatHaxDirection.isNot(exclude, MatHaxDirection.WEST) && MatHaxDirection.isNot(exclude, MatHaxDirection.NORTH)) {
                    renderer.line(x, y, z, x, y + height, z, bottom, top);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.WEST) && MatHaxDirection.isNot(exclude, MatHaxDirection.SOUTH)) {
                    renderer.line(x, y, z + 1, x, y + height, z + 1, bottom, top);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.EAST) && MatHaxDirection.isNot(exclude, MatHaxDirection.NORTH)) {
                    renderer.line(x + 1, y, z, x + 1, y + height, z, bottom, top);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.EAST) && MatHaxDirection.isNot(exclude, MatHaxDirection.SOUTH)) {
                    renderer.line(x + 1, y, z + 1, x + 1, y + height, z + 1, bottom, top);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.NORTH)) {
                    renderer.line(x, y, z, x + 1, y, z, bottom);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.NORTH)) {
                    renderer.line(x, y + height, z, x + 1, y + height, z, top);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.SOUTH)) {
                    renderer.line(x, y, z + 1, x + 1, y, z + 1, bottom);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.SOUTH)) {
                    renderer.line(x, y + height, z + 1, x + 1, y + height, z + 1, top);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.WEST)) {
                    renderer.line(x, y, z, x, y, z + 1, bottom);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.WEST)) {
                    renderer.line(x, y + height, z, x, y + height, z + 1, top);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.EAST)) {
                    renderer.line(x + 1, y, z, x + 1, y, z + 1, bottom);
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.EAST)) {
                    renderer.line(x + 1, y + height, z, x + 1, y + height, z + 1, top);
                }
            }

            if (mode.sides()) {
                top.a = originalTopA / 2;
                bottom.a = originalBottompA / 2;

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.UP) && topQuad) {
                    renderer.quad(x, y + height, z, x, y + height, z + 1, x + 1, y + height, z + 1, x + 1, y + height, z, top); // Top
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.DOWN) && bottomQuad) {
                    renderer.quad(x, y, z, x, y, z + 1, x + 1, y, z + 1, x + 1, y, z, bottom); // Bottom
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.NORTH)) {
                    renderer.gradientQuadVertical(x, y, z, x + 1, y + height, z, top, bottom); // North
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.SOUTH)) {
                    renderer.gradientQuadVertical(x, y, z + 1, x + 1, y + height, z + 1, top, bottom); // South
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.WEST)) {
                    renderer.gradientQuadVertical(x, y, z, x, y + height, z + 1, top, bottom); // West
                }

                if (MatHaxDirection.isNot(exclude, MatHaxDirection.EAST)) {
                    renderer.gradientQuadVertical(x + 1, y, z, x + 1, y + height, z + 1, top, bottom); // East
                }

                top.a = originalTopA;
                bottom.a = originalBottompA;
            }
        }

        public enum Type {
            Bedrock("Bedrock"),
            Obsidian("Obsidian"),
            Mixed("Mixed");

            private final String name;

            Type(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return name;
            }
        }
    }
}

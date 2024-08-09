/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import java.util.Optional;
import java.util.stream.IntStream;
import net.minecraft.data.ModelsUtil;
import net.minecraft.data.StockTextureAliases;
import net.minecraft.util.ResourceLocation;

public class StockModelShapes {
    public static final ModelsUtil CUBE = StockModelShapes.makeBlockModel("cube", StockTextureAliases.PARTICLE, StockTextureAliases.NORTH, StockTextureAliases.SOUTH, StockTextureAliases.EAST, StockTextureAliases.WEST, StockTextureAliases.UP, StockTextureAliases.DOWN);
    public static final ModelsUtil CUBE_DIRECTIONAL = StockModelShapes.makeBlockModel("cube_directional", StockTextureAliases.PARTICLE, StockTextureAliases.NORTH, StockTextureAliases.SOUTH, StockTextureAliases.EAST, StockTextureAliases.WEST, StockTextureAliases.UP, StockTextureAliases.DOWN);
    public static final ModelsUtil CUBE_ALL = StockModelShapes.makeBlockModel("cube_all", StockTextureAliases.ALL);
    public static final ModelsUtil CUBE_MIRRORED_ALL = StockModelShapes.makeBlockModel("cube_mirrored_all", "_mirrored", StockTextureAliases.ALL);
    public static final ModelsUtil CUBE_COLUMN = StockModelShapes.makeBlockModel("cube_column", StockTextureAliases.END, StockTextureAliases.SIDE);
    public static final ModelsUtil CUBE_COLUMN_HORIZONTAL = StockModelShapes.makeBlockModel("cube_column_horizontal", "_horizontal", StockTextureAliases.END, StockTextureAliases.SIDE);
    public static final ModelsUtil CUBE_TOP = StockModelShapes.makeBlockModel("cube_top", StockTextureAliases.TOP, StockTextureAliases.SIDE);
    public static final ModelsUtil CUBE_BOTTOM_TOP = StockModelShapes.makeBlockModel("cube_bottom_top", StockTextureAliases.TOP, StockTextureAliases.BOTTOM, StockTextureAliases.SIDE);
    public static final ModelsUtil ORIENTABLE = StockModelShapes.makeBlockModel("orientable", StockTextureAliases.TOP, StockTextureAliases.FRONT, StockTextureAliases.SIDE);
    public static final ModelsUtil ORIENTABLE_WITH_BOTTOM = StockModelShapes.makeBlockModel("orientable_with_bottom", StockTextureAliases.TOP, StockTextureAliases.BOTTOM, StockTextureAliases.SIDE, StockTextureAliases.FRONT);
    public static final ModelsUtil ORIENTABLE_VERTICAL = StockModelShapes.makeBlockModel("orientable_vertical", "_vertical", StockTextureAliases.FRONT, StockTextureAliases.SIDE);
    public static final ModelsUtil BUTTON = StockModelShapes.makeBlockModel("button", StockTextureAliases.TEXTURE);
    public static final ModelsUtil BUTTON_PRESSED = StockModelShapes.makeBlockModel("button_pressed", "_pressed", StockTextureAliases.TEXTURE);
    public static final ModelsUtil BUTTON_INVENTORY = StockModelShapes.makeBlockModel("button_inventory", "_inventory", StockTextureAliases.TEXTURE);
    public static final ModelsUtil DOOR_BOTTOM = StockModelShapes.makeBlockModel("door_bottom", "_bottom", StockTextureAliases.TOP, StockTextureAliases.BOTTOM);
    public static final ModelsUtil DOOR_BOTTOM_RH = StockModelShapes.makeBlockModel("door_bottom_rh", "_bottom_hinge", StockTextureAliases.TOP, StockTextureAliases.BOTTOM);
    public static final ModelsUtil DOOR_TOP = StockModelShapes.makeBlockModel("door_top", "_top", StockTextureAliases.TOP, StockTextureAliases.BOTTOM);
    public static final ModelsUtil DOOR_TOP_RH = StockModelShapes.makeBlockModel("door_top_rh", "_top_hinge", StockTextureAliases.TOP, StockTextureAliases.BOTTOM);
    public static final ModelsUtil FENCE_POST = StockModelShapes.makeBlockModel("fence_post", "_post", StockTextureAliases.TEXTURE);
    public static final ModelsUtil FENCE_SIDE = StockModelShapes.makeBlockModel("fence_side", "_side", StockTextureAliases.TEXTURE);
    public static final ModelsUtil FENCE_INVENTORY = StockModelShapes.makeBlockModel("fence_inventory", "_inventory", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_WALL_POST = StockModelShapes.makeBlockModel("template_wall_post", "_post", StockTextureAliases.WALL);
    public static final ModelsUtil TEMPLATE_WALL_SIDE = StockModelShapes.makeBlockModel("template_wall_side", "_side", StockTextureAliases.WALL);
    public static final ModelsUtil TEMPLATE_WALL_SIDE_TALL = StockModelShapes.makeBlockModel("template_wall_side_tall", "_side_tall", StockTextureAliases.WALL);
    public static final ModelsUtil WALL_INVENTORY = StockModelShapes.makeBlockModel("wall_inventory", "_inventory", StockTextureAliases.WALL);
    public static final ModelsUtil TEMPLATE_FENCE_GATE = StockModelShapes.makeBlockModel("template_fence_gate", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_FENCE_GATE_OPEN = StockModelShapes.makeBlockModel("template_fence_gate_open", "_open", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_FENCE_GATE_WALL = StockModelShapes.makeBlockModel("template_fence_gate_wall", "_wall", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_FENCE_GATE_WALL_OPEN = StockModelShapes.makeBlockModel("template_fence_gate_wall_open", "_wall_open", StockTextureAliases.TEXTURE);
    public static final ModelsUtil PRESSURE_PLATE_UP = StockModelShapes.makeBlockModel("pressure_plate_up", StockTextureAliases.TEXTURE);
    public static final ModelsUtil PRESSURE_PLATE_DOWN = StockModelShapes.makeBlockModel("pressure_plate_down", "_down", StockTextureAliases.TEXTURE);
    public static final ModelsUtil PARTICLE = StockModelShapes.makeEmptyModel(StockTextureAliases.PARTICLE);
    public static final ModelsUtil SLAB = StockModelShapes.makeBlockModel("slab", StockTextureAliases.BOTTOM, StockTextureAliases.TOP, StockTextureAliases.SIDE);
    public static final ModelsUtil SLAB_TOP = StockModelShapes.makeBlockModel("slab_top", "_top", StockTextureAliases.BOTTOM, StockTextureAliases.TOP, StockTextureAliases.SIDE);
    public static final ModelsUtil LEAVES = StockModelShapes.makeBlockModel("leaves", StockTextureAliases.ALL);
    public static final ModelsUtil STAIRS = StockModelShapes.makeBlockModel("stairs", StockTextureAliases.BOTTOM, StockTextureAliases.TOP, StockTextureAliases.SIDE);
    public static final ModelsUtil INNER_STAIRS = StockModelShapes.makeBlockModel("inner_stairs", "_inner", StockTextureAliases.BOTTOM, StockTextureAliases.TOP, StockTextureAliases.SIDE);
    public static final ModelsUtil OUTER_STAIRS = StockModelShapes.makeBlockModel("outer_stairs", "_outer", StockTextureAliases.BOTTOM, StockTextureAliases.TOP, StockTextureAliases.SIDE);
    public static final ModelsUtil TEMPLATE_TRAPDOOR_TOP = StockModelShapes.makeBlockModel("template_trapdoor_top", "_top", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_TRAPDOOR_BOTTOM = StockModelShapes.makeBlockModel("template_trapdoor_bottom", "_bottom", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_TRAPDOOR_OPEN = StockModelShapes.makeBlockModel("template_trapdoor_open", "_open", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_ORIENTABLE_TRAPDOOR_TOP = StockModelShapes.makeBlockModel("template_orientable_trapdoor_top", "_top", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_ORIENTABLE_TRAPDOOR_BOTTOM = StockModelShapes.makeBlockModel("template_orientable_trapdoor_bottom", "_bottom", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_ORIENTABLE_TRAPDOOR_OPEN = StockModelShapes.makeBlockModel("template_orientable_trapdoor_open", "_open", StockTextureAliases.TEXTURE);
    public static final ModelsUtil CROSS = StockModelShapes.makeBlockModel("cross", StockTextureAliases.CROSS);
    public static final ModelsUtil TINTED_CROSS = StockModelShapes.makeBlockModel("tinted_cross", StockTextureAliases.CROSS);
    public static final ModelsUtil FLOWER_POT_CROSS = StockModelShapes.makeBlockModel("flower_pot_cross", StockTextureAliases.PLANT);
    public static final ModelsUtil TINTED_FLOWER_POT_CROSS = StockModelShapes.makeBlockModel("tinted_flower_pot_cross", StockTextureAliases.PLANT);
    public static final ModelsUtil RAIL_FLAT = StockModelShapes.makeBlockModel("rail_flat", StockTextureAliases.RAIL);
    public static final ModelsUtil RAIL_CURVED = StockModelShapes.makeBlockModel("rail_curved", "_corner", StockTextureAliases.RAIL);
    public static final ModelsUtil TEMPLATE_RAIL_RAISED_NE = StockModelShapes.makeBlockModel("template_rail_raised_ne", "_raised_ne", StockTextureAliases.RAIL);
    public static final ModelsUtil TEMPLATE_RAIL_RAISED_SW = StockModelShapes.makeBlockModel("template_rail_raised_sw", "_raised_sw", StockTextureAliases.RAIL);
    public static final ModelsUtil CARPET = StockModelShapes.makeBlockModel("carpet", StockTextureAliases.WOOL);
    public static final ModelsUtil CORAL_FAN = StockModelShapes.makeBlockModel("coral_fan", StockTextureAliases.FAN);
    public static final ModelsUtil CORAL_WALL_FAN = StockModelShapes.makeBlockModel("coral_wall_fan", StockTextureAliases.FAN);
    public static final ModelsUtil TEMPLATE_GLAZED_TERRACOTTA = StockModelShapes.makeBlockModel("template_glazed_terracotta", StockTextureAliases.PATTERN);
    public static final ModelsUtil TEMPLATE_CHORUS_FLOWER = StockModelShapes.makeBlockModel("template_chorus_flower", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_DAYLIGHT_SENSOR = StockModelShapes.makeBlockModel("template_daylight_detector", StockTextureAliases.TOP, StockTextureAliases.SIDE);
    public static final ModelsUtil TEMPLATE_GLASS_PANE_NOSIDE = StockModelShapes.makeBlockModel("template_glass_pane_noside", "_noside", StockTextureAliases.PANE);
    public static final ModelsUtil TEMPLATE_GLASS_PANE_NOSIDE_ALT = StockModelShapes.makeBlockModel("template_glass_pane_noside_alt", "_noside_alt", StockTextureAliases.PANE);
    public static final ModelsUtil TEMPLATE_GLASS_PANE_POST = StockModelShapes.makeBlockModel("template_glass_pane_post", "_post", StockTextureAliases.PANE, StockTextureAliases.EDGE);
    public static final ModelsUtil TEMPLATE_GLASS_PANE_SIDE = StockModelShapes.makeBlockModel("template_glass_pane_side", "_side", StockTextureAliases.PANE, StockTextureAliases.EDGE);
    public static final ModelsUtil TEMPLATE_GLASS_PANE_SIDE_ALT = StockModelShapes.makeBlockModel("template_glass_pane_side_alt", "_side_alt", StockTextureAliases.PANE, StockTextureAliases.EDGE);
    public static final ModelsUtil TEMPLATE_COMMAND_BLOCK = StockModelShapes.makeBlockModel("template_command_block", StockTextureAliases.FRONT, StockTextureAliases.BACK, StockTextureAliases.SIDE);
    public static final ModelsUtil TEMPLATE_ANVIL = StockModelShapes.makeBlockModel("template_anvil", StockTextureAliases.TOP);
    public static final ModelsUtil[] STEM_GROWTH_STAGES = (ModelsUtil[])IntStream.range(0, 8).mapToObj(StockModelShapes::lambda$static$0).toArray(StockModelShapes::lambda$static$1);
    public static final ModelsUtil STEM_FRUIT = StockModelShapes.makeBlockModel("stem_fruit", StockTextureAliases.STEM, StockTextureAliases.UPPERSTEM);
    public static final ModelsUtil CROP = StockModelShapes.makeBlockModel("crop", StockTextureAliases.CROP);
    public static final ModelsUtil TEMPLATE_FARMLAND = StockModelShapes.makeBlockModel("template_farmland", StockTextureAliases.DIRT, StockTextureAliases.TOP);
    public static final ModelsUtil TEMPLATE_FIRE_FLOOR = StockModelShapes.makeBlockModel("template_fire_floor", StockTextureAliases.FIRE);
    public static final ModelsUtil TEMPLATE_FIRE_SIDE = StockModelShapes.makeBlockModel("template_fire_side", StockTextureAliases.FIRE);
    public static final ModelsUtil TEMPLATE_FIRE_SIDE_ALT = StockModelShapes.makeBlockModel("template_fire_side_alt", StockTextureAliases.FIRE);
    public static final ModelsUtil TEMPLATE_FIRE_UP = StockModelShapes.makeBlockModel("template_fire_up", StockTextureAliases.FIRE);
    public static final ModelsUtil TEMPLATE_FIRE_UP_ALT = StockModelShapes.makeBlockModel("template_fire_up_alt", StockTextureAliases.FIRE);
    public static final ModelsUtil TEMPLATE_CAMPFIRE = StockModelShapes.makeBlockModel("template_campfire", StockTextureAliases.FIRE, StockTextureAliases.LIT_LOG);
    public static final ModelsUtil TEMPLATE_LANTERN = StockModelShapes.makeBlockModel("template_lantern", StockTextureAliases.LANTERN);
    public static final ModelsUtil TEMPLATE_HANGING_LANTERN = StockModelShapes.makeBlockModel("template_hanging_lantern", "_hanging", StockTextureAliases.LANTERN);
    public static final ModelsUtil TEMPLATE_TORCH = StockModelShapes.makeBlockModel("template_torch", StockTextureAliases.TORCH);
    public static final ModelsUtil TEMPLATE_TORCH_WALL = StockModelShapes.makeBlockModel("template_torch_wall", StockTextureAliases.TORCH);
    public static final ModelsUtil TEMPLATE_PISTON = StockModelShapes.makeBlockModel("template_piston", StockTextureAliases.PLATFORM, StockTextureAliases.BOTTOM, StockTextureAliases.SIDE);
    public static final ModelsUtil TEMPLATE_PISTON_HEAD = StockModelShapes.makeBlockModel("template_piston_head", StockTextureAliases.PLATFORM, StockTextureAliases.SIDE, StockTextureAliases.UNSTICKY);
    public static final ModelsUtil TEMPLATE_PISTON_HEAD_SHORT = StockModelShapes.makeBlockModel("template_piston_head_short", StockTextureAliases.PLATFORM, StockTextureAliases.SIDE, StockTextureAliases.UNSTICKY);
    public static final ModelsUtil TEMPLATE_SEAGRASS = StockModelShapes.makeBlockModel("template_seagrass", StockTextureAliases.TEXTURE);
    public static final ModelsUtil TEMPLATE_TURTLE_EGG = StockModelShapes.makeBlockModel("template_turtle_egg", StockTextureAliases.ALL);
    public static final ModelsUtil TEMPLATE_TWO_TURTLE_EGGS = StockModelShapes.makeBlockModel("template_two_turtle_eggs", StockTextureAliases.ALL);
    public static final ModelsUtil TEMPLATE_THREE_TURTLE_EGGS = StockModelShapes.makeBlockModel("template_three_turtle_eggs", StockTextureAliases.ALL);
    public static final ModelsUtil TEMPLATE_FOUR_TURTLE_EGGS = StockModelShapes.makeBlockModel("template_four_turtle_eggs", StockTextureAliases.ALL);
    public static final ModelsUtil TEMPLATE_SINGLE_FACE = StockModelShapes.makeBlockModel("template_single_face", StockTextureAliases.TEXTURE);
    public static final ModelsUtil GENERATED = StockModelShapes.makeItemModel("generated", StockTextureAliases.LAYER_ZERO);
    public static final ModelsUtil HANDHELD = StockModelShapes.makeItemModel("handheld", StockTextureAliases.LAYER_ZERO);
    public static final ModelsUtil HANDHELD_ROD = StockModelShapes.makeItemModel("handheld_rod", StockTextureAliases.LAYER_ZERO);
    public static final ModelsUtil TEMPLATE_SHULKER_BOX = StockModelShapes.makeItemModel("template_shulker_box", StockTextureAliases.PARTICLE);
    public static final ModelsUtil TEMPLATE_BED = StockModelShapes.makeItemModel("template_bed", StockTextureAliases.PARTICLE);
    public static final ModelsUtil TEMPLATE_BANNER = StockModelShapes.makeItemModel("template_banner", new StockTextureAliases[0]);
    public static final ModelsUtil TEMPLATE_SKULL = StockModelShapes.makeItemModel("template_skull", new StockTextureAliases[0]);

    private static ModelsUtil makeEmptyModel(StockTextureAliases ... stockTextureAliasesArray) {
        return new ModelsUtil(Optional.empty(), Optional.empty(), stockTextureAliasesArray);
    }

    private static ModelsUtil makeBlockModel(String string, StockTextureAliases ... stockTextureAliasesArray) {
        return new ModelsUtil(Optional.of(new ResourceLocation("minecraft", "block/" + string)), Optional.empty(), stockTextureAliasesArray);
    }

    private static ModelsUtil makeItemModel(String string, StockTextureAliases ... stockTextureAliasesArray) {
        return new ModelsUtil(Optional.of(new ResourceLocation("minecraft", "item/" + string)), Optional.empty(), stockTextureAliasesArray);
    }

    private static ModelsUtil makeBlockModel(String string, String string2, StockTextureAliases ... stockTextureAliasesArray) {
        return new ModelsUtil(Optional.of(new ResourceLocation("minecraft", "block/" + string)), Optional.of(string2), stockTextureAliasesArray);
    }

    private static ModelsUtil[] lambda$static$1(int n) {
        return new ModelsUtil[n];
    }

    private static ModelsUtil lambda$static$0(int n) {
        return StockModelShapes.makeBlockModel("stem_growth" + n, "_stage" + n, StockTextureAliases.STEM);
    }
}


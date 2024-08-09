/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallHeight;
import net.minecraft.data.BlockModelDefinition;
import net.minecraft.data.BlockModelFields;
import net.minecraft.data.BlockModelWriter;
import net.minecraft.data.BlockStateVariantBuilder;
import net.minecraft.data.FinishedMultiPartBlockState;
import net.minecraft.data.FinishedVariantBlockState;
import net.minecraft.data.IFinishedBlockState;
import net.minecraft.data.IMultiPartPredicateBuilder;
import net.minecraft.data.ModelTextures;
import net.minecraft.data.ModelsResourceUtil;
import net.minecraft.data.ModelsUtil;
import net.minecraft.data.StockModelShapes;
import net.minecraft.data.StockTextureAliases;
import net.minecraft.data.TexturedModel;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BambooLeaves;
import net.minecraft.state.properties.BellAttachment;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ComparatorMode;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.PistonType;
import net.minecraft.state.properties.RailShape;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawOrientation;

public class BlockModelProvider {
    private final Consumer<IFinishedBlockState> field_239834_a_;
    private final BiConsumer<ResourceLocation, Supplier<JsonElement>> field_239835_b_;
    private final Consumer<Item> field_239836_c_;

    public BlockModelProvider(Consumer<IFinishedBlockState> consumer, BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer, Consumer<Item> consumer2) {
        this.field_239834_a_ = consumer;
        this.field_239835_b_ = biConsumer;
        this.field_239836_c_ = consumer2;
    }

    private void func_239869_a_(Block block) {
        this.field_239836_c_.accept(block.asItem());
    }

    private void func_239957_c_(Block block, ResourceLocation resourceLocation) {
        this.field_239835_b_.accept(ModelsResourceUtil.func_240219_a_(block.asItem()), new BlockModelWriter(resourceLocation));
    }

    private void func_239867_a_(Item item, ResourceLocation resourceLocation) {
        this.field_239835_b_.accept(ModelsResourceUtil.func_240219_a_(item), new BlockModelWriter(resourceLocation));
    }

    private void func_239866_a_(Item item) {
        StockModelShapes.GENERATED.func_240234_a_(ModelsResourceUtil.func_240219_a_(item), ModelTextures.func_240352_b_(item), this.field_239835_b_);
    }

    private void func_239934_b_(Block block) {
        Item item = block.asItem();
        if (item != Items.AIR) {
            StockModelShapes.GENERATED.func_240234_a_(ModelsResourceUtil.func_240219_a_(item), ModelTextures.func_240340_B_(block), this.field_239835_b_);
        }
    }

    private void func_239885_a_(Block block, String string) {
        Item item = block.asItem();
        StockModelShapes.GENERATED.func_240234_a_(ModelsResourceUtil.func_240219_a_(item), ModelTextures.func_240376_j_(ModelTextures.func_240347_a_(block, string)), this.field_239835_b_);
    }

    private static BlockStateVariantBuilder func_239933_b_() {
        return BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.HORIZONTAL_FACING).func_240143_a_(Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240143_a_(Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240143_a_(Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240143_a_(Direction.NORTH, BlockModelDefinition.getNewModelDefinition());
    }

    private static BlockStateVariantBuilder func_239952_c_() {
        return BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.HORIZONTAL_FACING).func_240143_a_(Direction.SOUTH, BlockModelDefinition.getNewModelDefinition()).func_240143_a_(Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240143_a_(Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240143_a_(Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270));
    }

    private static BlockStateVariantBuilder func_239964_d_() {
        return BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.HORIZONTAL_FACING).func_240143_a_(Direction.EAST, BlockModelDefinition.getNewModelDefinition()).func_240143_a_(Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240143_a_(Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240143_a_(Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270));
    }

    private static BlockStateVariantBuilder func_239974_e_() {
        return BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.FACING).func_240143_a_(Direction.DOWN, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90)).func_240143_a_(Direction.UP, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270)).func_240143_a_(Direction.NORTH, BlockModelDefinition.getNewModelDefinition()).func_240143_a_(Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240143_a_(Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240143_a_(Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90));
    }

    private static FinishedVariantBlockState func_239968_d_(Block block, ResourceLocation resourceLocation) {
        return FinishedVariantBlockState.func_240121_a_(block, BlockModelProvider.func_239915_a_(resourceLocation));
    }

    private static BlockModelDefinition[] func_239915_a_(ResourceLocation resourceLocation) {
        return new BlockModelDefinition[]{BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)};
    }

    private static FinishedVariantBlockState func_239979_e_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return FinishedVariantBlockState.func_240121_a_(block, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180));
    }

    private static BlockStateVariantBuilder func_239894_a_(BooleanProperty booleanProperty, ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return BlockStateVariantBuilder.func_240133_a_(booleanProperty).func_240143_a_(true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240143_a_(false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2));
    }

    private void func_239953_c_(Block block) {
        ResourceLocation resourceLocation = TexturedModel.field_240434_a_.func_240466_a_(block, this.field_239835_b_);
        ResourceLocation resourceLocation2 = TexturedModel.field_240435_b_.func_240466_a_(block, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239979_e_(block, resourceLocation, resourceLocation2));
    }

    private void func_239965_d_(Block block) {
        ResourceLocation resourceLocation = TexturedModel.field_240434_a_.func_240466_a_(block, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239968_d_(block, resourceLocation));
    }

    private static IFinishedBlockState func_239987_f_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.POWERED).func_240143_a_(false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240143_a_(true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2))).func_240125_a_(BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.FACE, BlockStateProperties.HORIZONTAL_FACING).func_240149_a_(AttachFace.FLOOR, Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(AttachFace.FLOOR, Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240149_a_(AttachFace.FLOOR, Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.FLOOR, Direction.NORTH, BlockModelDefinition.getNewModelDefinition()).func_240149_a_(AttachFace.WALL, Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240149_a_(AttachFace.WALL, Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240149_a_(AttachFace.WALL, Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240149_a_(AttachFace.WALL, Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240149_a_(AttachFace.CEILING, Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.CEILING, Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.CEILING, Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.CEILING, Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180)));
    }

    private static BlockStateVariantBuilder.Four<Direction, DoubleBlockHalf, DoorHingeSide, Boolean> func_239903_a_(BlockStateVariantBuilder.Four<Direction, DoubleBlockHalf, DoorHingeSide, Boolean> four, DoubleBlockHalf doubleBlockHalf, ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return four.func_240170_a_(Direction.EAST, doubleBlockHalf, DoorHingeSide.LEFT, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240170_a_(Direction.SOUTH, doubleBlockHalf, DoorHingeSide.LEFT, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240170_a_(Direction.WEST, doubleBlockHalf, DoorHingeSide.LEFT, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240170_a_(Direction.NORTH, doubleBlockHalf, DoorHingeSide.LEFT, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240170_a_(Direction.EAST, doubleBlockHalf, DoorHingeSide.RIGHT, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240170_a_(Direction.SOUTH, doubleBlockHalf, DoorHingeSide.RIGHT, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240170_a_(Direction.WEST, doubleBlockHalf, DoorHingeSide.RIGHT, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240170_a_(Direction.NORTH, doubleBlockHalf, DoorHingeSide.RIGHT, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240170_a_(Direction.EAST, doubleBlockHalf, DoorHingeSide.LEFT, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240170_a_(Direction.SOUTH, doubleBlockHalf, DoorHingeSide.LEFT, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240170_a_(Direction.WEST, doubleBlockHalf, DoorHingeSide.LEFT, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240170_a_(Direction.NORTH, doubleBlockHalf, DoorHingeSide.LEFT, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240170_a_(Direction.EAST, doubleBlockHalf, DoorHingeSide.RIGHT, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240170_a_(Direction.SOUTH, doubleBlockHalf, DoorHingeSide.RIGHT, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240170_a_(Direction.WEST, doubleBlockHalf, DoorHingeSide.RIGHT, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240170_a_(Direction.NORTH, doubleBlockHalf, DoorHingeSide.RIGHT, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180));
    }

    private static IFinishedBlockState func_239943_b_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3, ResourceLocation resourceLocation4) {
        return FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockModelProvider.func_239903_a_(BlockModelProvider.func_239903_a_(BlockStateVariantBuilder.func_240136_a_(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.DOUBLE_BLOCK_HALF, BlockStateProperties.DOOR_HINGE, BlockStateProperties.OPEN), DoubleBlockHalf.LOWER, resourceLocation, resourceLocation2), DoubleBlockHalf.UPPER, resourceLocation3, resourceLocation4));
    }

    private static IFinishedBlockState func_239994_g_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return FinishedMultiPartBlockState.func_240106_a_(block).func_240111_a_(BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.EAST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.SOUTH, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WEST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true));
    }

    private static IFinishedBlockState func_239970_d_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3) {
        return FinishedMultiPartBlockState.func_240106_a_(block).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.UP, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WALL_HEIGHT_NORTH, WallHeight.LOW), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WALL_HEIGHT_EAST, WallHeight.LOW), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WALL_HEIGHT_SOUTH, WallHeight.LOW), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WALL_HEIGHT_WEST, WallHeight.LOW), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WALL_HEIGHT_NORTH, WallHeight.TALL), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WALL_HEIGHT_EAST, WallHeight.TALL), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WALL_HEIGHT_SOUTH, WallHeight.TALL), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WALL_HEIGHT_WEST, WallHeight.TALL), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true));
    }

    private static IFinishedBlockState func_239960_c_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3, ResourceLocation resourceLocation4) {
        return FinishedVariantBlockState.func_240120_a_(block, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240125_a_(BlockModelProvider.func_239952_c_()).func_240125_a_(BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.IN_WALL, BlockStateProperties.OPEN).func_240149_a_(false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240149_a_(true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4)).func_240149_a_(false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240149_a_(true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)));
    }

    private static IFinishedBlockState func_239980_e_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3) {
        return FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240135_a_(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.STAIRS_SHAPE).func_240161_a_(Direction.EAST, Half.BOTTOM, StairsShape.STRAIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240161_a_(Direction.WEST, Half.BOTTOM, StairsShape.STRAIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.SOUTH, Half.BOTTOM, StairsShape.STRAIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.NORTH, Half.BOTTOM, StairsShape.STRAIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)).func_240161_a_(Direction.WEST, Half.BOTTOM, StairsShape.OUTER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.WEST, Half.BOTTOM, StairsShape.OUTER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)).func_240161_a_(Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.EAST, Half.BOTTOM, StairsShape.INNER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240161_a_(Direction.WEST, Half.BOTTOM, StairsShape.INNER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.NORTH, Half.BOTTOM, StairsShape.INNER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.EAST, Half.BOTTOM, StairsShape.INNER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.WEST, Half.BOTTOM, StairsShape.INNER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240161_a_(Direction.NORTH, Half.BOTTOM, StairsShape.INNER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.EAST, Half.TOP, StairsShape.STRAIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.WEST, Half.TOP, StairsShape.STRAIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.SOUTH, Half.TOP, StairsShape.STRAIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.NORTH, Half.TOP, StairsShape.STRAIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.EAST, Half.TOP, StairsShape.OUTER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.WEST, Half.TOP, StairsShape.OUTER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.SOUTH, Half.TOP, StairsShape.OUTER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.NORTH, Half.TOP, StairsShape.OUTER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.EAST, Half.TOP, StairsShape.OUTER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.WEST, Half.TOP, StairsShape.OUTER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.SOUTH, Half.TOP, StairsShape.OUTER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.NORTH, Half.TOP, StairsShape.OUTER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.EAST, Half.TOP, StairsShape.INNER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.WEST, Half.TOP, StairsShape.INNER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.SOUTH, Half.TOP, StairsShape.INNER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.NORTH, Half.TOP, StairsShape.INNER_RIGHT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.EAST, Half.TOP, StairsShape.INNER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.WEST, Half.TOP, StairsShape.INNER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.SOUTH, Half.TOP, StairsShape.INNER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240161_a_(Direction.NORTH, Half.TOP, StairsShape.INNER_LEFT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)));
    }

    private static IFinishedBlockState func_239988_f_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3) {
        return FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240135_a_(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.OPEN).func_240161_a_(Direction.NORTH, Half.BOTTOM, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240161_a_(Direction.SOUTH, Half.BOTTOM, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240161_a_(Direction.EAST, Half.BOTTOM, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240161_a_(Direction.WEST, Half.BOTTOM, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240161_a_(Direction.NORTH, Half.TOP, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240161_a_(Direction.SOUTH, Half.TOP, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240161_a_(Direction.EAST, Half.TOP, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240161_a_(Direction.WEST, Half.TOP, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240161_a_(Direction.NORTH, Half.BOTTOM, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)).func_240161_a_(Direction.SOUTH, Half.BOTTOM, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240161_a_(Direction.EAST, Half.BOTTOM, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240161_a_(Direction.WEST, Half.BOTTOM, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240161_a_(Direction.NORTH, Half.TOP, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240161_a_(Direction.SOUTH, Half.TOP, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R0)).func_240161_a_(Direction.EAST, Half.TOP, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240161_a_(Direction.WEST, Half.TOP, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)));
    }

    private static IFinishedBlockState func_239995_g_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3) {
        return FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240135_a_(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.OPEN).func_240161_a_(Direction.NORTH, Half.BOTTOM, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240161_a_(Direction.SOUTH, Half.BOTTOM, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240161_a_(Direction.EAST, Half.BOTTOM, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240161_a_(Direction.WEST, Half.BOTTOM, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240161_a_(Direction.NORTH, Half.TOP, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240161_a_(Direction.SOUTH, Half.TOP, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240161_a_(Direction.EAST, Half.TOP, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240161_a_(Direction.WEST, Half.TOP, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240161_a_(Direction.NORTH, Half.BOTTOM, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)).func_240161_a_(Direction.SOUTH, Half.BOTTOM, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240161_a_(Direction.EAST, Half.BOTTOM, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240161_a_(Direction.WEST, Half.BOTTOM, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240161_a_(Direction.NORTH, Half.TOP, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)).func_240161_a_(Direction.SOUTH, Half.TOP, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240161_a_(Direction.EAST, Half.TOP, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240161_a_(Direction.WEST, Half.TOP, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)));
    }

    private static FinishedVariantBlockState func_239978_e_(Block block, ResourceLocation resourceLocation) {
        return FinishedVariantBlockState.func_240120_a_(block, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation));
    }

    private static BlockStateVariantBuilder func_239983_f_() {
        return BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.AXIS).func_240143_a_(Direction.Axis.Y, BlockModelDefinition.getNewModelDefinition()).func_240143_a_(Direction.Axis.Z, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90)).func_240143_a_(Direction.Axis.X, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90));
    }

    private static IFinishedBlockState func_239986_f_(Block block, ResourceLocation resourceLocation) {
        return FinishedVariantBlockState.func_240120_a_(block, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240125_a_(BlockModelProvider.func_239983_f_());
    }

    private void func_243685_g(Block block, ResourceLocation resourceLocation) {
        this.field_239834_a_.accept(BlockModelProvider.func_239986_f_(block, resourceLocation));
    }

    private void func_239882_a_(Block block, TexturedModel.ISupplier iSupplier) {
        ResourceLocation resourceLocation = iSupplier.func_240466_a_(block, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239986_f_(block, resourceLocation));
    }

    private void func_239939_b_(Block block, TexturedModel.ISupplier iSupplier) {
        ResourceLocation resourceLocation = iSupplier.func_240466_a_(block, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(block, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240125_a_(BlockModelProvider.func_239933_b_()));
    }

    private static IFinishedBlockState func_240000_h_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.AXIS).func_240143_a_(Direction.Axis.Y, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240143_a_(Direction.Axis.Z, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90)).func_240143_a_(Direction.Axis.X, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)));
    }

    private void func_239883_a_(Block block, TexturedModel.ISupplier iSupplier, TexturedModel.ISupplier iSupplier2) {
        ResourceLocation resourceLocation = iSupplier.func_240466_a_(block, this.field_239835_b_);
        ResourceLocation resourceLocation2 = iSupplier2.func_240466_a_(block, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_240000_h_(block, resourceLocation, resourceLocation2));
    }

    private ResourceLocation func_239886_a_(Block block, String string, ModelsUtil modelsUtil, Function<ResourceLocation, ModelTextures> function) {
        return modelsUtil.func_240229_a_(block, string, function.apply(ModelTextures.func_240347_a_(block, string)), this.field_239835_b_);
    }

    private static IFinishedBlockState func_240006_i_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.POWERED, resourceLocation2, resourceLocation));
    }

    private static IFinishedBlockState func_240001_h_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3) {
        return FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.SLAB_TYPE).func_240143_a_(SlabType.BOTTOM, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240143_a_(SlabType.TOP, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240143_a_(SlabType.DOUBLE, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)));
    }

    private void func_239975_e_(Block block) {
        this.func_239956_c_(block, TexturedModel.field_240434_a_);
    }

    private void func_239956_c_(Block block, TexturedModel.ISupplier iSupplier) {
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, iSupplier.func_240466_a_(block, this.field_239835_b_)));
    }

    private void func_239880_a_(Block block, ModelTextures modelTextures, ModelsUtil modelsUtil) {
        ResourceLocation resourceLocation = modelsUtil.func_240228_a_(block, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, resourceLocation));
    }

    private BlockTextureCombiner func_239884_a_(Block block, TexturedModel texturedModel) {
        return new BlockTextureCombiner(this, texturedModel.func_240464_b_()).func_240058_a_(block, texturedModel.func_240457_a_());
    }

    private BlockTextureCombiner func_239967_d_(Block block, TexturedModel.ISupplier iSupplier) {
        TexturedModel texturedModel = iSupplier.get(block);
        return new BlockTextureCombiner(this, texturedModel.func_240464_b_()).func_240058_a_(block, texturedModel.func_240457_a_());
    }

    private BlockTextureCombiner func_239984_f_(Block block) {
        return this.func_239967_d_(block, TexturedModel.field_240434_a_);
    }

    private BlockTextureCombiner func_239905_a_(ModelTextures modelTextures) {
        return new BlockTextureCombiner(this, modelTextures);
    }

    private void func_239991_g_(Block block) {
        ModelTextures modelTextures = ModelTextures.func_240382_p_(block);
        ResourceLocation resourceLocation = StockModelShapes.DOOR_BOTTOM.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.DOOR_BOTTOM_RH.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation3 = StockModelShapes.DOOR_TOP.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation4 = StockModelShapes.DOOR_TOP_RH.func_240228_a_(block, modelTextures, this.field_239835_b_);
        this.func_239866_a_(block.asItem());
        this.field_239834_a_.accept(BlockModelProvider.func_239943_b_(block, resourceLocation, resourceLocation2, resourceLocation3, resourceLocation4));
    }

    private void func_239998_h_(Block block) {
        ModelTextures modelTextures = ModelTextures.func_240353_b_(block);
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_ORIENTABLE_TRAPDOOR_TOP.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_ORIENTABLE_TRAPDOOR_BOTTOM.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation3 = StockModelShapes.TEMPLATE_ORIENTABLE_TRAPDOOR_OPEN.func_240228_a_(block, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239988_f_(block, resourceLocation, resourceLocation2, resourceLocation3));
        this.func_239957_c_(block, resourceLocation2);
    }

    private void func_240004_i_(Block block) {
        ModelTextures modelTextures = ModelTextures.func_240353_b_(block);
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_TRAPDOOR_TOP.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_TRAPDOOR_BOTTOM.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation3 = StockModelShapes.TEMPLATE_TRAPDOOR_OPEN.func_240228_a_(block, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239995_g_(block, resourceLocation, resourceLocation2, resourceLocation3));
        this.func_239957_c_(block, resourceLocation2);
    }

    private LogsVariantHelper func_240009_j_(Block block) {
        return new LogsVariantHelper(this, ModelTextures.func_240378_l_(block));
    }

    private void func_240014_k_(Block block) {
        this.func_239872_a_(block, block);
    }

    private void func_239872_a_(Block block, Block block2) {
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, ModelsResourceUtil.func_240221_a_(block2)));
    }

    private void func_239877_a_(Block block, TintMode tintMode) {
        this.func_239934_b_(block);
        this.func_239937_b_(block, tintMode);
    }

    private void func_239878_a_(Block block, TintMode tintMode, ModelTextures modelTextures) {
        this.func_239934_b_(block);
        this.func_239938_b_(block, tintMode, modelTextures);
    }

    private void func_239937_b_(Block block, TintMode tintMode) {
        ModelTextures modelTextures = ModelTextures.func_240358_c_(block);
        this.func_239938_b_(block, tintMode, modelTextures);
    }

    private void func_239938_b_(Block block, TintMode tintMode, ModelTextures modelTextures) {
        ResourceLocation resourceLocation = tintMode.func_240066_a_().func_240228_a_(block, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, resourceLocation));
    }

    private void func_239874_a_(Block block, Block block2, TintMode tintMode) {
        this.func_239877_a_(block, tintMode);
        ModelTextures modelTextures = ModelTextures.func_240362_d_(block);
        ResourceLocation resourceLocation = tintMode.func_240067_b_().func_240228_a_(block2, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block2, resourceLocation));
    }

    private void func_239935_b_(Block block, Block block2) {
        TexturedModel texturedModel = TexturedModel.field_240444_k_.get(block);
        ResourceLocation resourceLocation = texturedModel.func_240459_a_(block, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, resourceLocation));
        ResourceLocation resourceLocation2 = StockModelShapes.CORAL_WALL_FAN.func_240228_a_(block2, texturedModel.func_240464_b_(), this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(block2, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240125_a_(BlockModelProvider.func_239933_b_()));
        this.func_239934_b_(block);
    }

    private void func_239954_c_(Block block, Block block2) {
        this.func_239866_a_(block.asItem());
        ModelTextures modelTextures = ModelTextures.func_240369_g_(block);
        ModelTextures modelTextures2 = ModelTextures.func_240346_a_(block, block2);
        ResourceLocation resourceLocation = StockModelShapes.STEM_FRUIT.func_240228_a_(block2, modelTextures2, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(block2, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.HORIZONTAL_FACING).func_240143_a_(Direction.WEST, BlockModelDefinition.getNewModelDefinition()).func_240143_a_(Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240143_a_(Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240143_a_(Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180))));
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.AGE_0_7).func_240145_a_(arg_0 -> this.lambda$func_239954_c_$0(block, modelTextures, arg_0))));
    }

    private void func_239873_a_(Block block, Block block2, Block block3, Block block4, Block block5, Block block6, Block block7, Block block8) {
        this.func_239877_a_(block, TintMode.NOT_TINTED);
        this.func_239877_a_(block2, TintMode.NOT_TINTED);
        this.func_239975_e_(block3);
        this.func_239975_e_(block4);
        this.func_239935_b_(block5, block7);
        this.func_239935_b_(block6, block8);
    }

    private void func_239955_c_(Block block, TintMode tintMode) {
        this.func_239885_a_(block, "_top");
        ResourceLocation resourceLocation = this.func_239886_a_(block, "_top", tintMode.func_240066_a_(), ModelTextures::func_240361_c_);
        ResourceLocation resourceLocation2 = this.func_239886_a_(block, "_bottom", tintMode.func_240066_a_(), ModelTextures::func_240361_c_);
        this.func_240011_j_(block, resourceLocation, resourceLocation2);
    }

    private void func_239990_g_() {
        this.func_239885_a_(Blocks.SUNFLOWER, "_front");
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240222_a_(Blocks.SUNFLOWER, "_top");
        ResourceLocation resourceLocation2 = this.func_239886_a_(Blocks.SUNFLOWER, "_bottom", TintMode.NOT_TINTED.func_240066_a_(), ModelTextures::func_240361_c_);
        this.func_240011_j_(Blocks.SUNFLOWER, resourceLocation, resourceLocation2);
    }

    private void func_239997_h_() {
        ResourceLocation resourceLocation = this.func_239886_a_(Blocks.TALL_SEAGRASS, "_top", StockModelShapes.TEMPLATE_SEAGRASS, ModelTextures::func_240350_a_);
        ResourceLocation resourceLocation2 = this.func_239886_a_(Blocks.TALL_SEAGRASS, "_bottom", StockModelShapes.TEMPLATE_SEAGRASS, ModelTextures::func_240350_a_);
        this.func_240011_j_(Blocks.TALL_SEAGRASS, resourceLocation, resourceLocation2);
    }

    private void func_240011_j_(Block block, ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.DOUBLE_BLOCK_HALF).func_240143_a_(DoubleBlockHalf.LOWER, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240143_a_(DoubleBlockHalf.UPPER, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation))));
    }

    private void func_240018_l_(Block block) {
        ModelTextures modelTextures = ModelTextures.func_240366_e_(block);
        ModelTextures modelTextures2 = ModelTextures.func_240367_e_(ModelTextures.func_240347_a_(block, "_corner"));
        ResourceLocation resourceLocation = StockModelShapes.RAIL_FLAT.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.RAIL_CURVED.func_240228_a_(block, modelTextures2, this.field_239835_b_);
        ResourceLocation resourceLocation3 = StockModelShapes.TEMPLATE_RAIL_RAISED_NE.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation4 = StockModelShapes.TEMPLATE_RAIL_RAISED_SW.func_240228_a_(block, modelTextures, this.field_239835_b_);
        this.func_239934_b_(block);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.RAIL_SHAPE).func_240143_a_(RailShape.NORTH_SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240143_a_(RailShape.EAST_WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240143_a_(RailShape.ASCENDING_EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240143_a_(RailShape.ASCENDING_WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240143_a_(RailShape.ASCENDING_NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)).func_240143_a_(RailShape.ASCENDING_SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4)).func_240143_a_(RailShape.SOUTH_EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240143_a_(RailShape.SOUTH_WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240143_a_(RailShape.NORTH_WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240143_a_(RailShape.NORTH_EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270))));
    }

    private void func_240021_m_(Block block) {
        ResourceLocation resourceLocation = this.func_239886_a_(block, "", StockModelShapes.RAIL_FLAT, ModelTextures::func_240367_e_);
        ResourceLocation resourceLocation2 = this.func_239886_a_(block, "", StockModelShapes.TEMPLATE_RAIL_RAISED_NE, ModelTextures::func_240367_e_);
        ResourceLocation resourceLocation3 = this.func_239886_a_(block, "", StockModelShapes.TEMPLATE_RAIL_RAISED_SW, ModelTextures::func_240367_e_);
        ResourceLocation resourceLocation4 = this.func_239886_a_(block, "_on", StockModelShapes.RAIL_FLAT, ModelTextures::func_240367_e_);
        ResourceLocation resourceLocation5 = this.func_239886_a_(block, "_on", StockModelShapes.TEMPLATE_RAIL_RAISED_NE, ModelTextures::func_240367_e_);
        ResourceLocation resourceLocation6 = this.func_239886_a_(block, "_on", StockModelShapes.TEMPLATE_RAIL_RAISED_SW, ModelTextures::func_240367_e_);
        BlockStateVariantBuilder blockStateVariantBuilder = BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.POWERED, BlockStateProperties.RAIL_SHAPE_STRAIGHT).func_240152_a_((arg_0, arg_1) -> BlockModelProvider.lambda$func_240021_m_$1(resourceLocation4, resourceLocation, resourceLocation5, resourceLocation2, resourceLocation6, resourceLocation3, arg_0, arg_1));
        this.func_239934_b_(block);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(blockStateVariantBuilder));
    }

    private BreakParticleHelper func_239916_a_(ResourceLocation resourceLocation, Block block) {
        return new BreakParticleHelper(this, resourceLocation, block);
    }

    private BreakParticleHelper func_239966_d_(Block block, Block block2) {
        return new BreakParticleHelper(this, ModelsResourceUtil.func_240221_a_(block), block2);
    }

    private void func_239871_a_(Block block, Item item) {
        ResourceLocation resourceLocation = StockModelShapes.PARTICLE.func_240228_a_(block, ModelTextures.func_240343_a_(item), this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, resourceLocation));
    }

    private void func_239993_g_(Block block, ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = StockModelShapes.PARTICLE.func_240228_a_(block, ModelTextures.func_240372_h_(resourceLocation), this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, resourceLocation2));
    }

    private void func_239976_e_(Block block, Block block2) {
        this.func_239956_c_(block, TexturedModel.field_240434_a_);
        ResourceLocation resourceLocation = TexturedModel.field_240442_i_.get(block).func_240459_a_(block2, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block2, resourceLocation));
    }

    private void func_239907_a_(TexturedModel.ISupplier iSupplier, Block ... blockArray) {
        for (Block block : blockArray) {
            ResourceLocation resourceLocation = iSupplier.func_240466_a_(block, this.field_239835_b_);
            this.field_239834_a_.accept(BlockModelProvider.func_239968_d_(block, resourceLocation));
        }
    }

    private void func_239948_b_(TexturedModel.ISupplier iSupplier, Block ... blockArray) {
        for (Block block : blockArray) {
            ResourceLocation resourceLocation = iSupplier.func_240466_a_(block, this.field_239835_b_);
            this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(block, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240125_a_(BlockModelProvider.func_239952_c_()));
        }
    }

    private void func_239985_f_(Block block, Block block2) {
        this.func_239975_e_(block);
        ModelTextures modelTextures = ModelTextures.func_240354_b_(block, block2);
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_GLASS_PANE_POST.func_240228_a_(block2, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_GLASS_PANE_SIDE.func_240228_a_(block2, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation3 = StockModelShapes.TEMPLATE_GLASS_PANE_SIDE_ALT.func_240228_a_(block2, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation4 = StockModelShapes.TEMPLATE_GLASS_PANE_NOSIDE.func_240228_a_(block2, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation5 = StockModelShapes.TEMPLATE_GLASS_PANE_NOSIDE_ALT.func_240228_a_(block2, modelTextures, this.field_239835_b_);
        Item item = block2.asItem();
        StockModelShapes.GENERATED.func_240234_a_(ModelsResourceUtil.func_240219_a_(item), ModelTextures.func_240340_B_(block), this.field_239835_b_);
        this.field_239834_a_.accept(FinishedMultiPartBlockState.func_240106_a_(block2).func_240111_a_(BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.EAST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.SOUTH, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WEST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.EAST, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.SOUTH, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WEST, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)));
    }

    private void func_240023_n_(Block block) {
        ModelTextures modelTextures = ModelTextures.func_240388_v_(block);
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_COMMAND_BLOCK.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = this.func_239886_a_(block, "_conditional", StockModelShapes.TEMPLATE_COMMAND_BLOCK, arg_0 -> BlockModelProvider.lambda$func_240023_n_$2(modelTextures, arg_0));
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.CONDITIONAL, resourceLocation2, resourceLocation)).func_240125_a_(BlockModelProvider.func_239974_e_()));
    }

    private void func_240025_o_(Block block) {
        ResourceLocation resourceLocation = TexturedModel.field_240446_m_.func_240466_a_(block, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, resourceLocation).func_240125_a_(BlockModelProvider.func_239952_c_()));
    }

    private List<BlockModelDefinition> func_239864_a_(int n) {
        String string = "_age" + n;
        return IntStream.range(1, 5).mapToObj(arg_0 -> BlockModelProvider.lambda$func_239864_a_$3(string, arg_0)).collect(Collectors.toList());
    }

    private void func_240003_i_() {
        this.func_239869_a_(Blocks.BAMBOO);
        this.field_239834_a_.accept(FinishedMultiPartBlockState.func_240106_a_(Blocks.BAMBOO).func_240109_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.AGE_0_1, 0), this.func_239864_a_(0)).func_240109_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.AGE_0_1, 1), this.func_239864_a_(1)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.BAMBOO_LEAVES, BambooLeaves.SMALL), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.BAMBOO, "_small_leaves"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.BAMBOO_LEAVES, BambooLeaves.LARGE), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.BAMBOO, "_large_leaves"))));
    }

    private BlockStateVariantBuilder func_240008_j_() {
        return BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.FACING).func_240143_a_(Direction.DOWN, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180)).func_240143_a_(Direction.UP, BlockModelDefinition.getNewModelDefinition()).func_240143_a_(Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90)).func_240143_a_(Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240143_a_(Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240143_a_(Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90));
    }

    private void func_240013_k_() {
        ResourceLocation resourceLocation = ModelTextures.func_240347_a_(Blocks.BARREL, "_top_open");
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.BARREL).func_240125_a_(this.func_240008_j_()).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.OPEN).func_240143_a_(false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, TexturedModel.field_240438_e_.func_240466_a_(Blocks.BARREL, this.field_239835_b_))).func_240143_a_(true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, TexturedModel.field_240438_e_.get(Blocks.BARREL).func_240460_a_(arg_0 -> BlockModelProvider.lambda$func_240013_k_$4(resourceLocation, arg_0)).func_240458_a_(Blocks.BARREL, "_open", this.field_239835_b_)))));
    }

    private static <T extends Comparable<T>> BlockStateVariantBuilder func_239895_a_(Property<T> property, T t, ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        BlockModelDefinition blockModelDefinition = BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation);
        BlockModelDefinition blockModelDefinition2 = BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2);
        return BlockStateVariantBuilder.func_240133_a_(property).func_240145_a_(arg_0 -> BlockModelProvider.lambda$func_239895_a_$5(t, blockModelDefinition, blockModelDefinition2, arg_0));
    }

    private void func_239887_a_(Block block, Function<Block, ModelTextures> function) {
        ModelTextures modelTextures = function.apply(block).func_240355_b_(StockTextureAliases.SIDE, StockTextureAliases.PARTICLE);
        ModelTextures modelTextures2 = modelTextures.func_240360_c_(StockTextureAliases.FRONT, ModelTextures.func_240347_a_(block, "_front_honey"));
        ResourceLocation resourceLocation = StockModelShapes.ORIENTABLE_WITH_BOTTOM.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.ORIENTABLE_WITH_BOTTOM.func_240229_a_(block, "_honey", modelTextures2, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockModelProvider.func_239933_b_()).func_240125_a_(BlockModelProvider.func_239895_a_(BlockStateProperties.HONEY_LEVEL, 5, resourceLocation2, resourceLocation)));
    }

    private void func_239876_a_(Block block, Property<Integer> property, int ... nArray) {
        if (property.getAllowedValues().size() != nArray.length) {
            throw new IllegalArgumentException();
        }
        Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap();
        BlockStateVariantBuilder blockStateVariantBuilder = BlockStateVariantBuilder.func_240133_a_(property).func_240145_a_(arg_0 -> this.lambda$func_239876_a_$7(nArray, int2ObjectOpenHashMap, block, arg_0));
        this.func_239866_a_(block.asItem());
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(blockStateVariantBuilder));
    }

    private void func_240017_l_() {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240222_a_(Blocks.BELL, "_floor");
        ResourceLocation resourceLocation2 = ModelsResourceUtil.func_240222_a_(Blocks.BELL, "_ceiling");
        ResourceLocation resourceLocation3 = ModelsResourceUtil.func_240222_a_(Blocks.BELL, "_wall");
        ResourceLocation resourceLocation4 = ModelsResourceUtil.func_240222_a_(Blocks.BELL, "_between_walls");
        this.func_239866_a_(Items.BELL);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.BELL).func_240125_a_(BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.BELL_ATTACHMENT).func_240149_a_(Direction.NORTH, BellAttachment.FLOOR, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240149_a_(Direction.SOUTH, BellAttachment.FLOOR, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240149_a_(Direction.EAST, BellAttachment.FLOOR, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(Direction.WEST, BellAttachment.FLOOR, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240149_a_(Direction.NORTH, BellAttachment.CEILING, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240149_a_(Direction.SOUTH, BellAttachment.CEILING, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240149_a_(Direction.EAST, BellAttachment.CEILING, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(Direction.WEST, BellAttachment.CEILING, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240149_a_(Direction.NORTH, BellAttachment.SINGLE_WALL, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240149_a_(Direction.SOUTH, BellAttachment.SINGLE_WALL, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(Direction.EAST, BellAttachment.SINGLE_WALL, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)).func_240149_a_(Direction.WEST, BellAttachment.SINGLE_WALL, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240149_a_(Direction.SOUTH, BellAttachment.DOUBLE_WALL, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(Direction.NORTH, BellAttachment.DOUBLE_WALL, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240149_a_(Direction.EAST, BellAttachment.DOUBLE_WALL, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4)).func_240149_a_(Direction.WEST, BellAttachment.DOUBLE_WALL, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180))));
    }

    private void func_240020_m_() {
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(Blocks.GRINDSTONE, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240221_a_(Blocks.GRINDSTONE))).func_240125_a_(BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.FACE, BlockStateProperties.HORIZONTAL_FACING).func_240149_a_(AttachFace.FLOOR, Direction.NORTH, BlockModelDefinition.getNewModelDefinition()).func_240149_a_(AttachFace.FLOOR, Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(AttachFace.FLOOR, Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.FLOOR, Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240149_a_(AttachFace.WALL, Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90)).func_240149_a_(AttachFace.WALL, Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(AttachFace.WALL, Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.WALL, Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240149_a_(AttachFace.CEILING, Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.CEILING, Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(AttachFace.CEILING, Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.CEILING, Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270))));
    }

    private void func_239977_e_(Block block, TexturedModel.ISupplier iSupplier) {
        ResourceLocation resourceLocation = iSupplier.func_240466_a_(block, this.field_239835_b_);
        ResourceLocation resourceLocation2 = ModelTextures.func_240347_a_(block, "_front_on");
        ResourceLocation resourceLocation3 = iSupplier.get(block).func_240460_a_(arg_0 -> BlockModelProvider.lambda$func_239977_e_$8(resourceLocation2, arg_0)).func_240458_a_(block, "_on", this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.LIT, resourceLocation3, resourceLocation)).func_240125_a_(BlockModelProvider.func_239933_b_()));
    }

    private void func_239921_a_(Block ... blockArray) {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240223_a_("campfire_off");
        for (Block block : blockArray) {
            ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_CAMPFIRE.func_240228_a_(block, ModelTextures.func_240339_A_(block), this.field_239835_b_);
            this.func_239866_a_(block.asItem());
            this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.LIT, resourceLocation2, resourceLocation)).func_240125_a_(BlockModelProvider.func_239952_c_()));
        }
    }

    private void func_240022_n_() {
        ModelTextures modelTextures = ModelTextures.func_240351_a_(ModelTextures.func_240341_C_(Blocks.BOOKSHELF), ModelTextures.func_240341_C_(Blocks.OAK_PLANKS));
        ResourceLocation resourceLocation = StockModelShapes.CUBE_COLUMN.func_240228_a_(Blocks.BOOKSHELF, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(Blocks.BOOKSHELF, resourceLocation));
    }

    private void func_240024_o_() {
        this.func_239866_a_(Items.REDSTONE);
        this.field_239834_a_.accept(FinishedMultiPartBlockState.func_240106_a_(Blocks.REDSTONE_WIRE).func_240108_a_(IMultiPartPredicateBuilder.func_240090_b_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.REDSTONE_NORTH, RedstoneSide.NONE).func_240098_a_(BlockStateProperties.REDSTONE_EAST, RedstoneSide.NONE).func_240098_a_(BlockStateProperties.REDSTONE_SOUTH, RedstoneSide.NONE).func_240098_a_(BlockStateProperties.REDSTONE_WEST, RedstoneSide.NONE), IMultiPartPredicateBuilder.func_240089_a_().func_240099_a_(BlockStateProperties.REDSTONE_NORTH, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}).func_240099_a_(BlockStateProperties.REDSTONE_EAST, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}), IMultiPartPredicateBuilder.func_240089_a_().func_240099_a_(BlockStateProperties.REDSTONE_EAST, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}).func_240099_a_(BlockStateProperties.REDSTONE_SOUTH, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}), IMultiPartPredicateBuilder.func_240089_a_().func_240099_a_(BlockStateProperties.REDSTONE_SOUTH, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}).func_240099_a_(BlockStateProperties.REDSTONE_WEST, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}), IMultiPartPredicateBuilder.func_240089_a_().func_240099_a_(BlockStateProperties.REDSTONE_WEST, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}).func_240099_a_(BlockStateProperties.REDSTONE_NORTH, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP})), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240223_a_("redstone_dust_dot"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240099_a_(BlockStateProperties.REDSTONE_NORTH, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240223_a_("redstone_dust_side0"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240099_a_(BlockStateProperties.REDSTONE_SOUTH, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240223_a_("redstone_dust_side_alt0"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240099_a_(BlockStateProperties.REDSTONE_EAST, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240223_a_("redstone_dust_side_alt1")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240099_a_(BlockStateProperties.REDSTONE_WEST, (Comparable)((Object)RedstoneSide.SIDE), (Comparable[])new RedstoneSide[]{RedstoneSide.UP}), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240223_a_("redstone_dust_side1")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.REDSTONE_NORTH, RedstoneSide.UP), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240223_a_("redstone_dust_up"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.REDSTONE_EAST, RedstoneSide.UP), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240223_a_("redstone_dust_up")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.REDSTONE_SOUTH, RedstoneSide.UP), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240223_a_("redstone_dust_up")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.REDSTONE_WEST, RedstoneSide.UP), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240223_a_("redstone_dust_up")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)));
    }

    private void func_240026_p_() {
        this.func_239866_a_(Items.COMPARATOR);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.COMPARATOR).func_240125_a_(BlockModelProvider.func_239952_c_()).func_240125_a_(BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.COMPARATOR_MODE, BlockStateProperties.POWERED).func_240149_a_(ComparatorMode.COMPARE, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240221_a_(Blocks.COMPARATOR))).func_240149_a_(ComparatorMode.COMPARE, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.COMPARATOR, "_on"))).func_240149_a_(ComparatorMode.SUBTRACT, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.COMPARATOR, "_subtract"))).func_240149_a_(ComparatorMode.SUBTRACT, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.COMPARATOR, "_on_subtract")))));
    }

    private void func_240028_q_() {
        ModelTextures modelTextures = ModelTextures.func_240345_a_(Blocks.SMOOTH_STONE);
        ModelTextures modelTextures2 = ModelTextures.func_240351_a_(ModelTextures.func_240347_a_(Blocks.SMOOTH_STONE_SLAB, "_side"), modelTextures.func_240348_a_(StockTextureAliases.TOP));
        ResourceLocation resourceLocation = StockModelShapes.SLAB.func_240228_a_(Blocks.SMOOTH_STONE_SLAB, modelTextures2, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.SLAB_TOP.func_240228_a_(Blocks.SMOOTH_STONE_SLAB, modelTextures2, this.field_239835_b_);
        ResourceLocation resourceLocation3 = StockModelShapes.CUBE_COLUMN.func_240235_b_(Blocks.SMOOTH_STONE_SLAB, "_double", modelTextures2, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_240001_h_(Blocks.SMOOTH_STONE_SLAB, resourceLocation, resourceLocation2, resourceLocation3));
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(Blocks.SMOOTH_STONE, StockModelShapes.CUBE_ALL.func_240228_a_(Blocks.SMOOTH_STONE, modelTextures, this.field_239835_b_)));
    }

    private void func_240030_r_() {
        this.func_239866_a_(Items.BREWING_STAND);
        this.field_239834_a_.accept(FinishedMultiPartBlockState.func_240106_a_(Blocks.BREWING_STAND).func_240111_a_(BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240341_C_(Blocks.BREWING_STAND))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.HAS_BOTTLE_0, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.BREWING_STAND, "_bottle0"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.HAS_BOTTLE_1, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.BREWING_STAND, "_bottle1"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.HAS_BOTTLE_2, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.BREWING_STAND, "_bottle2"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.HAS_BOTTLE_0, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.BREWING_STAND, "_empty0"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.HAS_BOTTLE_1, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.BREWING_STAND, "_empty1"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.HAS_BOTTLE_2, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.BREWING_STAND, "_empty2"))));
    }

    private void func_240027_p_(Block block) {
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_SINGLE_FACE.func_240228_a_(block, ModelTextures.func_240353_b_(block), this.field_239835_b_);
        ResourceLocation resourceLocation2 = ModelsResourceUtil.func_240223_a_("mushroom_block_inside");
        this.field_239834_a_.accept(FinishedMultiPartBlockState.func_240106_a_(block).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.EAST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.SOUTH, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WEST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.UP, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.DOWN, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.EAST, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, false)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.SOUTH, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, false)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WEST, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, false)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.UP, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, false)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.DOWN, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, false)));
        this.func_239957_c_(block, TexturedModel.field_240434_a_.func_240465_a_(block, "_inventory", this.field_239835_b_));
    }

    private void func_240032_s_() {
        this.func_239866_a_(Items.CAKE);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.CAKE).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.BITES_0_6).func_240143_a_(0, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240221_a_(Blocks.CAKE))).func_240143_a_(1, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.CAKE, "_slice1"))).func_240143_a_(2, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.CAKE, "_slice2"))).func_240143_a_(3, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.CAKE, "_slice3"))).func_240143_a_(4, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.CAKE, "_slice4"))).func_240143_a_(5, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.CAKE, "_slice5"))).func_240143_a_(6, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.CAKE, "_slice6")))));
    }

    private void func_240034_t_() {
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.PARTICLE, ModelTextures.func_240347_a_(Blocks.CARTOGRAPHY_TABLE, "_side3")).func_240349_a_(StockTextureAliases.DOWN, ModelTextures.func_240341_C_(Blocks.DARK_OAK_PLANKS)).func_240349_a_(StockTextureAliases.UP, ModelTextures.func_240347_a_(Blocks.CARTOGRAPHY_TABLE, "_top")).func_240349_a_(StockTextureAliases.NORTH, ModelTextures.func_240347_a_(Blocks.CARTOGRAPHY_TABLE, "_side3")).func_240349_a_(StockTextureAliases.EAST, ModelTextures.func_240347_a_(Blocks.CARTOGRAPHY_TABLE, "_side3")).func_240349_a_(StockTextureAliases.SOUTH, ModelTextures.func_240347_a_(Blocks.CARTOGRAPHY_TABLE, "_side1")).func_240349_a_(StockTextureAliases.WEST, ModelTextures.func_240347_a_(Blocks.CARTOGRAPHY_TABLE, "_side2"));
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(Blocks.CARTOGRAPHY_TABLE, StockModelShapes.CUBE.func_240228_a_(Blocks.CARTOGRAPHY_TABLE, modelTextures, this.field_239835_b_)));
    }

    private void func_240036_u_() {
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.PARTICLE, ModelTextures.func_240347_a_(Blocks.SMITHING_TABLE, "_front")).func_240349_a_(StockTextureAliases.DOWN, ModelTextures.func_240347_a_(Blocks.SMITHING_TABLE, "_bottom")).func_240349_a_(StockTextureAliases.UP, ModelTextures.func_240347_a_(Blocks.SMITHING_TABLE, "_top")).func_240349_a_(StockTextureAliases.NORTH, ModelTextures.func_240347_a_(Blocks.SMITHING_TABLE, "_front")).func_240349_a_(StockTextureAliases.SOUTH, ModelTextures.func_240347_a_(Blocks.SMITHING_TABLE, "_front")).func_240349_a_(StockTextureAliases.EAST, ModelTextures.func_240347_a_(Blocks.SMITHING_TABLE, "_side")).func_240349_a_(StockTextureAliases.WEST, ModelTextures.func_240347_a_(Blocks.SMITHING_TABLE, "_side"));
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(Blocks.SMITHING_TABLE, StockModelShapes.CUBE.func_240228_a_(Blocks.SMITHING_TABLE, modelTextures, this.field_239835_b_)));
    }

    private void func_239875_a_(Block block, Block block2, BiFunction<Block, Block, ModelTextures> biFunction) {
        ModelTextures modelTextures = biFunction.apply(block, block2);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, StockModelShapes.CUBE.func_240228_a_(block, modelTextures, this.field_239835_b_)));
    }

    private void func_240038_v_() {
        ModelTextures modelTextures = ModelTextures.func_240375_j_(Blocks.PUMPKIN);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(Blocks.PUMPKIN, ModelsResourceUtil.func_240221_a_(Blocks.PUMPKIN)));
        this.func_239879_a_(Blocks.CARVED_PUMPKIN, modelTextures);
        this.func_239879_a_(Blocks.JACK_O_LANTERN, modelTextures);
    }

    private void func_239879_a_(Block block, ModelTextures modelTextures) {
        ResourceLocation resourceLocation = StockModelShapes.ORIENTABLE.func_240228_a_(block, modelTextures.func_240360_c_(StockTextureAliases.FRONT, ModelTextures.func_240341_C_(block)), this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(block, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240125_a_(BlockModelProvider.func_239933_b_()));
    }

    private void func_240040_w_() {
        this.func_239866_a_(Items.CAULDRON);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.CAULDRON).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.LEVEL_0_3).func_240143_a_(0, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240221_a_(Blocks.CAULDRON))).func_240143_a_(1, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.CAULDRON, "_level1"))).func_240143_a_(2, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.CAULDRON, "_level2"))).func_240143_a_(3, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.CAULDRON, "_level3")))));
    }

    private void func_239992_g_(Block block, Block block2) {
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.END, ModelTextures.func_240347_a_(block2, "_top")).func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240341_C_(block));
        this.func_239880_a_(block, modelTextures, StockModelShapes.CUBE_COLUMN);
    }

    private void func_240042_x_() {
        ModelTextures modelTextures = ModelTextures.func_240353_b_(Blocks.CHORUS_FLOWER);
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_CHORUS_FLOWER.func_240228_a_(Blocks.CHORUS_FLOWER, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = this.func_239886_a_(Blocks.CHORUS_FLOWER, "_dead", StockModelShapes.TEMPLATE_CHORUS_FLOWER, arg_0 -> BlockModelProvider.lambda$func_240042_x_$9(modelTextures, arg_0));
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.CHORUS_FLOWER).func_240125_a_(BlockModelProvider.func_239895_a_(BlockStateProperties.AGE_0_5, 5, resourceLocation2, resourceLocation)));
    }

    private void func_240029_q_(Block block) {
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(Blocks.FURNACE, "_top")).func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(Blocks.FURNACE, "_side")).func_240349_a_(StockTextureAliases.FRONT, ModelTextures.func_240347_a_(block, "_front"));
        ModelTextures modelTextures2 = new ModelTextures().func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(Blocks.FURNACE, "_top")).func_240349_a_(StockTextureAliases.FRONT, ModelTextures.func_240347_a_(block, "_front_vertical"));
        ResourceLocation resourceLocation = StockModelShapes.ORIENTABLE.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.ORIENTABLE_VERTICAL.func_240228_a_(block, modelTextures2, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.FACING).func_240143_a_(Direction.DOWN, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180)).func_240143_a_(Direction.UP, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240143_a_(Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240143_a_(Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240143_a_(Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240143_a_(Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270))));
    }

    private void func_240044_y_() {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240221_a_(Blocks.END_PORTAL_FRAME);
        ResourceLocation resourceLocation2 = ModelsResourceUtil.func_240222_a_(Blocks.END_PORTAL_FRAME, "_filled");
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.END_PORTAL_FRAME).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.EYE).func_240143_a_(false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240143_a_(true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2))).func_240125_a_(BlockModelProvider.func_239952_c_()));
    }

    private void func_240045_z_() {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240222_a_(Blocks.CHORUS_PLANT, "_side");
        ResourceLocation resourceLocation2 = ModelsResourceUtil.func_240222_a_(Blocks.CHORUS_PLANT, "_noside");
        ResourceLocation resourceLocation3 = ModelsResourceUtil.func_240222_a_(Blocks.CHORUS_PLANT, "_noside1");
        ResourceLocation resourceLocation4 = ModelsResourceUtil.func_240222_a_(Blocks.CHORUS_PLANT, "_noside2");
        ResourceLocation resourceLocation5 = ModelsResourceUtil.func_240222_a_(Blocks.CHORUS_PLANT, "_noside3");
        this.field_239834_a_.accept(FinishedMultiPartBlockState.func_240106_a_(Blocks.CHORUS_PLANT).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.EAST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.SOUTH, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WEST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.UP, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.DOWN, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240110_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240204_e_, 2), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5)).func_240110_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.EAST, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240204_e_, 2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240110_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.SOUTH, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240204_e_, 2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240110_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WEST, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240204_e_, 2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240110_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.UP, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240204_e_, 2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240203_d_, true)).func_240110_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.DOWN, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240204_e_, 2).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240203_d_, true)));
    }

    private void func_239837_A_() {
        this.field_239834_a_.accept(FinishedMultiPartBlockState.func_240106_a_(Blocks.COMPOSTER).func_240111_a_(BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240341_C_(Blocks.COMPOSTER))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.LEVEL_0_8, 1), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.COMPOSTER, "_contents1"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.LEVEL_0_8, 2), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.COMPOSTER, "_contents2"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.LEVEL_0_8, 3), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.COMPOSTER, "_contents3"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.LEVEL_0_8, 4), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.COMPOSTER, "_contents4"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.LEVEL_0_8, 5), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.COMPOSTER, "_contents5"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.LEVEL_0_8, 6), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.COMPOSTER, "_contents6"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.LEVEL_0_8, 7), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.COMPOSTER, "_contents7"))).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.LEVEL_0_8, 8), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.COMPOSTER, "_contents_ready"))));
    }

    private void func_240031_r_(Block block) {
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.BOTTOM, ModelTextures.func_240341_C_(Blocks.NETHERRACK)).func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240341_C_(block)).func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(block, "_side"));
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, StockModelShapes.CUBE_BOTTOM_TOP.func_240228_a_(block, modelTextures, this.field_239835_b_)));
    }

    private void func_239838_B_() {
        ResourceLocation resourceLocation = ModelTextures.func_240347_a_(Blocks.DAYLIGHT_DETECTOR, "_side");
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(Blocks.DAYLIGHT_DETECTOR, "_top")).func_240349_a_(StockTextureAliases.SIDE, resourceLocation);
        ModelTextures modelTextures2 = new ModelTextures().func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(Blocks.DAYLIGHT_DETECTOR, "_inverted_top")).func_240349_a_(StockTextureAliases.SIDE, resourceLocation);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.DAYLIGHT_DETECTOR).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.INVERTED).func_240143_a_(false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, StockModelShapes.TEMPLATE_DAYLIGHT_SENSOR.func_240228_a_(Blocks.DAYLIGHT_DETECTOR, modelTextures, this.field_239835_b_))).func_240143_a_(true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, StockModelShapes.TEMPLATE_DAYLIGHT_SENSOR.func_240234_a_(ModelsResourceUtil.func_240222_a_(Blocks.DAYLIGHT_DETECTOR, "_inverted"), modelTextures2, this.field_239835_b_)))));
    }

    private void func_239839_C_(Block block) {
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(block, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240221_a_(block))).func_240125_a_(this.func_240008_j_()));
    }

    private void func_239840_D_() {
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.DIRT, ModelTextures.func_240341_C_(Blocks.DIRT)).func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240341_C_(Blocks.FARMLAND));
        ModelTextures modelTextures2 = new ModelTextures().func_240349_a_(StockTextureAliases.DIRT, ModelTextures.func_240341_C_(Blocks.DIRT)).func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(Blocks.FARMLAND, "_moist"));
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_FARMLAND.func_240228_a_(Blocks.FARMLAND, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_FARMLAND.func_240234_a_(ModelTextures.func_240347_a_(Blocks.FARMLAND, "_moist"), modelTextures2, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.FARMLAND).func_240125_a_(BlockModelProvider.func_239895_a_(BlockStateProperties.MOISTURE_0_7, 7, resourceLocation2, resourceLocation)));
    }

    private List<ResourceLocation> func_240033_s_(Block block) {
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_FIRE_FLOOR.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, "_floor0"), ModelTextures.func_240384_r_(block), this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_FIRE_FLOOR.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, "_floor1"), ModelTextures.func_240385_s_(block), this.field_239835_b_);
        return ImmutableList.of(resourceLocation, resourceLocation2);
    }

    private List<ResourceLocation> func_240035_t_(Block block) {
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_FIRE_SIDE.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, "_side0"), ModelTextures.func_240384_r_(block), this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_FIRE_SIDE.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, "_side1"), ModelTextures.func_240385_s_(block), this.field_239835_b_);
        ResourceLocation resourceLocation3 = StockModelShapes.TEMPLATE_FIRE_SIDE_ALT.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, "_side_alt0"), ModelTextures.func_240384_r_(block), this.field_239835_b_);
        ResourceLocation resourceLocation4 = StockModelShapes.TEMPLATE_FIRE_SIDE_ALT.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, "_side_alt1"), ModelTextures.func_240385_s_(block), this.field_239835_b_);
        return ImmutableList.of(resourceLocation, resourceLocation2, resourceLocation3, resourceLocation4);
    }

    private List<ResourceLocation> func_240037_u_(Block block) {
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_FIRE_UP.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, "_up0"), ModelTextures.func_240384_r_(block), this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_FIRE_UP.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, "_up1"), ModelTextures.func_240385_s_(block), this.field_239835_b_);
        ResourceLocation resourceLocation3 = StockModelShapes.TEMPLATE_FIRE_UP_ALT.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, "_up_alt0"), ModelTextures.func_240384_r_(block), this.field_239835_b_);
        ResourceLocation resourceLocation4 = StockModelShapes.TEMPLATE_FIRE_UP_ALT.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, "_up_alt1"), ModelTextures.func_240385_s_(block), this.field_239835_b_);
        return ImmutableList.of(resourceLocation, resourceLocation2, resourceLocation3, resourceLocation4);
    }

    private static List<BlockModelDefinition> func_239914_a_(List<ResourceLocation> list, UnaryOperator<BlockModelDefinition> unaryOperator) {
        return list.stream().map(BlockModelProvider::lambda$func_239914_a_$10).map(unaryOperator).collect(Collectors.toList());
    }

    private void func_239841_E_() {
        IMultiPartPredicateBuilder.Properties properties = IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, false).func_240098_a_(BlockStateProperties.EAST, false).func_240098_a_(BlockStateProperties.SOUTH, false).func_240098_a_(BlockStateProperties.WEST, false).func_240098_a_(BlockStateProperties.UP, false);
        List<ResourceLocation> list = this.func_240033_s_(Blocks.FIRE);
        List<ResourceLocation> list2 = this.func_240035_t_(Blocks.FIRE);
        List<ResourceLocation> list3 = this.func_240037_u_(Blocks.FIRE);
        this.field_239834_a_.accept(FinishedMultiPartBlockState.func_240106_a_(Blocks.FIRE).func_240109_a_(properties, BlockModelProvider.func_239914_a_(list, BlockModelProvider::lambda$func_239841_E_$11)).func_240109_a_(IMultiPartPredicateBuilder.func_240090_b_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, true), properties), BlockModelProvider.func_239914_a_(list2, BlockModelProvider::lambda$func_239841_E_$12)).func_240109_a_(IMultiPartPredicateBuilder.func_240090_b_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.EAST, true), properties), BlockModelProvider.func_239914_a_(list2, BlockModelProvider::lambda$func_239841_E_$13)).func_240109_a_(IMultiPartPredicateBuilder.func_240090_b_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.SOUTH, true), properties), BlockModelProvider.func_239914_a_(list2, BlockModelProvider::lambda$func_239841_E_$14)).func_240109_a_(IMultiPartPredicateBuilder.func_240090_b_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WEST, true), properties), BlockModelProvider.func_239914_a_(list2, BlockModelProvider::lambda$func_239841_E_$15)).func_240109_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.UP, true), BlockModelProvider.func_239914_a_(list3, BlockModelProvider::lambda$func_239841_E_$16)));
    }

    private void func_239842_F_() {
        List<ResourceLocation> list = this.func_240033_s_(Blocks.SOUL_FIRE);
        List<ResourceLocation> list2 = this.func_240035_t_(Blocks.SOUL_FIRE);
        this.field_239834_a_.accept(FinishedMultiPartBlockState.func_240106_a_(Blocks.SOUL_FIRE).func_240112_a_(BlockModelProvider.func_239914_a_(list, BlockModelProvider::lambda$func_239842_F_$17)).func_240112_a_(BlockModelProvider.func_239914_a_(list2, BlockModelProvider::lambda$func_239842_F_$18)).func_240112_a_(BlockModelProvider.func_239914_a_(list2, BlockModelProvider::lambda$func_239842_F_$19)).func_240112_a_(BlockModelProvider.func_239914_a_(list2, BlockModelProvider::lambda$func_239842_F_$20)).func_240112_a_(BlockModelProvider.func_239914_a_(list2, BlockModelProvider::lambda$func_239842_F_$21)));
    }

    private void func_240039_v_(Block block) {
        ResourceLocation resourceLocation = TexturedModel.field_240448_o_.func_240466_a_(block, this.field_239835_b_);
        ResourceLocation resourceLocation2 = TexturedModel.field_240449_p_.func_240466_a_(block, this.field_239835_b_);
        this.func_239866_a_(block.asItem());
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.HANGING, resourceLocation2, resourceLocation)));
    }

    private void func_239843_G_() {
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.FROSTED_ICE).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.AGE_0_3).func_240143_a_(0, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, this.func_239886_a_(Blocks.FROSTED_ICE, "_0", StockModelShapes.CUBE_ALL, ModelTextures::func_240356_b_))).func_240143_a_(1, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, this.func_239886_a_(Blocks.FROSTED_ICE, "_1", StockModelShapes.CUBE_ALL, ModelTextures::func_240356_b_))).func_240143_a_(2, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, this.func_239886_a_(Blocks.FROSTED_ICE, "_2", StockModelShapes.CUBE_ALL, ModelTextures::func_240356_b_))).func_240143_a_(3, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, this.func_239886_a_(Blocks.FROSTED_ICE, "_3", StockModelShapes.CUBE_ALL, ModelTextures::func_240356_b_)))));
    }

    private void func_239844_H_() {
        ResourceLocation resourceLocation = ModelTextures.func_240341_C_(Blocks.DIRT);
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.BOTTOM, resourceLocation).func_240355_b_(StockTextureAliases.BOTTOM, StockTextureAliases.PARTICLE).func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(Blocks.GRASS_BLOCK, "_top")).func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(Blocks.GRASS_BLOCK, "_snow"));
        BlockModelDefinition blockModelDefinition = BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, StockModelShapes.CUBE_BOTTOM_TOP.func_240229_a_(Blocks.GRASS_BLOCK, "_snow", modelTextures, this.field_239835_b_));
        this.func_239889_a_(Blocks.GRASS_BLOCK, ModelsResourceUtil.func_240221_a_(Blocks.GRASS_BLOCK), blockModelDefinition);
        ResourceLocation resourceLocation2 = TexturedModel.field_240438_e_.get(Blocks.MYCELIUM).func_240460_a_(arg_0 -> BlockModelProvider.lambda$func_239844_H_$22(resourceLocation, arg_0)).func_240459_a_(Blocks.MYCELIUM, this.field_239835_b_);
        this.func_239889_a_(Blocks.MYCELIUM, resourceLocation2, blockModelDefinition);
        ResourceLocation resourceLocation3 = TexturedModel.field_240438_e_.get(Blocks.PODZOL).func_240460_a_(arg_0 -> BlockModelProvider.lambda$func_239844_H_$23(resourceLocation, arg_0)).func_240459_a_(Blocks.PODZOL, this.field_239835_b_);
        this.func_239889_a_(Blocks.PODZOL, resourceLocation3, blockModelDefinition);
    }

    private void func_239889_a_(Block block, ResourceLocation resourceLocation, BlockModelDefinition blockModelDefinition) {
        List<BlockModelDefinition> list = Arrays.asList(BlockModelProvider.func_239915_a_(resourceLocation));
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.SNOWY).func_240143_a_(true, blockModelDefinition).func_240144_a_(false, list)));
    }

    private void func_239845_I_() {
        this.func_239866_a_(Items.COCOA_BEANS);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.COCOA).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.AGE_0_2).func_240143_a_(0, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.COCOA, "_stage0"))).func_240143_a_(1, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.COCOA, "_stage1"))).func_240143_a_(2, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.COCOA, "_stage2")))).func_240125_a_(BlockModelProvider.func_239952_c_()));
    }

    private void func_239846_J_() {
        this.field_239834_a_.accept(BlockModelProvider.func_239968_d_(Blocks.GRASS_PATH, ModelsResourceUtil.func_240221_a_(Blocks.GRASS_PATH)));
    }

    private void func_239999_h_(Block block, Block block2) {
        ModelTextures modelTextures = ModelTextures.func_240353_b_(block2);
        ResourceLocation resourceLocation = StockModelShapes.PRESSURE_PLATE_UP.func_240228_a_(block, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.PRESSURE_PLATE_DOWN.func_240228_a_(block, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockModelProvider.func_239895_a_(BlockStateProperties.POWER_0_15, 1, resourceLocation2, resourceLocation)));
    }

    private void func_239847_K_() {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240221_a_(Blocks.HOPPER);
        ResourceLocation resourceLocation2 = ModelsResourceUtil.func_240222_a_(Blocks.HOPPER, "_side");
        this.func_239866_a_(Items.HOPPER);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.HOPPER).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.FACING_EXCEPT_UP).func_240143_a_(Direction.DOWN, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240143_a_(Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240143_a_(Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240143_a_(Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240143_a_(Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270))));
    }

    private void func_240005_i_(Block block, Block block2) {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240221_a_(block);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(block2, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)));
        this.func_239957_c_(block2, resourceLocation);
    }

    private void func_239848_L_() {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240222_a_(Blocks.IRON_BARS, "_post_ends");
        ResourceLocation resourceLocation2 = ModelsResourceUtil.func_240222_a_(Blocks.IRON_BARS, "_post");
        ResourceLocation resourceLocation3 = ModelsResourceUtil.func_240222_a_(Blocks.IRON_BARS, "_cap");
        ResourceLocation resourceLocation4 = ModelsResourceUtil.func_240222_a_(Blocks.IRON_BARS, "_cap_alt");
        ResourceLocation resourceLocation5 = ModelsResourceUtil.func_240222_a_(Blocks.IRON_BARS, "_side");
        ResourceLocation resourceLocation6 = ModelsResourceUtil.func_240222_a_(Blocks.IRON_BARS, "_side_alt");
        this.field_239834_a_.accept(FinishedMultiPartBlockState.func_240106_a_(Blocks.IRON_BARS).func_240111_a_(BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, false).func_240098_a_(BlockStateProperties.EAST, false).func_240098_a_(BlockStateProperties.SOUTH, false).func_240098_a_(BlockStateProperties.WEST, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation2)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, true).func_240098_a_(BlockStateProperties.EAST, false).func_240098_a_(BlockStateProperties.SOUTH, false).func_240098_a_(BlockStateProperties.WEST, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, false).func_240098_a_(BlockStateProperties.EAST, true).func_240098_a_(BlockStateProperties.SOUTH, false).func_240098_a_(BlockStateProperties.WEST, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation3).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, false).func_240098_a_(BlockStateProperties.EAST, false).func_240098_a_(BlockStateProperties.SOUTH, true).func_240098_a_(BlockStateProperties.WEST, false), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, false).func_240098_a_(BlockStateProperties.EAST, false).func_240098_a_(BlockStateProperties.SOUTH, false).func_240098_a_(BlockStateProperties.WEST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation4).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.NORTH, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.EAST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.SOUTH, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation6)).func_240108_a_(IMultiPartPredicateBuilder.func_240089_a_().func_240098_a_(BlockStateProperties.WEST, true), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation6).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)));
        this.func_239934_b_(Blocks.IRON_BARS);
    }

    private void func_240041_w_(Block block) {
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(block, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240221_a_(block))).func_240125_a_(BlockModelProvider.func_239933_b_()));
    }

    private void func_239849_M_() {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240221_a_(Blocks.LEVER);
        ResourceLocation resourceLocation2 = ModelsResourceUtil.func_240222_a_(Blocks.LEVER, "_on");
        this.func_239934_b_(Blocks.LEVER);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.LEVER).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.POWERED, resourceLocation, resourceLocation2)).func_240125_a_(BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.FACE, BlockStateProperties.HORIZONTAL_FACING).func_240149_a_(AttachFace.CEILING, Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.CEILING, Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240149_a_(AttachFace.CEILING, Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.CEILING, Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(AttachFace.FLOOR, Direction.NORTH, BlockModelDefinition.getNewModelDefinition()).func_240149_a_(AttachFace.FLOOR, Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(AttachFace.FLOOR, Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.FLOOR, Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240149_a_(AttachFace.WALL, Direction.NORTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90)).func_240149_a_(AttachFace.WALL, Direction.EAST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240149_a_(AttachFace.WALL, Direction.SOUTH, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240149_a_(AttachFace.WALL, Direction.WEST, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270))));
    }

    private void func_239850_N_() {
        this.func_239934_b_(Blocks.LILY_PAD);
        this.field_239834_a_.accept(BlockModelProvider.func_239968_d_(Blocks.LILY_PAD, ModelsResourceUtil.func_240221_a_(Blocks.LILY_PAD)));
    }

    private void func_239851_O_() {
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.NETHER_PORTAL).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.HORIZONTAL_AXIS).func_240143_a_(Direction.Axis.X, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.NETHER_PORTAL, "_ns"))).func_240143_a_(Direction.Axis.Z, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.NETHER_PORTAL, "_ew")))));
    }

    private void func_239852_P_() {
        ResourceLocation resourceLocation = TexturedModel.field_240434_a_.func_240466_a_(Blocks.NETHERRACK, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240121_a_(Blocks.NETHERRACK, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R180), BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270)));
    }

    private void func_239853_Q_() {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240221_a_(Blocks.OBSERVER);
        ResourceLocation resourceLocation2 = ModelsResourceUtil.func_240222_a_(Blocks.OBSERVER, "_on");
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.OBSERVER).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.POWERED, resourceLocation2, resourceLocation)).func_240125_a_(BlockModelProvider.func_239974_e_()));
    }

    private void func_239854_R_() {
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.BOTTOM, ModelTextures.func_240347_a_(Blocks.PISTON, "_bottom")).func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(Blocks.PISTON, "_side"));
        ResourceLocation resourceLocation = ModelTextures.func_240347_a_(Blocks.PISTON, "_top_sticky");
        ResourceLocation resourceLocation2 = ModelTextures.func_240347_a_(Blocks.PISTON, "_top");
        ModelTextures modelTextures2 = modelTextures.func_240360_c_(StockTextureAliases.PLATFORM, resourceLocation);
        ModelTextures modelTextures3 = modelTextures.func_240360_c_(StockTextureAliases.PLATFORM, resourceLocation2);
        ResourceLocation resourceLocation3 = ModelsResourceUtil.func_240222_a_(Blocks.PISTON, "_base");
        this.func_239890_a_(Blocks.PISTON, resourceLocation3, modelTextures3);
        this.func_239890_a_(Blocks.STICKY_PISTON, resourceLocation3, modelTextures2);
        ResourceLocation resourceLocation4 = StockModelShapes.CUBE_BOTTOM_TOP.func_240229_a_(Blocks.PISTON, "_inventory", modelTextures.func_240360_c_(StockTextureAliases.TOP, resourceLocation2), this.field_239835_b_);
        ResourceLocation resourceLocation5 = StockModelShapes.CUBE_BOTTOM_TOP.func_240229_a_(Blocks.STICKY_PISTON, "_inventory", modelTextures.func_240360_c_(StockTextureAliases.TOP, resourceLocation), this.field_239835_b_);
        this.func_239957_c_(Blocks.PISTON, resourceLocation4);
        this.func_239957_c_(Blocks.STICKY_PISTON, resourceLocation5);
    }

    private void func_239890_a_(Block block, ResourceLocation resourceLocation, ModelTextures modelTextures) {
        ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_PISTON.func_240228_a_(block, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(block).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.EXTENDED, resourceLocation, resourceLocation2)).func_240125_a_(BlockModelProvider.func_239974_e_()));
    }

    private void func_239855_S_() {
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.UNSTICKY, ModelTextures.func_240347_a_(Blocks.PISTON, "_top")).func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(Blocks.PISTON, "_side"));
        ModelTextures modelTextures2 = modelTextures.func_240360_c_(StockTextureAliases.PLATFORM, ModelTextures.func_240347_a_(Blocks.PISTON, "_top_sticky"));
        ModelTextures modelTextures3 = modelTextures.func_240360_c_(StockTextureAliases.PLATFORM, ModelTextures.func_240347_a_(Blocks.PISTON, "_top"));
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.PISTON_HEAD).func_240125_a_(BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.SHORT, BlockStateProperties.PISTON_TYPE).func_240149_a_(false, PistonType.DEFAULT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, StockModelShapes.TEMPLATE_PISTON_HEAD.func_240229_a_(Blocks.PISTON, "_head", modelTextures3, this.field_239835_b_))).func_240149_a_(false, PistonType.STICKY, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, StockModelShapes.TEMPLATE_PISTON_HEAD.func_240229_a_(Blocks.PISTON, "_head_sticky", modelTextures2, this.field_239835_b_))).func_240149_a_(true, PistonType.DEFAULT, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, StockModelShapes.TEMPLATE_PISTON_HEAD_SHORT.func_240229_a_(Blocks.PISTON, "_head_short", modelTextures3, this.field_239835_b_))).func_240149_a_(true, PistonType.STICKY, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, StockModelShapes.TEMPLATE_PISTON_HEAD_SHORT.func_240229_a_(Blocks.PISTON, "_head_short_sticky", modelTextures2, this.field_239835_b_)))).func_240125_a_(BlockModelProvider.func_239974_e_()));
    }

    private void func_239856_T_() {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240222_a_(Blocks.SCAFFOLDING, "_stable");
        ResourceLocation resourceLocation2 = ModelsResourceUtil.func_240222_a_(Blocks.SCAFFOLDING, "_unstable");
        this.func_239957_c_(Blocks.SCAFFOLDING, resourceLocation);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.SCAFFOLDING).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.BOTTOM, resourceLocation2, resourceLocation)));
    }

    private void func_239857_U_() {
        ResourceLocation resourceLocation = TexturedModel.field_240434_a_.func_240466_a_(Blocks.REDSTONE_LAMP, this.field_239835_b_);
        ResourceLocation resourceLocation2 = this.func_239886_a_(Blocks.REDSTONE_LAMP, "_on", StockModelShapes.CUBE_ALL, ModelTextures::func_240356_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.REDSTONE_LAMP).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.LIT, resourceLocation2, resourceLocation)));
    }

    private void func_240010_j_(Block block, Block block2) {
        ModelTextures modelTextures = ModelTextures.func_240387_u_(block);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, StockModelShapes.TEMPLATE_TORCH.func_240228_a_(block, modelTextures, this.field_239835_b_)));
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(block2, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, StockModelShapes.TEMPLATE_TORCH_WALL.func_240228_a_(block2, modelTextures, this.field_239835_b_))).func_240125_a_(BlockModelProvider.func_239964_d_()));
        this.func_239934_b_(block);
        this.func_239869_a_(block2);
    }

    private void func_239858_V_() {
        ModelTextures modelTextures = ModelTextures.func_240387_u_(Blocks.REDSTONE_TORCH);
        ModelTextures modelTextures2 = ModelTextures.func_240374_i_(ModelTextures.func_240347_a_(Blocks.REDSTONE_TORCH, "_off"));
        ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_TORCH.func_240228_a_(Blocks.REDSTONE_TORCH, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_TORCH.func_240229_a_(Blocks.REDSTONE_TORCH, "_off", modelTextures2, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.REDSTONE_TORCH).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.LIT, resourceLocation, resourceLocation2)));
        ResourceLocation resourceLocation3 = StockModelShapes.TEMPLATE_TORCH_WALL.func_240228_a_(Blocks.REDSTONE_WALL_TORCH, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation4 = StockModelShapes.TEMPLATE_TORCH_WALL.func_240229_a_(Blocks.REDSTONE_WALL_TORCH, "_off", modelTextures2, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.REDSTONE_WALL_TORCH).func_240125_a_(BlockModelProvider.func_239894_a_(BlockStateProperties.LIT, resourceLocation3, resourceLocation4)).func_240125_a_(BlockModelProvider.func_239964_d_()));
        this.func_239934_b_(Blocks.REDSTONE_TORCH);
        this.func_239869_a_(Blocks.REDSTONE_WALL_TORCH);
    }

    private void func_239859_W_() {
        this.func_239866_a_(Items.REPEATER);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.REPEATER).func_240125_a_(BlockStateVariantBuilder.func_240135_a_(BlockStateProperties.DELAY_1_4, BlockStateProperties.LOCKED, BlockStateProperties.POWERED).func_240160_a_(BlockModelProvider::lambda$func_239859_W_$24)).func_240125_a_(BlockModelProvider.func_239952_c_()));
    }

    private void func_239860_X_() {
        this.func_239866_a_(Items.SEA_PICKLE);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.SEA_PICKLE).func_240125_a_(BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.PICKLES_1_4, BlockStateProperties.WATERLOGGED).func_240150_a_(1, false, Arrays.asList(BlockModelProvider.func_239915_a_(ModelsResourceUtil.func_240223_a_("dead_sea_pickle")))).func_240150_a_(2, false, Arrays.asList(BlockModelProvider.func_239915_a_(ModelsResourceUtil.func_240223_a_("two_dead_sea_pickles")))).func_240150_a_(3, false, Arrays.asList(BlockModelProvider.func_239915_a_(ModelsResourceUtil.func_240223_a_("three_dead_sea_pickles")))).func_240150_a_(4, false, Arrays.asList(BlockModelProvider.func_239915_a_(ModelsResourceUtil.func_240223_a_("four_dead_sea_pickles")))).func_240150_a_(1, true, Arrays.asList(BlockModelProvider.func_239915_a_(ModelsResourceUtil.func_240223_a_("sea_pickle")))).func_240150_a_(2, true, Arrays.asList(BlockModelProvider.func_239915_a_(ModelsResourceUtil.func_240223_a_("two_sea_pickles")))).func_240150_a_(3, true, Arrays.asList(BlockModelProvider.func_239915_a_(ModelsResourceUtil.func_240223_a_("three_sea_pickles")))).func_240150_a_(4, true, Arrays.asList(BlockModelProvider.func_239915_a_(ModelsResourceUtil.func_240223_a_("four_sea_pickles"))))));
    }

    private void func_239861_Y_() {
        ModelTextures modelTextures = ModelTextures.func_240345_a_(Blocks.SNOW);
        ResourceLocation resourceLocation = StockModelShapes.CUBE_ALL.func_240228_a_(Blocks.SNOW_BLOCK, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.SNOW).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.LAYERS_1_8).func_240145_a_(arg_0 -> BlockModelProvider.lambda$func_239861_Y_$25(resourceLocation, arg_0))));
        this.func_239957_c_(Blocks.SNOW, ModelsResourceUtil.func_240222_a_(Blocks.SNOW, "_height2"));
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(Blocks.SNOW_BLOCK, resourceLocation));
    }

    private void func_239862_Z_() {
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(Blocks.STONECUTTER, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240221_a_(Blocks.STONECUTTER))).func_240125_a_(BlockModelProvider.func_239933_b_()));
    }

    private void func_239923_aa_() {
        ResourceLocation resourceLocation = TexturedModel.field_240434_a_.func_240466_a_(Blocks.STRUCTURE_BLOCK, this.field_239835_b_);
        this.func_239957_c_(Blocks.STRUCTURE_BLOCK, resourceLocation);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.STRUCTURE_BLOCK).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.STRUCTURE_BLOCK_MODE).func_240145_a_(this::lambda$func_239923_aa_$26)));
    }

    private void func_239924_ab_() {
        this.func_239866_a_(Items.SWEET_BERRIES);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.SWEET_BERRY_BUSH).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.AGE_0_3).func_240145_a_(this::lambda$func_239924_ab_$27)));
    }

    private void func_239925_ac_() {
        this.func_239866_a_(Items.STRING);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.TRIPWIRE).func_240125_a_(BlockStateVariantBuilder.func_240137_a_(BlockStateProperties.ATTACHED, BlockStateProperties.EAST, BlockStateProperties.NORTH, BlockStateProperties.SOUTH, BlockStateProperties.WEST).func_240177_a_(false, false, false, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_ns"))).func_240177_a_(false, true, false, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_n")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(false, false, true, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_n"))).func_240177_a_(false, false, false, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_n")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(false, false, false, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_n")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(false, true, true, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_ne"))).func_240177_a_(false, true, false, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_ne")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(false, false, false, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_ne")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(false, false, true, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_ne")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(false, false, true, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_ns"))).func_240177_a_(false, true, false, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_ns")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(false, true, true, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_nse"))).func_240177_a_(false, true, false, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_nse")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(false, false, true, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_nse")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(false, true, true, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_nse")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(false, true, true, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_nsew"))).func_240177_a_(true, false, false, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_ns"))).func_240177_a_(true, false, true, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_n"))).func_240177_a_(true, false, false, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_n")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(true, true, false, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_n")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(true, false, false, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_n")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(true, true, true, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_ne"))).func_240177_a_(true, true, false, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_ne")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(true, false, false, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_ne")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(true, false, true, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_ne")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(true, false, true, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_ns"))).func_240177_a_(true, true, false, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_ns")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(true, true, true, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_nse"))).func_240177_a_(true, true, false, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_nse")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(true, false, true, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_nse")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(true, true, true, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_nse")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(true, true, true, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.TRIPWIRE, "_attached_nsew")))));
    }

    private void func_239926_ad_() {
        this.func_239934_b_(Blocks.TRIPWIRE_HOOK);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.TRIPWIRE_HOOK).func_240125_a_(BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.ATTACHED, BlockStateProperties.POWERED).func_240152_a_(BlockModelProvider::lambda$func_239926_ad_$28)).func_240125_a_(BlockModelProvider.func_239933_b_()));
    }

    private ResourceLocation func_239865_a_(int n, String string, ModelTextures modelTextures) {
        switch (n) {
            case 1: {
                return StockModelShapes.TEMPLATE_TURTLE_EGG.func_240234_a_(ModelsResourceUtil.func_240223_a_(string + "turtle_egg"), modelTextures, this.field_239835_b_);
            }
            case 2: {
                return StockModelShapes.TEMPLATE_TWO_TURTLE_EGGS.func_240234_a_(ModelsResourceUtil.func_240223_a_("two_" + string + "turtle_eggs"), modelTextures, this.field_239835_b_);
            }
            case 3: {
                return StockModelShapes.TEMPLATE_THREE_TURTLE_EGGS.func_240234_a_(ModelsResourceUtil.func_240223_a_("three_" + string + "turtle_eggs"), modelTextures, this.field_239835_b_);
            }
            case 4: {
                return StockModelShapes.TEMPLATE_FOUR_TURTLE_EGGS.func_240234_a_(ModelsResourceUtil.func_240223_a_("four_" + string + "turtle_eggs"), modelTextures, this.field_239835_b_);
            }
        }
        throw new UnsupportedOperationException();
    }

    private ResourceLocation func_239912_a_(Integer n, Integer n2) {
        switch (n2) {
            case 0: {
                return this.func_239865_a_(n, "", ModelTextures.func_240356_b_(ModelTextures.func_240341_C_(Blocks.TURTLE_EGG)));
            }
            case 1: {
                return this.func_239865_a_(n, "slightly_cracked_", ModelTextures.func_240356_b_(ModelTextures.func_240347_a_(Blocks.TURTLE_EGG, "_slightly_cracked")));
            }
            case 2: {
                return this.func_239865_a_(n, "very_cracked_", ModelTextures.func_240356_b_(ModelTextures.func_240347_a_(Blocks.TURTLE_EGG, "_very_cracked")));
            }
        }
        throw new UnsupportedOperationException();
    }

    private void func_239927_ae_() {
        this.func_239866_a_(Items.TURTLE_EGG);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.TURTLE_EGG).func_240125_a_(BlockStateVariantBuilder.func_240134_a_(BlockStateProperties.EGGS_1_4, BlockStateProperties.HATCH_0_2).func_240155_b_(this::lambda$func_239927_ae_$29)));
    }

    private void func_239928_af_() {
        this.func_239934_b_(Blocks.VINE);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.VINE).func_240125_a_(BlockStateVariantBuilder.func_240137_a_(BlockStateProperties.EAST, BlockStateProperties.NORTH, BlockStateProperties.SOUTH, BlockStateProperties.UP, BlockStateProperties.WEST).func_240177_a_(false, false, false, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_1"))).func_240177_a_(false, false, true, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_1"))).func_240177_a_(false, false, false, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_1")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(false, true, false, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_1")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(true, false, false, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_1")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(true, true, false, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2"))).func_240177_a_(true, false, true, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(false, false, true, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(false, true, false, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(true, false, false, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2_opposite"))).func_240177_a_(false, true, true, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2_opposite")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(true, true, true, false, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_3"))).func_240177_a_(true, false, true, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_3")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(false, true, true, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_3")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(true, true, false, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_3")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(true, true, true, false, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_4"))).func_240177_a_(false, false, false, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_u"))).func_240177_a_(false, false, true, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_1u"))).func_240177_a_(false, false, false, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_1u")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(false, true, false, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_1u")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(true, false, false, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_1u")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(true, true, false, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2u"))).func_240177_a_(true, false, true, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2u")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(false, false, true, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2u")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(false, true, false, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2u")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(true, false, false, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2u_opposite"))).func_240177_a_(false, true, true, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_2u_opposite")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(true, true, true, true, false, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_3u"))).func_240177_a_(true, false, true, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_3u")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90)).func_240177_a_(false, true, true, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_3u")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180)).func_240177_a_(true, true, false, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_3u")).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270)).func_240177_a_(true, true, true, true, true, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.VINE, "_4u")))));
    }

    private void func_239929_ag_() {
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(Blocks.MAGMA_BLOCK, StockModelShapes.CUBE_ALL.func_240228_a_(Blocks.MAGMA_BLOCK, ModelTextures.func_240356_b_(ModelsResourceUtil.func_240223_a_("magma")), this.field_239835_b_)));
    }

    private void func_240043_x_(Block block) {
        this.func_239956_c_(block, TexturedModel.field_240445_l_);
        StockModelShapes.TEMPLATE_SHULKER_BOX.func_240234_a_(ModelsResourceUtil.func_240219_a_(block.asItem()), ModelTextures.func_240383_q_(block), this.field_239835_b_);
    }

    private void func_239936_b_(Block block, Block block2, TintMode tintMode) {
        this.func_239937_b_(block, tintMode);
        this.func_239937_b_(block2, tintMode);
    }

    private void func_240015_k_(Block block, Block block2) {
        StockModelShapes.TEMPLATE_BED.func_240234_a_(ModelsResourceUtil.func_240219_a_(block.asItem()), ModelTextures.func_240383_q_(block2), this.field_239835_b_);
    }

    private void func_239930_ah_() {
        ResourceLocation resourceLocation = ModelsResourceUtil.func_240221_a_(Blocks.STONE);
        ResourceLocation resourceLocation2 = ModelsResourceUtil.func_240222_a_(Blocks.STONE, "_mirrored");
        this.field_239834_a_.accept(BlockModelProvider.func_239979_e_(Blocks.INFESTED_STONE, resourceLocation, resourceLocation2));
        this.func_239957_c_(Blocks.INFESTED_STONE, resourceLocation);
    }

    private void func_240019_l_(Block block, Block block2) {
        this.func_239877_a_(block, TintMode.NOT_TINTED);
        ModelTextures modelTextures = ModelTextures.func_240365_d_(ModelTextures.func_240347_a_(block, "_pot"));
        ResourceLocation resourceLocation = TintMode.NOT_TINTED.func_240067_b_().func_240228_a_(block2, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block2, resourceLocation));
    }

    private void func_239931_ai_() {
        ResourceLocation resourceLocation = ModelTextures.func_240347_a_(Blocks.RESPAWN_ANCHOR, "_bottom");
        ResourceLocation resourceLocation2 = ModelTextures.func_240347_a_(Blocks.RESPAWN_ANCHOR, "_top_off");
        ResourceLocation resourceLocation3 = ModelTextures.func_240347_a_(Blocks.RESPAWN_ANCHOR, "_top");
        ResourceLocation[] resourceLocationArray = new ResourceLocation[5];
        for (int i = 0; i < 5; ++i) {
            ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.BOTTOM, resourceLocation).func_240349_a_(StockTextureAliases.TOP, i == 0 ? resourceLocation2 : resourceLocation3).func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(Blocks.RESPAWN_ANCHOR, "_side" + i));
            resourceLocationArray[i] = StockModelShapes.CUBE_BOTTOM_TOP.func_240229_a_(Blocks.RESPAWN_ANCHOR, "_" + i, modelTextures, this.field_239835_b_);
        }
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240119_a_(Blocks.RESPAWN_ANCHOR).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.CHARGES).func_240145_a_(arg_0 -> BlockModelProvider.lambda$func_239931_ai_$30(resourceLocationArray, arg_0))));
        this.func_239867_a_(Items.RESPAWN_ANCHOR, resourceLocationArray[0]);
    }

    private BlockModelDefinition func_239898_a_(JigsawOrientation jigsawOrientation, BlockModelDefinition blockModelDefinition) {
        switch (jigsawOrientation) {
            case DOWN_NORTH: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90);
            }
            case DOWN_SOUTH: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180);
            }
            case DOWN_WEST: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270);
            }
            case DOWN_EAST: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R90).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90);
            }
            case UP_NORTH: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180);
            }
            case UP_SOUTH: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270);
            }
            case UP_WEST: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90);
            }
            case UP_EAST: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240200_a_, BlockModelFields.Rotation.R270).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270);
            }
            case NORTH_UP: {
                return blockModelDefinition;
            }
            case SOUTH_UP: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180);
            }
            case WEST_UP: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270);
            }
            case EAST_UP: {
                return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90);
            }
        }
        throw new UnsupportedOperationException("Rotation " + jigsawOrientation + " can't be expressed with existing x and y values");
    }

    private void func_239932_aj_() {
        ResourceLocation resourceLocation = ModelTextures.func_240347_a_(Blocks.JIGSAW, "_top");
        ResourceLocation resourceLocation2 = ModelTextures.func_240347_a_(Blocks.JIGSAW, "_bottom");
        ResourceLocation resourceLocation3 = ModelTextures.func_240347_a_(Blocks.JIGSAW, "_side");
        ResourceLocation resourceLocation4 = ModelTextures.func_240347_a_(Blocks.JIGSAW, "_lock");
        ModelTextures modelTextures = new ModelTextures().func_240349_a_(StockTextureAliases.DOWN, resourceLocation3).func_240349_a_(StockTextureAliases.WEST, resourceLocation3).func_240349_a_(StockTextureAliases.EAST, resourceLocation3).func_240349_a_(StockTextureAliases.PARTICLE, resourceLocation).func_240349_a_(StockTextureAliases.NORTH, resourceLocation).func_240349_a_(StockTextureAliases.SOUTH, resourceLocation2).func_240349_a_(StockTextureAliases.UP, resourceLocation4);
        ResourceLocation resourceLocation5 = StockModelShapes.CUBE_DIRECTIONAL.func_240228_a_(Blocks.JIGSAW, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(FinishedVariantBlockState.func_240120_a_(Blocks.JIGSAW, BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation5)).func_240125_a_(BlockStateVariantBuilder.func_240133_a_(BlockStateProperties.ORIENTATION).func_240145_a_(this::lambda$func_239932_aj_$31)));
    }

    public void func_239863_a_() {
        this.func_240014_k_(Blocks.AIR);
        this.func_239872_a_(Blocks.CAVE_AIR, Blocks.AIR);
        this.func_239872_a_(Blocks.VOID_AIR, Blocks.AIR);
        this.func_240014_k_(Blocks.BEACON);
        this.func_240014_k_(Blocks.CACTUS);
        this.func_239872_a_(Blocks.BUBBLE_COLUMN, Blocks.WATER);
        this.func_240014_k_(Blocks.DRAGON_EGG);
        this.func_240014_k_(Blocks.DRIED_KELP_BLOCK);
        this.func_240014_k_(Blocks.ENCHANTING_TABLE);
        this.func_240014_k_(Blocks.FLOWER_POT);
        this.func_239866_a_(Items.FLOWER_POT);
        this.func_240014_k_(Blocks.HONEY_BLOCK);
        this.func_240014_k_(Blocks.WATER);
        this.func_240014_k_(Blocks.LAVA);
        this.func_240014_k_(Blocks.SLIME_BLOCK);
        this.func_239866_a_(Items.CHAIN);
        this.func_240014_k_(Blocks.POTTED_BAMBOO);
        this.func_240014_k_(Blocks.POTTED_CACTUS);
        this.func_239871_a_(Blocks.BARRIER, Items.BARRIER);
        this.func_239866_a_(Items.BARRIER);
        this.func_239871_a_(Blocks.STRUCTURE_VOID, Items.STRUCTURE_VOID);
        this.func_239866_a_(Items.STRUCTURE_VOID);
        this.func_239993_g_(Blocks.MOVING_PISTON, ModelTextures.func_240347_a_(Blocks.PISTON, "_side"));
        this.func_239956_c_(Blocks.COAL_ORE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.COAL_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.DIAMOND_ORE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.DIAMOND_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.EMERALD_ORE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.EMERALD_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.GOLD_ORE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.NETHER_GOLD_ORE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.GOLD_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.IRON_ORE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.IRON_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.ANCIENT_DEBRIS, TexturedModel.field_240436_c_);
        this.func_239956_c_(Blocks.NETHERITE_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.LAPIS_ORE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.LAPIS_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.NETHER_QUARTZ_ORE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.REDSTONE_ORE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.REDSTONE_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.GILDED_BLACKSTONE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.BLUE_ICE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.CHISELED_NETHER_BRICKS, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.CLAY, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.COARSE_DIRT, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.CRACKED_NETHER_BRICKS, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.CRACKED_STONE_BRICKS, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.CRYING_OBSIDIAN, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.END_STONE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.GLOWSTONE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.GRAVEL, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.HONEYCOMB_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.ICE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.JUKEBOX, TexturedModel.field_240439_f_);
        this.func_239956_c_(Blocks.LODESTONE, TexturedModel.field_240436_c_);
        this.func_239956_c_(Blocks.MELON, TexturedModel.field_240436_c_);
        this.func_239956_c_(Blocks.NETHER_WART_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.NOTE_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.PACKED_ICE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.OBSIDIAN, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.QUARTZ_BRICKS, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.SEA_LANTERN, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.SHROOMLIGHT, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.SOUL_SAND, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.SOUL_SOIL, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.SPAWNER, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.SPONGE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.SEAGRASS, TexturedModel.field_240450_q_);
        this.func_239866_a_(Items.SEAGRASS);
        this.func_239956_c_(Blocks.TNT, TexturedModel.field_240438_e_);
        this.func_239956_c_(Blocks.TARGET, TexturedModel.field_240436_c_);
        this.func_239956_c_(Blocks.WARPED_WART_BLOCK, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.WET_SPONGE, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, TexturedModel.field_240434_a_);
        this.func_239956_c_(Blocks.CHISELED_QUARTZ_BLOCK, TexturedModel.field_240436_c_.func_240467_a_(BlockModelProvider::lambda$func_239863_a_$32));
        this.func_239956_c_(Blocks.CHISELED_STONE_BRICKS, TexturedModel.field_240434_a_);
        this.func_239992_g_(Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE);
        this.func_239992_g_(Blocks.CHISELED_RED_SANDSTONE, Blocks.RED_SANDSTONE);
        this.func_239956_c_(Blocks.CHISELED_POLISHED_BLACKSTONE, TexturedModel.field_240434_a_);
        this.func_239999_h_(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.GOLD_BLOCK);
        this.func_239999_h_(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.IRON_BLOCK);
        this.func_240022_n_();
        this.func_240030_r_();
        this.func_240032_s_();
        this.func_239921_a_(Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE);
        this.func_240034_t_();
        this.func_240040_w_();
        this.func_240042_x_();
        this.func_240045_z_();
        this.func_239837_A_();
        this.func_239838_B_();
        this.func_240044_y_();
        this.func_239839_C_(Blocks.END_ROD);
        this.func_239840_D_();
        this.func_239841_E_();
        this.func_239842_F_();
        this.func_239843_G_();
        this.func_239844_H_();
        this.func_239845_I_();
        this.func_239846_J_();
        this.func_240020_m_();
        this.func_239847_K_();
        this.func_239848_L_();
        this.func_239849_M_();
        this.func_239850_N_();
        this.func_239851_O_();
        this.func_239852_P_();
        this.func_239853_Q_();
        this.func_239854_R_();
        this.func_239855_S_();
        this.func_239856_T_();
        this.func_239858_V_();
        this.func_239857_U_();
        this.func_239859_W_();
        this.func_239860_X_();
        this.func_240036_u_();
        this.func_239861_Y_();
        this.func_239862_Z_();
        this.func_239923_aa_();
        this.func_239924_ab_();
        this.func_239925_ac_();
        this.func_239926_ad_();
        this.func_239927_ae_();
        this.func_239928_af_();
        this.func_239929_ag_();
        this.func_239932_aj_();
        this.func_240041_w_(Blocks.LADDER);
        this.func_239934_b_(Blocks.LADDER);
        this.func_240041_w_(Blocks.LECTERN);
        this.func_240010_j_(Blocks.TORCH, Blocks.WALL_TORCH);
        this.func_240010_j_(Blocks.SOUL_TORCH, Blocks.SOUL_WALL_TORCH);
        this.func_239875_a_(Blocks.CRAFTING_TABLE, Blocks.OAK_PLANKS, ModelTextures::func_240359_c_);
        this.func_239875_a_(Blocks.FLETCHING_TABLE, Blocks.BIRCH_PLANKS, ModelTextures::func_240363_d_);
        this.func_240031_r_(Blocks.CRIMSON_NYLIUM);
        this.func_240031_r_(Blocks.WARPED_NYLIUM);
        this.func_240029_q_(Blocks.DISPENSER);
        this.func_240029_q_(Blocks.DROPPER);
        this.func_240039_v_(Blocks.LANTERN);
        this.func_240039_v_(Blocks.SOUL_LANTERN);
        this.func_243685_g(Blocks.CHAIN, ModelsResourceUtil.func_240221_a_(Blocks.CHAIN));
        this.func_239882_a_(Blocks.BASALT, TexturedModel.field_240436_c_);
        this.func_239882_a_(Blocks.POLISHED_BASALT, TexturedModel.field_240436_c_);
        this.func_239882_a_(Blocks.BONE_BLOCK, TexturedModel.field_240436_c_);
        this.func_239965_d_(Blocks.DIRT);
        this.func_239965_d_(Blocks.SAND);
        this.func_239965_d_(Blocks.RED_SAND);
        this.func_239953_c_(Blocks.BEDROCK);
        this.func_239883_a_(Blocks.HAY_BLOCK, TexturedModel.field_240436_c_, TexturedModel.field_240437_d_);
        this.func_239883_a_(Blocks.PURPUR_PILLAR, TexturedModel.field_240451_r_, TexturedModel.field_240452_s_);
        this.func_239883_a_(Blocks.QUARTZ_PILLAR, TexturedModel.field_240451_r_, TexturedModel.field_240452_s_);
        this.func_239939_b_(Blocks.LOOM, TexturedModel.field_240441_h_);
        this.func_240038_v_();
        this.func_239887_a_(Blocks.BEE_NEST, ModelTextures::func_240389_w_);
        this.func_239887_a_(Blocks.BEEHIVE, ModelTextures::func_240391_y_);
        this.func_239876_a_(Blocks.BEETROOTS, BlockStateProperties.AGE_0_3, 0, 1, 2, 3);
        this.func_239876_a_(Blocks.CARROTS, BlockStateProperties.AGE_0_7, 0, 0, 1, 1, 2, 2, 2, 3);
        this.func_239876_a_(Blocks.NETHER_WART, BlockStateProperties.AGE_0_3, 0, 1, 1, 2);
        this.func_239876_a_(Blocks.POTATOES, BlockStateProperties.AGE_0_7, 0, 0, 1, 1, 2, 2, 2, 3);
        this.func_239876_a_(Blocks.WHEAT, BlockStateProperties.AGE_0_7, 0, 1, 2, 3, 4, 5, 6, 7);
        this.func_239916_a_(ModelsResourceUtil.func_240223_a_("banner"), Blocks.OAK_PLANKS).func_240050_a_(StockModelShapes.TEMPLATE_BANNER, Blocks.WHITE_BANNER, Blocks.ORANGE_BANNER, Blocks.MAGENTA_BANNER, Blocks.LIGHT_BLUE_BANNER, Blocks.YELLOW_BANNER, Blocks.LIME_BANNER, Blocks.PINK_BANNER, Blocks.GRAY_BANNER, Blocks.LIGHT_GRAY_BANNER, Blocks.CYAN_BANNER, Blocks.PURPLE_BANNER, Blocks.BLUE_BANNER, Blocks.BROWN_BANNER, Blocks.GREEN_BANNER, Blocks.RED_BANNER, Blocks.BLACK_BANNER).func_240052_b_(Blocks.WHITE_WALL_BANNER, Blocks.ORANGE_WALL_BANNER, Blocks.MAGENTA_WALL_BANNER, Blocks.LIGHT_BLUE_WALL_BANNER, Blocks.YELLOW_WALL_BANNER, Blocks.LIME_WALL_BANNER, Blocks.PINK_WALL_BANNER, Blocks.GRAY_WALL_BANNER, Blocks.LIGHT_GRAY_WALL_BANNER, Blocks.CYAN_WALL_BANNER, Blocks.PURPLE_WALL_BANNER, Blocks.BLUE_WALL_BANNER, Blocks.BROWN_WALL_BANNER, Blocks.GREEN_WALL_BANNER, Blocks.RED_WALL_BANNER, Blocks.BLACK_WALL_BANNER);
        this.func_239916_a_(ModelsResourceUtil.func_240223_a_("bed"), Blocks.OAK_PLANKS).func_240052_b_(Blocks.WHITE_BED, Blocks.ORANGE_BED, Blocks.MAGENTA_BED, Blocks.LIGHT_BLUE_BED, Blocks.YELLOW_BED, Blocks.LIME_BED, Blocks.PINK_BED, Blocks.GRAY_BED, Blocks.LIGHT_GRAY_BED, Blocks.CYAN_BED, Blocks.PURPLE_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.GREEN_BED, Blocks.RED_BED, Blocks.BLACK_BED);
        this.func_240015_k_(Blocks.WHITE_BED, Blocks.WHITE_WOOL);
        this.func_240015_k_(Blocks.ORANGE_BED, Blocks.ORANGE_WOOL);
        this.func_240015_k_(Blocks.MAGENTA_BED, Blocks.MAGENTA_WOOL);
        this.func_240015_k_(Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_BLUE_WOOL);
        this.func_240015_k_(Blocks.YELLOW_BED, Blocks.YELLOW_WOOL);
        this.func_240015_k_(Blocks.LIME_BED, Blocks.LIME_WOOL);
        this.func_240015_k_(Blocks.PINK_BED, Blocks.PINK_WOOL);
        this.func_240015_k_(Blocks.GRAY_BED, Blocks.GRAY_WOOL);
        this.func_240015_k_(Blocks.LIGHT_GRAY_BED, Blocks.LIGHT_GRAY_WOOL);
        this.func_240015_k_(Blocks.CYAN_BED, Blocks.CYAN_WOOL);
        this.func_240015_k_(Blocks.PURPLE_BED, Blocks.PURPLE_WOOL);
        this.func_240015_k_(Blocks.BLUE_BED, Blocks.BLUE_WOOL);
        this.func_240015_k_(Blocks.BROWN_BED, Blocks.BROWN_WOOL);
        this.func_240015_k_(Blocks.GREEN_BED, Blocks.GREEN_WOOL);
        this.func_240015_k_(Blocks.RED_BED, Blocks.RED_WOOL);
        this.func_240015_k_(Blocks.BLACK_BED, Blocks.BLACK_WOOL);
        this.func_239916_a_(ModelsResourceUtil.func_240223_a_("skull"), Blocks.SOUL_SAND).func_240050_a_(StockModelShapes.TEMPLATE_SKULL, Blocks.CREEPER_HEAD, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL).func_240051_a_(Blocks.DRAGON_HEAD).func_240052_b_(Blocks.CREEPER_WALL_HEAD, Blocks.DRAGON_WALL_HEAD, Blocks.PLAYER_WALL_HEAD, Blocks.ZOMBIE_WALL_HEAD, Blocks.SKELETON_WALL_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL);
        this.func_240043_x_(Blocks.SHULKER_BOX);
        this.func_240043_x_(Blocks.WHITE_SHULKER_BOX);
        this.func_240043_x_(Blocks.ORANGE_SHULKER_BOX);
        this.func_240043_x_(Blocks.MAGENTA_SHULKER_BOX);
        this.func_240043_x_(Blocks.LIGHT_BLUE_SHULKER_BOX);
        this.func_240043_x_(Blocks.YELLOW_SHULKER_BOX);
        this.func_240043_x_(Blocks.LIME_SHULKER_BOX);
        this.func_240043_x_(Blocks.PINK_SHULKER_BOX);
        this.func_240043_x_(Blocks.GRAY_SHULKER_BOX);
        this.func_240043_x_(Blocks.LIGHT_GRAY_SHULKER_BOX);
        this.func_240043_x_(Blocks.CYAN_SHULKER_BOX);
        this.func_240043_x_(Blocks.PURPLE_SHULKER_BOX);
        this.func_240043_x_(Blocks.BLUE_SHULKER_BOX);
        this.func_240043_x_(Blocks.BROWN_SHULKER_BOX);
        this.func_240043_x_(Blocks.GREEN_SHULKER_BOX);
        this.func_240043_x_(Blocks.RED_SHULKER_BOX);
        this.func_240043_x_(Blocks.BLACK_SHULKER_BOX);
        this.func_239956_c_(Blocks.CONDUIT, TexturedModel.field_240445_l_);
        this.func_239869_a_(Blocks.CONDUIT);
        this.func_239916_a_(ModelsResourceUtil.func_240223_a_("chest"), Blocks.OAK_PLANKS).func_240052_b_(Blocks.CHEST, Blocks.TRAPPED_CHEST);
        this.func_239916_a_(ModelsResourceUtil.func_240223_a_("ender_chest"), Blocks.OBSIDIAN).func_240052_b_(Blocks.ENDER_CHEST);
        this.func_239966_d_(Blocks.END_PORTAL, Blocks.OBSIDIAN).func_240051_a_(Blocks.END_PORTAL, Blocks.END_GATEWAY);
        this.func_239975_e_(Blocks.WHITE_CONCRETE);
        this.func_239975_e_(Blocks.ORANGE_CONCRETE);
        this.func_239975_e_(Blocks.MAGENTA_CONCRETE);
        this.func_239975_e_(Blocks.LIGHT_BLUE_CONCRETE);
        this.func_239975_e_(Blocks.YELLOW_CONCRETE);
        this.func_239975_e_(Blocks.LIME_CONCRETE);
        this.func_239975_e_(Blocks.PINK_CONCRETE);
        this.func_239975_e_(Blocks.GRAY_CONCRETE);
        this.func_239975_e_(Blocks.LIGHT_GRAY_CONCRETE);
        this.func_239975_e_(Blocks.CYAN_CONCRETE);
        this.func_239975_e_(Blocks.PURPLE_CONCRETE);
        this.func_239975_e_(Blocks.BLUE_CONCRETE);
        this.func_239975_e_(Blocks.BROWN_CONCRETE);
        this.func_239975_e_(Blocks.GREEN_CONCRETE);
        this.func_239975_e_(Blocks.RED_CONCRETE);
        this.func_239975_e_(Blocks.BLACK_CONCRETE);
        this.func_239907_a_(TexturedModel.field_240434_a_, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER);
        this.func_239975_e_(Blocks.TERRACOTTA);
        this.func_239975_e_(Blocks.WHITE_TERRACOTTA);
        this.func_239975_e_(Blocks.ORANGE_TERRACOTTA);
        this.func_239975_e_(Blocks.MAGENTA_TERRACOTTA);
        this.func_239975_e_(Blocks.LIGHT_BLUE_TERRACOTTA);
        this.func_239975_e_(Blocks.YELLOW_TERRACOTTA);
        this.func_239975_e_(Blocks.LIME_TERRACOTTA);
        this.func_239975_e_(Blocks.PINK_TERRACOTTA);
        this.func_239975_e_(Blocks.GRAY_TERRACOTTA);
        this.func_239975_e_(Blocks.LIGHT_GRAY_TERRACOTTA);
        this.func_239975_e_(Blocks.CYAN_TERRACOTTA);
        this.func_239975_e_(Blocks.PURPLE_TERRACOTTA);
        this.func_239975_e_(Blocks.BLUE_TERRACOTTA);
        this.func_239975_e_(Blocks.BROWN_TERRACOTTA);
        this.func_239975_e_(Blocks.GREEN_TERRACOTTA);
        this.func_239975_e_(Blocks.RED_TERRACOTTA);
        this.func_239975_e_(Blocks.BLACK_TERRACOTTA);
        this.func_239985_f_(Blocks.GLASS, Blocks.GLASS_PANE);
        this.func_239985_f_(Blocks.WHITE_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.ORANGE_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.MAGENTA_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.YELLOW_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.LIME_STAINED_GLASS, Blocks.LIME_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.PINK_STAINED_GLASS, Blocks.PINK_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.GRAY_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.CYAN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.PURPLE_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.BLUE_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.BROWN_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.GREEN_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.RED_STAINED_GLASS, Blocks.RED_STAINED_GLASS_PANE);
        this.func_239985_f_(Blocks.BLACK_STAINED_GLASS, Blocks.BLACK_STAINED_GLASS_PANE);
        this.func_239948_b_(TexturedModel.field_240443_j_, Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.BLACK_GLAZED_TERRACOTTA);
        this.func_239976_e_(Blocks.WHITE_WOOL, Blocks.WHITE_CARPET);
        this.func_239976_e_(Blocks.ORANGE_WOOL, Blocks.ORANGE_CARPET);
        this.func_239976_e_(Blocks.MAGENTA_WOOL, Blocks.MAGENTA_CARPET);
        this.func_239976_e_(Blocks.LIGHT_BLUE_WOOL, Blocks.LIGHT_BLUE_CARPET);
        this.func_239976_e_(Blocks.YELLOW_WOOL, Blocks.YELLOW_CARPET);
        this.func_239976_e_(Blocks.LIME_WOOL, Blocks.LIME_CARPET);
        this.func_239976_e_(Blocks.PINK_WOOL, Blocks.PINK_CARPET);
        this.func_239976_e_(Blocks.GRAY_WOOL, Blocks.GRAY_CARPET);
        this.func_239976_e_(Blocks.LIGHT_GRAY_WOOL, Blocks.LIGHT_GRAY_CARPET);
        this.func_239976_e_(Blocks.CYAN_WOOL, Blocks.CYAN_CARPET);
        this.func_239976_e_(Blocks.PURPLE_WOOL, Blocks.PURPLE_CARPET);
        this.func_239976_e_(Blocks.BLUE_WOOL, Blocks.BLUE_CARPET);
        this.func_239976_e_(Blocks.BROWN_WOOL, Blocks.BROWN_CARPET);
        this.func_239976_e_(Blocks.GREEN_WOOL, Blocks.GREEN_CARPET);
        this.func_239976_e_(Blocks.RED_WOOL, Blocks.RED_CARPET);
        this.func_239976_e_(Blocks.BLACK_WOOL, Blocks.BLACK_CARPET);
        this.func_239874_a_(Blocks.FERN, Blocks.POTTED_FERN, TintMode.TINTED);
        this.func_239874_a_(Blocks.DANDELION, Blocks.POTTED_DANDELION, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.POPPY, Blocks.POTTED_POPPY, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.BLUE_ORCHID, Blocks.POTTED_BLUE_ORCHID, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.ALLIUM, Blocks.POTTED_ALLIUM, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.AZURE_BLUET, Blocks.POTTED_AZURE_BLUET, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.RED_TULIP, Blocks.POTTED_RED_TULIP, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.ORANGE_TULIP, Blocks.POTTED_ORANGE_TULIP, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.WHITE_TULIP, Blocks.POTTED_WHITE_TULIP, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.PINK_TULIP, Blocks.POTTED_PINK_TULIP, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.OXEYE_DAISY, Blocks.POTTED_OXEYE_DAISY, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.CORNFLOWER, Blocks.POTTED_CORNFLOWER, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.LILY_OF_THE_VALLEY, Blocks.POTTED_LILY_OF_THE_VALLEY, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.WITHER_ROSE, Blocks.POTTED_WITHER_ROSE, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.RED_MUSHROOM, Blocks.POTTED_RED_MUSHROOM, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.BROWN_MUSHROOM, Blocks.POTTED_BROWN_MUSHROOM, TintMode.NOT_TINTED);
        this.func_239874_a_(Blocks.DEAD_BUSH, Blocks.POTTED_DEAD_BUSH, TintMode.NOT_TINTED);
        this.func_240027_p_(Blocks.BROWN_MUSHROOM_BLOCK);
        this.func_240027_p_(Blocks.RED_MUSHROOM_BLOCK);
        this.func_240027_p_(Blocks.MUSHROOM_STEM);
        this.func_239877_a_(Blocks.GRASS, TintMode.TINTED);
        this.func_239937_b_(Blocks.SUGAR_CANE, TintMode.TINTED);
        this.func_239866_a_(Items.SUGAR_CANE);
        this.func_239936_b_(Blocks.KELP, Blocks.KELP_PLANT, TintMode.TINTED);
        this.func_239866_a_(Items.KELP);
        this.func_239869_a_(Blocks.KELP_PLANT);
        this.func_239936_b_(Blocks.WEEPING_VINES, Blocks.WEEPING_VINES_PLANT, TintMode.NOT_TINTED);
        this.func_239936_b_(Blocks.TWISTING_VINES, Blocks.TWISTING_VINES_PLANT, TintMode.NOT_TINTED);
        this.func_239885_a_(Blocks.WEEPING_VINES, "_plant");
        this.func_239869_a_(Blocks.WEEPING_VINES_PLANT);
        this.func_239885_a_(Blocks.TWISTING_VINES, "_plant");
        this.func_239869_a_(Blocks.TWISTING_VINES_PLANT);
        this.func_239878_a_(Blocks.BAMBOO_SAPLING, TintMode.TINTED, ModelTextures.func_240361_c_(ModelTextures.func_240347_a_(Blocks.BAMBOO, "_stage0")));
        this.func_240003_i_();
        this.func_239877_a_(Blocks.COBWEB, TintMode.NOT_TINTED);
        this.func_239955_c_(Blocks.LILAC, TintMode.NOT_TINTED);
        this.func_239955_c_(Blocks.ROSE_BUSH, TintMode.NOT_TINTED);
        this.func_239955_c_(Blocks.PEONY, TintMode.NOT_TINTED);
        this.func_239955_c_(Blocks.TALL_GRASS, TintMode.TINTED);
        this.func_239955_c_(Blocks.LARGE_FERN, TintMode.TINTED);
        this.func_239990_g_();
        this.func_239997_h_();
        this.func_239873_a_(Blocks.TUBE_CORAL, Blocks.DEAD_TUBE_CORAL, Blocks.TUBE_CORAL_BLOCK, Blocks.DEAD_TUBE_CORAL_BLOCK, Blocks.TUBE_CORAL_FAN, Blocks.DEAD_TUBE_CORAL_FAN, Blocks.TUBE_CORAL_WALL_FAN, Blocks.DEAD_TUBE_CORAL_WALL_FAN);
        this.func_239873_a_(Blocks.BRAIN_CORAL, Blocks.DEAD_BRAIN_CORAL, Blocks.BRAIN_CORAL_BLOCK, Blocks.DEAD_BRAIN_CORAL_BLOCK, Blocks.BRAIN_CORAL_FAN, Blocks.DEAD_BRAIN_CORAL_FAN, Blocks.BRAIN_CORAL_WALL_FAN, Blocks.DEAD_BRAIN_CORAL_WALL_FAN);
        this.func_239873_a_(Blocks.BUBBLE_CORAL, Blocks.DEAD_BUBBLE_CORAL, Blocks.BUBBLE_CORAL_BLOCK, Blocks.DEAD_BUBBLE_CORAL_BLOCK, Blocks.BUBBLE_CORAL_FAN, Blocks.DEAD_BUBBLE_CORAL_FAN, Blocks.BUBBLE_CORAL_WALL_FAN, Blocks.DEAD_BUBBLE_CORAL_WALL_FAN);
        this.func_239873_a_(Blocks.FIRE_CORAL, Blocks.DEAD_FIRE_CORAL, Blocks.FIRE_CORAL_BLOCK, Blocks.DEAD_FIRE_CORAL_BLOCK, Blocks.FIRE_CORAL_FAN, Blocks.DEAD_FIRE_CORAL_FAN, Blocks.FIRE_CORAL_WALL_FAN, Blocks.DEAD_FIRE_CORAL_WALL_FAN);
        this.func_239873_a_(Blocks.HORN_CORAL, Blocks.DEAD_HORN_CORAL, Blocks.HORN_CORAL_BLOCK, Blocks.DEAD_HORN_CORAL_BLOCK, Blocks.HORN_CORAL_FAN, Blocks.DEAD_HORN_CORAL_FAN, Blocks.HORN_CORAL_WALL_FAN, Blocks.DEAD_HORN_CORAL_WALL_FAN);
        this.func_239954_c_(Blocks.MELON_STEM, Blocks.ATTACHED_MELON_STEM);
        this.func_239954_c_(Blocks.PUMPKIN_STEM, Blocks.ATTACHED_PUMPKIN_STEM);
        this.func_239984_f_(Blocks.ACACIA_PLANKS).func_240056_a_(Blocks.ACACIA_BUTTON).func_240061_c_(Blocks.ACACIA_FENCE).func_240062_d_(Blocks.ACACIA_FENCE_GATE).func_240063_e_(Blocks.ACACIA_PRESSURE_PLATE).func_240057_a_(Blocks.ACACIA_SIGN, Blocks.ACACIA_WALL_SIGN).func_240064_f_(Blocks.ACACIA_SLAB).func_240065_g_(Blocks.ACACIA_STAIRS);
        this.func_239991_g_(Blocks.ACACIA_DOOR);
        this.func_239998_h_(Blocks.ACACIA_TRAPDOOR);
        this.func_240009_j_(Blocks.ACACIA_LOG).func_240072_c_(Blocks.ACACIA_LOG).func_240070_a_(Blocks.ACACIA_WOOD);
        this.func_240009_j_(Blocks.STRIPPED_ACACIA_LOG).func_240072_c_(Blocks.STRIPPED_ACACIA_LOG).func_240070_a_(Blocks.STRIPPED_ACACIA_WOOD);
        this.func_239874_a_(Blocks.ACACIA_SAPLING, Blocks.POTTED_ACACIA_SAPLING, TintMode.NOT_TINTED);
        this.func_239956_c_(Blocks.ACACIA_LEAVES, TexturedModel.field_240447_n_);
        this.func_239984_f_(Blocks.BIRCH_PLANKS).func_240056_a_(Blocks.BIRCH_BUTTON).func_240061_c_(Blocks.BIRCH_FENCE).func_240062_d_(Blocks.BIRCH_FENCE_GATE).func_240063_e_(Blocks.BIRCH_PRESSURE_PLATE).func_240057_a_(Blocks.BIRCH_SIGN, Blocks.BIRCH_WALL_SIGN).func_240064_f_(Blocks.BIRCH_SLAB).func_240065_g_(Blocks.BIRCH_STAIRS);
        this.func_239991_g_(Blocks.BIRCH_DOOR);
        this.func_239998_h_(Blocks.BIRCH_TRAPDOOR);
        this.func_240009_j_(Blocks.BIRCH_LOG).func_240072_c_(Blocks.BIRCH_LOG).func_240070_a_(Blocks.BIRCH_WOOD);
        this.func_240009_j_(Blocks.STRIPPED_BIRCH_LOG).func_240072_c_(Blocks.STRIPPED_BIRCH_LOG).func_240070_a_(Blocks.STRIPPED_BIRCH_WOOD);
        this.func_239874_a_(Blocks.BIRCH_SAPLING, Blocks.POTTED_BIRCH_SAPLING, TintMode.NOT_TINTED);
        this.func_239956_c_(Blocks.BIRCH_LEAVES, TexturedModel.field_240447_n_);
        this.func_239984_f_(Blocks.OAK_PLANKS).func_240056_a_(Blocks.OAK_BUTTON).func_240061_c_(Blocks.OAK_FENCE).func_240062_d_(Blocks.OAK_FENCE_GATE).func_240063_e_(Blocks.OAK_PRESSURE_PLATE).func_240057_a_(Blocks.OAK_SIGN, Blocks.OAK_WALL_SIGN).func_240064_f_(Blocks.OAK_SLAB).func_240064_f_(Blocks.PETRIFIED_OAK_SLAB).func_240065_g_(Blocks.OAK_STAIRS);
        this.func_239991_g_(Blocks.OAK_DOOR);
        this.func_240004_i_(Blocks.OAK_TRAPDOOR);
        this.func_240009_j_(Blocks.OAK_LOG).func_240072_c_(Blocks.OAK_LOG).func_240070_a_(Blocks.OAK_WOOD);
        this.func_240009_j_(Blocks.STRIPPED_OAK_LOG).func_240072_c_(Blocks.STRIPPED_OAK_LOG).func_240070_a_(Blocks.STRIPPED_OAK_WOOD);
        this.func_239874_a_(Blocks.OAK_SAPLING, Blocks.POTTED_OAK_SAPLING, TintMode.NOT_TINTED);
        this.func_239956_c_(Blocks.OAK_LEAVES, TexturedModel.field_240447_n_);
        this.func_239984_f_(Blocks.SPRUCE_PLANKS).func_240056_a_(Blocks.SPRUCE_BUTTON).func_240061_c_(Blocks.SPRUCE_FENCE).func_240062_d_(Blocks.SPRUCE_FENCE_GATE).func_240063_e_(Blocks.SPRUCE_PRESSURE_PLATE).func_240057_a_(Blocks.SPRUCE_SIGN, Blocks.SPRUCE_WALL_SIGN).func_240064_f_(Blocks.SPRUCE_SLAB).func_240065_g_(Blocks.SPRUCE_STAIRS);
        this.func_239991_g_(Blocks.SPRUCE_DOOR);
        this.func_239998_h_(Blocks.SPRUCE_TRAPDOOR);
        this.func_240009_j_(Blocks.SPRUCE_LOG).func_240072_c_(Blocks.SPRUCE_LOG).func_240070_a_(Blocks.SPRUCE_WOOD);
        this.func_240009_j_(Blocks.STRIPPED_SPRUCE_LOG).func_240072_c_(Blocks.STRIPPED_SPRUCE_LOG).func_240070_a_(Blocks.STRIPPED_SPRUCE_WOOD);
        this.func_239874_a_(Blocks.SPRUCE_SAPLING, Blocks.POTTED_SPRUCE_SAPLING, TintMode.NOT_TINTED);
        this.func_239956_c_(Blocks.SPRUCE_LEAVES, TexturedModel.field_240447_n_);
        this.func_239984_f_(Blocks.DARK_OAK_PLANKS).func_240056_a_(Blocks.DARK_OAK_BUTTON).func_240061_c_(Blocks.DARK_OAK_FENCE).func_240062_d_(Blocks.DARK_OAK_FENCE_GATE).func_240063_e_(Blocks.DARK_OAK_PRESSURE_PLATE).func_240057_a_(Blocks.DARK_OAK_SIGN, Blocks.DARK_OAK_WALL_SIGN).func_240064_f_(Blocks.DARK_OAK_SLAB).func_240065_g_(Blocks.DARK_OAK_STAIRS);
        this.func_239991_g_(Blocks.DARK_OAK_DOOR);
        this.func_240004_i_(Blocks.DARK_OAK_TRAPDOOR);
        this.func_240009_j_(Blocks.DARK_OAK_LOG).func_240072_c_(Blocks.DARK_OAK_LOG).func_240070_a_(Blocks.DARK_OAK_WOOD);
        this.func_240009_j_(Blocks.STRIPPED_DARK_OAK_LOG).func_240072_c_(Blocks.STRIPPED_DARK_OAK_LOG).func_240070_a_(Blocks.STRIPPED_DARK_OAK_WOOD);
        this.func_239874_a_(Blocks.DARK_OAK_SAPLING, Blocks.POTTED_DARK_OAK_SAPLING, TintMode.NOT_TINTED);
        this.func_239956_c_(Blocks.DARK_OAK_LEAVES, TexturedModel.field_240447_n_);
        this.func_239984_f_(Blocks.JUNGLE_PLANKS).func_240056_a_(Blocks.JUNGLE_BUTTON).func_240061_c_(Blocks.JUNGLE_FENCE).func_240062_d_(Blocks.JUNGLE_FENCE_GATE).func_240063_e_(Blocks.JUNGLE_PRESSURE_PLATE).func_240057_a_(Blocks.JUNGLE_SIGN, Blocks.JUNGLE_WALL_SIGN).func_240064_f_(Blocks.JUNGLE_SLAB).func_240065_g_(Blocks.JUNGLE_STAIRS);
        this.func_239991_g_(Blocks.JUNGLE_DOOR);
        this.func_239998_h_(Blocks.JUNGLE_TRAPDOOR);
        this.func_240009_j_(Blocks.JUNGLE_LOG).func_240072_c_(Blocks.JUNGLE_LOG).func_240070_a_(Blocks.JUNGLE_WOOD);
        this.func_240009_j_(Blocks.STRIPPED_JUNGLE_LOG).func_240072_c_(Blocks.STRIPPED_JUNGLE_LOG).func_240070_a_(Blocks.STRIPPED_JUNGLE_WOOD);
        this.func_239874_a_(Blocks.JUNGLE_SAPLING, Blocks.POTTED_JUNGLE_SAPLING, TintMode.NOT_TINTED);
        this.func_239956_c_(Blocks.JUNGLE_LEAVES, TexturedModel.field_240447_n_);
        this.func_239984_f_(Blocks.CRIMSON_PLANKS).func_240056_a_(Blocks.CRIMSON_BUTTON).func_240061_c_(Blocks.CRIMSON_FENCE).func_240062_d_(Blocks.CRIMSON_FENCE_GATE).func_240063_e_(Blocks.CRIMSON_PRESSURE_PLATE).func_240057_a_(Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN).func_240064_f_(Blocks.CRIMSON_SLAB).func_240065_g_(Blocks.CRIMSON_STAIRS);
        this.func_239991_g_(Blocks.CRIMSON_DOOR);
        this.func_239998_h_(Blocks.CRIMSON_TRAPDOOR);
        this.func_240009_j_(Blocks.CRIMSON_STEM).func_240071_b_(Blocks.CRIMSON_STEM).func_240070_a_(Blocks.CRIMSON_HYPHAE);
        this.func_240009_j_(Blocks.STRIPPED_CRIMSON_STEM).func_240071_b_(Blocks.STRIPPED_CRIMSON_STEM).func_240070_a_(Blocks.STRIPPED_CRIMSON_HYPHAE);
        this.func_239874_a_(Blocks.CRIMSON_FUNGUS, Blocks.POTTED_CRIMSON_FUNGUS, TintMode.NOT_TINTED);
        this.func_240019_l_(Blocks.CRIMSON_ROOTS, Blocks.POTTED_CRIMSON_ROOTS);
        this.func_239984_f_(Blocks.WARPED_PLANKS).func_240056_a_(Blocks.WARPED_BUTTON).func_240061_c_(Blocks.WARPED_FENCE).func_240062_d_(Blocks.WARPED_FENCE_GATE).func_240063_e_(Blocks.WARPED_PRESSURE_PLATE).func_240057_a_(Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN).func_240064_f_(Blocks.WARPED_SLAB).func_240065_g_(Blocks.WARPED_STAIRS);
        this.func_239991_g_(Blocks.WARPED_DOOR);
        this.func_239998_h_(Blocks.WARPED_TRAPDOOR);
        this.func_240009_j_(Blocks.WARPED_STEM).func_240071_b_(Blocks.WARPED_STEM).func_240070_a_(Blocks.WARPED_HYPHAE);
        this.func_240009_j_(Blocks.STRIPPED_WARPED_STEM).func_240071_b_(Blocks.STRIPPED_WARPED_STEM).func_240070_a_(Blocks.STRIPPED_WARPED_HYPHAE);
        this.func_239874_a_(Blocks.WARPED_FUNGUS, Blocks.POTTED_WARPED_FUNGUS, TintMode.NOT_TINTED);
        this.func_240019_l_(Blocks.WARPED_ROOTS, Blocks.POTTED_WARPED_ROOTS);
        this.func_239937_b_(Blocks.NETHER_SPROUTS, TintMode.NOT_TINTED);
        this.func_239866_a_(Items.NETHER_SPROUTS);
        this.func_239905_a_(ModelTextures.func_240345_a_(Blocks.STONE)).func_240059_a_(this::lambda$func_239863_a_$33).func_240064_f_(Blocks.STONE_SLAB).func_240063_e_(Blocks.STONE_PRESSURE_PLATE).func_240056_a_(Blocks.STONE_BUTTON).func_240065_g_(Blocks.STONE_STAIRS);
        this.func_239991_g_(Blocks.IRON_DOOR);
        this.func_240004_i_(Blocks.IRON_TRAPDOOR);
        this.func_239984_f_(Blocks.STONE_BRICKS).func_240060_b_(Blocks.STONE_BRICK_WALL).func_240065_g_(Blocks.STONE_BRICK_STAIRS).func_240064_f_(Blocks.STONE_BRICK_SLAB);
        this.func_239984_f_(Blocks.MOSSY_STONE_BRICKS).func_240060_b_(Blocks.MOSSY_STONE_BRICK_WALL).func_240065_g_(Blocks.MOSSY_STONE_BRICK_STAIRS).func_240064_f_(Blocks.MOSSY_STONE_BRICK_SLAB);
        this.func_239984_f_(Blocks.COBBLESTONE).func_240060_b_(Blocks.COBBLESTONE_WALL).func_240065_g_(Blocks.COBBLESTONE_STAIRS).func_240064_f_(Blocks.COBBLESTONE_SLAB);
        this.func_239984_f_(Blocks.MOSSY_COBBLESTONE).func_240060_b_(Blocks.MOSSY_COBBLESTONE_WALL).func_240065_g_(Blocks.MOSSY_COBBLESTONE_STAIRS).func_240064_f_(Blocks.MOSSY_COBBLESTONE_SLAB);
        this.func_239984_f_(Blocks.PRISMARINE).func_240060_b_(Blocks.PRISMARINE_WALL).func_240065_g_(Blocks.PRISMARINE_STAIRS).func_240064_f_(Blocks.PRISMARINE_SLAB);
        this.func_239984_f_(Blocks.PRISMARINE_BRICKS).func_240065_g_(Blocks.PRISMARINE_BRICK_STAIRS).func_240064_f_(Blocks.PRISMARINE_BRICK_SLAB);
        this.func_239984_f_(Blocks.DARK_PRISMARINE).func_240065_g_(Blocks.DARK_PRISMARINE_STAIRS).func_240064_f_(Blocks.DARK_PRISMARINE_SLAB);
        this.func_239967_d_(Blocks.SANDSTONE, TexturedModel.field_240453_t_).func_240060_b_(Blocks.SANDSTONE_WALL).func_240065_g_(Blocks.SANDSTONE_STAIRS).func_240064_f_(Blocks.SANDSTONE_SLAB);
        this.func_239884_a_(Blocks.SMOOTH_SANDSTONE, TexturedModel.func_240463_a_(ModelTextures.func_240347_a_(Blocks.SANDSTONE, "_top"))).func_240064_f_(Blocks.SMOOTH_SANDSTONE_SLAB).func_240065_g_(Blocks.SMOOTH_SANDSTONE_STAIRS);
        this.func_239884_a_(Blocks.CUT_SANDSTONE, TexturedModel.field_240436_c_.get(Blocks.SANDSTONE).func_240460_a_(BlockModelProvider::lambda$func_239863_a_$34)).func_240064_f_(Blocks.CUT_SANDSTONE_SLAB);
        this.func_239967_d_(Blocks.RED_SANDSTONE, TexturedModel.field_240453_t_).func_240060_b_(Blocks.RED_SANDSTONE_WALL).func_240065_g_(Blocks.RED_SANDSTONE_STAIRS).func_240064_f_(Blocks.RED_SANDSTONE_SLAB);
        this.func_239884_a_(Blocks.SMOOTH_RED_SANDSTONE, TexturedModel.func_240463_a_(ModelTextures.func_240347_a_(Blocks.RED_SANDSTONE, "_top"))).func_240064_f_(Blocks.SMOOTH_RED_SANDSTONE_SLAB).func_240065_g_(Blocks.SMOOTH_RED_SANDSTONE_STAIRS);
        this.func_239884_a_(Blocks.CUT_RED_SANDSTONE, TexturedModel.field_240436_c_.get(Blocks.RED_SANDSTONE).func_240460_a_(BlockModelProvider::lambda$func_239863_a_$35)).func_240064_f_(Blocks.CUT_RED_SANDSTONE_SLAB);
        this.func_239984_f_(Blocks.BRICKS).func_240060_b_(Blocks.BRICK_WALL).func_240065_g_(Blocks.BRICK_STAIRS).func_240064_f_(Blocks.BRICK_SLAB);
        this.func_239984_f_(Blocks.NETHER_BRICKS).func_240061_c_(Blocks.NETHER_BRICK_FENCE).func_240060_b_(Blocks.NETHER_BRICK_WALL).func_240065_g_(Blocks.NETHER_BRICK_STAIRS).func_240064_f_(Blocks.NETHER_BRICK_SLAB);
        this.func_239984_f_(Blocks.PURPUR_BLOCK).func_240065_g_(Blocks.PURPUR_STAIRS).func_240064_f_(Blocks.PURPUR_SLAB);
        this.func_239984_f_(Blocks.DIORITE).func_240060_b_(Blocks.DIORITE_WALL).func_240065_g_(Blocks.DIORITE_STAIRS).func_240064_f_(Blocks.DIORITE_SLAB);
        this.func_239984_f_(Blocks.POLISHED_DIORITE).func_240065_g_(Blocks.POLISHED_DIORITE_STAIRS).func_240064_f_(Blocks.POLISHED_DIORITE_SLAB);
        this.func_239984_f_(Blocks.GRANITE).func_240060_b_(Blocks.GRANITE_WALL).func_240065_g_(Blocks.GRANITE_STAIRS).func_240064_f_(Blocks.GRANITE_SLAB);
        this.func_239984_f_(Blocks.POLISHED_GRANITE).func_240065_g_(Blocks.POLISHED_GRANITE_STAIRS).func_240064_f_(Blocks.POLISHED_GRANITE_SLAB);
        this.func_239984_f_(Blocks.ANDESITE).func_240060_b_(Blocks.ANDESITE_WALL).func_240065_g_(Blocks.ANDESITE_STAIRS).func_240064_f_(Blocks.ANDESITE_SLAB);
        this.func_239984_f_(Blocks.POLISHED_ANDESITE).func_240065_g_(Blocks.POLISHED_ANDESITE_STAIRS).func_240064_f_(Blocks.POLISHED_ANDESITE_SLAB);
        this.func_239984_f_(Blocks.END_STONE_BRICKS).func_240060_b_(Blocks.END_STONE_BRICK_WALL).func_240065_g_(Blocks.END_STONE_BRICK_STAIRS).func_240064_f_(Blocks.END_STONE_BRICK_SLAB);
        this.func_239967_d_(Blocks.QUARTZ_BLOCK, TexturedModel.field_240436_c_).func_240065_g_(Blocks.QUARTZ_STAIRS).func_240064_f_(Blocks.QUARTZ_SLAB);
        this.func_239884_a_(Blocks.SMOOTH_QUARTZ, TexturedModel.func_240463_a_(ModelTextures.func_240347_a_(Blocks.QUARTZ_BLOCK, "_bottom"))).func_240065_g_(Blocks.SMOOTH_QUARTZ_STAIRS).func_240064_f_(Blocks.SMOOTH_QUARTZ_SLAB);
        this.func_239984_f_(Blocks.RED_NETHER_BRICKS).func_240064_f_(Blocks.RED_NETHER_BRICK_SLAB).func_240065_g_(Blocks.RED_NETHER_BRICK_STAIRS).func_240060_b_(Blocks.RED_NETHER_BRICK_WALL);
        this.func_239967_d_(Blocks.BLACKSTONE, TexturedModel.field_240454_u_).func_240060_b_(Blocks.BLACKSTONE_WALL).func_240065_g_(Blocks.BLACKSTONE_STAIRS).func_240064_f_(Blocks.BLACKSTONE_SLAB);
        this.func_239984_f_(Blocks.POLISHED_BLACKSTONE_BRICKS).func_240060_b_(Blocks.POLISHED_BLACKSTONE_BRICK_WALL).func_240065_g_(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS).func_240064_f_(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
        this.func_239984_f_(Blocks.POLISHED_BLACKSTONE).func_240060_b_(Blocks.POLISHED_BLACKSTONE_WALL).func_240063_e_(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE).func_240056_a_(Blocks.POLISHED_BLACKSTONE_BUTTON).func_240065_g_(Blocks.POLISHED_BLACKSTONE_STAIRS).func_240064_f_(Blocks.POLISHED_BLACKSTONE_SLAB);
        this.func_240028_q_();
        this.func_240018_l_(Blocks.RAIL);
        this.func_240021_m_(Blocks.POWERED_RAIL);
        this.func_240021_m_(Blocks.DETECTOR_RAIL);
        this.func_240021_m_(Blocks.ACTIVATOR_RAIL);
        this.func_240026_p_();
        this.func_240023_n_(Blocks.COMMAND_BLOCK);
        this.func_240023_n_(Blocks.REPEATING_COMMAND_BLOCK);
        this.func_240023_n_(Blocks.CHAIN_COMMAND_BLOCK);
        this.func_240025_o_(Blocks.ANVIL);
        this.func_240025_o_(Blocks.CHIPPED_ANVIL);
        this.func_240025_o_(Blocks.DAMAGED_ANVIL);
        this.func_240013_k_();
        this.func_240017_l_();
        this.func_239977_e_(Blocks.FURNACE, TexturedModel.field_240440_g_);
        this.func_239977_e_(Blocks.BLAST_FURNACE, TexturedModel.field_240440_g_);
        this.func_239977_e_(Blocks.SMOKER, TexturedModel.field_240441_h_);
        this.func_240024_o_();
        this.func_239931_ai_();
        this.func_240005_i_(Blocks.CHISELED_STONE_BRICKS, Blocks.INFESTED_CHISELED_STONE_BRICKS);
        this.func_240005_i_(Blocks.COBBLESTONE, Blocks.INFESTED_COBBLESTONE);
        this.func_240005_i_(Blocks.CRACKED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS);
        this.func_240005_i_(Blocks.MOSSY_STONE_BRICKS, Blocks.INFESTED_MOSSY_STONE_BRICKS);
        this.func_239930_ah_();
        this.func_240005_i_(Blocks.STONE_BRICKS, Blocks.INFESTED_STONE_BRICKS);
        SpawnEggItem.getEggs().forEach(this::lambda$func_239863_a_$36);
    }

    private void lambda$func_239863_a_$36(SpawnEggItem spawnEggItem) {
        this.func_239867_a_(spawnEggItem, ModelsResourceUtil.func_240224_b_("template_spawn_egg"));
    }

    private static void lambda$func_239863_a_$35(ModelTextures modelTextures) {
        modelTextures.func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240341_C_(Blocks.CUT_RED_SANDSTONE));
    }

    private static void lambda$func_239863_a_$34(ModelTextures modelTextures) {
        modelTextures.func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240341_C_(Blocks.CUT_SANDSTONE));
    }

    private ResourceLocation lambda$func_239863_a_$33(ModelTextures modelTextures) {
        ResourceLocation resourceLocation = StockModelShapes.CUBE_ALL.func_240228_a_(Blocks.STONE, modelTextures, this.field_239835_b_);
        ResourceLocation resourceLocation2 = StockModelShapes.CUBE_MIRRORED_ALL.func_240228_a_(Blocks.STONE, modelTextures, this.field_239835_b_);
        this.field_239834_a_.accept(BlockModelProvider.func_239979_e_(Blocks.STONE, resourceLocation, resourceLocation2));
        return resourceLocation;
    }

    private static void lambda$func_239863_a_$32(ModelTextures modelTextures) {
        modelTextures.func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240341_C_(Blocks.CHISELED_QUARTZ_BLOCK));
    }

    private BlockModelDefinition lambda$func_239932_aj_$31(JigsawOrientation jigsawOrientation) {
        return this.func_239898_a_(jigsawOrientation, BlockModelDefinition.getNewModelDefinition());
    }

    private static BlockModelDefinition lambda$func_239931_ai_$30(ResourceLocation[] resourceLocationArray, Integer n) {
        return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocationArray[n]);
    }

    private List lambda$func_239927_ae_$29(Integer n, Integer n2) {
        return Arrays.asList(BlockModelProvider.func_239915_a_(this.func_239912_a_(n, n2)));
    }

    private static BlockModelDefinition lambda$func_239926_ad_$28(Boolean bl, Boolean bl2) {
        return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.TRIPWIRE_HOOK, (bl != false ? "_attached" : "") + (bl2 != false ? "_on" : "")));
    }

    private BlockModelDefinition lambda$func_239924_ab_$27(Integer n) {
        return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, this.func_239886_a_(Blocks.SWEET_BERRY_BUSH, "_stage" + n, StockModelShapes.CROSS, ModelTextures::func_240361_c_));
    }

    private BlockModelDefinition lambda$func_239923_aa_$26(StructureMode structureMode) {
        return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, this.func_239886_a_(Blocks.STRUCTURE_BLOCK, "_" + structureMode.getString(), StockModelShapes.CUBE_ALL, ModelTextures::func_240356_b_));
    }

    private static BlockModelDefinition lambda$func_239861_Y_$25(ResourceLocation resourceLocation, Integer n) {
        return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, n < 8 ? ModelsResourceUtil.func_240222_a_(Blocks.SNOW, "_height" + n * 2) : resourceLocation);
    }

    private static BlockModelDefinition lambda$func_239859_W_$24(Integer n, Boolean bl, Boolean bl2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('_').append(n).append("tick");
        if (bl2.booleanValue()) {
            stringBuilder.append("_on");
        }
        if (bl.booleanValue()) {
            stringBuilder.append("_locked");
        }
        return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelTextures.func_240347_a_(Blocks.REPEATER, stringBuilder.toString()));
    }

    private static void lambda$func_239844_H_$23(ResourceLocation resourceLocation, ModelTextures modelTextures) {
        modelTextures.func_240349_a_(StockTextureAliases.BOTTOM, resourceLocation);
    }

    private static void lambda$func_239844_H_$22(ResourceLocation resourceLocation, ModelTextures modelTextures) {
        modelTextures.func_240349_a_(StockTextureAliases.BOTTOM, resourceLocation);
    }

    private static BlockModelDefinition lambda$func_239842_F_$21(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270);
    }

    private static BlockModelDefinition lambda$func_239842_F_$20(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180);
    }

    private static BlockModelDefinition lambda$func_239842_F_$19(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90);
    }

    private static BlockModelDefinition lambda$func_239842_F_$18(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition;
    }

    private static BlockModelDefinition lambda$func_239842_F_$17(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition;
    }

    private static BlockModelDefinition lambda$func_239841_E_$16(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition;
    }

    private static BlockModelDefinition lambda$func_239841_E_$15(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R270);
    }

    private static BlockModelDefinition lambda$func_239841_E_$14(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R180);
    }

    private static BlockModelDefinition lambda$func_239841_E_$13(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition.replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90);
    }

    private static BlockModelDefinition lambda$func_239841_E_$12(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition;
    }

    private static BlockModelDefinition lambda$func_239841_E_$11(BlockModelDefinition blockModelDefinition) {
        return blockModelDefinition;
    }

    private static BlockModelDefinition lambda$func_239914_a_$10(ResourceLocation resourceLocation) {
        return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation);
    }

    private static ModelTextures lambda$func_240042_x_$9(ModelTextures modelTextures, ResourceLocation resourceLocation) {
        return modelTextures.func_240360_c_(StockTextureAliases.TEXTURE, resourceLocation);
    }

    private static void lambda$func_239977_e_$8(ResourceLocation resourceLocation, ModelTextures modelTextures) {
        modelTextures.func_240349_a_(StockTextureAliases.FRONT, resourceLocation);
    }

    private BlockModelDefinition lambda$func_239876_a_$7(int[] nArray, Int2ObjectMap int2ObjectMap, Block block, Integer n) {
        int n2 = nArray[n];
        ResourceLocation resourceLocation = int2ObjectMap.computeIfAbsent(n2, arg_0 -> this.lambda$func_239876_a_$6(block, n2, arg_0));
        return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, resourceLocation);
    }

    private ResourceLocation lambda$func_239876_a_$6(Block block, int n, int n2) {
        return this.func_239886_a_(block, "_stage" + n, StockModelShapes.CROP, ModelTextures::func_240370_g_);
    }

    private static BlockModelDefinition lambda$func_239895_a_$5(Comparable comparable, BlockModelDefinition blockModelDefinition, BlockModelDefinition blockModelDefinition2, Comparable comparable2) {
        boolean bl = comparable2.compareTo(comparable) >= 0;
        return bl ? blockModelDefinition : blockModelDefinition2;
    }

    private static void lambda$func_240013_k_$4(ResourceLocation resourceLocation, ModelTextures modelTextures) {
        modelTextures.func_240349_a_(StockTextureAliases.TOP, resourceLocation);
    }

    private static BlockModelDefinition lambda$func_239864_a_$3(String string, int n) {
        return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, ModelsResourceUtil.func_240222_a_(Blocks.BAMBOO, n + string));
    }

    private static ModelTextures lambda$func_240023_n_$2(ModelTextures modelTextures, ResourceLocation resourceLocation) {
        return modelTextures.func_240360_c_(StockTextureAliases.SIDE, resourceLocation);
    }

    private static BlockModelDefinition lambda$func_240021_m_$1(ResourceLocation resourceLocation, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3, ResourceLocation resourceLocation4, ResourceLocation resourceLocation5, ResourceLocation resourceLocation6, Boolean bl, RailShape railShape) {
        switch (railShape) {
            case NORTH_SOUTH: {
                return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, bl != false ? resourceLocation : resourceLocation2);
            }
            case EAST_WEST: {
                return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, bl != false ? resourceLocation : resourceLocation2).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90);
            }
            case ASCENDING_EAST: {
                return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, bl != false ? resourceLocation3 : resourceLocation4).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90);
            }
            case ASCENDING_WEST: {
                return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, bl != false ? resourceLocation5 : resourceLocation6).replaceInfoValue(BlockModelFields.field_240201_b_, BlockModelFields.Rotation.R90);
            }
            case ASCENDING_NORTH: {
                return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, bl != false ? resourceLocation3 : resourceLocation4);
            }
            case ASCENDING_SOUTH: {
                return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, bl != false ? resourceLocation5 : resourceLocation6);
            }
        }
        throw new UnsupportedOperationException("Fix you generator!");
    }

    private BlockModelDefinition lambda$func_239954_c_$0(Block block, ModelTextures modelTextures, Integer n) {
        return BlockModelDefinition.getNewModelDefinition().replaceInfoValue(BlockModelFields.field_240202_c_, StockModelShapes.STEM_GROWTH_STAGES[n].func_240228_a_(block, modelTextures, this.field_239835_b_));
    }

    class BlockTextureCombiner {
        private final ModelTextures field_240054_b_;
        @Nullable
        private ResourceLocation field_240055_c_;
        final BlockModelProvider this$0;

        public BlockTextureCombiner(BlockModelProvider blockModelProvider, ModelTextures modelTextures) {
            this.this$0 = blockModelProvider;
            this.field_240054_b_ = modelTextures;
        }

        public BlockTextureCombiner func_240058_a_(Block block, ModelsUtil modelsUtil) {
            this.field_240055_c_ = modelsUtil.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, this.field_240055_c_));
            return this;
        }

        public BlockTextureCombiner func_240059_a_(Function<ModelTextures, ResourceLocation> function) {
            this.field_240055_c_ = function.apply(this.field_240054_b_);
            return this;
        }

        public BlockTextureCombiner func_240056_a_(Block block) {
            ResourceLocation resourceLocation = StockModelShapes.BUTTON.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation2 = StockModelShapes.BUTTON_PRESSED.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_239987_f_(block, resourceLocation, resourceLocation2));
            ResourceLocation resourceLocation3 = StockModelShapes.BUTTON_INVENTORY.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.func_239957_c_(block, resourceLocation3);
            return this;
        }

        public BlockTextureCombiner func_240060_b_(Block block) {
            ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_WALL_POST.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_WALL_SIDE.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation3 = StockModelShapes.TEMPLATE_WALL_SIDE_TALL.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_239970_d_(block, resourceLocation, resourceLocation2, resourceLocation3));
            ResourceLocation resourceLocation4 = StockModelShapes.WALL_INVENTORY.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.func_239957_c_(block, resourceLocation4);
            return this;
        }

        public BlockTextureCombiner func_240061_c_(Block block) {
            ResourceLocation resourceLocation = StockModelShapes.FENCE_POST.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation2 = StockModelShapes.FENCE_SIDE.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_239994_g_(block, resourceLocation, resourceLocation2));
            ResourceLocation resourceLocation3 = StockModelShapes.FENCE_INVENTORY.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.func_239957_c_(block, resourceLocation3);
            return this;
        }

        public BlockTextureCombiner func_240062_d_(Block block) {
            ResourceLocation resourceLocation = StockModelShapes.TEMPLATE_FENCE_GATE_OPEN.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation2 = StockModelShapes.TEMPLATE_FENCE_GATE.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation3 = StockModelShapes.TEMPLATE_FENCE_GATE_WALL_OPEN.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation4 = StockModelShapes.TEMPLATE_FENCE_GATE_WALL.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_239960_c_(block, resourceLocation, resourceLocation2, resourceLocation3, resourceLocation4));
            return this;
        }

        public BlockTextureCombiner func_240063_e_(Block block) {
            ResourceLocation resourceLocation = StockModelShapes.PRESSURE_PLATE_UP.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation2 = StockModelShapes.PRESSURE_PLATE_DOWN.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_240006_i_(block, resourceLocation, resourceLocation2));
            return this;
        }

        public BlockTextureCombiner func_240057_a_(Block block, Block block2) {
            ResourceLocation resourceLocation = StockModelShapes.PARTICLE.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, resourceLocation));
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block2, resourceLocation));
            this.this$0.func_239866_a_(block.asItem());
            this.this$0.func_239869_a_(block2);
            return this;
        }

        public BlockTextureCombiner func_240064_f_(Block block) {
            if (this.field_240055_c_ == null) {
                throw new IllegalStateException("Full block not generated yet");
            }
            ResourceLocation resourceLocation = StockModelShapes.SLAB.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation2 = StockModelShapes.SLAB_TOP.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_240001_h_(block, resourceLocation, resourceLocation2, this.field_240055_c_));
            return this;
        }

        public BlockTextureCombiner func_240065_g_(Block block) {
            ResourceLocation resourceLocation = StockModelShapes.INNER_STAIRS.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation2 = StockModelShapes.STAIRS.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation3 = StockModelShapes.OUTER_STAIRS.func_240228_a_(block, this.field_240054_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_239980_e_(block, resourceLocation, resourceLocation2, resourceLocation3));
            return this;
        }
    }

    class LogsVariantHelper {
        private final ModelTextures field_240069_b_;
        final BlockModelProvider this$0;

        public LogsVariantHelper(BlockModelProvider blockModelProvider, ModelTextures modelTextures) {
            this.this$0 = blockModelProvider;
            this.field_240069_b_ = modelTextures;
        }

        public LogsVariantHelper func_240070_a_(Block block) {
            ModelTextures modelTextures = this.field_240069_b_.func_240360_c_(StockTextureAliases.END, this.field_240069_b_.func_240348_a_(StockTextureAliases.SIDE));
            ResourceLocation resourceLocation = StockModelShapes.CUBE_COLUMN.func_240228_a_(block, modelTextures, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_239986_f_(block, resourceLocation));
            return this;
        }

        public LogsVariantHelper func_240071_b_(Block block) {
            ResourceLocation resourceLocation = StockModelShapes.CUBE_COLUMN.func_240228_a_(block, this.field_240069_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_239986_f_(block, resourceLocation));
            return this;
        }

        public LogsVariantHelper func_240072_c_(Block block) {
            ResourceLocation resourceLocation = StockModelShapes.CUBE_COLUMN.func_240228_a_(block, this.field_240069_b_, this.this$0.field_239835_b_);
            ResourceLocation resourceLocation2 = StockModelShapes.CUBE_COLUMN_HORIZONTAL.func_240228_a_(block, this.field_240069_b_, this.this$0.field_239835_b_);
            this.this$0.field_239834_a_.accept(BlockModelProvider.func_240000_h_(block, resourceLocation, resourceLocation2));
            return this;
        }
    }

    static enum TintMode {
        TINTED,
        NOT_TINTED;


        public ModelsUtil func_240066_a_() {
            return this == TINTED ? StockModelShapes.TINTED_CROSS : StockModelShapes.CROSS;
        }

        public ModelsUtil func_240067_b_() {
            return this == TINTED ? StockModelShapes.TINTED_FLOWER_POT_CROSS : StockModelShapes.FLOWER_POT_CROSS;
        }
    }

    class BreakParticleHelper {
        private final ResourceLocation field_240049_b_;
        final BlockModelProvider this$0;

        public BreakParticleHelper(BlockModelProvider blockModelProvider, ResourceLocation resourceLocation, Block block) {
            this.this$0 = blockModelProvider;
            this.field_240049_b_ = StockModelShapes.PARTICLE.func_240234_a_(resourceLocation, ModelTextures.func_240383_q_(block), blockModelProvider.field_239835_b_);
        }

        public BreakParticleHelper func_240051_a_(Block ... blockArray) {
            for (Block block : blockArray) {
                this.this$0.field_239834_a_.accept(BlockModelProvider.func_239978_e_(block, this.field_240049_b_));
            }
            return this;
        }

        public BreakParticleHelper func_240052_b_(Block ... blockArray) {
            for (Block block : blockArray) {
                this.this$0.func_239869_a_(block);
            }
            return this.func_240051_a_(blockArray);
        }

        public BreakParticleHelper func_240050_a_(ModelsUtil modelsUtil, Block ... blockArray) {
            for (Block block : blockArray) {
                modelsUtil.func_240234_a_(ModelsResourceUtil.func_240219_a_(block.asItem()), ModelTextures.func_240383_q_(block), this.this$0.field_239835_b_);
            }
            return this.func_240051_a_(blockArray);
        }
    }
}


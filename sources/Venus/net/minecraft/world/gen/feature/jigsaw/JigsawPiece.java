/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.jigsaw.EmptyJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.FeatureJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.LegacySingleJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.ListJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class JigsawPiece {
    public static final Codec<JigsawPiece> field_236847_e_ = Registry.STRUCTURE_POOL_ELEMENT.dispatch("element_type", JigsawPiece::getType, IJigsawDeserializer::codec);
    @Nullable
    private volatile JigsawPattern.PlacementBehaviour projection;

    protected static <E extends JigsawPiece> RecordCodecBuilder<E, JigsawPattern.PlacementBehaviour> func_236848_d_() {
        return ((MapCodec)JigsawPattern.PlacementBehaviour.field_236858_c_.fieldOf("projection")).forGetter(JigsawPiece::getPlacementBehaviour);
    }

    protected JigsawPiece(JigsawPattern.PlacementBehaviour placementBehaviour) {
        this.projection = placementBehaviour;
    }

    public abstract List<Template.BlockInfo> getJigsawBlocks(TemplateManager var1, BlockPos var2, Rotation var3, Random var4);

    public abstract MutableBoundingBox getBoundingBox(TemplateManager var1, BlockPos var2, Rotation var3);

    public abstract boolean func_230378_a_(TemplateManager var1, ISeedReader var2, StructureManager var3, ChunkGenerator var4, BlockPos var5, BlockPos var6, Rotation var7, MutableBoundingBox var8, Random var9, boolean var10);

    public abstract IJigsawDeserializer<?> getType();

    public void handleDataMarker(IWorld iWorld, Template.BlockInfo blockInfo, BlockPos blockPos, Rotation rotation, Random random2, MutableBoundingBox mutableBoundingBox) {
    }

    public JigsawPiece setPlacementBehaviour(JigsawPattern.PlacementBehaviour placementBehaviour) {
        this.projection = placementBehaviour;
        return this;
    }

    public JigsawPattern.PlacementBehaviour getPlacementBehaviour() {
        JigsawPattern.PlacementBehaviour placementBehaviour = this.projection;
        if (placementBehaviour == null) {
            throw new IllegalStateException();
        }
        return placementBehaviour;
    }

    public int getGroundLevelDelta() {
        return 0;
    }

    public static Function<JigsawPattern.PlacementBehaviour, EmptyJigsawPiece> func_242864_g() {
        return JigsawPiece::lambda$func_242864_g$0;
    }

    public static Function<JigsawPattern.PlacementBehaviour, LegacySingleJigsawPiece> func_242849_a(String string) {
        return arg_0 -> JigsawPiece.lambda$func_242849_a$2(string, arg_0);
    }

    public static Function<JigsawPattern.PlacementBehaviour, LegacySingleJigsawPiece> func_242851_a(String string, StructureProcessorList structureProcessorList) {
        return arg_0 -> JigsawPiece.lambda$func_242851_a$4(string, structureProcessorList, arg_0);
    }

    public static Function<JigsawPattern.PlacementBehaviour, SingleJigsawPiece> func_242859_b(String string) {
        return arg_0 -> JigsawPiece.lambda$func_242859_b$6(string, arg_0);
    }

    public static Function<JigsawPattern.PlacementBehaviour, SingleJigsawPiece> func_242861_b(String string, StructureProcessorList structureProcessorList) {
        return arg_0 -> JigsawPiece.lambda$func_242861_b$8(string, structureProcessorList, arg_0);
    }

    public static Function<JigsawPattern.PlacementBehaviour, FeatureJigsawPiece> func_242845_a(ConfiguredFeature<?, ?> configuredFeature) {
        return arg_0 -> JigsawPiece.lambda$func_242845_a$10(configuredFeature, arg_0);
    }

    public static Function<JigsawPattern.PlacementBehaviour, ListJigsawPiece> func_242853_a(List<Function<JigsawPattern.PlacementBehaviour, ? extends JigsawPiece>> list) {
        return arg_0 -> JigsawPiece.lambda$func_242853_a$12(list, arg_0);
    }

    private static ListJigsawPiece lambda$func_242853_a$12(List list, JigsawPattern.PlacementBehaviour placementBehaviour) {
        return new ListJigsawPiece(list.stream().map(arg_0 -> JigsawPiece.lambda$func_242853_a$11(placementBehaviour, arg_0)).collect(Collectors.toList()), placementBehaviour);
    }

    private static JigsawPiece lambda$func_242853_a$11(JigsawPattern.PlacementBehaviour placementBehaviour, Function function) {
        return (JigsawPiece)function.apply(placementBehaviour);
    }

    private static FeatureJigsawPiece lambda$func_242845_a$10(ConfiguredFeature configuredFeature, JigsawPattern.PlacementBehaviour placementBehaviour) {
        return new FeatureJigsawPiece(() -> JigsawPiece.lambda$func_242845_a$9(configuredFeature), placementBehaviour);
    }

    private static ConfiguredFeature lambda$func_242845_a$9(ConfiguredFeature configuredFeature) {
        return configuredFeature;
    }

    private static SingleJigsawPiece lambda$func_242861_b$8(String string, StructureProcessorList structureProcessorList, JigsawPattern.PlacementBehaviour placementBehaviour) {
        return new SingleJigsawPiece(Either.left(new ResourceLocation(string)), () -> JigsawPiece.lambda$func_242861_b$7(structureProcessorList), placementBehaviour);
    }

    private static StructureProcessorList lambda$func_242861_b$7(StructureProcessorList structureProcessorList) {
        return structureProcessorList;
    }

    private static SingleJigsawPiece lambda$func_242859_b$6(String string, JigsawPattern.PlacementBehaviour placementBehaviour) {
        return new SingleJigsawPiece(Either.left(new ResourceLocation(string)), JigsawPiece::lambda$func_242859_b$5, placementBehaviour);
    }

    private static StructureProcessorList lambda$func_242859_b$5() {
        return ProcessorLists.field_244101_a;
    }

    private static LegacySingleJigsawPiece lambda$func_242851_a$4(String string, StructureProcessorList structureProcessorList, JigsawPattern.PlacementBehaviour placementBehaviour) {
        return new LegacySingleJigsawPiece(Either.left(new ResourceLocation(string)), () -> JigsawPiece.lambda$func_242851_a$3(structureProcessorList), placementBehaviour);
    }

    private static StructureProcessorList lambda$func_242851_a$3(StructureProcessorList structureProcessorList) {
        return structureProcessorList;
    }

    private static LegacySingleJigsawPiece lambda$func_242849_a$2(String string, JigsawPattern.PlacementBehaviour placementBehaviour) {
        return new LegacySingleJigsawPiece(Either.left(new ResourceLocation(string)), JigsawPiece::lambda$func_242849_a$1, placementBehaviour);
    }

    private static StructureProcessorList lambda$func_242849_a$1() {
        return ProcessorLists.field_244101_a;
    }

    private static EmptyJigsawPiece lambda$func_242864_g$0(JigsawPattern.PlacementBehaviour placementBehaviour) {
        return EmptyJigsawPiece.INSTANCE;
    }
}


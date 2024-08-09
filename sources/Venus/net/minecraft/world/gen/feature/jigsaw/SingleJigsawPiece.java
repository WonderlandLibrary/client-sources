/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.JigsawReplacementStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class SingleJigsawPiece
extends JigsawPiece {
    private static final Codec<Either<ResourceLocation, Template>> field_236837_a_ = Codec.of(SingleJigsawPiece::func_236840_a_, ResourceLocation.CODEC.map(Either::left));
    public static final Codec<SingleJigsawPiece> field_236838_b_ = RecordCodecBuilder.create(SingleJigsawPiece::lambda$static$0);
    protected final Either<ResourceLocation, Template> field_236839_c_;
    protected final Supplier<StructureProcessorList> processors;

    private static <T> DataResult<T> func_236840_a_(Either<ResourceLocation, Template> either, DynamicOps<T> dynamicOps, T t) {
        Optional<ResourceLocation> optional = either.left();
        return !optional.isPresent() ? DataResult.error("Can not serialize a runtime pool element") : ResourceLocation.CODEC.encode(optional.get(), dynamicOps, t);
    }

    protected static <E extends SingleJigsawPiece> RecordCodecBuilder<E, Supplier<StructureProcessorList>> func_236844_b_() {
        return ((MapCodec)IStructureProcessorType.field_242922_m.fieldOf("processors")).forGetter(SingleJigsawPiece::lambda$func_236844_b_$1);
    }

    protected static <E extends SingleJigsawPiece> RecordCodecBuilder<E, Either<ResourceLocation, Template>> func_236846_c_() {
        return ((MapCodec)field_236837_a_.fieldOf("location")).forGetter(SingleJigsawPiece::lambda$func_236846_c_$2);
    }

    protected SingleJigsawPiece(Either<ResourceLocation, Template> either, Supplier<StructureProcessorList> supplier, JigsawPattern.PlacementBehaviour placementBehaviour) {
        super(placementBehaviour);
        this.field_236839_c_ = either;
        this.processors = supplier;
    }

    public SingleJigsawPiece(Template template) {
        this(Either.right(template), SingleJigsawPiece::lambda$new$3, JigsawPattern.PlacementBehaviour.RIGID);
    }

    private Template func_236843_a_(TemplateManager templateManager) {
        return this.field_236839_c_.map(templateManager::getTemplateDefaulted, Function.identity());
    }

    public List<Template.BlockInfo> getDataMarkers(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, boolean bl) {
        Template template = this.func_236843_a_(templateManager);
        List<Template.BlockInfo> list = template.func_215386_a(blockPos, new PlacementSettings().setRotation(rotation), Blocks.STRUCTURE_BLOCK, bl);
        ArrayList<Template.BlockInfo> arrayList = Lists.newArrayList();
        for (Template.BlockInfo blockInfo : list) {
            StructureMode structureMode;
            if (blockInfo.nbt == null || (structureMode = StructureMode.valueOf(blockInfo.nbt.getString("mode"))) != StructureMode.DATA) continue;
            arrayList.add(blockInfo);
        }
        return arrayList;
    }

    @Override
    public List<Template.BlockInfo> getJigsawBlocks(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, Random random2) {
        Template template = this.func_236843_a_(templateManager);
        List<Template.BlockInfo> list = template.func_215386_a(blockPos, new PlacementSettings().setRotation(rotation), Blocks.JIGSAW, false);
        Collections.shuffle(list, random2);
        return list;
    }

    @Override
    public MutableBoundingBox getBoundingBox(TemplateManager templateManager, BlockPos blockPos, Rotation rotation) {
        Template template = this.func_236843_a_(templateManager);
        return template.getMutableBoundingBox(new PlacementSettings().setRotation(rotation), blockPos);
    }

    @Override
    public boolean func_230378_a_(TemplateManager templateManager, ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockPos blockPos2, Rotation rotation, MutableBoundingBox mutableBoundingBox, Random random2, boolean bl) {
        PlacementSettings placementSettings;
        Template template = this.func_236843_a_(templateManager);
        if (!template.func_237146_a_(iSeedReader, blockPos, blockPos2, placementSettings = this.func_230379_a_(rotation, mutableBoundingBox, bl), random2, 1)) {
            return true;
        }
        for (Template.BlockInfo blockInfo : Template.func_237145_a_(iSeedReader, blockPos, blockPos2, placementSettings, this.getDataMarkers(templateManager, blockPos, rotation, true))) {
            this.handleDataMarker(iSeedReader, blockInfo, blockPos, rotation, random2, mutableBoundingBox);
        }
        return false;
    }

    protected PlacementSettings func_230379_a_(Rotation rotation, MutableBoundingBox mutableBoundingBox, boolean bl) {
        PlacementSettings placementSettings = new PlacementSettings();
        placementSettings.setBoundingBox(mutableBoundingBox);
        placementSettings.setRotation(rotation);
        placementSettings.func_215223_c(false);
        placementSettings.setIgnoreEntities(true);
        placementSettings.addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
        placementSettings.func_237133_d_(false);
        if (!bl) {
            placementSettings.addProcessor(JigsawReplacementStructureProcessor.INSTANCE);
        }
        this.processors.get().func_242919_a().forEach(placementSettings::addProcessor);
        this.getPlacementBehaviour().getStructureProcessors().forEach(placementSettings::addProcessor);
        return placementSettings;
    }

    @Override
    public IJigsawDeserializer<?> getType() {
        return IJigsawDeserializer.SINGLE_POOL_ELEMENT;
    }

    public String toString() {
        return "Single[" + this.field_236839_c_ + "]";
    }

    private static StructureProcessorList lambda$new$3() {
        return ProcessorLists.field_244101_a;
    }

    private static Either lambda$func_236846_c_$2(SingleJigsawPiece singleJigsawPiece) {
        return singleJigsawPiece.field_236839_c_;
    }

    private static Supplier lambda$func_236844_b_$1(SingleJigsawPiece singleJigsawPiece) {
        return singleJigsawPiece.processors;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(SingleJigsawPiece.func_236846_c_(), SingleJigsawPiece.func_236844_b_(), SingleJigsawPiece.func_236848_d_()).apply(instance, SingleJigsawPiece::new);
    }
}


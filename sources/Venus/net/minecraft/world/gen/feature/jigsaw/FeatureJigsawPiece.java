/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JigsawBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawOrientation;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class FeatureJigsawPiece
extends JigsawPiece {
    public static final Codec<FeatureJigsawPiece> field_236816_a_ = RecordCodecBuilder.create(FeatureJigsawPiece::lambda$static$1);
    private final Supplier<ConfiguredFeature<?, ?>> configuredFeature;
    private final CompoundNBT nbt;

    protected FeatureJigsawPiece(Supplier<ConfiguredFeature<?, ?>> supplier, JigsawPattern.PlacementBehaviour placementBehaviour) {
        super(placementBehaviour);
        this.configuredFeature = supplier;
        this.nbt = this.writeNBT();
    }

    private CompoundNBT writeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("name", "minecraft:bottom");
        compoundNBT.putString("final_state", "minecraft:air");
        compoundNBT.putString("pool", "minecraft:empty");
        compoundNBT.putString("target", "minecraft:empty");
        compoundNBT.putString("joint", JigsawTileEntity.OrientationType.ROLLABLE.getString());
        return compoundNBT;
    }

    public BlockPos getSize(TemplateManager templateManager, Rotation rotation) {
        return BlockPos.ZERO;
    }

    @Override
    public List<Template.BlockInfo> getJigsawBlocks(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, Random random2) {
        ArrayList<Template.BlockInfo> arrayList = Lists.newArrayList();
        arrayList.add(new Template.BlockInfo(blockPos, (BlockState)Blocks.JIGSAW.getDefaultState().with(JigsawBlock.ORIENTATION, JigsawOrientation.func_239641_a_(Direction.DOWN, Direction.SOUTH)), this.nbt));
        return arrayList;
    }

    @Override
    public MutableBoundingBox getBoundingBox(TemplateManager templateManager, BlockPos blockPos, Rotation rotation) {
        BlockPos blockPos2 = this.getSize(templateManager, rotation);
        return new MutableBoundingBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + blockPos2.getX(), blockPos.getY() + blockPos2.getY(), blockPos.getZ() + blockPos2.getZ());
    }

    @Override
    public boolean func_230378_a_(TemplateManager templateManager, ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockPos blockPos2, Rotation rotation, MutableBoundingBox mutableBoundingBox, Random random2, boolean bl) {
        return this.configuredFeature.get().func_242765_a(iSeedReader, chunkGenerator, random2, blockPos);
    }

    @Override
    public IJigsawDeserializer<?> getType() {
        return IJigsawDeserializer.FEATURE_POOL_ELEMENT;
    }

    public String toString() {
        return "Feature[" + Registry.FEATURE.getKey((Feature<?>)this.configuredFeature.get().func_242766_b()) + "]";
    }

    private static App lambda$static$1(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)ConfiguredFeature.field_236264_b_.fieldOf("feature")).forGetter(FeatureJigsawPiece::lambda$static$0), FeatureJigsawPiece.func_236848_d_()).apply(instance, FeatureJigsawPiece::new);
    }

    private static Supplier lambda$static$0(FeatureJigsawPiece featureJigsawPiece) {
        return featureJigsawPiece.configuredFeature;
    }
}


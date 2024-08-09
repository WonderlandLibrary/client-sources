/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.feature.template.Template;

public class LegacySingleJigsawPiece
extends SingleJigsawPiece {
    public static final Codec<LegacySingleJigsawPiece> field_236832_a_ = RecordCodecBuilder.create(LegacySingleJigsawPiece::lambda$static$0);

    protected LegacySingleJigsawPiece(Either<ResourceLocation, Template> either, Supplier<StructureProcessorList> supplier, JigsawPattern.PlacementBehaviour placementBehaviour) {
        super(either, supplier, placementBehaviour);
    }

    @Override
    protected PlacementSettings func_230379_a_(Rotation rotation, MutableBoundingBox mutableBoundingBox, boolean bl) {
        PlacementSettings placementSettings = super.func_230379_a_(rotation, mutableBoundingBox, bl);
        placementSettings.removeProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
        placementSettings.addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
        return placementSettings;
    }

    @Override
    public IJigsawDeserializer<?> getType() {
        return IJigsawDeserializer.field_236849_e_;
    }

    @Override
    public String toString() {
        return "LegacySingle[" + this.field_236839_c_ + "]";
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(LegacySingleJigsawPiece.func_236846_c_(), LegacySingleJigsawPiece.func_236844_b_(), LegacySingleJigsawPiece.func_236848_d_()).apply(instance, LegacySingleJigsawPiece::new);
    }
}


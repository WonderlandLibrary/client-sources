/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ListJigsawPiece
extends JigsawPiece {
    public static final Codec<ListJigsawPiece> field_236834_a_ = RecordCodecBuilder.create(ListJigsawPiece::lambda$static$1);
    private final List<JigsawPiece> elements;

    public ListJigsawPiece(List<JigsawPiece> list, JigsawPattern.PlacementBehaviour placementBehaviour) {
        super(placementBehaviour);
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Elements are empty");
        }
        this.elements = list;
        this.setProjectionOnEachElement(placementBehaviour);
    }

    @Override
    public List<Template.BlockInfo> getJigsawBlocks(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, Random random2) {
        return this.elements.get(0).getJigsawBlocks(templateManager, blockPos, rotation, random2);
    }

    @Override
    public MutableBoundingBox getBoundingBox(TemplateManager templateManager, BlockPos blockPos, Rotation rotation) {
        MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getNewBoundingBox();
        for (JigsawPiece jigsawPiece : this.elements) {
            MutableBoundingBox mutableBoundingBox2 = jigsawPiece.getBoundingBox(templateManager, blockPos, rotation);
            mutableBoundingBox.expandTo(mutableBoundingBox2);
        }
        return mutableBoundingBox;
    }

    @Override
    public boolean func_230378_a_(TemplateManager templateManager, ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockPos blockPos2, Rotation rotation, MutableBoundingBox mutableBoundingBox, Random random2, boolean bl) {
        for (JigsawPiece jigsawPiece : this.elements) {
            if (jigsawPiece.func_230378_a_(templateManager, iSeedReader, structureManager, chunkGenerator, blockPos, blockPos2, rotation, mutableBoundingBox, random2, bl)) continue;
            return true;
        }
        return false;
    }

    @Override
    public IJigsawDeserializer<?> getType() {
        return IJigsawDeserializer.LIST_POOL_ELEMENT;
    }

    @Override
    public JigsawPiece setPlacementBehaviour(JigsawPattern.PlacementBehaviour placementBehaviour) {
        super.setPlacementBehaviour(placementBehaviour);
        this.setProjectionOnEachElement(placementBehaviour);
        return this;
    }

    public String toString() {
        return "List[" + this.elements.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
    }

    private void setProjectionOnEachElement(JigsawPattern.PlacementBehaviour placementBehaviour) {
        this.elements.forEach(arg_0 -> ListJigsawPiece.lambda$setProjectionOnEachElement$2(placementBehaviour, arg_0));
    }

    private static void lambda$setProjectionOnEachElement$2(JigsawPattern.PlacementBehaviour placementBehaviour, JigsawPiece jigsawPiece) {
        jigsawPiece.setPlacementBehaviour(placementBehaviour);
    }

    private static App lambda$static$1(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)JigsawPiece.field_236847_e_.listOf().fieldOf("elements")).forGetter(ListJigsawPiece::lambda$static$0), ListJigsawPiece.func_236848_d_()).apply(instance, ListJigsawPiece::new);
    }

    private static List lambda$static$0(ListJigsawPiece listJigsawPiece) {
        return listJigsawPiece.elements;
    }
}


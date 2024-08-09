/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.jigsaw.EmptyJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.FeatureJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.LegacySingleJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.ListJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;

public interface IJigsawDeserializer<P extends JigsawPiece> {
    public static final IJigsawDeserializer<SingleJigsawPiece> SINGLE_POOL_ELEMENT = IJigsawDeserializer.func_236851_a_("single_pool_element", SingleJigsawPiece.field_236838_b_);
    public static final IJigsawDeserializer<ListJigsawPiece> LIST_POOL_ELEMENT = IJigsawDeserializer.func_236851_a_("list_pool_element", ListJigsawPiece.field_236834_a_);
    public static final IJigsawDeserializer<FeatureJigsawPiece> FEATURE_POOL_ELEMENT = IJigsawDeserializer.func_236851_a_("feature_pool_element", FeatureJigsawPiece.field_236816_a_);
    public static final IJigsawDeserializer<EmptyJigsawPiece> EMPTY_POOL_ELEMENT = IJigsawDeserializer.func_236851_a_("empty_pool_element", EmptyJigsawPiece.field_236814_a_);
    public static final IJigsawDeserializer<LegacySingleJigsawPiece> field_236849_e_ = IJigsawDeserializer.func_236851_a_("legacy_single_pool_element", LegacySingleJigsawPiece.field_236832_a_);

    public Codec<P> codec();

    public static <P extends JigsawPiece> IJigsawDeserializer<P> func_236851_a_(String string, Codec<P> codec) {
        return Registry.register(Registry.STRUCTURE_POOL_ELEMENT, string, () -> IJigsawDeserializer.lambda$func_236851_a_$0(codec));
    }

    private static Codec lambda$func_236851_a_$0(Codec codec) {
        return codec;
    }
}


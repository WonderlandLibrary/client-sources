/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.world.gen.feature.template.BlackStoneReplacementProcessor;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.BlockMosinessProcessor;
import net.minecraft.world.gen.feature.template.GravityStructureProcessor;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.JigsawReplacementStructureProcessor;
import net.minecraft.world.gen.feature.template.LavaSubmergingProcessor;
import net.minecraft.world.gen.feature.template.NopProcessor;
import net.minecraft.world.gen.feature.template.RuleStructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessorList;

public interface IStructureProcessorType<P extends StructureProcessor> {
    public static final IStructureProcessorType<BlockIgnoreStructureProcessor> BLOCK_IGNORE = IStructureProcessorType.func_237139_a_("block_ignore", BlockIgnoreStructureProcessor.field_237073_a_);
    public static final IStructureProcessorType<IntegrityProcessor> BLOCK_ROT = IStructureProcessorType.func_237139_a_("block_rot", IntegrityProcessor.field_237077_a_);
    public static final IStructureProcessorType<GravityStructureProcessor> GRAVITY = IStructureProcessorType.func_237139_a_("gravity", GravityStructureProcessor.field_237081_a_);
    public static final IStructureProcessorType<JigsawReplacementStructureProcessor> JIGSAW_REPLACEMENT = IStructureProcessorType.func_237139_a_("jigsaw_replacement", JigsawReplacementStructureProcessor.field_237085_a_);
    public static final IStructureProcessorType<RuleStructureProcessor> RULE = IStructureProcessorType.func_237139_a_("rule", RuleStructureProcessor.field_237125_a_);
    public static final IStructureProcessorType<NopProcessor> NOP = IStructureProcessorType.func_237139_a_("nop", NopProcessor.field_237097_a_);
    public static final IStructureProcessorType<BlockMosinessProcessor> field_237135_g_ = IStructureProcessorType.func_237139_a_("block_age", BlockMosinessProcessor.field_237062_a_);
    public static final IStructureProcessorType<BlackStoneReplacementProcessor> field_237136_h_ = IStructureProcessorType.func_237139_a_("blackstone_replace", BlackStoneReplacementProcessor.field_237057_a_);
    public static final IStructureProcessorType<LavaSubmergingProcessor> field_241534_i_ = IStructureProcessorType.func_237139_a_("lava_submerged_block", LavaSubmergingProcessor.field_241531_a_);
    public static final Codec<StructureProcessor> field_237137_i_ = Registry.STRUCTURE_PROCESSOR.dispatch("processor_type", StructureProcessor::getType, IStructureProcessorType::codec);
    public static final Codec<StructureProcessorList> field_242920_k = field_237137_i_.listOf().xmap(StructureProcessorList::new, StructureProcessorList::func_242919_a);
    public static final Codec<StructureProcessorList> field_242921_l = Codec.either(((MapCodec)field_242920_k.fieldOf("processors")).codec(), field_242920_k).xmap(IStructureProcessorType::lambda$static$2, Either::left);
    public static final Codec<Supplier<StructureProcessorList>> field_242922_m = RegistryKeyCodec.create(Registry.STRUCTURE_PROCESSOR_LIST_KEY, field_242921_l);

    public Codec<P> codec();

    public static <P extends StructureProcessor> IStructureProcessorType<P> func_237139_a_(String string, Codec<P> codec) {
        return Registry.register(Registry.STRUCTURE_PROCESSOR, string, () -> IStructureProcessorType.lambda$func_237139_a_$3(codec));
    }

    private static Codec lambda$func_237139_a_$3(Codec codec) {
        return codec;
    }

    private static StructureProcessorList lambda$static$2(Either either) {
        return either.map(IStructureProcessorType::lambda$static$0, IStructureProcessorType::lambda$static$1);
    }

    private static StructureProcessorList lambda$static$1(StructureProcessorList structureProcessorList) {
        return structureProcessorList;
    }

    private static StructureProcessorList lambda$static$0(StructureProcessorList structureProcessorList) {
        return structureProcessorList;
    }
}


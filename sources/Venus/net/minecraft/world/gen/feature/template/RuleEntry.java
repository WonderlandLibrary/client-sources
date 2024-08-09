/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.AlwaysTrueTest;
import net.minecraft.world.gen.feature.template.PosRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;

public class RuleEntry {
    public static final Codec<RuleEntry> field_237108_a_ = RecordCodecBuilder.create(RuleEntry::lambda$static$5);
    private final RuleTest inputPredicate;
    private final RuleTest locationPredicate;
    private final PosRuleTest field_237109_d_;
    private final BlockState outputState;
    @Nullable
    private final CompoundNBT outputNbt;

    public RuleEntry(RuleTest ruleTest, RuleTest ruleTest2, BlockState blockState) {
        this(ruleTest, ruleTest2, AlwaysTrueTest.field_237100_b_, blockState, Optional.empty());
    }

    public RuleEntry(RuleTest ruleTest, RuleTest ruleTest2, PosRuleTest posRuleTest, BlockState blockState) {
        this(ruleTest, ruleTest2, posRuleTest, blockState, Optional.empty());
    }

    public RuleEntry(RuleTest ruleTest, RuleTest ruleTest2, PosRuleTest posRuleTest, BlockState blockState, Optional<CompoundNBT> optional) {
        this.inputPredicate = ruleTest;
        this.locationPredicate = ruleTest2;
        this.field_237109_d_ = posRuleTest;
        this.outputState = blockState;
        this.outputNbt = optional.orElse(null);
    }

    public boolean func_237110_a_(BlockState blockState, BlockState blockState2, BlockPos blockPos, BlockPos blockPos2, BlockPos blockPos3, Random random2) {
        return this.inputPredicate.test(blockState, random2) && this.locationPredicate.test(blockState2, random2) && this.field_237109_d_.func_230385_a_(blockPos, blockPos2, blockPos3, random2);
    }

    public BlockState getOutputState() {
        return this.outputState;
    }

    @Nullable
    public CompoundNBT getOutputNbt() {
        return this.outputNbt;
    }

    private static App lambda$static$5(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)RuleTest.field_237127_c_.fieldOf("input_predicate")).forGetter(RuleEntry::lambda$static$0), ((MapCodec)RuleTest.field_237127_c_.fieldOf("location_predicate")).forGetter(RuleEntry::lambda$static$1), PosRuleTest.field_237102_c_.optionalFieldOf("position_predicate", AlwaysTrueTest.field_237100_b_).forGetter(RuleEntry::lambda$static$2), ((MapCodec)BlockState.CODEC.fieldOf("output_state")).forGetter(RuleEntry::lambda$static$3), CompoundNBT.CODEC.optionalFieldOf("output_nbt").forGetter(RuleEntry::lambda$static$4)).apply(instance, RuleEntry::new);
    }

    private static Optional lambda$static$4(RuleEntry ruleEntry) {
        return Optional.ofNullable(ruleEntry.outputNbt);
    }

    private static BlockState lambda$static$3(RuleEntry ruleEntry) {
        return ruleEntry.outputState;
    }

    private static PosRuleTest lambda$static$2(RuleEntry ruleEntry) {
        return ruleEntry.field_237109_d_;
    }

    private static RuleTest lambda$static$1(RuleEntry ruleEntry) {
        return ruleEntry.locationPredicate;
    }

    private static RuleTest lambda$static$0(RuleEntry ruleEntry) {
        return ruleEntry.inputPredicate;
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

public class BlockStateMatchRuleTest
extends RuleTest {
    public static final Codec<BlockStateMatchRuleTest> field_237079_a_ = ((MapCodec)BlockState.CODEC.fieldOf("block_state")).xmap(BlockStateMatchRuleTest::new, BlockStateMatchRuleTest::lambda$static$0).codec();
    private final BlockState state;

    public BlockStateMatchRuleTest(BlockState blockState) {
        this.state = blockState;
    }

    @Override
    public boolean test(BlockState blockState, Random random2) {
        return blockState == this.state;
    }

    @Override
    protected IRuleTestType<?> getType() {
        return IRuleTestType.BLOCKSTATE_MATCH;
    }

    private static BlockState lambda$static$0(BlockStateMatchRuleTest blockStateMatchRuleTest) {
        return blockStateMatchRuleTest.state;
    }
}


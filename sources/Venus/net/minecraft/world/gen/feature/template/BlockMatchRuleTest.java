/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

public class BlockMatchRuleTest
extends RuleTest {
    public static final Codec<BlockMatchRuleTest> field_237075_a_ = ((MapCodec)Registry.BLOCK.fieldOf("block")).xmap(BlockMatchRuleTest::new, BlockMatchRuleTest::lambda$static$0).codec();
    private final Block block;

    public BlockMatchRuleTest(Block block) {
        this.block = block;
    }

    @Override
    public boolean test(BlockState blockState, Random random2) {
        return blockState.isIn(this.block);
    }

    @Override
    protected IRuleTestType<?> getType() {
        return IRuleTestType.BLOCK_MATCH;
    }

    private static Block lambda$static$0(BlockMatchRuleTest blockMatchRuleTest) {
        return blockMatchRuleTest.block;
    }
}


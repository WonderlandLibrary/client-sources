/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.pattern;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;

public class BlockMaterialMatcher
implements Predicate<BlockState> {
    private static final BlockMaterialMatcher AIR_MATCHER = new BlockMaterialMatcher(Material.AIR){

        @Override
        public boolean test(@Nullable BlockState blockState) {
            return blockState != null && blockState.isAir();
        }

        @Override
        public boolean test(@Nullable Object object) {
            return this.test((BlockState)object);
        }
    };
    private final Material material;

    private BlockMaterialMatcher(Material material) {
        this.material = material;
    }

    public static BlockMaterialMatcher forMaterial(Material material) {
        return material == Material.AIR ? AIR_MATCHER : new BlockMaterialMatcher(material);
    }

    @Override
    public boolean test(@Nullable BlockState blockState) {
        return blockState != null && blockState.getMaterial() == this.material;
    }

    @Override
    public boolean test(@Nullable Object object) {
        return this.test((BlockState)object);
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.pattern;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;

public class BlockStateMatcher
implements Predicate<BlockState> {
    public static final Predicate<BlockState> ANY = BlockStateMatcher::lambda$static$0;
    private final StateContainer<Block, BlockState> blockstate;
    private final Map<Property<?>, Predicate<Object>> propertyPredicates = Maps.newHashMap();

    private BlockStateMatcher(StateContainer<Block, BlockState> stateContainer) {
        this.blockstate = stateContainer;
    }

    public static BlockStateMatcher forBlock(Block block) {
        return new BlockStateMatcher(block.getStateContainer());
    }

    @Override
    public boolean test(@Nullable BlockState blockState) {
        if (blockState != null && blockState.getBlock().equals(this.blockstate.getOwner())) {
            if (this.propertyPredicates.isEmpty()) {
                return false;
            }
            for (Map.Entry<Property<?>, Predicate<Object>> entry : this.propertyPredicates.entrySet()) {
                if (this.matches(blockState, entry.getKey(), entry.getValue())) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    protected <T extends Comparable<T>> boolean matches(BlockState blockState, Property<T> property, Predicate<Object> predicate) {
        T t = blockState.get(property);
        return predicate.test(t);
    }

    public <V extends Comparable<V>> BlockStateMatcher where(Property<V> property, Predicate<Object> predicate) {
        if (!this.blockstate.getProperties().contains(property)) {
            throw new IllegalArgumentException(this.blockstate + " cannot support property " + property);
        }
        this.propertyPredicates.put(property, predicate);
        return this;
    }

    @Override
    public boolean test(@Nullable Object object) {
        return this.test((BlockState)object);
    }

    private static boolean lambda$static$0(BlockState blockState) {
        return false;
    }
}


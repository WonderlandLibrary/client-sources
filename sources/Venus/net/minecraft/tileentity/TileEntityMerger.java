/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class TileEntityMerger {
    public static <S extends TileEntity> ICallbackWrapper<S> func_226924_a_(TileEntityType<S> tileEntityType, Function<BlockState, Type> function, Function<BlockState, Direction> function2, DirectionProperty directionProperty, BlockState blockState, IWorld iWorld, BlockPos blockPos, BiPredicate<IWorld, BlockPos> biPredicate) {
        Type type;
        boolean bl;
        S s = tileEntityType.getIfExists(iWorld, blockPos);
        if (s == null) {
            return ICallback::func_225537_b_;
        }
        if (biPredicate.test(iWorld, blockPos)) {
            return ICallback::func_225537_b_;
        }
        Type type2 = function.apply(blockState);
        boolean bl2 = type2 == Type.SINGLE;
        boolean bl3 = bl = type2 == Type.FIRST;
        if (bl2) {
            return new ICallbackWrapper.Single<S>(s);
        }
        BlockPos blockPos2 = blockPos.offset(function2.apply(blockState));
        BlockState blockState2 = iWorld.getBlockState(blockPos2);
        if (blockState2.isIn(blockState.getBlock()) && (type = function.apply(blockState2)) != Type.SINGLE && type2 != type && blockState2.get(directionProperty) == blockState.get(directionProperty)) {
            if (biPredicate.test(iWorld, blockPos2)) {
                return ICallback::func_225537_b_;
            }
            S s2 = tileEntityType.getIfExists(iWorld, blockPos2);
            if (s2 != null) {
                S s3 = bl ? s : s2;
                S s4 = bl ? s2 : s;
                return new ICallbackWrapper.Double<S>(s3, s4);
            }
        }
        return new ICallbackWrapper.Single<S>(s);
    }

    public static interface ICallbackWrapper<S> {
        public <T> T apply(ICallback<? super S, T> var1);

        public static final class Single<S>
        implements ICallbackWrapper<S> {
            private final S field_226927_a_;

            public Single(S s) {
                this.field_226927_a_ = s;
            }

            @Override
            public <T> T apply(ICallback<? super S, T> iCallback) {
                return iCallback.func_225538_a_(this.field_226927_a_);
            }
        }

        public static final class Double<S>
        implements ICallbackWrapper<S> {
            private final S field_226925_a_;
            private final S field_226926_b_;

            public Double(S s, S s2) {
                this.field_226925_a_ = s;
                this.field_226926_b_ = s2;
            }

            @Override
            public <T> T apply(ICallback<? super S, T> iCallback) {
                return iCallback.func_225539_a_(this.field_226925_a_, this.field_226926_b_);
            }
        }
    }

    public static enum Type {
        SINGLE,
        FIRST,
        SECOND;

    }

    public static interface ICallback<S, T> {
        public T func_225539_a_(S var1, S var2);

        public T func_225538_a_(S var1);

        public T func_225537_b_();
    }
}


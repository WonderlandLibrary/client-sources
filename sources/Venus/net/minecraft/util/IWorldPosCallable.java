/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWorldPosCallable {
    public static final IWorldPosCallable DUMMY = new IWorldPosCallable(){

        @Override
        public <T> Optional<T> apply(BiFunction<World, BlockPos, T> biFunction) {
            return Optional.empty();
        }
    };

    public static IWorldPosCallable of(World world, BlockPos blockPos) {
        return new IWorldPosCallable(){
            final World val$world;
            final BlockPos val$pos;
            {
                this.val$world = world;
                this.val$pos = blockPos;
            }

            @Override
            public <T> Optional<T> apply(BiFunction<World, BlockPos, T> biFunction) {
                return Optional.of(biFunction.apply(this.val$world, this.val$pos));
            }
        };
    }

    public <T> Optional<T> apply(BiFunction<World, BlockPos, T> var1);

    default public <T> T applyOrElse(BiFunction<World, BlockPos, T> biFunction, T t) {
        return this.apply(biFunction).orElse(t);
    }

    default public void consume(BiConsumer<World, BlockPos> biConsumer) {
        this.apply((arg_0, arg_1) -> IWorldPosCallable.lambda$consume$0(biConsumer, arg_0, arg_1));
    }

    private static Optional lambda$consume$0(BiConsumer biConsumer, World world, BlockPos blockPos) {
        biConsumer.accept(world, blockPos);
        return Optional.empty();
    }
}


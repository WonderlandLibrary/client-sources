/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class GlobalPos {
    public static final Codec<GlobalPos> CODEC = RecordCodecBuilder.create(GlobalPos::lambda$static$0);
    private final RegistryKey<World> dimension;
    private final BlockPos pos;

    private GlobalPos(RegistryKey<World> registryKey, BlockPos blockPos) {
        this.dimension = registryKey;
        this.pos = blockPos;
    }

    public static GlobalPos getPosition(RegistryKey<World> registryKey, BlockPos blockPos) {
        return new GlobalPos(registryKey, blockPos);
    }

    public RegistryKey<World> getDimension() {
        return this.dimension;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            GlobalPos globalPos = (GlobalPos)object;
            return Objects.equals(this.dimension, globalPos.dimension) && Objects.equals(this.pos, globalPos.pos);
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.dimension, this.pos);
    }

    public String toString() {
        return this.dimension.toString() + " " + this.pos;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)World.CODEC.fieldOf("dimension")).forGetter(GlobalPos::getDimension), ((MapCodec)BlockPos.CODEC.fieldOf("pos")).forGetter(GlobalPos::getPos)).apply(instance, GlobalPos::getPosition);
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.village;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;

public class PointOfInterest {
    private final BlockPos pos;
    private final PointOfInterestType type;
    private int freeTickets;
    private final Runnable onChange;

    public static Codec<PointOfInterest> func_234150_a_(Runnable runnable) {
        return RecordCodecBuilder.create(arg_0 -> PointOfInterest.lambda$func_234150_a_$3(runnable, arg_0));
    }

    private PointOfInterest(BlockPos blockPos, PointOfInterestType pointOfInterestType, int n, Runnable runnable) {
        this.pos = blockPos.toImmutable();
        this.type = pointOfInterestType;
        this.freeTickets = n;
        this.onChange = runnable;
    }

    public PointOfInterest(BlockPos blockPos, PointOfInterestType pointOfInterestType, Runnable runnable) {
        this(blockPos, pointOfInterestType, pointOfInterestType.getMaxFreeTickets(), runnable);
    }

    protected boolean claim() {
        if (this.freeTickets <= 0) {
            return true;
        }
        --this.freeTickets;
        this.onChange.run();
        return false;
    }

    protected boolean release() {
        if (this.freeTickets >= this.type.getMaxFreeTickets()) {
            return true;
        }
        ++this.freeTickets;
        this.onChange.run();
        return false;
    }

    public boolean hasSpace() {
        return this.freeTickets > 0;
    }

    public boolean isOccupied() {
        return this.freeTickets != this.type.getMaxFreeTickets();
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public PointOfInterestType getType() {
        return this.type;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object != null && this.getClass() == object.getClass() ? Objects.equals(this.pos, ((PointOfInterest)object).pos) : false;
    }

    public int hashCode() {
        return this.pos.hashCode();
    }

    private static App lambda$func_234150_a_$3(Runnable runnable, RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockPos.CODEC.fieldOf("pos")).forGetter(PointOfInterest::lambda$func_234150_a_$0), ((MapCodec)Registry.POINT_OF_INTEREST_TYPE.fieldOf("type")).forGetter(PointOfInterest::lambda$func_234150_a_$1), ((MapCodec)Codec.INT.fieldOf("free_tickets")).orElse(0).forGetter(PointOfInterest::lambda$func_234150_a_$2), RecordCodecBuilder.point(runnable)).apply(instance, PointOfInterest::new);
    }

    private static Integer lambda$func_234150_a_$2(PointOfInterest pointOfInterest) {
        return pointOfInterest.freeTickets;
    }

    private static PointOfInterestType lambda$func_234150_a_$1(PointOfInterest pointOfInterest) {
        return pointOfInterest.type;
    }

    private static BlockPos lambda$func_234150_a_$0(PointOfInterest pointOfInterest) {
        return pointOfInterest.pos;
    }
}


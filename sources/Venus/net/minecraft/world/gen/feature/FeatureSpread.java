/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

public class FeatureSpread {
    public static final Codec<FeatureSpread> field_242249_a = Codec.either(Codec.INT, RecordCodecBuilder.create(FeatureSpread::lambda$static$2).comapFlatMap(FeatureSpread::lambda$static$3, Function.identity())).xmap(FeatureSpread::lambda$static$5, FeatureSpread::lambda$static$6);
    private final int field_242250_b;
    private final int field_242251_c;

    public static Codec<FeatureSpread> func_242254_a(int n, int n2, int n3) {
        Function<FeatureSpread, DataResult> function = arg_0 -> FeatureSpread.lambda$func_242254_a$7(n, n2, n3, arg_0);
        return field_242249_a.flatXmap(function, function);
    }

    private FeatureSpread(int n, int n2) {
        this.field_242250_b = n;
        this.field_242251_c = n2;
    }

    public static FeatureSpread func_242252_a(int n) {
        return new FeatureSpread(n, 0);
    }

    public static FeatureSpread func_242253_a(int n, int n2) {
        return new FeatureSpread(n, n2);
    }

    public int func_242259_a(Random random2) {
        return this.field_242251_c == 0 ? this.field_242250_b : this.field_242250_b + random2.nextInt(this.field_242251_c + 1);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            FeatureSpread featureSpread = (FeatureSpread)object;
            return this.field_242250_b == featureSpread.field_242250_b && this.field_242251_c == featureSpread.field_242251_c;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.field_242250_b, this.field_242251_c);
    }

    public String toString() {
        return "[" + this.field_242250_b + "-" + (this.field_242250_b + this.field_242251_c) + "]";
    }

    private static DataResult lambda$func_242254_a$7(int n, int n2, int n3, FeatureSpread featureSpread) {
        if (featureSpread.field_242250_b >= n && featureSpread.field_242250_b <= n2) {
            return featureSpread.field_242251_c <= n3 ? DataResult.success(featureSpread) : DataResult.error("Spread too big: " + featureSpread.field_242251_c + " > " + n3);
        }
        return DataResult.error("Base value out of range: " + featureSpread.field_242250_b + " [" + n + "-" + n2 + "]");
    }

    private static Either lambda$static$6(FeatureSpread featureSpread) {
        return featureSpread.field_242251_c == 0 ? Either.left(featureSpread.field_242250_b) : Either.right(featureSpread);
    }

    private static FeatureSpread lambda$static$5(Either either) {
        return either.map(FeatureSpread::func_242252_a, FeatureSpread::lambda$static$4);
    }

    private static FeatureSpread lambda$static$4(FeatureSpread featureSpread) {
        return featureSpread;
    }

    private static DataResult lambda$static$3(FeatureSpread featureSpread) {
        return featureSpread.field_242251_c < 0 ? DataResult.error("Spread must be non-negative, got: " + featureSpread.field_242251_c) : DataResult.success(featureSpread);
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.INT.fieldOf("base")).forGetter(FeatureSpread::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("spread")).forGetter(FeatureSpread::lambda$static$1)).apply(instance, FeatureSpread::new);
    }

    private static Integer lambda$static$1(FeatureSpread featureSpread) {
        return featureSpread.field_242251_c;
    }

    private static Integer lambda$static$0(FeatureSpread featureSpread) {
        return featureSpread.field_242250_b;
    }
}


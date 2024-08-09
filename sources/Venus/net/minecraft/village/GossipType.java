/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.village;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public enum GossipType {
    MAJOR_NEGATIVE("major_negative", -5, 100, 10, 10),
    MINOR_NEGATIVE("minor_negative", -1, 200, 20, 20),
    MINOR_POSITIVE("minor_positive", 1, 200, 1, 5),
    MAJOR_POSITIVE("major_positive", 5, 100, 0, 100),
    TRADING("trading", 1, 25, 2, 20);

    public final String id;
    public final int weight;
    public final int max;
    public final int decayPerDay;
    public final int decayPerTransfer;
    private static final Map<String, GossipType> BY_ID;

    private GossipType(String string2, int n2, int n3, int n4, int n5) {
        this.id = string2;
        this.weight = n2;
        this.max = n3;
        this.decayPerDay = n4;
        this.decayPerTransfer = n5;
    }

    @Nullable
    public static GossipType byId(String string) {
        return BY_ID.get(string);
    }

    private static String lambda$static$0(GossipType gossipType) {
        return gossipType.id;
    }

    static {
        BY_ID = Stream.of(GossipType.values()).collect(ImmutableMap.toImmutableMap(GossipType::lambda$static$0, Function.identity()));
    }
}


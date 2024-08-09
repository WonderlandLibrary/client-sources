/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command;

import java.util.Optional;

public interface Parameters {
    public Optional<Integer> asInt(int var1);

    public Optional<Float> asFloat(int var1);

    public Optional<Double> asDouble(int var1);

    public Optional<String> asString(int var1);

    public String collectMessage(int var1);
}


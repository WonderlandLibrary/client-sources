/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mpp.venusfr.command.Parameters;

public class ParametersImpl
implements Parameters {
    private final String[] parameters;

    public ParametersImpl(String[] stringArray) {
        this.parameters = stringArray;
    }

    @Override
    public Optional<Integer> asInt(int n) {
        return Optional.ofNullable(this.getElementFromParametersOrNull(n, Integer::valueOf));
    }

    @Override
    public Optional<Float> asFloat(int n) {
        return Optional.ofNullable(this.getElementFromParametersOrNull(n, Float::valueOf));
    }

    @Override
    public Optional<Double> asDouble(int n) {
        return Optional.ofNullable(this.getElementFromParametersOrNull(n, Double::valueOf));
    }

    @Override
    public Optional<String> asString(int n) {
        return Optional.ofNullable(this.getElementFromParametersOrNull(n, String::valueOf));
    }

    @Override
    public String collectMessage(int n) {
        return IntStream.range(n, this.parameters.length).mapToObj(this::lambda$collectMessage$0).collect(Collectors.joining(" ")).trim();
    }

    private <T> T getElementFromParametersOrNull(int n, Function<String, T> function) {
        if (n >= this.parameters.length) {
            return null;
        }
        try {
            return function.apply(this.parameters[n]);
        } catch (Exception exception) {
            return null;
        }
    }

    private String lambda$collectMessage$0(int n) {
        return this.asString(n).orElse("");
    }
}


package com.alan.clients.module.impl.other.data;

import lombok.AllArgsConstructor;
import net.minecraft.util.MathHelper;

import java.util.function.Function;

@AllArgsConstructor
public enum Equations {
    DIFFERENCE(values -> values[0] - values[1]),
    EUCLIDEAN_DISTANCE(values -> Math.sqrt(Math.pow(values[0], 2) + Math.pow(values[1], 2))),
    WRAPPED_TO_180_DISTANCE(values -> MathHelper.wrapAngleTo180_double(values[0] - values[1]));

    private final Function<Double[], Double> equation;

    public double run(Double... values) {
        return equation.apply(values);
    }
}

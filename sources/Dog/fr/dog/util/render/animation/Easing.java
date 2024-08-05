package fr.dog.util.render.animation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

/**
 * This code is part of Liticane's Animation Library.
 *
 * @author Liticane
 * @since 22/03/2024
 */
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
public enum Easing {
    EASE_IN_OUT_SINE(x -> -(Math.cos(Math.PI * x) - 1) / 2),
    EASE_IN_OUT_QUAD(x -> x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2),
    EASE_OUT_QUAD(x -> 1 - (1 - x) * (1 - x)),
    EASE_IN_OUT_BACK(x -> x < 0.5
            ? (Math.pow(2 * x, 2) * ((1.70158 * 1.525 + 1) * 2 * x - 1.70158 * 1.525)) / 2
            : (Math.pow(2 * x - 2, 2) * ((1.70158 * 1.525 + 1) * (x * 2 - 2) + 1.70158 * 1.525) + 2) / 2),
    EASE_OUT_BACK(x -> 1 + 2.70158 * Math.pow(x - 1, 3) + 1.70158 * Math.pow(x - 1, 2));

    private final Function<Double, Double> function;
}

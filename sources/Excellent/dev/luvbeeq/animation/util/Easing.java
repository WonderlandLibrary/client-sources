package dev.luvbeeq.animation.util;

@FunctionalInterface
public interface Easing {
    double ease(double value);
}
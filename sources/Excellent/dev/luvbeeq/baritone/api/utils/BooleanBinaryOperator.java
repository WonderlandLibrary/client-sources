package dev.luvbeeq.baritone.api.utils;

/**
 * @author Brady
 */
@FunctionalInterface
public interface BooleanBinaryOperator {

    boolean applyAsBoolean(boolean a, boolean b);
}

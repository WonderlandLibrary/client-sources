/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.Direction;

public class DirectionProperty
extends EnumProperty<Direction> {
    protected DirectionProperty(String string, Collection<Direction> collection) {
        super(string, Direction.class, collection);
    }

    public static DirectionProperty create(String string, Predicate<Direction> predicate) {
        return DirectionProperty.create(string, Arrays.stream(Direction.values()).filter(predicate).collect(Collectors.toList()));
    }

    public static DirectionProperty create(String string, Direction ... directionArray) {
        return DirectionProperty.create(string, Lists.newArrayList(directionArray));
    }

    public static DirectionProperty create(String string, Collection<Direction> collection) {
        return new DirectionProperty(string, collection);
    }
}


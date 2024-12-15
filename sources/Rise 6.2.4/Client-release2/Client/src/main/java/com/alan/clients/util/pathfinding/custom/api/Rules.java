package com.alan.clients.util.pathfinding.custom.api;

import com.alan.clients.util.pathfinding.custom.PathFinder;
import com.alan.clients.util.player.PlayerUtil;
import net.minecraft.block.material.Material;
import net.minecraft.util.Tuple;

import java.util.Arrays;
import java.util.function.Function;

public enum Rules {
    COLLISIONS(data -> {
        Point point = data.getFirst().get(data.getFirst().size() - 1);

        return PlayerUtil.block(point).getMaterial() == Material.air;
    }),

    LEGIT(data -> {
        Point point = data.getFirst().get(data.getFirst().size() - 1);

        return PlayerUtil.block(point.add(0, -1, 0)).getMaterial() != Material.air ||
                PlayerUtil.block(point.add(0, -2, 0)).getMaterial() != Material.air;
    });

    final Function<Tuple<Path, PathFinder>, Boolean> exception;

    Rules(Function<Tuple<Path, PathFinder>, Boolean> exception) {
        this.exception = exception;
    }

    public static boolean run(Path path, PathFinder pathFinder, Rules... rules) {
        return Arrays.stream(rules).allMatch(rule -> rule.exception.apply(new Tuple<>(path, pathFinder)));
    }
}

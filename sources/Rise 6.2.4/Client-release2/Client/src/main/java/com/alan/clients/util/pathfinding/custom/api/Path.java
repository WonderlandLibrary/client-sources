package com.alan.clients.util.pathfinding.custom.api;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class Path extends ArrayList<Point> {
    public Path(@NotNull Collection<? extends Point> c) {
        super(c);
    }

    public Path() {
    }
}

package com.alan.clients.util.pathfinding.custom.api;

import net.minecraft.util.Vec3i;

public class Map {
    // Amount of memory we take up depending on the maps size
    private final int size;
    private final int memorySize;
    private boolean[][][] map;
    public final Vec3i offset;

    public Map(int size, Vec3i offset) {
        this.size = size;
        this.offset = offset;
        this.memorySize = this.size * 2 + 2;
        this.reset();
    }

    public void plot(Point point) {
        plot(point.getX(), point.getY(), point.getZ());
    }

    public void reset() {
        this.map = new boolean[memorySize][memorySize][memorySize];
    }

    public void plot(int x, int y, int z) {
        x -= offset.getX();
        y -= offset.getY();
        z -= offset.getZ();

        this.checkOutside(x, y, z);

        /*
         *  We increase all the values by size, because negatives don't exist in memory,
         *  so we double the given size, and add size to every plot
         */
        map[x + size][y + size][z + size] = true;
    }

    public void erase(int x, int y, int z) {
        x -= offset.getX();
        y -= offset.getY();
        z -= offset.getZ();

        this.checkOutside(x, y, z);

        /*
         *  We increase all the values by size, because negatives don't exist in memory,
         *  so we double the given size, and add size to every plot
         */
        map[x + size][y + size][z + size] = false;
    }

    public boolean view(Point point) {
        return view(point.getX(), point.getY(), point.getZ());
    }

    public boolean view(int x, int y, int z) {
        x -= offset.getX();
        y -= offset.getY();
        z -= offset.getZ();

        this.checkOutside(x, y, z);

        /*
         *  We increase all the values by size, because negatives don't exist in memory,
         *  so we double the given size, and add size to every plot
         */
        return map[x + size][y + size][z + size];
    }

    // Checks if the given values are outside our plot, if they are it throws an exception
    public void checkOutside(int x, int y, int z) {
        if (Math.abs(x) > size || Math.abs(y) > size || Math.abs(z) > size) {
            System.out.println("Outside of size, " + x + " " + y + " " + z);
        }
    }
}

/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class PointTrace {
    public Minecraft mc = Minecraft.getMinecraft();
    public static List<PointTrace> points = new ArrayList<PointTrace>();
    public double x;
    public double y;
    public double z;
    public String name;
    public String serverName;
    public int dimension;
    public int index = 1;

    public PointTrace(String name, double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.serverName = this.mc.isSingleplayer() || this.mc.getCurrentServerData() == null ? "SinglePlayer" : this.mc.getCurrentServerData().serverIP;
        this.dimension = Minecraft.player != null ? Minecraft.player.dimension : 0;
        this.index += points.size();
    }

    public PointTrace(String name, String serverName, double x, double y, double z, int dimension, int index) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.serverName = serverName;
        this.dimension = dimension;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public String getServerName() {
        return this.serverName;
    }

    public int getIndex() {
        return this.index;
    }

    public static PointTrace getPointByName(String name) {
        for (PointTrace point : points) {
            if (!point.name.equalsIgnoreCase(name)) continue;
            return point;
        }
        return null;
    }

    public static void addPoint(String name, float x, float y, float z) {
        boolean add = true;
        for (PointTrace point : points) {
            if (point == null || PointTrace.getPointByName(name) == null) continue;
            add = false;
        }
        if (add) {
            points.add(new PointTrace(name, x, y, z));
        }
    }

    public static void removePoint(PointTrace current) {
        points.remove(current);
    }

    public static void clearPoints() {
        points.clear();
    }

    public static List<PointTrace> getPointList() {
        return points;
    }

    public static double getX(PointTrace point) {
        return (double)Math.round(point.x * 10.0) / 10.0;
    }

    public static double getY(PointTrace point) {
        return (double)Math.round(point.y * 10.0) / 10.0;
    }

    public static double getZ(PointTrace point) {
        return (double)Math.round(point.z * 10.0) / 10.0;
    }

    public static int getDemension(PointTrace point) {
        return point.dimension;
    }
}


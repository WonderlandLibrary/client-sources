/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.waypoint;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class WaypointUtil {
    public static ArrayList<Point> points;

    public static void setup() {
        points = new ArrayList();
    }

    public static void addPoint(String name, String server, double[] position, int color) {
        points.add(new Point(name, server, position, color));
    }

    public static void removePoint(String name) {
        if (!points.isEmpty() && WaypointUtil.pointExists(name)) {
            points.remove(WaypointUtil.getPoint(name));
        }
    }

    public static Point getPoint(String name) {
        for (Point p2 : points) {
            if (!p2.name().equalsIgnoreCase(name)) continue;
            return p2;
        }
        return null;
    }

    public static boolean pointExists(String name) {
        if (!points.isEmpty()) {
            for (Point p2 : points) {
                if (!p2.name().equalsIgnoreCase(name)) continue;
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Point> getWaypoints() {
        ArrayList<Point> serverPoints = new ArrayList<Point>();
        String curServ = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        if (!points.isEmpty()) {
            for (Point point : points) {
                if (!point.server().equals(curServ)) continue;
                serverPoints.add(point);
            }
        }
        return serverPoints;
    }

    public static class Point {
        private String name;
        private String server;
        private double[] pos;
        private int color;

        public Point(String name, String server, double[] pos, int color) {
            this.name = name;
            this.server = server;
            this.pos = pos;
            this.color = color;
        }

        public String server() {
            return this.server;
        }

        public int color() {
            return this.color;
        }

        public double[] pos() {
            return this.pos;
        }

        public String name() {
            return this.name;
        }
    }

}


package com.alan.clients.util.pathfinding.custom;

import com.alan.clients.util.Accessor;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.pathfinding.custom.api.Map;
import com.alan.clients.util.pathfinding.custom.api.Path;
import com.alan.clients.util.pathfinding.custom.api.Point;
import com.alan.clients.util.pathfinding.custom.api.Rules;
import com.alan.clients.util.process.ThreadUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3i;
import org.lwjgl.opengl.GL11;
import rip.vantage.commons.util.time.StopWatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.alan.clients.util.pathfinding.custom.api.Rules.COLLISIONS;
import static com.alan.clients.util.pathfinding.custom.api.Rules.LEGIT;

public class PathFinder extends ArrayList<Path> implements Accessor {
    public static int MAX_DISTANCE = 100;

    public Point start = new Point((int) mc.thePlayer.posX, (int) mc.thePlayer.posY, (int) mc.thePlayer.posZ), end;
    private final Map map = new Map(MAX_DISTANCE, start);
    private final Map blacklist = new Map(MAX_DISTANCE, start);
    public Path path;
    public Vec3i destinationOffset;
    public int closest = Integer.MAX_VALUE;
    private final boolean dev;

    public PathFinder(Point end, boolean dev) {
        this.end = end;
        this.dev = dev;
        this.destinationOffset = new Vec3i(Math.abs(start.getX() - end.getX()), Math.abs(start.getY() - end.getY()), Math.abs(start.getZ() - end.getZ()));
    }

    public PathFinder(Point end) {
        this.end = end;
        this.dev = false;
        this.destinationOffset = new Vec3i(Math.abs(start.getX() - end.getX()), Math.abs(start.getY() - end.getY()), Math.abs(start.getZ() - end.getZ()));
    }

    public void run() {
        Executor threadPool = Executors.newFixedThreadPool(1);

        threadPool.execute(() -> {
            StopWatch stopWatch = new StopWatch();

            while (path == null && !stopWatch.finished(dev ? 15000 : 50)) {
                next();

                if (this.size() == 0) break;
                if (dev) ThreadUtil.sleep(200);
            }

            if (this.path == null) {
                ChatUtil.display("Failed to find path.");
            } else {
                ChatUtil.display("Took " + stopWatch.getElapsedTime() + " ms.");
            }
        });
    }

    private void next() {
        try {
            if (this.isEmpty()) {
                Path path = new Path();
                path.add(start);
                this.add(path);
            } else {
                List<Path> add = new ArrayList<>();
                List<Path> remove = new ArrayList<>();

                Rules[] rules = {COLLISIONS, LEGIT};

                for (Path path : this) {
                    int offset = 1;

                    for (int x = -offset; x <= offset; x += offset * 2) {
                        Path next = splitPath(path, x, 0, 0);
                        if (next != null && Rules.run(next, this, rules)) add.add(next);
                    }

                    for (int y = -offset; y <= offset; y += offset * 2) {
                        Path next = splitPath(path, 0, y, 0);
                        if (next != null && Rules.run(next, this, rules)) add.add(next);
                    }

                    for (int z = -offset; z <= offset; z += offset * 2) {
                        Path next = splitPath(path, 0, 0, z);
                        if (next != null && Rules.run(next, this, rules)) add.add(next);
                    }

                    remove.add(path);
                }

                this.removeAll(remove);
                this.addAll(add);

                add.forEach(path -> {
                    if (path.get(path.size() - 1).equals(end)) {
                        this.path = path;
                    }
                });

                this.sort(Comparator.comparingDouble(path -> (int) path.get(path.size() - 1).distance(end)));

                int closest = (int) this.get(0).get(this.get(0).size() - 1).distance(end);
                this.removeRange(Math.max(1, Math.min(25 /* Depth of 25 */, this.size() - 1)), this.size());

                if (closest < this.closest) this.closest = closest;

            }
        } catch (Exception exception) {
            ChatUtil.debug("Exception in Console");
            exception.printStackTrace();
            this.clear();
        }
    }

    private Path splitPath(Path path, int offsetX, int offsetY, int offsetZ) {
        Path nextPath = new Path(path);
        Point nextPoint = nextPath.get(nextPath.size() - 1);

        nextPoint = new Point(nextPoint.add(offsetX, offsetY, offsetZ));

        if (map.view(nextPoint)) return null;

        nextPath.add(nextPoint);
        map.plot(nextPoint);

        return nextPath;
    }

    public void render() {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GL11.glDepthMask(false);

        RenderUtil.color(ColorUtil.withAlpha(getTheme().getFirstColor(), 100));

        double size = 0.6;

        for (Path path : new ArrayList<>(this)) {
            if (path == null) continue;
            for (Point point : path) {
                RenderUtil.drawBoundingBox(new AxisAlignedBB(point.getX() + (1 - size), point.getY() + (1 - size), point.getZ() + (1 - size), point.getX() + size, point.getY() + size, point.getZ() + size));
            }
        }

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GL11.glDepthMask(true);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        GlStateManager.resetColor();

        if (path != null) {
            Point last = null;

            for (Point point : path) {
                if (last != null) {
                    RenderUtil.drawLine(last.getX() + 0.5, last.getY() + 0.5, last.getZ() + 0.5, point.getX() + 0.5, point.getY() + 0.5, point.getZ() + 0.5, Color.WHITE, 1);
                }

                last = point;
            }
        }
    }
}

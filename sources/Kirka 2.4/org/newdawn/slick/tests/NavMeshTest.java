/*
 * Decompiled with CFR 0.143.
 */
package org.newdawn.slick.tests;

import java.io.IOException;
import java.io.PrintStream;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Bootstrap;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.newdawn.slick.util.pathfinding.navmesh.Link;
import org.newdawn.slick.util.pathfinding.navmesh.NavMesh;
import org.newdawn.slick.util.pathfinding.navmesh.NavMeshBuilder;
import org.newdawn.slick.util.pathfinding.navmesh.NavPath;
import org.newdawn.slick.util.pathfinding.navmesh.Space;

public class NavMeshTest
extends BasicGame
implements PathFindingContext {
    private NavMesh navMesh;
    private NavMeshBuilder builder;
    private boolean showSpaces = true;
    private boolean showLinks = true;
    private NavPath path;
    private float sx;
    private float sy;
    private float ex;
    private float ey;
    private DataMap dataMap;

    public NavMeshTest() {
        super("Nav-mesh Test");
    }

    public void init(GameContainer container) throws SlickException {
        container.setShowFPS(false);
        try {
            this.dataMap = new DataMap("testdata/map.dat");
        }
        catch (IOException e) {
            throw new SlickException("Failed to load map data", e);
        }
        this.builder = new NavMeshBuilder();
        this.navMesh = this.builder.build(this.dataMap);
        System.out.println("Navmesh shapes: " + this.navMesh.getSpaceCount());
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(2)) {
            boolean bl = this.showLinks = !this.showLinks;
        }
        if (container.getInput().isKeyPressed(3)) {
            this.showSpaces = !this.showSpaces;
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        int i;
        g.translate(50.0f, 50.0f);
        for (int x = 0; x < 50; ++x) {
            for (int y = 0; y < 50; ++y) {
                if (!this.dataMap.blocked(this, x, y)) continue;
                g.setColor(Color.gray);
                g.fillRect(x * 10 + 1, y * 10 + 1, 8.0f, 8.0f);
            }
        }
        if (this.showSpaces) {
            for (i = 0; i < this.navMesh.getSpaceCount(); ++i) {
                Space space = this.navMesh.getSpace(i);
                if (this.builder.clear(this.dataMap, space)) {
                    g.setColor(new Color(1.0f, 1.0f, 0.0f, 0.5f));
                    g.fillRect(space.getX() * 10.0f, space.getY() * 10.0f, space.getWidth() * 10.0f, space.getHeight() * 10.0f);
                }
                g.setColor(Color.yellow);
                g.drawRect(space.getX() * 10.0f, space.getY() * 10.0f, space.getWidth() * 10.0f, space.getHeight() * 10.0f);
                if (!this.showLinks) continue;
                int links = space.getLinkCount();
                for (int j = 0; j < links; ++j) {
                    Link link = space.getLink(j);
                    g.setColor(Color.red);
                    g.fillRect(link.getX() * 10.0f - 2.0f, link.getY() * 10.0f - 2.0f, 5.0f, 5.0f);
                }
            }
        }
        if (this.path != null) {
            g.setColor(Color.white);
            for (i = 0; i < this.path.length() - 1; ++i) {
                g.drawLine(this.path.getX(i) * 10.0f, this.path.getY(i) * 10.0f, this.path.getX(i + 1) * 10.0f, this.path.getY(i + 1) * 10.0f);
            }
        }
    }

    public Mover getMover() {
        return null;
    }

    public int getSearchDistance() {
        return 0;
    }

    public int getSourceX() {
        return 0;
    }

    public int getSourceY() {
        return 0;
    }

    public void mousePressed(int button, int x, int y) {
        float mx = (float)(x - 50) / 10.0f;
        float my = (float)(y - 50) / 10.0f;
        if (button == 0) {
            this.sx = mx;
            this.sy = my;
        } else {
            this.ex = mx;
            this.ey = my;
        }
        this.path = this.navMesh.findPath(this.sx, this.sy, this.ex, this.ey, true);
    }

    public static void main(String[] argv) {
        Bootstrap.runAsApplication(new NavMeshTest(), 600, 600, false);
    }

    private class DataMap
    implements TileBasedMap {
        private byte[] map = new byte[2500];

        public DataMap(String ref) throws IOException {
            ResourceLoader.getResourceAsStream(ref).read(this.map);
        }

        public boolean blocked(PathFindingContext context, int tx, int ty) {
            if (tx < 0 || ty < 0 || tx >= 50 || ty >= 50) {
                return false;
            }
            return this.map[tx + ty * 50] != 0;
        }

        public float getCost(PathFindingContext context, int tx, int ty) {
            return 1.0f;
        }

        public int getHeightInTiles() {
            return 50;
        }

        public int getWidthInTiles() {
            return 50;
        }

        public void pathFinderVisited(int x, int y) {
        }
    }

}


package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Vector2f;
import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import java.util.Collection;
import java.util.HashSet;
import HORIZON-6-0-SKIDPROTECTION.Circle;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import java.util.ArrayList;
import HORIZON-6-0-SKIDPROTECTION.GeomUtil;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.GeomUtilListener;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class GeomUtilTileTest extends BasicGame implements GeomUtilListener
{
    private Shape Ó;
    private Shape à;
    private Shape[] Ø;
    private GeomUtil áŒŠÆ;
    private ArrayList áˆºÑ¢Õ;
    private ArrayList ÂµÈ;
    private ArrayList á;
    private ArrayList ˆÏ­;
    private ArrayList[][] £á;
    private Shape[][] Å;
    
    public GeomUtilTileTest() {
        super("GeomUtilTileTest");
        this.áŒŠÆ = new GeomUtil();
        this.áˆºÑ¢Õ = new ArrayList();
        this.ÂµÈ = new ArrayList();
        this.á = new ArrayList();
        this.ˆÏ­ = new ArrayList();
    }
    
    private void HorizonCode_Horizon_È(final ArrayList shapes, final float minx, final float miny, final float maxx, final float maxy, final int segments) {
        this.£á = new ArrayList[segments][segments];
        this.Å = new Shape[segments][segments];
        final float dx = (maxx - minx) / segments;
        final float dy = (maxy - miny) / segments;
        for (int x = 0; x < segments; ++x) {
            for (int y = 0; y < segments; ++y) {
                this.£á[x][y] = new ArrayList();
                final Polygon segmentPolygon = new Polygon();
                segmentPolygon.Â(minx + dx * x, miny + dy * y);
                segmentPolygon.Â(minx + dx * x + dx, miny + dy * y);
                segmentPolygon.Â(minx + dx * x + dx, miny + dy * y + dy);
                segmentPolygon.Â(minx + dx * x, miny + dy * y + dy);
                for (int i = 0; i < shapes.size(); ++i) {
                    final Shape shape = shapes.get(i);
                    if (this.HorizonCode_Horizon_È(shape, segmentPolygon)) {
                        this.£á[x][y].add(shape);
                    }
                }
                this.Å[x][y] = segmentPolygon;
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final Shape shape) {
        for (int segments = this.£á.length, x = 0; x < segments; ++x) {
            for (int y = 0; y < segments; ++y) {
                this.£á[x][y].remove(shape);
            }
        }
    }
    
    private void Â(final Shape shape) {
        for (int segments = this.£á.length, x = 0; x < segments; ++x) {
            for (int y = 0; y < segments; ++y) {
                if (this.HorizonCode_Horizon_È(shape, this.Å[x][y])) {
                    this.£á[x][y].add(shape);
                }
            }
        }
    }
    
    public void Ó() {
        final int size = 10;
        final int[][] map = { { 0, 0, 0, 0, 0, 0, 0, 3, 0, 0 }, { 0, 1, 1, 1, 0, 0, 1, 1, 1, 0 }, { 0, 1, 1, 0, 0, 0, 5, 1, 6, 0 }, { 0, 1, 2, 0, 0, 0, 4, 1, 1, 0 }, { 0, 1, 1, 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 0, 0, 3, 0, 1, 1, 0, 0 }, { 0, 0, 0, 1, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, new int[10], new int[10] };
        for (int x = 0; x < map[0].length; ++x) {
            for (int y = 0; y < map.length; ++y) {
                if (map[y][x] != 0) {
                    switch (map[y][x]) {
                        case 1: {
                            final Polygon p2 = new Polygon();
                            p2.Â(x * 32, y * 32);
                            p2.Â(x * 32 + 32, y * 32);
                            p2.Â(x * 32 + 32, y * 32 + 32);
                            p2.Â(x * 32, y * 32 + 32);
                            this.áˆºÑ¢Õ.add(p2);
                            break;
                        }
                        case 2: {
                            final Polygon poly = new Polygon();
                            poly.Â(x * 32, y * 32);
                            poly.Â(x * 32 + 32, y * 32);
                            poly.Â(x * 32, y * 32 + 32);
                            this.áˆºÑ¢Õ.add(poly);
                            break;
                        }
                        case 3: {
                            final Circle ellipse = new Circle(x * 32 + 16, y * 32 + 32, 16.0f, 16);
                            this.áˆºÑ¢Õ.add(ellipse);
                            break;
                        }
                        case 4: {
                            final Polygon p3 = new Polygon();
                            p3.Â(x * 32 + 32, y * 32);
                            p3.Â(x * 32 + 32, y * 32 + 32);
                            p3.Â(x * 32, y * 32 + 32);
                            this.áˆºÑ¢Õ.add(p3);
                            break;
                        }
                        case 5: {
                            final Polygon p4 = new Polygon();
                            p4.Â(x * 32, y * 32);
                            p4.Â(x * 32 + 32, y * 32);
                            p4.Â(x * 32 + 32, y * 32 + 32);
                            this.áˆºÑ¢Õ.add(p4);
                            break;
                        }
                        case 6: {
                            final Polygon p5 = new Polygon();
                            p5.Â(x * 32, y * 32);
                            p5.Â(x * 32 + 32, y * 32);
                            p5.Â(x * 32, y * 32 + 32);
                            this.áˆºÑ¢Õ.add(p5);
                            break;
                        }
                    }
                }
            }
        }
        final long before = System.currentTimeMillis();
        this.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, 0.0f, 0.0f, (size + 1) * 32, (size + 1) * 32, 8);
        this.ÂµÈ = this.à();
        final long after = System.currentTimeMillis();
        System.out.println("Combine took: " + (after - before));
        System.out.println("Combine result: " + this.ÂµÈ.size());
    }
    
    private ArrayList à() {
        boolean updated = true;
        while (updated) {
            updated = false;
            for (int x = 0; x < this.£á.length; ++x) {
                for (int y = 0; y < this.£á.length; ++y) {
                    final ArrayList shapes = this.£á[x][y];
                    final int before = shapes.size();
                    this.HorizonCode_Horizon_È(shapes);
                    final int after = shapes.size();
                    updated |= (before != after);
                }
            }
        }
        final HashSet result = new HashSet();
        for (int x2 = 0; x2 < this.£á.length; ++x2) {
            for (int y2 = 0; y2 < this.£á.length; ++y2) {
                result.addAll(this.£á[x2][y2]);
            }
        }
        return new ArrayList(result);
    }
    
    private ArrayList HorizonCode_Horizon_È(final ArrayList shapes) {
        ArrayList last = shapes;
        ArrayList current = shapes;
        for (boolean first = true; current.size() != last.size() || first; first = false, last = current, current = this.Â(current)) {}
        final ArrayList pruned = new ArrayList();
        for (int i = 0; i < current.size(); ++i) {
            pruned.add(current.get(i).ÇŽÕ());
        }
        return pruned;
    }
    
    private ArrayList Â(final ArrayList shapes) {
        ArrayList result = new ArrayList(shapes);
        if (this.£á != null) {
            result = shapes;
        }
        for (int i = 0; i < shapes.size(); ++i) {
            final Shape first = (Shape)shapes.get(i);
            for (int j = i + 1; j < shapes.size(); ++j) {
                final Shape second = (Shape)shapes.get(j);
                if (first.HorizonCode_Horizon_È(second)) {
                    final Shape[] joined = this.áŒŠÆ.Â(first, second);
                    if (joined.length == 1) {
                        if (this.£á != null) {
                            this.HorizonCode_Horizon_È(first);
                            this.HorizonCode_Horizon_È(second);
                            this.Â(joined[0]);
                        }
                        else {
                            result.remove(first);
                            result.remove(second);
                            result.add(joined[0]);
                        }
                        return result;
                    }
                }
            }
        }
        return result;
    }
    
    public boolean HorizonCode_Horizon_È(final Shape shape1, final Shape shape2) {
        if (shape1.HorizonCode_Horizon_È(shape2)) {
            return true;
        }
        for (int i = 0; i < shape1.Ñ¢á(); ++i) {
            final float[] pt = shape1.HorizonCode_Horizon_È(i);
            if (shape2.HorizonCode_Horizon_È(pt[0], pt[1])) {
                return true;
            }
        }
        for (int i = 0; i < shape2.Ñ¢á(); ++i) {
            final float[] pt = shape2.HorizonCode_Horizon_È(i);
            if (shape1.HorizonCode_Horizon_È(pt[0], pt[1])) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.áŒŠÆ.HorizonCode_Horizon_È(this);
        this.Ó();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(Color.à);
        for (int i = 0; i < this.áˆºÑ¢Õ.size(); ++i) {
            final Shape shape = this.áˆºÑ¢Õ.get(i);
            g.HorizonCode_Horizon_È(shape);
        }
        g.Â(Color.Ý);
        if (this.Å != null) {
            g.HorizonCode_Horizon_È(this.Å[0][0]);
        }
        g.Â(0.0f, 320.0f);
        for (int i = 0; i < this.ÂµÈ.size(); ++i) {
            g.Â(Color.Ý);
            final Shape shape = this.ÂµÈ.get(i);
            g.HorizonCode_Horizon_È(shape);
            for (int j = 0; j < shape.Ñ¢á(); ++j) {
                g.Â(Color.Ø­áŒŠá);
                final float[] pt = shape.HorizonCode_Horizon_È(j);
                g.Ó(pt[0] - 1.0f, pt[1] - 1.0f, 3.0f, 3.0f);
            }
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new GeomUtilTileTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y) {
    }
    
    @Override
    public void Â(final float x, final float y) {
        this.á.add(new Vector2f(x, y));
    }
    
    @Override
    public void Ý(final float x, final float y) {
        this.ˆÏ­.add(new Vector2f(x, y));
    }
}

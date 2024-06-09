package org.newdawn.slick.tests;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.GeomUtil;
import org.newdawn.slick.geom.GeomUtilListener;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;








public class GeomUtilTileTest
  extends BasicGame
  implements GeomUtilListener
{
  private Shape source;
  private Shape cut;
  private Shape[] result;
  private GeomUtil util = new GeomUtil();
  

  private ArrayList original = new ArrayList();
  
  private ArrayList combined = new ArrayList();
  

  private ArrayList intersections = new ArrayList();
  
  private ArrayList used = new ArrayList();
  

  private ArrayList[][] quadSpace;
  

  private Shape[][] quadSpaceShapes;
  

  public GeomUtilTileTest()
  {
    super("GeomUtilTileTest");
  }
  











  private void generateSpace(ArrayList shapes, float minx, float miny, float maxx, float maxy, int segments)
  {
    quadSpace = new ArrayList[segments][segments];
    quadSpaceShapes = new Shape[segments][segments];
    
    float dx = (maxx - minx) / segments;
    float dy = (maxy - miny) / segments;
    
    for (int x = 0; x < segments; x++) {
      for (int y = 0; y < segments; y++) {
        quadSpace[x][y] = new ArrayList();
        

        Polygon segmentPolygon = new Polygon();
        segmentPolygon.addPoint(minx + dx * x, miny + dy * y);
        segmentPolygon.addPoint(minx + dx * x + dx, miny + dy * y);
        segmentPolygon.addPoint(minx + dx * x + dx, miny + dy * y + dy);
        segmentPolygon.addPoint(minx + dx * x, miny + dy * y + dy);
        
        for (int i = 0; i < shapes.size(); i++) {
          Shape shape = (Shape)shapes.get(i);
          
          if (collides(shape, segmentPolygon)) {
            quadSpace[x][y].add(shape);
          }
        }
        
        quadSpaceShapes[x][y] = segmentPolygon;
      }
    }
  }
  




  private void removeFromQuadSpace(Shape shape)
  {
    int segments = quadSpace.length;
    
    for (int x = 0; x < segments; x++) {
      for (int y = 0; y < segments; y++) {
        quadSpace[x][y].remove(shape);
      }
    }
  }
  




  private void addToQuadSpace(Shape shape)
  {
    int segments = quadSpace.length;
    
    for (int x = 0; x < segments; x++) {
      for (int y = 0; y < segments; y++) {
        if (collides(shape, quadSpaceShapes[x][y])) {
          quadSpace[x][y].add(shape);
        }
      }
    }
  }
  


  public void init()
  {
    int size = 10;
    int[][] map = {
      { 0, 0, 0, 0, 0, 0, 0, 3 }, 
      { 0, 1, 1, 1, 0, 0, 1, 1, 1 }, 
      { 0, 1, 1, 0, 0, 0, 5, 1, 6 }, 
      { 0, 1, 2, 0, 0, 0, 4, 1, 1 }, 
      { 0, 1, 1, 0, 0, 0, 1, 1 }, 
      { 0, 0, 0, 0, 3, 0, 1, 1 }, 
      { 0, 0, 0, 1, 1, 0, 0, 0, 1 }, 
      { 0, 0, 0, 1, 1 }, 
      new int[10], 
      new int[10] };
    











    for (int x = 0; x < map[0].length; x++) {
      for (int y = 0; y < map.length; y++) {
        if (map[y][x] != 0) {
          switch (map[y][x]) {
          case 1: 
            Polygon p2 = new Polygon();
            p2.addPoint(x * 32, y * 32);
            p2.addPoint(x * 32 + 32, y * 32);
            p2.addPoint(x * 32 + 32, y * 32 + 32);
            p2.addPoint(x * 32, y * 32 + 32);
            original.add(p2);
            break;
          case 2: 
            Polygon poly = new Polygon();
            poly.addPoint(x * 32, y * 32);
            poly.addPoint(x * 32 + 32, y * 32);
            poly.addPoint(x * 32, y * 32 + 32);
            original.add(poly);
            break;
          case 3: 
            Circle ellipse = new Circle(x * 32 + 16, y * 32 + 32, 16.0F, 16);
            original.add(ellipse);
            break;
          case 4: 
            Polygon p = new Polygon();
            p.addPoint(x * 32 + 32, y * 32);
            p.addPoint(x * 32 + 32, y * 32 + 32);
            p.addPoint(x * 32, y * 32 + 32);
            original.add(p);
            break;
          case 5: 
            Polygon p3 = new Polygon();
            p3.addPoint(x * 32, y * 32);
            p3.addPoint(x * 32 + 32, y * 32);
            p3.addPoint(x * 32 + 32, y * 32 + 32);
            original.add(p3);
            break;
          case 6: 
            Polygon p4 = new Polygon();
            p4.addPoint(x * 32, y * 32);
            p4.addPoint(x * 32 + 32, y * 32);
            p4.addPoint(x * 32, y * 32 + 32);
            original.add(p4);
          }
          
        }
      }
    }
    
    long before = System.currentTimeMillis();
    

    generateSpace(original, 0.0F, 0.0F, (size + 1) * 32, (size + 1) * 32, 8);
    combined = combineQuadSpace();
    



    long after = System.currentTimeMillis();
    System.out.println("Combine took: " + (after - before));
    System.out.println("Combine result: " + combined.size());
  }
  




  private ArrayList combineQuadSpace()
  {
    boolean updated = true;
    int x; for (; updated; 
        

        x < quadSpace.length)
    {
      updated = false;
      
      x = 0; continue;
      for (int y = 0; y < quadSpace.length; y++) {
        ArrayList shapes = quadSpace[x][y];
        int before = shapes.size();
        combine(shapes);
        int after = shapes.size();
        
        updated |= before != after;
      }
      x++;
    }
    











    HashSet result = new HashSet();
    
    for (int x = 0; x < quadSpace.length; x++) {
      for (int y = 0; y < quadSpace.length; y++) {
        result.addAll(quadSpace[x][y]);
      }
    }
    
    return new ArrayList(result);
  }
  






  private ArrayList combine(ArrayList shapes)
  {
    ArrayList last = shapes;
    ArrayList current = shapes;
    boolean first = true;
    
    while ((current.size() != last.size()) || (first)) {
      first = false;
      last = current;
      current = combineImpl(current);
    }
    
    ArrayList pruned = new ArrayList();
    for (int i = 0; i < current.size(); i++) {
      pruned.add(((Shape)current.get(i)).prune());
    }
    return pruned;
  }
  







  private ArrayList combineImpl(ArrayList shapes)
  {
    ArrayList result = new ArrayList(shapes);
    if (quadSpace != null) {
      result = shapes;
    }
    
    for (int i = 0; i < shapes.size(); i++) {
      Shape first = (Shape)shapes.get(i);
      for (int j = i + 1; j < shapes.size(); j++) {
        Shape second = (Shape)shapes.get(j);
        
        if (first.intersects(second))
        {


          Shape[] joined = util.union(first, second);
          if (joined.length == 1) {
            if (quadSpace != null) {
              removeFromQuadSpace(first);
              removeFromQuadSpace(second);
              addToQuadSpace(joined[0]);
            } else {
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
  






  public boolean collides(Shape shape1, Shape shape2)
  {
    if (shape1.intersects(shape2)) {
      return true;
    }
    for (int i = 0; i < shape1.getPointCount(); i++) {
      float[] pt = shape1.getPoint(i);
      if (shape2.contains(pt[0], pt[1])) {
        return true;
      }
    }
    for (int i = 0; i < shape2.getPointCount(); i++) {
      float[] pt = shape2.getPoint(i);
      if (shape1.contains(pt[0], pt[1])) {
        return true;
      }
    }
    
    return false;
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    util.setListener(this);
    init();
  }
  



  public void update(GameContainer container, int delta)
    throws SlickException
  {}
  



  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.setColor(Color.green);
    for (int i = 0; i < original.size(); i++) {
      Shape shape = (Shape)original.get(i);
      g.draw(shape);
    }
    
    g.setColor(Color.white);
    if (quadSpaceShapes != null) {
      g.draw(quadSpaceShapes[0][0]);
    }
    
    g.translate(0.0F, 320.0F);
    
    for (int i = 0; i < combined.size(); i++) {
      g.setColor(Color.white);
      Shape shape = (Shape)combined.get(i);
      g.draw(shape);
      for (int j = 0; j < shape.getPointCount(); j++) {
        g.setColor(Color.yellow);
        float[] pt = shape.getPoint(j);
        g.fillOval(pt[0] - 1.0F, pt[1] - 1.0F, 3.0F, 3.0F);
      }
    }
  }
  





  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(
        new GeomUtilTileTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  
  public void pointExcluded(float x, float y) {}
  
  public void pointIntersected(float x, float y)
  {
    intersections.add(new Vector2f(x, y));
  }
  
  public void pointUsed(float x, float y) {
    used.add(new Vector2f(x, y));
  }
}

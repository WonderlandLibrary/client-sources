package org.newdawn.slick.tests;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.GeomUtil;
import org.newdawn.slick.geom.GeomUtilListener;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;









public class GeomUtilTest
  extends BasicGame
  implements GeomUtilListener
{
  private Shape source;
  private Shape cut;
  private Shape[] result;
  private ArrayList points = new ArrayList();
  
  private ArrayList marks = new ArrayList();
  
  private ArrayList exclude = new ArrayList();
  

  private boolean dynamic;
  
  private GeomUtil util = new GeomUtil();
  

  private int xp;
  

  private int yp;
  
  private Circle circle;
  
  private Shape rect;
  
  private Polygon star;
  
  private boolean union;
  

  public GeomUtilTest()
  {
    super("GeomUtilTest");
  }
  


  public void init()
  {
    Polygon source = new Polygon();
    source.addPoint(100.0F, 100.0F);
    source.addPoint(150.0F, 80.0F);
    source.addPoint(210.0F, 120.0F);
    source.addPoint(340.0F, 150.0F);
    source.addPoint(150.0F, 200.0F);
    source.addPoint(120.0F, 250.0F);
    this.source = source;
    
    circle = new Circle(0.0F, 0.0F, 50.0F);
    rect = new Rectangle(-100.0F, -40.0F, 200.0F, 80.0F);
    star = new Polygon();
    
    float dis = 40.0F;
    for (int i = 0; i < 360; i += 30) {
      dis = dis == 40.0F ? 60 : 40;
      double x = Math.cos(Math.toRadians(i)) * dis;
      double y = Math.sin(Math.toRadians(i)) * dis;
      star.addPoint((float)x, (float)y);
    }
    
    cut = circle;
    cut.setLocation(203.0F, 78.0F);
    xp = ((int)cut.getCenterX());
    yp = ((int)cut.getCenterY());
    makeBoolean();
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    util.setListener(this);
    init();
    container.setVSync(true);
  }
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {
    if (container.getInput().isKeyPressed(57)) {
      dynamic = (!dynamic);
    }
    if (container.getInput().isKeyPressed(28)) {
      union = (!union);
      makeBoolean();
    }
    if (container.getInput().isKeyPressed(2)) {
      cut = circle;
      circle.setCenterX(xp);
      circle.setCenterY(yp);
      makeBoolean();
    }
    if (container.getInput().isKeyPressed(3)) {
      cut = rect;
      rect.setCenterX(xp);
      rect.setCenterY(yp);
      makeBoolean();
    }
    if (container.getInput().isKeyPressed(4)) {
      cut = star;
      star.setCenterX(xp);
      star.setCenterY(yp);
      makeBoolean();
    }
    
    if (dynamic) {
      xp = container.getInput().getMouseX();
      yp = container.getInput().getMouseY();
      makeBoolean();
    }
  }
  


  private void makeBoolean()
  {
    marks.clear();
    points.clear();
    exclude.clear();
    cut.setCenterX(xp);
    cut.setCenterY(yp);
    if (union) {
      result = util.union(source, cut);
    } else {
      result = util.subtract(source, cut);
    }
  }
  


  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.drawString("Space - toggle movement of cutting shape", 530.0F, 10.0F);
    g.drawString("1,2,3 - select cutting shape", 530.0F, 30.0F);
    g.drawString("Mouse wheel - rotate shape", 530.0F, 50.0F);
    g.drawString("Enter - toggle union/subtract", 530.0F, 70.0F);
    g.drawString("MODE: " + (union ? "Union" : "Cut"), 530.0F, 200.0F);
    
    g.setColor(Color.green);
    g.draw(source);
    g.setColor(Color.red);
    g.draw(cut);
    
    g.setColor(Color.white);
    for (int i = 0; i < exclude.size(); i++) {
      Vector2f pt = (Vector2f)exclude.get(i);
      g.drawOval(x - 3.0F, y - 3.0F, 7.0F, 7.0F);
    }
    g.setColor(Color.yellow);
    for (int i = 0; i < points.size(); i++) {
      Vector2f pt = (Vector2f)points.get(i);
      g.fillOval(x - 1.0F, y - 1.0F, 3.0F, 3.0F);
    }
    g.setColor(Color.white);
    for (int i = 0; i < marks.size(); i++) {
      Vector2f pt = (Vector2f)marks.get(i);
      g.fillOval(x - 1.0F, y - 1.0F, 3.0F, 3.0F);
    }
    
    g.translate(0.0F, 300.0F);
    g.setColor(Color.white);
    if (result != null) {
      for (int i = 0; i < result.length; i++) {
        g.draw(result[i]);
      }
      
      g.drawString("Polys:" + result.length, 10.0F, 100.0F);
      g.drawString("X:" + xp, 10.0F, 120.0F);
      g.drawString("Y:" + yp, 10.0F, 130.0F);
    }
  }
  




  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new GeomUtilTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  
  public void pointExcluded(float x, float y) {
    exclude.add(new Vector2f(x, y));
  }
  
  public void pointIntersected(float x, float y) {
    marks.add(new Vector2f(x, y));
  }
  
  public void pointUsed(float x, float y) {
    points.add(new Vector2f(x, y));
  }
  
  public void mouseWheelMoved(int change) {
    if (dynamic) {
      if (change < 0) {
        cut = cut.transform(Transform.createRotateTransform((float)Math.toRadians(10.0D), cut.getCenterX(), cut.getCenterY()));
      } else {
        cut = cut.transform(Transform.createRotateTransform((float)Math.toRadians(-10.0D), cut.getCenterX(), cut.getCenterY()));
      }
    }
  }
}

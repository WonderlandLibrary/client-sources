package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.opengl.renderer.Renderer;






public class GeomTest
  extends BasicGame
{
  private Shape rect = new Rectangle(100.0F, 100.0F, 100.0F, 100.0F);
  
  private Shape circle = new Circle(500.0F, 200.0F, 50.0F);
  
  private Shape rect1 = new Rectangle(150.0F, 120.0F, 50.0F, 100.0F).transform(Transform.createTranslateTransform(50.0F, 50.0F));
  
  private Shape rect2 = new Rectangle(310.0F, 210.0F, 50.0F, 100.0F)
    .transform(Transform.createRotateTransform((float)Math.toRadians(45.0D), 335.0F, 260.0F));
  
  private Shape circle1 = new Circle(150.0F, 90.0F, 30.0F);
  
  private Shape circle2 = new Circle(310.0F, 110.0F, 70.0F);
  
  private Shape circle3 = new Ellipse(510.0F, 150.0F, 70.0F, 70.0F);
  
  private Shape circle4 = new Ellipse(510.0F, 350.0F, 30.0F, 30.0F).transform(
    Transform.createTranslateTransform(-510.0F, -350.0F)).transform(
    Transform.createScaleTransform(2.0F, 2.0F)).transform(
    Transform.createTranslateTransform(510.0F, 350.0F));
  
  private Shape roundRect = new RoundedRectangle(50.0F, 175.0F, 100.0F, 100.0F, 20.0F);
  
  private Shape roundRect2 = new RoundedRectangle(50.0F, 280.0F, 50.0F, 50.0F, 20.0F, 20, 5);
  


  public GeomTest()
  {
    super("Geom Test");
  }
  


  public void init(GameContainer container)
    throws SlickException
  {}
  


  public void render(GameContainer container, Graphics g)
  {
    g.setColor(Color.white);
    g.drawString("Red indicates a collision, green indicates no collision", 50.0F, 420.0F);
    g.drawString("White are the targets", 50.0F, 435.0F);
    
    g.pushTransform();
    g.translate(100.0F, 100.0F);
    g.pushTransform();
    g.translate(-50.0F, -50.0F);
    g.scale(10.0F, 10.0F);
    g.setColor(Color.red);
    g.fillRect(0.0F, 0.0F, 5.0F, 5.0F);
    g.setColor(Color.white);
    g.drawRect(0.0F, 0.0F, 5.0F, 5.0F);
    g.popTransform();
    g.setColor(Color.green);
    g.fillRect(20.0F, 20.0F, 50.0F, 50.0F);
    g.popTransform();
    
    g.setColor(Color.white);
    g.draw(rect);
    g.draw(circle);
    
    g.setColor(rect1.intersects(rect) ? Color.red : Color.green);
    g.draw(rect1);
    g.setColor(rect2.intersects(rect) ? Color.red : Color.green);
    g.draw(rect2);
    g.setColor(roundRect.intersects(rect) ? Color.red : Color.green);
    g.draw(roundRect);
    g.setColor(circle1.intersects(rect) ? Color.red : Color.green);
    g.draw(circle1);
    g.setColor(circle2.intersects(rect) ? Color.red : Color.green);
    g.draw(circle2);
    g.setColor(circle3.intersects(circle) ? Color.red : Color.green);
    g.fill(circle3);
    g.setColor(circle4.intersects(circle) ? Color.red : Color.green);
    g.draw(circle4);
    
    g.fill(roundRect2);
    g.setColor(Color.blue);
    g.draw(roundRect2);
    g.setColor(Color.blue);
    g.draw(new Circle(100.0F, 100.0F, 50.0F));
    g.drawRect(50.0F, 50.0F, 100.0F, 100.0F);
  }
  




  public void update(GameContainer container, int delta) {}
  



  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      Renderer.setRenderer(2);
      
      AppGameContainer container = new AppGameContainer(new GeomTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}

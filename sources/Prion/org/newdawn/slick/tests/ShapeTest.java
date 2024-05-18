package org.newdawn.slick.tests;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.opengl.renderer.Renderer;















public class ShapeTest
  extends BasicGame
{
  private Rectangle rect;
  private RoundedRectangle roundRect;
  private Ellipse ellipse;
  private Circle circle;
  private Polygon polygon;
  private ArrayList shapes;
  private boolean[] keys;
  private char[] lastChar;
  private Polygon randomShape = new Polygon();
  


  public ShapeTest()
  {
    super("Geom Test");
  }
  
  public void createPoly(float x, float y) {
    int size = 20;
    int change = 10;
    
    randomShape = new Polygon();
    
    randomShape.addPoint(0 + (int)(Math.random() * change), 0 + (int)(Math.random() * change));
    randomShape.addPoint(size - (int)(Math.random() * change), 0 + (int)(Math.random() * change));
    randomShape.addPoint(size - (int)(Math.random() * change), size - (int)(Math.random() * change));
    randomShape.addPoint(0 + (int)(Math.random() * change), size - (int)(Math.random() * change));
    

    randomShape.setCenterX(x);
    randomShape.setCenterY(y);
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    shapes = new ArrayList();
    rect = new Rectangle(10.0F, 10.0F, 100.0F, 80.0F);
    shapes.add(rect);
    roundRect = new RoundedRectangle(150.0F, 10.0F, 60.0F, 80.0F, 20.0F);
    shapes.add(roundRect);
    ellipse = new Ellipse(350.0F, 40.0F, 50.0F, 30.0F);
    shapes.add(ellipse);
    circle = new Circle(470.0F, 60.0F, 50.0F);
    shapes.add(circle);
    polygon = new Polygon(new float[] { 550.0F, 10.0F, 600.0F, 40.0F, 620.0F, 100.0F, 570.0F, 130.0F });
    shapes.add(polygon);
    
    keys = new boolean['Ā'];
    lastChar = new char['Ā'];
    createPoly(200.0F, 200.0F);
  }
  


  public void render(GameContainer container, Graphics g)
  {
    g.setColor(Color.green);
    
    for (int i = 0; i < shapes.size(); i++) {
      g.fill((Shape)shapes.get(i));
    }
    g.fill(randomShape);
    g.setColor(Color.black);
    g.setAntiAlias(true);
    g.draw(randomShape);
    g.setAntiAlias(false);
    
    g.setColor(Color.white);
    g.drawString("keys", 10.0F, 300.0F);
    g.drawString("wasd - move rectangle", 10.0F, 315.0F);
    g.drawString("WASD - resize rectangle", 10.0F, 330.0F);
    g.drawString("tgfh - move rounded rectangle", 10.0F, 345.0F);
    g.drawString("TGFH - resize rounded rectangle", 10.0F, 360.0F);
    g.drawString("ry - resize corner radius on rounded rectangle", 10.0F, 375.0F);
    g.drawString("ikjl - move ellipse", 10.0F, 390.0F);
    g.drawString("IKJL - resize ellipse", 10.0F, 405.0F);
    g.drawString("Arrows - move circle", 10.0F, 420.0F);
    g.drawString("Page Up/Page Down - resize circle", 10.0F, 435.0F);
    g.drawString("numpad 8546 - move polygon", 10.0F, 450.0F);
  }
  



  public void update(GameContainer container, int delta)
  {
    createPoly(200.0F, 200.0F);
    if (keys[1] != 0) {
      System.exit(0);
    }
    if (keys[17] != 0) {
      if (lastChar[17] == 'w') {
        rect.setY(rect.getY() - 1.0F);
      }
      else {
        rect.setHeight(rect.getHeight() - 1.0F);
      }
    }
    if (keys[31] != 0) {
      if (lastChar[31] == 's') {
        rect.setY(rect.getY() + 1.0F);
      }
      else {
        rect.setHeight(rect.getHeight() + 1.0F);
      }
    }
    if (keys[30] != 0) {
      if (lastChar[30] == 'a') {
        rect.setX(rect.getX() - 1.0F);
      }
      else {
        rect.setWidth(rect.getWidth() - 1.0F);
      }
    }
    if (keys[32] != 0) {
      if (lastChar[32] == 'd') {
        rect.setX(rect.getX() + 1.0F);
      }
      else {
        rect.setWidth(rect.getWidth() + 1.0F);
      }
    }
    if (keys[20] != 0) {
      if (lastChar[20] == 't') {
        roundRect.setY(roundRect.getY() - 1.0F);
      }
      else {
        roundRect.setHeight(roundRect.getHeight() - 1.0F);
      }
    }
    if (keys[34] != 0) {
      if (lastChar[34] == 'g') {
        roundRect.setY(roundRect.getY() + 1.0F);
      }
      else {
        roundRect.setHeight(roundRect.getHeight() + 1.0F);
      }
    }
    if (keys[33] != 0) {
      if (lastChar[33] == 'f') {
        roundRect.setX(roundRect.getX() - 1.0F);
      }
      else {
        roundRect.setWidth(roundRect.getWidth() - 1.0F);
      }
    }
    if (keys[35] != 0) {
      if (lastChar[35] == 'h') {
        roundRect.setX(roundRect.getX() + 1.0F);
      }
      else {
        roundRect.setWidth(roundRect.getWidth() + 1.0F);
      }
    }
    if (keys[19] != 0) {
      roundRect.setCornerRadius(roundRect.getCornerRadius() - 1.0F);
    }
    if (keys[21] != 0) {
      roundRect.setCornerRadius(roundRect.getCornerRadius() + 1.0F);
    }
    if (keys[23] != 0) {
      if (lastChar[23] == 'i') {
        ellipse.setCenterY(ellipse.getCenterY() - 1.0F);
      }
      else {
        ellipse.setRadius2(ellipse.getRadius2() - 1.0F);
      }
    }
    if (keys[37] != 0) {
      if (lastChar[37] == 'k') {
        ellipse.setCenterY(ellipse.getCenterY() + 1.0F);
      }
      else {
        ellipse.setRadius2(ellipse.getRadius2() + 1.0F);
      }
    }
    if (keys[36] != 0) {
      if (lastChar[36] == 'j') {
        ellipse.setCenterX(ellipse.getCenterX() - 1.0F);
      }
      else {
        ellipse.setRadius1(ellipse.getRadius1() - 1.0F);
      }
    }
    if (keys[38] != 0) {
      if (lastChar[38] == 'l') {
        ellipse.setCenterX(ellipse.getCenterX() + 1.0F);
      }
      else {
        ellipse.setRadius1(ellipse.getRadius1() + 1.0F);
      }
    }
    if (keys['È'] != 0) {
      circle.setCenterY(circle.getCenterY() - 1.0F);
    }
    if (keys['Ð'] != 0) {
      circle.setCenterY(circle.getCenterY() + 1.0F);
    }
    if (keys['Ë'] != 0) {
      circle.setCenterX(circle.getCenterX() - 1.0F);
    }
    if (keys['Í'] != 0) {
      circle.setCenterX(circle.getCenterX() + 1.0F);
    }
    if (keys['É'] != 0) {
      circle.setRadius(circle.getRadius() - 1.0F);
    }
    if (keys['Ñ'] != 0) {
      circle.setRadius(circle.getRadius() + 1.0F);
    }
    if (keys[72] != 0) {
      polygon.setY(polygon.getY() - 1.0F);
    }
    if (keys[76] != 0) {
      polygon.setY(polygon.getY() + 1.0F);
    }
    if (keys[75] != 0) {
      polygon.setX(polygon.getX() - 1.0F);
    }
    if (keys[77] != 0) {
      polygon.setX(polygon.getX() + 1.0F);
    }
  }
  


  public void keyPressed(int key, char c)
  {
    keys[key] = true;
    lastChar[key] = c;
  }
  


  public void keyReleased(int key, char c)
  {
    keys[key] = false;
  }
  



  public static void main(String[] argv)
  {
    try
    {
      Renderer.setRenderer(2);
      AppGameContainer container = new AppGameContainer(new ShapeTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}

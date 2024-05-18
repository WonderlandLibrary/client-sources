package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;






public class TransformTest2
  extends BasicGame
{
  private float scale = 1.0F;
  

  private boolean scaleUp;
  
  private boolean scaleDown;
  
  private float camX = 320.0F;
  
  private float camY = 240.0F;
  

  private boolean moveLeft;
  

  private boolean moveUp;
  
  private boolean moveRight;
  
  private boolean moveDown;
  

  public TransformTest2()
  {
    super("Transform Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    container.setTargetFrameRate(100);
  }
  


  public void render(GameContainer contiainer, Graphics g)
  {
    g.translate(320.0F, 240.0F);
    
    g.translate(-camX * scale, -camY * scale);
    

    g.scale(scale, scale);
    
    g.setColor(Color.red);
    for (int x = 0; x < 10; x++) {
      for (int y = 0; y < 10; y++) {
        g.fillRect(65036 + x * 100, 65036 + y * 100, 80.0F, 80.0F);
      }
    }
    
    g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
    g.fillRect(-320.0F, -240.0F, 640.0F, 480.0F);
    g.setColor(Color.white);
    g.drawRect(-320.0F, -240.0F, 640.0F, 480.0F);
  }
  


  public void update(GameContainer container, int delta)
  {
    if (scaleUp) {
      scale += delta * 0.001F;
    }
    if (scaleDown) {
      scale -= delta * 0.001F;
    }
    
    float moveSpeed = delta * 0.4F * (1.0F / scale);
    
    if (moveLeft) {
      camX -= moveSpeed;
    }
    if (moveUp) {
      camY -= moveSpeed;
    }
    if (moveRight) {
      camX += moveSpeed;
    }
    if (moveDown) {
      camY += moveSpeed;
    }
  }
  


  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
    if (key == 16) {
      scaleUp = true;
    }
    if (key == 30) {
      scaleDown = true;
    }
    
    if (key == 203) {
      moveLeft = true;
    }
    if (key == 200) {
      moveUp = true;
    }
    if (key == 205) {
      moveRight = true;
    }
    if (key == 208) {
      moveDown = true;
    }
  }
  


  public void keyReleased(int key, char c)
  {
    if (key == 16) {
      scaleUp = false;
    }
    if (key == 30) {
      scaleDown = false;
    }
    
    if (key == 203) {
      moveLeft = false;
    }
    if (key == 200) {
      moveUp = false;
    }
    if (key == 205) {
      moveRight = false;
    }
    if (key == 208) {
      moveDown = false;
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new TransformTest2());
      container.setDisplayMode(640, 480, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}

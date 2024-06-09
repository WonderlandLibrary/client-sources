package org.newdawn.slick.tests;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;










public class AnimationTest
  extends BasicGame
{
  private Animation animation;
  private Animation limited;
  private Animation manual;
  private Animation pingPong;
  private GameContainer container;
  private int start = 5000;
  


  public AnimationTest()
  {
    super("Animation Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    this.container = container;
    
    SpriteSheet sheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
    animation = new Animation();
    for (int i = 0; i < 8; i++) {
      animation.addFrame(sheet.getSprite(i, 0), 150);
    }
    limited = new Animation();
    for (int i = 0; i < 8; i++) {
      limited.addFrame(sheet.getSprite(i, 0), 150);
    }
    limited.stopAt(7);
    manual = new Animation(false);
    for (int i = 0; i < 8; i++) {
      manual.addFrame(sheet.getSprite(i, 0), 150);
    }
    pingPong = new Animation(sheet, 0, 0, 7, 0, true, 150, true);
    pingPong.setPingPong(true);
    container.getGraphics().setBackground(new Color(0.4F, 0.6F, 0.6F));
  }
  


  public void render(GameContainer container, Graphics g)
  {
    g.drawString("Space to restart() animation", 100.0F, 50.0F);
    g.drawString("Til Limited animation: " + start, 100.0F, 500.0F);
    g.drawString("Hold 1 to move the manually animated", 100.0F, 70.0F);
    g.drawString("PingPong Frame:" + pingPong.getFrame(), 600.0F, 70.0F);
    
    g.scale(-1.0F, 1.0F);
    animation.draw(-100.0F, 100.0F);
    animation.draw(-200.0F, 100.0F, 144.0F, 260.0F);
    if (start < 0) {
      limited.draw(-400.0F, 100.0F, 144.0F, 260.0F);
    }
    manual.draw(-600.0F, 100.0F, 144.0F, 260.0F);
    pingPong.draw(-700.0F, 100.0F, 72.0F, 130.0F);
  }
  


  public void update(GameContainer container, int delta)
  {
    if (container.getInput().isKeyDown(2)) {
      manual.update(delta);
    }
    if (start >= 0) {
      start -= delta;
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new AnimationTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  


  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      container.exit();
    }
    if (key == 57) {
      limited.restart();
    }
  }
}

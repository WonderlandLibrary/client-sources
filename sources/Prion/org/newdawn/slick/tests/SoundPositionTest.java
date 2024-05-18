package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;









public class SoundPositionTest
  extends BasicGame
{
  private GameContainer myContainer;
  private Music music;
  private int[] engines = new int[3];
  


  public SoundPositionTest()
  {
    super("Music Position Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    SoundStore.get().setMaxSources(32);
    
    myContainer = container;
    music = new Music("testdata/kirby.ogg", true);
    music.play();
  }
  


  public void render(GameContainer container, Graphics g)
  {
    g.setColor(Color.white);
    g.drawString("Position: " + music.getPosition(), 100.0F, 100.0F);
    g.drawString("Space - Pause/Resume", 100.0F, 130.0F);
    g.drawString("Right Arrow - Advance 5 seconds", 100.0F, 145.0F);
  }
  



  public void update(GameContainer container, int delta) {}
  



  public void keyPressed(int key, char c)
  {
    if (key == 57) {
      if (music.playing()) {
        music.pause();
      } else {
        music.resume();
      }
    }
    if (key == 205) {
      music.setPosition(music.getPosition() + 5.0F);
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new SoundPositionTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}

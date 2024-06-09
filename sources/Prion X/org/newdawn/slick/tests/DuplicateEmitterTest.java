package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;












public class DuplicateEmitterTest
  extends BasicGame
{
  private GameContainer container;
  private ParticleSystem explosionSystem;
  private ConfigurableEmitter explosionEmitter;
  
  public DuplicateEmitterTest()
  {
    super("DuplicateEmitterTest");
  }
  



  public void init(GameContainer container)
    throws SlickException
  {
    this.container = container;
    
    try
    {
      explosionSystem = ParticleIO.loadConfiguredSystem("testdata/endlessexplosion.xml");
      
      explosionEmitter = ((ConfigurableEmitter)explosionSystem.getEmitter(0));
      
      explosionEmitter.setPosition(400.0F, 100.0F);
      
      for (int i = 0; i < 5; i++)
      {
        ConfigurableEmitter newOne = explosionEmitter.duplicate();
        
        if (newOne == null) {
          throw new SlickException("Failed to duplicate explosionEmitter");
        }
        name = (name + "_" + i);
        
        newOne.setPosition((i + 1) * 133, 400.0F);
        
        explosionSystem.addEmitter(newOne);
      }
    } catch (IOException e) {
      throw new SlickException("Failed to load particle systems", e);
    }
  }
  

  public void update(GameContainer container, int delta)
    throws SlickException
  {
    explosionSystem.update(delta);
  }
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    explosionSystem.render();
  }
  


  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      container.exit();
    }
    if (key == 37) {
      explosionEmitter.wrapUp();
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new DuplicateEmitterTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}

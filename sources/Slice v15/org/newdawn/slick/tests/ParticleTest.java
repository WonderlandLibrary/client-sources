package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;







public class ParticleTest
  extends BasicGame
{
  private ParticleSystem system;
  private int mode = 2;
  


  public ParticleTest()
  {
    super("Particle Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    Image image = new Image("testdata/particle.tga", true);
    system = new ParticleSystem(image);
    
    system.addEmitter(new FireEmitter(400, 300, 45.0F));
    system.addEmitter(new FireEmitter(200, 300, 60.0F));
    system.addEmitter(new FireEmitter(600, 300, 30.0F));
  }
  




  public void render(GameContainer container, Graphics g)
  {
    for (int i = 0; i < 100; i++) {
      g.translate(1.0F, 1.0F);
      system.render();
    }
    g.resetTransform();
    g.drawString("Press space to toggle blending mode", 200.0F, 500.0F);
    g.drawString("Particle Count: " + system.getParticleCount() * 100, 200.0F, 520.0F);
  }
  


  public void update(GameContainer container, int delta)
  {
    system.update(delta);
  }
  


  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
    if (key == 57) {
      mode = (1 == mode ? 2 : 1);
      system.setBlendingMode(mode);
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new ParticleTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}

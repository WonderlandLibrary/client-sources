package org.newdawn.slick.particles.effects;

import org.newdawn.slick.Image;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;








public class FireEmitter
  implements ParticleEmitter
{
  private int x;
  private int y;
  private int interval = 50;
  
  private int timer;
  
  private float size = 40.0F;
  





  public FireEmitter() {}
  




  public FireEmitter(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  






  public FireEmitter(int x, int y, float size)
  {
    this.x = x;
    this.y = y;
    this.size = size;
  }
  


  public void update(ParticleSystem system, int delta)
  {
    timer -= delta;
    if (timer <= 0) {
      timer = interval;
      Particle p = system.getNewParticle(this, 1000.0F);
      p.setColor(1.0F, 1.0F, 1.0F, 0.5F);
      p.setPosition(x, y);
      p.setSize(size);
      float vx = (float)(-0.019999999552965164D + Math.random() * 0.03999999910593033D);
      float vy = (float)-(Math.random() * 0.15000000596046448D);
      p.setVelocity(vx, vy, 1.1F);
    }
  }
  


  public void updateParticle(Particle particle, int delta)
  {
    if (particle.getLife() > 600.0F) {
      particle.adjustSize(0.07F * delta);
    } else {
      particle.adjustSize(-0.04F * delta * (size / 40.0F));
    }
    float c = 0.002F * delta;
    particle.adjustColor(0.0F, -c / 2.0F, -c * 2.0F, -c / 4.0F);
  }
  


  public boolean isEnabled()
  {
    return true;
  }
  



  public void setEnabled(boolean enabled) {}
  



  public boolean completed()
  {
    return false;
  }
  


  public boolean useAdditive()
  {
    return false;
  }
  


  public Image getImage()
  {
    return null;
  }
  


  public boolean usePoints(ParticleSystem system)
  {
    return false;
  }
  


  public boolean isOriented()
  {
    return false;
  }
  
  public void wrapUp() {}
  
  public void resetState() {}
}

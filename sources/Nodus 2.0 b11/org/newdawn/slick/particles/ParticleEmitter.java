package org.newdawn.slick.particles;

import org.newdawn.slick.Image;

public abstract interface ParticleEmitter
{
  public abstract void update(ParticleSystem paramParticleSystem, int paramInt);
  
  public abstract boolean completed();
  
  public abstract void wrapUp();
  
  public abstract void updateParticle(Particle paramParticle, int paramInt);
  
  public abstract boolean isEnabled();
  
  public abstract void setEnabled(boolean paramBoolean);
  
  public abstract boolean useAdditive();
  
  public abstract Image getImage();
  
  public abstract boolean isOriented();
  
  public abstract boolean usePoints(ParticleSystem paramParticleSystem);
  
  public abstract void resetState();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.particles.ParticleEmitter
 * JD-Core Version:    0.7.0.1
 */
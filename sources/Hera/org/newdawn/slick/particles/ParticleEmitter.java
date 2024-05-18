package org.newdawn.slick.particles;

import org.newdawn.slick.Image;

public interface ParticleEmitter {
  void update(ParticleSystem paramParticleSystem, int paramInt);
  
  boolean completed();
  
  void wrapUp();
  
  void updateParticle(Particle paramParticle, int paramInt);
  
  boolean isEnabled();
  
  void setEnabled(boolean paramBoolean);
  
  boolean useAdditive();
  
  Image getImage();
  
  boolean isOriented();
  
  boolean usePoints(ParticleSystem paramParticleSystem);
  
  void resetState();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\particles\ParticleEmitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
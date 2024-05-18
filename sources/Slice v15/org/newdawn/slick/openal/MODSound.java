package org.newdawn.slick.openal;

import java.io.IOException;
import java.io.InputStream;
import net.SliceClient.module.Module;






















































public class MODSound
  extends AudioImpl
{
  private Module module;
  private SoundStore store;
  
  public MODSound(SoundStore store, InputStream in)
    throws IOException
  {}
  
  private void cleanUpSource() {}
  
  public void poll() {}
  
  public int playAsSoundEffect(float pitch, float gain, boolean loop)
  {
    return -1;
  }
  


  public void stop()
  {
    store.setMOD(null);
  }
  


  public float getPosition()
  {
    throw new RuntimeException("Positioning on modules is not currently supported");
  }
  


  public boolean setPosition(float position)
  {
    throw new RuntimeException("Positioning on modules is not currently supported");
  }
}

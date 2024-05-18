package org.newdawn.slick.openal;



public class NullAudio
  implements Audio
{
  public NullAudio() {}
  


  public int getBufferID()
  {
    return 0;
  }
  


  public float getPosition()
  {
    return 0.0F;
  }
  


  public boolean isPlaying()
  {
    return false;
  }
  


  public int playAsMusic(float pitch, float gain, boolean loop)
  {
    return 0;
  }
  


  public int playAsSoundEffect(float pitch, float gain, boolean loop)
  {
    return 0;
  }
  



  public int playAsSoundEffect(float pitch, float gain, boolean loop, float x, float y, float z)
  {
    return 0;
  }
  


  public boolean setPosition(float position)
  {
    return false;
  }
  
  public void stop() {}
}

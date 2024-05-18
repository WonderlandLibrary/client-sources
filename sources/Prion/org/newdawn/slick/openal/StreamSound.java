package org.newdawn.slick.openal;

import java.io.IOException;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.util.Log;













public class StreamSound
  extends AudioImpl
{
  private OpenALStreamPlayer player;
  
  public StreamSound(OpenALStreamPlayer player)
  {
    this.player = player;
  }
  


  public boolean isPlaying()
  {
    return SoundStore.get().isPlaying(player);
  }
  

  public int playAsMusic(float pitch, float gain, boolean loop)
  {
    try
    {
      cleanUpSource();
      
      player.setup(pitch);
      player.play(loop);
      SoundStore.get().setStream(player);
    } catch (IOException e) {
      Log.error("Failed to read OGG source: " + player.getSource());
    }
    
    return SoundStore.get().getSource(0);
  }
  


  private void cleanUpSource()
  {
    SoundStore store = SoundStore.get();
    
    AL10.alSourceStop(store.getSource(0));
    IntBuffer buffer = BufferUtils.createIntBuffer(1);
    int queued = AL10.alGetSourcei(store.getSource(0), 4117);
    
    while (queued > 0)
    {
      AL10.alSourceUnqueueBuffers(store.getSource(0), buffer);
      queued--;
    }
    
    AL10.alSourcei(store.getSource(0), 4105, 0);
  }
  


  public int playAsSoundEffect(float pitch, float gain, boolean loop, float x, float y, float z)
  {
    return playAsMusic(pitch, gain, loop);
  }
  


  public int playAsSoundEffect(float pitch, float gain, boolean loop)
  {
    return playAsMusic(pitch, gain, loop);
  }
  


  public void stop()
  {
    SoundStore.get().setStream(null);
  }
  


  public boolean setPosition(float position)
  {
    return player.setPosition(position);
  }
  


  public float getPosition()
  {
    return player.getPosition();
  }
}

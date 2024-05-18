package org.newdawn.slick.openal;

import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.util.Log;




















public class DeferredSound
  extends AudioImpl
  implements DeferredResource
{
  public static final int OGG = 1;
  public static final int WAV = 2;
  public static final int MOD = 3;
  public static final int AIF = 4;
  private int type;
  private String ref;
  private Audio target;
  private InputStream in;
  
  public DeferredSound(String ref, InputStream in, int type)
  {
    this.ref = ref;
    this.type = type;
    

    if (ref.equals(in.toString())) {
      this.in = in;
    }
    
    LoadingList.get().add(this);
  }
  


  private void checkTarget()
  {
    if (target == null) {
      throw new RuntimeException("Attempt to use deferred sound before loading");
    }
  }
  

  public void load()
    throws IOException
  {
    boolean before = SoundStore.get().isDeferredLoading();
    SoundStore.get().setDeferredLoading(false);
    if (in != null)
      switch (type) {
      case 1: 
        target = SoundStore.get().getOgg(in);
        break;
      case 2: 
        target = SoundStore.get().getWAV(in);
        break;
      case 3: 
        target = SoundStore.get().getMOD(in);
        break;
      case 4: 
        target = SoundStore.get().getAIF(in);
        break;
      default: 
        Log.error("Unrecognised sound type: " + type);
        

        break; } else {
      switch (type) {
      case 1: 
        target = SoundStore.get().getOgg(ref);
        break;
      case 2: 
        target = SoundStore.get().getWAV(ref);
        break;
      case 3: 
        target = SoundStore.get().getMOD(ref);
        break;
      case 4: 
        target = SoundStore.get().getAIF(ref);
        break;
      default: 
        Log.error("Unrecognised sound type: " + type);
      }
      
    }
    SoundStore.get().setDeferredLoading(before);
  }
  


  public boolean isPlaying()
  {
    checkTarget();
    
    return target.isPlaying();
  }
  


  public int playAsMusic(float pitch, float gain, boolean loop)
  {
    checkTarget();
    return target.playAsMusic(pitch, gain, loop);
  }
  


  public int playAsSoundEffect(float pitch, float gain, boolean loop)
  {
    checkTarget();
    return target.playAsSoundEffect(pitch, gain, loop);
  }
  









  public int playAsSoundEffect(float pitch, float gain, boolean loop, float x, float y, float z)
  {
    checkTarget();
    return target.playAsSoundEffect(pitch, gain, loop, x, y, z);
  }
  


  public void stop()
  {
    checkTarget();
    target.stop();
  }
  


  public String getDescription()
  {
    return ref;
  }
}

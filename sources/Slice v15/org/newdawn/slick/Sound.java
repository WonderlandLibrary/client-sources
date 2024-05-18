package org.newdawn.slick;

import java.io.InputStream;
import java.net.URL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.Log;













public class Sound
{
  private Audio sound;
  
  public Sound(InputStream in, String ref)
    throws SlickException
  {
    SoundStore.get().init();
    try
    {
      if (ref.toLowerCase().endsWith(".ogg")) {
        sound = SoundStore.get().getOgg(in);
      } else if (ref.toLowerCase().endsWith(".wav")) {
        sound = SoundStore.get().getWAV(in);
      } else if (ref.toLowerCase().endsWith(".aif")) {
        sound = SoundStore.get().getAIF(in);
      } else if ((ref.toLowerCase().endsWith(".xm")) || (ref.toLowerCase().endsWith(".mod"))) {
        sound = SoundStore.get().getMOD(in);
      } else {
        throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
      }
    } catch (Exception e) {
      Log.error(e);
      throw new SlickException("Failed to load sound: " + ref);
    }
  }
  




  public Sound(URL url)
    throws SlickException
  {
    SoundStore.get().init();
    String ref = url.getFile();
    try
    {
      if (ref.toLowerCase().endsWith(".ogg")) {
        sound = SoundStore.get().getOgg(url.openStream());
      } else if (ref.toLowerCase().endsWith(".wav")) {
        sound = SoundStore.get().getWAV(url.openStream());
      } else if (ref.toLowerCase().endsWith(".aif")) {
        sound = SoundStore.get().getAIF(url.openStream());
      } else if ((ref.toLowerCase().endsWith(".xm")) || (ref.toLowerCase().endsWith(".mod"))) {
        sound = SoundStore.get().getMOD(url.openStream());
      } else {
        throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
      }
    } catch (Exception e) {
      Log.error(e);
      throw new SlickException("Failed to load sound: " + ref);
    }
  }
  




  public Sound(String ref)
    throws SlickException
  {
    SoundStore.get().init();
    try
    {
      if (ref.toLowerCase().endsWith(".ogg")) {
        sound = SoundStore.get().getOgg(ref);
      } else if (ref.toLowerCase().endsWith(".wav")) {
        sound = SoundStore.get().getWAV(ref);
      } else if (ref.toLowerCase().endsWith(".aif")) {
        sound = SoundStore.get().getAIF(ref);
      } else if ((ref.toLowerCase().endsWith(".xm")) || (ref.toLowerCase().endsWith(".mod"))) {
        sound = SoundStore.get().getMOD(ref);
      } else {
        throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
      }
    } catch (Exception e) {
      Log.error(e);
      throw new SlickException("Failed to load sound: " + ref);
    }
  }
  


  public void play()
  {
    play(1.0F, 1.0F);
  }
  





  public void play(float pitch, float volume)
  {
    sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), false);
  }
  






  public void playAt(float x, float y, float z)
  {
    playAt(1.0F, 1.0F, x, y, z);
  }
  








  public void playAt(float pitch, float volume, float x, float y, float z)
  {
    sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), false, x, y, z);
  }
  

  public void loop()
  {
    loop(1.0F, 1.0F);
  }
  





  public void loop(float pitch, float volume)
  {
    sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), true);
  }
  




  public boolean playing()
  {
    return sound.isPlaying();
  }
  


  public void stop()
  {
    sound.stop();
  }
}

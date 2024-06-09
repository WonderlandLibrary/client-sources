package org.newdawn.slick;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioImpl;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.Log;













public class Music
{
  private static Music currentMusic;
  private Audio sound;
  private boolean playing;
  
  public static void poll(int delta)
  {
    if (currentMusic != null) {
      SoundStore.get().poll(delta);
      if (!SoundStore.get().isMusicPlaying()) {
        if (!currentMusicpositioning) {
          Music oldMusic = currentMusic;
          currentMusic = null;
          oldMusic.fireMusicEnded();
        }
      } else {
        currentMusic.update(delta);
      }
    }
  }
  





  private ArrayList listeners = new ArrayList();
  
  private float volume = 1.0F;
  
  private float fadeStartGain;
  
  private float fadeEndGain;
  
  private int fadeTime;
  
  private int fadeDuration;
  
  private boolean stopAfterFade;
  
  private boolean positioning;
  
  private float requiredPosition = -1.0F;
  




  public Music(String ref)
    throws SlickException
  {
    this(ref, false);
  }
  




  public Music(URL ref)
    throws SlickException
  {
    this(ref, false);
  }
  




  public Music(InputStream in, String ref)
    throws SlickException
  {
    SoundStore.get().init();
    try
    {
      if (ref.toLowerCase().endsWith(".ogg")) {
        sound = SoundStore.get().getOgg(in);
      } else if (ref.toLowerCase().endsWith(".wav")) {
        sound = SoundStore.get().getWAV(in);
      } else if ((ref.toLowerCase().endsWith(".xm")) || (ref.toLowerCase().endsWith(".mod"))) {
        sound = SoundStore.get().getMOD(in);
      } else if ((ref.toLowerCase().endsWith(".aif")) || (ref.toLowerCase().endsWith(".aiff"))) {
        sound = SoundStore.get().getAIF(in);
      } else {
        throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
      }
    } catch (Exception e) {
      Log.error(e);
      throw new SlickException("Failed to load music: " + ref);
    }
  }
  





  public Music(URL url, boolean streamingHint)
    throws SlickException
  {
    SoundStore.get().init();
    String ref = url.getFile();
    try
    {
      if (ref.toLowerCase().endsWith(".ogg")) {
        if (streamingHint) {
          sound = SoundStore.get().getOggStream(url);
        } else {
          sound = SoundStore.get().getOgg(url.openStream());
        }
      } else if (ref.toLowerCase().endsWith(".wav")) {
        sound = SoundStore.get().getWAV(url.openStream());
      } else if ((ref.toLowerCase().endsWith(".xm")) || (ref.toLowerCase().endsWith(".mod"))) {
        sound = SoundStore.get().getMOD(url.openStream());
      } else if ((ref.toLowerCase().endsWith(".aif")) || (ref.toLowerCase().endsWith(".aiff"))) {
        sound = SoundStore.get().getAIF(url.openStream());
      } else {
        throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
      }
    } catch (Exception e) {
      Log.error(e);
      throw new SlickException("Failed to load sound: " + url);
    }
  }
  





  public Music(String ref, boolean streamingHint)
    throws SlickException
  {
    SoundStore.get().init();
    try
    {
      if (ref.toLowerCase().endsWith(".ogg")) {
        if (streamingHint) {
          sound = SoundStore.get().getOggStream(ref);
        } else {
          sound = SoundStore.get().getOgg(ref);
        }
      } else if (ref.toLowerCase().endsWith(".wav")) {
        sound = SoundStore.get().getWAV(ref);
      } else if ((ref.toLowerCase().endsWith(".xm")) || (ref.toLowerCase().endsWith(".mod"))) {
        sound = SoundStore.get().getMOD(ref);
      } else if ((ref.toLowerCase().endsWith(".aif")) || (ref.toLowerCase().endsWith(".aiff"))) {
        sound = SoundStore.get().getAIF(ref);
      } else {
        throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
      }
    } catch (Exception e) {
      Log.error(e);
      throw new SlickException("Failed to load sound: " + ref);
    }
  }
  




  public void addListener(MusicListener listener)
  {
    listeners.add(listener);
  }
  




  public void removeListener(MusicListener listener)
  {
    listeners.remove(listener);
  }
  


  private void fireMusicEnded()
  {
    playing = false;
    for (int i = 0; i < listeners.size(); i++) {
      ((MusicListener)listeners.get(i)).musicEnded(this);
    }
  }
  




  private void fireMusicSwapped(Music newMusic)
  {
    playing = false;
    for (int i = 0; i < listeners.size(); i++) {
      ((MusicListener)listeners.get(i)).musicSwapped(this, newMusic);
    }
  }
  

  public void loop()
  {
    loop(1.0F, 1.0F);
  }
  


  public void play()
  {
    play(1.0F, 1.0F);
  }
  





  public void play(float pitch, float volume)
  {
    startMusic(pitch, volume, false);
  }
  





  public void loop(float pitch, float volume)
  {
    startMusic(pitch, volume, true);
  }
  





  private void startMusic(float pitch, float volume, boolean loop)
  {
    if (currentMusic != null) {
      currentMusic.stop();
      currentMusic.fireMusicSwapped(this);
    }
    
    currentMusic = this;
    if (volume < 0.0F)
      volume = 0.0F;
    if (volume > 1.0F) {
      volume = 1.0F;
    }
    sound.playAsMusic(pitch, volume, loop);
    playing = true;
    setVolume(volume);
    if (requiredPosition != -1.0F) {
      setPosition(requiredPosition);
    }
  }
  


  public void pause()
  {
    playing = false;
    AudioImpl.pauseMusic();
  }
  


  public void stop()
  {
    sound.stop();
  }
  


  public void resume()
  {
    playing = true;
    AudioImpl.restartMusic();
  }
  




  public boolean playing()
  {
    return (currentMusic == this) && (playing);
  }
  





  public void setVolume(float volume)
  {
    if (volume > 1.0F) {
      volume = 1.0F;
    } else if (volume < 0.0F) {
      volume = 0.0F;
    }
    
    this.volume = volume;
    
    if (currentMusic == this) {
      SoundStore.get().setCurrentMusicVolume(volume);
    }
  }
  



  public float getVolume()
  {
    return volume;
  }
  






  public void fade(int duration, float endVolume, boolean stopAfterFade)
  {
    this.stopAfterFade = stopAfterFade;
    fadeStartGain = volume;
    fadeEndGain = endVolume;
    fadeDuration = duration;
    fadeTime = duration;
  }
  





  void update(int delta)
  {
    if (!playing) {
      return;
    }
    
    if (fadeTime > 0) {
      fadeTime -= delta;
      if (fadeTime < 0) {
        fadeTime = 0;
        if (stopAfterFade) {
          stop();
          return;
        }
      }
      
      float offset = (fadeEndGain - fadeStartGain) * (1.0F - fadeTime / fadeDuration);
      setVolume(fadeStartGain + offset);
    }
  }
  






  public boolean setPosition(float position)
  {
    if (playing) {
      requiredPosition = -1.0F;
      
      positioning = true;
      playing = false;
      boolean result = sound.setPosition(position);
      playing = true;
      positioning = false;
      
      return result;
    }
    requiredPosition = position;
    return false;
  }
  





  public float getPosition()
  {
    return sound.getPosition();
  }
}

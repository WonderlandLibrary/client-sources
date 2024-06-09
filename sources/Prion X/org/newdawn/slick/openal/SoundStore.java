package org.newdawn.slick.openal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.OpenALException;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;









public class SoundStore
{
  private static SoundStore store = new SoundStore();
  

  private boolean sounds;
  
  private boolean music;
  
  private boolean soundWorks;
  
  private int sourceCount;
  
  private HashMap loaded = new HashMap();
  
  private int currentMusic = -1;
  
  private IntBuffer sources;
  
  private int nextSource;
  
  private boolean inited = false;
  

  private MODSound mod;
  
  private OpenALStreamPlayer stream;
  
  private float musicVolume = 1.0F;
  
  private float soundVolume = 1.0F;
  
  private float lastCurrentMusicVolume = 1.0F;
  

  private boolean paused;
  

  private boolean deferred;
  
  private FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0F, 0.0F, 0.0F });
  
  private FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3);
  

  private int maxSources = 64;
  



  private SoundStore() {}
  



  public void clear()
  {
    store = new SoundStore();
  }
  


  public void disable()
  {
    inited = true;
  }
  





  public void setDeferredLoading(boolean deferred)
  {
    this.deferred = deferred;
  }
  




  public boolean isDeferredLoading()
  {
    return deferred;
  }
  




  public void setMusicOn(boolean music)
  {
    if (soundWorks) {
      this.music = music;
      if (music) {
        restartLoop();
        setMusicVolume(musicVolume);
      } else {
        pauseLoop();
      }
    }
  }
  




  public boolean isMusicOn()
  {
    return music;
  }
  




  public void setMusicVolume(float volume)
  {
    if (volume < 0.0F) {
      volume = 0.0F;
    }
    if (volume > 1.0F) {
      volume = 1.0F;
    }
    
    musicVolume = volume;
    if (soundWorks) {
      AL10.alSourcef(sources.get(0), 4106, lastCurrentMusicVolume * musicVolume);
    }
  }
  




  public float getCurrentMusicVolume()
  {
    return lastCurrentMusicVolume;
  }
  




  public void setCurrentMusicVolume(float volume)
  {
    if (volume < 0.0F) {
      volume = 0.0F;
    }
    if (volume > 1.0F) {
      volume = 1.0F;
    }
    
    if (soundWorks) {
      lastCurrentMusicVolume = volume;
      AL10.alSourcef(sources.get(0), 4106, lastCurrentMusicVolume * musicVolume);
    }
  }
  




  public void setSoundVolume(float volume)
  {
    if (volume < 0.0F) {
      volume = 0.0F;
    }
    soundVolume = volume;
  }
  




  public boolean soundWorks()
  {
    return soundWorks;
  }
  




  public boolean musicOn()
  {
    return music;
  }
  




  public float getSoundVolume()
  {
    return soundVolume;
  }
  




  public float getMusicVolume()
  {
    return musicVolume;
  }
  





  public int getSource(int index)
  {
    if (!soundWorks) {
      return -1;
    }
    if (index < 0) {
      return -1;
    }
    return sources.get(index);
  }
  




  public void setSoundsOn(boolean sounds)
  {
    if (soundWorks) {
      this.sounds = sounds;
    }
  }
  




  public boolean soundsOn()
  {
    return sounds;
  }
  





  public void setMaxSources(int max)
  {
    maxSources = max;
  }
  



  public void init()
  {
    if (inited) {
      return;
    }
    Log.info("Initialising sounds..");
    inited = true;
    
    AccessController.doPrivileged(new PrivilegedAction() {
      public Object run() {
        try {
          AL.create();
          soundWorks = true;
          sounds = true;
          music = true;
          Log.info("- Sound works");
        } catch (Exception e) {
          Log.error("Sound initialisation failure.");
          Log.error(e);
          soundWorks = false;
          sounds = false;
          music = false;
        }
        
        return null;
      }
    });
    if (soundWorks) {
      sourceCount = 0;
      sources = BufferUtils.createIntBuffer(maxSources);
      break label114;
      for (;;) { IntBuffer temp = BufferUtils.createIntBuffer(1);
        try
        {
          AL10.alGenSources(temp);
          
          if (AL10.alGetError() == 0) {
            sourceCount += 1;
            sources.put(temp.get(0));
            if (sourceCount > maxSources - 1) {}
          }
          else
          {
            if (AL10.alGetError() == 0) {
              continue;
            }
          }
        }
        catch (OpenALException e) {}
      }
      




      label114:
      




      Log.info("- " + sourceCount + " OpenAL source available");
      
      if (AL10.alGetError() != 0) {
        sounds = false;
        music = false;
        soundWorks = false;
        Log.error("- AL init failed");
      } else {
        FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(
          new float[] { 0.0F, 0.0F, -1.0F, 0.0F, 1.0F, 0.0F });
        FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(
          new float[] { 0.0F, 0.0F, 0.0F });
        FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(
          new float[] { 0.0F, 0.0F, 0.0F });
        listenerPos.flip();
        listenerVel.flip();
        listenerOri.flip();
        AL10.alListener(4100, listenerPos);
        AL10.alListener(4102, listenerVel);
        AL10.alListener(4111, listenerOri);
        
        Log.info("- Sounds source generated");
      }
    }
  }
  




  void stopSource(int index)
  {
    AL10.alSourceStop(sources.get(index));
  }
  









  int playAsSound(int buffer, float pitch, float gain, boolean loop)
  {
    return playAsSoundAt(buffer, pitch, gain, loop, 0.0F, 0.0F, 0.0F);
  }
  












  int playAsSoundAt(int buffer, float pitch, float gain, boolean loop, float x, float y, float z)
  {
    gain *= soundVolume;
    if (gain == 0.0F) {
      gain = 0.001F;
    }
    if ((soundWorks) && 
      (sounds)) {
      int nextSource = findFreeSource();
      if (nextSource == -1) {
        return -1;
      }
      
      AL10.alSourceStop(sources.get(nextSource));
      
      AL10.alSourcei(sources.get(nextSource), 4105, buffer);
      AL10.alSourcef(sources.get(nextSource), 4099, pitch);
      AL10.alSourcef(sources.get(nextSource), 4106, gain);
      AL10.alSourcei(sources.get(nextSource), 4103, loop ? 1 : 0);
      
      sourcePos.clear();
      sourceVel.clear();
      sourceVel.put(new float[] { 0.0F, 0.0F, 0.0F });
      sourcePos.put(new float[] { x, y, z });
      sourcePos.flip();
      sourceVel.flip();
      AL10.alSource(sources.get(nextSource), 4100, sourcePos);
      AL10.alSource(sources.get(nextSource), 4102, sourceVel);
      
      AL10.alSourcePlay(sources.get(nextSource));
      
      return nextSource;
    }
    

    return -1;
  }
  




  boolean isPlaying(int index)
  {
    int state = AL10.alGetSourcei(sources.get(index), 4112);
    
    return state == 4114;
  }
  




  private int findFreeSource()
  {
    for (int i = 1; i < sourceCount - 1; i++) {
      int state = AL10.alGetSourcei(sources.get(i), 4112);
      
      if ((state != 4114) && (state != 4115)) {
        return i;
      }
    }
    
    return -1;
  }
  







  void playAsMusic(int buffer, float pitch, float gain, boolean loop)
  {
    paused = false;
    
    setMOD(null);
    
    if (soundWorks) {
      if (currentMusic != -1) {
        AL10.alSourceStop(sources.get(0));
      }
      
      getMusicSource();
      
      AL10.alSourcei(sources.get(0), 4105, buffer);
      AL10.alSourcef(sources.get(0), 4099, pitch);
      AL10.alSourcei(sources.get(0), 4103, loop ? 1 : 0);
      
      currentMusic = sources.get(0);
      
      if (!music) {
        pauseLoop();
      } else {
        AL10.alSourcePlay(sources.get(0));
      }
    }
  }
  




  private int getMusicSource()
  {
    return sources.get(0);
  }
  




  public void setMusicPitch(float pitch)
  {
    if (soundWorks) {
      AL10.alSourcef(sources.get(0), 4099, pitch);
    }
  }
  


  public void pauseLoop()
  {
    if ((soundWorks) && (currentMusic != -1)) {
      paused = true;
      AL10.alSourcePause(currentMusic);
    }
  }
  


  public void restartLoop()
  {
    if ((music) && (soundWorks) && (currentMusic != -1)) {
      paused = false;
      AL10.alSourcePlay(currentMusic);
    }
  }
  






  boolean isPlaying(OpenALStreamPlayer player)
  {
    return stream == player;
  }
  





  public Audio getMOD(String ref)
    throws IOException
  {
    return getMOD(ref, ResourceLoader.getResourceAsStream(ref));
  }
  





  public Audio getMOD(InputStream in)
    throws IOException
  {
    return getMOD(in.toString(), in);
  }
  






  public Audio getMOD(String ref, InputStream in)
    throws IOException
  {
    if (!soundWorks) {
      return new NullAudio();
    }
    if (!inited) {
      throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
    }
    if (deferred) {
      return new DeferredSound(ref, in, 3);
    }
    
    return new MODSound(this, in);
  }
  





  public Audio getAIF(String ref)
    throws IOException
  {
    return getAIF(ref, ResourceLoader.getResourceAsStream(ref));
  }
  






  public Audio getAIF(InputStream in)
    throws IOException
  {
    return getAIF(in.toString(), in);
  }
  






  public Audio getAIF(String ref, InputStream in)
    throws IOException
  {
    in = new BufferedInputStream(in);
    
    if (!soundWorks) {
      return new NullAudio();
    }
    if (!inited) {
      throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
    }
    if (deferred) {
      return new DeferredSound(ref, in, 4);
    }
    
    int buffer = -1;
    
    if (loaded.get(ref) != null) {
      buffer = ((Integer)loaded.get(ref)).intValue();
    } else {
      try {
        IntBuffer buf = BufferUtils.createIntBuffer(1);
        
        AiffData data = AiffData.create(in);
        AL10.alGenBuffers(buf);
        AL10.alBufferData(buf.get(0), format, data, samplerate);
        
        loaded.put(ref, new Integer(buf.get(0)));
        buffer = buf.get(0);
      } catch (Exception e) {
        Log.error(e);
        IOException x = new IOException("Failed to load: " + ref);
        x.initCause(e);
        
        throw x;
      }
    }
    
    if (buffer == -1) {
      throw new IOException("Unable to load: " + ref);
    }
    
    return new AudioImpl(this, buffer);
  }
  







  public Audio getWAV(String ref)
    throws IOException
  {
    return getWAV(ref, ResourceLoader.getResourceAsStream(ref));
  }
  





  public Audio getWAV(InputStream in)
    throws IOException
  {
    return getWAV(in.toString(), in);
  }
  






  public Audio getWAV(String ref, InputStream in)
    throws IOException
  {
    if (!soundWorks) {
      return new NullAudio();
    }
    if (!inited) {
      throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
    }
    if (deferred) {
      return new DeferredSound(ref, in, 2);
    }
    
    int buffer = -1;
    
    if (loaded.get(ref) != null) {
      buffer = ((Integer)loaded.get(ref)).intValue();
    } else {
      try {
        IntBuffer buf = BufferUtils.createIntBuffer(1);
        
        WaveData data = WaveData.create(in);
        AL10.alGenBuffers(buf);
        AL10.alBufferData(buf.get(0), format, data, samplerate);
        
        loaded.put(ref, new Integer(buf.get(0)));
        buffer = buf.get(0);
      } catch (Exception e) {
        Log.error(e);
        IOException x = new IOException("Failed to load: " + ref);
        x.initCause(e);
        
        throw x;
      }
    }
    
    if (buffer == -1) {
      throw new IOException("Unable to load: " + ref);
    }
    
    return new AudioImpl(this, buffer);
  }
  





  public Audio getOggStream(String ref)
    throws IOException
  {
    if (!soundWorks) {
      return new NullAudio();
    }
    
    setMOD(null);
    setStream(null);
    
    if (currentMusic != -1) {
      AL10.alSourceStop(sources.get(0));
    }
    
    getMusicSource();
    currentMusic = sources.get(0);
    
    return new StreamSound(new OpenALStreamPlayer(currentMusic, ref));
  }
  





  public Audio getOggStream(URL ref)
    throws IOException
  {
    if (!soundWorks) {
      return new NullAudio();
    }
    
    setMOD(null);
    setStream(null);
    
    if (currentMusic != -1) {
      AL10.alSourceStop(sources.get(0));
    }
    
    getMusicSource();
    currentMusic = sources.get(0);
    
    return new StreamSound(new OpenALStreamPlayer(currentMusic, ref));
  }
  





  public Audio getOgg(String ref)
    throws IOException
  {
    return getOgg(ref, ResourceLoader.getResourceAsStream(ref));
  }
  





  public Audio getOgg(InputStream in)
    throws IOException
  {
    return getOgg(in.toString(), in);
  }
  






  public Audio getOgg(String ref, InputStream in)
    throws IOException
  {
    if (!soundWorks) {
      return new NullAudio();
    }
    if (!inited) {
      throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
    }
    if (deferred) {
      return new DeferredSound(ref, in, 1);
    }
    
    int buffer = -1;
    
    if (loaded.get(ref) != null) {
      buffer = ((Integer)loaded.get(ref)).intValue();
    } else {
      try {
        IntBuffer buf = BufferUtils.createIntBuffer(1);
        
        OggDecoder decoder = new OggDecoder();
        OggData ogg = decoder.getData(in);
        
        AL10.alGenBuffers(buf);
        AL10.alBufferData(buf.get(0), channels > 1 ? 4355 : 4353, data, rate);
        
        loaded.put(ref, new Integer(buf.get(0)));
        
        buffer = buf.get(0);
      } catch (Exception e) {
        Log.error(e);
        Sys.alert("Error", "Failed to load: " + ref + " - " + e.getMessage());
        throw new IOException("Unable to load: " + ref);
      }
    }
    
    if (buffer == -1) {
      throw new IOException("Unable to load: " + ref);
    }
    
    return new AudioImpl(this, buffer);
  }
  




  void setMOD(MODSound sound)
  {
    if (!soundWorks) {
      return;
    }
    
    currentMusic = sources.get(0);
    stopSource(0);
    
    mod = sound;
    if (sound != null) {
      stream = null;
    }
    paused = false;
  }
  




  void setStream(OpenALStreamPlayer stream)
  {
    if (!soundWorks) {
      return;
    }
    
    currentMusic = sources.get(0);
    this.stream = stream;
    if (stream != null) {
      mod = null;
    }
    paused = false;
  }
  




  public void poll(int delta)
  {
    if (!soundWorks) {
      return;
    }
    if (paused) {
      return;
    }
    
    if (music) {
      if (mod != null) {
        try {
          mod.poll();
        } catch (OpenALException e) {
          Log.error("Error with OpenGL MOD Player on this this platform");
          Log.error(e);
          mod = null;
        }
      }
      if (stream != null) {
        try {
          stream.update();
        } catch (OpenALException e) {
          Log.error("Error with OpenGL Streaming Player on this this platform");
          Log.error(e);
          mod = null;
        }
      }
    }
  }
  





  public boolean isMusicPlaying()
  {
    if (!soundWorks) {
      return false;
    }
    
    int state = AL10.alGetSourcei(sources.get(0), 4112);
    return (state == 4114) || (state == 4115);
  }
  




  public static SoundStore get()
  {
    return store;
  }
  






  public void stopSoundEffect(int id)
  {
    AL10.alSourceStop(id);
  }
  





  public int getSourceCount()
  {
    return sourceCount;
  }
}

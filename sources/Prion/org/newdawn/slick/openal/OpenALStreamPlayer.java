package org.newdawn.slick.openal;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.OpenALException;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;














public class OpenALStreamPlayer
{
  public static final int BUFFER_COUNT = 3;
  private static final int sectionSize = 81920;
  private byte[] buffer = new byte[81920];
  
  private IntBuffer bufferNames;
  
  private ByteBuffer bufferData = BufferUtils.createByteBuffer(81920);
  
  private IntBuffer unqueued = BufferUtils.createIntBuffer(1);
  
  private int source;
  
  private int remainingBufferCount;
  
  private boolean loop;
  
  private boolean done = true;
  

  private AudioInputStream audio;
  

  private String ref;
  

  private URL url;
  

  private float pitch;
  
  private float positionOffset;
  

  public OpenALStreamPlayer(int source, String ref)
  {
    this.source = source;
    this.ref = ref;
    
    bufferNames = BufferUtils.createIntBuffer(3);
    AL10.alGenBuffers(bufferNames);
  }
  





  public OpenALStreamPlayer(int source, URL url)
  {
    this.source = source;
    this.url = url;
    
    bufferNames = BufferUtils.createIntBuffer(3);
    AL10.alGenBuffers(bufferNames);
  }
  



  private void initStreams()
    throws IOException
  {
    if (this.audio != null) {
      this.audio.close();
    }
    
    OggInputStream audio;
    OggInputStream audio;
    if (url != null) {
      audio = new OggInputStream(url.openStream());
    } else {
      audio = new OggInputStream(ResourceLoader.getResourceAsStream(ref));
    }
    
    this.audio = audio;
    positionOffset = 0.0F;
  }
  




  public String getSource()
  {
    return url == null ? ref : url.toString();
  }
  


  private void removeBuffers()
  {
    IntBuffer buffer = BufferUtils.createIntBuffer(1);
    int queued = AL10.alGetSourcei(source, 4117);
    
    while (queued > 0)
    {
      AL10.alSourceUnqueueBuffers(source, buffer);
      queued--;
    }
  }
  




  public void play(boolean loop)
    throws IOException
  {
    this.loop = loop;
    initStreams();
    
    done = false;
    
    AL10.alSourceStop(source);
    removeBuffers();
    
    startPlayback();
  }
  




  public void setup(float pitch)
  {
    this.pitch = pitch;
  }
  





  public boolean done()
  {
    return done;
  }
  





  public void update()
  {
    if (done) {
      return;
    }
    
    float sampleRate = audio.getRate();
    float sampleSize;
    float sampleSize; if (audio.getChannels() > 1) {
      sampleSize = 4.0F;
    } else {
      sampleSize = 2.0F;
    }
    
    int processed = AL10.alGetSourcei(source, 4118);
    while (processed > 0) {
      unqueued.clear();
      AL10.alSourceUnqueueBuffers(source, unqueued);
      
      int bufferIndex = unqueued.get(0);
      
      float bufferLength = AL10.alGetBufferi(bufferIndex, 8196) / sampleSize / sampleRate;
      positionOffset += bufferLength;
      
      if (stream(bufferIndex)) {
        AL10.alSourceQueueBuffers(source, unqueued);
      } else {
        remainingBufferCount -= 1;
        if (remainingBufferCount == 0) {
          done = true;
        }
      }
      processed--;
    }
    
    int state = AL10.alGetSourcei(source, 4112);
    
    if (state != 4114) {
      AL10.alSourcePlay(source);
    }
  }
  




  public boolean stream(int bufferId)
  {
    try
    {
      int count = audio.read(buffer);
      
      if (count != -1) {
        bufferData.clear();
        bufferData.put(buffer, 0, count);
        bufferData.flip();
        
        int format = audio.getChannels() > 1 ? 4355 : 4353;
        try {
          AL10.alBufferData(bufferId, format, bufferData, audio.getRate());
        } catch (OpenALException e) {
          Log.error("Failed to loop buffer: " + bufferId + " " + format + " " + count + " " + audio.getRate(), e);
          return false;
        }
      }
      else if (loop) {
        initStreams();
        stream(bufferId);
      } else {
        done = true;
        return false;
      }
      

      return true;
    } catch (IOException e) {
      Log.error(e); }
    return false;
  }
  





  public boolean setPosition(float position)
  {
    try
    {
      if (getPosition() > position) {
        initStreams();
      }
      
      float sampleRate = audio.getRate();
      float sampleSize;
      float sampleSize; if (audio.getChannels() > 1) {
        sampleSize = 4.0F;
      } else {
        sampleSize = 2.0F;
      }
      
      while (positionOffset < position) {
        int count = audio.read(buffer);
        if (count != -1) {
          float bufferLength = count / sampleSize / sampleRate;
          positionOffset += bufferLength;
        } else {
          if (loop) {
            initStreams();
          } else {
            done = true;
          }
          return false;
        }
      }
      
      startPlayback();
      
      return true;
    } catch (IOException e) {
      Log.error(e); }
    return false;
  }
  



  private void startPlayback()
  {
    AL10.alSourcei(source, 4103, 0);
    AL10.alSourcef(source, 4099, pitch);
    
    remainingBufferCount = 3;
    
    for (int i = 0; i < 3; i++) {
      stream(bufferNames.get(i));
    }
    
    AL10.alSourceQueueBuffers(source, bufferNames);
    AL10.alSourcePlay(source);
  }
  




  public float getPosition()
  {
    return positionOffset + AL10.alGetSourcef(source, 4132);
  }
}

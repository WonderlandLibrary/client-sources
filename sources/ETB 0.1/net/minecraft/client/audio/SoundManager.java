package net.minecraft.client.audio;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import io.netty.util.internal.ThreadLocalRandom;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.SoundSystemLogger;
import paulscode.sound.Source;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager
{
  private static final Marker LOG_MARKER = org.apache.logging.log4j.MarkerManager.getMarker("SOUNDS");
  private static final Logger logger = LogManager.getLogger();
  

  private final SoundHandler sndHandler;
  

  private final GameSettings options;
  

  private SoundSystemStarterThread sndSystem;
  

  private boolean loaded;
  

  private int playTime = 0;
  



  private final Map playingSounds = HashBiMap.create();
  


  private final Map invPlayingSounds;
  


  private Map playingSoundPoolEntries;
  

  private final Multimap categorySounds;
  

  private final List tickableSounds;
  

  private final Map delayedSounds;
  

  private final Map playingSoundsStopTime;
  

  private static final String __OBFID = "CL_00001141";
  


  public SoundManager(SoundHandler p_i45119_1_, GameSettings p_i45119_2_)
  {
    invPlayingSounds = ((BiMap)playingSounds).inverse();
    playingSoundPoolEntries = Maps.newHashMap();
    categorySounds = com.google.common.collect.HashMultimap.create();
    tickableSounds = com.google.common.collect.Lists.newArrayList();
    delayedSounds = Maps.newHashMap();
    playingSoundsStopTime = Maps.newHashMap();
    sndHandler = p_i45119_1_;
    options = p_i45119_2_;
    
    try
    {
      SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
      SoundSystemConfig.setCodec("ogg", paulscode.sound.codecs.CodecJOrbis.class);
    }
    catch (SoundSystemException var4)
    {
      logger.error(LOG_MARKER, "Error linking with the LibraryJavaSound plug-in", var4);
    }
  }
  
  public void reloadSoundSystem()
  {
    unloadSoundSystem();
    loadSoundSystem();
  }
  



  private synchronized void loadSoundSystem()
  {
    if (!loaded)
    {


















      try
      {


















        new Thread(new Runnable()
        {
          private static final String __OBFID = "CL_00001142";
          
          public void run()
          {
            SoundSystemConfig.setLogger(new SoundSystemLogger()
            {
              private static final String __OBFID = "CL_00002378";
              
              public void message(String p_message_1_, int p_message_2_) {
                if (!p_message_1_.isEmpty())
                {
                  SoundManager.logger.info(p_message_1_);
                }
              }
              
              public void importantMessage(String p_importantMessage_1_, int p_importantMessage_2_) {
                if (!p_importantMessage_1_.isEmpty())
                {
                  SoundManager.logger.warn(p_importantMessage_1_);
                }
              }
              
              public void errorMessage(String p_errorMessage_1_, String p_errorMessage_2_, int p_errorMessage_3_) {
                if (!p_errorMessage_2_.isEmpty())
                {
                  SoundManager.logger.error("Error in class '" + p_errorMessage_1_ + "'");
                  SoundManager.logger.error(p_errorMessage_2_);
                }
                
              }
            }); SoundManager tmp23_20 = SoundManager.this;tmp23_20.getClass();sndSystem = new SoundManager.SoundSystemStarterThread(tmp23_20, null);
            loaded = true;
            sndSystem.setMasterVolume(options.getSoundLevel(SoundCategory.MASTER));
            SoundManager.logger.info(SoundManager.LOG_MARKER, "Sound engine started");
          }
        }, "Sound Library Loader").start();
      }
      catch (RuntimeException var2)
      {
        logger.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", var2);
        options.setSoundLevel(SoundCategory.MASTER, 0.0F);
        options.saveOptions();
      }
    }
  }
  



  private float getSoundCategoryVolume(SoundCategory p_148595_1_)
  {
    return (p_148595_1_ != null) && (p_148595_1_ != SoundCategory.MASTER) ? options.getSoundLevel(p_148595_1_) : 1.0F;
  }
  



  public void setSoundCategoryVolume(SoundCategory p_148601_1_, float p_148601_2_)
  {
    if (loaded)
    {
      if (p_148601_1_ == SoundCategory.MASTER)
      {
        sndSystem.setMasterVolume(p_148601_2_);
      }
      else
      {
        Iterator var3 = categorySounds.get(p_148601_1_).iterator();
        
        while (var3.hasNext())
        {
          String var4 = (String)var3.next();
          ISound var5 = (ISound)playingSounds.get(var4);
          float var6 = getNormalizedVolume(var5, (SoundPoolEntry)playingSoundPoolEntries.get(var5), p_148601_1_);
          
          if (var6 <= 0.0F)
          {
            stopSound(var5);
          }
          else
          {
            sndSystem.setVolume(var4, var6);
          }
        }
      }
    }
  }
  



  public void unloadSoundSystem()
  {
    if (loaded)
    {
      stopAllSounds();
      sndSystem.cleanup();
      loaded = false;
    }
  }
  



  public void stopAllSounds()
  {
    if (loaded)
    {
      Iterator var1 = playingSounds.keySet().iterator();
      
      while (var1.hasNext())
      {
        String var2 = (String)var1.next();
        sndSystem.stop(var2);
      }
      
      playingSounds.clear();
      delayedSounds.clear();
      tickableSounds.clear();
      categorySounds.clear();
      playingSoundPoolEntries.clear();
      playingSoundsStopTime.clear();
    }
  }
  
  public void updateAllSounds()
  {
    playTime += 1;
    Iterator var1 = tickableSounds.iterator();
    

    while (var1.hasNext())
    {
      ITickableSound var2 = (ITickableSound)var1.next();
      var2.update();
      
      if (var2.isDonePlaying())
      {
        stopSound(var2);
      }
      else
      {
        String var3 = (String)invPlayingSounds.get(var2);
        sndSystem.setVolume(var3, getNormalizedVolume(var2, (SoundPoolEntry)playingSoundPoolEntries.get(var2), sndHandler.getSound(var2.getSoundLocation()).getSoundCategory()));
        sndSystem.setPitch(var3, getNormalizedPitch(var2, (SoundPoolEntry)playingSoundPoolEntries.get(var2)));
        sndSystem.setPosition(var3, var2.getXPosF(), var2.getYPosF(), var2.getZPosF());
      }
    }
    
    var1 = playingSounds.entrySet().iterator();
    

    while (var1.hasNext())
    {
      Map.Entry var9 = (Map.Entry)var1.next();
      String var3 = (String)var9.getKey();
      ISound var4 = (ISound)var9.getValue();
      
      if (!sndSystem.playing(var3))
      {
        int var5 = ((Integer)playingSoundsStopTime.get(var3)).intValue();
        
        if (var5 <= playTime)
        {
          int var6 = var4.getRepeatDelay();
          
          if ((var4.canRepeat()) && (var6 > 0))
          {
            delayedSounds.put(var4, Integer.valueOf(playTime + var6));
          }
          
          var1.remove();
          logger.debug(LOG_MARKER, "Removed channel {} because it's not playing anymore", new Object[] { var3 });
          sndSystem.removeSource(var3);
          playingSoundsStopTime.remove(var3);
          playingSoundPoolEntries.remove(var4);
          
          try
          {
            categorySounds.remove(sndHandler.getSound(var4.getSoundLocation()).getSoundCategory(), var3);
          }
          catch (RuntimeException localRuntimeException) {}
          



          if ((var4 instanceof ITickableSound))
          {
            tickableSounds.remove(var4);
          }
        }
      }
    }
    
    Iterator var10 = delayedSounds.entrySet().iterator();
    
    while (var10.hasNext())
    {
      Map.Entry var11 = (Map.Entry)var10.next();
      
      if (playTime >= ((Integer)var11.getValue()).intValue())
      {
        ISound var4 = (ISound)var11.getKey();
        
        if ((var4 instanceof ITickableSound))
        {
          ((ITickableSound)var4).update();
        }
        
        playSound(var4);
        var10.remove();
      }
    }
  }
  



  public boolean isSoundPlaying(ISound p_148597_1_)
  {
    if (!loaded)
    {
      return false;
    }
    

    String var2 = (String)invPlayingSounds.get(p_148597_1_);
    return var2 != null;
  }
  

  public void stopSound(ISound p_148602_1_)
  {
    if (loaded)
    {
      String var2 = (String)invPlayingSounds.get(p_148602_1_);
      
      if (var2 != null)
      {
        sndSystem.stop(var2);
      }
    }
  }
  
  public void playSound(ISound p_148611_1_)
  {
    if (loaded)
    {
      if (sndSystem.getMasterVolume() <= 0.0F)
      {
        logger.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", new Object[] { p_148611_1_.getSoundLocation() });
      }
      else
      {
        SoundEventAccessorComposite var2 = sndHandler.getSound(p_148611_1_.getSoundLocation());
        
        if (var2 == null)
        {
          logger.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}", new Object[] { p_148611_1_.getSoundLocation() });
        }
        else
        {
          SoundPoolEntry var3 = (SoundPoolEntry)var2.cloneEntry();
          
          if (var3 == SoundHandler.missing_sound)
          {
            logger.warn(LOG_MARKER, "Unable to play empty soundEvent: {}", new Object[] { var2.getSoundEventLocation() });
          }
          else
          {
            float var4 = p_148611_1_.getVolume();
            float var5 = 16.0F;
            
            if (var4 > 1.0F)
            {
              var5 *= var4;
            }
            
            SoundCategory var6 = var2.getSoundCategory();
            float var7 = getNormalizedVolume(p_148611_1_, var3, var6);
            double var8 = getNormalizedPitch(p_148611_1_, var3);
            ResourceLocation var10 = var3.getSoundPoolEntryLocation();
            
            if (var7 == 0.0F)
            {
              logger.debug(LOG_MARKER, "Skipped playing sound {}, volume was zero.", new Object[] { var10 });
            }
            else
            {
              boolean var11 = (p_148611_1_.canRepeat()) && (p_148611_1_.getRepeatDelay() == 0);
              String var12 = MathHelper.func_180182_a(ThreadLocalRandom.current()).toString();
              
              if (var3.isStreamingSound())
              {
                sndSystem.newStreamingSource(false, var12, getURLForSoundResource(var10), var10.toString(), var11, p_148611_1_.getXPosF(), p_148611_1_.getYPosF(), p_148611_1_.getZPosF(), p_148611_1_.getAttenuationType().getTypeInt(), var5);
              }
              else
              {
                sndSystem.newSource(false, var12, getURLForSoundResource(var10), var10.toString(), var11, p_148611_1_.getXPosF(), p_148611_1_.getYPosF(), p_148611_1_.getZPosF(), p_148611_1_.getAttenuationType().getTypeInt(), var5);
              }
              
              logger.debug(LOG_MARKER, "Playing sound {} for event {} as channel {}", new Object[] { var3.getSoundPoolEntryLocation(), var2.getSoundEventLocation(), var12 });
              sndSystem.setPitch(var12, (float)var8);
              sndSystem.setVolume(var12, var7);
              sndSystem.play(var12);
              playingSoundsStopTime.put(var12, Integer.valueOf(playTime + 20));
              playingSounds.put(var12, p_148611_1_);
              playingSoundPoolEntries.put(p_148611_1_, var3);
              
              if (var6 != SoundCategory.MASTER)
              {
                categorySounds.put(var6, var12);
              }
              
              if ((p_148611_1_ instanceof ITickableSound))
              {
                tickableSounds.add((ITickableSound)p_148611_1_);
              }
            }
          }
        }
      }
    }
  }
  



  private float getNormalizedPitch(ISound p_148606_1_, SoundPoolEntry p_148606_2_)
  {
    return (float)MathHelper.clamp_double(p_148606_1_.getPitch() * p_148606_2_.getPitch(), 0.5D, 2.0D);
  }
  



  private float getNormalizedVolume(ISound p_148594_1_, SoundPoolEntry p_148594_2_, SoundCategory p_148594_3_)
  {
    return (float)MathHelper.clamp_double(p_148594_1_.getVolume() * p_148594_2_.getVolume(), 0.0D, 1.0D) * getSoundCategoryVolume(p_148594_3_);
  }
  



  public void pauseAllSounds()
  {
    Iterator var1 = playingSounds.keySet().iterator();
    
    while (var1.hasNext())
    {
      String var2 = (String)var1.next();
      logger.debug(LOG_MARKER, "Pausing channel {}", new Object[] { var2 });
      sndSystem.pause(var2);
    }
  }
  



  public void resumeAllSounds()
  {
    Iterator var1 = playingSounds.keySet().iterator();
    
    while (var1.hasNext())
    {
      String var2 = (String)var1.next();
      logger.debug(LOG_MARKER, "Resuming channel {}", new Object[] { var2 });
      sndSystem.play(var2);
    }
  }
  



  public void playDelayedSound(ISound p_148599_1_, int p_148599_2_)
  {
    delayedSounds.put(p_148599_1_, Integer.valueOf(playTime + p_148599_2_));
  }
  
  private static URL getURLForSoundResource(ResourceLocation p_148612_0_)
  {
    String var1 = String.format("%s:%s:%s", new Object[] { "mcsounddomain", p_148612_0_.getResourceDomain(), p_148612_0_.getResourcePath() });
    URLStreamHandler var2 = new URLStreamHandler()
    {
      private static final String __OBFID = "CL_00001143";
      
      protected URLConnection openConnection(URL p_openConnection_1_) {
        new URLConnection(p_openConnection_1_) {
          private static final String __OBFID = "CL_00001144";
          
          public void connect() {}
          
          public InputStream getInputStream() throws IOException {
            return Minecraft.getMinecraft().getResourceManager().getResource(val$p_148612_0_).getInputStream();
          }
        };
      }
    };
    
    try
    {
      return new URL(null, var1, var2);
    }
    catch (MalformedURLException var4)
    {
      throw new Error("TODO: Sanely handle url exception! :D");
    }
  }
  



  public void setListener(EntityPlayer p_148615_1_, float p_148615_2_)
  {
    if ((loaded) && (p_148615_1_ != null))
    {
      float var3 = prevRotationPitch + (rotationPitch - prevRotationPitch) * p_148615_2_;
      float var4 = prevRotationYaw + (rotationYaw - prevRotationYaw) * p_148615_2_;
      double var5 = prevPosX + (posX - prevPosX) * p_148615_2_;
      double var7 = prevPosY + (posY - prevPosY) * p_148615_2_ + p_148615_1_.getEyeHeight();
      double var9 = prevPosZ + (posZ - prevPosZ) * p_148615_2_;
      float var11 = MathHelper.cos((var4 + 90.0F) * 0.017453292F);
      float var12 = MathHelper.sin((var4 + 90.0F) * 0.017453292F);
      float var13 = MathHelper.cos(-var3 * 0.017453292F);
      float var14 = MathHelper.sin(-var3 * 0.017453292F);
      float var15 = MathHelper.cos((-var3 + 90.0F) * 0.017453292F);
      float var16 = MathHelper.sin((-var3 + 90.0F) * 0.017453292F);
      float var17 = var11 * var13;
      float var19 = var12 * var13;
      float var20 = var11 * var15;
      float var22 = var12 * var15;
      sndSystem.setListenerPosition((float)var5, (float)var7, (float)var9);
      sndSystem.setListenerOrientation(var17, var14, var19, var20, var16, var22);
    }
  }
  
  class SoundSystemStarterThread extends SoundSystem
  {
    private static final String __OBFID = "CL_00001145";
    
    private SoundSystemStarterThread() {}
    
    public boolean playing(String p_playing_1_)
    {
      Object var2 = SoundSystemConfig.THREAD_SYNC;
      
      synchronized (SoundSystemConfig.THREAD_SYNC)
      {
        if (soundLibrary == null)
        {
          return false;
        }
        

        Source var3 = (Source)soundLibrary.getSources().get(p_playing_1_);
        return var3 != null;
      }
    }
    

    SoundSystemStarterThread(Object p_i45118_2_)
    {
      this();
    }
  }
}

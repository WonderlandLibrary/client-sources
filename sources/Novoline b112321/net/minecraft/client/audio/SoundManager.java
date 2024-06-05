package net.minecraft.client.audio;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.GR;
import net.rj;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager$SoundSystemStarterThread;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {
   private static final Logger logger = LogManager.getLogger();
   private static final Marker LOG_MARKER = MarkerManager.getMarker("SOUNDS");
   private final SoundHandler sndHandler;
   private final GameSettings options;
   private SoundManager$SoundSystemStarterThread m;
   private boolean loaded;
   private int playTime = 0;
   private final Map playingSounds = HashBiMap.create();
   private final Map invPlayingSounds;
   private Map playingSoundPoolEntries;
   private final Multimap categorySounds;
   private final List tickableSounds;
   private final Map delayedSounds;
   private final Map playingSoundsStopTime;

   public SoundManager(SoundHandler var1, GameSettings var2) {
      this.invPlayingSounds = ((BiMap)this.playingSounds).inverse();
      this.playingSoundPoolEntries = Maps.newHashMap();
      this.categorySounds = HashMultimap.create();
      this.tickableSounds = Lists.newArrayList();
      this.delayedSounds = Maps.newHashMap();
      this.playingSoundsStopTime = Maps.newHashMap();
      this.sndHandler = var1;
      this.options = var2;
      Class var10000 = LibraryLWJGLOpenAL.class;

      try {
         SoundSystemConfig.addLibrary(var10000);
         SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
      } catch (SoundSystemException var4) {
         logger.error(LOG_MARKER, "Error linking with the LibraryJavaSound plug-in", var4);
      }

   }

   public void reloadSoundSystem() {
      this.unloadSoundSystem();
      this.loadSoundSystem();
   }

   private synchronized void loadSoundSystem() {
      if(!this.loaded) {
         try {
            (new Thread(this::lambda$loadSoundSystem$0, "Sound Library Loader")).start();
         } catch (RuntimeException var2) {
            logger.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", var2);
            this.options.setSoundLevel(SoundCategory.MASTER, 0.0F);
            this.options.saveOptions();
         }
      }

   }

   private float getSoundCategoryVolume(SoundCategory var1) {
      return var1 != SoundCategory.MASTER?this.options.getSoundLevel(var1):1.0F;
   }

   public void setSoundCategoryVolume(SoundCategory var1, float var2) {
      if(this.loaded) {
         if(var1 == SoundCategory.MASTER) {
            this.m.setMasterVolume(var2);
         } else {
            for(String var4 : this.categorySounds.get(var1)) {
               ISound var5 = (ISound)this.playingSounds.get(var4);
               float var6 = this.getNormalizedVolume(var5, (SoundPoolEntry)this.playingSoundPoolEntries.get(var5), var1);
               if(var6 <= 0.0F) {
                  this.stopSound(var5);
               } else {
                  this.m.setVolume(var4, var6);
               }
            }
         }
      }

   }

   public void unloadSoundSystem() {
      if(this.loaded) {
         this.stopAllSounds();
         this.m.cleanup();
         this.loaded = false;
      }

   }

   public void stopAllSounds() {
      if(this.loaded) {
         for(String var2 : this.playingSounds.keySet()) {
            this.m.stop(var2);
         }

         this.playingSounds.clear();
         this.delayedSounds.clear();
         this.tickableSounds.clear();
         this.categorySounds.clear();
         this.playingSoundPoolEntries.clear();
         this.playingSoundsStopTime.clear();
      }

   }

   public void updateAllSounds() {
      ++this.playTime;

      for(ITickableSound var2 : this.tickableSounds) {
         var2.update();
         if(var2.isDonePlaying()) {
            this.stopSound(var2);
         } else {
            String var3 = (String)this.invPlayingSounds.get(var2);
            this.m.setVolume(var3, this.getNormalizedVolume(var2, (SoundPoolEntry)this.playingSoundPoolEntries.get(var2), this.sndHandler.getSound(var2.getSoundLocation()).getSoundCategory()));
            this.m.setPitch(var3, this.getNormalizedPitch(var2, (SoundPoolEntry)this.playingSoundPoolEntries.get(var2)));
            this.m.setPosition(var3, var2.getXPosF(), var2.getYPosF(), var2.getZPosF());
         }
      }

      Iterator var9 = this.playingSounds.entrySet().iterator();

      while(var9.hasNext()) {
         Entry var10 = (Entry)var9.next();
         String var12 = (String)var10.getKey();
         ISound var4 = (ISound)var10.getValue();
         if(!this.m.playing(var12)) {
            int var5 = ((Integer)this.playingSoundsStopTime.get(var12)).intValue();
            if(var5 <= this.playTime) {
               int var6 = var4.getRepeatDelay();
               if(var4.canRepeat()) {
                  this.delayedSounds.put(var4, Integer.valueOf(this.playTime + var6));
               }

               var9.remove();
               logger.debug(LOG_MARKER, "Removed channel {} because it\'s not playing anymore", new Object[]{var12});
               this.m.removeSource(var12);
               this.playingSoundsStopTime.remove(var12);
               this.playingSoundPoolEntries.remove(var4);

               try {
                  this.categorySounds.remove(this.sndHandler.getSound(var4.getSoundLocation()).getSoundCategory(), var12);
               } catch (RuntimeException var8) {
                  ;
               }

               if(var4 instanceof ITickableSound) {
                  this.tickableSounds.remove(var4);
               }
            }
         }
      }

      Iterator var11 = this.delayedSounds.entrySet().iterator();

      while(var11.hasNext()) {
         Entry var13 = (Entry)var11.next();
         if(this.playTime >= ((Integer)var13.getValue()).intValue()) {
            ISound var14 = (ISound)var13.getKey();
            if(var14 instanceof ITickableSound) {
               ((ITickableSound)var14).update();
            }

            this.b(var14);
            var11.remove();
         }
      }

   }

   public boolean isSoundPlaying(ISound var1) {
      if(!this.loaded) {
         return false;
      } else {
         String var2 = (String)this.invPlayingSounds.get(var1);
         return this.m.playing(var2) || this.playingSoundsStopTime.containsKey(var2) && ((Integer)this.playingSoundsStopTime.get(var2)).intValue() <= this.playTime;
      }
   }

   public void stopSound(ISound var1) {
      if(this.loaded) {
         String var2 = (String)this.invPlayingSounds.get(var1);
         this.m.stop(var2);
      }

   }

   public void b(ISound var1) {
      if(this.loaded) {
         if(this.m.getMasterVolume() <= 0.0F) {
            logger.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", new Object[]{var1.getSoundLocation()});
         } else {
            SoundEventAccessorComposite var2 = this.sndHandler.getSound(var1.getSoundLocation());
            logger.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}", new Object[]{var1.getSoundLocation()});
         }
      }

   }

   private float getNormalizedPitch(ISound var1, SoundPoolEntry var2) {
      return (float)MathHelper.b((double)var1.getPitch() * var2.getPitch(), 0.5D, 2.0D);
   }

   private float getNormalizedVolume(ISound var1, SoundPoolEntry var2, SoundCategory var3) {
      return (float)MathHelper.b((double)var1.getVolume() * var2.getVolume(), 0.0D, 1.0D) * this.getSoundCategoryVolume(var3);
   }

   public void pauseAllSounds() {
      for(String var2 : this.playingSounds.keySet()) {
         logger.debug(LOG_MARKER, "Pausing channel {}", new Object[]{var2});
         this.m.pause(var2);
      }

   }

   public void resumeAllSounds() {
      for(String var2 : this.playingSounds.keySet()) {
         logger.debug(LOG_MARKER, "Resuming channel {}", new Object[]{var2});
         this.m.play(var2);
      }

   }

   public void playDelayedSound(ISound var1, int var2) {
      this.delayedSounds.put(var1, Integer.valueOf(this.playTime + var2));
   }

   private static URL getURLForSoundResource(ResourceLocation var0) {
      String var1 = String.format("%s:%s:%s", new Object[]{"mcsounddomain", var0.getResourceDomain(), var0.getResourcePath()});
      rj var2 = new rj(var0);

      try {
         return new URL((URL)null, var1, var2);
      } catch (MalformedURLException var4) {
         throw new Error("TODO: Sanely handle url exception! :D");
      }
   }

   public void setListener(EntityPlayer var1, float var2) {
      if(this.loaded) {
         float var3 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var2;
         float var4 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var2;
         double var5 = var1.prevPosX + (var1.posX - var1.prevPosX) * (double)var2;
         double var7 = var1.prevPosY + (var1.posY - var1.prevPosY) * (double)var2 + (double)var1.getEyeHeight();
         double var9 = var1.prevPosZ + (var1.posZ - var1.prevPosZ) * (double)var2;
         float var11 = MathHelper.cos((var4 + 90.0F) * 0.017453292F);
         float var12 = MathHelper.sin((var4 + 90.0F) * 0.017453292F);
         float var13 = MathHelper.cos(-var3 * 0.017453292F);
         float var14 = MathHelper.sin(-var3 * 0.017453292F);
         float var15 = MathHelper.cos((-var3 + 90.0F) * 0.017453292F);
         float var16 = MathHelper.sin((-var3 + 90.0F) * 0.017453292F);
         float var17 = var11 * var13;
         float var18 = var12 * var13;
         float var19 = var11 * var15;
         float var20 = var12 * var15;
         this.m.setListenerPosition((float)var5, (float)var7, (float)var9);
         this.m.setListenerOrientation(var17, var14, var18, var19, var16, var20);
      }

   }

   private void lambda$loadSoundSystem$0() {
      SoundSystemConfig.setLogger(new GR(this));
      this.m = new SoundManager$SoundSystemStarterThread(this, (GR)null);
      this.loaded = true;
      this.m.setMasterVolume(this.options.getSoundLevel(SoundCategory.MASTER));
      logger.info(LOG_MARKER, "Sound engine started");
   }

   static Logger access$000() {
      return logger;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}

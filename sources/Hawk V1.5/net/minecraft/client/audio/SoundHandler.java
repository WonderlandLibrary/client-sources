package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundHandler implements IUpdatePlayerListBox, IResourceManagerReloadListener {
   private final SoundManager sndManager;
   public static final SoundPoolEntry missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0D, 0.0D, false);
   private final SoundRegistry sndRegistry = new SoundRegistry();
   private static final ParameterizedType field_147696_d = new ParameterizedType() {
      private static final String __OBFID = "CL_00001148";

      public Type getRawType() {
         return Map.class;
      }

      public Type[] getActualTypeArguments() {
         return new Type[]{String.class, SoundList.class};
      }

      public Type getOwnerType() {
         return null;
      }
   };
   private static final Logger logger = LogManager.getLogger();
   private final IResourceManager mcResourceManager;
   private static final Gson field_147699_c = (new GsonBuilder()).registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
   private static final String __OBFID = "CL_00001147";

   public void playSound(ISound var1) {
      this.sndManager.playSound(var1);
   }

   public void onResourceManagerReload(IResourceManager param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean isSoundPlaying(ISound var1) {
      return this.sndManager.isSoundPlaying(var1);
   }

   public void update() {
      this.sndManager.updateAllSounds();
   }

   public void pauseSounds() {
      this.sndManager.pauseAllSounds();
   }

   public void setListener(EntityPlayer var1, float var2) {
      this.sndManager.setListener(var1, var2);
   }

   public void stopSound(ISound var1) {
      this.sndManager.stopSound(var1);
   }

   static SoundRegistry access$0(SoundHandler var0) {
      return var0.sndRegistry;
   }

   public SoundEventAccessorComposite getRandomSoundFromCategories(SoundCategory... var1) {
      ArrayList var2 = Lists.newArrayList();
      Iterator var3 = this.sndRegistry.getKeys().iterator();

      while(var3.hasNext()) {
         ResourceLocation var4 = (ResourceLocation)var3.next();
         SoundEventAccessorComposite var5 = (SoundEventAccessorComposite)this.sndRegistry.getObject(var4);
         if (ArrayUtils.contains(var1, var5.getSoundCategory())) {
            var2.add(var5);
         }
      }

      if (var2.isEmpty()) {
         return null;
      } else {
         return (SoundEventAccessorComposite)var2.get((new Random()).nextInt(var2.size()));
      }
   }

   public void playDelayedSound(ISound var1, int var2) {
      this.sndManager.playDelayedSound(var1, var2);
   }

   public SoundEventAccessorComposite getSound(ResourceLocation var1) {
      return (SoundEventAccessorComposite)this.sndRegistry.getObject(var1);
   }

   protected Map getSoundMap(InputStream var1) {
      try {
         Map var2 = (Map)field_147699_c.fromJson(new InputStreamReader(var1), field_147696_d);
         IOUtils.closeQuietly(var1);
         return var2;
      } finally {
         IOUtils.closeQuietly(var1);
      }
   }

   public void setSoundLevel(SoundCategory var1, float var2) {
      if (var1 == SoundCategory.MASTER && var2 <= 0.0F) {
         this.stopSounds();
      }

      this.sndManager.setSoundCategoryVolume(var1, var2);
   }

   public void resumeSounds() {
      this.sndManager.resumeAllSounds();
   }

   public SoundHandler(IResourceManager var1, GameSettings var2) {
      this.mcResourceManager = var1;
      this.sndManager = new SoundManager(this, var2);
   }

   public void unloadSounds() {
      this.sndManager.unloadSoundSystem();
   }

   private void loadSoundResource(ResourceLocation var1, SoundList var2) {
      boolean var3 = !this.sndRegistry.containsKey(var1);
      SoundEventAccessorComposite var4;
      if (!var3 && !var2.canReplaceExisting()) {
         var4 = (SoundEventAccessorComposite)this.sndRegistry.getObject(var1);
      } else {
         if (!var3) {
            logger.debug("Replaced sound event location {}", new Object[]{var1});
         }

         var4 = new SoundEventAccessorComposite(var1, 1.0D, 1.0D, var2.getSoundCategory());
         this.sndRegistry.registerSound(var4);
      }

      Iterator var5 = var2.getSoundList().iterator();

      while(var5.hasNext()) {
         InputStream var12;
         label124: {
            label123: {
               SoundList.SoundEntry var6 = (SoundList.SoundEntry)var5.next();
               String var7 = var6.getSoundEntryName();
               ResourceLocation var8 = new ResourceLocation(var7);
               String var9 = var7.contains(":") ? var8.getResourceDomain() : var1.getResourceDomain();
               Object var10;
               switch(var6.getSoundEntryType()) {
               case FILE:
                  ResourceLocation var11 = new ResourceLocation(var9, String.valueOf((new StringBuilder("sounds/")).append(var8.getResourcePath()).append(".ogg")));
                  var12 = null;
                  boolean var18 = false;

                  try {
                     try {
                        var18 = true;
                        var12 = this.mcResourceManager.getResource(var11).getInputStream();
                     } catch (FileNotFoundException var19) {
                        logger.warn("File {} does not exist, cannot add it to event {}", new Object[]{var11, var1});
                        var18 = false;
                        break label123;
                     } catch (IOException var20) {
                        logger.warn(String.valueOf((new StringBuilder("Could not load sound file ")).append(var11).append(", cannot add it to event ").append(var1)), var20);
                        var18 = false;
                        break label124;
                     }

                     IOUtils.closeQuietly(var12);
                     var10 = new SoundEventAccessor(new SoundPoolEntry(var11, (double)var6.getSoundEntryPitch(), (double)var6.getSoundEntryVolume(), var6.isStreaming()), var6.getSoundEntryWeight());
                     var18 = false;
                     break;
                  } finally {
                     if (var18) {
                        IOUtils.closeQuietly(var12);
                     }
                  }
               case SOUND_EVENT:
                  var10 = new ISoundEventAccessor(this, var9, var6) {
                     private static final String __OBFID = "CL_00001149";
                     final ResourceLocation field_148726_a;
                     final SoundHandler this$0;

                     public SoundPoolEntry getEntry() {
                        SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.access$0(this.this$0).getObject(this.field_148726_a);
                        return (SoundPoolEntry)(var1 == null ? SoundHandler.missing_sound : var1.cloneEntry());
                     }

                     public Object cloneEntry() {
                        return this.getEntry();
                     }

                     public int getWeight() {
                        SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.access$0(this.this$0).getObject(this.field_148726_a);
                        return var1 == null ? 0 : var1.getWeight();
                     }

                     {
                        this.this$0 = var1;
                        this.field_148726_a = new ResourceLocation(var2, var3.getSoundEntryName());
                     }
                  };
                  break;
               default:
                  throw new IllegalStateException("IN YOU FACE");
               }

               var4.addSoundToEventPool((ISoundEventAccessor)var10);
               continue;
            }

            IOUtils.closeQuietly(var12);
            continue;
         }

         IOUtils.closeQuietly(var12);
      }

   }

   public void stopSounds() {
      this.sndManager.stopAllSounds();
   }

   static final class SwitchType {
      private static final String __OBFID = "CL_00001150";
      static final int[] field_148765_a = new int[SoundList.SoundEntry.Type.values().length];

      static {
         try {
            field_148765_a[SoundList.SoundEntry.Type.FILE.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_148765_a[SoundList.SoundEntry.Type.SOUND_EVENT.ordinal()] = 2;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}

package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.ER;
import net.aeH;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler$1;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundList$SoundEntry$Type;
import net.minecraft.client.audio.SoundListSerializer;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundHandler implements IResourceManagerReloadListener, ITickable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
   private static final ParameterizedType TYPE = new SoundHandler$1();
   public static final SoundPoolEntry missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0D, 0.0D, false);
   private final SoundRegistry sndRegistry = new SoundRegistry();
   private final aeH g;
   private final IResourceManager mcResourceManager;

   public SoundHandler(IResourceManager var1, GameSettings var2) {
      this.mcResourceManager = var1;
      this.g = new aeH(this, var2);
   }

   public SoundRegistry getSndRegistry() {
      return this.sndRegistry;
   }

   public void d() {
      SoundList var1 = new SoundList();
      var1.setSoundCategory(SoundCategory.MASTER);
      ER var2 = new ER();
      var2.a("daun/chris");
      var2.a(SoundList$SoundEntry$Type.FILE);
      var2.a(1.0F);
      var2.b(1.0F);
      var2.a(1);
      var1.getSoundList().add(var2);
      this.loadSoundResource(new ResourceLocation("minecraft", "daun.chris"), var1);
   }

   public void onResourceManagerReload(IResourceManager var1) {
      this.g.h();
      this.sndRegistry.clearMap();
      this.e();

      for(String var3 : var1.getResourceDomains()) {
         try {
            for(IResource var5 : var1.getAllResources(new ResourceLocation(var3, "sounds.json"))) {
               try {
                  Map var6 = this.getSoundMap(var5.getInputStream());

                  for(Entry var8 : var6.entrySet()) {
                     this.loadSoundResource(new ResourceLocation(var3, (String)var8.getKey()), (SoundList)var8.getValue());
                  }
               } catch (RuntimeException var9) {
                  LOGGER.warn("Invalid sounds.json", var9);
               }
            }
         } catch (IOException var10) {
            ;
         }
      }

   }

   protected Map getSoundMap(InputStream var1) {
      Map var2;
      try {
         var2 = (Map)GSON.fromJson(new InputStreamReader(var1), TYPE);
      } finally {
         IOUtils.closeQuietly(var1);
      }

      return var2;
   }

   private void loadSoundResource(ResourceLocation param1, SoundList param2) {
      // $FF: Couldn't be decompiled
   }

   public SoundEventAccessorComposite getSound(ResourceLocation var1) {
      return (SoundEventAccessorComposite)this.sndRegistry.getObject(var1);
   }

   public void playSound(ISound var1) {
      this.g.b(var1);
   }

   public void e() {
      SoundList var1 = new SoundList();
      var1.setSoundCategory(SoundCategory.MASTER);
      ER var2 = new ER();
      var2.a("daun/xfiles");
      var2.a(SoundList$SoundEntry$Type.FILE);
      var2.a(1.0F);
      var2.b(1.0F);
      var2.a(1);
      var1.getSoundList().add(var2);
      this.loadSoundResource(new ResourceLocation("minecraft", "daun.xfiles"), var1);
   }

   public void playDelayedSound(ISound var1, int var2) {
      this.g.a(var1, var2);
   }

   public void setListener(EntityPlayer var1, float var2) {
      this.g.a(var1, var2);
   }

   public void pauseSounds() {
      this.g.c();
   }

   public void stopSounds() {
      this.g.a();
   }

   public void unloadSounds() {
      this.g.g();
   }

   public void update() {
      this.g.e();
   }

   public void resumeSounds() {
      this.g.f();
   }

   public void setSoundLevel(SoundCategory var1, float var2) {
      if(var1 == SoundCategory.MASTER && var2 <= 0.0F) {
         this.stopSounds();
      }

      this.g.a(var1, var2);
   }

   public void stopSound(ISound var1) {
      this.g.c(var1);
   }

   public SoundEventAccessorComposite getRandomSoundFromCategories(SoundCategory... var1) {
      ArrayList var2 = Lists.newArrayList();

      for(ResourceLocation var4 : this.sndRegistry.getKeys()) {
         SoundEventAccessorComposite var5 = (SoundEventAccessorComposite)this.sndRegistry.getObject(var4);
         if(ArrayUtils.contains(var1, var5.getSoundCategory())) {
            var2.add(var5);
         }
      }

      if(var2.isEmpty()) {
         return null;
      } else {
         return (SoundEventAccessorComposite)var2.get((new Random()).nextInt(var2.size()));
      }
   }

   public boolean isSoundPlaying(ISound var1) {
      return this.g.a(var1);
   }

   static SoundRegistry access$000(SoundHandler var0) {
      return var0.sndRegistry;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}

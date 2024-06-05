package net;

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
import net.rj;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager$1;
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

public class aeH {
   private static final Logger g = LogManager.getLogger();
   private static final Marker f = MarkerManager.getMarker("SOUNDS");
   private final SoundHandler d;
   private final GameSettings a;
   private SoundManager$SoundSystemStarterThread m;
   private boolean j;
   private int h = 0;
   private final Map c = HashBiMap.create();
   private final Map n;
   private Map l;
   private final Multimap b;
   private final List i;
   private final Map e;
   private final Map k;

   public aeH(SoundHandler var1, GameSettings var2) {
      this.n = ((BiMap)this.c).inverse();
      this.l = Maps.newHashMap();
      this.b = HashMultimap.create();
      this.i = Lists.newArrayList();
      this.e = Maps.newHashMap();
      this.k = Maps.newHashMap();
      this.d = var1;
      this.a = var2;
      Class var10000 = LibraryLWJGLOpenAL.class;

      try {
         SoundSystemConfig.addLibrary(var10000);
         SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
      } catch (SoundSystemException var4) {
         g.error(f, "Error linking with the LibraryJavaSound plug-in", var4);
      }

   }

   public void h() {
      this.g();
      this.d();
   }

   private synchronized void d() {
      if(!this.j) {
         try {
            (new Thread(this::lambda$loadSoundSystem$0, "Sound Library Loader")).start();
         } catch (RuntimeException var2) {
            g.error(f, "Error starting SoundSystem. Turning off sounds & music", var2);
            this.a.setSoundLevel(SoundCategory.MASTER, 0.0F);
            this.a.saveOptions();
         }
      }

   }

   private float a(SoundCategory var1) {
      return var1 != SoundCategory.MASTER?this.a.getSoundLevel(var1):1.0F;
   }

   public void a(SoundCategory var1, float var2) {
      if(this.j) {
         if(var1 == SoundCategory.MASTER) {
            this.m.setMasterVolume(var2);
         } else {
            for(String var4 : this.b.get(var1)) {
               ISound var5 = (ISound)this.c.get(var4);
               float var6 = this.a(var5, (SoundPoolEntry)this.l.get(var5), var1);
               if(var6 <= 0.0F) {
                  this.c(var5);
               } else {
                  this.m.setVolume(var4, var6);
               }
            }
         }
      }

   }

   public void g() {
      if(this.j) {
         this.a();
         this.m.cleanup();
         this.j = false;
      }

   }

   public void a() {
      if(this.j) {
         for(String var2 : this.c.keySet()) {
            this.m.stop(var2);
         }

         this.c.clear();
         this.e.clear();
         this.i.clear();
         this.b.clear();
         this.l.clear();
         this.k.clear();
      }

   }

   public void e() {
      ++this.h;

      for(ITickableSound var2 : this.i) {
         var2.update();
         if(var2.isDonePlaying()) {
            this.c(var2);
         } else {
            String var3 = (String)this.n.get(var2);
            this.m.setVolume(var3, this.a(var2, (SoundPoolEntry)this.l.get(var2), this.d.getSound(var2.getSoundLocation()).getSoundCategory()));
            this.m.setPitch(var3, this.a(var2, (SoundPoolEntry)this.l.get(var2)));
            this.m.setPosition(var3, var2.getXPosF(), var2.getYPosF(), var2.getZPosF());
         }
      }

      Iterator var9 = this.c.entrySet().iterator();

      while(var9.hasNext()) {
         Entry var10 = (Entry)var9.next();
         String var12 = (String)var10.getKey();
         ISound var4 = (ISound)var10.getValue();
         if(!this.m.playing(var12)) {
            int var5 = ((Integer)this.k.get(var12)).intValue();
            if(var5 <= this.h) {
               int var6 = var4.getRepeatDelay();
               if(var4.canRepeat()) {
                  this.e.put(var4, Integer.valueOf(this.h + var6));
               }

               var9.remove();
               g.debug(f, "Removed channel {} because it\'s not playing anymore", new Object[]{var12});
               this.m.removeSource(var12);
               this.k.remove(var12);
               this.l.remove(var4);

               try {
                  this.b.remove(this.d.getSound(var4.getSoundLocation()).getSoundCategory(), var12);
               } catch (RuntimeException var8) {
                  ;
               }

               if(var4 instanceof ITickableSound) {
                  this.i.remove(var4);
               }
            }
         }
      }

      Iterator var11 = this.e.entrySet().iterator();

      while(var11.hasNext()) {
         Entry var13 = (Entry)var11.next();
         if(this.h >= ((Integer)var13.getValue()).intValue()) {
            ISound var14 = (ISound)var13.getKey();
            if(var14 instanceof ITickableSound) {
               ((ITickableSound)var14).update();
            }

            this.b(var14);
            var11.remove();
         }
      }

   }

   public boolean a(ISound var1) {
      if(!this.j) {
         return false;
      } else {
         String var2 = (String)this.n.get(var1);
         return this.m.playing(var2) || this.k.containsKey(var2) && ((Integer)this.k.get(var2)).intValue() <= this.h;
      }
   }

   public void c(ISound var1) {
      if(this.j) {
         String var2 = (String)this.n.get(var1);
         this.m.stop(var2);
      }

   }

   public void b(ISound var1) {
      if(this.j) {
         if(this.m.getMasterVolume() <= 0.0F) {
            g.debug(f, "Skipped playing soundEvent: {}, master volume was zero", new Object[]{var1.getSoundLocation()});
         } else {
            SoundEventAccessorComposite var2 = this.d.getSound(var1.getSoundLocation());
            g.warn(f, "Unable to play unknown soundEvent: {}", new Object[]{var1.getSoundLocation()});
         }
      }

   }

   private float a(ISound var1, SoundPoolEntry var2) {
      return (float)MathHelper.clamp_double((double)var1.getPitch() * var2.getPitch(), 0.5D, 2.0D);
   }

   private float a(ISound var1, SoundPoolEntry var2, SoundCategory var3) {
      return (float)MathHelper.clamp_double((double)var1.getVolume() * var2.getVolume(), 0.0D, 1.0D) * this.a(var3);
   }

   public void c() {
      for(String var2 : this.c.keySet()) {
         g.debug(f, "Pausing channel {}", new Object[]{var2});
         this.m.pause(var2);
      }

   }

   public void f() {
      for(String var2 : this.c.keySet()) {
         g.debug(f, "Resuming channel {}", new Object[]{var2});
         this.m.play(var2);
      }

   }

   public void a(ISound var1, int var2) {
      this.e.put(var1, Integer.valueOf(this.h + var2));
   }

   private static URL a(ResourceLocation var0) {
      String var1 = String.format("%s:%s:%s", new Object[]{"mcsounddomain", var0.getResourceDomain(), var0.getResourcePath()});
      rj var2 = new rj(var0);

      try {
         return new URL((URL)null, var1, var2);
      } catch (MalformedURLException var4) {
         throw new Error("TODO: Sanely handle url exception! :D");
      }
   }

   public void a(EntityPlayer var1, float var2) {
      if(this.j) {
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
      SoundSystemConfig.setLogger(new SoundManager$1(this));
      this.m = new SoundManager$SoundSystemStarterThread(this, (SoundManager$1)null);
      this.j = true;
      this.m.setMasterVolume(this.a.getSoundLevel(SoundCategory.MASTER));
      g.info(f, "Sound engine started");
   }

   static Logger b() {
      return g;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}

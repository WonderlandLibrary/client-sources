package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class SkinManager {
   private final LoadingCache skinCacheLoader;
   private final TextureManager textureManager;
   private static final String __OBFID = "CL_00001830";
   private final File skinCacheDir;
   private static final ExecutorService THREAD_POOL;
   private final MinecraftSessionService sessionService;

   public void func_152790_a(GameProfile var1, SkinManager.SkinAvailableCallback var2, boolean var3) {
      THREAD_POOL.submit(new Runnable(this, var1, var3, var2) {
         private final GameProfile val$p_152790_1_;
         private final boolean val$p_152790_3_;
         private final SkinManager.SkinAvailableCallback val$p_152790_2_;
         private static final String __OBFID = "CL_00001827";
         final SkinManager this$0;

         {
            this.this$0 = var1;
            this.val$p_152790_1_ = var2;
            this.val$p_152790_3_ = var3;
            this.val$p_152790_2_ = var4;
         }

         static SkinManager access$0(Object var0) {
            return var0.this$0;
         }

         public void run() {
            HashMap var1 = Maps.newHashMap();

            try {
               var1.putAll(SkinManager.access$0(this.this$0).getTextures(this.val$p_152790_1_, this.val$p_152790_3_));
            } catch (InsecureTextureException var3) {
            }

            if (var1.isEmpty() && this.val$p_152790_1_.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
               var1.putAll(SkinManager.access$0(this.this$0).getTextures(SkinManager.access$0(this.this$0).fillProfileProperties(this.val$p_152790_1_, false), false));
            }

            Minecraft.getMinecraft().addScheduledTask(new Runnable(this, var1, this.val$p_152790_2_) {
               private final SkinManager.SkinAvailableCallback val$p_152790_2_;
               private static final String __OBFID = "CL_00001826";
               private final HashMap val$var1;
               final <undefinedtype> this$1;

               public void run() {
                  if (this.val$var1.containsKey(Type.SKIN)) {
                     null.access$0(this.this$1).loadSkin((MinecraftProfileTexture)this.val$var1.get(Type.SKIN), Type.SKIN, this.val$p_152790_2_);
                  }

                  if (this.val$var1.containsKey(Type.CAPE)) {
                     null.access$0(this.this$1).loadSkin((MinecraftProfileTexture)this.val$var1.get(Type.CAPE), Type.CAPE, this.val$p_152790_2_);
                  }

               }

               {
                  this.this$1 = var1;
                  this.val$var1 = var2;
                  this.val$p_152790_2_ = var3;
               }
            });
         }
      });
   }

   public ResourceLocation loadSkin(MinecraftProfileTexture var1, Type var2, SkinManager.SkinAvailableCallback var3) {
      ResourceLocation var4 = new ResourceLocation(String.valueOf((new StringBuilder("skins/")).append(var1.getHash())));
      ITextureObject var5 = this.textureManager.getTexture(var4);
      if (var5 != null) {
         if (var3 != null) {
            var3.func_180521_a(var2, var4, var1);
         }
      } else {
         File var6 = new File(this.skinCacheDir, var1.getHash().substring(0, 2));
         File var7 = new File(var6, var1.getHash());
         ImageBufferDownload var8 = var2 == Type.SKIN ? new ImageBufferDownload() : null;
         ThreadDownloadImageData var9 = new ThreadDownloadImageData(var7, var1.getUrl(), DefaultPlayerSkin.func_177335_a(), new IImageBuffer(this, var8, var3, var2, var4, var1) {
            private final SkinManager.SkinAvailableCallback val$p_152789_3_;
            private final Type val$p_152789_2_;
            private static final String __OBFID = "CL_00001828";
            private final ImageBufferDownload val$var8;
            final SkinManager this$0;
            private final MinecraftProfileTexture val$p_152789_1_;
            private final ResourceLocation val$var4;

            public void func_152634_a() {
               if (this.val$var8 != null) {
                  this.val$var8.func_152634_a();
               }

               if (this.val$p_152789_3_ != null) {
                  this.val$p_152789_3_.func_180521_a(this.val$p_152789_2_, this.val$var4, this.val$p_152789_1_);
               }

            }

            {
               this.this$0 = var1;
               this.val$var8 = var2;
               this.val$p_152789_3_ = var3;
               this.val$p_152789_2_ = var4;
               this.val$var4 = var5;
               this.val$p_152789_1_ = var6;
            }

            public BufferedImage parseUserSkin(BufferedImage var1) {
               if (this.val$var8 != null) {
                  var1 = this.val$var8.parseUserSkin(var1);
               }

               return var1;
            }
         });
         this.textureManager.loadTexture(var4, var9);
      }

      return var4;
   }

   public SkinManager(TextureManager var1, File var2, MinecraftSessionService var3) {
      this.textureManager = var1;
      this.skinCacheDir = var2;
      this.sessionService = var3;
      this.skinCacheLoader = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader(this) {
         private static final String __OBFID = "CL_00001829";
         final SkinManager this$0;

         public Map func_152786_a(GameProfile var1) {
            return Minecraft.getMinecraft().getSessionService().getTextures(var1, false);
         }

         public Object load(Object var1) {
            return this.func_152786_a((GameProfile)var1);
         }

         {
            this.this$0 = var1;
         }
      });
   }

   static MinecraftSessionService access$0(SkinManager var0) {
      return var0.sessionService;
   }

   public ResourceLocation loadSkin(MinecraftProfileTexture var1, Type var2) {
      return this.loadSkin(var1, var2, (SkinManager.SkinAvailableCallback)null);
   }

   static {
      THREAD_POOL = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue());
   }

   public Map loadSkinFromCache(GameProfile var1) {
      return (Map)this.skinCacheLoader.getUnchecked(var1);
   }

   public interface SkinAvailableCallback {
      void func_180521_a(Type var1, ResourceLocation var2, MinecraftProfileTexture var3);
   }
}

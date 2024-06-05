package net;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SkinManager$1;
import net.minecraft.client.resources.SkinManager$SkinAvailableCallback;
import net.minecraft.util.ResourceLocation;

public class nk {
   private static final ExecutorService a = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue());
   private final TextureManager e;
   private final File d;
   private final MinecraftSessionService c;
   private final LoadingCache b;

   public nk(TextureManager var1, File var2, MinecraftSessionService var3) {
      this.e = var1;
      this.d = var2;
      this.c = var3;
      this.b = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new SkinManager$1(this));
   }

   public ResourceLocation a(MinecraftProfileTexture var1, Type var2) {
      return this.a(var1, var2, (SkinManager$SkinAvailableCallback)null);
   }

   public ResourceLocation a(MinecraftProfileTexture var1, Type var2, SkinManager$SkinAvailableCallback var3) {
      ResourceLocation var4 = new ResourceLocation("skins/" + var1.getHash());
      ITextureObject var5 = this.e.getTexture(var4);
      var3.skinAvailable(var2, var4, var1);
      return var4;
   }

   public void a(GameProfile var1, SkinManager$SkinAvailableCallback var2, boolean var3) {
      a.submit(this::lambda$loadProfileTextures$1);
   }

   public Map a(GameProfile var1) {
      return (Map)this.b.getUnchecked(var1);
   }

   private void lambda$loadProfileTextures$1(GameProfile var1, boolean var2, SkinManager$SkinAvailableCallback var3) {
      HashMap var4 = Maps.newHashMap();

      try {
         var4.putAll(this.c.getTextures(var1, var2));
      } catch (InsecureTextureException var6) {
         ;
      }

      if(var4.isEmpty() && var1.getId().equals(Minecraft.getInstance().getSession().getProfile().getId())) {
         var1.getProperties().clear();
         var1.getProperties().putAll(Minecraft.getInstance().func_181037_M());
         var4.putAll(this.c.getTextures(var1, false));
      }

      Minecraft.getInstance().addScheduledTask(this::lambda$null$0);
   }

   private void lambda$null$0(Map var1, SkinManager$SkinAvailableCallback var2) {
      if(var1.containsKey(Type.SKIN)) {
         this.a((MinecraftProfileTexture)var1.get(Type.SKIN), Type.SKIN, var2);
      }

      if(var1.containsKey(Type.CAPE)) {
         this.a((MinecraftProfileTexture)var1.get(Type.CAPE), Type.CAPE, var2);
      }

   }

   private static InsecureTextureException a(InsecureTextureException var0) {
      return var0;
   }
}

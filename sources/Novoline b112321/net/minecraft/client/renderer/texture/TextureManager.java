package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.RandomMobs;
import net.shadersmod.client.ShadersTex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager implements ITickable, IResourceManagerReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map mapTextureObjects = Maps.newHashMap();
   private final List listTickables = Lists.newArrayList();
   private final Map mapTextureCounters = Maps.newHashMap();
   private final IResourceManager theResourceManager;

   public TextureManager(IResourceManager var1) {
      this.theResourceManager = var1;
   }

   public void bindTexture(ResourceLocation var1) {
      if(Config.isRandomMobs()) {
         var1 = RandomMobs.getTextureLocation(var1);
      }

      ITextureObject var2 = (ITextureObject)this.mapTextureObjects.get(var1);
      SimpleTexture var3 = new SimpleTexture(var1);
      this.loadTexture(var1, var3);
      if(Config.isShaders()) {
         ShadersTex.bindTexture(var3);
      } else {
         TextureUtil.bindTexture(var3.getGlTextureId());
      }

   }

   public ITextureObject bindTextureA(ResourceLocation var1) {
      if(Config.isRandomMobs()) {
         var1 = RandomMobs.getTextureLocation(var1);
      }

      ITextureObject var2 = (ITextureObject)this.mapTextureObjects.get(var1);
      SimpleTexture var3 = new SimpleTexture(var1);
      this.loadTexture(var1, var3);
      if(Config.isShaders()) {
         ShadersTex.bindTexture(var3);
      } else {
         TextureUtil.bindTexture(var3.getGlTextureId());
      }

      return var3;
   }

   public boolean loadTickableTexture(ResourceLocation var1, ITickableTextureObject var2) {
      if(this.loadTexture(var1, var2)) {
         this.listTickables.add(var2);
         return true;
      } else {
         return false;
      }
   }

   public boolean loadTexture(ResourceLocation var1, ITextureObject var2) {
      boolean var3 = true;
      Object var4 = var2;

      label5: {
         Throwable var5;
         try {
            ITextureObject var10000 = var2;
            TextureManager var10001 = this;

            try {
               var10000.loadTexture(var10001.theResourceManager);
               break label5;
            } catch (Throwable var8) {
               var5 = var8;
            }
         } catch (IOException var9) {
            LOGGER.warn("Failed to load texture: " + var1, var9);
            var4 = TextureUtil.missingTexture;
            this.mapTextureObjects.put(var1, var4);
            var3 = false;
            break label5;
         }

         CrashReport var6 = CrashReport.makeCrashReport(var5, "Registering texture");
         CrashReportCategory var7 = var6.makeCategory("Resource location being registered");
         var7.addCrashSection("Resource location", var1);
         var7.addCrashSectionCallable("Texture object class", TextureManager::lambda$loadTexture$0);
         throw new ReportedException(var6);
      }

      this.mapTextureObjects.put(var1, var4);
      return var3;
   }

   public ITextureObject getTexture(ResourceLocation var1) {
      return (ITextureObject)this.mapTextureObjects.get(var1);
   }

   public ResourceLocation getDynamicTextureLocation(String var1, DynamicTexture var2) {
      if(var1.equals("logo")) {
         var2 = Config.getMojangLogoTexture(var2);
      }

      Integer var3 = (Integer)this.mapTextureCounters.get(var1);
      var3 = Integer.valueOf(1);
      this.mapTextureCounters.put(var1, var3);
      ResourceLocation var4 = new ResourceLocation(String.format("dynamic/%s_%d", new Object[]{var1, var3}));
      this.loadTexture(var4, var2);
      return var4;
   }

   public void tick() {
      for(Object var2 : this.listTickables) {
         ((ITickable)var2).tick();
      }

   }

   public void deleteTexture(ResourceLocation var1) {
      ITextureObject var2 = this.getTexture(var1);
      this.mapTextureObjects.remove(var1);
      TextureUtil.deleteTexture(var2.getGlTextureId());
   }

   public void onResourceManagerReload(IResourceManager var1) {
      Config.dbg("*** Reloading textures ***");
      Config.log("Resource packs: " + Config.getResourcePackNames());
      Iterator var2 = this.mapTextureObjects.keySet().iterator();

      while(var2.hasNext()) {
         ResourceLocation var3 = (ResourceLocation)var2.next();
         String var4 = var3.getResourcePath();
         if(var4.startsWith("mcpatcher/") || var4.startsWith("optifine/")) {
            ITextureObject var5 = (ITextureObject)this.mapTextureObjects.get(var3);
            if(var5 instanceof AbstractTexture) {
               AbstractTexture var6 = (AbstractTexture)var5;
               var6.deleteGlTexture();
            }

            var2.remove();
         }
      }

      for(Entry var8 : this.mapTextureObjects.entrySet()) {
         this.loadTexture((ResourceLocation)var8.getKey(), (ITextureObject)var8.getValue());
      }

   }

   public void reloadBannerTextures() {
      for(Entry var2 : this.mapTextureObjects.entrySet()) {
         ITextureObject var3 = (ITextureObject)var2.getValue();
         if(var3 instanceof LayeredColorMaskTexture) {
            this.loadTexture((ResourceLocation)var2.getKey(), var3);
         }
      }

   }

   private static String lambda$loadTexture$0(ITextureObject var0) throws Exception {
      return var0.getClass().getName();
   }

   private static ReportedException a(ReportedException var0) {
      return var0;
   }
}

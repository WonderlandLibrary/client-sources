package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomGuis;
import net.optifine.EmissiveTextures;
import net.optifine.RandomEntities;
import net.optifine.shaders.ShadersTex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager implements ITickable, IResourceManagerReloadListener {
   private static final Logger logger = LogManager.getLogger();
   private final Map<ResourceLocation, ITextureObject> mapTextureObjects = Maps.newHashMap();
   private final List<ITickable> listTickables = Lists.newArrayList();
   private final Map<String, Integer> mapTextureCounters = Maps.newHashMap();
   private IResourceManager theResourceManager;

   public TextureManager(IResourceManager resourceManager) {
      this.theResourceManager = resourceManager;
   }

   public void bindTexture(ResourceLocation resource) {
      if (Config.isRandomEntities()) {
         resource = RandomEntities.getTextureLocation(resource);
      }

      if (Config.isCustomGuis()) {
         resource = CustomGuis.getTextureLocation(resource);
      }

      ITextureObject itextureobject = (ITextureObject)this.mapTextureObjects.get(resource);
      if (EmissiveTextures.isActive()) {
         itextureobject = EmissiveTextures.getEmissiveTexture((ITextureObject)itextureobject, this.mapTextureObjects);
      }

      if (itextureobject == null) {
         itextureobject = new SimpleTexture(resource);
         this.loadTexture(resource, (ITextureObject)itextureobject);
      }

      if (Config.isShaders()) {
         ShadersTex.bindTexture((ITextureObject)itextureobject);
      } else {
         TextureUtil.bindTexture(((ITextureObject)itextureobject).getGlTextureId());
      }

   }

   public boolean loadTickableTexture(ResourceLocation textureLocation, ITickableTextureObject textureObj) {
      if (this.loadTexture(textureLocation, textureObj)) {
         this.listTickables.add(textureObj);
         return true;
      } else {
         return false;
      }
   }

   public boolean loadTexture(ResourceLocation textureLocation, final ITextureObject textureObj) {
      boolean flag = true;

      try {
         ((ITextureObject)textureObj).loadTexture(this.theResourceManager);
      } catch (IOException var8) {
         logger.warn("Failed to load texture: " + textureLocation, var8);
         textureObj = TextureUtil.missingTexture;
         this.mapTextureObjects.put(textureLocation, textureObj);
         flag = false;
      } catch (Throwable var9) {
         CrashReport crashreport = CrashReport.makeCrashReport(var9, "Registering texture");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Resource location being registered");
         crashreportcategory.addCrashSection("Resource location", textureLocation);
         crashreportcategory.addCrashSectionCallable("Texture object class", new Callable<String>() {
            public String call() throws Exception {
               return textureObj.getClass().getName();
            }
         });
         throw new ReportedException(crashreport);
      }

      this.mapTextureObjects.put(textureLocation, textureObj);
      return flag;
   }

   public ITextureObject getTexture(ResourceLocation textureLocation) {
      return (ITextureObject)this.mapTextureObjects.get(textureLocation);
   }

   public ResourceLocation getDynamicTextureLocation(String name, DynamicTexture texture) {
      if (name.equals("logo")) {
         texture = Config.getMojangLogoTexture(texture);
      }

      Integer integer = (Integer)this.mapTextureCounters.get(name);
      if (integer == null) {
         integer = 1;
      } else {
         integer = integer + 1;
      }

      this.mapTextureCounters.put(name, integer);
      ResourceLocation resourcelocation = new ResourceLocation(String.format("dynamic/%s_%d", name, integer));
      this.loadTexture(resourcelocation, texture);
      return resourcelocation;
   }

   public void tick() {
      Iterator var1 = this.listTickables.iterator();

      while(var1.hasNext()) {
         ITickable itickable = (ITickable)var1.next();
         itickable.tick();
      }

   }

   public void deleteTexture(ResourceLocation textureLocation) {
      ITextureObject itextureobject = this.getTexture(textureLocation);
      if (itextureobject != null) {
         this.mapTextureObjects.remove(textureLocation);
         TextureUtil.deleteTexture(itextureobject.getGlTextureId());
      }

   }

   public void onResourceManagerReload(IResourceManager resourceManager) {
      Config.dbg("*** Reloading textures ***");
      Config.log("Resource packs: " + Config.getResourcePackNames());
      Iterator iterator = this.mapTextureObjects.keySet().iterator();

      while(true) {
         ResourceLocation resourcelocation;
         String s;
         do {
            if (!iterator.hasNext()) {
               EmissiveTextures.update();
               Iterator var7 = (new HashSet(this.mapTextureObjects.entrySet())).iterator();

               while(var7.hasNext()) {
                  Object e = var7.next();
                  Entry<ResourceLocation, ITextureObject> entry = (Entry)e;
                  this.loadTexture((ResourceLocation)entry.getKey(), (ITextureObject)entry.getValue());
               }

               return;
            }

            resourcelocation = (ResourceLocation)iterator.next();
            s = resourcelocation.getResourcePath();
         } while(!s.startsWith("mcpatcher/") && !s.startsWith("optifine/") && !EmissiveTextures.isEmissive(resourcelocation));

         ITextureObject itextureobject = (ITextureObject)this.mapTextureObjects.get(resourcelocation);
         if (itextureobject instanceof AbstractTexture) {
            AbstractTexture abstracttexture = (AbstractTexture)itextureobject;
            abstracttexture.deleteGlTexture();
         }

         iterator.remove();
      }
   }

   public void reloadBannerTextures() {
      Iterator var1 = (new HashSet(this.mapTextureObjects.entrySet())).iterator();

      while(var1.hasNext()) {
         Object e = var1.next();
         Entry<ResourceLocation, ITextureObject> entry = (Entry)e;
         ResourceLocation resourcelocation = (ResourceLocation)entry.getKey();
         ITextureObject itextureobject = (ITextureObject)entry.getValue();
         if (itextureobject instanceof LayeredColorMaskTexture) {
            this.loadTexture(resourcelocation, itextureobject);
         }
      }

   }
}

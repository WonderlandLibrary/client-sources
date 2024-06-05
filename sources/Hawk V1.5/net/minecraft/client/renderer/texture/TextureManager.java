package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.RandomMobs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shadersmod.client.ShadersTex;

public class TextureManager implements IResourceManagerReloadListener, ITickable {
   private final List listTickables = Lists.newArrayList();
   private final Map mapTextureCounters = Maps.newHashMap();
   private static final String __OBFID = "CL_00001064";
   private final Map mapTextureObjects = Maps.newHashMap();
   private static final Logger logger = LogManager.getLogger();
   private IResourceManager theResourceManager;

   public void deleteTexture(ResourceLocation var1) {
      ITextureObject var2 = this.getTexture(var1);
      if (var2 != null) {
         TextureUtil.deleteTexture(var2.getGlTextureId());
      }

   }

   public ITextureObject getTexture(ResourceLocation var1) {
      return (ITextureObject)this.mapTextureObjects.get(var1);
   }

   public boolean loadTickableTexture(ResourceLocation var1, ITickableTextureObject var2) {
      if (this.loadTexture(var1, var2)) {
         this.listTickables.add(var2);
         return true;
      } else {
         return false;
      }
   }

   public void bindTexture(ResourceLocation var1) {
      if (Config.isRandomMobs()) {
         var1 = RandomMobs.getTextureLocation(var1);
      }

      Object var2 = (ITextureObject)this.mapTextureObjects.get(var1);
      if (var2 == null) {
         var2 = new SimpleTexture(var1);
         this.loadTexture(var1, (ITextureObject)var2);
      }

      if (Config.isShaders()) {
         ShadersTex.bindTexture((ITextureObject)var2);
      } else {
         TextureUtil.bindTexture(((ITextureObject)var2).getGlTextureId());
      }

   }

   public boolean loadTexture(ResourceLocation var1, ITextureObject var2) {
      boolean var3 = true;
      Object var4 = var2;

      try {
         var2.loadTexture(this.theResourceManager);
      } catch (IOException var8) {
         logger.warn(String.valueOf((new StringBuilder("Failed to load texture: ")).append(var1)), var8);
         var4 = TextureUtil.missingTexture;
         this.mapTextureObjects.put(var1, var4);
         var3 = false;
      } catch (Throwable var9) {
         CrashReport var6 = CrashReport.makeCrashReport(var9, "Registering texture");
         CrashReportCategory var7 = var6.makeCategory("Resource location being registered");
         var7.addCrashSection("Resource location", var1);
         var7.addCrashSectionCallable("Texture object class", new Callable(this, var2) {
            private final ITextureObject val$p_110579_2_;
            final TextureManager this$0;
            private static final String __OBFID = "CL_00001065";

            public String call() {
               return this.val$p_110579_2_.getClass().getName();
            }

            {
               this.this$0 = var1;
               this.val$p_110579_2_ = var2;
            }

            public Object call() throws Exception {
               return this.call();
            }
         });
         throw new ReportedException(var6);
      }

      this.mapTextureObjects.put(var1, var4);
      return var3;
   }

   public TextureManager(IResourceManager var1) {
      this.theResourceManager = var1;
   }

   public void onResourceManagerReload(IResourceManager var1) {
      Config.dbg("*** Reloading textures ***");
      Config.log(String.valueOf((new StringBuilder("Resource packs: ")).append(Config.getResourcePackNames())));
      Iterator var2 = this.mapTextureObjects.keySet().iterator();

      while(var2.hasNext()) {
         ResourceLocation var3 = (ResourceLocation)var2.next();
         if (var3.getResourcePath().startsWith("mcpatcher/")) {
            ITextureObject var4 = (ITextureObject)this.mapTextureObjects.get(var3);
            if (var4 instanceof AbstractTexture) {
               AbstractTexture var5 = (AbstractTexture)var4;
               var5.deleteGlTexture();
            }

            var2.remove();
         }
      }

      Iterator var6 = this.mapTextureObjects.entrySet().iterator();

      while(var6.hasNext()) {
         Entry var7 = (Entry)var6.next();
         this.loadTexture((ResourceLocation)var7.getKey(), (ITextureObject)var7.getValue());
      }

   }

   public void tick() {
      Iterator var1 = this.listTickables.iterator();

      while(var1.hasNext()) {
         ITickable var2 = (ITickable)var1.next();
         var2.tick();
      }

   }

   public ResourceLocation getDynamicTextureLocation(String var1, DynamicTexture var2) {
      if (var1.equals("logo")) {
         var2 = Config.getMojangLogoTexture(var2);
      }

      Integer var3 = (Integer)this.mapTextureCounters.get(var1);
      if (var3 == null) {
         var3 = 1;
      } else {
         var3 = var3 + 1;
      }

      this.mapTextureCounters.put(var1, var3);
      ResourceLocation var4 = new ResourceLocation(String.format("dynamic/%s_%d", var1, var3));
      this.loadTexture(var4, var2);
      return var4;
   }
}

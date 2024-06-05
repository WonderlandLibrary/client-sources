package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.apX;
import net.rW;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleResource;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallbackResourceManager implements IResourceManager {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final List resourcePacks = Lists.newArrayList();
   private final apX b;

   public FallbackResourceManager(apX var1) {
      this.b = var1;
   }

   public void addResourcePack(IResourcePack var1) {
      this.resourcePacks.add(var1);
   }

   public Set getResourceDomains() {
      return null;
   }

   public IResource getResource(ResourceLocation var1) throws IOException {
      IResourcePack var2 = null;
      ResourceLocation var3 = getLocationMcmeta(var1);
      int var4 = this.resourcePacks.size() - 1;

      while(true) {
         IResourcePack var5 = (IResourcePack)this.resourcePacks.get(var4);
         if(var5.resourceExists(var3)) {
            var2 = var5;
         }

         if(var5.resourceExists(var1)) {
            InputStream var6 = null;
            var6 = this.getInputStream(var3, var2);
            return new SimpleResource(var5.getPackName(), var1, this.getInputStream(var1, var5), var6, this.b);
         }

         --var4;
      }
   }

   protected InputStream getInputStream(ResourceLocation var1, IResourcePack var2) throws IOException {
      InputStream var3 = var2.getInputStream(var1);
      return (InputStream)(LOGGER.isDebugEnabled()?new rW(var3, var1, var2.getPackName()):var3);
   }

   public List getAllResources(ResourceLocation var1) throws IOException {
      ArrayList var2 = Lists.newArrayList();
      ResourceLocation var3 = getLocationMcmeta(var1);

      for(IResourcePack var5 : this.resourcePacks) {
         if(var5.resourceExists(var1)) {
            InputStream var6 = var5.resourceExists(var3)?this.getInputStream(var3, var5):null;
            var2.add(new SimpleResource(var5.getPackName(), var1, this.getInputStream(var1, var5), var6, this.b));
         }
      }

      if(var2.isEmpty()) {
         throw new FileNotFoundException(var1.toString());
      } else {
         return var2;
      }
   }

   static ResourceLocation getLocationMcmeta(ResourceLocation var0) {
      return new ResourceLocation(var0.getResourceDomain(), var0.getResourcePath() + ".mcmeta");
   }

   static Logger access$000() {
      return LOGGER;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

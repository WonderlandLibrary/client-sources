package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallbackResourceManager implements IResourceManager {
   protected final List resourcePacks = Lists.newArrayList();
   private static final String __OBFID = "CL_00001074";
   private static final Logger field_177246_b = LogManager.getLogger();
   private final IMetadataSerializer frmMetadataSerializer;

   public IResource getResource(ResourceLocation var1) throws IOException {
      IResourcePack var2 = null;
      ResourceLocation var3 = getLocationMcmeta(var1);

      for(int var4 = this.resourcePacks.size() - 1; var4 >= 0; --var4) {
         IResourcePack var5 = (IResourcePack)this.resourcePacks.get(var4);
         if (var2 == null && var5.resourceExists(var3)) {
            var2 = var5;
         }

         if (var5.resourceExists(var1)) {
            InputStream var6 = null;
            if (var2 != null) {
               var6 = this.func_177245_a(var3, var2);
            }

            return new SimpleResource(var5.getPackName(), var1, this.func_177245_a(var1, var5), var6, this.frmMetadataSerializer);
         }
      }

      throw new FileNotFoundException(var1.toString());
   }

   protected InputStream func_177245_a(ResourceLocation var1, IResourcePack var2) throws IOException {
      InputStream var3 = var2.getInputStream(var1);
      return (InputStream)(field_177246_b.isDebugEnabled() ? new FallbackResourceManager.ImputStreamLeakedResourceLogger(var3, var1, var2.getPackName()) : var3);
   }

   public List getAllResources(ResourceLocation var1) throws IOException {
      ArrayList var2 = Lists.newArrayList();
      ResourceLocation var3 = getLocationMcmeta(var1);
      Iterator var4 = this.resourcePacks.iterator();

      while(var4.hasNext()) {
         IResourcePack var5 = (IResourcePack)var4.next();
         if (var5.resourceExists(var1)) {
            InputStream var6 = var5.resourceExists(var3) ? this.func_177245_a(var3, var5) : null;
            var2.add(new SimpleResource(var5.getPackName(), var1, this.func_177245_a(var1, var5), var6, this.frmMetadataSerializer));
         }
      }

      if (var2.isEmpty()) {
         throw new FileNotFoundException(var1.toString());
      } else {
         return var2;
      }
   }

   static ResourceLocation getLocationMcmeta(ResourceLocation var0) {
      return new ResourceLocation(var0.getResourceDomain(), String.valueOf((new StringBuilder(String.valueOf(var0.getResourcePath()))).append(".mcmeta")));
   }

   static Logger access$0() {
      return field_177246_b;
   }

   public Set getResourceDomains() {
      return null;
   }

   public void addResourcePack(IResourcePack var1) {
      this.resourcePacks.add(var1);
   }

   public FallbackResourceManager(IMetadataSerializer var1) {
      this.frmMetadataSerializer = var1;
   }

   static class ImputStreamLeakedResourceLogger extends InputStream {
      private final String field_177328_b;
      private final InputStream field_177330_a;
      private static final String __OBFID = "CL_00002395";
      private boolean field_177329_c = false;

      public ImputStreamLeakedResourceLogger(InputStream var1, ResourceLocation var2, String var3) {
         this.field_177330_a = var1;
         ByteArrayOutputStream var4 = new ByteArrayOutputStream();
         (new Exception()).printStackTrace(new PrintStream(var4));
         this.field_177328_b = String.valueOf((new StringBuilder("Leaked resource: '")).append(var2).append("' loaded from pack: '").append(var3).append("'\n").append(var4.toString()));
      }

      public int read() throws IOException {
         return this.field_177330_a.read();
      }

      protected void finalize() throws Throwable {
         if (!this.field_177329_c) {
            FallbackResourceManager.access$0().warn(this.field_177328_b);
         }

         super.finalize();
      }

      public void close() throws IOException {
         this.field_177330_a.close();
         this.field_177329_c = true;
      }
   }
}

package net.minecraft.client.resources;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager implements IReloadableResourceManager {
   private static final Joiner joinerResourcePacks = Joiner.on(", ");
   private final Set setResourceDomains = Sets.newLinkedHashSet();
   private final Map domainResourceManagers = Maps.newHashMap();
   private final List reloadListeners = Lists.newArrayList();
   private static final Logger logger = LogManager.getLogger();
   private static final String __OBFID = "CL_00001091";
   private final IMetadataSerializer rmMetadataSerializer;

   public SimpleReloadableResourceManager(IMetadataSerializer var1) {
      this.rmMetadataSerializer = var1;
   }

   public IResource getResource(ResourceLocation var1) throws IOException {
      IResourceManager var2 = (IResourceManager)this.domainResourceManagers.get(var1.getResourceDomain());
      if (var2 != null) {
         return var2.getResource(var1);
      } else {
         throw new FileNotFoundException(var1.toString());
      }
   }

   public Set getResourceDomains() {
      return this.setResourceDomains;
   }

   private void clearResources() {
      this.domainResourceManagers.clear();
      this.setResourceDomains.clear();
   }

   public void reloadResources(List var1) {
      this.clearResources();
      logger.info(String.valueOf((new StringBuilder("Reloading ResourceManager: ")).append(joinerResourcePacks.join(Iterables.transform(var1, new Function(this) {
         private static final String __OBFID = "CL_00001092";
         final SimpleReloadableResourceManager this$0;

         public String apply(IResourcePack var1) {
            return var1.getPackName();
         }

         public Object apply(Object var1) {
            return this.apply((IResourcePack)var1);
         }

         {
            this.this$0 = var1;
         }
      })))));
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         IResourcePack var3 = (IResourcePack)var2.next();
         this.reloadResourcePack(var3);
      }

      this.notifyReloadListeners();
   }

   public void registerReloadListener(IResourceManagerReloadListener var1) {
      this.reloadListeners.add(var1);
      var1.onResourceManagerReload(this);
   }

   public void reloadResourcePack(IResourcePack var1) {
      FallbackResourceManager var2;
      for(Iterator var3 = var1.getResourceDomains().iterator(); var3.hasNext(); var2.addResourcePack(var1)) {
         String var4 = (String)var3.next();
         this.setResourceDomains.add(var4);
         var2 = (FallbackResourceManager)this.domainResourceManagers.get(var4);
         if (var2 == null) {
            var2 = new FallbackResourceManager(this.rmMetadataSerializer);
            this.domainResourceManagers.put(var4, var2);
         }
      }

   }

   private void notifyReloadListeners() {
      Iterator var1 = this.reloadListeners.iterator();

      while(var1.hasNext()) {
         IResourceManagerReloadListener var2 = (IResourceManagerReloadListener)var1.next();
         var2.onResourceManagerReload(this);
      }

   }

   public List getAllResources(ResourceLocation var1) throws IOException {
      IResourceManager var2 = (IResourceManager)this.domainResourceManagers.get(var1.getResourceDomain());
      if (var2 != null) {
         return var2.getAllResources(var1);
      } else {
         throw new FileNotFoundException(var1.toString());
      }
   }
}

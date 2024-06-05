package net.minecraft.client.resources;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.apX;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager$1;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager implements IReloadableResourceManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Joiner joinerResourcePacks = Joiner.on(", ");
   private final Map domainResourceManagers = Maps.newHashMap();
   private final List reloadListeners = Lists.newArrayList();
   private final Set setResourceDomains = Sets.newLinkedHashSet();
   private final apX e;

   public SimpleReloadableResourceManager(apX var1) {
      this.e = var1;
   }

   public void reloadResourcePack(IResourcePack var1) {
      for(String var3 : var1.getResourceDomains()) {
         this.setResourceDomains.add(var3);
         FallbackResourceManager var4 = (FallbackResourceManager)this.domainResourceManagers.get(var3);
         var4 = new FallbackResourceManager(this.e);
         this.domainResourceManagers.put(var3, var4);
         var4.addResourcePack(var1);
      }

   }

   public Set getResourceDomains() {
      return this.setResourceDomains;
   }

   public IResource getResource(ResourceLocation var1) throws IOException {
      IResourceManager var2 = (IResourceManager)this.domainResourceManagers.get(var1.getResourceDomain());
      return var2.getResource(var1);
   }

   public List getAllResources(ResourceLocation var1) throws IOException {
      IResourceManager var2 = (IResourceManager)this.domainResourceManagers.get(var1.getResourceDomain());
      return var2.getAllResources(var1);
   }

   private void clearResources() {
      this.domainResourceManagers.clear();
      this.setResourceDomains.clear();
   }

   public void reloadResources(List var1) {
      this.clearResources();
      LOGGER.info("Reloading ResourceManager: " + joinerResourcePacks.join(Iterables.transform(var1, new SimpleReloadableResourceManager$1(this))));

      for(IResourcePack var3 : var1) {
         this.reloadResourcePack(var3);
      }

      this.notifyReloadListeners();
   }

   public void registerReloadListener(IResourceManagerReloadListener var1) {
      this.reloadListeners.add(var1);
      var1.onResourceManagerReload(this);
   }

   private void notifyReloadListeners() {
      for(IResourceManagerReloadListener var2 : this.reloadListeners) {
         var2.onResourceManagerReload(this);
      }

   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

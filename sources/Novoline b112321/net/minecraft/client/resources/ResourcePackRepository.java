package net.minecraft.client.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import net.apX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository$1;
import net.minecraft.client.resources.ResourcePackRepository$Entry;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackRepository {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final FileFilter resourcePackFilter = ResourcePackRepository::lambda$static$0;
   private final File dirResourcepacks;
   public final IResourcePack rprDefaultResourcePack;
   private final File dirServerResourcepacks;
   public final apX b;
   private IResourcePack resourcePackInstance;
   private final ReentrantLock lock = new ReentrantLock();
   private ListenableFuture field_177322_i;
   private List repositoryEntriesAll = Lists.newArrayList();
   private final List repositoryEntries = Lists.newArrayList();

   public ResourcePackRepository(File var1, File var2, IResourcePack var3, apX var4, GameSettings var5) {
      this.dirResourcepacks = var1;
      this.dirServerResourcepacks = var2;
      this.rprDefaultResourcePack = var3;
      this.b = var4;
      this.fixDirResourcepacks();
      this.updateRepositoryEntriesAll();
      Iterator var6 = var5.resourcePacks.iterator();

      while(var6.hasNext()) {
         String var7 = (String)var6.next();

         for(ResourcePackRepository$Entry var9 : this.repositoryEntriesAll) {
            if(var9.getResourcePackName().equals(var7)) {
               if(var9.func_183027_f() == 1 || var5.field_183018_l.contains(var9.getResourcePackName())) {
                  this.repositoryEntries.add(var9);
                  break;
               }

               var6.remove();
               LOGGER.warn("Removed selected resource pack {} because it\'s no longer compatible", new Object[]{var9.getResourcePackName()});
            }
         }
      }

   }

   private void fixDirResourcepacks() {
      if(this.dirResourcepacks.exists()) {
         if(!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs())) {
            LOGGER.warn("Unable to recreate resourcepack folder, it exists but is not a directory: " + this.dirResourcepacks);
         }
      } else if(!this.dirResourcepacks.mkdirs()) {
         LOGGER.warn("Unable to create resourcepack folder: " + this.dirResourcepacks);
      }

   }

   private List getResourcePackFiles() {
      return this.dirResourcepacks.isDirectory()?Arrays.asList(this.dirResourcepacks.listFiles(resourcePackFilter)):Collections.emptyList();
   }

   public void updateRepositoryEntriesAll() {
      ArrayList var1 = Lists.newArrayList();

      for(File var3 : this.getResourcePackFiles()) {
         ResourcePackRepository$Entry var4 = new ResourcePackRepository$Entry(this, var3, (ResourcePackRepository$1)null);
         if(!this.repositoryEntriesAll.contains(var4)) {
            ResourcePackRepository$Entry var10000 = var4;

            try {
               var10000.updateResourcePack();
               var1.add(var4);
            } catch (Exception var6) {
               var1.remove(var4);
            }
         } else {
            int var5 = this.repositoryEntriesAll.indexOf(var4);
            if(var5 > -1 && var5 < this.repositoryEntriesAll.size()) {
               var1.add(this.repositoryEntriesAll.get(var5));
            }
         }
      }

      this.repositoryEntriesAll.removeAll(var1);

      for(ResourcePackRepository$Entry var8 : this.repositoryEntriesAll) {
         var8.closeResourcePack();
      }

      this.repositoryEntriesAll = var1;
   }

   public List getRepositoryEntriesAll() {
      return ImmutableList.copyOf(this.repositoryEntriesAll);
   }

   public List getRepositoryEntries() {
      return ImmutableList.copyOf(this.repositoryEntries);
   }

   public void setRepositories(List var1) {
      this.repositoryEntries.clear();
      this.repositoryEntries.addAll(var1);
   }

   public File getDirResourcepacks() {
      return this.dirResourcepacks;
   }

   public ListenableFuture downloadResourcePack(String var1, String var2) {
      String var3;
      if(var2.matches("^[a-f0-9]{40}$")) {
         var3 = var2;
      } else {
         var3 = "legacy";
      }

      File var4 = new File(this.dirServerResourcepacks, var3);
      this.lock.lock();
      ResourcePackRepository var10000 = this;

      try {
         var10000.func_148529_f();
         if(var4.exists() && var2.length() == 40) {
            try {
               String var5 = Hashing.sha1().hashBytes(Files.toByteArray(var4)).toString();
               if(var5.equals(var2)) {
                  ListenableFuture var16 = this.setResourcePackInstance(var4);
                  return var16;
               }

               LOGGER.warn("File " + var4 + " had wrong hash (expected " + var2 + ", found " + var5 + "). Deleting it.");
               FileUtils.deleteQuietly(var4);
            } catch (IOException var13) {
               LOGGER.warn("File " + var4 + " couldn\'t be hashed. Deleting it.", var13);
               FileUtils.deleteQuietly(var4);
            }
         }

         this.func_183028_i();
         GuiScreenWorking var15 = new GuiScreenWorking();
         Map var6 = Minecraft.getSessionInfo();
         Minecraft var7 = Minecraft.getInstance();
         Futures.getUnchecked(var7.addScheduledTask(ResourcePackRepository::lambda$downloadResourcePack$1));
         SettableFuture var8 = SettableFuture.create();
         this.field_177322_i = HttpUtil.downloadResourcePack(var4, var1, var6, 52428800, var15, var7.getProxy());
         Futures.addCallback(this.field_177322_i, new ResourcePackRepository$1(this, var4, var8));
         ListenableFuture var9 = this.field_177322_i;
         return var9;
      } finally {
         this.lock.unlock();
      }
   }

   private void func_183028_i() {
      ArrayList var1 = Lists.newArrayList(FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, (IOFileFilter)null));
      var1.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
      int var2 = 0;

      for(File var4 : var1) {
         if(var2++ >= 10) {
            LOGGER.info("Deleting old server resource pack " + var4.getName());
            FileUtils.deleteQuietly(var4);
         }
      }

   }

   public ListenableFuture setResourcePackInstance(File var1) {
      this.resourcePackInstance = new FileResourcePack(var1);
      return Minecraft.getInstance().scheduleResourcesRefresh();
   }

   public IResourcePack getResourcePackInstance() {
      return this.resourcePackInstance;
   }

   public void func_148529_f() {
      this.lock.lock();
      ResourcePackRepository var10000 = this;

      try {
         if(var10000.field_177322_i != null) {
            this.field_177322_i.cancel(true);
         }

         this.field_177322_i = null;
         if(this.resourcePackInstance != null) {
            this.resourcePackInstance = null;
            Minecraft.getInstance().scheduleResourcesRefresh();
         }
      } finally {
         this.lock.unlock();
      }

   }

   private static void lambda$downloadResourcePack$1(Minecraft var0, GuiScreenWorking var1) {
      var0.displayGuiScreen(var1);
   }

   private static boolean lambda$static$0(File var0) {
      return var0.isFile() && var0.getName().endsWith(".zip") || var0.isDirectory() && (new File(var0, "pack.mcmeta")).isFile();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}

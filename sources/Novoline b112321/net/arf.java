package net;

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
import net.VM;
import net.apX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository$3;
import net.minecraft.client.resources.ResourcePackRepository$Entry;
import net.minecraft.client.settings.GameSettings;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class arf {
   private static final Logger j = LogManager.getLogger();
   private static final FileFilter a = arf::lambda$static$0;
   private final File f;
   public final IResourcePack g;
   private final File d;
   public final apX b;
   private IResourcePack c;
   private final ReentrantLock e = new ReentrantLock();
   private ListenableFuture k;
   private List i = Lists.newArrayList();
   private final List h = Lists.newArrayList();

   public arf(File var1, File var2, IResourcePack var3, apX var4, GameSettings var5) {
      this.f = var1;
      this.d = var2;
      this.g = var3;
      this.b = var4;
      this.h();
      this.i();
      Iterator var6 = var5.t.iterator();

      while(var6.hasNext()) {
         String var7 = (String)var6.next();

         for(ResourcePackRepository$Entry var9 : this.i) {
            if(var9.getResourcePackName().equals(var7)) {
               if(var9.func_183027_f() == 1 || var5.E.contains(var9.getResourcePackName())) {
                  this.h.add(var9);
                  break;
               }

               var6.remove();
               j.warn("Removed selected resource pack {} because it\'s no longer compatible", new Object[]{var9.getResourcePackName()});
            }
         }
      }

   }

   private void h() {
      if(this.f.exists()) {
         if(!this.f.isDirectory() && (!this.f.delete() || !this.f.mkdirs())) {
            j.warn("Unable to recreate resourcepack folder, it exists but is not a directory: " + this.f);
         }
      } else if(!this.f.mkdirs()) {
         j.warn("Unable to create resourcepack folder: " + this.f);
      }

   }

   private List c() {
      return this.f.isDirectory()?Arrays.asList(this.f.listFiles(a)):Collections.emptyList();
   }

   public void i() {
      ArrayList var1 = Lists.newArrayList();

      for(File var3 : this.c()) {
         ResourcePackRepository$Entry var4 = new ResourcePackRepository$Entry(this, var3, (ResourcePackRepository$3)null);
         if(!this.i.contains(var4)) {
            ResourcePackRepository$Entry var10000 = var4;

            try {
               var10000.updateResourcePack();
               var1.add(var4);
            } catch (Exception var6) {
               var1.remove(var4);
            }
         } else {
            int var5 = this.i.indexOf(var4);
            if(var5 > -1 && var5 < this.i.size()) {
               var1.add(this.i.get(var5));
            }
         }
      }

      this.i.removeAll(var1);

      for(ResourcePackRepository$Entry var8 : this.i) {
         var8.closeResourcePack();
      }

      this.i = var1;
   }

   public List d() {
      return ImmutableList.copyOf(this.i);
   }

   public List f() {
      return ImmutableList.copyOf(this.h);
   }

   public void a(List var1) {
      this.h.clear();
      this.h.addAll(var1);
   }

   public File b() {
      return this.f;
   }

   public ListenableFuture a(String var1, String var2) {
      String var3;
      if(var2.matches("^[a-f0-9]{40}$")) {
         var3 = var2;
      } else {
         var3 = "legacy";
      }

      File var4 = new File(this.d, var3);
      this.e.lock();
      arf var10000 = this;

      try {
         var10000.g();
         if(var4.exists() && var2.length() == 40) {
            try {
               String var5 = Hashing.sha1().hashBytes(Files.toByteArray(var4)).toString();
               if(var5.equals(var2)) {
                  ListenableFuture var16 = this.a(var4);
                  return var16;
               }

               j.warn("File " + var4 + " had wrong hash (expected " + var2 + ", found " + var5 + "). Deleting it.");
               FileUtils.deleteQuietly(var4);
            } catch (IOException var13) {
               j.warn("File " + var4 + " couldn\'t be hashed. Deleting it.", var13);
               FileUtils.deleteQuietly(var4);
            }
         }

         this.e();
         GuiScreenWorking var15 = new GuiScreenWorking();
         Map var6 = Minecraft.getSessionInfo();
         Minecraft var7 = Minecraft.getMinecraft();
         Futures.getUnchecked(var7.addScheduledTask(arf::lambda$downloadResourcePack$1));
         SettableFuture var8 = SettableFuture.create();
         this.k = VM.a(var4, var1, var6, 52428800, var15, var7.getProxy());
         Futures.addCallback(this.k, new ResourcePackRepository$3(this, var4, var8));
         ListenableFuture var9 = this.k;
         return var9;
      } finally {
         this.e.unlock();
      }
   }

   private void e() {
      ArrayList var1 = Lists.newArrayList(FileUtils.listFiles(this.d, TrueFileFilter.TRUE, (IOFileFilter)null));
      var1.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
      int var2 = 0;

      for(File var4 : var1) {
         if(var2++ >= 10) {
            j.info("Deleting old server resource pack " + var4.getName());
            FileUtils.deleteQuietly(var4);
         }
      }

   }

   public ListenableFuture a(File var1) {
      this.c = new FileResourcePack(var1);
      return Minecraft.getMinecraft().scheduleResourcesRefresh();
   }

   public IResourcePack a() {
      return this.c;
   }

   public void g() {
      this.e.lock();
      arf var10000 = this;

      try {
         if(var10000.k != null) {
            this.k.cancel(true);
         }

         this.k = null;
         if(this.c != null) {
            this.c = null;
            Minecraft.getMinecraft().scheduleResourcesRefresh();
         }
      } finally {
         this.e.unlock();
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

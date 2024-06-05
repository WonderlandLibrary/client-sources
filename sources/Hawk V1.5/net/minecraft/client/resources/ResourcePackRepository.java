package net.minecraft.client.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.awt.image.BufferedImage;
import java.io.Closeable;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackRepository {
   private final File dirResourcepacks;
   public final IMetadataSerializer rprMetadataSerializer;
   private ListenableFuture field_177322_i;
   private final File field_148534_e;
   private static final FileFilter resourcePackFilter = new FileFilter() {
      private static final String __OBFID = "CL_00001088";

      public boolean accept(File var1) {
         boolean var2 = var1.isFile() && var1.getName().endsWith(".zip");
         boolean var3 = var1.isDirectory() && (new File(var1, "pack.mcmeta")).isFile();
         return var2 || var3;
      }
   };
   private final ReentrantLock field_177321_h = new ReentrantLock();
   private List repositoryEntries = Lists.newArrayList();
   private static final Logger field_177320_c = LogManager.getLogger();
   private static final String __OBFID = "CL_00001087";
   public final IResourcePack rprDefaultResourcePack;
   private IResourcePack field_148532_f;
   private List repositoryEntriesAll = Lists.newArrayList();

   public File getDirResourcepacks() {
      return this.dirResourcepacks;
   }

   public ListenableFuture func_180601_a(String var1, String var2) {
      String var3;
      if (var2.matches("^[a-f0-9]{40}$")) {
         var3 = var2;
      } else {
         var3 = var1.substring(var1.lastIndexOf("/") + 1);
         if (var3.contains("?")) {
            var3 = var3.substring(0, var3.indexOf("?"));
         }

         if (!var3.endsWith(".zip")) {
            return Futures.immediateFailedFuture(new IllegalArgumentException("Invalid filename; must end in .zip"));
         }

         var3 = String.valueOf((new StringBuilder("legacy_")).append(var3.replaceAll("\\W", "")));
      }

      File var4 = new File(this.field_148534_e, var3);
      this.field_177321_h.lock();

      ListenableFuture var11;
      try {
         this.func_148529_f();
         if (var4.exists() && var2.length() == 40) {
            try {
               String var5 = Hashing.sha1().hashBytes(Files.toByteArray(var4)).toString();
               if (var5.equals(var2)) {
                  ListenableFuture var17 = this.func_177319_a(var4);
                  var11 = var17;
                  return var11;
               }

               field_177320_c.warn(String.valueOf((new StringBuilder("File ")).append(var4).append(" had wrong hash (expected ").append(var2).append(", found ").append(var5).append("). Deleting it.")));
               FileUtils.deleteQuietly(var4);
            } catch (IOException var14) {
               field_177320_c.warn(String.valueOf((new StringBuilder("File ")).append(var4).append(" couldn't be hashed. Deleting it.")), var14);
               FileUtils.deleteQuietly(var4);
            }
         }

         GuiScreenWorking var16 = new GuiScreenWorking();
         Map var6 = Minecraft.func_175596_ai();
         Minecraft var7 = Minecraft.getMinecraft();
         Futures.getUnchecked(var7.addScheduledTask(new Runnable(this, var7, var16) {
            private static final String __OBFID = "CL_00001089";
            private final GuiScreenWorking val$var15;
            final ResourcePackRepository this$0;
            private final Minecraft val$var7;

            public void run() {
               this.val$var7.displayGuiScreen(this.val$var15);
            }

            {
               this.this$0 = var1;
               this.val$var7 = var2;
               this.val$var15 = var3;
            }
         }));
         SettableFuture var8 = SettableFuture.create();
         this.field_177322_i = HttpUtil.func_180192_a(var4, var1, var6, 52428800, var16, var7.getProxy());
         Futures.addCallback(this.field_177322_i, new FutureCallback(this, var4, var8) {
            private static final String __OBFID = "CL_00002394";
            private final File val$var4;
            final ResourcePackRepository this$0;
            private final SettableFuture val$var8;

            {
               this.this$0 = var1;
               this.val$var4 = var2;
               this.val$var8 = var3;
            }

            public void onSuccess(Object var1) {
               this.this$0.func_177319_a(this.val$var4);
               this.val$var8.set((Object)null);
            }

            public void onFailure(Throwable var1) {
               this.val$var8.setException(var1);
            }
         });
         ListenableFuture var9 = this.field_177322_i;
         var11 = var9;
      } finally {
         this.field_177321_h.unlock();
      }

      return var11;
   }

   private void fixDirResourcepacks() {
      if (!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs())) {
         field_177320_c.debug(String.valueOf((new StringBuilder("Unable to create resourcepack folder: ")).append(this.dirResourcepacks)));
      }

   }

   public List getRepositoryEntries() {
      return ImmutableList.copyOf(this.repositoryEntries);
   }

   public IResourcePack getResourcePackInstance() {
      return this.field_148532_f;
   }

   public void updateRepositoryEntriesAll() {
      ArrayList var1 = Lists.newArrayList();
      Iterator var2 = this.getResourcePackFiles().iterator();

      while(var2.hasNext()) {
         File var3 = (File)var2.next();
         ResourcePackRepository.Entry var4 = new ResourcePackRepository.Entry(this, var3, (Object)null);
         if (!this.repositoryEntriesAll.contains(var4)) {
            try {
               var4.updateResourcePack();
               var1.add(var4);
            } catch (Exception var6) {
               var1.remove(var4);
            }
         } else {
            int var5 = this.repositoryEntriesAll.indexOf(var4);
            if (var5 > -1 && var5 < this.repositoryEntriesAll.size()) {
               var1.add(this.repositoryEntriesAll.get(var5));
            }
         }
      }

      this.repositoryEntriesAll.removeAll(var1);
      var2 = this.repositoryEntriesAll.iterator();

      while(var2.hasNext()) {
         ResourcePackRepository.Entry var7 = (ResourcePackRepository.Entry)var2.next();
         var7.closeResourcePack();
      }

      this.repositoryEntriesAll = var1;
   }

   public ListenableFuture func_177319_a(File var1) {
      this.field_148532_f = new FileResourcePack(var1);
      return Minecraft.getMinecraft().func_175603_A();
   }

   public void func_148527_a(List var1) {
      this.repositoryEntries.clear();
      this.repositoryEntries.addAll(var1);
   }

   public void func_148529_f() {
      this.field_177321_h.lock();

      try {
         if (this.field_177322_i != null) {
            this.field_177322_i.cancel(true);
         }

         this.field_177322_i = null;
         this.field_148532_f = null;
         this.field_177321_h.unlock();
      } finally {
         this.field_177321_h.unlock();
      }
   }

   public List getRepositoryEntriesAll() {
      return ImmutableList.copyOf(this.repositoryEntriesAll);
   }

   private List getResourcePackFiles() {
      return this.dirResourcepacks.isDirectory() ? Arrays.asList(this.dirResourcepacks.listFiles(resourcePackFilter)) : Collections.emptyList();
   }

   public ResourcePackRepository(File var1, File var2, IResourcePack var3, IMetadataSerializer var4, GameSettings var5) {
      this.dirResourcepacks = var1;
      this.field_148534_e = var2;
      this.rprDefaultResourcePack = var3;
      this.rprMetadataSerializer = var4;
      this.fixDirResourcepacks();
      this.updateRepositoryEntriesAll();
      Iterator var6 = var5.resourcePacks.iterator();

      while(true) {
         while(var6.hasNext()) {
            String var7 = (String)var6.next();
            Iterator var8 = this.repositoryEntriesAll.iterator();

            while(var8.hasNext()) {
               ResourcePackRepository.Entry var9 = (ResourcePackRepository.Entry)var8.next();
               if (var9.getResourcePackName().equals(var7)) {
                  this.repositoryEntries.add(var9);
                  break;
               }
            }
         }

         return;
      }
   }

   public class Entry {
      private BufferedImage texturePackIcon;
      private final File resourcePackFile;
      private ResourceLocation locationTexturePackIcon;
      final ResourcePackRepository this$0;
      private static final String __OBFID = "CL_00001090";
      private IResourcePack reResourcePack;
      private PackMetadataSection rePackMetadataSection;

      public void updateResourcePack() throws IOException {
         this.reResourcePack = (IResourcePack)(this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile));
         this.rePackMetadataSection = (PackMetadataSection)this.reResourcePack.getPackMetadata(this.this$0.rprMetadataSerializer, "pack");

         try {
            this.texturePackIcon = this.reResourcePack.getPackImage();
         } catch (IOException var2) {
         }

         if (this.texturePackIcon == null) {
            this.texturePackIcon = this.this$0.rprDefaultResourcePack.getPackImage();
         }

         this.closeResourcePack();
      }

      public boolean equals(Object var1) {
         return this == var1 ? true : (var1 instanceof ResourcePackRepository.Entry ? this.toString().equals(var1.toString()) : false);
      }

      public String toString() {
         return String.format("%s:%s:%d", this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", this.resourcePackFile.lastModified());
      }

      public String getTexturePackDescription() {
         return this.rePackMetadataSection == null ? String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append("Invalid pack.mcmeta (or missing 'pack' section)")) : this.rePackMetadataSection.func_152805_a().getFormattedText();
      }

      private Entry(ResourcePackRepository var1, File var2) {
         this.this$0 = var1;
         this.resourcePackFile = var2;
      }

      Entry(ResourcePackRepository var1, File var2, Object var3) {
         this(var1, var2);
      }

      public void bindTexturePackIcon(TextureManager var1) {
         if (this.locationTexturePackIcon == null) {
            this.locationTexturePackIcon = var1.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
         }

         var1.bindTexture(this.locationTexturePackIcon);
      }

      public String getResourcePackName() {
         return this.reResourcePack.getPackName();
      }

      public IResourcePack getResourcePack() {
         return this.reResourcePack;
      }

      public void closeResourcePack() {
         if (this.reResourcePack instanceof Closeable) {
            IOUtils.closeQuietly((Closeable)this.reResourcePack);
         }

      }

      public int hashCode() {
         return this.toString().hashCode();
      }
   }
}

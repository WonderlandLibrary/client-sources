/*     */ package net.minecraft.client.resources;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.SettableFuture;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenWorking;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*     */ import net.minecraft.client.resources.data.PackMetadataSection;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.io.comparator.LastModifiedFileComparator;
/*     */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ResourcePackRepository {
/*  42 */   private static final Logger logger = LogManager.getLogger();
/*  43 */   private static final FileFilter resourcePackFilter = new FileFilter()
/*     */     {
/*     */       public boolean accept(File p_accept_1_)
/*     */       {
/*  47 */         boolean flag = (p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip"));
/*  48 */         boolean flag1 = (p_accept_1_.isDirectory() && (new File(p_accept_1_, "pack.mcmeta")).isFile());
/*  49 */         return !(!flag && !flag1);
/*     */       }
/*     */     };
/*     */   private final File dirResourcepacks;
/*     */   public final IResourcePack rprDefaultResourcePack;
/*     */   private final File dirServerResourcepacks;
/*     */   public final IMetadataSerializer rprMetadataSerializer;
/*     */   private IResourcePack resourcePackInstance;
/*  57 */   private final ReentrantLock lock = new ReentrantLock();
/*     */   private ListenableFuture<Object> field_177322_i;
/*  59 */   private List<Entry> repositoryEntriesAll = Lists.newArrayList();
/*  60 */   private List<Entry> repositoryEntries = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public ResourcePackRepository(File dirResourcepacksIn, File dirServerResourcepacksIn, IResourcePack rprDefaultResourcePackIn, IMetadataSerializer rprMetadataSerializerIn, GameSettings settings) {
/*  64 */     this.dirResourcepacks = dirResourcepacksIn;
/*  65 */     this.dirServerResourcepacks = dirServerResourcepacksIn;
/*  66 */     this.rprDefaultResourcePack = rprDefaultResourcePackIn;
/*  67 */     this.rprMetadataSerializer = rprMetadataSerializerIn;
/*  68 */     fixDirResourcepacks();
/*  69 */     updateRepositoryEntriesAll();
/*  70 */     Iterator<String> iterator = settings.resourcePacks.iterator();
/*     */     
/*  72 */     while (iterator.hasNext()) {
/*     */       
/*  74 */       String s = iterator.next();
/*     */       
/*  76 */       for (Entry resourcepackrepository$entry : this.repositoryEntriesAll) {
/*     */         
/*  78 */         if (resourcepackrepository$entry.getResourcePackName().equals(s)) {
/*     */           
/*  80 */           if (resourcepackrepository$entry.func_183027_f() == 1 || settings.field_183018_l.contains(resourcepackrepository$entry.getResourcePackName())) {
/*     */             
/*  82 */             this.repositoryEntries.add(resourcepackrepository$entry);
/*     */             
/*     */             break;
/*     */           } 
/*  86 */           iterator.remove();
/*  87 */           logger.warn("Removed selected resource pack {} because it's no longer compatible", new Object[] { resourcepackrepository$entry.getResourcePackName() });
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixDirResourcepacks() {
/*  95 */     if (this.dirResourcepacks.exists()) {
/*     */       
/*  97 */       if (!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs()))
/*     */       {
/*  99 */         logger.warn("Unable to recreate resourcepack folder, it exists but is not a directory: " + this.dirResourcepacks);
/*     */       }
/*     */     }
/* 102 */     else if (!this.dirResourcepacks.mkdirs()) {
/*     */       
/* 104 */       logger.warn("Unable to create resourcepack folder: " + this.dirResourcepacks);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<File> getResourcePackFiles() {
/* 110 */     return this.dirResourcepacks.isDirectory() ? Arrays.<File>asList(this.dirResourcepacks.listFiles(resourcePackFilter)) : Collections.<File>emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRepositoryEntriesAll() {
/* 115 */     List<Entry> list = Lists.newArrayList();
/*     */     
/* 117 */     for (File file1 : getResourcePackFiles()) {
/*     */       
/* 119 */       Entry resourcepackrepository$entry = new Entry(file1, null);
/*     */       
/* 121 */       if (!this.repositoryEntriesAll.contains(resourcepackrepository$entry)) {
/*     */ 
/*     */         
/*     */         try {
/* 125 */           resourcepackrepository$entry.updateResourcePack();
/* 126 */           list.add(resourcepackrepository$entry);
/*     */         }
/* 128 */         catch (Exception var6) {
/*     */           
/* 130 */           list.remove(resourcepackrepository$entry);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 135 */       int i = this.repositoryEntriesAll.indexOf(resourcepackrepository$entry);
/*     */       
/* 137 */       if (i > -1 && i < this.repositoryEntriesAll.size())
/*     */       {
/* 139 */         list.add(this.repositoryEntriesAll.get(i));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 144 */     this.repositoryEntriesAll.removeAll(list);
/*     */     
/* 146 */     for (Entry resourcepackrepository$entry1 : this.repositoryEntriesAll)
/*     */     {
/* 148 */       resourcepackrepository$entry1.closeResourcePack();
/*     */     }
/*     */     
/* 151 */     this.repositoryEntriesAll = list;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Entry> getRepositoryEntriesAll() {
/* 156 */     return (List<Entry>)ImmutableList.copyOf(this.repositoryEntriesAll);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Entry> getRepositoryEntries() {
/* 161 */     return (List<Entry>)ImmutableList.copyOf(this.repositoryEntries);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRepositories(List<Entry> p_148527_1_) {
/* 166 */     this.repositoryEntries.clear();
/* 167 */     this.repositoryEntries.addAll(p_148527_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDirResourcepacks() {
/* 172 */     return this.dirResourcepacks;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> downloadResourcePack(String url, String hash) {
/*     */     String s;
/* 179 */     if (hash.matches("^[a-f0-9]{40}$")) {
/*     */       
/* 181 */       s = hash;
/*     */     }
/*     */     else {
/*     */       
/* 185 */       s = "legacy";
/*     */     } 
/*     */     
/* 188 */     final File file1 = new File(this.dirServerResourcepacks, s);
/* 189 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 193 */       func_148529_f();
/*     */       
/* 195 */       if (file1.exists() && hash.length() == 40) {
/*     */         
/*     */         try {
/*     */           
/* 199 */           String s1 = Hashing.sha1().hashBytes(Files.toByteArray(file1)).toString();
/*     */           
/* 201 */           if (s1.equals(hash)) {
/*     */             
/* 203 */             ListenableFuture<Object> listenablefuture1 = setResourcePackInstance(file1);
/* 204 */             return listenablefuture1;
/*     */           } 
/*     */           
/* 207 */           logger.warn("File " + file1 + " had wrong hash (expected " + hash + ", found " + s1 + "). Deleting it.");
/* 208 */           FileUtils.deleteQuietly(file1);
/*     */         }
/* 210 */         catch (IOException ioexception) {
/*     */           
/* 212 */           logger.warn("File " + file1 + " couldn't be hashed. Deleting it.", ioexception);
/* 213 */           FileUtils.deleteQuietly(file1);
/*     */         } 
/*     */       }
/*     */       
/* 217 */       func_183028_i();
/* 218 */       final GuiScreenWorking guiscreenworking = new GuiScreenWorking();
/* 219 */       Map<String, String> map = Minecraft.getSessionInfo();
/* 220 */       final Minecraft minecraft = Minecraft.getMinecraft();
/* 221 */       Futures.getUnchecked((Future)minecraft.addScheduledTask(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 225 */                 minecraft.displayGuiScreen((GuiScreen)guiscreenworking);
/*     */               }
/*     */             }));
/* 228 */       final SettableFuture<Object> settablefuture = SettableFuture.create();
/* 229 */       this.field_177322_i = HttpUtil.downloadResourcePack(file1, url, map, 52428800, (IProgressUpdate)guiscreenworking, minecraft.getProxy());
/* 230 */       Futures.addCallback(this.field_177322_i, new FutureCallback<Object>()
/*     */           {
/*     */             public void onSuccess(Object p_onSuccess_1_)
/*     */             {
/* 234 */               ResourcePackRepository.this.setResourcePackInstance(file1);
/* 235 */               settablefuture.set(null);
/*     */             }
/*     */             
/*     */             public void onFailure(Throwable p_onFailure_1_) {
/* 239 */               settablefuture.setException(p_onFailure_1_);
/*     */             }
/*     */           });
/* 242 */       ListenableFuture<Object> listenablefuture = this.field_177322_i;
/* 243 */       return listenablefuture;
/*     */     }
/*     */     finally {
/*     */       
/* 247 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_183028_i() {
/* 253 */     List<File> list = Lists.newArrayList(FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, null));
/* 254 */     Collections.sort(list, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
/* 255 */     int i = 0;
/*     */     
/* 257 */     for (File file1 : list) {
/*     */       
/* 259 */       if (i++ >= 10) {
/*     */         
/* 261 */         logger.info("Deleting old server resource pack " + file1.getName());
/* 262 */         FileUtils.deleteQuietly(file1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> setResourcePackInstance(File p_177319_1_) {
/* 269 */     this.resourcePackInstance = new FileResourcePack(p_177319_1_);
/* 270 */     return Minecraft.getMinecraft().scheduleResourcesRefresh();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IResourcePack getResourcePackInstance() {
/* 278 */     return this.resourcePackInstance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_148529_f() {
/* 283 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 287 */       if (this.field_177322_i != null)
/*     */       {
/* 289 */         this.field_177322_i.cancel(true);
/*     */       }
/*     */       
/* 292 */       this.field_177322_i = null;
/*     */       
/* 294 */       if (this.resourcePackInstance != null)
/*     */       {
/* 296 */         this.resourcePackInstance = null;
/* 297 */         Minecraft.getMinecraft().scheduleResourcesRefresh();
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 302 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public class Entry
/*     */   {
/*     */     private final File resourcePackFile;
/*     */     private IResourcePack reResourcePack;
/*     */     private PackMetadataSection rePackMetadataSection;
/*     */     private BufferedImage texturePackIcon;
/*     */     private ResourceLocation locationTexturePackIcon;
/*     */     
/*     */     private Entry(File resourcePackFileIn) {
/* 316 */       this.resourcePackFile = resourcePackFileIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateResourcePack() throws IOException {
/* 321 */       this.reResourcePack = this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile);
/* 322 */       this.rePackMetadataSection = this.reResourcePack.<PackMetadataSection>getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack");
/*     */ 
/*     */       
/*     */       try {
/* 326 */         this.texturePackIcon = this.reResourcePack.getPackImage();
/*     */       }
/* 328 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 333 */       if (this.texturePackIcon == null)
/*     */       {
/* 335 */         this.texturePackIcon = ResourcePackRepository.this.rprDefaultResourcePack.getPackImage();
/*     */       }
/*     */       
/* 338 */       closeResourcePack();
/*     */     }
/*     */ 
/*     */     
/*     */     public void bindTexturePackIcon(TextureManager textureManagerIn) {
/* 343 */       if (this.locationTexturePackIcon == null)
/*     */       {
/* 345 */         this.locationTexturePackIcon = textureManagerIn.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
/*     */       }
/*     */       
/* 348 */       textureManagerIn.bindTexture(this.locationTexturePackIcon);
/*     */     }
/*     */ 
/*     */     
/*     */     public void closeResourcePack() {
/* 353 */       if (this.reResourcePack instanceof Closeable)
/*     */       {
/* 355 */         IOUtils.closeQuietly((Closeable)this.reResourcePack);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public IResourcePack getResourcePack() {
/* 361 */       return this.reResourcePack;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getResourcePackName() {
/* 366 */       return this.reResourcePack.getPackName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTexturePackDescription() {
/* 371 */       return (this.rePackMetadataSection == null) ? (EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)") : this.rePackMetadataSection.getPackDescription().getFormattedText();
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_183027_f() {
/* 376 */       return this.rePackMetadataSection.getPackFormat();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 381 */       return (this == p_equals_1_) ? true : ((p_equals_1_ instanceof Entry) ? toString().equals(p_equals_1_.toString()) : false);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 386 */       return toString().hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 391 */       return String.format("%s:%s:%d", new Object[] { this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", Long.valueOf(this.resourcePackFile.lastModified()) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\ResourcePackRepository.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
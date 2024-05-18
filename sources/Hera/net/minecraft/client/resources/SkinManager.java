/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.InsecureTextureException;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.IImageBuffer;
/*     */ import net.minecraft.client.renderer.ImageBufferDownload;
/*     */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class SkinManager
/*     */ {
/*  29 */   private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
/*     */   
/*     */   private final TextureManager textureManager;
/*     */   private final File skinCacheDir;
/*     */   private final MinecraftSessionService sessionService;
/*     */   private final LoadingCache<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>> skinCacheLoader;
/*     */   
/*     */   public SkinManager(TextureManager textureManagerInstance, File skinCacheDirectory, MinecraftSessionService sessionService) {
/*  37 */     this.textureManager = textureManagerInstance;
/*  38 */     this.skinCacheDir = skinCacheDirectory;
/*  39 */     this.sessionService = sessionService;
/*  40 */     this.skinCacheLoader = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>>()
/*     */         {
/*     */           public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> load(GameProfile p_load_1_) throws Exception
/*     */           {
/*  44 */             return Minecraft.getMinecraft().getSessionService().getTextures(p_load_1_, false);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation loadSkin(MinecraftProfileTexture profileTexture, MinecraftProfileTexture.Type p_152792_2_) {
/*  54 */     return loadSkin(profileTexture, p_152792_2_, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation loadSkin(final MinecraftProfileTexture profileTexture, final MinecraftProfileTexture.Type p_152789_2_, final SkinAvailableCallback skinAvailableCallback) {
/*  62 */     final ResourceLocation resourcelocation = new ResourceLocation("skins/" + profileTexture.getHash());
/*     */     
/*  64 */     ITextureObject itextureobject = this.textureManager.getTexture(resourcelocation);
/*     */     
/*  66 */     if (itextureobject != null) {
/*     */       
/*  68 */       if (skinAvailableCallback != null)
/*     */       {
/*  70 */         skinAvailableCallback.skinAvailable(p_152789_2_, resourcelocation, profileTexture);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  75 */       File file1 = new File(this.skinCacheDir, (profileTexture.getHash().length() > 2) ? profileTexture.getHash().substring(0, 2) : "xx");
/*  76 */       File file2 = new File(file1, profileTexture.getHash());
/*  77 */       final ImageBufferDownload iimagebuffer = (p_152789_2_ == MinecraftProfileTexture.Type.SKIN) ? new ImageBufferDownload() : null;
/*  78 */       ThreadDownloadImageData threaddownloadimagedata = new ThreadDownloadImageData(file2, profileTexture.getUrl(), DefaultPlayerSkin.getDefaultSkinLegacy(), new IImageBuffer()
/*     */           {
/*     */             public BufferedImage parseUserSkin(BufferedImage image)
/*     */             {
/*  82 */               if (iimagebuffer != null)
/*     */               {
/*  84 */                 image = iimagebuffer.parseUserSkin(image);
/*     */               }
/*     */               
/*  87 */               return image;
/*     */             }
/*     */             
/*     */             public void skinAvailable() {
/*  91 */               if (iimagebuffer != null)
/*     */               {
/*  93 */                 iimagebuffer.skinAvailable();
/*     */               }
/*     */               
/*  96 */               if (skinAvailableCallback != null)
/*     */               {
/*  98 */                 skinAvailableCallback.skinAvailable(p_152789_2_, resourcelocation, profileTexture);
/*     */               }
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 104 */       this.textureManager.loadTexture(resourcelocation, (ITextureObject)threaddownloadimagedata);
/*     */     } 
/*     */     
/* 107 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadProfileTextures(final GameProfile profile, final SkinAvailableCallback skinAvailableCallback, final boolean requireSecure) {
/* 112 */     THREAD_POOL.submit(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 116 */             final Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = Maps.newHashMap();
/*     */ 
/*     */             
/*     */             try {
/* 120 */               map.putAll(SkinManager.this.sessionService.getTextures(profile, requireSecure));
/*     */             }
/* 122 */             catch (InsecureTextureException insecureTextureException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 127 */             if (map.isEmpty() && profile.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
/*     */               
/* 129 */               profile.getProperties().clear();
/* 130 */               profile.getProperties().putAll((Multimap)Minecraft.getMinecraft().func_181037_M());
/* 131 */               map.putAll(SkinManager.this.sessionService.getTextures(profile, false));
/*     */             } 
/*     */             
/* 134 */             Minecraft.getMinecraft().addScheduledTask(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 138 */                     if (map.containsKey(MinecraftProfileTexture.Type.SKIN))
/*     */                     {
/* 140 */                       SkinManager.null.access$0(SkinManager.null.this).loadSkin((MinecraftProfileTexture)map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, skinAvailableCallback);
/*     */                     }
/*     */                     
/* 143 */                     if (map.containsKey(MinecraftProfileTexture.Type.CAPE))
/*     */                     {
/* 145 */                       SkinManager.null.access$0(SkinManager.null.this).loadSkin((MinecraftProfileTexture)map.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, skinAvailableCallback);
/*     */                     }
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> loadSkinFromCache(GameProfile profile) {
/* 155 */     return (Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>)this.skinCacheLoader.getUnchecked(profile);
/*     */   }
/*     */   
/*     */   public static interface SkinAvailableCallback {
/*     */     void skinAvailable(MinecraftProfileTexture.Type param1Type, ResourceLocation param1ResourceLocation, MinecraftProfileTexture param1MinecraftProfileTexture);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\SkinManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
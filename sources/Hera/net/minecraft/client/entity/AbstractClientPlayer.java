/*     */ package net.minecraft.client.entity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.IImageBuffer;
/*     */ import net.minecraft.client.renderer.ImageBufferDownload;
/*     */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import optfine.Config;
/*     */ import optfine.PlayerConfigurations;
/*     */ import optfine.Reflector;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractClientPlayer
/*     */   extends EntityPlayer
/*     */ {
/*     */   private NetworkPlayerInfo playerInfo;
/*  33 */   private ResourceLocation ofLocationCape = null;
/*     */   
/*     */   private static final String __OBFID = "CL_00000935";
/*     */   
/*     */   public AbstractClientPlayer(World worldIn, GameProfile playerProfile) {
/*  38 */     super(worldIn, playerProfile);
/*  39 */     String s = playerProfile.getName();
/*  40 */     downloadCape(s);
/*  41 */     PlayerConfigurations.getPlayerConfiguration(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/*  49 */     NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(getGameProfile().getId());
/*  50 */     return (networkplayerinfo != null && networkplayerinfo.getGameType() == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPlayerInfo() {
/*  58 */     return (getPlayerInfo() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NetworkPlayerInfo getPlayerInfo() {
/*  63 */     if (this.playerInfo == null)
/*     */     {
/*  65 */       this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(getUniqueID());
/*     */     }
/*     */     
/*  68 */     return this.playerInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSkin() {
/*  76 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/*  77 */     return (networkplayerinfo != null && networkplayerinfo.hasLocationSkin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationSkin() {
/*  85 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/*  86 */     return (networkplayerinfo == null) ? DefaultPlayerSkin.getDefaultSkin(getUniqueID()) : networkplayerinfo.getLocationSkin();
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationCape() {
/*  91 */     if (!Config.isShowCapes())
/*     */     {
/*  93 */       return null;
/*     */     }
/*  95 */     if (this.ofLocationCape != null)
/*     */     {
/*  97 */       return this.ofLocationCape;
/*     */     }
/*     */ 
/*     */     
/* 101 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 102 */     return (networkplayerinfo == null) ? null : networkplayerinfo.getLocationCape();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
/* 108 */     TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 109 */     Object object = texturemanager.getTexture(resourceLocationIn);
/*     */     
/* 111 */     if (object == null) {
/*     */       
/* 113 */       object = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] { StringUtils.stripControlCodes(username) }), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(username)), (IImageBuffer)new ImageBufferDownload());
/* 114 */       texturemanager.loadTexture(resourceLocationIn, (ITextureObject)object);
/*     */     } 
/*     */     
/* 117 */     return (ThreadDownloadImageData)object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getLocationSkin(String username) {
/* 125 */     return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSkinType() {
/* 130 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 131 */     return (networkplayerinfo == null) ? DefaultPlayerSkin.getSkinType(getUniqueID()) : networkplayerinfo.getSkinType();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFovModifier() {
/* 136 */     float f = 1.0F;
/*     */     
/* 138 */     if (this.capabilities.isFlying)
/*     */     {
/* 140 */       f *= 1.1F;
/*     */     }
/*     */     
/* 143 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 144 */     f = (float)(f * (iattributeinstance.getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0D) / 2.0D);
/*     */     
/* 146 */     if (this.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f))
/*     */     {
/* 148 */       f = 1.0F;
/*     */     }
/*     */     
/* 151 */     if (isUsingItem() && getItemInUse().getItem() == Items.bow) {
/*     */       
/* 153 */       int i = getItemInUseDuration();
/* 154 */       float f1 = i / 20.0F;
/*     */       
/* 156 */       if (f1 > 1.0F) {
/*     */         
/* 158 */         f1 = 1.0F;
/*     */       }
/*     */       else {
/*     */         
/* 162 */         f1 *= f1;
/*     */       } 
/*     */       
/* 165 */       f *= 1.0F - f1 * 0.15F;
/*     */     } 
/*     */     
/* 168 */     return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, new Object[] { this, Float.valueOf(f) }) : f;
/*     */   }
/*     */ 
/*     */   
/*     */   private void downloadCape(String p_downloadCape_1_) {
/* 173 */     if (p_downloadCape_1_ != null && !p_downloadCape_1_.isEmpty()) {
/*     */       
/* 175 */       p_downloadCape_1_ = StringUtils.stripControlCodes(p_downloadCape_1_);
/* 176 */       String s = "http://s.optifine.net/capes/" + p_downloadCape_1_ + ".png";
/* 177 */       String s1 = FilenameUtils.getBaseName(s);
/* 178 */       final ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s1);
/* 179 */       TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 180 */       ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
/*     */       
/* 182 */       if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData) {
/*     */         
/* 184 */         ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;
/*     */         
/* 186 */         if (threaddownloadimagedata.imageFound != null) {
/*     */           
/* 188 */           if (threaddownloadimagedata.imageFound.booleanValue())
/*     */           {
/* 190 */             this.ofLocationCape = resourcelocation;
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 197 */       IImageBuffer iimagebuffer = new IImageBuffer()
/*     */         {
/* 199 */           ImageBufferDownload ibd = new ImageBufferDownload();
/*     */           
/*     */           public BufferedImage parseUserSkin(BufferedImage image) {
/* 202 */             return AbstractClientPlayer.this.parseCape(image);
/*     */           }
/*     */           
/*     */           public void skinAvailable() {
/* 206 */             AbstractClientPlayer.this.ofLocationCape = resourcelocation;
/*     */           }
/*     */         };
/* 209 */       ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData(null, s, null, iimagebuffer);
/* 210 */       texturemanager.loadTexture(resourcelocation, (ITextureObject)threaddownloadimagedata1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private BufferedImage parseCape(BufferedImage p_parseCape_1_) {
/* 216 */     int i = 64;
/* 217 */     int j = 32;
/* 218 */     int k = p_parseCape_1_.getWidth();
/*     */     
/* 220 */     for (int l = p_parseCape_1_.getHeight(); i < k || j < l; j *= 2)
/*     */     {
/* 222 */       i *= 2;
/*     */     }
/*     */     
/* 225 */     BufferedImage bufferedimage = new BufferedImage(i, j, 2);
/* 226 */     Graphics graphics = bufferedimage.getGraphics();
/* 227 */     graphics.drawImage(p_parseCape_1_, 0, 0, null);
/* 228 */     graphics.dispose();
/* 229 */     return bufferedimage;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\entity\AbstractClientPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
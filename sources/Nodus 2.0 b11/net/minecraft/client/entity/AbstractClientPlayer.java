/*   1:    */ package net.minecraft.client.entity;
/*   2:    */ 
/*   3:    */ import com.mojang.authlib.GameProfile;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.renderer.IImageBuffer;
/*   6:    */ import net.minecraft.client.renderer.ImageBufferDownload;
/*   7:    */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*   8:    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.src.Config;
/*  12:    */ import net.minecraft.util.ResourceLocation;
/*  13:    */ import net.minecraft.util.StringUtils;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public abstract class AbstractClientPlayer
/*  17:    */   extends EntityPlayer
/*  18:    */ {
/*  19: 18 */   public static final ResourceLocation locationStevePng = new ResourceLocation("textures/entity/steve.png");
/*  20:    */   private ThreadDownloadImageData downloadImageSkin;
/*  21:    */   private ThreadDownloadImageData downloadImageCape;
/*  22:    */   private ResourceLocation locationSkin;
/*  23:    */   private ResourceLocation locationCape;
/*  24:    */   private static final String __OBFID = "CL_00000935";
/*  25:    */   
/*  26:    */   public AbstractClientPlayer(World p_i45074_1_, GameProfile p_i45074_2_)
/*  27:    */   {
/*  28: 27 */     super(p_i45074_1_, p_i45074_2_);
/*  29: 28 */     setupCustomSkin();
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void setupCustomSkin()
/*  33:    */   {
/*  34: 33 */     String var1 = getCommandSenderName();
/*  35: 35 */     if (!var1.isEmpty())
/*  36:    */     {
/*  37: 37 */       this.locationSkin = getLocationSkin(var1);
/*  38: 38 */       this.locationCape = getLocationCape(var1);
/*  39: 39 */       this.downloadImageSkin = getDownloadImageSkin(this.locationSkin, var1);
/*  40: 40 */       this.downloadImageCape = getDownloadImageCape(this.locationCape, var1);
/*  41: 41 */       this.downloadImageCape.enabled = Config.isShowCapes();
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ThreadDownloadImageData getTextureSkin()
/*  46:    */   {
/*  47: 47 */     return this.downloadImageSkin;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ThreadDownloadImageData getTextureCape()
/*  51:    */   {
/*  52: 52 */     return this.downloadImageCape;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ResourceLocation getLocationSkin()
/*  56:    */   {
/*  57: 57 */     return this.locationSkin;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public ResourceLocation getLocationCape()
/*  61:    */   {
/*  62: 62 */     return this.locationCape;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation par0ResourceLocation, String par1Str)
/*  66:    */   {
/*  67: 67 */     return getDownloadImage(par0ResourceLocation, getSkinUrl(par1Str), locationStevePng, new ImageBufferDownload());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static ThreadDownloadImageData getDownloadImageCape(ResourceLocation par0ResourceLocation, String par1Str)
/*  71:    */   {
/*  72: 72 */     return getDownloadImage(par0ResourceLocation, getCapeUrl(par1Str), null, null);
/*  73:    */   }
/*  74:    */   
/*  75:    */   private static ThreadDownloadImageData getDownloadImage(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer)
/*  76:    */   {
/*  77: 77 */     TextureManager var4 = Minecraft.getMinecraft().getTextureManager();
/*  78: 78 */     Object var5 = var4.getTexture(par0ResourceLocation);
/*  79: 80 */     if (var5 == null)
/*  80:    */     {
/*  81: 82 */       var5 = new ThreadDownloadImageData(par1Str, par2ResourceLocation, par3IImageBuffer);
/*  82: 83 */       var4.loadTexture(par0ResourceLocation, (ITextureObject)var5);
/*  83:    */     }
/*  84: 86 */     return (ThreadDownloadImageData)var5;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static String getSkinUrl(String par0Str)
/*  88:    */   {
/*  89: 91 */     return String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] { StringUtils.stripControlCodes(par0Str) });
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static String getCapeUrl(String par0Str)
/*  93:    */   {
/*  94: 96 */     return String.format("http://skins.minecraft.net/MinecraftCloaks/%s.png", new Object[] { StringUtils.stripControlCodes(par0Str) });
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static ResourceLocation getLocationSkin(String par0Str)
/*  98:    */   {
/*  99:101 */     return new ResourceLocation("skins/" + StringUtils.stripControlCodes(par0Str));
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static ResourceLocation getLocationCape(String par0Str)
/* 103:    */   {
/* 104:106 */     return new ResourceLocation("cloaks/" + StringUtils.stripControlCodes(par0Str));
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static ResourceLocation getLocationSkull(String par0Str)
/* 108:    */   {
/* 109:111 */     return new ResourceLocation("skull/" + StringUtils.stripControlCodes(par0Str));
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.entity.AbstractClientPlayer
 * JD-Core Version:    0.7.0.1
 */
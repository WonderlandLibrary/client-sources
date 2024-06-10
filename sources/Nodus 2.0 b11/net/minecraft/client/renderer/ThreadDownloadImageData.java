/*   1:    */ package net.minecraft.client.renderer;
/*   2:    */ 
/*   3:    */ import java.awt.image.BufferedImage;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.HttpURLConnection;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.util.concurrent.atomic.AtomicInteger;
/*   8:    */ import javax.imageio.ImageIO;
/*   9:    */ import net.minecraft.client.Minecraft;
/*  10:    */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*  11:    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*  12:    */ import net.minecraft.client.resources.IResourceManager;
/*  13:    */ import net.minecraft.src.ThreadDownloadImage;
/*  14:    */ import net.minecraft.util.ResourceLocation;
/*  15:    */ import org.apache.logging.log4j.LogManager;
/*  16:    */ import org.apache.logging.log4j.Logger;
/*  17:    */ 
/*  18:    */ public class ThreadDownloadImageData
/*  19:    */   extends SimpleTexture
/*  20:    */ {
/*  21: 20 */   private static final Logger logger = ;
/*  22: 21 */   private static final AtomicInteger field_147643_d = new AtomicInteger(0);
/*  23:    */   private final String imageUrl;
/*  24:    */   private final IImageBuffer imageBuffer;
/*  25:    */   private BufferedImage bufferedImage;
/*  26:    */   private Thread imageThread;
/*  27:    */   private boolean textureUploaded;
/*  28: 27 */   public boolean enabled = true;
/*  29:    */   private static final String __OBFID = "CL_00001049";
/*  30:    */   
/*  31:    */   public ThreadDownloadImageData(String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer)
/*  32:    */   {
/*  33: 32 */     super(par2ResourceLocation);
/*  34: 33 */     this.imageUrl = par1Str;
/*  35: 34 */     this.imageBuffer = par3IImageBuffer;
/*  36:    */   }
/*  37:    */   
/*  38:    */   private void func_147640_e()
/*  39:    */   {
/*  40: 39 */     if ((!this.textureUploaded) && (this.bufferedImage != null))
/*  41:    */     {
/*  42: 41 */       if (this.textureLocation != null) {
/*  43: 43 */         func_147631_c();
/*  44:    */       }
/*  45: 46 */       TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
/*  46: 47 */       this.textureUploaded = true;
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getGlTextureId()
/*  51:    */   {
/*  52: 53 */     func_147640_e();
/*  53: 54 */     return super.getGlTextureId();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void func_147641_a(BufferedImage p_147641_1_)
/*  57:    */   {
/*  58: 59 */     this.bufferedImage = p_147641_1_;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void loadTexture(IResourceManager par1ResourceManager)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64: 64 */     if ((this.bufferedImage == null) && (this.textureLocation != null)) {
/*  65: 66 */       super.loadTexture(par1ResourceManager);
/*  66:    */     }
/*  67: 69 */     if (this.imageThread == null)
/*  68:    */     {
/*  69: 71 */       this.imageThread = new Thread("Texture Downloader #" + field_147643_d.incrementAndGet())
/*  70:    */       {
/*  71:    */         private static final String __OBFID = "CL_00001050";
/*  72:    */         
/*  73:    */         public void run()
/*  74:    */         {
/*  75: 76 */           HttpURLConnection var1 = null;
/*  76:    */           try
/*  77:    */           {
/*  78: 80 */             var1 = (HttpURLConnection)new URL(ThreadDownloadImageData.this.imageUrl).openConnection(Minecraft.getMinecraft().getProxy());
/*  79: 81 */             var1.setDoInput(true);
/*  80: 82 */             var1.setDoOutput(false);
/*  81: 83 */             var1.connect();
/*  82: 85 */             if (var1.getResponseCode() / 100 == 2)
/*  83:    */             {
/*  84: 87 */               BufferedImage var6 = ImageIO.read(var1.getInputStream());
/*  85: 89 */               if (ThreadDownloadImageData.this.imageBuffer != null) {
/*  86: 91 */                 var6 = ThreadDownloadImageData.this.imageBuffer.parseUserSkin(var6);
/*  87:    */               }
/*  88: 94 */               ThreadDownloadImageData.this.func_147641_a(var6);
/*  89: 95 */               return;
/*  90:    */             }
/*  91: 98 */             return;
/*  92:    */           }
/*  93:    */           catch (Exception var61)
/*  94:    */           {
/*  95:102 */             ThreadDownloadImageData.logger.error("Couldn't download http texture", var61);
/*  96:    */           }
/*  97:    */           finally
/*  98:    */           {
/*  99:106 */             if (var1 != null) {
/* 100:108 */               var1.disconnect();
/* 101:    */             }
/* 102:    */           }
/* 103:    */         }
/* 104:112 */       };
/* 105:113 */       this.imageThread.setDaemon(true);
/* 106:114 */       this.imageThread.setName("Skin downloader: " + this.imageUrl);
/* 107:115 */       this.imageThread.start();
/* 108:    */       try
/* 109:    */       {
/* 110:119 */         URL e = new URL(this.imageUrl);
/* 111:120 */         String path = e.getPath();
/* 112:121 */         String prefixSkin = "/MinecraftSkins/";
/* 113:122 */         String prefixCape = "/MinecraftCloaks/";
/* 114:124 */         if (path.startsWith(prefixCape))
/* 115:    */         {
/* 116:126 */           String file = path.substring(prefixCape.length());
/* 117:127 */           String ofUrl = "http://s.optifine.net/capes/" + file;
/* 118:128 */           ThreadDownloadImage t = new ThreadDownloadImage(this, ofUrl, new ImageBufferDownload());
/* 119:129 */           t.setDaemon(true);
/* 120:130 */           t.setName("Cape downloader: " + this.imageUrl);
/* 121:131 */           t.start();
/* 122:    */         }
/* 123:    */       }
/* 124:    */       catch (Exception localException) {}
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isTextureUploaded()
/* 129:    */   {
/* 130:143 */     if (!this.enabled) {
/* 131:145 */       return false;
/* 132:    */     }
/* 133:149 */     func_147640_e();
/* 134:150 */     return this.textureUploaded;
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.ThreadDownloadImageData
 * JD-Core Version:    0.7.0.1
 */
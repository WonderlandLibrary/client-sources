/*   1:    */ package net.minecraft.client.renderer;
/*   2:    */ 
/*   3:    */ import java.awt.Graphics;
/*   4:    */ import java.awt.image.BufferedImage;
/*   5:    */ import java.awt.image.DataBufferInt;
/*   6:    */ import java.awt.image.WritableRaster;
/*   7:    */ 
/*   8:    */ public class ImageBufferDownload
/*   9:    */   implements IImageBuffer
/*  10:    */ {
/*  11:    */   private int[] imageData;
/*  12:    */   private int imageWidth;
/*  13:    */   private int imageHeight;
/*  14:    */   private static final String __OBFID = "CL_00000956";
/*  15:    */   
/*  16:    */   public BufferedImage parseUserSkin(BufferedImage par1BufferedImage)
/*  17:    */   {
/*  18: 17 */     if (par1BufferedImage == null) {
/*  19: 19 */       return null;
/*  20:    */     }
/*  21: 23 */     this.imageWidth = 64;
/*  22: 24 */     this.imageHeight = 32;
/*  23: 25 */     int srcWidth = par1BufferedImage.getWidth();
/*  24: 26 */     int srcHeight = par1BufferedImage.getHeight();
/*  25: 28 */     if (srcWidth == 64)
/*  26:    */     {
/*  27: 28 */       if ((srcHeight == 32) || (srcHeight == 64)) {}
/*  28:    */     }
/*  29:    */     else {
/*  30: 30 */       while ((this.imageWidth < srcWidth) || (this.imageHeight < srcHeight))
/*  31:    */       {
/*  32: 32 */         this.imageWidth *= 2;
/*  33: 33 */         this.imageHeight *= 2;
/*  34:    */       }
/*  35:    */     }
/*  36: 37 */     BufferedImage bufferedimage = new BufferedImage(this.imageWidth, this.imageHeight, 2);
/*  37: 38 */     Graphics g = bufferedimage.getGraphics();
/*  38: 39 */     g.drawImage(par1BufferedImage, 0, 0, null);
/*  39: 40 */     g.dispose();
/*  40: 41 */     this.imageData = ((DataBufferInt)bufferedimage.getRaster().getDataBuffer()).getData();
/*  41: 42 */     int w = this.imageWidth;
/*  42: 43 */     int h = this.imageHeight;
/*  43: 44 */     setAreaOpaque(0, 0, w / 2, h / 2);
/*  44: 45 */     setAreaTransparent(w / 2, 0, w, h);
/*  45: 46 */     setAreaOpaque(0, h / 2, w, h);
/*  46: 47 */     return bufferedimage;
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void setAreaTransparent(int par1, int par2, int par3, int par4)
/*  50:    */   {
/*  51: 58 */     if (!hasTransparency(par1, par2, par3, par4)) {
/*  52: 60 */       for (int var5 = par1; var5 < par3; var5++) {
/*  53: 62 */         for (int var6 = par2; var6 < par4; var6++) {
/*  54: 64 */           this.imageData[(var5 + var6 * this.imageWidth)] &= 0xFFFFFF;
/*  55:    */         }
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void setAreaOpaque(int par1, int par2, int par3, int par4)
/*  61:    */   {
/*  62: 75 */     for (int var5 = par1; var5 < par3; var5++) {
/*  63: 77 */       for (int var6 = par2; var6 < par4; var6++) {
/*  64: 79 */         this.imageData[(var5 + var6 * this.imageWidth)] |= 0xFF000000;
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   private boolean hasTransparency(int par1, int par2, int par3, int par4)
/*  70:    */   {
/*  71: 89 */     for (int var5 = par1; var5 < par3; var5++) {
/*  72: 91 */       for (int var6 = par2; var6 < par4; var6++)
/*  73:    */       {
/*  74: 93 */         int var7 = this.imageData[(var5 + var6 * this.imageWidth)];
/*  75: 95 */         if ((var7 >> 24 & 0xFF) < 128) {
/*  76: 97 */           return true;
/*  77:    */         }
/*  78:    */       }
/*  79:    */     }
/*  80:102 */     return false;
/*  81:    */   }
/*  82:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.ImageBufferDownload
 * JD-Core Version:    0.7.0.1
 */
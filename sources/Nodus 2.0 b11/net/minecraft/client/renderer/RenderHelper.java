/*  1:   */ package net.minecraft.client.renderer;
/*  2:   */ 
/*  3:   */ import java.nio.FloatBuffer;
/*  4:   */ import net.minecraft.util.Vec3;
/*  5:   */ import org.lwjgl.opengl.GL11;
/*  6:   */ 
/*  7:   */ public class RenderHelper
/*  8:   */ {
/*  9:10 */   private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
/* 10:11 */   private static final Vec3 field_82884_b = Vec3.createVectorHelper(0.2000000029802322D, 1.0D, -0.699999988079071D).normalize();
/* 11:12 */   private static final Vec3 field_82885_c = Vec3.createVectorHelper(-0.2000000029802322D, 1.0D, 0.699999988079071D).normalize();
/* 12:   */   private static final String __OBFID = "CL_00000629";
/* 13:   */   
/* 14:   */   public static void disableStandardItemLighting()
/* 15:   */   {
/* 16:20 */     GL11.glDisable(2896);
/* 17:21 */     GL11.glDisable(16384);
/* 18:22 */     GL11.glDisable(16385);
/* 19:23 */     GL11.glDisable(2903);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static void enableStandardItemLighting()
/* 23:   */   {
/* 24:31 */     GL11.glEnable(2896);
/* 25:32 */     GL11.glEnable(16384);
/* 26:33 */     GL11.glEnable(16385);
/* 27:34 */     GL11.glEnable(2903);
/* 28:35 */     GL11.glColorMaterial(1032, 5634);
/* 29:36 */     float var0 = 0.4F;
/* 30:37 */     float var1 = 0.6F;
/* 31:38 */     float var2 = 0.0F;
/* 32:39 */     GL11.glLight(16384, 4611, setColorBuffer(field_82884_b.xCoord, field_82884_b.yCoord, field_82884_b.zCoord, 0.0D));
/* 33:40 */     GL11.glLight(16384, 4609, setColorBuffer(var1, var1, var1, 1.0F));
/* 34:41 */     GL11.glLight(16384, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 35:42 */     GL11.glLight(16384, 4610, setColorBuffer(var2, var2, var2, 1.0F));
/* 36:43 */     GL11.glLight(16385, 4611, setColorBuffer(field_82885_c.xCoord, field_82885_c.yCoord, field_82885_c.zCoord, 0.0D));
/* 37:44 */     GL11.glLight(16385, 4609, setColorBuffer(var1, var1, var1, 1.0F));
/* 38:45 */     GL11.glLight(16385, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 39:46 */     GL11.glLight(16385, 4610, setColorBuffer(var2, var2, var2, 1.0F));
/* 40:47 */     GL11.glShadeModel(7424);
/* 41:48 */     GL11.glLightModel(2899, setColorBuffer(var0, var0, var0, 1.0F));
/* 42:   */   }
/* 43:   */   
/* 44:   */   private static FloatBuffer setColorBuffer(double par0, double par2, double par4, double par6)
/* 45:   */   {
/* 46:56 */     return setColorBuffer((float)par0, (float)par2, (float)par4, (float)par6);
/* 47:   */   }
/* 48:   */   
/* 49:   */   private static FloatBuffer setColorBuffer(float par0, float par1, float par2, float par3)
/* 50:   */   {
/* 51:64 */     colorBuffer.clear();
/* 52:65 */     colorBuffer.put(par0).put(par1).put(par2).put(par3);
/* 53:66 */     colorBuffer.flip();
/* 54:67 */     return colorBuffer;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public static void enableGUIStandardItemLighting()
/* 58:   */   {
/* 59:75 */     GL11.glPushMatrix();
/* 60:76 */     GL11.glRotatef(-30.0F, 0.0F, 1.0F, 0.0F);
/* 61:77 */     GL11.glRotatef(165.0F, 1.0F, 0.0F, 0.0F);
/* 62:78 */     enableStandardItemLighting();
/* 63:79 */     GL11.glPopMatrix();
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.RenderHelper
 * JD-Core Version:    0.7.0.1
 */
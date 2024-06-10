/*     */ package nightmare.utils.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderUtils
/*     */ {
/*     */   public static void setColor(Color color) {
/*  15 */     if (color == null) {
/*  16 */       color = Color.white;
/*     */     }
/*  18 */     color((color.getRed() / 255.0F), (color.getGreen() / 255.0F), (color.getBlue() / 255.0F), (color.getAlpha() / 255.0F));
/*     */   }
/*     */   
/*     */   private static void color(double red, double green, double blue, double alpha) {
/*  22 */     GL11.glColor4d(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawRect(double left, double top, double right, double bottom, int color) {
/*  27 */     if (left < right) {
/*     */       
/*  29 */       double i = left;
/*  30 */       left = right;
/*  31 */       right = i;
/*     */     } 
/*     */     
/*  34 */     if (top < bottom) {
/*     */       
/*  36 */       double j = top;
/*  37 */       top = bottom;
/*  38 */       bottom = j;
/*     */     } 
/*     */     
/*  41 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  42 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*  43 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*  44 */     float f2 = (color & 0xFF) / 255.0F;
/*  45 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  46 */     WorldRenderer worldrenderer = tessellator.func_178180_c();
/*  47 */     GlStateManager.func_179147_l();
/*  48 */     GlStateManager.func_179090_x();
/*  49 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/*  50 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/*  51 */     worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/*  52 */     worldrenderer.func_181662_b(left, bottom, 0.0D).func_181675_d();
/*  53 */     worldrenderer.func_181662_b(right, bottom, 0.0D).func_181675_d();
/*  54 */     worldrenderer.func_181662_b(right, top, 0.0D).func_181675_d();
/*  55 */     worldrenderer.func_181662_b(left, top, 0.0D).func_181675_d();
/*  56 */     tessellator.func_78381_a();
/*  57 */     GlStateManager.func_179098_w();
/*  58 */     GlStateManager.func_179084_k();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawRect(float left, float top, float right, float bottom, int color) {
/*  63 */     if (left < right) {
/*     */       
/*  65 */       float i = left;
/*  66 */       left = right;
/*  67 */       right = i;
/*     */     } 
/*     */     
/*  70 */     if (top < bottom) {
/*     */       
/*  72 */       float j = top;
/*  73 */       top = bottom;
/*  74 */       bottom = j;
/*     */     } 
/*     */     
/*  77 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  78 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*  79 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*  80 */     float f2 = (color & 0xFF) / 255.0F;
/*  81 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  82 */     WorldRenderer worldrenderer = tessellator.func_178180_c();
/*  83 */     GlStateManager.func_179147_l();
/*  84 */     GlStateManager.func_179090_x();
/*  85 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/*  86 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/*  87 */     worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/*  88 */     worldrenderer.func_181662_b(left, bottom, 0.0D).func_181675_d();
/*  89 */     worldrenderer.func_181662_b(right, bottom, 0.0D).func_181675_d();
/*  90 */     worldrenderer.func_181662_b(right, top, 0.0D).func_181675_d();
/*  91 */     worldrenderer.func_181662_b(left, top, 0.0D).func_181675_d();
/*  92 */     tessellator.func_78381_a();
/*  93 */     GlStateManager.func_179098_w();
/*  94 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   public static void drawBorderedRect(float left, float top, float right, float bottom, float lineWidth, int color, int outlineColor) {
/*  98 */     drawRect(left, top, right, bottom, color);
/*     */     
/* 100 */     float f = (outlineColor >> 24 & 0xFF) / 255.0F;
/* 101 */     float f1 = (outlineColor >> 16 & 0xFF) / 255.0F;
/* 102 */     float f2 = (outlineColor >> 8 & 0xFF) / 255.0F;
/* 103 */     float f3 = (outlineColor & 0xFF) / 255.0F;
/*     */     
/* 105 */     GL11.glEnable(3042);
/* 106 */     GL11.glDisable(3553);
/* 107 */     GL11.glBlendFunc(770, 771);
/* 108 */     GL11.glEnable(2848);
/*     */     
/* 110 */     GL11.glPushMatrix();
/* 111 */     GL11.glColor4f(f1, f2, f3, f);
/* 112 */     GL11.glLineWidth(lineWidth);
/* 113 */     GL11.glBegin(1);
/* 114 */     GL11.glVertex2d(left, top);
/* 115 */     GL11.glVertex2d(left, bottom);
/* 116 */     GL11.glVertex2d(right, bottom);
/* 117 */     GL11.glVertex2d(right, top);
/* 118 */     GL11.glVertex2d(left, top);
/* 119 */     GL11.glVertex2d(right, top);
/* 120 */     GL11.glVertex2d(left, bottom);
/* 121 */     GL11.glVertex2d(right, bottom);
/* 122 */     GL11.glEnd();
/* 123 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 124 */     GL11.glPopMatrix();
/*     */     
/* 126 */     GlStateManager.func_179098_w();
/* 127 */     GlStateManager.func_179084_k();
/* 128 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 255.0F);
/* 129 */     GL11.glEnable(3553);
/* 130 */     GL11.glDisable(3042);
/* 131 */     GL11.glDisable(2848);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\render\RenderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
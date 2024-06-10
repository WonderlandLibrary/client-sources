/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.client.renderer.OpenGlHelper;
/*  6:   */ import org.lwjgl.opengl.GL11;
/*  7:   */ 
/*  8:   */ public class GuiLabel
/*  9:   */   extends Gui
/* 10:   */ {
/* 11:   */   protected int field_146167_a;
/* 12:   */   protected int field_146161_f;
/* 13:   */   public int field_146162_g;
/* 14:   */   public int field_146174_h;
/* 15:   */   private ArrayList field_146173_k;
/* 16:   */   private boolean field_146170_l;
/* 17:   */   public boolean field_146172_j;
/* 18:   */   private boolean field_146171_m;
/* 19:   */   private int field_146168_n;
/* 20:   */   private int field_146169_o;
/* 21:   */   private int field_146166_p;
/* 22:   */   private int field_146165_q;
/* 23:   */   private FontRenderer field_146164_r;
/* 24:   */   private int field_146163_s;
/* 25:   */   private static final String __OBFID = "CL_00000671";
/* 26:   */   
/* 27:   */   public void func_146159_a(Minecraft p_146159_1_, int p_146159_2_, int p_146159_3_)
/* 28:   */   {
/* 29:28 */     if (this.field_146172_j)
/* 30:   */     {
/* 31:30 */       GL11.glEnable(3042);
/* 32:31 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 33:32 */       GL11.glBlendFunc(770, 771);
/* 34:33 */       func_146160_b(p_146159_1_, p_146159_2_, p_146159_3_);
/* 35:34 */       int var4 = this.field_146174_h + this.field_146161_f / 2 + this.field_146163_s / 2;
/* 36:35 */       int var5 = var4 - this.field_146173_k.size() * 10 / 2;
/* 37:37 */       for (int var6 = 0; var6 < this.field_146173_k.size(); var6++) {
/* 38:39 */         if (this.field_146170_l) {
/* 39:41 */           drawCenteredString(this.field_146164_r, (String)this.field_146173_k.get(var6), this.field_146162_g + this.field_146167_a / 2, var5 + var6 * 10, this.field_146168_n);
/* 40:   */         } else {
/* 41:45 */           drawString(this.field_146164_r, (String)this.field_146173_k.get(var6), this.field_146162_g, var5 + var6 * 10, this.field_146168_n);
/* 42:   */         }
/* 43:   */       }
/* 44:   */     }
/* 45:   */   }
/* 46:   */   
/* 47:   */   protected void func_146160_b(Minecraft p_146160_1_, int p_146160_2_, int p_146160_3_)
/* 48:   */   {
/* 49:53 */     if (this.field_146171_m)
/* 50:   */     {
/* 51:55 */       int var4 = this.field_146167_a + this.field_146163_s * 2;
/* 52:56 */       int var5 = this.field_146161_f + this.field_146163_s * 2;
/* 53:57 */       int var6 = this.field_146162_g - this.field_146163_s;
/* 54:58 */       int var7 = this.field_146174_h - this.field_146163_s;
/* 55:59 */       drawRect(var6, var7, var6 + var4, var7 + var5, this.field_146169_o);
/* 56:60 */       drawHorizontalLine(var6, var6 + var4, var7, this.field_146166_p);
/* 57:61 */       drawHorizontalLine(var6, var6 + var4, var7 + var5, this.field_146165_q);
/* 58:62 */       drawVerticalLine(var6, var7, var7 + var5, this.field_146166_p);
/* 59:63 */       drawVerticalLine(var6 + var4, var7, var7 + var5, this.field_146165_q);
/* 60:   */     }
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiLabel
 * JD-Core Version:    0.7.0.1
 */
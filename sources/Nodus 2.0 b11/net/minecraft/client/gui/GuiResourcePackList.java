/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.client.renderer.Tessellator;
/*  6:   */ import net.minecraft.client.resources.ResourcePackListEntry;
/*  7:   */ import net.minecraft.util.EnumChatFormatting;
/*  8:   */ 
/*  9:   */ public abstract class GuiResourcePackList
/* 10:   */   extends GuiListExtended
/* 11:   */ {
/* 12:   */   protected final Minecraft field_148205_k;
/* 13:   */   protected final List field_148204_l;
/* 14:   */   private static final String __OBFID = "CL_00000825";
/* 15:   */   
/* 16:   */   public GuiResourcePackList(Minecraft p_i45055_1_, int p_i45055_2_, int p_i45055_3_, List p_i45055_4_)
/* 17:   */   {
/* 18:17 */     super(p_i45055_1_, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
/* 19:18 */     this.field_148205_k = p_i45055_1_;
/* 20:19 */     this.field_148204_l = p_i45055_4_;
/* 21:20 */     this.field_148163_i = false;
/* 22:21 */     func_148133_a(true, (int)(p_i45055_1_.fontRenderer.FONT_HEIGHT * 1.5F));
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected void func_148129_a(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_)
/* 26:   */   {
/* 27:26 */     String var4 = EnumChatFormatting.UNDERLINE + EnumChatFormatting.BOLD + func_148202_k();
/* 28:27 */     this.field_148205_k.fontRenderer.drawString(var4, p_148129_1_ + this.field_148155_a / 2 - this.field_148205_k.fontRenderer.getStringWidth(var4) / 2, Math.min(this.field_148153_b + 3, p_148129_2_), 16777215);
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected abstract String func_148202_k();
/* 32:   */   
/* 33:   */   public List func_148201_l()
/* 34:   */   {
/* 35:34 */     return this.field_148204_l;
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected int getSize()
/* 39:   */   {
/* 40:39 */     return func_148201_l().size();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public ResourcePackListEntry func_148180_b(int p_148203_1_)
/* 44:   */   {
/* 45:44 */     return (ResourcePackListEntry)func_148201_l().get(p_148203_1_);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int func_148139_c()
/* 49:   */   {
/* 50:49 */     return this.field_148155_a;
/* 51:   */   }
/* 52:   */   
/* 53:   */   protected int func_148137_d()
/* 54:   */   {
/* 55:54 */     return this.field_148151_d - 6;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiResourcePackList
 * JD-Core Version:    0.7.0.1
 */
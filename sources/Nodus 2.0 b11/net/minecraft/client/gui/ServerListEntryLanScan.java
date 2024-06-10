/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ import net.minecraft.client.resources.I18n;
/*  6:   */ 
/*  7:   */ public class ServerListEntryLanScan
/*  8:   */   implements GuiListExtended.IGuiListEntry
/*  9:   */ {
/* 10: 9 */   private final Minecraft field_148288_a = Minecraft.getMinecraft();
/* 11:   */   private static final String __OBFID = "CL_00000815";
/* 12:   */   
/* 13:   */   public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
/* 14:   */   {
/* 15:14 */     int var10 = p_148279_3_ + p_148279_5_ / 2 - this.field_148288_a.fontRenderer.FONT_HEIGHT / 2;
/* 16:15 */     this.field_148288_a.fontRenderer.drawString(I18n.format("lanServer.scanning", new Object[0]), GuiScreen.width / 2 - this.field_148288_a.fontRenderer.getStringWidth(I18n.format("lanServer.scanning", new Object[0])) / 2, var10, 16777215);
/* 17:   */     String var11;
/* 18:   */     String var11;
/* 19:   */     String var11;
/* 20:18 */     switch ((int)(Minecraft.getSystemTime() / 300L % 4L))
/* 21:   */     {
/* 22:   */     case 0: 
/* 23:   */     default: 
/* 24:22 */       var11 = "O o o";
/* 25:23 */       break;
/* 26:   */     case 1: 
/* 27:   */     case 3: 
/* 28:27 */       var11 = "o O o";
/* 29:28 */       break;
/* 30:   */     case 2: 
/* 31:31 */       var11 = "o o O";
/* 32:   */     }
/* 33:34 */     this.field_148288_a.fontRenderer.drawString(var11, GuiScreen.width / 2 - this.field_148288_a.fontRenderer.getStringWidth(var11) / 2, var10 + this.field_148288_a.fontRenderer.FONT_HEIGHT, 8421504);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
/* 37:   */   {
/* 38:39 */     return false;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {}
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.ServerListEntryLanScan
 * JD-Core Version:    0.7.0.1
 */
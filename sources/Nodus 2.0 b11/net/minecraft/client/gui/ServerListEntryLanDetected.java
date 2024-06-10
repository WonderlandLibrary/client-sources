/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.network.LanServerDetector.LanServer;
/*  5:   */ import net.minecraft.client.renderer.Tessellator;
/*  6:   */ import net.minecraft.client.resources.I18n;
/*  7:   */ import net.minecraft.client.settings.GameSettings;
/*  8:   */ 
/*  9:   */ public class ServerListEntryLanDetected
/* 10:   */   implements GuiListExtended.IGuiListEntry
/* 11:   */ {
/* 12:   */   private final GuiMultiplayer field_148292_c;
/* 13:   */   protected final Minecraft field_148293_a;
/* 14:   */   protected final LanServerDetector.LanServer field_148291_b;
/* 15:13 */   private long field_148290_d = 0L;
/* 16:   */   private static final String __OBFID = "CL_00000816";
/* 17:   */   
/* 18:   */   protected ServerListEntryLanDetected(GuiMultiplayer p_i45046_1_, LanServerDetector.LanServer p_i45046_2_)
/* 19:   */   {
/* 20:18 */     this.field_148292_c = p_i45046_1_;
/* 21:19 */     this.field_148291_b = p_i45046_2_;
/* 22:20 */     this.field_148293_a = Minecraft.getMinecraft();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void func_148279_a(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
/* 26:   */   {
/* 27:25 */     this.field_148293_a.fontRenderer.drawString(I18n.format("lanServer.title", new Object[0]), p_148279_2_ + 32 + 3, p_148279_3_ + 1, 16777215);
/* 28:26 */     this.field_148293_a.fontRenderer.drawString(this.field_148291_b.getServerMotd(), p_148279_2_ + 32 + 3, p_148279_3_ + 12, 8421504);
/* 29:28 */     if (this.field_148293_a.gameSettings.hideServerAddress) {
/* 30:30 */       this.field_148293_a.fontRenderer.drawString(I18n.format("selectServer.hiddenAddress", new Object[0]), p_148279_2_ + 32 + 3, p_148279_3_ + 12 + 11, 3158064);
/* 31:   */     } else {
/* 32:34 */       this.field_148293_a.fontRenderer.drawString(this.field_148291_b.getServerIpPort(), p_148279_2_ + 32 + 3, p_148279_3_ + 12 + 11, 3158064);
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
/* 37:   */   {
/* 38:40 */     this.field_148292_c.func_146790_a(p_148278_1_);
/* 39:42 */     if (Minecraft.getSystemTime() - this.field_148290_d < 250L) {
/* 40:44 */       this.field_148292_c.func_146796_h();
/* 41:   */     }
/* 42:47 */     this.field_148290_d = Minecraft.getSystemTime();
/* 43:48 */     return false;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {}
/* 47:   */   
/* 48:   */   public LanServerDetector.LanServer func_148289_a()
/* 49:   */   {
/* 50:55 */     return this.field_148291_b;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.ServerListEntryLanDetected
 * JD-Core Version:    0.7.0.1
 */
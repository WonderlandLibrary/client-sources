/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ import net.minecraft.client.resources.I18n;
/*  8:   */ import net.minecraft.util.IChatComponent;
/*  9:   */ 
/* 10:   */ public class GuiScreenDisconnectedOnline
/* 11:   */   extends GuiScreen
/* 12:   */ {
/* 13:   */   private String field_146867_a;
/* 14:   */   private IChatComponent field_146865_f;
/* 15:   */   private List field_146866_g;
/* 16:   */   private final GuiScreen field_146868_h;
/* 17:   */   private static final String __OBFID = "CL_00000778";
/* 18:   */   
/* 19:   */   public GuiScreenDisconnectedOnline(GuiScreen p_i45037_1_, String p_i45037_2_, IChatComponent p_i45037_3_)
/* 20:   */   {
/* 21:20 */     this.field_146868_h = p_i45037_1_;
/* 22:21 */     this.field_146867_a = I18n.format(p_i45037_2_, new Object[0]);
/* 23:22 */     this.field_146865_f = p_i45037_3_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void keyTyped(char par1, int par2) {}
/* 27:   */   
/* 28:   */   public void initGui()
/* 29:   */   {
/* 30:35 */     this.buttonList.clear();
/* 31:36 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.back", new Object[0])));
/* 32:37 */     this.field_146866_g = this.fontRendererObj.listFormattedStringToWidth(this.field_146865_f.getFormattedText(), width - 50);
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 36:   */   {
/* 37:42 */     if (p_146284_1_.id == 0) {
/* 38:44 */       this.mc.displayGuiScreen(this.field_146868_h);
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void drawScreen(int par1, int par2, float par3)
/* 43:   */   {
/* 44:53 */     drawDefaultBackground();
/* 45:54 */     drawCenteredString(this.fontRendererObj, this.field_146867_a, width / 2, height / 2 - 50, 11184810);
/* 46:55 */     int var4 = height / 2 - 30;
/* 47:57 */     if (this.field_146866_g != null) {
/* 48:59 */       for (Iterator var5 = this.field_146866_g.iterator(); var5.hasNext(); var4 += this.fontRendererObj.FONT_HEIGHT)
/* 49:   */       {
/* 50:61 */         String var6 = (String)var5.next();
/* 51:62 */         drawCenteredString(this.fontRendererObj, var6, width / 2, var4, 16777215);
/* 52:   */       }
/* 53:   */     }
/* 54:66 */     super.drawScreen(par1, par2, par3);
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenDisconnectedOnline
 * JD-Core Version:    0.7.0.1
 */
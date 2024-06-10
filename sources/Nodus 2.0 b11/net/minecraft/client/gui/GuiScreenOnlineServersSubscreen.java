/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  6:   */ import net.minecraft.client.resources.I18n;
/*  7:   */ 
/*  8:   */ public class GuiScreenOnlineServersSubscreen
/*  9:   */ {
/* 10:   */   private final int field_148400_g;
/* 11:   */   private final int field_148407_h;
/* 12:   */   private final int field_148408_i;
/* 13:   */   private final int field_148406_j;
/* 14:15 */   List field_148405_a = new ArrayList();
/* 15:   */   String[] field_148403_b;
/* 16:   */   String[] field_148404_c;
/* 17:   */   String[][] field_148401_d;
/* 18:   */   int field_148402_e;
/* 19:   */   int field_148399_f;
/* 20:   */   private static final String __OBFID = "CL_00000796";
/* 21:   */   
/* 22:   */   public GuiScreenOnlineServersSubscreen(int par1, int par2, int par3, int par4, int par5, int par6)
/* 23:   */   {
/* 24:25 */     this.field_148400_g = par1;
/* 25:26 */     this.field_148407_h = par2;
/* 26:27 */     this.field_148408_i = par3;
/* 27:28 */     this.field_148406_j = par4;
/* 28:29 */     this.field_148402_e = par5;
/* 29:30 */     this.field_148399_f = par6;
/* 30:31 */     func_148395_a();
/* 31:   */   }
/* 32:   */   
/* 33:   */   private void func_148395_a()
/* 34:   */   {
/* 35:36 */     func_148396_b();
/* 36:37 */     this.field_148405_a.add(new NodusGuiButton(5005, this.field_148408_i, this.field_148406_j + 1, 212, 20, func_148398_c()));
/* 37:38 */     this.field_148405_a.add(new NodusGuiButton(5006, this.field_148408_i, this.field_148406_j + 25, 212, 20, func_148393_d()));
/* 38:   */   }
/* 39:   */   
/* 40:   */   private void func_148396_b()
/* 41:   */   {
/* 42:43 */     this.field_148403_b = new String[] { I18n.format("options.difficulty.peaceful", new Object[0]), I18n.format("options.difficulty.easy", new Object[0]), I18n.format("options.difficulty.normal", new Object[0]), I18n.format("options.difficulty.hard", new Object[0]) };
/* 43:44 */     this.field_148404_c = new String[] { I18n.format("selectWorld.gameMode.survival", new Object[0]), I18n.format("selectWorld.gameMode.creative", new Object[0]), I18n.format("selectWorld.gameMode.adventure", new Object[0]) };
/* 44:45 */     this.field_148401_d = new String[][] { { I18n.format("selectWorld.gameMode.survival.line1", new Object[0]), I18n.format("selectWorld.gameMode.survival.line2", new Object[0]) }, { I18n.format("selectWorld.gameMode.creative.line1", new Object[0]), I18n.format("selectWorld.gameMode.creative.line2", new Object[0]) }, { I18n.format("selectWorld.gameMode.adventure.line1", new Object[0]), I18n.format("selectWorld.gameMode.adventure.line2", new Object[0]) } };
/* 45:   */   }
/* 46:   */   
/* 47:   */   private String func_148398_c()
/* 48:   */   {
/* 49:50 */     String var1 = I18n.format("options.difficulty", new Object[0]);
/* 50:51 */     return var1 + ": " + this.field_148403_b[this.field_148402_e];
/* 51:   */   }
/* 52:   */   
/* 53:   */   private String func_148393_d()
/* 54:   */   {
/* 55:56 */     String var1 = I18n.format("selectWorld.gameMode", new Object[0]);
/* 56:57 */     return var1 + ": " + this.field_148404_c[this.field_148399_f];
/* 57:   */   }
/* 58:   */   
/* 59:   */   void func_148397_a(NodusGuiButton p_148397_1_)
/* 60:   */   {
/* 61:62 */     if (p_148397_1_.enabled) {
/* 62:64 */       if (p_148397_1_.id == 5005)
/* 63:   */       {
/* 64:66 */         this.field_148402_e = ((this.field_148402_e + 1) % this.field_148403_b.length);
/* 65:67 */         p_148397_1_.displayString = func_148398_c();
/* 66:   */       }
/* 67:69 */       else if (p_148397_1_.id == 5006)
/* 68:   */       {
/* 69:71 */         this.field_148399_f = ((this.field_148399_f + 1) % this.field_148404_c.length);
/* 70:72 */         p_148397_1_.displayString = func_148393_d();
/* 71:   */       }
/* 72:   */     }
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void func_148394_a(GuiScreen p_148394_1_, FontRenderer p_148394_2_)
/* 76:   */   {
/* 77:79 */     GuiScreen.drawString(p_148394_2_, this.field_148401_d[this.field_148399_f][0], this.field_148408_i, this.field_148406_j + 50, 10526880);
/* 78:80 */     GuiScreen.drawString(p_148394_2_, this.field_148401_d[this.field_148399_f][1], this.field_148408_i, this.field_148406_j + 60, 10526880);
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenOnlineServersSubscreen
 * JD-Core Version:    0.7.0.1
 */
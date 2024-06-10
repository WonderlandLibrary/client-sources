/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.resources.I18n;
/*   7:    */ import net.minecraft.client.settings.GameSettings;
/*   8:    */ import net.minecraft.client.settings.GameSettings.Options;
/*   9:    */ import net.minecraft.client.settings.KeyBinding;
/*  10:    */ 
/*  11:    */ public class GuiControls
/*  12:    */   extends GuiScreen
/*  13:    */ {
/*  14: 10 */   private static final GameSettings.Options[] field_146492_g = { GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN };
/*  15:    */   private GuiScreen field_146496_h;
/*  16: 12 */   protected String field_146495_a = "Controls";
/*  17:    */   private GameSettings field_146497_i;
/*  18: 14 */   public KeyBinding field_146491_f = null;
/*  19:    */   private GuiKeyBindingList field_146494_r;
/*  20:    */   private NodusGuiButton field_146493_s;
/*  21:    */   private static final String __OBFID = "CL_00000736";
/*  22:    */   
/*  23:    */   public GuiControls(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
/*  24:    */   {
/*  25: 21 */     this.field_146496_h = par1GuiScreen;
/*  26: 22 */     this.field_146497_i = par2GameSettings;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void initGui()
/*  30:    */   {
/*  31: 30 */     this.field_146494_r = new GuiKeyBindingList(this, this.mc);
/*  32: 31 */     this.buttonList.add(new NodusGuiButton(200, width / 2 - 155, height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
/*  33: 32 */     this.buttonList.add(this.field_146493_s = new NodusGuiButton(201, width / 2 - 155 + 160, height - 29, 150, 20, I18n.format("controls.resetAll", new Object[0])));
/*  34: 33 */     this.field_146495_a = I18n.format("controls.title", new Object[0]);
/*  35: 34 */     int var1 = 0;
/*  36: 35 */     GameSettings.Options[] var2 = field_146492_g;
/*  37: 36 */     int var3 = var2.length;
/*  38: 38 */     for (int var4 = 0; var4 < var3; var4++)
/*  39:    */     {
/*  40: 40 */       GameSettings.Options var5 = var2[var4];
/*  41: 42 */       if (var5.getEnumFloat()) {
/*  42: 44 */         this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5));
/*  43:    */       } else {
/*  44: 48 */         this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5, this.field_146497_i.getKeyBinding(var5)));
/*  45:    */       }
/*  46: 51 */       var1++;
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  51:    */   {
/*  52: 57 */     if (p_146284_1_.id == 200)
/*  53:    */     {
/*  54: 59 */       this.mc.displayGuiScreen(this.field_146496_h);
/*  55:    */     }
/*  56: 61 */     else if (p_146284_1_.id == 201)
/*  57:    */     {
/*  58: 63 */       KeyBinding[] var2 = this.mc.gameSettings.keyBindings;
/*  59: 64 */       int var3 = var2.length;
/*  60: 66 */       for (int var4 = 0; var4 < var3; var4++)
/*  61:    */       {
/*  62: 68 */         KeyBinding var5 = var2[var4];
/*  63: 69 */         var5.setKeyCode(var5.getKeyCodeDefault());
/*  64:    */       }
/*  65: 72 */       KeyBinding.resetKeyBindingArrayAndHash();
/*  66:    */     }
/*  67: 74 */     else if ((p_146284_1_.id < 100) && ((p_146284_1_ instanceof GuiOptionButton)))
/*  68:    */     {
/*  69: 76 */       this.field_146497_i.setOptionValue(((GuiOptionButton)p_146284_1_).func_146136_c(), 1);
/*  70: 77 */       p_146284_1_.displayString = this.field_146497_i.getKeyBinding(GameSettings.Options.getEnumOptions(p_146284_1_.id));
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void mouseClicked(int par1, int par2, int par3)
/*  75:    */   {
/*  76: 86 */     if (this.field_146491_f != null)
/*  77:    */     {
/*  78: 88 */       this.field_146497_i.setKeyCodeSave(this.field_146491_f, -100 + par3);
/*  79: 89 */       this.field_146491_f = null;
/*  80: 90 */       KeyBinding.resetKeyBindingArrayAndHash();
/*  81:    */     }
/*  82: 92 */     else if ((par3 != 0) || (!this.field_146494_r.func_148179_a(par1, par2, par3)))
/*  83:    */     {
/*  84: 94 */       super.mouseClicked(par1, par2, par3);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_)
/*  89:    */   {
/*  90:100 */     if ((p_146286_3_ != 0) || (!this.field_146494_r.func_148181_b(p_146286_1_, p_146286_2_, p_146286_3_))) {
/*  91:102 */       super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void keyTyped(char par1, int par2)
/*  96:    */   {
/*  97:111 */     if (this.field_146491_f != null)
/*  98:    */     {
/*  99:113 */       if (par2 == 1) {
/* 100:115 */         this.field_146497_i.setKeyCodeSave(this.field_146491_f, 0);
/* 101:    */       } else {
/* 102:119 */         this.field_146497_i.setKeyCodeSave(this.field_146491_f, par2);
/* 103:    */       }
/* 104:122 */       this.field_146491_f = null;
/* 105:123 */       KeyBinding.resetKeyBindingArrayAndHash();
/* 106:    */     }
/* 107:    */     else
/* 108:    */     {
/* 109:127 */       super.keyTyped(par1, par2);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void drawScreen(int par1, int par2, float par3)
/* 114:    */   {
/* 115:136 */     drawDefaultBackground();
/* 116:137 */     this.field_146494_r.func_148128_a(par1, par2, par3);
/* 117:138 */     drawCenteredString(this.fontRendererObj, this.field_146495_a, width / 2, 8, 16777215);
/* 118:139 */     boolean var4 = true;
/* 119:140 */     KeyBinding[] var5 = this.field_146497_i.keyBindings;
/* 120:141 */     int var6 = var5.length;
/* 121:143 */     for (int var7 = 0; var7 < var6; var7++)
/* 122:    */     {
/* 123:145 */       KeyBinding var8 = var5[var7];
/* 124:147 */       if (var8.getKeyCode() != var8.getKeyCodeDefault())
/* 125:    */       {
/* 126:149 */         var4 = false;
/* 127:150 */         break;
/* 128:    */       }
/* 129:    */     }
/* 130:154 */     this.field_146493_s.enabled = (!var4);
/* 131:155 */     super.drawScreen(par1, par2, par3);
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiControls
 * JD-Core Version:    0.7.0.1
 */
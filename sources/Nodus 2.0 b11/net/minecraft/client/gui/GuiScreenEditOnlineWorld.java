/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.util.List;
/*   5:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.gui.mco.GuiScreenResetWorld;
/*   8:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*   9:    */ import net.minecraft.client.mco.McoClient;
/*  10:    */ import net.minecraft.client.mco.McoServer;
/*  11:    */ import net.minecraft.client.resources.I18n;
/*  12:    */ import net.minecraft.util.Session;
/*  13:    */ import org.apache.logging.log4j.LogManager;
/*  14:    */ import org.apache.logging.log4j.Logger;
/*  15:    */ import org.lwjgl.input.Keyboard;
/*  16:    */ 
/*  17:    */ public class GuiScreenEditOnlineWorld
/*  18:    */   extends GuiScreen
/*  19:    */ {
/*  20: 20 */   private static final Logger logger = ;
/*  21:    */   private GuiScreen field_146855_f;
/*  22:    */   private GuiScreen field_146857_g;
/*  23:    */   private GuiTextField field_146863_h;
/*  24:    */   private GuiTextField field_146864_i;
/*  25:    */   private McoServer field_146861_r;
/*  26:    */   private NodusGuiButton field_146860_s;
/*  27:    */   private int field_146859_t;
/*  28:    */   private int field_146858_u;
/*  29:    */   private int field_146856_v;
/*  30:    */   private GuiScreenOnlineServersSubscreen field_146854_w;
/*  31:    */   private static final String __OBFID = "CL_00000779";
/*  32:    */   
/*  33:    */   public GuiScreenEditOnlineWorld(GuiScreen par1GuiScreen, GuiScreen par2GuiScreen, McoServer par3McoServer)
/*  34:    */   {
/*  35: 35 */     this.field_146855_f = par1GuiScreen;
/*  36: 36 */     this.field_146857_g = par2GuiScreen;
/*  37: 37 */     this.field_146861_r = par3McoServer;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void updateScreen()
/*  41:    */   {
/*  42: 45 */     this.field_146864_i.updateCursorCounter();
/*  43: 46 */     this.field_146863_h.updateCursorCounter();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void initGui()
/*  47:    */   {
/*  48: 54 */     this.field_146859_t = (width / 4);
/*  49: 55 */     this.field_146858_u = (width / 4 - 2);
/*  50: 56 */     this.field_146856_v = (width / 2 + 4);
/*  51: 57 */     Keyboard.enableRepeatEvents(true);
/*  52: 58 */     this.buttonList.clear();
/*  53: 59 */     this.buttonList.add(this.field_146860_s = new NodusGuiButton(0, this.field_146859_t, height / 4 + 120 + 22, this.field_146858_u, 20, I18n.format("mco.configure.world.buttons.done", new Object[0])));
/*  54: 60 */     this.buttonList.add(new NodusGuiButton(1, this.field_146856_v, height / 4 + 120 + 22, this.field_146858_u, 20, I18n.format("gui.cancel", new Object[0])));
/*  55: 61 */     this.field_146864_i = new GuiTextField(this.fontRendererObj, this.field_146859_t, 56, 212, 20);
/*  56: 62 */     this.field_146864_i.setFocused(true);
/*  57: 63 */     this.field_146864_i.func_146203_f(32);
/*  58: 64 */     this.field_146864_i.setText(this.field_146861_r.func_148801_b());
/*  59: 65 */     this.field_146863_h = new GuiTextField(this.fontRendererObj, this.field_146859_t, 96, 212, 20);
/*  60: 66 */     this.field_146863_h.func_146203_f(32);
/*  61: 67 */     this.field_146863_h.setText(this.field_146861_r.func_148800_a());
/*  62: 68 */     this.field_146854_w = new GuiScreenOnlineServersSubscreen(width, height, this.field_146859_t, 122, this.field_146861_r.field_148820_i, this.field_146861_r.field_148817_j);
/*  63: 69 */     this.buttonList.addAll(this.field_146854_w.field_148405_a);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void onGuiClosed()
/*  67:    */   {
/*  68: 77 */     Keyboard.enableRepeatEvents(false);
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  72:    */   {
/*  73: 82 */     if (p_146284_1_.enabled) {
/*  74: 84 */       if (p_146284_1_.id == 1) {
/*  75: 86 */         this.mc.displayGuiScreen(this.field_146855_f);
/*  76: 88 */       } else if (p_146284_1_.id == 0) {
/*  77: 90 */         func_146853_g();
/*  78: 92 */       } else if (p_146284_1_.id == 2) {
/*  79: 94 */         this.mc.displayGuiScreen(new GuiScreenResetWorld(this, this.field_146861_r));
/*  80:    */       } else {
/*  81: 98 */         this.field_146854_w.func_148397_a(p_146284_1_);
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void func_146853_g()
/*  87:    */   {
/*  88:105 */     Session var1 = this.mc.getSession();
/*  89:106 */     McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/*  90:    */     try
/*  91:    */     {
/*  92:110 */       String var3 = (this.field_146863_h.getText() != null) && (!this.field_146863_h.getText().trim().equals("")) ? this.field_146863_h.getText() : null;
/*  93:111 */       var2.func_148689_a(this.field_146861_r.field_148812_a, this.field_146864_i.getText(), var3, this.field_146854_w.field_148402_e, this.field_146854_w.field_148399_f);
/*  94:112 */       this.field_146861_r.func_148803_a(this.field_146864_i.getText());
/*  95:113 */       this.field_146861_r.func_148804_b(this.field_146863_h.getText());
/*  96:114 */       this.field_146861_r.field_148820_i = this.field_146854_w.field_148402_e;
/*  97:115 */       this.field_146861_r.field_148817_j = this.field_146854_w.field_148399_f;
/*  98:116 */       this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this.field_146857_g, this.field_146861_r));
/*  99:    */     }
/* 100:    */     catch (ExceptionMcoService var4)
/* 101:    */     {
/* 102:120 */       logger.error("Couldn't edit world");
/* 103:    */     }
/* 104:    */     catch (UnsupportedEncodingException var5)
/* 105:    */     {
/* 106:124 */       logger.error("Couldn't edit world");
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void keyTyped(char par1, int par2)
/* 111:    */   {
/* 112:133 */     this.field_146864_i.textboxKeyTyped(par1, par2);
/* 113:134 */     this.field_146863_h.textboxKeyTyped(par1, par2);
/* 114:136 */     if (par2 == 15)
/* 115:    */     {
/* 116:138 */       this.field_146864_i.setFocused(!this.field_146864_i.isFocused());
/* 117:139 */       this.field_146863_h.setFocused(!this.field_146863_h.isFocused());
/* 118:    */     }
/* 119:142 */     if ((par2 == 28) || (par2 == 156)) {
/* 120:144 */       func_146853_g();
/* 121:    */     }
/* 122:147 */     this.field_146860_s.enabled = ((this.field_146864_i.getText() != null) && (!this.field_146864_i.getText().trim().equals("")));
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 126:    */   {
/* 127:155 */     super.mouseClicked(par1, par2, par3);
/* 128:156 */     this.field_146863_h.mouseClicked(par1, par2, par3);
/* 129:157 */     this.field_146864_i.mouseClicked(par1, par2, par3);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void drawScreen(int par1, int par2, float par3)
/* 133:    */   {
/* 134:165 */     drawDefaultBackground();
/* 135:166 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.configure.world.edit.title", new Object[0]), width / 2, 17, 16777215);
/* 136:167 */     drawString(this.fontRendererObj, I18n.format("mco.configure.world.name", new Object[0]), this.field_146859_t, 43, 10526880);
/* 137:168 */     drawString(this.fontRendererObj, I18n.format("mco.configure.world.description", new Object[0]), this.field_146859_t, 84, 10526880);
/* 138:169 */     this.field_146864_i.drawTextBox();
/* 139:170 */     this.field_146863_h.drawTextBox();
/* 140:171 */     this.field_146854_w.func_148394_a(this, this.fontRendererObj);
/* 141:172 */     super.drawScreen(par1, par2, par3);
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenEditOnlineWorld
 * JD-Core Version:    0.7.0.1
 */
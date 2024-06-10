/*   1:    */ package net.minecraft.client.gui.mco;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.gui.GuiScreen;
/*   7:    */ import net.minecraft.client.gui.GuiScreenConfirmation;
/*   8:    */ import net.minecraft.client.gui.GuiScreenConfirmation.ConfirmationType;
/*   9:    */ import net.minecraft.client.gui.GuiScreenLongRunningTask;
/*  10:    */ import net.minecraft.client.gui.GuiTextField;
/*  11:    */ import net.minecraft.client.gui.TaskLongRunning;
/*  12:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  13:    */ import net.minecraft.client.mco.McoClient;
/*  14:    */ import net.minecraft.client.mco.McoServer;
/*  15:    */ import net.minecraft.client.mco.WorldTemplate;
/*  16:    */ import net.minecraft.client.resources.I18n;
/*  17:    */ import net.minecraft.util.Session;
/*  18:    */ import org.apache.logging.log4j.LogManager;
/*  19:    */ import org.apache.logging.log4j.Logger;
/*  20:    */ import org.lwjgl.input.Keyboard;
/*  21:    */ 
/*  22:    */ public class GuiScreenResetWorld
/*  23:    */   extends ScreenWithCallback
/*  24:    */ {
/*  25: 23 */   private static final Logger logger = ;
/*  26:    */   private GuiScreen field_146742_f;
/*  27:    */   private McoServer field_146743_g;
/*  28:    */   private GuiTextField field_146749_h;
/*  29: 27 */   private final int field_146750_i = 1;
/*  30: 28 */   private final int field_146747_r = 2;
/*  31: 29 */   private static int field_146746_s = 3;
/*  32:    */   private WorldTemplate field_146745_t;
/*  33:    */   private NodusGuiButton field_146744_u;
/*  34:    */   private static final String __OBFID = "CL_00000810";
/*  35:    */   
/*  36:    */   public GuiScreenResetWorld(GuiScreen par1GuiScreen, McoServer par2McoServer)
/*  37:    */   {
/*  38: 36 */     this.field_146742_f = par1GuiScreen;
/*  39: 37 */     this.field_146743_g = par2McoServer;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void updateScreen()
/*  43:    */   {
/*  44: 45 */     this.field_146749_h.updateCursorCounter();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void initGui()
/*  48:    */   {
/*  49: 53 */     Keyboard.enableRepeatEvents(true);
/*  50: 54 */     this.buttonList.clear();
/*  51: 55 */     this.buttonList.add(this.field_146744_u = new NodusGuiButton(1, width / 2 - 100, height / 4 + 120 + 12, 97, 20, I18n.format("mco.configure.world.buttons.reset", new Object[0])));
/*  52: 56 */     this.buttonList.add(new NodusGuiButton(2, width / 2 + 5, height / 4 + 120 + 12, 97, 20, I18n.format("gui.cancel", new Object[0])));
/*  53: 57 */     this.field_146749_h = new GuiTextField(this.fontRendererObj, width / 2 - 100, 99, 200, 20);
/*  54: 58 */     this.field_146749_h.setFocused(true);
/*  55: 59 */     this.field_146749_h.func_146203_f(32);
/*  56: 60 */     this.field_146749_h.setText("");
/*  57: 62 */     if (this.field_146745_t == null)
/*  58:    */     {
/*  59: 64 */       this.buttonList.add(new NodusGuiButton(field_146746_s, width / 2 - 100, 125, 200, 20, I18n.format("mco.template.default.name", new Object[0])));
/*  60:    */     }
/*  61:    */     else
/*  62:    */     {
/*  63: 68 */       this.field_146749_h.setText("");
/*  64: 69 */       this.field_146749_h.func_146184_c(false);
/*  65: 70 */       this.field_146749_h.setFocused(false);
/*  66: 71 */       this.buttonList.add(new NodusGuiButton(field_146746_s, width / 2 - 100, 125, 200, 20, I18n.format("mco.template.name", new Object[0]) + ": " + this.field_146745_t.field_148785_b));
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void onGuiClosed()
/*  71:    */   {
/*  72: 80 */     Keyboard.enableRepeatEvents(false);
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected void keyTyped(char par1, int par2)
/*  76:    */   {
/*  77: 88 */     this.field_146749_h.textboxKeyTyped(par1, par2);
/*  78: 90 */     if ((par2 == 28) || (par2 == 156)) {
/*  79: 92 */       actionPerformed(this.field_146744_u);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  84:    */   {
/*  85: 98 */     if (p_146284_1_.enabled) {
/*  86:100 */       if (p_146284_1_.id == 2)
/*  87:    */       {
/*  88:102 */         this.mc.displayGuiScreen(this.field_146742_f);
/*  89:    */       }
/*  90:104 */       else if (p_146284_1_.id == 1)
/*  91:    */       {
/*  92:106 */         String var2 = I18n.format("mco.configure.world.reset.question.line1", new Object[0]);
/*  93:107 */         String var3 = I18n.format("mco.configure.world.reset.question.line2", new Object[0]);
/*  94:108 */         this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Warning, var2, var3, 1));
/*  95:    */       }
/*  96:110 */       else if (p_146284_1_.id == field_146746_s)
/*  97:    */       {
/*  98:112 */         this.mc.displayGuiScreen(new GuiScreenMcoWorldTemplate(this, this.field_146745_t));
/*  99:    */       }
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void confirmClicked(boolean par1, int par2)
/* 104:    */   {
/* 105:119 */     if ((par1) && (par2 == 1)) {
/* 106:121 */       func_146741_h();
/* 107:    */     } else {
/* 108:125 */       this.mc.displayGuiScreen(this);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void func_146741_h()
/* 113:    */   {
/* 114:131 */     ResetWorldTask var1 = new ResetWorldTask(this.field_146743_g.field_148812_a, this.field_146749_h.getText(), this.field_146745_t);
/* 115:132 */     GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(this.mc, this.field_146742_f, var1);
/* 116:133 */     var2.func_146902_g();
/* 117:134 */     this.mc.displayGuiScreen(var2);
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 121:    */   {
/* 122:142 */     super.mouseClicked(par1, par2, par3);
/* 123:143 */     this.field_146749_h.mouseClicked(par1, par2, par3);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void drawScreen(int par1, int par2, float par3)
/* 127:    */   {
/* 128:151 */     drawDefaultBackground();
/* 129:152 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.reset.world.title", new Object[0]), width / 2, 17, 16777215);
/* 130:153 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.reset.world.warning", new Object[0]), width / 2, 56, 16711680);
/* 131:154 */     drawString(this.fontRendererObj, I18n.format("mco.reset.world.seed", new Object[0]), width / 2 - 100, 86, 10526880);
/* 132:155 */     this.field_146749_h.drawTextBox();
/* 133:156 */     super.drawScreen(par1, par2, par3);
/* 134:    */   }
/* 135:    */   
/* 136:    */   void func_146740_a(WorldTemplate p_146740_1_)
/* 137:    */   {
/* 138:161 */     this.field_146745_t = p_146740_1_;
/* 139:    */   }
/* 140:    */   
/* 141:    */   void func_146735_a(Object p_146735_1_)
/* 142:    */   {
/* 143:166 */     func_146740_a((WorldTemplate)p_146735_1_);
/* 144:    */   }
/* 145:    */   
/* 146:    */   class ResetWorldTask
/* 147:    */     extends TaskLongRunning
/* 148:    */   {
/* 149:    */     private final long field_148422_c;
/* 150:    */     private final String field_148420_d;
/* 151:    */     private final WorldTemplate field_148421_e;
/* 152:    */     private static final String __OBFID = "CL_00000811";
/* 153:    */     
/* 154:    */     public ResetWorldTask(long par2, String par4Str, WorldTemplate par5WorldTemplate)
/* 155:    */     {
/* 156:178 */       this.field_148422_c = par2;
/* 157:179 */       this.field_148420_d = par4Str;
/* 158:180 */       this.field_148421_e = par5WorldTemplate;
/* 159:    */     }
/* 160:    */     
/* 161:    */     public void run()
/* 162:    */     {
/* 163:185 */       Session var1 = GuiScreenResetWorld.this.mc.getSession();
/* 164:186 */       McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 165:187 */       String var3 = I18n.format("mco.reset.world.resetting.screen.title", new Object[0]);
/* 166:188 */       func_148417_b(var3);
/* 167:    */       try
/* 168:    */       {
/* 169:192 */         if (func_148418_c()) {
/* 170:194 */           return;
/* 171:    */         }
/* 172:197 */         if (this.field_148421_e != null) {
/* 173:199 */           var2.func_148696_e(this.field_148422_c, this.field_148421_e.field_148787_a);
/* 174:    */         } else {
/* 175:203 */           var2.func_148699_d(this.field_148422_c, this.field_148420_d);
/* 176:    */         }
/* 177:206 */         if (func_148418_c()) {
/* 178:208 */           return;
/* 179:    */         }
/* 180:211 */         GuiScreenResetWorld.this.mc.displayGuiScreen(GuiScreenResetWorld.this.field_146742_f);
/* 181:    */       }
/* 182:    */       catch (ExceptionMcoService var5)
/* 183:    */       {
/* 184:215 */         if (func_148418_c()) {
/* 185:217 */           return;
/* 186:    */         }
/* 187:220 */         GuiScreenResetWorld.logger.error("Couldn't reset world");
/* 188:221 */         func_148416_a(var5.toString());
/* 189:    */       }
/* 190:    */       catch (Exception var6)
/* 191:    */       {
/* 192:225 */         if (func_148418_c()) {
/* 193:227 */           return;
/* 194:    */         }
/* 195:230 */         GuiScreenResetWorld.logger.error("Couldn't reset world");
/* 196:231 */         func_148416_a(var6.toString());
/* 197:    */       }
/* 198:    */     }
/* 199:    */   }
/* 200:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.mco.GuiScreenResetWorld
 * JD-Core Version:    0.7.0.1
 */
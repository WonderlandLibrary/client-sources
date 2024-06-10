/*   1:    */ package net.minecraft.client.gui.mco;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.List;
/*   8:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   9:    */ import net.minecraft.client.Minecraft;
/*  10:    */ import net.minecraft.client.gui.GuiScreen;
/*  11:    */ import net.minecraft.client.gui.GuiScreenLongRunningTask;
/*  12:    */ import net.minecraft.client.gui.GuiTextField;
/*  13:    */ import net.minecraft.client.gui.TaskLongRunning;
/*  14:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  15:    */ import net.minecraft.client.mco.McoClient;
/*  16:    */ import net.minecraft.client.mco.WorldTemplate;
/*  17:    */ import net.minecraft.client.resources.I18n;
/*  18:    */ import net.minecraft.util.Session;
/*  19:    */ import org.apache.logging.log4j.LogManager;
/*  20:    */ import org.apache.logging.log4j.Logger;
/*  21:    */ import org.lwjgl.input.Keyboard;
/*  22:    */ 
/*  23:    */ public class GuiScreenCreateOnlineWorld
/*  24:    */   extends ScreenWithCallback
/*  25:    */ {
/*  26: 26 */   private static final Logger logger = ;
/*  27:    */   private GuiScreen field_146758_f;
/*  28:    */   private GuiTextField field_146760_g;
/*  29:    */   private String field_146766_h;
/*  30: 30 */   private static int field_146767_i = 0;
/*  31: 31 */   private static int field_146764_r = 1;
/*  32: 32 */   private static int field_146763_s = 2;
/*  33:    */   private boolean field_146762_t;
/*  34: 34 */   private String field_146761_u = "You must enter a name!";
/*  35:    */   private WorldTemplate field_146759_v;
/*  36:    */   private static final String __OBFID = "CL_00000776";
/*  37:    */   
/*  38:    */   public GuiScreenCreateOnlineWorld(GuiScreen par1GuiScreen)
/*  39:    */   {
/*  40: 40 */     this.buttonList = Collections.synchronizedList(new ArrayList());
/*  41: 41 */     this.field_146758_f = par1GuiScreen;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void updateScreen()
/*  45:    */   {
/*  46: 49 */     this.field_146760_g.updateCursorCounter();
/*  47: 50 */     this.field_146766_h = this.field_146760_g.getText();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void initGui()
/*  51:    */   {
/*  52: 58 */     Keyboard.enableRepeatEvents(true);
/*  53: 59 */     this.buttonList.clear();
/*  54: 60 */     this.buttonList.add(new NodusGuiButton(field_146767_i, width / 2 - 100, height / 4 + 120 + 17, 97, 20, I18n.format("mco.create.world", new Object[0])));
/*  55: 61 */     this.buttonList.add(new NodusGuiButton(field_146764_r, width / 2 + 5, height / 4 + 120 + 17, 95, 20, I18n.format("gui.cancel", new Object[0])));
/*  56: 62 */     this.field_146760_g = new GuiTextField(this.fontRendererObj, width / 2 - 100, 65, 200, 20);
/*  57: 63 */     this.field_146760_g.setFocused(true);
/*  58: 65 */     if (this.field_146766_h != null) {
/*  59: 67 */       this.field_146760_g.setText(this.field_146766_h);
/*  60:    */     }
/*  61: 70 */     if (this.field_146759_v == null) {
/*  62: 72 */       this.buttonList.add(new NodusGuiButton(field_146763_s, width / 2 - 100, 107, 200, 20, I18n.format("mco.template.default.name", new Object[0])));
/*  63:    */     } else {
/*  64: 76 */       this.buttonList.add(new NodusGuiButton(field_146763_s, width / 2 - 100, 107, 200, 20, I18n.format("mco.template.name", new Object[0]) + ": " + this.field_146759_v.field_148785_b));
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void onGuiClosed()
/*  69:    */   {
/*  70: 85 */     Keyboard.enableRepeatEvents(false);
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  74:    */   {
/*  75: 90 */     if (p_146284_1_.enabled) {
/*  76: 92 */       if (p_146284_1_.id == field_146764_r) {
/*  77: 94 */         this.mc.displayGuiScreen(this.field_146758_f);
/*  78: 96 */       } else if (p_146284_1_.id == field_146767_i) {
/*  79: 98 */         func_146757_h();
/*  80:100 */       } else if (p_146284_1_.id == field_146763_s) {
/*  81:102 */         this.mc.displayGuiScreen(new GuiScreenMcoWorldTemplate(this, this.field_146759_v));
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void func_146757_h()
/*  87:    */   {
/*  88:109 */     if (func_146753_i())
/*  89:    */     {
/*  90:111 */       TaskWorldCreation var1 = new TaskWorldCreation(this.field_146760_g.getText(), this.field_146759_v);
/*  91:112 */       GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(this.mc, this.field_146758_f, var1);
/*  92:113 */       var2.func_146902_g();
/*  93:114 */       this.mc.displayGuiScreen(var2);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   private boolean func_146753_i()
/*  98:    */   {
/*  99:120 */     this.field_146762_t = ((this.field_146760_g.getText() == null) || (this.field_146760_g.getText().trim().equals("")));
/* 100:121 */     return !this.field_146762_t;
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected void keyTyped(char par1, int par2)
/* 104:    */   {
/* 105:129 */     this.field_146760_g.textboxKeyTyped(par1, par2);
/* 106:131 */     if (par2 == 15) {
/* 107:133 */       this.field_146760_g.setFocused(!this.field_146760_g.isFocused());
/* 108:    */     }
/* 109:136 */     if ((par2 == 28) || (par2 == 156)) {
/* 110:138 */       actionPerformed((NodusGuiButton)this.buttonList.get(0));
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 115:    */   {
/* 116:147 */     super.mouseClicked(par1, par2, par3);
/* 117:148 */     this.field_146760_g.mouseClicked(par1, par2, par3);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void drawScreen(int par1, int par2, float par3)
/* 121:    */   {
/* 122:156 */     drawDefaultBackground();
/* 123:157 */     drawCenteredString(this.fontRendererObj, I18n.format("mco.selectServer.create", new Object[0]), width / 2, 11, 16777215);
/* 124:158 */     drawString(this.fontRendererObj, I18n.format("mco.configure.world.name", new Object[0]), width / 2 - 100, 52, 10526880);
/* 125:160 */     if (this.field_146762_t) {
/* 126:162 */       drawCenteredString(this.fontRendererObj, this.field_146761_u, width / 2, 167, 16711680);
/* 127:    */     }
/* 128:165 */     this.field_146760_g.drawTextBox();
/* 129:166 */     super.drawScreen(par1, par2, par3);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void func_146735_a(WorldTemplate p_146756_1_)
/* 133:    */   {
/* 134:171 */     this.field_146759_v = p_146756_1_;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void func_146735_a(Object p_146735_1_)
/* 138:    */   {
/* 139:176 */     func_146735_a((WorldTemplate)p_146735_1_);
/* 140:    */   }
/* 141:    */   
/* 142:    */   class TaskWorldCreation
/* 143:    */     extends TaskLongRunning
/* 144:    */   {
/* 145:    */     private final String field_148427_c;
/* 146:    */     private final WorldTemplate field_148426_d;
/* 147:    */     private static final String __OBFID = "CL_00000777";
/* 148:    */     
/* 149:    */     public TaskWorldCreation(String p_i45036_2_, WorldTemplate p_i45036_3_)
/* 150:    */     {
/* 151:187 */       this.field_148427_c = p_i45036_2_;
/* 152:188 */       this.field_148426_d = p_i45036_3_;
/* 153:    */     }
/* 154:    */     
/* 155:    */     public void run()
/* 156:    */     {
/* 157:193 */       String var1 = I18n.format("mco.create.world.wait", new Object[0]);
/* 158:194 */       func_148417_b(var1);
/* 159:195 */       Session var2 = GuiScreenCreateOnlineWorld.this.mc.getSession();
/* 160:196 */       McoClient var3 = new McoClient(var2.getSessionID(), var2.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 161:    */       try
/* 162:    */       {
/* 163:200 */         if (this.field_148426_d != null) {
/* 164:202 */           var3.func_148707_a(this.field_148427_c, this.field_148426_d.field_148787_a);
/* 165:    */         } else {
/* 166:206 */           var3.func_148707_a(this.field_148427_c, "-1");
/* 167:    */         }
/* 168:209 */         GuiScreenCreateOnlineWorld.this.mc.displayGuiScreen(GuiScreenCreateOnlineWorld.this.field_146758_f);
/* 169:    */       }
/* 170:    */       catch (ExceptionMcoService var5)
/* 171:    */       {
/* 172:213 */         GuiScreenCreateOnlineWorld.logger.error("Couldn't create world");
/* 173:214 */         func_148416_a(var5.toString());
/* 174:    */       }
/* 175:    */       catch (UnsupportedEncodingException var6)
/* 176:    */       {
/* 177:218 */         GuiScreenCreateOnlineWorld.logger.error("Couldn't create world");
/* 178:219 */         func_148416_a(var6.getLocalizedMessage());
/* 179:    */       }
/* 180:    */       catch (IOException var7)
/* 181:    */       {
/* 182:223 */         GuiScreenCreateOnlineWorld.logger.error("Could not parse response creating world");
/* 183:224 */         func_148416_a(var7.getLocalizedMessage());
/* 184:    */       }
/* 185:    */       catch (Exception var8)
/* 186:    */       {
/* 187:228 */         GuiScreenCreateOnlineWorld.logger.error("Could not create world");
/* 188:229 */         func_148416_a(var8.getLocalizedMessage());
/* 189:    */       }
/* 190:    */     }
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.mco.GuiScreenCreateOnlineWorld
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.List;
/*   5:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*   8:    */ import net.minecraft.client.mco.McoClient;
/*   9:    */ import net.minecraft.client.mco.McoServer;
/*  10:    */ import net.minecraft.client.resources.I18n;
/*  11:    */ import net.minecraft.util.Session;
/*  12:    */ import org.apache.logging.log4j.LogManager;
/*  13:    */ import org.apache.logging.log4j.Logger;
/*  14:    */ import org.lwjgl.input.Keyboard;
/*  15:    */ 
/*  16:    */ public class GuiScreenInvite
/*  17:    */   extends GuiScreen
/*  18:    */ {
/*  19: 19 */   private static final Logger logger = ;
/*  20:    */   private GuiTextField field_146921_f;
/*  21:    */   private McoServer field_146923_g;
/*  22:    */   private final GuiScreen field_146929_h;
/*  23:    */   private final GuiScreenConfigureWorld field_146930_i;
/*  24: 24 */   private final int field_146927_r = 0;
/*  25: 25 */   private final int field_146926_s = 1;
/*  26: 26 */   private String field_146925_t = "Could not invite the provided name";
/*  27:    */   private String field_146924_u;
/*  28:    */   private boolean field_146922_v;
/*  29:    */   private static final String __OBFID = "CL_00000780";
/*  30:    */   
/*  31:    */   public GuiScreenInvite(GuiScreen par1GuiScreen, GuiScreenConfigureWorld par2GuiScreenConfigureWorld, McoServer par3McoServer)
/*  32:    */   {
/*  33: 33 */     this.field_146929_h = par1GuiScreen;
/*  34: 34 */     this.field_146930_i = par2GuiScreenConfigureWorld;
/*  35: 35 */     this.field_146923_g = par3McoServer;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void updateScreen()
/*  39:    */   {
/*  40: 43 */     this.field_146921_f.updateCursorCounter();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void initGui()
/*  44:    */   {
/*  45: 51 */     Keyboard.enableRepeatEvents(true);
/*  46: 52 */     this.buttonList.clear();
/*  47: 53 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, height / 4 + 96 + 12, I18n.format("mco.configure.world.buttons.invite", new Object[0])));
/*  48: 54 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*  49: 55 */     this.field_146921_f = new GuiTextField(this.fontRendererObj, width / 2 - 100, 66, 200, 20);
/*  50: 56 */     this.field_146921_f.setFocused(true);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void onGuiClosed()
/*  54:    */   {
/*  55: 64 */     Keyboard.enableRepeatEvents(false);
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  59:    */   {
/*  60: 69 */     if (p_146284_1_.enabled) {
/*  61: 71 */       if (p_146284_1_.id == 1)
/*  62:    */       {
/*  63: 73 */         this.mc.displayGuiScreen(this.field_146930_i);
/*  64:    */       }
/*  65: 75 */       else if (p_146284_1_.id == 0)
/*  66:    */       {
/*  67: 77 */         Session var2 = this.mc.getSession();
/*  68: 78 */         McoClient var3 = new McoClient(var2.getSessionID(), var2.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/*  69: 80 */         if ((this.field_146921_f.getText() == null) || (this.field_146921_f.getText().isEmpty())) {
/*  70: 82 */           return;
/*  71:    */         }
/*  72:    */         try
/*  73:    */         {
/*  74: 87 */           McoServer var4 = var3.func_148697_b(this.field_146923_g.field_148812_a, this.field_146921_f.getText());
/*  75: 89 */           if (var4 != null)
/*  76:    */           {
/*  77: 91 */             this.field_146923_g.field_148806_f = var4.field_148806_f;
/*  78: 92 */             this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this.field_146929_h, this.field_146923_g));
/*  79:    */           }
/*  80:    */           else
/*  81:    */           {
/*  82: 96 */             func_146920_a(this.field_146925_t);
/*  83:    */           }
/*  84:    */         }
/*  85:    */         catch (ExceptionMcoService var5)
/*  86:    */         {
/*  87:101 */           logger.error("Couldn't invite user");
/*  88:102 */           func_146920_a(var5.field_148829_b);
/*  89:    */         }
/*  90:    */         catch (IOException var6)
/*  91:    */         {
/*  92:106 */           logger.error("Couldn't parse response inviting user", var6);
/*  93:107 */           func_146920_a(this.field_146925_t);
/*  94:    */         }
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void func_146920_a(String p_146920_1_)
/* 100:    */   {
/* 101:115 */     this.field_146922_v = true;
/* 102:116 */     this.field_146924_u = p_146920_1_;
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected void keyTyped(char par1, int par2)
/* 106:    */   {
/* 107:124 */     this.field_146921_f.textboxKeyTyped(par1, par2);
/* 108:126 */     if (par2 == 15) {
/* 109:128 */       if (this.field_146921_f.isFocused()) {
/* 110:130 */         this.field_146921_f.setFocused(false);
/* 111:    */       } else {
/* 112:134 */         this.field_146921_f.setFocused(true);
/* 113:    */       }
/* 114:    */     }
/* 115:138 */     if ((par2 == 28) || (par2 == 156)) {
/* 116:140 */       actionPerformed((NodusGuiButton)this.buttonList.get(0));
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 121:    */   {
/* 122:149 */     super.mouseClicked(par1, par2, par3);
/* 123:150 */     this.field_146921_f.mouseClicked(par1, par2, par3);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void drawScreen(int par1, int par2, float par3)
/* 127:    */   {
/* 128:158 */     drawDefaultBackground();
/* 129:159 */     drawString(this.fontRendererObj, I18n.format("mco.configure.world.invite.profile.name", new Object[0]), width / 2 - 100, 53, 10526880);
/* 130:161 */     if (this.field_146922_v) {
/* 131:163 */       drawCenteredString(this.fontRendererObj, this.field_146924_u, width / 2, 100, 16711680);
/* 132:    */     }
/* 133:166 */     this.field_146921_f.drawTextBox();
/* 134:167 */     super.drawScreen(par1, par2, par3);
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenInvite
 * JD-Core Version:    0.7.0.1
 */
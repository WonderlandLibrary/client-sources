/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import io.netty.buffer.Unpooled;
/*   4:    */ import java.util.List;
/*   5:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*   8:    */ import net.minecraft.client.resources.I18n;
/*   9:    */ import net.minecraft.command.server.CommandBlockLogic;
/*  10:    */ import net.minecraft.network.PacketBuffer;
/*  11:    */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*  12:    */ import net.minecraft.util.IChatComponent;
/*  13:    */ import org.apache.logging.log4j.LogManager;
/*  14:    */ import org.apache.logging.log4j.Logger;
/*  15:    */ import org.lwjgl.input.Keyboard;
/*  16:    */ 
/*  17:    */ public class GuiCommandBlock
/*  18:    */   extends GuiScreen
/*  19:    */ {
/*  20: 16 */   private static final Logger field_146488_a = ;
/*  21:    */   private GuiTextField field_146485_f;
/*  22:    */   private GuiTextField field_146486_g;
/*  23:    */   private final CommandBlockLogic field_146489_h;
/*  24:    */   private NodusGuiButton field_146490_i;
/*  25:    */   private NodusGuiButton field_146487_r;
/*  26:    */   private static final String __OBFID = "CL_00000748";
/*  27:    */   
/*  28:    */   public GuiCommandBlock(CommandBlockLogic p_i45032_1_)
/*  29:    */   {
/*  30: 26 */     this.field_146489_h = p_i45032_1_;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void updateScreen()
/*  34:    */   {
/*  35: 34 */     this.field_146485_f.updateCursorCounter();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void initGui()
/*  39:    */   {
/*  40: 42 */     Keyboard.enableRepeatEvents(true);
/*  41: 43 */     this.buttonList.clear();
/*  42: 44 */     this.buttonList.add(this.field_146490_i = new NodusGuiButton(0, width / 2 - 4 - 150, height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
/*  43: 45 */     this.buttonList.add(this.field_146487_r = new NodusGuiButton(1, width / 2 + 4, height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  44: 46 */     this.field_146485_f = new GuiTextField(this.fontRendererObj, width / 2 - 150, 50, 300, 20);
/*  45: 47 */     this.field_146485_f.func_146203_f(32767);
/*  46: 48 */     this.field_146485_f.setFocused(true);
/*  47: 49 */     this.field_146485_f.setText(this.field_146489_h.func_145753_i());
/*  48: 50 */     this.field_146486_g = new GuiTextField(this.fontRendererObj, width / 2 - 150, 135, 300, 20);
/*  49: 51 */     this.field_146486_g.func_146203_f(32767);
/*  50: 52 */     this.field_146486_g.func_146184_c(false);
/*  51: 53 */     this.field_146486_g.setText(this.field_146489_h.func_145753_i());
/*  52: 55 */     if (this.field_146489_h.func_145749_h() != null) {
/*  53: 57 */       this.field_146486_g.setText(this.field_146489_h.func_145749_h().getUnformattedText());
/*  54:    */     }
/*  55: 60 */     this.field_146490_i.enabled = (this.field_146485_f.getText().trim().length() > 0);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void onGuiClosed()
/*  59:    */   {
/*  60: 68 */     Keyboard.enableRepeatEvents(false);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  64:    */   {
/*  65: 73 */     if (p_146284_1_.enabled) {
/*  66: 75 */       if (p_146284_1_.id == 1)
/*  67:    */       {
/*  68: 77 */         this.mc.displayGuiScreen(null);
/*  69:    */       }
/*  70: 79 */       else if (p_146284_1_.id == 0)
/*  71:    */       {
/*  72: 81 */         PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
/*  73:    */         try
/*  74:    */         {
/*  75: 85 */           var2.writeByte(this.field_146489_h.func_145751_f());
/*  76: 86 */           this.field_146489_h.func_145757_a(var2);
/*  77: 87 */           var2.writeStringToBuffer(this.field_146485_f.getText());
/*  78: 88 */           this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", var2));
/*  79:    */         }
/*  80:    */         catch (Exception var7)
/*  81:    */         {
/*  82: 92 */           field_146488_a.error("Couldn't send command block info", var7);
/*  83:    */         }
/*  84:    */         finally
/*  85:    */         {
/*  86: 96 */           var2.release();
/*  87:    */         }
/*  88: 99 */         this.mc.displayGuiScreen(null);
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected void keyTyped(char par1, int par2)
/*  94:    */   {
/*  95:109 */     this.field_146485_f.textboxKeyTyped(par1, par2);
/*  96:110 */     this.field_146486_g.textboxKeyTyped(par1, par2);
/*  97:111 */     this.field_146490_i.enabled = (this.field_146485_f.getText().trim().length() > 0);
/*  98:113 */     if ((par2 != 28) && (par2 != 156))
/*  99:    */     {
/* 100:115 */       if (par2 == 1) {
/* 101:117 */         actionPerformed(this.field_146487_r);
/* 102:    */       }
/* 103:    */     }
/* 104:    */     else {
/* 105:122 */       actionPerformed(this.field_146490_i);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 110:    */   {
/* 111:131 */     super.mouseClicked(par1, par2, par3);
/* 112:132 */     this.field_146485_f.mouseClicked(par1, par2, par3);
/* 113:133 */     this.field_146486_g.mouseClicked(par1, par2, par3);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void drawScreen(int par1, int par2, float par3)
/* 117:    */   {
/* 118:141 */     drawDefaultBackground();
/* 119:142 */     drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), width / 2, 20, 16777215);
/* 120:143 */     drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), width / 2 - 150, 37, 10526880);
/* 121:144 */     this.field_146485_f.drawTextBox();
/* 122:145 */     byte var4 = 75;
/* 123:146 */     byte var5 = 0;
/* 124:147 */     FontRenderer var10001 = this.fontRendererObj;
/* 125:148 */     String var10002 = I18n.format("advMode.nearestPlayer", new Object[0]);
/* 126:149 */     int var10003 = width / 2 - 150;
/* 127:150 */     int var7 = var5 + 1;
/* 128:151 */     drawString(var10001, var10002, var10003, var4 + var5 * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 129:152 */     drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), width / 2 - 150, var4 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 130:153 */     drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), width / 2 - 150, var4 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 131:155 */     if (this.field_146486_g.getText().length() > 0)
/* 132:    */     {
/* 133:157 */       int var6 = var4 + var7 * this.fontRendererObj.FONT_HEIGHT + 20;
/* 134:158 */       drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), width / 2 - 150, var6, 10526880);
/* 135:159 */       this.field_146486_g.drawTextBox();
/* 136:    */     }
/* 137:162 */     super.drawScreen(par1, par2, par3);
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiCommandBlock
 * JD-Core Version:    0.7.0.1
 */
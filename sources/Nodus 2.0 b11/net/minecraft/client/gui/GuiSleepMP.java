/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  5:   */ import net.minecraft.client.Minecraft;
/*  6:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  7:   */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  8:   */ import net.minecraft.client.resources.I18n;
/*  9:   */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/* 10:   */ 
/* 11:   */ public class GuiSleepMP
/* 12:   */   extends GuiChat
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000697";
/* 15:   */   
/* 16:   */   public void initGui()
/* 17:   */   {
/* 18:17 */     super.initGui();
/* 19:18 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected void keyTyped(char par1, int par2)
/* 23:   */   {
/* 24:26 */     if (par2 == 1)
/* 25:   */     {
/* 26:28 */       func_146418_g();
/* 27:   */     }
/* 28:30 */     else if ((par2 != 28) && (par2 != 156))
/* 29:   */     {
/* 30:32 */       super.keyTyped(par1, par2);
/* 31:   */     }
/* 32:   */     else
/* 33:   */     {
/* 34:36 */       String var3 = this.field_146415_a.getText().trim();
/* 35:38 */       if (!var3.isEmpty()) {
/* 36:40 */         this.mc.thePlayer.sendChatMessage(var3);
/* 37:   */       }
/* 38:43 */       this.field_146415_a.setText("");
/* 39:44 */       this.mc.ingameGUI.getChatGUI().resetScroll();
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 44:   */   {
/* 45:50 */     if (p_146284_1_.id == 1) {
/* 46:52 */       func_146418_g();
/* 47:   */     } else {
/* 48:56 */       super.actionPerformed(p_146284_1_);
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   private void func_146418_g()
/* 53:   */   {
/* 54:62 */     NetHandlerPlayClient var1 = this.mc.thePlayer.sendQueue;
/* 55:63 */     var1.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, 3));
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiSleepMP
 * JD-Core Version:    0.7.0.1
 */
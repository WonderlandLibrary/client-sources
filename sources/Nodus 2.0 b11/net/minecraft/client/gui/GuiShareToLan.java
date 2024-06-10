/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.resources.I18n;
/*   7:    */ import net.minecraft.server.integrated.IntegratedServer;
/*   8:    */ import net.minecraft.util.ChatComponentText;
/*   9:    */ import net.minecraft.util.ChatComponentTranslation;
/*  10:    */ import net.minecraft.util.IChatComponent;
/*  11:    */ import net.minecraft.world.WorldSettings.GameType;
/*  12:    */ 
/*  13:    */ public class GuiShareToLan
/*  14:    */   extends GuiScreen
/*  15:    */ {
/*  16:    */   private final GuiScreen field_146598_a;
/*  17:    */   private NodusGuiButton field_146596_f;
/*  18:    */   private NodusGuiButton field_146597_g;
/*  19: 15 */   private String field_146599_h = "survival";
/*  20:    */   private boolean field_146600_i;
/*  21:    */   private static final String __OBFID = "CL_00000713";
/*  22:    */   
/*  23:    */   public GuiShareToLan(GuiScreen par1GuiScreen)
/*  24:    */   {
/*  25: 21 */     this.field_146598_a = par1GuiScreen;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void initGui()
/*  29:    */   {
/*  30: 29 */     this.buttonList.clear();
/*  31: 30 */     this.buttonList.add(new NodusGuiButton(101, width / 2 - 155, height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
/*  32: 31 */     this.buttonList.add(new NodusGuiButton(102, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  33: 32 */     this.buttonList.add(this.field_146597_g = new NodusGuiButton(104, width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/*  34: 33 */     this.buttonList.add(this.field_146596_f = new NodusGuiButton(103, width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/*  35: 34 */     func_146595_g();
/*  36:    */   }
/*  37:    */   
/*  38:    */   private void func_146595_g()
/*  39:    */   {
/*  40: 39 */     this.field_146597_g.displayString = (I18n.format("selectWorld.gameMode", new Object[0]) + " " + I18n.format(new StringBuilder("selectWorld.gameMode.").append(this.field_146599_h).toString(), new Object[0]));
/*  41: 40 */     this.field_146596_f.displayString = (I18n.format("selectWorld.allowCommands", new Object[0]) + " ");
/*  42: 42 */     if (this.field_146600_i) {
/*  43: 44 */       this.field_146596_f.displayString += I18n.format("options.on", new Object[0]);
/*  44:    */     } else {
/*  45: 48 */       this.field_146596_f.displayString += I18n.format("options.off", new Object[0]);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  50:    */   {
/*  51: 54 */     if (p_146284_1_.id == 102)
/*  52:    */     {
/*  53: 56 */       this.mc.displayGuiScreen(this.field_146598_a);
/*  54:    */     }
/*  55: 58 */     else if (p_146284_1_.id == 104)
/*  56:    */     {
/*  57: 60 */       if (this.field_146599_h.equals("survival")) {
/*  58: 62 */         this.field_146599_h = "creative";
/*  59: 64 */       } else if (this.field_146599_h.equals("creative")) {
/*  60: 66 */         this.field_146599_h = "adventure";
/*  61:    */       } else {
/*  62: 70 */         this.field_146599_h = "survival";
/*  63:    */       }
/*  64: 73 */       func_146595_g();
/*  65:    */     }
/*  66: 75 */     else if (p_146284_1_.id == 103)
/*  67:    */     {
/*  68: 77 */       this.field_146600_i = (!this.field_146600_i);
/*  69: 78 */       func_146595_g();
/*  70:    */     }
/*  71: 80 */     else if (p_146284_1_.id == 101)
/*  72:    */     {
/*  73: 82 */       this.mc.displayGuiScreen(null);
/*  74: 83 */       String var2 = this.mc.getIntegratedServer().shareToLAN(WorldSettings.GameType.getByName(this.field_146599_h), this.field_146600_i);
/*  75:    */       Object var3;
/*  76:    */       Object var3;
/*  77: 86 */       if (var2 != null) {
/*  78: 88 */         var3 = new ChatComponentTranslation("commands.publish.started", new Object[] { var2 });
/*  79:    */       } else {
/*  80: 92 */         var3 = new ChatComponentText("commands.publish.failed");
/*  81:    */       }
/*  82: 95 */       this.mc.ingameGUI.getChatGUI().func_146227_a((IChatComponent)var3);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void drawScreen(int par1, int par2, float par3)
/*  87:    */   {
/*  88:104 */     drawDefaultBackground();
/*  89:105 */     drawCenteredString(this.fontRendererObj, I18n.format("lanServer.title", new Object[0]), width / 2, 50, 16777215);
/*  90:106 */     drawCenteredString(this.fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), width / 2, 82, 16777215);
/*  91:107 */     super.drawScreen(par1, par2, par3);
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiShareToLan
 * JD-Core Version:    0.7.0.1
 */
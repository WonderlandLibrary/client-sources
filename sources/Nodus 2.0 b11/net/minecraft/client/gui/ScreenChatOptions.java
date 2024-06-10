/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.resources.I18n;
/*   7:    */ import net.minecraft.client.settings.GameSettings;
/*   8:    */ import net.minecraft.client.settings.GameSettings.Options;
/*   9:    */ 
/*  10:    */ public class ScreenChatOptions
/*  11:    */   extends GuiScreen
/*  12:    */ {
/*  13:  9 */   private static final GameSettings.Options[] field_146399_a = { GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH };
/*  14: 10 */   private static final GameSettings.Options[] field_146395_f = { GameSettings.Options.SHOW_CAPE };
/*  15:    */   private final GuiScreen field_146396_g;
/*  16:    */   private final GameSettings field_146400_h;
/*  17:    */   private String field_146401_i;
/*  18:    */   private String field_146398_r;
/*  19:    */   private int field_146397_s;
/*  20:    */   private static final String __OBFID = "CL_00000681";
/*  21:    */   
/*  22:    */   public ScreenChatOptions(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
/*  23:    */   {
/*  24: 20 */     this.field_146396_g = par1GuiScreen;
/*  25: 21 */     this.field_146400_h = par2GameSettings;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void initGui()
/*  29:    */   {
/*  30: 29 */     int var1 = 0;
/*  31: 30 */     this.field_146401_i = I18n.format("options.chat.title", new Object[0]);
/*  32: 31 */     this.field_146398_r = I18n.format("options.multiplayer.title", new Object[0]);
/*  33: 32 */     GameSettings.Options[] var2 = field_146399_a;
/*  34: 33 */     int var3 = var2.length;
/*  35: 37 */     for (int var4 = 0; var4 < var3; var4++)
/*  36:    */     {
/*  37: 39 */       GameSettings.Options var5 = var2[var4];
/*  38: 41 */       if (var5.getEnumFloat()) {
/*  39: 43 */         this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5));
/*  40:    */       } else {
/*  41: 47 */         this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5, this.field_146400_h.getKeyBinding(var5)));
/*  42:    */       }
/*  43: 50 */       var1++;
/*  44:    */     }
/*  45: 53 */     if (var1 % 2 == 1) {
/*  46: 55 */       var1++;
/*  47:    */     }
/*  48: 58 */     this.field_146397_s = (height / 6 + 24 * (var1 >> 1));
/*  49: 59 */     var1 += 2;
/*  50: 60 */     var2 = field_146395_f;
/*  51: 61 */     var3 = var2.length;
/*  52: 63 */     for (var4 = 0; var4 < var3; var4++)
/*  53:    */     {
/*  54: 65 */       GameSettings.Options var5 = var2[var4];
/*  55: 67 */       if (var5.getEnumFloat()) {
/*  56: 69 */         this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5));
/*  57:    */       } else {
/*  58: 73 */         this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, height / 6 + 24 * (var1 >> 1), var5, this.field_146400_h.getKeyBinding(var5)));
/*  59:    */       }
/*  60: 76 */       var1++;
/*  61:    */     }
/*  62: 79 */     this.buttonList.add(new NodusGuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  66:    */   {
/*  67: 84 */     if (p_146284_1_.enabled)
/*  68:    */     {
/*  69: 86 */       if ((p_146284_1_.id < 100) && ((p_146284_1_ instanceof GuiOptionButton)))
/*  70:    */       {
/*  71: 88 */         this.field_146400_h.setOptionValue(((GuiOptionButton)p_146284_1_).func_146136_c(), 1);
/*  72: 89 */         p_146284_1_.displayString = this.field_146400_h.getKeyBinding(GameSettings.Options.getEnumOptions(p_146284_1_.id));
/*  73:    */       }
/*  74: 92 */       if (p_146284_1_.id == 200)
/*  75:    */       {
/*  76: 94 */         this.mc.gameSettings.saveOptions();
/*  77: 95 */         this.mc.displayGuiScreen(this.field_146396_g);
/*  78:    */       }
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void drawScreen(int par1, int par2, float par3)
/*  83:    */   {
/*  84:105 */     drawDefaultBackground();
/*  85:106 */     drawCenteredString(this.fontRendererObj, this.field_146401_i, width / 2, 20, 16777215);
/*  86:107 */     drawCenteredString(this.fontRendererObj, this.field_146398_r, width / 2, this.field_146397_s + 7, 16777215);
/*  87:108 */     super.drawScreen(par1, par2, par3);
/*  88:    */   }
/*  89:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.ScreenChatOptions
 * JD-Core Version:    0.7.0.1
 */
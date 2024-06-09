/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class ScreenChatOptions
/*    */   extends GuiScreen {
/*  9 */   private static final GameSettings.Options[] field_146399_a = new GameSettings.Options[] { GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH, GameSettings.Options.REDUCED_DEBUG_INFO };
/*    */   
/*    */   private final GuiScreen parentScreen;
/*    */   private final GameSettings game_settings;
/*    */   private String field_146401_i;
/*    */   
/*    */   public ScreenChatOptions(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
/* 16 */     this.parentScreen = parentScreenIn;
/* 17 */     this.game_settings = gameSettingsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 26 */     int i = 0;
/* 27 */     this.field_146401_i = I18n.format("options.chat.title", new Object[0]); byte b; int j;
/*    */     GameSettings.Options[] arrayOfOptions;
/* 29 */     for (j = (arrayOfOptions = field_146399_a).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/*    */       
/* 31 */       if (gamesettings$options.getEnumFloat()) {
/*    */         
/* 33 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), gamesettings$options));
/*    */       }
/*    */       else {
/*    */         
/* 37 */         this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), gamesettings$options, this.game_settings.getKeyBinding(gamesettings$options)));
/*    */       } 
/*    */       
/* 40 */       i++;
/*    */       b++; }
/*    */     
/* 43 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 120, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 51 */     if (button.enabled) {
/*    */       
/* 53 */       if (button.id < 100 && button instanceof GuiOptionButton) {
/*    */         
/* 55 */         this.game_settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/* 56 */         button.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*    */       } 
/*    */       
/* 59 */       if (button.id == 200) {
/*    */         
/* 61 */         this.mc.gameSettings.saveOptions();
/* 62 */         this.mc.displayGuiScreen(this.parentScreen);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 72 */     drawDefaultBackground();
/* 73 */     drawCenteredString(this.fontRendererObj, this.field_146401_i, this.width / 2, 20, 16777215);
/* 74 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\ScreenChatOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
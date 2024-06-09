/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.Language;
/*     */ import net.minecraft.client.resources.LanguageManager;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiLanguage
/*     */   extends GuiScreen
/*     */ {
/*     */   protected GuiScreen parentScreen;
/*     */   private List list;
/*     */   private final GameSettings game_settings_3;
/*     */   private final LanguageManager languageManager;
/*     */   private GuiOptionButton forceUnicodeFontBtn;
/*     */   private GuiOptionButton confirmSettingsBtn;
/*     */   
/*     */   public GuiLanguage(GuiScreen screen, GameSettings gameSettingsObj, LanguageManager manager) {
/*  37 */     this.parentScreen = screen;
/*  38 */     this.game_settings_3 = gameSettingsObj;
/*  39 */     this.languageManager = manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  48 */     this.buttonList.add(this.forceUnicodeFontBtn = new GuiOptionButton(100, this.width / 2 - 155, this.height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
/*  49 */     this.buttonList.add(this.confirmSettingsBtn = new GuiOptionButton(6, this.width / 2 - 155 + 160, this.height - 38, I18n.format("gui.done", new Object[0])));
/*  50 */     this.list = new List(this.mc);
/*  51 */     this.list.registerScrollButtons(7, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  59 */     super.handleMouseInput();
/*  60 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  68 */     if (button.enabled) {
/*     */       
/*  70 */       switch (button.id) {
/*     */         case 5:
/*     */           return;
/*     */ 
/*     */         
/*     */         case 6:
/*  76 */           this.mc.displayGuiScreen(this.parentScreen);
/*     */ 
/*     */         
/*     */         case 100:
/*  80 */           if (button instanceof GuiOptionButton) {
/*     */             
/*  82 */             this.game_settings_3.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/*  83 */             button.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
/*  84 */             ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  85 */             int i = scaledresolution.getScaledWidth();
/*  86 */             int j = scaledresolution.getScaledHeight();
/*  87 */             setWorldAndResolution(this.mc, i, j);
/*     */           } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  93 */       this.list.actionPerformed(button);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 103 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/* 104 */     drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), this.width / 2, 16, 16777215);
/* 105 */     drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", this.width / 2, this.height - 56, 8421504);
/* 106 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class List
/*     */     extends GuiSlot {
/* 111 */     private final java.util.List<String> langCodeList = Lists.newArrayList();
/* 112 */     private final Map<String, Language> languageMap = Maps.newHashMap();
/*     */ 
/*     */     
/*     */     public List(Minecraft mcIn) {
/* 116 */       super(mcIn, GuiLanguage.this.width, GuiLanguage.this.height, 32, GuiLanguage.this.height - 65 + 4, 18);
/*     */       
/* 118 */       for (Language language : GuiLanguage.this.languageManager.getLanguages()) {
/*     */         
/* 120 */         this.languageMap.put(language.getLanguageCode(), language);
/* 121 */         this.langCodeList.add(language.getLanguageCode());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 127 */       return this.langCodeList.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 132 */       Language language = this.languageMap.get(this.langCodeList.get(slotIndex));
/* 133 */       GuiLanguage.this.languageManager.setCurrentLanguage(language);
/* 134 */       GuiLanguage.this.game_settings_3.language = language.getLanguageCode();
/* 135 */       this.mc.refreshResources();
/* 136 */       GuiLanguage.this.fontRendererObj.setUnicodeFlag(!(!GuiLanguage.this.languageManager.isCurrentLocaleUnicode() && !GuiLanguage.this.game_settings_3.forceUnicodeFont));
/* 137 */       GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.isCurrentLanguageBidirectional());
/* 138 */       GuiLanguage.this.confirmSettingsBtn.displayString = I18n.format("gui.done", new Object[0]);
/* 139 */       GuiLanguage.this.forceUnicodeFontBtn.displayString = GuiLanguage.this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
/* 140 */       GuiLanguage.this.game_settings_3.saveOptions();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 145 */       return ((String)this.langCodeList.get(slotIndex)).equals(GuiLanguage.this.languageManager.getCurrentLanguage().getLanguageCode());
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getContentHeight() {
/* 150 */       return getSize() * 18;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 155 */       GuiLanguage.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 160 */       GuiLanguage.this.fontRendererObj.setBidiFlag(true);
/* 161 */       GuiLanguage.this.drawCenteredString(GuiLanguage.this.fontRendererObj, ((Language)this.languageMap.get(this.langCodeList.get(entryID))).toString(), this.width / 2, p_180791_3_ + 1, 16777215);
/* 162 */       GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.getCurrentLanguage().isBidirectional());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiLanguage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
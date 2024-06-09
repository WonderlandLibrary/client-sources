/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundCategory;
/*     */ import net.minecraft.client.audio.SoundEventAccessorComposite;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.gui.stream.GuiStreamOptions;
/*     */ import net.minecraft.client.gui.stream.GuiStreamUnavailable;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.stream.IStream;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ 
/*     */ public class GuiOptions extends GuiScreen implements GuiYesNoCallback {
/*  20 */   private static final GameSettings.Options[] field_146440_f = new GameSettings.Options[] { GameSettings.Options.FOV };
/*     */   
/*     */   private final GuiScreen field_146441_g;
/*     */   
/*     */   private final GameSettings game_settings_1;
/*     */   private GuiButton field_175357_i;
/*     */   private GuiLockIconButton field_175356_r;
/*  27 */   protected String field_146442_a = "Options";
/*     */ 
/*     */   
/*     */   public GuiOptions(GuiScreen p_i1046_1_, GameSettings p_i1046_2_) {
/*  31 */     this.field_146441_g = p_i1046_1_;
/*  32 */     this.game_settings_1 = p_i1046_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  41 */     int i = 0;
/*  42 */     this.field_146442_a = I18n.format("options.title", new Object[0]); byte b; int j;
/*     */     GameSettings.Options[] arrayOfOptions;
/*  44 */     for (j = (arrayOfOptions = field_146440_f).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/*     */       
/*  46 */       if (gamesettings$options.getEnumFloat()) {
/*     */         
/*  48 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), gamesettings$options));
/*     */       }
/*     */       else {
/*     */         
/*  52 */         GuiOptionButton guioptionbutton = new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), gamesettings$options, this.game_settings_1.getKeyBinding(gamesettings$options));
/*  53 */         this.buttonList.add(guioptionbutton);
/*     */       } 
/*     */       
/*  56 */       i++;
/*     */       b++; }
/*     */     
/*  59 */     if (this.mc.theWorld != null) {
/*     */       
/*  61 */       EnumDifficulty enumdifficulty = this.mc.theWorld.getDifficulty();
/*  62 */       this.field_175357_i = new GuiButton(108, this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), 150, 20, func_175355_a(enumdifficulty));
/*  63 */       this.buttonList.add(this.field_175357_i);
/*     */       
/*  65 */       if (this.mc.isSingleplayer() && !this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*     */         
/*  67 */         this.field_175357_i.setWidth(this.field_175357_i.getButtonWidth() - 20);
/*  68 */         this.field_175356_r = new GuiLockIconButton(109, this.field_175357_i.xPosition + this.field_175357_i.getButtonWidth(), this.field_175357_i.yPosition);
/*  69 */         this.buttonList.add(this.field_175356_r);
/*  70 */         this.field_175356_r.func_175229_b(this.mc.theWorld.getWorldInfo().isDifficultyLocked());
/*  71 */         this.field_175356_r.enabled = !this.field_175356_r.func_175230_c();
/*  72 */         this.field_175357_i.enabled = !this.field_175356_r.func_175230_c();
/*     */       }
/*     */       else {
/*     */         
/*  76 */         this.field_175357_i.enabled = false;
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     this.buttonList.add(new GuiButton(110, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
/*  81 */     this.buttonList.add(new GuiButton(8675309, this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, "Super Secret Settings...")
/*     */         {
/*     */           public void playPressSound(SoundHandler soundHandlerIn)
/*     */           {
/*  85 */             SoundEventAccessorComposite soundeventaccessorcomposite = soundHandlerIn.getRandomSoundFromCategories(new SoundCategory[] { SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER });
/*     */             
/*  87 */             if (soundeventaccessorcomposite != null)
/*     */             {
/*  89 */               soundHandlerIn.playSound((ISound)PositionedSoundRecord.create(soundeventaccessorcomposite.getSoundEventLocation(), 0.5F));
/*     */             }
/*     */           }
/*     */         });
/*  93 */     this.buttonList.add(new GuiButton(106, this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
/*  94 */     this.buttonList.add(new GuiButton(107, this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.stream", new Object[0])));
/*  95 */     this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
/*  96 */     this.buttonList.add(new GuiButton(100, this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
/*  97 */     this.buttonList.add(new GuiButton(102, this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
/*  98 */     this.buttonList.add(new GuiButton(103, this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.chat.title", new Object[0])));
/*  99 */     this.buttonList.add(new GuiButton(105, this.width / 2 - 155, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
/* 100 */     this.buttonList.add(new GuiButton(104, this.width / 2 + 5, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
/* 101 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_175355_a(EnumDifficulty p_175355_1_) {
/* 106 */     ChatComponentText chatComponentText = new ChatComponentText("");
/* 107 */     chatComponentText.appendSibling((IChatComponent)new ChatComponentTranslation("options.difficulty", new Object[0]));
/* 108 */     chatComponentText.appendText(": ");
/* 109 */     chatComponentText.appendSibling((IChatComponent)new ChatComponentTranslation(p_175355_1_.getDifficultyResourceKey(), new Object[0]));
/* 110 */     return chatComponentText.getFormattedText();
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 115 */     this.mc.displayGuiScreen(this);
/*     */     
/* 117 */     if (id == 109 && result && this.mc.theWorld != null) {
/*     */       
/* 119 */       this.mc.theWorld.getWorldInfo().setDifficultyLocked(true);
/* 120 */       this.field_175356_r.func_175229_b(true);
/* 121 */       this.field_175356_r.enabled = false;
/* 122 */       this.field_175357_i.enabled = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 131 */     if (button.enabled) {
/*     */       
/* 133 */       if (button.id < 100 && button instanceof GuiOptionButton) {
/*     */         
/* 135 */         GameSettings.Options gamesettings$options = ((GuiOptionButton)button).returnEnumOptions();
/* 136 */         this.game_settings_1.setOptionValue(gamesettings$options, 1);
/* 137 */         button.displayString = this.game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */       } 
/*     */       
/* 140 */       if (button.id == 108) {
/*     */         
/* 142 */         this.mc.theWorld.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(this.mc.theWorld.getDifficulty().getDifficultyId() + 1));
/* 143 */         this.field_175357_i.displayString = func_175355_a(this.mc.theWorld.getDifficulty());
/*     */       } 
/*     */       
/* 146 */       if (button.id == 109)
/*     */       {
/* 148 */         this.mc.displayGuiScreen(new GuiYesNo(this, (new ChatComponentTranslation("difficulty.lock.title", new Object[0])).getFormattedText(), (new ChatComponentTranslation("difficulty.lock.question", new Object[] { new ChatComponentTranslation(this.mc.theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0]) })).getFormattedText(), 109));
/*     */       }
/*     */       
/* 151 */       if (button.id == 110) {
/*     */         
/* 153 */         this.mc.gameSettings.saveOptions();
/* 154 */         this.mc.displayGuiScreen(new GuiCustomizeSkin(this));
/*     */       } 
/*     */       
/* 157 */       if (button.id == 8675309)
/*     */       {
/* 159 */         this.mc.entityRenderer.activateNextShader();
/*     */       }
/*     */       
/* 162 */       if (button.id == 101) {
/*     */         
/* 164 */         this.mc.gameSettings.saveOptions();
/* 165 */         this.mc.displayGuiScreen(new GuiVideoSettings(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 168 */       if (button.id == 100) {
/*     */         
/* 170 */         this.mc.gameSettings.saveOptions();
/* 171 */         this.mc.displayGuiScreen(new GuiControls(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 174 */       if (button.id == 102) {
/*     */         
/* 176 */         this.mc.gameSettings.saveOptions();
/* 177 */         this.mc.displayGuiScreen(new GuiLanguage(this, this.game_settings_1, this.mc.getLanguageManager()));
/*     */       } 
/*     */       
/* 180 */       if (button.id == 103) {
/*     */         
/* 182 */         this.mc.gameSettings.saveOptions();
/* 183 */         this.mc.displayGuiScreen(new ScreenChatOptions(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 186 */       if (button.id == 104) {
/*     */         
/* 188 */         this.mc.gameSettings.saveOptions();
/* 189 */         this.mc.displayGuiScreen(new GuiSnooper(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 192 */       if (button.id == 200) {
/*     */         
/* 194 */         this.mc.gameSettings.saveOptions();
/* 195 */         this.mc.displayGuiScreen(this.field_146441_g);
/*     */       } 
/*     */       
/* 198 */       if (button.id == 105) {
/*     */         
/* 200 */         this.mc.gameSettings.saveOptions();
/* 201 */         this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
/*     */       } 
/*     */       
/* 204 */       if (button.id == 106) {
/*     */         
/* 206 */         this.mc.gameSettings.saveOptions();
/* 207 */         this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 210 */       if (button.id == 107) {
/*     */         
/* 212 */         this.mc.gameSettings.saveOptions();
/* 213 */         IStream istream = this.mc.getTwitchStream();
/*     */         
/* 215 */         if (istream.func_152936_l() && istream.func_152928_D()) {
/*     */           
/* 217 */           this.mc.displayGuiScreen((GuiScreen)new GuiStreamOptions(this, this.game_settings_1));
/*     */         }
/*     */         else {
/*     */           
/* 221 */           GuiStreamUnavailable.func_152321_a(this);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 232 */     drawDefaultBackground();
/* 233 */     drawCenteredString(this.fontRendererObj, this.field_146442_a, this.width / 2, 15, 16777215);
/* 234 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package optfine;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiOptionButton;
/*     */ import net.minecraft.client.gui.GuiOptionSlider;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ public class GuiAnimationSettingsOF
/*     */   extends GuiScreen {
/*     */   private GuiScreen prevScreen;
/*  14 */   protected String title = "Animation Settings";
/*     */   private GameSettings settings;
/*  16 */   private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.ANIMATED_WATER, GameSettings.Options.ANIMATED_LAVA, GameSettings.Options.ANIMATED_FIRE, GameSettings.Options.ANIMATED_PORTAL, GameSettings.Options.ANIMATED_REDSTONE, GameSettings.Options.ANIMATED_EXPLOSION, GameSettings.Options.ANIMATED_FLAME, GameSettings.Options.ANIMATED_SMOKE, GameSettings.Options.VOID_PARTICLES, GameSettings.Options.WATER_PARTICLES, GameSettings.Options.RAIN_SPLASH, GameSettings.Options.PORTAL_PARTICLES, GameSettings.Options.POTION_PARTICLES, GameSettings.Options.DRIPPING_WATER_LAVA, GameSettings.Options.ANIMATED_TERRAIN, GameSettings.Options.ANIMATED_TEXTURES, GameSettings.Options.FIREWORK_PARTICLES, GameSettings.Options.PARTICLES };
/*     */ 
/*     */   
/*     */   public GuiAnimationSettingsOF(GuiScreen p_i34_1_, GameSettings p_i34_2_) {
/*  20 */     this.prevScreen = p_i34_1_;
/*  21 */     this.settings = p_i34_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  30 */     int i = 0; byte b; int j;
/*     */     GameSettings.Options[] arrayOfOptions;
/*  32 */     for (j = (arrayOfOptions = enumOptions).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/*     */       
/*  34 */       int m = this.width / 2 - 155 + i % 2 * 160;
/*  35 */       int k = this.height / 6 + 21 * i / 2 - 10;
/*     */       
/*  37 */       if (!gamesettings$options.getEnumFloat()) {
/*     */         
/*  39 */         this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), m, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
/*     */       }
/*     */       else {
/*     */         
/*  43 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), m, k, gamesettings$options));
/*     */       } 
/*     */       
/*  46 */       i++;
/*     */       b++; }
/*     */     
/*  49 */     this.buttonList.add(new GuiButton(210, this.width / 2 - 155, this.height / 6 + 168 + 11, 70, 20, "All ON"));
/*  50 */     this.buttonList.add(new GuiButton(211, this.width / 2 - 155 + 80, this.height / 6 + 168 + 11, 70, 20, "All OFF"));
/*  51 */     this.buttonList.add(new GuiOptionButton(200, this.width / 2 + 5, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  59 */     if (button.enabled) {
/*     */       
/*  61 */       if (button.id < 200 && button instanceof GuiOptionButton) {
/*     */         
/*  63 */         this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/*  64 */         button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */       } 
/*     */       
/*  67 */       if (button.id == 200) {
/*     */         
/*  69 */         this.mc.gameSettings.saveOptions();
/*  70 */         this.mc.displayGuiScreen(this.prevScreen);
/*     */       } 
/*     */       
/*  73 */       if (button.id == 210)
/*     */       {
/*  75 */         this.mc.gameSettings.setAllAnimations(true);
/*     */       }
/*     */       
/*  78 */       if (button.id == 211)
/*     */       {
/*  80 */         this.mc.gameSettings.setAllAnimations(false);
/*     */       }
/*     */       
/*  83 */       if (button.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
/*     */         
/*  85 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  86 */         int i = scaledresolution.getScaledWidth();
/*  87 */         int j = scaledresolution.getScaledHeight();
/*  88 */         setWorldAndResolution(this.mc, i, j);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  98 */     drawDefaultBackground();
/*  99 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
/* 100 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\GuiAnimationSettingsOF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
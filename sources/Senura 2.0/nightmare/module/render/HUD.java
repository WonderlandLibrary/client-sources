/*     */ package nightmare.module.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.event.EventTarget;
/*     */ import nightmare.event.impl.EventRender2D;
/*     */ import nightmare.fonts.impl.Fonts;
/*     */ import nightmare.gui.notification.NotificationManager;
/*     */ import nightmare.gui.window.Window;
/*     */ import nightmare.module.Category;
/*     */ import nightmare.module.Module;
/*     */ import nightmare.settings.Setting;
/*     */ import nightmare.utils.ColorUtils;
/*     */ import nightmare.utils.ScreenUtils;
/*     */ import nightmare.utils.render.BlurUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HUD
/*     */   extends Module
/*     */ {
/*     */   private int stringWidth;
/*  28 */   private FontRenderer fr = mc.field_71466_p;
/*     */   
/*     */   public HUD() {
/*  31 */     super("HUD", 0, Category.RENDER);
/*     */     
/*  33 */     Nightmare.instance.settingsManager.rSetting(new Setting("ActiveMods", this, true));
/*  34 */     Nightmare.instance.settingsManager.rSetting(new Setting("Background", this, true));
/*  35 */     Nightmare.instance.settingsManager.rSetting(new Setting("ClientName", this, false));
/*  36 */     Nightmare.instance.settingsManager.rSetting(new Setting("Inventory", this, true));
/*  37 */     Nightmare.instance.settingsManager.rSetting(new Setting("Notification", this, true));
/*  38 */     Nightmare.instance.settingsManager.rSetting(new Setting("TargetHUD", this, true));
/*  39 */     Nightmare.instance.settingsManager.rSetting(new Setting("Distance", this, 6.5D, 3.0D, 15.0D, false));
/*  40 */     Nightmare.instance.settingsManager.rSetting(new Setting("Red", this, 0.0D, 0.0D, 255.0D, true));
/*  41 */     Nightmare.instance.settingsManager.rSetting(new Setting("Green", this, 210.0D, 0.0D, 255.0D, true));
/*  42 */     Nightmare.instance.settingsManager.rSetting(new Setting("Blue", this, 255.0D, 0.0D, 255.0D, true));
/*     */   }
/*     */ 
/*     */   
/*     */   @EventTarget
/*     */   public void onRender(EventRender2D event) {
/*  48 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "ClientName").getValBoolean()) {
/*  49 */       String title = Nightmare.instance.getName() + " | " + Minecraft.func_175610_ah() + "fps";
/*  50 */       this.stringWidth = Fonts.REGULAR.REGULAR_18.REGULAR_18.getStringWidth(title) + 6;
/*  51 */       Gui.func_73734_a(6, 6, 8 + this.stringWidth + 2, 23, (new Color(60, 60, 60)).getRGB());
/*  52 */       Gui.func_73734_a(8, 8, 8 + this.stringWidth, 21, (new Color(25, 25, 25)).getRGB());
/*  53 */       Gui.func_73734_a(8, 20, 8 + this.stringWidth, 21, ColorUtils.getClientColor());
/*  54 */       Fonts.REGULAR.REGULAR_18.REGULAR_18.drawString(title, 11.0D, 11.0D, -1, true);
/*     */     } 
/*     */     
/*  57 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "ActiveMods").getValBoolean()) {
/*  58 */       renderActiveMods();
/*     */     }
/*     */     
/*  61 */     if (Nightmare.instance.moduleManager.getModuleByName("Blur").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Blur"), "ClickGUI").getValBoolean() && 
/*  62 */       mc.field_71462_r instanceof nightmare.clickgui.ClickGUI) {
/*  63 */       BlurUtils.drawBlurRect(0.0F, 0.0F, ScreenUtils.getWidth(), ScreenUtils.getHeight());
/*     */     }
/*     */ 
/*     */     
/*  67 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "Notification").getValBoolean()) {
/*  68 */       NotificationManager.doRender(ScreenUtils.getWidth(), ScreenUtils.getHeight());
/*     */     }
/*     */     
/*  71 */     if (!(mc.field_71462_r instanceof nightmare.clickgui.ClickGUI)) {
/*  72 */       for (Window window : Nightmare.instance.windowManager.getWindows()) {
/*  73 */         if (Nightmare.instance.settingsManager.getSettingByName(this, window.getName()).getValBoolean()) {
/*  74 */           Nightmare.instance.windowManager.getWindowByName(window.getName()).onRender();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderActiveMods() {
/*  82 */     ArrayList<Module> enabledMods = new ArrayList<>();
/*  83 */     ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
/*  84 */     int moduleY = 0;
/*     */     
/*  86 */     for (Module m : Nightmare.instance.moduleManager.getModules()) {
/*  87 */       if (m.isToggled()) {
/*  88 */         enabledMods.add(m);
/*     */       }
/*     */     } 
/*     */     
/*  92 */     enabledMods.sort((m1, m2) -> Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(m2.getDisplayName()) - Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(m1.getDisplayName()));
/*     */     
/*  94 */     for (Module m : enabledMods) {
/*     */       
/*  96 */       if (Nightmare.instance.settingsManager.getSettingByName(this, "Background").getValBoolean()) {
/*  97 */         Gui.func_73734_a(sr.func_78326_a() - Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(m.getDisplayName()) - 6, moduleY * (this.fr.field_78288_b + 2), sr.func_78326_a(), 2 + this.fr.field_78288_b + moduleY * (this.fr.field_78288_b + 2), ColorUtils.getBackgroundColor());
/*     */       }
/*     */       
/* 100 */       Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(m.getDisplayName(), (sr.func_78326_a() - Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(m.getDisplayName()) - 4), (2 + moduleY * (this.fr.field_78288_b + 2)), ColorUtils.getClientColor(), true);
/*     */       
/* 102 */       moduleY++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\render\HUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
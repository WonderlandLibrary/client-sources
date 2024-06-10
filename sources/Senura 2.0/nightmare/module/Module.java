/*     */ package nightmare.module;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.gui.notification.NotificationManager;
/*     */ import nightmare.gui.notification.NotificationType;
/*     */ 
/*     */ public class Module
/*     */ {
/*  11 */   protected static Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*     */   private String name;
/*     */   
/*     */   private String displayName;
/*     */   
/*     */   private int key;
/*     */   
/*     */   private Category category;
/*     */   
/*     */   protected boolean toggled;
/*     */   protected boolean blatantModule;
/*     */   public boolean visible = true;
/*     */   
/*     */   public Module(String name, int key, Category category) {
/*  26 */     this.name = name;
/*  27 */     this.key = key;
/*  28 */     this.category = category;
/*  29 */     this.toggled = false;
/*  30 */     this.blatantModule = false;
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  34 */     Nightmare.instance.eventManager.register(this);
/*     */   }
/*     */   public void onDisable() {
/*  37 */     Nightmare.instance.eventManager.unregister(this);
/*     */   }
/*     */   public void onToggle() {}
/*     */   
/*     */   public void toggle() {
/*  42 */     this.toggled = !this.toggled;
/*  43 */     onToggle();
/*  44 */     if (this.toggled) {
/*  45 */       if (isBlatantModule() && Nightmare.instance.moduleManager.getModuleByName("BlatantMode").isDisabled()) {
/*  46 */         setToggled(false);
/*  47 */         NotificationManager.show(NotificationType.ERROR, "Module", "This is BlatantModule!, Please toggle BlatantMode", 3000);
/*     */       } else {
/*  49 */         onEnable();
/*  50 */         NotificationManager.show(NotificationType.SUCCESS, "Module", EnumChatFormatting.GREEN + "Enable " + EnumChatFormatting.WHITE + this.name, 2500);
/*     */       } 
/*     */     } else {
/*     */       
/*  54 */       onDisable();
/*  55 */       NotificationManager.show(NotificationType.ERROR, "Module", EnumChatFormatting.RED + "Disable " + EnumChatFormatting.WHITE + this.name, 2500);
/*     */     } 
/*  57 */     if (Nightmare.instance.fileManager != null) {
/*  58 */       Nightmare.instance.fileManager.getConfigManager().save();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isToggled() {
/*  63 */     return this.toggled;
/*     */   }
/*     */   
/*     */   public boolean isDisabled() {
/*  67 */     return !this.toggled;
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/*  71 */     return (this.displayName == null) ? this.name : this.displayName;
/*     */   }
/*     */   
/*     */   public void setToggled(boolean toggled) {
/*  75 */     this.toggled = toggled;
/*     */     
/*  77 */     if (this.toggled) {
/*  78 */       onEnable();
/*     */     } else {
/*  80 */       onDisable();
/*     */     } 
/*  82 */     if (Nightmare.instance.fileManager != null) {
/*  83 */       Nightmare.instance.fileManager.getConfigManager().save();
/*     */     }
/*     */   }
/*     */   
/*     */   public Category getCategory() {
/*  88 */     return this.category;
/*     */   }
/*     */   
/*     */   public int getKey() {
/*  92 */     return this.key;
/*     */   }
/*     */   
/*     */   public int setKey(int key) {
/*  96 */     return this.key = key;
/*     */   }
/*     */   
/*     */   public String setDisplayName(String displayName) {
/* 100 */     return this.displayName = displayName;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 104 */     return this.name;
/*     */   }
/*     */   
/*     */   public String setName(String name) {
/* 108 */     return this.name = name;
/*     */   }
/*     */   
/*     */   public boolean setBlatantModule(boolean blatant) {
/* 112 */     return this.blatantModule = blatant;
/*     */   }
/*     */   
/*     */   public boolean isBlatantModule() {
/* 116 */     return this.blatantModule;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\Module.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package nightmare;
/*    */ 
/*    */ import nightmare.clickgui.ClickGUI;
/*    */ import nightmare.event.EventManager;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventKey;
/*    */ import nightmare.file.FileManager;
/*    */ import nightmare.fonts.api.FontManager;
/*    */ import nightmare.fonts.impl.SimpleFontManager;
/*    */ import nightmare.gui.window.WindowManager;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.module.ModuleManager;
/*    */ import nightmare.settings.SettingsManager;
/*    */ 
/*    */ public class Nightmare {
/* 16 */   public static Nightmare instance = new Nightmare();
/*    */   
/* 18 */   private String name = "Senura 2.0"; private String version = "2.0";
/*    */   
/*    */   public SettingsManager settingsManager;
/*    */   
/*    */   public EventManager eventManager;
/*    */   public ModuleManager moduleManager;
/*    */   public ClickGUI clickGUI;
/*    */   public WindowManager windowManager;
/*    */   public FileManager fileManager;
/* 27 */   public FontManager fontManager = SimpleFontManager.create();
/*    */   
/*    */   public void startClient() {
/* 30 */     this.settingsManager = new SettingsManager();
/* 31 */     this.eventManager = new EventManager();
/* 32 */     this.moduleManager = new ModuleManager();
/* 33 */     this.clickGUI = new ClickGUI();
/* 34 */     this.windowManager = new WindowManager();
/* 35 */     this.fileManager = new FileManager();
/*    */     
/* 37 */     this.eventManager.register(this);
/*    */   }
/*    */   
/*    */   public void stopClient() {
/* 41 */     this.eventManager.unregister(this);
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onKey(EventKey event) {
/* 46 */     this.moduleManager.getModules().stream().filter(module -> (module.getKey() == event.getKey())).forEach(module -> module.toggle());
/*    */   }
/*    */   
/*    */   public String getName() {
/* 50 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 54 */     return this.version;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\Nightmare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
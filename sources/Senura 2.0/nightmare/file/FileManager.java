/*    */ package nightmare.file;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import nightmare.file.config.ConfigManager;
/*    */ import nightmare.file.position.PositionManager;
/*    */ 
/*    */ 
/*    */ public class FileManager
/*    */ {
/* 11 */   private Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   private File dir;
/*    */   
/*    */   private ConfigManager configManager;
/*    */   
/*    */   private PositionManager positionManager;
/*    */   
/*    */   public FileManager() {
/* 20 */     this.dir = new File(this.mc.field_71412_D, "Senura");
/* 21 */     if (!this.dir.exists()) {
/* 22 */       this.dir.mkdir();
/*    */     }
/*    */     
/* 25 */     this.configManager = new ConfigManager();
/* 26 */     this.positionManager = new PositionManager();
/*    */   }
/*    */   
/*    */   public File getDir() {
/* 30 */     return this.dir;
/*    */   }
/*    */   
/*    */   public void setDir(File dir) {
/* 34 */     this.dir = dir;
/*    */   }
/*    */   
/*    */   public ConfigManager getConfigManager() {
/* 38 */     return this.configManager;
/*    */   }
/*    */   
/*    */   public PositionManager getPositionManager() {
/* 42 */     return this.positionManager;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\file\FileManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package nightmare.file.position;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.gui.window.Window;
/*    */ 
/*    */ 
/*    */ public class PositionManager
/*    */ {
/* 17 */   private static Minecraft mc = Minecraft.func_71410_x(); private File positionDir;
/*    */   
/*    */   public File getPositionDir() {
/* 20 */     return this.positionDir;
/*    */   }
/*    */   private File dataFile;
/*    */   public void setPositionDir(File positionDir) {
/* 24 */     this.positionDir = positionDir;
/*    */   }
/*    */   
/*    */   public File getDataFile() {
/* 28 */     return this.dataFile;
/*    */   }
/*    */   
/*    */   public void setDataFile(File dataFile) {
/* 32 */     this.dataFile = dataFile;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PositionManager() {
/* 41 */     this.positionDir = new File(mc.field_71412_D, "Senura/config");
/* 42 */     this.dataFile = new File(this.positionDir, "Position.txt");
/*    */     
/* 44 */     if (!this.dataFile.exists()) {
/*    */       try {
/* 46 */         this.dataFile.createNewFile();
/* 47 */       } catch (IOException e) {
/* 48 */         e.printStackTrace();
/*    */       } 
/*    */     }
/* 51 */     load();
/*    */   }
/*    */ 
/*    */   
/*    */   public void save() {
/* 56 */     ArrayList<String> toSave = new ArrayList<>();
/*    */     
/* 58 */     for (Window window : Nightmare.instance.windowManager.getWindows()) {
/* 59 */       toSave.add("POS:" + window.getName() + ":" + window.getX() + ":" + window.getY() + ":" + window.getWidth() + ":" + window.getHeight());
/*    */     }
/*    */     
/*    */     try {
/* 63 */       PrintWriter pw = new PrintWriter(this.dataFile);
/* 64 */       for (String str : toSave) {
/* 65 */         pw.println(str);
/*    */       }
/* 67 */       pw.close();
/* 68 */     } catch (FileNotFoundException e) {
/* 69 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void load() {
/* 75 */     ArrayList<String> lines = new ArrayList<>();
/*    */     
/*    */     try {
/* 78 */       BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
/* 79 */       String line = reader.readLine();
/* 80 */       while (line != null) {
/* 81 */         lines.add(line);
/* 82 */         line = reader.readLine();
/*    */       } 
/* 84 */       reader.close();
/* 85 */     } catch (Exception e) {
/* 86 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 89 */     for (String s : lines) {
/*    */       
/* 91 */       String[] args = s.split(":");
/*    */       
/* 93 */       if (s.toLowerCase().startsWith("pos:")) {
/* 94 */         Window window = Nightmare.instance.windowManager.getWindowByName(args[1]);
/* 95 */         if (window != null) {
/* 96 */           window.setX(Integer.parseInt(args[2]));
/* 97 */           window.setY(Integer.parseInt(args[3]));
/* 98 */           window.setWidth(Integer.parseInt(args[4]));
/* 99 */           window.setHeight(Integer.parseInt(args[5]));
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\file\position\PositionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
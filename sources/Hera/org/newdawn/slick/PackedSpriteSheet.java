/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.HashMap;
/*     */ import org.newdawn.slick.util.Log;
/*     */ import org.newdawn.slick.util.ResourceLoader;
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
/*     */ public class PackedSpriteSheet
/*     */ {
/*     */   private Image image;
/*     */   private String basePath;
/*  25 */   private HashMap sections = new HashMap<Object, Object>();
/*     */   
/*  27 */   private int filter = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PackedSpriteSheet(String def) throws SlickException {
/*  36 */     this(def, (Color)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PackedSpriteSheet(String def, Color trans) throws SlickException {
/*  47 */     def = def.replace('\\', '/');
/*  48 */     this.basePath = def.substring(0, def.lastIndexOf("/") + 1);
/*     */     
/*  50 */     loadDefinition(def, trans);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PackedSpriteSheet(String def, int filter) throws SlickException {
/*  61 */     this(def, filter, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PackedSpriteSheet(String def, int filter, Color trans) throws SlickException {
/*  73 */     this.filter = filter;
/*     */     
/*  75 */     def = def.replace('\\', '/');
/*  76 */     this.basePath = def.substring(0, def.lastIndexOf("/") + 1);
/*     */     
/*  78 */     loadDefinition(def, trans);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getFullImage() {
/*  87 */     return this.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getSprite(String name) {
/*  97 */     Section section = (Section)this.sections.get(name);
/*     */     
/*  99 */     if (section == null) {
/* 100 */       throw new RuntimeException("Unknown sprite from packed sheet: " + name);
/*     */     }
/*     */     
/* 103 */     return this.image.getSubImage(section.x, section.y, section.width, section.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpriteSheet getSpriteSheet(String name) {
/* 113 */     Image image = getSprite(name);
/* 114 */     Section section = (Section)this.sections.get(name);
/*     */     
/* 116 */     return new SpriteSheet(image, section.width / section.tilesx, section.height / section.tilesy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadDefinition(String def, Color trans) throws SlickException {
/* 128 */     BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceLoader.getResourceAsStream(def)));
/*     */     
/*     */     try {
/* 131 */       this.image = new Image(this.basePath + reader.readLine(), false, this.filter, trans);
/* 132 */       while (reader.ready() && 
/* 133 */         reader.readLine() != null) {
/*     */ 
/*     */ 
/*     */         
/* 137 */         Section sect = new Section(reader);
/* 138 */         this.sections.put(sect.name, sect);
/*     */         
/* 140 */         if (reader.readLine() == null) {
/*     */           break;
/*     */         }
/*     */       } 
/* 144 */     } catch (Exception e) {
/* 145 */       Log.error(e);
/* 146 */       throw new SlickException("Failed to process definitions file - invalid format?", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Section
/*     */   {
/*     */     public int x;
/*     */ 
/*     */     
/*     */     public int y;
/*     */ 
/*     */     
/*     */     public int width;
/*     */ 
/*     */     
/*     */     public int height;
/*     */ 
/*     */     
/*     */     public int tilesx;
/*     */ 
/*     */     
/*     */     public int tilesy;
/*     */ 
/*     */     
/*     */     public String name;
/*     */ 
/*     */ 
/*     */     
/*     */     public Section(BufferedReader reader) throws IOException {
/* 178 */       this.name = reader.readLine().trim();
/*     */       
/* 180 */       this.x = Integer.parseInt(reader.readLine().trim());
/* 181 */       this.y = Integer.parseInt(reader.readLine().trim());
/* 182 */       this.width = Integer.parseInt(reader.readLine().trim());
/* 183 */       this.height = Integer.parseInt(reader.readLine().trim());
/* 184 */       this.tilesx = Integer.parseInt(reader.readLine().trim());
/* 185 */       this.tilesy = Integer.parseInt(reader.readLine().trim());
/* 186 */       reader.readLine().trim();
/* 187 */       reader.readLine().trim();
/*     */       
/* 189 */       this.tilesx = Math.max(1, this.tilesx);
/* 190 */       this.tilesy = Math.max(1, this.tilesy);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\PackedSpriteSheet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
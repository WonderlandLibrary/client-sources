/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import org.newdawn.slick.util.Log;
/*   8:    */ import org.newdawn.slick.util.ResourceLoader;
/*   9:    */ 
/*  10:    */ public class PackedSpriteSheet
/*  11:    */ {
/*  12:    */   private Image image;
/*  13:    */   private String basePath;
/*  14: 25 */   private HashMap sections = new HashMap();
/*  15: 27 */   private int filter = 2;
/*  16:    */   
/*  17:    */   public PackedSpriteSheet(String def)
/*  18:    */     throws SlickException
/*  19:    */   {
/*  20: 36 */     this(def, null);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public PackedSpriteSheet(String def, Color trans)
/*  24:    */     throws SlickException
/*  25:    */   {
/*  26: 47 */     def = def.replace('\\', '/');
/*  27: 48 */     this.basePath = def.substring(0, def.lastIndexOf("/") + 1);
/*  28:    */     
/*  29: 50 */     loadDefinition(def, trans);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public PackedSpriteSheet(String def, int filter)
/*  33:    */     throws SlickException
/*  34:    */   {
/*  35: 61 */     this(def, filter, null);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public PackedSpriteSheet(String def, int filter, Color trans)
/*  39:    */     throws SlickException
/*  40:    */   {
/*  41: 73 */     this.filter = filter;
/*  42:    */     
/*  43: 75 */     def = def.replace('\\', '/');
/*  44: 76 */     this.basePath = def.substring(0, def.lastIndexOf("/") + 1);
/*  45:    */     
/*  46: 78 */     loadDefinition(def, trans);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Image getFullImage()
/*  50:    */   {
/*  51: 87 */     return this.image;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Image getSprite(String name)
/*  55:    */   {
/*  56: 97 */     Section section = (Section)this.sections.get(name);
/*  57: 99 */     if (section == null) {
/*  58:100 */       throw new RuntimeException("Unknown sprite from packed sheet: " + name);
/*  59:    */     }
/*  60:103 */     return this.image.getSubImage(section.x, section.y, section.width, section.height);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public SpriteSheet getSpriteSheet(String name)
/*  64:    */   {
/*  65:113 */     Image image = getSprite(name);
/*  66:114 */     Section section = (Section)this.sections.get(name);
/*  67:    */     
/*  68:116 */     return new SpriteSheet(image, section.width / section.tilesx, section.height / section.tilesy);
/*  69:    */   }
/*  70:    */   
/*  71:    */   private void loadDefinition(String def, Color trans)
/*  72:    */     throws SlickException
/*  73:    */   {
/*  74:128 */     BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceLoader.getResourceAsStream(def)));
/*  75:    */     try
/*  76:    */     {
/*  77:131 */       this.image = new Image(this.basePath + reader.readLine(), false, this.filter, trans);
/*  78:132 */       while (reader.ready())
/*  79:    */       {
/*  80:133 */         if (reader.readLine() == null) {
/*  81:    */           break;
/*  82:    */         }
/*  83:137 */         Section sect = new Section(reader);
/*  84:138 */         this.sections.put(sect.name, sect);
/*  85:140 */         if (reader.readLine() == null) {
/*  86:    */           break;
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90:    */     catch (Exception e)
/*  91:    */     {
/*  92:145 */       Log.error(e);
/*  93:146 */       throw new SlickException("Failed to process definitions file - invalid format?", e);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   private class Section
/*  98:    */   {
/*  99:    */     public int x;
/* 100:    */     public int y;
/* 101:    */     public int width;
/* 102:    */     public int height;
/* 103:    */     public int tilesx;
/* 104:    */     public int tilesy;
/* 105:    */     public String name;
/* 106:    */     
/* 107:    */     public Section(BufferedReader reader)
/* 108:    */       throws IOException
/* 109:    */     {
/* 110:178 */       this.name = reader.readLine().trim();
/* 111:    */       
/* 112:180 */       this.x = Integer.parseInt(reader.readLine().trim());
/* 113:181 */       this.y = Integer.parseInt(reader.readLine().trim());
/* 114:182 */       this.width = Integer.parseInt(reader.readLine().trim());
/* 115:183 */       this.height = Integer.parseInt(reader.readLine().trim());
/* 116:184 */       this.tilesx = Integer.parseInt(reader.readLine().trim());
/* 117:185 */       this.tilesy = Integer.parseInt(reader.readLine().trim());
/* 118:186 */       reader.readLine().trim();
/* 119:187 */       reader.readLine().trim();
/* 120:    */       
/* 121:189 */       this.tilesx = Math.max(1, this.tilesx);
/* 122:190 */       this.tilesy = Math.max(1, this.tilesy);
/* 123:    */     }
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.PackedSpriteSheet
 * JD-Core Version:    0.7.0.1
 */
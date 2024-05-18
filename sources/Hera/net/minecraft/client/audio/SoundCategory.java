/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum SoundCategory
/*    */ {
/*  8 */   MASTER("master", 0),
/*  9 */   MUSIC("music", 1),
/* 10 */   RECORDS("record", 2),
/* 11 */   WEATHER("weather", 3),
/* 12 */   BLOCKS("block", 4),
/* 13 */   MOBS("hostile", 5),
/* 14 */   ANIMALS("neutral", 6),
/* 15 */   PLAYERS("player", 7),
/* 16 */   AMBIENT("ambient", 8);
/*    */   static {
/* 18 */     NAME_CATEGORY_MAP = Maps.newHashMap();
/* 19 */     ID_CATEGORY_MAP = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     byte b;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     int i;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     SoundCategory[] arrayOfSoundCategory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     for (i = (arrayOfSoundCategory = values()).length, b = 0; b < i; ) { SoundCategory soundcategory = arrayOfSoundCategory[b];
/*    */       
/* 47 */       if (NAME_CATEGORY_MAP.containsKey(soundcategory.getCategoryName()) || ID_CATEGORY_MAP.containsKey(Integer.valueOf(soundcategory.getCategoryId())))
/*    */       {
/* 49 */         throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + soundcategory);
/*    */       }
/*    */       
/* 52 */       NAME_CATEGORY_MAP.put(soundcategory.getCategoryName(), soundcategory);
/* 53 */       ID_CATEGORY_MAP.put(Integer.valueOf(soundcategory.getCategoryId()), soundcategory);
/*    */       b++; }
/*    */   
/*    */   }
/*    */   
/*    */   private static final Map<String, SoundCategory> NAME_CATEGORY_MAP;
/*    */   private static final Map<Integer, SoundCategory> ID_CATEGORY_MAP;
/*    */   private final String categoryName;
/*    */   private final int categoryId;
/*    */   
/*    */   SoundCategory(String name, int id) {
/*    */     this.categoryName = name;
/*    */     this.categoryId = id;
/*    */   }
/*    */   
/*    */   public String getCategoryName() {
/*    */     return this.categoryName;
/*    */   }
/*    */   
/*    */   public int getCategoryId() {
/*    */     return this.categoryId;
/*    */   }
/*    */   
/*    */   public static SoundCategory getCategory(String name) {
/*    */     return NAME_CATEGORY_MAP.get(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\audio\SoundCategory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
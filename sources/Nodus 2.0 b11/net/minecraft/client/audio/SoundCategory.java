/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Maps;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public enum SoundCategory
/*  7:   */ {
/*  8: 8 */   MASTER("master", 0),  MUSIC("music", 1),  RECORDS("record", 2),  WEATHER("weather", 3),  BLOCKS("block", 4),  MOBS("hostile", 5),  ANIMALS("neutral", 6),  PLAYERS("player", 7),  AMBIENT("ambient", 8);
/*  9:   */   
/* 10:   */   private static final Map field_147168_j;
/* 11:   */   private static final Map field_147169_k;
/* 12:   */   private final String categoryName;
/* 13:   */   private final int categoryId;
/* 14:   */   private static final String __OBFID = "CL_00001686";
/* 15:   */   
/* 16:   */   private SoundCategory(String p_i45126_3_, int p_i45126_4_)
/* 17:   */   {
/* 18:25 */     this.categoryName = p_i45126_3_;
/* 19:26 */     this.categoryId = p_i45126_4_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getCategoryName()
/* 23:   */   {
/* 24:31 */     return this.categoryName;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getCategoryId()
/* 28:   */   {
/* 29:36 */     return this.categoryId;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static SoundCategory func_147154_a(String p_147154_0_)
/* 33:   */   {
/* 34:41 */     return (SoundCategory)field_147168_j.get(p_147154_0_);
/* 35:   */   }
/* 36:   */   
/* 37:   */   static
/* 38:   */   {
/* 39:17 */     field_147168_j = Maps.newHashMap();
/* 40:18 */     field_147169_k = Maps.newHashMap();
/* 41:   */     
/* 42:   */ 
/* 43:   */ 
/* 44:   */ 
/* 45:   */ 
/* 46:   */ 
/* 47:   */ 
/* 48:   */ 
/* 49:   */ 
/* 50:   */ 
/* 51:   */ 
/* 52:   */ 
/* 53:   */ 
/* 54:   */ 
/* 55:   */ 
/* 56:   */ 
/* 57:   */ 
/* 58:   */ 
/* 59:   */ 
/* 60:   */ 
/* 61:   */ 
/* 62:   */ 
/* 63:   */ 
/* 64:   */ 
/* 65:   */ 
/* 66:   */ 
/* 67:45 */     SoundCategory[] var0 = values();
/* 68:46 */     int var1 = var0.length;
/* 69:48 */     for (int var2 = 0; var2 < var1; var2++)
/* 70:   */     {
/* 71:50 */       SoundCategory var3 = var0[var2];
/* 72:52 */       if ((field_147168_j.containsKey(var3.getCategoryName())) || (field_147169_k.containsKey(Integer.valueOf(var3.getCategoryId())))) {
/* 73:54 */         throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + var3);
/* 74:   */       }
/* 75:57 */       field_147168_j.put(var3.getCategoryName(), var3);
/* 76:58 */       field_147169_k.put(Integer.valueOf(var3.getCategoryId()), var3);
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.SoundCategory
 * JD-Core Version:    0.7.0.1
 */
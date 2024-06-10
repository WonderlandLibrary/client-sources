/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ 
/*  5:   */ public class EnchantmentNameParts
/*  6:   */ {
/*  7: 7 */   public static final EnchantmentNameParts instance = new EnchantmentNameParts();
/*  8: 8 */   private Random rand = new Random();
/*  9: 9 */   private String[] namePartsArray = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");
/* 10:   */   private static final String __OBFID = "CL_00000756";
/* 11:   */   
/* 12:   */   public String generateNewRandomName()
/* 13:   */   {
/* 14:17 */     int var1 = this.rand.nextInt(2) + 3;
/* 15:18 */     String var2 = "";
/* 16:20 */     for (int var3 = 0; var3 < var1; var3++)
/* 17:   */     {
/* 18:22 */       if (var3 > 0) {
/* 19:24 */         var2 = var2 + " ";
/* 20:   */       }
/* 21:27 */       var2 = var2 + this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
/* 22:   */     }
/* 23:30 */     return var2;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void reseedRandomGenerator(long p_148335_1_)
/* 27:   */   {
/* 28:38 */     this.rand.setSeed(p_148335_1_);
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.EnchantmentNameParts
 * JD-Core Version:    0.7.0.1
 */
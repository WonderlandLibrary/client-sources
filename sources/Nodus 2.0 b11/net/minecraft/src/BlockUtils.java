/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ 
/*  5:   */ public class BlockUtils
/*  6:   */ {
/*  7: 7 */   private static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
/*  8: 8 */   private static ReflectorMethod ForgeBlock_setLightOpacity = new ReflectorMethod(ForgeBlock, "setLightOpacity");
/*  9: 9 */   private static boolean directAccessValid = true;
/* 10:   */   
/* 11:   */   public static void setLightOpacity(Block block, int opacity)
/* 12:   */   {
/* 13:13 */     if (directAccessValid) {
/* 14:   */       try
/* 15:   */       {
/* 16:17 */         block.setLightOpacity(opacity);
/* 17:18 */         return;
/* 18:   */       }
/* 19:   */       catch (IllegalAccessError var3)
/* 20:   */       {
/* 21:22 */         directAccessValid = false;
/* 22:24 */         if (!ForgeBlock_setLightOpacity.exists()) {
/* 23:26 */           throw var3;
/* 24:   */         }
/* 25:   */       }
/* 26:   */     }
/* 27:31 */     Reflector.callVoid(block, ForgeBlock_setLightOpacity, new Object[] { Integer.valueOf(opacity) });
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.BlockUtils
 * JD-Core Version:    0.7.0.1
 */
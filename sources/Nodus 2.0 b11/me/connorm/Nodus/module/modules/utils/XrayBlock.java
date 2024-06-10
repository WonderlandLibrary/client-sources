/*  1:   */ package me.connorm.Nodus.module.modules.utils;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ 
/*  5:   */ public class XrayBlock
/*  6:   */ {
/*  7:   */   private String blockName;
/*  8:   */   
/*  9:   */   public XrayBlock(String blockName)
/* 10:   */   {
/* 11:11 */     this.blockName = blockName;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getBlockName()
/* 15:   */   {
/* 16:16 */     return this.blockName;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Block toBlock()
/* 20:   */   {
/* 21:21 */     return Block.getBlockFromName(this.blockName);
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.utils.XrayBlock
 * JD-Core Version:    0.7.0.1
 */
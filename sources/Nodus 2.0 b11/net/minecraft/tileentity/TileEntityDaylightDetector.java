/*  1:   */ package net.minecraft.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.BlockDaylightDetector;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class TileEntityDaylightDetector
/*  7:   */   extends TileEntity
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000350";
/* 10:   */   
/* 11:   */   public void updateEntity()
/* 12:   */   {
/* 13:11 */     if ((this.worldObj != null) && (!this.worldObj.isClient) && (this.worldObj.getTotalWorldTime() % 20L == 0L))
/* 14:   */     {
/* 15:13 */       this.blockType = getBlockType();
/* 16:15 */       if ((this.blockType instanceof BlockDaylightDetector)) {
/* 17:17 */         ((BlockDaylightDetector)this.blockType).func_149957_e(this.worldObj, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 18:   */       }
/* 19:   */     }
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityDaylightDetector
 * JD-Core Version:    0.7.0.1
 */
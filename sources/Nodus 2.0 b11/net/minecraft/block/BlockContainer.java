/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.tileentity.TileEntity;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public abstract class BlockContainer
/*  8:   */   extends Block
/*  9:   */   implements ITileEntityProvider
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000193";
/* 12:   */   
/* 13:   */   protected BlockContainer(Material p_i45386_1_)
/* 14:   */   {
/* 15:13 */     super(p_i45386_1_);
/* 16:14 */     this.isBlockContainer = true;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/* 20:   */   {
/* 21:19 */     super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 25:   */   {
/* 26:24 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 27:25 */     p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
/* 31:   */   {
/* 32:30 */     super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
/* 33:31 */     TileEntity var7 = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
/* 34:32 */     return var7 != null ? var7.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockContainer
 * JD-Core Version:    0.7.0.1
 */
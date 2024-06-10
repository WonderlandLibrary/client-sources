/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.dispenser.IBlockSource;
/*  4:   */ import net.minecraft.tileentity.TileEntity;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class BlockSourceImpl
/*  8:   */   implements IBlockSource
/*  9:   */ {
/* 10:   */   private final World worldObj;
/* 11:   */   private final int xPos;
/* 12:   */   private final int yPos;
/* 13:   */   private final int zPos;
/* 14:   */   private static final String __OBFID = "CL_00001194";
/* 15:   */   
/* 16:   */   public BlockSourceImpl(World par1World, int par2, int par3, int par4)
/* 17:   */   {
/* 18:17 */     this.worldObj = par1World;
/* 19:18 */     this.xPos = par2;
/* 20:19 */     this.yPos = par3;
/* 21:20 */     this.zPos = par4;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public World getWorld()
/* 25:   */   {
/* 26:25 */     return this.worldObj;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public double getX()
/* 30:   */   {
/* 31:30 */     return this.xPos + 0.5D;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public double getY()
/* 35:   */   {
/* 36:35 */     return this.yPos + 0.5D;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public double getZ()
/* 40:   */   {
/* 41:40 */     return this.zPos + 0.5D;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int getXInt()
/* 45:   */   {
/* 46:45 */     return this.xPos;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int getYInt()
/* 50:   */   {
/* 51:50 */     return this.yPos;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public int getZInt()
/* 55:   */   {
/* 56:55 */     return this.zPos;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public int getBlockMetadata()
/* 60:   */   {
/* 61:60 */     return this.worldObj.getBlockMetadata(this.xPos, this.yPos, this.zPos);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public TileEntity getBlockTileEntity()
/* 65:   */   {
/* 66:65 */     return this.worldObj.getTileEntity(this.xPos, this.yPos, this.zPos);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSourceImpl
 * JD-Core Version:    0.7.0.1
 */
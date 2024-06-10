/*  1:   */ package net.minecraft.dispenser;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.BlockDispenser;
/*  5:   */ import net.minecraft.entity.item.EntityItem;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.util.EnumFacing;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class BehaviorDefaultDispenseItem
/* 11:   */   implements IBehaviorDispenseItem
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00001195";
/* 14:   */   
/* 15:   */   public final ItemStack dispense(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 16:   */   {
/* 17:18 */     ItemStack var3 = dispenseStack(par1IBlockSource, par2ItemStack);
/* 18:19 */     playDispenseSound(par1IBlockSource);
/* 19:20 */     spawnDispenseParticles(par1IBlockSource, BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata()));
/* 20:21 */     return var3;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 24:   */   {
/* 25:29 */     EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 26:30 */     IPosition var4 = BlockDispenser.func_149939_a(par1IBlockSource);
/* 27:31 */     ItemStack var5 = par2ItemStack.splitStack(1);
/* 28:32 */     doDispense(par1IBlockSource.getWorld(), var5, 6, var3, var4);
/* 29:33 */     return par2ItemStack;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void doDispense(World par0World, ItemStack par1ItemStack, int par2, EnumFacing par3EnumFacing, IPosition par4IPosition)
/* 33:   */   {
/* 34:38 */     double var5 = par4IPosition.getX();
/* 35:39 */     double var7 = par4IPosition.getY();
/* 36:40 */     double var9 = par4IPosition.getZ();
/* 37:41 */     EntityItem var11 = new EntityItem(par0World, var5, var7 - 0.3D, var9, par1ItemStack);
/* 38:42 */     double var12 = par0World.rand.nextDouble() * 0.1D + 0.2D;
/* 39:43 */     var11.motionX = (par3EnumFacing.getFrontOffsetX() * var12);
/* 40:44 */     var11.motionY = 0.2000000029802322D;
/* 41:45 */     var11.motionZ = (par3EnumFacing.getFrontOffsetZ() * var12);
/* 42:46 */     var11.motionX += par0World.rand.nextGaussian() * 0.007499999832361937D * par2;
/* 43:47 */     var11.motionY += par0World.rand.nextGaussian() * 0.007499999832361937D * par2;
/* 44:48 */     var11.motionZ += par0World.rand.nextGaussian() * 0.007499999832361937D * par2;
/* 45:49 */     par0World.spawnEntityInWorld(var11);
/* 46:   */   }
/* 47:   */   
/* 48:   */   protected void playDispenseSound(IBlockSource par1IBlockSource)
/* 49:   */   {
/* 50:57 */     par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
/* 51:   */   }
/* 52:   */   
/* 53:   */   protected void spawnDispenseParticles(IBlockSource par1IBlockSource, EnumFacing par2EnumFacing)
/* 54:   */   {
/* 55:65 */     par1IBlockSource.getWorld().playAuxSFX(2000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), func_82488_a(par2EnumFacing));
/* 56:   */   }
/* 57:   */   
/* 58:   */   private int func_82488_a(EnumFacing par1EnumFacing)
/* 59:   */   {
/* 60:70 */     return par1EnumFacing.getFrontOffsetX() + 1 + (par1EnumFacing.getFrontOffsetZ() + 1) * 3;
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.dispenser.BehaviorDefaultDispenseItem
 * JD-Core Version:    0.7.0.1
 */
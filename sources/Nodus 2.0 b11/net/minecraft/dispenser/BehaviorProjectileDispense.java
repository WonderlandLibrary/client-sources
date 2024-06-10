/*  1:   */ package net.minecraft.dispenser;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.BlockDispenser;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.IProjectile;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.util.EnumFacing;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public abstract class BehaviorProjectileDispense
/* 11:   */   extends BehaviorDefaultDispenseItem
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00001394";
/* 14:   */   
/* 15:   */   public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 16:   */   {
/* 17:19 */     World var3 = par1IBlockSource.getWorld();
/* 18:20 */     IPosition var4 = BlockDispenser.func_149939_a(par1IBlockSource);
/* 19:21 */     EnumFacing var5 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 20:22 */     IProjectile var6 = getProjectileEntity(var3, var4);
/* 21:23 */     var6.setThrowableHeading(var5.getFrontOffsetX(), var5.getFrontOffsetY() + 0.1F, var5.getFrontOffsetZ(), func_82500_b(), func_82498_a());
/* 22:24 */     var3.spawnEntityInWorld((Entity)var6);
/* 23:25 */     par2ItemStack.splitStack(1);
/* 24:26 */     return par2ItemStack;
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected void playDispenseSound(IBlockSource par1IBlockSource)
/* 28:   */   {
/* 29:34 */     par1IBlockSource.getWorld().playAuxSFX(1002, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected abstract IProjectile getProjectileEntity(World paramWorld, IPosition paramIPosition);
/* 33:   */   
/* 34:   */   protected float func_82498_a()
/* 35:   */   {
/* 36:44 */     return 6.0F;
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected float func_82500_b()
/* 40:   */   {
/* 41:49 */     return 1.1F;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.dispenser.BehaviorProjectileDispense
 * JD-Core Version:    0.7.0.1
 */
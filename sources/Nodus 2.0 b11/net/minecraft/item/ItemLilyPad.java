/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.entity.player.PlayerCapabilities;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ import net.minecraft.util.MovingObjectPosition;
/*  9:   */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public class ItemLilyPad
/* 13:   */   extends ItemColored
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00000074";
/* 16:   */   
/* 17:   */   public ItemLilyPad(Block p_i45357_1_)
/* 18:   */   {
/* 19:16 */     super(p_i45357_1_, false);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 23:   */   {
/* 24:24 */     MovingObjectPosition var4 = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
/* 25:26 */     if (var4 == null) {
/* 26:28 */       return par1ItemStack;
/* 27:   */     }
/* 28:32 */     if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
/* 29:   */     {
/* 30:34 */       int var5 = var4.blockX;
/* 31:35 */       int var6 = var4.blockY;
/* 32:36 */       int var7 = var4.blockZ;
/* 33:38 */       if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7)) {
/* 34:40 */         return par1ItemStack;
/* 35:   */       }
/* 36:43 */       if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack)) {
/* 37:45 */         return par1ItemStack;
/* 38:   */       }
/* 39:48 */       if ((par2World.getBlock(var5, var6, var7).getMaterial() == Material.water) && (par2World.getBlockMetadata(var5, var6, var7) == 0) && (par2World.isAirBlock(var5, var6 + 1, var7)))
/* 40:   */       {
/* 41:50 */         par2World.setBlock(var5, var6 + 1, var7, Blocks.waterlily);
/* 42:52 */         if (!par3EntityPlayer.capabilities.isCreativeMode) {
/* 43:54 */           par1ItemStack.stackSize -= 1;
/* 44:   */         }
/* 45:   */       }
/* 46:   */     }
/* 47:59 */     return par1ItemStack;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/* 51:   */   {
/* 52:65 */     return Blocks.waterlily.getRenderColor(par1ItemStack.getItemDamage());
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemLilyPad
 * JD-Core Version:    0.7.0.1
 */
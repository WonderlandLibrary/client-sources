/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  6:   */ import net.minecraft.creativetab.CreativeTabs;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  9:   */ import net.minecraft.init.Items;
/* 10:   */ import net.minecraft.util.IIcon;
/* 11:   */ import net.minecraft.util.MovingObjectPosition;
/* 12:   */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/* 13:   */ import net.minecraft.world.World;
/* 14:   */ 
/* 15:   */ public class ItemGlassBottle
/* 16:   */   extends Item
/* 17:   */ {
/* 18:   */   private static final String __OBFID = "CL_00001776";
/* 19:   */   
/* 20:   */   public ItemGlassBottle()
/* 21:   */   {
/* 22:18 */     setCreativeTab(CreativeTabs.tabBrewing);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public IIcon getIconFromDamage(int par1)
/* 26:   */   {
/* 27:26 */     return Items.potionitem.getIconFromDamage(0);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 31:   */   {
/* 32:34 */     MovingObjectPosition var4 = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
/* 33:36 */     if (var4 == null) {
/* 34:38 */       return par1ItemStack;
/* 35:   */     }
/* 36:42 */     if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
/* 37:   */     {
/* 38:44 */       int var5 = var4.blockX;
/* 39:45 */       int var6 = var4.blockY;
/* 40:46 */       int var7 = var4.blockZ;
/* 41:48 */       if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7)) {
/* 42:50 */         return par1ItemStack;
/* 43:   */       }
/* 44:53 */       if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack)) {
/* 45:55 */         return par1ItemStack;
/* 46:   */       }
/* 47:58 */       if (par2World.getBlock(var5, var6, var7).getMaterial() == Material.water)
/* 48:   */       {
/* 49:60 */         par1ItemStack.stackSize -= 1;
/* 50:62 */         if (par1ItemStack.stackSize <= 0) {
/* 51:64 */           return new ItemStack(Items.potionitem);
/* 52:   */         }
/* 53:67 */         if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
/* 54:69 */           par3EntityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
/* 55:   */         }
/* 56:   */       }
/* 57:   */     }
/* 58:74 */     return par1ItemStack;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void registerIcons(IIconRegister par1IconRegister) {}
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemGlassBottle
 * JD-Core Version:    0.7.0.1
 */
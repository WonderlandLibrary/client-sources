/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.creativetab.CreativeTabs;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  6:   */ import net.minecraft.init.Items;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ import net.minecraft.world.WorldProvider;
/*  9:   */ import net.minecraft.world.storage.MapData;
/* 10:   */ 
/* 11:   */ public class ItemEmptyMap
/* 12:   */   extends ItemMapBase
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000024";
/* 15:   */   
/* 16:   */   protected ItemEmptyMap()
/* 17:   */   {
/* 18:15 */     setCreativeTab(CreativeTabs.tabMisc);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 22:   */   {
/* 23:23 */     ItemStack var4 = new ItemStack(Items.filled_map, 1, par2World.getUniqueDataId("map"));
/* 24:24 */     String var5 = "map_" + var4.getItemDamage();
/* 25:25 */     MapData var6 = new MapData(var5);
/* 26:26 */     par2World.setItemData(var5, var6);
/* 27:27 */     var6.scale = 0;
/* 28:28 */     int var7 = 128 * (1 << var6.scale);
/* 29:29 */     var6.xCenter = ((int)(Math.round(par3EntityPlayer.posX / var7) * var7));
/* 30:30 */     var6.zCenter = ((int)(Math.round(par3EntityPlayer.posZ / var7) * var7));
/* 31:31 */     var6.dimension = ((byte)par2World.provider.dimensionId);
/* 32:32 */     var6.markDirty();
/* 33:33 */     par1ItemStack.stackSize -= 1;
/* 34:35 */     if (par1ItemStack.stackSize <= 0) {
/* 35:37 */       return var4;
/* 36:   */     }
/* 37:41 */     if (!par3EntityPlayer.inventory.addItemStackToInventory(var4.copy())) {
/* 38:43 */       par3EntityPlayer.dropPlayerItemWithRandomChoice(var4, false);
/* 39:   */     }
/* 40:46 */     return par1ItemStack;
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemEmptyMap
 * JD-Core Version:    0.7.0.1
 */
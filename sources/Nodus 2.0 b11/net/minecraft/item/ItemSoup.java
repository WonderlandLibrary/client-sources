/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.init.Items;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class ItemSoup
/*  8:   */   extends ItemFood
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00001778";
/* 11:   */   
/* 12:   */   public ItemSoup(int p_i45330_1_)
/* 13:   */   {
/* 14:13 */     super(p_i45330_1_, false);
/* 15:14 */     setMaxStackSize(1);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 19:   */   {
/* 20:19 */     super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
/* 21:20 */     return new ItemStack(Items.bowl);
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemSoup
 * JD-Core Version:    0.7.0.1
 */
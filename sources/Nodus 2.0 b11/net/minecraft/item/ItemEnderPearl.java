/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.creativetab.CreativeTabs;
/*  5:   */ import net.minecraft.entity.item.EntityEnderPearl;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.entity.player.PlayerCapabilities;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class ItemEnderPearl
/* 11:   */   extends Item
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000027";
/* 14:   */   
/* 15:   */   public ItemEnderPearl()
/* 16:   */   {
/* 17:14 */     this.maxStackSize = 16;
/* 18:15 */     setCreativeTab(CreativeTabs.tabMisc);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 22:   */   {
/* 23:23 */     if (par3EntityPlayer.capabilities.isCreativeMode) {
/* 24:25 */       return par1ItemStack;
/* 25:   */     }
/* 26:29 */     par1ItemStack.stackSize -= 1;
/* 27:30 */     par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 28:32 */     if (!par2World.isClient) {
/* 29:34 */       par2World.spawnEntityInWorld(new EntityEnderPearl(par2World, par3EntityPlayer));
/* 30:   */     }
/* 31:37 */     return par1ItemStack;
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemEnderPearl
 * JD-Core Version:    0.7.0.1
 */
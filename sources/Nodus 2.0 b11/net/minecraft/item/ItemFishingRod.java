/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.entity.projectile.EntityFishHook;
/*  8:   */ import net.minecraft.util.IIcon;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class ItemFishingRod
/* 12:   */   extends Item
/* 13:   */ {
/* 14:   */   private IIcon theIcon;
/* 15:   */   private static final String __OBFID = "CL_00000034";
/* 16:   */   
/* 17:   */   public ItemFishingRod()
/* 18:   */   {
/* 19:17 */     setMaxDamage(64);
/* 20:18 */     setMaxStackSize(1);
/* 21:19 */     setCreativeTab(CreativeTabs.tabTools);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean isFull3D()
/* 25:   */   {
/* 26:27 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean shouldRotateAroundWhenRendering()
/* 30:   */   {
/* 31:36 */     return true;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 35:   */   {
/* 36:44 */     if (par3EntityPlayer.fishEntity != null)
/* 37:   */     {
/* 38:46 */       int var4 = par3EntityPlayer.fishEntity.func_146034_e();
/* 39:47 */       par1ItemStack.damageItem(var4, par3EntityPlayer);
/* 40:48 */       par3EntityPlayer.swingItem();
/* 41:   */     }
/* 42:   */     else
/* 43:   */     {
/* 44:52 */       par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 45:54 */       if (!par2World.isClient) {
/* 46:56 */         par2World.spawnEntityInWorld(new EntityFishHook(par2World, par3EntityPlayer));
/* 47:   */       }
/* 48:59 */       par3EntityPlayer.swingItem();
/* 49:   */     }
/* 50:62 */     return par1ItemStack;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void registerIcons(IIconRegister par1IconRegister)
/* 54:   */   {
/* 55:67 */     this.itemIcon = par1IconRegister.registerIcon(getIconString() + "_uncast");
/* 56:68 */     this.theIcon = par1IconRegister.registerIcon(getIconString() + "_cast");
/* 57:   */   }
/* 58:   */   
/* 59:   */   public IIcon func_94597_g()
/* 60:   */   {
/* 61:73 */     return this.theIcon;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public boolean isItemTool(ItemStack par1ItemStack)
/* 65:   */   {
/* 66:81 */     return super.isItemTool(par1ItemStack);
/* 67:   */   }
/* 68:   */   
/* 69:   */   public int getItemEnchantability()
/* 70:   */   {
/* 71:89 */     return 1;
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemFishingRod
 * JD-Core Version:    0.7.0.1
 */
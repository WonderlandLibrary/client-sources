/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.creativetab.CreativeTabs;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.potion.Potion;
/*  7:   */ import net.minecraft.potion.PotionEffect;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class ItemAppleGold
/* 11:   */   extends ItemFood
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000037";
/* 14:   */   
/* 15:   */   public ItemAppleGold(int p_i45341_1_, float p_i45341_2_, boolean p_i45341_3_)
/* 16:   */   {
/* 17:16 */     super(p_i45341_1_, p_i45341_2_, p_i45341_3_);
/* 18:17 */     setHasSubtypes(true);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean hasEffect(ItemStack par1ItemStack)
/* 22:   */   {
/* 23:22 */     return par1ItemStack.getItemDamage() > 0;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public EnumRarity getRarity(ItemStack par1ItemStack)
/* 27:   */   {
/* 28:30 */     return par1ItemStack.getItemDamage() == 0 ? EnumRarity.rare : EnumRarity.epic;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 32:   */   {
/* 33:35 */     if (!par2World.isClient) {
/* 34:37 */       par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 2400, 0));
/* 35:   */     }
/* 36:40 */     if (par1ItemStack.getItemDamage() > 0)
/* 37:   */     {
/* 38:42 */       if (!par2World.isClient)
/* 39:   */       {
/* 40:44 */         par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
/* 41:45 */         par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
/* 42:46 */         par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
/* 43:   */       }
/* 44:   */     }
/* 45:   */     else {
/* 46:51 */       super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/* 51:   */   {
/* 52:60 */     p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
/* 53:61 */     p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemAppleGold
 * JD-Core Version:    0.7.0.1
 */
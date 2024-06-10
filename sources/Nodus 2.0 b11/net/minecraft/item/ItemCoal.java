/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.util.IIcon;
/*  7:   */ 
/*  8:   */ public class ItemCoal
/*  9:   */   extends Item
/* 10:   */ {
/* 11:   */   private IIcon field_111220_a;
/* 12:   */   private static final String __OBFID = "CL_00000002";
/* 13:   */   
/* 14:   */   public ItemCoal()
/* 15:   */   {
/* 16:15 */     setHasSubtypes(true);
/* 17:16 */     setMaxDamage(0);
/* 18:17 */     setCreativeTab(CreativeTabs.tabMaterials);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 22:   */   {
/* 23:26 */     return par1ItemStack.getItemDamage() == 1 ? "item.charcoal" : "item.coal";
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/* 27:   */   {
/* 28:34 */     p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
/* 29:35 */     p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
/* 30:   */   }
/* 31:   */   
/* 32:   */   public IIcon getIconFromDamage(int par1)
/* 33:   */   {
/* 34:43 */     return par1 == 1 ? this.field_111220_a : super.getIconFromDamage(par1);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void registerIcons(IIconRegister par1IconRegister)
/* 38:   */   {
/* 39:48 */     super.registerIcons(par1IconRegister);
/* 40:49 */     this.field_111220_a = par1IconRegister.registerIcon("charcoal");
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemCoal
 * JD-Core Version:    0.7.0.1
 */
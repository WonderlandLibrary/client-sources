/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import net.minecraft.block.BlockJukebox;
/*  7:   */ import net.minecraft.creativetab.CreativeTabs;
/*  8:   */ import net.minecraft.entity.player.EntityPlayer;
/*  9:   */ import net.minecraft.init.Blocks;
/* 10:   */ import net.minecraft.util.IIcon;
/* 11:   */ import net.minecraft.util.StatCollector;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class ItemRecord
/* 15:   */   extends Item
/* 16:   */ {
/* 17:16 */   private static final Map field_150928_b = new HashMap();
/* 18:   */   public final String field_150929_a;
/* 19:   */   private static final String __OBFID = "CL_00000057";
/* 20:   */   
/* 21:   */   protected ItemRecord(String p_i45350_1_)
/* 22:   */   {
/* 23:22 */     this.field_150929_a = p_i45350_1_;
/* 24:23 */     this.maxStackSize = 1;
/* 25:24 */     setCreativeTab(CreativeTabs.tabMisc);
/* 26:25 */     field_150928_b.put(p_i45350_1_, this);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public IIcon getIconFromDamage(int par1)
/* 30:   */   {
/* 31:33 */     return this.itemIcon;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 35:   */   {
/* 36:42 */     if ((par3World.getBlock(par4, par5, par6) == Blocks.jukebox) && (par3World.getBlockMetadata(par4, par5, par6) == 0))
/* 37:   */     {
/* 38:44 */       if (par3World.isClient) {
/* 39:46 */         return true;
/* 40:   */       }
/* 41:50 */       ((BlockJukebox)Blocks.jukebox).func_149926_b(par3World, par4, par5, par6, par1ItemStack);
/* 42:51 */       par3World.playAuxSFXAtEntity(null, 1005, par4, par5, par6, Item.getIdFromItem(this));
/* 43:52 */       par1ItemStack.stackSize -= 1;
/* 44:53 */       return true;
/* 45:   */     }
/* 46:58 */     return false;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 50:   */   {
/* 51:67 */     par3List.add(func_150927_i());
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String func_150927_i()
/* 55:   */   {
/* 56:72 */     return StatCollector.translateToLocal("item.record." + this.field_150929_a + ".desc");
/* 57:   */   }
/* 58:   */   
/* 59:   */   public EnumRarity getRarity(ItemStack par1ItemStack)
/* 60:   */   {
/* 61:80 */     return EnumRarity.rare;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static ItemRecord func_150926_b(String p_150926_0_)
/* 65:   */   {
/* 66:85 */     return (ItemRecord)field_150928_b.get(p_150926_0_);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemRecord
 * JD-Core Version:    0.7.0.1
 */
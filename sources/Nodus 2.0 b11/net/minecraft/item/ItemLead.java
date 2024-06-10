/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.creativetab.CreativeTabs;
/*  7:   */ import net.minecraft.entity.EntityLeashKnot;
/*  8:   */ import net.minecraft.entity.EntityLiving;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.util.AABBPool;
/* 11:   */ import net.minecraft.util.AxisAlignedBB;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class ItemLead
/* 15:   */   extends Item
/* 16:   */ {
/* 17:   */   private static final String __OBFID = "CL_00000045";
/* 18:   */   
/* 19:   */   public ItemLead()
/* 20:   */   {
/* 21:19 */     setCreativeTab(CreativeTabs.tabTools);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 25:   */   {
/* 26:28 */     Block var11 = par3World.getBlock(par4, par5, par6);
/* 27:30 */     if (var11.getRenderType() == 11)
/* 28:   */     {
/* 29:32 */       if (par3World.isClient) {
/* 30:34 */         return true;
/* 31:   */       }
/* 32:38 */       func_150909_a(par2EntityPlayer, par3World, par4, par5, par6);
/* 33:39 */       return true;
/* 34:   */     }
/* 35:44 */     return false;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static boolean func_150909_a(EntityPlayer p_150909_0_, World p_150909_1_, int p_150909_2_, int p_150909_3_, int p_150909_4_)
/* 39:   */   {
/* 40:50 */     EntityLeashKnot var5 = EntityLeashKnot.getKnotForBlock(p_150909_1_, p_150909_2_, p_150909_3_, p_150909_4_);
/* 41:51 */     boolean var6 = false;
/* 42:52 */     double var7 = 7.0D;
/* 43:53 */     List var9 = p_150909_1_.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB(p_150909_2_ - var7, p_150909_3_ - var7, p_150909_4_ - var7, p_150909_2_ + var7, p_150909_3_ + var7, p_150909_4_ + var7));
/* 44:55 */     if (var9 != null)
/* 45:   */     {
/* 46:57 */       Iterator var10 = var9.iterator();
/* 47:59 */       while (var10.hasNext())
/* 48:   */       {
/* 49:61 */         EntityLiving var11 = (EntityLiving)var10.next();
/* 50:63 */         if ((var11.getLeashed()) && (var11.getLeashedToEntity() == p_150909_0_))
/* 51:   */         {
/* 52:65 */           if (var5 == null) {
/* 53:67 */             var5 = EntityLeashKnot.func_110129_a(p_150909_1_, p_150909_2_, p_150909_3_, p_150909_4_);
/* 54:   */           }
/* 55:70 */           var11.setLeashedToEntity(var5, true);
/* 56:71 */           var6 = true;
/* 57:   */         }
/* 58:   */       }
/* 59:   */     }
/* 60:76 */     return var6;
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemLead
 * JD-Core Version:    0.7.0.1
 */
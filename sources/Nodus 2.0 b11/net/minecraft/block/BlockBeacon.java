/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  5:   */ import net.minecraft.creativetab.CreativeTabs;
/*  6:   */ import net.minecraft.entity.EntityLivingBase;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.tileentity.TileEntity;
/* 10:   */ import net.minecraft.tileentity.TileEntityBeacon;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ 
/* 13:   */ public class BlockBeacon
/* 14:   */   extends BlockContainer
/* 15:   */ {
/* 16:   */   private static final String __OBFID = "CL_00000197";
/* 17:   */   
/* 18:   */   public BlockBeacon()
/* 19:   */   {
/* 20:19 */     super(Material.glass);
/* 21:20 */     setHardness(3.0F);
/* 22:21 */     setCreativeTab(CreativeTabs.tabMisc);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/* 26:   */   {
/* 27:29 */     return new TileEntityBeacon();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 31:   */   {
/* 32:37 */     if (p_149727_1_.isClient) {
/* 33:39 */       return true;
/* 34:   */     }
/* 35:43 */     TileEntityBeacon var10 = (TileEntityBeacon)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
/* 36:45 */     if (var10 != null) {
/* 37:47 */       p_149727_5_.func_146104_a(var10);
/* 38:   */     }
/* 39:50 */     return true;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean isOpaqueCube()
/* 43:   */   {
/* 44:56 */     return false;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public boolean renderAsNormalBlock()
/* 48:   */   {
/* 49:61 */     return false;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int getRenderType()
/* 53:   */   {
/* 54:69 */     return 34;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 58:   */   {
/* 59:74 */     super.registerBlockIcons(p_149651_1_);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/* 63:   */   {
/* 64:82 */     super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
/* 65:84 */     if (p_149689_6_.hasDisplayName()) {
/* 66:86 */       ((TileEntityBeacon)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145999_a(p_149689_6_.getDisplayName());
/* 67:   */     }
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockBeacon
 * JD-Core Version:    0.7.0.1
 */
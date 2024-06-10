/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.command.server.CommandBlockLogic;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.tileentity.TileEntity;
/*  10:    */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class BlockCommandBlock
/*  14:    */   extends BlockContainer
/*  15:    */ {
/*  16:    */   private static final String __OBFID = "CL_00000219";
/*  17:    */   
/*  18:    */   public BlockCommandBlock()
/*  19:    */   {
/*  20: 19 */     super(Material.iron);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  24:    */   {
/*  25: 27 */     return new TileEntityCommandBlock();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  29:    */   {
/*  30: 32 */     if (!p_149695_1_.isClient)
/*  31:    */     {
/*  32: 34 */       boolean var6 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_);
/*  33: 35 */       int var7 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/*  34: 36 */       boolean var8 = (var7 & 0x1) != 0;
/*  35: 38 */       if ((var6) && (!var8))
/*  36:    */       {
/*  37: 40 */         p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var7 | 0x1, 4);
/*  38: 41 */         p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, func_149738_a(p_149695_1_));
/*  39:    */       }
/*  40: 43 */       else if ((!var6) && (var8))
/*  41:    */       {
/*  42: 45 */         p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var7 & 0xFFFFFFFE, 4);
/*  43:    */       }
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  48:    */   {
/*  49: 55 */     TileEntity var6 = p_149674_1_.getTileEntity(p_149674_2_, p_149674_3_, p_149674_4_);
/*  50: 57 */     if ((var6 != null) && ((var6 instanceof TileEntityCommandBlock)))
/*  51:    */     {
/*  52: 59 */       CommandBlockLogic var7 = ((TileEntityCommandBlock)var6).func_145993_a();
/*  53: 60 */       var7.func_145755_a(p_149674_1_);
/*  54: 61 */       p_149674_1_.func_147453_f(p_149674_2_, p_149674_3_, p_149674_4_, this);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int func_149738_a(World p_149738_1_)
/*  59:    */   {
/*  60: 67 */     return 1;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  64:    */   {
/*  65: 75 */     TileEntityCommandBlock var10 = (TileEntityCommandBlock)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
/*  66: 77 */     if (var10 != null) {
/*  67: 79 */       p_149727_5_.func_146100_a(var10);
/*  68:    */     }
/*  69: 82 */     return true;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean hasComparatorInputOverride()
/*  73:    */   {
/*  74: 87 */     return true;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/*  78:    */   {
/*  79: 92 */     TileEntity var6 = p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_);
/*  80: 93 */     return (var6 != null) && ((var6 instanceof TileEntityCommandBlock)) ? ((TileEntityCommandBlock)var6).func_145993_a().func_145760_g() : 0;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  84:    */   {
/*  85:101 */     TileEntityCommandBlock var7 = (TileEntityCommandBlock)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_);
/*  86:103 */     if (p_149689_6_.hasDisplayName()) {
/*  87:105 */       var7.func_145993_a().func_145754_b(p_149689_6_.getDisplayName());
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int quantityDropped(Random p_149745_1_)
/*  92:    */   {
/*  93:114 */     return 0;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockCommandBlock
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.EntityLivingBase;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.util.IIcon;
/*  14:    */ import net.minecraft.util.MathHelper;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class BlockEndPortalFrame
/*  18:    */   extends Block
/*  19:    */ {
/*  20:    */   private IIcon field_150023_a;
/*  21:    */   private IIcon field_150022_b;
/*  22:    */   private static final String __OBFID = "CL_00000237";
/*  23:    */   
/*  24:    */   public BlockEndPortalFrame()
/*  25:    */   {
/*  26: 25 */     super(Material.rock);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  30:    */   {
/*  31: 33 */     return p_149691_1_ == 0 ? Blocks.end_stone.getBlockTextureFromSide(p_149691_1_) : p_149691_1_ == 1 ? this.field_150023_a : this.blockIcon;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  35:    */   {
/*  36: 38 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/*  37: 39 */     this.field_150023_a = p_149651_1_.registerIcon(getTextureName() + "_top");
/*  38: 40 */     this.field_150022_b = p_149651_1_.registerIcon(getTextureName() + "_eye");
/*  39:    */   }
/*  40:    */   
/*  41:    */   public IIcon func_150021_e()
/*  42:    */   {
/*  43: 45 */     return this.field_150022_b;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isOpaqueCube()
/*  47:    */   {
/*  48: 50 */     return false;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getRenderType()
/*  52:    */   {
/*  53: 58 */     return 26;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setBlockBoundsForItemRender()
/*  57:    */   {
/*  58: 66 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/*  62:    */   {
/*  63: 71 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
/*  64: 72 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  65: 73 */     int var8 = p_149743_1_.getBlockMetadata(p_149743_2_, p_149743_3_, p_149743_4_);
/*  66: 75 */     if (func_150020_b(var8))
/*  67:    */     {
/*  68: 77 */       setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
/*  69: 78 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  70:    */     }
/*  71: 81 */     setBlockBoundsForItemRender();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static boolean func_150020_b(int p_150020_0_)
/*  75:    */   {
/*  76: 86 */     return (p_150020_0_ & 0x4) != 0;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  80:    */   {
/*  81: 91 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  85:    */   {
/*  86: 99 */     int var7 = ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2) % 4;
/*  87:100 */     p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean hasComparatorInputOverride()
/*  91:    */   {
/*  92:105 */     return true;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/*  96:    */   {
/*  97:110 */     int var6 = p_149736_1_.getBlockMetadata(p_149736_2_, p_149736_3_, p_149736_4_);
/*  98:111 */     return func_150020_b(var6) ? 15 : 0;
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockEndPortalFrame
 * JD-Core Version:    0.7.0.1
 */
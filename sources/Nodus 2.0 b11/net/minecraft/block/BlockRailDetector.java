/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.command.IEntitySelector;
/*   7:    */ import net.minecraft.command.server.CommandBlockLogic;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityMinecartCommandBlock;
/*  10:    */ import net.minecraft.entity.item.EntityMinecart;
/*  11:    */ import net.minecraft.inventory.Container;
/*  12:    */ import net.minecraft.inventory.IInventory;
/*  13:    */ import net.minecraft.util.AABBPool;
/*  14:    */ import net.minecraft.util.AxisAlignedBB;
/*  15:    */ import net.minecraft.util.IIcon;
/*  16:    */ import net.minecraft.world.IBlockAccess;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ 
/*  19:    */ public class BlockRailDetector
/*  20:    */   extends BlockRailBase
/*  21:    */ {
/*  22:    */   private IIcon[] field_150055_b;
/*  23:    */   private static final String __OBFID = "CL_00000225";
/*  24:    */   
/*  25:    */   public BlockRailDetector()
/*  26:    */   {
/*  27: 24 */     super(true);
/*  28: 25 */     setTickRandomly(true);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int func_149738_a(World p_149738_1_)
/*  32:    */   {
/*  33: 30 */     return 20;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean canProvidePower()
/*  37:    */   {
/*  38: 38 */     return true;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/*  42:    */   {
/*  43: 43 */     if (!p_149670_1_.isClient)
/*  44:    */     {
/*  45: 45 */       int var6 = p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_);
/*  46: 47 */       if ((var6 & 0x8) == 0) {
/*  47: 49 */         func_150054_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, var6);
/*  48:    */       }
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  53:    */   {
/*  54: 59 */     if (!p_149674_1_.isClient)
/*  55:    */     {
/*  56: 61 */       int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/*  57: 63 */       if ((var6 & 0x8) != 0) {
/*  58: 65 */         func_150054_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var6);
/*  59:    */       }
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/*  64:    */   {
/*  65: 72 */     return (p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_) & 0x8) != 0 ? 15 : 0;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
/*  69:    */   {
/*  70: 77 */     return p_149748_5_ == 1 ? 15 : (p_149748_1_.getBlockMetadata(p_149748_2_, p_149748_3_, p_149748_4_) & 0x8) == 0 ? 0 : 0;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void func_150054_a(World p_150054_1_, int p_150054_2_, int p_150054_3_, int p_150054_4_, int p_150054_5_)
/*  74:    */   {
/*  75: 82 */     boolean var6 = (p_150054_5_ & 0x8) != 0;
/*  76: 83 */     boolean var7 = false;
/*  77: 84 */     float var8 = 0.125F;
/*  78: 85 */     List var9 = p_150054_1_.getEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().getAABB(p_150054_2_ + var8, p_150054_3_, p_150054_4_ + var8, p_150054_2_ + 1 - var8, p_150054_3_ + 1 - var8, p_150054_4_ + 1 - var8));
/*  79: 87 */     if (!var9.isEmpty()) {
/*  80: 89 */       var7 = true;
/*  81:    */     }
/*  82: 92 */     if ((var7) && (!var6))
/*  83:    */     {
/*  84: 94 */       p_150054_1_.setBlockMetadataWithNotify(p_150054_2_, p_150054_3_, p_150054_4_, p_150054_5_ | 0x8, 3);
/*  85: 95 */       p_150054_1_.notifyBlocksOfNeighborChange(p_150054_2_, p_150054_3_, p_150054_4_, this);
/*  86: 96 */       p_150054_1_.notifyBlocksOfNeighborChange(p_150054_2_, p_150054_3_ - 1, p_150054_4_, this);
/*  87: 97 */       p_150054_1_.markBlockRangeForRenderUpdate(p_150054_2_, p_150054_3_, p_150054_4_, p_150054_2_, p_150054_3_, p_150054_4_);
/*  88:    */     }
/*  89:100 */     if ((!var7) && (var6))
/*  90:    */     {
/*  91:102 */       p_150054_1_.setBlockMetadataWithNotify(p_150054_2_, p_150054_3_, p_150054_4_, p_150054_5_ & 0x7, 3);
/*  92:103 */       p_150054_1_.notifyBlocksOfNeighborChange(p_150054_2_, p_150054_3_, p_150054_4_, this);
/*  93:104 */       p_150054_1_.notifyBlocksOfNeighborChange(p_150054_2_, p_150054_3_ - 1, p_150054_4_, this);
/*  94:105 */       p_150054_1_.markBlockRangeForRenderUpdate(p_150054_2_, p_150054_3_, p_150054_4_, p_150054_2_, p_150054_3_, p_150054_4_);
/*  95:    */     }
/*  96:108 */     if (var7) {
/*  97:110 */       p_150054_1_.scheduleBlockUpdate(p_150054_2_, p_150054_3_, p_150054_4_, this, func_149738_a(p_150054_1_));
/*  98:    */     }
/*  99:113 */     p_150054_1_.func_147453_f(p_150054_2_, p_150054_3_, p_150054_4_, this);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/* 103:    */   {
/* 104:118 */     super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/* 105:119 */     func_150054_a(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, p_149726_1_.getBlockMetadata(p_149726_2_, p_149726_3_, p_149726_4_));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean hasComparatorInputOverride()
/* 109:    */   {
/* 110:124 */     return true;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/* 114:    */   {
/* 115:129 */     if ((p_149736_1_.getBlockMetadata(p_149736_2_, p_149736_3_, p_149736_4_) & 0x8) > 0)
/* 116:    */     {
/* 117:131 */       float var6 = 0.125F;
/* 118:132 */       List var7 = p_149736_1_.getEntitiesWithinAABB(EntityMinecartCommandBlock.class, AxisAlignedBB.getAABBPool().getAABB(p_149736_2_ + var6, p_149736_3_, p_149736_4_ + var6, p_149736_2_ + 1 - var6, p_149736_3_ + 1 - var6, p_149736_4_ + 1 - var6));
/* 119:134 */       if (var7.size() > 0) {
/* 120:136 */         return ((EntityMinecartCommandBlock)var7.get(0)).func_145822_e().func_145760_g();
/* 121:    */       }
/* 122:139 */       List var8 = p_149736_1_.selectEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().getAABB(p_149736_2_ + var6, p_149736_3_, p_149736_4_ + var6, p_149736_2_ + 1 - var6, p_149736_3_ + 1 - var6, p_149736_4_ + 1 - var6), IEntitySelector.selectInventories);
/* 123:141 */       if (var8.size() > 0) {
/* 124:143 */         return Container.calcRedstoneFromInventory((IInventory)var8.get(0));
/* 125:    */       }
/* 126:    */     }
/* 127:147 */     return 0;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 131:    */   {
/* 132:152 */     this.field_150055_b = new IIcon[2];
/* 133:153 */     this.field_150055_b[0] = p_149651_1_.registerIcon(getTextureName());
/* 134:154 */     this.field_150055_b[1] = p_149651_1_.registerIcon(getTextureName() + "_powered");
/* 135:    */   }
/* 136:    */   
/* 137:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 138:    */   {
/* 139:162 */     return (p_149691_2_ & 0x8) != 0 ? this.field_150055_b[1] : this.field_150055_b[0];
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRailDetector
 * JD-Core Version:    0.7.0.1
 */
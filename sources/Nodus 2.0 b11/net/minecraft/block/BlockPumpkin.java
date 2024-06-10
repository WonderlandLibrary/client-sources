/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.EntityLivingBase;
/*   8:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*   9:    */ import net.minecraft.entity.monster.EntitySnowman;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class BlockPumpkin
/*  17:    */   extends BlockDirectional
/*  18:    */ {
/*  19:    */   private boolean field_149985_a;
/*  20:    */   private IIcon field_149984_b;
/*  21:    */   private IIcon field_149986_M;
/*  22:    */   private static final String __OBFID = "CL_00000291";
/*  23:    */   
/*  24:    */   protected BlockPumpkin(boolean p_i45419_1_)
/*  25:    */   {
/*  26: 24 */     super(Material.field_151572_C);
/*  27: 25 */     setTickRandomly(true);
/*  28: 26 */     this.field_149985_a = p_i45419_1_;
/*  29: 27 */     setCreativeTab(CreativeTabs.tabBlock);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  33:    */   {
/*  34: 35 */     return (p_149691_2_ == 1) && (p_149691_1_ == 4) ? this.field_149986_M : (p_149691_2_ == 0) && (p_149691_1_ == 3) ? this.field_149986_M : (p_149691_2_ == 3) && (p_149691_1_ == 5) ? this.field_149986_M : (p_149691_2_ == 2) && (p_149691_1_ == 2) ? this.field_149986_M : p_149691_1_ == 0 ? this.field_149984_b : p_149691_1_ == 1 ? this.field_149984_b : this.blockIcon;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  38:    */   {
/*  39: 40 */     super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  40: 42 */     if ((p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_) == Blocks.snow) && (p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_) == Blocks.snow))
/*  41:    */     {
/*  42: 44 */       if (!p_149726_1_.isClient)
/*  43:    */       {
/*  44: 46 */         p_149726_1_.setBlock(p_149726_2_, p_149726_3_, p_149726_4_, getBlockById(0), 0, 2);
/*  45: 47 */         p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_, getBlockById(0), 0, 2);
/*  46: 48 */         p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_, getBlockById(0), 0, 2);
/*  47: 49 */         EntitySnowman var9 = new EntitySnowman(p_149726_1_);
/*  48: 50 */         var9.setLocationAndAngles(p_149726_2_ + 0.5D, p_149726_3_ - 1.95D, p_149726_4_ + 0.5D, 0.0F, 0.0F);
/*  49: 51 */         p_149726_1_.spawnEntityInWorld(var9);
/*  50: 52 */         p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_, p_149726_4_, getBlockById(0));
/*  51: 53 */         p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, getBlockById(0));
/*  52: 54 */         p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 2, p_149726_4_, getBlockById(0));
/*  53:    */       }
/*  54: 57 */       for (int var10 = 0; var10 < 120; var10++) {
/*  55: 59 */         p_149726_1_.spawnParticle("snowshovel", p_149726_2_ + p_149726_1_.rand.nextDouble(), p_149726_3_ - 2 + p_149726_1_.rand.nextDouble() * 2.5D, p_149726_4_ + p_149726_1_.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
/*  56:    */       }
/*  57:    */     }
/*  58: 62 */     else if ((p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block) && (p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_) == Blocks.iron_block))
/*  59:    */     {
/*  60: 64 */       boolean var5 = (p_149726_1_.getBlock(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block) && (p_149726_1_.getBlock(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block);
/*  61: 65 */       boolean var6 = (p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1) == Blocks.iron_block) && (p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1) == Blocks.iron_block);
/*  62: 67 */       if ((var5) || (var6))
/*  63:    */       {
/*  64: 69 */         p_149726_1_.setBlock(p_149726_2_, p_149726_3_, p_149726_4_, getBlockById(0), 0, 2);
/*  65: 70 */         p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_, getBlockById(0), 0, 2);
/*  66: 71 */         p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_, getBlockById(0), 0, 2);
/*  67: 73 */         if (var5)
/*  68:    */         {
/*  69: 75 */           p_149726_1_.setBlock(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_, getBlockById(0), 0, 2);
/*  70: 76 */           p_149726_1_.setBlock(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_, getBlockById(0), 0, 2);
/*  71:    */         }
/*  72:    */         else
/*  73:    */         {
/*  74: 80 */           p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1, getBlockById(0), 0, 2);
/*  75: 81 */           p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1, getBlockById(0), 0, 2);
/*  76:    */         }
/*  77: 84 */         EntityIronGolem var7 = new EntityIronGolem(p_149726_1_);
/*  78: 85 */         var7.setPlayerCreated(true);
/*  79: 86 */         var7.setLocationAndAngles(p_149726_2_ + 0.5D, p_149726_3_ - 1.95D, p_149726_4_ + 0.5D, 0.0F, 0.0F);
/*  80: 87 */         p_149726_1_.spawnEntityInWorld(var7);
/*  81: 89 */         for (int var8 = 0; var8 < 120; var8++) {
/*  82: 91 */           p_149726_1_.spawnParticle("snowballpoof", p_149726_2_ + p_149726_1_.rand.nextDouble(), p_149726_3_ - 2 + p_149726_1_.rand.nextDouble() * 3.9D, p_149726_4_ + p_149726_1_.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
/*  83:    */         }
/*  84: 94 */         p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_, p_149726_4_, getBlockById(0));
/*  85: 95 */         p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, getBlockById(0));
/*  86: 96 */         p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 2, p_149726_4_, getBlockById(0));
/*  87: 98 */         if (var5)
/*  88:    */         {
/*  89:100 */           p_149726_1_.notifyBlockChange(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_, getBlockById(0));
/*  90:101 */           p_149726_1_.notifyBlockChange(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_, getBlockById(0));
/*  91:    */         }
/*  92:    */         else
/*  93:    */         {
/*  94:105 */           p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1, getBlockById(0));
/*  95:106 */           p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1, getBlockById(0));
/*  96:    */         }
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/* 102:    */   {
/* 103:114 */     return (p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_).blockMaterial.isReplaceable()) && (World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_));
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/* 107:    */   {
/* 108:122 */     int var7 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 2.5D) & 0x3;
/* 109:123 */     p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 113:    */   {
/* 114:128 */     this.field_149986_M = p_149651_1_.registerIcon(getTextureName() + "_face_" + (this.field_149985_a ? "on" : "off"));
/* 115:129 */     this.field_149984_b = p_149651_1_.registerIcon(getTextureName() + "_top");
/* 116:130 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockPumpkin
 * JD-Core Version:    0.7.0.1
 */
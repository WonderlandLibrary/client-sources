/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.EntityLivingBase;
/*   9:    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  12:    */ import net.minecraft.init.Items;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.util.IIcon;
/*  15:    */ import net.minecraft.world.Explosion;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public class BlockTNT
/*  19:    */   extends Block
/*  20:    */ {
/*  21:    */   private IIcon field_150116_a;
/*  22:    */   private IIcon field_150115_b;
/*  23:    */   private static final String __OBFID = "CL_00000324";
/*  24:    */   
/*  25:    */   public BlockTNT()
/*  26:    */   {
/*  27: 25 */     super(Material.tnt);
/*  28: 26 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  32:    */   {
/*  33: 34 */     return p_149691_1_ == 1 ? this.field_150116_a : p_149691_1_ == 0 ? this.field_150115_b : this.blockIcon;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  37:    */   {
/*  38: 39 */     super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  39: 41 */     if (p_149726_1_.isBlockIndirectlyGettingPowered(p_149726_2_, p_149726_3_, p_149726_4_))
/*  40:    */     {
/*  41: 43 */       onBlockDestroyedByPlayer(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, 1);
/*  42: 44 */       p_149726_1_.setBlockToAir(p_149726_2_, p_149726_3_, p_149726_4_);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  47:    */   {
/*  48: 50 */     if (p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_))
/*  49:    */     {
/*  50: 52 */       onBlockDestroyedByPlayer(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 1);
/*  51: 53 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int quantityDropped(Random p_149745_1_)
/*  56:    */   {
/*  57: 62 */     return 1;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_)
/*  61:    */   {
/*  62: 70 */     if (!p_149723_1_.isClient)
/*  63:    */     {
/*  64: 72 */       EntityTNTPrimed var6 = new EntityTNTPrimed(p_149723_1_, p_149723_2_ + 0.5F, p_149723_3_ + 0.5F, p_149723_4_ + 0.5F, p_149723_5_.getExplosivePlacedBy());
/*  65: 73 */       var6.fuse = (p_149723_1_.rand.nextInt(var6.fuse / 4) + var6.fuse / 8);
/*  66: 74 */       p_149723_1_.spawnEntityInWorld(var6);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_)
/*  71:    */   {
/*  72: 80 */     func_150114_a(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_, null);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void func_150114_a(World p_150114_1_, int p_150114_2_, int p_150114_3_, int p_150114_4_, int p_150114_5_, EntityLivingBase p_150114_6_)
/*  76:    */   {
/*  77: 85 */     if (!p_150114_1_.isClient) {
/*  78: 87 */       if ((p_150114_5_ & 0x1) == 1)
/*  79:    */       {
/*  80: 89 */         EntityTNTPrimed var7 = new EntityTNTPrimed(p_150114_1_, p_150114_2_ + 0.5F, p_150114_3_ + 0.5F, p_150114_4_ + 0.5F, p_150114_6_);
/*  81: 90 */         p_150114_1_.spawnEntityInWorld(var7);
/*  82: 91 */         p_150114_1_.playSoundAtEntity(var7, "game.tnt.primed", 1.0F, 1.0F);
/*  83:    */       }
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  88:    */   {
/*  89:101 */     if ((p_149727_5_.getCurrentEquippedItem() != null) && (p_149727_5_.getCurrentEquippedItem().getItem() == Items.flint_and_steel))
/*  90:    */     {
/*  91:103 */       func_150114_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, 1, p_149727_5_);
/*  92:104 */       p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
/*  93:105 */       p_149727_5_.getCurrentEquippedItem().damageItem(1, p_149727_5_);
/*  94:106 */       return true;
/*  95:    */     }
/*  96:110 */     return super.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/* 100:    */   {
/* 101:116 */     if (((p_149670_5_ instanceof EntityArrow)) && (!p_149670_1_.isClient))
/* 102:    */     {
/* 103:118 */       EntityArrow var6 = (EntityArrow)p_149670_5_;
/* 104:120 */       if (var6.isBurning())
/* 105:    */       {
/* 106:122 */         func_150114_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, 1, (var6.shootingEntity instanceof EntityLivingBase) ? (EntityLivingBase)var6.shootingEntity : null);
/* 107:123 */         p_149670_1_.setBlockToAir(p_149670_2_, p_149670_3_, p_149670_4_);
/* 108:    */       }
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean canDropFromExplosion(Explosion p_149659_1_)
/* 113:    */   {
/* 114:133 */     return false;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 118:    */   {
/* 119:138 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/* 120:139 */     this.field_150116_a = p_149651_1_.registerIcon(getTextureName() + "_top");
/* 121:140 */     this.field_150115_b = p_149651_1_.registerIcon(getTextureName() + "_bottom");
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockTNT
 * JD-Core Version:    0.7.0.1
 */
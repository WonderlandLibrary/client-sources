/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.item.EntityItem;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  11:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  12:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  13:    */ import net.minecraft.init.Blocks;
/*  14:    */ import net.minecraft.init.Items;
/*  15:    */ import net.minecraft.item.Item;
/*  16:    */ import net.minecraft.item.ItemArmor;
/*  17:    */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*  18:    */ import net.minecraft.item.ItemStack;
/*  19:    */ import net.minecraft.util.AxisAlignedBB;
/*  20:    */ import net.minecraft.util.IIcon;
/*  21:    */ import net.minecraft.util.MathHelper;
/*  22:    */ import net.minecraft.world.World;
/*  23:    */ 
/*  24:    */ public class BlockCauldron
/*  25:    */   extends Block
/*  26:    */ {
/*  27:    */   private IIcon field_150029_a;
/*  28:    */   private IIcon field_150028_b;
/*  29:    */   private IIcon field_150030_M;
/*  30:    */   private static final String __OBFID = "CL_00000213";
/*  31:    */   
/*  32:    */   public BlockCauldron()
/*  33:    */   {
/*  34: 30 */     super(Material.iron);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  38:    */   {
/*  39: 38 */     return p_149691_1_ == 0 ? this.field_150030_M : p_149691_1_ == 1 ? this.field_150028_b : this.blockIcon;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  43:    */   {
/*  44: 43 */     this.field_150029_a = p_149651_1_.registerIcon(getTextureName() + "_" + "inner");
/*  45: 44 */     this.field_150028_b = p_149651_1_.registerIcon(getTextureName() + "_top");
/*  46: 45 */     this.field_150030_M = p_149651_1_.registerIcon(getTextureName() + "_" + "bottom");
/*  47: 46 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static IIcon func_150026_e(String p_150026_0_)
/*  51:    */   {
/*  52: 51 */     return p_150026_0_.equals("bottom") ? Blocks.cauldron.field_150030_M : p_150026_0_.equals("inner") ? Blocks.cauldron.field_150029_a : null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/*  56:    */   {
/*  57: 56 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
/*  58: 57 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  59: 58 */     float var8 = 0.125F;
/*  60: 59 */     setBlockBounds(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
/*  61: 60 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  62: 61 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
/*  63: 62 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  64: 63 */     setBlockBounds(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  65: 64 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  66: 65 */     setBlockBounds(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
/*  67: 66 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  68: 67 */     setBlockBoundsForItemRender();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setBlockBoundsForItemRender()
/*  72:    */   {
/*  73: 75 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean isOpaqueCube()
/*  77:    */   {
/*  78: 80 */     return false;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getRenderType()
/*  82:    */   {
/*  83: 88 */     return 24;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean renderAsNormalBlock()
/*  87:    */   {
/*  88: 93 */     return false;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/*  92:    */   {
/*  93: 98 */     int var6 = func_150027_b(p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_));
/*  94: 99 */     float var7 = p_149670_3_ + (6.0F + 3 * var6) / 16.0F;
/*  95:101 */     if ((!p_149670_1_.isClient) && (p_149670_5_.isBurning()) && (var6 > 0) && (p_149670_5_.boundingBox.minY <= var7))
/*  96:    */     {
/*  97:103 */       p_149670_5_.extinguish();
/*  98:104 */       func_150024_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, var6 - 1);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 103:    */   {
/* 104:113 */     if (p_149727_1_.isClient) {
/* 105:115 */       return true;
/* 106:    */     }
/* 107:119 */     ItemStack var10 = p_149727_5_.inventory.getCurrentItem();
/* 108:121 */     if (var10 == null) {
/* 109:123 */       return true;
/* 110:    */     }
/* 111:127 */     int var11 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
/* 112:128 */     int var12 = func_150027_b(var11);
/* 113:130 */     if (var10.getItem() == Items.water_bucket)
/* 114:    */     {
/* 115:132 */       if (var12 < 3)
/* 116:    */       {
/* 117:134 */         if (!p_149727_5_.capabilities.isCreativeMode) {
/* 118:136 */           p_149727_5_.inventory.setInventorySlotContents(p_149727_5_.inventory.currentItem, new ItemStack(Items.bucket));
/* 119:    */         }
/* 120:139 */         func_150024_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, 3);
/* 121:    */       }
/* 122:142 */       return true;
/* 123:    */     }
/* 124:146 */     if (var10.getItem() == Items.glass_bottle)
/* 125:    */     {
/* 126:148 */       if (var12 > 0)
/* 127:    */       {
/* 128:150 */         if (!p_149727_5_.capabilities.isCreativeMode)
/* 129:    */         {
/* 130:152 */           ItemStack var13 = new ItemStack(Items.potionitem, 1, 0);
/* 131:154 */           if (!p_149727_5_.inventory.addItemStackToInventory(var13)) {
/* 132:156 */             p_149727_1_.spawnEntityInWorld(new EntityItem(p_149727_1_, p_149727_2_ + 0.5D, p_149727_3_ + 1.5D, p_149727_4_ + 0.5D, var13));
/* 133:158 */           } else if ((p_149727_5_ instanceof EntityPlayerMP)) {
/* 134:160 */             ((EntityPlayerMP)p_149727_5_).sendContainerToPlayer(p_149727_5_.inventoryContainer);
/* 135:    */           }
/* 136:163 */           var10.stackSize -= 1;
/* 137:165 */           if (var10.stackSize <= 0) {
/* 138:167 */             p_149727_5_.inventory.setInventorySlotContents(p_149727_5_.inventory.currentItem, null);
/* 139:    */           }
/* 140:    */         }
/* 141:171 */         func_150024_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, var12 - 1);
/* 142:    */       }
/* 143:    */     }
/* 144:174 */     else if ((var12 > 0) && ((var10.getItem() instanceof ItemArmor)) && (((ItemArmor)var10.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH))
/* 145:    */     {
/* 146:176 */       ItemArmor var14 = (ItemArmor)var10.getItem();
/* 147:177 */       var14.removeColor(var10);
/* 148:178 */       func_150024_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, var12 - 1);
/* 149:179 */       return true;
/* 150:    */     }
/* 151:182 */     return false;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void func_150024_a(World p_150024_1_, int p_150024_2_, int p_150024_3_, int p_150024_4_, int p_150024_5_)
/* 155:    */   {
/* 156:190 */     p_150024_1_.setBlockMetadataWithNotify(p_150024_2_, p_150024_3_, p_150024_4_, MathHelper.clamp_int(p_150024_5_, 0, 3), 2);
/* 157:191 */     p_150024_1_.func_147453_f(p_150024_2_, p_150024_3_, p_150024_4_, this);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void fillWithRain(World p_149639_1_, int p_149639_2_, int p_149639_3_, int p_149639_4_)
/* 161:    */   {
/* 162:199 */     if (p_149639_1_.rand.nextInt(20) == 1)
/* 163:    */     {
/* 164:201 */       int var5 = p_149639_1_.getBlockMetadata(p_149639_2_, p_149639_3_, p_149639_4_);
/* 165:203 */       if (var5 < 3) {
/* 166:205 */         p_149639_1_.setBlockMetadataWithNotify(p_149639_2_, p_149639_3_, p_149639_4_, var5 + 1, 2);
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 172:    */   {
/* 173:212 */     return Items.cauldron;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 177:    */   {
/* 178:220 */     return Items.cauldron;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean hasComparatorInputOverride()
/* 182:    */   {
/* 183:225 */     return true;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/* 187:    */   {
/* 188:230 */     int var6 = p_149736_1_.getBlockMetadata(p_149736_2_, p_149736_3_, p_149736_4_);
/* 189:231 */     return func_150027_b(var6);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static int func_150027_b(int p_150027_0_)
/* 193:    */   {
/* 194:236 */     return p_150027_0_;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static float func_150025_c(int p_150025_0_)
/* 198:    */   {
/* 199:241 */     int var1 = MathHelper.clamp_int(p_150025_0_, 0, 3);
/* 200:242 */     return (6 + 3 * var1) / 16.0F;
/* 201:    */   }
/* 202:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockCauldron
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   8:    */ import net.minecraft.entity.EntityLivingBase;
/*   9:    */ import net.minecraft.entity.boss.EntityWither;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.init.Items;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.nbt.NBTTagCompound;
/*  17:    */ import net.minecraft.stats.AchievementList;
/*  18:    */ import net.minecraft.tileentity.TileEntity;
/*  19:    */ import net.minecraft.tileentity.TileEntitySkull;
/*  20:    */ import net.minecraft.util.AxisAlignedBB;
/*  21:    */ import net.minecraft.util.IIcon;
/*  22:    */ import net.minecraft.util.MathHelper;
/*  23:    */ import net.minecraft.world.EnumDifficulty;
/*  24:    */ import net.minecraft.world.IBlockAccess;
/*  25:    */ import net.minecraft.world.World;
/*  26:    */ 
/*  27:    */ public class BlockSkull
/*  28:    */   extends BlockContainer
/*  29:    */ {
/*  30:    */   private static final String __OBFID = "CL_00000307";
/*  31:    */   
/*  32:    */   protected BlockSkull()
/*  33:    */   {
/*  34: 32 */     super(Material.circuits);
/*  35: 33 */     setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getRenderType()
/*  39:    */   {
/*  40: 41 */     return -1;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean isOpaqueCube()
/*  44:    */   {
/*  45: 46 */     return false;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean renderAsNormalBlock()
/*  49:    */   {
/*  50: 51 */     return false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  54:    */   {
/*  55: 56 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 0x7;
/*  56: 58 */     switch (var5)
/*  57:    */     {
/*  58:    */     case 1: 
/*  59:    */     default: 
/*  60: 62 */       setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
/*  61: 63 */       break;
/*  62:    */     case 2: 
/*  63: 66 */       setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
/*  64: 67 */       break;
/*  65:    */     case 3: 
/*  66: 70 */       setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
/*  67: 71 */       break;
/*  68:    */     case 4: 
/*  69: 74 */       setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
/*  70: 75 */       break;
/*  71:    */     case 5: 
/*  72: 78 */       setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  77:    */   {
/*  78: 88 */     setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/*  79: 89 */     return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  83:    */   {
/*  84: 97 */     int var7 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 2.5D) & 0x3;
/*  85: 98 */     p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  89:    */   {
/*  90:106 */     return new TileEntitySkull();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/*  94:    */   {
/*  95:114 */     return Items.skull;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
/*  99:    */   {
/* 100:122 */     TileEntity var5 = p_149643_1_.getTileEntity(p_149643_2_, p_149643_3_, p_149643_4_);
/* 101:123 */     return (var5 != null) && ((var5 instanceof TileEntitySkull)) ? ((TileEntitySkull)var5).func_145904_a() : super.getDamageValue(p_149643_1_, p_149643_2_, p_149643_3_, p_149643_4_);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int damageDropped(int p_149692_1_)
/* 105:    */   {
/* 106:131 */     return p_149692_1_;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {}
/* 110:    */   
/* 111:    */   public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
/* 112:    */   {
/* 113:144 */     if (p_149681_6_.capabilities.isCreativeMode)
/* 114:    */     {
/* 115:146 */       p_149681_5_ |= 0x8;
/* 116:147 */       p_149681_1_.setBlockMetadataWithNotify(p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, 4);
/* 117:    */     }
/* 118:150 */     super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 122:    */   {
/* 123:155 */     if (!p_149749_1_.isClient)
/* 124:    */     {
/* 125:157 */       if ((p_149749_6_ & 0x8) == 0)
/* 126:    */       {
/* 127:159 */         ItemStack var7 = new ItemStack(Items.skull, 1, getDamageValue(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_));
/* 128:160 */         TileEntitySkull var8 = (TileEntitySkull)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
/* 129:162 */         if ((var8.func_145904_a() == 3) && (var8.func_145907_c() != null) && (var8.func_145907_c().length() > 0))
/* 130:    */         {
/* 131:164 */           var7.setTagCompound(new NBTTagCompound());
/* 132:165 */           var7.getTagCompound().setString("SkullOwner", var8.func_145907_c());
/* 133:    */         }
/* 134:168 */         dropBlockAsItem_do(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, var7);
/* 135:    */       }
/* 136:171 */       super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 141:    */   {
/* 142:177 */     return Items.skull;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void func_149965_a(World p_149965_1_, int p_149965_2_, int p_149965_3_, int p_149965_4_, TileEntitySkull p_149965_5_)
/* 146:    */   {
/* 147:182 */     if ((p_149965_5_.func_145904_a() == 1) && (p_149965_3_ >= 2) && (p_149965_1_.difficultySetting != EnumDifficulty.PEACEFUL) && (!p_149965_1_.isClient))
/* 148:    */     {
/* 149:190 */       for (int var6 = -2; var6 <= 0; var6++) {
/* 150:192 */         if ((p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6) == Blocks.soul_sand) && (p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 1) == Blocks.soul_sand) && (p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 2, p_149965_4_ + var6 + 1) == Blocks.soul_sand) && (p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 2) == Blocks.soul_sand) && (func_149966_a(p_149965_1_, p_149965_2_, p_149965_3_, p_149965_4_ + var6, 1)) && (func_149966_a(p_149965_1_, p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, 1)) && (func_149966_a(p_149965_1_, p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, 1)))
/* 151:    */         {
/* 152:194 */           p_149965_1_.setBlockMetadataWithNotify(p_149965_2_, p_149965_3_, p_149965_4_ + var6, 8, 2);
/* 153:195 */           p_149965_1_.setBlockMetadataWithNotify(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, 8, 2);
/* 154:196 */           p_149965_1_.setBlockMetadataWithNotify(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, 8, 2);
/* 155:197 */           p_149965_1_.setBlock(p_149965_2_, p_149965_3_, p_149965_4_ + var6, getBlockById(0), 0, 2);
/* 156:198 */           p_149965_1_.setBlock(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, getBlockById(0), 0, 2);
/* 157:199 */           p_149965_1_.setBlock(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, getBlockById(0), 0, 2);
/* 158:200 */           p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6, getBlockById(0), 0, 2);
/* 159:201 */           p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 1, getBlockById(0), 0, 2);
/* 160:202 */           p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 2, getBlockById(0), 0, 2);
/* 161:203 */           p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 2, p_149965_4_ + var6 + 1, getBlockById(0), 0, 2);
/* 162:205 */           if (!p_149965_1_.isClient)
/* 163:    */           {
/* 164:207 */             EntityWither var7 = new EntityWither(p_149965_1_);
/* 165:208 */             var7.setLocationAndAngles(p_149965_2_ + 0.5D, p_149965_3_ - 1.45D, p_149965_4_ + var6 + 1.5D, 90.0F, 0.0F);
/* 166:209 */             var7.renderYawOffset = 90.0F;
/* 167:210 */             var7.func_82206_m();
/* 168:212 */             if (!p_149965_1_.isClient)
/* 169:    */             {
/* 170:214 */               Iterator var8 = p_149965_1_.getEntitiesWithinAABB(EntityPlayer.class, var7.boundingBox.expand(50.0D, 50.0D, 50.0D)).iterator();
/* 171:216 */               while (var8.hasNext())
/* 172:    */               {
/* 173:218 */                 EntityPlayer var9 = (EntityPlayer)var8.next();
/* 174:219 */                 var9.triggerAchievement(AchievementList.field_150963_I);
/* 175:    */               }
/* 176:    */             }
/* 177:223 */             p_149965_1_.spawnEntityInWorld(var7);
/* 178:    */           }
/* 179:226 */           for (int var10 = 0; var10 < 120; var10++) {
/* 180:228 */             p_149965_1_.spawnParticle("snowballpoof", p_149965_2_ + p_149965_1_.rand.nextDouble(), p_149965_3_ - 2 + p_149965_1_.rand.nextDouble() * 3.9D, p_149965_4_ + var6 + 1 + p_149965_1_.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
/* 181:    */           }
/* 182:231 */           p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_, p_149965_4_ + var6, getBlockById(0));
/* 183:232 */           p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, getBlockById(0));
/* 184:233 */           p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, getBlockById(0));
/* 185:234 */           p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6, getBlockById(0));
/* 186:235 */           p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 1, getBlockById(0));
/* 187:236 */           p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 2, getBlockById(0));
/* 188:237 */           p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 2, p_149965_4_ + var6 + 1, getBlockById(0));
/* 189:238 */           return;
/* 190:    */         }
/* 191:    */       }
/* 192:242 */       for (var6 = -2; var6 <= 0; var6++) {
/* 193:244 */         if ((p_149965_1_.getBlock(p_149965_2_ + var6, p_149965_3_ - 1, p_149965_4_) == Blocks.soul_sand) && (p_149965_1_.getBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 1, p_149965_4_) == Blocks.soul_sand) && (p_149965_1_.getBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 2, p_149965_4_) == Blocks.soul_sand) && (p_149965_1_.getBlock(p_149965_2_ + var6 + 2, p_149965_3_ - 1, p_149965_4_) == Blocks.soul_sand) && (func_149966_a(p_149965_1_, p_149965_2_ + var6, p_149965_3_, p_149965_4_, 1)) && (func_149966_a(p_149965_1_, p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, 1)) && (func_149966_a(p_149965_1_, p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, 1)))
/* 194:    */         {
/* 195:246 */           p_149965_1_.setBlockMetadataWithNotify(p_149965_2_ + var6, p_149965_3_, p_149965_4_, 8, 2);
/* 196:247 */           p_149965_1_.setBlockMetadataWithNotify(p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, 8, 2);
/* 197:248 */           p_149965_1_.setBlockMetadataWithNotify(p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, 8, 2);
/* 198:249 */           p_149965_1_.setBlock(p_149965_2_ + var6, p_149965_3_, p_149965_4_, getBlockById(0), 0, 2);
/* 199:250 */           p_149965_1_.setBlock(p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, getBlockById(0), 0, 2);
/* 200:251 */           p_149965_1_.setBlock(p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, getBlockById(0), 0, 2);
/* 201:252 */           p_149965_1_.setBlock(p_149965_2_ + var6, p_149965_3_ - 1, p_149965_4_, getBlockById(0), 0, 2);
/* 202:253 */           p_149965_1_.setBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 1, p_149965_4_, getBlockById(0), 0, 2);
/* 203:254 */           p_149965_1_.setBlock(p_149965_2_ + var6 + 2, p_149965_3_ - 1, p_149965_4_, getBlockById(0), 0, 2);
/* 204:255 */           p_149965_1_.setBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 2, p_149965_4_, getBlockById(0), 0, 2);
/* 205:257 */           if (!p_149965_1_.isClient)
/* 206:    */           {
/* 207:259 */             EntityWither var7 = new EntityWither(p_149965_1_);
/* 208:260 */             var7.setLocationAndAngles(p_149965_2_ + var6 + 1.5D, p_149965_3_ - 1.45D, p_149965_4_ + 0.5D, 0.0F, 0.0F);
/* 209:261 */             var7.func_82206_m();
/* 210:263 */             if (!p_149965_1_.isClient)
/* 211:    */             {
/* 212:265 */               Iterator var8 = p_149965_1_.getEntitiesWithinAABB(EntityPlayer.class, var7.boundingBox.expand(50.0D, 50.0D, 50.0D)).iterator();
/* 213:267 */               while (var8.hasNext())
/* 214:    */               {
/* 215:269 */                 EntityPlayer var9 = (EntityPlayer)var8.next();
/* 216:270 */                 var9.triggerAchievement(AchievementList.field_150963_I);
/* 217:    */               }
/* 218:    */             }
/* 219:274 */             p_149965_1_.spawnEntityInWorld(var7);
/* 220:    */           }
/* 221:277 */           for (int var10 = 0; var10 < 120; var10++) {
/* 222:279 */             p_149965_1_.spawnParticle("snowballpoof", p_149965_2_ + var6 + 1 + p_149965_1_.rand.nextDouble(), p_149965_3_ - 2 + p_149965_1_.rand.nextDouble() * 3.9D, p_149965_4_ + p_149965_1_.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
/* 223:    */           }
/* 224:282 */           p_149965_1_.notifyBlockChange(p_149965_2_ + var6, p_149965_3_, p_149965_4_, getBlockById(0));
/* 225:283 */           p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, getBlockById(0));
/* 226:284 */           p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, getBlockById(0));
/* 227:285 */           p_149965_1_.notifyBlockChange(p_149965_2_ + var6, p_149965_3_ - 1, p_149965_4_, getBlockById(0));
/* 228:286 */           p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 1, p_149965_3_ - 1, p_149965_4_, getBlockById(0));
/* 229:287 */           p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 2, p_149965_3_ - 1, p_149965_4_, getBlockById(0));
/* 230:288 */           p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 1, p_149965_3_ - 2, p_149965_4_, getBlockById(0));
/* 231:289 */           return;
/* 232:    */         }
/* 233:    */       }
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   private boolean func_149966_a(World p_149966_1_, int p_149966_2_, int p_149966_3_, int p_149966_4_, int p_149966_5_)
/* 238:    */   {
/* 239:297 */     if (p_149966_1_.getBlock(p_149966_2_, p_149966_3_, p_149966_4_) != this) {
/* 240:299 */       return false;
/* 241:    */     }
/* 242:303 */     TileEntity var6 = p_149966_1_.getTileEntity(p_149966_2_, p_149966_3_, p_149966_4_);
/* 243:304 */     return ((TileEntitySkull)var6).func_145904_a() == p_149966_5_;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void registerBlockIcons(IIconRegister p_149651_1_) {}
/* 247:    */   
/* 248:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 249:    */   {
/* 250:315 */     return Blocks.soul_sand.getBlockTextureFromSide(p_149691_1_);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public String getItemIconName()
/* 254:    */   {
/* 255:323 */     return getTextureName() + "_" + net.minecraft.item.ItemSkull.field_94587_a[0];
/* 256:    */   }
/* 257:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSkull
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.HashMultiset;
/*   4:    */ import com.google.common.collect.Iterables;
/*   5:    */ import com.google.common.collect.Multisets;
/*   6:    */ import java.util.List;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.material.MapColor;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.entity.Entity;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.init.Items;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.network.Packet;
/*  16:    */ import net.minecraft.network.play.server.S34PacketMaps;
/*  17:    */ import net.minecraft.util.MathHelper;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ import net.minecraft.world.WorldProvider;
/*  20:    */ import net.minecraft.world.chunk.Chunk;
/*  21:    */ import net.minecraft.world.storage.MapData;
/*  22:    */ import net.minecraft.world.storage.MapData.MapInfo;
/*  23:    */ import net.minecraft.world.storage.WorldInfo;
/*  24:    */ 
/*  25:    */ public class ItemMap
/*  26:    */   extends ItemMapBase
/*  27:    */ {
/*  28:    */   private static final String __OBFID = "CL_00000047";
/*  29:    */   
/*  30:    */   protected ItemMap()
/*  31:    */   {
/*  32: 26 */     setHasSubtypes(true);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static MapData func_150912_a(int p_150912_0_, World p_150912_1_)
/*  36:    */   {
/*  37: 31 */     String var2 = "map_" + p_150912_0_;
/*  38: 32 */     MapData var3 = (MapData)p_150912_1_.loadItemData(MapData.class, var2);
/*  39: 34 */     if (var3 == null)
/*  40:    */     {
/*  41: 36 */       var3 = new MapData(var2);
/*  42: 37 */       p_150912_1_.setItemData(var2, var3);
/*  43:    */     }
/*  44: 40 */     return var3;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public MapData getMapData(ItemStack par1ItemStack, World par2World)
/*  48:    */   {
/*  49: 45 */     String var3 = "map_" + par1ItemStack.getItemDamage();
/*  50: 46 */     MapData var4 = (MapData)par2World.loadItemData(MapData.class, var3);
/*  51: 48 */     if ((var4 == null) && (!par2World.isClient))
/*  52:    */     {
/*  53: 50 */       par1ItemStack.setItemDamage(par2World.getUniqueDataId("map"));
/*  54: 51 */       var3 = "map_" + par1ItemStack.getItemDamage();
/*  55: 52 */       var4 = new MapData(var3);
/*  56: 53 */       var4.scale = 3;
/*  57: 54 */       int var5 = 128 * (1 << var4.scale);
/*  58: 55 */       var4.xCenter = (Math.round(par2World.getWorldInfo().getSpawnX() / var5) * var5);
/*  59: 56 */       var4.zCenter = (Math.round(par2World.getWorldInfo().getSpawnZ() / var5) * var5);
/*  60: 57 */       var4.dimension = ((byte)par2World.provider.dimensionId);
/*  61: 58 */       var4.markDirty();
/*  62: 59 */       par2World.setItemData(var3, var4);
/*  63:    */     }
/*  64: 62 */     return var4;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void updateMapData(World par1World, Entity par2Entity, MapData par3MapData)
/*  68:    */   {
/*  69: 67 */     if ((par1World.provider.dimensionId == par3MapData.dimension) && ((par2Entity instanceof EntityPlayer)))
/*  70:    */     {
/*  71: 69 */       int var4 = 1 << par3MapData.scale;
/*  72: 70 */       int var5 = par3MapData.xCenter;
/*  73: 71 */       int var6 = par3MapData.zCenter;
/*  74: 72 */       int var7 = MathHelper.floor_double(par2Entity.posX - var5) / var4 + 64;
/*  75: 73 */       int var8 = MathHelper.floor_double(par2Entity.posZ - var6) / var4 + 64;
/*  76: 74 */       int var9 = 128 / var4;
/*  77: 76 */       if (par1World.provider.hasNoSky) {
/*  78: 78 */         var9 /= 2;
/*  79:    */       }
/*  80: 81 */       MapData.MapInfo var10 = par3MapData.func_82568_a((EntityPlayer)par2Entity);
/*  81: 82 */       var10.field_82569_d += 1;
/*  82: 84 */       for (int var11 = var7 - var9 + 1; var11 < var7 + var9; var11++) {
/*  83: 86 */         if ((var11 & 0xF) == (var10.field_82569_d & 0xF))
/*  84:    */         {
/*  85: 88 */           int var12 = 255;
/*  86: 89 */           int var13 = 0;
/*  87: 90 */           double var14 = 0.0D;
/*  88: 92 */           for (int var16 = var8 - var9 - 1; var16 < var8 + var9; var16++) {
/*  89: 94 */             if ((var11 >= 0) && (var16 >= -1) && (var11 < 128) && (var16 < 128))
/*  90:    */             {
/*  91: 96 */               int var17 = var11 - var7;
/*  92: 97 */               int var18 = var16 - var8;
/*  93: 98 */               boolean var19 = var17 * var17 + var18 * var18 > (var9 - 2) * (var9 - 2);
/*  94: 99 */               int var20 = (var5 / var4 + var11 - 64) * var4;
/*  95:100 */               int var21 = (var6 / var4 + var16 - 64) * var4;
/*  96:101 */               HashMultiset var22 = HashMultiset.create();
/*  97:102 */               Chunk var23 = par1World.getChunkFromBlockCoords(var20, var21);
/*  98:104 */               if (!var23.isEmpty())
/*  99:    */               {
/* 100:106 */                 int var24 = var20 & 0xF;
/* 101:107 */                 int var25 = var21 & 0xF;
/* 102:108 */                 int var26 = 0;
/* 103:109 */                 double var27 = 0.0D;
/* 104:112 */                 if (par1World.provider.hasNoSky)
/* 105:    */                 {
/* 106:114 */                   int var29 = var20 + var21 * 231871;
/* 107:115 */                   var29 = var29 * var29 * 31287121 + var29 * 11;
/* 108:117 */                   if ((var29 >> 20 & 0x1) == 0) {
/* 109:119 */                     var22.add(Blocks.dirt.getMapColor(0), 10);
/* 110:    */                   } else {
/* 111:123 */                     var22.add(Blocks.stone.getMapColor(0), 100);
/* 112:    */                   }
/* 113:126 */                   var27 = 100.0D;
/* 114:    */                 }
/* 115:    */                 else
/* 116:    */                 {
/* 117:130 */                   for (int var29 = 0; var29 < var4; var29++) {
/* 118:132 */                     for (int var30 = 0; var30 < var4; var30++)
/* 119:    */                     {
/* 120:134 */                       int var31 = var23.getHeightValue(var29 + var24, var30 + var25) + 1;
/* 121:135 */                       Block var32 = Blocks.air;
/* 122:136 */                       int var33 = 0;
/* 123:138 */                       if (var31 > 1)
/* 124:    */                       {
/* 125:    */                         do
/* 126:    */                         {
/* 127:142 */                           var31--;
/* 128:143 */                           var32 = var23.func_150810_a(var29 + var24, var31, var30 + var25);
/* 129:144 */                           var33 = var23.getBlockMetadata(var29 + var24, var31, var30 + var25);
/* 130:146 */                         } while ((var32.getMapColor(var33) == MapColor.field_151660_b) && (var31 > 0));
/* 131:148 */                         if ((var31 > 0) && (var32.getMaterial().isLiquid()))
/* 132:    */                         {
/* 133:150 */                           int var34 = var31 - 1;
/* 134:    */                           Block var35;
/* 135:    */                           do
/* 136:    */                           {
/* 137:155 */                             var35 = var23.func_150810_a(var29 + var24, var34--, var30 + var25);
/* 138:156 */                             var26++;
/* 139:158 */                           } while ((var34 > 0) && (var35.getMaterial().isLiquid()));
/* 140:    */                         }
/* 141:    */                       }
/* 142:162 */                       var27 += var31 / (var4 * var4);
/* 143:163 */                       var22.add(var32.getMapColor(var33));
/* 144:    */                     }
/* 145:    */                   }
/* 146:    */                 }
/* 147:168 */                 var26 /= var4 * var4;
/* 148:169 */                 double var36 = (var27 - var14) * 4.0D / (var4 + 4) + ((var11 + var16 & 0x1) - 0.5D) * 0.4D;
/* 149:170 */                 byte var39 = 1;
/* 150:172 */                 if (var36 > 0.6D) {
/* 151:174 */                   var39 = 2;
/* 152:    */                 }
/* 153:177 */                 if (var36 < -0.6D) {
/* 154:179 */                   var39 = 0;
/* 155:    */                 }
/* 156:182 */                 MapColor var38 = (MapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(var22), MapColor.field_151660_b);
/* 157:184 */                 if (var38 == MapColor.field_151662_n)
/* 158:    */                 {
/* 159:186 */                   var36 = var26 * 0.1D + (var11 + var16 & 0x1) * 0.2D;
/* 160:187 */                   var39 = 1;
/* 161:189 */                   if (var36 < 0.5D) {
/* 162:191 */                     var39 = 2;
/* 163:    */                   }
/* 164:194 */                   if (var36 > 0.9D) {
/* 165:196 */                     var39 = 0;
/* 166:    */                   }
/* 167:    */                 }
/* 168:200 */                 var14 = var27;
/* 169:202 */                 if ((var16 >= 0) && (var17 * var17 + var18 * var18 < var9 * var9) && ((!var19) || ((var11 + var16 & 0x1) != 0)))
/* 170:    */                 {
/* 171:204 */                   byte var37 = par3MapData.colors[(var11 + var16 * 128)];
/* 172:205 */                   byte var40 = (byte)(var38.colorIndex * 4 + var39);
/* 173:207 */                   if (var37 != var40)
/* 174:    */                   {
/* 175:209 */                     if (var12 > var16) {
/* 176:211 */                       var12 = var16;
/* 177:    */                     }
/* 178:214 */                     if (var13 < var16) {
/* 179:216 */                       var13 = var16;
/* 180:    */                     }
/* 181:219 */                     par3MapData.colors[(var11 + var16 * 128)] = var40;
/* 182:    */                   }
/* 183:    */                 }
/* 184:    */               }
/* 185:    */             }
/* 186:    */           }
/* 187:226 */           if (var12 <= var13) {
/* 188:228 */             par3MapData.setColumnDirty(var11, var12, var13);
/* 189:    */           }
/* 190:    */         }
/* 191:    */       }
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
/* 196:    */   {
/* 197:241 */     if (!par2World.isClient)
/* 198:    */     {
/* 199:243 */       MapData var6 = getMapData(par1ItemStack, par2World);
/* 200:245 */       if ((par3Entity instanceof EntityPlayer))
/* 201:    */       {
/* 202:247 */         EntityPlayer var7 = (EntityPlayer)par3Entity;
/* 203:248 */         var6.updateVisiblePlayers(var7, par1ItemStack);
/* 204:    */       }
/* 205:251 */       if (par5) {
/* 206:253 */         updateMapData(par2World, par3Entity, var6);
/* 207:    */       }
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   public Packet func_150911_c(ItemStack p_150911_1_, World p_150911_2_, EntityPlayer p_150911_3_)
/* 212:    */   {
/* 213:260 */     byte[] var4 = getMapData(p_150911_1_, p_150911_2_).getUpdatePacketData(p_150911_1_, p_150911_2_, p_150911_3_);
/* 214:261 */     return var4 == null ? null : new S34PacketMaps(p_150911_1_.getItemDamage(), var4);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 218:    */   {
/* 219:269 */     if ((par1ItemStack.hasTagCompound()) && (par1ItemStack.getTagCompound().getBoolean("map_is_scaling")))
/* 220:    */     {
/* 221:271 */       MapData var4 = Items.filled_map.getMapData(par1ItemStack, par2World);
/* 222:272 */       par1ItemStack.setItemDamage(par2World.getUniqueDataId("map"));
/* 223:273 */       MapData var5 = new MapData("map_" + par1ItemStack.getItemDamage());
/* 224:274 */       var5.scale = ((byte)(var4.scale + 1));
/* 225:276 */       if (var5.scale > 4) {
/* 226:278 */         var5.scale = 4;
/* 227:    */       }
/* 228:281 */       var5.xCenter = var4.xCenter;
/* 229:282 */       var5.zCenter = var4.zCenter;
/* 230:283 */       var5.dimension = var4.dimension;
/* 231:284 */       var5.markDirty();
/* 232:285 */       par2World.setItemData("map_" + par1ItemStack.getItemDamage(), var5);
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 237:    */   {
/* 238:294 */     MapData var5 = getMapData(par1ItemStack, par2EntityPlayer.worldObj);
/* 239:296 */     if (par4) {
/* 240:298 */       if (var5 == null)
/* 241:    */       {
/* 242:300 */         par3List.add("Unknown map");
/* 243:    */       }
/* 244:    */       else
/* 245:    */       {
/* 246:304 */         par3List.add("Scaling at 1:" + (1 << var5.scale));
/* 247:305 */         par3List.add("(Level " + var5.scale + "/" + 4 + ")");
/* 248:    */       }
/* 249:    */     }
/* 250:    */   }
/* 251:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemMap
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.minecraft.world.gen.structure;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Random;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.concurrent.Callable;
/*  12:    */ import net.minecraft.block.Block;
/*  13:    */ import net.minecraft.crash.CrashReport;
/*  14:    */ import net.minecraft.crash.CrashReportCategory;
/*  15:    */ import net.minecraft.nbt.NBTBase;
/*  16:    */ import net.minecraft.nbt.NBTTagCompound;
/*  17:    */ import net.minecraft.util.ReportedException;
/*  18:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  19:    */ import net.minecraft.world.ChunkPosition;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ import net.minecraft.world.gen.MapGenBase;
/*  22:    */ 
/*  23:    */ public abstract class MapGenStructure
/*  24:    */   extends MapGenBase
/*  25:    */ {
/*  26:    */   private MapGenStructureData field_143029_e;
/*  27: 29 */   protected Map structureMap = new HashMap();
/*  28:    */   private static final String __OBFID = "CL_00000505";
/*  29:    */   
/*  30:    */   public abstract String func_143025_a();
/*  31:    */   
/*  32:    */   protected final void func_151538_a(World p_151538_1_, final int p_151538_2_, final int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_)
/*  33:    */   {
/*  34: 36 */     func_143027_a(p_151538_1_);
/*  35: 38 */     if (!this.structureMap.containsKey(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_))))
/*  36:    */     {
/*  37: 40 */       this.rand.nextInt();
/*  38:    */       try
/*  39:    */       {
/*  40: 44 */         if (canSpawnStructureAtCoords(p_151538_2_, p_151538_3_))
/*  41:    */         {
/*  42: 46 */           StructureStart var7 = getStructureStart(p_151538_2_, p_151538_3_);
/*  43: 47 */           this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_)), var7);
/*  44: 48 */           func_143026_a(p_151538_2_, p_151538_3_, var7);
/*  45:    */         }
/*  46:    */       }
/*  47:    */       catch (Throwable var10)
/*  48:    */       {
/*  49: 53 */         CrashReport var8 = CrashReport.makeCrashReport(var10, "Exception preparing structure feature");
/*  50: 54 */         CrashReportCategory var9 = var8.makeCategory("Feature being prepared");
/*  51: 55 */         var9.addCrashSectionCallable("Is feature chunk", new Callable()
/*  52:    */         {
/*  53:    */           private static final String __OBFID = "CL_00000506";
/*  54:    */           
/*  55:    */           public String call()
/*  56:    */           {
/*  57: 60 */             return MapGenStructure.this.canSpawnStructureAtCoords(p_151538_2_, p_151538_3_) ? "True" : "False";
/*  58:    */           }
/*  59: 62 */         });
/*  60: 63 */         var9.addCrashSection("Chunk location", String.format("%d,%d", new Object[] { Integer.valueOf(p_151538_2_), Integer.valueOf(p_151538_3_) }));
/*  61: 64 */         var9.addCrashSectionCallable("Chunk pos hash", new Callable()
/*  62:    */         {
/*  63:    */           private static final String __OBFID = "CL_00000507";
/*  64:    */           
/*  65:    */           public String call()
/*  66:    */           {
/*  67: 69 */             return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_));
/*  68:    */           }
/*  69: 71 */         });
/*  70: 72 */         var9.addCrashSectionCallable("Structure type", new Callable()
/*  71:    */         {
/*  72:    */           private static final String __OBFID = "CL_00000508";
/*  73:    */           
/*  74:    */           public String call()
/*  75:    */           {
/*  76: 77 */             return MapGenStructure.this.getClass().getCanonicalName();
/*  77:    */           }
/*  78: 79 */         });
/*  79: 80 */         throw new ReportedException(var8);
/*  80:    */       }
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean generateStructuresInChunk(World par1World, Random par2Random, int par3, int par4)
/*  85:    */   {
/*  86: 90 */     func_143027_a(par1World);
/*  87: 91 */     int var5 = (par3 << 4) + 8;
/*  88: 92 */     int var6 = (par4 << 4) + 8;
/*  89: 93 */     boolean var7 = false;
/*  90: 94 */     Iterator var8 = this.structureMap.values().iterator();
/*  91: 96 */     while (var8.hasNext())
/*  92:    */     {
/*  93: 98 */       StructureStart var9 = (StructureStart)var8.next();
/*  94:100 */       if ((var9.isSizeableStructure()) && (var9.getBoundingBox().intersectsWith(var5, var6, var5 + 15, var6 + 15)))
/*  95:    */       {
/*  96:102 */         var9.generateStructure(par1World, par2Random, new StructureBoundingBox(var5, var6, var5 + 15, var6 + 15));
/*  97:103 */         var7 = true;
/*  98:104 */         func_143026_a(var9.func_143019_e(), var9.func_143018_f(), var9);
/*  99:    */       }
/* 100:    */     }
/* 101:108 */     return var7;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean hasStructureAt(int par1, int par2, int par3)
/* 105:    */   {
/* 106:116 */     func_143027_a(this.worldObj);
/* 107:117 */     return func_143028_c(par1, par2, par3) != null;
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected StructureStart func_143028_c(int par1, int par2, int par3)
/* 111:    */   {
/* 112:122 */     Iterator var4 = this.structureMap.values().iterator();
/* 113:    */     Iterator var6;
/* 114:    */     label106:
/* 115:124 */     for (; var4.hasNext(); var6.hasNext())
/* 116:    */     {
/* 117:126 */       StructureStart var5 = (StructureStart)var4.next();
/* 118:128 */       if ((!var5.isSizeableStructure()) || (!var5.getBoundingBox().intersectsWith(par1, par3, par1, par3))) {
/* 119:    */         break label106;
/* 120:    */       }
/* 121:130 */       var6 = var5.getComponents().iterator();
/* 122:    */       
/* 123:132 */       continue;
/* 124:    */       
/* 125:134 */       StructureComponent var7 = (StructureComponent)var6.next();
/* 126:136 */       if (var7.getBoundingBox().isVecInside(par1, par2, par3)) {
/* 127:138 */         return var5;
/* 128:    */       }
/* 129:    */     }
/* 130:144 */     return null;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean func_142038_b(int par1, int par2, int par3)
/* 134:    */   {
/* 135:149 */     func_143027_a(this.worldObj);
/* 136:150 */     Iterator var4 = this.structureMap.values().iterator();
/* 137:    */     StructureStart var5;
/* 138:    */     do
/* 139:    */     {
/* 140:155 */       if (!var4.hasNext()) {
/* 141:157 */         return false;
/* 142:    */       }
/* 143:160 */       var5 = (StructureStart)var4.next();
/* 144:162 */     } while (!var5.isSizeableStructure());
/* 145:164 */     return var5.getBoundingBox().intersectsWith(par1, par3, par1, par3);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public ChunkPosition func_151545_a(World p_151545_1_, int p_151545_2_, int p_151545_3_, int p_151545_4_)
/* 149:    */   {
/* 150:169 */     this.worldObj = p_151545_1_;
/* 151:170 */     func_143027_a(p_151545_1_);
/* 152:171 */     this.rand.setSeed(p_151545_1_.getSeed());
/* 153:172 */     long var5 = this.rand.nextLong();
/* 154:173 */     long var7 = this.rand.nextLong();
/* 155:174 */     long var9 = (p_151545_2_ >> 4) * var5;
/* 156:175 */     long var11 = (p_151545_4_ >> 4) * var7;
/* 157:176 */     this.rand.setSeed(var9 ^ var11 ^ p_151545_1_.getSeed());
/* 158:177 */     func_151538_a(p_151545_1_, p_151545_2_ >> 4, p_151545_4_ >> 4, 0, 0, null);
/* 159:178 */     double var13 = 1.7976931348623157E+308D;
/* 160:179 */     ChunkPosition var15 = null;
/* 161:180 */     Iterator var16 = this.structureMap.values().iterator();
/* 162:187 */     while (var16.hasNext())
/* 163:    */     {
/* 164:189 */       StructureStart var17 = (StructureStart)var16.next();
/* 165:191 */       if (var17.isSizeableStructure())
/* 166:    */       {
/* 167:193 */         StructureComponent var18 = (StructureComponent)var17.getComponents().get(0);
/* 168:194 */         ChunkPosition var19 = var18.func_151553_a();
/* 169:195 */         int var20 = var19.field_151329_a - p_151545_2_;
/* 170:196 */         int var21 = var19.field_151327_b - p_151545_3_;
/* 171:197 */         int var22 = var19.field_151328_c - p_151545_4_;
/* 172:198 */         double var23 = var20 * var20 + var21 * var21 + var22 * var22;
/* 173:200 */         if (var23 < var13)
/* 174:    */         {
/* 175:202 */           var13 = var23;
/* 176:203 */           var15 = var19;
/* 177:    */         }
/* 178:    */       }
/* 179:    */     }
/* 180:208 */     if (var15 != null) {
/* 181:210 */       return var15;
/* 182:    */     }
/* 183:214 */     List var25 = getCoordList();
/* 184:216 */     if (var25 != null)
/* 185:    */     {
/* 186:218 */       ChunkPosition var26 = null;
/* 187:219 */       Iterator var27 = var25.iterator();
/* 188:221 */       while (var27.hasNext())
/* 189:    */       {
/* 190:223 */         ChunkPosition var19 = (ChunkPosition)var27.next();
/* 191:224 */         int var20 = var19.field_151329_a - p_151545_2_;
/* 192:225 */         int var21 = var19.field_151327_b - p_151545_3_;
/* 193:226 */         int var22 = var19.field_151328_c - p_151545_4_;
/* 194:227 */         double var23 = var20 * var20 + var21 * var21 + var22 * var22;
/* 195:229 */         if (var23 < var13)
/* 196:    */         {
/* 197:231 */           var13 = var23;
/* 198:232 */           var26 = var19;
/* 199:    */         }
/* 200:    */       }
/* 201:236 */       return var26;
/* 202:    */     }
/* 203:240 */     return null;
/* 204:    */   }
/* 205:    */   
/* 206:    */   protected List getCoordList()
/* 207:    */   {
/* 208:251 */     return null;
/* 209:    */   }
/* 210:    */   
/* 211:    */   private void func_143027_a(World par1World)
/* 212:    */   {
/* 213:256 */     if (this.field_143029_e == null)
/* 214:    */     {
/* 215:258 */       this.field_143029_e = ((MapGenStructureData)par1World.loadItemData(MapGenStructureData.class, func_143025_a()));
/* 216:260 */       if (this.field_143029_e == null)
/* 217:    */       {
/* 218:262 */         this.field_143029_e = new MapGenStructureData(func_143025_a());
/* 219:263 */         par1World.setItemData(func_143025_a(), this.field_143029_e);
/* 220:    */       }
/* 221:    */       else
/* 222:    */       {
/* 223:267 */         NBTTagCompound var2 = this.field_143029_e.func_143041_a();
/* 224:268 */         Iterator var3 = var2.func_150296_c().iterator();
/* 225:270 */         while (var3.hasNext())
/* 226:    */         {
/* 227:272 */           String var4 = (String)var3.next();
/* 228:273 */           NBTBase var5 = var2.getTag(var4);
/* 229:275 */           if (var5.getId() == 10)
/* 230:    */           {
/* 231:277 */             NBTTagCompound var6 = (NBTTagCompound)var5;
/* 232:279 */             if ((var6.hasKey("ChunkX")) && (var6.hasKey("ChunkZ")))
/* 233:    */             {
/* 234:281 */               int var7 = var6.getInteger("ChunkX");
/* 235:282 */               int var8 = var6.getInteger("ChunkZ");
/* 236:283 */               StructureStart var9 = MapGenStructureIO.func_143035_a(var6, par1World);
/* 237:284 */               this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(var7, var8)), var9);
/* 238:    */             }
/* 239:    */           }
/* 240:    */         }
/* 241:    */       }
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   private void func_143026_a(int par1, int par2, StructureStart par3StructureStart)
/* 246:    */   {
/* 247:294 */     this.field_143029_e.func_143043_a(par3StructureStart.func_143021_a(par1, par2), par1, par2);
/* 248:295 */     this.field_143029_e.markDirty();
/* 249:    */   }
/* 250:    */   
/* 251:    */   protected abstract boolean canSpawnStructureAtCoords(int paramInt1, int paramInt2);
/* 252:    */   
/* 253:    */   protected abstract StructureStart getStructureStart(int paramInt1, int paramInt2);
/* 254:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.MapGenStructure
 * JD-Core Version:    0.7.0.1
 */
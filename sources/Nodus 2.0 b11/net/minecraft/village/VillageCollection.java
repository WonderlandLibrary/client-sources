/*   1:    */ package net.minecraft.village;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.block.BlockDoor;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.nbt.NBTTagList;
/*  10:    */ import net.minecraft.util.ChunkCoordinates;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ import net.minecraft.world.WorldSavedData;
/*  13:    */ 
/*  14:    */ public class VillageCollection
/*  15:    */   extends WorldSavedData
/*  16:    */ {
/*  17:    */   private World worldObj;
/*  18: 22 */   private final List villagerPositionsList = new ArrayList();
/*  19: 23 */   private final List newDoors = new ArrayList();
/*  20: 24 */   private final List villageList = new ArrayList();
/*  21:    */   private int tickCounter;
/*  22:    */   private static final String __OBFID = "CL_00001635";
/*  23:    */   
/*  24:    */   public VillageCollection(String par1Str)
/*  25:    */   {
/*  26: 30 */     super(par1Str);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public VillageCollection(World par1World)
/*  30:    */   {
/*  31: 35 */     super("villages");
/*  32: 36 */     this.worldObj = par1World;
/*  33: 37 */     markDirty();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void func_82566_a(World par1World)
/*  37:    */   {
/*  38: 42 */     this.worldObj = par1World;
/*  39: 43 */     Iterator var2 = this.villageList.iterator();
/*  40: 45 */     while (var2.hasNext())
/*  41:    */     {
/*  42: 47 */       Village var3 = (Village)var2.next();
/*  43: 48 */       var3.func_82691_a(par1World);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void addVillagerPosition(int par1, int par2, int par3)
/*  48:    */   {
/*  49: 58 */     if (this.villagerPositionsList.size() <= 64) {
/*  50: 60 */       if (!isVillagerPositionPresent(par1, par2, par3)) {
/*  51: 62 */         this.villagerPositionsList.add(new ChunkCoordinates(par1, par2, par3));
/*  52:    */       }
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void tick()
/*  57:    */   {
/*  58: 72 */     this.tickCounter += 1;
/*  59: 73 */     Iterator var1 = this.villageList.iterator();
/*  60: 75 */     while (var1.hasNext())
/*  61:    */     {
/*  62: 77 */       Village var2 = (Village)var1.next();
/*  63: 78 */       var2.tick(this.tickCounter);
/*  64:    */     }
/*  65: 81 */     removeAnnihilatedVillages();
/*  66: 82 */     dropOldestVillagerPosition();
/*  67: 83 */     addNewDoorsToVillageOrCreateVillage();
/*  68: 85 */     if (this.tickCounter % 400 == 0) {
/*  69: 87 */       markDirty();
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void removeAnnihilatedVillages()
/*  74:    */   {
/*  75: 93 */     Iterator var1 = this.villageList.iterator();
/*  76: 95 */     while (var1.hasNext())
/*  77:    */     {
/*  78: 97 */       Village var2 = (Village)var1.next();
/*  79: 99 */       if (var2.isAnnihilated())
/*  80:    */       {
/*  81:101 */         var1.remove();
/*  82:102 */         markDirty();
/*  83:    */       }
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List getVillageList()
/*  88:    */   {
/*  89:112 */     return this.villageList;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Village findNearestVillage(int par1, int par2, int par3, int par4)
/*  93:    */   {
/*  94:121 */     Village var5 = null;
/*  95:122 */     float var6 = 3.4028235E+38F;
/*  96:123 */     Iterator var7 = this.villageList.iterator();
/*  97:125 */     while (var7.hasNext())
/*  98:    */     {
/*  99:127 */       Village var8 = (Village)var7.next();
/* 100:128 */       float var9 = var8.getCenter().getDistanceSquared(par1, par2, par3);
/* 101:130 */       if (var9 < var6)
/* 102:    */       {
/* 103:132 */         float var10 = par4 + var8.getVillageRadius();
/* 104:134 */         if (var9 <= var10 * var10)
/* 105:    */         {
/* 106:136 */           var5 = var8;
/* 107:137 */           var6 = var9;
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111:142 */     return var5;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private void dropOldestVillagerPosition()
/* 115:    */   {
/* 116:147 */     if (!this.villagerPositionsList.isEmpty()) {
/* 117:149 */       addUnassignedWoodenDoorsAroundToNewDoorsList((ChunkCoordinates)this.villagerPositionsList.remove(0));
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   private void addNewDoorsToVillageOrCreateVillage()
/* 122:    */   {
/* 123:155 */     int var1 = 0;
/* 124:157 */     while (var1 < this.newDoors.size())
/* 125:    */     {
/* 126:159 */       VillageDoorInfo var2 = (VillageDoorInfo)this.newDoors.get(var1);
/* 127:160 */       boolean var3 = false;
/* 128:161 */       Iterator var4 = this.villageList.iterator();
/* 129:165 */       while (var4.hasNext())
/* 130:    */       {
/* 131:167 */         Village var5 = (Village)var4.next();
/* 132:168 */         int var6 = (int)var5.getCenter().getDistanceSquared(var2.posX, var2.posY, var2.posZ);
/* 133:169 */         int var7 = 32 + var5.getVillageRadius();
/* 134:171 */         if (var6 <= var7 * var7)
/* 135:    */         {
/* 136:176 */           var5.addVillageDoorInfo(var2);
/* 137:177 */           var3 = true;
/* 138:    */         }
/* 139:    */       }
/* 140:180 */       if (!var3)
/* 141:    */       {
/* 142:182 */         Village var8 = new Village(this.worldObj);
/* 143:183 */         var8.addVillageDoorInfo(var2);
/* 144:184 */         this.villageList.add(var8);
/* 145:185 */         markDirty();
/* 146:    */       }
/* 147:188 */       var1++;
/* 148:    */     }
/* 149:193 */     this.newDoors.clear();
/* 150:    */   }
/* 151:    */   
/* 152:    */   private void addUnassignedWoodenDoorsAroundToNewDoorsList(ChunkCoordinates par1ChunkCoordinates)
/* 153:    */   {
/* 154:198 */     byte var2 = 16;
/* 155:199 */     byte var3 = 4;
/* 156:200 */     byte var4 = 16;
/* 157:202 */     for (int var5 = par1ChunkCoordinates.posX - var2; var5 < par1ChunkCoordinates.posX + var2; var5++) {
/* 158:204 */       for (int var6 = par1ChunkCoordinates.posY - var3; var6 < par1ChunkCoordinates.posY + var3; var6++) {
/* 159:206 */         for (int var7 = par1ChunkCoordinates.posZ - var4; var7 < par1ChunkCoordinates.posZ + var4; var7++) {
/* 160:208 */           if (isWoodenDoorAt(var5, var6, var7))
/* 161:    */           {
/* 162:210 */             VillageDoorInfo var8 = getVillageDoorAt(var5, var6, var7);
/* 163:212 */             if (var8 == null) {
/* 164:214 */               addDoorToNewListIfAppropriate(var5, var6, var7);
/* 165:    */             } else {
/* 166:218 */               var8.lastActivityTimestamp = this.tickCounter;
/* 167:    */             }
/* 168:    */           }
/* 169:    */         }
/* 170:    */       }
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   private VillageDoorInfo getVillageDoorAt(int par1, int par2, int par3)
/* 175:    */   {
/* 176:228 */     Iterator var4 = this.newDoors.iterator();
/* 177:    */     VillageDoorInfo var5;
/* 178:    */     do
/* 179:    */     {
/* 180:233 */       if (!var4.hasNext())
/* 181:    */       {
/* 182:235 */         var4 = this.villageList.iterator();
/* 183:    */         VillageDoorInfo var6;
/* 184:    */         do
/* 185:    */         {
/* 186:240 */           if (!var4.hasNext()) {
/* 187:242 */             return null;
/* 188:    */           }
/* 189:245 */           Village var7 = (Village)var4.next();
/* 190:246 */           var6 = var7.getVillageDoorAt(par1, par2, par3);
/* 191:248 */         } while (var6 == null);
/* 192:250 */         return var6;
/* 193:    */       }
/* 194:253 */       var5 = (VillageDoorInfo)var4.next();
/* 195:255 */     } while ((var5.posX != par1) || (var5.posZ != par3) || (Math.abs(var5.posY - par2) > 1));
/* 196:257 */     return var5;
/* 197:    */   }
/* 198:    */   
/* 199:    */   private void addDoorToNewListIfAppropriate(int par1, int par2, int par3)
/* 200:    */   {
/* 201:262 */     int var4 = ((BlockDoor)Blocks.wooden_door).func_150013_e(this.worldObj, par1, par2, par3);
/* 202:266 */     if ((var4 != 0) && (var4 != 2))
/* 203:    */     {
/* 204:268 */       int var5 = 0;
/* 205:270 */       for (int var6 = -5; var6 < 0; var6++) {
/* 206:272 */         if (this.worldObj.canBlockSeeTheSky(par1, par2, par3 + var6)) {
/* 207:274 */           var5--;
/* 208:    */         }
/* 209:    */       }
/* 210:278 */       for (var6 = 1; var6 <= 5; var6++) {
/* 211:280 */         if (this.worldObj.canBlockSeeTheSky(par1, par2, par3 + var6)) {
/* 212:282 */           var5++;
/* 213:    */         }
/* 214:    */       }
/* 215:286 */       if (var5 != 0) {
/* 216:288 */         this.newDoors.add(new VillageDoorInfo(par1, par2, par3, 0, var5 > 0 ? -2 : 2, this.tickCounter));
/* 217:    */       }
/* 218:    */     }
/* 219:    */     else
/* 220:    */     {
/* 221:293 */       int var5 = 0;
/* 222:295 */       for (int var6 = -5; var6 < 0; var6++) {
/* 223:297 */         if (this.worldObj.canBlockSeeTheSky(par1 + var6, par2, par3)) {
/* 224:299 */           var5--;
/* 225:    */         }
/* 226:    */       }
/* 227:303 */       for (var6 = 1; var6 <= 5; var6++) {
/* 228:305 */         if (this.worldObj.canBlockSeeTheSky(par1 + var6, par2, par3)) {
/* 229:307 */           var5++;
/* 230:    */         }
/* 231:    */       }
/* 232:311 */       if (var5 != 0) {
/* 233:313 */         this.newDoors.add(new VillageDoorInfo(par1, par2, par3, var5 > 0 ? -2 : 2, 0, this.tickCounter));
/* 234:    */       }
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   private boolean isVillagerPositionPresent(int par1, int par2, int par3)
/* 239:    */   {
/* 240:320 */     Iterator var4 = this.villagerPositionsList.iterator();
/* 241:    */     ChunkCoordinates var5;
/* 242:    */     do
/* 243:    */     {
/* 244:325 */       if (!var4.hasNext()) {
/* 245:327 */         return false;
/* 246:    */       }
/* 247:330 */       var5 = (ChunkCoordinates)var4.next();
/* 248:332 */     } while ((var5.posX != par1) || (var5.posY != par2) || (var5.posZ != par3));
/* 249:334 */     return true;
/* 250:    */   }
/* 251:    */   
/* 252:    */   private boolean isWoodenDoorAt(int par1, int par2, int par3)
/* 253:    */   {
/* 254:339 */     return this.worldObj.getBlock(par1, par2, par3) == Blocks.wooden_door;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/* 258:    */   {
/* 259:347 */     this.tickCounter = par1NBTTagCompound.getInteger("Tick");
/* 260:348 */     NBTTagList var2 = par1NBTTagCompound.getTagList("Villages", 10);
/* 261:350 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/* 262:    */     {
/* 263:352 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 264:353 */       Village var5 = new Village();
/* 265:354 */       var5.readVillageDataFromNBT(var4);
/* 266:355 */       this.villageList.add(var5);
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/* 271:    */   {
/* 272:364 */     par1NBTTagCompound.setInteger("Tick", this.tickCounter);
/* 273:365 */     NBTTagList var2 = new NBTTagList();
/* 274:366 */     Iterator var3 = this.villageList.iterator();
/* 275:368 */     while (var3.hasNext())
/* 276:    */     {
/* 277:370 */       Village var4 = (Village)var3.next();
/* 278:371 */       NBTTagCompound var5 = new NBTTagCompound();
/* 279:372 */       var4.writeVillageDataToNBT(var5);
/* 280:373 */       var2.appendTag(var5);
/* 281:    */     }
/* 282:376 */     par1NBTTagCompound.setTag("Villages", var2);
/* 283:    */   }
/* 284:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.village.VillageCollection
 * JD-Core Version:    0.7.0.1
 */
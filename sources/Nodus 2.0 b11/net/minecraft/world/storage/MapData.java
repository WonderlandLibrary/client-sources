/*   1:    */ package net.minecraft.world.storage;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.LinkedHashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import net.minecraft.entity.item.EntityItemFrame;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ import net.minecraft.world.WorldSavedData;
/*  17:    */ 
/*  18:    */ public class MapData
/*  19:    */   extends WorldSavedData
/*  20:    */ {
/*  21:    */   public int xCenter;
/*  22:    */   public int zCenter;
/*  23:    */   public byte dimension;
/*  24:    */   public byte scale;
/*  25: 23 */   public byte[] colors = new byte[16384];
/*  26: 28 */   public List playersArrayList = new ArrayList();
/*  27: 33 */   private Map playersHashMap = new HashMap();
/*  28: 34 */   public Map playersVisibleOnMap = new LinkedHashMap();
/*  29:    */   private static final String __OBFID = "CL_00000577";
/*  30:    */   
/*  31:    */   public MapData(String par1Str)
/*  32:    */   {
/*  33: 39 */     super(par1Str);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/*  37:    */   {
/*  38: 47 */     this.dimension = par1NBTTagCompound.getByte("dimension");
/*  39: 48 */     this.xCenter = par1NBTTagCompound.getInteger("xCenter");
/*  40: 49 */     this.zCenter = par1NBTTagCompound.getInteger("zCenter");
/*  41: 50 */     this.scale = par1NBTTagCompound.getByte("scale");
/*  42: 52 */     if (this.scale < 0) {
/*  43: 54 */       this.scale = 0;
/*  44:    */     }
/*  45: 57 */     if (this.scale > 4) {
/*  46: 59 */       this.scale = 4;
/*  47:    */     }
/*  48: 62 */     short var2 = par1NBTTagCompound.getShort("width");
/*  49: 63 */     short var3 = par1NBTTagCompound.getShort("height");
/*  50: 65 */     if ((var2 == 128) && (var3 == 128))
/*  51:    */     {
/*  52: 67 */       this.colors = par1NBTTagCompound.getByteArray("colors");
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 71 */       byte[] var4 = par1NBTTagCompound.getByteArray("colors");
/*  57: 72 */       this.colors = new byte[16384];
/*  58: 73 */       int var5 = (128 - var2) / 2;
/*  59: 74 */       int var6 = (128 - var3) / 2;
/*  60: 76 */       for (int var7 = 0; var7 < var3; var7++)
/*  61:    */       {
/*  62: 78 */         int var8 = var7 + var6;
/*  63: 80 */         if ((var8 >= 0) || (var8 < 128)) {
/*  64: 82 */           for (int var9 = 0; var9 < var2; var9++)
/*  65:    */           {
/*  66: 84 */             int var10 = var9 + var5;
/*  67: 86 */             if ((var10 >= 0) || (var10 < 128)) {
/*  68: 88 */               this.colors[(var10 + var8 * 128)] = var4[(var9 + var7 * var2)];
/*  69:    */             }
/*  70:    */           }
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/*  77:    */   {
/*  78:101 */     par1NBTTagCompound.setByte("dimension", this.dimension);
/*  79:102 */     par1NBTTagCompound.setInteger("xCenter", this.xCenter);
/*  80:103 */     par1NBTTagCompound.setInteger("zCenter", this.zCenter);
/*  81:104 */     par1NBTTagCompound.setByte("scale", this.scale);
/*  82:105 */     par1NBTTagCompound.setShort("width", (short)128);
/*  83:106 */     par1NBTTagCompound.setShort("height", (short)128);
/*  84:107 */     par1NBTTagCompound.setByteArray("colors", this.colors);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void updateVisiblePlayers(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
/*  88:    */   {
/*  89:115 */     if (!this.playersHashMap.containsKey(par1EntityPlayer))
/*  90:    */     {
/*  91:117 */       MapInfo var3 = new MapInfo(par1EntityPlayer);
/*  92:118 */       this.playersHashMap.put(par1EntityPlayer, var3);
/*  93:119 */       this.playersArrayList.add(var3);
/*  94:    */     }
/*  95:122 */     if (!par1EntityPlayer.inventory.hasItemStack(par2ItemStack)) {
/*  96:124 */       this.playersVisibleOnMap.remove(par1EntityPlayer.getCommandSenderName());
/*  97:    */     }
/*  98:127 */     for (int var5 = 0; var5 < this.playersArrayList.size(); var5++)
/*  99:    */     {
/* 100:129 */       MapInfo var4 = (MapInfo)this.playersArrayList.get(var5);
/* 101:131 */       if ((!var4.entityplayerObj.isDead) && ((var4.entityplayerObj.inventory.hasItemStack(par2ItemStack)) || (par2ItemStack.isOnItemFrame())))
/* 102:    */       {
/* 103:133 */         if ((!par2ItemStack.isOnItemFrame()) && (var4.entityplayerObj.dimension == this.dimension)) {
/* 104:135 */           func_82567_a(0, var4.entityplayerObj.worldObj, var4.entityplayerObj.getCommandSenderName(), var4.entityplayerObj.posX, var4.entityplayerObj.posZ, var4.entityplayerObj.rotationYaw);
/* 105:    */         }
/* 106:    */       }
/* 107:    */       else
/* 108:    */       {
/* 109:140 */         this.playersHashMap.remove(var4.entityplayerObj);
/* 110:141 */         this.playersArrayList.remove(var4);
/* 111:    */       }
/* 112:    */     }
/* 113:145 */     if (par2ItemStack.isOnItemFrame()) {
/* 114:147 */       func_82567_a(1, par1EntityPlayer.worldObj, "frame-" + par2ItemStack.getItemFrame().getEntityId(), par2ItemStack.getItemFrame().field_146063_b, par2ItemStack.getItemFrame().field_146062_d, par2ItemStack.getItemFrame().hangingDirection * 90);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void func_82567_a(int par1, World par2World, String par3Str, double par4, double par6, double par8)
/* 119:    */   {
/* 120:153 */     int var10 = 1 << this.scale;
/* 121:154 */     float var11 = (float)(par4 - this.xCenter) / var10;
/* 122:155 */     float var12 = (float)(par6 - this.zCenter) / var10;
/* 123:156 */     byte var13 = (byte)(int)(var11 * 2.0F + 0.5D);
/* 124:157 */     byte var14 = (byte)(int)(var12 * 2.0F + 0.5D);
/* 125:158 */     byte var16 = 63;
/* 126:    */     byte var15;
/* 127:161 */     if ((var11 >= -var16) && (var12 >= -var16) && (var11 <= var16) && (var12 <= var16))
/* 128:    */     {
/* 129:163 */       par8 += (par8 < 0.0D ? -8.0D : 8.0D);
/* 130:164 */       byte var15 = (byte)(int)(par8 * 16.0D / 360.0D);
/* 131:166 */       if (this.dimension < 0)
/* 132:    */       {
/* 133:168 */         int var17 = (int)(par2World.getWorldInfo().getWorldTime() / 10L);
/* 134:169 */         var15 = (byte)(var17 * var17 * 34187121 + var17 * 121 >> 15 & 0xF);
/* 135:    */       }
/* 136:    */     }
/* 137:    */     else
/* 138:    */     {
/* 139:174 */       if ((Math.abs(var11) >= 320.0F) || (Math.abs(var12) >= 320.0F))
/* 140:    */       {
/* 141:176 */         this.playersVisibleOnMap.remove(par3Str);
/* 142:177 */         return;
/* 143:    */       }
/* 144:180 */       par1 = 6;
/* 145:181 */       var15 = 0;
/* 146:183 */       if (var11 <= -var16) {
/* 147:185 */         var13 = (byte)(int)(var16 * 2 + 2.5D);
/* 148:    */       }
/* 149:188 */       if (var12 <= -var16) {
/* 150:190 */         var14 = (byte)(int)(var16 * 2 + 2.5D);
/* 151:    */       }
/* 152:193 */       if (var11 >= var16) {
/* 153:195 */         var13 = (byte)(var16 * 2 + 1);
/* 154:    */       }
/* 155:198 */       if (var12 >= var16) {
/* 156:200 */         var14 = (byte)(var16 * 2 + 1);
/* 157:    */       }
/* 158:    */     }
/* 159:204 */     this.playersVisibleOnMap.put(par3Str, new MapCoord((byte)par1, var13, var14, var15));
/* 160:    */   }
/* 161:    */   
/* 162:    */   public byte[] getUpdatePacketData(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 163:    */   {
/* 164:212 */     MapInfo var4 = (MapInfo)this.playersHashMap.get(par3EntityPlayer);
/* 165:213 */     return var4 == null ? null : var4.getPlayersOnMap(par1ItemStack);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setColumnDirty(int par1, int par2, int par3)
/* 169:    */   {
/* 170:222 */     super.markDirty();
/* 171:224 */     for (int var4 = 0; var4 < this.playersArrayList.size(); var4++)
/* 172:    */     {
/* 173:226 */       MapInfo var5 = (MapInfo)this.playersArrayList.get(var4);
/* 174:228 */       if ((var5.field_76209_b[par1] < 0) || (var5.field_76209_b[par1] > par2)) {
/* 175:230 */         var5.field_76209_b[par1] = par2;
/* 176:    */       }
/* 177:233 */       if ((var5.field_76210_c[par1] < 0) || (var5.field_76210_c[par1] < par3)) {
/* 178:235 */         var5.field_76210_c[par1] = par3;
/* 179:    */       }
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void updateMPMapData(byte[] par1ArrayOfByte)
/* 184:    */   {
/* 185:247 */     if (par1ArrayOfByte[0] == 0)
/* 186:    */     {
/* 187:249 */       int var2 = par1ArrayOfByte[1] & 0xFF;
/* 188:250 */       int var3 = par1ArrayOfByte[2] & 0xFF;
/* 189:252 */       for (int var4 = 0; var4 < par1ArrayOfByte.length - 3; var4++) {
/* 190:254 */         this.colors[((var4 + var3) * 128 + var2)] = par1ArrayOfByte[(var4 + 3)];
/* 191:    */       }
/* 192:257 */       markDirty();
/* 193:    */     }
/* 194:259 */     else if (par1ArrayOfByte[0] == 1)
/* 195:    */     {
/* 196:261 */       this.playersVisibleOnMap.clear();
/* 197:263 */       for (int var2 = 0; var2 < (par1ArrayOfByte.length - 1) / 3; var2++)
/* 198:    */       {
/* 199:265 */         byte var7 = (byte)(par1ArrayOfByte[(var2 * 3 + 1)] >> 4);
/* 200:266 */         byte var8 = par1ArrayOfByte[(var2 * 3 + 2)];
/* 201:267 */         byte var5 = par1ArrayOfByte[(var2 * 3 + 3)];
/* 202:268 */         byte var6 = (byte)(par1ArrayOfByte[(var2 * 3 + 1)] & 0xF);
/* 203:269 */         this.playersVisibleOnMap.put("icon-" + var2, new MapCoord(var7, var8, var5, var6));
/* 204:    */       }
/* 205:    */     }
/* 206:272 */     else if (par1ArrayOfByte[0] == 2)
/* 207:    */     {
/* 208:274 */       this.scale = par1ArrayOfByte[1];
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public MapInfo func_82568_a(EntityPlayer par1EntityPlayer)
/* 213:    */   {
/* 214:280 */     MapInfo var2 = (MapInfo)this.playersHashMap.get(par1EntityPlayer);
/* 215:282 */     if (var2 == null)
/* 216:    */     {
/* 217:284 */       var2 = new MapInfo(par1EntityPlayer);
/* 218:285 */       this.playersHashMap.put(par1EntityPlayer, var2);
/* 219:286 */       this.playersArrayList.add(var2);
/* 220:    */     }
/* 221:289 */     return var2;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public class MapCoord
/* 225:    */   {
/* 226:    */     public byte iconSize;
/* 227:    */     public byte centerX;
/* 228:    */     public byte centerZ;
/* 229:    */     public byte iconRotation;
/* 230:    */     private static final String __OBFID = "CL_00000579";
/* 231:    */     
/* 232:    */     public MapCoord(byte par2, byte par3, byte par4, byte par5)
/* 233:    */     {
/* 234:302 */       this.iconSize = par2;
/* 235:303 */       this.centerX = par3;
/* 236:304 */       this.centerZ = par4;
/* 237:305 */       this.iconRotation = par5;
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public class MapInfo
/* 242:    */   {
/* 243:    */     public final EntityPlayer entityplayerObj;
/* 244:312 */     public int[] field_76209_b = new int[''];
/* 245:313 */     public int[] field_76210_c = new int[''];
/* 246:    */     private int currentRandomNumber;
/* 247:    */     private int ticksUntilPlayerLocationMapUpdate;
/* 248:    */     private byte[] lastPlayerLocationOnMap;
/* 249:    */     public int field_82569_d;
/* 250:    */     private boolean field_82570_i;
/* 251:    */     private static final String __OBFID = "CL_00000578";
/* 252:    */     
/* 253:    */     public MapInfo(EntityPlayer par2EntityPlayer)
/* 254:    */     {
/* 255:323 */       this.entityplayerObj = par2EntityPlayer;
/* 256:325 */       for (int var3 = 0; var3 < this.field_76209_b.length; var3++)
/* 257:    */       {
/* 258:327 */         this.field_76209_b[var3] = 0;
/* 259:328 */         this.field_76210_c[var3] = 127;
/* 260:    */       }
/* 261:    */     }
/* 262:    */     
/* 263:    */     public byte[] getPlayersOnMap(ItemStack par1ItemStack)
/* 264:    */     {
/* 265:336 */       if (!this.field_82570_i)
/* 266:    */       {
/* 267:338 */         byte[] var2 = { 2, MapData.this.scale };
/* 268:339 */         this.field_82570_i = true;
/* 269:340 */         return var2;
/* 270:    */       }
/* 271:347 */       if (--this.ticksUntilPlayerLocationMapUpdate < 0)
/* 272:    */       {
/* 273:349 */         this.ticksUntilPlayerLocationMapUpdate = 4;
/* 274:350 */         byte[] var2 = new byte[MapData.this.playersVisibleOnMap.size() * 3 + 1];
/* 275:351 */         var2[0] = 1;
/* 276:352 */         int var3 = 0;
/* 277:354 */         for (Iterator var4 = MapData.this.playersVisibleOnMap.values().iterator(); var4.hasNext(); var3++)
/* 278:    */         {
/* 279:356 */           MapData.MapCoord var5 = (MapData.MapCoord)var4.next();
/* 280:357 */           var2[(var3 * 3 + 1)] = ((byte)(var5.iconSize << 4 | var5.iconRotation & 0xF));
/* 281:358 */           var2[(var3 * 3 + 2)] = var5.centerX;
/* 282:359 */           var2[(var3 * 3 + 3)] = var5.centerZ;
/* 283:    */         }
/* 284:362 */         boolean var9 = !par1ItemStack.isOnItemFrame();
/* 285:364 */         if ((this.lastPlayerLocationOnMap != null) && (this.lastPlayerLocationOnMap.length == var2.length)) {
/* 286:366 */           for (int var10 = 0; var10 < var2.length; var10++) {
/* 287:368 */             if (var2[var10] != this.lastPlayerLocationOnMap[var10])
/* 288:    */             {
/* 289:370 */               var9 = false;
/* 290:371 */               break;
/* 291:    */             }
/* 292:    */           }
/* 293:    */         } else {
/* 294:377 */           var9 = false;
/* 295:    */         }
/* 296:380 */         if (!var9)
/* 297:    */         {
/* 298:382 */           this.lastPlayerLocationOnMap = var2;
/* 299:383 */           return var2;
/* 300:    */         }
/* 301:    */       }
/* 302:387 */       for (int var8 = 0; var8 < 1; var8++)
/* 303:    */       {
/* 304:389 */         int var3 = this.currentRandomNumber++ * 11 % 128;
/* 305:391 */         if (this.field_76209_b[var3] >= 0)
/* 306:    */         {
/* 307:393 */           int var11 = this.field_76210_c[var3] - this.field_76209_b[var3] + 1;
/* 308:394 */           int var10 = this.field_76209_b[var3];
/* 309:395 */           byte[] var6 = new byte[var11 + 3];
/* 310:396 */           var6[0] = 0;
/* 311:397 */           var6[1] = ((byte)var3);
/* 312:398 */           var6[2] = ((byte)var10);
/* 313:400 */           for (int var7 = 0; var7 < var6.length - 3; var7++) {
/* 314:402 */             var6[(var7 + 3)] = MapData.this.colors[((var7 + var10) * 128 + var3)];
/* 315:    */           }
/* 316:405 */           this.field_76210_c[var3] = -1;
/* 317:406 */           this.field_76209_b[var3] = -1;
/* 318:407 */           return var6;
/* 319:    */         }
/* 320:    */       }
/* 321:411 */       return null;
/* 322:    */     }
/* 323:    */   }
/* 324:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.MapData
 * JD-Core Version:    0.7.0.1
 */
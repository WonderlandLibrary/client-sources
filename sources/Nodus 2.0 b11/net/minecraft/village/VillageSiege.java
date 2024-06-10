/*   1:    */ package net.minecraft.village;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.entity.EnumCreatureType;
/*   7:    */ import net.minecraft.entity.monster.EntityZombie;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.util.ChunkCoordinates;
/*  10:    */ import net.minecraft.util.MathHelper;
/*  11:    */ import net.minecraft.util.Vec3;
/*  12:    */ import net.minecraft.util.Vec3Pool;
/*  13:    */ import net.minecraft.world.SpawnerAnimals;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class VillageSiege
/*  17:    */ {
/*  18:    */   private World worldObj;
/*  19:    */   private boolean field_75535_b;
/*  20: 19 */   private int field_75536_c = -1;
/*  21:    */   private int field_75533_d;
/*  22:    */   private int field_75534_e;
/*  23:    */   private Village theVillage;
/*  24:    */   private int field_75532_g;
/*  25:    */   private int field_75538_h;
/*  26:    */   private int field_75539_i;
/*  27:    */   private static final String __OBFID = "CL_00001634";
/*  28:    */   
/*  29:    */   public VillageSiege(World par1World)
/*  30:    */   {
/*  31: 32 */     this.worldObj = par1World;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void tick()
/*  35:    */   {
/*  36: 40 */     boolean var1 = false;
/*  37: 42 */     if (var1)
/*  38:    */     {
/*  39: 44 */       if (this.field_75536_c == 2) {
/*  40: 46 */         this.field_75533_d = 100;
/*  41:    */       }
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45: 52 */       if (this.worldObj.isDaytime())
/*  46:    */       {
/*  47: 54 */         this.field_75536_c = 0;
/*  48: 55 */         return;
/*  49:    */       }
/*  50: 58 */       if (this.field_75536_c == 2) {
/*  51: 60 */         return;
/*  52:    */       }
/*  53: 63 */       if (this.field_75536_c == 0)
/*  54:    */       {
/*  55: 65 */         float var2 = this.worldObj.getCelestialAngle(0.0F);
/*  56: 67 */         if ((var2 < 0.5D) || (var2 > 0.501D)) {
/*  57: 69 */           return;
/*  58:    */         }
/*  59: 72 */         this.field_75536_c = (this.worldObj.rand.nextInt(10) == 0 ? 1 : 2);
/*  60: 73 */         this.field_75535_b = false;
/*  61: 75 */         if (this.field_75536_c == 2) {
/*  62: 77 */           return;
/*  63:    */         }
/*  64:    */       }
/*  65:    */     }
/*  66: 82 */     if (!this.field_75535_b)
/*  67:    */     {
/*  68: 84 */       if (!func_75529_b()) {
/*  69: 86 */         return;
/*  70:    */       }
/*  71: 89 */       this.field_75535_b = true;
/*  72:    */     }
/*  73: 92 */     if (this.field_75534_e > 0)
/*  74:    */     {
/*  75: 94 */       this.field_75534_e -= 1;
/*  76:    */     }
/*  77:    */     else
/*  78:    */     {
/*  79: 98 */       this.field_75534_e = 2;
/*  80:100 */       if (this.field_75533_d > 0)
/*  81:    */       {
/*  82:102 */         spawnZombie();
/*  83:103 */         this.field_75533_d -= 1;
/*  84:    */       }
/*  85:    */       else
/*  86:    */       {
/*  87:107 */         this.field_75536_c = 2;
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   private boolean func_75529_b()
/*  93:    */   {
/*  94:114 */     List var1 = this.worldObj.playerEntities;
/*  95:115 */     Iterator var2 = var1.iterator();
/*  96:117 */     while (var2.hasNext())
/*  97:    */     {
/*  98:119 */       EntityPlayer var3 = (EntityPlayer)var2.next();
/*  99:120 */       this.theVillage = this.worldObj.villageCollectionObj.findNearestVillage((int)var3.posX, (int)var3.posY, (int)var3.posZ, 1);
/* 100:122 */       if ((this.theVillage != null) && (this.theVillage.getNumVillageDoors() >= 10) && (this.theVillage.getTicksSinceLastDoorAdding() >= 20) && (this.theVillage.getNumVillagers() >= 20))
/* 101:    */       {
/* 102:124 */         ChunkCoordinates var4 = this.theVillage.getCenter();
/* 103:125 */         float var5 = this.theVillage.getVillageRadius();
/* 104:126 */         boolean var6 = false;
/* 105:127 */         int var7 = 0;
/* 106:131 */         while (var7 < 10)
/* 107:    */         {
/* 108:133 */           this.field_75532_g = (var4.posX + (int)(MathHelper.cos(this.worldObj.rand.nextFloat() * 3.141593F * 2.0F) * var5 * 0.9D));
/* 109:134 */           this.field_75538_h = var4.posY;
/* 110:135 */           this.field_75539_i = (var4.posZ + (int)(MathHelper.sin(this.worldObj.rand.nextFloat() * 3.141593F * 2.0F) * var5 * 0.9D));
/* 111:136 */           var6 = false;
/* 112:137 */           Iterator var8 = this.worldObj.villageCollectionObj.getVillageList().iterator();
/* 113:139 */           while (var8.hasNext())
/* 114:    */           {
/* 115:141 */             Village var9 = (Village)var8.next();
/* 116:143 */             if ((var9 != this.theVillage) && (var9.isInRange(this.field_75532_g, this.field_75538_h, this.field_75539_i)))
/* 117:    */             {
/* 118:145 */               var6 = true;
/* 119:146 */               break;
/* 120:    */             }
/* 121:    */           }
/* 122:150 */           if (!var6) {
/* 123:    */             break;
/* 124:    */           }
/* 125:152 */           var7++;
/* 126:    */         }
/* 127:157 */         if (var6) {
/* 128:159 */           return false;
/* 129:    */         }
/* 130:162 */         Vec3 var10 = func_75527_a(this.field_75532_g, this.field_75538_h, this.field_75539_i);
/* 131:164 */         if (var10 != null)
/* 132:    */         {
/* 133:166 */           this.field_75534_e = 0;
/* 134:167 */           this.field_75533_d = 20;
/* 135:168 */           return true;
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:176 */     return false;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private boolean spawnZombie()
/* 143:    */   {
/* 144:181 */     Vec3 var1 = func_75527_a(this.field_75532_g, this.field_75538_h, this.field_75539_i);
/* 145:183 */     if (var1 == null) {
/* 146:185 */       return false;
/* 147:    */     }
/* 148:    */     try
/* 149:    */     {
/* 150:193 */       EntityZombie var2 = new EntityZombie(this.worldObj);
/* 151:194 */       var2.onSpawnWithEgg(null);
/* 152:195 */       var2.setVillager(false);
/* 153:    */     }
/* 154:    */     catch (Exception var4)
/* 155:    */     {
/* 156:199 */       var4.printStackTrace();
/* 157:200 */       return false;
/* 158:    */     }
/* 159:    */     EntityZombie var2;
/* 160:203 */     var2.setLocationAndAngles(var1.xCoord, var1.yCoord, var1.zCoord, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
/* 161:204 */     this.worldObj.spawnEntityInWorld(var2);
/* 162:205 */     ChunkCoordinates var3 = this.theVillage.getCenter();
/* 163:206 */     var2.setHomeArea(var3.posX, var3.posY, var3.posZ, this.theVillage.getVillageRadius());
/* 164:207 */     return true;
/* 165:    */   }
/* 166:    */   
/* 167:    */   private Vec3 func_75527_a(int par1, int par2, int par3)
/* 168:    */   {
/* 169:213 */     for (int var4 = 0; var4 < 10; var4++)
/* 170:    */     {
/* 171:215 */       int var5 = par1 + this.worldObj.rand.nextInt(16) - 8;
/* 172:216 */       int var6 = par2 + this.worldObj.rand.nextInt(6) - 3;
/* 173:217 */       int var7 = par3 + this.worldObj.rand.nextInt(16) - 8;
/* 174:219 */       if ((this.theVillage.isInRange(var5, var6, var7)) && (SpawnerAnimals.canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, this.worldObj, var5, var6, var7))) {
/* 175:221 */         this.worldObj.getWorldVec3Pool().getVecFromPool(var5, var6, var7);
/* 176:    */       }
/* 177:    */     }
/* 178:225 */     return null;
/* 179:    */   }
/* 180:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.village.VillageSiege
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.entity.DataWatcher;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.EntityLiving;
/*   9:    */ import net.minecraft.entity.EntityLivingBase;
/*  10:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  11:    */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*  12:    */ import net.minecraft.entity.ai.EntityAIDefendVillage;
/*  13:    */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*  14:    */ import net.minecraft.entity.ai.EntityAILookAtVillager;
/*  15:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*  16:    */ import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
/*  17:    */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*  18:    */ import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
/*  19:    */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*  20:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  21:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  22:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  23:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  24:    */ import net.minecraft.entity.player.EntityPlayer;
/*  25:    */ import net.minecraft.init.Blocks;
/*  26:    */ import net.minecraft.init.Items;
/*  27:    */ import net.minecraft.item.Item;
/*  28:    */ import net.minecraft.nbt.NBTTagCompound;
/*  29:    */ import net.minecraft.pathfinding.PathNavigate;
/*  30:    */ import net.minecraft.util.AxisAlignedBB;
/*  31:    */ import net.minecraft.util.ChunkCoordinates;
/*  32:    */ import net.minecraft.util.DamageSource;
/*  33:    */ import net.minecraft.util.MathHelper;
/*  34:    */ import net.minecraft.village.Village;
/*  35:    */ import net.minecraft.village.VillageCollection;
/*  36:    */ import net.minecraft.world.World;
/*  37:    */ 
/*  38:    */ public class EntityIronGolem
/*  39:    */   extends EntityGolem
/*  40:    */ {
/*  41:    */   private int homeCheckTimer;
/*  42:    */   Village villageObj;
/*  43:    */   private int attackTimer;
/*  44:    */   private int holdRoseTick;
/*  45:    */   private static final String __OBFID = "CL_00001652";
/*  46:    */   
/*  47:    */   public EntityIronGolem(World par1World)
/*  48:    */   {
/*  49: 42 */     super(par1World);
/*  50: 43 */     setSize(1.4F, 2.9F);
/*  51: 44 */     getNavigator().setAvoidsWater(true);
/*  52: 45 */     this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
/*  53: 46 */     this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
/*  54: 47 */     this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
/*  55: 48 */     this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  56: 49 */     this.tasks.addTask(5, new EntityAILookAtVillager(this));
/*  57: 50 */     this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
/*  58: 51 */     this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  59: 52 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  60: 53 */     this.targetTasks.addTask(1, new EntityAIDefendVillage(this));
/*  61: 54 */     this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
/*  62: 55 */     this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, true, IMob.mobSelector));
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void entityInit()
/*  66:    */   {
/*  67: 60 */     super.entityInit();
/*  68: 61 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isAIEnabled()
/*  72:    */   {
/*  73: 69 */     return true;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected void updateAITick()
/*  77:    */   {
/*  78: 77 */     if (--this.homeCheckTimer <= 0)
/*  79:    */     {
/*  80: 79 */       this.homeCheckTimer = (70 + this.rand.nextInt(50));
/*  81: 80 */       this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);
/*  82: 82 */       if (this.villageObj == null)
/*  83:    */       {
/*  84: 84 */         detachHome();
/*  85:    */       }
/*  86:    */       else
/*  87:    */       {
/*  88: 88 */         ChunkCoordinates var1 = this.villageObj.getCenter();
/*  89: 89 */         setHomeArea(var1.posX, var1.posY, var1.posZ, (int)(this.villageObj.getVillageRadius() * 0.6F));
/*  90:    */       }
/*  91:    */     }
/*  92: 93 */     super.updateAITick();
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void applyEntityAttributes()
/*  96:    */   {
/*  97: 98 */     super.applyEntityAttributes();
/*  98: 99 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
/*  99:100 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected int decreaseAirSupply(int par1)
/* 103:    */   {
/* 104:108 */     return par1;
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected void collideWithEntity(Entity par1Entity)
/* 108:    */   {
/* 109:113 */     if (((par1Entity instanceof IMob)) && (getRNG().nextInt(20) == 0)) {
/* 110:115 */       setAttackTarget((EntityLivingBase)par1Entity);
/* 111:    */     }
/* 112:118 */     super.collideWithEntity(par1Entity);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void onLivingUpdate()
/* 116:    */   {
/* 117:127 */     super.onLivingUpdate();
/* 118:129 */     if (this.attackTimer > 0) {
/* 119:131 */       this.attackTimer -= 1;
/* 120:    */     }
/* 121:134 */     if (this.holdRoseTick > 0) {
/* 122:136 */       this.holdRoseTick -= 1;
/* 123:    */     }
/* 124:139 */     if ((this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-007D) && (this.rand.nextInt(5) == 0))
/* 125:    */     {
/* 126:141 */       int var1 = MathHelper.floor_double(this.posX);
/* 127:142 */       int var2 = MathHelper.floor_double(this.posY - 0.2000000029802322D - this.yOffset);
/* 128:143 */       int var3 = MathHelper.floor_double(this.posZ);
/* 129:144 */       Block var4 = this.worldObj.getBlock(var1, var2, var3);
/* 130:146 */       if (var4.getMaterial() != Material.air) {
/* 131:148 */         this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(var4) + "_" + this.worldObj.getBlockMetadata(var1, var2, var3), this.posX + (this.rand.nextFloat() - 0.5D) * this.width, this.boundingBox.minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D);
/* 132:    */       }
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean canAttackClass(Class par1Class)
/* 137:    */   {
/* 138:158 */     return (isPlayerCreated()) && (EntityPlayer.class.isAssignableFrom(par1Class)) ? false : super.canAttackClass(par1Class);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 142:    */   {
/* 143:166 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 144:167 */     par1NBTTagCompound.setBoolean("PlayerCreated", isPlayerCreated());
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 148:    */   {
/* 149:175 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 150:176 */     setPlayerCreated(par1NBTTagCompound.getBoolean("PlayerCreated"));
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean attackEntityAsMob(Entity par1Entity)
/* 154:    */   {
/* 155:181 */     this.attackTimer = 10;
/* 156:182 */     this.worldObj.setEntityState(this, (byte)4);
/* 157:183 */     boolean var2 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 7 + this.rand.nextInt(15));
/* 158:185 */     if (var2) {
/* 159:187 */       par1Entity.motionY += 0.4000000059604645D;
/* 160:    */     }
/* 161:190 */     playSound("mob.irongolem.throw", 1.0F, 1.0F);
/* 162:191 */     return var2;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void handleHealthUpdate(byte par1)
/* 166:    */   {
/* 167:196 */     if (par1 == 4)
/* 168:    */     {
/* 169:198 */       this.attackTimer = 10;
/* 170:199 */       playSound("mob.irongolem.throw", 1.0F, 1.0F);
/* 171:    */     }
/* 172:201 */     else if (par1 == 11)
/* 173:    */     {
/* 174:203 */       this.holdRoseTick = 400;
/* 175:    */     }
/* 176:    */     else
/* 177:    */     {
/* 178:207 */       super.handleHealthUpdate(par1);
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Village getVillage()
/* 183:    */   {
/* 184:213 */     return this.villageObj;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getAttackTimer()
/* 188:    */   {
/* 189:218 */     return this.attackTimer;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setHoldingRose(boolean par1)
/* 193:    */   {
/* 194:223 */     this.holdRoseTick = (par1 ? 400 : 0);
/* 195:224 */     this.worldObj.setEntityState(this, (byte)11);
/* 196:    */   }
/* 197:    */   
/* 198:    */   protected String getHurtSound()
/* 199:    */   {
/* 200:232 */     return "mob.irongolem.hit";
/* 201:    */   }
/* 202:    */   
/* 203:    */   protected String getDeathSound()
/* 204:    */   {
/* 205:240 */     return "mob.irongolem.death";
/* 206:    */   }
/* 207:    */   
/* 208:    */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/* 209:    */   {
/* 210:245 */     playSound("mob.irongolem.walk", 1.0F, 1.0F);
/* 211:    */   }
/* 212:    */   
/* 213:    */   protected void dropFewItems(boolean par1, int par2)
/* 214:    */   {
/* 215:253 */     int var3 = this.rand.nextInt(3);
/* 216:256 */     for (int var4 = 0; var4 < var3; var4++) {
/* 217:258 */       func_145778_a(Item.getItemFromBlock(Blocks.red_flower), 1, 0.0F);
/* 218:    */     }
/* 219:261 */     var4 = 3 + this.rand.nextInt(3);
/* 220:263 */     for (int var5 = 0; var5 < var4; var5++) {
/* 221:265 */       func_145779_a(Items.iron_ingot, 1);
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public int getHoldRoseTick()
/* 226:    */   {
/* 227:271 */     return this.holdRoseTick;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public boolean isPlayerCreated()
/* 231:    */   {
/* 232:276 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setPlayerCreated(boolean par1)
/* 236:    */   {
/* 237:281 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 238:283 */     if (par1) {
/* 239:285 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x1)));
/* 240:    */     } else {
/* 241:289 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFE)));
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void onDeath(DamageSource par1DamageSource)
/* 246:    */   {
/* 247:298 */     if ((!isPlayerCreated()) && (this.attackingPlayer != null) && (this.villageObj != null)) {
/* 248:300 */       this.villageObj.setReputationForPlayer(this.attackingPlayer.getCommandSenderName(), -5);
/* 249:    */     }
/* 250:303 */     super.onDeath(par1DamageSource);
/* 251:    */   }
/* 252:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityIronGolem
 * JD-Core Version:    0.7.0.1
 */
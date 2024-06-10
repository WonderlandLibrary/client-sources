/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.DataWatcher;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   7:    */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*   8:    */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*   9:    */ import net.minecraft.entity.ai.EntityAICreeperSwell;
/*  10:    */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*  11:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*  12:    */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*  13:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  14:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  15:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  16:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  17:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  18:    */ import net.minecraft.entity.effect.EntityLightningBolt;
/*  19:    */ import net.minecraft.entity.passive.EntityOcelot;
/*  20:    */ import net.minecraft.entity.player.EntityPlayer;
/*  21:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  22:    */ import net.minecraft.init.Items;
/*  23:    */ import net.minecraft.item.Item;
/*  24:    */ import net.minecraft.item.ItemStack;
/*  25:    */ import net.minecraft.nbt.NBTTagCompound;
/*  26:    */ import net.minecraft.util.DamageSource;
/*  27:    */ import net.minecraft.world.GameRules;
/*  28:    */ import net.minecraft.world.World;
/*  29:    */ 
/*  30:    */ public class EntityCreeper
/*  31:    */   extends EntityMob
/*  32:    */ {
/*  33:    */   private int lastActiveTime;
/*  34:    */   private int timeSinceIgnited;
/*  35: 36 */   private int fuseTime = 30;
/*  36: 39 */   private int explosionRadius = 3;
/*  37:    */   private static final String __OBFID = "CL_00001684";
/*  38:    */   
/*  39:    */   public EntityCreeper(World par1World)
/*  40:    */   {
/*  41: 44 */     super(par1World);
/*  42: 45 */     this.tasks.addTask(1, new EntityAISwimming(this));
/*  43: 46 */     this.tasks.addTask(2, new EntityAICreeperSwell(this));
/*  44: 47 */     this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
/*  45: 48 */     this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
/*  46: 49 */     this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
/*  47: 50 */     this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  48: 51 */     this.tasks.addTask(6, new EntityAILookIdle(this));
/*  49: 52 */     this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
/*  50: 53 */     this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected void applyEntityAttributes()
/*  54:    */   {
/*  55: 58 */     super.applyEntityAttributes();
/*  56: 59 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isAIEnabled()
/*  60:    */   {
/*  61: 67 */     return true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getMaxSafePointTries()
/*  65:    */   {
/*  66: 75 */     return getAttackTarget() == null ? 3 : 3 + (int)(getHealth() - 1.0F);
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected void fall(float par1)
/*  70:    */   {
/*  71: 83 */     super.fall(par1);
/*  72: 84 */     this.timeSinceIgnited = ((int)(this.timeSinceIgnited + par1 * 1.5F));
/*  73: 86 */     if (this.timeSinceIgnited > this.fuseTime - 5) {
/*  74: 88 */       this.timeSinceIgnited = (this.fuseTime - 5);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void entityInit()
/*  79:    */   {
/*  80: 94 */     super.entityInit();
/*  81: 95 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)-1));
/*  82: 96 */     this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
/*  83: 97 */     this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  87:    */   {
/*  88:105 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  89:107 */     if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
/*  90:109 */       par1NBTTagCompound.setBoolean("powered", true);
/*  91:    */     }
/*  92:112 */     par1NBTTagCompound.setShort("Fuse", (short)this.fuseTime);
/*  93:113 */     par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
/*  94:114 */     par1NBTTagCompound.setBoolean("ignited", func_146078_ca());
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  98:    */   {
/*  99:122 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 100:123 */     this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));
/* 101:125 */     if (par1NBTTagCompound.func_150297_b("Fuse", 99)) {
/* 102:127 */       this.fuseTime = par1NBTTagCompound.getShort("Fuse");
/* 103:    */     }
/* 104:130 */     if (par1NBTTagCompound.func_150297_b("ExplosionRadius", 99)) {
/* 105:132 */       this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
/* 106:    */     }
/* 107:135 */     if (par1NBTTagCompound.getBoolean("ignited")) {
/* 108:137 */       func_146079_cb();
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void onUpdate()
/* 113:    */   {
/* 114:146 */     if (isEntityAlive())
/* 115:    */     {
/* 116:148 */       this.lastActiveTime = this.timeSinceIgnited;
/* 117:150 */       if (func_146078_ca()) {
/* 118:152 */         setCreeperState(1);
/* 119:    */       }
/* 120:155 */       int var1 = getCreeperState();
/* 121:157 */       if ((var1 > 0) && (this.timeSinceIgnited == 0)) {
/* 122:159 */         playSound("creeper.primed", 1.0F, 0.5F);
/* 123:    */       }
/* 124:162 */       this.timeSinceIgnited += var1;
/* 125:164 */       if (this.timeSinceIgnited < 0) {
/* 126:166 */         this.timeSinceIgnited = 0;
/* 127:    */       }
/* 128:169 */       if (this.timeSinceIgnited >= this.fuseTime)
/* 129:    */       {
/* 130:171 */         this.timeSinceIgnited = this.fuseTime;
/* 131:172 */         func_146077_cc();
/* 132:    */       }
/* 133:    */     }
/* 134:176 */     super.onUpdate();
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected String getHurtSound()
/* 138:    */   {
/* 139:184 */     return "mob.creeper.say";
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected String getDeathSound()
/* 143:    */   {
/* 144:192 */     return "mob.creeper.death";
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void onDeath(DamageSource par1DamageSource)
/* 148:    */   {
/* 149:200 */     super.onDeath(par1DamageSource);
/* 150:202 */     if ((par1DamageSource.getEntity() instanceof EntitySkeleton))
/* 151:    */     {
/* 152:204 */       int var2 = Item.getIdFromItem(Items.record_13);
/* 153:205 */       int var3 = Item.getIdFromItem(Items.record_wait);
/* 154:206 */       int var4 = var2 + this.rand.nextInt(var3 - var2 + 1);
/* 155:207 */       func_145779_a(Item.getItemById(var4), 1);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean attackEntityAsMob(Entity par1Entity)
/* 160:    */   {
/* 161:213 */     return true;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean getPowered()
/* 165:    */   {
/* 166:221 */     return this.dataWatcher.getWatchableObjectByte(17) == 1;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public float getCreeperFlashIntensity(float par1)
/* 170:    */   {
/* 171:229 */     return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / (this.fuseTime - 2);
/* 172:    */   }
/* 173:    */   
/* 174:    */   protected Item func_146068_u()
/* 175:    */   {
/* 176:234 */     return Items.gunpowder;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int getCreeperState()
/* 180:    */   {
/* 181:242 */     return this.dataWatcher.getWatchableObjectByte(16);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setCreeperState(int par1)
/* 185:    */   {
/* 186:250 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
/* 190:    */   {
/* 191:258 */     super.onStruckByLightning(par1EntityLightningBolt);
/* 192:259 */     this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected boolean interact(EntityPlayer par1EntityPlayer)
/* 196:    */   {
/* 197:267 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/* 198:269 */     if ((var2 != null) && (var2.getItem() == Items.flint_and_steel))
/* 199:    */     {
/* 200:271 */       this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
/* 201:272 */       par1EntityPlayer.swingItem();
/* 202:274 */       if (!this.worldObj.isClient)
/* 203:    */       {
/* 204:276 */         func_146079_cb();
/* 205:277 */         var2.damageItem(1, par1EntityPlayer);
/* 206:278 */         return true;
/* 207:    */       }
/* 208:    */     }
/* 209:282 */     return super.interact(par1EntityPlayer);
/* 210:    */   }
/* 211:    */   
/* 212:    */   private void func_146077_cc()
/* 213:    */   {
/* 214:287 */     if (!this.worldObj.isClient)
/* 215:    */     {
/* 216:289 */       boolean var1 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
/* 217:291 */       if (getPowered()) {
/* 218:293 */         this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * 2, var1);
/* 219:    */       } else {
/* 220:297 */         this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius, var1);
/* 221:    */       }
/* 222:300 */       setDead();
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean func_146078_ca()
/* 227:    */   {
/* 228:306 */     return this.dataWatcher.getWatchableObjectByte(18) != 0;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void func_146079_cb()
/* 232:    */   {
/* 233:311 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)1));
/* 234:    */   }
/* 235:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityCreeper
 * JD-Core Version:    0.7.0.1
 */
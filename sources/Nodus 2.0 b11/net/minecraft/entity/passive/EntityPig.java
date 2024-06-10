/*   1:    */ package net.minecraft.entity.passive;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.entity.DataWatcher;
/*   6:    */ import net.minecraft.entity.EntityAgeable;
/*   7:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   8:    */ import net.minecraft.entity.ai.EntityAIControlledByPlayer;
/*   9:    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*  10:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*  11:    */ import net.minecraft.entity.ai.EntityAIMate;
/*  12:    */ import net.minecraft.entity.ai.EntityAIPanic;
/*  13:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  14:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  15:    */ import net.minecraft.entity.ai.EntityAITempt;
/*  16:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  17:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  18:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  19:    */ import net.minecraft.entity.effect.EntityLightningBolt;
/*  20:    */ import net.minecraft.entity.monster.EntityPigZombie;
/*  21:    */ import net.minecraft.entity.player.EntityPlayer;
/*  22:    */ import net.minecraft.init.Items;
/*  23:    */ import net.minecraft.item.Item;
/*  24:    */ import net.minecraft.item.ItemStack;
/*  25:    */ import net.minecraft.nbt.NBTTagCompound;
/*  26:    */ import net.minecraft.pathfinding.PathNavigate;
/*  27:    */ import net.minecraft.stats.AchievementList;
/*  28:    */ import net.minecraft.world.World;
/*  29:    */ 
/*  30:    */ public class EntityPig
/*  31:    */   extends EntityAnimal
/*  32:    */ {
/*  33:    */   private final EntityAIControlledByPlayer aiControlledByPlayer;
/*  34:    */   private static final String __OBFID = "CL_00001647";
/*  35:    */   
/*  36:    */   public EntityPig(World par1World)
/*  37:    */   {
/*  38: 33 */     super(par1World);
/*  39: 34 */     setSize(0.9F, 0.9F);
/*  40: 35 */     getNavigator().setAvoidsWater(true);
/*  41: 36 */     this.tasks.addTask(0, new EntityAISwimming(this));
/*  42: 37 */     this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
/*  43: 38 */     this.tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3F));
/*  44: 39 */     this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
/*  45: 40 */     this.tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot_on_a_stick, false));
/*  46: 41 */     this.tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot, false));
/*  47: 42 */     this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
/*  48: 43 */     this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
/*  49: 44 */     this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  50: 45 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean isAIEnabled()
/*  54:    */   {
/*  55: 53 */     return true;
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected void applyEntityAttributes()
/*  59:    */   {
/*  60: 58 */     super.applyEntityAttributes();
/*  61: 59 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  62: 60 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void updateAITasks()
/*  66:    */   {
/*  67: 65 */     super.updateAITasks();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean canBeSteered()
/*  71:    */   {
/*  72: 74 */     ItemStack var1 = ((EntityPlayer)this.riddenByEntity).getHeldItem();
/*  73: 75 */     return (var1 != null) && (var1.getItem() == Items.carrot_on_a_stick);
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected void entityInit()
/*  77:    */   {
/*  78: 80 */     super.entityInit();
/*  79: 81 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  83:    */   {
/*  84: 89 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  85: 90 */     par1NBTTagCompound.setBoolean("Saddle", getSaddled());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  89:    */   {
/*  90: 98 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  91: 99 */     setSaddled(par1NBTTagCompound.getBoolean("Saddle"));
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected String getLivingSound()
/*  95:    */   {
/*  96:107 */     return "mob.pig.say";
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected String getHurtSound()
/* 100:    */   {
/* 101:115 */     return "mob.pig.say";
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected String getDeathSound()
/* 105:    */   {
/* 106:123 */     return "mob.pig.death";
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/* 110:    */   {
/* 111:128 */     playSound("mob.pig.step", 0.15F, 1.0F);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean interact(EntityPlayer par1EntityPlayer)
/* 115:    */   {
/* 116:136 */     if (super.interact(par1EntityPlayer)) {
/* 117:138 */       return true;
/* 118:    */     }
/* 119:140 */     if ((getSaddled()) && (!this.worldObj.isClient) && ((this.riddenByEntity == null) || (this.riddenByEntity == par1EntityPlayer)))
/* 120:    */     {
/* 121:142 */       par1EntityPlayer.mountEntity(this);
/* 122:143 */       return true;
/* 123:    */     }
/* 124:147 */     return false;
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected Item func_146068_u()
/* 128:    */   {
/* 129:153 */     return isBurning() ? Items.cooked_porkchop : Items.porkchop;
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected void dropFewItems(boolean par1, int par2)
/* 133:    */   {
/* 134:161 */     int var3 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);
/* 135:163 */     for (int var4 = 0; var4 < var3; var4++) {
/* 136:165 */       if (isBurning()) {
/* 137:167 */         func_145779_a(Items.cooked_porkchop, 1);
/* 138:    */       } else {
/* 139:171 */         func_145779_a(Items.porkchop, 1);
/* 140:    */       }
/* 141:    */     }
/* 142:175 */     if (getSaddled()) {
/* 143:177 */       func_145779_a(Items.saddle, 1);
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean getSaddled()
/* 148:    */   {
/* 149:186 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setSaddled(boolean par1)
/* 153:    */   {
/* 154:194 */     if (par1) {
/* 155:196 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
/* 156:    */     } else {
/* 157:200 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
/* 162:    */   {
/* 163:209 */     if (!this.worldObj.isClient)
/* 164:    */     {
/* 165:211 */       EntityPigZombie var2 = new EntityPigZombie(this.worldObj);
/* 166:212 */       var2.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
/* 167:213 */       var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 168:214 */       this.worldObj.spawnEntityInWorld(var2);
/* 169:215 */       setDead();
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   protected void fall(float par1)
/* 174:    */   {
/* 175:224 */     super.fall(par1);
/* 176:226 */     if ((par1 > 5.0F) && ((this.riddenByEntity instanceof EntityPlayer))) {
/* 177:228 */       ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public EntityPig createChild(EntityAgeable par1EntityAgeable)
/* 182:    */   {
/* 183:234 */     return new EntityPig(this.worldObj);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean isBreedingItem(ItemStack par1ItemStack)
/* 187:    */   {
/* 188:243 */     return (par1ItemStack != null) && (par1ItemStack.getItem() == Items.carrot);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public EntityAIControlledByPlayer getAIControlledByPlayer()
/* 192:    */   {
/* 193:251 */     return this.aiControlledByPlayer;
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityPig
 * JD-Core Version:    0.7.0.1
 */
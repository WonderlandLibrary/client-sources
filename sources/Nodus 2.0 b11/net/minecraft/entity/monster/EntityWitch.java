/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import java.util.UUID;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.entity.DataWatcher;
/*   9:    */ import net.minecraft.entity.EntityLivingBase;
/*  10:    */ import net.minecraft.entity.IRangedAttackMob;
/*  11:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  12:    */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*  13:    */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*  14:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*  15:    */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*  16:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  17:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  18:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  19:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  20:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  21:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  22:    */ import net.minecraft.entity.player.EntityPlayer;
/*  23:    */ import net.minecraft.entity.projectile.EntityPotion;
/*  24:    */ import net.minecraft.init.Items;
/*  25:    */ import net.minecraft.item.Item;
/*  26:    */ import net.minecraft.item.ItemPotion;
/*  27:    */ import net.minecraft.item.ItemStack;
/*  28:    */ import net.minecraft.potion.Potion;
/*  29:    */ import net.minecraft.potion.PotionEffect;
/*  30:    */ import net.minecraft.util.AxisAlignedBB;
/*  31:    */ import net.minecraft.util.DamageSource;
/*  32:    */ import net.minecraft.util.MathHelper;
/*  33:    */ import net.minecraft.world.World;
/*  34:    */ 
/*  35:    */ public class EntityWitch
/*  36:    */   extends EntityMob
/*  37:    */   implements IRangedAttackMob
/*  38:    */ {
/*  39: 32 */   private static final UUID field_110184_bp = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
/*  40: 33 */   private static final AttributeModifier field_110185_bq = new AttributeModifier(field_110184_bp, "Drinking speed penalty", -0.25D, 0).setSaved(false);
/*  41: 36 */   private static final Item[] witchDrops = { Items.glowstone_dust, Items.sugar, Items.redstone, Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick };
/*  42:    */   private int witchAttackTimer;
/*  43:    */   private static final String __OBFID = "CL_00001701";
/*  44:    */   
/*  45:    */   public EntityWitch(World par1World)
/*  46:    */   {
/*  47: 47 */     super(par1World);
/*  48: 48 */     this.tasks.addTask(1, new EntityAISwimming(this));
/*  49: 49 */     this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 60, 10.0F));
/*  50: 50 */     this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
/*  51: 51 */     this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  52: 52 */     this.tasks.addTask(3, new EntityAILookIdle(this));
/*  53: 53 */     this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
/*  54: 54 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected void entityInit()
/*  58:    */   {
/*  59: 59 */     super.entityInit();
/*  60: 60 */     getDataWatcher().addObject(21, Byte.valueOf((byte)0));
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected String getLivingSound()
/*  64:    */   {
/*  65: 68 */     return "mob.witch.idle";
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected String getHurtSound()
/*  69:    */   {
/*  70: 76 */     return "mob.witch.hurt";
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected String getDeathSound()
/*  74:    */   {
/*  75: 84 */     return "mob.witch.death";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setAggressive(boolean par1)
/*  79:    */   {
/*  80: 92 */     getDataWatcher().updateObject(21, Byte.valueOf((byte)(par1 ? 1 : 0)));
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean getAggressive()
/*  84:    */   {
/*  85:100 */     return getDataWatcher().getWatchableObjectByte(21) == 1;
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void applyEntityAttributes()
/*  89:    */   {
/*  90:105 */     super.applyEntityAttributes();
/*  91:106 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
/*  92:107 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean isAIEnabled()
/*  96:    */   {
/*  97:115 */     return true;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void onLivingUpdate()
/* 101:    */   {
/* 102:124 */     if (!this.worldObj.isClient)
/* 103:    */     {
/* 104:126 */       if (getAggressive())
/* 105:    */       {
/* 106:128 */         if (this.witchAttackTimer-- <= 0)
/* 107:    */         {
/* 108:130 */           setAggressive(false);
/* 109:131 */           ItemStack var1 = getHeldItem();
/* 110:132 */           setCurrentItemOrArmor(0, null);
/* 111:134 */           if ((var1 != null) && (var1.getItem() == Items.potionitem))
/* 112:    */           {
/* 113:136 */             List var2 = Items.potionitem.getEffects(var1);
/* 114:138 */             if (var2 != null)
/* 115:    */             {
/* 116:140 */               Iterator var3 = var2.iterator();
/* 117:142 */               while (var3.hasNext())
/* 118:    */               {
/* 119:144 */                 PotionEffect var4 = (PotionEffect)var3.next();
/* 120:145 */                 addPotionEffect(new PotionEffect(var4));
/* 121:    */               }
/* 122:    */             }
/* 123:    */           }
/* 124:150 */           getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(field_110185_bq);
/* 125:    */         }
/* 126:    */       }
/* 127:    */       else
/* 128:    */       {
/* 129:155 */         short var5 = -1;
/* 130:157 */         if ((this.rand.nextFloat() < 0.15F) && (isInsideOfMaterial(Material.water)) && (!isPotionActive(Potion.waterBreathing))) {
/* 131:159 */           var5 = 8237;
/* 132:161 */         } else if ((this.rand.nextFloat() < 0.15F) && (isBurning()) && (!isPotionActive(Potion.fireResistance))) {
/* 133:163 */           var5 = 16307;
/* 134:165 */         } else if ((this.rand.nextFloat() < 0.05F) && (getHealth() < getMaxHealth())) {
/* 135:167 */           var5 = 16341;
/* 136:169 */         } else if ((this.rand.nextFloat() < 0.25F) && (getAttackTarget() != null) && (!isPotionActive(Potion.moveSpeed)) && (getAttackTarget().getDistanceSqToEntity(this) > 121.0D)) {
/* 137:171 */           var5 = 16274;
/* 138:173 */         } else if ((this.rand.nextFloat() < 0.25F) && (getAttackTarget() != null) && (!isPotionActive(Potion.moveSpeed)) && (getAttackTarget().getDistanceSqToEntity(this) > 121.0D)) {
/* 139:175 */           var5 = 16274;
/* 140:    */         }
/* 141:178 */         if (var5 > -1)
/* 142:    */         {
/* 143:180 */           setCurrentItemOrArmor(0, new ItemStack(Items.potionitem, 1, var5));
/* 144:181 */           this.witchAttackTimer = getHeldItem().getMaxItemUseDuration();
/* 145:182 */           setAggressive(true);
/* 146:183 */           IAttributeInstance var6 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 147:184 */           var6.removeModifier(field_110185_bq);
/* 148:185 */           var6.applyModifier(field_110185_bq);
/* 149:    */         }
/* 150:    */       }
/* 151:189 */       if (this.rand.nextFloat() < 0.00075F) {
/* 152:191 */         this.worldObj.setEntityState(this, (byte)15);
/* 153:    */       }
/* 154:    */     }
/* 155:195 */     super.onLivingUpdate();
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void handleHealthUpdate(byte par1)
/* 159:    */   {
/* 160:200 */     if (par1 == 15) {
/* 161:202 */       for (int var2 = 0; var2 < this.rand.nextInt(35) + 10; var2++) {
/* 162:204 */         this.worldObj.spawnParticle("witchMagic", this.posX + this.rand.nextGaussian() * 0.1299999952316284D, this.boundingBox.maxY + 0.5D + this.rand.nextGaussian() * 0.1299999952316284D, this.posZ + this.rand.nextGaussian() * 0.1299999952316284D, 0.0D, 0.0D, 0.0D);
/* 163:    */       }
/* 164:    */     } else {
/* 165:209 */       super.handleHealthUpdate(par1);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected float applyPotionDamageCalculations(DamageSource par1DamageSource, float par2)
/* 170:    */   {
/* 171:218 */     par2 = super.applyPotionDamageCalculations(par1DamageSource, par2);
/* 172:220 */     if (par1DamageSource.getEntity() == this) {
/* 173:222 */       par2 = 0.0F;
/* 174:    */     }
/* 175:225 */     if (par1DamageSource.isMagicDamage()) {
/* 176:227 */       par2 = (float)(par2 * 0.15D);
/* 177:    */     }
/* 178:230 */     return par2;
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected void dropFewItems(boolean par1, int par2)
/* 182:    */   {
/* 183:238 */     int var3 = this.rand.nextInt(3) + 1;
/* 184:240 */     for (int var4 = 0; var4 < var3; var4++)
/* 185:    */     {
/* 186:242 */       int var5 = this.rand.nextInt(3);
/* 187:243 */       Item var6 = witchDrops[this.rand.nextInt(witchDrops.length)];
/* 188:245 */       if (par2 > 0) {
/* 189:247 */         var5 += this.rand.nextInt(par2 + 1);
/* 190:    */       }
/* 191:250 */       for (int var7 = 0; var7 < var5; var7++) {
/* 192:252 */         func_145779_a(var6, 1);
/* 193:    */       }
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
/* 198:    */   {
/* 199:262 */     if (!getAggressive())
/* 200:    */     {
/* 201:264 */       EntityPotion var3 = new EntityPotion(this.worldObj, this, 32732);
/* 202:265 */       var3.rotationPitch -= -20.0F;
/* 203:266 */       double var4 = par1EntityLivingBase.posX + par1EntityLivingBase.motionX - this.posX;
/* 204:267 */       double var6 = par1EntityLivingBase.posY + par1EntityLivingBase.getEyeHeight() - 1.100000023841858D - this.posY;
/* 205:268 */       double var8 = par1EntityLivingBase.posZ + par1EntityLivingBase.motionZ - this.posZ;
/* 206:269 */       float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
/* 207:271 */       if ((var10 >= 8.0F) && (!par1EntityLivingBase.isPotionActive(Potion.moveSlowdown))) {
/* 208:273 */         var3.setPotionDamage(32698);
/* 209:275 */       } else if ((par1EntityLivingBase.getHealth() >= 8.0F) && (!par1EntityLivingBase.isPotionActive(Potion.poison))) {
/* 210:277 */         var3.setPotionDamage(32660);
/* 211:279 */       } else if ((var10 <= 3.0F) && (!par1EntityLivingBase.isPotionActive(Potion.weakness)) && (this.rand.nextFloat() < 0.25F)) {
/* 212:281 */         var3.setPotionDamage(32696);
/* 213:    */       }
/* 214:284 */       var3.setThrowableHeading(var4, var6 + var10 * 0.2F, var8, 0.75F, 8.0F);
/* 215:285 */       this.worldObj.spawnEntityInWorld(var3);
/* 216:    */     }
/* 217:    */   }
/* 218:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityWitch
 * JD-Core Version:    0.7.0.1
 */
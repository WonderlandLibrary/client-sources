/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.BlockSilverfish;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.EnumCreatureAttribute;
/*   8:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   9:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.init.Blocks;
/*  12:    */ import net.minecraft.item.Item;
/*  13:    */ import net.minecraft.util.AxisAlignedBB;
/*  14:    */ import net.minecraft.util.DamageSource;
/*  15:    */ import net.minecraft.util.EntityDamageSource;
/*  16:    */ import net.minecraft.util.MathHelper;
/*  17:    */ import net.minecraft.world.GameRules;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*  20:    */ 
/*  21:    */ public class EntitySilverfish
/*  22:    */   extends EntityMob
/*  23:    */ {
/*  24:    */   private int allySummonCooldown;
/*  25:    */   private static final String __OBFID = "CL_00001696";
/*  26:    */   
/*  27:    */   public EntitySilverfish(World par1World)
/*  28:    */   {
/*  29: 28 */     super(par1World);
/*  30: 29 */     setSize(0.3F, 0.7F);
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected void applyEntityAttributes()
/*  34:    */   {
/*  35: 34 */     super.applyEntityAttributes();
/*  36: 35 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*  37: 36 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579D);
/*  38: 37 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected boolean canTriggerWalking()
/*  42:    */   {
/*  43: 46 */     return false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected Entity findPlayerToAttack()
/*  47:    */   {
/*  48: 55 */     double var1 = 8.0D;
/*  49: 56 */     return this.worldObj.getClosestVulnerablePlayerToEntity(this, var1);
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected String getLivingSound()
/*  53:    */   {
/*  54: 64 */     return "mob.silverfish.say";
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected String getHurtSound()
/*  58:    */   {
/*  59: 72 */     return "mob.silverfish.hit";
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected String getDeathSound()
/*  63:    */   {
/*  64: 80 */     return "mob.silverfish.kill";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  68:    */   {
/*  69: 88 */     if (isEntityInvulnerable()) {
/*  70: 90 */       return false;
/*  71:    */     }
/*  72: 94 */     if ((this.allySummonCooldown <= 0) && (((par1DamageSource instanceof EntityDamageSource)) || (par1DamageSource == DamageSource.magic))) {
/*  73: 96 */       this.allySummonCooldown = 20;
/*  74:    */     }
/*  75: 99 */     return super.attackEntityFrom(par1DamageSource, par2);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void attackEntity(Entity par1Entity, float par2)
/*  79:    */   {
/*  80:108 */     if ((this.attackTime <= 0) && (par2 < 1.2F) && (par1Entity.boundingBox.maxY > this.boundingBox.minY) && (par1Entity.boundingBox.minY < this.boundingBox.maxY))
/*  81:    */     {
/*  82:110 */       this.attackTime = 20;
/*  83:111 */       attackEntityAsMob(par1Entity);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/*  88:    */   {
/*  89:117 */     playSound("mob.silverfish.step", 0.15F, 1.0F);
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected Item func_146068_u()
/*  93:    */   {
/*  94:122 */     return Item.getItemById(0);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void onUpdate()
/*  98:    */   {
/*  99:130 */     this.renderYawOffset = this.rotationYaw;
/* 100:131 */     super.onUpdate();
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected void updateEntityActionState()
/* 104:    */   {
/* 105:136 */     super.updateEntityActionState();
/* 106:138 */     if (!this.worldObj.isClient)
/* 107:    */     {
/* 108:145 */       if (this.allySummonCooldown > 0)
/* 109:    */       {
/* 110:147 */         this.allySummonCooldown -= 1;
/* 111:149 */         if (this.allySummonCooldown == 0)
/* 112:    */         {
/* 113:151 */           int var1 = MathHelper.floor_double(this.posX);
/* 114:152 */           int var2 = MathHelper.floor_double(this.posY);
/* 115:153 */           int var3 = MathHelper.floor_double(this.posZ);
/* 116:154 */           boolean var4 = false;
/* 117:156 */           for (int var5 = 0; (!var4) && (var5 <= 5) && (var5 >= -5); var5 = var5 <= 0 ? 1 - var5 : 0 - var5) {
/* 118:158 */             for (int var6 = 0; (!var4) && (var6 <= 10) && (var6 >= -10); var6 = var6 <= 0 ? 1 - var6 : 0 - var6) {
/* 119:160 */               for (int var7 = 0; (!var4) && (var7 <= 10) && (var7 >= -10); var7 = var7 <= 0 ? 1 - var7 : 0 - var7) {
/* 120:162 */                 if (this.worldObj.getBlock(var1 + var6, var2 + var5, var3 + var7) == Blocks.monster_egg)
/* 121:    */                 {
/* 122:164 */                   if (!this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
/* 123:    */                   {
/* 124:166 */                     int var8 = this.worldObj.getBlockMetadata(var1 + var6, var2 + var5, var3 + var7);
/* 125:167 */                     ImmutablePair var9 = BlockSilverfish.func_150197_b(var8);
/* 126:168 */                     this.worldObj.setBlock(var1 + var6, var2 + var5, var3 + var7, (Block)var9.getLeft(), ((Integer)var9.getRight()).intValue(), 3);
/* 127:    */                   }
/* 128:    */                   else
/* 129:    */                   {
/* 130:172 */                     this.worldObj.func_147480_a(var1 + var6, var2 + var5, var3 + var7, false);
/* 131:    */                   }
/* 132:175 */                   Blocks.monster_egg.onBlockDestroyedByPlayer(this.worldObj, var1 + var6, var2 + var5, var3 + var7, 0);
/* 133:177 */                   if (this.rand.nextBoolean())
/* 134:    */                   {
/* 135:179 */                     var4 = true;
/* 136:180 */                     break;
/* 137:    */                   }
/* 138:    */                 }
/* 139:    */               }
/* 140:    */             }
/* 141:    */           }
/* 142:    */         }
/* 143:    */       }
/* 144:189 */       if ((this.entityToAttack == null) && (!hasPath()))
/* 145:    */       {
/* 146:191 */         int var1 = MathHelper.floor_double(this.posX);
/* 147:192 */         int var2 = MathHelper.floor_double(this.posY + 0.5D);
/* 148:193 */         int var3 = MathHelper.floor_double(this.posZ);
/* 149:194 */         int var10 = this.rand.nextInt(6);
/* 150:195 */         Block var11 = this.worldObj.getBlock(var1 + net.minecraft.util.Facing.offsetsXForSide[var10], var2 + net.minecraft.util.Facing.offsetsYForSide[var10], var3 + net.minecraft.util.Facing.offsetsZForSide[var10]);
/* 151:196 */         int var6 = this.worldObj.getBlockMetadata(var1 + net.minecraft.util.Facing.offsetsXForSide[var10], var2 + net.minecraft.util.Facing.offsetsYForSide[var10], var3 + net.minecraft.util.Facing.offsetsZForSide[var10]);
/* 152:198 */         if (BlockSilverfish.func_150196_a(var11))
/* 153:    */         {
/* 154:200 */           this.worldObj.setBlock(var1 + net.minecraft.util.Facing.offsetsXForSide[var10], var2 + net.minecraft.util.Facing.offsetsYForSide[var10], var3 + net.minecraft.util.Facing.offsetsZForSide[var10], Blocks.monster_egg, BlockSilverfish.func_150195_a(var11, var6), 3);
/* 155:201 */           spawnExplosionParticle();
/* 156:202 */           setDead();
/* 157:    */         }
/* 158:    */         else
/* 159:    */         {
/* 160:206 */           updateWanderPath();
/* 161:    */         }
/* 162:    */       }
/* 163:209 */       else if ((this.entityToAttack != null) && (!hasPath()))
/* 164:    */       {
/* 165:211 */         this.entityToAttack = null;
/* 166:    */       }
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public float getBlockPathWeight(int par1, int par2, int par3)
/* 171:    */   {
/* 172:222 */     return this.worldObj.getBlock(par1, par2 - 1, par3) == Blocks.stone ? 10.0F : super.getBlockPathWeight(par1, par2, par3);
/* 173:    */   }
/* 174:    */   
/* 175:    */   protected boolean isValidLightLevel()
/* 176:    */   {
/* 177:230 */     return true;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean getCanSpawnHere()
/* 181:    */   {
/* 182:238 */     if (super.getCanSpawnHere())
/* 183:    */     {
/* 184:240 */       EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 5.0D);
/* 185:241 */       return var1 == null;
/* 186:    */     }
/* 187:245 */     return false;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public EnumCreatureAttribute getCreatureAttribute()
/* 191:    */   {
/* 192:254 */     return EnumCreatureAttribute.ARTHROPOD;
/* 193:    */   }
/* 194:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntitySilverfish
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.entity.EntityLiving;
/*   7:    */ import net.minecraft.entity.EntityLivingBase;
/*   8:    */ import net.minecraft.entity.IRangedAttackMob;
/*   9:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  10:    */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*  11:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*  12:    */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*  13:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  14:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  15:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  16:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.entity.projectile.EntitySnowball;
/*  19:    */ import net.minecraft.init.Blocks;
/*  20:    */ import net.minecraft.init.Items;
/*  21:    */ import net.minecraft.item.Item;
/*  22:    */ import net.minecraft.pathfinding.PathNavigate;
/*  23:    */ import net.minecraft.util.DamageSource;
/*  24:    */ import net.minecraft.util.MathHelper;
/*  25:    */ import net.minecraft.world.World;
/*  26:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  27:    */ 
/*  28:    */ public class EntitySnowman
/*  29:    */   extends EntityGolem
/*  30:    */   implements IRangedAttackMob
/*  31:    */ {
/*  32:    */   private static final String __OBFID = "CL_00001650";
/*  33:    */   
/*  34:    */   public EntitySnowman(World par1World)
/*  35:    */   {
/*  36: 28 */     super(par1World);
/*  37: 29 */     setSize(0.4F, 1.8F);
/*  38: 30 */     getNavigator().setAvoidsWater(true);
/*  39: 31 */     this.tasks.addTask(1, new EntityAIArrowAttack(this, 1.25D, 20, 10.0F));
/*  40: 32 */     this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
/*  41: 33 */     this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  42: 34 */     this.tasks.addTask(4, new EntityAILookIdle(this));
/*  43: 35 */     this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isAIEnabled()
/*  47:    */   {
/*  48: 43 */     return true;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void applyEntityAttributes()
/*  52:    */   {
/*  53: 48 */     super.applyEntityAttributes();
/*  54: 49 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
/*  55: 50 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2000000029802322D);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void onLivingUpdate()
/*  59:    */   {
/*  60: 59 */     super.onLivingUpdate();
/*  61: 60 */     int var1 = MathHelper.floor_double(this.posX);
/*  62: 61 */     int var2 = MathHelper.floor_double(this.posY);
/*  63: 62 */     int var3 = MathHelper.floor_double(this.posZ);
/*  64: 64 */     if (isWet()) {
/*  65: 66 */       attackEntityFrom(DamageSource.drown, 1.0F);
/*  66:    */     }
/*  67: 69 */     if (this.worldObj.getBiomeGenForCoords(var1, var3).getFloatTemperature(var1, var2, var3) > 1.0F) {
/*  68: 71 */       attackEntityFrom(DamageSource.onFire, 1.0F);
/*  69:    */     }
/*  70: 74 */     for (int var4 = 0; var4 < 4; var4++)
/*  71:    */     {
/*  72: 76 */       var1 = MathHelper.floor_double(this.posX + (var4 % 2 * 2 - 1) * 0.25F);
/*  73: 77 */       var2 = MathHelper.floor_double(this.posY);
/*  74: 78 */       var3 = MathHelper.floor_double(this.posZ + (var4 / 2 % 2 * 2 - 1) * 0.25F);
/*  75: 80 */       if ((this.worldObj.getBlock(var1, var2, var3).getMaterial() == Material.air) && (this.worldObj.getBiomeGenForCoords(var1, var3).getFloatTemperature(var1, var2, var3) < 0.8F) && (Blocks.snow_layer.canPlaceBlockAt(this.worldObj, var1, var2, var3))) {
/*  76: 82 */         this.worldObj.setBlock(var1, var2, var3, Blocks.snow_layer);
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected Item func_146068_u()
/*  82:    */   {
/*  83: 89 */     return Items.snowball;
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected void dropFewItems(boolean par1, int par2)
/*  87:    */   {
/*  88: 97 */     int var3 = this.rand.nextInt(16);
/*  89: 99 */     for (int var4 = 0; var4 < var3; var4++) {
/*  90:101 */       func_145779_a(Items.snowball, 1);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
/*  95:    */   {
/*  96:110 */     EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
/*  97:111 */     double var4 = par1EntityLivingBase.posX - this.posX;
/*  98:112 */     double var6 = par1EntityLivingBase.posY + par1EntityLivingBase.getEyeHeight() - 1.100000023841858D - var3.posY;
/*  99:113 */     double var8 = par1EntityLivingBase.posZ - this.posZ;
/* 100:114 */     float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.2F;
/* 101:115 */     var3.setThrowableHeading(var4, var6 + var10, var8, 1.6F, 12.0F);
/* 102:116 */     playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
/* 103:117 */     this.worldObj.spawnEntityInWorld(var3);
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntitySnowman
 * JD-Core Version:    0.7.0.1
 */
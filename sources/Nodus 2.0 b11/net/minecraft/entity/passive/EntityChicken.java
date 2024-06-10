/*   1:    */ package net.minecraft.entity.passive;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.entity.EntityAgeable;
/*   6:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   7:    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*   8:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*   9:    */ import net.minecraft.entity.ai.EntityAIMate;
/*  10:    */ import net.minecraft.entity.ai.EntityAIPanic;
/*  11:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  12:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  13:    */ import net.minecraft.entity.ai.EntityAITempt;
/*  14:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  15:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  16:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.init.Items;
/*  19:    */ import net.minecraft.item.Item;
/*  20:    */ import net.minecraft.item.ItemSeeds;
/*  21:    */ import net.minecraft.item.ItemStack;
/*  22:    */ import net.minecraft.world.World;
/*  23:    */ 
/*  24:    */ public class EntityChicken
/*  25:    */   extends EntityAnimal
/*  26:    */ {
/*  27:    */   public float field_70886_e;
/*  28:    */   public float destPos;
/*  29:    */   public float field_70884_g;
/*  30:    */   public float field_70888_h;
/*  31: 27 */   public float field_70889_i = 1.0F;
/*  32:    */   public int timeUntilNextEgg;
/*  33:    */   private static final String __OBFID = "CL_00001639";
/*  34:    */   
/*  35:    */   public EntityChicken(World par1World)
/*  36:    */   {
/*  37: 35 */     super(par1World);
/*  38: 36 */     setSize(0.3F, 0.7F);
/*  39: 37 */     this.timeUntilNextEgg = (this.rand.nextInt(6000) + 6000);
/*  40: 38 */     this.tasks.addTask(0, new EntityAISwimming(this));
/*  41: 39 */     this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
/*  42: 40 */     this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
/*  43: 41 */     this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.wheat_seeds, false));
/*  44: 42 */     this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
/*  45: 43 */     this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
/*  46: 44 */     this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  47: 45 */     this.tasks.addTask(7, new EntityAILookIdle(this));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isAIEnabled()
/*  51:    */   {
/*  52: 53 */     return true;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected void applyEntityAttributes()
/*  56:    */   {
/*  57: 58 */     super.applyEntityAttributes();
/*  58: 59 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
/*  59: 60 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void onLivingUpdate()
/*  63:    */   {
/*  64: 69 */     super.onLivingUpdate();
/*  65: 70 */     this.field_70888_h = this.field_70886_e;
/*  66: 71 */     this.field_70884_g = this.destPos;
/*  67: 72 */     this.destPos = ((float)(this.destPos + (this.onGround ? -1 : 4) * 0.3D));
/*  68: 74 */     if (this.destPos < 0.0F) {
/*  69: 76 */       this.destPos = 0.0F;
/*  70:    */     }
/*  71: 79 */     if (this.destPos > 1.0F) {
/*  72: 81 */       this.destPos = 1.0F;
/*  73:    */     }
/*  74: 84 */     if ((!this.onGround) && (this.field_70889_i < 1.0F)) {
/*  75: 86 */       this.field_70889_i = 1.0F;
/*  76:    */     }
/*  77: 89 */     this.field_70889_i = ((float)(this.field_70889_i * 0.9D));
/*  78: 91 */     if ((!this.onGround) && (this.motionY < 0.0D)) {
/*  79: 93 */       this.motionY *= 0.6D;
/*  80:    */     }
/*  81: 96 */     this.field_70886_e += this.field_70889_i * 2.0F;
/*  82: 98 */     if ((!isChild()) && (!this.worldObj.isClient) && (--this.timeUntilNextEgg <= 0))
/*  83:    */     {
/*  84:100 */       playSound("mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*  85:101 */       func_145779_a(Items.egg, 1);
/*  86:102 */       this.timeUntilNextEgg = (this.rand.nextInt(6000) + 6000);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected void fall(float par1) {}
/*  91:    */   
/*  92:    */   protected String getLivingSound()
/*  93:    */   {
/*  94:116 */     return "mob.chicken.say";
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected String getHurtSound()
/*  98:    */   {
/*  99:124 */     return "mob.chicken.hurt";
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected String getDeathSound()
/* 103:    */   {
/* 104:132 */     return "mob.chicken.hurt";
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/* 108:    */   {
/* 109:137 */     playSound("mob.chicken.step", 0.15F, 1.0F);
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected Item func_146068_u()
/* 113:    */   {
/* 114:142 */     return Items.feather;
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected void dropFewItems(boolean par1, int par2)
/* 118:    */   {
/* 119:150 */     int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
/* 120:152 */     for (int var4 = 0; var4 < var3; var4++) {
/* 121:154 */       func_145779_a(Items.feather, 1);
/* 122:    */     }
/* 123:157 */     if (isBurning()) {
/* 124:159 */       func_145779_a(Items.cooked_chicken, 1);
/* 125:    */     } else {
/* 126:163 */       func_145779_a(Items.chicken, 1);
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public EntityChicken createChild(EntityAgeable par1EntityAgeable)
/* 131:    */   {
/* 132:169 */     return new EntityChicken(this.worldObj);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean isBreedingItem(ItemStack par1ItemStack)
/* 136:    */   {
/* 137:178 */     return (par1ItemStack != null) && ((par1ItemStack.getItem() instanceof ItemSeeds));
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityChicken
 * JD-Core Version:    0.7.0.1
 */
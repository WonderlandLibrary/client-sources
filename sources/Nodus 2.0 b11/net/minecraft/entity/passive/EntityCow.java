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
/*  18:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  19:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  20:    */ import net.minecraft.init.Items;
/*  21:    */ import net.minecraft.item.Item;
/*  22:    */ import net.minecraft.item.ItemStack;
/*  23:    */ import net.minecraft.pathfinding.PathNavigate;
/*  24:    */ import net.minecraft.world.World;
/*  25:    */ 
/*  26:    */ public class EntityCow
/*  27:    */   extends EntityAnimal
/*  28:    */ {
/*  29:    */   private static final String __OBFID = "CL_00001640";
/*  30:    */   
/*  31:    */   public EntityCow(World par1World)
/*  32:    */   {
/*  33: 26 */     super(par1World);
/*  34: 27 */     setSize(0.9F, 1.3F);
/*  35: 28 */     getNavigator().setAvoidsWater(true);
/*  36: 29 */     this.tasks.addTask(0, new EntityAISwimming(this));
/*  37: 30 */     this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
/*  38: 31 */     this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
/*  39: 32 */     this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.wheat, false));
/*  40: 33 */     this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
/*  41: 34 */     this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
/*  42: 35 */     this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  43: 36 */     this.tasks.addTask(7, new EntityAILookIdle(this));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isAIEnabled()
/*  47:    */   {
/*  48: 44 */     return true;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void applyEntityAttributes()
/*  52:    */   {
/*  53: 49 */     super.applyEntityAttributes();
/*  54: 50 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  55: 51 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2000000029802322D);
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected String getLivingSound()
/*  59:    */   {
/*  60: 59 */     return "mob.cow.say";
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected String getHurtSound()
/*  64:    */   {
/*  65: 67 */     return "mob.cow.hurt";
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected String getDeathSound()
/*  69:    */   {
/*  70: 75 */     return "mob.cow.hurt";
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/*  74:    */   {
/*  75: 80 */     playSound("mob.cow.step", 0.15F, 1.0F);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected float getSoundVolume()
/*  79:    */   {
/*  80: 88 */     return 0.4F;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected Item func_146068_u()
/*  84:    */   {
/*  85: 93 */     return Items.leather;
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void dropFewItems(boolean par1, int par2)
/*  89:    */   {
/*  90:101 */     int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
/*  91:104 */     for (int var4 = 0; var4 < var3; var4++) {
/*  92:106 */       func_145779_a(Items.leather, 1);
/*  93:    */     }
/*  94:109 */     var3 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);
/*  95:111 */     for (var4 = 0; var4 < var3; var4++) {
/*  96:113 */       if (isBurning()) {
/*  97:115 */         func_145779_a(Items.cooked_beef, 1);
/*  98:    */       } else {
/*  99:119 */         func_145779_a(Items.beef, 1);
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean interact(EntityPlayer par1EntityPlayer)
/* 105:    */   {
/* 106:129 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/* 107:131 */     if ((var2 != null) && (var2.getItem() == Items.bucket) && (!par1EntityPlayer.capabilities.isCreativeMode))
/* 108:    */     {
/* 109:133 */       if (var2.stackSize-- == 1) {
/* 110:135 */         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
/* 111:137 */       } else if (!par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
/* 112:139 */         par1EntityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
/* 113:    */       }
/* 114:142 */       return true;
/* 115:    */     }
/* 116:146 */     return super.interact(par1EntityPlayer);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public EntityCow createChild(EntityAgeable par1EntityAgeable)
/* 120:    */   {
/* 121:152 */     return new EntityCow(this.worldObj);
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityCow
 * JD-Core Version:    0.7.0.1
 */
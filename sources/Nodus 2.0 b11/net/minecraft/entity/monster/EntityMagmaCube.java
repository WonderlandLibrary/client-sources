/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   6:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   7:    */ import net.minecraft.init.Items;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.world.EnumDifficulty;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class EntityMagmaCube
/*  13:    */   extends EntitySlime
/*  14:    */ {
/*  15:    */   private static final String __OBFID = "CL_00001691";
/*  16:    */   
/*  17:    */   public EntityMagmaCube(World par1World)
/*  18:    */   {
/*  19: 15 */     super(par1World);
/*  20: 16 */     this.isImmuneToFire = true;
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected void applyEntityAttributes()
/*  24:    */   {
/*  25: 21 */     super.applyEntityAttributes();
/*  26: 22 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2000000029802322D);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean getCanSpawnHere()
/*  30:    */   {
/*  31: 30 */     return (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (this.worldObj.checkNoEntityCollision(this.boundingBox)) && (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) && (!this.worldObj.isAnyLiquid(this.boundingBox));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getTotalArmorValue()
/*  35:    */   {
/*  36: 38 */     return getSlimeSize() * 3;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getBrightnessForRender(float par1)
/*  40:    */   {
/*  41: 43 */     return 15728880;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public float getBrightness(float par1)
/*  45:    */   {
/*  46: 51 */     return 1.0F;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected String getSlimeParticle()
/*  50:    */   {
/*  51: 59 */     return "flame";
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected EntitySlime createInstance()
/*  55:    */   {
/*  56: 64 */     return new EntityMagmaCube(this.worldObj);
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Item func_146068_u()
/*  60:    */   {
/*  61: 69 */     return Items.magma_cream;
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void dropFewItems(boolean par1, int par2)
/*  65:    */   {
/*  66: 77 */     Item var3 = func_146068_u();
/*  67: 79 */     if ((var3 != null) && (getSlimeSize() > 1))
/*  68:    */     {
/*  69: 81 */       int var4 = this.rand.nextInt(4) - 2;
/*  70: 83 */       if (par2 > 0) {
/*  71: 85 */         var4 += this.rand.nextInt(par2 + 1);
/*  72:    */       }
/*  73: 88 */       for (int var5 = 0; var5 < var4; var5++) {
/*  74: 90 */         func_145779_a(var3, 1);
/*  75:    */       }
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isBurning()
/*  80:    */   {
/*  81:100 */     return false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected int getJumpDelay()
/*  85:    */   {
/*  86:108 */     return super.getJumpDelay() * 4;
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected void alterSquishAmount()
/*  90:    */   {
/*  91:113 */     this.squishAmount *= 0.9F;
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected void jump()
/*  95:    */   {
/*  96:121 */     this.motionY = (0.42F + getSlimeSize() * 0.1F);
/*  97:122 */     this.isAirBorne = true;
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected void fall(float par1) {}
/* 101:    */   
/* 102:    */   protected boolean canDamagePlayer()
/* 103:    */   {
/* 104:135 */     return true;
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected int getAttackStrength()
/* 108:    */   {
/* 109:143 */     return super.getAttackStrength() + 2;
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected String getJumpSound()
/* 113:    */   {
/* 114:151 */     return getSlimeSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean handleLavaMovement()
/* 118:    */   {
/* 119:159 */     return false;
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected boolean makesSoundOnLand()
/* 123:    */   {
/* 124:167 */     return true;
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityMagmaCube
 * JD-Core Version:    0.7.0.1
 */
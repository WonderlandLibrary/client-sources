/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.BlockRailBase;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.util.DamageSource;
/*  10:    */ import net.minecraft.world.Explosion;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class EntityMinecartTNT
/*  14:    */   extends EntityMinecart
/*  15:    */ {
/*  16: 14 */   private int minecartTNTFuse = -1;
/*  17:    */   private static final String __OBFID = "CL_00001680";
/*  18:    */   
/*  19:    */   public EntityMinecartTNT(World par1World)
/*  20:    */   {
/*  21: 19 */     super(par1World);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public EntityMinecartTNT(World par1World, double par2, double par4, double par6)
/*  25:    */   {
/*  26: 24 */     super(par1World, par2, par4, par6);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getMinecartType()
/*  30:    */   {
/*  31: 29 */     return 3;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Block func_145817_o()
/*  35:    */   {
/*  36: 34 */     return Blocks.tnt;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void onUpdate()
/*  40:    */   {
/*  41: 42 */     super.onUpdate();
/*  42: 44 */     if (this.minecartTNTFuse > 0)
/*  43:    */     {
/*  44: 46 */       this.minecartTNTFuse -= 1;
/*  45: 47 */       this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
/*  46:    */     }
/*  47: 49 */     else if (this.minecartTNTFuse == 0)
/*  48:    */     {
/*  49: 51 */       explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  50:    */     }
/*  51: 54 */     if (this.isCollidedHorizontally)
/*  52:    */     {
/*  53: 56 */       double var1 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*  54: 58 */       if (var1 >= 0.009999999776482582D) {
/*  55: 60 */         explodeCart(var1);
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void killMinecart(DamageSource par1DamageSource)
/*  61:    */   {
/*  62: 67 */     super.killMinecart(par1DamageSource);
/*  63: 68 */     double var2 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*  64: 70 */     if (!par1DamageSource.isExplosion()) {
/*  65: 72 */       entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0F);
/*  66:    */     }
/*  67: 75 */     if ((par1DamageSource.isFireDamage()) || (par1DamageSource.isExplosion()) || (var2 >= 0.009999999776482582D)) {
/*  68: 77 */       explodeCart(var2);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected void explodeCart(double par1)
/*  73:    */   {
/*  74: 86 */     if (!this.worldObj.isClient)
/*  75:    */     {
/*  76: 88 */       double var3 = Math.sqrt(par1);
/*  77: 90 */       if (var3 > 5.0D) {
/*  78: 92 */         var3 = 5.0D;
/*  79:    */       }
/*  80: 95 */       this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0D + this.rand.nextDouble() * 1.5D * var3), true);
/*  81: 96 */       setDead();
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void fall(float par1)
/*  86:    */   {
/*  87:105 */     if (par1 >= 3.0F)
/*  88:    */     {
/*  89:107 */       float var2 = par1 / 10.0F;
/*  90:108 */       explodeCart(var2 * var2);
/*  91:    */     }
/*  92:111 */     super.fall(par1);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void onActivatorRailPass(int par1, int par2, int par3, boolean par4)
/*  96:    */   {
/*  97:119 */     if ((par4) && (this.minecartTNTFuse < 0)) {
/*  98:121 */       ignite();
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void handleHealthUpdate(byte par1)
/* 103:    */   {
/* 104:127 */     if (par1 == 10) {
/* 105:129 */       ignite();
/* 106:    */     } else {
/* 107:133 */       super.handleHealthUpdate(par1);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void ignite()
/* 112:    */   {
/* 113:142 */     this.minecartTNTFuse = 80;
/* 114:144 */     if (!this.worldObj.isClient)
/* 115:    */     {
/* 116:146 */       this.worldObj.setEntityState(this, (byte)10);
/* 117:147 */       this.worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0F, 1.0F);
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int func_94104_d()
/* 122:    */   {
/* 123:153 */     return this.minecartTNTFuse;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isIgnited()
/* 127:    */   {
/* 128:161 */     return this.minecartTNTFuse > -1;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public float func_145772_a(Explosion p_145772_1_, World p_145772_2_, int p_145772_3_, int p_145772_4_, int p_145772_5_, Block p_145772_6_)
/* 132:    */   {
/* 133:166 */     return (isIgnited()) && ((BlockRailBase.func_150051_a(p_145772_6_)) || (BlockRailBase.func_150049_b_(p_145772_2_, p_145772_3_, p_145772_4_ + 1, p_145772_5_))) ? 0.0F : super.func_145772_a(p_145772_1_, p_145772_2_, p_145772_3_, p_145772_4_, p_145772_5_, p_145772_6_);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean func_145774_a(Explosion p_145774_1_, World p_145774_2_, int p_145774_3_, int p_145774_4_, int p_145774_5_, Block p_145774_6_, float p_145774_7_)
/* 137:    */   {
/* 138:171 */     return (isIgnited()) && ((BlockRailBase.func_150051_a(p_145774_6_)) || (BlockRailBase.func_150049_b_(p_145774_2_, p_145774_3_, p_145774_4_ + 1, p_145774_5_))) ? false : super.func_145774_a(p_145774_1_, p_145774_2_, p_145774_3_, p_145774_4_, p_145774_5_, p_145774_6_, p_145774_7_);
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 142:    */   {
/* 143:179 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 144:181 */     if (par1NBTTagCompound.func_150297_b("TNTFuse", 99)) {
/* 145:183 */       this.minecartTNTFuse = par1NBTTagCompound.getInteger("TNTFuse");
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 150:    */   {
/* 151:192 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 152:193 */     par1NBTTagCompound.setInteger("TNTFuse", this.minecartTNTFuse);
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityMinecartTNT
 * JD-Core Version:    0.7.0.1
 */
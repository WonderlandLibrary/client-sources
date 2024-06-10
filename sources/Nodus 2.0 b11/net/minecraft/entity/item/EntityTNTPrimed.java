/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ import net.minecraft.world.World;
/*   7:    */ 
/*   8:    */ public class EntityTNTPrimed
/*   9:    */   extends Entity
/*  10:    */ {
/*  11:    */   public int fuse;
/*  12:    */   private EntityLivingBase tntPlacedBy;
/*  13:    */   private static final String __OBFID = "CL_00001681";
/*  14:    */   
/*  15:    */   public EntityTNTPrimed(World par1World)
/*  16:    */   {
/*  17: 17 */     super(par1World);
/*  18: 18 */     this.preventEntitySpawning = true;
/*  19: 19 */     setSize(0.98F, 0.98F);
/*  20: 20 */     this.yOffset = (this.height / 2.0F);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public EntityTNTPrimed(World par1World, double par2, double par4, double par6, EntityLivingBase par8EntityLivingBase)
/*  24:    */   {
/*  25: 25 */     this(par1World);
/*  26: 26 */     setPosition(par2, par4, par6);
/*  27: 27 */     float var9 = (float)(Math.random() * 3.141592653589793D * 2.0D);
/*  28: 28 */     this.motionX = (-(float)Math.sin(var9) * 0.02F);
/*  29: 29 */     this.motionY = 0.2000000029802322D;
/*  30: 30 */     this.motionZ = (-(float)Math.cos(var9) * 0.02F);
/*  31: 31 */     this.fuse = 80;
/*  32: 32 */     this.prevPosX = par2;
/*  33: 33 */     this.prevPosY = par4;
/*  34: 34 */     this.prevPosZ = par6;
/*  35: 35 */     this.tntPlacedBy = par8EntityLivingBase;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected void entityInit() {}
/*  39:    */   
/*  40:    */   protected boolean canTriggerWalking()
/*  41:    */   {
/*  42: 46 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean canBeCollidedWith()
/*  46:    */   {
/*  47: 54 */     return !this.isDead;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void onUpdate()
/*  51:    */   {
/*  52: 62 */     this.prevPosX = this.posX;
/*  53: 63 */     this.prevPosY = this.posY;
/*  54: 64 */     this.prevPosZ = this.posZ;
/*  55: 65 */     this.motionY -= 0.03999999910593033D;
/*  56: 66 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  57: 67 */     this.motionX *= 0.9800000190734863D;
/*  58: 68 */     this.motionY *= 0.9800000190734863D;
/*  59: 69 */     this.motionZ *= 0.9800000190734863D;
/*  60: 71 */     if (this.onGround)
/*  61:    */     {
/*  62: 73 */       this.motionX *= 0.699999988079071D;
/*  63: 74 */       this.motionZ *= 0.699999988079071D;
/*  64: 75 */       this.motionY *= -0.5D;
/*  65:    */     }
/*  66: 78 */     if (this.fuse-- <= 0)
/*  67:    */     {
/*  68: 80 */       setDead();
/*  69: 82 */       if (!this.worldObj.isClient) {
/*  70: 84 */         explode();
/*  71:    */       }
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75: 89 */       this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   private void explode()
/*  80:    */   {
/*  81: 95 */     float var1 = 4.0F;
/*  82: 96 */     this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, var1, true);
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  86:    */   {
/*  87:104 */     par1NBTTagCompound.setByte("Fuse", (byte)this.fuse);
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  91:    */   {
/*  92:112 */     this.fuse = par1NBTTagCompound.getByte("Fuse");
/*  93:    */   }
/*  94:    */   
/*  95:    */   public float getShadowSize()
/*  96:    */   {
/*  97:117 */     return 0.0F;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public EntityLivingBase getTntPlacedBy()
/* 101:    */   {
/* 102:125 */     return this.tntPlacedBy;
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityTNTPrimed
 * JD-Core Version:    0.7.0.1
 */
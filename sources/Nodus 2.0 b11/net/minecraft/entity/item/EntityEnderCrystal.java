/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.DataWatcher;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ import net.minecraft.util.DamageSource;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ import net.minecraft.world.WorldProviderEnd;
/*  12:    */ 
/*  13:    */ public class EntityEnderCrystal
/*  14:    */   extends Entity
/*  15:    */ {
/*  16:    */   public int innerRotation;
/*  17:    */   public int health;
/*  18:    */   private static final String __OBFID = "CL_00001658";
/*  19:    */   
/*  20:    */   public EntityEnderCrystal(World par1World)
/*  21:    */   {
/*  22: 20 */     super(par1World);
/*  23: 21 */     this.preventEntitySpawning = true;
/*  24: 22 */     setSize(2.0F, 2.0F);
/*  25: 23 */     this.yOffset = (this.height / 2.0F);
/*  26: 24 */     this.health = 5;
/*  27: 25 */     this.innerRotation = this.rand.nextInt(100000);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public EntityEnderCrystal(World par1World, double par2, double par4, double par6)
/*  31:    */   {
/*  32: 30 */     this(par1World);
/*  33: 31 */     setPosition(par2, par4, par6);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected boolean canTriggerWalking()
/*  37:    */   {
/*  38: 40 */     return false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected void entityInit()
/*  42:    */   {
/*  43: 45 */     this.dataWatcher.addObject(8, Integer.valueOf(this.health));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void onUpdate()
/*  47:    */   {
/*  48: 53 */     this.prevPosX = this.posX;
/*  49: 54 */     this.prevPosY = this.posY;
/*  50: 55 */     this.prevPosZ = this.posZ;
/*  51: 56 */     this.innerRotation += 1;
/*  52: 57 */     this.dataWatcher.updateObject(8, Integer.valueOf(this.health));
/*  53: 58 */     int var1 = MathHelper.floor_double(this.posX);
/*  54: 59 */     int var2 = MathHelper.floor_double(this.posY);
/*  55: 60 */     int var3 = MathHelper.floor_double(this.posZ);
/*  56: 62 */     if (((this.worldObj.provider instanceof WorldProviderEnd)) && (this.worldObj.getBlock(var1, var2, var3) != Blocks.fire)) {
/*  57: 64 */       this.worldObj.setBlock(var1, var2, var3, Blocks.fire);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}
/*  62:    */   
/*  63:    */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}
/*  64:    */   
/*  65:    */   public float getShadowSize()
/*  66:    */   {
/*  67: 80 */     return 0.0F;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean canBeCollidedWith()
/*  71:    */   {
/*  72: 88 */     return true;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  76:    */   {
/*  77: 96 */     if (isEntityInvulnerable()) {
/*  78: 98 */       return false;
/*  79:    */     }
/*  80:102 */     if ((!this.isDead) && (!this.worldObj.isClient))
/*  81:    */     {
/*  82:104 */       this.health = 0;
/*  83:106 */       if (this.health <= 0)
/*  84:    */       {
/*  85:108 */         setDead();
/*  86:110 */         if (!this.worldObj.isClient) {
/*  87:112 */           this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 6.0F, true);
/*  88:    */         }
/*  89:    */       }
/*  90:    */     }
/*  91:117 */     return true;
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityEnderCrystal
 * JD-Core Version:    0.7.0.1
 */
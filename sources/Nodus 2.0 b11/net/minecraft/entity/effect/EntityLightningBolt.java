/*   1:    */ package net.minecraft.entity.effect;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.BlockFire;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.util.AABBPool;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ import net.minecraft.world.EnumDifficulty;
/*  15:    */ import net.minecraft.world.GameRules;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public class EntityLightningBolt
/*  19:    */   extends EntityWeatherEffect
/*  20:    */ {
/*  21:    */   private int lightningState;
/*  22:    */   public long boltVertex;
/*  23:    */   private int boltLivingTime;
/*  24:    */   private static final String __OBFID = "CL_00001666";
/*  25:    */   
/*  26:    */   public EntityLightningBolt(World par1World, double par2, double par4, double par6)
/*  27:    */   {
/*  28: 33 */     super(par1World);
/*  29: 34 */     setLocationAndAngles(par2, par4, par6, 0.0F, 0.0F);
/*  30: 35 */     this.lightningState = 2;
/*  31: 36 */     this.boltVertex = this.rand.nextLong();
/*  32: 37 */     this.boltLivingTime = (this.rand.nextInt(3) + 1);
/*  33: 39 */     if ((!par1World.isClient) && (par1World.getGameRules().getGameRuleBooleanValue("doFireTick")) && ((par1World.difficultySetting == EnumDifficulty.NORMAL) || (par1World.difficultySetting == EnumDifficulty.HARD)) && (par1World.doChunksNearChunkExist(MathHelper.floor_double(par2), MathHelper.floor_double(par4), MathHelper.floor_double(par6), 10)))
/*  34:    */     {
/*  35: 41 */       int var8 = MathHelper.floor_double(par2);
/*  36: 42 */       int var9 = MathHelper.floor_double(par4);
/*  37: 43 */       int var10 = MathHelper.floor_double(par6);
/*  38: 45 */       if ((par1World.getBlock(var8, var9, var10).getMaterial() == Material.air) && (Blocks.fire.canPlaceBlockAt(par1World, var8, var9, var10))) {
/*  39: 47 */         par1World.setBlock(var8, var9, var10, Blocks.fire);
/*  40:    */       }
/*  41: 50 */       for (var8 = 0; var8 < 4; var8++)
/*  42:    */       {
/*  43: 52 */         var9 = MathHelper.floor_double(par2) + this.rand.nextInt(3) - 1;
/*  44: 53 */         var10 = MathHelper.floor_double(par4) + this.rand.nextInt(3) - 1;
/*  45: 54 */         int var11 = MathHelper.floor_double(par6) + this.rand.nextInt(3) - 1;
/*  46: 56 */         if ((par1World.getBlock(var9, var10, var11).getMaterial() == Material.air) && (Blocks.fire.canPlaceBlockAt(par1World, var9, var10, var11))) {
/*  47: 58 */           par1World.setBlock(var9, var10, var11, Blocks.fire);
/*  48:    */         }
/*  49:    */       }
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void onUpdate()
/*  54:    */   {
/*  55: 69 */     super.onUpdate();
/*  56: 71 */     if (this.lightningState == 2)
/*  57:    */     {
/*  58: 73 */       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
/*  59: 74 */       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
/*  60:    */     }
/*  61: 77 */     this.lightningState -= 1;
/*  62: 79 */     if (this.lightningState < 0) {
/*  63: 81 */       if (this.boltLivingTime == 0)
/*  64:    */       {
/*  65: 83 */         setDead();
/*  66:    */       }
/*  67: 85 */       else if (this.lightningState < -this.rand.nextInt(10))
/*  68:    */       {
/*  69: 87 */         this.boltLivingTime -= 1;
/*  70: 88 */         this.lightningState = 1;
/*  71: 89 */         this.boltVertex = this.rand.nextLong();
/*  72: 91 */         if ((!this.worldObj.isClient) && (this.worldObj.getGameRules().getGameRuleBooleanValue("doFireTick")) && (this.worldObj.doChunksNearChunkExist(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 10)))
/*  73:    */         {
/*  74: 93 */           int var1 = MathHelper.floor_double(this.posX);
/*  75: 94 */           int var2 = MathHelper.floor_double(this.posY);
/*  76: 95 */           int var3 = MathHelper.floor_double(this.posZ);
/*  77: 97 */           if ((this.worldObj.getBlock(var1, var2, var3).getMaterial() == Material.air) && (Blocks.fire.canPlaceBlockAt(this.worldObj, var1, var2, var3))) {
/*  78: 99 */             this.worldObj.setBlock(var1, var2, var3, Blocks.fire);
/*  79:    */           }
/*  80:    */         }
/*  81:    */       }
/*  82:    */     }
/*  83:105 */     if (this.lightningState >= 0) {
/*  84:107 */       if (this.worldObj.isClient)
/*  85:    */       {
/*  86:109 */         this.worldObj.lastLightningBolt = 2;
/*  87:    */       }
/*  88:    */       else
/*  89:    */       {
/*  90:113 */         double var6 = 3.0D;
/*  91:114 */         List var7 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getAABBPool().getAABB(this.posX - var6, this.posY - var6, this.posZ - var6, this.posX + var6, this.posY + 6.0D + var6, this.posZ + var6));
/*  92:116 */         for (int var4 = 0; var4 < var7.size(); var4++)
/*  93:    */         {
/*  94:118 */           Entity var5 = (Entity)var7.get(var4);
/*  95:119 */           var5.onStruckByLightning(this);
/*  96:    */         }
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected void entityInit() {}
/* 102:    */   
/* 103:    */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}
/* 104:    */   
/* 105:    */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}
/* 106:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.effect.EntityLightningBolt
 * JD-Core Version:    0.7.0.1
 */
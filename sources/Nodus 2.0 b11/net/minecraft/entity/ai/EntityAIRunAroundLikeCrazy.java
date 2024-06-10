/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.passive.EntityHorse;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.pathfinding.PathNavigate;
/*  8:   */ import net.minecraft.util.Vec3;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class EntityAIRunAroundLikeCrazy
/* 12:   */   extends EntityAIBase
/* 13:   */ {
/* 14:   */   private EntityHorse horseHost;
/* 15:   */   private double field_111178_b;
/* 16:   */   private double field_111179_c;
/* 17:   */   private double field_111176_d;
/* 18:   */   private double field_111177_e;
/* 19:   */   private static final String __OBFID = "CL_00001612";
/* 20:   */   
/* 21:   */   public EntityAIRunAroundLikeCrazy(EntityHorse par1EntityHorse, double par2)
/* 22:   */   {
/* 23:19 */     this.horseHost = par1EntityHorse;
/* 24:20 */     this.field_111178_b = par2;
/* 25:21 */     setMutexBits(1);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean shouldExecute()
/* 29:   */   {
/* 30:29 */     if ((!this.horseHost.isTame()) && (this.horseHost.riddenByEntity != null))
/* 31:   */     {
/* 32:31 */       Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.horseHost, 5, 4);
/* 33:33 */       if (var1 == null) {
/* 34:35 */         return false;
/* 35:   */       }
/* 36:39 */       this.field_111179_c = var1.xCoord;
/* 37:40 */       this.field_111176_d = var1.yCoord;
/* 38:41 */       this.field_111177_e = var1.zCoord;
/* 39:42 */       return true;
/* 40:   */     }
/* 41:47 */     return false;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void startExecuting()
/* 45:   */   {
/* 46:56 */     this.horseHost.getNavigator().tryMoveToXYZ(this.field_111179_c, this.field_111176_d, this.field_111177_e, this.field_111178_b);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean continueExecuting()
/* 50:   */   {
/* 51:64 */     return (!this.horseHost.getNavigator().noPath()) && (this.horseHost.riddenByEntity != null);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void updateTask()
/* 55:   */   {
/* 56:72 */     if (this.horseHost.getRNG().nextInt(50) == 0)
/* 57:   */     {
/* 58:74 */       if ((this.horseHost.riddenByEntity instanceof EntityPlayer))
/* 59:   */       {
/* 60:76 */         int var1 = this.horseHost.getTemper();
/* 61:77 */         int var2 = this.horseHost.getMaxTemper();
/* 62:79 */         if ((var2 > 0) && (this.horseHost.getRNG().nextInt(var2) < var1))
/* 63:   */         {
/* 64:81 */           this.horseHost.setTamedBy((EntityPlayer)this.horseHost.riddenByEntity);
/* 65:82 */           this.horseHost.worldObj.setEntityState(this.horseHost, (byte)7);
/* 66:83 */           return;
/* 67:   */         }
/* 68:86 */         this.horseHost.increaseTemper(5);
/* 69:   */       }
/* 70:89 */       this.horseHost.riddenByEntity.mountEntity(null);
/* 71:90 */       this.horseHost.riddenByEntity = null;
/* 72:91 */       this.horseHost.makeHorseRearWithSound();
/* 73:92 */       this.horseHost.worldObj.setEntityState(this.horseHost, (byte)6);
/* 74:   */     }
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy
 * JD-Core Version:    0.7.0.1
 */
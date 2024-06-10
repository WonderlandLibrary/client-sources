/*   1:    */ package me.connorm.Nodus.module.modules;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import me.connorm.Nodus.Nodus;
/*   6:    */ import me.connorm.Nodus.event.player.EventPlayerMotionUpdate;
/*   7:    */ import me.connorm.Nodus.event.player.EventPlayerPostMotionUpdate;
/*   8:    */ import me.connorm.Nodus.module.core.Category;
/*   9:    */ import me.connorm.Nodus.module.core.NodusModule;
/*  10:    */ import me.connorm.Nodus.relations.NodusRelationsManager;
/*  11:    */ import me.connorm.Nodus.relations.friend.NodusFriendManager;
/*  12:    */ import me.connorm.lib.EventTarget;
/*  13:    */ import net.minecraft.client.Minecraft;
/*  14:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  15:    */ import net.minecraft.client.multiplayer.WorldClient;
/*  16:    */ import net.minecraft.entity.Entity;
/*  17:    */ import net.minecraft.entity.EntityLivingBase;
/*  18:    */ import net.minecraft.item.ItemBow;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.util.StringUtils;
/*  21:    */ 
/*  22:    */ public class BowAimbot
/*  23:    */   extends NodusModule
/*  24:    */ {
/*  25:    */   private float targetPitch;
/*  26:    */   private float targetYaw;
/*  27:    */   private EntityLivingBase targetEntity;
/*  28:    */   
/*  29:    */   public BowAimbot()
/*  30:    */   {
/*  31: 25 */     super("BowAimbot", Category.COMBAT);
/*  32:    */   }
/*  33:    */   
/*  34:    */   @EventTarget
/*  35:    */   public void updatePlayerMotion(EventPlayerMotionUpdate theEvent)
/*  36:    */   {
/*  37: 31 */     if (!isToggled()) {
/*  38: 32 */       return;
/*  39:    */     }
/*  40: 33 */     if (Nodus.theNodus.getMinecraft().thePlayer.getCurrentEquippedItem() != null) {
/*  41: 35 */       if ((Nodus.theNodus.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow))
/*  42:    */       {
/*  43: 37 */         this.targetEntity = getCursorEntity();
/*  44: 39 */         if (Nodus.theNodus.relationsManager.friendManager.isFriend(StringUtils.stripControlCodes(this.targetEntity.getCommandSenderName()))) {
/*  45: 40 */           return;
/*  46:    */         }
/*  47: 42 */         if (this.targetEntity == null) {
/*  48: 43 */           return;
/*  49:    */         }
/*  50: 45 */         this.targetPitch = Nodus.theNodus.getMinecraft().thePlayer.rotationPitch;
/*  51: 46 */         this.targetYaw = Nodus.theNodus.getMinecraft().thePlayer.rotationYaw;
/*  52: 47 */         silentAim(this.targetEntity);
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   @EventTarget
/*  58:    */   public void postUpdatePlayerMotion(EventPlayerPostMotionUpdate theEvent)
/*  59:    */   {
/*  60: 55 */     if ((this.targetEntity != null) && (Nodus.theNodus.getMinecraft().thePlayer.getCurrentEquippedItem() != null) && ((Nodus.theNodus.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow)))
/*  61:    */     {
/*  62: 57 */       Nodus.theNodus.getMinecraft().thePlayer.rotationPitch = this.targetPitch;
/*  63: 58 */       Nodus.theNodus.getMinecraft().thePlayer.rotationYaw = this.targetYaw;
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void silentAim(EntityLivingBase targetEntity)
/*  68:    */   {
/*  69: 64 */     int bowCurrentCharge = Nodus.theNodus.getMinecraft().thePlayer.getItemInUseDuration();
/*  70: 65 */     float bowVelocity = bowCurrentCharge / 20.0F;
/*  71: 66 */     bowVelocity = (bowVelocity * bowVelocity + bowVelocity * 2.0F) / 3.0F;
/*  72: 68 */     if (bowVelocity < 0.1D) {
/*  73: 70 */       return;
/*  74:    */     }
/*  75: 73 */     if (bowVelocity > 1.0F) {
/*  76: 75 */       bowVelocity = 1.0F;
/*  77:    */     }
/*  78: 78 */     double xDistance = targetEntity.posX - Nodus.theNodus.getMinecraft().thePlayer.posX;
/*  79: 79 */     double zDistance = targetEntity.posZ - Nodus.theNodus.getMinecraft().thePlayer.posZ;
/*  80: 80 */     double eyeDistance = targetEntity.posY + targetEntity.getEyeHeight() - (Nodus.theNodus.getMinecraft().thePlayer.posY + Nodus.theNodus.getMinecraft().thePlayer.getEyeHeight());
/*  81: 81 */     double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
/*  82: 82 */     double eyeTrajectoryXZ = Math.sqrt(trajectoryXZ * trajectoryXZ + eyeDistance * eyeDistance);
/*  83: 83 */     float trajectoryTheta90 = (float)(Math.atan2(zDistance, xDistance) * 180.0D / 3.141592653589793D) - 90.0F;
/*  84:    */     
/*  85: 85 */     float bowTrajectory = -getTrajectoryAngleSolutionLow((float)trajectoryXZ, (float)eyeDistance, bowVelocity);
/*  86:    */     
/*  87: 87 */     Nodus.theNodus.getMinecraft().thePlayer.rotationPitch = bowTrajectory;
/*  88: 88 */     Nodus.theNodus.getMinecraft().thePlayer.rotationYaw = trajectoryTheta90;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public EntityLivingBase getCursorEntity()
/*  92:    */   {
/*  93: 93 */     EntityLivingBase poorEntity = null;
/*  94: 94 */     double distanceToEntity = 1000.0D;
/*  95: 96 */     for (Iterator entityIterator = Nodus.theNodus.getMinecraft().theWorld.loadedEntityList.iterator(); entityIterator.hasNext();)
/*  96:    */     {
/*  97: 98 */       Object currentObject = entityIterator.next();
/*  98:100 */       if ((currentObject instanceof Entity))
/*  99:    */       {
/* 100:102 */         Entity targetEntity = (Entity)currentObject;
/* 101:104 */         if (((targetEntity instanceof EntityLivingBase)) && (targetEntity != Nodus.theNodus.getMinecraft().thePlayer)) {
/* 102:106 */           if ((targetEntity.getDistanceToEntity(Nodus.theNodus.getMinecraft().thePlayer) <= 140.0F) && (Nodus.theNodus.getMinecraft().thePlayer.canEntityBeSeen(targetEntity)) && (((EntityLivingBase)targetEntity).deathTime <= 0))
/* 103:    */           {
/* 104:111 */             if (poorEntity == null) {
/* 105:113 */               poorEntity = (EntityLivingBase)targetEntity;
/* 106:    */             }
/* 107:116 */             double xDistance = targetEntity.posX - Nodus.theNodus.getMinecraft().thePlayer.posX;
/* 108:117 */             double zDistance = targetEntity.posZ - Nodus.theNodus.getMinecraft().thePlayer.posZ;
/* 109:118 */             double eyeDistance = Nodus.theNodus.getMinecraft().thePlayer.posY + Nodus.theNodus.getMinecraft().thePlayer.getEyeHeight() - (targetEntity.posY + targetEntity.getEyeHeight());
/* 110:119 */             double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
/* 111:120 */             float trajectoryTheta90 = (float)(Math.atan2(zDistance, xDistance) * 180.0D / 3.141592653589793D) - 90.0F;
/* 112:121 */             float trajectoryTheta = (float)(Math.atan2(eyeDistance, trajectoryXZ) * 180.0D / 3.141592653589793D);
/* 113:    */             
/* 114:123 */             double xAngleDistance = getDistanceBetweenAngles(trajectoryTheta90, Nodus.theNodus.getMinecraft().thePlayer.rotationYaw % 360.0F);
/* 115:124 */             double yAngleDistance = getDistanceBetweenAngles(trajectoryTheta, Nodus.theNodus.getMinecraft().thePlayer.rotationPitch % 360.0F);
/* 116:    */             
/* 117:126 */             double entityDistance = Math.sqrt(xAngleDistance * xAngleDistance + yAngleDistance * yAngleDistance);
/* 118:128 */             if (entityDistance < distanceToEntity)
/* 119:    */             {
/* 120:130 */               poorEntity = (EntityLivingBase)targetEntity;
/* 121:131 */               distanceToEntity = entityDistance;
/* 122:    */             }
/* 123:    */           }
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:135 */     return poorEntity;
/* 128:    */   }
/* 129:    */   
/* 130:    */   private float getTrajectoryAngleSolutionLow(float angleX, float angleY, float bowVelocity)
/* 131:    */   {
/* 132:140 */     float velocityIncrement = 0.006F;
/* 133:141 */     float squareRootBow = bowVelocity * bowVelocity * bowVelocity * bowVelocity - velocityIncrement * (velocityIncrement * (angleX * angleX) + 2.0F * angleY * (bowVelocity * bowVelocity));
/* 134:142 */     return (float)Math.toDegrees(Math.atan((bowVelocity * bowVelocity - Math.sqrt(squareRootBow)) / (velocityIncrement * angleX)));
/* 135:    */   }
/* 136:    */   
/* 137:    */   private float getDistanceBetweenAngles(float angle1, float angle2)
/* 138:    */   {
/* 139:147 */     float angleToEntity = Math.abs(angle1 - angle2) % 360.0F;
/* 140:148 */     if (angleToEntity > 180.0F) {
/* 141:150 */       angleToEntity = 360.0F - angleToEntity;
/* 142:    */     }
/* 143:152 */     return angleToEntity;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.BowAimbot
 * JD-Core Version:    0.7.0.1
 */
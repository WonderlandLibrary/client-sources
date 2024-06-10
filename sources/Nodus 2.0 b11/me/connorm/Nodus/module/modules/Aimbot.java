/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.Nodus.module.modules.utils.AimbotUtils;
/*  8:   */ import me.connorm.Nodus.relations.NodusRelationsManager;
/*  9:   */ import me.connorm.Nodus.relations.friend.NodusFriendManager;
/* 10:   */ import me.connorm.lib.EventTarget;
/* 11:   */ import net.minecraft.client.Minecraft;
/* 12:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/* 13:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 14:   */ import net.minecraft.entity.Entity;
/* 15:   */ import net.minecraft.entity.EntityLivingBase;
/* 16:   */ import net.minecraft.entity.player.EntityPlayer;
/* 17:   */ import net.minecraft.util.MathHelper;
/* 18:   */ import net.minecraft.util.StringUtils;
/* 19:   */ 
/* 20:   */ public class Aimbot
/* 21:   */   extends NodusModule
/* 22:   */ {
/* 23:   */   public Aimbot()
/* 24:   */   {
/* 25:20 */     super("Aimbot", Category.COMBAT);
/* 26:   */   }
/* 27:   */   
/* 28:   */   @EventTarget
/* 29:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 30:   */   {
/* 31:26 */     for (Object theObject : Nodus.theNodus.getMinecraft().theWorld.loadedEntityList) {
/* 32:28 */       if ((theObject instanceof EntityLivingBase))
/* 33:   */       {
/* 34:30 */         EntityLivingBase targetEntity = (EntityLivingBase)theObject;
/* 35:32 */         if ((AimbotUtils.aimbotMode != 1) || 
/* 36:   */         
/* 37:34 */           ((targetEntity instanceof EntityPlayer))) {
/* 38:40 */           if ((AimbotUtils.aimbotMode != 2) || 
/* 39:   */           
/* 40:42 */             (!(targetEntity instanceof EntityPlayer))) {
/* 41:48 */             if ((!targetEntity.equals(theEvent.getPlayer())) && (theEvent.getPlayer().canEntityBeSeen(targetEntity)) && (theEvent.getPlayer().getDistanceToEntity(targetEntity) <= 4.0F)) {
/* 42:50 */               if (!Nodus.theNodus.relationsManager.friendManager.isFriend(StringUtils.stripControlCodes(targetEntity.getCommandSenderName()))) {
/* 43:53 */                 faceEntity(targetEntity);
/* 44:   */               }
/* 45:   */             }
/* 46:   */           }
/* 47:   */         }
/* 48:   */       }
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void faceEntity(Entity theEntity)
/* 53:   */   {
/* 54:61 */     double xDistance = theEntity.posX - Nodus.theNodus.getMinecraft().thePlayer.posX;
/* 55:62 */     double zDistance = theEntity.posZ - Nodus.theNodus.getMinecraft().thePlayer.posZ;
/* 56:63 */     double yDistance = theEntity.posY + theEntity.getEyeHeight() / 1.4D - Nodus.theNodus.getMinecraft().thePlayer.posY + Nodus.theNodus.getMinecraft().thePlayer.getEyeHeight() / 1.4D;
/* 57:64 */     double angleHelper = MathHelper.sqrt_double(xDistance * xDistance + zDistance * zDistance);
/* 58:   */     
/* 59:66 */     float newYaw = (float)Math.toDegrees(-Math.atan(xDistance / zDistance));
/* 60:67 */     float newPitch = (float)-Math.toDegrees(Math.atan(yDistance / angleHelper));
/* 61:69 */     if ((zDistance < 0.0D) && (xDistance < 0.0D)) {
/* 62:71 */       newYaw = (float)(90.0D + Math.toDegrees(Math.atan(zDistance / xDistance)));
/* 63:73 */     } else if ((zDistance < 0.0D) && (xDistance > 0.0D)) {
/* 64:75 */       newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(zDistance / xDistance)));
/* 65:   */     }
/* 66:78 */     Nodus.theNodus.getMinecraft().thePlayer.rotationYaw = newYaw;
/* 67:79 */     Nodus.theNodus.getMinecraft().thePlayer.rotationPitch = newPitch;
/* 68:80 */     Nodus.theNodus.getMinecraft().thePlayer.rotationYawHead = newPitch;
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Aimbot
 * JD-Core Version:    0.7.0.1
 */
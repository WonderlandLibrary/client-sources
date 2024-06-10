/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  6:   */ import me.connorm.Nodus.module.core.Category;
/*  7:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  8:   */ import me.connorm.Nodus.module.modules.utils.AimbotUtils;
/*  9:   */ import me.connorm.Nodus.relations.NodusRelationsManager;
/* 10:   */ import me.connorm.Nodus.relations.friend.NodusFriendManager;
/* 11:   */ import me.connorm.lib.EventTarget;
/* 12:   */ import net.minecraft.client.Minecraft;
/* 13:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/* 14:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 15:   */ import net.minecraft.entity.Entity;
/* 16:   */ import net.minecraft.entity.EntityLivingBase;
/* 17:   */ import net.minecraft.entity.player.EntityPlayer;
/* 18:   */ import net.minecraft.util.MathHelper;
/* 19:   */ import net.minecraft.util.StringUtils;
/* 20:   */ import org.lwjgl.input.Mouse;
/* 21:   */ 
/* 22:   */ public class ClickAimbot
/* 23:   */   extends NodusModule
/* 24:   */ {
/* 25:   */   public ClickAimbot()
/* 26:   */   {
/* 27:22 */     super("ClickAimbot", Category.COMBAT);
/* 28:   */   }
/* 29:   */   
/* 30:   */   @EventTarget
/* 31:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 32:   */   {
/* 33:28 */     EntityPlayer thePlayer = theEvent.getPlayer();
/* 34:29 */     for (Object theObject : Nodus.theNodus.getMinecraft().theWorld.loadedEntityList) {
/* 35:31 */       if ((theObject instanceof EntityLivingBase))
/* 36:   */       {
/* 37:33 */         EntityLivingBase targetEntity = (EntityLivingBase)theObject;
/* 38:34 */         if ((!targetEntity.equals(theEvent.getPlayer())) && (theEvent.getPlayer().canEntityBeSeen(targetEntity)) && (theEvent.getPlayer().getDistanceToEntity(targetEntity) <= 4.0F)) {
/* 39:36 */           if (Mouse.isButtonDown(0)) {
/* 40:39 */             if ((AimbotUtils.aimbotMode != 1) || 
/* 41:   */             
/* 42:41 */               ((targetEntity instanceof EntityPlayer))) {
/* 43:47 */               if ((AimbotUtils.aimbotMode != 2) || 
/* 44:   */               
/* 45:49 */                 (!(targetEntity instanceof EntityPlayer))) {
/* 46:55 */                 if (!Nodus.theNodus.relationsManager.friendManager.isFriend(StringUtils.stripControlCodes(targetEntity.getCommandSenderName())))
/* 47:   */                 {
/* 48:58 */                   faceEntity(targetEntity);
/* 49:59 */                   if (Nodus.theNodus.moduleManager.autoSwordModule.isToggled()) {
/* 50:61 */                     Nodus.theNodus.moduleManager.autoSwordModule.executeAutoSword(thePlayer);
/* 51:   */                   }
/* 52:   */                 }
/* 53:   */               }
/* 54:   */             }
/* 55:   */           }
/* 56:   */         }
/* 57:   */       }
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void faceEntity(Entity theEntity)
/* 62:   */   {
/* 63:71 */     double xDistance = theEntity.posX - Nodus.theNodus.getMinecraft().thePlayer.posX;
/* 64:72 */     double zDistance = theEntity.posZ - Nodus.theNodus.getMinecraft().thePlayer.posZ;
/* 65:73 */     double yDistance = theEntity.posY + theEntity.getEyeHeight() / 1.4D - Nodus.theNodus.getMinecraft().thePlayer.posY + Nodus.theNodus.getMinecraft().thePlayer.getEyeHeight() / 1.4D;
/* 66:74 */     double angleHelper = MathHelper.sqrt_double(xDistance * xDistance + zDistance * zDistance);
/* 67:   */     
/* 68:76 */     float newYaw = (float)Math.toDegrees(-Math.atan(xDistance / zDistance));
/* 69:77 */     float newPitch = (float)-Math.toDegrees(Math.atan(yDistance / angleHelper));
/* 70:79 */     if ((zDistance < 0.0D) && (xDistance < 0.0D)) {
/* 71:81 */       newYaw = (float)(90.0D + Math.toDegrees(Math.atan(zDistance / xDistance)));
/* 72:83 */     } else if ((zDistance < 0.0D) && (xDistance > 0.0D)) {
/* 73:85 */       newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(zDistance / xDistance)));
/* 74:   */     }
/* 75:88 */     Nodus.theNodus.getMinecraft().thePlayer.rotationYaw = newYaw;
/* 76:89 */     Nodus.theNodus.getMinecraft().thePlayer.rotationPitch = newPitch;
/* 77:90 */     Nodus.theNodus.getMinecraft().thePlayer.rotationYawHead = newPitch;
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.ClickAimbot
 * JD-Core Version:    0.7.0.1
 */
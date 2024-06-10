/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  6:   */ import me.connorm.Nodus.module.core.Category;
/*  7:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  8:   */ import me.connorm.Nodus.module.modules.utils.ForceFieldUtils;
/*  9:   */ import me.connorm.Nodus.relations.NodusRelationsManager;
/* 10:   */ import me.connorm.Nodus.relations.friend.NodusFriendManager;
/* 11:   */ import me.connorm.lib.EventTarget;
/* 12:   */ import net.minecraft.client.Minecraft;
/* 13:   */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/* 14:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 15:   */ import net.minecraft.entity.EntityLivingBase;
/* 16:   */ import net.minecraft.entity.player.EntityPlayer;
/* 17:   */ import net.minecraft.util.StringUtils;
/* 18:   */ 
/* 19:   */ public class ForceField
/* 20:   */   extends NodusModule
/* 21:   */ {
/* 22:16 */   private long currentTime = 0L;
/* 23:17 */   private long lastTime = -1L;
/* 24:18 */   private float auraRange = 4.0F;
/* 25:19 */   private float auraAPS = 15.0F;
/* 26:   */   
/* 27:   */   public ForceField()
/* 28:   */   {
/* 29:23 */     super("ForceField", Category.COMBAT);
/* 30:   */   }
/* 31:   */   
/* 32:   */   @EventTarget
/* 33:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 34:   */   {
/* 35:29 */     EntityPlayer thePlayer = theEvent.getPlayer();
/* 36:30 */     this.currentTime = (System.nanoTime() / 1000000L);
/* 37:31 */     if (!hasDelayed((1000.0F / this.auraAPS))) {
/* 38:32 */       return;
/* 39:   */     }
/* 40:33 */     for (Object theObject : Nodus.theNodus.getMinecraft().theWorld.loadedEntityList) {
/* 41:35 */       if ((theObject instanceof EntityLivingBase))
/* 42:   */       {
/* 43:37 */         EntityLivingBase targetEntity = (EntityLivingBase)theObject;
/* 44:39 */         if ((ForceFieldUtils.forceFieldMode != 1) || 
/* 45:   */         
/* 46:41 */           ((targetEntity instanceof EntityPlayer))) {
/* 47:47 */           if ((ForceFieldUtils.forceFieldMode != 2) || 
/* 48:   */           
/* 49:49 */             (!(targetEntity instanceof EntityPlayer))) {
/* 50:55 */             if ((!targetEntity.equals(thePlayer)) && (thePlayer.getDistanceToEntity(targetEntity) <= this.auraRange)) {
/* 51:57 */               if (!Nodus.theNodus.relationsManager.friendManager.isFriend(StringUtils.stripControlCodes(targetEntity.getCommandSenderName())))
/* 52:   */               {
/* 53:59 */                 thePlayer.swingItem();
/* 54:60 */                 Nodus.theNodus.getMinecraft().playerController.attackEntity(thePlayer, targetEntity);
/* 55:61 */                 this.lastTime = (System.nanoTime() / 1000000L);
/* 56:62 */                 if (!Nodus.theNodus.moduleManager.autoSwordModule.isToggled()) {
/* 57:   */                   break;
/* 58:   */                 }
/* 59:64 */                 Nodus.theNodus.moduleManager.autoSwordModule.executeAutoSword(thePlayer);
/* 60:   */                 
/* 61:66 */                 break;
/* 62:   */               }
/* 63:   */             }
/* 64:   */           }
/* 65:   */         }
/* 66:   */       }
/* 67:   */     }
/* 68:   */   }
/* 69:   */   
/* 70:   */   private boolean hasDelayed(long theTime)
/* 71:   */   {
/* 72:74 */     return this.currentTime - this.lastTime >= theTime;
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.ForceField
 * JD-Core Version:    0.7.0.1
 */
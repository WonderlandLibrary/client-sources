/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.event.player.EventPlayerAttackEntity;
/*  4:   */ import me.connorm.Nodus.module.core.Category;
/*  5:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  6:   */ import me.connorm.lib.EventTarget;
/*  7:   */ import net.minecraft.block.material.Material;
/*  8:   */ import net.minecraft.entity.player.EntityPlayer;
/*  9:   */ 
/* 10:   */ public class Criticals
/* 11:   */   extends NodusModule
/* 12:   */ {
/* 13:   */   public Criticals()
/* 14:   */   {
/* 15:15 */     super("Criticals", Category.COMBAT);
/* 16:   */   }
/* 17:   */   
/* 18:   */   @EventTarget
/* 19:   */   public void attackEntity(EventPlayerAttackEntity theEvent)
/* 20:   */   {
/* 21:21 */     EntityPlayer thePlayer = theEvent.getPlayer();
/* 22:22 */     boolean shouldCritical = (!thePlayer.isInWater()) && (!thePlayer.isInsideOfMaterial(Material.lava)) && (thePlayer.onGround);
/* 23:23 */     if (shouldCritical)
/* 24:   */     {
/* 25:25 */       thePlayer.motionY = 0.1000000014901161D;
/* 26:26 */       thePlayer.fallDistance = 0.1F;
/* 27:27 */       thePlayer.onGround = false;
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Criticals
 * JD-Core Version:    0.7.0.1
 */
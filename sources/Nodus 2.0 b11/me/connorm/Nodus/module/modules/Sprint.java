/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  4:   */ import me.connorm.Nodus.module.core.Category;
/*  5:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  6:   */ import me.connorm.lib.EventTarget;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ 
/*  9:   */ public class Sprint
/* 10:   */   extends NodusModule
/* 11:   */ {
/* 12:   */   public Sprint()
/* 13:   */   {
/* 14:13 */     super("Sprint", Category.PLAYER);
/* 15:   */   }
/* 16:   */   
/* 17:   */   @EventTarget
/* 18:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 19:   */   {
/* 20:19 */     if ((!theEvent.getPlayer().isCollidedHorizontally) && (theEvent.getPlayer().moveForward != 0.0F)) {
/* 21:21 */       theEvent.getPlayer().setSprinting(true);
/* 22:   */     }
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Sprint
 * JD-Core Version:    0.7.0.1
 */
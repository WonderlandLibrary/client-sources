/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.lib.EventTarget;
/*  8:   */ 
/*  9:   */ public class FastPlace
/* 10:   */   extends NodusModule
/* 11:   */ {
/* 12:   */   public FastPlace()
/* 13:   */   {
/* 14:14 */     super("FastPlace", Category.WORLD);
/* 15:   */   }
/* 16:   */   
/* 17:   */   @EventTarget
/* 18:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 19:   */   {
/* 20:20 */     Nodus.theNodus.getMinecraft().rightClickDelayTimer = 0;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.FastPlace
 * JD-Core Version:    0.7.0.1
 */
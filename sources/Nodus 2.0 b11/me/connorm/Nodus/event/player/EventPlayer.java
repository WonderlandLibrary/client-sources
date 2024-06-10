/*  1:   */ package me.connorm.Nodus.event.player;
/*  2:   */ 
/*  3:   */ import me.connorm.lib.event.Event;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ 
/*  6:   */ public class EventPlayer
/*  7:   */   implements Event
/*  8:   */ {
/*  9:   */   private EntityPlayer thePlayer;
/* 10:   */   
/* 11:   */   public EventPlayer(EntityPlayer thePlayer)
/* 12:   */   {
/* 13:12 */     this.thePlayer = thePlayer;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public EntityPlayer getPlayer()
/* 17:   */   {
/* 18:17 */     return this.thePlayer;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.event.player.EventPlayer
 * JD-Core Version:    0.7.0.1
 */
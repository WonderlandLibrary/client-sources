/*  1:   */ package me.connorm.Nodus.event.player;
/*  2:   */ 
/*  3:   */ import me.connorm.lib.event.Event;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ 
/*  7:   */ public class EventPlayerPostAttackEntity
/*  8:   */   implements Event
/*  9:   */ {
/* 10:   */   private EntityPlayer thePlayer;
/* 11:   */   private Entity targetEntity;
/* 12:   */   
/* 13:   */   public EventPlayerPostAttackEntity(EntityPlayer thePlayer, Entity targetEntity)
/* 14:   */   {
/* 15:14 */     this.thePlayer = thePlayer;
/* 16:15 */     this.targetEntity = targetEntity;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public EntityPlayer getPlayer()
/* 20:   */   {
/* 21:20 */     return this.thePlayer;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Entity getTarget()
/* 25:   */   {
/* 26:25 */     return this.targetEntity;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.event.player.EventPlayerPostAttackEntity
 * JD-Core Version:    0.7.0.1
 */
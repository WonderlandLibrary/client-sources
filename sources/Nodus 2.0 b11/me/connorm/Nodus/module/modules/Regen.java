/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.lib.EventTarget;
/*  8:   */ import net.minecraft.client.Minecraft;
/*  9:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/* 10:   */ import net.minecraft.client.network.NetHandlerPlayClient;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.network.play.client.C03PacketPlayer;
/* 13:   */ 
/* 14:   */ public class Regen
/* 15:   */   extends NodusModule
/* 16:   */ {
/* 17:   */   public Regen()
/* 18:   */   {
/* 19:15 */     super("Regen", Category.COMBAT);
/* 20:   */   }
/* 21:   */   
/* 22:   */   @EventTarget
/* 23:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 24:   */   {
/* 25:21 */     if ((theEvent.getPlayer().isOnLadder()) || (theEvent.getPlayer().isInWater()) || (theEvent.getPlayer().onGround)) {
/* 26:30 */       new Thread()
/* 27:   */       {
/* 28:   */         public void run()
/* 29:   */         {
/* 30:26 */           for (short theShort = 0; theShort < 3100; theShort = (short)(theShort + 1)) {
/* 31:27 */             Nodus.theNodus.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
/* 32:   */           }
/* 33:   */         }
/* 34:   */       }.start();
/* 35:   */     }
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Regen
 * JD-Core Version:    0.7.0.1
 */
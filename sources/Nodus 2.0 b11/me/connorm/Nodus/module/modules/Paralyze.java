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
/* 11:   */ import net.minecraft.network.play.client.C03PacketPlayer;
/* 12:   */ 
/* 13:   */ public class Paralyze
/* 14:   */   extends NodusModule
/* 15:   */ {
/* 16:   */   public Paralyze()
/* 17:   */   {
/* 18:15 */     super("Paralyze", Category.WORLD);
/* 19:   */   }
/* 20:   */   
/* 21:   */   @EventTarget
/* 22:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 23:   */   {
/* 24:21 */     spamPackets();
/* 25:   */   }
/* 26:   */   
/* 27:   */   private void spamPackets()
/* 28:   */   {
/* 29:26 */     for (int packetIterator = 0; packetIterator < 20000; packetIterator++) {
/* 30:28 */       Nodus.theNodus.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
/* 31:   */     }
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Paralyze
 * JD-Core Version:    0.7.0.1
 */
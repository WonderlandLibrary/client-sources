/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.lib.EventTarget;
/*  8:   */ import net.minecraft.client.Minecraft;
/*  9:   */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/* 10:   */ import net.minecraft.client.network.NetHandlerPlayClient;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.network.play.client.C03PacketPlayer;
/* 13:   */ 
/* 14:   */ public class NoFall
/* 15:   */   extends NodusModule
/* 16:   */ {
/* 17:   */   public NoFall()
/* 18:   */   {
/* 19:16 */     super("NoFall", Category.PLAYER);
/* 20:   */   }
/* 21:   */   
/* 22:   */   @EventTarget
/* 23:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 24:   */   {
/* 25:22 */     EntityPlayer thePlayer = theEvent.getPlayer();
/* 26:23 */     if (thePlayer.fallDistance > 2.0F) {
/* 27:25 */       Nodus.theNodus.getMinecraft().playerController.netClientHandler.addToSendQueue(new C03PacketPlayer(true));
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.NoFall
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  4:   */ import me.connorm.Nodus.module.core.Category;
/*  5:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  6:   */ import me.connorm.lib.EventTarget;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  9:   */ import net.minecraft.item.Item;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.item.ItemSword;
/* 12:   */ import org.lwjgl.input.Mouse;
/* 13:   */ 
/* 14:   */ public class AutoSword
/* 15:   */   extends NodusModule
/* 16:   */ {
/* 17:   */   public AutoSword()
/* 18:   */   {
/* 19:18 */     super("AutoSword", Category.COMBAT);
/* 20:   */   }
/* 21:   */   
/* 22:   */   @EventTarget
/* 23:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 24:   */   {
/* 25:24 */     EntityPlayer thePlayer = theEvent.getPlayer();
/* 26:25 */     if (Mouse.isButtonDown(0)) {
/* 27:27 */       executeAutoSword(thePlayer);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void executeAutoSword(EntityPlayer thePlayer)
/* 32:   */   {
/* 33:33 */     for (int inventorySlot = 0; inventorySlot < 9; inventorySlot++) {
/* 34:35 */       if (thePlayer.inventory.getStackInSlot(inventorySlot) != null)
/* 35:   */       {
/* 36:37 */         Item currentSword = thePlayer.inventory.getStackInSlot(inventorySlot).getItem();
/* 37:38 */         if ((currentSword != null) && ((currentSword instanceof ItemSword))) {
/* 38:39 */           thePlayer.inventory.currentItem = inventorySlot;
/* 39:   */         }
/* 40:   */       }
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.AutoSword
 * JD-Core Version:    0.7.0.1
 */
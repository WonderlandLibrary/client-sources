/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.lib.EventTarget;
/*  8:   */ import net.minecraft.client.Minecraft;
/*  9:   */ import net.minecraft.client.settings.GameSettings;
/* 10:   */ import net.minecraft.entity.player.EntityPlayer;
/* 11:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 12:   */ import net.minecraft.item.ItemFood;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.util.FoodStats;
/* 15:   */ 
/* 16:   */ public class AutoEat
/* 17:   */   extends NodusModule
/* 18:   */ {
/* 19:15 */   private boolean eatingFood = false;
/* 20:16 */   private int previousSlot = -1;
/* 21:17 */   private boolean finishedEating = false;
/* 22:   */   
/* 23:   */   public AutoEat()
/* 24:   */   {
/* 25:21 */     super("AutoEat", Category.PLAYER);
/* 26:   */   }
/* 27:   */   
/* 28:   */   @EventTarget
/* 29:   */   public void updatePlayer(EventPlayerUpdate theEvent)
/* 30:   */   {
/* 31:27 */     EntityPlayer thePlayer = theEvent.getPlayer();
/* 32:28 */     InventoryPlayer theInventory = thePlayer.inventory;
/* 33:30 */     if ((this.previousSlot != -1) && (this.finishedEating) && (this.eatingFood))
/* 34:   */     {
/* 35:32 */       thePlayer.inventory.currentItem = this.previousSlot;
/* 36:   */       
/* 37:34 */       Nodus.theNodus.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
/* 38:   */       
/* 39:36 */       this.eatingFood = false;
/* 40:   */     }
/* 41:41 */     if (thePlayer.getFoodStats().getFoodLevel() < 18) {
/* 42:43 */       for (int inventorySlot = 0; inventorySlot < 9; inventorySlot++)
/* 43:   */       {
/* 44:45 */         ItemStack currentItem = theInventory.getStackInSlot(inventorySlot);
/* 45:47 */         if ((currentItem != null) && ((currentItem.getItem() instanceof ItemFood)))
/* 46:   */         {
/* 47:50 */           this.previousSlot = theInventory.currentItem;
/* 48:   */           
/* 49:52 */           theInventory.currentItem = inventorySlot;
/* 50:   */           
/* 51:54 */           Nodus.theNodus.getMinecraft().gameSettings.keyBindUseItem.pressed = true;
/* 52:56 */           if (thePlayer.getFoodStats().getFoodLevel() > 16)
/* 53:   */           {
/* 54:59 */             this.eatingFood = true;
/* 55:   */             
/* 56:61 */             this.finishedEating = true;
/* 57:   */           }
/* 58:   */         }
/* 59:   */       }
/* 60:   */     }
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.AutoEat
 * JD-Core Version:    0.7.0.1
 */
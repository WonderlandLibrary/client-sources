/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.event.player.EventPlayerBlockClick;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import me.connorm.lib.EventTarget;
/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ import net.minecraft.client.Minecraft;
/* 10:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class AutoTool
/* 16:   */   extends NodusModule
/* 17:   */ {
/* 18:   */   public AutoTool()
/* 19:   */   {
/* 20:17 */     super("AutoTool", Category.PLAYER);
/* 21:   */   }
/* 22:   */   
/* 23:   */   @EventTarget
/* 24:   */   public void clickBlock(EventPlayerBlockClick theEvent)
/* 25:   */   {
/* 26:23 */     EntityPlayer thePlayer = theEvent.getPlayer();
/* 27:   */     
/* 28:25 */     float compareTool = 1.0F;
/* 29:26 */     int inventorySlot = -1;
/* 30:28 */     for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
/* 31:   */       try
/* 32:   */       {
/* 33:31 */         ItemStack currentTool = thePlayer.inventory.getStackInSlot(hotbarSlot);
/* 34:33 */         if (currentTool != null)
/* 35:   */         {
/* 36:35 */           float toolStregnth = getStregnthAgainstBlock(currentTool, Nodus.theNodus.getMinecraft().theWorld.getBlock(theEvent.getX(), theEvent.getY(), theEvent.getZ()));
/* 37:37 */           if (toolStregnth > compareTool)
/* 38:   */           {
/* 39:39 */             compareTool = toolStregnth;
/* 40:40 */             inventorySlot = hotbarSlot;
/* 41:   */           }
/* 42:   */         }
/* 43:   */       }
/* 44:   */       catch (Exception localException) {}
/* 45:   */     }
/* 46:44 */     if (inventorySlot != -1) {
/* 47:45 */       thePlayer.inventory.currentItem = inventorySlot;
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   private float getStregnthAgainstBlock(ItemStack itemStack, Block blockCheck)
/* 52:   */   {
/* 53:50 */     return itemStack.func_150997_a(blockCheck);
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.AutoTool
 * JD-Core Version:    0.7.0.1
 */
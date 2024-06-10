/*   1:    */ package me.connorm.Nodus.module.modules;
/*   2:    */ 
/*   3:    */ import me.connorm.Nodus.Nodus;
/*   4:    */ import me.connorm.Nodus.event.player.EventPlayerMotionUpdate;
/*   5:    */ import me.connorm.Nodus.event.player.EventPlayerPostMotionUpdate;
/*   6:    */ import me.connorm.Nodus.module.core.Category;
/*   7:    */ import me.connorm.Nodus.module.core.NodusModule;
/*   8:    */ import me.connorm.lib.EventTarget;
/*   9:    */ import net.minecraft.client.Minecraft;
/*  10:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  11:    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*  12:    */ import net.minecraft.entity.player.EntityPlayer;
/*  13:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  14:    */ import net.minecraft.inventory.Container;
/*  15:    */ import net.minecraft.inventory.Slot;
/*  16:    */ import net.minecraft.item.Item;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ 
/*  19:    */ public class AutoSoup
/*  20:    */   extends NodusModule
/*  21:    */ {
/*  22: 15 */   int soupCount = 0;
/*  23: 16 */   int previousItem = -1;
/*  24:    */   
/*  25:    */   public AutoSoup()
/*  26:    */   {
/*  27: 20 */     super("AutoSoup", Category.COMBAT);
/*  28:    */   }
/*  29:    */   
/*  30:    */   @EventTarget
/*  31:    */   public void updatePlayerMotion(EventPlayerMotionUpdate theEvent)
/*  32:    */   {
/*  33: 26 */     EntityPlayer thePlayer = theEvent.getPlayer();
/*  34:    */     
/*  35: 28 */     this.soupCount = updateSoupCount();
/*  36:    */     
/*  37: 30 */     float playerHealth = thePlayer.getHealth();
/*  38: 32 */     if ((playerHealth <= 14.0F) && (this.soupCount > 0))
/*  39:    */     {
/*  40: 35 */       this.previousItem = -1;
/*  41: 36 */       int soupSlot = -1;
/*  42: 38 */       for (int inventorySlot = 0; inventorySlot < 9; inventorySlot++)
/*  43:    */       {
/*  44: 40 */         ItemStack currentItem = thePlayer.inventory.getStackInSlot(inventorySlot);
/*  45: 42 */         if ((currentItem != null) && (currentItem.getItem() == Item.getItemById(282))) {
/*  46: 44 */           soupSlot = inventorySlot;
/*  47:    */         }
/*  48:    */       }
/*  49: 48 */       if (soupSlot == -1)
/*  50:    */       {
/*  51: 50 */         for (int inventorySlot = 9; inventorySlot < 36; inventorySlot++) {
/*  52: 52 */           if (thePlayer.inventoryContainer.getSlot(inventorySlot).getHasStack())
/*  53:    */           {
/*  54: 54 */             ItemStack currentItem = thePlayer.inventoryContainer.getSlot(inventorySlot).getStack();
/*  55: 56 */             if ((currentItem != null) && (currentItem.getItem() == Item.getItemById(282))) {
/*  56: 58 */               soupSlot = inventorySlot;
/*  57:    */             }
/*  58:    */           }
/*  59:    */         }
/*  60: 63 */         if (soupSlot == -1)
/*  61:    */         {
/*  62: 65 */           this.soupCount = 0;
/*  63: 66 */           return;
/*  64:    */         }
/*  65: 69 */         cleanUp();
/*  66: 70 */         Nodus.theNodus.getMinecraft().playerController.windowClick(thePlayer.inventoryContainer.windowId, soupSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/*  67: 71 */         return;
/*  68:    */       }
/*  69: 75 */       this.previousItem = thePlayer.inventory.currentItem;
/*  70: 76 */       thePlayer.inventory.currentItem = soupSlot;
/*  71: 77 */       Nodus.theNodus.getMinecraft().playerController.sendUseItem(thePlayer, Nodus.theNodus.getMinecraft().theWorld, thePlayer.inventory.getStackInSlot(soupSlot));
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   @EventTarget
/*  76:    */   public void postUpdatePlayerMotion(EventPlayerPostMotionUpdate theEvent)
/*  77:    */   {
/*  78: 85 */     if (this.previousItem != -1)
/*  79:    */     {
/*  80: 87 */       Nodus.theNodus.getMinecraft().playerController.onStoppedUsingItem(Nodus.theNodus.getMinecraft().thePlayer);
/*  81: 88 */       Nodus.theNodus.getMinecraft().thePlayer.inventory.currentItem = this.previousItem;
/*  82: 89 */       this.previousItem = -1;
/*  83: 90 */       cleanUp();
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void cleanUp()
/*  88:    */   {
/*  89: 96 */     boolean hasRoom = false;
/*  90: 97 */     for (int inventorySlot = 9; inventorySlot < 36; inventorySlot++)
/*  91:    */     {
/*  92: 99 */       ItemStack currentItem = Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.getSlot(inventorySlot).getStack();
/*  93:101 */       if ((currentItem == null) || ((currentItem.getItem() == Item.getItemById(281)) && (currentItem.stackSize < 65))) {
/*  94:103 */         hasRoom = true;
/*  95:    */       }
/*  96:    */     }
/*  97:106 */     for (int inventorySlot = 36; inventorySlot < 45; inventorySlot++) {
/*  98:108 */       if (Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.getSlot(inventorySlot).getHasStack())
/*  99:    */       {
/* 100:110 */         ItemStack currentItem = Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.getSlot(inventorySlot).getStack();
/* 101:112 */         if ((currentItem != null) && (currentItem.getItem() == Item.getItemById(281)))
/* 102:    */         {
/* 103:114 */           if (hasRoom)
/* 104:    */           {
/* 105:116 */             Nodus.theNodus.getMinecraft().playerController.windowClick(Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.windowId, inventorySlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/* 106:117 */             break;
/* 107:    */           }
/* 108:120 */           Nodus.theNodus.getMinecraft().playerController.windowClick(Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.windowId, inventorySlot, 0, 0, Nodus.theNodus.getMinecraft().thePlayer);
/* 109:121 */           Nodus.theNodus.getMinecraft().playerController.windowClick(Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.windowId, -999, 0, 0, Nodus.theNodus.getMinecraft().thePlayer);
/* 110:122 */           break;
/* 111:    */         }
/* 112:    */       }
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int updateSoupCount()
/* 117:    */   {
/* 118:131 */     int soupCount = 0;
/* 119:132 */     for (int inventorySlot = 0; inventorySlot < 45; inventorySlot++)
/* 120:    */     {
/* 121:134 */       ItemStack currentItem = Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.getSlot(inventorySlot).getStack();
/* 122:136 */       if ((currentItem != null) && (currentItem.getItem() == Item.getItemById(282))) {
/* 123:138 */         soupCount++;
/* 124:    */       }
/* 125:    */     }
/* 126:141 */     return soupCount;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.AutoSoup
 * JD-Core Version:    0.7.0.1
 */
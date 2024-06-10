/*   1:    */ package me.connorm.Nodus.module.modules;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.List;
/*   5:    */ import me.connorm.Nodus.Nodus;
/*   6:    */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*   7:    */ import me.connorm.Nodus.module.core.Category;
/*   8:    */ import me.connorm.Nodus.module.core.NodusModule;
/*   9:    */ import me.connorm.lib.EventTarget;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  12:    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  15:    */ import net.minecraft.inventory.Container;
/*  16:    */ import net.minecraft.inventory.Slot;
/*  17:    */ import net.minecraft.item.Item;
/*  18:    */ import net.minecraft.item.ItemStack;
/*  19:    */ 
/*  20:    */ public class AutoArmor
/*  21:    */   extends NodusModule
/*  22:    */ {
/*  23:    */   public AutoArmor()
/*  24:    */   {
/*  25: 18 */     super("AutoArmor", Category.PLAYER);
/*  26:    */   }
/*  27:    */   
/*  28:    */   @EventTarget
/*  29:    */   public void updatePlayer(EventPlayerUpdate theEvent)
/*  30:    */   {
/*  31: 24 */     ItemStack playerHelmet = theEvent.getPlayer().inventory.armorItemInSlot(3);
/*  32: 25 */     ItemStack playerChestplate = theEvent.getPlayer().inventory.armorItemInSlot(2);
/*  33: 26 */     ItemStack playerLeggings = theEvent.getPlayer().inventory.armorItemInSlot(1);
/*  34: 27 */     ItemStack playerBoots = theEvent.getPlayer().inventory.armorItemInSlot(0);
/*  35: 29 */     if (playerHelmet == null) {
/*  36: 31 */       wearHelmet();
/*  37:    */     } else {
/*  38: 34 */       compareItem(playerHelmet, this.helmetPriority, 3);
/*  39:    */     }
/*  40: 37 */     if (playerChestplate == null) {
/*  41: 39 */       wearChestplate();
/*  42:    */     } else {
/*  43: 42 */       compareItem(playerChestplate, this.chestPriority, 2);
/*  44:    */     }
/*  45: 45 */     if (playerLeggings == null) {
/*  46: 47 */       wearLeggings();
/*  47:    */     } else {
/*  48: 50 */       compareItem(playerLeggings, this.legsPriority, 1);
/*  49:    */     }
/*  50: 53 */     if (playerBoots == null) {
/*  51: 55 */       wearBoots();
/*  52:    */     } else {
/*  53: 58 */       compareItem(playerBoots, this.bootsPriority, 0);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   private void removeArmor(int armorPiece)
/*  58:    */   {
/*  59: 64 */     Nodus.theNodus.getMinecraft().playerController.windowClick(0, 8 - armorPiece, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void compareItem(ItemStack currentItem, Integer[] itemPriority, int armorPiece)
/*  63:    */   {
/*  64: 69 */     for (int itemSlot = 44; itemSlot >= 9; itemSlot--)
/*  65:    */     {
/*  66: 71 */       ItemStack itemStack = Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.getSlot(itemSlot).getStack();
/*  67: 72 */       if (itemStack != null) {
/*  68: 74 */         if (Arrays.asList(itemPriority).indexOf(Integer.valueOf(Item.getIdFromItem(itemStack.getItem()))) > Arrays.asList(itemPriority).indexOf(Integer.valueOf(Item.getIdFromItem(currentItem.getItem())))) {
/*  69: 76 */           if ((itemSlot >= 36) && (itemSlot <= 44))
/*  70:    */           {
/*  71: 78 */             Nodus.theNodus.getMinecraft().thePlayer.inventory.currentItem = (itemSlot - 36);
/*  72: 79 */             removeArmor(armorPiece);
/*  73: 80 */             Nodus.theNodus.getMinecraft().playerController.windowClick(0, itemSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/*  74:    */           }
/*  75:    */           else
/*  76:    */           {
/*  77: 83 */             removeArmor(armorPiece);
/*  78: 84 */             Nodus.theNodus.getMinecraft().playerController.windowClick(0, itemSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/*  79:    */           }
/*  80:    */         }
/*  81:    */       }
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void wearHelmet()
/*  86:    */   {
/*  87: 93 */     for (int itemSlot = 44; itemSlot >= 9; itemSlot--)
/*  88:    */     {
/*  89: 95 */       ItemStack itemStack = Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.getSlot(itemSlot).getStack();
/*  90: 97 */       if (itemStack != null) {
/*  91: 99 */         if ((itemSlot >= 36) && (itemSlot <= 44))
/*  92:    */         {
/*  93:101 */           if (Arrays.asList(this.helmetPriority).contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem()))))
/*  94:    */           {
/*  95:103 */             Nodus.theNodus.getMinecraft().thePlayer.inventory.currentItem = (itemSlot - 36);
/*  96:104 */             Nodus.theNodus.getMinecraft().playerController.windowClick(0, itemSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/*  97:    */           }
/*  98:    */         }
/*  99:106 */         else if (Arrays.asList(this.helmetPriority).contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem())))) {
/* 100:108 */           Nodus.theNodus.getMinecraft().playerController.windowClick(0, itemSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/* 101:    */         }
/* 102:    */       }
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   private void wearChestplate()
/* 107:    */   {
/* 108:116 */     for (int itemSlot = 44; itemSlot >= 9; itemSlot--)
/* 109:    */     {
/* 110:118 */       ItemStack itemStack = Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.getSlot(itemSlot).getStack();
/* 111:120 */       if (itemStack != null) {
/* 112:122 */         if ((itemSlot >= 36) && (itemSlot <= 44))
/* 113:    */         {
/* 114:124 */           if (Arrays.asList(this.chestPriority).contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem()))))
/* 115:    */           {
/* 116:126 */             Nodus.theNodus.getMinecraft().thePlayer.inventory.currentItem = (itemSlot - 36);
/* 117:127 */             Nodus.theNodus.getMinecraft().playerController.windowClick(0, itemSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/* 118:    */           }
/* 119:    */         }
/* 120:130 */         else if (Arrays.asList(this.chestPriority).contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem())))) {
/* 121:132 */           Nodus.theNodus.getMinecraft().playerController.windowClick(0, itemSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/* 122:    */         }
/* 123:    */       }
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   private void wearLeggings()
/* 128:    */   {
/* 129:140 */     for (int itemSlot = 44; itemSlot >= 9; itemSlot--)
/* 130:    */     {
/* 131:142 */       ItemStack itemStack = Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.getSlot(itemSlot).getStack();
/* 132:144 */       if (itemStack != null) {
/* 133:146 */         if ((itemSlot >= 36) && (itemSlot <= 44))
/* 134:    */         {
/* 135:148 */           if (Arrays.asList(this.legsPriority).contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem()))))
/* 136:    */           {
/* 137:150 */             Nodus.theNodus.getMinecraft().thePlayer.inventory.currentItem = (itemSlot - 36);
/* 138:151 */             Nodus.theNodus.getMinecraft().playerController.windowClick(0, itemSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/* 139:    */           }
/* 140:    */         }
/* 141:154 */         else if (Arrays.asList(this.legsPriority).contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem())))) {
/* 142:156 */           Nodus.theNodus.getMinecraft().playerController.windowClick(0, itemSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/* 143:    */         }
/* 144:    */       }
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   private void wearBoots()
/* 149:    */   {
/* 150:164 */     for (int itemSlot = 44; itemSlot >= 9; itemSlot--)
/* 151:    */     {
/* 152:166 */       ItemStack itemStack = Nodus.theNodus.getMinecraft().thePlayer.inventoryContainer.getSlot(itemSlot).getStack();
/* 153:168 */       if (itemStack != null) {
/* 154:170 */         if ((itemSlot >= 36) && (itemSlot <= 44))
/* 155:    */         {
/* 156:172 */           if (Arrays.asList(this.bootsPriority).contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem()))))
/* 157:    */           {
/* 158:174 */             Nodus.theNodus.getMinecraft().thePlayer.inventory.currentItem = (itemSlot - 36);
/* 159:175 */             Nodus.theNodus.getMinecraft().playerController.windowClick(0, itemSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/* 160:    */           }
/* 161:    */         }
/* 162:178 */         else if (Arrays.asList(this.bootsPriority).contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem())))) {
/* 163:180 */           Nodus.theNodus.getMinecraft().playerController.windowClick(0, itemSlot, 0, 1, Nodus.theNodus.getMinecraft().thePlayer);
/* 164:    */         }
/* 165:    */       }
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:187 */   private final Integer[] helmetPriority = {
/* 170:188 */     Integer.valueOf(298), Integer.valueOf(314), Integer.valueOf(302), Integer.valueOf(306), Integer.valueOf(310) };
/* 171:192 */   private final Integer[] chestPriority = {
/* 172:193 */     Integer.valueOf(299), Integer.valueOf(315), Integer.valueOf(303), Integer.valueOf(307), Integer.valueOf(311) };
/* 173:197 */   private final Integer[] legsPriority = {
/* 174:198 */     Integer.valueOf(300), Integer.valueOf(316), Integer.valueOf(304), Integer.valueOf(308), Integer.valueOf(312) };
/* 175:202 */   private final Integer[] bootsPriority = {
/* 176:203 */     Integer.valueOf(301), Integer.valueOf(317), Integer.valueOf(305), Integer.valueOf(309), Integer.valueOf(313) };
/* 177:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.AutoArmor
 * JD-Core Version:    0.7.0.1
 */
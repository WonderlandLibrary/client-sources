/*     */ package me.eagler.module.modules.player;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import me.eagler.event.EventManager;
/*     */ import me.eagler.event.EventTarget;
/*     */ import me.eagler.event.events.PacketEvent;
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.Setting;
/*     */ import me.eagler.utils.TimeHelper;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OldInvCleaner
/*     */   extends Module
/*     */ {
/*  34 */   public TimeHelper time = new TimeHelper();
/*     */   
/*  36 */   public ArrayList<Integer> blacklist = new ArrayList<Integer>();
/*     */   
/*     */   public boolean finished = false;
/*     */   
/*     */   public int delay;
/*     */   
/*     */   public OldInvCleaner() {
/*  43 */     super("InvCleaner", Category.Player);
/*     */     
/*  45 */     this.delay = 0;
/*     */     
/*  47 */     ArrayList<String> options = new ArrayList<String>();
/*     */     
/*  49 */     options.add("Normal");
/*  50 */     options.add("OpenInv");
/*     */     
/*  52 */     this.settingManager.addSetting(new Setting("InvCleanerMode", this, "OpenInv", options));
/*  53 */     this.settingManager.addSetting(new Setting("InvCleanerDelay", this, 120.0D, 0.0D, 500.0D, true));
/*  54 */     this.settingManager.addSetting(new Setting("InvCleanerAAC", this, true));
/*  55 */     this.settingManager.addSetting(new Setting("InvSort", this, true));
/*     */   }
/*     */   
/*     */   public void onEnabled() {
/*  59 */     EventManager.register(this);
/*  60 */     this.blacklist.add(Integer.valueOf(367));
/*  61 */     this.blacklist.add(Integer.valueOf(287));
/*  62 */     this.blacklist.add(Integer.valueOf(349));
/*     */   }
/*     */   
/*     */   public void onDisabled() {
/*  66 */     EventManager.unregister(this);
/*     */   }
/*     */   
/*     */   @EventTarget
/*     */   public void onPacket(PacketEvent event) {
/*  71 */     Packet packet = event.getPacket();
/*  72 */     if (packet instanceof C03PacketPlayer && 
/*  73 */       this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) {
/*  74 */       C03PacketPlayer.pitch = 0.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/*  79 */     if (this.settingManager.getSettingByName("InvSort").getBoolean())
/*     */     {
/*  81 */       for (int i = 0; i < this.mc.thePlayer.inventory.getSizeInventory(); i++) {
/*     */         
/*  83 */         if (!isWeapon(this.mc.thePlayer.inventory.getStackInSlot(0)) && this.mc.thePlayer.inventory.getStackInSlot(0) != null)
/*     */         {
/*  85 */           this.mc.playerController.windowClick(0, 36, 0, 4, (EntityPlayer)this.mc.thePlayer);
/*     */         }
/*     */ 
/*     */         
/*  89 */         if (this.mc.thePlayer.inventory.getStackInSlot(i) != null) {
/*     */           
/*  91 */           if (isWeapon(this.mc.thePlayer.inventory.getStackInSlot(i)) && this.mc.thePlayer.inventory.getStackInSlot(0) == null) {
/*     */             
/*  93 */             this.mc.playerController.windowClick(0, i, 0, 1, (EntityPlayer)this.mc.thePlayer);
/*     */ 
/*     */             
/*  96 */             this.mc.playerController.updateController();
/*     */           } 
/*     */ 
/*     */           
/* 100 */           for (int o = 37; o < 45; o++) {
/*     */             
/* 102 */             if (isWeapon(this.mc.thePlayer.inventory.getStackInSlot(o - 36)) && this.mc.thePlayer.inventory.getStackInSlot(0) == null) {
/*     */               
/* 104 */               this.mc.playerController.windowClick(0, o, 0, 1, (EntityPlayer)this.mc.thePlayer);
/*     */ 
/*     */               
/* 107 */               this.mc.playerController.updateController();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (isEnabled()) {
/* 122 */       this.delay = (int)this.settingManager.getSettingByName("InvCleanerDelay").getValue();
/* 123 */       setExtraTag(this.delay);
/*     */       
/* 125 */       if (!this.settingManager.getSettingByName("InvCleanerMode").getMode().equalsIgnoreCase("Normal"))
/*     */       {
/* 127 */         if (!(this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)) {
/*     */           return;
/*     */         }
/*     */       }
/*     */       
/* 132 */       if (this.settingManager.getSettingByName("InvCleanerAAC").getBoolean()) {
/* 133 */         if (this.mc.thePlayer.moveForward >= 0.5F)
/*     */           return; 
/* 135 */         if (this.mc.thePlayer.moveStrafing >= 0.5F)
/*     */           return; 
/*     */       } 
/* 138 */       if (this.mc.thePlayer.isUsingItem())
/*     */         return; 
/* 140 */       if (this.time.hasReached(this.delay)) {
/* 141 */         CopyOnWriteArrayList<Integer> uselessItems = new CopyOnWriteArrayList<Integer>();
/* 142 */         for (int o = 0; o < 45; o++) {
/* 143 */           if (this.mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
/* 144 */             ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(o).getStack();
/* 145 */             if (this.mc.thePlayer.inventory.armorItemInSlot(0) != item && 
/* 146 */               this.mc.thePlayer.inventory.armorItemInSlot(1) != item && 
/* 147 */               this.mc.thePlayer.inventory.armorItemInSlot(2) != item && 
/* 148 */               this.mc.thePlayer.inventory.armorItemInSlot(3) != item && 
/* 149 */               item != null && item.getItem() != null && Item.getIdFromItem(item.getItem()) != 0 && 
/* 150 */               !stackIsUseful(o))
/* 151 */               uselessItems.add(Integer.valueOf(o)); 
/*     */           } 
/*     */         } 
/* 154 */         if (!uselessItems.isEmpty()) {
/* 155 */           this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, (
/* 156 */               (Integer)uselessItems.get(0)).intValue(), 1, 4, (EntityPlayer)this.mc.thePlayer);
/* 157 */           uselessItems.remove(0);
/* 158 */           this.time.reset();
/* 159 */         } else if (this.mc.thePlayer.inventoryContainer.getSlot(0).getHasStack() && 
/* 160 */           !(this.mc.thePlayer.inventoryContainer.getSlot(0).getStack()
/* 161 */           .getItem() instanceof net.minecraft.item.ItemSword)) {
/* 162 */           for (int i = 0; i < 45; i++) {
/* 163 */             if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && 
/* 164 */               this.mc.thePlayer.inventoryContainer.getSlot(i).getStack()
/* 165 */               .getItem() instanceof net.minecraft.item.ItemSword)
/* 166 */               this.mc.playerController.windowClick(0, i, 0, 2, (EntityPlayer)this.mc.thePlayer); 
/* 167 */             if (i == 45)
/* 168 */               i = 0; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean stackIsUseful(int i) {
/* 176 */     ItemStack itemStack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/* 177 */     boolean hasAlreadyOrBetter = false;
/* 178 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemSword || 
/* 179 */       itemStack.getItem() instanceof net.minecraft.item.ItemPickaxe || 
/* 180 */       itemStack.getItem() instanceof net.minecraft.item.ItemAxe) {
/* 181 */       for (int j = 0; j < 45; j++) {
/* 182 */         if (j != i && 
/* 183 */           this.mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
/* 184 */           ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(j).getStack();
/* 185 */           if ((item != null && item.getItem() instanceof net.minecraft.item.ItemSword) || 
/* 186 */             item.getItem() instanceof net.minecraft.item.ItemAxe || 
/* 187 */             item.getItem() instanceof net.minecraft.item.ItemPickaxe) {
/* 188 */             float damageFound = getItemDamage(itemStack);
/* 189 */             damageFound += EnchantmentHelper.func_152377_a(itemStack, EnumCreatureAttribute.UNDEFINED);
/* 190 */             float damageCurrent = getItemDamage(item);
/* 191 */             damageCurrent += EnchantmentHelper.func_152377_a(item, EnumCreatureAttribute.UNDEFINED);
/* 192 */             if (damageCurrent > damageFound) {
/* 193 */               hasAlreadyOrBetter = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 199 */     } else if (itemStack.getItem() instanceof net.minecraft.item.ItemArmor) {
/* 200 */       for (int j = 0; j < 45; j++) {
/* 201 */         if (i != j && 
/* 202 */           this.mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
/* 203 */           ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(j).getStack();
/* 204 */           if (item != null && item.getItem() instanceof net.minecraft.item.ItemArmor) {
/* 205 */             List<Integer> helmet = 
/* 206 */               Arrays.asList(new Integer[] { Integer.valueOf(298), Integer.valueOf(314), 
/* 207 */                   Integer.valueOf(302), Integer.valueOf(306), Integer.valueOf(310) });
/* 208 */             List<Integer> chestplate = 
/* 209 */               Arrays.asList(new Integer[] { Integer.valueOf(299), Integer.valueOf(315), 
/* 210 */                   Integer.valueOf(303), Integer.valueOf(307), Integer.valueOf(311) });
/* 211 */             List<Integer> leggings = 
/* 212 */               Arrays.asList(new Integer[] { Integer.valueOf(300), Integer.valueOf(316), 
/* 213 */                   Integer.valueOf(304), Integer.valueOf(308), Integer.valueOf(312) });
/* 214 */             List<Integer> boots = 
/* 215 */               Arrays.asList(new Integer[] { Integer.valueOf(301), Integer.valueOf(317), 
/* 216 */                   Integer.valueOf(305), Integer.valueOf(309), Integer.valueOf(313) });
/* 217 */             if (helmet.contains(Integer.valueOf(Item.getIdFromItem(item.getItem()))) && 
/* 218 */               helmet.contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem())))) {
/* 219 */               if (helmet.indexOf(Integer.valueOf(Item.getIdFromItem(itemStack.getItem()))) < helmet
/* 220 */                 .indexOf(Integer.valueOf(Item.getIdFromItem(item.getItem())))) {
/* 221 */                 hasAlreadyOrBetter = true;
/*     */                 break;
/*     */               } 
/* 224 */             } else if (chestplate.contains(Integer.valueOf(Item.getIdFromItem(item.getItem()))) && 
/* 225 */               chestplate.contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem())))) {
/* 226 */               if (chestplate
/* 227 */                 .indexOf(Integer.valueOf(Item.getIdFromItem(itemStack.getItem()))) < chestplate
/* 228 */                 .indexOf(Integer.valueOf(Item.getIdFromItem(item.getItem())))) {
/* 229 */                 hasAlreadyOrBetter = true;
/*     */                 break;
/*     */               } 
/* 232 */             } else if (leggings.contains(Integer.valueOf(Item.getIdFromItem(item.getItem()))) && 
/* 233 */               leggings.contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem())))) {
/* 234 */               if (leggings
/* 235 */                 .indexOf(Integer.valueOf(Item.getIdFromItem(itemStack.getItem()))) < leggings
/* 236 */                 .indexOf(Integer.valueOf(Item.getIdFromItem(item.getItem())))) {
/* 237 */                 hasAlreadyOrBetter = true;
/*     */                 break;
/*     */               } 
/* 240 */             } else if (boots.contains(Integer.valueOf(Item.getIdFromItem(item.getItem()))) && 
/* 241 */               boots.contains(Integer.valueOf(Item.getIdFromItem(itemStack.getItem()))) && 
/* 242 */               boots.indexOf(Integer.valueOf(Item.getIdFromItem(itemStack.getItem()))) < boots
/* 243 */               .indexOf(Integer.valueOf(Item.getIdFromItem(item.getItem())))) {
/* 244 */               hasAlreadyOrBetter = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 251 */     for (int o = 0; o < 45; o++) {
/* 252 */       if (i != o && 
/* 253 */         this.mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
/* 254 */         ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(o).getStack();
/* 255 */         if (item != null && (item.getItem() instanceof net.minecraft.item.ItemSword || 
/* 256 */           item.getItem() instanceof net.minecraft.item.ItemAxe || 
/* 257 */           item.getItem() instanceof net.minecraft.item.ItemBow || 
/* 258 */           item.getItem() instanceof net.minecraft.item.ItemFishingRod || 
/* 259 */           item.getItem() instanceof net.minecraft.item.ItemArmor || 
/* 260 */           item.getItem() instanceof net.minecraft.item.ItemAxe || 
/* 261 */           item.getItem() instanceof net.minecraft.item.ItemPickaxe || 
/* 262 */           Item.getIdFromItem(item.getItem()) == 346)) {
/* 263 */           Item found = item.getItem();
/* 264 */           if (Item.getIdFromItem(itemStack.getItem()) == Item.getIdFromItem(item.getItem())) {
/* 265 */             hasAlreadyOrBetter = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 271 */     if (Item.getIdFromItem(itemStack.getItem()) == 367)
/* 272 */       return false; 
/* 273 */     if (Item.getIdFromItem(itemStack.getItem()) == 30)
/* 274 */       return true; 
/* 275 */     if (Item.getIdFromItem(itemStack.getItem()) == 326)
/* 276 */       return true; 
/* 277 */     if (Item.getIdFromItem(itemStack.getItem()) == 259)
/* 278 */       return true; 
/* 279 */     if (Item.getIdFromItem(itemStack.getItem()) == 262)
/* 280 */       return true; 
/* 281 */     if (Item.getIdFromItem(itemStack.getItem()) == 264)
/* 282 */       return true; 
/* 283 */     if (Item.getIdFromItem(itemStack.getItem()) == 265)
/* 284 */       return true; 
/* 285 */     if (Item.getIdFromItem(itemStack.getItem()) == 346)
/* 286 */       return false; 
/* 287 */     if (Item.getIdFromItem(itemStack.getItem()) == 384)
/* 288 */       return true; 
/* 289 */     if (Item.getIdFromItem(itemStack.getItem()) == 345)
/* 290 */       return true; 
/* 291 */     if (Item.getIdFromItem(itemStack.getItem()) == 296)
/* 292 */       return true; 
/* 293 */     if (Item.getIdFromItem(itemStack.getItem()) == 336)
/* 294 */       return true; 
/* 295 */     if (Item.getIdFromItem(itemStack.getItem()) == 266)
/* 296 */       return true; 
/* 297 */     if (Item.getIdFromItem(itemStack.getItem()) == 280)
/* 298 */       return true; 
/* 299 */     if (itemStack.hasDisplayName())
/* 300 */       return true; 
/* 301 */     if (hasAlreadyOrBetter)
/* 302 */       return false; 
/* 303 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemArmor)
/* 304 */       return true; 
/* 305 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemAxe)
/* 306 */       return true; 
/* 307 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemBow)
/* 308 */       return true; 
/* 309 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemSword)
/* 310 */       return true; 
/* 311 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemPotion)
/* 312 */       return true; 
/* 313 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemFlintAndSteel)
/* 314 */       return true; 
/* 315 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemEnderPearl)
/* 316 */       return true; 
/* 317 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemBlock)
/* 318 */       return true; 
/* 319 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemFood)
/* 320 */       return true; 
/* 321 */     if (itemStack.getItem() instanceof net.minecraft.item.ItemPickaxe)
/* 322 */       return true; 
/* 323 */     return false;
/*     */   }
/*     */   
/*     */   private float getItemDamage(ItemStack itemStack) {
/* 327 */     Multimap multimap = itemStack.getAttributeModifiers();
/* 328 */     if (!multimap.isEmpty()) {
/* 329 */       Iterator<Map.Entry> iterator = multimap.entries().iterator();
/* 330 */       if (iterator.hasNext()) {
/*     */         double damage;
/* 332 */         Map.Entry entry = iterator.next();
/* 333 */         AttributeModifier attributeModifier = (AttributeModifier)entry.getValue();
/* 334 */         if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
/* 335 */           damage = attributeModifier.getAmount();
/*     */         } else {
/* 337 */           damage = attributeModifier.getAmount() * 100.0D;
/*     */         } 
/* 339 */         if (attributeModifier.getAmount() > 1.0D)
/* 340 */           return 1.0F + (float)damage; 
/* 341 */         return 1.0F;
/*     */       } 
/*     */     } 
/* 344 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isWeapon(ItemStack item) {
/* 349 */     if (item != null) {
/*     */       
/* 351 */       if (item.getItem() instanceof net.minecraft.item.ItemSword)
/*     */       {
/* 353 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 357 */       if (item.getItem() instanceof net.minecraft.item.ItemAxe)
/*     */       {
/* 359 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 363 */       if (item.getItem() instanceof net.minecraft.item.ItemPickaxe)
/*     */       {
/* 365 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 371 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\player\OldInvCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
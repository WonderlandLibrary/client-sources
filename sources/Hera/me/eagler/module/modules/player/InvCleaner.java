/*     */ package me.eagler.module.modules.player;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.Setting;
/*     */ import me.eagler.utils.PlayerUtils;
/*     */ import me.eagler.utils.TimeHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvCleaner
/*     */   extends Module
/*     */ {
/*  29 */   private ArrayList<Integer> blacklist = new ArrayList<Integer>();
/*  30 */   private HashMap<Integer, ItemStack> uselessItems = new HashMap<Integer, ItemStack>();
/*  31 */   private ArrayList<Integer> uItems = new ArrayList<Integer>();
/*     */   
/*  33 */   private TimeHelper time = new TimeHelper();
/*     */   
/*  35 */   private ItemStack bestStack = null;
/*     */   
/*  37 */   private int stackSlot = 0;
/*     */   private ItemStack bestHelmet; private ItemStack bestChestplate; private ItemStack bestLeggings; private ItemStack bestBoots; public void onEnable() {
/*     */     this.time.reset();
/*  40 */   } public InvCleaner() { super("InvCleaner", Category.Player);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     this.bestHelmet = null;
/* 236 */     this.bestChestplate = null;
/* 237 */     this.bestLeggings = null;
/* 238 */     this.bestBoots = null; this.blacklist.add(Integer.valueOf(367)); this.blacklist.add(Integer.valueOf(287)); this.blacklist.add(Integer.valueOf(349)); ArrayList<String> options = new ArrayList<String>(); options.add("Normal"); options.add("OpenInv"); options.add("AAC"); this.settingManager.addSetting(new Setting("ICMode", this, "OpenInv", options)); this.settingManager.addSetting(new Setting("InvCleanerDelay", this, 120.0D, 0.0D, 500.0D, true)); this.settingManager.addSetting(new Setting("InvSort", this, true)); }
/*     */   public void onUpdate() { String mode = this.settingManager.getSettingByName("ICMode").getMode(); double delay = this.settingManager.getSettingByName("InvCleanerDelay").getValue(); setExtraTag(delay); if (mode.equalsIgnoreCase("OpenInv")) if (!(this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)) return;   if (mode.equalsIgnoreCase("AAC")) { if (!(this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)) return;  if (PlayerUtils.playeriswalking())
/*     */         return;  }  for (int i = 0; i < this.uItems.size() - 1; i++) { if (this.uselessItems.containsKey(this.uItems.get(i))) { ItemStack is = this.uselessItems.get(this.uItems.get(i)); if (this.mc.thePlayer.inventoryContainer.getSlot(((Integer)this.uItems.get(i)).intValue()) != null)
/*     */           if (this.mc.thePlayer.inventoryContainer.getSlot(((Integer)this.uItems.get(i)).intValue()).getHasStack())
/* 242 */             if (this.mc.thePlayer.inventoryContainer.getSlot(((Integer)this.uItems.get(i)).intValue()).getStack() == this.uselessItems.get(this.uItems.get(i))) { if (this.time.hasReached((long)delay)) { this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, ((Integer)this.uItems.get(i)).intValue(), 1, 4, (EntityPlayer)this.mc.thePlayer); this.uselessItems.remove(this.uItems.get(i)); this.uItems.remove(i); this.time.reset(); }  } else { this.uselessItems.remove(this.uItems.get(i)); this.uItems.remove(i); }    }  }  bestWeapon(); checkBlacklist(); bestArmor(); } private void bestArmorType(String type, ItemStack bestType) { this.bestHelmet = null;
/* 243 */     this.bestChestplate = null;
/* 244 */     this.bestLeggings = null;
/* 245 */     this.bestBoots = null;
/*     */     
/* 247 */     int stackSlot = 0;
/*     */     
/* 249 */     for (int i = 0; i < this.mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) {
/*     */       
/* 251 */       if (this.mc.thePlayer.inventoryContainer.getSlot(i) != null)
/*     */       {
/* 253 */         if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
/*     */           
/* 255 */           ItemStack currentStack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/*     */           
/* 257 */           if (currentStack.getItem().getUnlocalizedName().toLowerCase().contains(type))
/*     */           {
/* 259 */             if (bestType != null)
/*     */             
/* 261 */             { if (currentStack.getItem().getMaxDamage() >= bestType.getItem().getMaxDamage())
/*     */               {
/* 263 */                 this.uselessItems.put(Integer.valueOf(stackSlot), bestType);
/* 264 */                 this.uItems.add(Integer.valueOf(stackSlot));
/*     */                 
/* 266 */                 bestType = currentStack;
/* 267 */                 stackSlot = i;
/*     */               }
/*     */               else
/*     */               {
/* 271 */                 this.uselessItems.put(Integer.valueOf(i), currentStack);
/* 272 */                 this.uItems.add(Integer.valueOf(i));
/*     */               }
/*     */                }
/*     */             
/*     */             else
/*     */             
/* 278 */             { bestType = currentStack;
/* 279 */               stackSlot = i; }  } 
/*     */         }  } 
/*     */     }  } private void bestWeapon() { this.stackSlot = 0; this.bestStack = null; for (int i = 0; i < this.mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) { if (this.mc.thePlayer.inventoryContainer.getSlot(i) != null)
/*     */         if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) { ItemStack currentStack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack(); if (currentStack.getItem() instanceof net.minecraft.item.ItemSword || currentStack.getItem() instanceof net.minecraft.item.ItemAxe || currentStack.getItem() instanceof net.minecraft.item.ItemPickaxe)
/*     */             if (this.bestStack != null) { if (currentStack.getAttackDamage() >= this.bestStack.getAttackDamage()) { this.uselessItems.put(Integer.valueOf(this.stackSlot), this.bestStack); this.uItems.add(Integer.valueOf(this.stackSlot)); this.bestStack = currentStack; this.stackSlot = i; }
/*     */               else { this.uselessItems.put(Integer.valueOf(i), currentStack); this.uItems.add(Integer.valueOf(i)); }
/*     */                }
/*     */             else { this.bestStack = currentStack; this.stackSlot = i; }
/*     */               }
/*     */           }
/*     */      }
/*     */   private void checkBlacklist() { for (int i = 0; i < this.mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) { if (this.mc.thePlayer.inventoryContainer.getSlot(i) != null)
/*     */         if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) { ItemStack itemStack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack(); int id = Item.getIdFromItem(itemStack.getItem()); if (this.blacklist.contains(Integer.valueOf(id))) { this.uselessItems.put(Integer.valueOf(i), itemStack); this.uItems.add(Integer.valueOf(i)); }
/*     */            }
/*     */           }
/*     */      }
/* 295 */   private void bestArmor() { bestArmorType("helmet", this.bestHelmet);
/* 296 */     bestArmorType("chestplate", this.bestChestplate);
/* 297 */     bestArmorType("leggings", this.bestLeggings);
/* 298 */     bestArmorType("boots", this.bestBoots); }
/*     */ 
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\player\InvCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
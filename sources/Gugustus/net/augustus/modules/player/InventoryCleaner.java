package net.augustus.modules.player;

import java.awt.Color;
import java.util.ArrayList;
import net.augustus.events.EventClick;
import net.augustus.events.EventEarlyTick;
import net.augustus.events.EventWorld;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.*;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Mouse;

public class InventoryCleaner extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   private final TimeHelper timeHelper2 = new TimeHelper();
   public StringValue mode = new StringValue(1, "Mode", this, "OpenInv", new String[]{"OpenInv", "SpoofInv", "Basic"});
   public BooleanValue noMove = new BooleanValue(5, "NoMove", this, false);
   public BooleanValue display = new BooleanValue(12315, "Display", this, false);
   public BooleanValue interactionCheck = new BooleanValue(6, "InteractCheck", this, false);
   public DoubleValue delay = new DoubleValue(2, "Delay", this, 100.0, 0.0, 1000.0, 0);
   public DoubleValue startDelay = new DoubleValue(3, "StartDelay", this, 100.0, 0.0, 1000.0, 0);
   public BooleanValue sort = new BooleanValue(4, "Sort", this, true);
   // blocks
   public BooleanValue sand = new BooleanValue(124, "Sand", this, false);
   public BooleanValue gravel = new BooleanValue(124, "Gravel", this, false);
   public BooleanValue soulSand = new BooleanValue(124, "SoulSand", this, false);
   public BooleanValue tnt = new BooleanValue(124, "TNT", this, false);
   public BooleanValue cobweb = new BooleanValue(124, "Cobweb", this, false);
   public BooleanValue rail = new BooleanValue(124, "Rails", this, false);
   public BooleansSetting haram = new BooleansSetting(12415, "BlacklistedBlocks", this, new Setting[]{sand, gravel, soulSand, tnt, cobweb, rail});
   // end blocks
   public BooleanValue onWorld = new BooleanValue(123, "DisableOnWorld", this, false);
   
   public int slot = -1;
   
   @EventTarget
   public void onWorld(EventWorld eventWorld) {
      if(onWorld.getBoolean()) {
         setToggled(false);
      }
   }
   private boolean blockInv;

   public InventoryCleaner() {
      super("InvCleaner", new Color(32, 94, 103), Categorys.PLAYER);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (mc.thePlayer != null) {
         mc.thePlayer.setServerInv(mc.currentScreen);
      }
      slot = -1;
   }

   @EventTarget
   public void onEventClick(EventClick eventClick) {
      this.blockInv = false;
      if (this.mode.getSelected().equals("SpoofInv")
         && this.interactionCheck.getBoolean()
         && (Mouse.isButtonDown(1) || Mouse.isButtonDown(0) || mm.killAura.isToggled() && mm.killAura.target != null || BlockUtil.isScaffoldToggled())) {
         this.closeInv();
         this.blockInv = true;
         this.timeHelper2.reset();
      }
   }

   @EventTarget
   public void onEventTick(EventEarlyTick eventEarlyTick) {
	   if(mm.arrayList.mode.getSelected().equalsIgnoreCase("Default")) {
	      this.setDisplayName(this.getName() + "  " + this.mode.getSelected());
	 }else {
		 this.setDisplayName(this.getName() + this.mode.getSelected());
	 }
      ArrayList<ItemStack> swords = new ArrayList<>();
      ArrayList<ItemStack> bows = new ArrayList<>();
      ArrayList<ItemStack> blocks = new ArrayList<>();
      ArrayList<ItemStack> rods = new ArrayList<>();
      ArrayList<ItemStack> foods = new ArrayList<>();
      ArrayList<ItemStack> gapples = new ArrayList<>();
      ArrayList<ItemStack> potions = new ArrayList<>();
      ArrayList<ItemStack> axes = new ArrayList<>();
      ArrayList<ItemStack> pickAxes = new ArrayList<>();
      ArrayList<ItemStack> shovels = new ArrayList<>();
      ArrayList<Integer> allToKeep = new ArrayList<>();
      int swordID = 0;
      int bowID = 1;
      int rodID = 2;
      int blockID = 4;
      int foodID = 7;
      int gappleID = 8;
      ItemStack sword = null;
      ItemStack bow = null;
      ItemStack rod = null;
      ItemStack food = null;
      ItemStack gapple = null;
      ItemStack axe = null;
      ItemStack block = null;
      ItemStack pickAxe = null;
      ItemStack shovel = null;
      ItemStack newSword = null;
      ItemStack newBow = null;
      ItemStack newRod = null;
      ItemStack newFood = null;
      ItemStack newGapple = null;
      ItemStack newAxe = null;
      ItemStack newPickAxe = null;
      ItemStack newShovel = null;
      ItemStack newBlock = null;
      long delay = (long)(this.delay.getValue() + (double)RandomUtil.nextLong(0L, 60L));
      long startDelay = (long)(this.startDelay.getValue() + (double)RandomUtil.nextLong(-35L, 35L));
      boolean invCleaner = mc.currentScreen instanceof GuiInventory
         || !this.mode.getSelected().equalsIgnoreCase("OpenInv")
            && (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory)
            && (!this.noMove.getBoolean() || this.noMove.getBoolean() && !MoveUtil.isMoving());
      if (invCleaner && !this.blockInv) {
         for(int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null) {
               if (itemStack.getItem() instanceof ItemSword) {
                  itemStack.setSlotID(i);
                  swords.add(itemStack);
                  if (i == swordID + 36) {
                     itemStack.setSlotID(i);
                     sword = itemStack;
                  }
               } else if (itemStack.getItem() instanceof ItemAxe) {
                  itemStack.setSlotID(i);
                  axes.add(itemStack);
                  if (i == swordID + 36) {
                     itemStack.setSlotID(i);
                     axe = itemStack;
                  }
               } else if (itemStack.getItem() instanceof ItemPickaxe) {
                  itemStack.setSlotID(i);
                  pickAxes.add(itemStack);
               } else if (itemStack.getItem() instanceof ItemSpade) {
                  itemStack.setSlotID(i);
                  shovels.add(itemStack);
               } else if (itemStack.getItem() instanceof ItemBlock) {
                  if (this.itemsToDrop(itemStack)) {
                     itemStack.setSlotID(i);
                     allToKeep.add(i);
                     blocks.add(itemStack);
                     if (i == blockID + 36) {
                        itemStack.setSlotID(i);
                        block = itemStack;
                     }
                  }
               } else if (itemStack.getItem() instanceof ItemBow) {
                  itemStack.setSlotID(i);
                  bows.add(itemStack);
                  if (i == bowID + 36) {
                     itemStack.setSlotID(i);
                     bow = itemStack;
                  }
               } else if (itemStack.getItem() instanceof ItemFishingRod) {
                  itemStack.setSlotID(i);
                  rods.add(itemStack);
                  if (i == rodID + 36) {
                     itemStack.setSlotID(i);
                     rod = itemStack;
                  }
               } else if (itemStack.getItem() instanceof ItemFood) {
                  if (itemStack.getItem() == Item.getByNameOrId("golden_apple")) {
                     itemStack.setSlotID(i);
                     gapples.add(itemStack);
                     allToKeep.add(i);
                     if (i == gappleID + 36) {
                        itemStack.setSlotID(i);
                        gapple = itemStack;
                     }
                  } else if (this.itemsToDrop(itemStack)) {
                     itemStack.setSlotID(i);
                     foods.add(itemStack);
                     allToKeep.add(i);
                     if (i == foodID + 36) {
                        itemStack.setSlotID(i);
                        food = itemStack;
                     }
                  }
               } else if (itemStack.getItem() instanceof ItemPotion) {
                  ItemPotion itemPotion = (ItemPotion)itemStack.getItem();
                  if (!itemPotion.getEffects(itemStack).isEmpty()) {
                     PotionEffect potionEffect = itemPotion.getEffects(itemStack).get(0);
                     Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                     if (!potion.isBadEffect()) {
                        itemStack.setSlotID(i);
                        potions.add(itemStack);
                        allToKeep.add(i);
                     }
                  }
               } else if (itemStack.getItem() instanceof ItemTool) {
                  if (this.itemsToDrop(itemStack)) {
                     itemStack.setSlotID(i);
                     allToKeep.add(i);
                  }
               } else if (this.itemsToKeep(itemStack)) {
                  itemStack.setSlotID(i);
                  allToKeep.add(i);
               } else if (!(itemStack.getItem() instanceof ItemArmor) && this.itemsToDrop(itemStack)) {
                  itemStack.setSlotID(i);
                  allToKeep.add(i);
               }
            }
         }

         for(ItemStack itemStack : blocks) {
            if (itemStack.getSlotID() != blockID + 36) {
               if (block == null) {
                  if (newBlock != null) {
                     if (itemStack.stackSize > newBlock.stackSize) {
                        newBlock = itemStack;
                     }
                  } else {
                     newBlock = itemStack;
                  }
               } else if (block.getItem() instanceof ItemBlock) {
                  if (newBlock != null) {
                     if (itemStack.stackSize > newBlock.stackSize) {
                        newBlock = itemStack;
                     }
                  } else if (itemStack.stackSize > block.stackSize) {
                     newBlock = itemStack;
                  }
               }
            }
         }

         for(ItemStack itemStack : swords) {
            if (itemStack.getSlotID() != swordID + 36) {
               if (sword == null) {
                  if (newSword != null) {
                     if (this.getDamageSword(itemStack) > this.getDamageSword(newSword)) {
                        newSword = itemStack;
                     } else if (this.getDamageSword(itemStack) == this.getDamageSword(newSword) && itemStack.getItemDamage() < newSword.getItemDamage()) {
                        newSword = itemStack;
                     }
                  } else {
                     newSword = itemStack;
                  }
               } else if (sword.getItem() instanceof ItemSword) {
                  if (newSword != null) {
                     if (this.getDamageSword(itemStack) > this.getDamageSword(newSword)) {
                        newSword = itemStack;
                     } else if (this.getDamageSword(itemStack) == this.getDamageSword(newSword) && itemStack.getItemDamage() < newSword.getItemDamage()) {
                        newSword = itemStack;
                     }
                  } else if (this.getDamageSword(itemStack) > this.getDamageSword(sword)) {
                     newSword = itemStack;
                  } else if (this.getDamageSword(itemStack) == this.getDamageSword(sword) && itemStack.getItemDamage() < sword.getItemDamage()) {
                     newSword = itemStack;
                  }
               }
            }
         }

         for(ItemStack itemStack : axes) {
            if (axe == null) {
               if (newAxe != null) {
                  if (this.getToolDamage(itemStack) > this.getToolDamage(newAxe)) {
                     newAxe = itemStack;
                  } else if (this.getToolDamage(itemStack) == this.getToolDamage(newAxe) && itemStack.getItemDamage() < newAxe.getItemDamage()) {
                     newAxe = itemStack;
                  }
               } else {
                  newAxe = itemStack;
               }
            } else if (axe.getItem() instanceof ItemAxe) {
               if (newAxe != null) {
                  if (this.getToolDamage(itemStack) > this.getToolDamage(newAxe)) {
                     newAxe = itemStack;
                  } else if (this.getToolDamage(itemStack) == this.getToolDamage(newAxe) && itemStack.getItemDamage() < newAxe.getItemDamage()) {
                     newAxe = itemStack;
                  }
               } else if (this.getToolDamage(itemStack) > this.getToolDamage(axe)) {
                  newAxe = itemStack;
               } else if (this.getToolDamage(itemStack) == this.getToolDamage(axe) && itemStack.getItemDamage() < axe.getItemDamage()) {
                  newAxe = itemStack;
               }
            }
         }

         for(ItemStack itemStack : pickAxes) {
            if (newPickAxe != null) {
               if (this.getToolSpeed(itemStack) > this.getToolSpeed(newPickAxe)) {
                  newPickAxe = itemStack;
               } else if (this.getToolSpeed(itemStack) == this.getToolSpeed(newPickAxe) && itemStack.getItemDamage() < newPickAxe.getItemDamage()) {
                  newPickAxe = itemStack;
               }
            } else {
               newPickAxe = itemStack;
            }
         }

         for(ItemStack itemStack : shovels) {
            if (newShovel != null) {
               if (this.getToolSpeed(itemStack) > this.getToolSpeed(newShovel)) {
                  newShovel = itemStack;
               } else if (this.getToolSpeed(itemStack) == this.getToolSpeed(newShovel) && itemStack.getItemDamage() < newShovel.getItemDamage()) {
                  newShovel = itemStack;
               }
            } else {
               newShovel = itemStack;
            }
         }

         for(ItemStack itemStack : bows) {
            if (itemStack.getSlotID() != bowID + 36) {
            }

            if (bow == null) {
               if (newBow != null) {
                  if (itemStack.getItemDamage() < newBow.getItemDamage()) {
                     newBow = itemStack;
                  }
               } else {
                  newBow = itemStack;
               }
            } else if (bow.getItem() instanceof ItemBow) {
               if (newBow != null) {
                  if (itemStack.getItemDamage() < newBow.getItemDamage()) {
                     newBow = itemStack;
                  }
               } else if (itemStack.getItemDamage() < bow.getItemDamage()) {
                  newBow = itemStack;
               }
            }
         }

         for(ItemStack itemStack : rods) {
            if (itemStack.getSlotID() != rodID + 36) {
               if (rod == null) {
                  if (newRod != null) {
                     if (itemStack.getItemDamage() < newRod.getItemDamage()) {
                        newRod = itemStack;
                     }
                  } else {
                     newRod = itemStack;
                  }
               } else if (rod.getItem() instanceof ItemFishingRod) {
                  if (newRod != null) {
                     if (itemStack.getItemDamage() < newRod.getItemDamage()) {
                        newRod = itemStack;
                     }
                  } else if (itemStack.getItemDamage() < rod.getItemDamage()) {
                     newRod = itemStack;
                  }
               }
            }
         }

         for(ItemStack itemStack : foods) {
            if (itemStack.getSlotID() != foodID + 36) {
               if (food == null) {
                  if (newFood != null) {
                     if (itemStack.stackSize > newFood.stackSize) {
                        newFood = itemStack;
                     }
                  } else {
                     newFood = itemStack;
                  }
               } else if (food.getItem() instanceof ItemFood) {
                  if (newFood != null) {
                     if (itemStack.stackSize > newFood.stackSize) {
                        newFood = itemStack;
                     }
                  } else if (itemStack.stackSize > food.stackSize) {
                     newFood = itemStack;
                  }
               }
            }
         }

         for(ItemStack itemStack : gapples) {
            if (itemStack.getSlotID() != gappleID + 36) {
               if (gapple == null) {
                  if (newGapple != null) {
                     if (itemStack.stackSize > newGapple.stackSize) {
                        newGapple = itemStack;
                     }
                  } else {
                     newGapple = itemStack;
                  }
               } else if (gapple.getItem() instanceof ItemFood) {
                  if (newGapple != null) {
                     if (itemStack.stackSize > newGapple.stackSize) {
                        newGapple = itemStack;
                     }
                  } else if (itemStack.stackSize > gapple.stackSize) {
                     newGapple = itemStack;
                  }
               }
            }
         }

         if (newSword != null) {
            allToKeep.add(newSword.getSlotID());
         }

         if (sword != null) {
            allToKeep.add(sword.getSlotID());
         }

         if (newBow != null) {
            allToKeep.add(newBow.getSlotID());
         }

         if (bow != null) {
            allToKeep.add(bow.getSlotID());
         }

         if (newRod != null) {
            allToKeep.add(newRod.getSlotID());
         }

         if (newBlock != null) {
            allToKeep.add(newBlock.getSlotID());
         }

         if (rod != null) {
            allToKeep.add(rod.getSlotID());
         }

         if (newFood != null) {
            allToKeep.add(newFood.getSlotID());
         }

         if (food != null) {
            allToKeep.add(food.getSlotID());
         }

         if (newGapple != null) {
            allToKeep.add(newGapple.getSlotID());
         }

         if (gapple != null) {
            allToKeep.add(gapple.getSlotID());
         }

         if (axe != null) {
            allToKeep.add(axe.getSlotID());
         }

         if (newAxe != null) {
            allToKeep.add(newAxe.getSlotID());
         }

         if (newPickAxe != null) {
            allToKeep.add(newPickAxe.getSlotID());
         }

         if (newShovel != null) {
            allToKeep.add(newShovel.getSlotID());
         }

         if (this.timeHelper2.reached(startDelay)) {
            if (this.timeHelper.reached(delay) && this.sort.getBoolean()) {
               if (newSword != null) {
                  this.switchItems(newSword, swordID);
                  this.timeHelper.reset();
                  if (this.delay.getValue() != 0.0) {
                     return;
                  }
               }

               if (sword == null && axe == null && newAxe != null) {
                  this.switchItems(newAxe, swordID);
                  this.timeHelper.reset();
                  if (this.delay.getValue() != 0.0) {
                     return;
                  }
               }

               if (newBow != null) {
                  this.switchItems(newBow, bowID);
                  this.timeHelper.reset();
                  if (this.delay.getValue() != 0.0) {
                     return;
                  }
               }

               if (newRod != null) {
                  this.switchItems(newRod, rodID);
                  this.timeHelper.reset();
                  if (this.delay.getValue() != 0.0) {
                     return;
                  }
               }

               if (newFood != null) {
                  this.switchItems(newFood, foodID);
                  this.timeHelper.reset();
                  if (this.delay.getValue() != 0.0) {
                     return;
                  }
               }

               if (newGapple != null) {
                  this.switchItems(newGapple, gappleID);
                  this.timeHelper.reset();
                  if (this.delay.getValue() != 0.0) {
                     return;
                  }
               }
               if (newBlock != null) {
                  this.switchItems(newBlock, blockID);
                  this.timeHelper.reset();
                  if (this.delay.getValue() != 0.0) {
                     return;
                  }
               }
            }

            if (mm.autoArmor.isToggled()) {
               mm.autoArmor.newAutoArmor(false);
            }

            if (this.timeHelper.reached(delay) && mm.autoArmor.isInvManager()) {
               for(int i = 9; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
                  ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                  if (itemStack != null) {
                     boolean notDrop = false;

                     for (int slot : allToKeep) {
                        if (i == slot) {
                           notDrop = true;
                           break;
                        }
                     }

                     if (!notDrop) {
                        this.dropItems(i);
                        this.timeHelper.reset();
                        if (this.delay.getValue() != 0.0) {
                           return;
                        }
                     }
                  }
               }
            }
         }
      } else {
         this.timeHelper2.reset();
      }

      this.closeInv();
   }

   private void closeInv() {
      if (mc.thePlayer.getServerInv() != null && this.mode.getSelected().equals("SpoofInv") && mc.currentScreen == null) {
         mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
         mc.thePlayer.setServerInv(null);
         System.out.println("CloseInv");
      }
   }

   private void openInv() {
      if (this.mode.getSelected().equals("SpoofInv") && mc.thePlayer.getServerInv() == null && mc.currentScreen == null) {
         mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
         mc.thePlayer.setServerInv(new GuiInventory(mc.thePlayer));
         System.out.println("OpenInv");
      }
   }

   private boolean itemsToKeep(ItemStack itemStack) {
      Item i = itemStack.getItem();
      return i.equals(Item.getItemById(288)) || i.equals(Item.getItemById(289));
   }

   private boolean itemsToDrop(ItemStack itemStack) {
	  this.slot = itemStack.getSlotID();
      Item i = itemStack.getItem();

      if(gravel.getBoolean()) {
         return i.equals(Item.getItemFromBlock(Blocks.gravel));
      }
      if(sand.getBoolean()) {
         return !i.equals(Item.getItemFromBlock(Blocks.sand));
      }
      if(soulSand.getBoolean()) {
         return !i.equals(Item.getItemFromBlock(Blocks.soul_sand));
      }
      if(tnt.getBoolean()) {
         return i.equals(Item.getItemFromBlock(Blocks.tnt));
      }
      if(cobweb.getBoolean()) {
         return i.equals(Item.getItemFromBlock(Blocks.web));
      }
      if(rail.getBoolean()) {
         return !(i.equals(Item.getItemFromBlock(Blocks.rail)) || i.equals(Item.getItemFromBlock(Blocks.golden_rail)) || i.equals(Item.getItemFromBlock(Blocks.activator_rail)) || i.equals(Item.getItemFromBlock(Blocks.detector_rail)));
      }

      for(int c = 183; c < 192; ++c) {
         if (i.equals(Item.getItemById(c))) {
            return false;
         }
      }

      for(int var4 = 290; var4 < 295; ++var4) {
         if (i.equals(Item.getItemById(var4))) {
            return false;
         }
      }

      if (!i.equals(Item.getItemById(39)) && !i.equals(Item.getItemById(40))) {
         return !i.equals(Item.getItemById(288))
            && !i.equals(Item.getItemById(289))
            && !i.equals(Item.getItemById(289))
            && !i.equals(Item.getByNameOrId("tallgrass"))
            && !i.equals(Item.getByNameOrId("deadbush"))
            && !i.equals(Item.getByNameOrId("red_flower"))
            && !i.equals(Item.getItemById(53))
            && !i.equals(Item.getItemById(65))
            && !i.equals(Item.getItemById(66))
            && !i.equals(Item.getItemById(67))
            && !i.equals(Item.getItemById(70))
            && !i.equals(Item.getItemById(72))
            && !i.equals(Item.getItemById(77))
            && !i.equals(Item.getItemById(85))
            && !i.equals(Item.getItemById(81))
            && !i.equals(Item.getItemById(96))
            && !i.equals(Item.getItemById(101))
            && !i.equals(Item.getItemById(102))
            && !i.equals(Item.getItemById(106))
            && !i.equals(Item.getItemById(107))
            && !i.equals(Item.getItemById(108))
            && !i.equals(Item.getItemById(109))
            && !i.equals(Item.getItemById(111))
            && !i.equals(Item.getItemById(113))
            && !i.equals(Item.getItemById(114))
            && !i.equals(Item.getItemById(128))
            && !i.equals(Item.getItemById(131))
            && !i.equals(Item.getItemById(134))
            && !i.equals(Item.getItemById(135))
            && !i.equals(Item.getItemById(136))
            && !i.equals(Item.getItemById(143))
            && !i.equals(Item.getItemById(136))
            && !i.equals(Item.getItemById(147))
            && !i.equals(Item.getItemById(148))
            && !i.equals(Item.getItemById(151))
            && !i.equals(Item.getItemById(154))
            && !i.equals(Item.getItemById(156))
            && !i.equals(Item.getItemById(157))
            && !i.equals(Item.getItemById(163))
            && !i.equals(Item.getItemById(164))
            && !i.equals(Item.getItemById(167))
            && !i.equals(Item.getItemById(180))
            && !i.equals(Item.getItemById(287))
            && !i.equals(Item.getItemById(287))
            && !i.equals(Item.getItemById(318))
            && !i.equals(Item.getItemById(321))
            && !i.equals(Item.getItemById(337))
            && !i.equals(Item.getItemById(338))
            && !i.equals(Item.getItemById(348))
            && !i.equals(Item.getItemById(352))
            && !i.equals(Item.getItemById(353))
            && !i.equals(Item.getItemById(354))
            && !i.equals(Item.getItemById(356))
            && !i.equals(Item.getItemById(361))
            && !i.equals(Item.getItemById(362))
            && !i.equals(Item.getItemById(367))
            && !i.equals(Item.getItemById(370))
            && !i.equals(Item.getItemById(371))
            && !i.equals(Item.getItemById(372))
            && !i.equals(Item.getItemById(373))
            && !i.equals(Item.getItemById(334))
            && !i.equals(Item.getByNameOrId("stone_slab"))
            && !i.equals(Item.getByNameOrId("snow_layer"))
            && !i.equals(Item.getByNameOrId("wooden_slab"))
            && !i.equals(Item.getByNameOrId("cobblestone_wall"))
            && !i.equals(Item.getByNameOrId("anvil"))
            && !i.equals(Item.getByNameOrId("stained_glass_pane"))
            && !i.equals(Item.getByNameOrId("carpet"))
            && !i.equals(Item.getByNameOrId("double_plant"))
            && !i.equals(Item.getByNameOrId("stone_slab2"))
            && !i.equals(Item.getByNameOrId("sapling"));
      } else {
         return mm.autoSoup.isToggled();
      }
   }

   private void switchItems(ItemStack itemStack, int hotBarSlot) {
	this.slot = itemStack.getSlotID();
      this.openInv();
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, itemStack.getSlotID(), hotBarSlot, 2, mc.thePlayer);
   }

   private void dropItems(int slotID) {
		  this.slot = slotID;
      this.openInv();
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotID, 1, 4, mc.thePlayer);
   }

   private double getDamageSword(ItemStack itemStack) {
      double damage = 0.0;
      if (itemStack.getItem() instanceof ItemSword) {
         damage += (double)(
            ((ItemSword)itemStack.getItem()).getAttackDamage()
               + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25F
         );
         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) / 11.0;
         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemStack) / 11.0;
         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 11.0;
      }

      return damage;
   }

   private double getToolDamage(ItemStack itemStack) {
      double damage = 0.0;
      if (itemStack.getItem() instanceof ItemAxe) {
         damage += (double)itemStack.getItem().getStrVsBlock(itemStack, new Block(Material.wood, MapColor.woodColor))
            + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack) / 11.0;
      } else if (itemStack.getItem() instanceof ItemPickaxe) {
         damage += (double)itemStack.getItem().getStrVsBlock(itemStack, new Block(Material.rock, MapColor.stoneColor))
            + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack) / 11.0;
      } else if (itemStack.getItem() instanceof ItemSpade) {
         damage += (double)itemStack.getItem().getStrVsBlock(itemStack, new Block(Material.sand, MapColor.sandColor))
            + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack) / 11.0;
      }

      damage += (double)(
         (float)itemStack.getItem().getMaxDamage() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25F
      );
      damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) / 11.0;
      damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemStack) / 11.0;
      return damage + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 11.0;
   }

   private double getToolSpeed(ItemStack itemStack) {
      double damage = 0.0;
      if (itemStack.getItem() instanceof ItemTool) {
         if (itemStack.getItem() instanceof ItemAxe) {
            damage += (double)(
               itemStack.getItem().getStrVsBlock(itemStack, new Block(Material.wood, MapColor.woodColor))
                  + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack)
            );
         } else if (itemStack.getItem() instanceof ItemPickaxe) {
            damage += (double)(
               itemStack.getItem().getStrVsBlock(itemStack, new Block(Material.rock, MapColor.stoneColor))
                  + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack)
            );
         } else if (itemStack.getItem() instanceof ItemSpade) {
            damage += (double)(
               itemStack.getItem().getStrVsBlock(itemStack, new Block(Material.sand, MapColor.sandColor))
                  + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack)
            );
         }

         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 11.0;
         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack) / 11.0;
         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) / 33.0;
      }

      return damage;
   }
}

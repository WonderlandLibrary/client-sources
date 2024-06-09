package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.module.impl.combat.Killaura;
import exhibition.util.NetUtil;
import exhibition.util.RotationUtils;
import java.util.Iterator;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class ChestStealer extends Module {
   private String DELAY = "DELAY";
   private String DROP = "DROP";
   private String CLOSE = "CLOSE";
   private String CHESTAURA = "CHESTAURA";
   private String IGNORE = "IGNORE";
   private exhibition.util.Timer timer = new exhibition.util.Timer();
   private exhibition.util.Timer stealTimer = new exhibition.util.Timer();
   private boolean isStealing;

   public ChestStealer(ModuleData data) {
      super(data);
      this.settings.put(this.DELAY, new Setting(this.DELAY, Integer.valueOf(2), "Tick delay before grabbing next item.", 1.0D, 1.0D, 5.0D));
      this.settings.put(this.DROP, new Setting(this.DROP, false, "Auto drop items."));
      this.settings.put(this.CLOSE, new Setting(this.CLOSE, true, "Auto closes chests when done."));
      this.settings.put(this.CHESTAURA, new Setting(this.CHESTAURA, false, "Auto opens chests near you."));
      this.settings.put(this.IGNORE, new Setting(this.IGNORE, true, "Ignores trash items for minigames."));
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre() && ((Boolean)((Setting)this.settings.get(this.CHESTAURA)).getValue()).booleanValue()) {
            if (this.stealTimer.delay(2000.0F) && this.isStealing) {
               this.stealTimer.reset();
               this.isStealing = false;
            }

            Iterator var11 = mc.theWorld.loadedTileEntityList.iterator();

            while(var11.hasNext()) {
               Object o = var11.next();
               if (o instanceof TileEntityChest) {
                  TileEntityChest chest = (TileEntityChest)o;
                  float x = (float)chest.getPos().getX();
                  float y = (float)chest.getPos().getY();
                  float z = (float)chest.getPos().getZ();
                  if (Killaura.loaded.isEmpty() && chest.lidAngle < 1.0F && Math.abs(RotationUtils.getYawChange((double)x + 0.5D, (double)z + 0.5D)) < 15.0F && mc.theWorld.rayTraceBlocks(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ), new Vec3((double)x + 0.5D, (double)(y + 1.0F), (double)z + 0.5D)) == null && !this.isStealing && !chest.isEmpty && mc.thePlayer.getDistance((double)x, (double)y, (double)z) < 4.0D && this.stealTimer.delay(1000.0F) && mc.currentScreen == null) {
                     this.isStealing = true;
                     NetUtil.sendPacketNoEvents(new C08PacketPlayerBlockPlacement(chest.getPos(), this.getFacingDirection(chest.getPos()).getIndex(), mc.thePlayer.getCurrentEquippedItem(), x, y, z));
                     chest.isEmpty = true;
                     this.stealTimer.reset();
                     float[] rot = RotationUtils.getRotationFromPosition((double)x, (double)y + 0.5D, (double)z);
                     em.setYaw(mc.thePlayer.rotationYaw + RotationUtils.getYawChange((double)x + 0.5D, (double)z + 0.5D));
                     em.setPitch(rot[1]);
                     break;
                  }
               }
            }
         } else if (mc.currentScreen instanceof GuiChest) {
            GuiChest guiChest = (GuiChest)mc.currentScreen;
            String name = guiChest.lowerChestInventory.getDisplayName().getUnformattedText().toLowerCase();
            if (((Boolean)((Setting)this.settings.get(this.IGNORE)).getValue()).booleanValue() && !name.contains("chest")) {
               return;
            }

            this.isStealing = true;
            boolean full = true;
            ItemStack[] arrayOfItemStack = mc.thePlayer.inventory.mainInventory;
            int j = mc.thePlayer.inventory.mainInventory.length;

            for(int i = 0; i < j; ++i) {
               ItemStack item = arrayOfItemStack[i];
               if (item == null) {
                  full = false;
                  break;
               }
            }

            boolean containsItems = false;
            if (!full) {
               ItemStack stack;
               int index;
               for(index = 0; index < guiChest.lowerChestInventory.getSizeInventory(); ++index) {
                  stack = guiChest.lowerChestInventory.getStackInSlot(index);
                  if (stack != null && !this.isBad(stack)) {
                     containsItems = true;
                     break;
                  }
               }

               if (containsItems) {
                  for(index = 0; index < guiChest.lowerChestInventory.getSizeInventory(); ++index) {
                     stack = guiChest.lowerChestInventory.getStackInSlot(index);
                     if (stack != null && this.timer.delay((float)(50 * ((Number)((Setting)this.settings.get(this.DELAY)).getValue()).intValue())) && !this.isBad(stack)) {
                        mc.playerController.windowClick(guiChest.inventorySlots.windowId, index, 0, ((Boolean)((Setting)this.settings.get(this.DROP)).getValue()).booleanValue() ? 0 : 1, mc.thePlayer);
                        if (((Boolean)((Setting)this.settings.get(this.DROP)).getValue()).booleanValue()) {
                           mc.playerController.windowClick(guiChest.inventorySlots.windowId, -999, 0, 0, mc.thePlayer);
                        }

                        this.timer.reset();
                     }
                  }
               } else if (((Boolean)((Setting)this.settings.get(this.CLOSE)).getValue()).booleanValue()) {
                  mc.thePlayer.closeScreen();
                  this.isStealing = false;
               }
            } else if (((Boolean)((Setting)this.settings.get(this.CLOSE)).getValue()).booleanValue()) {
               mc.thePlayer.closeScreen();
               this.isStealing = false;
            }
         } else {
            this.isStealing = false;
         }
      }

   }

   private EnumFacing getFacingDirection(BlockPos pos) {
      EnumFacing direction = null;
      if (!mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isSolidFullCube()) {
         direction = EnumFacing.UP;
      }

      MovingObjectPosition rayResult = mc.theWorld.rayTraceBlocks(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ), new Vec3((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D));
      return rayResult != null ? rayResult.facing : direction;
   }

   private boolean isBad(ItemStack item) {
      if (item != null && item.getItem() instanceof ItemSword) {
         return this.getDamage(item) <= this.bestDamage();
      } else if (item != null && item.getItem() instanceof ItemArmor && !this.canEquip(item) && (!this.betterCheck(item) || this.canEquip(item))) {
         return true;
      } else {
         return item != null && !(item.getItem() instanceof ItemArmor) && (item.getItem().getUnlocalizedName().contains("tnt") || item.getItem().getUnlocalizedName().contains("stick") || item.getItem().getUnlocalizedName().contains("egg") || item.getItem().getUnlocalizedName().contains("string") || item.getItem().getUnlocalizedName().contains("flint") || item.getItem().getUnlocalizedName().contains("compass") || item.getItem().getUnlocalizedName().contains("feather") || item.getItem().getUnlocalizedName().contains("bucket") || item.getItem().getUnlocalizedName().contains("chest") && !item.getDisplayName().toLowerCase().contains("collect") || item.getItem().getUnlocalizedName().contains("snow") || item.getItem().getUnlocalizedName().contains("fish") || item.getItem().getUnlocalizedName().contains("enchant") || item.getItem().getUnlocalizedName().contains("exp") || item.getItem().getUnlocalizedName().contains("shears") || item.getItem().getUnlocalizedName().contains("anvil") || item.getItem().getUnlocalizedName().contains("torch") || item.getItem().getUnlocalizedName().contains("seeds") || item.getItem().getUnlocalizedName().contains("leather") || item.getItem() instanceof ItemPickaxe || item.getItem() instanceof ItemGlassBottle || item.getItem() instanceof ItemTool || item.getItem().getUnlocalizedName().contains("piston") || item.getItem().getUnlocalizedName().contains("potion") && this.isBadPotion(item));
      }
   }

   private double getProtectionValue(ItemStack stack) {
      return stack.getItem() instanceof ItemArmor ? (double)((ItemArmor)stack.getItem()).damageReduceAmount + (double)((100 - ((ItemArmor)stack.getItem()).damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D : 0.0D;
   }

   private boolean betterCheck(ItemStack stack) {
      try {
         if (stack.getItem() instanceof ItemArmor) {
            if (mc.thePlayer.getEquipmentInSlot(1) != null && stack.getUnlocalizedName().contains("boots")) {
               assert mc.thePlayer.getEquipmentInSlot(1).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(1)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(1).getItem()).damageReduceAmount) {
                  return true;
               }
            }

            if (mc.thePlayer.getEquipmentInSlot(2) != null && stack.getUnlocalizedName().contains("leggings")) {
               assert mc.thePlayer.getEquipmentInSlot(2).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(2)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(2).getItem()).damageReduceAmount) {
                  return true;
               }
            }

            if (mc.thePlayer.getEquipmentInSlot(3) != null && stack.getUnlocalizedName().contains("chestplate")) {
               assert mc.thePlayer.getEquipmentInSlot(3).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(3)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(3).getItem()).damageReduceAmount) {
                  return true;
               }
            }

            if (mc.thePlayer.getEquipmentInSlot(4) != null && stack.getUnlocalizedName().contains("helmet")) {
               assert mc.thePlayer.getEquipmentInSlot(4).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(4)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(4).getItem()).damageReduceAmount) {
                  return true;
               }
            }
         }

         return false;
      } catch (Exception var3) {
         return false;
      }
   }

   private float bestDamage() {
      float bestDamage = 0.0F;

      for(int i = 0; i < 45; ++i) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemSword && this.getDamage(is) > bestDamage) {
               bestDamage = this.getDamage(is);
            }
         }
      }

      return bestDamage;
   }

   private boolean canEquip(ItemStack stack) {
      assert stack.getItem() instanceof ItemArmor;

      return mc.thePlayer.getEquipmentInSlot(1) == null && stack.getUnlocalizedName().contains("boots") || mc.thePlayer.getEquipmentInSlot(2) == null && stack.getUnlocalizedName().contains("leggings") || mc.thePlayer.getEquipmentInSlot(3) == null && stack.getUnlocalizedName().contains("chestplate") || mc.thePlayer.getEquipmentInSlot(4) == null && stack.getUnlocalizedName().contains("helmet");
   }

   private boolean isBadPotion(ItemStack stack) {
      if (stack != null && stack.getItem() instanceof ItemPotion) {
         ItemPotion potion = (ItemPotion)stack.getItem();
         if (ItemPotion.isSplash(stack.getItemDamage())) {
            Iterator var3 = potion.getEffects(stack).iterator();

            while(var3.hasNext()) {
               Object o = var3.next();
               PotionEffect effect = (PotionEffect)o;
               if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private float getDamage(ItemStack stack) {
      return !(stack.getItem() instanceof ItemSword) ? 0.0F : (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F + ((ItemSword)stack.getItem()).getDamageGiven();
   }
}

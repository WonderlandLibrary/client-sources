package net.augustus.modules.player;

import java.awt.Color;
import net.augustus.events.EventClick;
import net.augustus.events.EventPostMouseOver;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.StringValue;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MovingObjectPosition;

public class AutoTool extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   public StringValue mode = new StringValue(1, "Mode", this, "Only Tools", new String[]{"Only Tools"});
   private int slot = 0;

   public AutoTool() {
      super("AutoTool", new Color(237, 237, 0), Categorys.PLAYER);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (mc.thePlayer != null && mc.theWorld != null) {
         this.slot = -1;
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
   }

   @EventTarget
   public void onEventPostMouseOver(EventPostMouseOver eventPostMouseOver) {
      double lastDamage = 0.0;
      this.slot = -1;
      MovingObjectPosition objectPosition = mc.objectMouseOver;
      String mode = this.mode.getSelected();
      if (objectPosition != null) {
         for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null) {
               Item item = itemStack.getItem();
               if (objectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                  double itemDamage = this.getItemDamage(itemStack);
                  double currentDamage = this.getItemDamage(mc.thePlayer.getHeldItem());
                  if (itemDamage > 1.0 && itemDamage > currentDamage && itemDamage > lastDamage) {
                     this.slot = i - 36;
                     lastDamage = itemDamage;
                     this.timeHelper.reset();
                  }
               }

               if (objectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                  && mode.equals("Only Tools")
                  && mc.gameSettings.keyBindAttack.isKeyDown()
                  && (item instanceof ItemTool || item instanceof ItemShears)) {
                  double toolSpeed = this.getToolSpeed(itemStack);
                  double currentSpeed = this.getToolSpeed(mc.thePlayer.getHeldItem());
                  if (toolSpeed > 1.0 && toolSpeed > currentSpeed && toolSpeed > lastDamage) {
                     this.slot = i - 36;
                     lastDamage = toolSpeed;
                     this.timeHelper.reset();
                  }
               }
            }
         }
      }

      if (BlockUtil.isScaffoldToggled()) {
         this.slot = -1;
      }

      mc.playerController.syncCurrentPlayItem();
   }

   @EventTarget
   public void onEventClick(EventClick eventClick) {
      if (!BlockUtil.isScaffoldToggled() && this.slot != -1) {
         eventClick.setSlot(this.slot);
      }
   }

   private double getToolSpeed(ItemStack itemStack) {
      double damage = 0.0;
      if (itemStack != null && (itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemShears)) {
         if (itemStack.getItem() instanceof ItemAxe) {
            damage += (double)(
               itemStack.getItem().getStrVsBlock(itemStack, mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock())
                  + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack)
            );
         } else if (itemStack.getItem() instanceof ItemPickaxe) {
            damage += (double)(
               itemStack.getItem().getStrVsBlock(itemStack, mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock())
                  + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack)
            );
         } else if (itemStack.getItem() instanceof ItemSpade) {
            damage += (double)(
               itemStack.getItem().getStrVsBlock(itemStack, mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock())
                  + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack)
            );
         } else if (itemStack.getItem() instanceof ItemShears) {
            System.out.println(itemStack.getItem().getStrVsBlock(itemStack, mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock()));
            damage += (double)itemStack.getItem().getStrVsBlock(itemStack, mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock());
         }

         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 11.0;
         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack) / 11.0;
         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) / 33.0;
         return damage - (double)itemStack.getItemDamage() / 10000.0;
      } else {
         return 0.0;
      }
   }

   private double getItemDamage(ItemStack itemStack) {
      double damage = 0.0;
      if (itemStack == null && itemStack.getItem() instanceof ItemTool) {
         damage += (double)((ItemTool)itemStack.getItem()).getDamageVsEntity();
         damage += (double)(
            (float)itemStack.getItem().getMaxDamage() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25F
         );
         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) / 11.0;
         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemStack) / 11.0;
         damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 11.0;
         damage -= (double)itemStack.getItemDamage() / 10000.0;
      }

      return damage;
   }

   public int getSlot() {
      return this.slot;
   }
}

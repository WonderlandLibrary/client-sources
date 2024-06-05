package cn.Hanabi.modules.Player;

import cn.Hanabi.value.*;
import ClassSub.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import net.minecraft.client.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import com.darkmagician6.eventapi.*;
import java.util.function.*;
import cn.Hanabi.modules.World.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import java.util.*;
import org.apache.commons.lang3.*;
import net.minecraft.potion.*;

public class ChestStealer extends Mod
{
    public static boolean isChest;
    private Value<Double> delay;
    private Value<Boolean> onlychest;
    private Value<Boolean> trash;
    Class205 time;
    private int[] itemHelmet;
    private int[] itemChestplate;
    private int[] itemLeggings;
    private int[] itemBoots;
    
    
    public ChestStealer() {
        super("ChestStealer", Category.PLAYER);
        this.delay = new Value<Double>("ChestStealer_Delay", 30.0, 0.0, 1000.0, 10.0);
        this.onlychest = new Value<Boolean>("ChestStealer_NoGameMenu", false);
        this.trash = new Value<Boolean>("ChestStealer_PickTrash", false);
        this.time = new Class205();
        this.itemHelmet = new int[] { 298, 302, 306, 310, 314 };
        this.itemChestplate = new int[] { 299, 303, 307, 311, 315 };
        this.itemLeggings = new int[] { 300, 304, 308, 312, 316 };
        this.itemBoots = new int[] { 301, 305, 309, 313, 317 };
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (!ChestStealer.isChest && this.onlychest.getValueState()) {
            return;
        }
        if (Minecraft.getMinecraft().thePlayer.openContainer != null && Minecraft.getMinecraft().thePlayer.openContainer instanceof ContainerChest) {
            final ContainerChest containerChest = (ContainerChest)Minecraft.getMinecraft().thePlayer.openContainer;
            if (this.isChestEmpty(containerChest)) {
                ChestStealer.mc.thePlayer.closeScreen();
            }
            for (int i = 0; i < containerChest.getLowerChestInventory().getSizeInventory(); ++i) {
                if (containerChest.getLowerChestInventory().getStackInSlot(i) != null && this.time.isDelayComplete((long)(Object)this.delay.getValueState() + new Random().nextInt(100)) && (this.itemIsUseful(containerChest, i) || this.trash.getValueState())) {
                    if (new Random().nextInt(100) <= 80) {
                        Minecraft.getMinecraft().playerController.windowClick(containerChest.windowId, i, 0, 1, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
                        this.time.reset();
                    }
                }
            }
        }
    }
    
    private boolean isChestEmpty(final ContainerChest containerChest) {
        for (int i = 0; i < containerChest.getLowerChestInventory().getSizeInventory(); ++i) {
            if (containerChest.getLowerChestInventory().getStackInSlot(i) != null && (this.itemIsUseful(containerChest, i) || this.trash.getValueState())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isPotionNegative(final ItemStack itemStack) {
        return ((ItemPotion)itemStack.getItem()).getEffects(itemStack).stream().map(ChestStealer::lambda$isPotionNegative$0).anyMatch(Potion::isBadEffect);
    }
    
    private boolean itemIsUseful(final ContainerChest containerChest, final int n) {
        final ItemStack getStackInSlot = containerChest.getLowerChestInventory().getStackInSlot(n);
        final Item getItem = getStackInSlot.getItem();
        return getItem instanceof ItemFood || (getItem instanceof ItemPotion && !this.isPotionNegative(getStackInSlot)) || (getItem instanceof ItemSword && this.isBestSword(containerChest, getStackInSlot)) || (getItem instanceof ItemArmor && this.isBestArmor(containerChest, getStackInSlot)) || (getItem instanceof ItemBlock && !Scaffold_.blacklistedBlocks.contains(Block.getBlockFromItem(getItem)));
    }
    
    private float getSwordDamage(final ItemStack itemStack) {
        float n = 0.0f;
        final Optional<AttributeModifier> first = itemStack.getAttributeModifiers().values().stream().findFirst();
        if (first.isPresent()) {
            n = (float)first.get().getAmount();
        }
        return n + EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);
    }
    
    private boolean isBestSword(final ContainerChest containerChest, final ItemStack itemStack) {
        final float swordDamage = this.getSwordDamage(itemStack);
        float n = 0.0f;
        for (int i = 0; i < 45; ++i) {
            if (ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final float swordDamage2 = this.getSwordDamage(ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getStack());
                if (swordDamage2 >= n) {
                    n = swordDamage2;
                }
            }
        }
        for (int j = 0; j < containerChest.getLowerChestInventory().getSizeInventory(); ++j) {
            if (containerChest.getLowerChestInventory().getStackInSlot(j) != null) {
                final float swordDamage3 = this.getSwordDamage(containerChest.getLowerChestInventory().getStackInSlot(j));
                if (swordDamage3 >= n) {
                    n = swordDamage3;
                }
            }
        }
        return swordDamage == n;
    }
    
    private boolean isBestArmor(final ContainerChest containerChest, final ItemStack itemStack) {
        final float n = ((ItemArmor)itemStack.getItem()).damageReduceAmount;
        float n2 = 0.0f;
        if (isContain(this.itemHelmet, Item.getIdFromItem(itemStack.getItem()))) {
            for (int i = 0; i < 45; ++i) {
                if (ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && isContain(this.itemHelmet, Item.getIdFromItem(ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()))) {
                    final float n3 = ((ItemArmor)ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).damageReduceAmount;
                    if (n3 > n2) {
                        n2 = n3;
                    }
                }
            }
            for (int j = 0; j < containerChest.getLowerChestInventory().getSizeInventory(); ++j) {
                if (containerChest.getLowerChestInventory().getStackInSlot(j) != null && isContain(this.itemHelmet, Item.getIdFromItem(containerChest.getLowerChestInventory().getStackInSlot(j).getItem()))) {
                    final float n4 = ((ItemArmor)containerChest.getLowerChestInventory().getStackInSlot(j).getItem()).damageReduceAmount;
                    if (n4 > n2) {
                        n2 = n4;
                    }
                }
            }
        }
        if (isContain(this.itemChestplate, Item.getIdFromItem(itemStack.getItem()))) {
            for (int k = 0; k < 45; ++k) {
                if (ChestStealer.mc.thePlayer.inventoryContainer.getSlot(k).getHasStack() && isContain(this.itemChestplate, Item.getIdFromItem(ChestStealer.mc.thePlayer.inventoryContainer.getSlot(k).getStack().getItem()))) {
                    final float n5 = ((ItemArmor)ChestStealer.mc.thePlayer.inventoryContainer.getSlot(k).getStack().getItem()).damageReduceAmount;
                    if (n5 > n2) {
                        n2 = n5;
                    }
                }
            }
            for (int l = 0; l < containerChest.getLowerChestInventory().getSizeInventory(); ++l) {
                if (containerChest.getLowerChestInventory().getStackInSlot(l) != null && isContain(this.itemChestplate, Item.getIdFromItem(containerChest.getLowerChestInventory().getStackInSlot(l).getItem()))) {
                    final float n6 = ((ItemArmor)containerChest.getLowerChestInventory().getStackInSlot(l).getItem()).damageReduceAmount;
                    if (n6 > n2) {
                        n2 = n6;
                    }
                }
            }
        }
        if (isContain(this.itemLeggings, Item.getIdFromItem(itemStack.getItem()))) {
            for (int n7 = 0; n7 < 45; ++n7) {
                if (ChestStealer.mc.thePlayer.inventoryContainer.getSlot(n7).getHasStack() && isContain(this.itemLeggings, Item.getIdFromItem(ChestStealer.mc.thePlayer.inventoryContainer.getSlot(n7).getStack().getItem()))) {
                    final float n8 = ((ItemArmor)ChestStealer.mc.thePlayer.inventoryContainer.getSlot(n7).getStack().getItem()).damageReduceAmount;
                    if (n8 > n2) {
                        n2 = n8;
                    }
                }
            }
            for (int n9 = 0; n9 < containerChest.getLowerChestInventory().getSizeInventory(); ++n9) {
                if (containerChest.getLowerChestInventory().getStackInSlot(n9) != null && isContain(this.itemLeggings, Item.getIdFromItem(containerChest.getLowerChestInventory().getStackInSlot(n9).getItem()))) {
                    final float n10 = ((ItemArmor)containerChest.getLowerChestInventory().getStackInSlot(n9).getItem()).damageReduceAmount;
                    if (n10 > n2) {
                        n2 = n10;
                    }
                }
            }
        }
        if (isContain(this.itemBoots, Item.getIdFromItem(itemStack.getItem()))) {
            for (int n11 = 0; n11 < 45; ++n11) {
                if (ChestStealer.mc.thePlayer.inventoryContainer.getSlot(n11).getHasStack() && isContain(this.itemBoots, Item.getIdFromItem(ChestStealer.mc.thePlayer.inventoryContainer.getSlot(n11).getStack().getItem()))) {
                    final float n12 = ((ItemArmor)ChestStealer.mc.thePlayer.inventoryContainer.getSlot(n11).getStack().getItem()).damageReduceAmount;
                    if (n12 > n2) {
                        n2 = n12;
                    }
                }
            }
            for (int n13 = 0; n13 < containerChest.getLowerChestInventory().getSizeInventory(); ++n13) {
                if (containerChest.getLowerChestInventory().getStackInSlot(n13) != null && isContain(this.itemBoots, Item.getIdFromItem(containerChest.getLowerChestInventory().getStackInSlot(n13).getItem()))) {
                    final float n14 = ((ItemArmor)containerChest.getLowerChestInventory().getStackInSlot(n13).getItem()).damageReduceAmount;
                    if (n14 > n2) {
                        n2 = n14;
                    }
                }
            }
        }
        return n == n2;
    }
    
    public static boolean isContain(final int[] array, final int n) {
        return ArrayUtils.contains(array, n);
    }
    
    private static Potion lambda$isPotionNegative$0(final PotionEffect potionEffect) {
        return Potion.potionTypes[potionEffect.getPotionID()];
    }
}

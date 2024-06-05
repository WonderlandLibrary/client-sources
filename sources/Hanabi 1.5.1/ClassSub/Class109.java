package ClassSub;

import net.minecraft.client.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.item.*;

public class Class109
{
    public static Minecraft mc;
    
    
    public void dropSlot(final int n) {
        Class109.mc.playerController.windowClick(new GuiInventory((EntityPlayer)Class109.mc.thePlayer).inventorySlots.windowId, n, 1, 4, (EntityPlayer)Class109.mc.thePlayer);
    }
    
    public static ItemStack getStackInSlot(final int n) {
        return Class109.mc.thePlayer.inventory.getStackInSlot(n);
    }
    
    public static boolean isBestArmorOfTypeInInv(final ItemStack itemStack) {
        try {
            if (itemStack == null) {
                return false;
            }
            if (itemStack.getItem() == null) {
                return false;
            }
            if (itemStack.getItem() != null && !(itemStack.getItem() instanceof ItemArmor)) {
                return false;
            }
            final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
            final int armorProt = getArmorProt(itemStack);
            for (int i = 0; i < 4; ++i) {
                final ItemStack itemStack2 = Class109.mc.thePlayer.inventory.armorInventory[i];
                final int armorProt2;
                if (itemStack2 != null && ((ItemArmor)itemStack2.getItem()).armorType == itemArmor.armorType && (armorProt2 = getArmorProt(itemStack2)) >= armorProt) {
                    return false;
                }
            }
            for (int j = 0; j < Class109.mc.thePlayer.inventory.getSizeInventory() - 4; ++j) {
                final ItemStack getStackInSlot = Class109.mc.thePlayer.inventory.getStackInSlot(j);
                if (getStackInSlot != null && getStackInSlot.getItem() instanceof ItemArmor) {
                    final ItemArmor itemArmor2 = (ItemArmor)getStackInSlot.getItem();
                    final int armorProt3;
                    if (itemArmor2.armorType == itemArmor.armorType && itemArmor2 != itemArmor && (armorProt3 = getArmorProt(getStackInSlot)) >= armorProt) {
                        return false;
                    }
                }
            }
        }
        catch (Exception ex) {}
        return true;
    }
    
    public static boolean hotbarHas(final Item item) {
        for (int i = 0; i <= 36; ++i) {
            final ItemStack getStackInSlot = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null && getStackInSlot.getItem() == item) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hotbarHas(final Item item, final int n) {
        for (int i = 0; i <= 36; ++i) {
            final ItemStack getStackInSlot = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null && getStackInSlot.getItem() == item && getSlotID(getStackInSlot.getItem()) == n) {
                return true;
            }
        }
        return false;
    }
    
    public static int getSlotID(final Item item) {
        for (int i = 0; i <= 36; ++i) {
            final ItemStack getStackInSlot = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null && getStackInSlot.getItem() == item) {
                return i;
            }
        }
        return -1;
    }
    
    public static ItemStack getItemBySlotID(final int n) {
        for (int i = 0; i <= 36; ++i) {
            final ItemStack getStackInSlot = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null && getSlotID(getStackInSlot.getItem()) == n) {
                return getStackInSlot;
            }
        }
        return null;
    }
    
    public static int getArmorProt(final ItemStack itemStack) {
        int n = -1;
        if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
            n = ((ItemArmor)itemStack.getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(itemStack)) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.generic);
        }
        return n;
    }
    
    public static int getBestSwordSlotID(final ItemStack itemStack, final double n) {
        for (int i = 0; i <= 36; ++i) {
            final ItemStack getStackInSlot = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null && getStackInSlot == itemStack && getSwordDamage(getStackInSlot) == getSwordDamage(itemStack)) {
                return i;
            }
        }
        return -1;
    }
    
    private static double getSwordDamage(final ItemStack itemStack) {
        double getAmount = 0.0;
        final Optional<AttributeModifier> first = itemStack.getAttributeModifiers().values().stream().findFirst();
        if (first.isPresent()) {
            getAmount = first.get().getAmount();
        }
        return getAmount + EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);
    }
    
    public boolean isBestChest(final int n) {
        if (getStackInSlot(n) != null && getStackInSlot(n).getItem() != null && getStackInSlot(n).getItem() instanceof ItemArmor) {
            final int n2 = ((ItemArmor)Class109.mc.thePlayer.inventory.getStackInSlot(n).getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(Class109.mc.thePlayer.inventory.getStackInSlot(n))) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { Class109.mc.thePlayer.inventory.getStackInSlot(n) }, DamageSource.generic);
            if (Class109.mc.thePlayer.inventory.armorInventory[2] != null) {
                final ItemArmor itemArmor = (ItemArmor)Class109.mc.thePlayer.inventory.armorInventory[2].getItem();
                final ItemStack itemStack = Class109.mc.thePlayer.inventory.armorInventory[2];
                final ItemArmor itemArmor2 = (ItemArmor)getStackInSlot(n).getItem();
                final int n3 = ((ItemArmor)itemStack.getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(itemStack)) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.generic);
                if (n3 > n2 || n3 == n2) {
                    return false;
                }
            }
            for (int i = 0; i < Class109.mc.thePlayer.inventory.getSizeInventory(); ++i) {
                if (getStackInSlot(i) != null && Class109.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemArmor) {
                    final int n4 = ((ItemArmor)Class109.mc.thePlayer.inventory.getStackInSlot(i).getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(Class109.mc.thePlayer.inventory.getStackInSlot(i))) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { Class109.mc.thePlayer.inventory.getStackInSlot(i) }, DamageSource.generic);
                    final ItemArmor itemArmor3 = (ItemArmor)getStackInSlot(n).getItem();
                    final ItemArmor itemArmor4 = (ItemArmor)getStackInSlot(i).getItem();
                    if (itemArmor3.armorType == 1 && itemArmor4.armorType == 1 && n4 > n2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public boolean isBestHelmet(final int n) {
        if (getStackInSlot(n) != null && getStackInSlot(n).getItem() != null && getStackInSlot(n).getItem() instanceof ItemArmor) {
            final int n2 = ((ItemArmor)Class109.mc.thePlayer.inventory.getStackInSlot(n).getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(Class109.mc.thePlayer.inventory.getStackInSlot(n))) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { Class109.mc.thePlayer.inventory.getStackInSlot(n) }, DamageSource.generic);
            if (Class109.mc.thePlayer.inventory.armorInventory[3] != null) {
                final ItemArmor itemArmor = (ItemArmor)Class109.mc.thePlayer.inventory.armorInventory[3].getItem();
                final ItemStack itemStack = Class109.mc.thePlayer.inventory.armorInventory[3];
                final ItemArmor itemArmor2 = (ItemArmor)getStackInSlot(n).getItem();
                final int n3 = ((ItemArmor)itemStack.getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(itemStack)) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.generic);
                if (n3 > n2 || n3 == n2) {
                    return false;
                }
            }
            for (int i = 0; i < Class109.mc.thePlayer.inventory.getSizeInventory(); ++i) {
                if (getStackInSlot(i) != null && Class109.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemArmor) {
                    final int n4 = ((ItemArmor)Class109.mc.thePlayer.inventory.getStackInSlot(i).getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(Class109.mc.thePlayer.inventory.getStackInSlot(i))) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { Class109.mc.thePlayer.inventory.getStackInSlot(i) }, DamageSource.generic);
                    final ItemArmor itemArmor3 = (ItemArmor)getStackInSlot(n).getItem();
                    final ItemArmor itemArmor4 = (ItemArmor)getStackInSlot(i).getItem();
                    if (itemArmor3.armorType == 0 && itemArmor4.armorType == 0 && n4 > n2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public boolean isBestLeggings(final int n) {
        if (getStackInSlot(n) != null && getStackInSlot(n).getItem() != null && getStackInSlot(n).getItem() instanceof ItemArmor) {
            final int n2 = ((ItemArmor)Class109.mc.thePlayer.inventory.getStackInSlot(n).getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(Class109.mc.thePlayer.inventory.getStackInSlot(n))) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { Class109.mc.thePlayer.inventory.getStackInSlot(n) }, DamageSource.generic);
            if (Class109.mc.thePlayer.inventory.armorInventory[1] != null) {
                final ItemArmor itemArmor = (ItemArmor)Class109.mc.thePlayer.inventory.armorInventory[1].getItem();
                final ItemStack itemStack = Class109.mc.thePlayer.inventory.armorInventory[1];
                final ItemArmor itemArmor2 = (ItemArmor)getStackInSlot(n).getItem();
                final int n3 = ((ItemArmor)itemStack.getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(itemStack)) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.generic);
                if (n3 > n2 || n3 == n2) {
                    return false;
                }
            }
            for (int i = 0; i < Class109.mc.thePlayer.inventory.getSizeInventory(); ++i) {
                if (getStackInSlot(i) != null && Class109.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemArmor) {
                    final int n4 = ((ItemArmor)Class109.mc.thePlayer.inventory.getStackInSlot(i).getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(Class109.mc.thePlayer.inventory.getStackInSlot(i))) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { Class109.mc.thePlayer.inventory.getStackInSlot(i) }, DamageSource.generic);
                    final ItemArmor itemArmor3 = (ItemArmor)getStackInSlot(n).getItem();
                    final ItemArmor itemArmor4 = (ItemArmor)getStackInSlot(i).getItem();
                    if (itemArmor3.armorType == 2 && itemArmor4.armorType == 2 && n4 > n2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public boolean isBestBoots(final int n) {
        if (getStackInSlot(n) != null && getStackInSlot(n).getItem() != null && getStackInSlot(n).getItem() instanceof ItemArmor) {
            final int n2 = ((ItemArmor)Class109.mc.thePlayer.inventory.getStackInSlot(n).getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(Class109.mc.thePlayer.inventory.getStackInSlot(n))) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { Class109.mc.thePlayer.inventory.getStackInSlot(n) }, DamageSource.generic);
            if (Class109.mc.thePlayer.inventory.armorInventory[0] != null) {
                final ItemArmor itemArmor = (ItemArmor)Class109.mc.thePlayer.inventory.armorInventory[0].getItem();
                final ItemStack itemStack = Class109.mc.thePlayer.inventory.armorInventory[0];
                final ItemArmor itemArmor2 = (ItemArmor)getStackInSlot(n).getItem();
                final int n3 = ((ItemArmor)itemStack.getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(itemStack)) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.generic);
                if (n3 > n2 || n3 == n2) {
                    return false;
                }
            }
            for (int i = 0; i < Class109.mc.thePlayer.inventory.getSizeInventory(); ++i) {
                if (getStackInSlot(i) != null && Class109.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemArmor) {
                    final int n4 = ((ItemArmor)Class109.mc.thePlayer.inventory.getStackInSlot(i).getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(Class109.mc.thePlayer.inventory.getStackInSlot(i))) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { Class109.mc.thePlayer.inventory.getStackInSlot(i) }, DamageSource.generic);
                    final ItemArmor itemArmor3 = (ItemArmor)getStackInSlot(n).getItem();
                    final ItemArmor itemArmor4 = (ItemArmor)getStackInSlot(i).getItem();
                    if (itemArmor3.armorType == 3 && itemArmor4.armorType == 3 && n4 > n2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public boolean isBestSword(final int n) {
        return this.getBestWeapon() == n;
    }
    
    public static int getItemType(final ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemArmor) {
            return ((ItemArmor)itemStack.getItem()).armorType;
        }
        return -1;
    }
    
    public static float getItemDamage(final ItemStack itemStack) {
        final Multimap getAttributeModifiers = itemStack.getAttributeModifiers();
        final Iterator iterator;
        if (!getAttributeModifiers.isEmpty() && (iterator = getAttributeModifiers.entries().iterator()).hasNext()) {
            final AttributeModifier attributeModifier = iterator.next().getValue();
            final double n = (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) ? attributeModifier.getAmount() : (attributeModifier.getAmount() * 100.0);
            return (attributeModifier.getAmount() > 1.0) ? (1.0f + (float)n) : 1.0f;
        }
        return 1.0f;
    }
    
    public boolean hasItemMoreTimes(final int n) {
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        list.clear();
        for (int i = 0; i < Class109.mc.thePlayer.inventory.getSizeInventory(); ++i) {
            if (!list.contains(getStackInSlot(i))) {
                list.add(getStackInSlot(i));
            }
            else if (getStackInSlot(i) == getStackInSlot(n)) {
                return true;
            }
        }
        return false;
    }
    
    public int getBestWeaponInHotbar() {
        final int currentItem = Class109.mc.thePlayer.inventory.currentItem;
        int n = -1;
        float n2 = 1.0f;
        for (int i = 0; i < 9; i = (byte)(i + 1)) {
            final ItemStack getStackInSlot = Class109.mc.thePlayer.inventory.getStackInSlot(i);
            final float n3;
            if (getStackInSlot != null && (n3 = getItemDamage(getStackInSlot) + EnchantmentHelper.getModifierForCreature(getStackInSlot, EnumCreatureAttribute.UNDEFINED)) > n2) {
                n2 = n3;
                n = i;
            }
        }
        if (n != -1) {
            return n;
        }
        return currentItem;
    }
    
    public int getBestWeapon() {
        final int currentItem = Class109.mc.thePlayer.inventory.currentItem;
        int n = -1;
        float n2 = 1.0f;
        for (int i = 0; i < Class109.mc.thePlayer.inventory.getSizeInventory(); i = (byte)(i + 1)) {
            final ItemStack stackInSlot;
            final float n3;
            if (getStackInSlot(i) != null && (stackInSlot = getStackInSlot(i)) != null && stackInSlot.getItem() != null && stackInSlot.getItem() instanceof ItemSword && (n3 = getItemDamage(stackInSlot) + EnchantmentHelper.getModifierForCreature(stackInSlot, EnumCreatureAttribute.UNDEFINED)) > n2) {
                n2 = n3;
                n = i;
            }
        }
        if (n != -1) {
            return n;
        }
        return currentItem;
    }
    
    public int getArmorProt(final int n) {
        int n2 = -1;
        if (getStackInSlot(n) != null && getStackInSlot(n).getItem() != null && getStackInSlot(n).getItem() instanceof ItemArmor) {
            n2 = ((ItemArmor)Class109.mc.thePlayer.inventory.getStackInSlot(n).getItem()).getArmorMaterial().getDamageReductionAmount(getItemType(Class109.mc.thePlayer.inventory.getStackInSlot(n))) + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { Class109.mc.thePlayer.inventory.getStackInSlot(n) }, DamageSource.generic);
        }
        return n2;
    }
    
    public static int getFirstItem(final Item item) {
        for (int i = 0; i < Class109.mc.thePlayer.inventory.getSizeInventory(); ++i) {
            if (getStackInSlot(i) != null && getStackInSlot(i).getItem() != null && getStackInSlot(i).getItem() == item) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean isBestSword(final ItemStack itemStack, final int n) {
        if (itemStack != null && itemStack.getItem() instanceof ItemSword) {
            for (int i = 0; i < Class109.mc.thePlayer.inventory.getSizeInventory(); ++i) {
                final ItemStack getStackInSlot = Class109.mc.thePlayer.inventory.getStackInSlot(i);
                if (getStackInSlot != null && getStackInSlot.getItem() instanceof ItemSword && getItemDamage(getStackInSlot) >= getItemDamage(itemStack) && n != i) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static {
        Class109.mc = Minecraft.getMinecraft();
    }
}

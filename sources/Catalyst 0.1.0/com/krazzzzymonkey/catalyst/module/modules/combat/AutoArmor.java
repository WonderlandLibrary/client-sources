package com.krazzzzymonkey.catalyst.module.modules.combat;

import java.util.Iterator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Arrays;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraft.network.play.client.CPacketClickWindow;
import com.krazzzzymonkey.catalyst.utils.system.Connection;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;
import com.krazzzzymonkey.catalyst.value.BooleanValue;
import com.krazzzzymonkey.catalyst.value.NumberValue;
import com.krazzzzymonkey.catalyst.module.Modules;

public class AutoArmor extends Modules
{
    public NumberValue delay;
    private int timer;
    public BooleanValue useEnchantments;
    public BooleanValue swapWhileMoving;
    
    int getArmorValue(final ItemArmor itemarmor, final ItemStack itemstack) {
        final int damageReduceAmount = itemarmor.damageReduceAmount;
        int calcModifierDamage = 0;
        final int toughness = (int)itemarmor.toughness;
        final int damageReduction = itemarmor.getArmorMaterial().getDamageReductionAmount(EntityEquipmentSlot.LEGS);
        if ((((boolean)this.useEnchantments.getValue()) ? 1 : 0) != 0) {
            final Enchantment protEnchant = Enchantments.PROTECTION;
            final int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(protEnchant, itemstack);
            final EntityPlayerSP entityPlayer = Wrapper.INSTANCE.player();
            final DamageSource damageSource = DamageSource.causePlayerDamage((EntityPlayer)entityPlayer);
            calcModifierDamage = protEnchant.calcModifierDamage(enchantmentLevel, damageSource);
        }
        return damageReduceAmount * 5 + calcModifierDamage * 3 + toughness + damageReduction;
    }
    
    @Override
    public void onEnable() {
        this.timer = 0;
        super.onEnable();
    }
    
    @Override
    public boolean onPacket(final Object obj, final Connection.Side connectionSide) {
        if ((connectionSide == Connection.Side.OUT) && ((obj instanceof CPacketClickWindow) ? 1 : 0) != 0) {
            this.timer = this.delay.getValue().intValue();
        }
        return true;
    }
    
    
    public static boolean isNullOrEmpty(final ItemStack itemstack) {
        int n;
        if (itemstack == null || (itemstack.isEmpty() ? 1 : 0) != 0) {
            n = 1;
        }
        else {
            n = 0;
        }
        return n;
    }
      
    public AutoArmor() {
        super("AutoArmor", ModuleCategory.COMBAT);
        this.useEnchantments = new BooleanValue("Enchantments", true);
        this.swapWhileMoving = new BooleanValue("SwapWhileMoving", false);
        final Value[] var = new Value[2];
        var[0] = this.useEnchantments;
        var[1] = this.swapWhileMoving;
        this.addValue(var);
        this.delay = new NumberValue("Delay", 2.0, 0.0, 20.0);
    }

    @Override
    public void onClientTick(final TickEvent.ClientTickEvent tickEvent) {
        if (this.timer > 0) {
            this.timer -= 1;
            return;
        }
        if ((((Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer) ? 1 : 0) != 0) && ((Wrapper.INSTANCE.mc().currentScreen instanceof InventoryEffectRenderer) ? 1 : 0) = 0) {
            return;
        }
        final InventoryPlayer inventory = Wrapper.INSTANCE.player().inventory;
        if (((((boolean)this.swapWhileMoving.getValue()) ? 1 : 0) = 0) && (!(fcmpl(Wrapper.INSTANCE.player().movementInput.moveForward, 0.0f)) = 0 || (fcmpl(Wrapper.INSTANCE.player().movementInput.moveStrafe, 0.0f)) != 0)) {
            return;
        }
        final int[] int1 = new int[4];
        final int[] int2 = new int[4];
        int i = 0;
        while (i < 4) {
            int1[i] = -1;
            final ItemStack itemStack = inventory.armorItemInSlot(i);
            if ((isNullOrEmpty(itemStack) ? 1 : 0) == 0) {
                if (((itemStack.getItem() instanceof ItemArmor) ? 1 : 0) == 0) {

                }
                else {
                    final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
                    int2[i] = this.getArmorValue(itemArmor, itemStack);
                }
            }
            ++i;
        }
        int i = 0;
        while (i < 36) {
            final ItemStack itemStack = inventory.getStackInSlot(i);
            if ((isNullOrEmpty(itemStack) ? 1 : 0) == 0) {
                if (((itemStack.getItem() instanceof ItemArmor) ? 1 : 0) == 0) {
                    if (null != null) {
                        return;
                    }
                }
                else {
                    final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
                    final int armorType = itemArmor.armorType.getIndex();
                    final int armorValue = this.getArmorValue(itemArmor, itemStack);
                    if (armorValue > int2[armorType]) {
                        int1[armorType] = i;
                        int2[armorType] = armorValue;
                    }
                }
            }
            ++i;
        }
        final Integer[] array = new Integer[4];
        array[0] = 0;
        array[1] = 1;
        array[2] = 2;
        array[3] = 3;
        final ArrayList<Integer> array = new ArrayList<Integer>(Arrays.asList(array));
        Collections.shuffle(array);
        final Iterator<Integer> iterator = array.iterator();
        while ((iterator.hasNext() ? 1 : 0) != 0) {
            final int next = iterator.next();
            int num = int1[next];
            if (num == -1) {
                continue;
            }
            else {
                final ItemStack itemStack = inventory.armorItemInSlot(next);
                if ((isNullOrEmpty(itemStack) ? 1 : 0) = 0 && (inventory.getFirstEmptyStack() == -1)) {
                    continue;
                }
                else {
                    if (num < 9) {
                        num += 36;
                    }
                    if ((isNullOrEmpty(itemStack) ? 1 : 0) == 0) {
                        Wrapper.INSTANCE.mc().playerController.windowClick(0, 8 - next, 0, ClickType.QUICK_MOVE, (EntityPlayer)Wrapper.INSTANCE.player());
                    }
                    Wrapper.INSTANCE.mc().playerController.windowClick(0, num, 0, ClickType.QUICK_MOVE, (EntityPlayer)Wrapper.INSTANCE.player());
                    break;
                }
            }
        }
        super.onClientTick(tickEvent);
    }
}

/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.MoveUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@FunctionRegister(name="AutoArmor", type=Category.Combat)
public class AutoArmor
extends Function {
    private final SliderSetting delay = new SliderSetting("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430", 100.0f, 0.0f, 1000.0f, 1.0f);
    private final StopWatch stopWatch = new StopWatch();

    public AutoArmor() {
        this.addSettings(this.delay);
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        Object object;
        Item item;
        Object object2;
        int n;
        if (MoveUtils.isMoving()) {
            return;
        }
        PlayerInventory playerInventory = AutoArmor.mc.player.inventory;
        int[] nArray = new int[4];
        int[] nArray2 = new int[4];
        for (n = 0; n < 4; ++n) {
            nArray[n] = -1;
            object2 = playerInventory.armorItemInSlot(n);
            if (!this.isItemValid((ItemStack)object2) || !((item = ((ItemStack)object2).getItem()) instanceof ArmorItem)) continue;
            object = (ArmorItem)item;
            nArray2[n] = this.calculateArmorValue((ArmorItem)object, (ItemStack)object2);
        }
        for (n = 0; n < 36; ++n) {
            object = playerInventory.getStackInSlot(n);
            if (!this.isItemValid((ItemStack)object) || !((object2 = ((ItemStack)object).getItem()) instanceof ArmorItem)) continue;
            item = (ArmorItem)object2;
            int n2 = ((ArmorItem)item).getSlot().getIndex();
            int n3 = this.calculateArmorValue((ArmorItem)item, (ItemStack)object);
            if (n3 <= nArray2[n2]) continue;
            nArray[n2] = n;
            nArray2[n2] = n3;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(arrayList);
        object2 = arrayList.iterator();
        while (object2.hasNext()) {
            int n4 = object2.next();
            int n5 = nArray[n4];
            if (n5 == -1 || this.isItemValid(playerInventory.armorItemInSlot(n4)) && playerInventory.getFirstEmptyStack() == -1) continue;
            if (n5 < 9) {
                n5 += 36;
            }
            if (!this.stopWatch.isReached(((Float)this.delay.get()).longValue())) break;
            ItemStack itemStack = playerInventory.armorItemInSlot(n4);
            if (this.isItemValid(itemStack)) {
                AutoArmor.mc.playerController.windowClick(0, 8 - n4, 0, ClickType.QUICK_MOVE, AutoArmor.mc.player);
            }
            AutoArmor.mc.playerController.windowClick(0, n5, 0, ClickType.QUICK_MOVE, AutoArmor.mc.player);
            this.stopWatch.reset();
            break;
        }
    }

    private boolean isItemValid(ItemStack itemStack) {
        return itemStack != null && !itemStack.isEmpty();
    }

    private int calculateArmorValue(ArmorItem armorItem, ItemStack itemStack) {
        int n = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, itemStack);
        IArmorMaterial iArmorMaterial = armorItem.getArmorMaterial();
        int n2 = iArmorMaterial.getDamageReductionAmount(armorItem.getEquipmentSlot());
        return armorItem.getDamageReduceAmount() * 20 + n * 12 + (int)(armorItem.getToughness() * 2.0f) + n2 * 5 >> 3;
    }
}


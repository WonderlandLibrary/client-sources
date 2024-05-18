package wwe.modules.player;

import wwe.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class AutoArmor extends qMIe
{
    public sbfc Xijd;
    public NumberValue delay;
    
    public AutoArmor() {
        super("Auto Armor", "Automatically places armor on you", 0, Category.PLAYER);
        this.Xijd = new sbfc();
    }
    
    @Override
    public void HrDr() {
        this.delay = new NumberValue("Delay", this, "Delay before you put next armor piece on", 0.3, 0.0, 2.0);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (Wrapper.mc.currentScreen != null) {
            return;
        }
        this.Xijd.gCgh();
        if (!this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
            return;
        }
        final int[] array = new int[4];
        final int[] array2 = new int[4];
        int i = 0;
        while (i < 4) {
            final ItemStack armorItemInSlot = Wrapper.mc.player.inventory.armorItemInSlot(i);
            if (armorItemInSlot != null && armorItemInSlot.getItem() instanceof ItemArmor) {
                array2[i] = ((ItemArmor)armorItemInSlot.getItem()).damageReduceAmount;
            }
            array[i] = -1;
            ++i;
            if (AutoArmor.\u200e\u2002\u200c\u200e\u200d) {
                throw null;
            }
        }
        int j = 0;
        while (j < 36) {
            final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(j);
            if (stackInSlot != null && stackInSlot.getItem() instanceof ItemArmor) {
                final ItemArmor itemArmor = (ItemArmor)stackInSlot.getItem();
                final int tlUl = tLUl(itemArmor);
                final int damageReduceAmount = itemArmor.damageReduceAmount;
                if (damageReduceAmount > array2[tlUl]) {
                    array[tlUl] = j;
                    array2[tlUl] = damageReduceAmount;
                }
            }
            ++j;
            if (AutoArmor.\u200e\u2002\u200c\u200e\u200d) {
                throw null;
            }
        }
        int k = 0;
        while (k < 4) {
            int n = array[k];
            if (n != -1) {
                Wrapper.mc.player.inventory.armorItemInSlot(k);
                if (n < 9) {
                    n += 36;
                }
                Wrapper.mc.playerController.windowClick(0, 8 - k, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                Wrapper.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                Wrapper.mc.playerController.windowClick(0, 8 - k, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                if (AutoArmor.\u200e\u2002\u200c\u200e\u200d) {
                    throw null;
                }
                break;
            }
            else {
                ++k;
                if (AutoArmor.\u200e\u2002\u200c\u200e\u200d) {
                    throw null;
                }
                continue;
            }
        }
        this.Xijd.BBwj();
    }
    
    public static int tLUl(final ItemArmor itemArmor) {
        return itemArmor.armorType.ordinal() - 2;
    }
    
    public static boolean cKMx(final ItemStack itemStack) {
        boolean b;
        if (itemStack == null || itemStack.getItem() instanceof ItemAir) {
            b = true;
            if (AutoArmor.\u200e\u2002\u200c\u200e\u200d) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    @Override
    public void LPxx() {
        this.Xijd.BBwj();
    }
    
    @Override
    public void qBmy() {
        this.Xijd.BBwj();
    }
}

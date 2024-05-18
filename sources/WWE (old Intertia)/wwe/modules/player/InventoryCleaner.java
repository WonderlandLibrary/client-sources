package wwe.modules.player;

import wwe.modules.*;
import wwe.utils.*;
import java.util.function.*;
import wwe.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;

public class InventoryCleaner extends qMIe
{
    int NjYY;
    public static Set<Item> KtLJ;
    int QNbN;
    public NumberValue delay;
    
    public InventoryCleaner() {
        super("Inventory Cleaner", "Clears unwanted items from you inventory", 0, Category.PLAYER);
        this.NjYY = 0;
        this.QNbN = 0;
        InventoryCleaner.KtLJ = new HashSet<Item>();
        Arrays.stream(new Item[0]).forEach(InventoryCleaner.KtLJ::add);
    }
    
    @Override
    public void HrDr() {
        this.delay = new NumberValue("Delay", this, "Delay before next item is thrown out", 2.0, 0.0, 10.0);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        ++this.QNbN;
        final Optional first = Wrapper.mc.player.inventoryContainer.inventorySlots.stream().filter(Slot::func_75216_d).filter(mYaJ::iKVK).filter(this::RyVG).findFirst();
        if (first.isPresent() && this.QNbN >= this.delay.OnGi()) {
            Wrapper.mc.playerController.windowClick(0, first.get().slotNumber, 1, ClickType.THROW, (EntityPlayer)Wrapper.mc.player);
            this.QNbN = 0;
        }
    }
    
    @Override
    public void LPxx() {
        this.NjYY = 9;
        super.LPxx();
    }
    
    private boolean evag(final ItemStack itemStack) {
        return InventoryCleaner.KtLJ.contains(itemStack.getItem());
    }
    
    private boolean RyVG(final Slot slot) {
        return this.evag(slot.getStack());
    }
    
    private static boolean iKVK(final Slot slot) {
        boolean b;
        if (!slot.getStack().equals(Wrapper.mc.player.getHeldItemMainhand())) {
            b = true;
            if (InventoryCleaner.\u2002\u200d\u200d\u2008\u200d\u2007\u2000) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
}

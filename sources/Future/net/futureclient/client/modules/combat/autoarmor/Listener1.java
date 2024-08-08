package net.futureclient.client.modules.combat.autoarmor;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemElytra;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.futureclient.client.of;
import net.futureclient.client.pg;
import net.futureclient.client.modules.combat.AutoTotem;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.combat.AutoArmor;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final AutoArmor k;
    
    public Listener1(final AutoArmor k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        final AutoTotem autoTotem;
        if ((autoTotem = (AutoTotem)pg.M().M().M((Class)of.class)) != null && autoTotem.M() && autoTotem.k) {
            return;
        }
        if (!AutoArmor.M(this.k).e(AutoArmor.M(this.k).B().floatValue() * 1000.0f) || AutoArmor.getMinecraft4().player.capabilities.isCreativeMode || (AutoArmor.getMinecraft7().currentScreen instanceof GuiContainer && !(AutoArmor.getMinecraft1().currentScreen instanceof GuiInventory)) || (AutoArmor.e(this.k).M() && !(AutoArmor.getMinecraft6().currentScreen instanceof GuiContainer))) {
            return;
        }
        if (AutoArmor.M(this.k).M() && AutoArmor.getMinecraft().player.inventoryContainer.getSlot(6).getStack().getItem() instanceof ItemElytra && AutoArmor.getMinecraft3().player.inventoryContainer.getSlot(6).getStack().getMaxDamage() - AutoArmor.getMinecraft2().player.inventoryContainer.getSlot(6).getStack().getItemDamage() < 5) {
            int i = 9;
            int n = 9;
            while (i <= 44) {
                final ItemStack stack;
                if ((stack = AutoArmor.getMinecraft5().player.inventoryContainer.getSlot(n).getStack()) != ItemStack.EMPTY && stack.getItem() instanceof ItemElytra && stack.getCount() == 1 && stack.getMaxDamage() - stack.getItemDamage() > 5) {
                    AutoArmor.M(this.k, true);
                    AutoArmor.M(this.k, 6, false);
                    AutoArmor.M(this.k, n, true);
                    AutoArmor.M(this.k, n, false);
                    AutoArmor.M(this.k, false);
                }
                i = (n = (byte)(n + 1));
            }
        }
        int j = 5;
        byte b = 5;
        while (j <= 8) {
            if (AutoArmor.M(this.k, b)) {
                AutoArmor.M(this.k).e();
                return;
            }
            j = ++b;
        }
    }
}

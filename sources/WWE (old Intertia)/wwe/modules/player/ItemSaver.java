package wwe.modules.player;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import wwe.*;

public class ItemSaver extends qMIe
{
    public NumberValue durabilityamount;
    
    public ItemSaver() {
        super("Item Saver", "Switch's slots if held item durability is at x amount(main hand)", 0, Category.PLAYER);
    }
    
    @Override
    public void HrDr() {
        this.durabilityamount = new NumberValue("Durability amount", this, "What durability amount before switching slots", 2.0, 1.0, 10.0);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        final ItemStack heldItemMainhand = Wrapper.mc.player.getHeldItemMainhand();
        if (jjZz.cKMx(heldItemMainhand)) {
            return;
        }
        if (heldItemMainhand.isItemDamaged() && heldItemMainhand.getMaxDamage() - heldItemMainhand.getItemDamage() < this.durabilityamount.OnGi()) {
            int currentItem = -1;
            int i = 0;
            while (i < 9) {
                final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
                if (stackInSlot.getItem() instanceof ItemAir || !stackInSlot.isItemDamaged() || stackInSlot.getMaxDamage() - stackInSlot.getItemDamage() > this.durabilityamount.OnGi()) {
                    currentItem = i;
                    if (ItemSaver.\u200a\u2006\u2003\u2000\u2000\u2005\u2006) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                    if (ItemSaver.\u200a\u2006\u2003\u2000\u2000\u2005\u2006) {
                        throw null;
                    }
                    continue;
                }
            }
            if (currentItem == -1) {
                HAxG.TuzD("Couldent find any slots with more then " + ((IcRI<Object>)this.durabilityamount).OnGi() + " durability, throwing current item out");
                Wrapper.mc.player.dropItem(false);
                if (ItemSaver.\u200a\u2006\u2003\u2000\u2000\u2005\u2006) {
                    throw null;
                }
            }
            else {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem));
                Wrapper.mc.player.inventory.currentItem = currentItem;
                Wrapper.mc.playerController.updateController();
            }
        }
    }
}

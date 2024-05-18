package wwe;

import wwe.utils.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.client.multiplayer.*;

public class VCSW extends HAxG
{
    public VCSW() {
        super("dupe");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            if (array.length < 1) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            if ((boolean)Boolean.parseBoolean(array[0])) {
                int i = 0;
                while (i < Wrapper.mc.player.inventory.mainInventory.size()) {
                    final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
                    if (stackInSlot.getItem() != null && stackInSlot.getItem() != Items.AIR) {
                        final PlayerControllerMP playerController = Wrapper.mc.playerController;
                        final int n = 0;
                        int n2;
                        if (i < 9) {
                            n2 = i + 36;
                            if (VCSW.\u2006\u200a\u200c\u200e) {
                                throw null;
                            }
                        }
                        else {
                            n2 = i;
                        }
                        playerController.windowClick(n, n2, 1, ClickType.THROW, (EntityPlayer)Wrapper.mc.player);
                    }
                    ++i;
                    if (VCSW.\u2006\u200a\u200c\u200e) {
                        throw null;
                    }
                }
                if (VCSW.\u2006\u200a\u200c\u200e) {
                    throw null;
                }
            }
            else {
                Wrapper.mc.player.dropItem(true);
            }
            Wrapper.mc.world.sendQuittingDisconnectingPacket();
            if (VCSW.\u2006\u200a\u200c\u200e) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "Does the 11/11 dupe works on vanilla servers/aout 25% of the time";
    }
    
    @Override
    public String ZlRY() {
        return "dupe dropall";
    }
}

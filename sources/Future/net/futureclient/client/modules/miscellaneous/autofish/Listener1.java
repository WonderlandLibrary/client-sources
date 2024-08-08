package net.futureclient.client.modules.miscellaneous.autofish;

import net.futureclient.client.events.Event;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFishingRod;
import net.futureclient.client.AC;
import net.futureclient.client.pg;
import net.futureclient.client.modules.miscellaneous.AutoEat;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.miscellaneous.AutoFish;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final AutoFish k;
    
    public Listener1(final AutoFish k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        final AutoEat autoEat;
        if ((autoEat = (AutoEat)pg.M().M().M((Class)AC.class)).M() && autoEat.b()) {
            return;
        }
        int currentItem = -1;
        int i = 0;
        int n = 0;
        while (true) {
            while (i < 9) {
                final ItemStack stackInSlot = AutoFish.getMinecraft16().player.inventory.getStackInSlot(n);
                if (!AutoFish.getMinecraft7().player.inventory.getStackInSlot(n).isEmpty() && stackInSlot.getItem() instanceof ItemFishingRod) {
                    final int n2 = currentItem = n;
                    if (n2 == -1) {
                        int n3 = -1;
                        int j = 9;
                        int n4 = 9;
                        while (true) {
                            while (j < 36) {
                                final ItemStack stackInSlot2 = AutoFish.getMinecraft12().player.inventory.getStackInSlot(n4);
                                if (!AutoFish.getMinecraft5().player.inventory.getStackInSlot(n4).isEmpty() && stackInSlot2.getItem() instanceof ItemFishingRod) {
                                    final int n5 = n3 = n4;
                                    if (n5 == -1) {
                                        return;
                                    }
                                    int currentItem2 = -1;
                                    int k = 0;
                                    int n6 = 0;
                                    while (k < 9) {
                                        if (AutoFish.getMinecraft().player.inventory.getStackInSlot(n6) == ItemStack.EMPTY) {
                                            currentItem2 = n6;
                                            break;
                                        }
                                        k = ++n6;
                                    }
                                    boolean b = false;
                                    if (currentItem2 == -1) {
                                        currentItem2 = AutoFish.getMinecraft6().player.inventory.currentItem;
                                        b = true;
                                    }
                                    final PlayerControllerMP playerController = AutoFish.getMinecraft10().playerController;
                                    final int n7 = n3;
                                    final int n8 = 0;
                                    playerController.windowClick(n8, n7, n8, ClickType.PICKUP, (EntityPlayer)AutoFish.getMinecraft15().player);
                                    final PlayerControllerMP playerController2 = AutoFish.getMinecraft18().playerController;
                                    final int n9 = 36 + currentItem2;
                                    final int n10 = 0;
                                    playerController2.windowClick(n10, n9, n10, ClickType.PICKUP, (EntityPlayer)AutoFish.getMinecraft2().player);
                                    if (b) {
                                        final PlayerControllerMP playerController3 = AutoFish.getMinecraft4().playerController;
                                        final int n11 = n3;
                                        final int n12 = 0;
                                        playerController3.windowClick(n12, n11, n12, ClickType.PICKUP, (EntityPlayer)AutoFish.getMinecraft17().player);
                                    }
                                    return;
                                }
                                else {
                                    j = ++n4;
                                }
                            }
                            final int n5 = n3;
                            continue;
                        }
                    }
                    if (AutoFish.getMinecraft8().player.inventory.currentItem != currentItem) {
                        AutoFish.getMinecraft9().player.inventory.currentItem = currentItem;
                        return;
                    }
                    if (AutoFish.b(this.k) > 0) {
                        AutoFish.B(this.k);
                        return;
                    }
                    if (AutoFish.getMinecraft3().player.fishEntity != null) {
                        AutoFish.e(this.k);
                        if (AutoFish.h(this.k) > 720) {
                            AutoFish.M(this.k);
                        }
                        if (AutoFish.getMinecraft14().player.fishEntity.caughtEntity != null) {
                            AutoFish.M(this.k);
                        }
                        if (AutoFish.M(this.k)) {
                            AutoFish.M(this.k);
                            if (AutoFish.C(this.k) >= 4) {
                                AutoFish.M(this.k);
                                AutoFish.M(this.k, false);
                            }
                        }
                        else if (AutoFish.C(this.k) != 0) {
                            AutoFish.i(this.k);
                        }
                        return;
                    }
                    AutoFish.M(this.k);
                    return;
                }
                else {
                    i = ++n;
                }
            }
            final int n2 = currentItem;
            continue;
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}

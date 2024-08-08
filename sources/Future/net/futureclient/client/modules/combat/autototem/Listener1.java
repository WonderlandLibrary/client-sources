package net.futureclient.client.modules.combat.autototem;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.combat.AutoTotem;
import net.futureclient.client.xe;
import net.futureclient.client.n;

public class Listener1 extends n<xe>
{
    public final AutoTotem k;
    
    public Listener1(final AutoTotem k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((xe)event);
    }
    
    @Override
    public void M(final xe xe) {
        this.k.e(String.format("AutoTotem §7[§F%s§7]", this.k.e()));
        if (!this.k.k) {
            AutoTotem.M(this.k).e();
        }
        if ((!(AutoTotem.getMinecraft12().currentScreen instanceof GuiContainer) || AutoTotem.getMinecraft10().currentScreen instanceof GuiInventory) && AutoTotem.getMinecraft14().player.getHeldItem(EnumHand.OFF_HAND).getItem() != Items.TOTEM_OF_UNDYING && !AutoTotem.getMinecraft9().player.isCreative()) {
            int i = 44;
            int n = 44;
            while (i >= 9) {
                if (AutoTotem.getMinecraft1().player.inventoryContainer.getSlot(n).getStack().getItem() == Items.TOTEM_OF_UNDYING) {
                    if (AutoTotem.getMinecraft13().player.getHeldItem(EnumHand.OFF_HAND).isEmpty()) {
                        this.k.k = true;
                        if (!AutoTotem.M(this.k).e(this.k.delay.B().floatValue() * 1000.0f)) {
                            break;
                        }
                        final PlayerControllerMP playerController = AutoTotem.getMinecraft2().playerController;
                        final int n2 = n;
                        final int n3 = 0;
                        playerController.windowClick(n3, n2, n3, ClickType.PICKUP, (EntityPlayer)AutoTotem.getMinecraft7().player);
                        if (AutoTotem.M(this.k).e(this.k.delay.B().floatValue() * 2000.0f)) {
                            final PlayerControllerMP playerController2 = AutoTotem.getMinecraft4().playerController;
                            final int n4 = 45;
                            final int n5 = 0;
                            playerController2.windowClick(n5, n4, n5, ClickType.PICKUP, (EntityPlayer)AutoTotem.getMinecraft11().player);
                            this.k.k = false;
                            return;
                        }
                        break;
                    }
                    else {
                        this.k.k = true;
                        if (!AutoTotem.M(this.k).e(this.k.delay.B().floatValue() * 1000.0f)) {
                            break;
                        }
                        final PlayerControllerMP playerController3 = AutoTotem.getMinecraft5().playerController;
                        final int n6 = n;
                        final int n7 = 0;
                        playerController3.windowClick(n7, n6, n7, ClickType.PICKUP, (EntityPlayer)AutoTotem.getMinecraft8().player);
                        if (!AutoTotem.M(this.k).e(this.k.delay.B().floatValue() * 2000.0f)) {
                            break;
                        }
                        final PlayerControllerMP playerController4 = AutoTotem.getMinecraft6().playerController;
                        final int n8 = 45;
                        final int n9 = 0;
                        playerController4.windowClick(n9, n8, n9, ClickType.PICKUP, (EntityPlayer)AutoTotem.getMinecraft3().player);
                        if (AutoTotem.M(this.k).e(this.k.delay.B().floatValue() * 3000.0f)) {
                            final PlayerControllerMP playerController5 = AutoTotem.getMinecraft().playerController;
                            final int n10 = n;
                            final int n11 = 0;
                            playerController5.windowClick(n11, n10, n11, ClickType.PICKUP, (EntityPlayer)AutoTotem.getMinecraft15().player);
                            this.k.k = false;
                            return;
                        }
                        break;
                    }
                }
                else {
                    i = --n;
                }
            }
        }
    }
}

package wwe.modules.player;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import wwe.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.client.multiplayer.*;

public class AutoEat extends qMIe
{
    public NumberValue hunger;
    public BooleanValue useGapples;
    public BooleanValue preferGapples;
    public boolean JWHM;
    
    public AutoEat() {
        super("Auto Eat", "Automatically Eats", 0, Category.PLAYER);
    }
    
    @Override
    public void HrDr() {
        this.hunger = new NumberValue("Hunger", this, "The hunger level to start eatting", 8.0, 0.0, 19.0);
        this.useGapples = new BooleanValue("Use Gapples", this, "Use god apples", false);
        this.preferGapples = new BooleanValue("Prefer Gapples", this, "Prefer god apples over normal food", false);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (Wrapper.mc.playerController.getCurrentGameType() == GameType.CREATIVE || Wrapper.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
            return;
        }
        if (this.JWHM && !Wrapper.mc.player.isHandActive()) {
            yYGD.rvRL(Wrapper.mc.gameSettings.keyBindUseItem, false);
            Wrapper.mc.playerController.processRightClick((EntityPlayer)Wrapper.mc.player, (World)Wrapper.mc.world, EnumHand.MAIN_HAND);
            this.JWHM = false;
        }
        if (this.JWHM) {
            return;
        }
        if (Wrapper.mc.player.getFoodStats().getFoodLevel() <= this.hunger.OnGi()) {
            final int egkr = this.Egkr();
            if (egkr == -1) {
                return;
            }
            if (Wrapper.mc.player.inventory.currentItem != egkr) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(egkr));
                Wrapper.mc.player.inventory.currentItem = egkr;
                Wrapper.mc.playerController.updateController();
            }
            if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood) {
                if (Wrapper.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE && this.useGapples.OnGi()) {
                    if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                        throw null;
                    }
                }
                else if (Wrapper.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE && !this.useGapples.OnGi()) {
                    if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                        throw null;
                    }
                    return;
                }
                yYGD.rvRL(Wrapper.mc.gameSettings.keyBindUseItem, true);
                Wrapper.mc.playerController.processRightClick((EntityPlayer)Wrapper.mc.player, (World)Wrapper.mc.world, EnumHand.MAIN_HAND);
                this.JWHM = true;
            }
        }
    }
    
    @Override
    public void LPxx() {
        this.JWHM = false;
    }
    
    @Override
    public void qBmy() {
        if (this.JWHM && Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            yYGD.rvRL(Wrapper.mc.gameSettings.keyBindUseItem, false);
        }
    }
    
    public int Egkr() {
        if (this.preferGapples.OnGi() && this.useGapples.OnGi()) {
            int i = 0;
            while (i < 9) {
                final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
                if (!jjZz.cKMx(stackInSlot) && stackInSlot.getItem() instanceof ItemFood && this.useGapples.OnGi() && stackInSlot.getItem() == Items.GOLDEN_APPLE) {
                    return i;
                }
                ++i;
                if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                    throw null;
                }
            }
            if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                throw null;
            }
        }
        else {
            int j = 0;
            while (j < 9) {
                final ItemStack stackInSlot2 = Wrapper.mc.player.inventory.getStackInSlot(j);
                Label_0259: {
                    if (!jjZz.cKMx(stackInSlot2) && stackInSlot2.getItem() instanceof ItemFood) {
                        if (stackInSlot2.getItem() == Items.GOLDEN_APPLE && this.useGapples.OnGi()) {
                            if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                                throw null;
                            }
                        }
                        else if (stackInSlot2.getItem() == Items.GOLDEN_APPLE && !this.useGapples.OnGi()) {
                            if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                                throw null;
                            }
                            break Label_0259;
                        }
                        return j;
                    }
                }
                ++j;
                if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                    throw null;
                }
            }
        }
        if (this.preferGapples.OnGi() && this.useGapples.OnGi()) {
            int k = 9;
            while (k < Wrapper.mc.player.inventory.mainInventory.size()) {
                final ItemStack stackInSlot3 = Wrapper.mc.player.inventory.getStackInSlot(k);
                if (!jjZz.cKMx(stackInSlot3) && stackInSlot3.getItem() instanceof ItemFood && this.useGapples.OnGi() && stackInSlot3.getItem() == Items.GOLDEN_APPLE) {
                    final PlayerControllerMP playerController = Wrapper.mc.playerController;
                    final int n = 0;
                    int n2;
                    if (k < 9) {
                        n2 = k + 36;
                        if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                            throw null;
                        }
                    }
                    else {
                        n2 = k;
                    }
                    playerController.windowClick(n, n2, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                    Wrapper.mc.playerController.windowClick(0, 36 + Wrapper.mc.player.inventory.currentItem, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                    final PlayerControllerMP playerController2 = Wrapper.mc.playerController;
                    final int n3 = 0;
                    int n4;
                    if (k < 9) {
                        n4 = k + 36;
                        if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                            throw null;
                        }
                    }
                    else {
                        n4 = k;
                    }
                    playerController2.windowClick(n3, n4, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                    return Wrapper.mc.player.inventory.currentItem;
                }
                ++k;
                if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                    throw null;
                }
            }
            if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                throw null;
            }
        }
        else {
            int l = 9;
            while (l < Wrapper.mc.player.inventory.mainInventory.size()) {
                final ItemStack stackInSlot4 = Wrapper.mc.player.inventory.getStackInSlot(l);
                Label_0795: {
                    if (!jjZz.cKMx(stackInSlot4) && stackInSlot4.getItem() instanceof ItemFood) {
                        if (stackInSlot4.getItem() == Items.GOLDEN_APPLE && this.useGapples.OnGi()) {
                            if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                                throw null;
                            }
                        }
                        else if (stackInSlot4.getItem() == Items.GOLDEN_APPLE && !this.useGapples.OnGi()) {
                            if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                                throw null;
                            }
                            break Label_0795;
                        }
                        final PlayerControllerMP playerController3 = Wrapper.mc.playerController;
                        final int n5 = 0;
                        int n6;
                        if (l < 9) {
                            n6 = l + 36;
                            if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                                throw null;
                            }
                        }
                        else {
                            n6 = l;
                        }
                        playerController3.windowClick(n5, n6, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                        Wrapper.mc.playerController.windowClick(0, 36 + Wrapper.mc.player.inventory.currentItem, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                        final PlayerControllerMP playerController4 = Wrapper.mc.playerController;
                        final int n7 = 0;
                        int n8;
                        if (l < 9) {
                            n8 = l + 36;
                            if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                                throw null;
                            }
                        }
                        else {
                            n8 = l;
                        }
                        playerController4.windowClick(n7, n8, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                        return Wrapper.mc.player.inventory.currentItem;
                    }
                }
                ++l;
                if (AutoEat.\u2003\u2002\u2007\u2001\u200d\u2005\u2001\u200a\u2004) {
                    throw null;
                }
            }
        }
        return -1;
    }
}

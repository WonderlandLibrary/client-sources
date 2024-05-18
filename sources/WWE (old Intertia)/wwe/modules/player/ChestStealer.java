package wwe.modules.player;

import wwe.modules.*;
import wwe.utils.*;
import wwe.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.passive.*;

public class ChestStealer extends qMIe
{
    public sbfc Xijd;
    public BooleanValue chest;
    public BooleanValue shulkerbox;
    public BooleanValue donkey;
    public BooleanValue random;
    public BooleanValue close;
    public NumberValue delay;
    
    public ChestStealer() {
        super("Chest Stealer", "Automatically steals items from chests", 0, Category.PLAYER);
        this.Xijd = new sbfc();
    }
    
    @Override
    public void HrDr() {
        this.chest = new BooleanValue("Chest", this, "Target chests", true);
        this.shulkerbox = new BooleanValue("Shulker box", this, "Target shulker boxes", true);
        this.donkey = new BooleanValue("Donkey", this, "Target donkeys and llamas", true);
        this.random = new BooleanValue("Random", this, "Randomly chooses a item", false);
        this.close = new BooleanValue("Close", this, "Auto close when chest is empty", true);
        this.delay = new NumberValue("Delay", this, "Delay from the items beening taken", 0.25, 0.0, 1.25);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (Wrapper.mc.currentScreen instanceof GuiChest && this.chest.OnGi()) {
            this.Xijd.gCgh();
            final GuiChest guiChest = (GuiChest)Wrapper.mc.currentScreen;
            final ArrayList<Integer> list = new ArrayList<Integer>();
            int i = 0;
            while (i < yYGD.kpCj(guiChest).getSizeInventory()) {
                if (yYGD.kpCj(guiChest).getStackInSlot(i) != null && !(yYGD.kpCj(guiChest).getStackInSlot(i).getItem() instanceof ItemAir)) {
                    if (!this.random.OnGi() && this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                        Wrapper.mc.playerController.windowClick(guiChest.inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)Wrapper.mc.player);
                        this.Xijd.BBwj();
                    }
                    if (this.random.OnGi()) {
                        list.add(i);
                    }
                }
                ++i;
                if (ChestStealer.\u200d) {
                    throw null;
                }
            }
            if (this.random.OnGi() && list.size() > 0 && this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                Wrapper.mc.playerController.windowClick(guiChest.inventorySlots.windowId, (int)list.get(new Random().nextInt(list.size())), 0, ClickType.QUICK_MOVE, (EntityPlayer)Wrapper.mc.player);
                this.Xijd.BBwj();
            }
            if (this.close.OnGi() && this.zVVq(guiChest)) {
                Wrapper.mc.player.closeScreen();
                this.Xijd.BBwj();
            }
        }
        if (Wrapper.mc.currentScreen instanceof GuiShulkerBox && this.shulkerbox.OnGi()) {
            this.Xijd.gCgh();
            final GuiShulkerBox guiShulkerBox = (GuiShulkerBox)Wrapper.mc.currentScreen;
            final ArrayList<Integer> list2 = new ArrayList<Integer>();
            int j = 0;
            while (j < yYGD.xZaV(guiShulkerBox).getSizeInventory()) {
                if (yYGD.xZaV(guiShulkerBox).getStackInSlot(j) != null && !(yYGD.xZaV(guiShulkerBox).getStackInSlot(j).getItem() instanceof ItemAir)) {
                    if (!this.random.OnGi() && this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                        Wrapper.mc.playerController.windowClick(guiShulkerBox.inventorySlots.windowId, j, 0, ClickType.QUICK_MOVE, (EntityPlayer)Wrapper.mc.player);
                        this.Xijd.BBwj();
                    }
                    if (this.random.OnGi()) {
                        list2.add(j);
                    }
                }
                ++j;
                if (ChestStealer.\u200d) {
                    throw null;
                }
            }
            if (this.random.OnGi() && list2.size() > 0 && this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                Wrapper.mc.playerController.windowClick(guiShulkerBox.inventorySlots.windowId, (int)list2.get(new Random().nextInt(list2.size())), 0, ClickType.QUICK_MOVE, (EntityPlayer)Wrapper.mc.player);
                this.Xijd.BBwj();
            }
            if (this.close.OnGi() && this.vZnr(guiShulkerBox)) {
                Wrapper.mc.player.closeScreen();
                this.Xijd.BBwj();
            }
        }
        if (Wrapper.mc.currentScreen instanceof GuiScreenHorseInventory && this.donkey.OnGi()) {
            this.Xijd.gCgh();
            final GuiScreenHorseInventory guiScreenHorseInventory = (GuiScreenHorseInventory)Wrapper.mc.currentScreen;
            if (yYGD.sKEZ(guiScreenHorseInventory) instanceof EntityDonkey || yYGD.sKEZ(guiScreenHorseInventory) instanceof EntityLlama) {
                final ArrayList<Integer> list3 = new ArrayList<Integer>();
                int n;
                if (yYGD.sKEZ(guiScreenHorseInventory) instanceof EntityLlama) {
                    n = 2;
                    if (ChestStealer.\u200d) {
                        throw null;
                    }
                }
                else {
                    n = 1;
                }
                int k = n;
                while (k < yYGD.Qdhw(guiScreenHorseInventory).getSizeInventory()) {
                    if (yYGD.Qdhw(guiScreenHorseInventory).getStackInSlot(k) != null && !(yYGD.Qdhw(guiScreenHorseInventory).getStackInSlot(k).getItem() instanceof ItemAir)) {
                        if (!this.random.OnGi() && this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                            Wrapper.mc.playerController.windowClick(guiScreenHorseInventory.inventorySlots.windowId, k, 0, ClickType.QUICK_MOVE, (EntityPlayer)Wrapper.mc.player);
                            this.Xijd.BBwj();
                        }
                        if (this.random.OnGi()) {
                            list3.add(k);
                        }
                    }
                    ++k;
                    if (ChestStealer.\u200d) {
                        throw null;
                    }
                }
                if (this.random.OnGi() && list3.size() > 0 && this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                    Wrapper.mc.playerController.windowClick(guiScreenHorseInventory.inventorySlots.windowId, (int)list3.get(new Random().nextInt(list3.size())), 0, ClickType.QUICK_MOVE, (EntityPlayer)Wrapper.mc.player);
                    this.Xijd.BBwj();
                }
                if (this.close.OnGi() && this.NiVD(guiScreenHorseInventory)) {
                    Wrapper.mc.player.closeScreen();
                    this.Xijd.BBwj();
                }
            }
        }
    }
    
    @Override
    public void LPxx() {
        this.Xijd.BBwj();
        super.LPxx();
    }
    
    public boolean zVVq(final GuiChest guiChest) {
        boolean b = true;
        int i = 0;
        while (i < yYGD.kpCj(guiChest).getSizeInventory()) {
            if (yYGD.kpCj(guiChest).getStackInSlot(i) != null && !(yYGD.kpCj(guiChest).getStackInSlot(i).getItem() instanceof ItemAir)) {
                b = false;
            }
            ++i;
            if (ChestStealer.\u200d) {
                throw null;
            }
        }
        return b;
    }
    
    public boolean vZnr(final GuiShulkerBox guiShulkerBox) {
        boolean b = true;
        int i = 0;
        while (i < yYGD.xZaV(guiShulkerBox).getSizeInventory()) {
            if (yYGD.xZaV(guiShulkerBox).getStackInSlot(i) != null && !(yYGD.xZaV(guiShulkerBox).getStackInSlot(i).getItem() instanceof ItemAir)) {
                b = false;
            }
            ++i;
            if (ChestStealer.\u200d) {
                throw null;
            }
        }
        return b;
    }
    
    public boolean NiVD(final GuiScreenHorseInventory guiScreenHorseInventory) {
        boolean b = true;
        int n;
        if (yYGD.sKEZ(guiScreenHorseInventory) instanceof EntityLlama) {
            n = 2;
            if (ChestStealer.\u200d) {
                throw null;
            }
        }
        else {
            n = 1;
        }
        int i = n;
        while (i < yYGD.Qdhw(guiScreenHorseInventory).getSizeInventory()) {
            if (yYGD.Qdhw(guiScreenHorseInventory).getStackInSlot(i) != null && !(yYGD.Qdhw(guiScreenHorseInventory).getStackInSlot(i).getItem() instanceof ItemAir)) {
                b = false;
            }
            ++i;
            if (ChestStealer.\u200d) {
                throw null;
            }
        }
        return b;
    }
}

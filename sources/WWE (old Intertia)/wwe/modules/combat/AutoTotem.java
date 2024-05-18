package wwe.modules.combat;

import wwe.*;
import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;

public class AutoTotem extends qMIe
{
    public ModeValue mode;
    public int lgIc;
    
    public AutoTotem() {
        super("Auto Totem", "Automatically places totem in off hand", 0, Category.COMBAT);
        this.lgIc = 0;
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("Dev");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Dev", list);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        this.owgW("Auto Totem " + TextFormatting.DARK_GRAY + this.JASE());
        if (this.mode.OnGi().equalsIgnoreCase("Dev")) {
            final ItemStack itemStackFromSlot = Wrapper.mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
            int n = 0;
            int i = 0;
            while (i < Wrapper.mc.player.inventory.mainInventory.size()) {
                if (Wrapper.mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                    ++n;
                }
                ++i;
                if (AutoTotem.\u200c\u200e\u2008\u2006\u2005\u2001) {
                    throw null;
                }
            }
            if (itemStackFromSlot.getItem() != Items.TOTEM_OF_UNDYING) {
                int j = 0;
                while (j < Wrapper.mc.player.inventory.mainInventory.size()) {
                    if (Wrapper.mc.player.inventory.getStackInSlot(j).getItem() == Items.TOTEM_OF_UNDYING && Wrapper.mc.currentScreen == null) {
                        final PlayerControllerMP playerController = Wrapper.mc.playerController;
                        final int n2 = 0;
                        int n3;
                        if (j < 9) {
                            n3 = j + 36;
                            if (AutoTotem.\u200c\u200e\u2008\u2006\u2005\u2001) {
                                throw null;
                            }
                        }
                        else {
                            n3 = j;
                        }
                        playerController.windowClick(n2, n3, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                        Wrapper.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                        if (AutoTotem.\u200c\u200e\u2008\u2006\u2005\u2001) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++j;
                        if (AutoTotem.\u200c\u200e\u2008\u2006\u2005\u2001) {
                            throw null;
                        }
                        continue;
                    }
                }
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            final ItemStack itemStackFromSlot2 = Wrapper.mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
            final NonNullList mainInventory = Wrapper.mc.player.inventory.mainInventory;
            int k = 0;
            while (k < mainInventory.size()) {
                if (mainInventory.get(k) != ItemStack.EMPTY && (itemStackFromSlot2 == null || itemStackFromSlot2.getItem() != Items.TOTEM_OF_UNDYING) && ((ItemStack)mainInventory.get(k)).getItem() == Items.TOTEM_OF_UNDYING) {
                    this.WCGE(k);
                    if (AutoTotem.\u200c\u200e\u2008\u2006\u2005\u2001) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++k;
                    if (AutoTotem.\u200c\u200e\u2008\u2006\u2005\u2001) {
                        throw null;
                    }
                    continue;
                }
            }
        }
    }
    
    public void WCGE(final int n) {
        if (Wrapper.mc.player.openContainer instanceof ContainerPlayer) {
            final PlayerControllerMP playerController = Wrapper.mc.playerController;
            final int n2 = 0;
            int n3;
            if (n < 9) {
                n3 = n + 36;
                if (AutoTotem.\u200c\u200e\u2008\u2006\u2005\u2001) {
                    throw null;
                }
            }
            else {
                n3 = n;
            }
            playerController.windowClick(n2, n3, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
            Wrapper.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
            final PlayerControllerMP playerController2 = Wrapper.mc.playerController;
            final int n4 = 0;
            int n5;
            if (n < 9) {
                n5 = n + 36;
                if (AutoTotem.\u200c\u200e\u2008\u2006\u2005\u2001) {
                    throw null;
                }
            }
            else {
                n5 = n;
            }
            playerController2.windowClick(n4, n5, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
        }
    }
    
    public int JASE() {
        if (Wrapper.mc.player == null || Wrapper.mc.player.inventoryContainer == null) {
            return 0;
        }
        int n = 0;
        int i = 0;
        while (i < Wrapper.mc.player.inventory.getSizeInventory()) {
            if (!Wrapper.mc.player.inventory.getStackInSlot(i).isEmpty() && Wrapper.mc.player.inventory.getStackInSlot(i).getItem().equals(Items.TOTEM_OF_UNDYING)) {
                n += Wrapper.mc.player.inventory.getStackInSlot(i).getCount();
            }
            ++i;
            if (AutoTotem.\u200c\u200e\u2008\u2006\u2005\u2001) {
                throw null;
            }
        }
        return n;
    }
}

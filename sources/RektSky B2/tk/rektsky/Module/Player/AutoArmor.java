package tk.rektsky.Module.Player;

import tk.rektsky.Module.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import tk.rektsky.Utils.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import tk.rektsky.Utils.Item.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class AutoArmor extends Module
{
    public IntSetting delaySetting;
    int ticks;
    
    public AutoArmor() {
        super("AutoArmor", "Automatically equips armor.", 0, Category.PLAYER);
        this.delaySetting = new IntSetting("Delay", 1, 20, 10);
        this.ticks = 0;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof WorldTickEvent) {
            ++this.ticks;
            if (this.ticks % this.delaySetting.getValue() != 0) {
                return;
            }
            final ArrayList<Slot> slots = ContainerUtil.getAllNonNullItems(this.mc.thePlayer.inventoryContainer);
            final ArrayList<Slot> helmets = new ArrayList<Slot>();
            final ArrayList<Slot> chestplates = new ArrayList<Slot>();
            final ArrayList<Slot> leggings = new ArrayList<Slot>();
            final ArrayList<Slot> boots = new ArrayList<Slot>();
            int s;
            int s2;
            final ArrayList<Slot> list;
            int s3;
            final ArrayList<Slot> list2;
            int s4;
            final ArrayList<Slot> list3;
            int s5;
            final ArrayList<Slot> list4;
            slots.forEach(slot -> {
                if (slot.getStack().getItem() instanceof ItemArmor) {
                    s = 0;
                    if (((ItemArmor)slot.getStack().getItem()).getUnlocalizedName().contains("helmet")) {
                        s2 = 3;
                        list.add(slot);
                    }
                    if (((ItemArmor)slot.getStack().getItem()).getUnlocalizedName().contains("chest")) {
                        s3 = 2;
                        list2.add(slot);
                    }
                    if (((ItemArmor)slot.getStack().getItem()).getUnlocalizedName().contains("leg")) {
                        s4 = 1;
                        list3.add(slot);
                    }
                    if (((ItemArmor)slot.getStack().getItem()).getUnlocalizedName().contains("boot")) {
                        s5 = 0;
                        list4.add(slot);
                    }
                }
                return;
            });
            final Slot[] helmetss = helmets.toArray(new Slot[0]);
            final Slot[] chestplatess = chestplates.toArray(new Slot[0]);
            final Slot[] leggingss = leggings.toArray(new Slot[0]);
            final Slot[] bootss = boots.toArray(new Slot[0]);
            Arrays.sort(helmetss, new ItemsUtil.ArmorComparator());
            Arrays.sort(chestplatess, new ItemsUtil.ArmorComparator());
            Arrays.sort(leggingss, new ItemsUtil.ArmorComparator());
            Arrays.sort(bootss, new ItemsUtil.ArmorComparator());
            if (helmetss.length >= 1 && this.mc.thePlayer.getCurrentArmor(3) != helmetss[0].getStack()) {
                if (this.mc.thePlayer.getCurrentArmor(3) != null) {
                    this.mc.playerController.windowClick(0, 5, 1, 1, this.mc.thePlayer);
                }
                this.mc.playerController.windowClick(0, helmetss[0].slotNumber, 1, 1, this.mc.thePlayer);
                return;
            }
            if (chestplatess.length >= 1 && this.mc.thePlayer.getCurrentArmor(2) != chestplatess[0].getStack()) {
                if (this.mc.thePlayer.getCurrentArmor(2) != null) {
                    this.mc.playerController.windowClick(0, 6, 1, 1, this.mc.thePlayer);
                }
                this.mc.playerController.windowClick(0, chestplatess[0].slotNumber, 1, 1, this.mc.thePlayer);
                return;
            }
            if (leggingss.length >= 1 && this.mc.thePlayer.getCurrentArmor(1) != leggingss[0].getStack()) {
                if (this.mc.thePlayer.getCurrentArmor(1) != null) {
                    this.mc.playerController.windowClick(0, 7, 1, 1, this.mc.thePlayer);
                }
                this.mc.playerController.windowClick(0, leggingss[0].slotNumber, 1, 1, this.mc.thePlayer);
                return;
            }
            if (bootss.length >= 1 && this.mc.thePlayer.getCurrentArmor(0) != bootss[0].getStack()) {
                if (this.mc.thePlayer.getCurrentArmor(0) != null) {
                    this.mc.playerController.windowClick(0, 8, 1, 1, this.mc.thePlayer);
                }
                this.mc.playerController.windowClick(0, bootss[0].slotNumber, 1, 1, this.mc.thePlayer);
            }
        }
    }
}

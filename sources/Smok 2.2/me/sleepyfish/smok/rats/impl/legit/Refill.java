package me.sleepyfish.smok.rats.impl.legit;

import maxstats.weave.event.EventTick;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.rats.settings.SpaceSetting;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;

import java.awt.*;

// Class from SMok Client by SleepyFish
public class Refill extends Rat {

    DoubleSetting cpsMin;
    DoubleSetting cpsMax;

    BoolSetting invOnly;

    BoolSetting soups;
    BoolSetting potions;
    BoolSetting drinks;
    BoolSetting gapples;
    BoolSetting food;

    private Timer timer = new Timer();
    private Robot robot;

    public Refill() {
        super("Refill", Rat.Category.Legit, "Refills items from Inventory");
    }

    @Override
    public void setup() {
        this.addSetting(this.cpsMin = new DoubleSetting("CPS Min", 7.0, 1.0, 20.0, 1.0));
        this.addSetting(this.cpsMax = new DoubleSetting("CPS Max", 11.0, 2.0, 25.0, 1.0));

        this.addSetting(this.invOnly = new BoolSetting("Inventory only", true));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.potions = new BoolSetting("Move Potions", "Like the heal throw potions", true));
        this.addSetting(this.drinks = new BoolSetting("Move Drinks", "Like the speed potion", false));
        this.addSetting(this.soups = new BoolSetting("Move Soups", true));
        this.addSetting(this.gapples = new BoolSetting("Move Gapples", false));
        this.addSetting(this.food = new BoolSetting("Move Food", false));

        try {
            this.robot = new Robot();
        } catch (Exception e) {
            ClientUtils.addMessage("Error: Refill robot");
        }
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (this.invOnly.isEnabled()) {
            if (!Utils.inInv())
                return;
        }

        for (Slot slot : mc.thePlayer.openContainer.inventorySlots) {
            if (slot != null && slot.getStack() != null && slot.getStack().getItem() != null) {

                if (this.timer.cpsTimer(this.cpsMin.getValueToInt(), this.cpsMax.getValueToInt())) {
                    Item item = slot.getStack().getItem();
                    String itemName = item.getItemStackDisplayName(slot.getStack()).toLowerCase();

                    if (this.soups.isEnabled()) {
                        if (!(item instanceof ItemSoup))
                            continue;
                    }

                    if (this.potions.isEnabled()) {
                        if (!(item instanceof ItemPotion && itemName.startsWith("splash potion of")))
                            continue;
                    }

                    if (this.drinks.isEnabled()) {
                        if (!(item instanceof ItemPotion && itemName.startsWith("potion of")))
                            continue;
                    }

                    if (this.gapples.isEnabled()) {
                        if (!(item instanceof ItemAppleGold))
                            continue;
                    }

                    if (this.food.isEnabled()) {
                        if (!(item instanceof ItemFood && !itemName.startsWith("raw")))
                            continue;
                    }

                    if (slot.slotNumber > 8) // 36
                        return;

                    Utils.shiftClick(slot.slotNumber);
                    this.timer.reset();
                }
            }
        }
    }

}
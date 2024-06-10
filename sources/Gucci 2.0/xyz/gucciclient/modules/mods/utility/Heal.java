package xyz.gucciclient.modules.mods.utility;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.utils.*;
import xyz.gucciclient.utils.Timer;
import xyz.gucciclient.values.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.awt.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Heal extends Module
{
    private int currentItem;
    private int vl;
    private int stage;
    private Timer timer;
    private BooleanValue pots;
    private BooleanValue soup;
    private BooleanValue ThrowBowls;
    private NumberValue Delay;
    
    public Heal() {
        super(Modules.Heal.name(), 0, Category.UTILITY);
        this.currentItem = 0;
        this.vl = 0;
        this.stage = 0;
        this.timer = new Timer();
        this.pots = new BooleanValue("Pots", true);
        this.soup = new BooleanValue("Soup", true);
        this.ThrowBowls = new BooleanValue("Throw bowls", true);
        this.addValue(this.Delay = new NumberValue("Delay", 50.0, 25.0, 1000.0));
        this.addBoolean(this.pots);
        this.addBoolean(this.soup);
        this.addBoolean(this.ThrowBowls);
    }
    
    @Override
    public void onEnable() {
        this.currentItem = this.mc.thePlayer.inventory.currentItem;
        if (this.mc.thePlayer.getHealth() <= 6.0f) {
            this.vl = 2;
        }
        else {
            if (this.mc.thePlayer.getHealth() > 12.0f) {
                this.toggle();
                return;
            }
            this.vl = 1;
        }
        if (this.switchItem()) {
            this.stage = 1;
        }
        else {
            this.toggle();
        }
    }
    
    @Override
    public void onDisable() {
        this.stage = 0;
        this.vl = 0;
        this.mc.thePlayer.inventory.currentItem = this.currentItem;
    }
    
    private boolean isPot(final ItemStack stack) {
        if (stack != null && stack.getItem() == Items.potionitem && ItemPotion.isSplash(stack.getItemDamage())) {
            for (final Object o : ((ItemPotion)stack.getItem()).getEffects(stack)) {
                if (((PotionEffect)o).getPotionID() == Potion.heal.id) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isItemSoup(final ItemStack stack) {
        return stack != null && stack.getItem() == Items.mushroom_stew;
    }
    
    private boolean isItemBowl(final ItemStack stack) {
        return stack != null && stack.getItem() == Items.bowl;
    }
    
    private boolean isValidItem(final ItemStack stack) {
        return (this.soup.getState() && this.isItemSoup(stack)) || (this.pots.getState() && this.isPot(stack));
    }
    
    private boolean switchItem() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.thePlayer.inventory.mainInventory[i];
            if (this.isValidItem(stack)) {
                this.mc.thePlayer.inventory.currentItem = i;
                return true;
            }
        }
        return false;
    }
    
    private void count() {
        --this.vl;
        if (this.vl > 0) {
            if (this.switchItem()) {
                this.stage = 1;
            }
            else {
                this.toggle();
            }
        }
        else {
            this.toggle();
        }
    }
    
    @SubscribeEvent
    public void Tick(final TickEvent.ClientTickEvent event) throws Exception {
        if (this.stage == 1 && this.timer.hasReached(this.Delay.getValue())) {
            if (!this.isValidItem(this.mc.thePlayer.inventory.getCurrentItem())) {
                this.toggle();
                return;
            }
            final Robot bot = new Robot();
            bot.mousePress(4);
            bot.mouseRelease(4);
            this.stage = 2;
            this.timer.reset();
        }
        if (this.stage == 2 && this.timer.hasReached(this.Delay.getValue())) {
            if (this.ThrowBowls.getState()) {
                if (!this.isItemBowl(this.mc.thePlayer.inventory.getCurrentItem()) && !this.isItemSoup(this.mc.thePlayer.inventory.getCurrentItem())) {
                    this.toggle();
                    return;
                }
                this.mc.thePlayer.dropOneItem(true);
                this.stage = 3;
                this.timer.reset();
            }
            else {
                this.count();
                this.timer.reset();
            }
        }
        else if (this.stage == 3 && this.timer.hasReached(this.Delay.getValue())) {
            this.count();
            this.timer.reset();
        }
    }
}

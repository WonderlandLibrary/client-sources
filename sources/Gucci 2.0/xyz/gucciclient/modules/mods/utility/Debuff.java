package xyz.gucciclient.modules.mods.utility;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import xyz.gucciclient.utils.*;
import xyz.gucciclient.utils.Timer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.awt.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Debuff extends Module
{
    private Timer timer;
    private int currentItem;
    private int count;
    private int stage;
    private NumberValue delay;
    
    public Debuff() {
        super(Modules.Debuff.name(), 0, Category.UTILITY);
        this.timer = new Timer();
        this.currentItem = 0;
        this.count = 0;
        this.stage = 0;
        this.addValue(this.delay = new NumberValue("Delay", 50.0, 25.0, 500.0));
    }
    
    @Override
    public void onEnable() {
        this.currentItem = Wrapper.getPlayer().inventory.currentItem;
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
        this.count = 0;
        Wrapper.getPlayer().inventory.currentItem = this.currentItem;
    }
    
    private boolean isPot(final ItemStack stack) {
        if (stack != null && stack.getItem() == Items.potionitem && ItemPotion.isSplash(stack.getItemDamage())) {
            for (final Object o : ((ItemPotion)stack.getItem()).getEffects(stack)) {
                final PotionEffect e = (PotionEffect)o;
                if (e.getPotionID() == Potion.weakness.id || e.getPotionID() == Potion.poison.id || e.getPotionID() == Potion.moveSlowdown.id || e.getPotionID() == Potion.blindness.id || e.getPotionID() == Potion.wither.id) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean switchItem() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Wrapper.getPlayer().inventory.mainInventory[i];
            if (this.isPot(stack)) {
                Wrapper.getPlayer().inventory.currentItem = i;
                return true;
            }
        }
        return false;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) throws Exception {
        if (this.stage == 1 && this.timer.hasReached(this.delay.getValue())) {
            if (!this.isPot(Wrapper.getPlayer().inventory.getCurrentItem())) {
                this.toggle();
                return;
            }
            final Robot bot = new Robot();
            bot.mousePress(4);
            bot.mouseRelease(4);
            this.stage = 2;
            this.timer.reset();
        }
        if (this.stage == 2 && this.timer.hasReached(this.delay.getValue())) {
            if (this.switchItem()) {
                this.stage = 1;
                this.timer.reset();
            }
            else {
                this.toggle();
            }
        }
    }
}

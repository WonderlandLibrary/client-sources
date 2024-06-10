package xyz.gucciclient.modules.mods.utility;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import xyz.gucciclient.utils.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.awt.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AgroPearl extends Module
{
    private int stage;
    private Timer timer;
    private int currentItem;
    private NumberValue delay;
    
    public AgroPearl() {
        super(Modules.AgroPearl.name(), 0, Category.UTILITY);
        this.stage = 0;
        this.timer = new Timer();
        this.currentItem = 0;
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
        Wrapper.getPlayer().inventory.currentItem = this.currentItem;
    }
    
    private boolean isEnderPearl(final ItemStack stack) {
        return stack != null && stack.getItem() == Items.ender_pearl;
    }
    
    private boolean switchItem() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Wrapper.getPlayer().inventory.mainInventory[i];
            if (this.isEnderPearl(stack)) {
                Wrapper.getPlayer().inventory.currentItem = i;
                return true;
            }
        }
        return false;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) throws Exception {
        if (this.stage == 1 && this.timer.hasReached(this.delay.getValue())) {
            if (!this.isEnderPearl(Wrapper.getPlayer().inventory.getCurrentItem())) {
                this.toggle();
                return;
            }
            final Robot bot = new Robot();
            if (Wrapper.getPlayer().getHealth() > 5.0f) {
                bot.mousePress(4);
                bot.mouseRelease(4);
                this.stage = 2;
                this.timer.reset();
            }
        }
        if (this.stage == 2 && this.timer.hasReached(this.delay.getValue())) {
            this.toggle();
            this.timer.reset();
        }
    }
}

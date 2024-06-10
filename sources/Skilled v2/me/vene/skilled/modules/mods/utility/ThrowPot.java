package me.vene.skilled.modules.mods.utility;
 
 import java.awt.AWTException;
 import java.awt.Robot;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.utilities.TimerUtil;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.init.Items;
 import net.minecraft.item.ItemPotion;
 import net.minecraft.item.ItemStack;
 import net.minecraft.potion.Potion;
 import net.minecraft.potion.PotionEffect;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class ThrowPot extends Module
{
    private int currentItem;
    private int vl;
    private int stage;
    private TimerUtil timer;
    private NumberValue Delay;
    private Minecraft mc;
    
    public ThrowPot() {
        super(StringRegistry.register("ThrowPot"), 0, Category.U);
        this.mc = Minecraft.getMinecraft();
        this.currentItem = 0;
        this.vl = 0;
        this.stage = 0;
        this.timer = new TimerUtil();
        this.addValue(this.Delay = new NumberValue(StringRegistry.register("Delay"), 50.0, 25.0, 1000.0));
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
    
    private boolean isValidItem(final ItemStack stack) {
        return this.isPot(stack);
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
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent e) {
        if (this.stage == 1 && this.timer.hasReached((float)this.Delay.getValue())) {
            if (!this.isValidItem(this.mc.thePlayer.inventory.getCurrentItem())) {
                this.toggle();
                return;
            }
            Robot bot = null;
            try {
                bot = new Robot();
            }
            catch (AWTException ex) {}
            bot.mousePress(4);
            bot.mouseRelease(4);
            this.stage = 2;
            this.timer.reset();
        }
        if (this.stage == 2 && this.timer.hasReached((float)this.Delay.getValue())) {
            this.count();
            this.timer.reset();
        }
        else if (this.stage == 3 && this.timer.hasReached((float)this.Delay.getValue())) {
            this.count();
            this.timer.reset();
        }
    }
}

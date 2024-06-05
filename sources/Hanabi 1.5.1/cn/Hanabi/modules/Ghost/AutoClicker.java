package cn.Hanabi.modules.Ghost;

import ClassSub.*;
import java.util.*;
import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.injection.interfaces.*;
import cn.Hanabi.events.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;

public class AutoClicker extends Mod
{
    private Class205 left;
    private Class205 right;
    Random random;
    public static boolean isClicking;
    public boolean isDone;
    public int timer;
    public Value<Double> maxCps;
    public Value<Double> minCps;
    public Value<Boolean> blockHit;
    public Value<Boolean> jitter;
    
    
    public AutoClicker() {
        super("AutoClicker", Category.GHOST);
        this.left = new Class205();
        this.right = new Class205();
        this.random = new Random();
        this.isDone = true;
        this.maxCps = new Value<Double>("AutoClicker_MaxCPS", 12.0, 1.0, 20.0, 1.0);
        this.minCps = new Value<Double>("AutoClicker_MinCPS", 8.0, 1.0, 20.0, 1.0);
        this.blockHit = new Value<Boolean>("AutoClicker_BlockHit", false);
        this.jitter = new Value<Boolean>("AutoClicker_Jitter", false);
    }
    
    public void onEnable() {
        this.isDone = true;
        this.timer = 0;
        super.onEnable();
    }
    
    public void onDisable() {
        this.isDone = true;
        super.onDisable();
    }
    
    private long getDelay() {
        return (long)((int)(Object)this.maxCps.getValueState() + this.random.nextDouble() * ((int)(Object)this.minCps.getValueState() - (int)(Object)this.maxCps.getValueState()));
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (AutoClicker.mc.thePlayer != null) {
            AutoClicker.isClicking = false;
            if ((int)(Object)this.minCps.getValueState() > (int)(Object)this.maxCps.getValueState()) {
                this.minCps.setValueState((double)this.maxCps.getValueState());
            }
            if (((IKeyBinding)AutoClicker.mc.gameSettings.keyBindAttack).getPress() && AutoClicker.mc.thePlayer.isUsingItem()) {
                this.swingItemNoPacket();
            }
            if (((IKeyBinding)AutoClicker.mc.gameSettings.keyBindAttack).getPress() && !AutoClicker.mc.thePlayer.isUsingItem() && this.left.isDelayComplete(Double.valueOf(1000.0 / this.getDelay()))) {
                if (this.jitter.getValueState()) {
                    this.jitter(this.random);
                }
                ((IMinecraft)AutoClicker.mc).setClickCounter(0);
                ((IMinecraft)AutoClicker.mc).runCrinkMouse();
                AutoClicker.isClicking = true;
                this.left.reset();
            }
        }
        if (!this.isDone) {
            switch (this.timer) {
                case 0: {
                    ((IKeyBinding)AutoClicker.mc.gameSettings.keyBindUseItem).setPress(false);
                    break;
                }
                case 1:
                case 2: {
                    ((IKeyBinding)AutoClicker.mc.gameSettings.keyBindUseItem).setPress(true);
                    break;
                }
                case 3: {
                    ((IKeyBinding)AutoClicker.mc.gameSettings.keyBindUseItem).setPress(false);
                    this.isDone = true;
                    this.timer = -1;
                    break;
                }
            }
            ++this.timer;
        }
    }
    
    public void swingItemNoPacket() {
        if (!AutoClicker.mc.thePlayer.isSwingInProgress || AutoClicker.mc.thePlayer.swingProgressInt >= ((IEntityLivingBase)AutoClicker.mc.thePlayer).runGetArmSwingAnimationEnd() / 2 || AutoClicker.mc.thePlayer.swingProgressInt < 0) {
            AutoClicker.mc.thePlayer.swingProgressInt = -1;
            AutoClicker.mc.thePlayer.isSwingInProgress = true;
        }
    }
    
    @EventTarget
    public void onCrink(final EventClickMouse eventClickMouse) {
        final ItemStack getCurrentEquippedItem = AutoClicker.mc.thePlayer.getCurrentEquippedItem();
        if (getCurrentEquippedItem != null && this.blockHit.getValueState() && getCurrentEquippedItem.getItem() instanceof ItemSword && !AutoClicker.mc.thePlayer.isUsingItem()) {
            if (!this.isDone || this.timer > 0) {
                return;
            }
            this.isDone = false;
        }
    }
    
    public void jitter(final Random random) {
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                final EntityPlayerSP thePlayer = AutoClicker.mc.thePlayer;
                thePlayer.rotationPitch -= (float)(random.nextFloat() * 0.6);
            }
            else {
                final EntityPlayerSP thePlayer2 = AutoClicker.mc.thePlayer;
                thePlayer2.rotationPitch += (float)(random.nextFloat() * 0.6);
            }
        }
        else if (random.nextBoolean()) {
            final EntityPlayerSP thePlayer3 = AutoClicker.mc.thePlayer;
            thePlayer3.rotationYaw -= (float)(random.nextFloat() * 0.6);
        }
        else {
            final EntityPlayerSP thePlayer4 = AutoClicker.mc.thePlayer;
            thePlayer4.rotationYaw += (float)(random.nextFloat() * 0.6);
        }
    }
    
    static {
        AutoClicker.isClicking = false;
    }
}

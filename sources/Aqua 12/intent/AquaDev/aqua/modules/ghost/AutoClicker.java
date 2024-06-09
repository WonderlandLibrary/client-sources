// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.ghost;

import org.lwjgl.input.Mouse;
import net.minecraft.util.MathHelper;
import java.util.Random;
import events.listeners.EventUpdate;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import intent.AquaDev.aqua.modules.Module;

public class AutoClicker extends Module
{
    TimeUtil timeUtil;
    public boolean attack;
    
    public AutoClicker() {
        super("AutoClicker", Type.Combat, "AutoClicker", 0, Category.Ghost);
        this.timeUtil = new TimeUtil();
        System.out.println("AutoClicker::init");
    }
    
    @Override
    public void setup() {
        Aqua.setmgr.register(new Setting("OnlyLeftClick", this, true));
        Aqua.setmgr.register(new Setting("1.9", this, false));
        Aqua.setmgr.register(new Setting("minCPS", this, 8.0, 1.0, 20.0, true));
        Aqua.setmgr.register(new Setting("maxCPS", this, 19.0, 1.0, 20.0, true));
    }
    
    @Override
    public void onEnable() {
        this.attack = false;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        this.attack = false;
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate) {
            final float minCPS = (float)Aqua.setmgr.getSetting("AutoClickerminCPS").getCurrentNumber();
            final float maxCPS = (float)Aqua.setmgr.getSetting("AutoClickermaxCPS").getCurrentNumber();
            final float CPS = (float)MathHelper.getRandomDoubleInRange(new Random(), minCPS, maxCPS);
            AutoClicker.mc.gameSettings.keyBindAttack.pressed = false;
            if (Mouse.isButtonDown(0)) {
                this.attack = true;
            }
            else {
                this.attack = false;
            }
            if (!Aqua.setmgr.getSetting("AutoClicker1.9").isState()) {
                if (this.timeUtil.hasReached((long)(1000.0f / CPS))) {
                    if (Aqua.setmgr.getSetting("AutoClickerOnlyLeftClick").isState()) {
                        if (this.attack) {
                            AutoClicker.mc.clickMouse();
                        }
                    }
                    else {
                        AutoClicker.mc.clickMouse();
                    }
                    this.timeUtil.reset();
                }
            }
            else if (Aqua.setmgr.getSetting("AutoClicker1.9").isState()) {
                if (Aqua.setmgr.getSetting("AutoClickerOnlyLeftClick").isState()) {
                    if (this.attack && AutoClicker.mc.thePlayer.ticksExisted % 11 == 0) {
                        AutoClicker.mc.clickMouse();
                    }
                }
                else if (AutoClicker.mc.thePlayer.ticksExisted % 11 == 0) {
                    AutoClicker.mc.clickMouse();
                }
            }
        }
    }
}

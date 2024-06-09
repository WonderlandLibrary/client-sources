// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.ghost;

import net.minecraft.util.MathHelper;
import java.util.Random;
import events.listeners.EventUpdate;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import intent.AquaDev.aqua.modules.Module;

public class Triggerbot extends Module
{
    TimeUtil timeUtil;
    
    public Triggerbot() {
        super("Triggerbot", Type.Combat, "Triggerbot", 0, Category.Ghost);
        this.timeUtil = new TimeUtil();
    }
    
    @Override
    public void setup() {
        Aqua.setmgr.register(new Setting("minCPS", this, 8.0, 1.0, 20.0, true));
        Aqua.setmgr.register(new Setting("maxCPS", this, 19.0, 1.0, 20.0, true));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate) {
            final float minCPS = (float)Aqua.setmgr.getSetting("TriggerbotminCPS").getCurrentNumber();
            final float maxCPS = (float)Aqua.setmgr.getSetting("TriggerbotmaxCPS").getCurrentNumber();
            final float CPS = (float)MathHelper.getRandomDoubleInRange(new Random(), minCPS, maxCPS);
            if (this.timeUtil.hasReached((long)(1000.0f / CPS)) && Triggerbot.mc.objectMouseOver.entityHit != null) {
                Triggerbot.mc.clickMouse();
                this.timeUtil.reset();
            }
        }
    }
}

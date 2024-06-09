// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.combat;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import java.util.Objects;
import events.listeners.EventUpdate;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.entity.player.EntityPlayer;
import intent.AquaDev.aqua.modules.Module;

public class TimerRange extends Module
{
    public static EntityPlayer target;
    String inRange;
    
    public TimerRange() {
        super("TimerRange", Type.Combat, "TimerRange", 0, Category.Combat);
        Aqua.setmgr.register(new Setting("Range", this, 3.0, 3.0, 6.0, false));
        Aqua.setmgr.register(new Setting("Boost", this, 12.0, 1.0, 50.0, false));
        Aqua.setmgr.register(new Setting("OnHitTPTicks", this, 18.0, 0.0, 100.0, false));
        Aqua.setmgr.register(new Setting("OnHitTP", this, false));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        TimerRange.mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            if (!Aqua.setmgr.getSetting("TimerRangeOnHitTP").isState()) {
                if (Aqua.moduleManager.getModuleByName("Killaura").isToggled()) {
                    if (TimerRange.target == null) {
                        this.inRange = "out";
                    }
                    if (TimerRange.target != null) {
                        this.inRange = "in";
                    }
                    TimerRange.target = this.searchTargets();
                    if (TimerRange.target != null && Objects.equals(this.inRange, "out")) {
                        this.inRange = "teleport";
                    }
                    if (TimerRange.target == null && Objects.equals(this.inRange, "in")) {
                        this.inRange = "slow";
                    }
                    if (Objects.equals(this.inRange, "teleport")) {
                        final float boost = (float)Aqua.setmgr.getSetting("TimerRangeBoost").getCurrentNumber();
                        TimerRange.mc.timer.timerSpeed = boost;
                    }
                    else if (Objects.equals(this.inRange, "slow")) {
                        TimerRange.mc.timer.timerSpeed = 0.2f;
                    }
                    else {
                        TimerRange.mc.timer.timerSpeed = 1.0f;
                    }
                }
                else {
                    TimerRange.mc.timer.timerSpeed = 1.0f;
                }
            }
            else if (Aqua.moduleManager.getModuleByName("Killaura").isToggled()) {
                if (TimerRange.target == null) {
                    this.inRange = "out";
                }
                if (TimerRange.target != null) {
                    this.inRange = "in";
                }
                TimerRange.target = Killaura.target;
                if (TimerRange.target != null && Objects.equals(this.inRange, "out")) {
                    this.inRange = "teleport";
                }
                if (TimerRange.target == null && Objects.equals(this.inRange, "in")) {
                    this.inRange = "slow";
                }
                if (Killaura.target != null) {
                    final float boostTicks = (float)Aqua.setmgr.getSetting("TimerRangeOnHitTPTicks").getCurrentNumber();
                    if (Objects.equals(this.inRange, "teleport") || (Killaura.target.hurtTime != 0 && TimerRange.mc.thePlayer.ticksExisted % boostTicks == 0.0f && TimerRange.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) == null)) {
                        final float boost2 = (float)Aqua.setmgr.getSetting("TimerRangeBoost").getCurrentNumber();
                        TimerRange.mc.timer.timerSpeed = boost2;
                    }
                    else if (Objects.equals(this.inRange, "slow")) {
                        TimerRange.mc.timer.timerSpeed = 0.2f;
                    }
                    else {
                        TimerRange.mc.timer.timerSpeed = 1.0f;
                    }
                }
                else {
                    TimerRange.mc.timer.timerSpeed = 1.0f;
                }
            }
            else {
                TimerRange.mc.timer.timerSpeed = 1.0f;
            }
        }
    }
    
    public EntityPlayer searchTargets() {
        final float range = (float)Aqua.setmgr.getSetting("TimerRangeRange").getCurrentNumber();
        EntityPlayer player = null;
        double closestDist = 100000.0;
        for (final Entity o : TimerRange.mc.theWorld.loadedEntityList) {
            if (!o.getName().equals(TimerRange.mc.thePlayer.getName()) && o instanceof EntityPlayer && TimerRange.mc.thePlayer.getDistanceToEntity(o) < range) {
                final double dist = TimerRange.mc.thePlayer.getDistanceToEntity(o);
                if (dist >= closestDist) {
                    continue;
                }
                closestDist = dist;
                player = (EntityPlayer)o;
            }
        }
        return player;
    }
    
    static {
        TimerRange.target = null;
    }
}

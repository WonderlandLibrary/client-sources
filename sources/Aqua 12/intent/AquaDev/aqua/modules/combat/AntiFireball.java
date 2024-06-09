// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.combat;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import java.util.Random;
import events.listeners.EventUpdate;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import net.minecraft.entity.projectile.EntityFireball;
import intent.AquaDev.aqua.modules.Module;

public class AntiFireball extends Module
{
    public static EntityFireball target;
    public TimeUtil timeUtil;
    
    public AntiFireball() {
        super("AntiFireball", Type.Combat, "AntiFireball", 0, Category.Combat);
        this.timeUtil = new TimeUtil();
        Aqua.setmgr.register(new Setting("Range", this, 6.0, 3.0, 6.0, false));
        Aqua.setmgr.register(new Setting("minCPS", this, 17.0, 1.0, 20.0, false));
        Aqua.setmgr.register(new Setting("maxCPS", this, 19.0, 1.0, 20.0, false));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        this.timeUtil.reset();
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate) {
            final float minCPS = (float)Aqua.setmgr.getSetting("AntiFireballminCPS").getCurrentNumber();
            final float maxCPS = (float)Aqua.setmgr.getSetting("AntiFireballmaxCPS").getCurrentNumber();
            final float CPS = (float)MathHelper.getRandomDoubleInRange(new Random(), minCPS, maxCPS);
            AntiFireball.target = this.searchTargets();
            if (AntiFireball.target != null && Killaura.target == null && this.timeUtil.hasReached((long)(1000.0f / CPS))) {
                AntiFireball.mc.thePlayer.swingItem();
                AntiFireball.mc.playerController.attackEntity(AntiFireball.mc.thePlayer, AntiFireball.target);
                this.timeUtil.reset();
            }
        }
    }
    
    public EntityFireball searchTargets() {
        final float range = (float)Aqua.setmgr.getSetting("AntiFireballRange").getCurrentNumber();
        EntityFireball player = null;
        double closestDist = 100000.0;
        for (final Entity o : AntiFireball.mc.theWorld.loadedEntityList) {
            if (!o.getName().equals(AntiFireball.mc.thePlayer.getName()) && o instanceof EntityFireball && AntiFireball.mc.thePlayer.getDistanceToEntity(o) < range) {
                final double dist = AntiFireball.mc.thePlayer.getDistanceToEntity(o);
                if (dist >= closestDist) {
                    continue;
                }
                closestDist = dist;
                player = (EntityFireball)o;
            }
        }
        return player;
    }
    
    static {
        AntiFireball.target = null;
    }
}

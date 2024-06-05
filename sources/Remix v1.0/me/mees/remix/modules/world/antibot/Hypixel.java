package me.mees.remix.modules.world.antibot;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.world.*;
import me.satisfactory.base.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import pw.stamina.causam.scan.method.model.*;

public class Hypixel extends Mode<AntiBot>
{
    public Hypixel(final AntiBot parent) {
        super(parent, "Hypixel");
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        for (final Entity entity : this.mc.theWorld.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
                final EntityPlayer ent;
                if ((ent = (EntityPlayer)entity) == this.mc.thePlayer) {
                    continue;
                }
                if (!ent.isInvisible() || ent.ticksExisted <= 105) {
                    continue;
                }
                ent.setInvisible(false);
                this.mc.theWorld.removeEntity(ent);
            }
        }
    }
}

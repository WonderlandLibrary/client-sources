/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Combat;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.TimeHelper;

public class TriggerBot
extends Module {
    TimeHelper triggerBotTime = new TimeHelper();
    int rndDelay;
    int rndDelayDelay;

    public TriggerBot() {
        super("TriggerBot", "TriggerBot", 0, Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        Entity entity;
        if (TriggerBot.mc.objectMouseOver != null && TriggerBot.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && (entity = TriggerBot.mc.objectMouseOver.entityHit) instanceof EntityLivingBase && entity.isEntityAlive()) {
            if (Minecraft.thePlayer.getDistanceToEntity(entity) <= 4.3f && this.rndDelay == 1) {
                mc.clickMouse();
            }
        }
        for (Object theObject : TriggerBot.mc.theWorld.loadedEntityList) {
            EntityLivingBase entityLivingBase;
            if (TriggerBot.mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || !((entityLivingBase = (EntityLivingBase)TriggerBot.mc.objectMouseOver.entityHit) instanceof EntityLivingBase) || !entityLivingBase.isEntityAlive()) continue;
            if (!(Minecraft.thePlayer.getDistanceToEntity(entityLivingBase) >= 4.2f) || !TimeHelper.hasReached(53L)) continue;
            if (entityLivingBase == Minecraft.thePlayer) continue;
            mc.clickMouse();
            TimeHelper.reset();
        }
        if (this.rndDelayDelay == 1) {
            this.rndDelay = 3;
        }
        if (this.rndDelayDelay == 4) {
            this.rndDelay = 2;
        }
        if (this.rndDelayDelay == 6) {
            this.rndDelay = 3;
        }
        if (this.rndDelayDelay == 9) {
            this.rndDelay = 4;
        }
        if (this.rndDelayDelay == 13) {
            this.rndDelay = 5;
        }
        if (this.rndDelayDelay == 18) {
            this.rndDelay = 2;
        }
        if (this.rndDelayDelay > 18) {
            this.rndDelayDelay = 0;
        }
        if (this.rndDelay != 0) {
            --this.rndDelay;
        }
        ++this.rndDelayDelay;
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
}


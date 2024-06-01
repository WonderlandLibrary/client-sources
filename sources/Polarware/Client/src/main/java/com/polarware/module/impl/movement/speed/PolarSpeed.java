package com.polarware.module.impl.movement.speed;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.module.impl.movement.SpeedModule;
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.AxisAlignedBB;

public class PolarSpeed extends Mode<SpeedModule> {

    public PolarSpeed(String name, SpeedModule parent) {
        super(name, parent);
    }

    @EventLink()
    private final Listener<StrafeEvent> onStrafeEvent = (event) -> {
        if (mc.thePlayer.movementInput.moveForward == 0.0F || mc.thePlayer.movementInput.moveStrafe == 0.0F)
            return;

        AxisAlignedBB playerBox = mc.thePlayer.getEntityBoundingBox();
        int collisions = 0;

        for (Entity entity : mc.theWorld.loadedEntityList) {
            AxisAlignedBB entityBox = entity.getEntityBoundingBox();

            if (valid(entity) && playerBox.intersectsWith(entityBox)) {
                ++collisions;
            }
        }

        double yaw = Math.toRadians(mc.thePlayer.movementYaw);
        double boost = 0.12 * collisions;

        mc.thePlayer.motionX -= Math.sin(yaw) * boost;
        mc.thePlayer.motionZ += Math.cos(yaw) * boost;
    };

    private boolean valid(Entity entity) {
        return entity != mc.thePlayer && (entity instanceof EntityLivingBase) && !(entity instanceof EntityArmorStand);
    }
}

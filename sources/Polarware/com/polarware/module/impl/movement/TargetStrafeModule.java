package com.polarware.module.impl.movement;

import com.polarware.Client;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.combat.KillAuraModule;
import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.JumpEvent;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.util.rotation.RotationUtil;
import com.polarware.util.vector.Vector3d;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import java.util.List;

/**
 * @author Alan
 * @since 20/10/2021
 */
@ModuleInfo(name = "module.movement.targetstrafe.name", description = "module.movement.targetstrafe.description", category = Category.MOVEMENT)
public class TargetStrafeModule extends Module {

    private final NumberValue range = new NumberValue("Range", this, 1, 0.2, 6, 0.1);

    public final BooleanValue holdJump = new BooleanValue("Hold Jump", this, true);
    private float yaw;
    private EntityLivingBase target;
    private boolean left, colliding;
    private boolean active;

    @EventLink(value = Priority.HIGH)
    public final Listener<JumpEvent> onJump = event -> {
        if (target != null && active) {
            event.setYaw(yaw);
        }
    };

    @EventLink(value = Priority.HIGH)
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (target != null && active) {
            event.setYaw(yaw);
        }
    };

    @EventLink(value = Priority.HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        // Disable if scaffold is enabled
        Module scaffold = getModule(ScaffoldModule.class);
        Module killaura = getModule(KillAuraModule.class);

        if (scaffold == null || scaffold.isEnabled() || killaura == null || !killaura.isEnabled()) {
            active = false;
            return;
        }

        active = true;
        
        /*
         * Getting targets and selecting the nearest one
         */
        Module speed = getModule(SpeedModule.class);
        Module test = null;
        Module flight = getModule(FlightModule.class);

        if (holdJump.getValue() && !mc.gameSettings.keyBindJump.isKeyDown() || !(mc.gameSettings.keyBindForward.isKeyDown() &&
                ((flight != null && flight.isEnabled()) || ((speed != null && speed.isEnabled()) || (test != null && test.isEnabled()))))) {
            target = null;
            return;
        }

        final List<EntityLivingBase> targets = Client.INSTANCE.getTargetComponent().getTargets(this.range.getValue().doubleValue() + 3);

        if (targets.isEmpty()) {
            target = null;
            return;
        }

        if (mc.thePlayer.isCollidedHorizontally || !PlayerUtil.isBlockUnder(5, false)) {
            if (!colliding) {
                MoveUtil.strafe();
                left = !left;
            }
            colliding = true;
        } else {
            colliding = false;
        }

        target = targets.get(0);

        if (target == null) {
            return;
        }

        float yaw = RotationUtil.calculate(target).getX() + (90 + 45) * (left ? -1 : 1);

        final double range = this.range.getValue().doubleValue();
        final double posX = -MathHelper.sin((float) Math.toRadians(yaw)) * range + target.posX;
        final double posZ = MathHelper.cos((float) Math.toRadians(yaw)) * range + target.posZ;

        yaw = RotationUtil.calculate(new Vector3d(posX, target.posY, posZ)).getX();

        this.yaw = yaw;
        mc.thePlayer.movementYaw = this.yaw;
    };
}
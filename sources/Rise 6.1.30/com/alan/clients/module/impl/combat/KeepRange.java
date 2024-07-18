package com.alan.clients.module.impl.combat;

import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.other.GameEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.NumberValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

@ModuleInfo(aliases = {"module.combat.keeprange.name", "S Tap"}, description = "module.combat.keeprange.description", category = Category.COMBAT)
public final class KeepRange extends Module {

    private final NumberValue range = new NumberValue("Range", this, 3, 0, 6, 0.1);
    private final BooleanValue disableNearEdge = new BooleanValue("Disable Near Edge", this, true);
    private final NumberValue edgeRange = new NumberValue("Edge Range", this, 5, 0, 6, 1, () -> !disableNearEdge.getValue());
    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("BackWards"))
            .add(new SubMode("Stop"))
            .setDefault("Stop");
    private final NumberValue combo = new NumberValue("Combo To Start", this, 2, 0, 6, 1);
    private boolean edge;
    private int row;

    @EventLink
    public final Listener<GameEvent> onGameEvent = event -> {
        if (!mc.thePlayer.onGround) return;

        edge = false;
        int range = edgeRange.getValue().intValue();

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                for (int y = -5; y <= 0; y++) {
                    boolean air = PlayerUtil.blockRelativeToPlayer(x, y, z) instanceof BlockAir;

                    if (!air) {
                        break;
                    }

                    if (y == 0) {
                        edge = true;
                        return;
                    }
                }
            }
        }
    };

    @EventLink
    public final Listener<MoveInputEvent> onMovementInput = event -> {
        EntityLivingBase target = TargetComponent.getTarget(10);

        double range = this.range.getValue().doubleValue();

        if (mc.thePlayer.ticksSinceAttack <= 7) range -= 0.2;

        if (target == null || (edge && disableNearEdge.getValue())) {
            row = 0;
            return;
        }

        if (target.hurtTime > 0) row += 1;
        if (mc.thePlayer.hurtTime > 0) row = 0;

        if (row <= combo.getValue().intValue() * 8 && combo.getValue().intValue() > 0) {
            return;
        }

        if (PlayerUtil.calculatePerfectRangeToEntity(target) < range - 0.05) {
            final float forward = event.getForward();
            final float strafe = event.getStrafe();

            final double angle = MathHelper.wrapAngleTo180_double(RotationUtil.calculate(target).getX() - 180);

            if (forward == 0 && strafe == 0) {
                return;
            }

            float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;

            for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
                for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
                    if (predictedStrafe == 0 && predictedForward == 0) continue;

                    final double predictedAngle = MathHelper.wrapAngleTo180_double(Math.toDegrees(MoveUtil.direction(mc.thePlayer.rotationYaw, predictedForward, predictedStrafe)));
                    final double difference = MathUtil.wrappedDifference(angle, predictedAngle);

                    if (difference < closestDifference) {
                        closestDifference = (float) difference;
                        closestForward = predictedForward;
                        closestStrafe = predictedStrafe;
                    }
                }
            }

            switch (mode.getValue().getName()) {
                case "Stop":
                    if (closestForward == forward * -1) event.setForward(0);
                    if (closestStrafe == strafe * -1) event.setStrafe(0);
                    break;

                case "BackWards":
                    event.setForward(closestForward);
                    event.setStrafe(closestStrafe);
                    break;
            }
        }
    };
}

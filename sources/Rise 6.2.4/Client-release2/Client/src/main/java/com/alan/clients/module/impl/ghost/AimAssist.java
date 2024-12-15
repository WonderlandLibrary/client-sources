package com.alan.clients.module.impl.ghost;

import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.RayCastUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

import java.util.List;

@ModuleInfo(aliases = {"module.ghost.aimassist.name"}, description = "module.ghost.aimassist.description", category = Category.GHOST)
public final class AimAssist extends Module {
    private final NumberValue speed = new NumberValue("Speed", this, 2, 1, 10, 1);
    private final BooleanValue click = new BooleanValue("Require Swinging", this, true);
    private final BooleanValue sticky = new BooleanValue("Sticky", this, false);
    private final BooleanValue mouseMovement = new BooleanValue("Require Mouse Movement", this, false);
    private final BooleanValue limitItems = new BooleanValue("Limit Items", this, false);
    private final BooleanValue aimWhilstOnTarget = new BooleanValue("Aim Whilst on Target", this, false);
    private final NumberValue fov = new NumberValue("FOV", this, 90, 0, 180, 1);
    private final BooleanValue showTargets = new BooleanValue("Targets", this, false);
    public final BooleanValue player = new BooleanValue("Player", this, true, () -> !showTargets.getValue());
    public final BooleanValue invisibles = new BooleanValue("Invisibles", this, false, () -> !showTargets.getValue());
    public final BooleanValue animals = new BooleanValue("Animals", this, false, () -> !showTargets.getValue());
    public final BooleanValue mobs = new BooleanValue("Mobs", this, false, () -> !showTargets.getValue());
    public final BooleanValue teams = new BooleanValue("Player Teammates", this, true, () -> !showTargets.getValue());
    private Vector2f move;
    EntityLivingBase target;

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        double range = 4;
        move = new Vector2f(0, 0);

        List<EntityLivingBase> targets = TargetComponent.getTargets(range, player.getValue(), invisibles.getValue(), animals.getValue(), mobs.getValue(), teams.getValue());

        if (targets.isEmpty()) {
            return;
        }

        target = targets.get(0);

        if (target == null || RayCastUtil.rayCast(RotationComponent.rotations, range, 5).typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
            return;
        }

        if (Math.abs(MathHelper.wrapAngleTo180_float(RotationUtil.calculate(target).getX() - mc.thePlayer.rotationYaw)) > fov.getValue().intValue()) {
            return;
        }

        if (limitItems.getValue() && (getComponent(Slot.class).getItemStack() == null || !(getComponent(Slot.class).getItemStack().getItem() instanceof ItemSword))) {
            return;
        }

        move = RotationUtil.move(RotationUtil.calculate(target), (speed.getValue().floatValue() * (sticky.getValue() ? 10 : 1)) / Minecraft.getDebugFPS() * 100);
    };

    @EventLink
    public final Listener<Render3DEvent> onRender3D = event -> {
        if (move == null) return;

        if (((mc.mouseHelper.deltaX != 0 || mc.mouseHelper.deltaY != 0) || !mouseMovement.getValue()) && RayCastUtil.rayCast(RotationComponent.rotations, 3, aimWhilstOnTarget.getValue() ? -0.3f : 0).typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK
                && (!click.getValue() || mc.thePlayer.isSwingInProgress)) {
            final float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            final float gcd = f * f * f * 8.0F;

            float f2 = (this.mc.mouseHelper.deltaX + (move.x - this.mc.mouseHelper.deltaX)) * gcd;

            this.mc.thePlayer.setAngles(f2, 0);
        }
    };
}
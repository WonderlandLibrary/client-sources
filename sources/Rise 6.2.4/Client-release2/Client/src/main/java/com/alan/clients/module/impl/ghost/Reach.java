package com.alan.clients.module.impl.ghost;

import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.RightClickEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.event.impl.render.MouseOverEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.RayCastUtil;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.BoundsNumberValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(aliases = {"module.ghost.reach.name"}, description = "module.ghost.reach.description", category = Category.GHOST)
public class Reach extends Module {

    public final BoundsNumberValue range = new BoundsNumberValue("Range", this, 3, 4, 3, 6, 0.01);
    private final NumberValue bufferDecrease = new NumberValue("Buffer Decrease", this, 1, 0.1, 10, 0.1, () -> !this.bufferAbuse.getValue());
    private final NumberValue maxBuffer = new NumberValue("Max Buffer", this, 5, 1, 200, 1, () -> !this.bufferAbuse.getValue());
    private final BooleanValue bufferAbuse = new BooleanValue("Buffer Abuse", this, false);

    private int lastId, attackTicks;
    private double combo;

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        this.attackTicks++;
    };

    @EventLink
    public final Listener<MouseOverEvent> onMouseOver = event -> {
        event.setRange(MathUtil.getRandom(this.range.getValue().doubleValue(),
                this.range.getSecondValue().doubleValue()));
    };

    @EventLink
    public final Listener<RightClickEvent> onRightClick = event ->
            mc.objectMouseOver = RayCastUtil.rayCast(RotationComponent.rotations, 4.5);

    @EventLink
    public final Listener<AttackEvent> onAttackEvent = event -> {
        final Entity entity = event.getTarget();

        if (this.bufferAbuse.getValue()) {
            if (RayCastUtil.rayCast(RotationComponent.rotations, 3.0D).typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
                if ((this.attackTicks > 9 || entity.getEntityId() != this.lastId) && this.combo < this.maxBuffer.getValue().intValue()) {
                    this.combo++;
                } else {
                    event.setCancelled();
                }
            } else {
                this.combo = Math.max(0, this.combo - this.bufferDecrease.getValue().doubleValue());
            }
        } else {
            this.combo = 0;
        }

        this.lastId = entity.getEntityId();
        this.attackTicks = 0;
    };
}
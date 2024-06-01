package com.polarware.module.impl.render;

import com.polarware.component.impl.render.ProjectionComponent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.render.targetinfo.ModernTargetInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.event.impl.render.Render2DEvent;
import com.polarware.util.vector.Vector2d;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.DragValue;
import com.polarware.value.impl.ModeValue;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.Entity;
import util.time.StopWatch;

import javax.vecmath.Vector4d;

/**
 * @author Alan
 * @since 10/19/2021
 */

@ModuleInfo(name = "module.render.targetinfo.name", description = "module.render.targetinfo.description", category = Category.RENDER)
public final class TargetInfoModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new ModernTargetInfo("Modern", this))
            .setDefault("Modern");

    public final DragValue positionValue = new DragValue("Position", this, new Vector2d(200, 200));
    public final BooleanValue followPlayer = new BooleanValue("Follow Player", this, false);

    public Vector2d position = new Vector2d(0, 0);
    public Entity target;
    public double distanceSq;
    public boolean inWorld;
    public StopWatch stopwatch = new StopWatch();

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.currentScreen instanceof GuiChat) {
            stopwatch.reset();
            target = mc.thePlayer;
        }

        if (target == null) {
            inWorld = false;
            return;
        }

        distanceSq = mc.thePlayer.getDistanceSqToEntity(target);
        inWorld = mc.theWorld.loadedEntityList.contains(target);
    };

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {

        if (event.getTarget() instanceof AbstractClientPlayer) {
            target = event.getTarget();
            stopwatch.reset();
        }
    };


    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        if (target == null) {
            return;
        }

        if (this.followPlayer.getValue()) {
            Vector4d position = ProjectionComponent.get(target);

            if (position == null) return;

            this.position.x = position.z;
            this.position.y = position.w - (position.w - position.y) / 2 - this.positionValue.scale.y / 2f;
        } else {
            this.position = positionValue.position;
        }
    };
}
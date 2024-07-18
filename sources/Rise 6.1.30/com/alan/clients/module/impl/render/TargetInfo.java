package com.alan.clients.module.impl.render;

import com.alan.clients.component.impl.render.ProjectionComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.render.targetinfo.*;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.DragValue;
import com.alan.clients.value.impl.ModeValue;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.Entity;
import rip.vantage.commons.util.time.StopWatch;

import javax.vecmath.Vector4d;

@ModuleInfo(aliases = {"module.render.targetinfo.name"}, description = "module.render.targetinfo.description", category = Category.RENDER)
public final class TargetInfo extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new BMSTargetInfo("BMS", this))
            .add(new NewTargetInfo("Compact", this))
            .add(new ModernTargetInfo("Modern", this))
            .add(new NovolineTargetInfo("Novoline", this))
            .add(new RueTargetInfo("Godly", this))
            .add(new CreidaTargetInfo("Creida Modern", this))
            //.add(new OldNovoTargetInfo("Old Novo", this))
            .add(new ExhibitionTargetInfo("Exhibition", this))
            .add(new WurstTargetInfo("Wurst", this)) // This will be added back when I add a system to change modes when interface changes
            .setDefault("Modern");

    public final DragValue positionValue = new DragValue("Position", this, new Vector2d(200, 200));
    public final BooleanValue followPlayer = new BooleanValue("Follow Player", this, false);

    public Vector2d position = new Vector2d(0, 0);
    public Entity target;
    public double distanceSq;
    public boolean inWorld;
    public StopWatch stopwatch = new StopWatch();

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
//        target = mc.thePlayer;

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

    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        if (event.getTarget() instanceof AbstractClientPlayer) {
            target = event.getTarget();
            stopwatch.reset();
        }
    };

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (target == null) {
            return;
        }

        if (this.followPlayer.getValue() && target != mc.thePlayer) {
            Vector4d position = ProjectionComponent.get(target);

            if (position == null) return;

            this.position.x = position.z;
            this.position.y = position.w - (position.w - position.y) / 2 - this.positionValue.scale.y / 2f;
        } else {
            this.position = positionValue.position;
        }
    };
}
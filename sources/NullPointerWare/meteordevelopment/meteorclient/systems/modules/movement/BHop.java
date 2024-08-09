// DO NOT DISTRIBUTE! ALL RIGHTS RESERVED BY LATTIA CLIENT (MIEP & LATTIA/LAGOON)

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.misc.Debug;
import meteordevelopment.meteorclient.systems.modules.world.Timer;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.util.math.MathHelper;


public final class BHop extends Module {

    public BHop() {
        super(Categories.Movement, "BHop", "Helps you with the SPEED.");
    }

    private double speed, level, lastSpeed;

    private boolean touchedGround;

    private int ticks, stage;

    @Override
    public void onActivate() {
        touchedGround = mc.player.isOnGround();
        ticks = 0;
        stage = 0;
        level = 0;
        lastSpeed = 0;
        speed = MoveUtils.getBaseMoveSpeed();
        super.onActivate();
    }

    @Override
    public void onDeactivate() {
        touchedGround = mc.player.isOnGround();
        ticks = 0;
        stage = 0;
        level = 0;
        lastSpeed = 0;
        speed = MoveUtils.getBaseMoveSpeed();
        Modules.get().get(Timer.class).resetOverride();
        MoveUtils.resetMotionXZ();
        super.onDeactivate();
    }

    @EventHandler(priority = EventPriority.HIGHEST + 1000)
    private void onUpdate(final SendMovementPacketsEvent.Pre e) {
        // TODO: fix? for some reason this breaks after like, dying or joining a new world
        if (isNull())
            return;

        final double xDist = mc.player.getX() - mc.player.prevX;
        final double zDist = mc.player.getZ() - mc.player.prevZ;

        lastSpeed = MathHelper.hypot(xDist, zDist);

        ticks++;

        if (Modules.get().isActive(Debug.class) && Modules.get().get(Debug.class).bhop.get() && Modules.get().get(Debug.class).bhopTicks.get())
            info("ticks is " + ticks);

        if (this.stage == 0) {
            // MoveUtils.setTimer(1.0F);
            Modules.get().get(Timer.class).setOverride(1);
            if (Modules.get().isActive(Debug.class) && Modules.get().get(Debug.class).bhop.get())
                info("Stage is 1");
            this.stage = 1;
        }

        if (!mc.player.isOnGround()) {
            if (this.ticks > 3 && mc.player.getVelocity().getY() > 0) {
                if (Modules.get().isActive(Debug.class) && Modules.get().get(Debug.class).bhop.get())
                    info("Motion is -0.27");
                MoveUtils.motionY(-0.27);
            }
        }

        if (MoveUtils.getSpeed() < 0.22f && !mc.player.isOnGround()) /* 0.22f */ {
            if (Modules.get().isActive(Debug.class) && Modules.get().get(Debug.class).bhop.get())
                info("Set motion to 0.22 on ground");
            MoveUtils.setMotion(0.22f);
        }

        if (MoveUtils.hasMovement()) {
            if (mc.player.isOnGround()) {
                this.ticks = 0;

                mc.player.jump();
                // MoveUtils.setTimer(1.2F);
                Modules.get().get(Timer.class).setOverride(1.2F);
                this.stage = 0;

                if (MoveUtils.getSpeed() < 0.48f) {
                    if (Modules.get().isActive(Debug.class) && Modules.get().get(Debug.class).bhop.get())
                        info("Set speed to 0.48");
                    MoveUtils.setMotion(0.48f);
                }
                else {
                    MoveUtils.setMotion((MoveUtils.getSpeed() * 0.985f));
                }
            }
        }
        else {
            Modules.get().get(Timer.class).setOverride(1);
            MoveUtils.resetMotionXZ();
        }
    }
}

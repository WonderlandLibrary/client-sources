package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.MouseMoveEvent;
import dev.lvstrng.argon.event.events.Render2DEvent;
import dev.lvstrng.argon.event.listeners.MouseMoveListener;
import dev.lvstrng.argon.event.listeners.Render2DListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.EnumSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

// THis module sucks
public final class AimAssist extends Module implements Render2DListener, MouseMoveListener {
    private final BooleanSetting stickyAim;
    private final BooleanSetting onlyWeapon;
    private final EnumSetting aimAt;
    private final BooleanSetting stopAtTarget;
    private final IntSetting radius;
    private final BooleanSetting seeOnly;
    private final BooleanSetting lookAtNearest;
    private final IntSetting fov;
    private final IntSetting verticalSpeed;
    private final IntSetting horizontalSpeed;
    private final IntSetting chance;
    private final BooleanSetting horizontal;
    private final BooleanSetting vertical;
    private final IntSetting waitOnMove;
    private final EnumSetting lerp;
    private final EnumSetting posMode;
    Timer timer;
    boolean canAim;

    enum AimType {
        Head("Head"),
        Body("Body"),

        ;public final String name;
        AimType(final String name) {this.name = name;}
    }

    enum TargetPosition {
        Precision
    }

    enum LerpType {
        Cubic, Linear, Smooth
    }


    public AimAssist() {
        super("Aim Assist", "Automatically aims at players for you", 0, Category.COMBAT);
        this.stickyAim = new BooleanSetting("Sticky Aim", false).setDescription("Aims at the last attacked player");
        this.onlyWeapon = new BooleanSetting("Only Weapon", true);
        this.aimAt = new EnumSetting("Aim At", AimType.Head, AimType.class);
        this.stopAtTarget = new BooleanSetting("Stop at Target", false).setDescription("Stops assisting if already aiming at the entity, helps bypass anticheat");
        this.radius = new IntSetting("Radius", 0.1, 6.0, 5.0, 0.1);
        this.seeOnly = new BooleanSetting("See Only", true);
        this.lookAtNearest = new BooleanSetting("Look at Nearest", false);
        this.fov = new IntSetting("FOV", 5.0, 360.0, 180.0, 1.0);
        this.verticalSpeed = new IntSetting("Vertical Speed", 0.0, 10.0, 2.0, 0.1);
        this.horizontalSpeed = new IntSetting("Horizontal Speed", 0.0, 10.0, 2.0, 0.1);
        this.chance = new IntSetting("Chance", 0.0, 100.0, 50.0, 1.0);
        this.horizontal = new BooleanSetting("Horizontal", true);
        this.vertical = new BooleanSetting("Vertical", true);
        this.waitOnMove = new IntSetting("Wait on Move", 0.0, 1000.0, 0.0, 1.0).setDescription("After you move your mouse aim assist will stop working for the selected amount of time");
        this.lerp = new EnumSetting("Lerp", LerpType.Linear, LerpType.class).setDescription("Linear interpolation to use to rotate");
        this.posMode = new EnumSetting("Pos mode", TargetPosition.Precision, TargetPosition.class).setDescription("Precision of the target position");
        this.timer = new Timer();
        this.addSettings(new Setting[]{
                this.stickyAim, this.onlyWeapon, this.aimAt, this.stopAtTarget, this.radius, this.seeOnly, this.lookAtNearest,
                this.fov, this.verticalSpeed, this.horizontalSpeed, this.chance, this.horizontal, this.vertical,
                this.waitOnMove, this.lerp, this.posMode
        });
    }

    public static double smoothLerp(double start, double end, float speed) {
        double progress = 1.0 - Math.pow(1.0 - speed, 3.0);
        return start + MathHelper.wrapDegrees(end - start) * (1.0 + 2.70158 * Math.pow(progress - 1.0, 3.0) + 1.70158 * Math.pow(progress - 1.0, 2.0));
    }

    @Override
    public void onEnable() {
        this.canAim = true;
        this.eventBus.registerPriorityListener(Render2DListener.class, this);
        this.eventBus.registerPriorityListener(MouseMoveListener.class, this);
        this.timer.reset();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(Render2DListener.class, this);
        this.eventBus.unregister(MouseMoveListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender2D(Render2DEvent po) {
        if (this.timer.hasElapsed(this.waitOnMove.getValueInt()) && !this.canAim) {
            this.canAim = true;
            this.timer.reset();
        }
        if (this.mc.player == null || this.mc.currentScreen != null) {
            return;
        }
        if (this.onlyWeapon.getValue() && !(this.mc.player.getMainHandStack().getItem() instanceof SwordItem) && !(this.mc.player.getMainHandStack().getItem() instanceof AxeItem)) {
            return;
        }
        PlayerEntity target = TargetUtil.getNearestPlayer(this.mc.player, this.radius.getValueInt(), this.seeOnly.getValue());
        if (this.stickyAim.getValue()) {
            LivingEntity lastTarget = this.mc.player.getLastAttacker();
            if (lastTarget instanceof PlayerEntity && lastTarget.squaredDistanceTo(this.mc.player) < this.radius.getValue() * this.radius.getValue()) {
                target = (PlayerEntity) lastTarget;
            }
        }
        if (target == null) {
            return;
        }
        HitResult crosshairTarget = this.mc.crosshairTarget;
        if (crosshairTarget instanceof EntityHitResult && ((EntityHitResult) crosshairTarget).getEntity() == target && this.stopAtTarget.getValue()) {
            return;
        }
        Vec3d targetVec = this.posMode.is(TargetPosition.Precision) ? target.getEyePos() : target.getLerpedPos(this.mc.getTickDelta());
        if (this.aimAt.is(AimType.Head)) {
            targetVec = targetVec.add(0.0, -0.5, 0.0);
        } else if (this.aimAt.is(AimType.Body)) {
            targetVec = targetVec.add(0.0, -1.2, 0.0);
        }
        if (this.lookAtNearest.getValue()) {
            targetVec = targetVec.add((this.mc.player.getX() - target.getX() > 0.0) ? 0.29 : -0.29, 0.0, (this.mc.player.getZ() - target.getZ() > 0.0) ? 0.29 : -0.29);
        }
        Rotation rotation = RotationUtil.method490(this.mc.player, targetVec);
        if (RotationUtil.method491(rotation) > this.fov.getValueInt() / 2.0) {
            return;
        }
        float horizontalDelta = this.horizontalSpeed.getValueInt() / 50.0f;
        float verticalDelta = this.verticalSpeed.getValueInt() / 50.0f;
        float yaw = this.mc.player.getYaw();
        float pitch = this.mc.player.getPitch();
        if (this.lerp.is(LerpType.Cubic)) {
            yaw = (float) this.cubicLerp(horizontalDelta, this.mc.player.getYaw(), rotation.getYaw());
            pitch = (float) this.cubicLerp(verticalDelta, this.mc.player.getPitch(), rotation.getPitch());
        }
        if (this.lerp.is(LerpType.Linear)) {
            yaw = this.linearLerp(horizontalDelta, this.mc.player.getYaw(), (float) rotation.getYaw());
            pitch = this.linearLerp(verticalDelta, this.mc.player.getPitch(), (float) rotation.getPitch());
        }
        if (this.lerp.is(LerpType.Smooth)) {
            yaw = (float) smoothLerp(this.mc.player.getYaw(), rotation.getYaw(), horizontalDelta * this.mc.getLastFrameDuration());
            pitch = (float) smoothLerp(this.mc.player.getPitch(), rotation.getPitch(), verticalDelta * this.mc.getLastFrameDuration());
        }
        if (RandomUtil.getRandom(1, 100) <= this.chance.getValueInt() && this.canAim) {
            if (this.horizontal.getValue()) {
                this.mc.player.setYaw(yaw);
            }
            if (this.vertical.getValue()) {
                this.mc.player.setPitch(pitch);
            }
        }
    }

    public float linearLerp(float delta, float start, float end) {
        return start + MathHelper.wrapDegrees(end - start) * delta;
    }

    public double cubicLerp(double delta, double start, double end) {
        delta = Math.max(0.0, Math.min(1.0, delta));
        return start + MathHelper.wrapDegrees(end - start) * (delta * delta * (3.0 - 2.0 * delta));
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
        this.canAim = false;
        this.timer.reset();
    }
}
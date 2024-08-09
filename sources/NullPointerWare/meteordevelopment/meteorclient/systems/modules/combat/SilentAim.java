package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.entity.player.AttackEntityEvent;
import meteordevelopment.meteorclient.events.entity.player.BreakBlockEvent;
import meteordevelopment.meteorclient.events.game.MouseUpdateEvent;
import meteordevelopment.meteorclient.events.render.Render2DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixin.AccessorMinecraftClient;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.math.MathUtils;
import meteordevelopment.meteorclient.utils.other.RotationUtils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class SilentAim extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> onlyWeapon = sgGeneral.add(new BoolSetting.Builder()
        .name("Only Weapon")
        .description("Only Weapon")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> maxDistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("Max Distance")
        .description("Max Distance")
        .defaultValue(4)
        .min(3)
        .max(5)
        .sliderMax(0.1)
        .build()
    );

    private final Setting<Boolean> lookAtNearest = sgGeneral.add(new BoolSetting.Builder()
        .name("Look At Nearest Hitbox's Corner")
        .description("Look At Nearest Hitbox's Corner")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> minYawSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("Min Horizontal Speed")
        .description("Min Horizontal Speed")
        .defaultValue(2)
        .min(1)
        .max(10)
        .sliderMax(0.1)
        .build()
    );

    private final Setting<Double> maxYawSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("Max Horizontal Speed")
        .description("Max Horizontal Speed")
        .defaultValue(4)
        .min(1)
        .max(10)
        .sliderMax(0.1)
        .build()
    );

    private final Setting<Double> minPitchSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("Min Vertical Speed")
        .description("Min Vertical Speed")
        .defaultValue(1)
        .min(0.5)
        .max(10)
        .sliderMax(0.1)
        .build()
    );

    private final Setting<Double> maxPitchSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("Max Vertical Speed")
        .description("Max Vertical Speed")
        .defaultValue(2)
        .min(0.5)
        .max(10)
        .sliderMax(0.1)
        .build()
    );

    private final Setting<Double> fov = sgGeneral.add(new DoubleSetting.Builder()
        .name("FOV")
        .description("FOV")
        .defaultValue(90)
        .min(1)
        .max(180)
        .sliderMax(1)
        .build()
    );

    private final Setting<Boolean> fovCircle = sgGeneral.add(new BoolSetting.Builder()
        .name("FOV Circle")
        .description("FOV Circle")
        .defaultValue(true)
        .build()
    );

    private final Setting<SettingColor> color  = sgGeneral.add(new ColorSetting.Builder()
        .name("Color")
        .description("The color of the circle!")
        .defaultValue(new SettingColor(255, 255, 255, 80))
        .visible(fovCircle::get)
        .build()
    );


    public SilentAim() {
        super(Categories.Combat, "SilentAim", "Automatically aims at players and hit them for you");
    }

    private LivingEntity targetPlayer;
    private boolean doAttack;
    private boolean canAttack;


    @EventHandler
    public void onMouseUpdate(final MouseUpdateEvent event) {
        if (mc.currentScreen != null || isNull()) {
            return;
        }

        Item mainHandItem = mc.player.getMainHandStack().getItem();

        if (!(mainHandItem instanceof AxeItem || mainHandItem instanceof SwordItem) && onlyWeapon.get()) {
            MeteorClient.rotationManager().disable();
            canAttack = false;
            return;
        }

        targetPlayer = PlayerUtils.findNearestEntity(mc.player, maxDistance.get(), true);

        if (targetPlayer == null || targetPlayer.isDead() || targetPlayer.isInvisible()) {
            MeteorClient.rotationManager().disable();
            canAttack = false;
            return;
        }

        Vec3d targetPlayerPos = targetPlayer.getPos();

        if (lookAtNearest.get()) {
            double halfHitboxSize = (targetPlayer.getBoundingBox().getLengthX() / 2) - 0.01d;

            double offsetX = (mc.player.getX() - targetPlayer.getX()) > 0 ? halfHitboxSize : -halfHitboxSize;
            double offsetZ = (mc.player.getZ() - targetPlayer.getZ()) > 0 ? halfHitboxSize : -halfHitboxSize;

            targetPlayerPos = targetPlayerPos.add(offsetX, 0, offsetZ);
        }

        RotationUtils.Rotation targetRot = RotationUtils.getDirection(mc.player, targetPlayerPos);

        if (RotationUtils.getAngleToRotation(targetRot) > fov.get() / 2) {
            MeteorClient.rotationManager().disable();
            canAttack = false;
            return;
        }

        RotationUtils.Rotation serverRotation =  MeteorClient.rotationManager().getServerRotation();

        HitResult hitResult = RotationUtils.getHitResult(mc.player, false, (float) serverRotation.yaw(), (float) serverRotation.pitch());

        canAttack = hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().equals(targetPlayer);

        float randomiseYaw = (float) MathUtils.getRandomDouble(0, 0.2);
        float randomisePitch = (float) MathUtils.getRandomDouble(0, 0.2);

        float yawStrength = (float) (MathUtils.getRandomDouble(minYawSpeed.get(), maxYawSpeed.get()) / 50);
        float pitchStrength = (float) (MathUtils.getRandomDouble(minPitchSpeed.get(), maxPitchSpeed.get()) / 50);

        float yaw = MathHelper.lerpAngleDegrees(yawStrength, (float) serverRotation.yaw(), (float) targetRot.yaw()) + randomiseYaw;
        float pitch = MathHelper.lerpAngleDegrees(pitchStrength, (float) serverRotation.pitch(), (float) targetRot.pitch()) + randomisePitch;

        if (!MeteorClient.rotationManager().isEnabled())  MeteorClient.rotationManager().enable();

        MeteorClient.rotationManager().setRotation(new RotationUtils.Rotation(yaw, pitch));
    }

    @Override
    public void onActivate() {
        targetPlayer = null;
        doAttack = false;
        canAttack = false;
    }

    @Override
    public void onDeactivate() {
        if (MeteorClient.rotationManager().isEnabled())
            MeteorClient.rotationManager().disable();
    }

    @EventHandler
    private void onaTick(final TickEvent.Pre e) {
        if (doAttack && targetPlayer != null) {
            ((AccessorMinecraftClient)mc).leftClick();
            doAttack = false;
        }
    }

    @EventHandler
    private void onBlockBreak(final BreakBlockEvent event) {
        if (targetPlayer != null) {
            Item mainHandItem = mc.player.getMainHandStack().getItem();

            if (!(mainHandItem instanceof AxeItem || mainHandItem instanceof SwordItem) && onlyWeapon.get())
                return;

            if (!targetPlayer.isAlive())
                return;

            if (!canAttack)
                return;

            if (!MeteorClient.rotationManager().isEnabled())
                return;

            event.cancel();
        }
    }

    @EventHandler
    private void onAttack(final AttackEntityEvent event) {
        if (targetPlayer != null) {
            Item mainHandItem = mc.player.getMainHandStack().getItem();

            if (!(mainHandItem instanceof AxeItem || mainHandItem instanceof SwordItem) && onlyWeapon.get())
                return;

            if (!targetPlayer.isAlive())
                return;

            if (!canAttack)
                return;

            doAttack = true;
            event.cancel();
        }
    }

    @EventHandler
    private void onHudRender(final Render2DEvent event) {
        if (fovCircle.get()) {
            RenderUtils.drawCircle(event.drawContext.getMatrices(), (double) mc.getWindow().getScaledWidth() / 2, (double) mc.getWindow().getScaledHeight() / 2, fov.get() * 2, 60, color.get());
        }
    }
}

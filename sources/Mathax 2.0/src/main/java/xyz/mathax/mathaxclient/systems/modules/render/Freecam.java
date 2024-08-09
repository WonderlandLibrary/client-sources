package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.eventbus.EventPriority;
import xyz.mathax.mathaxclient.events.entity.DamageEvent;
import xyz.mathax.mathaxclient.events.game.GameLeftEvent;
import xyz.mathax.mathaxclient.events.game.OpenScreenEvent;
import xyz.mathax.mathaxclient.events.mathax.KeyEvent;
import xyz.mathax.mathaxclient.events.mathax.MouseButtonEvent;
import xyz.mathax.mathaxclient.events.mathax.MouseScrollEvent;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.world.ChunkOcclusionEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.movement.GuiMove;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.input.Input;
import xyz.mathax.mathaxclient.utils.input.KeyAction;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;
import org.lwjgl.glfw.GLFW;

public class Freecam extends Module {
    public final Vector3d pos = new Vector3d();
    public final Vector3d prevPos = new Vector3d();

    private Perspective perspective;

    private double speedValue;
    private double fovScale;
    private boolean bobView;

    public float yaw, pitch;
    public float prevYaw, prevPitch;

    private boolean forward, backward, right, left, up, down;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup renderSettings = settings.createGroup("Render");

    // General

    private final Setting<Double> speedSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Speed")
            .description("Your speed while in Freecam.")
            .onChanged(value -> speedValue = value)
            .defaultValue(1.0)
            .min(0.0)
            .build()
    );

    private final Setting<Double> speedScrollSensitivitySetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Speed scroll sensitivity")
            .description("Allows you to change speed value using scroll wheel. (0 to disable)")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 2)
            .build()
    );

    private final Setting<Boolean> rotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Rotate")
            .description("Rotates to the block or entity you are looking at.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> autoDisableOnDamageSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Toggle on damage")
            .description("Disables Freecam when you take damage.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> toggleOnDeathSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Toggle on death")
            .description("Disables Freecam when you die.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> autoDisableOnLogSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Toggle on log")
            .description("Disables Freecam when you disconnect from a server.")
            .defaultValue(true)
            .build()
    );

    // Render

    private final Setting<Boolean> reloadChunksSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Reload chunks")
            .description("Disables cave culling.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> renderHandsSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Show hands")
            .description("Whether or not to render your hands in Freecam.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> staticViewSetting = renderSettings.add(new BoolSetting.Builder()
            .name("Static")
            .description("Disable settings that move the view.")
            .defaultValue(true)
            .build()
    );

    public Freecam(Category category) {
        super(category, "Freecam", "Allows the camera to move away from the player.");
    }

    @Override
    public void onEnable() {
        fovScale = mc.options.getFovEffectScale().getValue();
        bobView = mc.options.getBobView().getValue();
        if (staticViewSetting.get()) {
            mc.options.getFovEffectScale().setValue((double)0);
            mc.options.getBobView().setValue(false);
        }

        yaw = mc.player.getYaw();
        pitch = mc.player.getPitch();

        perspective = mc.options.getPerspective();
        speedValue = speedSetting.get();

        Utils.set(pos, mc.gameRenderer.getCamera().getPos());
        Utils.set(prevPos, mc.gameRenderer.getCamera().getPos());

        prevYaw = yaw;
        prevPitch = pitch;

        forward = false;
        backward = false;
        right = false;
        left = false;
        up = false;
        down = false;

        unpress();

        if (reloadChunksSetting.get()) {
            mc.worldRenderer.reload();
        }
    }

    @Override
    public void onDisable() {
        if (reloadChunksSetting.get()) {
            mc.worldRenderer.reload();
        }

        mc.options.setPerspective(perspective);

        if (staticViewSetting.get()) {
            mc.options.getFovEffectScale().setValue((double)fovScale);
            mc.options.getBobView().setValue(bobView);
        }
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent event) {
        unpress();

        prevPos.set(pos);
        prevYaw = yaw;
        prevPitch = pitch;
    }

    private void unpress() {
        mc.options.forwardKey.setPressed(false);
        mc.options.backKey.setPressed(false);
        mc.options.rightKey.setPressed(false);
        mc.options.leftKey.setPressed(false);
        mc.options.jumpKey.setPressed(false);
        mc.options.sneakKey.setPressed(false);
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.cameraEntity.isInsideWall()) {
            mc.getCameraEntity().noClip = true;
        }

        if (!perspective.isFirstPerson()) {
            mc.options.setPerspective(Perspective.FIRST_PERSON);
        }

        Vec3d forward = Vec3d.fromPolar(0, yaw);
        Vec3d right = Vec3d.fromPolar(0, yaw + 90);
        double velX = 0;
        double velY = 0;
        double velZ = 0;
        if (rotateSetting.get()) {
            BlockPos crossHairBlockPosition;
            Vec3d crossHairPosition;

            if (mc.crosshairTarget instanceof EntityHitResult) {
                crossHairBlockPosition = ((EntityHitResult) mc.crosshairTarget).getEntity().getBlockPos();
                Rotations.rotate(Rotations.getYaw(crossHairBlockPosition), Rotations.getPitch(crossHairBlockPosition), 0, null);
            } else {
                crossHairPosition = mc.crosshairTarget.getPos();
                crossHairBlockPosition = ((BlockHitResult) mc.crosshairTarget).getBlockPos();
                if (!mc.world.getBlockState(crossHairBlockPosition).isAir()) {
                    Rotations.rotate(Rotations.getYaw(crossHairPosition), Rotations.getPitch(crossHairPosition), 0, null);
                }
            }
        }

        double speed = 0.5;
        if (mc.options.sprintKey.isPressed()) {
            speed = 1;
        }

        boolean diagonal1 = false;
        if (this.forward) {
            velX += forward.x * speed * speedValue;
            velZ += forward.z * speed * speedValue;
            diagonal1 = true;
        }

        if (this.backward) {
            velX -= forward.x * speed * speedValue;
            velZ -= forward.z * speed * speedValue;
            diagonal1 = true;
        }

        boolean diagonal2 = false;
        if (this.right) {
            velX += right.x * speed * speedValue;
            velZ += right.z * speed * speedValue;
            diagonal2 = true;
        }

        if (this.left) {
            velX -= right.x * speed * speedValue;
            velZ -= right.z * speed * speedValue;
            diagonal2 = true;
        }

        if (diagonal1 && diagonal2) {
            double diagonal = 1 / Math.sqrt(2);
            velX *= diagonal;
            velZ *= diagonal;
        }

        if (this.up) {
            velY += speed * speedValue;
        }

        if (this.down) {
            velY -= speed * speedValue;
        }

        prevPos.set(pos);
        pos.set(pos.x + velX, pos.y + velY, pos.z + velZ);
    }

    @EventHandler
    public void onKey(KeyEvent event) {
        if (Input.isKeyPressed(GLFW.GLFW_KEY_F3)) {
            return;
        }

        if (checkGuiMove()) {
            return;
        }

        boolean cancel = true;
        if (mc.options.forwardKey.matchesKey(event.key, 0)) {
            forward = event.action != KeyAction.Release;
            mc.options.forwardKey.setPressed(false);
        } else if (mc.options.backKey.matchesKey(event.key, 0)) {
            backward = event.action != KeyAction.Release;
            mc.options.backKey.setPressed(false);
        } else if (mc.options.rightKey.matchesKey(event.key, 0)) {
            right = event.action != KeyAction.Release;
            mc.options.rightKey.setPressed(false);
        } else if (mc.options.leftKey.matchesKey(event.key, 0)) {
            left = event.action != KeyAction.Release;
            mc.options.leftKey.setPressed(false);
        } else if (mc.options.jumpKey.matchesKey(event.key, 0)) {
            up = event.action != KeyAction.Release;
            mc.options.jumpKey.setPressed(false);
        } else if (mc.options.sneakKey.matchesKey(event.key, 0)) {
            down = event.action != KeyAction.Release;
            mc.options.sneakKey.setPressed(false);
        } else {
            cancel = false;
        }

        if (cancel) {
            event.cancel();
        }
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent event) {
        if (checkGuiMove()) {
            return;
        }

        boolean cancel = true;
        if (mc.options.sneakKey.matchesMouse(event.button)) {
            forward = event.action != KeyAction.Release;
            mc.options.forwardKey.setPressed(false);
        } else if (mc.options.sneakKey.matchesMouse(event.button)) {
            backward = event.action != KeyAction.Release;
            mc.options.backKey.setPressed(false);
        } else if (mc.options.sneakKey.matchesMouse(event.button)) {
            right = event.action != KeyAction.Release;
            mc.options.rightKey.setPressed(false);
        } else if (mc.options.sneakKey.matchesMouse(event.button)) {
            left = event.action != KeyAction.Release;
            mc.options.leftKey.setPressed(false);
        } else if (mc.options.sneakKey.matchesMouse(event.button)) {
            up = event.action != KeyAction.Release;
            mc.options.jumpKey.setPressed(false);
        } else if (mc.options.sneakKey.matchesMouse(event.button)) {
            down = event.action != KeyAction.Release;
            mc.options.sneakKey.setPressed(false);
        } else {
            cancel = false;
        }

        if (cancel) {
            event.cancel();
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onMouseScroll(MouseScrollEvent event) {
        if (speedScrollSensitivitySetting.get() > 0) {
            speedValue += event.value * 0.25 * (speedScrollSensitivitySetting.get() * speedValue);
            if (speedValue < 0.1) {
                speedValue = 0.1;
            }

            event.cancel();
        }
    }

    @EventHandler
    private void onChunkOcclusion(ChunkOcclusionEvent event) {
        event.cancel();
    }

    @EventHandler
    private void onDamage(DamageEvent event) {
        if (event.entity.getUuid() == null) {
            return;
        }

        if (!event.entity.getUuid().equals(mc.player.getUuid())) {
            return;
        }

        if (autoDisableOnDamageSetting.get() || (toggleOnDeathSetting.get() && event.entity.getHealth() <= 0)) {
            info("You took damage or died, disabling...");
            forceToggle(false);
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!autoDisableOnLogSetting.get()) {
            return;
        }

        forceToggle(false);
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event)  {
        if (event.packet instanceof DeathMessageS2CPacket packet) {
            Entity entity = mc.world.getEntityById(packet.getEntityId());
            if (entity == mc.player && toggleOnDeathSetting.get()) {
                info("Toggled off because you died.");
                forceToggle(false);
            }
        }
    }

    private boolean checkGuiMove() {
        // TODO: This is very bad but you all can cope :cope:
        GuiMove guiMove = Modules.get().get(GuiMove.class);
        if (mc.currentScreen != null && !guiMove.isEnabled()) {
            return true;
        }

        return (mc.currentScreen != null && guiMove.isEnabled() && guiMove.skip());
    }

    public void changeLookDirection(double deltaX, double deltaY) {
        prevYaw = yaw;
        prevPitch = pitch;

        yaw += deltaX;
        pitch += deltaY;

        pitch = MathHelper.clamp(pitch, -90, 90);
    }

    public boolean renderHands() {
        return !isEnabled() || renderHandsSetting.get();
    }

    public double getX(float tickDelta) {
        return MathHelper.lerp(tickDelta, prevPos.x, pos.x);
    }

    public double getY(float tickDelta) {
        return MathHelper.lerp(tickDelta, prevPos.y, pos.y);
    }

    public double getZ(float tickDelta) {
        return MathHelper.lerp(tickDelta, prevPos.z, pos.z);
    }

    public double getYaw(float tickDelta) {
        return MathHelper.lerp(tickDelta, prevYaw, yaw);
    }

    public double getPitch(float tickDelta) {
        return MathHelper.lerp(tickDelta, prevPitch, pitch);
    }
}

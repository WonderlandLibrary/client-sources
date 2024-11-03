package dev.stephen.nexus.utils.rotation.manager;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.input.EventMovementInput;
import dev.stephen.nexus.event.impl.player.EventJump;
import dev.stephen.nexus.event.impl.player.EventSilentRotation;
import dev.stephen.nexus.event.impl.player.EventTickAI;
import dev.stephen.nexus.event.impl.player.EventYawMoveFix;
import dev.stephen.nexus.event.impl.render.EventRender3D;
import dev.stephen.nexus.module.modules.movement.MoveFix;
import dev.stephen.nexus.module.modules.player.Scaffold;
import dev.stephen.nexus.utils.Utils;
import dev.stephen.nexus.utils.math.MathUtils;
import dev.stephen.nexus.utils.mc.ChatUtils;
import dev.stephen.nexus.utils.mc.MoveUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;
import dev.stephen.nexus.utils.rotation.RotationUtils;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationManager implements Utils {

    public float yaw = 0;
    public float prevYaw = 0;
    public float pitch = 0;
    public float prevPitch = 0;
    public boolean rotating;
    private float speed;

    // third person things
    public Pair<Float, Float> rotationPitch = new Pair<>(0f, 0f);

    @EventLink
    public final Listener<EventRender3D> eventRender3DListener = event -> {
        if (mc.player == null || mc.world == null) {
            return;
        }
        /*
        Matrix4f matrix4f = event.getMatrixStack().peek().getPositionMatrix();

        float[] rotation = getCurrentRotation();
        float yaw = rotation[0];
        float pitch = rotation[1];

        Vec3d direction = getRotationVec(yaw, pitch);

        RenderSystem.setShaderColor(1, 1, 1, 1.0f);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glLineWidth(3.0f);

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);

        float startX = (float) mc.player.getX();
        float startY = (float) mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose());
        float startZ = (float) mc.player.getZ();
        bufferBuilder.vertex(matrix4f, startX, startY, startZ);

        float lenght = 5;

        float endX = startX + (float) (direction.x * lenght);
        float endY = startY + (float) (direction.y * lenght);
        float endZ = startZ + (float) (direction.z * lenght);
        bufferBuilder.vertex(matrix4f, endX, endY, endZ);

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

        RenderSystem.setShaderColor(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glLineWidth(1.0f);
        */
    };


    private Vec3d getRotationVec(float yaw, float pitch) {
        float yawCos = MathHelper.cos(-yaw * 0.017453292f);
        float yawSin = MathHelper.sin(-yaw * 0.017453292f);
        float pitchCos = MathHelper.cos(pitch * 0.017453292f);
        float pitchSin = MathHelper.sin(pitch * 0.017453292f);

        return new Vec3d(yawSin * pitchCos, -pitchSin, yawCos * pitchCos);
    }

    @EventLink
    public final Listener<EventTickAI> eventTickListener = event -> {
        if (mc.player == null || mc.world == null) {
            return;
        }

        EventSilentRotation rotation = new EventSilentRotation(mc.player.getYaw(), mc.player.getPitch(), speed);
        Client.INSTANCE.getEventManager().post(rotation);
        prevYaw = yaw;
        prevPitch = pitch;

        if (!rotating && !rotation.hasBeenModified()) {
            yaw = mc.player.getYaw();
            pitch = mc.player.getPitch();
            return;
        }

        speed = rotation.getSpeed();

        float[] patchedRots = RotationUtils.getPatchedAndCappedRots(new float[]{prevYaw, prevPitch}, new float[]{rotation.getYaw(), rotation.getPitch()}, speed);

        yaw = patchedRots[0];
        pitch = patchedRots[1];
        rotating = rotation.hasBeenModified() || (Math.abs(RotationUtils.getYawDifference(mc.player.getYaw(), yaw)) > speed);
    };

    @EventLink
    public final Listener<EventYawMoveFix> eventMoveListener = event -> {
        if (!rotating || !doMoveFix())
            return;
        event.setYaw(yaw);
    };

    @EventLink
    public final Listener<EventJump> eventJumpListener = event -> {
        if (!rotating || !doMoveFix())
            return;
        event.setYaw(yaw);
    };

    @EventLink
    public final Listener<EventMovementInput> eventMovementInputListener = event -> {
        if (!rotating || !doSilentMoveFix())
            return;

        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(Scaffold.class).telly.getValue() && PlayerUtil.inAirTicks() <= 5) {
            return;
        }

        float forward = event.getMovementForward();
        float strafe = event.getMovementSideways();

        final double angle = MathUtils.wrapAngleTo180_double(Math.toDegrees(MoveUtils.direction(mc.player.getYaw(), forward, strafe)));

        if (forward == 0 && strafe == 0) {
            return;
        }

        float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;

        for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
            for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
                if (predictedStrafe == 0 && predictedForward == 0) continue;

                final double predictedAngle = MathUtils.wrapAngleTo180_double(Math.toDegrees(MoveUtils.direction(yaw, predictedForward, predictedStrafe)));
                final double difference = Math.abs(angle - predictedAngle);

                if (difference < closestDifference) {
                    closestDifference = (float) difference;
                    closestForward = predictedForward;
                    closestStrafe = predictedStrafe;
                }
            }
        }
        event.setMovementForward(closestForward);
        event.setMovementSideways(closestStrafe);
    };

    private boolean doMoveFix() {
        return Client.INSTANCE.getModuleManager().getModule(MoveFix.class).isEnabled();
    }

    private boolean doSilentMoveFix() {
        return doMoveFix() && Client.INSTANCE.getModuleManager().getModule(MoveFix.class).silent.getValue();
    }

    public float[] getCurrentRotation() {
        return new float[]{yaw, pitch};
    }

    public float[] getPrevRotation() {
        return new float[]{prevYaw, prevPitch};
    }
}
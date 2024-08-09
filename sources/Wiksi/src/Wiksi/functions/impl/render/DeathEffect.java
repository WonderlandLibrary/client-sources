package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.*;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.math.StopWatch;
import src.Wiksi.utils.render.DisplayUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SDestroyEntitiesPacket;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import java.util.ArrayList;
import java.util.List;

@FunctionRegister(name = "DeathEffect", type = Category.Render)
public class DeathEffect extends Function {
    private Animation animate = new Animation();
    private boolean useAnimation;

    LivingEntity target;
    long time;
    public StopWatch stopWatch = new StopWatch();

    private float yaw, pitch;

    private final List<Vector3d> position = new ArrayList<>();

    private int current;

    private Vector3d setPosition;

    @Subscribe
    public void onPacket(AttackEvent e) {
        if (mc.player == null || mc.world == null)
            return;
        if (e.entity instanceof PlayerEntity)
            target = (LivingEntity) e.entity;
        time = System.currentTimeMillis();

    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (mc.player == null || mc.world == null)
            return;

        if (e.getPacket() instanceof SDestroyEntitiesPacket p) {
            for (int ids : p.getEntityIDs()) {
                if (target != null) {
                    if (ids == mc.player.getEntityId())
                        continue;

                    if (time + 150 >= System.currentTimeMillis() && target.getEntityId() == ids) {
                        if (((LivingEntity) mc.world.getEntityByID(ids)).getHealth() < 5) {
                            onKill(target);
                            target = null;
                        }
                    }
                }
            }
        }

    }

    public float back;

    @Subscribe
    public void onUpdate(EventMotion e) {
        if (mc.player == null || mc.world == null)
            return;

        if (useAnimation) {
            if (mc.player.ticksExisted % 3 == 0)
                current++;
            Vector3d player = new Vector3d(
                    MathUtil.interpolate(mc.player.getPosX(), mc.player.lastTickPosX, mc.getRenderPartialTicks()),
                    MathUtil.interpolate(mc.player.getPosY(), mc.player.lastTickPosY, mc.getRenderPartialTicks()),
                    MathUtil.interpolate(mc.player.getPosZ(), mc.player.lastTickPosZ, mc.getRenderPartialTicks()))
                    .add(0, mc.player.getEyeHeight(), 0);

            position.add(player);
        }

        if (target != null) {
            if (time + 500 >= System.currentTimeMillis() && target.getHealth() <= 0f) {
                onKill(target);
                target = null;
            }

        }

        if (stopWatch.isReached(300)) {
            animate = animate.animate(0, 1f, Easings.CIRC_OUT);
        }
        if (stopWatch.isReached(900)) {
            useAnimation = false;
            last = null;
        }
    }

    public Vector2f last;

    @Subscribe
    public void onCameraController(CameraEvent e) {
        if (useAnimation) {
            mc.getRenderManager().info.setDirection(
                    (float) (yaw + (6 * animate.getValue())),
                    (float) (pitch + (6 * animate.getValue())));

            back = MathUtil.fast(back, stopWatch.isReached(500) ? 1 : 0, 10);
            Vector3d player = new Vector3d(
                    MathUtil.interpolate(mc.player.getPosX(), mc.player.lastTickPosX, mc.getRenderPartialTicks()),
                    MathUtil.interpolate(mc.player.getPosY(), mc.player.lastTickPosY, mc.getRenderPartialTicks()),
                    MathUtil.interpolate(mc.player.getPosZ(), mc.player.lastTickPosZ, mc.getRenderPartialTicks()))
                    .add(0, mc.player.getEyeHeight(), 0);

            if (setPosition != null) {
                mc.getRenderManager().info.setDirection(
                        (float) MathUtil.interpolate((float) (yaw + (6 * animate.getValue())),
                                mc.player.getYaw(e.partialTicks), 1 - back),
                        (float) MathUtil.interpolate((float) (pitch + (6 * animate.getValue())),
                                mc.player.getPitch(e.partialTicks), 1 - back));
                mc.getRenderManager().info.setPosition(MathUtil.interpolate(setPosition, player, 1 - back));
            }
            mc.getRenderManager().info.moveForward(1f * animate.getValue());

        }

    }

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.player == null || mc.world == null || e.getType() != EventDisplay.Type.POST) {
            return;
        }
        animate.update();
        if (useAnimation && setPosition != null && position.size() > 1) {
            setPosition = MathUtil.fast(setPosition, position.get(current), 1);
            DisplayUtils.drawWhite((float) animate.getValue());

        }
    }

    public void onKill(LivingEntity entity) {
        position.clear();
        current = 0;
        animate = animate.animate(1, 1f, Easings.CIRC_OUT);
        useAnimation = true;
        stopWatch.reset();
        Vector3d player = new Vector3d(
                MathUtil.interpolate(mc.player.getPosX(), mc.player.lastTickPosX, mc.getRenderPartialTicks()),
                MathUtil.interpolate(mc.player.getPosY(), mc.player.lastTickPosY, mc.getRenderPartialTicks()),
                MathUtil.interpolate(mc.player.getPosZ(), mc.player.lastTickPosZ, mc.getRenderPartialTicks()))
                .add(0, mc.player.getEyeHeight(), 0);

        setPosition = player;
        yaw = mc.player.getYaw(mc.getRenderPartialTicks());
        pitch = mc.player.getPitch(mc.getRenderPartialTicks());
    }

}

package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.MovingEvent;
import fun.ellant.events.TickEvent;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

@FunctionRegister(name = "NoClip", type = Category.MOVEMENT, desc = "Позволяет вам ходить в стене")
public class NoClip extends Function {

    final ModeSetting mode = new ModeSetting("Обход", "Обычный", "Обычный", "Пакетный");
    private Vector3d targetPosition;
    private int tickCounter = 0;
    private final double FORWARD_DISTANCE = 1.0;

    public NoClip() {
        addSettings(mode);
    }

    @Subscribe
    private void onMoving(MovingEvent move) {
        if (mode.is("Обычный")) {
            if (!collisionPredict(move.getTo())) {
                if (move.isCollidedHorizontal())
                    move.setIgnoreHorizontal(true);
                if (move.getMotion().y > 0 || mc.player.isSneaking()) {
                    move.setIgnoreVertical(true);
                }
                move.getMotion().y = Math.min(move.getMotion().y, 99999);
            }
        } else if (mode.is("Пакетный")) {
            move.cancel();
        } else if (mode.is("GrimAC")) {
            if (mc.player.getPosY() % 1.0 < 0.1 || mc.player.getPosY() % 1.0 > 0.9)
                return;

            if (!mc.world.getBlockState(mc.player.getPosition().down()).isAir())
                return;

            mc.player.setMotion(mc.player.getMotion().x, 0.42, mc.player.getMotion().z);
        }
    }

    @Subscribe
    private void onTick(TickEvent event) {
        if (mode.is("Пакетный") && targetPosition != null) {
            double posX = mc.player.getPosX();
            double posY = mc.player.getPosY();
            double posZ = mc.player.getPosZ();

            Vector3d motion = targetPosition.subtract(posX, posY, posZ).normalize().scale(FORWARD_DISTANCE);

            mc.getConnection().sendPacket(new CPlayerPacket.PositionPacket(
                    posX + motion.x,
                    posY + motion.y,
                    posZ + motion.z,
                    true
            ));
        }
    }

    public boolean collisionPredict(Vector3d to) {
        List<VoxelShape> collisionList = mc.world.getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625D)).toList();
        boolean prevCollision = collisionList.isEmpty();
        Vector3d backUp = mc.player.getPositionVec();
        mc.player.setPosition(to.x, to.y, to.z);
        collisionList = mc.world.getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625D)).toList();
        boolean collision = collisionList.isEmpty() && prevCollision;
        mc.player.setPosition(backUp.x, backUp.y, backUp.z);
        return collision;
    }
}
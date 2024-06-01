package best.actinium.util.player;

import best.actinium.component.componets.RotationComponent;
import best.actinium.module.impl.movement.scaffold.ScafUtil;
import best.actinium.util.IAccess;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjglx.util.vector.Vector2f;

public class VectorUtil implements IAccess {
    public static Vec3 getNewVector(ScafUtil.BlockData lastblockdata) {
        if(lastblockdata == null) {
            return null;
        }

        BlockPos pos = lastblockdata.getPosition();

        double x = pos.getX() + Math.random(), y = pos.getY() + Math.random(), z = pos.getZ() + Math.random();

        final MovingObjectPosition movingObjectPosition = RayTraceUtil.rayCast(RotationComponent.rotations != null ? RotationComponent.rotations : new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotIncrement), mc.playerController.getBlockReachDistance());

        switch (lastblockdata.getFacing()) {
            case DOWN:
                x = pos.getY();
                break;

            case UP:
                y = pos.getY() + 1;
                break;

            case NORTH:
                z = pos.getZ();
                break;

            case EAST:
                x = pos.getX() + 1;
                break;

            case SOUTH:
                z = pos.getZ() + 1;
                break;

            case WEST:
                x = pos.getX();
                break;
        }

        //we love java dont we
        if (movingObjectPosition != null && movingObjectPosition.getBlockPos() != null && movingObjectPosition.getBlockPos().equals(lastblockdata.getPosition()) &&
                movingObjectPosition.sideHit == lastblockdata.getFacing()) {
            //more of a pain then the old one but still
            x = movingObjectPosition.hitVec.xCoord;
            y = movingObjectPosition.hitVec.yCoord;
            z = movingObjectPosition.hitVec.zCoord;
        }

        return new Vec3(x,y,z);
    }
}

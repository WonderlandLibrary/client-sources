package wtf.resolute.moduled.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;

import java.util.ArrayList;
import java.util.List;

import static wtf.resolute.utiled.client.IMinecraft.mc;

@ModuleAnontion(name = "PearlTarget", type = Categories.Player,server = "")
public class PearlTarget extends Module {

    @Subscribe
    public void onUpdate(EventUpdate e) {
    }

    public List<Vector3d> getPearlPositions(EnderPearlEntity entity) {
        Vector3d pearlPosition = entity.getPositionVec();
        Vector3d pearlMotion = entity.getMotion();
        Vector3d lastPosition;

        List<Vector3d> positions = new ArrayList<>();

        for (int i = 0; i < 300; i++) {
            lastPosition = pearlPosition;
            pearlPosition = pearlPosition.add(pearlMotion);
            pearlMotion = updatePearlMotion(entity, pearlMotion);

            if (shouldEntityHit(pearlPosition, lastPosition) || pearlPosition.y <= 0) {
                break;
            }
            positions.add(pearlPosition);
        }
        return positions;
    }

    private Vector3d updatePearlMotion(EnderPearlEntity pearl, Vector3d originalPearlMotion) {
        Vector3d pearlMotion = originalPearlMotion;
        if (pearl.isInWater()) {
            pearlMotion = pearlMotion.scale(0.8f);
        } else {
            pearlMotion = pearlMotion.scale(0.99f);
        }

        if (!pearl.hasNoGravity())
            pearlMotion.y -= pearl.getGravityVelocity();

        return pearlMotion;
    }

    private boolean shouldEntityHit(Vector3d pearlPosition, Vector3d lastPosition) {
        final RayTraceContext rayTraceContext = new RayTraceContext(
                lastPosition,
                pearlPosition,
                RayTraceContext.BlockMode.COLLIDER,
                RayTraceContext.FluidMode.NONE,
                mc.player
        );
        final BlockRayTraceResult blockHitResult = mc.world.rayTraceBlocks(rayTraceContext);

        return blockHitResult.getType() == RayTraceResult.Type.BLOCK;
    }

}

package us.dev.direkt.module.internal.core.listeners;

import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.render.EventRender3D;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.Module;
import us.dev.direkt.util.render.OGLRender;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

import java.util.Map;

@ModData(label = "Position Listener", category = ModCategory.CORE)
public class EntityPositionListener extends Module {

	private static Map<Entity, Vec3d> entityUpperBounds = Maps.newHashMap();
	private static Map<Entity, Vec3d> entityLowerBounds = Maps.newHashMap();

    @Listener
    protected Link<EventRender3D> onRenderTick = new Link<>(event -> {
        if (!entityUpperBounds.isEmpty()) {
            entityUpperBounds.clear();
        }
        if (!entityLowerBounds.isEmpty()) {
            entityLowerBounds.clear();
        }
        for (Entity e : Wrapper.getLoadedEntities()) {
            final Vec3d bound = getEntityRenderPosition(e).addVector(0, e.height + 0.2, 0);
            Vec3d upperBounds = OGLRender.to2D(bound.xCoord, bound.yCoord, bound.zCoord),
                    lowerBounds = OGLRender.to2D(bound.xCoord, bound.yCoord - 2, bound.zCoord);
            if (upperBounds != null && lowerBounds != null) {
                entityUpperBounds.put(e, upperBounds);
                entityLowerBounds.put(e, lowerBounds);
            }
        }
    });
	
    private Vec3d getEntityRenderPosition(Entity entity) {
        double partial = Wrapper.getTimer().renderPartialTicks;

        double x = entity.lastTickPosX + ((entity.posX - entity.lastTickPosX) * partial) - Wrapper.getMinecraft().getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * partial) - Wrapper.getMinecraft().getRenderManager().viewerPosY;
        double z = entity.lastTickPosZ + ((entity.posZ - entity.lastTickPosZ) * partial) - Wrapper.getMinecraft().getRenderManager().viewerPosZ;

        return new Vec3d(x, y, z);
    }
    
    public static Map<Entity, Vec3d> getEntityUpperBounds(){
    	return entityUpperBounds;
    }
    
    public static Map<Entity, Vec3d> getEntityLowerBounds(){
    	return entityLowerBounds;
    }
    
}


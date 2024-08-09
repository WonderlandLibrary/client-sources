package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.WorldEvent;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.utils.render.ColorUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import static org.lwjgl.opengl.GL11.*;

@FunctionRegister(name = "Predictions", type = Category.Render)
public class Predictions extends Function {

    @Subscribe
    public void onRender(WorldEvent event) {
        glPushMatrix();

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);

        glEnable(GL_BLEND);
        glEnable(GL_LINE_SMOOTH);

        Vector3d renderOffset = mc.getRenderManager().info.getProjectedView();

        glTranslated(-renderOffset.x, -renderOffset.y, -renderOffset.z);

        glLineWidth(3);

        buffer.begin(1, DefaultVertexFormats.POSITION);

        for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof EnderPearlEntity throwable) {
                Vector3d motion = throwable.getMotion();
                Vector3d pos = throwable.getPositionVec();
                Vector3d prevPos;

                for (int i = 0; i < 150; i++) {
                    prevPos = pos;
                    pos = pos.add(motion);
                    motion = getNextMotion(throwable, motion);

                    ColorUtils.setColor(HUD.getColor(i));

                    buffer.pos(prevPos.x, prevPos.y, prevPos.z).endVertex();

                    RayTraceContext rayTraceContext = new RayTraceContext(
                            prevPos,
                            pos,
                            RayTraceContext.BlockMode.COLLIDER,
                            RayTraceContext.FluidMode.NONE,
                            throwable
                    );

                    BlockRayTraceResult blockHitResult = mc.world.rayTraceBlocks(rayTraceContext);

                    boolean isLast = blockHitResult.getType() == RayTraceResult.Type.BLOCK;

                    if (isLast) {
                        pos = blockHitResult.getHitVec();
                    }

                    buffer.pos(pos.x, pos.y, pos.z).endVertex();

                    if (isLast || pos.y < 0) break;
                }
            }
        }

        tessellator.draw();

        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        glPopMatrix();
    }

    private Vector3d getNextMotion(ThrowableEntity throwable, Vector3d motion) {
        if (throwable.isInWater()) {
            motion = motion.scale(0.8);
        } else {
            motion = motion.scale(0.99);
        }

        if (!throwable.hasNoGravity()) {
            motion.y -= throwable.getGravityVelocity();
        }

        return motion;
    }
}

package wtf.resolute.moduled.impl.render;

import com.google.common.eventbus.Subscribe;
import net.minecraft.util.ResourceLocation;
import wtf.resolute.evented.WorldEvent;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.impl.render.HUD.HUD;
import wtf.resolute.utiled.render.ColorUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import static org.lwjgl.opengl.GL11.*;
@ModuleAnontion(name = "PearlPredict", type = Categories.Render,server = "")
public class Predictions extends Module {

    private final ResourceLocation pearl = new ResourceLocation("resolute/images/pearl.png");

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

        Vector3d finalPos = null;

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
                    if (isLast || pos.y < 0) {
                        finalPos = pos;
                        break;
                    }
                }
            }
        }

        tessellator.draw();

        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        glPopMatrix();

        if (finalPos != null) {
            renderMarkerAt(finalPos, renderOffset);
        }
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

    private void renderMarkerAt(Vector3d position, Vector3d renderOffset) {
        glPushMatrix();

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);

        glTranslated(position.x - renderOffset.x, position.y - renderOffset.y, position.z - renderOffset.z);

        float scale = 0.2F;
        glScalef(scale, scale, scale);

        glColor4f(1.0F, 0.0F, 0.0F, 1.0F); // Red color

        glBegin(GL_QUADS);
        glVertex3d(-1, 0, -1);
        glVertex3d(1, 0, -1);
        glVertex3d(1, 0, 1);
        glVertex3d(-1, 0, 1);
        glEnd();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);

        glPopMatrix();
    }
}

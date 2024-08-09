/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.WorldEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.render.HUD;
import mpp.venusfr.utils.render.ColorUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name="Predictions", type=Category.Visual)
public class Predictions
extends Function {
    @Subscribe
    public void onRender(WorldEvent worldEvent) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        Vector3d vector3d = Predictions.mc.getRenderManager().info.getProjectedView();
        GL11.glTranslated(-vector3d.x, -vector3d.y, -vector3d.z);
        GL11.glLineWidth(3.0f);
        buffer.begin(1, DefaultVertexFormats.POSITION);
        block0: for (Entity entity2 : Predictions.mc.world.getAllEntities()) {
            if (!(entity2 instanceof EnderPearlEntity)) continue;
            EnderPearlEntity enderPearlEntity = (EnderPearlEntity)entity2;
            Vector3d vector3d2 = enderPearlEntity.getMotion();
            Vector3d vector3d3 = enderPearlEntity.getPositionVec();
            for (int i = 0; i < 150; ++i) {
                boolean bl;
                Vector3d vector3d4 = vector3d3;
                vector3d3 = vector3d3.add(vector3d2);
                vector3d2 = this.getNextMotion(enderPearlEntity, vector3d2);
                ColorUtils.setColor(HUD.getColor(i));
                buffer.pos(vector3d4.x, vector3d4.y, vector3d4.z).endVertex();
                RayTraceContext rayTraceContext = new RayTraceContext(vector3d4, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, enderPearlEntity);
                BlockRayTraceResult blockRayTraceResult = Predictions.mc.world.rayTraceBlocks(rayTraceContext);
                boolean bl2 = bl = blockRayTraceResult.getType() == RayTraceResult.Type.BLOCK;
                if (bl) {
                    vector3d3 = blockRayTraceResult.getHitVec();
                }
                buffer.pos(vector3d3.x, vector3d3.y, vector3d3.z).endVertex();
                if (bl || vector3d3.y < 0.0) continue block0;
            }
        }
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }

    private Vector3d getNextMotion(ThrowableEntity throwableEntity, Vector3d vector3d) {
        vector3d = throwableEntity.isInWater() ? vector3d.scale(0.8) : vector3d.scale(0.99);
        if (!throwableEntity.hasNoGravity()) {
            vector3d.y -= (double)throwableEntity.getGravityVelocity();
        }
        return vector3d;
    }
}


package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.event.render.EventRender3D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.render.ColorUtility;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.HashMap;

@ModuleAnnotation(name = "Predict", category = Category.RENDER)
public class Predict extends Module {
    private final MultiBooleanSetting objects = new MultiBooleanSetting("Objects", Arrays.asList("Pearls", "Arrows"));

    @EventTarget
    public void onRender(EventRender3D eventRender3D) {
        double ix = -(mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * eventRender3D.getPartialTicks());
        double iy = -(mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * eventRender3D.getPartialTicks());
        double iz = -(mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * eventRender3D.getPartialTicks());

        GlStateManager.pushMatrix();
        GlStateManager.translate(ix, iy, iz);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1.5f);
        GL11.glBegin(GL11.GL_LINES);

        {
            for (Entity entity : mc.world.loadedEntityList) {
                if (objects.get(0) && entity instanceof EntityEnderPearl) {
                    drawPrediction(entity, ((EntityEnderPearl) entity).getGravityVelocity(), 0.8F);
                }
                if (objects.get(1) && entity instanceof EntityArrow) {
                    drawPrediction(entity, 0.05, 0.6F);
                }
            }
        }

        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        GlStateManager.popMatrix();
    }


    public HashMap<Entity, Vec3d> lastPoss = new HashMap<>();
    public HashMap<Entity, Integer> i1 = new HashMap<>();

    private void drawPrediction(Entity e, double g, double water) {
        double motionX = e.motionX;
        double motionY = e.motionY;
        double motionZ = e.motionZ;
        double x = e.posX;
        double y = e.posY;
        double z = e.posZ;

        for (int i = 0; i < 300; i++) {
            ColorUtility.setColor(Arraylist.getColor(i * 4));
            Vec3d lastPos = new Vec3d(x, y, z);
            x += motionX;
            y += motionY;
            z += motionZ;
            if (mc.world.getBlockState(new BlockPos((int) x, (int) y, (int) z)).getBlock() == Blocks.WATER) {
                motionX *= water;
                motionY *= water;
                motionZ *= water;
            } else {
                motionX *= 0.99;
                motionY *= 0.99;
                motionZ *= 0.99;
            }
            motionY -= g;
            Vec3d pos = new Vec3d(x, y, z);
            if (mc.world.rayTraceBlocks(lastPos, pos) != null) {

                if (mc.world.rayTraceBlocks(lastPos, pos).typeOfHit == RayTraceResult.Type.ENTITY) {
                    break;
                }
                break;
            }

            if (y <= 0) break;
            if (e.motionZ == 0 && e.motionX == 0 && e.motionY == 0) continue;
            lastPoss.put(e, new Vec3d(lastPos.x, lastPos.y, lastPos.z));
            GL11.glVertex3d(lastPos.x, lastPos.y, lastPos.z);
            GL11.glVertex3d(x, y, z);
            i1.put(e, i);
        }
    }
}

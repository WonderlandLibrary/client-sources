/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import mpp.venusfr.events.WorldEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.render.HUD;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.render.ColorUtils;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name="Trails", type=Category.Visual)
public class Trails
extends Function {
    @Subscribe
    public void onRender(WorldEvent worldEvent) {
        for (PlayerEntity object : Trails.mc.world.getPlayers()) {
            object.points.removeIf(Trails::lambda$onRender$0);
            if (!(object instanceof ClientPlayerEntity) || object == Trails.mc.player && Trails.mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) continue;
            Vector3d vector3d = new Vector3d(MathUtil.interpolate(object.getPosX(), object.lastTickPosX, (double)worldEvent.getPartialTicks()), MathUtil.interpolate(object.getPosY(), object.lastTickPosY, (double)worldEvent.getPartialTicks()), MathUtil.interpolate(object.getPosZ(), object.lastTickPosZ, (double)worldEvent.getPartialTicks()));
            object.points.add(new Point(vector3d));
        }
        RenderSystem.pushMatrix();
        Vector3d vector3d = Trails.mc.getRenderManager().info.getProjectedView();
        RenderSystem.translated(-vector3d.x, -vector3d.y, -vector3d.z);
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.disableTexture();
        RenderSystem.blendFunc(770, 771);
        RenderSystem.shadeModel(7425);
        RenderSystem.disableAlphaTest();
        RenderSystem.depthMask(false);
        RenderSystem.lineWidth(3.0f);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        for (Entity entity2 : Trails.mc.world.getAllEntities()) {
            float f;
            float f2;
            GL11.glBegin(8);
            ArrayList<Point> arrayList = entity2.points;
            for (Point point : arrayList) {
                f2 = arrayList.indexOf(point);
                f = f2 / (float)arrayList.size();
                ColorUtils.setAlphaColor(HUD.getColor(arrayList.indexOf(point), 2.0f), f * 0.5f);
                GL11.glVertex3d(point.getPosition().x, point.getPosition().y, point.getPosition().z);
                GL11.glVertex3d(point.getPosition().x, point.getPosition().y + (double)entity2.getHeight(), point.getPosition().z);
            }
            GL11.glEnd();
            GL11.glBegin(3);
            for (Point point : arrayList) {
                f2 = arrayList.indexOf(point);
                f = f2 / (float)arrayList.size();
                ColorUtils.setAlphaColor(HUD.getColor(arrayList.indexOf(point), 2.0f), f);
                GL11.glVertex3d(point.getPosition().x, point.getPosition().y, point.getPosition().z);
            }
            GL11.glEnd();
            GL11.glBegin(3);
            for (Point point : arrayList) {
                f2 = arrayList.indexOf(point);
                f = f2 / (float)arrayList.size();
                ColorUtils.setAlphaColor(HUD.getColor(arrayList.indexOf(point), 2.0f), f);
                GL11.glVertex3d(point.getPosition().x, point.getPosition().y + (double)entity2.getHeight(), point.getPosition().z);
            }
            GL11.glEnd();
        }
        GL11.glHint(3154, 4352);
        GL11.glDisable(2848);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableCull();
        RenderSystem.shadeModel(7424);
        RenderSystem.depthMask(true);
        RenderSystem.popMatrix();
    }

    private static boolean lambda$onRender$0(Point point) {
        return point.time.isReached(500L);
    }

    public static class Point {
        private final Vector3d position;
        private final StopWatch time = new StopWatch();

        public Point(Vector3d vector3d) {
            this.position = vector3d;
        }

        public Vector3d getPosition() {
            return this.position;
        }

        public StopWatch getTime() {
            return this.time;
        }
    }
}


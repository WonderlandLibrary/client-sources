package fun.ellant.functions.impl.render;

import java.util.Iterator;
import java.util.List;

import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.functions.settings.Setting;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import org.lwjgl.opengl.GL11;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.systems.RenderSystem;

import fun.ellant.events.WorldEvent;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.render.ColorUtils;
import lombok.Getter;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name = "Trails", type = Category.RENDER, desc = "За вами остаётся след")
public class Trails extends Function {
    private final BooleanSetting hider = new BooleanSetting("Скрывать в 1 лице", true);
    public ModeSetting mod = new ModeSetting("Мод", "Обычный", new String[]{"Обычный", "Линия", "Точки"});
    public SliderSetting pointsTime = new SliderSetting("Длина", 500.0F, 165.0F, 2200.0F, 0.5F);
    public SliderSetting tolshina = new SliderSetting("Размер поинтов", 30.0F, 10.0F, 55.0F, 0.5F);
    private Vector3d lastPoint = null;

    public Trails() {
        this.addSettings(new Setting[]{this.hider, this.pointsTime, this.mod, this.tolshina});
    }

    @Subscribe
    public void onRender(WorldEvent event) {
        float pointsi = (Float)this.pointsTime.get();
        Iterator bb = mc.world.getPlayers().iterator();

        while(true) {
            PlayerEntity entity;
            Vector3d player;
            do {
                do {
                    do {
                        if (!bb.hasNext()) {
                            RenderSystem.pushMatrix();
                            Vector3d projection = mc.getRenderManager().info.getProjectedView();
                            RenderSystem.translated(-projection.x, -projection.y, -projection.z);
                            RenderSystem.enableBlend();
                            RenderSystem.disableCull();
                            RenderSystem.disableTexture();
                            RenderSystem.blendFunc(770, 771);
                            RenderSystem.shadeModel(7425);
                            RenderSystem.disableAlphaTest();
                            RenderSystem.depthMask(false);
                            RenderSystem.lineWidth(3.0F);
                            GL11.glEnable(2848);
                            GL11.glHint(3154, 4354);
                            Iterator aa = mc.world.getAllEntities().iterator();

                            while(aa.hasNext()) {
                                Entity entity1 = (Entity)aa.next();
                                if (this.mod.is("Обычный")) {
                                    GL11.glBegin(8);
                                } else if (this.mod.is("Линия")) {
                                    GL11.glBegin(3);
                                } else if (this.mod.is("Точки")) {
                                    GL11.glEnable(2832);
                                    GL11.glHint(3153, 4354);
                                    float tolshinaa = (Float)this.tolshina.get();
                                    GL11.glPointSize(tolshinaa);
                                    GL11.glBegin(0);
                                }

                                List<fun.ellant.functions.impl.render.Trails.Point> points = entity1.points;
                                Iterator var8 = points.iterator();

                                while(var8.hasNext()) {
                                    fun.ellant.functions.impl.render.Trails.Point point = (fun.ellant.functions.impl.render.Trails.Point)var8.next();
                                    float index = (float)points.indexOf(point);
                                    float alpha = index / (float)points.size();
                                    ColorUtils.setAlphaColor(HUD.getColor(points.indexOf(point), 2.0F), alpha * 0.5F);
                                    GL11.glVertex3d(point.getPosition().x, point.getPosition().y, point.getPosition().z);
                                    if (this.mod.is("Обычный")) {
                                        GL11.glVertex3d(point.getPosition().x, point.getPosition().y + (double)entity1.getHeight(), point.getPosition().z);
                                    }
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
                            return;
                        }

                        entity = (PlayerEntity)bb.next();
                        entity.points.removeIf((p) -> {
                            return p.time.isReached((long)pointsi);
                        });
                    } while(!(entity instanceof ClientPlayerEntity));
                } while((Boolean)this.hider.get() && entity == mc.player && mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON);

                player = new Vector3d(MathUtil.interpolate(entity.getPosX(), entity.lastTickPosX, (double)event.getPartialTicks()), MathUtil.interpolate(entity.getPosY(), entity.lastTickPosY, (double)event.getPartialTicks()), MathUtil.interpolate(entity.getPosZ(), entity.lastTickPosZ, (double)event.getPartialTicks()));
            } while(this.lastPoint != null && !(this.lastPoint.distanceTo(player) > 1.0E-8D));

            entity.points.add(new fun.ellant.functions.impl.render.Trails.Point(this, player));
            this.lastPoint = player;
        }
    }
    @Getter
    public static class Point {

        private final Vector3d position;
        private final StopWatch time = new StopWatch();

        public Point(Trails trails, Vector3d position) {
            this.position = position;
        }
    }
}
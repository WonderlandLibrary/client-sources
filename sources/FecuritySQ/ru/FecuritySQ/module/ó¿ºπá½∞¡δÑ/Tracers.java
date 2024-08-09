package ru.FecuritySQ.module.визуальные;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventHud;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionColor;
import ru.FecuritySQ.option.imp.OptionNumric;
import ru.FecuritySQ.utils.MathUtil;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;

public class Tracers extends Module {

    public OptionColor color = new OptionColor("Обычный цвет", new Color(255, 255, 255, 255));
    public OptionColor fcolor = new OptionColor("Цвет друзей", new Color(25, 227, 142, 255));

    public Tracers() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
        addOption(color);
        addOption(fcolor);
    }

    @Override
    public void event(Event event) {
        if(event instanceof EventHud && isEnabled()) {
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST );
            for (Entity e : mc.world.getAllEntities()) {
                if (e != mc.player && e instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity)e;
                    Vector3d cords = MathUtil.interpolate(player, mc.getRenderPartialTicks());
                    double[] vec = RenderUtil.project(cords.x, cords.y, cords.z);
                    if(vec != null) {
                        GL11.glPushMatrix();
                        GL11.glBlendFunc(770, 771);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                        Color color = FecuritySQ.get().getFriendManager().isFriend(player.getName().getString()) ? this.color.get() : this.fcolor.get();

                        GL11.glColor3f(color.getRed(), color.getGreen(), color.getBlue());

                        GL11.glEnable(GL11.GL_LINE_SMOOTH);
                        GL11.glLineWidth(1F);
                        GL11.glBegin(GL11.GL_LINES);
                        int startx = mc.getMainWindow().getScaledWidth() / 2;
                        int starty = mc.getMainWindow().getScaledHeight() / 2;
                        GL11.glVertex2d(startx, starty);
                        GL11.glVertex2d(vec[0], vec[1]);
                        GL11.glEnd();
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glDisable(GL11.GL_LINE_SMOOTH);
                        GL11.glPopMatrix();
                    }
                }
            }
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glPopMatrix();
        }
    }

}

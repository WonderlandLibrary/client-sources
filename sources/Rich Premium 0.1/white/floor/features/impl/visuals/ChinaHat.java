package white.floor.features.impl.visuals;

import java.awt.*;
import java.util.ArrayList;

import clickgui.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;


import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.Event3D;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;

public class ChinaHat extends Feature {

    public ChinaHat() {
        super("ChinaHat", "Kitayoz yaya",0, Category.VISUALS);
    }

    @EventTarget
    public void asf(Event3D event) {

        ItemStack stack = mc.player.getEquipmentInSlot(4);
        final double height = stack.getItem() instanceof ItemArmor ? mc.player.isSneaking() ? -0.1 : 0.12 : mc.player.isSneaking() ? -0.22 : 0;

        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glTranslatef(0f, (float) (mc.player.height + 0 + height), 0f);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glRotatef(-mc.player.rotationYaw, 0f, 1f, 0f);

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex3d(0.0, 0.3, 0.0);
        double radius = 0.5;
        for (int i = 0; i < 361; i += 12) {
                DrawHelper.setColor(DrawHelper.setAlpha(DrawHelper.astolfoColors45(i - i + 1, i, 0.5f, 16), 195).getRGB());
            GL11.glVertex3d(Math.cos(i * Math.PI / 180.0) * radius, 0, Math.sin(i * Math.PI / 180.0) * radius);
            GL11.glVertex3d(Math.cos(Math.toRadians(i)) * radius, -0.04, Math.sin(Math.toRadians(i)) * radius);
        }
        GL11.glVertex3d(0.0, 0.3, 0.0);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.resetColor();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}
package white.floor.features.impl.visuals;

import clickgui.setting.Setting;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.Event3D;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.features.impl.combat.KillauraTest;
import white.floor.features.impl.combat.KillauraTest;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;

public class TargetESP extends Feature {

    double height;
    boolean animat;

    public TargetESP() {
        super("TargetESP", "Beautiful circle on enemy.", 0, Category.VISUALS);
        ArrayList<String> targetEsp = new ArrayList<String>();
        targetEsp.add("Jello");
        targetEsp.add("Sims");
        targetEsp.add("JelloFDP");
        Main.settingsManager.rSetting(new Setting("TargetESP Mode", this, "Jello", targetEsp));
        Main.settingsManager.rSetting(new Setting("Speed", this, 0.2, 0.1, 0.5, false));
        Main.settingsManager.rSetting(new Setting("Size", this, 0.4, 0.1, 1, false));
        Main.settingsManager.rSetting(new Setting("Alpha", this, 1.2, 1, 2, false));
        Main.settingsManager.rSetting(new Setting("DepthTest", this, false));
    }

    @EventTarget
    public void jija(Event3D xaski) {

        String mode = Main.instance.settingsManager.getSettingByName(Main.featureDirector.getModule(TargetESP.class), "TargetESP Mode").getValString();

        this.setModuleName("TargetESP " + ChatFormatting.GRAY + "[" + mode + ChatFormatting.GRAY + "]");

        if (KillauraTest.target != null && KillauraTest.target.getHealth() > 0.0 && mc.player.getDistanceToEntity(KillauraTest.target) <= Main.instance.settingsManager.getSettingByName(Main.featureDirector.getModule(KillauraTest.class), "Range").getValDouble() && Main.featureDirector.getModule(KillauraTest.class).isToggled()) {

            if (mode.equalsIgnoreCase("Jello"))
                DrawHelper.jelloCircle(Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TargetESP.class), "Speed").getValDouble() / 20, Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TargetESP.class), "Size").getValDouble(), Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TargetESP.class), "Alpha").getValDouble(), KillauraTest.target);

            if (mode.equalsIgnoreCase("Sims")) {
                float radius = 0.2f;
                int side = 4;

                if (animat)
                {
                    height = MathHelper.lerp(height, 0.4, 2 * Feature.deltaTime());
                    if (height > 0.39) animat = false;
                }
                else
                {
                    height = MathHelper.lerp(height, 0.1, 4 * Feature.deltaTime());
                    if (height < 0.11) animat = true;
                }

                GL11.glPushMatrix();
                GL11.glTranslated(KillauraTest.target.lastTickPosX + (KillauraTest.target.posX - KillauraTest.target.lastTickPosX) * xaski.getPartialTicks() - mc.renderManager.viewerPosX, (KillauraTest.target.lastTickPosY + (KillauraTest.target.posY - KillauraTest.target.lastTickPosY) * xaski.getPartialTicks() - mc.renderManager.viewerPosY) + KillauraTest.target.height + height, KillauraTest.target.lastTickPosZ + (KillauraTest.target.posZ - KillauraTest.target.lastTickPosZ) * xaski.getPartialTicks() - mc.renderManager.viewerPosZ);
                GL11.glRotatef((mc.player.ticksExisted + mc.timer.renderPartialTicks) * 10, 0.0f, 1.0F, 0.0f);
                DrawHelper.setColor(KillauraTest.target.hurtTime > 0 ? DrawHelper.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs(System.currentTimeMillis() / (long) 15) / 100.0 + 6.0F * (1 * 2.55) / 60).getRGB() : DrawHelper.TwoColoreffect(new Color(90, 200, 79), new Color(30, 120, 20), Math.abs(System.currentTimeMillis() / (long) 15) / 100.0 + 6.0F * (1 * 2.55) / 90).getRGB());
                DrawHelper.enableSmoothLine(0.5F);
                Cylinder c = new Cylinder();
                c.setDrawStyle(GLU.GLU_LINE);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                c.draw(0F, radius, 0.3f, side, 100);
                GL11.glTranslated(0.0, 0.0, 0.3);
                c.draw(radius, 0f, 0.3f, side, 100);
                DrawHelper.disableSmoothLine();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }

            if (mode.equalsIgnoreCase("JelloFDP")) {
                    double everyTime = 1500;
                    double drawTime = (System.currentTimeMillis() % everyTime);
                    boolean drawMode = drawTime > (everyTime / 2);
                    double drawPercent = drawTime / (everyTime / 2);
                    // true when goes up
                    if (!drawMode) {
                        drawPercent = 1 - drawPercent;
                    } else {
                        drawPercent -= 1;
                    }

                    drawPercent = MathHelper.easeInOutQuad(drawPercent, 2);

                    mc.entityRenderer.disableLightmap();
                    GL11.glPushMatrix();
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glEnable(GL11.GL_LINE_SMOOTH);
                    GL11.glEnable(GL11.GL_BLEND);
                    if(Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TargetESP.class), "DepthTest").getValBoolean())
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glDisable(GL11.GL_CULL_FACE);
                    GL11.glShadeModel(7425);
                    mc.entityRenderer.disableLightmap();

                    double radius = KillauraTest.target.width;
                    double height = KillauraTest.target.height + 0.1;
                    double x = KillauraTest.target.lastTickPosX + (KillauraTest.target.posX - KillauraTest.target.lastTickPosX) * xaski.getPartialTicks() - mc.renderManager.viewerPosX;
                    double y = (KillauraTest.target.lastTickPosY + (KillauraTest.target.posY - KillauraTest.target.lastTickPosY) * xaski.getPartialTicks() - mc.renderManager.viewerPosY) + height * drawPercent;
                    double z = KillauraTest.target.lastTickPosZ + (KillauraTest.target.posZ - KillauraTest.target.lastTickPosZ) * xaski.getPartialTicks() - mc.renderManager.viewerPosZ;
                    double eased = (height / 3) * ((drawPercent > 0.5) ? 1 - drawPercent : drawPercent) * ((drawMode) ? -1 : 1);

                    for (int lox = 0; lox < 360; lox += 5) {
                        Color color = Main.getClientColor();
                        double x1 = x - Math.sin(lox * Math.PI / 180F) * radius;
                        double z1 = z + Math.cos(lox * Math.PI / 180F) * radius;
                        double x2 = x - Math.sin((lox - 5) * Math.PI / 180F) * radius;
                        double z2 = z + Math.cos((lox - 5) * Math.PI / 180F) * radius;

                        GL11.glBegin(GL11.GL_QUADS);
                        DrawHelper.glColor(color, 0f);
                        GL11.glVertex3d(x1, y + eased, z1);
                        GL11.glVertex3d(x2, y + eased, z2);
                        DrawHelper.glColor(color, 255);
                        GL11.glVertex3d(x2, y, z2);
                        GL11.glVertex3d(x1, y, z1);
                        GL11.glEnd();

                        GL11.glBegin(GL_LINE_LOOP);
                        GL11.glVertex3d(x2, y, z2);
                        GL11.glVertex3d(x1, y, z1);
                        GL11.glEnd();
                    }

                    GL11.glEnable(GL11.GL_CULL_FACE);
                    GL11.glShadeModel(7424);
                    GL11.glColor4f(1f, 1f, 1f, 1f);
                    if(Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TargetESP.class), "DepthTest").getValBoolean())
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glDisable(GL11.GL_LINE_SMOOTH);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glPopMatrix();
                }
            }
        }




    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}

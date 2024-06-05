package rip.helium.cheat.impl.visual;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.EntityRenderEvent;
import rip.helium.utils.property.impl.DoubleProperty;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class SkeletonESP extends Cheat {

    public SkeletonESP() {
        super("Skeletal", "Skeleton Esp.", CheatCategory.VISUAL);
    }

    //private static Map<EntityPlayer, float[][]> entities;
    private static final Map<EntityPlayer, float[][]> playerRotationMap = new HashMap<>();
    int red = 255;
    int green = 255;
    int blue = 255;


    public static void addEntity(EntityPlayer e, ModelPlayer model) {
        playerRotationMap.put(e, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
    }

    @Collect
    public final void onRender(EntityRenderEvent event) {
        setupRender(true);
        playerRotationMap.keySet().removeIf(player -> !mc.theWorld.playerEntities.contains(player));
        for (EntityPlayer player : playerRotationMap.keySet()) {
            if (player instanceof EntityPlayerSP || player.isInvisible()) {
                continue;
            }
            glPushMatrix();
            final float[][] modelRotations = playerRotationMap.get(player);
            glLineWidth(1.0f);
            glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final double x = player.posX;
            final double y = player.posY;
            final double z = player.posZ;
            glTranslated(x, y, z);
            final float bodyYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * mc.timer.renderPartialTicks;
            glRotatef(-bodyYawOffset, 0.0f, 1.0f, 0.0f);
            glTranslated(0.0, 0.0, player.isSneaking() ? -0.235 : 0.0);
            final float legHeight = player.isSneaking() ? 0.6f : 0.75f;
            glPushMatrix();
            glTranslated(-0.125, legHeight, 0.0);
            if (modelRotations[3][0] != 0.0f) {
                glRotatef(modelRotations[3][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[3][1] != 0.0f) {
                glRotatef(modelRotations[3][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[3][2] != 0.0f) {
                glRotatef(modelRotations[3][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            glBegin(3);
            glVertex3d(0.0, 0.0, 0.0);
            glVertex3d(0.0, -legHeight, 0.0);
            glEnd();
            glPopMatrix();
            glPushMatrix();
            glTranslated(0.125, legHeight, 0.0);
            if (modelRotations[4][0] != 0.0f) {
                glRotatef(modelRotations[4][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[4][1] != 0.0f) {
                glRotatef(modelRotations[4][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[4][2] != 0.0f) {
                glRotatef(modelRotations[4][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            glBegin(3);
            glVertex3d(0.0, 0.0, 0.0);
            glVertex3d(0.0, -legHeight, 0.0);
            glEnd();
            glPopMatrix();
            glTranslated(0.0, 0.0, player.isSneaking() ? 0.25 : 0.0);
            glPushMatrix();
            glTranslated(0.0, player.isSneaking() ? -0.05 : 0.0, player.isSneaking() ? -0.01725 : 0.0);

            // Left arm
            glPushMatrix();
            glTranslated(-0.375, (double) legHeight + 0.55, 0.0);
            if (modelRotations[1][0] != 0.0f) {
                glRotatef(modelRotations[1][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[1][1] != 0.0f) {
                glRotatef(modelRotations[1][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[1][2] != 0.0f) {
                glRotatef(-modelRotations[1][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            glBegin(3);
            glVertex3d(0.0, 0.0, 0.0);
            glVertex3d(0.0, -0.5, 0.0);
            glEnd();
            glPopMatrix();

            // Right arm
            glPushMatrix();
            glTranslated(0.375, (double) legHeight + 0.55, 0.0);
            if (modelRotations[2][0] != 0.0f) {
                glRotatef(modelRotations[2][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[2][1] != 0.0f) {
                glRotatef(modelRotations[2][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[2][2] != 0.0f) {
                glRotatef(-modelRotations[2][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            glBegin(3);
            glVertex3d(0.0, 0.0, 0.0);
            glVertex3d(0.0, -0.5, 0.0);
            glEnd();
            glPopMatrix();
            glRotatef(bodyYawOffset - player.rotationYawHead, 0.0f, 1.0f, 0.0f);

            // Head
            glPushMatrix();
            glTranslated(0.0, (double) legHeight + 0.55, 0.0);
            if (modelRotations[0][0] != 0.0f) {
                glRotatef(modelRotations[0][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            glBegin(3);
            glVertex3d(0.0, 0.0, 0.0);
            glVertex3d(0.0, 0.3, 0.0);
            glEnd();
            glPopMatrix();

            glPopMatrix();
            glRotatef(player.isSneaking() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
            glTranslated(0.0, player.isSneaking() ? -0.16175 : 0.0, player.isSneaking() ? -0.48025 : 0.0);

            // Pelvis
            glPushMatrix();
            glTranslated(0.0, legHeight, 0.0);
            glBegin(3);
            glVertex3d(-0.125, 0.0, 0.0);
            glVertex3d(0.125, 0.0, 0.0);
            glEnd();
            glPopMatrix();

            // Body
            glPushMatrix();
            glTranslated(0.0, legHeight, 0.0);
            glBegin(3);
            glVertex3d(0.0, 0.0, 0.0);
            glVertex3d(0.0, 0.55, 0.0);
            glEnd();
            glPopMatrix();

            // Shoulder
            glPushMatrix();
            glTranslated(0.0, (double) legHeight + 0.55, 0.0);
            glBegin(3);
            glVertex3d(-0.375, 0.0, 0.0);
            glVertex3d(0.375, 0.0, 0.0);
            glEnd();
            glPopMatrix();

            glPopMatrix();
        }
        setupRender(false);
    }

    private void setupRender(boolean start) {
        if (start) {
            glDisable(GL_LINE_SMOOTH);
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_TEXTURE_2D);
        } else {
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_DEPTH_TEST);
        }

        GL11.glDepthMask(!start);
    }
}

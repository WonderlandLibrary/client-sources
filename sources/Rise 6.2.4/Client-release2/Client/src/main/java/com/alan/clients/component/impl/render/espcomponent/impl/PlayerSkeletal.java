package com.alan.clients.component.impl.render.espcomponent.impl;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.component.impl.render.espcomponent.api.ESP;
import com.alan.clients.component.impl.render.espcomponent.api.ESPColor;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.math.MathInterpolation;
import com.alan.clients.util.render.ColorUtil;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.Config;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class PlayerSkeletal extends ESP implements Accessor {
    private final Map<EntityPlayer, float[][]> rotationMap = new HashMap<>();
    private static final float DEGREES_IN_RADIAN = 57.295776f;

    public PlayerSkeletal(ESPColor espColor) {
        super(espColor);
    }

    @Override
    public void render3D() {
        glPushMatrix();
        setupRenderState();

        for (Entity player : TargetComponent.getTargets()) {
            if (player instanceof EntityPlayer) {
                drawSkeleton((EntityPlayer) player, Config.renderPartialTicks);
            }
        }

        restoreRenderState();
        glPopMatrix();
    }

    private void setupRenderState() {
        glLineWidth(Client.INSTANCE.getModuleManager().get(com.alan.clients.module.impl.render.ESP.class).skeletalWidth.getValue().floatValue());
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        ColorUtil.glColor(Client.INSTANCE.getModuleManager().get(com.alan.clients.module.impl.render.ESP.class).whiteColor.getValue() ? Color.WHITE : espColor.getNormalColor());
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_TEXTURE_2D);
        glDepthMask(false);
    }

    private void restoreRenderState() {
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void updatePlayerAngles(EntityPlayer entityPlayer, ModelBiped modelBiped) {
        rotationMap.put(entityPlayer, new float[][]{
                {modelBiped.bipedHead.rotateAngleX, modelBiped.bipedHead.rotateAngleY, modelBiped.bipedHead.rotateAngleZ},
                {modelBiped.bipedRightArm.rotateAngleX, modelBiped.bipedRightArm.rotateAngleY, modelBiped.bipedRightArm.rotateAngleZ},
                {modelBiped.bipedLeftArm.rotateAngleX, modelBiped.bipedLeftArm.rotateAngleY, modelBiped.bipedLeftArm.rotateAngleZ},
                {modelBiped.bipedRightLeg.rotateAngleX, modelBiped.bipedRightLeg.rotateAngleY, modelBiped.bipedRightLeg.rotateAngleZ},
                {modelBiped.bipedLeftLeg.rotateAngleX, modelBiped.bipedLeftLeg.rotateAngleY, modelBiped.bipedLeftLeg.rotateAngleZ}
        });
    }

    private void drawSkeleton(EntityPlayer player, float partialTicks) {
        float[][] entPos = rotationMap.get(player);
        if (entPos != null) {
            glPushMatrix();

            float x = (float) (MathInterpolation.interpolate(player.posX, player.prevPosX, partialTicks) - RenderManager.renderPosX);
            float y = (float) (MathInterpolation.interpolate(player.posY, player.prevPosY, partialTicks) - RenderManager.renderPosY);
            float z = (float) (MathInterpolation.interpolate(player.posZ, player.prevPosZ, partialTicks) - RenderManager.renderPosZ);
            glTranslated(x, y, z);

            boolean sneaking = player.isSneaking();

            float rotationYawHead = player.rotationYawHead;
            float renderYawOffset = player.renderYawOffset;
            float prevRenderYawOffset = player.prevRenderYawOffset;

            float xOff = MathInterpolation.interpolate(renderYawOffset, prevRenderYawOffset, partialTicks);
            float yOff = sneaking ? 0.6F : 0.75F;

            glRotatef(-xOff, 0.0F, 1.0F, 0.0F);
            glTranslatef(0.0F, 0.0F, sneaking ? -0.235F : 0.0F);

            // draw limbs with rotation
            drawLimbs(entPos, yOff, sneaking, xOff, rotationYawHead);

            glPopMatrix();
        }
    }

    private void drawLimbs(float[][] entPos, float yOff, boolean sneaking, float xOff, float rotationYawHead) {
        // draw arms
        for (int i = 1; i <= 2; i++) {
            drawArm(entPos[i + 2], i == 1 ? -0.125F : 0.125F, yOff);
        }

        glTranslatef(0.0F, 0.0F, sneaking ? 0.25F : 0.0F);
        glPushMatrix();
        glTranslatef(0.0F, sneaking ? -0.05F : 0.0F, sneaking ? -0.01725F : 0.0F);

        // draw right and left arm
        for (int i = 1; i <= 2; i++) {
            drawLimb(entPos[i], i == 1 ? -0.375F : 0.375F, yOff + 0.55F);
        }

        // handle head position
        glRotatef(xOff - rotationYawHead, 0.0F, 1.0F, 0.0F);
        drawHead(entPos[0], yOff);

        glPopMatrix();

        // draw spine and other body parts
        drawSpine(yOff);
    }

    private void drawArm(float[] rotations, float xOffset, float yOff) {
        glPushMatrix();
        glTranslatef(xOffset, yOff, 0.0F);
        applyRotations(rotations);
        glBegin(GL_LINE_STRIP);
        glVertex3i(0, 0, 0);
        glVertex3f(0.0F, -yOff, 0.0F);
        glEnd();
        glPopMatrix();
    }

    private void drawLimb(float[] rotations, float xOffset, float yOff) {
        glPushMatrix();
        glTranslatef(xOffset, yOff, 0.0F);
        applyRotations(rotations);
        glBegin(GL_LINE_STRIP);
        glVertex3i(0, 0, 0);
        glVertex3f(0.0F, -0.5F, 0.0F);
        glEnd();
        glPopMatrix();
    }

    private void drawHead(float[] rotations, float yOff) {
        glPushMatrix();
        glTranslatef(0.0F, yOff + 0.55F, 0.0F);
        applyRotations(rotations);
        glBegin(GL_LINE_STRIP);
        glVertex3i(0, 0, 0);
        glVertex3f(0.0F, 0.3F, 0.0F);
        glEnd();
        glPopMatrix();
    }

    private void applyRotations(float[] rotations) {
        if (rotations[0] != 0.0F) {
            glRotatef(rotations[0] * DEGREES_IN_RADIAN, 1.0F, 0.0F, 0.0F);
        }
        if (rotations[1] != 0.0F) {
            glRotatef(rotations[1] * DEGREES_IN_RADIAN, 0.0F, 1.0F, 0.0F);
        }
        if (rotations[2] != 0.0F) {
            glRotatef(rotations[2] * DEGREES_IN_RADIAN, 0.0F, 0.0F, 1.0F);
        }
    }

    private void drawSpine(float yOff) {
        glPushMatrix();
        glTranslated(0.0F, yOff, 0.0F);
        glBegin(GL_LINE_STRIP);
        glVertex3f(-0.125F, 0.0F, 0.0F);
        glVertex3f(0.125F, 0.0F, 0.0F);
        glEnd();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(0.0F, yOff, 0.0F);
        glBegin(GL_LINE_STRIP);
        glVertex3i(0, 0, 0);
        glVertex3f(0.0F, 0.55F, 0.0F);
        glEnd();
        glPopMatrix();

        glPushMatrix();
        glTranslatef(0.0F, yOff + 0.55F, 0.0F);
        glBegin(GL_LINE_STRIP);
        glVertex3f(-0.375F, 0.0F, 0.0F);
        glVertex3f(0.375F, 0.0F, 0.0F);
        glEnd();
        glPopMatrix();
    }
}

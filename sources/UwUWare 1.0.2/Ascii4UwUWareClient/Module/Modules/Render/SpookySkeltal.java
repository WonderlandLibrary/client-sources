package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender3D;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.Render.RenderUtil;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;


public class SpookySkeltal
        extends Module {


    private static final Map<EntityPlayer, float[][]> modelRotations = new HashMap<EntityPlayer, float[][]>();

    public SpookySkeltal() {
        super("BoneESP", new String[]{"SpookySkeltal"}, ModuleType.Render);

    }

    @EventHandler
    public void onRender(EventRender3D event) {
        RenderUtil.setupRender(true);
        GL11.glDisable((int) 2848);
        GlStateManager.disableLighting();
        modelRotations.keySet().removeIf(player -> {
                    if (mc.theWorld.playerEntities.contains(player)) return false;
                    return true;
                } );
        mc.theWorld.playerEntities.forEach(player -> {
                    if (player == mc.thePlayer) return;
                    if (player.isInvisible()) {
                        return;
                    }
                    float[][] modelRotations = SpookySkeltal.modelRotations.get(player);
                    if (modelRotations == null) {
                        return;
                    }
                    GL11.glPushMatrix();
                    GL11.glLineWidth((float) 1.0f);
                    GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
                    Vec3 interp = RenderUtil.interpolateRender(player);
                    double x = interp.getX() - RenderManager.renderPosX;
                    double y = interp.getY() - RenderManager.renderPosY;
                    double z = interp.getZ() - RenderManager.renderPosZ;
                    GL11.glTranslated((double) x, (double) y, (double) z);
                    float bodyYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * mc.timer.renderPartialTicks;
                    GL11.glRotatef((float) (-bodyYawOffset), (float) 0.0f, (float) 1.0f, (float) 0.0f);
                    GL11.glTranslated((double) 0.0, (double) 0.0, (double) (player.isSneaking() ? -0.235 : 0.0));
                    float legHeight = player.isSneaking() ? 0.6f : 0.75f;
                    GL11.glPushMatrix();
                    GL11.glTranslated((double) -0.125, (double) legHeight, (double) 0.0);
                    if (modelRotations[3][0] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[3][0] * 57.295776f), (float) 1.0f, (float) 0.0f, (float) 0.0f);
                    }
                    if (modelRotations[3][1] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[3][1] * 57.295776f), (float) 0.0f, (float) 1.0f, (float) 0.0f);
                    }
                    if (modelRotations[3][2] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[3][2] * 57.295776f), (float) 0.0f, (float) 0.0f, (float) 1.0f);
                    }
                    GL11.glBegin((int) 3);
                    GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
                    GL11.glVertex3d((double) 0.0, (double) (-legHeight), (double) 0.0);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated((double) 0.125, (double) legHeight, (double) 0.0);
                    if (modelRotations[4][0] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[4][0] * 57.295776f), (float) 1.0f, (float) 0.0f, (float) 0.0f);
                    }
                    if (modelRotations[4][1] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[4][1] * 57.295776f), (float) 0.0f, (float) 1.0f, (float) 0.0f);
                    }
                    if (modelRotations[4][2] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[4][2] * 57.295776f), (float) 0.0f, (float) 0.0f, (float) 1.0f);
                    }
                    GL11.glBegin((int) 3);
                    GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
                    GL11.glVertex3d((double) 0.0, (double) (-legHeight), (double) 0.0);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glTranslated((double) 0.0, (double) 0.0, (double) (player.isSneaking() ? 0.25 : 0.0));
                    GL11.glPushMatrix();
                    GL11.glTranslated((double) 0.0, (double) (player.isSneaking() ? -0.05 : 0.0), (double) (player.isSneaking() ? -0.01725 : 0.0));
                    GL11.glPushMatrix();
                    GL11.glTranslated((double) -0.375, (double) ((double) legHeight + 0.55), (double) 0.0);
                    if (modelRotations[1][0] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[1][0] * 57.295776f), (float) 1.0f, (float) 0.0f, (float) 0.0f);
                    }
                    if (modelRotations[1][1] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[1][1] * 57.295776f), (float) 0.0f, (float) 1.0f, (float) 0.0f);
                    }
                    if (modelRotations[1][2] != 0.0f) {
                        GL11.glRotatef((float) ((-modelRotations[1][2]) * 57.295776f), (float) 0.0f, (float) 0.0f, (float) 1.0f);
                    }
                    GL11.glBegin((int) 3);
                    GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
                    GL11.glVertex3d((double) 0.0, (double) -0.5, (double) 0.0);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated((double) 0.375, (double) ((double) legHeight + 0.55), (double) 0.0);
                    if (modelRotations[2][0] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[2][0] * 57.295776f), (float) 1.0f, (float) 0.0f, (float) 0.0f);
                    }
                    if (modelRotations[2][1] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[2][1] * 57.295776f), (float) 0.0f, (float) 1.0f, (float) 0.0f);
                    }
                    if (modelRotations[2][2] != 0.0f) {
                        GL11.glRotatef((float) ((-modelRotations[2][2]) * 57.295776f), (float) 0.0f, (float) 0.0f, (float) 1.0f);
                    }
                    GL11.glBegin((int) 3);
                    GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
                    GL11.glVertex3d((double) 0.0, (double) -0.5, (double) 0.0);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glRotatef((float) (bodyYawOffset - player.rotationYawHead), (float) 0.0f, (float) 1.0f, (float) 0.0f);
                    GL11.glPushMatrix();
                    GL11.glTranslated((double) 0.0, (double) ((double) legHeight + 0.55), (double) 0.0);
                    if (modelRotations[0][0] != 0.0f) {
                        GL11.glRotatef((float) (modelRotations[0][0] * 57.295776f), (float) 1.0f, (float) 0.0f, (float) 0.0f);
                    }
                    GL11.glBegin((int) 3);
                    GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
                    GL11.glVertex3d((double) 0.0, (double) 0.3, (double) 0.0);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                    GL11.glRotatef((float) (player.isSneaking() ? 25.0f : 0.0f), (float) 1.0f, (float) 0.0f, (float) 0.0f);
                    GL11.glTranslated((double) 0.0, (double) (player.isSneaking() ? -0.16175 : 0.0), (double) (player.isSneaking() ? -0.48025 : 0.0));
                    GL11.glPushMatrix();
                    GL11.glTranslated((double) 0.0, (double) legHeight, (double) 0.0);
                    GL11.glBegin((int) 3);
                    GL11.glVertex3d((double) -0.125, (double) 0.0, (double) 0.0);
                    GL11.glVertex3d((double) 0.125, (double) 0.0, (double) 0.0);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated((double) 0.0, (double) legHeight, (double) 0.0);
                    GL11.glBegin((int) 3);
                    GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
                    GL11.glVertex3d((double) 0.0, (double) 0.55, (double) 0.0);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated((double) 0.0, (double) ((double) legHeight + 0.55), (double) 0.0);
                    GL11.glBegin((int) 3);
                    GL11.glVertex3d((double) -0.375, (double) 0.0, (double) 0.0);
                    GL11.glVertex3d((double) 0.375, (double) 0.0, (double) 0.0);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                }
        );
        RenderUtil.setupRender(false);
    }

    public static void updateModel(EntityPlayer player, ModelPlayer model) {
        modelRotations.put(player, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
    }
}


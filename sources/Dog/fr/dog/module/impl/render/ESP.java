package fr.dog.module.impl.render;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.render.Render2DEvent;
import fr.dog.event.impl.render.Render3DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.module.impl.combat.AntiBot;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.player.PlayerUtil;
import fr.dog.util.math.MathsUtil;
import fr.dog.util.render.ColorUtil;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.model.ModelUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import org.lwjglx.opengl.Display;
import org.lwjglx.util.glu.GLU;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings("unused")
public class ESP extends Module {
    private final FloatBuffer windowPosition = GLAllocation.createDirectFloatBuffer(4);
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelMatrix = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projectionMatrix = GLAllocation.createDirectFloatBuffer(16);
    private final float[] BUFFER = new float[3];
    private final Map<EntityPlayer, float[]> entityPositionMap = new HashMap<>();

    //Settings
    private final ModeProperty espMode = ModeProperty.newInstance("Mode", new String[]{"Outline", "Penis"}, "Outline");
    private final NumberProperty penisSize = NumberProperty.newInstance("Penis Size", 0f, 0.5f, 1f, 0.1f, () -> espMode.is("Penis"));

    public ESP() {
        super("ESP", ModuleCategory.RENDER);
        this.registerProperties(espMode, penisSize);
    }

    @Override
    protected void onDisable() {
        this.entityPositionMap.clear();
    }

    @SubscribeEvent
    private void onRender2D(Render2DEvent e) {
        glDisable(GL_TEXTURE_2D);
        GlStateManager.enableAlpha();

        for (final EntityPlayer player : entityPositionMap.keySet()) {
            if ((player.getDistanceToEntity(mc.thePlayer) < 1.0F && mc.gameSettings.thirdPersonView == 0) ||
                    !RenderUtil.isBBInFrustum(player.getEntityBoundingBox()))
                continue;

            final float[] positions = entityPositionMap.get(player);

            final float x = positions[0], y = positions[1],
                    x2 = positions[2], y2 = positions[3],
                    health = player.getHealth(), maxHealth = player.getMaxHealth(),
                    healthPercentage = health / maxHealth;

            if(!espMode.is("Outline"))
                return;

            glColor4ub((byte) 0, (byte) 0, (byte) 0, (byte) 0x96);
            glBegin(GL_QUADS);

            { // Outline
                glVertex2f(x, y);
                glVertex2f(x, y2);
                glVertex2f(x + 1.5F, y2);
                glVertex2f(x + 1.5F, y);

                glVertex2f(x2 - 1.5F, y);
                glVertex2f(x2 - 1.5F, y2);
                glVertex2f(x2, y2);
                glVertex2f(x2, y);

                glVertex2f(x + 1.5F, y);
                glVertex2f(x + 1.5F, y + 1.5F);
                glVertex2f(x2 - 1.5F, y + 1.5F);
                glVertex2f(x2 - 1.5F, y);

                glVertex2f(x + 1.5F, y2 - 1.5F);
                glVertex2f(x + 1.5F, y2);
                glVertex2f(x2 - 1.5F, y2);
                glVertex2f(x2 - 1.5F, y2 - 1.5F);
            }



            ColorUtil.setColor(PlayerUtil.getTeamColor(player));

            { // Main
                glVertex2f(x + 0.5F, y + 0.5F);
                glVertex2f(x + 0.5F, y2 - 0.5F);
                glVertex2f(x + 1, y2 - 0.5F);
                glVertex2f(x + 1, y + 0.5F);

                glVertex2f(x2 - 1, y + 0.5F);
                glVertex2f(x2 - 1, y2 - 0.5F);
                glVertex2f(x2 - 0.5F, y2 - 0.5F);
                glVertex2f(x2 - 0.5F, y + 0.5F);

                glVertex2f(x + 0.5F, y + 0.5F);
                glVertex2f(x + 0.5F, y + 1);
                glVertex2f(x2 - 0.5F, y + 1);
                glVertex2f(x2 - 0.5F, y + 0.5F);

                glVertex2f(x + 0.5F, y2 - 1);
                glVertex2f(x + 0.5F, y2 - 0.5F);
                glVertex2f(x2 - 0.5F, y2 - 0.5F);
                glVertex2f(x2 - 0.5F, y2 - 1);
            }

            glEnd();

        }

        GlStateManager.disableAlpha();
        glEnable(GL_TEXTURE_2D);
    }

    @SubscribeEvent
    private void onRender3D(Render3DEvent e) {
        this.entityPositionMap.clear();

        AntiBot antiBotModule = Dog.getInstance().getModuleManager().getModule(AntiBot.class);
        final float partialTicks = e.getPartialTicks();

        for (final EntityPlayer player : mc.theWorld.playerEntities) {
            if (!isValid(player)) {
                continue;
            }
            
            if (antiBotModule.isEnabled() && antiBotModule.isBot(player)) {
                continue;
            }

            switch (espMode.getValue()) {
                case "Outline" -> {
                    final double
                            posX = (MathsUtil.interpolate(player.prevPosX, player.posX, partialTicks) - RenderManager.viewerPosX),
                            posY = (MathsUtil.interpolate(player.prevPosY, player.posY, partialTicks) - RenderManager.viewerPosY),
                            posZ = (MathsUtil.interpolate(player.prevPosZ, player.posZ, partialTicks) - RenderManager.viewerPosZ);

                    final double halfWidth = player.width / 2.0D;

                    final AxisAlignedBB bb = new AxisAlignedBB(posX - halfWidth, posY, posZ - halfWidth,
                            posX + halfWidth, posY + player.height + (player.isSneaking() ? -0.2 : 0.1), posZ + halfWidth).expand(0.1, 0.1, 0.1);

                    final double[][] vectors = {
                            {bb.minX, bb.minY, bb.minZ},
                            {bb.minX, bb.maxY, bb.minZ},
                            {bb.minX, bb.maxY, bb.maxZ},
                            {bb.minX, bb.minY, bb.maxZ},
                            {bb.maxX, bb.minY, bb.minZ},
                            {bb.maxX, bb.maxY, bb.minZ},
                            {bb.maxX, bb.maxY, bb.maxZ},
                            {bb.maxX, bb.minY, bb.maxZ}
                    };

                    float[] projection;
                    final float[] position = new float[]{Float.MAX_VALUE, Float.MAX_VALUE, -1.0F, -1.0F};

                    for (final double[] vec : vectors) {
                        projection = project2D((float) vec[0], (float) vec[1], (float) vec[2]);
                        if (projection != null && projection[2] >= 0.0F && projection[2] < 1.0F) {
                            final float pX = projection[0];
                            final float pY = projection[1];
                            position[0] = Math.min(position[0], pX);
                            position[1] = Math.min(position[1], pY);
                            position[2] = Math.max(position[2], pX);
                            position[3] = Math.max(position[3], pY);
                        }
                    }

                    entityPositionMap.put(player, position);
                }

                case "Penis" -> {
                    if(player == mc.thePlayer)
                        continue;

                    Vec3 playerLook = player.getLook(mc.timer.renderPartialTicks);
                    Vec3 positionEyes = player.getPositionEyes(mc.timer.renderPartialTicks);

                    double pantsHeight = -0.75;

                    float yaw = player.rotationYaw;
                    float yawRad = (float) Math.toRadians(yaw);
                    float offsetX = (float) Math.cos(yawRad) * 0.1f;
                    float offsetZ = (float) Math.sin(yawRad) * 0.1f;

                    Vec3 leftBallPos = positionEyes.addVector(-offsetX, pantsHeight, -offsetZ);
                    Vec3 rightBallPos = positionEyes.addVector(offsetX, pantsHeight, offsetZ);

                    Color c = ColorUtil.interpolateColorC(new Color(0xF881FF), new Color(0, 0, 0), penisSize.getValue());

                    ModelUtil.draw3DSphere(leftBallPos, 0.1f, c);
                    ModelUtil.draw3DSphere(rightBallPos, 0.1f, c);

                    float extensionOffsetX = (float) Math.cos(yawRad + (float) Math.PI / 2) * (penisSize.getValue()/10);
                    float extensionOffsetZ = (float) Math.sin(yawRad + (float) Math.PI / 2) * (penisSize.getValue()/10);

                    Vec3 middlePos = positionEyes.addVector(0, pantsHeight, 0);

                    for (int i = 0; i < 10; i++) {
                        Vec3 spherePos = middlePos.addVector(i * extensionOffsetX, 0, i * extensionOffsetZ);

                        ModelUtil.draw3DSphere(spherePos, 0.1f, i == 9 ? new Color(255, 0, 145) : c);
                    }

                }
            }
        }
    }

    private float[] project2D(float x, float y, float z) {
        GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, modelMatrix);
        GL11.glGetFloatv(GL11.GL_PROJECTION_MATRIX, projectionMatrix);
        GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);

        if (GLU.gluProject(x, y, z, modelMatrix, projectionMatrix, viewport, windowPosition)) {
            BUFFER[0] = windowPosition.get(0) / 2;
            BUFFER[1] = (Display.getHeight() - windowPosition.get(1)) / 2;
            BUFFER[2] = windowPosition.get(2);
            return BUFFER;
        }

        return null;
    }

    private boolean isValid(final Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (!entity.isEntityAlive()) {
                return false;
            }

            if (entity instanceof EntityPlayerSP && mc.gameSettings.thirdPersonView == 0) {
                return false;
            }

            return RenderUtil.isBBInFrustum(entity.getEntityBoundingBox());
        }

        return false;
    }
}

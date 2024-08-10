package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.render.ColorUtil;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Vector4d;
import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ModuleInfo(
        name = "ESP",
        category = Category.RENDER
)
public class ESP extends Module {


    // ESP Stuff
    public final ModeValue<String> boxMode = new ModeValue<>("Box", new String[] {"Box", "Corners", "None"});

    // Render Vars
    public final BooleanValue healthBar = new BooleanValue("Health bar",  false);
    public final BooleanValue you = new BooleanValue("Render Me", false);
    public final BooleanValue players = new BooleanValue("Render Players", true);
    public final BooleanValue invisibles = new BooleanValue("Render Invisibles", false);
    public final BooleanValue mobs = new BooleanValue("Render Mobs", true);
    public final BooleanValue animals = new BooleanValue("Render Animals", false);
    public final BooleanValue items = new BooleanValue("Render Items", false);

    // Skeleton
    private final BooleanValue skeleton = new BooleanValue("Skeleton", false);
    private final NumberValue<Float> width = new NumberValue<>("Skeleton Width",  1F, 0.5F, 10F, 0.1F);

    // Color
    private final BooleanValue shadow = new BooleanValue("Shadow", true);
    public final ModeValue<String> colormodes = new ModeValue<>("Color", new String[] { "Client Theme", "Rainbow", "Custom" });
    private final NumberValue<Integer> redValue = new NumberValue<>("Red", 0, 0, 255, 1);
    private final NumberValue<Integer> greenValue = new NumberValue<>("Green", 255, 0, 255, 1);
    private final NumberValue<Integer> blueValue = new NumberValue<>("Blue", 255, 0, 255, 1);
    private final NumberValue<Integer> alphaValue = new NumberValue<>("Alpha", 200, 0, 255, 1);

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});

    // Variables

    public final java.util.List<Entity> collectedEntities;
    private final IntBuffer viewport;
    private final FloatBuffer modelview;
    private final FloatBuffer projection;
    private final FloatBuffer vector;
    private int color;
    private final int backgroundColor;
    private int black;
    private static final Map<EntityPlayer, float[][]> entities = new HashMap<>();

    public ESP() {
        addSettings(
                boxMode,healthBar,
                you,players,invisibles,mobs,animals,items,
                skeleton, width,
                shadow, colormodes, redValue, greenValue, blueValue, alphaValue, displayMode
        );
        collectedEntities = new ArrayList<>();
        viewport = GLAllocation.createDirectIntBuffer(16);
        modelview = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
        vector = GLAllocation.createDirectFloatBuffer(4);
        backgroundColor = (new Color(0, 0, 0, 120)).getRGB();
    }


    @Listen
    public void onRender (RenderEvent event) {
        Color ct = ColorUtil.getColor();
        if (colormodes.getValue().equals("Client Theme")){
            color = ct.getRGB();
        } else {
            color = (!colormodes.getValue().equals("Rainbow")) ? new Color(redValue.getValue(), greenValue.getValue(), blueValue.getValue(), alphaValue.getValue()).getRGB() : ColorUtil.rainbow(-100, 1.0f, 0.47f).getRGB();
        }

        if (!shadow.getValue()) {
            if (colormodes.getValue().equals("Client Theme")){
                black = ct.getRGB();
            } else {
                black = (!colormodes.getValue().equals("Rainbow")) ? new Color(redValue.getValue(), greenValue.getValue(), blueValue.getValue(), alphaValue.getValue()).getRGB() : ColorUtil.rainbow(-100, 1.0f, 0.47f).getRGB();
            }
        } else {
            black = Color.BLACK.getRGB();
        }

        if (event.getState() == RenderEvent.State.RENDER_2D) {
            Render2D(event);
        }
        if (event.getState() == RenderEvent.State.RENDER_3D) {
            Render3D(event);
        }
    }


    public void Render2D (RenderEvent event) {
        if (event.getState() == RenderEvent.State.RENDER_2D) {
            GL11.glPushMatrix();
            collectEntities();
            ScaledResolution scaledResolution = new ScaledResolution(mc.getMinecraft());
            int scaleFactor = scaledResolution.getScaleFactor();
            double scaling = (double) scaleFactor / Math.pow(scaleFactor, 2.0D);
            GL11.glScaled(scaling, scaling, scaling);
            RenderManager renderMng = mc.getRenderManager();
            boolean health = healthBar.getValue();
            int i = 0;

            for (int collectedEntitiesSize = collectedEntities.size(); i < collectedEntitiesSize; ++i) {
                Entity entity = collectedEntities.get(i);
                if (isValid(entity) && RenderUtil.isInViewFrustrum(entity.getEntityBoundingBox())) {
                    double x = interpolateDB(entity.posX, entity.lastTickPosX, event.partialTicks);
                    double y = interpolateDB(entity.posY, entity.lastTickPosY, event.partialTicks);
                    double z = interpolateDB(entity.posZ, entity.lastTickPosZ, event.partialTicks);
                    double width = (double) entity.width / 1.5D;
                    double height = (double) entity.height + (entity.isSneaking() ? -0.3D : 0.2D);
                    AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                    List<javax.vecmath.Vector3d> vectors = Arrays.asList(new javax.vecmath.Vector3d(aabb.minX, aabb.minY, aabb.minZ), new javax.vecmath.Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new javax.vecmath.Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new javax.vecmath.Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new javax.vecmath.Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new javax.vecmath.Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new javax.vecmath.Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new javax.vecmath.Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ));
                    mc.getEntityRenderer().setupCameraTransform(event.partialTicks, 0);
                    Vector4d position = null;
                    for (javax.vecmath.Vector3d vector3d : vectors) {
                        vector3d = project2D(scaleFactor, vector3d.x - renderMng.viewerPosX, vector3d.y - renderMng.viewerPosY, vector3d.z - renderMng.viewerPosZ);
                        if (vector3d != null && vector3d.z >= 0.0D && vector3d.z < 1.0D) {
                            if (position == null) {
                                position = new Vector4d(vector3d.x, vector3d.y, vector3d.z, 0.0D);
                            }

                            position.x = Math.min(vector3d.x, position.x);
                            position.y = Math.min(vector3d.y, position.y);
                            position.z = Math.max(vector3d.x, position.z);
                            position.w = Math.max(vector3d.y, position.w);
                        }
                    }

                    if (position != null) {
                        mc.getEntityRenderer().setupOverlayRendering();
                        double posX = position.x;
                        double posY = position.y;
                        double endPosX = position.z;
                        double endPosY = position.w;
                        if (boxMode.getValue().equals("Box")) {
                            Gui.drawRect(posX - 1.0D, posY, posX + 0.5D, endPosY + 0.5D, black);
                            Gui.drawRect(posX - 1.0D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 0.5D, black);
                            Gui.drawRect(endPosX - 0.5D - 0.5D, posY, endPosX + 0.5D, endPosY + 0.5D, black);
                            Gui.drawRect(posX - 1.0D, endPosY - 0.5D - 0.5D, endPosX + 0.5D, endPosY + 0.5D, black);
                            Gui.drawRect(posX - 0.5D, posY, posX + 0.5D - 0.5D, endPosY, color);
                            Gui.drawRect(posX, endPosY - 0.5D, endPosX, endPosY, color);
                            Gui.drawRect(posX - 0.5D, posY, endPosX, posY + 0.5D, color);
                            Gui.drawRect(endPosX - 0.5D, posY, endPosX, endPosY, color);
                        } else if (boxMode.getValue().equals("Corners")) {
                            Gui.drawRect(posX + 0.5D, posY, posX - 1.0D, posY + (endPosY - posY) / 4.0D + 0.5D, black);
                            Gui.drawRect(posX - 1.0D, endPosY, posX + 0.5D, endPosY - (endPosY - posY) / 4.0D - 0.5D, black);
                            Gui.drawRect(posX - 1.0D, posY - 0.5D, posX + (endPosX - posX) / 3.0D + 0.5D, posY + 1.0D, black);
                            Gui.drawRect(endPosX - (endPosX - posX) / 3.0D - 0.5D, posY - 0.5D, endPosX, posY + 1.0D, black);
                            Gui.drawRect(endPosX - 1.0D, posY, endPosX + 0.5D, posY + (endPosY - posY) / 4.0D + 0.5D, black);
                            Gui.drawRect(endPosX - 1.0D, endPosY, endPosX + 0.5D, endPosY - (endPosY - posY) / 4.0D - 0.5D, black);
                            Gui.drawRect(posX - 1.0D, endPosY - 1.0D, posX + (endPosX - posX) / 3.0D + 0.5D, endPosY + 0.5D, black);
                            Gui.drawRect(endPosX - (endPosX - posX) / 3.0D - 0.5D, endPosY - 1.0D, endPosX + 0.5D, endPosY + 0.5D, black);
                            Gui.drawRect(posX, posY, posX - 0.5D, posY + (endPosY - posY) / 4.0D, color);
                            Gui.drawRect(posX, endPosY, posX - 0.5D, endPosY - (endPosY - posY) / 4.0D, color);
                            Gui.drawRect(posX - 0.5D, posY, posX + (endPosX - posX) / 3.0D, posY + 0.5D, color);
                            Gui.drawRect(endPosX - (endPosX - posX) / 3.0D, posY, endPosX, posY + 0.5D, color);
                            Gui.drawRect(endPosX - 0.5D, posY, endPosX, posY + (endPosY - posY) / 4.0D, color);
                            Gui.drawRect(endPosX - 0.5D, endPosY, endPosX, endPosY - (endPosY - posY) / 4.0D, color);
                            Gui.drawRect(posX, endPosY - 0.5D, posX + (endPosX - posX) / 3.0D, endPosY, color);
                            Gui.drawRect(endPosX - (endPosX - posX) / 3.0D, endPosY - 0.5D, endPosX - 0.5D, endPosY, color);
                        }

                        boolean living = entity instanceof EntityLivingBase;
                        EntityLivingBase entityLivingBase;
                        float armorValue;
                        float itemDurability;
                        double durabilityWidth;
                        double textWidth;
                        float tagY;
                        if (living) {
                            entityLivingBase = (EntityLivingBase) entity;
                            if (health) {
                                armorValue = entityLivingBase.getHealth();
                                itemDurability = entityLivingBase.getMaxHealth();
                                if (armorValue > itemDurability) {
                                    armorValue = itemDurability;
                                }

                                durabilityWidth = armorValue / itemDurability;
                                textWidth = (endPosY - posY) * durabilityWidth;
                                Gui.drawRect(posX - 3.5D, posY - 0.5D, posX - 1.5D, endPosY + 0.5D, backgroundColor);
                                if (armorValue > 0.0F) {
                                    int healthColor = ColorUtil.getHealthColor(armorValue, itemDurability).getRGB();
                                    Gui.drawRect(posX - 3.0D, endPosY, posX - 2.0D, endPosY - textWidth, healthColor);
                                    tagY = entityLivingBase.getAbsorptionAmount();
                                    if (tagY > 0.0F) {
                                        Gui.drawRect(posX - 3.0D, endPosY, posX - 2.0D, endPosY - (endPosY - posY) / 6.0D * (double) tagY / 2.0D, (new Color(Potion.absorption.getLiquidColor())).getRGB());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            GL11.glPopMatrix();
            GlStateManager.enableBlend();
            mc.getEntityRenderer().setupOverlayRendering();
        }
    }

    private void collectEntities() {
        collectedEntities.clear();
        List<Entity> playerEntities = mc.theWorld.loadedEntityList;
        int i = 0;

        for (Entity entity : playerEntities) {
            if (isValid(entity)) {
                collectedEntities.add(entity);
            }
        }
    }

    private javax.vecmath.Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat(2982, modelview);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        return GLU.gluProject((float)x, (float)y, (float)z, modelview, projection, viewport, vector) ? new javax.vecmath.Vector3d((vector.get(0) / (float)scaleFactor), (((float) Display.getHeight() - vector.get(1)) / (float)scaleFactor), vector.get(2)) : null;
    }

    public static void addEntity(EntityPlayer e, ModelPlayer model) {
        entities.put(e, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
    }

    private boolean isValid(Entity entity) {
        if (entity != mc.thePlayer || you.getValue() && mc.gameSettings.thirdPersonView != 0) {
            if (entity.isDead) {
                return false;
            } else if (!invisibles.getValue() && entity.isInvisible()) {
                return false;
            } else if (items.getValue() && entity instanceof EntityItem && mc.thePlayer.getDistanceToEntity(entity) < 10.0F) {
                return true;
            } else if (animals.getValue() && entity instanceof EntityAnimal) {
                return true;
            } else if (players.getValue() && entity instanceof EntityPlayer) {
                return true;
            } else {
                return mobs.getValue() && (entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityDragon || entity instanceof EntityGolem);
            }
        } else {
            return false;
        }
    }

    public void Render3D(RenderEvent e) {
        if (e.getState() == RenderEvent.State.RENDER_3D) {
            if (skeleton.getValue()) {
                startEnd(true);
                GL11.glEnable(GL11.GL_COLOR_MATERIAL);
                GL11.glDisable(2848);
                mc.theWorld.playerEntities.forEach(player -> drawSkeleton(e, player));
                GlStateManager.color(1, 1, 1, 1);
                startEnd(false);
            }
        }
    }

    private void drawSkeleton(RenderEvent event, EntityPlayer e) {
        if (event.getState() == RenderEvent.State.RENDER_3D) {
            final Color color = new Color(e.getDisplayName().equals(mc.thePlayer.getDisplayName()) ? 0xFF99ff99 : new Color(255, 255, 255).getRGB());
            if (!e.isInvisible()) {
                float[][] entPos = entities.get(e);
                if (entPos != null && e.getEntityId() != -1488 && RenderUtil.isInViewFrustrum(e.getEntityBoundingBox()) && !e.isDead && e != mc.thePlayer && !e.isPlayerSleeping() && isValid(e)) {
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_LINE_SMOOTH);
                    GL11.glLineWidth(width.getValue());
                    GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1);
                    Vec3 vec = getVec3(event, e);
                    double x = vec.xCoord - mc.getRenderManager().renderPosX;
                    double y = vec.yCoord - mc.getRenderManager().renderPosY;
                    double z = vec.zCoord - mc.getRenderManager().renderPosZ;
                    GL11.glTranslated(x, y, z);
                    float xOff = e.prevRenderYawOffset + (e.renderYawOffset - e.prevRenderYawOffset) * event.getPartialTicks();
                    GL11.glRotatef(-xOff, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslated(0.0D, 0.0D, e.isSneaking() ? -0.235D : 0.0D);
                    float yOff = e.isSneaking() ? 0.6F : 0.75F;
                    GL11.glPushMatrix();
                    GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1);
                    GL11.glTranslated(-0.125D, yOff, 0.0D);
                    if (entPos[3][0] != 0.0F) {
                        GL11.glRotatef(entPos[3][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                    }

                    if (entPos[3][1] != 0.0F) {
                        GL11.glRotatef(entPos[3][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
                    }

                    if (entPos[3][2] != 0.0F) {
                        GL11.glRotatef(entPos[3][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
                    }

                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, (-yOff), 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1);
                    GL11.glTranslated(0.125D, yOff, 0.0D);
                    if (entPos[4][0] != 0.0F) {
                        GL11.glRotatef(entPos[4][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                    }

                    if (entPos[4][1] != 0.0F) {
                        GL11.glRotatef(entPos[4][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
                    }

                    if (entPos[4][2] != 0.0F) {
                        GL11.glRotatef(entPos[4][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
                    }

                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, (-yOff), 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glTranslated(0.0D, 0.0D, e.isSneaking() ? 0.25D : 0.0D);
                    GL11.glPushMatrix();
                    GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1);
                    GL11.glTranslated(0.0D, e.isSneaking() ? -0.05D : 0.0D, e.isSneaking() ? -0.01725D : 0.0D);
                    GL11.glPushMatrix();
                    GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1);
                    GL11.glTranslated(-0.375D, yOff + 0.55D, 0.0D);
                    if (entPos[1][0] != 0.0F) {
                        GL11.glRotatef(entPos[1][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                    }

                    if (entPos[1][1] != 0.0F) {
                        GL11.glRotatef(entPos[1][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
                    }

                    if (entPos[1][2] != 0.0F) {
                        GL11.glRotatef(-entPos[1][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
                    }

                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, -0.5D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.375D, yOff + 0.55D, 0.0D);
                    if (entPos[2][0] != 0.0F) {
                        GL11.glRotatef(entPos[2][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                    }

                    if (entPos[2][1] != 0.0F) {
                        GL11.glRotatef(entPos[2][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
                    }

                    if (entPos[2][2] != 0.0F) {
                        GL11.glRotatef(-entPos[2][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
                    }

                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, -0.5D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glRotatef(xOff - e.rotationYawHead, 0.0F, 1.0F, 0.0F);
                    GL11.glPushMatrix();
                    GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1);
                    GL11.glTranslated(0.0D, yOff + 0.55D, 0.0D);
                    if (entPos[0][0] != 0.0F) {
                        GL11.glRotatef(entPos[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                    }

                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, 0.3D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                    GL11.glRotatef(e.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glTranslated(0.0D, e.isSneaking() ? -0.16175D : 0.0D, e.isSneaking() ? -0.48025D : 0.0D);
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.0D, yOff, 0.0D);
                    GL11.glBegin(3);
                    GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.125D, 0.0D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1);
                    GL11.glTranslated(0.0D, yOff, 0.0D);
                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, 0.55D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.0D, yOff + 0.55D, 0.0D);
                    GL11.glBegin(3);
                    GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.375D, 0.0D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                }
            }
        }
    }

    private Vec3 getVec3(RenderEvent event, EntityPlayer var0) {
        if (event.getState() == RenderEvent.State.RENDER_3D) {
            float pt = event.getPartialTicks();
            double x = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * pt;
            double y = var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * pt;
            double z = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * pt;
            return new Vec3(x, y, z);
        }
        return null;
    }

    private void startEnd(boolean revert) {
        if (revert) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GL11.glEnable(2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GL11.glHint(3154, 4354);
        } else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable(2848);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        GlStateManager.depthMask(!revert);
    }

    public static double interpolateDB(double now, double old, double scale) {

        return old + (now - old) * scale;
    }

    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Simple":
                return boxMode.getValue().toString();
        }
        return null;
    }

}

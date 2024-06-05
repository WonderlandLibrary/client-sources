package client.metaware.impl.module.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.render.Render2DEvent;
import client.metaware.impl.event.impl.render.RenderNametagEvent;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.util.other.PlayerUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

@ModuleInfo(name = "2DESP", renderName = "2D ESP", category = Category.VISUALS)
public final class ESP2D extends Module {

    public final Property<Boolean> outline = new Property<Boolean>("Outline", true);
    public final EnumProperty<BoxMode> boxMode = new EnumProperty("Mode", BoxMode.Box);
    public final Property<Boolean> tag = new Property<Boolean>("Nametag", true);
    public final Property<Boolean> healthBar = new Property<Boolean>("Health Bar", true);
    public final Property<Boolean> armorBar = new Property<Boolean>("Armor Bar", true);
    public final Property<Boolean> players = new Property<>("Players", true);
    public final Property<Boolean> yourSelf = new Property<>("Yourself", false, players::getValue);
    public final Property<Boolean> monsters = new Property<>("Mobs", false);
    public final Property<Boolean> animals = new Property<>("Animals", false);
    public final Property<Boolean> invisibles = new Property<>("Invisibles", false);


    public final List collectedEntities = new ArrayList<>();
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
    private int color = Color.WHITE.getRGB();
    private final int backgroundColor = new Color(0, 0, 0, 120).getRGB();
    private final int black = Color.BLACK.getRGB();

    @EventHandler
    private final Listener<RenderNametagEvent> eventListener = event -> {
      event.setCancelled(true);
    };

    @EventHandler
    private final Listener<Render2DEvent> render2DEventListener = event -> {
        GL11.glPushMatrix();
        collectEntities();
        float partialTicks = event.getPartialTicks();
        ScaledResolution scaledResolution = event.getScaledResolution();
        int scaleFactor = scaledResolution.getScaleFactor();
        double scaling = (double)scaleFactor / Math.pow((double)scaleFactor, 2.0D);
        GL11.glScaled(scaling, scaling, scaling);
        int black = new Color(0, 0, 0, 120).getRGB();
        int background = backgroundColor;
        float scale = 0.65F;
        float upscale = 1.0f / scale;
        RenderManager renderMng = mc.getRenderManager();
        EntityRenderer entityRenderer = mc.entityRenderer;
        boolean tag = this.tag.getValue();
        boolean outline = this.outline.getValue();
        boolean health = healthBar.getValue();
        boolean armor = armorBar.getValue();
        List collectedEntities = this.collectedEntities;
        int i = 0;
        int healthcolor = 0;
        for(int collectedEntitiesSize = collectedEntities.size(); i < collectedEntitiesSize; ++i) {
            final Entity entity = (Entity)collectedEntities.get(i);
            if (isValid(entity) && RenderUtil.isInViewFrustrum(entity)) {
                int healthColor = RenderUtil.drawHealth((EntityLivingBase) entity);
                int color1 = new Color(healthColor).getRed();
                int color2 = new Color(healthColor).getGreen();
                int color3 = new Color(healthColor).getBlue();
                int color = new Color(color1, color2, color3, 135).getRGB();
                double x = RenderUtil.interpolateScale(entity.posX, entity.lastTickPosX, (float)partialTicks);
                double y = RenderUtil.interpolateScale(entity.posY, entity.lastTickPosY, (float)partialTicks);
                double z = RenderUtil.interpolateScale(entity.posZ, entity.lastTickPosZ, (float)partialTicks);
                double width = (double)entity.width / 1.5D;
                double height = (double)entity.height + (entity.isSneaking() ? -0.3D : 0.2D);
                AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                List vectors = Arrays.asList(new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ));
                entityRenderer.setupCameraTransform(partialTicks, 0);
                Vector4d position = null;
                Iterator var38 = vectors.iterator();

                final CustomFontRenderer fr = Metaware.INSTANCE.getFontManager().currentFont().size(25);

                while(var38.hasNext()) {
                    Vector3d vector = (Vector3d)var38.next();
                    vector = project2D(scaleFactor, vector.x - renderMng.viewerPosX, vector.y - renderMng.viewerPosY, vector.z - renderMng.viewerPosZ);
                    if (vector != null && vector.z >= 0.0D && vector.z < 1.0D) {
                        if (position == null) {
                            position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
                        }

                        position.x = Math.min(vector.x, position.x);
                        position.y = Math.min(vector.y, position.y);
                        position.z = Math.max(vector.x, position.z);
                        position.w = Math.max(vector.y, position.w);
                    }
                }

                if (position != null) {
                    entityRenderer.setupOverlayRendering();
                    double posX = position.x;
                    double posY = position.y;
                    double endPosX = position.z;
                    double endPosY = position.w;
                    if (outline) {
                        if (boxMode.getValue().equals(BoxMode.Box)) {
                            Gui.drawRect(posX - 1.0D, posY, posX + 0.5D, endPosY + 0.5D, black);
                            Gui.drawRect(posX - 1.0D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 0.5D, black);
                            Gui.drawRect(endPosX - 0.5D - 0.5D, posY, endPosX + 0.5D, endPosY + 0.5D, black);
                            Gui.drawRect(posX - 1.0D, endPosY - 0.5D - 0.5D, endPosX + 0.5D, endPosY + 0.5D, black);
                            Gui.drawRect(posX - 0.5D, posY, posX + 0.5D - 0.5D, endPosY, color);
                            Gui.drawRect(posX, endPosY - 0.5D, endPosX, endPosY, color);
                            Gui.drawRect(posX - 0.5D, posY, endPosX, posY + 0.5D, color);
                            Gui.drawRect(endPosX - 0.5D, posY, endPosX, endPosY, color);
                        } else {
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
                    }

                    boolean living = entity instanceof EntityLivingBase;
                    EntityLivingBase entityLivingBase;
                    float armorValue;
                    float itemDurability;
                    double durabilityWidth;
                    double textWidth;
                    float tagY;
                    if (living) {
                        entityLivingBase = (EntityLivingBase)entity;
                        if (health) {
                            armorValue = entityLivingBase.getHealth();
                            itemDurability = entityLivingBase.getMaxHealth();
                            if (armorValue > itemDurability) {
                                armorValue = itemDurability;
                            }

                            durabilityWidth = (double)(armorValue / itemDurability);
                            textWidth = (endPosY - posY) * durabilityWidth;
                            Gui.drawRect(posX - 3.5D, posY - 0.5D, posX - 1.5D, endPosY + 0.5D, background);
                            if (armorValue > 0.0F) {
                                healthcolor = color;
                                Gui.drawRect((float)posX - 3.0f, (float)endPosY, (float)posX - 2.0f, (float)endPosY - (float)textWidth, healthColor);
                                tagY = entityLivingBase.getAbsorptionAmount();
                                if (tagY > 0.0F) {
                                    Gui.drawRect(posX - 5.0D, endPosY, posX - 4.0D, endPosY - (endPosY - posY) / 6.0D * (double)tagY / 2.0D, (new Color(Potion.absorption.getLiquidColor())).getRGB());
                                }
                            }

                        }
                    }

                    if (tag && entity instanceof EntityPlayer) {
                        float scaledHeight = 10.0F;
                        final EntityPlayer ent = (EntityPlayer) entity;
                        String name = ent.getGameProfile().getName();

                        durabilityWidth = (endPosX - posX) / 2.0D;
                        textWidth = (double)((float)fr.getWidth(name) * scale);
                        float tagX = (float)((posX + durabilityWidth - textWidth / 2.0D) * (double)upscale);
                        tagY = (float)(posY * (double)upscale) - scaledHeight;
                        GL11.glPushMatrix();
                        GL11.glScalef(scale, scale, scale);

                        final int bg = new Color(1, 1, 1).getRGB();
                        fr.drawStringWithShadow(name, tagX + 1, tagY - 5, bg);
                        fr.drawStringWithShadow(name, tagX - 1, tagY - 5, bg);
                        fr.drawStringWithShadow(name, tagX, tagY - 5 - 1, bg);
                        fr.drawStringWithShadow(name, tagX, tagY - 5 + 1, bg);
                        fr.drawStringWithShadow(name, tagX, tagY - 5, -1);
                        final EntityLivingBase entityLiving = (EntityLivingBase) entity;
                        //fr.drawStringWithShadow(String.format("%.1f", entityLiving.getHealth() / 2.0f), tagX - 50, tagY, -1);
                        GL11.glPopMatrix();
                    }
                    if (armor) {
                        if (living) {
                            entityLivingBase = (EntityLivingBase)entity;
                            armorValue = (float)entityLivingBase.getTotalArmorValue();
                            double armorWidth = (endPosX - posX) * (double)armorValue / 20.0D;
                            Gui.drawRect(posX - 0.5D, endPosY + 1.0, posX - 0.5D + endPosX - posX + 1.0D, endPosY + 3.5, background);
                            RenderUtil.drawBorderedRect(posX - 0.5D, endPosY + 4.3, endPosX - posX + 1.0D, 1.5, 0.5, new Color(28, 28, 28, 255).getRGB(), -1);
                            if (armorValue > 0.0F) {
                                Gui.drawRect(posX, endPosY + 2.0D, posX + armorWidth, endPosY + 3.0D, 16777215);
                            }
                        }
                    }
                }
            }
        }

        GL11.glPopMatrix();
        GlStateManager.enableBlend();
        entityRenderer.setupOverlayRendering();
    };


    private boolean isFriendly(EntityPlayer player) {
        return PlayerUtil.isOnSameTeam(player);
    }

    private void collectEntities() {
        collectedEntities.clear();
        List playerEntities = mc.theWorld.loadedEntityList;
        int i = 0;

        for(int playerEntitiesSize = playerEntities.size(); i < playerEntitiesSize; ++i) {
            Entity entity = (Entity)playerEntities.get(i);
            if (isValid(entity)) {
                collectedEntities.add(entity);
            }
        }

    }

    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat(2982, modelview);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        return GLU.gluProject((float)x, (float)y, (float)z, modelview, projection, viewport, vector) ? new Vector3d((double)(vector.get(0) / (float)scaleFactor), (double)(((float)Display.getHeight() - vector.get(1)) / (float)scaleFactor), (double)vector.get(2)) : null;
    }

    private boolean isValid(Entity entity) {
        if (entity != mc.thePlayer || yourSelf.getValue() && mc.gameSettings.thirdPersonView != 0) {
            if (entity.isDead) {
                return false;
            } else if (!invisibles.getValue() && entity.isInvisible()) {
                return false;
            } else if (animals.getValue() && entity instanceof EntityAnimal) {
                return true;
            } else if (players.getValue() && entity instanceof EntityPlayer) {
                return true;
            } else {
                return monsters.getValue() && (entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityDragon || entity instanceof EntityGolem);
            }
        } else {
            return false;
        }
    }

    public enum BoxMode {
        Box, Corner;
    }
}
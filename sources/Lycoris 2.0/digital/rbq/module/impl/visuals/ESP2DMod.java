/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package digital.rbq.module.impl.visuals;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
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
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import digital.rbq.annotations.Label;
import digital.rbq.core.Autumn;
import digital.rbq.events.render.RenderGuiEvent;
import digital.rbq.events.render.RenderNametagEvent;
import digital.rbq.friend.FriendManager;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.visuals.StreamerMod;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.module.option.impl.EnumOption;
import digital.rbq.utils.ColorUtils;
import digital.rbq.utils.PlayerUtils;
import digital.rbq.utils.render.RenderUtils;

@Label(value="2DESP")
@Category(value=ModuleCategory.VISUALS)
@Aliases(value={"2desp", "esp2d", "esp"})
public final class ESP2DMod
extends Module {
    public final BoolOption outline = new BoolOption("Outline", true);
    public final EnumOption<BoxMode> boxMode = new EnumOption<BoxMode>("Mode", BoxMode.BOX, this.outline::getValue);
    public final BoolOption tag = new BoolOption("Tag", true);
    public final BoolOption healthBar = new BoolOption("Health bar", true);
    public final BoolOption armorBar = new BoolOption("Armor bar", true);
    public final BoolOption localPlayer = new BoolOption("Local Player", true);
    public final BoolOption players = new BoolOption("Players", true);
    public final BoolOption invisibles = new BoolOption("Invisibles", false);
    public final BoolOption mobs = new BoolOption("Mobs", true);
    public final BoolOption animals = new BoolOption("Animals", false);
    public final BoolOption chests = new BoolOption("Chests", true);
    public final BoolOption droppedItems = new BoolOption("Dropped Items", false);
    public final List<Entity> collectedEntities = new ArrayList<Entity>();
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
    private final int color = Color.WHITE.getRGB();
    private final int backgroundColor = new Color(0, 0, 0, 120).getRGB();
    private final int black = Color.BLACK.getRGB();
    private StreamerMod streamerMode = null;

    @Override
    public void onEnabled() {
        if (this.streamerMode == null) {
            this.streamerMode = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(StreamerMod.class);
        }
    }

    public ESP2DMod() {
        this.addOptions(this.outline, this.boxMode, this.tag, this.healthBar, this.armorBar, this.localPlayer, this.players, this.invisibles, this.mobs, this.animals, this.chests, this.droppedItems);
    }

    @Listener(value=RenderNametagEvent.class)
    public void onEvent(RenderNametagEvent event) {
        if (this.isValid(event.getEntity()) && this.tag.getValue().booleanValue()) {
            event.setCancelled();
        }
    }

    @Listener(value=RenderGuiEvent.class)
    public void onRender(RenderGuiEvent event) {
        GL11.glPushMatrix();
        this.collectEntities();
        float partialTicks = event.getPartialTicks();
        ScaledResolution scaledResolution = event.getScaledResolution();
        int scaleFactor = scaledResolution.getScaleFactor();
        double scaling = (double)scaleFactor / Math.pow(scaleFactor, 2.0);
        GL11.glScaled((double)scaling, (double)scaling, (double)scaling);
        int black = this.black;
        int color = this.color;
        int background = this.backgroundColor;
        float scale = 0.65f;
        float upscale = 1.0f / scale;
        FontRenderer fr = ESP2DMod.mc.fontRendererObj;
        RenderManager renderMng = mc.getRenderManager();
        EntityRenderer entityRenderer = ESP2DMod.mc.entityRenderer;
        boolean tag = this.tag.getValue();
        boolean outline = this.outline.getValue();
        boolean health = this.healthBar.getValue();
        boolean armor = this.armorBar.getValue();
        BoxMode mode = (BoxMode)((Object)this.boxMode.getValue());
        StreamerMod streamerMode = this.streamerMode;
        List<Entity> collectedEntities = this.collectedEntities;
        int collectedEntitiesSize = collectedEntities.size();
        for (int i = 0; i < collectedEntitiesSize; ++i) {
            ItemStack itemStack;
            EntityLivingBase entityLivingBase;
            boolean living;
            Entity entity = collectedEntities.get(i);
            if (!this.isValid(entity) || !RenderUtils.isInViewFrustrum(entity)) continue;
            double x = RenderUtils.interpolate(entity.posX, entity.lastTickPosX, partialTicks);
            double y = RenderUtils.interpolate(entity.posY, entity.lastTickPosY, partialTicks);
            double z = RenderUtils.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks);
            double width = (double)entity.width / 1.5;
            double height = (double)entity.height + (entity.isSneaking() ? -0.3 : 0.2);
            AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
            List<Vector3d> vectors = Arrays.asList(new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ));
            entityRenderer.setupCameraTransform(partialTicks, 0);
            Vector4d position = null;
            for (Vector3d vector : vectors) {
                vector = this.project2D(scaleFactor, vector.x - renderMng.viewerPosX, vector.y - renderMng.viewerPosY, vector.z - renderMng.viewerPosZ);
                if (vector == null || !(vector.z >= 0.0) || !(vector.z < 1.0)) continue;
                if (position == null) {
                    position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                }
                position.x = Math.min(vector.x, position.x);
                position.y = Math.min(vector.y, position.y);
                position.z = Math.max(vector.x, position.z);
                position.w = Math.max(vector.y, position.w);
            }
            if (position == null) continue;
            entityRenderer.setupOverlayRendering(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            double posX = position.x;
            double posY = position.y;
            double endPosX = position.z;
            double endPosY = position.w;
            if (outline) {
                if (mode == BoxMode.BOX) {
                    Gui.drawRect(posX - 1.0, posY, posX + 0.5, endPosY + 0.5, black);
                    Gui.drawRect(posX - 1.0, posY - 0.5, endPosX + 0.5, posY + 0.5 + 0.5, black);
                    Gui.drawRect(endPosX - 0.5 - 0.5, posY, endPosX + 0.5, endPosY + 0.5, black);
                    Gui.drawRect(posX - 1.0, endPosY - 0.5 - 0.5, endPosX + 0.5, endPosY + 0.5, black);
                    Gui.drawRect(posX - 0.5, posY, posX + 0.5 - 0.5, endPosY, color);
                    Gui.drawRect(posX, endPosY - 0.5, endPosX, endPosY, color);
                    Gui.drawRect(posX - 0.5, posY, endPosX, posY + 0.5, color);
                    Gui.drawRect(endPosX - 0.5, posY, endPosX, endPosY, color);
                } else {
                    Gui.drawRect(posX + 0.5, posY, posX - 1.0, posY + (endPosY - posY) / 4.0 + 0.5, black);
                    Gui.drawRect(posX - 1.0, endPosY, posX + 0.5, endPosY - (endPosY - posY) / 4.0 - 0.5, black);
                    Gui.drawRect(posX - 1.0, posY - 0.5, posX + (endPosX - posX) / 3.0 + 0.5, posY + 1.0, black);
                    Gui.drawRect(endPosX - (endPosX - posX) / 3.0 - 0.5, posY - 0.5, endPosX, posY + 1.0, black);
                    Gui.drawRect(endPosX - 1.0, posY, endPosX + 0.5, posY + (endPosY - posY) / 4.0 + 0.5, black);
                    Gui.drawRect(endPosX - 1.0, endPosY, endPosX + 0.5, endPosY - (endPosY - posY) / 4.0 - 0.5, black);
                    Gui.drawRect(posX - 1.0, endPosY - 1.0, posX + (endPosX - posX) / 3.0 + 0.5, endPosY + 0.5, black);
                    Gui.drawRect(endPosX - (endPosX - posX) / 3.0 - 0.5, endPosY - 1.0, endPosX + 0.5, endPosY + 0.5, black);
                    Gui.drawRect(posX, posY, posX - 0.5, posY + (endPosY - posY) / 4.0, color);
                    Gui.drawRect(posX, endPosY, posX - 0.5, endPosY - (endPosY - posY) / 4.0, color);
                    Gui.drawRect(posX - 0.5, posY, posX + (endPosX - posX) / 3.0, posY + 0.5, color);
                    Gui.drawRect(endPosX - (endPosX - posX) / 3.0, posY, endPosX, posY + 0.5, color);
                    Gui.drawRect(endPosX - 0.5, posY, endPosX, posY + (endPosY - posY) / 4.0, color);
                    Gui.drawRect(endPosX - 0.5, endPosY, endPosX, endPosY - (endPosY - posY) / 4.0, color);
                    Gui.drawRect(posX, endPosY - 0.5, posX + (endPosX - posX) / 3.0, endPosY, color);
                    Gui.drawRect(endPosX - (endPosX - posX) / 3.0, endPosY - 0.5, endPosX - 0.5, endPosY, color);
                }
            }
            if (living = entity instanceof EntityLivingBase) {
                entityLivingBase = (EntityLivingBase)entity;
                if (health) {
                    float maxHealth;
                    float hp = entityLivingBase.getHealth();
                    if (hp > (maxHealth = entityLivingBase.getMaxHealth())) {
                        hp = maxHealth;
                    }
                    double hpPercentage = hp / maxHealth;
                    double hpHeight = (endPosY - posY) * hpPercentage;
                    Gui.drawRect(posX - 3.5, posY - 0.5, posX - 1.5, endPosY + 0.5, background);
                    if (hp > 0.0f) {
                        int healthColor = ColorUtils.getHealthColor(hp, maxHealth).getRGB();
                        Gui.drawRect(posX - 3.0, endPosY, posX - 2.0, endPosY - hpHeight, healthColor);
                        float absorption = entityLivingBase.getAbsorptionAmount();
                        if (absorption > 0.0f) {
                            Gui.drawRect(posX - 3.0, endPosY, posX - 2.0, endPosY - (endPosY - posY) / 6.0 * (double)absorption / 2.0, new Color(Potion.absorption.getLiquidColor()).getRGB());
                        }
                    }
                }
            }
            if (tag) {
                float scaledHeight = 10.0f;
                String name = entity.getName();
                if (entity instanceof EntityItem) {
                    name = ((EntityItem)entity).getEntityItem().getDisplayName();
                }
                String prefix = "";
                if (entity instanceof EntityPlayer) {
                    String string = prefix = this.isFriendly((EntityPlayer)entity) ? "\u00a7a" : "\u00a7c";
                }
                if (living && streamerMode.isEnabled()) {
                    name = "Enemy";
                    if (prefix.equals("\u00a7a")) {
                        name = "Friend";
                    } else if (entity instanceof EntityPlayerSP) {
                        name = "You";
                    }
                }
                double dif = (endPosX - posX) / 2.0;
                double textWidth = (float)fr.getStringWidth(name) * scale;
                float tagX = (float)((posX + dif - textWidth / 2.0) * (double)upscale);
                float tagY = (float)(posY * (double)upscale) - scaledHeight;
                GL11.glPushMatrix();
                GL11.glScalef((float)scale, (float)scale, (float)scale);
                if (living) {
                    Gui.drawRect(tagX - 2.0f, tagY - 2.0f, (double)tagX + textWidth * (double)upscale + 2.0, tagY + 9.0f, new Color(0, 0, 0, 140).getRGB());
                }
                fr.drawStringWithShadow(prefix + name, tagX, tagY, -1);
                GL11.glPopMatrix();
            }
            if (!armor) continue;
            if (living) {
                entityLivingBase = (EntityLivingBase)entity;
                float armorValue = entityLivingBase.getTotalArmorValue();
                double armorWidth = (endPosX - posX) * (double)armorValue / 20.0;
                Gui.drawRect(posX - 0.5, endPosY + 1.5, posX - 0.5 + endPosX - posX + 1.0, endPosY + 1.5 + 2.0, background);
                if (!(armorValue > 0.0f)) continue;
                Gui.drawRect(posX, endPosY + 2.0, posX + armorWidth, endPosY + 3.0, 0xFFFFFF);
                continue;
            }
            if (!(entity instanceof EntityItem) || !(itemStack = ((EntityItem)entity).getEntityItem()).isItemStackDamageable()) continue;
            int maxDamage = itemStack.getMaxDamage();
            float itemDurability = maxDamage - itemStack.getItemDamage();
            double durabilityWidth = (endPosX - posX) * (double)itemDurability / (double)maxDamage;
            Gui.drawRect(posX - 0.5, endPosY + 1.5, posX - 0.5 + endPosX - posX + 1.0, endPosY + 1.5 + 2.0, background);
            Gui.drawRect(posX, endPosY + 2.0, posX + durabilityWidth, endPosY + 3.0, 0xFFFFFF);
        }
        GL11.glPopMatrix();
        GlStateManager.enableBlend();
        entityRenderer.setupOverlayRendering(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
    }

    private boolean isFriendly(EntityPlayer player) {
        return PlayerUtils.isOnSameTeam(player) || FriendManager.isFriend(player.getName());
    }

    private void collectEntities() {
        this.collectedEntities.clear();
        List playerEntities = ESP2DMod.mc.theWorld.loadedEntityList;
        int playerEntitiesSize = playerEntities.size();
        for (int i = 0; i < playerEntitiesSize; ++i) {
            Entity entity = (Entity)playerEntities.get(i);
            if (!this.isValid(entity)) continue;
            this.collectedEntities.add(entity);
        }
    }

    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat((int)2982, (FloatBuffer)this.modelview);
        GL11.glGetFloat((int)2983, (FloatBuffer)this.projection);
        GL11.glGetInteger((int)2978, (IntBuffer)this.viewport);
        if (GLU.gluProject((float)((float)x), (float)((float)y), (float)((float)z), (FloatBuffer)this.modelview, (FloatBuffer)this.projection, (IntBuffer)this.viewport, (FloatBuffer)this.vector)) {
            return new Vector3d(this.vector.get(0) / (float)scaleFactor, ((float)Display.getHeight() - this.vector.get(1)) / (float)scaleFactor, this.vector.get(2));
        }
        return null;
    }

    private boolean isValid(Entity entity) {
        if (!(entity != ESP2DMod.mc.thePlayer || this.localPlayer.getValue().booleanValue() && ESP2DMod.mc.gameSettings.thirdPersonView != 0)) {
            return false;
        }
        if (entity.isDead) {
            return false;
        }
        if (!this.invisibles.getValue().booleanValue() && entity.isInvisible()) {
            return false;
        }
        if (this.droppedItems.getValue().booleanValue() && entity instanceof EntityItem && ESP2DMod.mc.thePlayer.getDistanceToEntity(entity) < 10.0f) {
            return true;
        }
        if (this.animals.getValue().booleanValue() && entity instanceof EntityAnimal) {
            return true;
        }
        if (this.players.getValue().booleanValue() && entity instanceof EntityPlayer) {
            return true;
        }
        return this.mobs.getValue() != false && (entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityDragon || entity instanceof EntityGolem);
    }

    public static enum BoxColor {
        WHITE,
        RED;

    }

    public static enum BoxMode {
        BOX,
        CORNERS;

    }
}


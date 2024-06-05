// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.visuals;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.gui.FontRenderer;
import java.util.Iterator;
import net.minecraft.util.AxisAlignedBB;
import java.awt.Color;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.utils.render.OGLUtils;
import gg.childtrafficking.smokex.event.Event;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import org.lwjgl.opengl.GL11;
import gg.childtrafficking.smokex.event.events.render.EventRender3D;
import gg.childtrafficking.smokex.event.events.system.EventLoadWorld;
import gg.childtrafficking.smokex.event.events.render.EventRender2D;
import gg.childtrafficking.smokex.event.events.render.EventRenderNameTag;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "ESP", renderName = "ESP", description = "Shows where entities are on your screen", category = ModuleCategory.VISUALS)
public final class ESPModule extends Module
{
    private static ESPModule espInst;
    public final BooleanProperty tagsProperty;
    public final BooleanProperty tagsBackgroundProperty;
    public final BooleanProperty tagsHealthProperty;
    public final BooleanProperty tagsHealthBarProperty;
    public final BooleanProperty esp2dProperty;
    public final BooleanProperty armorBarProperty;
    public final BooleanProperty healthBarProperty;
    public final BooleanProperty pulsingHealthBarProperty;
    public final BooleanProperty skeletonsProperty;
    public final NumberProperty skeletonWidthProperty;
    private final Map<EntityPlayer, float[][]> playerRotationMap;
    private final Map<EntityPlayer, float[]> entityPosMap;
    private final EventListener<EventRenderNameTag> renderNameTagEventEventCallback;
    private final EventListener<EventRender2D> eventEventCallback;
    private final Map<EntityPlayer, Relationship> playerRelationshipMap;
    private final EventListener<EventLoadWorld> onWorldLoad;
    private final EventListener<EventRender3D> render3DEventEventCallback;
    
    private void drawSkeleton(final float pt, final EntityPlayer player) {
        final float[][] entPos;
        if ((entPos = this.playerRotationMap.get(player)) != null) {
            GL11.glPushMatrix();
            final float x = (float)(RenderingUtils.interpolate(player.prevPosX, player.posX, pt) - RenderManager.renderPosX);
            final float y = (float)(RenderingUtils.interpolate(player.prevPosY, player.posY, pt) - RenderManager.renderPosY);
            final float z = (float)(RenderingUtils.interpolate(player.prevPosZ, player.posZ, pt) - RenderManager.renderPosZ);
            GL11.glTranslated((double)x, (double)y, (double)z);
            final boolean sneaking = player.isSneaking();
            float renderYawOffset = 0.0f;
            float rotationYawHead = 0.0f;
            float prevRenderYawOffset = 0.0f;
            Label_0170: {
                if (player instanceof EntityPlayerSP) {
                    final EntityPlayerSP localPlayer = (EntityPlayerSP)player;
                    if (localPlayer.currentEvent.isRotating()) {
                        final EventUpdate event = localPlayer.currentEvent;
                        rotationYawHead = (renderYawOffset = event.getYaw());
                        prevRenderYawOffset = event.getPrevYaw();
                        break Label_0170;
                    }
                }
                rotationYawHead = player.rotationYawHead;
                renderYawOffset = player.renderYawOffset;
                prevRenderYawOffset = player.prevRenderYawOffset;
            }
            final float xOff = (float)RenderingUtils.interpolate(prevRenderYawOffset, renderYawOffset, pt);
            final float yOff = sneaking ? 0.6f : 0.75f;
            GL11.glRotatef(-xOff, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, 0.0f, sneaking ? -0.235f : 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.125f, yOff, 0.0f);
            if (entPos[3][0] != 0.0f) {
                GL11.glRotatef(entPos[3][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[3][1] != 0.0f) {
                GL11.glRotatef(entPos[3][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[3][2] != 0.0f) {
                GL11.glRotatef(entPos[3][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, -yOff, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.125f, yOff, 0.0f);
            if (entPos[4][0] != 0.0f) {
                GL11.glRotatef(entPos[4][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[4][1] != 0.0f) {
                GL11.glRotatef(entPos[4][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[4][2] != 0.0f) {
                GL11.glRotatef(entPos[4][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, -yOff, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslatef(0.0f, 0.0f, sneaking ? 0.25f : 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, sneaking ? -0.05f : 0.0f, sneaking ? -0.01725f : 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.375f, yOff + 0.55f, 0.0f);
            if (entPos[1][0] != 0.0f) {
                GL11.glRotatef(entPos[1][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[1][1] != 0.0f) {
                GL11.glRotatef(entPos[1][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[1][2] != 0.0f) {
                GL11.glRotatef(-entPos[1][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, -0.5f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.375f, yOff + 0.55f, 0.0f);
            if (entPos[2][0] != 0.0f) {
                GL11.glRotatef(entPos[2][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[2][1] != 0.0f) {
                GL11.glRotatef(entPos[2][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[2][2] != 0.0f) {
                GL11.glRotatef(-entPos[2][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, -0.5f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef(xOff - rotationYawHead, 0.0f, 1.0f, 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, yOff + 0.55f, 0.0f);
            if (entPos[0][0] != 0.0f) {
                GL11.glRotatef(entPos[0][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, 0.3f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef(sneaking ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, sneaking ? -0.16175f : 0.0f, sneaking ? -0.48025f : 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (double)yOff, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3f(-0.125f, 0.0f, 0.0f);
            GL11.glVertex3f(0.125f, 0.0f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, yOff, 0.0f);
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, 0.55f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, yOff + 0.55f, 0.0f);
            GL11.glBegin(3);
            GL11.glVertex3f(-0.375f, 0.0f, 0.0f);
            GL11.glVertex3f(0.375f, 0.0f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }
    
    public static boolean isValid(final Entity entity) {
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        final EntityPlayer player = (EntityPlayer)entity;
        if (!player.isEntityAlive()) {
            return false;
        }
        if (player.isInvisible()) {
            return false;
        }
        final Map<EntityPlayer, Relationship> relationshipMap = ESPModule.espInst.playerRelationshipMap;
        final Relationship relationship = relationshipMap.get(player);
        final boolean hasData = relationship != null;
        final boolean teammate = hasData && relationship.teammate;
        final boolean friend = hasData && relationship.friend;
        if (!hasData) {
            relationshipMap.put(player, new Relationship(teammate, friend));
        }
        return RenderingUtils.isBBInFrustum(entity.getEntityBoundingBox()) && Minecraft.getMinecraft().theWorld.loadedEntityList.contains(player);
    }
    
    public ESPModule() {
        this.tagsProperty = new BooleanProperty("Tags", true);
        this.tagsBackgroundProperty = new BooleanProperty("TagBackground", "Tag Background", true, this.tagsProperty::getValue);
        this.tagsHealthProperty = new BooleanProperty("TagsHP", "TagsHP", false, this.tagsProperty::getValue);
        this.tagsHealthBarProperty = new BooleanProperty("TagsHPBar", "Tags HP Bar", false, this.tagsProperty::getValue);
        this.esp2dProperty = new BooleanProperty("2DESP", "2D ESP", true);
        this.armorBarProperty = new BooleanProperty("ArmorBar", "Armor Bar", false, this.esp2dProperty::getValue);
        this.healthBarProperty = new BooleanProperty("HPBar", "HP Bar", true, this.esp2dProperty::getValue);
        this.pulsingHealthBarProperty = new BooleanProperty("PulsingHP", "Pulsing HP", this.healthBarProperty.getValue());
        this.skeletonsProperty = new BooleanProperty("Skeletons", true);
        this.skeletonWidthProperty = new NumberProperty("SkeletonWidth", "Skeleton Width", (T)0.5, (T)0.5, (T)5.0, (T)0.5, this.skeletonsProperty::getValue);
        this.playerRotationMap = new HashMap<EntityPlayer, float[][]>();
        this.entityPosMap = new HashMap<EntityPlayer, float[]>();
        this.renderNameTagEventEventCallback = Event::cancel;
        this.eventEventCallback = (event -> {
            final boolean tags = this.tagsProperty.getValue();
            final boolean esp2d = this.esp2dProperty.getValue();
            if (!esp2d && !tags) {
                return;
            }
            else {
                final boolean box = false;
                final boolean healthBar = this.healthBarProperty.getValue();
                final boolean armorBar = this.armorBarProperty.getValue();
                final boolean noTextureHotPath = esp2d && !tags && (healthBar || armorBar);
                if (noTextureHotPath) {
                    GL11.glDisable(3553);
                }
                OGLUtils.enableBlending();
                this.entityPosMap.keySet().iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final EntityPlayer player = iterator.next();
                    if (player.getDistanceToEntity(this.mc.thePlayer) >= 1.0f || this.mc.gameSettings.thirdPersonView != 0) {
                        if (!RenderingUtils.isBBInFrustum(player.getEntityBoundingBox())) {
                            continue;
                        }
                        else {
                            final float[] positions = this.entityPosMap.get(player);
                            final float x = positions[0];
                            final float y = positions[1];
                            final float x2 = positions[2];
                            final float y2 = positions[3];
                            final float health = player.getHealth();
                            final float maxHealth = player.getMaxHealth();
                            final float healthPercentage = health / maxHealth;
                            if (tags) {
                                final FontRenderer fontRenderer = this.mc.fontRendererObj;
                                final String name = player.getGameProfile().getName();
                                final float halfWidth = (float)(fontRenderer.getStringWidth(name) / 2);
                                final float xDif = x2 - x;
                                final float middle = x + xDif / 2.0f;
                                final float textHeight = 10.0f;
                                float renderY = y - 10.0f - 2.0f;
                                final boolean teamMate = false;
                                final boolean friend = SmokeXClient.getInstance().getPlayerManager().isFriend(player.getName());
                                final boolean enemy = SmokeXClient.getInstance().getPlayerManager().isEnemy(player.getName());
                                GL11.glPushMatrix();
                                final String text = (enemy ? "§C" : (friend ? "§A" : "§R")) + name;
                                final boolean tagsHealthBar = this.tagsHealthBarProperty.getValue();
                                final float left = middle - halfWidth - 1.0f;
                                final float right = middle + halfWidth + 1.0f;
                                if (tagsHealthBar) {
                                    renderY -= 2.5f;
                                }
                                final String healthString = String.valueOf((int)health);
                                final float healthWidth = (float)fontRenderer.getStringWidth(healthString);
                                final float healthBoxLeft = right + 1.0f;
                                if (this.tagsBackgroundProperty.getValue()) {
                                    GL11.glDisable(3553);
                                    GL11.glColor4ub((byte)0, (byte)0, (byte)0, (byte)85);
                                    GL11.glBegin(7);
                                    GL11.glVertex2f(left * 1.4285715f, (renderY - 7.0f) * 1.4285715f);
                                    GL11.glVertex2f(left * 1.4285715f, (renderY + 10.0f + 1.0f) * 1.4285715f);
                                    GL11.glVertex2f(right * 1.4285715f, (renderY + 10.0f + 1.0f) * 1.4285715f);
                                    GL11.glVertex2f(right * 1.4285715f, (renderY - 7.0f) * 1.4285715f);
                                    if (this.tagsHealthProperty.getValue()) {
                                        final float healthBoxRight = healthBoxLeft + healthWidth + 2.0f;
                                        final float healthBoxBottom = renderY + 10.0f + 1.0f;
                                        GL11.glVertex2f(healthBoxLeft, renderY - 1.0f);
                                        GL11.glVertex2f(healthBoxLeft, healthBoxBottom);
                                        GL11.glVertex2f(healthBoxRight, healthBoxBottom);
                                        GL11.glVertex2f(healthBoxRight, renderY - 1.0f);
                                    }
                                    if (tagsHealthBar) {
                                        OGLUtils.color(-8912896);
                                        GL11.glVertex2f(left, renderY + 10.0f + 1.0f);
                                        GL11.glVertex2f(left, renderY + 10.0f + 2.0f);
                                        GL11.glVertex2f(right, renderY + 10.0f + 2.0f);
                                        GL11.glVertex2f(right, renderY + 10.0f + 1.0f);
                                        OGLUtils.color(RenderingUtils.getColorFromPercentage(healthPercentage));
                                        final float healthBarRight = left + (right - left) * healthPercentage;
                                        GL11.glVertex2f(left, renderY + 10.0f + 1.0f);
                                        GL11.glVertex2f(left, renderY + 10.0f + 2.0f);
                                        GL11.glVertex2f(healthBarRight, renderY + 10.0f + 2.0f);
                                        GL11.glVertex2f(healthBarRight, renderY + 10.0f + 1.0f);
                                    }
                                    GL11.glEnd();
                                    GL11.glEnable(3553);
                                }
                                fontRenderer.drawStringWithShadow(text, middle - halfWidth, renderY + 0.5f, -1);
                                if (this.tagsHealthProperty.getValue()) {
                                    fontRenderer.drawStringWithShadow(healthString, healthBoxLeft + 1.0f, renderY + 0.5f, RenderingUtils.getColorFromPercentage(health / player.getMaxHealth()));
                                }
                                OGLUtils.enableBlending();
                            }
                            GL11.glScalef(1.4285715f, 1.4285715f, 1.4285715f);
                            GL11.glPopMatrix();
                            if (esp2d) {
                                if (armorBar) {
                                    final float armorPercentage = player.getTotalArmorValue() / 20.0f;
                                    final float armorBarWidth = (x2 - x) * armorPercentage;
                                    if (!noTextureHotPath) {
                                        GL11.glDisable(3553);
                                    }
                                    GL11.glColor4ub((byte)0, (byte)0, (byte)0, (byte)(-106));
                                    GL11.glBegin(7);
                                    GL11.glVertex2f(x, y2 + 0.5f);
                                    GL11.glVertex2f(x, y2 + 2.5f);
                                    GL11.glVertex2f(x2, y2 + 2.5f);
                                    GL11.glVertex2f(x2, y2 + 0.5f);
                                    if (armorPercentage > 0.0f) {
                                        OGLUtils.color(new Color(0, 100, 255).getRGB());
                                        GL11.glVertex2f(x + 0.5f, y2 + 1.0f);
                                        GL11.glVertex2f(x + 0.5f, y2 + 2.0f);
                                        GL11.glVertex2f(x + armorBarWidth - 0.5f, y2 + 2.0f);
                                        GL11.glVertex2f(x + armorBarWidth - 0.5f, y2 + 1.0f);
                                    }
                                    GL11.glEnd();
                                    if (!noTextureHotPath && !healthBar) {
                                        GL11.glEnable(3553);
                                    }
                                }
                                if (healthBar) {
                                    if (!noTextureHotPath && !armorBar) {
                                        GL11.glDisable(3553);
                                    }
                                    final float healthBarLeft = x - 2.5f;
                                    final float healthBarRight2 = x - 0.5f;
                                    GL11.glColor4ub((byte)0, (byte)0, (byte)0, (byte)(-106));
                                    GL11.glBegin(7);
                                    GL11.glVertex2f(healthBarLeft, y);
                                    GL11.glVertex2f(healthBarLeft, y2);
                                    GL11.glVertex2f(healthBarRight2, y2);
                                    GL11.glVertex2f(healthBarRight2, y);
                                    final float healthBarLeft2 = healthBarLeft + 0.5f;
                                    final float healthBarRight3 = healthBarRight2 - 0.5f;
                                    final float heightDif = y - y2;
                                    final float healthBarHeight = heightDif * healthPercentage;
                                    final boolean pulsing = this.pulsingHealthBarProperty.getValue();
                                    final float topOfHealthBar = y2 + 0.5f + healthBarHeight;
                                    final HealthBarColor healthBarColorMode = HealthBarColor.SOLID;
                                    if (healthBarColorMode != HealthBarColor.GRADIENT) {
                                        final int color = (healthBarColorMode == HealthBarColor.SOLID) ? RenderingUtils.getColorFromPercentage(healthPercentage) : RenderingUtils.getColorFromPercentage(healthPercentage);
                                        OGLUtils.color(pulsing ? RenderingUtils.fadeBetween(color, RenderingUtils.darker(color)) : color);
                                        GL11.glVertex2f(healthBarLeft2, topOfHealthBar);
                                        GL11.glVertex2f(healthBarLeft2, y2 - 0.5f);
                                        GL11.glVertex2f(healthBarRight3, y2 - 0.5f);
                                        GL11.glVertex2f(healthBarRight3, topOfHealthBar);
                                    }
                                    else {
                                        final boolean needScissor = health < maxHealth;
                                        if (needScissor) {
                                            GL11.glEnable(3089);
                                            OGLUtils.startScissorBox(event.getResolution(), (int)healthBarLeft2, (int)(y2 + healthBarHeight), 2, (int)y2);
                                        }
                                        final int startColor = Color.WHITE.getRGB();
                                        final int endColor = Color.GREEN.getRGB();
                                        GL11.glShadeModel(7425);
                                        OGLUtils.color(pulsing ? RenderingUtils.fadeBetween(startColor, RenderingUtils.darker(startColor)) : startColor);
                                        GL11.glVertex2f(healthBarLeft2, topOfHealthBar);
                                        GL11.glVertex2f(healthBarLeft2, y2 - 0.5f);
                                        OGLUtils.color(pulsing ? RenderingUtils.fadeBetween(endColor, RenderingUtils.darker(endColor)) : endColor);
                                        GL11.glVertex2f(healthBarRight3, y2 - 0.5f);
                                        GL11.glVertex2f(healthBarRight3, topOfHealthBar);
                                        GL11.glShadeModel(7424);
                                        if (needScissor) {
                                            GL11.glDisable(3089);
                                        }
                                    }
                                    final float absorption = player.getAbsorptionAmount();
                                    final float absorptionPercentage = Math.min(1.0f, absorption / 20.0f);
                                    final int absorptionColor = new Color(255, 255, 0).getRGB();
                                    final float absorptionHeight = heightDif * absorptionPercentage;
                                    final float topOfAbsorptionBar = y2 + 0.5f + absorptionHeight;
                                    OGLUtils.color(pulsing ? RenderingUtils.fadeBetween(absorptionColor, RenderingUtils.darker(absorptionColor)) : absorptionColor);
                                    GL11.glVertex2f(healthBarLeft2, topOfAbsorptionBar);
                                    GL11.glVertex2f(healthBarLeft2, y2 - 0.5f);
                                    GL11.glVertex2f(healthBarRight3, y2 - 0.5f);
                                    GL11.glVertex2f(healthBarRight3, topOfAbsorptionBar);
                                    GL11.glEnd();
                                    if (!noTextureHotPath) {
                                        GL11.glEnable(3553);
                                    }
                                    else {
                                        continue;
                                    }
                                }
                                else {
                                    continue;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                GL11.glDisable(3042);
                if (noTextureHotPath) {
                    GL11.glEnable(3553);
                }
                return;
            }
        });
        this.playerRelationshipMap = new HashMap<EntityPlayer, Relationship>();
        this.onWorldLoad = (event -> {
            this.entityPosMap.clear();
            this.playerRotationMap.clear();
            this.playerRelationshipMap.clear();
            return;
        });
        this.render3DEventEventCallback = (event -> {
            final boolean skeletons = this.skeletonsProperty.getValue();
            final boolean project2D = this.esp2dProperty.getValue() || this.tagsProperty.getValue();
            if (project2D && !this.entityPosMap.isEmpty()) {
                this.entityPosMap.clear();
            }
            if (skeletons) {
                GL11.glPushMatrix();
                GL11.glLineWidth(0.5f);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2848);
                OGLUtils.color(Color.WHITE.getRGB());
                GL11.glDisable(2929);
                GL11.glDisable(3553);
                GL11.glDepthMask(false);
            }
            final float partialTicks = event.getPartialTicks();
            this.mc.theWorld.playerEntities.iterator();
            final Iterator iterator2;
            while (iterator2.hasNext()) {
                final EntityPlayer player2 = iterator2.next();
                if (!isValid(player2)) {
                    continue;
                }
                else {
                    if (project2D) {
                        final double posX = RenderingUtils.interpolate(player2.prevPosX, player2.posX, partialTicks) - RenderManager.viewerPosX;
                        final double posY = RenderingUtils.interpolate(player2.prevPosY, player2.posY, partialTicks) - RenderManager.viewerPosY;
                        final double posZ = RenderingUtils.interpolate(player2.prevPosZ, player2.posZ, partialTicks) - RenderManager.viewerPosZ;
                        final double halfWidth2 = player2.width / 2.0;
                        new AxisAlignedBB(posX - halfWidth2, posY, posZ - halfWidth2, posX + halfWidth2, posY + player2.height + (player2.isSneaking() ? -0.2 : 0.1), posZ + halfWidth2);
                        final AxisAlignedBB axisAlignedBB;
                        final AxisAlignedBB bb = axisAlignedBB.expand(0.1, 0.1, 0.1);
                        final double[][] vectors = { { bb.minX, bb.minY, bb.minZ }, { bb.minX, bb.maxY, bb.minZ }, { bb.minX, bb.maxY, bb.maxZ }, { bb.minX, bb.minY, bb.maxZ }, { bb.maxX, bb.minY, bb.minZ }, { bb.maxX, bb.maxY, bb.minZ }, { bb.maxX, bb.maxY, bb.maxZ }, { bb.maxX, bb.minY, bb.maxZ } };
                        final float[] position = { Float.MAX_VALUE, Float.MAX_VALUE, -1.0f, -1.0f };
                        final double[][] array;
                        int i = 0;
                        for (int length = array.length; i < length; ++i) {
                            final double[] vec = array[i];
                            final float[] projection = OGLUtils.project2D((float)vec[0], (float)vec[1], (float)vec[2], 2);
                            if (projection != null && projection[2] >= 0.0f && projection[2] < 1.0f) {
                                final float pX = projection[0];
                                final float pY = projection[1];
                                position[0] = Math.min(position[0], pX);
                                position[1] = Math.min(position[1], pY);
                                position[2] = Math.max(position[2], pX);
                                position[3] = Math.max(position[3], pY);
                            }
                        }
                        this.entityPosMap.put(player2, position);
                    }
                    if (skeletons) {
                        this.drawSkeleton(partialTicks, player2);
                    }
                    else {
                        continue;
                    }
                }
            }
            if (skeletons) {
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glEnable(3553);
                GL11.glDisable(2848);
                GL11.glEnable(2929);
                GL11.glPopMatrix();
            }
            return;
        });
        ESPModule.espInst = this;
    }
    
    public static boolean shouldDrawSkeletons() {
        return ESPModule.espInst.isToggled() && ESPModule.espInst.skeletonsProperty.getValue();
    }
    
    public static void addEntity(final EntityPlayer e, final ModelPlayer model) {
        ESPModule.espInst.playerRotationMap.put(e, new float[][] { { model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ }, { model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ }, { model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ }, { model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ }, { model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ } });
    }
    
    @Override
    public void onDisable() {
        this.entityPosMap.clear();
        this.playerRotationMap.clear();
        super.onDisable();
    }
    
    private enum HealthBarColor
    {
        SOLID, 
        HEALTH, 
        GRADIENT;
    }
    
    private enum Targets
    {
        PLAYERS, 
        TEAMMATES, 
        FRIENDS, 
        INVISIBLES, 
        SELF;
    }
    
    private static class Relationship
    {
        private boolean teammate;
        private boolean friend;
        
        public Relationship(final boolean teammate, final boolean friend) {
            this.teammate = teammate;
            this.friend = friend;
        }
    }
}

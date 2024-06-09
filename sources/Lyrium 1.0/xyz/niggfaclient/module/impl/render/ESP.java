// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import java.util.HashMap;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.model.ModelPlayer;
import javax.vecmath.Vector3f;
import xyz.niggfaclient.font.MCFontRenderer;
import java.util.Iterator;
import java.util.function.Predicate;
import org.lwjgl.util.vector.Vector4f;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.entity.RenderManager;
import xyz.niggfaclient.utils.render.GradientUtil;
import net.minecraft.entity.EntityLivingBase;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import xyz.niggfaclient.utils.player.RotationUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import xyz.niggfaclient.utils.render.RenderUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.EnumChatFormatting;
import xyz.niggfaclient.utils.player.PlayerUtil;
import xyz.niggfaclient.font.Fonts;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.utils.Utils;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import xyz.niggfaclient.utils.render.ColorUtil;
import xyz.niggfaclient.events.impl.UpdateModelEvent;
import xyz.niggfaclient.events.impl.Render3DEvent;
import xyz.niggfaclient.events.impl.Render2DEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.NameEvent;
import xyz.niggfaclient.eventbus.Listener;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "ESP", description = "Renders a box around players through walls", cat = Category.RENDER)
public class ESP extends Module
{
    private final EnumProperty<BGMode> backgroundMode;
    private final Property<Boolean> lp;
    private final Property<Boolean> background;
    private final Property<Boolean> healthText;
    private final Property<Boolean> healthBar;
    private final Property<Boolean> esp_2d;
    public final Property<Boolean> tags;
    private final Property<Boolean> box;
    private final Property<Boolean> skeleton;
    private final DoubleProperty skeletonWidth;
    private final Property<Boolean> arrows;
    private final Property<Integer> arrowsColor;
    private final DoubleProperty arrowsRadius;
    private final DoubleProperty arrowsSize;
    private final DoubleProperty arrowsAlpha;
    public static Map<EntityPlayer, float[][]> playerRotationMap;
    public static Map<EntityPlayer, float[]> entityPosMap;
    @EventLink
    private final Listener<NameEvent> nameEventListener;
    @EventLink
    private final Listener<Render2DEvent> render2DEventListener;
    @EventLink
    private final Listener<Render3DEvent> render3DEventListener;
    @EventLink
    private final Listener<UpdateModelEvent> updateModelEventListener;
    
    public ESP() {
        this.backgroundMode = new EnumProperty<BGMode>("Background Mode", BGMode.Round);
        this.lp = new Property<Boolean>("Local Player", true);
        this.background = new Property<Boolean>("Background", true);
        this.healthText = new Property<Boolean>("Health Text", false);
        this.healthBar = new Property<Boolean>("Health Bar", true);
        this.esp_2d = new Property<Boolean>("2D ESP", true);
        this.tags = new Property<Boolean>("Tags", true);
        this.box = new Property<Boolean>("Box", true);
        this.skeleton = new Property<Boolean>("Skeleton", false);
        this.skeletonWidth = new DoubleProperty("Skeleton Width", 0.5, 0.5, 5.0, 0.5, this.skeleton::getValue);
        this.arrows = new Property<Boolean>("Arrows", true);
        this.arrowsColor = new Property<Integer>("Arrow Color", ColorUtil.DEEP_PURPLE, this.arrows::getValue);
        this.arrowsRadius = new DoubleProperty("Arrow Radius", 30.0, 10.0, 100.0, 1.0, this.arrows::getValue);
        this.arrowsSize = new DoubleProperty("Arrow Size", 6.0, 3.0, 30.0, 1.0, this.arrows::getValue);
        this.arrowsAlpha = new DoubleProperty("Arrow Alpha", 1.0, 0.1, 1.0, 0.1, this.arrows::getValue);
        this.nameEventListener = (e -> {
            if (this.tags.getValue() && ESP.entityPosMap.containsKey(e.getEntity())) {
                e.setCancelled();
            }
            return;
        });
        int count;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer entity;
        String s;
        String name;
        float[] positions;
        float x;
        float y;
        float x2;
        float y2;
        float health;
        float maxHealth;
        float healthPercentage;
        float heightDif;
        float healthBarHeight;
        float middleX;
        float middleY;
        float pt;
        MCFontRenderer sf21;
        String string;
        final StringBuilder sb;
        float halfWidth;
        float xDif;
        float middle;
        float textHeight;
        float renderY;
        float arrowSize;
        float alpha;
        float yaw;
        float offset;
        float left;
        float right;
        String string2;
        final StringBuilder sb2;
        String user;
        Color firstColor;
        Color secondColor;
        Color thirdColor;
        Color fourthColor;
        this.render2DEventListener = (e -> {
            count = 0;
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            ESP.entityPosMap.keySet().iterator();
            while (iterator.hasNext()) {
                entity = iterator.next();
                if (entity.getDistanceToEntity(this.mc.thePlayer) < 1.0f && !Utils.isInThirdPerson()) {
                    continue;
                }
                else {
                    if (ModuleManager.getModule(Streamer.class).isEnabled()) {
                        if (entity == this.mc.thePlayer) {
                            s = Client.getInstance().clientName + " User";
                        }
                        else {
                            s = "Player";
                        }
                    }
                    else {
                        s = String.valueOf(entity.getDisplayName().getFormattedText());
                    }
                    name = s;
                    positions = ESP.entityPosMap.get(entity);
                    x = positions[0];
                    y = positions[1];
                    x2 = positions[2];
                    y2 = positions[3];
                    health = entity.getHealth();
                    maxHealth = entity.getMaxHealth();
                    healthPercentage = health / maxHealth;
                    heightDif = y - y2;
                    healthBarHeight = heightDif * healthPercentage;
                    middleX = e.getWidth() / 2.0f;
                    middleY = e.getHeight() / 2.0f;
                    pt = e.getTicks();
                    sf21 = Fonts.sf21;
                    new StringBuilder().append(PlayerUtil.isOnSameTeam(entity) ? EnumChatFormatting.GREEN : EnumChatFormatting.RED).append(name);
                    if (Client.getInstance().getFriendManager().isFriend(name)) {
                        string = EnumChatFormatting.LIGHT_PURPLE + " [FRIEND]";
                    }
                    else {
                        string = "";
                    }
                    halfWidth = sf21.getStringWidthProtected(sb.append(string).append(ChatFormatting.GRAY).append(" [").append(ChatFormatting.RESET).append(Math.round(health * 100.0 / 100.0)).append(" HP").append(ChatFormatting.GRAY).append("]").toString()) / 2.0f;
                    xDif = x2 - x;
                    middle = x + xDif / 2.0f;
                    textHeight = (float)Fonts.sf21.getStringHeight(name);
                    renderY = y - textHeight - 2.0f;
                    RenderUtils.startBlending();
                    if (this.arrows.getValue() && entity instanceof EntityOtherPlayerMP) {
                        GL11.glPushMatrix();
                        arrowSize = this.arrowsSize.getValue().floatValue();
                        alpha = (float)Math.max(1.0f - entity.getDistanceToEntity(entity) / 30.0f, this.arrowsAlpha.getValue());
                        GL11.glTranslatef(middleX + 0.5f, middleY, 1.0f);
                        yaw = RenderUtils.interpolate(RotationUtils.getYawToEntity(entity, true), RotationUtils.getYawToEntity(entity, false), pt) - RenderUtils.interpolate(entity.prevRotationYaw, entity.rotationYaw, pt);
                        GL11.glRotatef(yaw, 0.0f, 0.0f, 1.0f);
                        GL11.glTranslatef(0.0f, -this.arrowsRadius.getValue().floatValue() - this.arrowsSize.getValue().floatValue(), 0.0f);
                        GL11.glDisable(3553);
                        GL11.glBegin(9);
                        GL11.glColor4ub((byte)(this.arrowsColor.getValue() >> 16 & 0xFF), (byte)(this.arrowsColor.getValue() >> 8 & 0xFF), (byte)(this.arrowsColor.getValue() & 0xFF), (byte)(alpha * 255.0f));
                        GL11.glVertex2f(0.0f, 0.0f);
                        offset = (float)(int)(arrowSize / 3.0f);
                        GL11.glVertex2f(-arrowSize + offset, arrowSize);
                        GL11.glVertex2f(0.0f, arrowSize - offset);
                        GL11.glVertex2f(arrowSize - offset, arrowSize);
                        GL11.glEnd();
                        GL11.glEnable(3553);
                        GL11.glPopMatrix();
                    }
                    RenderUtils.endBlending();
                    GL11.glPushMatrix();
                    if (this.healthBar.getValue()) {
                        Gui.drawRect(x - 3.0, y, x - 1.0f, y2, 2013265920);
                        Gui.drawRect(x - 2.5f, y2 + 0.5f + healthBarHeight, x - 1.5f, y2 - 0.5f, new Color(0, 255, 0).getRGB());
                    }
                    if (this.healthText.getValue()) {
                        Fonts.sf15.drawStringWithShadow(Math.round(health / 100.0f * 100.0f) * 5 + "%", x - 35.0f, y2 + 0.5f + healthBarHeight, -1);
                    }
                    if (this.background.getValue()) {
                        left = middle - halfWidth - 1.0f;
                        right = middle + halfWidth + 1.0f;
                        if (this.backgroundMode.getValue() == BGMode.Rect) {
                            Gui.drawRect(left, renderY - 1.0f, right, renderY + textHeight + 1.0f, new Color(0, 0, 0, 150).getRGB());
                        }
                        else {
                            RenderUtils.drawRoundedRect2(left - 1.0f, renderY - 1.0f, right, renderY + textHeight + 1.0f, 6.0, new Color(0, 0, 0, 150).getRGB());
                        }
                    }
                    if (this.tags.getValue()) {
                        new StringBuilder().append(name);
                        if (Client.getInstance().getFriendManager().isFriend(name)) {
                            string2 = EnumChatFormatting.LIGHT_PURPLE + " [FRIEND]";
                        }
                        else {
                            string2 = "";
                        }
                        user = sb2.append(string2).toString();
                        Fonts.sf21.drawStringWithShadow((PlayerUtil.isOnSameTeam(entity) ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + user + ChatFormatting.GRAY + " [" + ChatFormatting.RESET + Math.round(health * 100.0 / 100.0) + " HP" + ChatFormatting.GRAY + "]", middle - halfWidth, renderY + 1.0f, ColorUtil.getHealthColor(entity).getRGB());
                    }
                    GL11.glPopMatrix();
                    firstColor = ColorUtil.interpolateColorsBackAndForth(5, 0, new Color(ColorUtil.getHUDColor(count * 200)), new Color(ColorUtil.getHUDColor(count * 200)).darker().darker());
                    secondColor = ColorUtil.interpolateColorsBackAndForth(5, 90, new Color(ColorUtil.getHUDColor(count * 200)), new Color(ColorUtil.getHUDColor(count * 200)).darker().darker());
                    thirdColor = ColorUtil.interpolateColorsBackAndForth(5, 180, new Color(ColorUtil.getHUDColor(count * 200)), new Color(ColorUtil.getHUDColor(count * 200)).darker().darker());
                    fourthColor = ColorUtil.interpolateColorsBackAndForth(5, 270, new Color(ColorUtil.getHUDColor(count * 200)), new Color(ColorUtil.getHUDColor(count * 200)).darker().darker());
                    if (this.box.getValue()) {
                        GradientUtil.drawGradientLR(x, y, x2 - x, 1.0f, 1.0f, firstColor, secondColor);
                        GradientUtil.drawGradientTB(x, y, 1.0f, y2 - y, 1.0f, firstColor, fourthColor);
                        GradientUtil.drawGradientLR(x, y2, x2 - x, 1.0f, 1.0f, fourthColor, thirdColor);
                        GradientUtil.drawGradientTB(x2, y, 1.0f, y2 - y + 1.0f, 1.0f, secondColor, thirdColor);
                        Gui.drawRect2(x - 0.5f, y - 0.5, x2 - x + 2.0f, 0.5, Color.BLACK.getRGB());
                        Gui.drawRect2(x - 0.5, y, 0.5, y2 - y + 1.0f, Color.BLACK.getRGB());
                        Gui.drawRect2(x - 0.5f, y2 + 1.0f, x2 - x + 2.0f, 0.5, Color.BLACK.getRGB());
                        Gui.drawRect2(x2 + 1.0f, y, 0.5, y2 - y + 1.0f, Color.BLACK.getRGB());
                        Gui.drawRect2(x + 1.0f, y + 1.0f, x2 - x - 1.0f, 0.5, Color.BLACK.getRGB());
                        Gui.drawRect2(x + 1.0f, y + 1.0f, 0.5, y2 - y - 1.0f, Color.BLACK.getRGB());
                        Gui.drawRect2(x + 1.0f, y2 - 0.5, x2 - x - 1.0f, 0.5, Color.BLACK.getRGB());
                        Gui.drawRect2(x2 - 0.5, y + 1.0f, 0.5, y2 - y - 1.0f, Color.BLACK.getRGB());
                    }
                    ++count;
                }
            }
            return;
        });
        float partialTicks;
        final Iterator<EntityPlayer> iterator2;
        EntityPlayer player;
        float posX;
        float posY;
        float posZ;
        double halfWidth2;
        AxisAlignedBB bb;
        double[][] vectors;
        Vector4f position;
        final double[][] array;
        int length;
        int j = 0;
        double[] vec;
        Vector3f projection;
        Object[] players;
        int i;
        int playersLength;
        EntityPlayer entityPlayer;
        float[][] entPos;
        float pt2;
        float x3;
        float y3;
        float z;
        boolean sneaking;
        float xOff;
        float yOff;
        this.render3DEventListener = (e -> {
            if (!ESP.entityPosMap.isEmpty()) {
                ESP.entityPosMap.clear();
            }
            if (this.esp_2d.getValue() || this.arrows.getValue() || this.tags.getValue()) {
                partialTicks = e.getPartialTicks();
                this.mc.theWorld.playerEntities.iterator();
                while (iterator2.hasNext()) {
                    player = iterator2.next();
                    if ((player instanceof EntityOtherPlayerMP || (this.lp.getValue() && Utils.isInThirdPerson())) && player.isEntityAlive()) {
                        if (player.isInvisible()) {
                            continue;
                        }
                        else {
                            GL11.glPushMatrix();
                            posX = (float)(RenderUtils.interpolate(player.prevPosX, player.posX, partialTicks) - RenderManager.viewerPosX);
                            posY = (float)(RenderUtils.interpolate(player.prevPosY, player.posY, partialTicks) - RenderManager.viewerPosY);
                            posZ = (float)(RenderUtils.interpolate(player.prevPosZ, player.posZ, partialTicks) - RenderManager.viewerPosZ);
                            halfWidth2 = player.width / 2.0 + 0.1;
                            bb = new AxisAlignedBB(posX - halfWidth2, posY + 0.1, posZ - halfWidth2, posX + halfWidth2, posY + player.height + 0.1, posZ + halfWidth2);
                            vectors = new double[][] { { bb.minX, bb.minY, bb.minZ }, { bb.minX, bb.maxY, bb.minZ }, { bb.minX, bb.maxY, bb.maxZ }, { bb.minX, bb.minY, bb.maxZ }, { bb.maxX, bb.minY, bb.minZ }, { bb.maxX, bb.maxY, bb.minZ }, { bb.maxX, bb.maxY, bb.maxZ }, { bb.maxX, bb.minY, bb.maxZ } };
                            position = new Vector4f(Float.MAX_VALUE, Float.MAX_VALUE, -1.0f, -1.0f);
                            for (length = array.length; j < length; ++j) {
                                vec = array[j];
                                projection = RenderUtils.project2D((float)vec[0], (float)vec[1], (float)vec[2], 2);
                                if (projection != null && projection.z >= 0.0f && projection.z < 1.0f) {
                                    position.x = Math.min(position.x, projection.x);
                                    position.y = Math.min(position.y, projection.y);
                                    position.z = Math.max(position.z, projection.x);
                                    position.w = Math.max(position.w, projection.y);
                                }
                            }
                            ESP.entityPosMap.put(player, new float[] { position.x, position.y, position.z, position.w });
                            GL11.glPopMatrix();
                        }
                    }
                }
            }
            if (this.skeleton.getValue()) {
                this.setupRender(true);
                GL11.glEnable(2903);
                GL11.glDisable(2848);
                ESP.playerRotationMap.keySet().removeIf(this::contain);
                players = ESP.playerRotationMap.keySet().toArray();
                for (i = 0, playersLength = players.length; i < playersLength; ++i) {
                    entityPlayer = (EntityPlayer)players[i];
                    entPos = ESP.playerRotationMap.get(entityPlayer);
                    if (entPos != null && entityPlayer.getEntityId() != -1488 && entityPlayer.isEntityAlive() && RenderUtils.isInViewFrustum(entityPlayer) && !entityPlayer.isDead && !entityPlayer.isPlayerSleeping() && !entityPlayer.isInvisible()) {
                        GL11.glPushMatrix();
                        pt2 = e.getPartialTicks();
                        x3 = (float)(RenderUtils.interpolate(entityPlayer.prevPosX, entityPlayer.posX, pt2) - RenderManager.renderPosX);
                        y3 = (float)(RenderUtils.interpolate(entityPlayer.prevPosY, entityPlayer.posY, pt2) - RenderManager.renderPosY);
                        z = (float)(RenderUtils.interpolate(entityPlayer.prevPosZ, entityPlayer.posZ, pt2) - RenderManager.renderPosZ);
                        GL11.glTranslated(x3, y3, z);
                        sneaking = entityPlayer.isSneaking();
                        xOff = RenderUtils.interpolate(entityPlayer.prevRenderYawOffset, entityPlayer.renderYawOffset, pt2);
                        yOff = (sneaking ? 0.6f : 0.75f);
                        GL11.glLineWidth(this.skeletonWidth.getValue().floatValue());
                        GL11.glRotatef(-xOff, 0.0f, 1.0f, 0.0f);
                        GL11.glTranslatef(0.0f, 0.0f, sneaking ? -0.235f : 0.0f);
                        RenderUtils.glColor(-1);
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
                        GL11.glRotatef(xOff - entityPlayer.rotationYawHead, 0.0f, 1.0f, 0.0f);
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
                        GL11.glTranslated(0.0, yOff, 0.0);
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
                this.setupRender(false);
            }
            return;
        });
        final ModelPlayer model;
        this.updateModelEventListener = (e -> {
            model = e.getModel();
            ESP.playerRotationMap.put(e.getPlayer(), new float[][] { { model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ }, { model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ }, { model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ }, { model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ }, { model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ } });
        });
    }
    
    public boolean isValid(final Entity entity) {
        return this.mc.theWorld.playerEntities.contains(entity) && (entity instanceof EntityOtherPlayerMP || (this.lp.getValue() && Utils.isInThirdPerson() && entity instanceof EntityPlayer)) && entity.isEntityAlive() && RenderUtils.isBBInFrustum(entity.getEntityBoundingBox());
    }
    
    public void setupRender(final boolean start) {
        if (start) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GL11.glEnable(2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GL11.glHint(3154, 4354);
        }
        else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable(2848);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        GlStateManager.depthMask(!start);
    }
    
    private boolean contain(final EntityPlayer player) {
        return !this.mc.theWorld.playerEntities.contains(player);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        ESP.playerRotationMap.clear();
        ESP.entityPosMap.clear();
    }
    
    static {
        ESP.playerRotationMap = new HashMap<EntityPlayer, float[][]>();
        ESP.entityPosMap = new HashMap<EntityPlayer, float[]>();
    }
    
    private enum BGMode
    {
        Rect, 
        Round;
    }
}

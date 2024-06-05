package io.github.nevalackin.radium.module.impl.render;

import io.github.nevalackin.radium.event.impl.render.Render2DEvent;
import io.github.nevalackin.radium.event.impl.render.Render3DEvent;
import io.github.nevalackin.radium.event.impl.render.RenderNameTagEvent;
import io.github.nevalackin.radium.gui.font.FontRenderer;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.utils.RotationUtils;
import io.github.nevalackin.radium.utils.StringUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.Colors;
import io.github.nevalackin.radium.utils.render.LockedResolution;
import io.github.nevalackin.radium.utils.render.OGLUtils;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.api.annotations.Priority;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(label = "ESP", category = ModuleCategory.RENDER)
public final class ESP extends Module {

    // TODO: Outline ESP
    private final Property<Boolean> outlineProperty = new Property<>(
            "Outline",
            false);
    private final Property<Boolean> arrowsProperty = new Property<>(
            "Arrows",
            true);
    private final Property<Integer> arrowsColorProperty = new Property<>(
            "Arrow Color",
            Colors.DEEP_PURPLE,
            arrowsProperty::getValue);
    private final DoubleProperty arrowsRadiusProperty = new DoubleProperty(
            "Arrow Radius",
            30,
            arrowsProperty::getValue,
            10,
            100,
            1);
    private final DoubleProperty arrowsSizeProperty = new DoubleProperty(
            "Arrow Size",
            6,
            arrowsProperty::getValue,
            3,
            30,
            1);
    private final EnumProperty<ArrowsShape> arrowShapeProperty = new EnumProperty<>(
            "Arrow Shape",
            ArrowsShape.ARROW,
            arrowsProperty::getValue);
    private final Property<Boolean> localPlayerProperty = new Property<>(
            "Local Player",
            true);
    private final Property<Boolean> tagsProperty = new Property<>(
            "Tags",
            true);
    private final Property<Boolean> tagsHealthProperty = new Property<>(
            "Tags Health",
            true,
            tagsProperty::getValue);
    private final Property<Boolean> esp2dProperty = new Property<>(
            "2D ESP",
            true);
    private final Property<Boolean> boxProperty = new Property<>(
            "Box",
            true,
            esp2dProperty::getValue);
    private final Property<Integer> boxColorProperty = new Property<>(
            "Box Color",
            Colors.LIGHT_BLUE,
            () -> boxProperty.isAvailable() && boxProperty.getValue());
    private final Property<Boolean> healthBarProperty = new Property<>(
            "Health Bar",
            true,
            esp2dProperty::getValue);
    private final Property<Boolean> armorBarProperty = new Property<>(
            "Armor Bar",
            true,
            esp2dProperty::getValue);
    private final EnumProperty<HealthBarColor> healthBarColorModeProperty = new EnumProperty<>(
            "Health Bar Color Mode",
            HealthBarColor.SOLID,
            () -> healthBarProperty.isAvailable() && healthBarProperty.getValue());
    private final Property<Integer> healthBarColorProperty = new Property<>("Health Bar Color", Colors.LIGHT_BLUE,
            () -> healthBarColorModeProperty.isAvailable() &&
                    healthBarColorModeProperty.isSelected(HealthBarColor.SOLID));
    private final Property<Integer> healthBarStartColorProperty = new Property<>("Health Bar Start Color", Colors.WHITE,
            () -> healthBarColorModeProperty.isAvailable() &&
                    healthBarColorModeProperty.isSelected(HealthBarColor.GRADIENT));
    private final Property<Integer> healthBarEndColorProperty = new Property<>("Health Bar End Color", Colors.LIGHT_BLUE,
            () -> healthBarColorModeProperty.isAvailable() &&
                    healthBarColorModeProperty.isSelected(HealthBarColor.GRADIENT));
    private final Property<Boolean> skeletonsProperty = new Property<>(
            "Skeletons",
            true);
    private final DoubleProperty skeletonWidthProperty = new DoubleProperty(
            "Skeleton Width",
            0.5,
            skeletonsProperty::getValue,
            0.5,
            5.0,
            0.5);
    private final Map<EntityPlayer, float[][]> playerRotationMap;
    private final Map<EntityPlayer, float[]> entityPosMap;

    public ESP() {
        playerRotationMap = new HashMap<>();
        entityPosMap = new HashMap<>();

        toggle();
    }

    public static boolean shouldDrawOutlineESP() {
        return getInstance().isEnabled() && getInstance().outlineProperty.getValue();
    }

    public static boolean shouldDrawSkeletons() {
        return getInstance().isEnabled() && getInstance().skeletonsProperty.getValue();
    }

    public static void addEntity(EntityPlayer e,
                                 ModelPlayer model) {
        if (e instanceof EntityOtherPlayerMP)
            getInstance().playerRotationMap.put(e, new float[][]{
                    {model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ},
                    {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ},
                    {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ},
                    {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ},
                    {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}
            });
    }

    public static boolean isValid(Entity entity) {
        return Wrapper.getLoadedPlayers().contains(entity) &&
                (entity instanceof EntityOtherPlayerMP ||
                (getInstance().localPlayerProperty.getValue() &&
                        Wrapper.isInThirdPerson() &&
                        entity instanceof EntityPlayer)) &&
                entity.isEntityAlive() &&
                !entity.isInvisible() &&
                RenderingUtils.isBBInFrustum(entity.getEntityBoundingBox());
    }

    public static ESP getInstance() {
        return ModuleManager.getInstance(ESP.class);
    }

    @Listener
    public void onRenderNameTagEvent(RenderNameTagEvent event) {
        if (tagsProperty.getValue() &&
                entityPosMap.containsKey(event.getEntityLivingBase()))
            event.setCancelled();
    }

    @Listener(Priority.LOW)
    public void onRender2DEvent(Render2DEvent event) {
        LockedResolution lr = event.getResolution();
        float middleX = lr.getWidth() / 2.0F;
        float middleY = lr.getHeight() / 2.0F;
        float pt = event.getPartialTicks();
        OGLUtils.startBlending();
        for (EntityPlayer player : entityPosMap.keySet()) {
            if (arrowsProperty.getValue() && player instanceof EntityOtherPlayerMP) {
                glPushMatrix();
                float arrowSize = arrowsSizeProperty.getValue().floatValue();
                float alpha = Math.max(1.0F - (Wrapper.getPlayer().getDistanceToEntity(player) / 30.0F), 0.3F);
                int color = arrowsColorProperty.getValue();
                glTranslatef(middleX + 0.5F, middleY, 1.0F);
                float yaw = RenderingUtils.interpolate(
                        RotationUtils.getYawToEntity(player, true),
                        RotationUtils.getYawToEntity(player, false),
                        pt) -
                        RenderingUtils.interpolate(
                                Wrapper.getPlayer().prevRotationYaw,
                                Wrapper.getPlayer().rotationYaw,
                                pt);
                glRotatef(yaw, 0, 0, 1);
                glTranslatef(0.0F, -arrowsRadiusProperty.getValue().floatValue() - (arrowsSizeProperty.getValue().floatValue()), 0.0F);
                glDisable(GL_TEXTURE_2D);
                // TODO: Investigate GL_TRIANGLE_STRIP vs GL_TRIANGLES
                glBegin(arrowShapeProperty.getValue() == ArrowsShape.ARROW ? GL_POLYGON : GL_TRIANGLE_STRIP);
                glColor4ub(
                        (byte) (color >> 16 & 255),
                        (byte) (color >> 8 & 255),
                        (byte) (color & 255),
                        (byte) (alpha * 255));
                glVertex2f(0, 0);
                float offset;
                switch (arrowShapeProperty.getValue()) {
                    case ARROW:
                        offset = (int) (arrowSize / 3.0F);
                        glVertex2f(-arrowSize + offset, arrowSize);
                        glVertex2f(0, arrowSize - offset);
                        glVertex2f(arrowSize - offset, arrowSize);
                        break;
                    case EQUILATERAL:
                        offset = (int) (arrowSize / 3.0F);
                        glVertex2f(-arrowSize + offset, arrowSize);
                        glVertex2f(arrowSize - offset, arrowSize);
                        break;
                    case ISOSCELES:
                        glVertex2f(-arrowSize, arrowSize);
                        glVertex2f(arrowSize, arrowSize);
                        break;
                }
                glEnd();
                glEnable(GL_TEXTURE_2D);
                glPopMatrix();
            }

            if (player.getDistanceToEntity(Wrapper.getPlayer()) < 1.0F && !Wrapper.isInThirdPerson())
                continue;

            glPushMatrix();
            float[] positions = entityPosMap.get(player);
            float x = positions[0];
            float y = positions[1];
            float x2 = positions[2];
            float y2 = positions[3];

            if (healthBarProperty.getValue()) {
                Gui.drawRect(x - 3.0, y, x - 1, y2, 0x78000000);

                float health = player.getHealth();
                float maxHealth = player.getMaxHealth();
                float healthPercentage = health / maxHealth;

                float heightDif = y - y2;
                float healthBarHeight = heightDif * healthPercentage;

                switch (healthBarColorModeProperty.getValue()) {
                    case SOLID:
                        Gui.drawRect(x - 2.5F, y2 + 0.5F + healthBarHeight, x - 1.5F,
                                y2 - 0.5F, healthBarColorProperty.getValue());
                        break;
                    case HEALTH:
                        Gui.drawRect(x - 2.5F, y2 + 0.5F + healthBarHeight, x - 1.5F,
                                y2 - 0.5F, RenderingUtils.getColorFromPercentage(healthPercentage));
                        break;
                    case GRADIENT:
                        boolean needScissor = health < maxHealth;
                        if (needScissor)
                            OGLUtils.startScissorBox(lr, (int) x - 3, (int) (y2 + healthBarHeight),
                                    2, (int) -healthBarHeight + 1);
                        Gui.drawGradientRect(x - 2.5F, y + 0.5F, x - 1.5F,
                                y2 - 0.5F, healthBarStartColorProperty.getValue(),
                                healthBarEndColorProperty.getValue());
                        if (needScissor)
                            OGLUtils.endScissorBox();
                        break;
                }
            }

            if (armorBarProperty.getValue()) {
                float armorPercentage = player.getTotalArmorValue() / 20.0F;
                float armorBarWidth = (x2 - x) * armorPercentage;

                Gui.drawRect(x, y2 + 1, x2, y2 + 3, 0x78000000);

                if (armorPercentage > 0)
                    Gui.drawRect(x + 0.5F, y2 + 1.5F, x + armorBarWidth - 1F, y2 + 2.5F, 0xFFFFFFFF);
            }

            if (tagsProperty.getValue()) {
                FontRenderer fontRenderer = Wrapper.getFontRenderer();

                final String name = player.getGameProfile().getName();
                float halfWidth = fontRenderer.getWidth(name) / 2;
                final float xDif = x2 - x;
                final float middle = x + (xDif / 2);
                final float textHeight = fontRenderer.getHeight(name);
                final float renderY = y - textHeight - 2;
                final boolean teamMate = StringUtils.isTeamMate(player);
                final String text = (teamMate ? "\247A" : "\247C") + name;

                Gui.drawRect(middle - halfWidth, renderY - 1,
                        middle + halfWidth, renderY + textHeight + 1, 0x78000000);
                fontRenderer.drawStringWithShadow(text, middle - halfWidth, renderY, -1);

                if (tagsHealthProperty.getValue()) {
                    final float health = player.getHealth();
                    final String healthString = String.valueOf((int) health);
                    final float healthWidth = fontRenderer.getWidth(healthString);
                    final float left = middle + halfWidth + 2;

                    Gui.drawRect(left, renderY - 1, left + 0.5 + healthWidth,
                            renderY + textHeight + 1, 0x78000000);
                    fontRenderer.drawStringWithShadow(healthString, left,
                            renderY, RenderingUtils.getColorFromPercentage(health / player.getMaxHealth()));
                }
            }

            if (boxProperty.getValue()) {
                glDisable(GL_TEXTURE_2D);
                RenderingUtils.color(0xB4000000);
                glLineWidth(1.0F);
                glBegin(GL_LINE_LOOP);
                glVertex2f(x, y);
                glVertex2f(x, y2);
                glVertex2f(x2, y2);
                glVertex2f(x2, y);
                glEnd();
                RenderingUtils.color(boxColorProperty.getValue());
                glTranslated(0.5F, 0.5F, 0.0F);
                glBegin(GL_LINE_LOOP);
                glVertex2f(x, y);
                glVertex2f(x, y2 - 1.0F);
                glVertex2f(x2 - 1.0F, y2 - 1.0F);
                glVertex2f(x2 - 1.0F, y);
                glEnd();
                RenderingUtils.color(0xB4000000);
                glTranslated(0.5F, 0.5F, 0.0F);
                glBegin(GL_LINE_LOOP);
                glVertex2f(x, y);
                glVertex2f(x, y2 - 2.0F);
                glVertex2f(x2 - 2.0F, y2 - 2.0F);
                glVertex2f(x2 - 2.0F, y);
                glEnd();
                glEnable(GL_TEXTURE_2D);
            }
            glPopMatrix();
        }
        OGLUtils.endBlending();
    }

    @Override
    public void onDisable() {
        entityPosMap.clear();
        playerRotationMap.clear();
    }

    @Listener
    public void onRender3DEvent(Render3DEvent e) {
        if (!entityPosMap.isEmpty())
            entityPosMap.clear();

        if (esp2dProperty.getValue() || arrowsProperty.getValue() || tagsProperty.getValue()) {
            float partialTicks = e.getPartialTicks();
            for (EntityPlayer player : Wrapper.getLoadedPlayers()) {
                if (!(player instanceof EntityOtherPlayerMP || (getInstance().localPlayerProperty.getValue() && Wrapper.isInThirdPerson())) ||
                        !player.isEntityAlive() ||
                        player.isInvisible()) continue;
                GL11.glPushMatrix();
                float posX = (float) (RenderingUtils.interpolate(player.prevPosX, player.posX, partialTicks) - RenderManager.viewerPosX);
                float posY = (float) (RenderingUtils.interpolate(player.prevPosY, player.posY, partialTicks) - RenderManager.viewerPosY);
                float posZ = (float) (RenderingUtils.interpolate(player.prevPosZ, player.posZ, partialTicks) - RenderManager.viewerPosZ);

                double halfWidth = player.width / 2.0D + 0.1D;
                AxisAlignedBB bb = new AxisAlignedBB(posX - halfWidth, posY + 0.1D, posZ - halfWidth,
                        posX + halfWidth, posY + player.height + 0.1D, posZ + halfWidth);

                double[][] vectors = {{bb.minX, bb.minY, bb.minZ},
                        {bb.minX, bb.maxY, bb.minZ},
                        {bb.minX, bb.maxY, bb.maxZ},
                        {bb.minX, bb.minY, bb.maxZ},
                        {bb.maxX, bb.minY, bb.minZ},
                        {bb.maxX, bb.maxY, bb.minZ},
                        {bb.maxX, bb.maxY, bb.maxZ},
                        {bb.maxX, bb.minY, bb.maxZ}};

                Vector3f projection;
                Vector4f position = new Vector4f(Float.MAX_VALUE, Float.MAX_VALUE, -1.0F, -1.0F);

                for (double[] vec : vectors) {
                    projection = OGLUtils.project2D((float) vec[0], (float) vec[1], (float) vec[2], 2);
                    if (projection != null && projection.z >= 0.0F && projection.z < 1.0F) {
                        position.x = Math.min(position.x, projection.x);
                        position.y = Math.min(position.y, projection.y);
                        position.z = Math.max(position.z, projection.x);
                        position.w = Math.max(position.w, projection.y);
                    }
                }

                entityPosMap.put(player, new float[]{position.x, position.y, position.z, position.w});

                GL11.glPopMatrix();
            }
        }

        if (skeletonsProperty.getValue()) {
            glPushMatrix();
            glLineWidth(skeletonWidthProperty.getValue().floatValue());
            glEnable(GL_BLEND);
            glEnable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_TEXTURE_2D);
            glDepthMask(false);

            for (EntityPlayer player : playerRotationMap.keySet())
                drawSkeleton(e, player);

            playerRotationMap.clear();

            glDepthMask(true);
            glDisable(GL_BLEND);
            glDisable(GL_LINE_SMOOTH);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_DEPTH_TEST);
            glPopMatrix();
        }
    }

    private void drawSkeleton(Render3DEvent event,
                              EntityPlayer player) {
        float[][] entPos;

        if ((entPos = playerRotationMap.get(player)) != null) {
            // TODO: Investigate GL_LINE_STRIP vs GL_LINES
            glPushMatrix();
            float pt = event.getPartialTicks();
            float x = (float) (RenderingUtils.interpolate(player.prevPosX, player.posX, pt) - RenderManager.renderPosX);
            float y = (float) (RenderingUtils.interpolate(player.prevPosY, player.posY, pt) - RenderManager.renderPosY);
            float z = (float) (RenderingUtils.interpolate(player.prevPosZ, player.posZ, pt) - RenderManager.renderPosZ);
            glTranslated(x, y, z);
            boolean sneaking = player.isSneaking();
            float xOff = RenderingUtils.interpolate(player.prevRenderYawOffset, player.renderYawOffset, pt);
            float yOff = sneaking ? 0.6F : 0.75F;
            glRotatef(-xOff, 0.0F, 1.0F, 0.0F);
            glTranslatef(0.0F, 0.0F, sneaking ? -0.235F : 0.0F);

            // Right leg
            glPushMatrix();
            glTranslatef(-0.125F, yOff, 0.0F);
            if (entPos[3][0] != 0.0F)
                glRotatef(entPos[3][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
            if (entPos[3][1] != 0.0F)
                glRotatef(entPos[3][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
            if (entPos[3][2] != 0.0F)
                glRotatef(entPos[3][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
            glBegin(GL_LINE_STRIP);
            glVertex3i(0, 0, 0);
            glVertex3f(0.0F, -yOff, 0.0F);
            glEnd();
            glPopMatrix();

            // Left leg
            glPushMatrix();
            glTranslatef(0.125F, yOff, 0.0F);
            if (entPos[4][0] != 0.0F)
                glRotatef(entPos[4][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
            if (entPos[4][1] != 0.0F)
                glRotatef(entPos[4][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
            if (entPos[4][2] != 0.0F)
                glRotatef(entPos[4][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
            glBegin(GL_LINE_STRIP);
            glVertex3i(0, 0, 0);
            glVertex3f(0.0F, -yOff, 0.0F);
            glEnd();
            glPopMatrix();

            glTranslatef(0.0F, 0.0F, sneaking ? 0.25F : 0.0F);
            glPushMatrix();
            glTranslatef(0.0F, sneaking ? -0.05F : 0.0F, sneaking ? -0.01725F : 0.0F);

            // Right arm
            glPushMatrix();
            glTranslatef(-0.375F, yOff + 0.55F, 0.0F);
            if (entPos[1][0] != 0.0F)
                glRotatef(entPos[1][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
            if (entPos[1][1] != 0.0F)
                glRotatef(entPos[1][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
            if (entPos[1][2] != 0.0F)
                glRotatef(-entPos[1][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
            glBegin(GL_LINE_STRIP);
            glVertex3i(0, 0, 0);
            glVertex3f(0.0F, -0.5F, 0.0F);
            glEnd();
            glPopMatrix();

            // Left arm
            glPushMatrix();
            glTranslatef(0.375F, yOff + 0.55F, 0.0F);
            if (entPos[2][0] != 0.0F)
                glRotatef(entPos[2][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
            if (entPos[2][1] != 0.0F)
                glRotatef(entPos[2][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
            if (entPos[2][2] != 0.0F)
                glRotatef(-entPos[2][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
            glBegin(GL_LINE_STRIP);
            glVertex3i(0, 0, 0);
            glVertex3f(0.0F, -0.5F, 0.0F);
            glEnd();
            glPopMatrix();

            glRotatef(xOff - player.rotationYawHead, 0.0F, 1.0F, 0.0F);

            // Head
            glPushMatrix();
            glTranslatef(0.0F, yOff + 0.55F, 0.0F);
            if (entPos[0][0] != 0.0F)
                glRotatef(entPos[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
            glBegin(GL_LINE_STRIP);
            glVertex3i(0, 0, 0);
            glVertex3f(0.0F, 0.3F, 0.0F);
            glEnd();
            glPopMatrix();

            glPopMatrix();

            glRotatef(sneaking ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
            glTranslatef(0.0F, sneaking ? -0.16175F : 0.0F, sneaking ? -0.48025F : 0.0F);

            // Pelvis
            glPushMatrix();
            glTranslated(0.0F, yOff, 0.0F);
            glBegin(GL_LINE_STRIP);
            glVertex3f(-0.125F, 0.0F, 0.0F);
            glVertex3f(0.125F, 0.0F, 0.0F);
            glEnd();
            glPopMatrix();

            // Body
            glPushMatrix();
            glTranslatef(0.0F, yOff, 0.0F);
            glBegin(GL_LINE_STRIP);
            glVertex3i(0, 0, 0);
            glVertex3f(0.0F, 0.55F, 0.0F);
            glEnd();
            glPopMatrix();

            // Chest
            glPushMatrix();
            glTranslatef(0.0F, yOff + 0.55F, 0.0F);
            glBegin(GL_LINE_STRIP);
            glVertex3f(-0.375F, 0.0F, 0.0F);
            glVertex3f(0.375F, 0.0F, 0.0F);
            glEnd();
            glPopMatrix();

            glPopMatrix();
        }
    }

    private enum ArrowsShape {
        EQUILATERAL, ARROW, ISOSCELES
    }

    private enum HealthBarColor {
        SOLID,
        HEALTH,
        GRADIENT
    }
}

package dev.star.module.impl.render;

import dev.star.event.impl.player.UpdateModelEvent;
import dev.star.event.impl.render.*;
import dev.star.module.impl.display.HUDMod;
import dev.star.utils.tuples.Pair;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.ParentAttribute;
import dev.star.module.settings.impl.*;
import dev.star.utils.font.AbstractFontRenderer;
import dev.star.utils.misc.MathUtils;
import dev.star.utils.render.*;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import static org.lwjgl.opengl.GL11.*;

public class ESP2D extends Module {

    private final MultipleBoolSetting validEntities = new MultipleBoolSetting("Valid Entities",
            new BooleanSetting("Players", true),
            new BooleanSetting("Animals", true),
            new BooleanSetting("Mobs", true));

    private final BooleanSetting mcfont = new BooleanSetting("Minecraft Font", true);
    public final BooleanSetting boxEsp = new BooleanSetting("Box", true);
    private final ModeSetting boxColorMode = new ModeSetting("Box Mode", "Sync", "Sync", "Health", "Gradient");
    public final BooleanSetting Skeleton = new BooleanSetting("Skeleton", true);

    private final BooleanSetting itemHeld = new BooleanSetting("Item Held Text", true);
    private final BooleanSetting healthBar = new BooleanSetting("Health Bar", true);
    private final ModeSetting healthBarMode = new ModeSetting("Health Bar Mode", "Color", "Health", "Gradient", "Color");
    private final BooleanSetting healthBarText = new BooleanSetting("Health Bar Text", true);


    private final ModeSetting textmode = new ModeSetting("Text Mode", "Hearts", "Hearts", "HP", "Percentage");

    public ESP2D() {
        super("2D ESP", Category.RENDER, "2D Rendered ESP");
        boxColorMode.addParent(boxEsp, ParentAttribute.BOOLEAN_CONDITION);
        healthBarMode.addParent(healthBar, ParentAttribute.BOOLEAN_CONDITION);
        healthBarText.addParent(healthBar, ParentAttribute.BOOLEAN_CONDITION);
        textmode.addParent(healthBar, ParentAttribute.BOOLEAN_CONDITION);


        addSettings(validEntities, mcfont, boxEsp, boxColorMode,  itemHeld, healthBar, healthBarMode, healthBarText, textmode, Skeleton);
    }


    private final Map<Entity, Vector4f> entityPosition = new HashMap<>();

    private final Map<EntityPlayer, float[][]> playerRotationMap = new WeakHashMap();


    private boolean contain(EntityPlayer var0) {
        return !mc.theWorld.getPlayerEntities().contains(var0);
    }

    @Override
    public void onUpdateModelEvent(UpdateModelEvent event) {
        if (Skeleton.isEnabled()) {
            ModelPlayer model = event.getModelPlayer();
            this.playerRotationMap.put((EntityPlayer) event.getEntity(), new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
        }
    }

    @Override
    public void onRender3DEvent(Render3DEvent event) {
        entityPosition.clear();
        for (final Entity entity : mc.theWorld.loadedEntityList) {
            if (shouldRender(entity) && ESPUtil.isInView(entity)) {
                entityPosition.put(entity, ESPUtil.getEntityPositionsOn2D(entity));
            }
        }


        if (Skeleton.isEnabled()) {


            this.setupRender(true);
            GL11.glEnable(2903);
            GL11.glDisable(2848);
            this.playerRotationMap.keySet().removeIf(this::contain);
            Map<EntityPlayer, float[][]> playerRotationMap = this.playerRotationMap;
            List worldPlayers = mc.theWorld.getPlayerEntities();
            Object[] players = playerRotationMap.keySet().toArray();
            int playersLength = players.length;


            for (Object o : players) {
                EntityPlayer player = (EntityPlayer) o;
                float[][] entPos = playerRotationMap.get(player);
                if (entPos == null || player.getEntityId() == -1488 || !player.isEntityAlive() || !ESPUtil.isInView(player) || player.isDead || player == mc.thePlayer || player.isPlayerSleeping() || player.isInvisible())
                    continue;
                GL11.glPushMatrix();
                float[][] modelRotations = playerRotationMap.get(player);
                GL11.glLineWidth(1.2f);

                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

                double x = interpolate(player.posX, player.lastTickPosX, event.getTicks()) - mc.getRenderManager().renderPosX;
                double y = interpolate(player.posY, player.lastTickPosY, event.getTicks()) - mc.getRenderManager().renderPosY;
                double z = interpolate(player.posZ, player.lastTickPosZ, event.getTicks()) - mc.getRenderManager().renderPosZ;
                GL11.glTranslated(x, y, z);
                float bodyYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * mc.timer.renderPartialTicks;
                GL11.glRotatef(-bodyYawOffset, 0.0f, 1.0f, 0.0f);
                GL11.glTranslated(0.0, 0.0, player.isSneaking() ? -0.235 : 0.0);
                float legHeight = player.isSneaking() ? 0.6f : 0.75f;
                float rad = 57.29578f;
                GL11.glPushMatrix();
                GL11.glTranslated(-0.125, legHeight, 0.0);

                if (modelRotations[3][0] != 0.0f) {
                    GL11.glRotatef(modelRotations[3][0] * 57.29578f, 1.0f, 0.0f, 0.0f);
                }

                if (modelRotations[3][1] != 0.0f) {
                    GL11.glRotatef(modelRotations[3][1] * 57.29578f, 0.0f, 1.0f, 0.0f);
                }

                if (modelRotations[3][2] != 0.0f) {
                    GL11.glRotatef(modelRotations[3][2] * 57.29578f, 0.0f, 0.0f, 1.0f);
                }

                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, -legHeight, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.125, legHeight, 0.0);

                if (modelRotations[4][0] != 0.0f) {
                    GL11.glRotatef(modelRotations[4][0] * 57.29578f, 1.0f, 0.0f, 0.0f);
                }

                if (modelRotations[4][1] != 0.0f) {
                    GL11.glRotatef(modelRotations[4][1] * 57.29578f, 0.0f, 1.0f, 0.0f);
                }

                if (modelRotations[4][2] != 0.0f) {
                    GL11.glRotatef(modelRotations[4][2] * 57.29578f, 0.0f, 0.0f, 1.0f);
                }

                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, -legHeight, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glTranslated(0.0, 0.0, player.isSneaking() ? 0.25 : 0.0);
                GL11.glPushMatrix();
                GL11.glTranslated(0.0, player.isSneaking() ? -0.05 : 0.0, player.isSneaking() ? -0.01725 : 0.0);
                GL11.glPushMatrix();
                GL11.glTranslated(-0.375, (double) legHeight + 0.55, 0.0);
                if (modelRotations[1][0] != 0.0f) {
                    GL11.glRotatef(modelRotations[1][0] * 57.29578f, 1.0f, 0.0f, 0.0f);
                }
                if (modelRotations[1][1] != 0.0f) {
                    GL11.glRotatef(modelRotations[1][1] * 57.29578f, 0.0f, 1.0f, 0.0f);
                }
                if (modelRotations[1][2] != 0.0f) {
                    GL11.glRotatef(-modelRotations[1][2] * 57.29578f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, -0.5, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.375, (double) legHeight + 0.55, 0.0);

                if (modelRotations[2][0] != 0.0f) {
                    GL11.glRotatef(modelRotations[2][0] * 57.29578f, 1.0f, 0.0f, 0.0f);
                }

                if (modelRotations[2][1] != 0.0f) {
                    GL11.glRotatef(modelRotations[2][1] * 57.29578f, 0.0f, 1.0f, 0.0f);
                }

                if (modelRotations[2][2] != 0.0f) {
                    GL11.glRotatef(-modelRotations[2][2] * 57.29578f, 0.0f, 0.0f, 1.0f);
                }

                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, -0.5, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glRotatef(bodyYawOffset - player.rotationYawHead, 0.0f, 1.0f, 0.0f);
                GL11.glPushMatrix();
                GL11.glTranslated(0.0, (double) legHeight + 0.55, 0.0);

                if (modelRotations[0][0] != 0.0f) {
                    GL11.glRotatef(modelRotations[0][0] * 57.29578f, 1.0f, 0.0f, 0.0f);
                }

                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, 0.3, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glRotatef(player.isSneaking() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
                GL11.glTranslated(0.0, player.isSneaking() ? -0.16175 : 0.0, player.isSneaking() ? -0.48025 : 0.0);
                GL11.glPushMatrix();
                GL11.glTranslated(0.0, legHeight, 0.0);
                GL11.glBegin(3);
                GL11.glVertex3d(-0.125, 0.0, 0.0);
                GL11.glVertex3d(0.125, 0.0, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.0, legHeight, 0.0);
                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, 0.55, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.0, (double) legHeight + 0.55, 0.0);
                GL11.glBegin(3);
                GL11.glVertex3d(-0.375, 0.0, 0.0);
                GL11.glVertex3d(0.375, 0.0, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }

            this.setupRender(false);
        }
    }

    private void setupRender(boolean start) {
        if (start) {
            GL11.glDisable(2848);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
        } else {
            GL11.glEnable(3553);
            GL11.glEnable(2929);
        }

        GL11.glDepthMask((!start ? 1 : 0) != 0);
    }


    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    private final NumberFormat df = new DecimalFormat("0.#");
    private final Color backgroundColor = new Color(10, 10, 10, 130);

    private Color firstColor = Color.BLACK, secondColor = Color.BLACK, thirdColor = Color.BLACK, fourthColor = Color.BLACK;


    @Override
    public void onRender2DEvent(Render2DEvent e) {


            if (HUDMod.isRainbowTheme()) {
                firstColor = HUDMod.color1.getRainbow().getColor(0);
                secondColor = HUDMod.color1.getRainbow().getColor(90);
                thirdColor = HUDMod.color1.getRainbow().getColor(180);
                fourthColor = HUDMod.color1.getRainbow().getColor(270);
            } else {
                gradientColorWheel(HUDMod.getClientColors());
            }

        for (Entity entity : entityPosition.keySet()) {
            Vector4f pos = entityPosition.get(entity);
            float x = pos.getX(),
                    y = pos.getY(),
                    right = pos.getZ(),
                    bottom = pos.getW();

            if (entity instanceof EntityLivingBase) {
                AbstractFontRenderer font = BoldFont20;
                if (mcfont.isEnabled()) {
                    font = mc.fontRendererObj;
                }
                EntityLivingBase renderingEntity = (EntityLivingBase) entity;

                if (itemHeld.isEnabled()) {
                    if (renderingEntity.getHeldItem() != null) {

                        float fontScale = .5f;
                        float middle = x + ((right - x) / 2);
                        float textWidth;
                        String text = renderingEntity.getHeldItem().getDisplayName();
                        textWidth = font.getStringWidth(text);
                        middle -= (textWidth * fontScale) / 2f;

                        glPushMatrix();
                        glTranslated(middle, (bottom + 4), 0);
                        glScaled(fontScale, fontScale, 1);
                        glTranslated(-middle, -(bottom + 4), 0);
                        GlStateManager.bindTexture(0);
                        RenderUtil.resetColor();
                        Gui.drawRect2(middle - 3, bottom + 1, font.getStringWidth(text) + 6, font.getHeight() + 5, backgroundColor.getRGB());
                        if (mcfont.isEnabled()) {
                            mc.fontRendererObj.drawStringWithShadow(text, middle, bottom + 4, -1);
                        } else {
                            BoldFont20.drawSmoothStringWithShadow(text, middle, bottom + 4, -1);
                        }
                        glPopMatrix();
                    }
                }


                if (healthBar.isEnabled()) {
                    float healthValue = renderingEntity.getHealth() / renderingEntity.getMaxHealth();
                    Color healthColor = healthValue > .75 ? new Color(66, 246, 123) : healthValue > .5 ? new Color(228, 255, 105) : healthValue > .35 ? new Color(236, 100, 64) : new Color(255, 65, 68);

                    float height = (bottom - y) + 1;
                    Gui.drawRect2(x - 3.5f, y - .5f, 2, height + 1, new Color(0, 0, 0, 180).getRGB());
                    if (healthBarMode.is("Color")) {
                        GradientUtil.drawGradientTB(x - 3, y, 1, height, .3f, firstColor, secondColor);
                        GradientUtil.drawGradientTB(x - 3, y + (height - (height * healthValue)), 1, height * healthValue, 1, firstColor, secondColor);
                    } else if (healthBarMode.is("Gradient")) {
                        Gui.drawRect2(x - 3, y, 1, height, ColorUtil.applyOpacity(healthColor, .3f).getRGB());
                       GradientUtil.drawGradientTB(x - 3, y + (height - (height * healthValue)), 1, height * healthValue, 1, Color.green, Color.red);

                    } else if (healthBarMode.is("Health")) {
                        Gui.drawRect2(x - 3, y, 1, height, ColorUtil.applyOpacity(Color.black, .3f).getRGB());
                        Gui.drawRect2(x - 3, y + (height - (height * healthValue)), 1, height * healthValue, healthColor.getRGB());

                    }

                    if (healthBarText.isEnabled()) {
                        healthValue *= 100;
                        String health = String.valueOf(MathUtils.round(healthValue, 1)).substring(0, healthValue == 100 ? 3 : 2);
                        String text = "";
                        switch (textmode.getMode()) {
                            case "Hearts":
                                text = String.format("%.1f", renderingEntity.getHealth() / 2) + "" + EnumChatFormatting.RED + "❤";
                                break;
                            case "HP":
                                text = (int) renderingEntity.getHealth() + " HP";
                                break;
                            case "Percentage":
                                text = health + "%";
                        }
                        double fontScale = .5;
                        float textX = x - ((font.getStringWidth(text) / 2f) + 2);
                        float fontHeight = mcfont.isEnabled() ? (float) (mc.fontRendererObj.FONT_HEIGHT * fontScale) : (float) (BoldFont20.getHeight() * fontScale);
                        float newHeight = height - fontHeight;
                        float textY = y + (newHeight - (newHeight * (healthValue / 100)));

                        glPushMatrix();
                        glTranslated(textX - 5, textY, 1);
                        glScaled(fontScale, fontScale, 1);
                        glTranslated(-(textX - 5), -textY, 1);
                        if (mcfont.isEnabled()) {
                            mc.fontRendererObj.drawStringWithShadow(text, textX, textY, -1);
                        } else {
                            BoldFont20.drawSmoothStringWithShadow(text, textX, textY, -1);
                        }
                        glPopMatrix();

                    }




                }


                if (boxEsp.isEnabled()) {

                    RenderUtil.rectangle(pos.x, pos.y, pos.z - pos.x, 1.5, Color.BLACK); // Top
                    RenderUtil.rectangle(pos.x, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK); // Left
                    RenderUtil.rectangle(pos.z, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK); // Right
                    RenderUtil.rectangle(pos.x, pos.w, pos.z - pos.x, 1.5, Color.BLACK); // Bottom

                    if (boxColorMode.is("Sync")) {

                        RenderUtil.horizontalGradient(pos.x + 0.5, pos.y + 0.5, pos.z - pos.x, 0.5, // Top
                                firstColor, secondColor);
                        RenderUtil.verticalGradient(pos.x + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Left
                                firstColor, secondColor);
                        RenderUtil.verticalGradient(pos.z + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Right
                                secondColor, firstColor);
                        RenderUtil.horizontalGradient(pos.x + 0.5, pos.w + 0.5, pos.z - pos.x, 0.5, // Bottom
                                secondColor, firstColor);
                    } else if (boxColorMode.is("Health")) {
                        float healthValue = renderingEntity.getHealth() / renderingEntity.getMaxHealth();
                        Color healthColor = healthValue > .75 ? new Color(66, 246, 123) : healthValue > .5 ? new Color(228, 255, 105) : healthValue > .35 ? new Color(236, 100, 64) : new Color(255, 65, 68);

                        RenderUtil.horizontalGradient(pos.x + 0.5, pos.y + 0.5, pos.z - pos.x, 0.5, // Top
                                healthColor, healthColor);
                        RenderUtil.verticalGradient(pos.x + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Left
                                healthColor, healthColor);
                        RenderUtil.verticalGradient(pos.z + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Right
                                healthColor, healthColor);
                        RenderUtil.horizontalGradient(pos.x + 0.5, pos.w + 0.5, pos.z - pos.x, 0.5, // Bottom
                                healthColor, healthColor);
                    } else if (boxColorMode.is("Gradient")) {
                        RenderUtil.horizontalGradient(pos.x + 0.5, pos.y + 0.5, pos.z - pos.x, 0.5, // Top
                                Color.green, Color.green);
                        RenderUtil.verticalGradient(pos.x + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Left
                                Color.green, Color.red);
                        RenderUtil.verticalGradient(pos.z + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Right
                                Color.green, Color.red);
                        RenderUtil.horizontalGradient(pos.x + 0.5, pos.w + 0.5, pos.z - pos.x, 0.5, // Bottom
                                Color.red, Color.red);
                    }
                    else if (boxColorMode.is("Custom")) {
                        RenderUtil.horizontalGradient(pos.x + 0.5, pos.y + 0.5, pos.z - pos.x, 0.5, // Top
                                firstColor, secondColor);
                        RenderUtil.verticalGradient(pos.x + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Left
                                firstColor, secondColor);
                        RenderUtil.verticalGradient(pos.z + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Right
                                secondColor, firstColor);
                        RenderUtil.horizontalGradient(pos.x + 0.5, pos.w + 0.5, pos.z - pos.x, 0.5, // Bottom
                                secondColor, firstColor);
                    }
                }
            }
        }

    }

    private void gradientColorWheel(Pair<Color, Color> colors) {
        firstColor = ColorUtil.interpolateColorsBackAndForth(15, 0, colors.getFirst(), colors.getSecond(), false);
        secondColor = ColorUtil.interpolateColorsBackAndForth(15, 90, colors.getFirst(), colors.getSecond(), false);
        thirdColor = ColorUtil.interpolateColorsBackAndForth(15, 180, colors.getFirst(), colors.getSecond(), false);
        fourthColor = ColorUtil.interpolateColorsBackAndForth(15, 270, colors.getFirst(), colors.getSecond(), false);
    }

    private boolean shouldRender(Entity entity) {
        if (entity.isDead || entity.isInvisible()) {
            return false;
        }
        if (validEntities.getSetting("Players").isEnabled() && entity instanceof EntityPlayer) {
            if (entity == mc.thePlayer) {
                return mc.gameSettings.thirdPersonView != 0;
            }
            return !entity.getDisplayName().getUnformattedText().contains("[NPC");
        }
        if (validEntities.getSetting("Animals").isEnabled() && entity instanceof EntityAnimal) {
            return true;
        }

        return validEntities.getSetting("mobs").isEnabled() && entity instanceof EntityMob;
    }


}

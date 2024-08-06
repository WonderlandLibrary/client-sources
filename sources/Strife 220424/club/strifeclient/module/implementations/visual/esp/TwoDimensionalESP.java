package club.strifeclient.module.implementations.visual.esp;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.Client;
import club.strifeclient.event.implementations.rendering.Render2DEvent;
import club.strifeclient.event.implementations.rendering.Render3DEvent;
import club.strifeclient.module.implementations.visual.ESP;
import club.strifeclient.setting.Mode;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.*;
import club.strifeclient.util.rendering.ColorUtil;
import club.strifeclient.util.rendering.DrawUtil;
import club.strifeclient.util.rendering.InterpolateUtil;
import club.strifeclient.util.rendering.RenderUtil;
import club.strifeclient.util.world.WorldUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public final class TwoDimensionalESP extends Mode<ESP.ESPMode> {

    private final ModeSetting<ColorUtil.ColorMode> colorModeSetting = new ModeSetting<>("Color Mode", ColorUtil.ColorMode.STATIC);
    private final ColorSetting colorSetting = new ColorSetting("Color", new Color(209, 50, 50),
            () -> colorModeSetting.getValue() == ColorUtil.ColorMode.STATIC || colorModeSetting.getValue() == ColorUtil.ColorMode.SWITCH);
    private final ColorSetting secondColorSetting = new ColorSetting("Second Color", new Color(29, 205, 200),
            () -> colorModeSetting.getValue() == ColorUtil.ColorMode.SWITCH);
    private final DoubleSetting colorSpeedSetting = new DoubleSetting("Color Speed", 8, 0.5, 10,
            0.5, () -> colorModeSetting.getValue() != ColorUtil.ColorMode.STATIC);
    private final MultiSelectSetting<Element> elementsSetting = new MultiSelectSetting<>("Elements", Element.BOX);
    private final ModeSetting<BoxMode> boxModeSetting = new ModeSetting<BoxMode>("Box Mode", BoxMode.BOX, () -> elementsSetting.isSelected(Element.BOX));
    private final BooleanSetting oppositeSetting = new BooleanSetting("Opposite Corners", false, () -> boxModeSetting.getValue() == BoxMode.HALF_CORNERS);

    private final HashMap<EntityLivingBase, double[]> coordinates = new HashMap<>();
    private ESP esp;
    // Credit: The base ESP is from Moropheles Client (I developed it too <3 + I have permission <3)

    @EventHandler
    private final Listener<Render3DEvent> render3DEventListener = e -> {
        final List<EntityLivingBase> entities = WorldUtil.getLivingEntities(en -> WorldUtil.isValid((EntityLivingBase) en, esp.targetsSetting));
        if (esp.selfSetting.getValue() && mc.gameSettings.thirdPersonView != 0)
            entities.add(mc.thePlayer);
        for (final EntityLivingBase entity : entities) {
            RenderUtil.getFrustum().setPosition(mc.thePlayer.getPositionVector());
            if (!RenderUtil.getFrustum().isBoundingBoxInFrustum(entity.getEntityBoundingBox())) continue;
            double[] coords = new double[4];
            final AxisAlignedBB interpolatedBB = InterpolateUtil.interpolateRenderBB(entity);
            InterpolateUtil.convertCoords(coords, interpolatedBB, entity);
            coordinates.put(entity, coords);
        }
    };

    @EventHandler
    private final Listener<Render2DEvent> render2DEventListener = e -> {
        for (final EntityLivingBase entity : coordinates.keySet()) {
            final double[] coords = coordinates.get(entity);
            final Color boxColor = ColorUtil.getColor(colorModeSetting, colorSetting, secondColorSetting, colorSpeedSetting, 0);
            glEnable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
            double x = coords[0], y = coords[1], w = coords[2], h = coords[3];
            final double lineLength = (w - x) / 5;
            for (Element element : Element.values()) {
                if (elementsSetting.isSelected(element)) {
                    switch (element) {
                        case BOX: {
                            switch (boxModeSetting.getValue()) {
                                case BOX: {
                                    glLineWidth(3f);
                                    DrawUtil.drawRect(GL_LINE_LOOP, x, y, w, h, Color.BLACK);
                                    glLineWidth(2f);
                                    DrawUtil.drawRect(GL_LINE_LOOP, x, y, w, h, boxColor);
                                    break;
                                }
                                case FILL: {
                                    DrawUtil.drawRect(GL_QUADS, x, y, w, h, boxColor);
                                    break;
                                }
                                case CORNERS: {
                                    glPushMatrix();
                                    GlStateManager.enableBlend();
                                    glDisable(GL_TEXTURE_2D);
                                    glLineWidth(3f);
                                    glColor4f(0, 0, 0, 1);
                                    glBegin(GL_LINES);
                                    // top left
                                    glVertex2d(x, y);
                                    glVertex2d(x + lineLength, y);
                                    glVertex2d(x, y);
                                    glVertex2d(x, y + lineLength);
                                    // top right
                                    glVertex2d(w, y);
                                    glVertex2d(w - lineLength, y);
                                    glVertex2d(w, y);
                                    glVertex2d(w, y + lineLength);
                                    // bottom left
                                    glVertex2d(x, h);
                                    glVertex2d(x + lineLength, h);
                                    glVertex2d(x, h);
                                    glVertex2d(x, h - lineLength);
                                    // bottom right
                                    glVertex2d(w, h);
                                    glVertex2d(w - lineLength, h);
                                    glVertex2d(w, h);
                                    glVertex2d(w, h - lineLength);
                                    glEnd();

                                    glLineWidth(2f);
                                    ColorUtil.doColor(boxColor.getRGB());
                                    glBegin(GL_LINES);
                                    // top left
                                    glVertex2d(x, y);
                                    glVertex2d(x + lineLength, y);
                                    glVertex2d(x, y);
                                    glVertex2d(x, y + lineLength);
                                    // top right
                                    glVertex2d(w, y);
                                    glVertex2d(w - lineLength, y);
                                    glVertex2d(w, y);
                                    glVertex2d(w, y + lineLength);
                                    // bottom left
                                    glVertex2d(x, h);
                                    glVertex2d(x + lineLength, h);
                                    glVertex2d(x, h);
                                    glVertex2d(x, h - lineLength);
                                    // bottom right
                                    glVertex2d(w, h);
                                    glVertex2d(w - lineLength, h);
                                    glVertex2d(w, h);
                                    glVertex2d(w, h - lineLength);
                                    glEnd();
                                    glEnable(GL_TEXTURE_2D);
                                    GlStateManager.disableBlend();
                                    glPopMatrix();
                                    break;
                                }
                                case VERT_SIDES: {
                                    glPushMatrix();
                                    GlStateManager.enableBlend();
                                    glDisable(GL_TEXTURE_2D);
                                    glLineWidth(3f);
                                    glColor4f(0, 0, 0, 1);
                                    glBegin(GL_LINES);
                                    glVertex2d(x, y);
                                    glVertex2d(x, y + lineLength);
                                    glVertex2d(x, y);
                                    glVertex2d(w, y);
                                    glVertex2d(w, y);
                                    glVertex2d(w, y + lineLength);
                                    glVertex2d(x, h);
                                    glVertex2d(x, h - lineLength);
                                    glVertex2d(x, h);
                                    glVertex2d(w, h);
                                    glVertex2d(w, h);
                                    glVertex2d(w, h - lineLength);
                                    glEnd();
                                    glLineWidth(2f);
                                    ColorUtil.doColor(boxColor.getRGB());
                                    glBegin(GL_LINES);
                                    glVertex2d(x, y);
                                    glVertex2d(x, y + lineLength);
                                    glVertex2d(x, y);
                                    glVertex2d(w, y);
                                    glVertex2d(w, y);
                                    glVertex2d(w, y + lineLength);
                                    glVertex2d(x, h);
                                    glVertex2d(x, h - lineLength);
                                    glVertex2d(x, h);
                                    glVertex2d(w, h);
                                    glVertex2d(w, h);
                                    glVertex2d(w, h - lineLength);
                                    glEnd();
                                    glEnable(GL_TEXTURE_2D);
                                    GlStateManager.disableBlend();
                                    glPopMatrix();
                                    break;
                                }
                                case HORIZ_SIDES: {
                                    glPushMatrix();
                                    GlStateManager.enableBlend();
                                    glDisable(GL_TEXTURE_2D);
                                    glLineWidth(3f);
                                    glColor4f(0, 0, 0, 1);
                                    glBegin(GL_LINES);
                                    glVertex2d(x, y);
                                    glVertex2d(x + lineLength, y);
                                    glVertex2d(x, y);
                                    glVertex2d(x, h);
                                    glVertex2d(x, h);
                                    glVertex2d(x + lineLength, h);
                                    glVertex2d(w, y);
                                    glVertex2d(w - lineLength, y);
                                    glVertex2d(w, y);
                                    glVertex2d(w, h);
                                    glVertex2d(w, h);
                                    glVertex2d(w - lineLength, h);
                                    glEnd();
                                    glLineWidth(2f);
                                    ColorUtil.doColor(boxColor.getRGB());
                                    glBegin(GL_LINES);
                                    glVertex2d(x, y);
                                    glVertex2d(x + lineLength, y);
                                    glVertex2d(x, y);
                                    glVertex2d(x, h);
                                    glVertex2d(x, h);
                                    glVertex2d(x + lineLength, h);
                                    glVertex2d(w, y);
                                    glVertex2d(w - lineLength, y);
                                    glVertex2d(w, y);
                                    glVertex2d(w, h);
                                    glVertex2d(w, h);
                                    glVertex2d(w - lineLength, h);
                                    glEnd();
                                    glEnable(GL_TEXTURE_2D);
                                    GlStateManager.disableBlend();
                                    glPopMatrix();
                                    break;
                                }
                                case HALF_CORNERS: {
                                    glPushMatrix();
                                    GlStateManager.enableBlend();
                                    glDisable(GL_TEXTURE_2D);
                                    if (oppositeSetting.getValue()) {
                                        glLineWidth(3f);
                                        glColor4f(0, 0, 0, 1);
                                        glBegin(GL_LINES);
                                        glVertex2d(w, y);
                                        glVertex2d(w - lineLength, y);
                                        glVertex2d(w, y);
                                        glVertex2d(w, y + lineLength);

                                        glVertex2d(x, h);
                                        glVertex2d(x + lineLength, h);
                                        glVertex2d(x, h);
                                        glVertex2d(x, h - lineLength);
                                        glEnd();

                                        glLineWidth(2f);
                                        ColorUtil.doColor(boxColor.getRGB());
                                        glBegin(GL_LINES);
                                        glVertex2d(w, y);
                                        glVertex2d(w - lineLength, y);
                                        glVertex2d(w, y);
                                        glVertex2d(w, y + lineLength);

                                        glVertex2d(x, h);
                                        glVertex2d(x + lineLength, h);
                                        glVertex2d(x, h);
                                        glVertex2d(x, h - lineLength);
                                        glEnd();
                                    } else {
                                        glLineWidth(3f);
                                        glColor4f(0, 0, 0, 1);
                                        glBegin(GL_LINES);
                                        glVertex2d(x, y);
                                        glVertex2d(x + lineLength, y);
                                        glVertex2d(x, y);
                                        glVertex2d(x, y + lineLength);

                                        glVertex2d(w, h);
                                        glVertex2d(w - lineLength, h);
                                        glVertex2d(w, h);
                                        glVertex2d(w, h - lineLength);
                                        glEnd();

                                        glLineWidth(2f);
                                        ColorUtil.doColor(boxColor.getRGB());
                                        glBegin(GL_LINES);
                                        glVertex2d(x, y);
                                        glVertex2d(x + lineLength, y);
                                        glVertex2d(x, y);
                                        glVertex2d(x, y + lineLength);

                                        glVertex2d(w, h);
                                        glVertex2d(w - lineLength, h);
                                        glVertex2d(w, h);
                                        glVertex2d(w, h - lineLength);
                                        glEnd();
                                    }
                                    glEnable(GL_TEXTURE_2D);
                                    GlStateManager.disableBlend();
                                    glPopMatrix();
                                    break;
                                }
                            }
                            break;
                        }
                        case HAND: {
                            if(entity.getHeldItem() != null) {
                                float scale = 0.5f;
                                float leftoverScale = 1 / scale;
                                x *= leftoverScale;
                                y *= leftoverScale;
                                w *= leftoverScale;
                                h *= leftoverScale;
                                glScalef(scale, scale, 1);
                                String text = entity.getHeldItem().getDisplayName();
                                mc.fontRendererObj.drawStringWithShadow(text, (float) (x + (w - x) / 2 - mc.fontRendererObj.getStringWidth(text) / 2f),
                                        (float) (boxModeSetting.getValue() == BoxMode.BOX || boxModeSetting.getValue() == BoxMode.FILL ?
                                                h + mc.fontRendererObj.FONT_HEIGHT - 3 : h - mc.fontRendererObj.FONT_HEIGHT / 2f), Color.WHITE.getRGB());
                                glScalef(leftoverScale, leftoverScale, 1);
                                x *= scale;
                                y *= scale;
                                w *= scale;
                                h *= scale;
                            }
                            break;
                        }
                        case NAME_TAGS: {
                            float scale = 0.55f;
                            float leftoverScale = 1 / scale;
                            x *= leftoverScale;
                            y *= leftoverScale;
                            w *= leftoverScale;
                            h *= leftoverScale;
                            glScalef(scale, scale, 1);
                            mc.fontRendererObj.drawStringWithShadow(entity.getDisplayName().getFormattedText(),
                                    (float) (x + (w - x) / 2 - mc.fontRendererObj.getStringWidth(entity.getDisplayName().getFormattedText()) / 2f),
                                    (float) (boxModeSetting.getValue() == BoxMode.BOX || boxModeSetting.getValue() == BoxMode.FILL ?
                                            y - mc.fontRendererObj.FONT_HEIGHT - 3 : y - mc.fontRendererObj.FONT_HEIGHT / 2f), Color.WHITE.getRGB());
                            glScalef(leftoverScale, leftoverScale, 1);
                            x *= scale;
                            y *= scale;
                            w *= scale;
                            h *= scale;
                            break;
                        }
                        case HEALTH: {
                            x -= 3;
                            w -= 3;
                            glPushMatrix();
                            GlStateManager.enableBlend();
                            glDisable(GL_TEXTURE_2D);
                            glLineWidth(3f);
                            glColor4f(0, 0, 0, 1);
                            glBegin(GL_LINES);
                            glVertex2d(x, y);
                            glVertex2d(x, h);
                            glEnd();
                            glLineWidth(2f);
                            ColorUtil.doColor(ColorUtil.getColorFromHealth(entity).getRGB());
                            glBegin(GL_LINES);
                            glVertex2d(x, y + (h - y));
                            glVertex2d(x, h - (h - y) * (entity.getHealth() / entity.getMaxHealth()));
                            glEnd();
                            glEnable(GL_TEXTURE_2D);
                            GlStateManager.disableBlend();
                            glPopMatrix();
                            x += 3;
                            w += 3;
                            break;
                        }
                        case ARMOR: {
                            final double spacing = (h - y) / 5 + 2;
                            double lastSpacing = 0;
                            for (int i = 0; i < 4; i++) {
                                if (mc.thePlayer.getCurrentArmor(3 - i) != null) {
                                    mc.getRenderItem().renderItemIntoGUI(mc.thePlayer.getCurrentArmor(3 - i), (int) w + 4, (int) (y + (i * spacing) - 1));
                                }
                                lastSpacing = i * spacing;
                            }
                            glDisable(GL_LIGHTING);
                            glColor4f(1, 1, 1, 1);
                            if (mc.thePlayer.getHeldItem() != null)
                                mc.getRenderItem().renderItemIntoGUI(mc.thePlayer.getHeldItem(), (int)w + 4, (int)(y + lastSpacing + spacing - 5));
                            glPopMatrix();
                            break;
                        }
                    }
                }
            }
            glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
            glDisable(GL_LINE_SMOOTH);
        }
        coordinates.clear();
    };

    public enum Element implements SerializableEnum {
        BOX("Box"), NAME_TAGS("Nametags"), HEALTH("Health"), ARMOR("Armor"), HAND("Hand");
        final String name;
        Element(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }

    public enum BoxMode implements SerializableEnum {
        BOX("Box"), FILL("Fill"), BLUR_FILL("Blur Fill"), HORIZ_SIDES("Horiz Sides"),
        VERT_SIDES("Vert Sides"), CORNERS("Corners"), HALF_CORNERS("Half Corners");
        final String name;
        BoxMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (esp == null) {
            esp = Client.INSTANCE.getModuleManager().getModule(ESP.class);
        }
    }

    @Override
    public void onDeselect() {
        super.onDeselect();
        coordinates.clear();
    }

    @Override
    public ESP.ESPMode getRepresentation() {
        return ESP.ESPMode.TWO_DIMENSIONAL;
    }
}

package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.client.renderering.Render3DEvent;
import wtf.diablo.client.font.FontRepository;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.friend.FriendRepository;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.gl.GLUtils;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

@ModuleMetaData(name = "2D ESP", description = "Draws a box around entities", category = ModuleCategoryEnum.RENDER)
public final class ESPModule extends AbstractModule {
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private final Frustum frustrum = new Frustum();

    private final BooleanSetting box = new BooleanSetting("Box", true);
    private final BooleanSetting healthBar = new BooleanSetting("Health Bar", true);
    private final BooleanSetting name = new BooleanSetting("Name", true);
    private final BooleanSetting heldItem = new BooleanSetting("Held Item", true);
    private final BooleanSetting armor = new BooleanSetting("Armor", true);
    private final ColorSetting color = new ColorSetting("Color", Color.WHITE);
    private final ColorSetting nametagColor = new ColorSetting("Nametag Color", Color.WHITE);
    private final NumberSetting<Float> nameScale = new NumberSetting<>("Name Scale", 0.75f, 0.1f, 2.0f, 0.1f);

    public ESPModule() {
        this.registerSettings(box, healthBar, color, name, heldItem, armor, nametagColor, nameScale);
    }

    @EventHandler
    public final Listener<Render3DEvent> render3DEventListener = e -> {
        final FriendRepository friendRepository = Diablo.getInstance().getFriendRepository();

        for (final Entity object : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            EntityLivingBase ent;
            if (!(object instanceof EntityLivingBase) || (object.isInvisible()) || ((ent = (EntityLivingBase) object) == mc.thePlayer) || !isInViewFrustrum(ent))
                continue;

            GLUtils.setup2DRendering(true);

            float delta = e.getPartialTicks();

            double posX = this.interpolate(object.prevPosX, object.posX, delta);
            double posY = this.interpolate(object.prevPosY, object.posY, delta);
            double posZ = this.interpolate(object.prevPosZ, object.posZ, delta);

            double finalWidth = (double) object.width / 2.2;
            double finalHeight = (double) object.height + (object.isSneaking() ? -0.225 : 0.125);
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(posX - finalWidth - 0.1, posY, posZ - finalWidth - 0.1, posX + finalWidth + 0.1, posY + finalHeight, posZ + finalWidth + 0.1);
            List<Vector3d> vectorList = Arrays.asList(new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ));
            mc.entityRenderer.setupCameraTransform(mc.getTimer().renderPartialTicks, 0);
            Vector4d posVec = null;

            for (Vector3d vector : vectorList) {
                FloatBuffer otherVec = GLAllocation.createDirectFloatBuffer(4);
                GL11.glGetFloat(2982, modelview);
                GL11.glGetFloat(2983, projection);
                GL11.glGetInteger(2978, viewport);
                if (GLU.gluProject((float) (vector.x - mc.getRenderManager().viewerPosX), (float) (vector.y - mc.getRenderManager().viewerPosY), (float) (vector.z - mc.getRenderManager().viewerPosZ), modelview, projection, viewport, otherVec)) {
                    vector = new Vector3d(otherVec.get(0) / (float) new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor(), ((float) Display.getHeight() - otherVec.get(1)) / (float) new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor(), otherVec.get(2));
                }
                if (!(vector.z >= 0.0) || !(vector.z < 1.0)) continue;
                if (posVec == null) {
                    posVec = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                }
                posVec.x = Math.min(vector.x, posVec.x);
                posVec.y = Math.min(vector.y, posVec.y);
                posVec.z = Math.max(vector.x, posVec.z);
                posVec.w = Math.max(vector.y, posVec.w);
            }

            //mc.entityRenderer.setupOverlayRendering();
            GlStateManager.pushMatrix();
            final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -2000.0F);

            if (posVec == null) continue;

            float x = (float) posVec.x;
            float w = (float) posVec.z - x;
            float y = (float) posVec.y;
            float h = (float) posVec.w - y;
            int healthBarColor = getHealthColor(ent);
            if (ent instanceof EntityPlayer) {
                float boarder = 0.45f;
                int boarderColor = Color.black.getRGB();

                if (healthBar.getValue()) {
                    final float healthBorder = 0.25f;

                    final float healthX = x - 2;
                    final float healthY = y;
                    final float healthW = 1F;
                    final float healthH = h;
                    final float healthMax = (float) ((int) ent.getMaxHealth()) / 2.0f;
                    final float healthValue = (float) ((int) ent.getHealth()) / 2.0f;

                    final int background = new Color(-1459617792, true).getRGB();

                    Gui.drawRect(healthX - healthBorder, healthY - healthBorder, healthX + healthBorder, healthY + healthH + healthBorder, boarderColor);
                    Gui.drawRect(healthX, healthY - healthBorder, healthX + healthW, healthY + healthBorder, boarderColor);
                    Gui.drawRect(healthX - healthBorder, healthY + healthH - healthBorder, healthX + healthBorder, healthY + healthH + healthBorder, boarderColor);
                    Gui.drawRect(healthX + healthW - healthBorder, healthY - healthBorder, healthX + healthW + healthBorder, healthY + healthH + healthBorder, boarderColor);

                    drawBar(healthX, healthY, healthW, healthH, healthMax, healthMax, healthBarColor, background);
                    drawBar(healthX, healthY, healthW, healthH, healthMax, healthValue, background, healthBarColor);
                }

                if (box.getValue()) {
                    float thickness = 0.55f;
                    int boxColor = friendRepository.isFriend(ent.getName()) ? ColorUtil.FRIEND_COLOR.getRGB() : color.getValue().getRGB();

                    Gui.drawRect(x - boarder, y - boarder, x + w + boarder, y + thickness + boarder, boarderColor);
                    Gui.drawRect(x - boarder, y - boarder, x + thickness + boarder, y + h + boarder, boarderColor);
                    Gui.drawRect(x - boarder, y + h - thickness - boarder, x + w + boarder, y + h + boarder, boarderColor);
                    Gui.drawRect(x + w - thickness - boarder, y - boarder, x + w + boarder, y + h + boarder, boarderColor);

                    Gui.drawRect(x, y, x + w, y + thickness, boxColor);
                    Gui.drawRect(x, y, x + thickness, y + h, boxColor);
                    Gui.drawRect(x, y + h - thickness, x + w, y + h, boxColor);
                    Gui.drawRect(x + w - thickness, y, x + w, y + h, boxColor);

                }

                final String name = ent.getName();

                final TTFFontRenderer font = FontRepository.SLKSCR12;

                double renderX = posVec.getX(),
                        renderY = posVec.getY(),
                        renderZ = posVec.getZ(),
                        renderWidth = posVec.getW();

                float fontScale = 1f; //default val

                final double distance = mc.thePlayer.getDistanceSqToEntity(ent);

                float distanceScale = (float) ((float) 1 - ((0.85 * (distance)) / 750));

                if (distanceScale < nameScale.getValue()) {
                    distanceScale = nameScale.getValue();
                }

                fontScale = Math.min(Math.abs(distanceScale), 1f);

                float midpoint = (float) (renderX + ((renderZ - renderX) / 2));
                float fontHeight = font.getHeight(name) * fontScale;

                if (this.name.getValue()) {
                    glPushMatrix();
                    glTranslated(midpoint, y - fontHeight, 0);
                    glScaled(fontScale, fontScale, 1);
                    glTranslated(-midpoint, -(y - fontHeight), 0);

                    /*
                    final float borderThickness = 0.55f;
                    final float thickness = 1.5f;

                    final float gapNameBackground = 1.5f;
                    Gui.drawRect(midpoint - font.getWidth(name) / 2 - gapNameBackground, renderY - (fontHeight + 4) - gapNameBackground, midpoint + font.getWidth(name) / 2 + gapNameBackground, renderY - 4 + gapNameBackground, Color.BLACK.getRGB());
                    */

                    font.drawStringWithOutline(name, midpoint - font.getWidth(name) / 2, renderY - (fontHeight + 4), nametagColor.getValue().getRGB(), Color.BLACK.getRGB(), 3);

                    glPopMatrix();

                }

                if (heldItem.getValue()) {
                    glPushMatrix();
                    glTranslated(midpoint, y + h, 0);
                    glScaled(fontScale * 0.725, fontScale * 0.725, 1);
                    glTranslated(-midpoint, -(y + h), 0);

                    mc.getRenderItem().renderItemAndEffectIntoGUI(ent.getHeldItem(), (int) (midpoint - 8), (int) (y + h + 2));
                    glScaled(1,1,1);
                    glPopMatrix();
                }

                fontScale = fontScale / 1.1f;

                glPushMatrix();
                glTranslated(x + w + 2, y + 2, 0);
                glScaled(fontScale, fontScale, 1);
                glTranslated(-(x + w + 2), -(y + 2), 0);

                double potionY = 0;

                final TTFFontRenderer potionFont = FontRepository.SLKSCR10;

                final float textX = x + w + 2;

                /*
                for (final PotionEffect effect : ent.getActivePotionEffects().stream().sorted(Comparator.comparing(potionEffect -> I18n.format(Potion.potionTypes[potionEffect.getPotionID()].getName()))).collect(Collectors.toList())) {
                    final Potion potion = Potion.potionTypes[effect.getPotionID()];
                    final String effectName = I18n.format(potion.getName());
                    final int color = potion.getLiquidColor();

                    int red = (color >> 16) & 0xFF;
                    int green = (color >> 8) & 0xFF;
                    int blue = color & 0xFF;

                    potionFont.drawStringWithOutline(String.format("%s %S", I18n.format(effect.getEffectName()), effect.getAmplifier() + 1), textX, y + 2 + potionY, new Color(red, green, blue).getRGB(), Color.BLACK.getRGB(), 2);
                    potionY += potionFont.getHeight(effectName) + 1;
                }

                 */

                if (((EntityPlayer) ent).isBlocking()) {
                    final String blockingString = "blocking";

                    potionFont.drawStringWithOutline(blockingString, textX, y + 2 + potionY, Color.GREEN.getRGB(), Color.BLACK.getRGB(), 2);
                }

                glPopMatrix();

                if (armor.getValue()) {
                    final int sectionY = (int) (h / 4);

                    for (int i = 4; i > 0; i--) {
                        final ItemStack armor = ((EntityPlayer) ent).inventory.armorInventory[4 - i];

                        if (armor != null) {
                            glPushMatrix();
                            glTranslated(x + w + 2, y + sectionY * (i - 1), 0);
                            glScaled(fontScale * 0.725, fontScale * 0.725, 1);
                            glTranslated(-(x + w + 2), -(y + sectionY * (i - 1)), 0);

                            mc.getRenderItem().renderItemAndEffectIntoGUI(armor, (int) (x + w + 2), (int) (y + sectionY * (i - 1)));
                            glScaled(1,1,1);
                            glPopMatrix();
                        }
                    }
                }
            }
            GLUtils.end2DRendering();
            GlStateManager.popMatrix();
        }
    };

    public boolean isInViewFrustrum(Entity entity) {
        return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    public boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        frustrum.setPosition(current.posX, current.posY, current.posZ);
        return frustrum.isBoundingBoxInFrustum(bb);
    }

    public void drawBorderedRect(double x, double y, double width, double height, double lineSize, int borderColor, int color) {
        Gui.drawRect(x, y, x + width, y + lineSize, borderColor);
        Gui.drawRect(x, y, x + lineSize, y + height, borderColor);
        Gui.drawRect(x + width - lineSize, y, x + width, y + height, borderColor);
        Gui.drawRect(x, y + height - lineSize, x + width, y + height, borderColor);
        Gui.drawRect(x + lineSize, y + lineSize, x + width - lineSize, y + height - lineSize, color);
    }

    public void drawRect(double x, double y, double width, double height, int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
    }


    public void drawBar(float x, float y, float width, float height, float max, float value, int color, int color2) {
        float f = (float) (color >> 24 & 0xFF) / 255.0f;
        float f1 = (float) (color >> 16 & 0xFF) / 255.0f;
        float f2 = (float) (color >> 8 & 0xFF) / 255.0f;
        float f3 = (float) (color & 0xFF) / 255.0f;
        float inc = height / max;
        GL11.glColor4f(f1, f2, f3, f);
        float incY = y + height - inc;
        int i = 0;
        while ((float) i < value) {
            drawRect(x + 0.25f, incY, width - 0.5f, inc, new Color(color2, true).getRGB());
            incY -= inc;
            ++i;
        }
    }

    private int getHealthColor(EntityLivingBase player) {
        return Color.HSBtoRGB(Math.max(0.0f, Math.min(player.getHealth(), player.getMaxHealth()) / player.getMaxHealth()) / 3.0f, 1f, 1.0f);
    }

    private double interpolate(double previous, double current, float delta) {
        return previous + (current - previous) * (double) delta;
    }

    enum EspMode implements IMode {
        BOX("Box"),
        CORNER("Corner"),
        FILLED("Filled");

        EspMode(String name) {
            this.name = name;
        }

        private final String name;

        @Override
        public String getName() {
            return this.name;
        }
    }
}

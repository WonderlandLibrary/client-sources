// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.visuals;

import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import java.util.Iterator;
import org.apache.commons.lang3.RandomUtils;
import net.minecraft.entity.EntityLivingBase;
import gg.childtrafficking.smokex.SmokeXClient;
import net.minecraft.entity.player.EntityPlayer;
import java.awt.Color;
import gg.childtrafficking.smokex.event.events.render.EventRender3D;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "ChinaHat", renderName = "China Hat", description = "CHING CHENG HANZI", aliases = { "" }, category = ModuleCategory.VISUALS)
public final class ChinaHatModule extends Module
{
    private final BooleanProperty renderInFirstPersonValue;
    private final NumberProperty<Double> sizeValue;
    private final NumberProperty<Double> pointsValue;
    private final NumberProperty<Float> offSetValue;
    private final EnumProperty<ColorMode> colorModeValue;
    private final double[][] pointsCache;
    private int lastPoints;
    private double lastSize;
    private float yaw;
    private float prevYaw;
    private float pitch;
    private float prevPitch;
    private final EventListener<EventUpdate> updateEventListener;
    private final EventListener<EventRender3D> render3DEventListener;
    private final Color[] rainbow;
    private final Color[] astolfo;
    private final Color[] pink;
    
    public ChinaHatModule() {
        this.renderInFirstPersonValue = new BooleanProperty("Render First Person", false);
        this.sizeValue = new NumberProperty<Double>("Size", 0.5, 0.0, 2.0, 0.1);
        this.pointsValue = new NumberProperty<Double>("Points", 30.0, 3.0, 180.0, 1.0);
        this.offSetValue = new NumberProperty<Float>("Off Set", 2000.0f, 0.0f, 5000.0f, 100.0f);
        this.colorModeValue = new EnumProperty<ColorMode>("Color", ColorMode.ASTOLFO);
        this.pointsCache = new double[181][2];
        this.updateEventListener = (event -> {
            this.yaw = event.getYaw();
            this.prevYaw = event.getPrevYaw();
            this.pitch = event.getPitch();
            this.prevPitch = event.getPrevPitch();
            return;
        });
        this.render3DEventListener = (event -> {
            if (this.lastSize != this.sizeValue.getValue() || this.lastPoints != this.pointsValue.getValue()) {
                this.lastSize = this.sizeValue.getValue();
                this.genPoints(this.lastPoints = this.pointsValue.getValue().intValue(), this.lastSize);
            }
            this.mc.theWorld.playerEntities.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final EntityPlayer entity = iterator.next();
                if (SmokeXClient.getInstance().getPlayerManager().isFriend(entity.getName()) || entity == this.mc.thePlayer) {
                    this.drawHat(event, entity);
                }
            }
            return;
        });
        this.rainbow = new Color[] { new Color(30, 250, 215), new Color(0, 200, 255), new Color(50, 100, 255), new Color(100, 50, 255), new Color(255, 50, 240), new Color(255, 0, 0), new Color(255, 150, 0), new Color(255, 255, 0), new Color(0, 255, 0), new Color(80, 240, 155) };
        this.astolfo = new Color[] { new Color(252, 106, 140), new Color(252, 106, 213), new Color(218, 106, 252), new Color(145, 106, 252), new Color(106, 140, 252), new Color(106, 213, 252), new Color(106, 213, 252), new Color(106, 140, 252), new Color(145, 106, 252), new Color(218, 106, 252), new Color(252, 106, 213), new Color(252, 106, 140) };
        this.pink = new Color[] { new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)), new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)) };
    }
    
    private void drawHat(final EventRender3D event, final EntityLivingBase entity) {
        final boolean isPlayerSP = entity.isEntityEqual(this.mc.thePlayer);
        if (this.mc.gameSettings.thirdPersonView == 0 && isPlayerSP && !this.renderInFirstPersonValue.getValue()) {
            return;
        }
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glShadeModel(7425);
        GL11.glEnable(3042);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.getPartialTicks() - RenderManager.renderPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.getPartialTicks() - RenderManager.renderPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.getPartialTicks() - RenderManager.renderPosZ;
        final Color[] colors = new Color[181];
        for (int i = 0; i < colors.length; ++i) {
            colors[i] = ((this.colorModeValue.getValue() == ColorMode.RAINBOW) ? this.fadeBetween(this.rainbow, 6000.0, i * (6000.0 / this.pointsValue.getValue())) : this.fadeBetween((this.colorModeValue.getValue() == ColorMode.ASTOLFO) ? this.astolfo : this.pink, (double)this.offSetValue.getValue().longValue(), i * (this.offSetValue.getValue() / this.pointsValue.getValue())));
        }
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 1.9, z);
        if (entity.isSneaking()) {
            GL11.glTranslated(0.0, -0.2, 0.0);
        }
        GL11.glRotatef((float)RenderingUtils.interpolate(isPlayerSP ? ((double)this.prevYaw) : ((double)entity.prevRotationYaw), isPlayerSP ? ((double)this.yaw) : ((double)entity.rotationYaw), event.getPartialTicks()), 0.0f, -1.0f, 0.0f);
        final float interpolate = (float)RenderingUtils.interpolate(isPlayerSP ? ((double)this.prevPitch) : ((double)entity.prevRotationPitch), isPlayerSP ? ((double)this.pitch) : ((double)entity.rotationPitch), event.getPartialTicks());
        GL11.glRotatef(interpolate / 3.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslated(0.0, 0.0, (double)(interpolate / 270.0f));
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(2);
        this.drawCircle(this.pointsValue.getValue().intValue() - 1, colors, 255);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glBegin(6);
        GL11.glVertex3d(0.0, this.sizeValue.getValue() / 2.0, 0.0);
        this.drawCircle(this.pointsValue.getValue().intValue(), colors, 85);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glShadeModel(7424);
        GL11.glEnable(2929);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
    }
    
    private void drawCircle(final int points, final Color[] colors, final int alpha) {
        for (int i = 0; i <= points; ++i) {
            final double[] point = this.pointsCache[i];
            final Color clr = colors[i];
            GL11.glColor4f(clr.getRed() / 255.0f, clr.getGreen() / 255.0f, clr.getBlue() / 255.0f, alpha / 255.0f);
            GL11.glVertex3d(point[0], 0.0, point[1]);
        }
    }
    
    private void genPoints(final int points, final double size) {
        for (int i = 0; i <= points; ++i) {
            final double cos = size * StrictMath.cos(i * 3.141592653589793 * 2.0 / points);
            final double sin = size * StrictMath.sin(i * 3.141592653589793 * 2.0 / points);
            this.pointsCache[i][0] = cos;
            this.pointsCache[i][1] = sin;
        }
    }
    
    public Color fadeBetween(final Color[] table, final double progress) {
        final int i = table.length;
        if (progress == 1.0) {
            return table[0];
        }
        if (progress == 0.0) {
            return table[i - 1];
        }
        final double max = Math.max(0.0, (1.0 - progress) * (i - 1));
        final int min = (int)max;
        return this.fadeBetween(table[min], table[min + 1], max - min);
    }
    
    public Color fadeBetween(final Color start, final Color end, double progress) {
        if (progress > 1.0) {
            progress = 1.0 - progress % 1.0;
        }
        return this.gradient(start, end, progress);
    }
    
    public Color gradient(final Color start, final Color end, final double progress) {
        final double invert = 1.0 - progress;
        return new Color((int)(start.getRed() * invert + end.getRed() * progress), (int)(start.getGreen() * invert + end.getGreen() * progress), (int)(start.getBlue() * invert + end.getBlue() * progress), (int)(start.getAlpha() * invert + end.getAlpha() * progress));
    }
    
    public Color fadeBetween(final Color[] table, final double speed, final double offset) {
        return this.fadeBetween(table, (System.currentTimeMillis() + offset) % speed / speed);
    }
    
    public enum ColorMode
    {
        ASTOLFO, 
        RAINBOW, 
        PINK;
    }
}


package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender2D;
import Ascii4UwUWareClient.API.Events.Render.EventRender3D;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Misc.Teams;
import Ascii4UwUWareClient.Util.Render.ColorUtils;
import Ascii4UwUWareClient.Util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class ESP2D extends Module {
    public static Mode<Enum> mode = new Mode("Mode", "mode", (Enum[])TwoD.values(), (Enum)TwoD.Box);
    public static Option<Boolean> HEALTH = new Option("Health", "Health", (Object)true);
    public static Option<Boolean> player = new Option("Players", "Players", (Object)true);
    public static Option<Boolean> mobs = new Option("Mobs", "Mobs", (Object)true);
    public static Option<Boolean> animals = new Option("Animals", "Animals", (Object)true);
    public static Option<Boolean> invis = new Option("Invis", "Invis", (Object)true);
    public static Option<Boolean> ARMOR = new Option("Armor", "Armor", (Object)true);
    private Map<EntityLivingBase, double[]> entityConvertedPointsMap;
    FontRenderer fr;

    public ESP2D() {
        super("ESP2D", new String[0], ModuleType.Render);
        this.addValues(mode, HEALTH, player, mobs, animals, invis, ARMOR);
        this.entityConvertedPointsMap = new HashMap();
        this.fr = mc.fontRendererObj;
    }

    @EventHandler
    public void onRender(EventRender3D event) {
        try {
            this.updatePositions();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @EventHandler
    public void onRender2D(EventRender2D event) {
        GlStateManager.pushMatrix();
        for (Entity entity : this.entityConvertedPointsMap.keySet()) {
            EntityPlayer ent = (EntityPlayer)entity;
            double[] renderPositions = (double[])this.entityConvertedPointsMap.get((Object)ent);
            double[] renderPositionsBottom = new double[]{renderPositions[4], renderPositions[5], renderPositions[6]};
            double[] renderPositionsX = new double[]{renderPositions[7], renderPositions[8], renderPositions[9]};
            double[] renderPositionsX2 = new double[]{renderPositions[10], renderPositions[11], renderPositions[12]};
            double[] renderPositionsZ = new double[]{renderPositions[13], renderPositions[14], renderPositions[15]};
            double[] renderPositionsZ2 = new double[]{renderPositions[16], renderPositions[17], renderPositions[18]};
            double[] renderPositionsTop1 = new double[]{renderPositions[19], renderPositions[20], renderPositions[21]};
            double[] renderPositionsTop2 = new double[]{renderPositions[22], renderPositions[23], renderPositions[24]};
            boolean bl = renderPositions[3] > 0.0 && renderPositions[3] <= 1.0 && renderPositionsBottom[2] > 0.0 && renderPositionsBottom[2] <= 1.0 && renderPositionsX[2] > 0.0 && renderPositionsX[2] <= 1.0 && renderPositionsX2[2] > 0.0 && renderPositionsX2[2] <= 1.0 && renderPositionsZ[2] > 0.0 && renderPositionsZ[2] <= 1.0 && renderPositionsZ2[2] > 0.0 && renderPositionsZ2[2] <= 1.0 && renderPositionsTop1[2] > 0.0 && renderPositionsTop1[2] <= 1.0 && renderPositionsTop2[2] > 0.0 && renderPositionsTop2[2] <= 1.0;
            boolean shouldRender = bl;
            GlStateManager.pushMatrix();
            GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
            if ((((Boolean)invis.getValue()).booleanValue() || !ent.isInvisible()) && ent instanceof EntityPlayer && !(ent instanceof EntityPlayerSP)) {
                try {
                    double[] xValues = new double[]{renderPositions[0], renderPositionsBottom[0], renderPositionsX[0], renderPositionsX2[0], renderPositionsZ[0], renderPositionsZ2[0], renderPositionsTop1[0], renderPositionsTop2[0]};
                    double[] yValues = new double[]{renderPositions[1], renderPositionsBottom[1], renderPositionsX[1], renderPositionsX2[1], renderPositionsZ[1], renderPositionsZ2[1], renderPositionsTop1[1], renderPositionsTop2[1]};
                    double x = renderPositions[0];
                    double y = renderPositions[1];
                    double endx = renderPositionsBottom[0];
                    double endy = renderPositionsBottom[1];
                    for (double bdubs : xValues) {
                        if (!(bdubs < x)) continue;
                        x = bdubs;
                    }
                    for (double bdubs : xValues) {
                        if (!(bdubs > endx)) continue;
                        endx = bdubs;
                    }
                    for (double bdubs : yValues) {
                        if (!(bdubs < y)) continue;
                        y = bdubs;
                    }
                    for (double bdubs : yValues) {
                        if (!(bdubs > endy)) continue;
                        endy = bdubs;
                    }
                    double xDiff = (endx - x) / 4.0;
                    double x2Diff = (endx - x) / 4.0;
                    double yDiff = xDiff;
                    int color = ColorUtils.getColor((int)255, (int)255);
                    color = Teams.isOnSameTeam((Entity)ent) ? ColorUtils.getColor((int)0, (int)255, (int)0, (int)255) : (ent.hurtTime > 0 ? ColorUtils.getColor((int)255, (int)0, (int)0, (int)255) : (ent.isInvisible() ? ColorUtils.getColor((int)255, (int)255, (int)0, (int)255) : ColorUtils.getColor((int)255, (int)255, (int)255, (int)255)));
                    if (mode.getValue() == TwoD.Box) {
                        RenderUtil.rectangleBordered((double)(x + 0.5), (double)(y + 0.5), (double)(endx - 0.5), (double)(endy - 0.5), (double)1.0, (int)ColorUtils.getColor((int)0, (int)0, (int)0, (int)0), (int)color);
                        RenderUtil.rectangleBordered((double)(x - 0.5), (double)(y - 0.5), (double)(endx + 0.5), (double)(endy + 0.5), (double)1.0, (int)ColorUtils.getColor((int)0, (int)0), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangleBordered((double)(x + 1.5), (double)(y + 1.5), (double)(endx - 1.5), (double)(endy - 1.5), (double)1.0, (int)ColorUtils.getColor((int)0, (int)0), (int)ColorUtils.getColor((int)0, (int)150));
                    }
                    if (mode.getValue() == TwoD.CornerB) {
                        RenderUtil.rectangle((double)(x + 0.5), (double)(y + 0.5), (double)(x + 1.5), (double)(y + yDiff + 0.5), (int)color);
                        RenderUtil.rectangle((double)(x + 0.5), (double)(endy - 0.5), (double)(x + 1.5), (double)(endy - yDiff - 0.5), (int)color);
                        RenderUtil.rectangle((double)(x - 0.5), (double)(y + 0.5), (double)(x + 0.5), (double)(y + yDiff + 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x + 1.5), (double)(y + 2.5), (double)(x + 2.5), (double)(y + yDiff + 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x - 0.5), (double)(y + yDiff + 0.5), (double)(x + 2.5), (double)(y + yDiff + 1.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x - 0.5), (double)(endy - 0.5), (double)(x + 0.5), (double)(endy - yDiff - 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x + 1.5), (double)(endy - 2.5), (double)(x + 2.5), (double)(endy - yDiff - 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x - 0.5), (double)(endy - yDiff - 0.5), (double)(x + 2.5), (double)(endy - yDiff - 1.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x + 1.0), (double)(y + 0.5), (double)(x + x2Diff), (double)(y + 1.5), (int)color);
                        RenderUtil.rectangle((double)(x - 0.5), (double)(y - 0.5), (double)(x + x2Diff), (double)(y + 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x + 1.5), (double)(y + 1.5), (double)(x + x2Diff), (double)(y + 2.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x + x2Diff), (double)(y - 0.5), (double)(x + x2Diff + 1.0), (double)(y + 2.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x + 1.0), (double)(endy - 0.5), (double)(x + x2Diff), (double)(endy - 1.5), (int)color);
                        RenderUtil.rectangle((double)(x - 0.5), (double)(endy + 0.5), (double)(x + x2Diff), (double)(endy - 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x + 1.5), (double)(endy - 1.5), (double)(x + x2Diff), (double)(endy - 2.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x + x2Diff), (double)(endy + 0.5), (double)(x + x2Diff + 1.0), (double)(endy - 2.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx - 0.5), (double)(y + 0.5), (double)(endx - 1.5), (double)(y + yDiff + 0.5), (int)color);
                        RenderUtil.rectangle((double)(endx - 0.5), (double)(endy - 0.5), (double)(endx - 1.5), (double)(endy - yDiff - 0.5), (int)color);
                        RenderUtil.rectangle((double)(endx + 0.5), (double)(y + 0.5), (double)(endx - 0.5), (double)(y + yDiff + 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx - 1.5), (double)(y + 2.5), (double)(endx - 2.5), (double)(y + yDiff + 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx + 0.5), (double)(y + yDiff + 0.5), (double)(endx - 2.5), (double)(y + yDiff + 1.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx + 0.5), (double)(endy - 0.5), (double)(endx - 0.5), (double)(endy - yDiff - 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx - 1.5), (double)(endy - 2.5), (double)(endx - 2.5), (double)(endy - yDiff - 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx + 0.5), (double)(endy - yDiff - 0.5), (double)(endx - 2.5), (double)(endy - yDiff - 1.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx - 1.0), (double)(y + 0.5), (double)(endx - x2Diff), (double)(y + 1.5), (int)color);
                        RenderUtil.rectangle((double)(endx + 0.5), (double)(y - 0.5), (double)(endx - x2Diff), (double)(y + 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx - 1.5), (double)(y + 1.5), (double)(endx - x2Diff), (double)(y + 2.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx - x2Diff), (double)(y - 0.5), (double)(endx - x2Diff - 1.0), (double)(y + 2.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx - 1.0), (double)(endy - 0.5), (double)(endx - x2Diff), (double)(endy - 1.5), (int)color);
                        RenderUtil.rectangle((double)(endx + 0.5), (double)(endy + 0.5), (double)(endx - x2Diff), (double)(endy - 0.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx - 1.5), (double)(endy - 1.5), (double)(endx - x2Diff), (double)(endy - 2.5), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(endx - x2Diff), (double)(endy + 0.5), (double)(endx - x2Diff - 1.0), (double)(endy - 2.5), (int)ColorUtils.getColor((int)0, (int)150));
                    }
                    mode.getValue();
                    if (((Boolean)HEALTH.getValue()).booleanValue()) {
                        float health = ent.getHealth();
                        float[] fractions = new float[]{0.0f, 0.5f, 1.0f};
                        Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
                        float progress = health / ent.getMaxHealth();
                        Color customColor = health >= 0.0f ? blendColors(fractions, colors, progress).brighter() : Color.RED;
                        double difference = y - endy + 0.5;
                        double healthLocation = endy + difference * (double)progress;
                        RenderUtil.rectangleBordered((double)(x - 6.5), (double)(y - 0.5), (double)(x - 2.5), (double)endy, (double)1.0, (int)ColorUtils.getColor((int)0, (int)100), (int)ColorUtils.getColor((int)0, (int)150));
                        RenderUtil.rectangle((double)(x - 5.5), (double)(endy - 1.0), (double)(x - 3.5), (double)healthLocation,  new Color(102, 172, 255).getRGB());
                        if (-difference > 50.0) {
                            for (int i = 1; i < 10; ++i) {
                                double dThing = difference / 10.0 * (double)i;
                                RenderUtil.rectangle((double)(x - 6.5), (double)(endy - 0.5 + dThing), (double)(x - 2.5), (double)(endy - 0.5 + dThing - 1.0), (int)ColorUtils.getColor((int)0));
                            }
                        }
                        if ((int)getIncremental(progress * 100.0f, 1.0) <= 40) {
                            GlStateManager.pushMatrix();
                            GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
                            String nigger = String.valueOf((int)((int)getIncremental(health * 5.0f, 1.0))) + "HP";
                            GlStateManager.popMatrix();
                        }
                    }
                }
                catch (Exception xValues) {
                    // empty catch block
                }
            }
            GlStateManager.popMatrix();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
        RenderUtil.rectangle((double)0.0, (double)0.0, (double)0.0, (double)0.0, (int)-1);
    }

    public static double getIncremental(double val, double inc) {
        double one = 1.0 / inc;
        return (double)Math.round((double)(val * one)) / one;
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        Object color = null;
        if (fractions == null) {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        if (colors == null) {
            throw new IllegalArgumentException("Colours can't be null");
        }
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        }
        int[] indicies = getFractionIndicies(fractions, progress);
        float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
        Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
        float max = range[1] - range[0];
        float value = progress - range[0];
        float weight = value / max;
        return blend(colorRange[0], colorRange[1], 1.0f - weight);
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float)ratio;
        float ir = 1.0f - r;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0f) {
            red = 0.0f;
        } else if (red > 255.0f) {
            red = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        } else if (green > 255.0f) {
            green = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        } else if (blue > 255.0f) {
            blue = 255.0f;
        }
        Color color3 = null;
        try {
            color3 = new Color(red, green, blue);
        }
        catch (IllegalArgumentException exp) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println(String.valueOf((Object)String.valueOf((Object)nf.format((double)red))) + "; " + nf.format((double)green) + "; " + nf.format((double)blue));
            exp.printStackTrace();
        }
        return color3;
    }

    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int startPoint;
        int[] range = new int[2];
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
        }
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    private void updatePositions() {
        this.entityConvertedPointsMap.clear();
        float pTicks = mc.timer.renderPartialTicks;
        for (Entity e2 : Minecraft.theWorld.getLoadedEntityList()) {
            EntityPlayer ent;
            double topY;
            if (!(e2 instanceof EntityPlayer) || (ent = (EntityPlayer)e2) == Minecraft.thePlayer) continue;
            double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX + 0.36;
            double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - mc.getRenderManager().viewerPosY;
            double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ + 0.36;
            y = topY = y + ((double)ent.height + 0.15);
            double[] convertedPoints = RenderUtil.convertTo2D((double)x, (double)y, (double)z);
            double[] convertedPoints2 = RenderUtil.convertTo2D((double)(x - 0.36), (double)y, (double)(z - 0.36));
            double xd = 0.0;
            if (convertedPoints2[2] < 0.0 || convertedPoints2[2] >= 1.0) continue;
            x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX - 0.36;
            z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ - 0.36;
            double[] convertedPointsBottom = RenderUtil.convertTo2D((double)x, (double)y, (double)z);
            y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - mc.getRenderManager().viewerPosY - 0.05;
            double[] convertedPointsx = RenderUtil.convertTo2D((double)x, (double)y, (double)z);
            x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX - 0.36;
            z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ + 0.36;
            double[] convertedPointsTop1 = RenderUtil.convertTo2D((double)x, (double)topY, (double)z);
            double[] convertedPointsx2 = RenderUtil.convertTo2D((double)x, (double)y, (double)z);
            x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX + 0.36;
            z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ + 0.36;
            double[] convertedPointsz = RenderUtil.convertTo2D((double)x, (double)y, (double)z);
            x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX + 0.36;
            z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ - 0.36;
            double[] convertedPointsTop2 = RenderUtil.convertTo2D((double)x, (double)topY, (double)z);
            double[] convertedPointsz2 = RenderUtil.convertTo2D((double)x, (double)y, (double)z);
            this.entityConvertedPointsMap.put((EntityLivingBase)ent, new double[]{convertedPoints[0], convertedPoints[1], 0.0, convertedPoints[2], convertedPointsBottom[0], convertedPointsBottom[1], convertedPointsBottom[2], convertedPointsx[0], convertedPointsx[1], convertedPointsx[2], convertedPointsx2[0], convertedPointsx2[1], convertedPointsx2[2], convertedPointsz[0], convertedPointsz[1], convertedPointsz[2], convertedPointsz2[0], convertedPointsz2[1], convertedPointsz2[2], convertedPointsTop1[0], convertedPointsTop1[1], convertedPointsTop1[2], convertedPointsTop2[0], convertedPointsTop2[1], convertedPointsTop2[2]});
        }
    }

    private String getColor(int level) {
        if (level == 2) {
            return "\u00a7a";
        }
        if (level == 3) {
            return "\u00a73";
        }
        if (level == 4) {
            return "\u00a74";
        }
        if (level >= 5) {
            return "\u00a76";
        }
        return "\u00a7f";
    }
    static enum TwoD {
        Box,
        CornerA,
        CornerB;

    }
}

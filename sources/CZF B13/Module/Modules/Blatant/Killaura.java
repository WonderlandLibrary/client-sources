package gq.vapu.czfclient.Module.Modules.Blatant;
// 超

import com.ibm.icu.text.NumberFormat;
import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Misc.EventChat;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.API.Events.World.EventPostUpdate;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Libraries.javax.vecmath.Vector2f;
import gq.vapu.czfclient.Manager.FriendManager;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Combat.AntiBot;
import gq.vapu.czfclient.Module.Modules.Misc.Teams;
import gq.vapu.czfclient.Util.CombatUtil;
import gq.vapu.czfclient.Util.Math.MathUtil;
import gq.vapu.czfclient.Util.Math.RotationUtil;
import gq.vapu.czfclient.Util.PlayerUtil;
import gq.vapu.czfclient.Util.Render.ColorUtils;
import gq.vapu.czfclient.Util.Render.Colors;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Killaura extends Module {
    public static EntityLivingBase curtarget;
    public static float rotationPitch;
    public static ArrayList<EntityLivingBase> targets = new ArrayList();
    public static EntityLivingBase curTarget = null;
    public static EntityLivingBase Target;
    public static Numbers<Double> Turnspeed = new Numbers<Double>("TurnSpeed", "TurnSpeed", 90.0, 1.0, 180.0, 1.0);
    public static float[] facing;
    static boolean allowCrits;
    private static final Numbers<Double> Switchdelay = new Numbers<Double>("Switchdelay", "switchdelay", 11.0, 0.0, 50.0,
            1.0);
    private static final Numbers<Double> aps = new Numbers<Double>("CPS", "CPS", 10.0, 1.0, 20.0, 0.5);
    private static long lastMS, lastMS2;
    private static float sYaw;
    public ArrayList<EntityLivingBase> attackedTargets = new ArrayList();
    public Option<Boolean> autoaim = new Option<Boolean>("AutoAim", "AutoAim", true);
    public Option<Boolean> dash = new Option<Boolean>("Dash Killaura", "Dash", false);
    public Option<Boolean> wall = new Option<Boolean>("Through Wall", "Through Wall", false);
    public Vector2f lastAngle = new Vector2f(0.0F, 0.0F);
    protected ModelBase mainModel;
    TimerUtil kms = new TimerUtil();
    TimerUtil timerUtil = new TimerUtil();
    double yPos;
    boolean direction = true;
    private final Mode<Enum> espmode = new Mode("ESP", "ESP", EMode.values(), EMode.Rainbow);
    private final Mode<Enum> Priority = new Mode("Priority", "Priority", priority.values(), priority.Health);
    private final Mode<Enum> mode = new Mode("Mode", "Mode", AuraMode.values(), AuraMode.Switch);
    private final Mode<Enum> hand = new Mode("Mode", "Mode", handMode.values(), handMode.Vow);
    private final Numbers<Double> crack = new Numbers("CrackSize", "CrackSize", Double.valueOf(1.0D), Double.valueOf(0.0D),
            Double.valueOf(5.0D), Double.valueOf(1.0D));
    private final Numbers<Double> reach = new Numbers<Double>("Ragen", "Ragen", 4.5, 1.0, 6.0, 0.1);
    // private Option<Boolean> targethp = new Option<Boolean>("TargetHP",
    // "TargetHP", false);
    private final Option<Boolean> blocking = new Option<Boolean>("Autoblock", "Autoblock", false);
    private final Option<Boolean> players = new Option<Boolean>("Players", "Players", true);
    private final Option<Boolean> animals = new Option<Boolean>("Animals", "Animals", false);
    private final Option<Boolean> mobs = new Option<Boolean>("Mobs", "Mobs", false);
    private final Option<Boolean> invis = new Option<Boolean>("Invisibles", "Invisibles", false);
    private final TimerUtil timer = new TimerUtil();
    // private Option<Boolean> god = new Option("Velt God Mode", "Velt God Mode",
    // Boolean.valueOf(false));
    private final Option<Boolean> raycast = new Option("Raycast", "Raycast", Boolean.valueOf(true));
    private final TimerUtil test = new TimerUtil();
    private boolean doBlock = false;
    private boolean unBlock = false;
    private long lastMs;
    private float curYaw = 0.0f;
    private float curPitch = 0.0f;
    private final int tick = 0;
    private int index;
    private float[] facing0;
    private float[] facing1;
    private float[] facing2;
    private float[] facing3;

    public Killaura() {
        super("KillAura", new String[]{"ka2", "aura2", "killa2"}, ModuleType.Blatant);
        this.addValues(this.espmode, this.hand, this.Priority, this.mode, aps, Switchdelay, this.reach,
                this.crack, Turnspeed, this.blocking, this.players, this.animals, this.mobs, this.raycast,
                this.invis, this.autoaim, this.wall);
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
        int[] indicies = Killaura.getFractionIndicies(fractions, progress);
        float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
        Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
        float max = range[1] - range[0];
        float value = progress - range[0];
        float weight = value / max;
        return Killaura.blend(colorRange[0], colorRange[1], 1.0f - weight);
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

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
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
        Color color = null;
        try {
            color = new Color(red, green, blue);
        } catch (IllegalArgumentException exp) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println(nf.format(red) + "; " + nf.format(green) + "; " + nf.format(blue));
            exp.printStackTrace();
        }
        return color;
    }

    public static double random(double min, double max) {
        Random random = new Random();
        return min + (int) (random.nextDouble() * (max - min));
    }

    public static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public static boolean hit(long milliseconds) {
        return (getCurrentMS() - lastMS) >= milliseconds;
    }

    public static void revert() {
        lastMS = getCurrentMS();
    }

    private static int randomNumber(double min, double max) {
        Random random = new Random();
        return (int) (min + (random.nextDouble() * (max - min)));
    }

    private static int randomNumber1(double min, double max) {
        Random random = new Random();
        return (int) (min + (random.nextDouble() * (max - min)));
    }

    public static float[] getRotationsNeededBlock(final double n, final double n2, final double n3) {
        final double n4 = n + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double n5 = n3 + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[]{
                Minecraft.getMinecraft().thePlayer.rotationYaw
                        + MathHelper.wrapAngleTo180_float((float) (Math.atan2(n5, n4) * 180.0 / 3.141592653589793)
                        - 90.0f - Minecraft.getMinecraft().thePlayer.rotationYaw),
                Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float) (-Math.atan2(
                        n2 + 0.5 - (Minecraft.getMinecraft().thePlayer.posY
                                + Minecraft.getMinecraft().thePlayer.getEyeHeight()),
                        MathHelper.sqrt_double(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793)
                        - Minecraft.getMinecraft().thePlayer.rotationPitch)};
    }

    public static float[] getRotationFromPosition(final double n, final double n2, final double n3) {
        final double n4 = n - Minecraft.getMinecraft().thePlayer.posX;
        final double n5 = n2 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[]{(float) (Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f,
                (float) (-Math.atan2(n3 - Minecraft.getMinecraft().thePlayer.posY - 1.2,
                        MathHelper.sqrt_double(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793)};
    }

    public static float[] getRotations(final Entity entity) {
        return getRotationFromPosition(entity.posX, entity.posZ, entity.posY + entity.getEyeHeight() / 2.0f);
    }

    public static float[] getRotationToEntity(Entity target) {
        Minecraft.getMinecraft();
        double xDiff = target.posX - mc.thePlayer.posX;
        Minecraft.getMinecraft();
        double yDiff = target.posY - mc.thePlayer.posY;
        Minecraft.getMinecraft();
        double zDiff = target.posZ - mc.thePlayer.posZ;
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        float pitch = (float) (-Math.atan2(
                target.posY + (double) target.getEyeHeight() / 0.0
                        - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight()),
                Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        if (yDiff > -0.2 && yDiff < 0.2) {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            pitch = (float) (-Math.atan2(
                    target.posY + (double) target.getEyeHeight() / HitLocation.CHEST.getOffset()
                            - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight()),
                    Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        } else if (yDiff > -0.2) {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            pitch = (float) (-Math.atan2(
                    target.posY + (double) target.getEyeHeight() / HitLocation.FEET.getOffset()
                            - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight()),
                    Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        } else if (yDiff < 0.3) {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            pitch = (float) (-Math.atan2(
                    target.posY + (double) target.getEyeHeight() / HitLocation.HEAD.getOffset()
                            - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight()),
                    Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        }
        return new float[]{yaw, pitch};
    }

    public static float getYawDifference(float current, float target) {
        float rot = 0;
        return rot + ((rot = (target + 180.0f - current) % 360.0f) > 0.0f ? -180.0f : 180.0f);
    }

    public void color(int color) {
        float f = (float) (color >> 24 & 255) / 255.0f;
        float f2 = (float) (color >> 16 & 255) / 255.0f;
        float f3 = (float) (color >> 8 & 255) / 255.0f;
        float f4 = (float) (color & 255) / 255.0f;
        GL11.glColor4f(f2, f3, f4, f);
    }

    public void drawRect(double x1, double y1, double x2, double y2, int color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        this.color(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor,
                                  int borderColor) {
        this.drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawRect(x + width, y, x1 - width, y + width, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawRect(x, y, x + width, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawRect(x1 - width, y, x1, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private boolean shouldAttack() {
        return this.timer.hasReached(1000 / aps.getValue().intValue());
    }

    @EventHandler

    private void renderHud(EventRender2D event) {

    }

    @EventHandler
    private void render(EventRender3D e) {

        if (curTarget == null || this.espmode.getValue() == EMode.None) {
            return;
        }
        Color color = new Color(255, 255, 255, 120);
        if (Killaura.curTarget.hurtResistantTime > 0) {
            color = new Color(Colors.RED.c);
        }
        if (curTarget != null) {
            if (this.espmode.getValue() == EMode.Box) {
                mc.getRenderManager();
                double x = Killaura.curTarget.lastTickPosX + (Killaura.curTarget.posX - Killaura.curTarget.lastTickPosX)
                        * (double) Killaura.mc.timer.renderPartialTicks - RenderManager.renderPosX;
                mc.getRenderManager();
                double y = Killaura.curTarget.lastTickPosY + (Killaura.curTarget.posY - Killaura.curTarget.lastTickPosY)
                        * (double) Killaura.mc.timer.renderPartialTicks - RenderManager.renderPosY;
                mc.getRenderManager();
                double z = Killaura.curTarget.lastTickPosZ + (Killaura.curTarget.posZ - Killaura.curTarget.lastTickPosZ)
                        * (double) Killaura.mc.timer.renderPartialTicks - RenderManager.renderPosZ;

                double width = Killaura.curTarget.getEntityBoundingBox().maxX
                        - Killaura.curTarget.getEntityBoundingBox().minX - 0.2;
                double height = Killaura.curTarget.getEntityBoundingBox().maxY
                        - Killaura.curTarget.getEntityBoundingBox().minY + 0.05;
                if (Killaura.curTarget.hurtTime > 5) {
                    float red = 1.0f;
                    float green = 0.0f;
                    float blue = 0.0f;
                    RenderUtil.drawEntityESP(x, y, z, width, height, red, green, blue, 0.5f, red, green, blue, 1, 2);
                } else {
                    float red = 0.0f;
                    float green = 1.0f;
                    float blue = 0.0f;
                    RenderUtil.drawEntityESP(x, y, z, width, height, red, green, blue, 0.5f, red, green, blue, 1, 2);
                }

            } else if (this.espmode.getValue() == EMode.Liquidbounce) {
                mc.getRenderManager();
                double x = Killaura.curTarget.lastTickPosX + (Killaura.curTarget.posX - Killaura.curTarget.lastTickPosX)
                        * (double) Killaura.mc.timer.renderPartialTicks - RenderManager.renderPosX;
                mc.getRenderManager();
                double y = Killaura.curTarget.lastTickPosY + (Killaura.curTarget.posY - Killaura.curTarget.lastTickPosY)
                        * (double) Killaura.mc.timer.renderPartialTicks - RenderManager.renderPosY;
                mc.getRenderManager();
                double z = Killaura.curTarget.lastTickPosZ + (Killaura.curTarget.posZ - Killaura.curTarget.lastTickPosZ)
                        * (double) Killaura.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
                if (curTarget instanceof EntityPlayer) {
                    double d = curTarget.isSneaking() ? 0.25 : 0.0;
                    double mid = 0.5;
                    GL11.glPushMatrix();
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    double rotAdd = -0.25 * (double) (Math.abs(Killaura.curTarget.rotationPitch) / 90.0f);
                    GL11.glTranslated((x -= 0.5) + 0.5,
                            (y += (double) curTarget.getEyeHeight() + 0.35 - d) + 0.5,
                            (z -= 0.5) + 0.5);
                    GL11.glRotated(-Killaura.curTarget.rotationYaw % 360.0f, 0.0, 1.0,
                            0.0);
                    GL11.glTranslated(-(x + 0.5), -(y + 0.5), -(z + 0.5));
                    GL11.glDisable(3553);
                    GL11.glEnable(2848);
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glColor4f((float) color.getRed() / 255.0f,
                            (float) color.getGreen() / 255.0f, (float) color.getBlue() / 255.0f,
                            0.5f);
                    RenderUtil.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 0.05, z + 1.0));
                    GL11.glDisable(2848);
                    GL11.glEnable(3553);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    GL11.glDisable(3042);
                    GL11.glPopMatrix();
                } else {
                    double width = Killaura.curTarget.getEntityBoundingBox().maxX
                            - Killaura.curTarget.getEntityBoundingBox().minX - 0.2;
                    if (Killaura.curTarget.hurtTime > 5) {
                        float red = 1.0f;
                        float green = 0.0f;
                        float blue = 0.0f;
                        RenderUtil.drawEntityESP(x, y + curTarget.getEyeHeight() + 0.25, z, width, 0.1, red, green,
                                blue, 0.5f, red, green, blue, 1, 2);
                    } else {
                        float red = 0.0f;
                        float green = 1.0f;
                        float blue = 0.0f;
                        RenderUtil.drawEntityESP(x, y + curTarget.getEyeHeight() + 0.25, z, width, 0.1, red, green,
                                blue, 0.5f, red, green, blue, 1, 2);
                    }

                }
            }

            if (curTarget == null || this.espmode.getValue() == EMode.Vape) {
                mc.getRenderManager();
                double x1 = Killaura.curTarget.lastTickPosX
                        + (Killaura.curTarget.posX - Killaura.curTarget.lastTickPosX)
                        * (double) Killaura.mc.timer.renderPartialTicks
                        - RenderManager.renderPosX;
                mc.getRenderManager();
                double y1 = Killaura.curTarget.lastTickPosY
                        + (Killaura.curTarget.posY - Killaura.curTarget.lastTickPosY)
                        * (double) Killaura.mc.timer.renderPartialTicks
                        - RenderManager.renderPosY;
                mc.getRenderManager();
                double z1 = Killaura.curTarget.lastTickPosZ
                        + (Killaura.curTarget.posZ - Killaura.curTarget.lastTickPosZ)
                        * (double) Killaura.mc.timer.renderPartialTicks
                        - RenderManager.renderPosZ;

                double width = Killaura.curTarget.getEntityBoundingBox().maxX
                        - Killaura.curTarget.getEntityBoundingBox().minX - 0.2;
                double height = Killaura.curTarget.getEntityBoundingBox().maxY
                        - Killaura.curTarget.getEntityBoundingBox().minY + 0.05;
                float red = 220 - Killaura.curTarget.hurtTime * 5 / 255.0f;
                float green = Killaura.curTarget.hurtTime * 10 / 255.0f;
                float blue = Killaura.curTarget.hurtTime * 2 / 255.0f;
                float alpha = (float) (80 + Killaura.curTarget.hurtTime * 10) / 255.0f;
                RenderUtil.drawEntityESP(x1, y1, z1, width, height, red, green, blue, alpha, red, green, blue, 1, 0);

            }
            if (curTarget == null || this.espmode.getValue() == EMode.Rainbow) {
                mc.getRenderManager();
                double x1 = Killaura.curTarget.lastTickPosX
                        + (Killaura.curTarget.posX - Killaura.curTarget.lastTickPosX)
                        * (double) Killaura.mc.timer.renderPartialTicks
                        - RenderManager.renderPosX;
                mc.getRenderManager();
                double y1 = Killaura.curTarget.lastTickPosY
                        + (Killaura.curTarget.posY - Killaura.curTarget.lastTickPosY)
                        * (double) Killaura.mc.timer.renderPartialTicks
                        - RenderManager.renderPosY;
                mc.getRenderManager();
                double z1 = Killaura.curTarget.lastTickPosZ
                        + (Killaura.curTarget.posZ - Killaura.curTarget.lastTickPosZ)
                        * (double) Killaura.mc.timer.renderPartialTicks
                        - RenderManager.renderPosZ;

                float blue;
                float green;
                float red;
                float alpha;

                double width = Killaura.curTarget.getEntityBoundingBox().maxX
                        - Killaura.curTarget.getEntityBoundingBox().minX - 0.2;
                double height = Killaura.curTarget.getEntityBoundingBox().maxY
                        - Killaura.curTarget.getEntityBoundingBox().minY + 0.05;
                if (Killaura.curTarget.hurtTime == 10) {
                    red = ColorUtils.getRainbow().getRed() / 255.0f;
                    green = ColorUtils.getRainbow().getGreen() / 255.0f;
                    blue = ColorUtils.getRainbow().getBlue() / 255.0f;
                    // float alpha = 160.0f;
                    alpha = (float) (80 + Killaura.curTarget.hurtTime * 10) / 255.0f;
                } else {
                    red = ColorUtils.getRainbow().getRed() / 255.0f;
                    green = ColorUtils.getRainbow().getGreen() / 255.0f;
                    blue = ColorUtils.getRainbow().getBlue() / 255.0f;

                    alpha = (float) (80 + Killaura.curTarget.hurtTime * 10) / 255.0f;
                }
                RenderUtil.drawEntityESP(x1, y1, z1, width, height, red, green, blue, alpha, red, green, blue, 1, 0);
            }
            if (curTarget == null || this.espmode.getValue() == EMode.Jello) {
                drawShadow(curTarget, e.getPartialTicks(), (float) yPos, direction);
                drawCircle(curTarget, e.getPartialTicks(), (float) yPos);
            }

        }

    }

    @EventHandler
    private void onRender2D(EventRender2D e) {
        if (timerUtil.delay(10)) {
            if (direction) {
                yPos += 0.03;
                if (2 - yPos < 0.02) {
                    direction = false;
                }
            } else {
                yPos -= 0.03;
                if (yPos < 0.02) {
                    direction = true;
                }
            }
            timerUtil.reset();
        }
    }

    private void drawShadow(Entity entity, float partialTicks, float pos, boolean direction) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(7425);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks - mc.getRenderManager().viewerPosY + pos;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks - mc.getRenderManager().viewerPosZ;
        GL11.glBegin(GL11.GL_QUAD_STRIP);
        for (int i = 0; i <= 180; i++) {
            double c1 = i * Math.PI * 2 / 180;
            double c2 = (i + 1) * Math.PI * 2 / 180;
            GlStateManager.color(1, 1, 1, 0.3f);
            GL11.glVertex3d(x + 0.5 * Math.cos(c1), y, z + 0.5 * Math.sin(c1));
            GL11.glVertex3d(x + 0.5 * Math.cos(c2), y, z + 0.5 * Math.sin(c2));
            GlStateManager.color(1, 1, 1, 0f);

            GL11.glVertex3d(x + 0.5 * Math.cos(c1), y + (direction ? -0.2 : 0.2), z + 0.5 * Math.sin(c1));
            GL11.glVertex3d(x + 0.5 * Math.cos(c2), y + (direction ? -0.2 : 0.2), z + 0.5 * Math.sin(c2));


        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(7424);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void drawCircle(Entity entity, float partialTicks, float pos) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(7425);
        GL11.glLineWidth(1);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks - mc.getRenderManager().viewerPosY + pos;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks - mc.getRenderManager().viewerPosZ;
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i <= 180; i++) {
            double c1 = i * Math.PI * 2 / 180;
            GlStateManager.color(2, 1, 1, 1);
            GL11.glVertex3d(x + 0.5 * Math.cos(c1), y, z + 0.5 * Math.sin(c1));


        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(7424);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private boolean canBlock() {
        return mc.thePlayer.getCurrentEquippedItem() != null
                && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
    }

    public Entity raycast5(Entity fromEntity) {
        /* 178 */
        if (this.raycast.getValue().booleanValue())
            /* 179 */ for (Entity en2 : mc.theWorld.loadedEntityList) {
            /* 180 */
            if (en2 == mc.thePlayer || en2.equals(mc.thePlayer) || en2 == fromEntity
                    || en2.equals(fromEntity)
                    || (!en2.isInvisible() && !(en2 instanceof net.minecraft.entity.item.EntityArmorStand))
                    || !en2.boundingBox.intersectsWith(fromEntity.boundingBox))
                /*     */ continue;
            /* 181 */
            return en2;
            /*     */
        }
        /* 184 */
        return fromEntity;
        /*     */
    }

    @EventHandler
    private void onUpdate(final EventPreUpdate event) {
        this.setSuffix(this.mode.getValue());
        if (mc.thePlayer.ticksExisted % Switchdelay.getValue().intValue() == 0 && targets.size() > 1) {
            ++this.index;
        }
        if (!targets.isEmpty() && this.index >= targets.size()) {
            this.index = 0;
        }
        if (this.autoaim.getValue().booleanValue()) {
            if (curTarget != null) {
                if (!RotationUtil.canEntityBeSeen(curTarget))
                    return;
                float[] rotations = CombatUtil.getRotations(curTarget);

                if (mc.thePlayer.ticksExisted % 50 == 0 && targets.size() > 1) {
                    ++this.index;
                }
                mc.thePlayer.rotationYawHead = rotations[0];
                mc.thePlayer.rotationYaw = rotations[0];
            }
        } else {

        }

        this.doBlock = false;
        this.clear();
        this.findTargets(event);
        this.setCurTarget();
        if (this.hand.getValue() == handMode.Vow) {
            if (curTarget != null) {
                Random rand = new Random();
                this.facing0 = Killaura.getRotationsNeededBlock(Killaura.curTarget.posX, Killaura.curTarget.posY,
                        Killaura.curTarget.posZ);
                this.facing1 = Killaura.getRotationFromPosition(Killaura.curTarget.posX, Killaura.curTarget.posY,
                        Killaura.curTarget.posZ);
                this.facing2 = Killaura.getRotationsNeededBlock(Killaura.curTarget.posX, Killaura.curTarget.posY,
                        Killaura.curTarget.posZ);
                this.facing3 = Killaura.getRotations(curTarget);
                for (int i2 = 0; i2 <= 3; ++i2) {
                    switch (Killaura.randomNumber(0.0, i2)) {
                        case 0: {
                            facing = this.facing0;
                        }
                        case 1: {
                            facing = this.facing1;
                        }
                        case 2: {
                            facing = this.facing2;
                        }
                        case 3: {
                            facing = this.facing3;
                        }
                    }
                }
                if (facing.length >= 0) {
                    event.setYaw(facing[0]);
                    event.setPitch(facing[1]);
                }
                if (curTarget != null) {
                    mc.thePlayer.renderYawOffset = facing[0];
                    mc.thePlayer.rotationYawHead = facing[0];
                }
            } else {
                targets.clear();
                this.attackedTargets.clear();
                this.lastMs = System.currentTimeMillis();
                if (this.unBlock) {
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    mc.thePlayer.itemInUseCount = 0;
                    this.unBlock = false;
                }
            }
        }
        if (this.hand.getValue() == handMode.Nov) {
            if (Killaura.curTarget != null) {
                final Random rand = new Random();
                this.facing0 = getRotationsNeededBlock(Killaura.curTarget.posX, Killaura.curTarget.posY,
                        Killaura.curTarget.posZ);
                this.facing1 = getRotationFromPosition(Killaura.curTarget.posX, Killaura.curTarget.posY,
                        Killaura.curTarget.posZ);
                this.facing2 = getRotationsNeededBlock(Killaura.curTarget.posX, Killaura.curTarget.posY,
                        Killaura.curTarget.posZ);
                this.facing3 = getRotations(Killaura.curTarget);
                for (int i = 0; i <= 3; ++i) {
                    switch (randomNumber(0.0, i)) {
                        case 0: {
                            Killaura.facing = this.facing0;
                        }
                        case 1: {
                            Killaura.facing = this.facing1;
                        }
                        case 2: {
                            Killaura.facing = this.facing2;
                        }
                        case 3: {
                            Killaura.facing = this.facing3;
                            break;
                        }
                    }
                }
                if (Killaura.facing.length >= 0) {
                    Turnspeed.getValue().intValue();
                    event.setYaw(Killaura.facing[0]);
                    Turnspeed.getValue().intValue();
                    event.setPitch(Killaura.facing[1]);
                }

                if (Killaura.curTarget != null) {
                    final Minecraft mc = Killaura.mc;
                    mc.thePlayer.renderYawOffset = Killaura.facing[0];
                    final Minecraft mc2 = Killaura.mc;
                    mc.thePlayer.rotationYawHead = Killaura.facing[0];
                }
                final int maxAngleStep = Turnspeed.getValue().intValue();
                final float[] rotations = RotationUtil.要不要把你妈卵子扣出来给你做寿司吃(Killaura.curTarget, 1000.0f, 1000.0f, false);
                final int xz = (int) (randomNumber1(maxAngleStep, maxAngleStep) / 100.0);
                float targetYaw = RotationUtil.你妈av公众于世啦快去看看(Killaura.sYaw, Killaura.curTarget.posX,
                        Killaura.curTarget.posZ);
                Random rand1 = new Random();
                facing0 = getRotationsNeededBlock(curTarget.posX, curTarget.posY, curTarget.posZ);
                facing1 = getRotationFromPosition(curTarget.posX, curTarget.posY, curTarget.posZ);
                facing2 = getRotationsNeededBlock(curTarget.posX, curTarget.posY, curTarget.posZ);
                facing3 = getRotations(curTarget);
                int i;
                for (i = 0; i <= 3; i++) {
                    switch (randomNumber1(0, i)) {
                        case 0:
                            facing = facing0;
                        case 1:
                            facing = facing1;
                        case 2:
                            facing = facing2;
                        case 3:
                            facing = facing3;
                    }
                }

                if (facing.length >= 0) {
                    event.setYaw((facing[0]));
                    event.setPitch(facing[1]);
                }
                if (Killaura.curTarget != null) {
                    mc.thePlayer.renderYawOffset = facing[0];
                    mc.thePlayer.rotationYawHead = facing[0];
                }
            } else {
                targets.clear();
                this.attackedTargets.clear();
                this.lastMs = System.currentTimeMillis();
                if (this.unBlock) {
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                            C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    mc.thePlayer.itemInUseCount = 0;
                    this.unBlock = false;
                }
            }
        }
    }

    private void doAttack() {
        int aps = Killaura.aps.getValue().intValue();
        int delayValue = (int) (1000 / Killaura.aps.getValue().intValue() + MathUtil.randomDouble(-1.0, 2.0));
        if ((double) mc.thePlayer.getDistanceToEntity(curTarget) <= this.reach.getValue() + 0.4 && this.tick == 0
                && this.test.delay(delayValue - 1)) {
            boolean miss = false;
            this.test.reset();

            if (mc.thePlayer.isBlocking() || mc.thePlayer.getHeldItem() != null
                    && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword
                    && this.blocking.getValue().booleanValue()) {
                NoSlow ns = (NoSlow) ModuleManager.getModuleByClass(NoSlow.class);
                double speed = ns.isEnabled() ? 1.0D : 0.2D;
                MovementInput.moveStrafe = (float) ((double) MovementInput.moveStrafe * speed);
                MovementInput.moveForward = (float) ((double) MovementInput.moveForward * speed);
                if (new Random().nextBoolean()) {
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                            C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
                this.unBlock = false;
            }
            if (!mc.thePlayer.isBlocking() && !this.blocking.getValue().booleanValue()
                    && mc.thePlayer.itemInUseCount > 0) {
                mc.thePlayer.itemInUseCount = 0;
            }

            this.attack(miss);
            this.doBlock = true;
            if (!miss) {
                for (Object o : mc.theWorld.loadedEntityList) {
                    EntityLivingBase entity;
                    if (!(o instanceof EntityLivingBase) || !this.isValidEntity(entity = (EntityLivingBase) o))
                        continue;
                    this.attackedTargets.add(curTarget);
                }
            }
        }

    }

    private void swap(int slot, int hotbarNum) {
        Minecraft.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2,
                mc.thePlayer);
    }

    @EventHandler
    public void onPost(EventPostUpdate event) {
        this.sortList(targets);
        if (curTarget != null) {
            if (!RotationUtil.canEntityBeSeen(curTarget))
                if (!this.wall.getValue().booleanValue()) {
                    targets.clear();
                    this.attackedTargets.clear();
                    curTarget = null;
                    mc.thePlayer.itemInUseCount = 0;
                    allowCrits = true;
                    mc.thePlayer.renderYawOffset = mc.thePlayer.rotationYaw;
                    rotationPitch = 0.0f;
                    this.curYaw = mc.thePlayer.rotationYaw;
                    this.curPitch = mc.thePlayer.rotationPitch;
                }
            this.doAttack();
            this.newAttack();
        }
        int crackSize = this.crack.getValue().intValue();

        if (curTarget != null
                && (mc.thePlayer.getHeldItem() != null
                && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword
                && this.blocking.getValue().booleanValue() || mc.thePlayer.isBlocking())
                && this.doBlock) {
            mc.thePlayer.itemInUseCount = mc.thePlayer.getHeldItem().getMaxItemUseDuration();
            mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255,
                    mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
            this.unBlock = true;
        }

        int i2 = 0;
        /* 251 */
        while (i2 < crackSize && curTarget != null) {
            /* 252 */
            mc.effectRenderer.emitParticleAtEntity(curTarget, EnumParticleTypes.CRIT);
            /* 253 */
            mc.effectRenderer.emitParticleAtEntity(curTarget, EnumParticleTypes.CRIT_MAGIC);
            /* 254 */
            i2++;
            /*     */
        }
    }

    private void attack(boolean fake) {
        mc.thePlayer.swingItem();
        if (!fake) {
            this.doBlock = true;
            if (ModuleManager.getModuleByClass(Criticals.class).isEnabled()) {
                if (mc.thePlayer.onGround && !Criticals.isOnWater()
                        && !Criticals.isInLiquid() && !ModuleManager.getModuleByClass(Fly.class).isEnabled()
                        && PlayerUtil.isMoving2() == true && curTarget.hurtTime <= 1) {

                    double x = mc.thePlayer.posX;
                    double y = mc.thePlayer.posY;
                    double z = mc.thePlayer.posZ;
//					mc.thePlayer.sendQueue
//							.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.06253, z, false));
//					mc.thePlayer.sendQueue
//							.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.06254, z, false));
//					mc.thePlayer.sendQueue
//							.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.05, z, false));

                    /*
                     * Criticals.mc.thePlayer.sendQueue.addToSendQueue( (Packet) new
                     * C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX,
                     * Criticals.mc.thePlayer.posY + 0.0624218713251234 + Math.random() * 2.0 /
                     * 1000.0, Criticals.mc.thePlayer.posZ, false));
                     * Criticals.mc.thePlayer.sendQueue.addToSendQueue( (Packet) new
                     * C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX,
                     * Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, false));
                     * Criticals.mc.thePlayer.sendQueue.addToSendQueue( (Packet) new
                     * C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX,
                     * Criticals.mc.thePlayer.posY + 0.012511000037193298 + Math.random() * 2.0 /
                     * 10000.0, Criticals.mc.thePlayer.posZ, false));
                     * Criticals.mc.thePlayer.sendQueue.addToSendQueue( (Packet) new
                     * C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX,
                     * Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, false));
                     */
                }
            }
            mc.thePlayer.sendQueue.addToSendQueue(
                    new C02PacketUseEntity(Killaura.curTarget, C02PacketUseEntity.Action.ATTACK));
            if (mc.thePlayer.isBlocking() && this.blocking.getValue().booleanValue()
                    && mc.thePlayer.inventory.getCurrentItem() != null
                    && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                if (new Random().nextBoolean())
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255,
                            mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                this.unBlock = true;
            }
            if (!mc.thePlayer.isBlocking() && !this.blocking.getValue().booleanValue()
                    && mc.thePlayer.itemInUseCount > 0) {
                mc.thePlayer.itemInUseCount = 0;
            }
        }
    }

    private void newAttack() {
        if (mc.thePlayer.isBlocking()) {
            for (int i = 0; i <= 2; i++) {
                if (new Random().nextBoolean())
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255,
                            mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
            }
        }
        if (mc.thePlayer.isBlocking()) {
            for (int i = 0; i <= 2; i++) {
                if (new Random().nextBoolean())
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255,
                            mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
            }
        }


        if (mc.thePlayer.isBlocking() && this.timer.delay(100)) {
            for (int i = 0; i <= 2; i++) {
                mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN
                ));

            }
        }
        if (!mc.thePlayer.isBlocking() && !this.blocking.getValue().booleanValue()
                && mc.thePlayer.itemInUseCount > 0) {
            mc.thePlayer.itemInUseCount = 0;
        }
    }

    private void setCurTarget() {
        if (targets.size() == 0) {
            curTarget = null;
            return;
        }
        curTarget = targets.get(index);
    }

    private void clear() {
        curTarget = null;
        targets.clear();
        for (EntityLivingBase ent : targets) {
            if (this.isValidEntity(ent))
                continue;
            targets.remove(ent);
            if (!this.attackedTargets.contains(ent))
                continue;
            this.attackedTargets.remove(ent);
        }
    }

    private void findTargets(EventPreUpdate event) {
        int maxSize = this.mode.getValue() == AuraMode.Switch ? 4 : 1;
        for (Entity o3 : mc.theWorld.loadedEntityList) {
            EntityLivingBase curEnt;
            if (o3 instanceof EntityLivingBase && this.isValidEntity(curEnt = (EntityLivingBase) o3)
                    && !targets.contains(curEnt)) {
                targets.add(curEnt);
            }
            if (targets.size() >= maxSize)
                break;
        }
        targets.sort((o1, o2) -> (int) (o1.getDistanceToEntity(o2) - o2.getDistanceToEntity(o1)));
    }

    private boolean isValidEntity(EntityLivingBase ent) {
        AntiBot ab = (AntiBot) ModuleManager.getModuleByClass(AntiBot.class);
        Target = ent;
        return ent != null && (ent != mc.thePlayer && ((!(ent instanceof EntityPlayer) || this.players.getValue()) && (((!(ent instanceof EntityAnimal) && !(ent instanceof EntitySquid))
                || this.animals.getValue()) && (((!(ent instanceof EntityMob) && !(ent instanceof EntityVillager)
                && !(ent instanceof EntityBat)) || this.mobs.getValue()) && (!((double) mc.thePlayer.getDistanceToEntity(
                ent) > this.reach.getValue() + 0.4) && ((!(ent instanceof EntityPlayer)
                || !FriendManager
                .isFriend(ent.getName())) && (!ent.isDead
                && ent.getHealth() > 0.0F && ((!ent.isInvisible()
                || this.invis
                .getValue()) && !ab.isServerBot(
                ent) && (!this.mc.thePlayer.isDead && (!(ent instanceof EntityPlayer)
                || !Teams
                .isOnSameTeam(
                        ent)))))))))));
    }

    @Override
    public void onEnable() {
//        Helper.sendMessage("请勿开启Auto Block，Auto Block无法绕过大部分反作弊");
        boolean isFristOpenGLBug = false;
        index = 0;
        this.curYaw = mc.thePlayer.rotationYaw;
        this.curPitch = mc.thePlayer.rotationPitch;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        targets.clear();
        this.attackedTargets.clear();
        curTarget = null;
        mc.thePlayer.itemInUseCount = 0;
        allowCrits = true;
        mc.thePlayer.renderYawOffset = mc.thePlayer.rotationYaw;
        rotationPitch = 0.0f;
        this.curYaw = mc.thePlayer.rotationYaw;
        this.curPitch = mc.thePlayer.rotationPitch;

        super.onDisable();
    }

    private void sortList(List<EntityLivingBase> weed) {
        if (this.Priority.getValue() == priority.Range) {
            weed.sort((o1, o2) -> (int) (o1.getDistanceToEntity(mc.thePlayer) - o2.getDistanceToEntity(mc.thePlayer)));
        }
        if (this.Priority.getValue() == priority.Fov) {
            weed.sort(Comparator.comparingDouble(o -> RotationUtil.主播没有技术主播妈妈死了主播不得好死忘了主播没有妈(mc.thePlayer.rotationPitch,
                    Killaura.getRotationToEntity(o)[0])));
        }
        if (this.Priority.getValue() == priority.Angle) {
            weed.sort((o1, o2) -> {
                float[] rot1 = getRotationToEntity(o1);
                float[] rot2 = getRotationToEntity(o2);
                return (int) (mc.thePlayer.rotationYaw - rot1[0] - (mc.thePlayer.rotationYaw - rot2[0]));
            });
        }
        if (this.Priority.getValue() == priority.Health) {
            weed.sort((o1, o2) -> (int) (o1.getHealth() - o2.getHealth()));
        }
    }

    @EventHandler
    public void onChat(EventChat e) {
        if (e.getMessage().contains("§eOpenGL错误§f：1281（Invalid value）")) {
            e.setCancelled(true);
        }
    }

    private float getYawDifference(float yaw, EntityLivingBase target) {
        return Killaura.getYawDifference(yaw, Killaura.getRotationToEntity(target)[0]);
    }

    enum HitLocation {
        AUTO(0.0), HEAD(1.0), CHEST(1.5), FEET(3.5);

        private final double offset;

        HitLocation(double offset) {
            this.offset = offset;
        }

        public double getOffset() {
            return this.offset;
        }
    }

    enum EMode {
        None, Box, Liquidbounce, Vape, Rainbow, Jello
    }

    enum priority {
        Range, Fov, Angle, Health
    }

    enum AuraMode {
        Switch, Single
    }

    enum handMode {
        Vow, Nov
    }
}

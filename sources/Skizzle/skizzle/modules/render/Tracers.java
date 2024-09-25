/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventRender3D;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.friends.Friend;
import skizzle.friends.FriendManager;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.settings.BooleanSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.BlockUtil;
import skizzle.util.ColourUtil;
import skizzle.util.RenderUtil;

public class Tracers
extends Module {
    public static float var5;
    public Minecraft mc = Minecraft.getMinecraft();
    public BooleanSetting chroma;
    public static float var2;
    public NumberSetting red;
    public BooleanSetting dist;
    public NumberSetting alpha;
    public NumberSetting lineWidth;
    public static float var4;
    public NumberSetting blue;
    public int colour = -16748374;
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u6e8c\u71c4\u55d1\ua7e1"), Qprot0.0("\u6e8d\u71c2\u55db\ua7e1\u4065"), Qprot0.0("\u6e8d\u71c2\u55db\ua7e1\u4065"), Qprot0.0("\u6e80\u71d9\u55c7\ua7eb\u4061\ueb08"));
    public static float var1;
    public static float var3;
    public NumberSetting green;

    @Override
    public void onEvent(Event Nigga) {
        Tracers Nigga2;
        if (Nigga instanceof EventRender3D) {
            float Nigga4 = ((EventRender3D)Nigga).getPartialTicks();
            for (Object Nigga5 : Minecraft.theWorld.loadedEntityList) {
                if (!(Nigga5 instanceof EntityLivingBase)) continue;
                EntityLivingBase Nigga6 = (EntityLivingBase)Nigga5;
                boolean Nigga3 = true;
                GL11.glLineWidth((float)((float)Nigga2.lineWidth.getValue()));
                boolean Nigga7 = false;
                if (Nigga6 instanceof EntityPlayer) {
                    Nigga7 = ((EntityPlayer)Nigga6).isFake;
                }
                if (Nigga7) continue;
                for (Friend friend : FriendManager.friends) {
                    if (!friend.getName().equals(Nigga6.getName())) continue;
                    Nigga3 = false;
                    GlStateManager.color(Float.intBitsToFloat(2.1187639E9f ^ 0x7E49C5A7), Float.intBitsToFloat(2.11959194E9f ^ 0x7E566803), Float.intBitsToFloat(1.10101696E9f ^ 0x7ED31CA9), Float.intBitsToFloat(1.08081613E9f ^ 0x7F4D947B));
                    RenderUtil.drawSmoothTracer(Nigga6, Nigga4);
                }
                if (ModuleManager.hackerDetector.hackers.contains(Nigga6.getName())) {
                    GlStateManager.color(Float.intBitsToFloat(1.09092096E9f ^ 0x7E75113F), Float.intBitsToFloat(2.13057792E9f ^ 0x7EFE0A33), Float.intBitsToFloat(2.1308535E9f ^ 0x7F023EA8), (float)(Nigga2.alpha.getValue() / 255.0));
                    RenderUtil.drawSmoothTracer(Nigga6, Nigga4);
                    Nigga3 = false;
                }
                if (Nigga3 && ModuleManager.targeting.isTarget(Nigga6) && !(Nigga6 instanceof EntityArmorStand) && Nigga6 != Nigga2.mc.thePlayer) {
                    GlStateManager.color((float)Nigga2.red.getValue() / Float.intBitsToFloat(1.01407706E9f ^ 0x7F0E9698), (float)Nigga2.green.getValue() / Float.intBitsToFloat(1.0124464E9f ^ 0x7F27B4BA), (float)Nigga2.blue.getValue() / Float.intBitsToFloat(1.04862202E9f ^ 0x7DFFB3DF), (float)(Nigga2.alpha.getValue() / 255.0));
                    if (Nigga2.dist.isEnabled()) {
                        GlStateManager.color((float)ColourUtil.cleanSwitch(Nigga2.mc.thePlayer.getDistanceToEntity(Nigga6) / Float.intBitsToFloat(1.0585191E9f ^ 0x7D37B82F))[0] / Float.intBitsToFloat(1.00693056E9f ^ 0x7F7B8A8D), (float)ColourUtil.cleanSwitch(Nigga2.mc.thePlayer.getDistanceToEntity(Nigga6) / Float.intBitsToFloat(1.02612531E9f ^ 0x7F096E0E))[1] / Float.intBitsToFloat(1.0513015E9f ^ 0x7DD69667), Float.intBitsToFloat(2.1388768E9f ^ 0x7F7CAB9B), (float)Nigga2.alpha.getValue() / Float.intBitsToFloat(1.01043494E9f ^ 0x7F45038D));
                    }
                    if (Nigga2.chroma.isEnabled()) {
                        Color color = new Color(Client.RGBColor);
                        GlStateManager.color((float)color.getRed() / Float.intBitsToFloat(1.04930323E9f ^ 0x7DF418B7), (float)color.getGreen() / Float.intBitsToFloat(1.03579674E9f ^ 0x7EC20101), (float)color.getBlue() / Float.intBitsToFloat(1.00705907E9f ^ 0x7F798073), (float)(Nigga2.alpha.getValue() / 255.0));
                    }
                    RenderUtil.drawSmoothTracer(Nigga6, Nigga4);
                }
                GL11.glLineWidth((float)Float.intBitsToFloat(1.09042803E9f ^ 0x7F7E9C6E));
            }
        }
        if (Nigga instanceof EventRenderGUI && Nigga2.mode.getMode().equals(Qprot0.0("\u6e80\u71d9\u55c7\ue262\u8afe\ueb08"))) {
            for (Object Nigga9 : Minecraft.theWorld.loadedEntityList) {
                if (!(Nigga9 instanceof EntityLivingBase)) continue;
                EntityLivingBase Nigga10 = (EntityLivingBase)Nigga9;
                boolean Nigga11 = true;
                GL11.glLineWidth((float)((float)Nigga2.lineWidth.getValue()));
                boolean Nigga3 = false;
                if (Nigga10 instanceof EntityPlayer) {
                    Nigga3 = ((EntityPlayer)Nigga10).isFake;
                }
                if (Nigga3) continue;
                for (Friend Nigga12 : FriendManager.friends) {
                    if (!Nigga12.getName().equals(Nigga10.getName())) continue;
                    Nigga11 = false;
                    GlStateManager.color(Float.intBitsToFloat(2.11418086E9f ^ 0x7E03D6EB), Float.intBitsToFloat(2.1338217E9f ^ 0x7F2F88CF), Float.intBitsToFloat(1.0764032E9f ^ 0x7F5BA8C9), Float.intBitsToFloat(1.09432947E9f ^ 0x7E1C422B));
                }
                if (ModuleManager.hackerDetector.hackers.contains(Nigga10.getName())) {
                    GlStateManager.color(Float.intBitsToFloat(1.07429286E9f ^ 0x7F7B5B9E), Float.intBitsToFloat(2.11346214E9f ^ 0x7DF8DFAF), Float.intBitsToFloat(2.13811392E9f ^ 0x7F71077C), (float)(Nigga2.alpha.getValue() / 255.0));
                    Nigga11 = false;
                }
                if (Nigga11 && ModuleManager.targeting.isTarget(Nigga10) && !(Nigga10 instanceof EntityArmorStand) && Nigga10 != Nigga2.mc.thePlayer) {
                    GlStateManager.color((float)Nigga2.red.getValue() / Float.intBitsToFloat(1.04940083E9f ^ 0x7DF395FF), (float)Nigga2.green.getValue() / Float.intBitsToFloat(1.03396998E9f ^ 0x7EDE213D), (float)Nigga2.blue.getValue() / Float.intBitsToFloat(1.03769907E9f ^ 0x7EA507FB), (float)(Nigga2.alpha.getValue() / 255.0));
                    if (Nigga2.dist.isEnabled()) {
                        GlStateManager.color((float)ColourUtil.cleanSwitch(Nigga2.mc.thePlayer.getDistanceToEntity(Nigga10) / Float.intBitsToFloat(1.02134912E9f ^ 0x7EC08CFF))[0] / Float.intBitsToFloat(1.01445306E9f ^ 0x7F085328), (float)ColourUtil.cleanSwitch(Nigga2.mc.thePlayer.getDistanceToEntity(Nigga10) / Float.intBitsToFloat(1.02224122E9f ^ 0x7ECE29AB))[1] / Float.intBitsToFloat(1.01246957E9f ^ 0x7F260F39), Float.intBitsToFloat(2.1267063E9f ^ 0x7EC2F651), (float)Nigga2.alpha.getValue() / Float.intBitsToFloat(1.05309549E9f ^ 0x7DBBF62F));
                    }
                    if (Nigga2.chroma.isEnabled()) {
                        Color Nigga13 = new Color(Client.RGBColor);
                        GlStateManager.color((float)Nigga13.getRed() / Float.intBitsToFloat(1.03555674E9f ^ 0x7EC6577B), (float)Nigga13.getGreen() / Float.intBitsToFloat(1.04000973E9f ^ 0x7E824A03), (float)Nigga13.getBlue() / Float.intBitsToFloat(1.01159475E9f ^ 0x7F34B5E0), (float)(Nigga2.alpha.getValue() / 255.0));
                    }
                    Tracers.drawArrowToEntity(Nigga10, -1);
                }
                GL11.glLineWidth((float)Float.intBitsToFloat(1.08348429E9f ^ 0x7F14A86F));
            }
        }
    }

    public Tracers() {
        super(Qprot0.0("\u6e95\u71d9\u55d4\ua7e7\u4073\ueb09\u8c3c"), 0, Module.Category.RENDER);
        Tracers Nigga;
        Nigga.lineWidth = new NumberSetting(Qprot0.0("\u6e8d\u71c2\u55db\ua7e1\u4036\ueb2c\u8c26\u02e8\u5716\ueb34"), 1.0, 0.0, 10.0, 0.0);
        Nigga.dist = new BooleanSetting(Qprot0.0("\u6e82\u71c4\u55d9\ua7eb\u4064\ueb5b\u8c0b\u02e5\u5711\ueb28\u6137\uaf02\uec34\u7248"), false);
        Nigga.chroma = new BooleanSetting(Qprot0.0("\u6e82\u71c3\u55c7\ua7eb\u407b\ueb1a"), false);
        Nigga.red = new NumberSetting(Qprot0.0("\u6e93\u71ce\u55d1"), 0.0, 0.0, 255.0, 1.0);
        Nigga.green = new NumberSetting(Qprot0.0("\u6e86\u71d9\u55d0\ua7e1\u4078"), 0.0, 0.0, 255.0, 1.0);
        Nigga.blue = new NumberSetting(Qprot0.0("\u6e83\u71c7\u55c0\ua7e1"), 0.0, 0.0, 255.0, 1.0);
        Nigga.alpha = new NumberSetting(Qprot0.0("\u6e80\u71c7\u55c5\ua7ec\u4077"), 255.0, 1.0, 255.0, 1.0);
        Nigga.addSettings(Nigga.lineWidth, Nigga.dist, Nigga.chroma, Nigga.red, Nigga.green, Nigga.blue, Nigga.alpha);
    }

    public static {
        throw throwable;
    }

    public static double[] getLookVector(float Nigga) {
        return new double[]{-MathHelper.sin(Nigga *= Float.intBitsToFloat(1.13274624E9f ^ 0x7F0AAC14)), MathHelper.cos(Nigga)};
    }

    public static void drawArrowToEntity(Entity Nigga, int Nigga2) {
        double[] Nigga3 = Tracers.getLookVector(BlockUtil.getYaw(Nigga));
        double Nigga4 = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(Nigga);
        GL11.glTranslated((double)((double)Minecraft.getMinecraft().displayWidth / 4.0 + Nigga3[0] * Nigga4), (double)((double)Minecraft.getMinecraft().displayHeight / 4.0 + Nigga3[1] * Nigga4), (double)0.0);
        double Nigga5 = BlockUtil.getYaw(Nigga) - Float.intBitsToFloat(1.03960326E9f ^ 0x7EC3163B);
        GL11.glRotated((double)Nigga5, (double)0.0, (double)0.0, (double)1.0);
        RenderUtil.drawTriangle(0.0, 0.0, -3.0, 5.0, 3.0, 5.0, Nigga2);
        GL11.glRotated((double)(-Nigga5), (double)0.0, (double)0.0, (double)1.0);
        GL11.glTranslated((double)(-((double)Minecraft.getMinecraft().displayWidth / 4.0 + Nigga3[0] * Nigga4)), (double)(-((double)Minecraft.getMinecraft().displayHeight / 4.0 + Nigga3[1] * Nigga4)), (double)0.0);
    }
}


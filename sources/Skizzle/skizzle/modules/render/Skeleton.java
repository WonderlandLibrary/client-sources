/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventRenderEntity;
import skizzle.friends.Friend;
import skizzle.friends.FriendManager;
import skizzle.modules.Module;
import skizzle.settings.BooleanSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.RenderUtil;

public class Skeleton
extends Module {
    public BooleanSetting team;
    public BooleanSetting players;
    public BooleanSetting friends;
    public NumberSetting green;
    public Minecraft mc;
    public NumberSetting blue;
    public int colour = -16748374;
    public BooleanSetting chroma;
    public BooleanSetting monster;
    public NumberSetting red;
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u9e0f\u71c4\ua552\ua7e1"), Qprot0.0("\u9e00\u71c4\ua54e"), Qprot0.0("\u9e00\u71c4\ua54e"), Qprot0.0("\u9e04\u71de\ua55a\ua7e8"), Qprot0.0("\u9e11\u71c4\ua55a\ua7ed\ub1f5\u1bba\u8c20\uf277"), Qprot0.0("\u9e0d\u71de\ua542\ua7e8\ub1f8\u1b96\u8c2a"));
    public BooleanSetting passive;
    public BooleanSetting invis;
    public BooleanSetting pre;
    public NumberSetting alpha;

    public boolean shouldRender(Entity Nigga) {
        EntityLivingBase entityLivingBase;
        Skeleton Nigga3;
        if (!Nigga3.invis.isEnabled() && Nigga.isInvisible()) {
            return false;
        }
        if (!Nigga3.players.isEnabled() && Nigga instanceof EntityOtherPlayerMP) {
            return false;
        }
        if (!Nigga3.passive.isEnabled() && ("" + Nigga.getClass()).contains(Qprot0.0("\u9e32\u71ca\ua545\u54ac\ua337\u1b8e\u8c2a"))) {
            return false;
        }
        if (!Nigga3.monster.isEnabled() && ("" + Nigga.getClass()).contains(Qprot0.0("\u9e2f\u71c4\ua558\u54ac\ua32a\u1b9d\u8c3d"))) {
            return false;
        }
        for (Friend friend : FriendManager.friends) {
            if (!(Nigga instanceof EntityPlayer) || Nigga3.friends.isEnabled() || !Nigga.getName().equals(friend.getName())) continue;
            return false;
        }
        return !(Nigga instanceof EntityLivingBase) || (entityLivingBase = (EntityLivingBase)Nigga).getTeam() == null || Nigga3.team.isEnabled() || !entityLivingBase.getTeam().isSameTeam(Nigga3.mc.thePlayer.getTeam());
    }

    public float[] getColors() {
        Skeleton Nigga;
        float Nigga2 = (float)(Nigga.red.getValue() / 255.0);
        float Nigga3 = (float)Nigga.green.getValue() / Float.intBitsToFloat(1.01286259E9f ^ 0x7F200E9D);
        float Nigga4 = (float)Nigga.blue.getValue() / Float.intBitsToFloat(1.03659341E9f ^ 0x7EB628F7);
        if (Nigga.chroma.isEnabled()) {
            Color Nigga5 = new Color(Client.RGBColor);
            Nigga2 = (float)Nigga5.getRed() / Float.intBitsToFloat(1.01013197E9f ^ 0x7F4A6412);
            Nigga3 = (float)Nigga5.getGreen() / Float.intBitsToFloat(1.00745216E9f ^ 0x7F738016);
            Nigga4 = (float)Nigga5.getBlue() / Float.intBitsToFloat(1.00881613E9f ^ 0x7F5E5009);
        }
        return new float[]{Nigga2, Nigga3, Nigga4, (float)Nigga.alpha.getValue() / Float.intBitsToFloat(1.04989894E9f ^ 0x7DEB2FB7)};
    }

    @Override
    public void onEvent(Event Nigga) {
        Skeleton Nigga2;
        if (Nigga instanceof EventRenderEntity && Minecraft.theWorld != null && Nigga2.mc.thePlayer != null && (Nigga.isPost() && !Nigga2.pre.isEnabled() || Nigga.isPre() && Nigga2.pre.isEnabled())) {
            Entity Nigga3 = ((EventRenderEntity)Nigga).entity;
            EventRenderEntity Nigga4 = (EventRenderEntity)Nigga;
            new AxisAlignedBB(Nigga3.boundingBox.minX - Nigga3.posX + Nigga4.smoothX, Nigga3.boundingBox.minY - Nigga3.posY + Nigga4.smoothY, Nigga3.boundingBox.minZ - Nigga3.posZ + Nigga4.smoothZ, Nigga3.boundingBox.maxX - Nigga3.posX + Nigga4.smoothX, Nigga3.boundingBox.maxY - Nigga3.posY + Nigga4.smoothY + 0.0, Nigga3.boundingBox.maxZ - Nigga3.posZ + Nigga4.smoothZ);
            if (Nigga3 instanceof EntityLivingBase && Nigga3 instanceof EntityPlayer) {
                GL11.glColor4d((double)(Nigga2.red.getValue() / 255.0), (double)(Nigga2.green.getValue() / 255.0), (double)(Nigga2.blue.getValue() / 255.0), (double)(Nigga2.alpha.getValue() / 255.0));
                GL11.glDisable((int)2929);
                GL11.glRotated((double)(Nigga2.mc.thePlayer.rotationYaw / Float.intBitsToFloat(1.05164525E9f ^ 0x7EAED549)), (double)0.0, (double)1.0, (double)0.0);
                RenderUtil.drawLine(0.0, 0.0, 0.0, 1.0, -1);
                GL11.glEnable((int)2929);
                GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
            }
        }
    }

    public static {
        throw throwable;
    }

    public Skeleton() {
        super(Qprot0.0("\u9e11\u71c0\ua553\ua7e8\ub1f4\u1b8c\u8c20\uf261"), 0, Module.Category.RENDER);
        Skeleton Nigga;
        Nigga.players = new BooleanSetting(Qprot0.0("\u9e12\u71c7\ua557\ua7fd\ub1f4\u1b8a\u8c3c"), true);
        Nigga.passive = new BooleanSetting(Qprot0.0("\u9e12\u71ca\ua545\ua7f7\ub1f8\u1b8e\u8c2a"), true);
        Nigga.monster = new BooleanSetting(Qprot0.0("\u9e0f\u71c4\ua558\ua7f7\ub1e5\u1b9d\u8c3d"), true);
        Nigga.friends = new BooleanSetting(Qprot0.0("\u9e04\u71d9\ua55f\ua7e1\ub1ff\u1b9c\u8c3c"), true);
        Nigga.team = new BooleanSetting(Qprot0.0("\u9e16\u71ce\ua557\ua7e9"), false);
        Nigga.invis = new BooleanSetting(Qprot0.0("\u9e0b\u71c5\ua540\ua7ed\ub1e2\u1b91\u8c2d\uf263\u5707"), false);
        Nigga.chroma = new BooleanSetting(Qprot0.0("\u9e01\u71c3\ua544\ua7eb\ub1fc\u1b99"), false);
        Nigga.red = new NumberSetting(Qprot0.0("\u9e10\u71ce\ua552"), 255.0, 0.0, 255.0, 1.0);
        Nigga.green = new NumberSetting(Qprot0.0("\u9e05\u71d9\ua553\ua7e1\ub1ff"), 255.0, 0.0, 255.0, 1.0);
        Nigga.blue = new NumberSetting(Qprot0.0("\u9e00\u71c7\ua543\ua7e1"), 255.0, 0.0, 255.0, 1.0);
        Nigga.alpha = new NumberSetting(Qprot0.0("\u9e03\u71c7\ua546\ua7ec\ub1f0"), 255.0, 25.0, 255.0, 1.0);
        Nigga.pre = new BooleanSetting(Qprot0.0("\u9e12\u71d9\ua553\ua7a4\ub1c3\u1b9d\u8c21\uf26b\u5707\u1aa9"), false);
        Nigga.mc = Minecraft.getMinecraft();
        Nigga.addSettings(Nigga.mode, Nigga.players, Nigga.passive, Nigga.monster, Nigga.friends, Nigga.team, Nigga.invis, Nigga.chroma, Nigga.red, Nigga.green, Nigga.blue, Nigga.alpha, Nigga.pre);
    }
}


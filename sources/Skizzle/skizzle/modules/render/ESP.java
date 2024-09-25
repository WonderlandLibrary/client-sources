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

public class ESP
extends Module {
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u1248\u71c4\u291d\ua7e1"), Qprot0.0("\u1247\u71c4\u2901"), Qprot0.0("\u1247\u71c4\u2901"), Qprot0.0("\u1243\u71de\u2915\ua7e8"), Qprot0.0("\u1256\u71c4\u2915\ua7ed\u3dbe\u97fd\u8c20\u7e38"), Qprot0.0("\u124a\u71de\u290d\ua7e8\u3db3\u97d1\u8c2a"));
    public Minecraft mc;
    public int colour = -16748374;
    public BooleanSetting invis;
    public BooleanSetting pre;
    public NumberSetting red;
    public BooleanSetting players = new BooleanSetting(Qprot0.0("\u1255\u71c7\u2918\ua7fd\u3dbf\u97cd\u8c3c"), true);
    public BooleanSetting team;
    public BooleanSetting monster;
    public BooleanSetting friends;
    public BooleanSetting chroma;
    public NumberSetting alpha;
    public NumberSetting blue;
    public NumberSetting green;
    public BooleanSetting passive = new BooleanSetting(Qprot0.0("\u1255\u71ca\u290a\ua7f7\u3db3\u97c9\u8c2a"), true);

    @Override
    public void onEvent(Event Nigga) {
        ESP Nigga2;
        if (Nigga instanceof EventRenderEntity && Minecraft.theWorld != null && Nigga2.mc.thePlayer != null && (Nigga.isPost() && !Nigga2.pre.isEnabled() || Nigga.isPre() && Nigga2.pre.isEnabled())) {
            Entity Nigga3 = ((EventRenderEntity)Nigga).entity;
            EventRenderEntity Nigga4 = (EventRenderEntity)Nigga;
            AxisAlignedBB Nigga5 = new AxisAlignedBB(Nigga3.boundingBox.minX - Nigga3.posX + Nigga4.smoothX, Nigga3.boundingBox.minY - Nigga3.posY + Nigga4.smoothY, Nigga3.boundingBox.minZ - Nigga3.posZ + Nigga4.smoothZ, Nigga3.boundingBox.maxX - Nigga3.posX + Nigga4.smoothX, Nigga3.boundingBox.maxY - Nigga3.posY + Nigga4.smoothY + 0.0, Nigga3.boundingBox.maxZ - Nigga3.posZ + Nigga4.smoothZ);
            if (Nigga3 instanceof EntityLivingBase && Nigga2.shouldRender(Nigga3)) {
                GL11.glColor4d((double)(Nigga2.red.getValue() / 255.0), (double)(Nigga2.green.getValue() / 255.0), (double)(Nigga2.blue.getValue() / 255.0), (double)(Nigga2.alpha.getValue() / 255.0));
                if (Nigga2.mode.getMode().equals(Qprot0.0("\u1256\u71c4\u2915\ue264\u4629\u97fd\u8c20\u7e38"))) {
                    RenderUtil.drawSolidBlockESP(Nigga5, Nigga2.getColors()[0], Nigga2.getColors()[1], Nigga2.getColors()[2], Nigga2.getColors()[3], 7);
                }
                if (Nigga2.mode.getMode().equals(Qprot0.0("\u1247\u71c4\u2901"))) {
                    RenderUtil.drawBoundingBox(Nigga5, 3);
                }
                GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
            }
        }
    }

    public ESP() {
        super(Qprot0.0("\u1240\u71f8\u2929"), 0, Module.Category.RENDER);
        ESP Nigga;
        Nigga.monster = new BooleanSetting(Qprot0.0("\u1248\u71c4\u2917\ua7f7\u3dae\u97da\u8c3d"), true);
        Nigga.friends = new BooleanSetting(Qprot0.0("\u1243\u71d9\u2910\ua7e1\u3db4\u97db\u8c3c"), true);
        Nigga.team = new BooleanSetting(Qprot0.0("\u1251\u71ce\u2918\ua7e9"), false);
        Nigga.invis = new BooleanSetting(Qprot0.0("\u124c\u71c5\u290f\ua7ed\u3da9\u97d6\u8c2d\u7e2c\u5707"), false);
        Nigga.chroma = new BooleanSetting(Qprot0.0("\u1246\u71c3\u290b\ua7eb\u3db7\u97de"), false);
        Nigga.red = new NumberSetting(Qprot0.0("\u1257\u71ce\u291d"), 255.0, 0.0, 255.0, 1.0);
        Nigga.green = new NumberSetting(Qprot0.0("\u1242\u71d9\u291c\ua7e1\u3db4"), 255.0, 0.0, 255.0, 1.0);
        Nigga.blue = new NumberSetting(Qprot0.0("\u1247\u71c7\u290c\ua7e1"), 255.0, 0.0, 255.0, 1.0);
        Nigga.alpha = new NumberSetting(Qprot0.0("\u1244\u71c7\u2909\ua7ec\u3dbb"), 255.0, 25.0, 255.0, 1.0);
        Nigga.pre = new BooleanSetting(Qprot0.0("\u1255\u71d9\u291c\ua7a4\u3d88\u97da\u8c21\u7e24\u5707\u96e2"), false);
        Nigga.mc = Minecraft.getMinecraft();
        Nigga.addSettings(Nigga.mode, Nigga.players, Nigga.passive, Nigga.monster, Nigga.friends, Nigga.team, Nigga.invis, Nigga.chroma, Nigga.red, Nigga.green, Nigga.blue, Nigga.alpha, Nigga.pre);
    }

    public float[] getColors() {
        ESP Nigga;
        float Nigga2 = (float)(Nigga.red.getValue() / 255.0);
        float Nigga3 = (float)Nigga.green.getValue() / Float.intBitsToFloat(1.03319232E9f ^ 0x7EEA4399);
        float Nigga4 = (float)Nigga.blue.getValue() / Float.intBitsToFloat(1.00793043E9f ^ 0x7F6CCC57);
        if (Nigga.chroma.isEnabled()) {
            Color Nigga5 = new Color(Client.RGBColor);
            Nigga2 = (float)Nigga5.getRed() / Float.intBitsToFloat(1.01384454E9f ^ 0x7F110A30);
            Nigga3 = (float)Nigga5.getGreen() / Float.intBitsToFloat(1.01176531E9f ^ 0x7F315035);
            Nigga4 = (float)Nigga5.getBlue() / Float.intBitsToFloat(1.01298368E9f ^ 0x7F1FE782);
        }
        return new float[]{Nigga2, Nigga3, Nigga4, (float)Nigga.alpha.getValue() / Float.intBitsToFloat(1.03475296E9f ^ 0x7ED213B7)};
    }

    public static {
        throw throwable;
    }

    public boolean shouldRender(Entity Nigga) {
        EntityLivingBase entityLivingBase;
        ESP Nigga3;
        if (!Nigga3.invis.isEnabled() && Nigga.isInvisible()) {
            return false;
        }
        if (!Nigga3.players.isEnabled() && Nigga instanceof EntityOtherPlayerMP) {
            return false;
        }
        if (!Nigga3.passive.isEnabled() && ("" + Nigga.getClass()).contains(Qprot0.0("\u1275\u71ca\u290a\u54ac\u2cca\u97c9\u8c2a"))) {
            return false;
        }
        if (!Nigga3.monster.isEnabled() && ("" + Nigga.getClass()).contains(Qprot0.0("\u1268\u71c4\u2917\u54ac\u2cd7\u97da\u8c3d"))) {
            return false;
        }
        for (Friend friend : FriendManager.friends) {
            if (!(Nigga instanceof EntityPlayer) || Nigga3.friends.isEnabled() || !Nigga.getName().equals(friend.getName())) continue;
            return false;
        }
        return !(Nigga instanceof EntityLivingBase) || (entityLivingBase = (EntityLivingBase)Nigga).getTeam() == null || Nigga3.team.isEnabled() || !entityLivingBase.getTeam().isSameTeam(Nigga3.mc.thePlayer.getTeam());
    }
}


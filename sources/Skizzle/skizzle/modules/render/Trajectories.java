/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import skizzle.events.Event;
import skizzle.events.listeners.EventRender3D;
import skizzle.modules.Module;
import skizzle.settings.BooleanSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.RenderUtil;

public class Trajectories
extends Module {
    public NumberSetting alpha;
    public NumberSetting blue;
    public NumberSetting range;
    public NumberSetting increment;
    public Minecraft mc = Minecraft.getMinecraft();
    public int colour = -16748374;
    public NumberSetting green;
    public NumberSetting red;
    public BooleanSetting chroma;
    public NumberSetting lineWidth = new NumberSetting(Qprot0.0("\u50e6\u71c2\u6bb0\ua7e1\u7a59\ud547\u8c26\u3c83\u5716\ud15b"), 1.0, 0.0, 10.0, 0.0);

    public Trajectories() {
        super(Qprot0.0("\u50fe\u71d9\u6bbf\ua7ee\u7a1c\ud573\u8c3b\u3c88\u5710\ud15a\u5f58\uaf1f"), 0, Module.Category.RENDER);
        Trajectories Nigga;
        Nigga.chroma = new BooleanSetting(Qprot0.0("\u50e9\u71c3\u6bac\ua7eb\u7a14\ud571"), false);
        Nigga.red = new NumberSetting(Qprot0.0("\u50f8\u71ce\u6bba"), 0.0, 0.0, 255.0, 1.0);
        Nigga.green = new NumberSetting(Qprot0.0("\u50ed\u71d9\u6bbb\ua7e1\u7a17"), 0.0, 0.0, 255.0, 1.0);
        Nigga.blue = new NumberSetting(Qprot0.0("\u50e8\u71c7\u6bab\ua7e1"), 0.0, 0.0, 255.0, 1.0);
        Nigga.alpha = new NumberSetting(Qprot0.0("\u50eb\u71c7\u6bae\ua7ec\u7a18"), 255.0, 1.0, 255.0, 1.0);
        Nigga.increment = new NumberSetting(Qprot0.0("\u50f9\u71c6\u6bb1\ua7eb\u7a0d\ud578\u8c21\u3c82\u5711\ud140"), 1.0, 0.0, 10.0, 0.0);
        Nigga.range = new NumberSetting(Qprot0.0("\u50e7\u71ca\u6ba6\ua7a4\u7a2b\ud571\u8c21\u3c80\u5707"), 1000.0, 100.0, 10000.0, 1.0);
        Nigga.addSettings(Nigga.lineWidth, Nigga.chroma, Nigga.red, Nigga.green, Nigga.blue, Nigga.alpha, Nigga.increment, Nigga.range);
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventRender3D) {
            double Nigga2;
            Trajectories Nigga3;
            EntityPlayerSP Nigga4 = Nigga3.mc.thePlayer;
            ItemStack Nigga5 = Nigga4.getCurrentEquippedItem();
            if (Nigga5 == null) {
                return;
            }
            Item Nigga6 = Nigga5.getItem();
            if (!(Nigga6 instanceof ItemBow || Nigga6 instanceof ItemSnowball || Nigga6 instanceof ItemEgg || Nigga6 instanceof ItemEnderPearl || Nigga6 instanceof ItemPotion && ItemPotion.isSplash(Nigga5.getItemDamage()))) {
                return;
            }
            boolean Nigga7 = Nigga4.getCurrentEquippedItem().getItem() instanceof ItemBow;
            boolean Nigga8 = Nigga4.getCurrentEquippedItem().getItem() instanceof ItemEgg || Nigga4.getCurrentEquippedItem().getItem() instanceof ItemSnowball || Nigga4.getCurrentEquippedItem().getItem() instanceof ItemEnderPearl;
            double Nigga9 = Nigga4.lastTickPosX + (Nigga4.posX - Nigga4.lastTickPosX) * (double)Nigga3.mc.timer.renderPartialTicks - (double)(MathHelper.cos((float)Math.toRadians(Nigga4.rotationYaw)) * Float.intBitsToFloat(1.09786675E9f ^ 0x7F53CAE4));
            double Nigga10 = Nigga4.lastTickPosY + (Nigga4.posY - Nigga4.lastTickPosY) * (double)Minecraft.getMinecraft().timer.renderPartialTicks + (double)Nigga4.getEyeHeight() - 0.0;
            double Nigga11 = Nigga4.lastTickPosZ + (Nigga4.posZ - Nigga4.lastTickPosZ) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - (double)(MathHelper.sin((float)Math.toRadians(Nigga4.rotationYaw)) * Float.intBitsToFloat(1.10769728E9f ^ 0x7C25C9BF));
            float Nigga12 = Nigga7 ? Float.intBitsToFloat(1.16515738E9f ^ 0x7AF2E3FF) : Float.intBitsToFloat(1.08414938E9f ^ 0x7E520297);
            float Nigga13 = (float)Math.toRadians(Nigga4.rotationYaw);
            float Nigga14 = (float)Math.toRadians(Nigga4.rotationPitch);
            float Nigga15 = -MathHelper.sin(Nigga13) * MathHelper.cos(Nigga14) * Nigga12;
            float Nigga16 = -MathHelper.sin(Nigga14) * Nigga12;
            float Nigga17 = MathHelper.cos(Nigga13) * MathHelper.cos(Nigga14) * Nigga12;
            double Nigga18 = Math.sqrt(Nigga15 * Nigga15 + Nigga16 * Nigga16 + Nigga17 * Nigga17);
            Nigga15 = (float)((double)Nigga15 / Nigga18);
            Nigga16 = (float)((double)Nigga16 / Nigga18);
            Nigga17 = (float)((double)Nigga17 / Nigga18);
            if (Nigga7) {
                float Nigga19 = (float)(72000 - Nigga4.getItemInUseCount()) / Float.intBitsToFloat(1.05485472E9f ^ 0x7F7FCE3E);
                if ((Nigga19 = (Nigga19 * Nigga19 + Nigga19 * Float.intBitsToFloat(1.05901082E9f ^ 0x7F1F38E5)) / Float.intBitsToFloat(1.05375462E9f ^ 0x7E8F04F3)) > Float.intBitsToFloat(1.10566464E9f ^ 0x7E671A4F)) {
                    Nigga19 = Float.intBitsToFloat(1.08371098E9f ^ 0x7F181E3A);
                }
                if (Nigga19 <= Float.intBitsToFloat(1.12062899E9f ^ 0x7F07BC10)) {
                    Nigga19 = Float.intBitsToFloat(1.09044813E9f ^ 0x7F7EEAD9);
                }
                Nigga15 *= (Nigga19 *= Float.intBitsToFloat(1.05827469E9f ^ 0x7F53FD8B));
                Nigga16 *= Nigga19;
                Nigga17 *= Nigga19;
            } else {
                Nigga15 = (float)((double)Nigga15 * 1.5);
                Nigga16 = (float)((double)Nigga16 * 1.5);
                Nigga17 = (float)((double)Nigga17 * 1.5);
            }
            GL11.glPushMatrix();
            GL11.glEnable((int)2848);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glLineWidth((float)Float.intBitsToFloat(1.10231667E9f ^ 0x7E5262E3));
            RenderManager Nigga20 = Nigga3.mc.getRenderManager();
            double Nigga21 = Nigga7 ? 0.0 : (Nigga6 instanceof ItemPotion ? 0.0 : 0.0);
            Vec3 Nigga22 = new Vec3(Nigga4.posX, Nigga4.posY + (double)Nigga4.getEyeHeight(), Nigga4.posZ);
            Enum Nigga23 = null;
            GL11.glColor4d((double)(Nigga3.red.getValue() / 255.0), (double)(Nigga3.green.getValue() / 255.0), (double)(Nigga3.blue.getValue() / 255.0), (double)(Nigga3.alpha.getValue() / 255.0));
            GL11.glBegin((int)3);
            for (Nigga2 = 0.0; Nigga2 < Nigga3.range.getValue(); Nigga2 += 1.0) {
                GL11.glVertex3d((double)(Nigga9 - Nigga20.renderPosX), (double)(Nigga10 - Nigga20.renderPosY + 0.0), (double)(Nigga11 - Nigga20.renderPosZ));
                Nigga9 += (double)Nigga15 / Nigga3.increment.getValue();
                Nigga10 += (double)Nigga16 / Nigga3.increment.getValue();
                Nigga11 += (double)Nigga17 / Nigga3.increment.getValue();
                if (Nigga8) {
                    Nigga15 = (float)((double)Nigga15 * 0.0);
                    Nigga16 = (float)((double)Nigga16 * 0.0);
                    Nigga17 = (float)((double)Nigga17 * 0.0);
                }
                Nigga16 = (float)((double)Nigga16 - Nigga21 / Nigga3.increment.getValue());
                MovingObjectPosition Nigga24 = Minecraft.theWorld.rayTraceBlocks(Nigga22, new Vec3(Nigga9, Nigga10, Nigga11));
                if (Nigga24 == null) continue;
                Nigga23 = Nigga24.sideHit;
                break;
            }
            GL11.glEnd();
            Nigga2 = Nigga9 - Nigga20.renderPosX;
            double Nigga25 = Nigga10 - Nigga20.renderPosY;
            double Nigga26 = Nigga11 - Nigga20.renderPosZ;
            GL11.glDisable((int)2929);
            GlStateManager.translate(Nigga2, Nigga25, Nigga26);
            if (Nigga23 != null) {
                if (Nigga23.equals(EnumFacing.SOUTH) || Nigga23.equals(EnumFacing.NORTH)) {
                    GlStateManager.rotate(Float.intBitsToFloat(1.00780461E9f ^ 0x7EA5E0B9), Float.intBitsToFloat(1.10005504E9f ^ 0x7E1181CF), Float.intBitsToFloat(2.13751795E9f ^ 0x7F67EF77), Float.intBitsToFloat(2.13863795E9f ^ 0x7F790665));
                }
                if (Nigga23.equals(EnumFacing.WEST) || Nigga23.equals(EnumFacing.EAST)) {
                    GlStateManager.rotate(Float.intBitsToFloat(1.03551462E9f ^ 0x7F0CB313), Float.intBitsToFloat(2.12715123E9f ^ 0x7EC9C059), Float.intBitsToFloat(2.10600973E9f ^ 0x7D872877), Float.intBitsToFloat(1.09741811E9f ^ 0x7EE94591));
                }
            }
            GlStateManager.translate(-Nigga2, -Nigga25, -Nigga26);
            RenderUtil.drawCircle(Nigga2, Nigga25, Nigga26, 0.0, 1.0, false);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)2848);
            GL11.glPopMatrix();
        }
    }

    public static {
        throw throwable;
    }
}


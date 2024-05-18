package wwe.modules.render;

import wwe.modules.*;
import wwe.utils.*;
import org.lwjgl.opengl.*;
import me.THEREALWWEFAN231.wwefan.mixin.mixins.interfaces.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import wwe.*;

public class Trajectories extends qMIe
{
    public Trajectories() {
        super("Trajectories", "Shows where your bow's arrow is going land", 0, Category.RENDER);
    }
    
    @oqrr
    public void efgw(final RTIo rtIo) {
        final EntityPlayerSP player = Wrapper.mc.player;
        final ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem == null) {
            return;
        }
        if (!(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow) && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemSnowball) && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemEgg) && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemEnderPearl) && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemSplashPotion) && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemLingeringPotion) && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod)) {
            return;
        }
        final boolean b = currentItem.getItem() instanceof ItemBow;
        double n = player.lastTickPosX + (player.posX - player.lastTickPosX) * yYGD.XgOL().renderPartialTicks - MathHelper.cos((float)Math.toRadians(player.rotationYaw)) * 0.16f;
        double n2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * yYGD.XgOL().renderPartialTicks + player.getEyeHeight() - 0.1;
        double n3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * yYGD.XgOL().renderPartialTicks - MathHelper.sin((float)Math.toRadians(player.rotationYaw)) * 0.16f;
        float n4;
        if (b) {
            n4 = 1.0f;
            if (Trajectories.\u2006\u2007\u2002\u200e\u2006\u2004\u200c\u2003\u2007) {
                throw null;
            }
        }
        else {
            n4 = 0.4f;
        }
        final float n5 = n4;
        final float n6 = (float)Math.toRadians(player.rotationYaw);
        final float n7 = (float)Math.toRadians(player.rotationPitch);
        final float n8 = -MathHelper.sin(n6) * MathHelper.cos(n7) * n5;
        final float n9 = -MathHelper.sin(n7) * n5;
        final float n10 = MathHelper.cos(n6) * MathHelper.cos(n7) * n5;
        final double sqrt = Math.sqrt(n8 * n8 + n9 * n9 + n10 * n10);
        final float n11 = n8 / (float)sqrt;
        final float n12 = n9 / (float)sqrt;
        final float n13 = n10 / (float)sqrt;
        float n17;
        float n18;
        float n19;
        if (b) {
            final float n14 = (72000 - player.getItemInUseCount()) / 20.0f;
            float n15 = (n14 * n14 + n14 * 2.0f) / 3.0f;
            if (n15 > 1.0f || n15 <= 0.1f) {
                n15 = 1.0f;
            }
            final float n16 = n15 * 3.0f;
            n17 = n11 * n16;
            n18 = n12 * n16;
            n19 = n13 * n16;
            if (Trajectories.\u2006\u2007\u2002\u200e\u2006\u2004\u200c\u2003\u2007) {
                throw null;
            }
        }
        else {
            n17 = (float)(n11 * 1.5);
            n18 = (float)(n12 * 1.5);
            n19 = (float)(n13 * 1.5);
        }
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ((InterfaceMixinEntityRenderer)Wrapper.mc.entityRenderer).orientCameraVoid(rtIo.vCPg());
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        Wrapper.mc.getRenderManager();
        double n20;
        if (b) {
            n20 = 0.05;
            if (Trajectories.\u2006\u2007\u2002\u200e\u2006\u2004\u200c\u2003\u2007) {
                throw null;
            }
        }
        else if (currentItem.getItem() instanceof ItemPotion) {
            n20 = 0.4;
            if (Trajectories.\u2006\u2007\u2002\u200e\u2006\u2004\u200c\u2003\u2007) {
                throw null;
            }
        }
        else if (currentItem.getItem() instanceof ItemFishingRod) {
            n20 = 0.15;
            if (Trajectories.\u2006\u2007\u2002\u200e\u2006\u2004\u200c\u2003\u2007) {
                throw null;
            }
        }
        else {
            n20 = 0.03;
        }
        final double n21 = n20;
        final Vec3d vec3d = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.75f);
        GL11.glBegin(3);
        int i = 0;
        while (i < 1000) {
            BIIV.VQeC(JIfG.JzFV(0x1AB01078BDFEA524L ^ 0x1AB01078BDFEA525L, 1.0f));
            GL11.glVertex3d(n - yYGD.onRX(), n2 - yYGD.UuJK(), n3 - yYGD.LkVR());
            n += n17 * 0.1;
            n2 += n18 * 0.1;
            n3 += n19 * 0.1;
            n17 *= (float)0.999;
            final float n22 = (float)(n18 * 0.999);
            n19 *= (float)0.999;
            n18 = n22 - (float)(n21 * 0.1);
            if (Wrapper.mc.world.rayTraceBlocks(vec3d, new Vec3d(n, n2, n3)) != null) {
                if (Trajectories.\u2006\u2007\u2002\u200e\u2006\u2004\u200c\u2003\u2007) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
                if (Trajectories.\u2006\u2007\u2002\u200e\u2006\u2004\u200c\u2003\u2007) {
                    throw null;
                }
                continue;
            }
        }
        GL11.glEnd();
        final double n23 = n - yYGD.onRX();
        final double n24 = n2 - yYGD.UuJK();
        final double n25 = n3 - yYGD.LkVR();
        GL11.glPushMatrix();
        GL11.glTranslated(n23 - 0.5, n24 - 0.5, n25 - 0.5);
        GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.25f);
        BIIV.VQeC(JIfG.JzFV(0xD0158CBB1CEE8F5BL ^ 0xD0158CBB1CEE8F5AL, 1.0f));
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        GL11.glBegin(7);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
}

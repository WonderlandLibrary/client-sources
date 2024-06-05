package cn.Hanabi.modules.Render;

import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import ClassSub.*;
import org.lwjgl.opengl.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import java.util.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class Projectiles extends Mod
{
    private EntityLivingBase entity;
    private MovingObjectPosition blockCollision;
    private MovingObjectPosition entityCollision;
    private static AxisAlignedBB aim;
    
    
    public Projectiles() {
        super("Projectiles", Category.RENDER);
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        if (Projectiles.mc.thePlayer.inventory.getCurrentItem() != null) {
            final EntityPlayerSP thePlayer = Projectiles.mc.thePlayer;
            final ItemStack getCurrentItem = thePlayer.inventory.getCurrentItem();
            final int getIdFromItem = Item.getIdFromItem(Projectiles.mc.thePlayer.getHeldItem().getItem());
            if (getIdFromItem == 261 || getIdFromItem == 368 || getIdFromItem == 332 || getIdFromItem == 344) {
                double n = thePlayer.lastTickPosX + (thePlayer.posX - thePlayer.lastTickPosX) * Class211.getTimer().renderPartialTicks - Math.cos(Math.toRadians(thePlayer.rotationYaw)) * 0.1599999964237213;
                double n2 = thePlayer.lastTickPosY + (thePlayer.posY - thePlayer.lastTickPosY) * Class211.getTimer().renderPartialTicks + thePlayer.getEyeHeight() - 0.1;
                double n3 = thePlayer.lastTickPosZ + (thePlayer.posZ - thePlayer.lastTickPosZ) * Class211.getTimer().renderPartialTicks - Math.sin(Math.toRadians(thePlayer.rotationYaw)) * 0.1599999964237213;
                final double n4 = (getCurrentItem.getItem() instanceof ItemBow) ? 1.0 : 0.4000000059604645;
                final double radians = Math.toRadians(thePlayer.rotationYaw);
                final double radians2 = Math.toRadians(thePlayer.rotationPitch);
                final double n5 = -Math.sin(radians) * Math.cos(radians2) * n4;
                final double n6 = -Math.sin(radians2) * n4;
                final double n7 = Math.cos(radians) * Math.cos(radians2) * n4;
                final double sqrt = Math.sqrt(n5 * n5 + n6 * n6 + n7 * n7);
                final double n8 = n5 / sqrt;
                final double n9 = n6 / sqrt;
                final double n10 = n7 / sqrt;
                double n14;
                double n15;
                double n16;
                if (getCurrentItem.getItem() instanceof ItemBow) {
                    final float n11 = (72000 - thePlayer.getItemInUseCount()) / 20.0f;
                    float n12 = (n11 * n11 + n11 * 2.0f) / 3.0f;
                    if (n12 > 1.0f) {
                        n12 = 1.0f;
                    }
                    final float n13 = n12 * 3.0f;
                    n14 = n8 * n13;
                    n15 = n9 * n13;
                    n16 = n10 * n13;
                }
                else {
                    n14 = n8 * 1.5;
                    n15 = n9 * 1.5;
                    n16 = n10 * 1.5;
                }
                GL11.glPushMatrix();
                GL11.glDisable(3553);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glEnable(2848);
                GL11.glLineWidth(2.0f);
                final double n17 = (getCurrentItem.getItem() instanceof ItemBow) ? 0.05 : 0.03;
                GL11.glColor4f(0.0f, 1.0f, 0.2f, 0.5f);
                GL11.glBegin(3);
                for (int i = 0; i < 2000; ++i) {
                    GL11.glVertex3d(n - ((IRenderManager)Projectiles.mc.getRenderManager()).getRenderPosX(), n2 - ((IRenderManager)Projectiles.mc.getRenderManager()).getRenderPosY(), n3 - ((IRenderManager)Projectiles.mc.getRenderManager()).getRenderPosZ());
                    n += n14 * 0.1;
                    n2 += n15 * 0.1;
                    n3 += n16 * 0.1;
                    n14 *= 0.999;
                    final double n18 = n15 * 0.999;
                    n16 *= 0.999;
                    n15 = n18 - n17 * 0.1;
                    final Vec3 vec3 = new Vec3(thePlayer.posX, thePlayer.posY + thePlayer.getEyeHeight(), thePlayer.posZ);
                    this.blockCollision = Projectiles.mc.theWorld.rayTraceBlocks(vec3, new Vec3(n, n2, n3));
                    for (final Entity entity : Projectiles.mc.theWorld.getLoadedEntityList()) {
                        if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayerSP)) {
                            this.entity = (EntityLivingBase)entity;
                            this.entityCollision = this.entity.getEntityBoundingBox().expand(0.3, 0.3, 0.3).calculateIntercept(vec3, new Vec3(n, n2, n3));
                            if (this.entityCollision != null) {
                                this.blockCollision = this.entityCollision;
                            }
                            if (this.entityCollision != null) {
                                GL11.glColor4f(1.0f, 0.0f, 0.2f, 0.5f);
                            }
                            if (this.entityCollision == null) {
                                continue;
                            }
                            this.blockCollision = this.entityCollision;
                        }
                    }
                    if (this.blockCollision != null) {
                        break;
                    }
                }
                GL11.glEnd();
                final double n19 = n - ((IRenderManager)Projectiles.mc.getRenderManager()).getRenderPosX();
                final double n20 = n2 - ((IRenderManager)Projectiles.mc.getRenderManager()).getRenderPosY();
                final double n21 = n3 - ((IRenderManager)Projectiles.mc.getRenderManager()).getRenderPosZ();
                GL11.glPushMatrix();
                GL11.glTranslated(n19 - 0.5, n20 - 0.5, n21 - 0.5);
                switch (this.blockCollision.sideHit.getIndex()) {
                    case 2:
                    case 3: {
                        GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                        Projectiles.aim = new AxisAlignedBB(0.0, 0.5, -1.0, 1.0, 0.45, 0.0);
                        break;
                    }
                    case 4:
                    case 5: {
                        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                        Projectiles.aim = new AxisAlignedBB(0.0, -0.5, 0.0, 1.0, -0.45, 1.0);
                        break;
                    }
                    default: {
                        Projectiles.aim = new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 0.45, 1.0);
                        break;
                    }
                }
                drawBox(Projectiles.aim);
                drawSelectionBoundingBox(Projectiles.aim);
                GL11.glPopMatrix();
                GL11.glDisable(3042);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(2848);
                GL11.glPopMatrix();
            }
        }
    }
    
    public static void drawBox(final AxisAlignedBB axisAlignedBB) {
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
    }
    
    public static void drawSelectionBoundingBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        getWorldRenderer.begin(3, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(3, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(1, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getInstance.draw();
    }
}

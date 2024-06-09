package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RenderWorldEvent;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.RenderingMethods;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myche on 2/21/2017.
 */
public class Prediction extends Module {

    public Prediction() {
        super("Prediction", Keyboard.KEY_NONE, Category.RENDER);
    }

    @SubscribeEvent
    public void onEvent(RenderWorldEvent event) {
        if (this.isEnabled()) {
            double renderPosX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * event.getPartialTicks();
            double renderPosY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * event.getPartialTicks();
            double renderPosZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * event.getPartialTicks();
            if ((mc.thePlayer.getHeldItem() == null) || (mc.gameSettings.thirdPersonView != 0)) {
                return;
            }
            if (!(mc.thePlayer.getHeldItem().getItem() instanceof ItemBow)) {
                return;
            }
            ItemStack stack = mc.thePlayer.getHeldItem();
            Item item = mc.thePlayer.getHeldItem().getItem();
            double posX = renderPosX - MathHelper.cos(mc.thePlayer.rotationYaw / 180.0F * 3.1415927F) * 0.16F;
            double posY = renderPosY + mc.thePlayer.getEyeHeight() - 0.1000000014901161D;
            double posZ = renderPosZ - MathHelper.sin(mc.thePlayer.rotationYaw / 180.0F * 3.1415927F) * 0.16F;
            double motionX = -MathHelper.sin(mc.thePlayer.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(mc.thePlayer.rotationPitch / 180.0F * 3.1415927F) * ((item instanceof ItemBow) ? 1.0D : 0.4D);
            double motionY = -MathHelper.sin(mc.thePlayer.rotationPitch / 180.0F * 3.1415927F) * ((item instanceof ItemBow) ? 1.0D : 0.4D);
            double motionZ = MathHelper.cos(mc.thePlayer.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(mc.thePlayer.rotationPitch / 180.0F * 3.1415927F) * ((item instanceof ItemBow) ? 1.0D : 0.4D);
            int var6 = 72000 - mc.thePlayer.getItemInUseCount();
            float power = var6 / 20.0F;
            power = (power * power + power * 2.0F) / 3.0F;
            if (power < 0.1D) {
                return;
            }
            if (power > 1.0F) {
                power = 1.0F;
            }
            float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
            motionX /= distance;
            motionY /= distance;
            motionZ /= distance;
            float pow = mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.9F : (item instanceof ItemFishingRod) ? 1.25F : (item instanceof ItemBow) ? power * 2.0F : 1.0F;
            motionX *= pow * (mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.75F : (item instanceof ItemFishingRod) ? 0.75F : 1.5F);
            motionY *= pow * (mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.75F : (item instanceof ItemFishingRod) ? 0.75F : 1.5F);
            motionZ *= pow * (mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.75F : (item instanceof ItemFishingRod) ? 0.75F : 1.5F);
            RenderingMethods.enableGL3D(0.4F);
            if (power > 0.6F) {
                GlStateManager.color(0.0F, 1.0F, 0.0F, 1.0F);
            } else {
                GlStateManager.color(0.8F, 0.5F, 0.0F, 1.0F);
            }
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer renderer = tessellator.getWorldRenderer();
            renderer.startDrawing(3);
            renderer.addVertex(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
            List<double[]> tm = new ArrayList();
            float size = (float) ((item instanceof ItemBow) ? 0.3D : 0.25D);
            boolean hasLanded = false;
            Entity landingOnEntity = null;
            MovingObjectPosition landingPosition = null;
            while ((!hasLanded) && (posY > 0.0D)) {
                Vec3 present = new Vec3(posX, posY, posZ);
                Vec3 future = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
                MovingObjectPosition possibleLandingStrip = mc.theWorld.rayTraceBlocks(present, future, false, true, false);
                if ((possibleLandingStrip != null) && (possibleLandingStrip.typeOfHit != MovingObjectPosition.MovingObjectType.MISS)) {
                    landingPosition = possibleLandingStrip;
                    hasLanded = true;
                }
                AxisAlignedBB arrowBox = new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
                List entities = getEntitiesWithinAABB(arrowBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
                for (Object entity : entities) {
                    Entity boundingBox = (Entity) entity;
                    if ((boundingBox.canBeCollidedWith()) && (boundingBox != mc.thePlayer)) {
                        float var7 = 0.3F;
                        AxisAlignedBB var8 = boundingBox.getEntityBoundingBox().expand(0.30000001192092896D, 0.30000001192092896D, 0.30000001192092896D);
                        MovingObjectPosition possibleEntityLanding = var8.calculateIntercept(present, future);
                        if (possibleEntityLanding != null) {
                            hasLanded = true;
                            landingOnEntity = boundingBox;
                            landingPosition = possibleEntityLanding;
                        }
                    }
                }
                if (landingOnEntity != null) {
                    GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
                }
                posX += motionX;
                posY += motionY;
                posZ += motionZ;
                float motionAdjustment = 0.99F;
                motionX *= 0.9900000095367432D;
                motionY *= 0.9900000095367432D;
                motionZ *= 0.9900000095367432D;
                motionY -= ((item instanceof ItemBow) ? 0.05D : 0.03D);
                renderer.addVertex(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
            }
            tessellator.draw();
            if ((landingPosition != null) && (landingPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)) {
                GlStateManager.translate(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
                int side = landingPosition.field_178784_b.getIndex();
                if (side == 2) {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                } else if (side == 3) {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                } else if (side == 4) {
                    GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                } else if (side == 5) {
                    GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                }
                Cylinder c = new Cylinder();
                GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                c.setDrawStyle(100011);
                if (landingOnEntity != null) {
                    GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glLineWidth(2.5F);
                    c.draw(0.6F, 0.3F, 0.0F, 4, 1);
                    GL11.glLineWidth(0.1F);
                    GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
                }
                c.draw(0.6F, 0.3F, 0.0F, 4, 1);
            }
            RenderingMethods.disableGL3D();
        }
    }

    private List getEntitiesWithinAABB(AxisAlignedBB bb) {
        ArrayList list = new ArrayList();
        int chunkMinX = MathHelper.floor_double((bb.minX - 2.0D) / 16.0D);
        int chunkMaxX = MathHelper.floor_double((bb.maxX + 2.0D) / 16.0D);
        int chunkMinZ = MathHelper.floor_double((bb.minZ - 2.0D) / 16.0D);
        int chunkMaxZ = MathHelper.floor_double((bb.maxZ + 2.0D) / 16.0D);
        for (int x = chunkMinX; x <= chunkMaxX; x++) {
            for (int z = chunkMinZ; z <= chunkMaxZ; z++) {
                if (mc.theWorld.getChunkProvider().chunkExists(x, z)) {
                    mc.theWorld.getChunkFromChunkCoords(x, z).func_177414_a(mc.thePlayer, bb, list, null);
                }
            }
        }
        return list;
    }

}

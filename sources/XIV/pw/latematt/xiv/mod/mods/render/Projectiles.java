package pw.latematt.xiv.mod.mods.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.Render3DEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rederpz
 * @author Whoever did this math originally
 */
public class Projectiles extends Mod implements Listener<Render3DEvent> {
    public Projectiles() {
        super("Projectiles", ModType.RENDER);
    }

    @Override
    public void onEventCalled(Render3DEvent event) {
        double renderPosX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * event.getPartialTicks();
        double renderPosY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * event.getPartialTicks();
        double renderPosZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * event.getPartialTicks();

        ItemStack stack;
        Item item;

        if (mc.thePlayer.getHeldItem() != null && mc.gameSettings.thirdPersonView == 0) {
            if (!isValidPotion(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getItem()) &&
                    mc.thePlayer.getHeldItem().getItem() != Items.experience_bottle &&
                    !(mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod) &&
                    !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) &&
                    !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSnowball) &&
                    !(mc.thePlayer.getHeldItem().getItem() instanceof ItemEnderPearl) &&
                    !(mc.thePlayer.getHeldItem().getItem() instanceof ItemEgg))
                return;
            stack = mc.thePlayer.getHeldItem();
            item = mc.thePlayer.getHeldItem().getItem();
        } else {
            return;
        }

        double posX = renderPosX - MathHelper.cos(mc.thePlayer.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        double posY = renderPosY + mc.thePlayer.getEyeHeight() - 0.1000000014901161D;
        double posZ = renderPosZ - MathHelper.sin(mc.thePlayer.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;

        double motionX = -MathHelper.sin(mc.thePlayer.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(mc.thePlayer.rotationPitch / 180.0F * (float) Math.PI) * (item instanceof ItemBow ? 1.0D : 0.4D);
        double motionY = -MathHelper.sin(mc.thePlayer.rotationPitch / 180.0F * (float) Math.PI) * (item instanceof ItemBow ? 1.0D : 0.4D);
        double motionZ = MathHelper.cos(mc.thePlayer.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(mc.thePlayer.rotationPitch / 180.0F * (float) Math.PI) * (item instanceof ItemBow ? 1.0D : 0.4D);

        int var6 = 72000 - mc.thePlayer.getItemInUseCount();
        float power = var6 / 20.0F;
        power = (power * power + power * 2.0F) / 3.0F;

        if (power < 0.1D)
            return;

        if (power > 1.0F)
            power = 1.0F;

        float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);

        motionX /= distance;
        motionY /= distance;
        motionZ /= distance;

        float pow = (item instanceof ItemBow ? power * 2.0F : isValidPotion(stack, item) ? 0.325F : item instanceof ItemFishingRod ? 1.25F : mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.9F : 1.0F);

        motionX *= pow * (item instanceof ItemFishingRod ? 0.75F : mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.75F : 1.5F);
        motionY *= pow * (item instanceof ItemFishingRod ? 0.75F : mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.75F : 1.5F);
        motionZ *= pow * (item instanceof ItemFishingRod ? 0.75F : mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.75F : 1.5F);

        RenderUtils.beginGl();
        if (power > 0.6F) {
            GlStateManager.color(0.0F, 1.0F, 0.0F, 1.0F);
        } else if (power > 0.3F) {
            GlStateManager.color(0.8F, 0.5F, 0.0F, 1.0F);
        } else {
            GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        renderer.startDrawing(3);

        float size = (float) (item instanceof ItemBow ? 0.3D : 0.25D);
        boolean hasLanded = false;
        Entity landingOnEntity = null;
        MovingObjectPosition landingPosition = null;
        while (!hasLanded && posY > 0.0D) {
            Vec3 present = new Vec3(posX, posY, posZ);
            Vec3 future = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);

            MovingObjectPosition possibleLandingStrip = mc.theWorld.rayTraceBlocks(present, future, false, true, false);
            if (possibleLandingStrip != null && possibleLandingStrip.typeOfHit != MovingObjectPosition.MovingObjectType.MISS) {
                landingPosition = possibleLandingStrip;
                hasLanded = true;
            }

            AxisAlignedBB arrowBox = new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
            List entities = getEntitiesWithinAABB(arrowBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
            for (Object entity : entities) {
                Entity boundingBox = (Entity) entity;
                if ((boundingBox.canBeCollidedWith()) && (boundingBox != mc.thePlayer)) {
                    float var11 = 0.3F;
                    AxisAlignedBB var12 = boundingBox.getEntityBoundingBox().expand(var11, var11, var11);
                    MovingObjectPosition possibleEntityLanding = var12.calculateIntercept(present, future);
                    if (possibleEntityLanding != null) {
                        hasLanded = true;
                        landingOnEntity = boundingBox;
                        landingPosition = possibleEntityLanding;
                    }
                }
            }

            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            float motionAdjustment = 0.99F;
            motionX *= motionAdjustment;
            motionY *= motionAdjustment;
            motionZ *= motionAdjustment;
            motionY -= (item instanceof ItemBow ? 0.05D : 0.03D);
            renderer.addVertex(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
        }
        tessellator.draw();

        if (landingPosition != null && landingPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (landingOnEntity != null) {
                double x = landingOnEntity.lastTickPosX + (landingOnEntity.posX - landingOnEntity.lastTickPosX) * event.getPartialTicks() - mc.getRenderManager().renderPosX;
                double y = landingOnEntity.lastTickPosY + (landingOnEntity.posY - landingOnEntity.lastTickPosY) * event.getPartialTicks() - mc.getRenderManager().renderPosY;
                double z = landingOnEntity.lastTickPosZ + (landingOnEntity.posZ - landingOnEntity.lastTickPosZ) * event.getPartialTicks() - mc.getRenderManager().renderPosZ;

                AxisAlignedBB box = AxisAlignedBB.fromBounds(x - landingOnEntity.width, y, z - landingOnEntity.width, x + landingOnEntity.width, y + landingOnEntity.height + 0.2D, z + landingOnEntity.width);
                if (landingOnEntity instanceof EntityLivingBase) {
                    box = AxisAlignedBB.fromBounds(x - landingOnEntity.width + 0.2D, y, z - landingOnEntity.width + 0.2D, x + landingOnEntity.width - 0.2D, y + landingOnEntity.height + (landingOnEntity.isSneaking() ? 0.02D : 0.2D), z + landingOnEntity.width - 0.2D);
                }

                RenderUtils.drawLines(box);
                RenderGlobal.drawOutlinedBoundingBox(box, -1);
            } else {
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
                c.setDrawStyle(GLU.GLU_LINE);
                c.draw(0.6F, 0.3F, 0.0F, 4, 1);
            }
        }
        RenderUtils.endGl();
    }

    private boolean isValidPotion(ItemStack stack, Item item) {
        if (item != null && item instanceof ItemPotion) {
            ItemPotion potion = (ItemPotion) item;
            if (!ItemPotion.isSplash(stack.getItemDamage()))
                return false;

            if (potion.getEffects(stack) != null) {
                return true;
            }
        }
        return false;
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

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}

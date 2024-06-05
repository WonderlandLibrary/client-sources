package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Projectiles extends Module {
    public Projectiles() {
        super("Projectiles", new String[]{"proj"}, ModuleType.Render);
    }

    @EventHandler
    public void onRender3D(EventRender3D event) {
        Object posBefore;
        if (Module.mc.thePlayer.getHeldItem() == null) {
            return;
        }
        Item item = Module.mc.thePlayer.getHeldItem().getItem();
        boolean isBow = false;
        float motionFactor = 1.5f;
        float motionSlowdown = 0.99f;
        float gravity = 0.0f;
        float size = 0.0f;
        if (item instanceof ItemBow) {
            if (!Module.mc.thePlayer.isUsingItem()) {
                return;
            }
            isBow = true;
            gravity = 0.05f;
            size = 0.3f;
            float power = (float) Module.mc.thePlayer.getItemInUseDuration() / 20.0f;
            if ((power = (power * power + power * 2.0f) / 3.0f) < 0.1f) {
                return;
            }
            if (power > 1.0f) {
                power = 1.0f;
            }
            motionFactor = power * 3.0f;
        } else if (item instanceof ItemFishingRod) {
            gravity = 0.04f;
            size = 0.25f;
            motionSlowdown = 0.92f;
        } else if (item instanceof ItemPotion && ItemPotion.isSplash(Module.mc.thePlayer.getHeldItem().getItemDamage())) {
            gravity = 0.05f;
            size = 0.25f;
            motionFactor = 0.5f;
        } else {
            if (!(item instanceof ItemSnowball || item instanceof ItemEnderPearl || item instanceof ItemEgg)) {
                return;
            }
            gravity = 0.03f;
            size = 0.25f;
        }
        float yaw = Module.mc.thePlayer.rotationYaw;
        float pitch = Module.mc.thePlayer.rotationPitch;
        double posX = RenderManager.renderPosX - (double) (MathHelper.cos(yaw / 180.0f * (float) Math.PI) * 0.16f);
        double posY = RenderManager.renderPosY + (double) Module.mc.thePlayer.getEyeHeight() - (double) 0.1f;
        double posZ = RenderManager.renderPosZ - (double) (MathHelper.sin(yaw / 180.0f * (float) Math.PI) * 0.16f);
        double motionX = (double) (-MathHelper.sin(yaw / 180.0f * (float) Math.PI) * MathHelper.cos(pitch / 180.0f * (float) Math.PI)) * (isBow ? 1.0 : 0.4);
        double motionY = (double) (-MathHelper.sin((pitch + (float) (item instanceof ItemPotion && ItemPotion.isSplash(Module.mc.thePlayer.getHeldItem().getItemDamage()) ? -20 : 0)) / 180.0f * (float) Math.PI)) * (isBow ? 1.0 : 0.4);
        double motionZ = (double) (MathHelper.cos(yaw / 180.0f * (float) Math.PI) * MathHelper.cos(pitch / 180.0f * (float) Math.PI)) * (isBow ? 1.0 : 0.4);
        float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= distance;
        motionY /= distance;
        motionZ /= distance;
        motionX *= motionFactor;
        motionY *= motionFactor;
        motionZ *= motionFactor;
        MovingObjectPosition landingPosition = null;
        boolean hasLanded = false;
        boolean hitEntity = false;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        List<Vec3> pos = new ArrayList<>();
        while (!hasLanded && posY > 0.0) {
            posBefore = new Vec3(posX, posY, posZ);
            Vec3 posAfter = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
            landingPosition = Module.mc.theWorld.rayTraceBlocks((Vec3) posBefore, posAfter, false, true, false);
            posBefore = new Vec3(posX, posY, posZ);
            posAfter = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
            if (landingPosition != null) {
                hasLanded = true;
                posAfter = new Vec3(landingPosition.hitVec.xCoord, landingPosition.hitVec.yCoord, landingPosition.hitVec.zCoord);
            }
            AxisAlignedBB arrowBox = new AxisAlignedBB(posX - (double) size, posY - (double) size, posZ - (double) size, posX + (double) size, posY + (double) size, posZ + (double) size).addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0);
            int chunkMinX = MathHelper.floor_double((arrowBox.minX - 2.0) / 16.0);
            int chunkMaxX = MathHelper.floor_double((arrowBox.maxX + 2.0) / 16.0);
            int chunkMinZ = MathHelper.floor_double((arrowBox.minZ - 2.0) / 16.0);
            int chunkMaxZ = MathHelper.floor_double((arrowBox.maxZ + 2.0) / 16.0);
            List<Entity> collidedEntities = new ArrayList<>();
            int n = chunkMinX;
            if (n <= chunkMaxX) {
                int x;
                do {
                    int z;
                    x = n++;
                    int n2 = chunkMinZ;
                    if (n2 > chunkMaxZ) continue;
                    do {
                        z = n2++;
                        Module.mc.theWorld.getChunkFromChunkCoords(x, z).getEntitiesWithinAABBForEntity(Module.mc.thePlayer, arrowBox, collidedEntities, null);
                    } while (z != chunkMaxZ);
                } while (x != chunkMaxX);
            }
            for (Entity possibleEntity : collidedEntities) {
                AxisAlignedBB possibleEntityBoundingBox;
                MovingObjectPosition movingObjectPosition;
                if (!possibleEntity.canBeCollidedWith() || possibleEntity == Module.mc.thePlayer || (movingObjectPosition = (possibleEntityBoundingBox = possibleEntity.getEntityBoundingBox().expand(size, size, size)).calculateIntercept((Vec3) posBefore, posAfter)) == null)
                    continue;
                MovingObjectPosition possibleEntityLanding = movingObjectPosition;
                hitEntity = true;
                hasLanded = true;
                landingPosition = possibleEntityLanding;
            }
            if (Module.mc.theWorld.getBlockState(new BlockPos(posX += motionX, posY += motionY, posZ += motionZ)).getBlock().getMaterial() == Material.water) {
                motionX *= 0.6;
                motionY *= 0.6;
                motionZ *= 0.6;
            } else {
                motionX *= motionSlowdown;
                motionY *= motionSlowdown;
                motionZ *= motionSlowdown;
            }
            motionY -= gravity;
            pos.add(new Vec3(posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ));
        }
        GL11.glDepthMask(false);
        posBefore = new int[]{3042, 2848};
        RenderUtils.enableGlCap((int[]) posBefore);
        posBefore = new int[]{2929, 3008, 3553};
        RenderUtils.disableGlCap((int[]) posBefore);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        Color color = hitEntity ? new Color(255, 140, 140) : new Color(140, 255, 140);
        RenderUtils.glColor(color);
        GL11.glLineWidth(2.0f);
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        boolean $i$f$forEach = false;
        for (Object element$iv : pos) {
            Vec3 it = (Vec3) element$iv;
            boolean bl = false;
            worldRenderer.pos(it.xCoord, it.yCoord, it.zCoord).endVertex();
        }
        tessellator.draw();
        GL11.glPushMatrix();
        GL11.glTranslated(posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ);
        if (landingPosition != null) {
            switch (landingPosition.sideHit.getAxis().ordinal()) {
                case 0: {
                    GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                    break;
                }
                case 2: {
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                }
            }
            RenderUtils.drawAxisAlignedBB(new AxisAlignedBB(-0.5, 0.0, -0.5, 0.5, 0.1, 0.5), color, true, true, 3.0f);
        }
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        RenderUtils.resetCaps();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}

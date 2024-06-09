/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.Render3DEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

@Module.Mod
public class Trajectories
extends Module {
    @EventTarget(value=0)
    public void onRender(Render3DEvent event) {
        float pow;
        double renderPosX = ClientUtils.player().lastTickPosX + (ClientUtils.player().posX - ClientUtils.player().lastTickPosX) * (double)event.getPartialTicks();
        double renderPosY = ClientUtils.player().lastTickPosY + (ClientUtils.player().posY - ClientUtils.player().lastTickPosY) * (double)event.getPartialTicks();
        double renderPosZ = ClientUtils.player().lastTickPosZ + (ClientUtils.player().posZ - ClientUtils.player().lastTickPosZ) * (double)event.getPartialTicks();
        if (ClientUtils.player().getHeldItem() == null || ClientUtils.mc().gameSettings.thirdPersonView != 0) {
            return;
        }
        if (!(ClientUtils.player().getHeldItem().getItem() instanceof ItemBow)) {
            return;
        }
        ItemStack stack = ClientUtils.player().getHeldItem();
        Item item = ClientUtils.player().getHeldItem().getItem();
        double posX = renderPosX - (double)(MathHelper.cos(ClientUtils.player().rotationYaw / 180.0f * 3.1415927f) * 0.16f);
        double posY = renderPosY + (double)ClientUtils.player().getEyeHeight() - 0.1000000014901161;
        double posZ = renderPosZ - (double)(MathHelper.sin(ClientUtils.player().rotationYaw / 180.0f * 3.1415927f) * 0.16f);
        double motionX = (double)(-MathHelper.sin(ClientUtils.player().rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(ClientUtils.player().rotationPitch / 180.0f * 3.1415927f)) * (item instanceof ItemBow ? 1.0 : 0.4);
        double motionY = (double)(-MathHelper.sin(ClientUtils.player().rotationPitch / 180.0f * 3.1415927f)) * (item instanceof ItemBow ? 1.0 : 0.4);
        double motionZ = (double)(MathHelper.cos(ClientUtils.player().rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(ClientUtils.player().rotationPitch / 180.0f * 3.1415927f)) * (item instanceof ItemBow ? 1.0 : 0.4);
        int var6 = 72000 - ClientUtils.player().getItemInUseCount();
        float power = (float)var6 / 20.0f;
        if ((double)(power = (power * power + power * 2.0f) / 3.0f) < 0.1) {
            return;
        }
        if (power > 1.0f) {
            power = 1.0f;
        }
        float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= (double)distance;
        motionY /= (double)distance;
        motionZ /= (double)distance;
        float f = item instanceof ItemBow ? power * 2.0f : (item instanceof ItemFishingRod ? 1.25f : (pow = ClientUtils.player().getHeldItem().getItem() == Items.experience_bottle ? 0.9f : 1.0f));
        motionX *= (double)(pow * (item instanceof ItemFishingRod ? 0.75f : (ClientUtils.player().getHeldItem().getItem() == Items.experience_bottle ? 0.75f : 1.5f)));
        motionY *= (double)(pow * (item instanceof ItemFishingRod ? 0.75f : (ClientUtils.player().getHeldItem().getItem() == Items.experience_bottle ? 0.75f : 1.5f)));
        motionZ *= (double)(pow * (item instanceof ItemFishingRod ? 0.75f : (ClientUtils.player().getHeldItem().getItem() == Items.experience_bottle ? 0.75f : 1.5f)));
        RenderUtils.enableGL3D(0.4f);
        if (power > 0.6f) {
            GlStateManager.color(0.0f, 1.0f, 0.0f, 1.0f);
        } else {
            GlStateManager.color(0.8f, 0.5f, 0.0f, 1.0f);
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        renderer.startDrawing(3);
        renderer.addVertex(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
        ArrayList tm = new ArrayList();
        float size = (float)(item instanceof ItemBow ? 0.3 : 0.25);
        boolean hasLanded = false;
        Entity landingOnEntity = null;
        MovingObjectPosition landingPosition = null;
        while (!hasLanded && posY > 0.0) {
            Vec3 present = new Vec3(posX, posY, posZ);
            Vec3 future = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
            ClientUtils.mc();
            MovingObjectPosition possibleLandingStrip = Minecraft.theWorld.rayTraceBlocks(present, future, false, true, false);
            if (possibleLandingStrip != null && possibleLandingStrip.typeOfHit != MovingObjectPosition.MovingObjectType.MISS) {
                landingPosition = possibleLandingStrip;
                hasLanded = true;
            }
            AxisAlignedBB arrowBox = new AxisAlignedBB(posX - (double)size, posY - (double)size, posZ - (double)size, posX + (double)size, posY + (double)size, posZ + (double)size);
            List entities = this.getEntitiesWithinAABB(arrowBox.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
            for (Object entity : entities) {
                Entity boundingBox = (Entity)entity;
                if (!boundingBox.canBeCollidedWith() || boundingBox == ClientUtils.player()) continue;
                float var7 = 0.3f;
                AxisAlignedBB var8 = boundingBox.getEntityBoundingBox().expand(0.30000001192092896, 0.30000001192092896, 0.30000001192092896);
                MovingObjectPosition possibleEntityLanding = var8.calculateIntercept(present, future);
                if (possibleEntityLanding == null) continue;
                hasLanded = true;
                landingOnEntity = boundingBox;
                landingPosition = possibleEntityLanding;
            }
            if (landingOnEntity != null) {
                GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);
            }
            float motionAdjustment = 0.99f;
            motionY *= 0.9900000095367432;
            renderer.addVertex((posX += (motionX *= 0.9900000095367432)) - renderPosX, (posY += (motionY -= item instanceof ItemBow ? 0.05 : 0.03)) - renderPosY, (posZ += (motionZ *= 0.9900000095367432)) - renderPosZ);
        }
        tessellator.draw();
        if (landingPosition != null && landingPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GlStateManager.translate(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
            int side = landingPosition.field_178784_b.getIndex();
            if (side == 2) {
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            } else if (side == 3) {
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            } else if (side == 4) {
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            } else if (side == 5) {
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            }
            Cylinder c = new Cylinder();
            GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
            c.setDrawStyle(100011);
            if (landingOnEntity != null) {
                GlStateManager.color(0.0f, 0.0f, 0.0f, 1.0f);
                GL11.glLineWidth((float)2.5f);
                c.draw(0.6f, 0.3f, 0.0f, 4, 1);
                GL11.glLineWidth((float)0.1f);
                GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);
            }
            c.draw(0.6f, 0.3f, 0.0f, 4, 1);
        }
        RenderUtils.disableGL3D();
    }

    private List getEntitiesWithinAABB(AxisAlignedBB bb) {
        ArrayList list = new ArrayList();
        int chunkMinX = MathHelper.floor_double((bb.minX - 2.0) / 16.0);
        int chunkMaxX = MathHelper.floor_double((bb.maxX + 2.0) / 16.0);
        int chunkMinZ = MathHelper.floor_double((bb.minZ - 2.0) / 16.0);
        int chunkMaxZ = MathHelper.floor_double((bb.maxZ + 2.0) / 16.0);
        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                ClientUtils.mc();
                if (!Minecraft.theWorld.getChunkProvider().chunkExists(x, z)) continue;
                ClientUtils.mc();
                Minecraft.theWorld.getChunkFromChunkCoords(x, z).func_177414_a(ClientUtils.player(), bb, list, null);
            }
        }
        return list;
    }
}


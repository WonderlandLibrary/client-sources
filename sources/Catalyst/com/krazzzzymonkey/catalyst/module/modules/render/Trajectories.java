package com.krazzzzymonkey.catalyst.module.modules.render;

import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.entity.EntityPlayerSP;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.ItemPotion;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.math.MathHelper;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemBow;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.krazzzzymonkey.catalyst.module.Modules;

public class Trajectories extends Modules
{


    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        final EntityPlayerSP player = Wrapper.INSTANCE.player();
        final float yaw = Wrapper.INSTANCE.player().rotationYaw;
        final float pitch = Wrapper.INSTANCE.player().rotationPitch;
        final ItemStack itemStack = player.inventory.getCurrentItem();
        if (itemStack == null) {
            return;
        }
        final Item item = itemStack.getItem();
        if (!(item instanceof ItemBow) && !(item instanceof ItemSnowball) && !(item instanceof ItemEgg) && !(item instanceof ItemEnderPearl) && !(item instanceof ItemSplashPotion) && !(item instanceof ItemLingeringPotion) && !(item instanceof ItemFishingRod) {
            return;
        }
        final boolean hasBow = player.inventory.getCurrentItem().getItem() instanceof ItemBow;
        double x1 = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks() - MathHelper.cos((float)Math.toRadians(yaw)) * 0.16f;
        double y1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks() + player.getEyeHeight() - 0.1;
        double z1 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks() - MathHelper.sin((float)Math.toRadians(yaw)) * 0.16f;
        float n;
        if (hasBow) {
            n = 1.0f;
        }
        else {
            n = 0.4f;
        }
        final float float1 = n;
        final float radYaw = (float)Math.toRadians(yaw);
        final float radPitch = (float)Math.toRadians(pitch);
        float float2 = -MathHelper.sin(radYaw) * MathHelper.cos(radPitch) * float1;
        float float3 = -MathHelper.sin(radPitch) * float1;
        float float4 = MathHelper.cos(radYaw) * MathHelper.cos(radPitch) * float1;
        final double double1 = Math.sqrt(float2 * float2 + float3 * float3 + float4 * float4);
        float2 /= (float)double1;
        float3 /= (float)double1;
        float4 /= (float)double1;
        if (hasBow) {
            float float5 = (72000 - player.getItemInUseCount()) / 20.0f;
            float5 = (float5 * float5 + float5 * 2.0f) / 3.0f;
            if ((fcmpl(float5, 1.0f)) > 0) {
                float5 = 1.0f;
            }
            if ((fcmpg(float5, 0.1f)) <= 0) {
                float5 = 1.0f;
            }
            float5 *= 3.0f;
            float2 *= float5;
            float3 *= float5;
            float4 *= float5;
        }
        else {
            float2 *= 1.5;
            float3 *= 1.5;
            float4 *= 1.5;
        }
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glEnable(32925);
        GL11.glDepthMask((boolean)(0 != 0));
        GL11.glLineWidth(1.8f);
        final RenderManager renderManager = Wrapper.INSTANCE.mc().getRenderManager();
        double n2;
        if (hasBow) {
            n2 = 0.05;
        }
        else if (item instanceof ItemPotion) {
            n2 = 0.4;
        }
        else if (item instanceof ItemFishingRod) {
            n2 = 0.15;
        }
        else {
            n2 = 0.03;
        }
        final double double2 = n2;
        final Vec3d vec3d = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        GL11.glColor3d(1.0, 1.0, 1.0);
        GL11.glBegin(3);
        int int1 = 0;
        while (int1 < 1000) {
            GL11.glVertex3d(x1 - renderManager.viewerPosX, y1 - renderManager.viewerPosY, z1 - renderManager.viewerPosZ);
            x1 += float2 * 0.1;
            y1 += float3 * 0.1;
            z1 += float4 * 0.1;
            float2 *= (float)0.999;
            float3 *= (float)0.999;
            float4 *= (float)0.999;
            float3 -= (float)(double2 * 0.1);
            if (Wrapper.INSTANCE.world().rayTraceBlocks(vec3d, new Vec3d(x1, y1, z1)) != null) {
                break;
            }
            else {
                ++int1;
                continue;
            }
        }
        GL11.glEnd();
        final double double3 = x1 - renderManager.viewerPosX;
        final double double4 = y1 - renderManager.viewerPosY;
        final double double5 = z1 - renderManager.viewerPosZ;
        final AxisAlignedBB axisAligned = new AxisAlignedBB(double3 - 0.5, double4 - 0.5, double5 - 0.5, double3 + 0.5, double4 + 0.5, double5 + 0.5);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
        RenderUtils.drawColorBox(axisAligned, 1.0f, 1.0f, 1.0f, 0.15f);
        GL11.glColor4d(1.0, 1.0, 1.0, 0.5);
        RenderUtils.drawSelectionBoundingBox(axisAligned);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(32925);
        GL11.glDepthMask((boolean)(1 != 0));
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        super.onRenderWorldLast(event);
    }
    
    public Trajectories() {
        super("Trajectories", ModuleCategory.RENDER);
    }

    
}
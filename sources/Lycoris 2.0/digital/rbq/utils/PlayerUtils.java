/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class PlayerUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isHoldingSword() {
        return PlayerUtils.mc.thePlayer.getCurrentEquippedItem() != null && PlayerUtils.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }

    public static boolean isOnSameTeam(EntityPlayer entity) {
        if (entity.getTeam() != null && PlayerUtils.mc.thePlayer.getTeam() != null) {
            char c2;
            char c1 = entity.getDisplayName().getFormattedText().charAt(1);
            return c1 == (c2 = PlayerUtils.mc.thePlayer.getDisplayName().getFormattedText().charAt(1));
        }
        return false;
    }

    public static boolean isInLiquid() {
        boolean inLiquid = false;
        AxisAlignedBB playerBB = PlayerUtils.mc.thePlayer.getEntityBoundingBox();
        int y = (int)playerBB.minY;
        for (int x = MathHelper.floor_double(playerBB.minX); x < MathHelper.floor_double(playerBB.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(playerBB.minZ); z < MathHelper.floor_double(playerBB.maxZ) + 1; ++z) {
                Block block = PlayerUtils.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block == null || block instanceof BlockAir) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                inLiquid = true;
            }
        }
        return inLiquid;
    }

    public static boolean isOnLiquid() {
        boolean onLiquid = false;
        AxisAlignedBB playerBB = PlayerUtils.mc.thePlayer.getEntityBoundingBox();
        WorldClient world = PlayerUtils.mc.theWorld;
        int y = (int)playerBB.offset((double)0.0, (double)-0.01, (double)0.0).minY;
        for (int x = MathHelper.floor_double(playerBB.minX); x < MathHelper.floor_double(playerBB.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(playerBB.minZ); z < MathHelper.floor_double(playerBB.maxZ) + 1; ++z) {
                Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block == null || block instanceof BlockAir) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                onLiquid = true;
            }
        }
        return onLiquid;
    }

    public static boolean isOnHypixel() {
        return !mc.isSingleplayer() && PlayerUtils.mc.getCurrentServerData().serverIP.contains("hypixel");
    }

    public static void damage() {
        double offset = 0.0601f;
        NetHandlerPlayClient netHandler = mc.getNetHandler();
        EntityPlayerSP player = PlayerUtils.mc.thePlayer;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        int i = 0;
        while ((double)i < (double)PlayerUtils.getMaxFallDist() / 0.05510000046342611 + 1.0) {
            netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + (double)0.0601f, z, false));
            netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + (double)5.0E-4f, z, false));
            netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + (double)0.005f + 6.01000003516674E-8, z, false));
            ++i;
        }
        netHandler.addToSendQueueSilent(new C03PacketPlayer(true));
    }

    public static float getMaxFallDist() {
        PotionEffect potioneffect = PlayerUtils.mc.thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return PlayerUtils.mc.thePlayer.getMaxFallHeight() + f;
    }

    public static boolean isBlockUnder() {
        EntityPlayerSP player = PlayerUtils.mc.thePlayer;
        WorldClient world = PlayerUtils.mc.theWorld;
        AxisAlignedBB pBb = player.getEntityBoundingBox();
        double height = player.posY + (double)player.getEyeHeight();
        int offset = 0;
        while ((double)offset < height) {
            if (!world.getCollidingBoundingBoxes(player, pBb.offset(0.0, -offset, 0.0)).isEmpty()) {
                return true;
            }
            offset += 2;
        }
        return false;
    }

    public static boolean isInsideBlock() {
        EntityPlayerSP player = PlayerUtils.mc.thePlayer;
        WorldClient world = PlayerUtils.mc.theWorld;
        AxisAlignedBB bb = player.getEntityBoundingBox();
        for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(bb.minY); y < MathHelper.floor_double(bb.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; ++z) {
                    AxisAlignedBB boundingBox;
                    Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == null || block instanceof BlockAir || (boundingBox = block.getCollisionBoundingBox(world, new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)))) == null || !player.getEntityBoundingBox().intersectsWith(boundingBox)) continue;
                    return true;
                }
            }
        }
        return false;
    }
}


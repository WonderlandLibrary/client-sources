// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.player;

import net.minecraft.client.settings.GameSettings;
import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.util.MathHelper;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import xyz.niggfaclient.utils.Utils;

public class PlayerUtil extends Utils
{
    public static int getEnchantment(final ItemStack itemStack, final Enchantment enchantment) {
        if (itemStack == null || itemStack.getEnchantmentTagList() == null || itemStack.getEnchantmentTagList().hasNoTags()) {
            return 0;
        }
        for (int i = 0; i < itemStack.getEnchantmentTagList().tagCount(); ++i) {
            final NBTTagCompound tagCompound = itemStack.getEnchantmentTagList().getCompoundTagAt(i);
            if (tagCompound.getShort("ench") == enchantment.effectId || tagCompound.getShort("id") == enchantment.effectId) {
                return tagCompound.getShort("lvl");
            }
        }
        return 0;
    }
    
    public static boolean isBlockUnderNoCollisions() {
        for (int offset = 0; offset < PlayerUtil.mc.thePlayer.posY + PlayerUtil.mc.thePlayer.getEyeHeight(); offset += 2) {
            final BlockPos blockPos = new BlockPos(PlayerUtil.mc.thePlayer.posX, offset, PlayerUtil.mc.thePlayer.posZ);
            if (PlayerUtil.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.air) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isBlockUnder() {
        for (int y = (int)PlayerUtil.mc.thePlayer.posY; y >= 0; --y) {
            if (!(PlayerUtil.mc.theWorld.getBlockState(new BlockPos(PlayerUtil.mc.thePlayer.posX, y, PlayerUtil.mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isInsideBlock() {
        if (PlayerUtil.mc.thePlayer.ticksExisted < 5) {
            return false;
        }
        for (int x = MathHelper.floor_double(PlayerUtil.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(PlayerUtil.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(PlayerUtil.mc.thePlayer.getEntityBoundingBox().minY); y < MathHelper.floor_double(PlayerUtil.mc.thePlayer.getEntityBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(PlayerUtil.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(PlayerUtil.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                    final Block block = PlayerUtil.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    final AxisAlignedBB boundingBox;
                    if (block != null && !(block instanceof BlockAir) && (boundingBox = block.getCollisionBoundingBox(PlayerUtil.mc.theWorld, new BlockPos(x, y, z), PlayerUtil.mc.theWorld.getBlockState(new BlockPos(x, y, z)))) != null && PlayerUtil.mc.thePlayer.getEntityBoundingBox().intersectsWith(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean isOnLiquid() {
        final double y = PlayerUtil.mc.thePlayer.posY - 0.1;
        for (int x = MathHelper.floor_double(PlayerUtil.mc.thePlayer.posX); x < MathHelper.ceiling_double_int(PlayerUtil.mc.thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(PlayerUtil.mc.thePlayer.posZ); z < MathHelper.ceiling_double_int(PlayerUtil.mc.thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (PlayerUtil.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isInLiquid() {
        final double y = PlayerUtil.mc.thePlayer.posY + 0.01;
        for (int x = MathHelper.floor_double(PlayerUtil.mc.thePlayer.posX); x < MathHelper.ceiling_double_int(PlayerUtil.mc.thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(PlayerUtil.mc.thePlayer.posZ); z < MathHelper.ceiling_double_int(PlayerUtil.mc.thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, (int)y, z);
                if (PlayerUtil.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isOnSameTeam(final EntityPlayer entity) {
        return entity.getTeam() != null && PlayerUtil.mc.thePlayer.getTeam() != null && entity.getDisplayName().getFormattedText().charAt(1) == PlayerUtil.mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
    }
    
    public static List<EntityPlayer> getTabPlayerList() {
        final List<NetworkPlayerInfo> players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(PlayerUtil.mc.thePlayer.sendQueue.getPlayerInfoMap());
        final List<EntityPlayer> playerList = new ArrayList<EntityPlayer>();
        for (final NetworkPlayerInfo playerInfo : players) {
            if (playerInfo != null) {
                playerList.add(PlayerUtil.mc.theWorld.getPlayerEntityByName(playerInfo.getGameProfile().getName()));
            }
        }
        return playerList;
    }
    
    public static Block getBlockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return PlayerUtil.mc.theWorld.getBlockState(new BlockPos(PlayerUtil.mc.thePlayer.posX + offsetX, PlayerUtil.mc.thePlayer.posY + offsetY, PlayerUtil.mc.thePlayer.posZ + offsetZ)).getBlock();
    }
    
    public static void resetCapabilities() {
        PlayerUtil.mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(PlayerUtil.mc.gameSettings.keyBindJump);
        PlayerUtil.mc.thePlayer.stepHeight = 0.625f;
        PlayerUtil.mc.timer.timerSpeed = 1.0f;
        PlayerUtil.mc.thePlayer.capabilities.isFlying = false;
        PlayerUtil.mc.thePlayer.capabilities.allowFlying = PlayerUtil.mc.playerController.isInCreativeMode();
        PlayerUtil.mc.thePlayer.capabilities.isCreativeMode = PlayerUtil.mc.playerController.isInCreativeMode();
        PlayerUtil.mc.thePlayer.isCollided = false;
    }
}

package arsenic.utils.minecraft;

import arsenic.main.Nexus;
import arsenic.module.impl.other.AntiBot;
import arsenic.module.impl.other.Targets;
import arsenic.utils.java.UtilityClass;
import arsenic.utils.rotations.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PlayerUtils extends UtilityClass {

    private static final Minecraft mc = Minecraft.getMinecraft();
    public static final List<Block> BLOCK_BLACKLIST = Arrays.asList(Blocks.enchanting_table, Blocks.chest, Blocks.ender_chest,
            Blocks.trapped_chest, Blocks.anvil, Blocks.sand, Blocks.web, Blocks.torch,
            Blocks.crafting_table, Blocks.furnace, Blocks.waterlily, Blocks.dispenser,
            Blocks.stone_pressure_plate, Blocks.wooden_pressure_plate, Blocks.noteblock,
            Blocks.dropper, Blocks.tnt, Blocks.standing_banner, Blocks.wall_banner, Blocks.redstone_torch);

    public static int findBlock() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
                final ItemBlock itemBlock = (ItemBlock) itemStack.getItem();
                final Block block = itemBlock.getBlock();
                if (block.isFullCube() && !BLOCK_BLACKLIST.contains(block)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void addMessageToChat(String msg) {
        if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(msg));
        }
    }

    public static boolean isPlayerHoldingBlocks() {
        if (mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() == null) {
            return false;
        }
        Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
        return item instanceof ItemBlock;
    }

    public static void addWaterMarkedMessageToChat(Object object) {
        addMessageToChat("§7[§cNexus§7]§r " + object.toString());
    }

    public static boolean playerOverAir() {
        return mc.theWorld.isAirBlock(getBlockUnderPlayer());
    }

    public static BlockPos getBlockUnderPlayer() {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY - 1.0D;
        double z = mc.thePlayer.posZ;
        return new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
    }

    public static EntityPlayer getClosestPlayerWithin(double distance) {
        Targets targetModule = Nexus.getNexus().getModuleManager().getModuleByClass(Targets.class);
        AntiBot antiBot = Nexus.getNexus().getModuleManager().getModuleByClass(AntiBot.class);
        EntityPlayer target = null;
        if (mc.theWorld == null) {
            return null;
        }
        for (EntityPlayer entity : mc.theWorld.playerEntities) {
            if (targetModule.isEnabled()) {
                if (!Targets.teamates.getValue()) {
                    if (Targets.isATeamMate(entity)) {
                        continue;
                    }
                }
            }
            if (antiBot.isEnabled()) {
                if (antiBot.isBot(entity)) {
                    continue;
                }
            }
            float tempDistance = mc.thePlayer.getDistanceToEntity(entity);
            if (entity != mc.thePlayer && tempDistance <= distance) {
                target = entity;
                distance = tempDistance;
            }
        }
        return target;
    }


    public static double direction(float rotationYaw, final double moveForward, final double moveStrafing) {
        if (moveForward < 0F) {
            rotationYaw += 180F;
        }

        float forward = 1F;

        if (moveForward < 0F) {
            forward = -0.5F;
        } else if (moveForward > 0F) {
            forward = 0.5F;
        }

        if (moveStrafing > 0F) {
            rotationYaw -= 90F * forward;
        }
        if (moveStrafing < 0F) {
            rotationYaw += 90F * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    public static BlockPos getBestBlockPos() {
        BlockPos lastOverBlock;
        BlockPos blockPos = PlayerUtils.getBlockUnderPlayer();
        if (!mc.theWorld.isAirBlock(blockPos) && mc.thePlayer.onGround) {
            return blockPos;
        }

        ArrayList<BlockPos> bpArray = new ArrayList<>();
        for (int x = -1; x <= 2; x++) {
            for (int z = -1; z <= 2; z++) {
                blockPos = PlayerUtils.getBlockUnderPlayer().add(x, 0, z);
                if (!mc.theWorld.isAirBlock(blockPos)) {
                    bpArray.add(blockPos);
                }
            }
        }

        if (bpArray.isEmpty()) {
            return null;
        }

        lastOverBlock = bpArray.stream().min(Comparator.comparingDouble((in) -> RotationUtils.getDistanceToBlockPos(in))).get();
        return lastOverBlock;
    }

    public static boolean isBlockUnder(final double xOffset, final double zOffset) {
        for (int offset = 0; offset < mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); offset += 2) {
            final AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox().offset(xOffset, -offset, zOffset);

            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, boundingBox).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlockUnder() {
        for (int offset = 0; offset < mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); offset += 2) {
            final AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);

            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, boundingBox).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPlayerInGame() {
        return (mc.thePlayer != null) && (mc.theWorld != null);
    }
}

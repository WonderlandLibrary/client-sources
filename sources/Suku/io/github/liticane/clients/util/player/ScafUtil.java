package io.github.liticane.clients.util.player;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.module.impl.movement.ScaffoldWalk;
import io.github.liticane.clients.util.interfaces.IMethods;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class ScafUtil implements IMethods {
    //TODO: RECODE THIS UTILL AND THE SCAFFOLD
    public static class BlockData {
        public BlockPos position;
        public EnumFacing facing;

        public BlockData(final BlockPos position, final EnumFacing facing) {
            this.position = position;
            this.facing = facing;
        }

        public BlockPos getPosition() {
            return this.position;
        }

        public EnumFacing getFacing() {
            return this.facing;
        }

    }
    public static float getdirectionyaw() {
        float rotationYaw = Minecraft.getInstance().player.rotationYaw;
        float n = 1.0f;
        if (Minecraft.getInstance().player.movementInput.moveForward < 0.0f) {
            rotationYaw += 180.0f;
            n = -0.5f;
        }
        else if (Minecraft.getInstance().player.movementInput.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (Minecraft.getInstance().player.movementInput.moveStrafe > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Minecraft.getInstance().player.movementInput.moveStrafe < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    public static BlockData placedata() {
        final EnumFacing[] invert = {EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH,
                EnumFacing.EAST, EnumFacing.WEST};
        BlockPos position = new BlockPos(0, 0, 0);
        if (MoveUtil.isMoving() && !mc.settings.keyBindJump.isKeyDown()) {
            for (double n2 = Client.INSTANCE.getModuleManager().get(ScaffoldWalk.class).scaffoldmode.is("Extend") ? Client.INSTANCE.getModuleManager().get(ScaffoldWalk.class).extenda.getValue() : 0 + 0.0001, n3 = 0.0; n3 <= n2; n3 += n2 / (Math.floor(n2))) {
                final BlockPos blockPos2 = new BlockPos(
                        mc.player.posX - MathHelper.sin(getdirectionyaw()) * n3,
                        mc.player.posY - 1.0,
                        mc.player.posZ + MathHelper.cos(getdirectionyaw()) * n3);
                final IBlockState blockState = mc.world.getBlockState(blockPos2);
                if (blockState != null && blockState.getBlock() == Blocks.air) {
                    position = blockPos2;
                    break;
                }
            }
        } else {
            position = new BlockPos(new BlockPos(mc.player.getPositionVector().xCoord,
                    Client.INSTANCE.getModuleManager().get(ScaffoldWalk.class).y.isToggled() ? Client.INSTANCE.getModuleManager().get(ScaffoldWalk.class).Ylevel : mc.player.getPositionVector().yCoord
                    , mc.player.getPositionVector().zCoord))
                    .offset(EnumFacing.DOWN);
        }

        if (!(mc.world.getBlockState(position).getBlock() instanceof BlockAir)
                && !(mc.world.getBlockState(position).getBlock() instanceof BlockLiquid)) {
            return null;
        }
        EnumFacing[] values;
        for (int length = (values = EnumFacing.values()).length, i = 0; i < length; ++i) {
            final EnumFacing facing = values[i];
            final BlockPos offset = position.offset(facing);
            if (!(mc.world.getBlockState(offset).getBlock() instanceof BlockAir)
                    && !(mc.world.getBlockState(position).getBlock() instanceof BlockLiquid)) {
                return new BlockData(offset, invert[facing.ordinal()]);
            }
        }
        final BlockPos[] offsets = {new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1),
                new BlockPos(0, 0, 1)};
        BlockPos[] array;
        for (int length2 = (array = offsets).length, j = 0; j < length2; ++j) {
            final BlockPos offset2 = array[j];
            final BlockPos offsetPos = position.add(offset2.getX(), 0, offset2.getZ());
            if (mc.world.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                EnumFacing[] values2;
                for (int length3 = (values2 = EnumFacing.values()).length, k = 0; k < length3; ++k) {
                    final EnumFacing facing2 = values2[k];
                    final BlockPos offset3 = offsetPos.offset(facing2);
                    if (!(mc.world.getBlockState(offset3).getBlock() instanceof BlockAir)) {
                        return new BlockData(offset3, invert[facing2.ordinal()]);
                    }
                }
            }

        }
        return null;
    }

    public static int getBlockSlot() {
        for (int i = 0; i < 9; i++) {
            final ItemStack itemStack = mc.player.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
                final ItemBlock itemBlock = (ItemBlock) itemStack.getItem();
                if (isBlockValid(itemBlock.getBlock())) {
                    return i;
                }
            }
        }
        return -1;
    }
    private static boolean isBlockValid(final Block block) {
        return (block.isFullBlock() || block == Blocks.glass) &&
                block != Blocks.sand &&
                block != Blocks.gravel &&
                block != Blocks.dispenser &&
                block != Blocks.command_block &&
                block != Blocks.noteblock &&
                block != Blocks.furnace &&
                block != Blocks.crafting_table &&
                block != Blocks.tnt &&
                block != Blocks.dropper &&
                block != Blocks.beacon;
    }

    public static int getLastHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            final ItemStack itemStack = mc.player.inventory.mainInventory[k];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 1) {
                hotbarNum = k;
                continue;
            }
        }
        return hotbarNum;
    }


    public static int getFreeHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            if (mc.player.inventory.mainInventory[k] == null) {
                hotbarNum = k;
                continue;
            } else {
                hotbarNum = 7;
            }
        }
        return hotbarNum;
    }

    public static Vec3 getHypixelVec3(BlockData data) {
        BlockPos pos = data.position;
        EnumFacing face = data.facing;
        double x = (double) pos.getX() + 0.5, y = (double) pos.getY() + 0.5, z = (double) pos.getZ() + 0.5;
        if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
            y += 0.5;
        } else {
            x += 0.3;
            z += 0.3;
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += 0.15;
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += 0.15;
        }
        return new Vec3(x, y, z);
    }

    public static boolean canItemBePlaced(ItemStack item) {
        if (item.getItem().getIdFromItem(item.getItem()) == 116)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 30)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 175)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 28)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 27)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 66)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 157)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 6)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 32)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 140)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 390)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 37)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 38)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 39)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 40)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 69)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 50)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 75)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 76)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 54)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 130)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 146)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 342)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 12)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 77)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 143)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 46)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 145)
            return false;

        return true;
    }
}

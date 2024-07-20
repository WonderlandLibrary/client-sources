/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Math;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BlockUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static BlockPos getOverallPos(BlockPos first, BlockPos second) {
        int x1 = first.getX();
        int y1 = first.getY();
        int z1 = first.getZ();
        int x2 = second.getX();
        int y2 = second.getY();
        int z2 = second.getZ();
        int diffX = x2 - x1;
        int diffY = y2 - y1;
        int diffZ = z2 - z1;
        return new BlockPos(x1 + diffX, y1 + diffY, z1 + diffZ);
    }

    public static Vec3d getOverallVec3d(Vec3d first, Vec3d second, float pc) {
        double x1 = first.xCoord;
        double y1 = first.yCoord;
        double z1 = first.zCoord;
        double x2 = second.xCoord;
        double y2 = second.yCoord;
        double z2 = second.zCoord;
        double diffX = x2 - x1;
        double diffY = y2 - y1;
        double diffZ = z2 - z1;
        return new Vec3d(x1 + diffX * (double)pc, y1 + diffY * (double)pc, z1 + diffZ * (double)pc);
    }

    public static final List<BlockPos> getSphere(Vec3d at, float radius) {
        CopyOnWriteArrayList<BlockPos> posses = new CopyOnWriteArrayList<BlockPos>();
        if (at != null) {
            for (int x = (int)(at.xCoord - (double)radius); x <= (int)(at.xCoord + (double)radius); ++x) {
                for (int y = (int)(at.yCoord - (double)radius); y <= (int)(at.yCoord + (double)radius); ++y) {
                    for (int z = (int)(at.zCoord - (double)radius); z <= (int)(at.zCoord + (double)radius); ++z) {
                        Vec3d vec3d = new Vec3d((double)x + 0.5, (double)y + 0.5, (double)z + 0.5);
                        if (!(at.distanceTo(vec3d) <= (double)radius)) continue;
                        posses.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return posses;
    }

    public static final List<BlockPos> getSphere(EntityLivingBase at, float radius) {
        return BlockUtils.getSphere(at.getPositionEyes(1.0f), radius);
    }

    public static boolean canPlaceCrystal(BlockPos pos, boolean is1l13lplusVersoin) {
        Block block1 = BlockUtils.mc.world.getBlockState(pos).getBlock();
        return !(block1 != Blocks.OBSIDIAN && block1 != Blocks.BEDROCK || !BlockUtils.mc.world.isAirBlock(pos.up()) || !is1l13lplusVersoin && !BlockUtils.mc.world.isAirBlock(pos.up(2)));
    }

    public static boolean getBlockWithExpand(double expand, BlockPos checkPos, Block block, boolean sideSetsMode) {
        if (block == null) {
            return false;
        }
        for (int xs = (int)((double)checkPos.getX() - expand); xs < (int)((double)checkPos.getX() + expand); ++xs) {
            for (int zs = (int)((double)checkPos.getZ() - expand); zs < (int)((double)checkPos.getZ() + expand); ++zs) {
                BlockPos checkedPos;
                Block check;
                double dz;
                double dx = xs - checkPos.getX();
                if (Math.sqrt(dx * dx + (dz = (double)(zs - checkPos.getZ())) * dz) > expand || (check = BlockUtils.mc.world.getBlockState(checkedPos = new BlockPos(xs, checkPos.getY(), zs)).getBlock()) != block || !sideSetsMode || !BlockUtils.blockMaterialIsCurrent(checkPos.down()) && !BlockUtils.blockMaterialIsCurrent(checkPos.down(2)) && !BlockUtils.blockMaterialIsCurrent(checkPos.down(3)) && BlockUtils.blockMaterialIsCurrentWithSideSetsCount(checkPos, true) != 0) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean getBlockWithExpand(double expand, BlockPos checkPos, Block block) {
        return BlockUtils.getBlockWithExpand(expand, checkPos, block, false);
    }

    public static final Material getBlockMaterial(BlockPos pos) {
        return BlockUtils.mc.world.getBlockState(pos).getBlock().getMaterial(BlockUtils.mc.world.getBlockState(pos));
    }

    public static boolean blockMaterialIsCurrent(BlockPos pos) {
        return !BlockUtils.getBlockMaterial(pos).isReplaceable() && !BlockUtils.getBlockMaterial(pos).isLiquid() && BlockUtils.getBlockMaterial(pos).blocksMovement();
    }

    public static int blockMaterialIsCurrentWithSideSetsCount(BlockPos pos, boolean onlyXZCheck) {
        List<Boolean> ifHas = Arrays.asList(BlockUtils.blockMaterialIsCurrent(pos.west()), BlockUtils.blockMaterialIsCurrent(pos.east()), BlockUtils.blockMaterialIsCurrent(pos.south()), BlockUtils.blockMaterialIsCurrent(pos.north()));
        if (!onlyXZCheck) {
            ifHas.add(BlockUtils.blockMaterialIsCurrent(pos.down()));
            ifHas.add(BlockUtils.blockMaterialIsCurrent(pos.up()));
        }
        ifHas = ifHas.stream().filter(b -> b).collect(Collectors.toList());
        return ifHas.size();
    }

    public static boolean blockMaterialIsCurrentWithSideSets(BlockPos pos) {
        return BlockUtils.blockMaterialIsCurrent(pos.west()) || BlockUtils.blockMaterialIsCurrent(pos.east()) || BlockUtils.blockMaterialIsCurrent(pos.south()) || BlockUtils.blockMaterialIsCurrent(pos.north()) || BlockUtils.blockMaterialIsCurrent(pos.down()) || BlockUtils.blockMaterialIsCurrent(pos.up());
    }

    public static boolean isOccupiedByEnt(BlockPos pos, float offsetXYZ, boolean ignoreCrystal) {
        return BlockUtils.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos).expandXyz(offsetXYZ)).stream().anyMatch(e -> ignoreCrystal || !(e instanceof EntityEnderCrystal));
    }

    public static boolean isOccupiedByEnt(BlockPos pos, boolean ignoreCrystal) {
        return BlockUtils.isOccupiedByEnt(pos, 0.0f, ignoreCrystal);
    }

    public static boolean canPlaceObsidian(BlockPos pos, float range, boolean firstPosSpammer, boolean sideSetsMode) {
        boolean aired = BlockUtils.getBlockMaterial(pos).isReplaceable() && BlockUtils.getBlockMaterial(pos.up()).isReplaceable() && BlockUtils.getBlockMaterial(pos.up().up()).isReplaceable();
        boolean neared = BlockUtils.blockMaterialIsCurrentWithSideSets(pos);
        return aired && neared && !BlockUtils.isOccupiedByEnt(pos.up(), firstPosSpammer) && (!BlockUtils.getBlockWithExpand(2.0, pos, Blocks.OBSIDIAN, sideSetsMode) && !BlockUtils.getBlockWithExpand(2.0, pos, Blocks.BEDROCK, sideSetsMode) || BlockUtils.mc.world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK);
    }

    public static BlockPos currentWithSideSetsPos(BlockPos pos) {
        if (BlockUtils.blockMaterialIsCurrent(pos.east())) {
            return pos.east();
        }
        if (BlockUtils.blockMaterialIsCurrent(pos.west())) {
            return pos.west();
        }
        if (BlockUtils.blockMaterialIsCurrent(pos.south())) {
            return pos.south();
        }
        if (BlockUtils.blockMaterialIsCurrent(pos.north())) {
            return pos.north();
        }
        if (BlockUtils.blockMaterialIsCurrent(pos.down())) {
            return pos.down();
        }
        return null;
    }

    public static boolean canPlaceBlock(BlockPos pos) {
        boolean aired = BlockUtils.getBlockMaterial(pos).isReplaceable();
        boolean neared = BlockUtils.currentWithSideSetsPos(pos) != null;
        return aired && neared && !BlockUtils.isOccupiedByEnt(pos, false);
    }

    public static final boolean canPosBeSeenCoord(BlockPos pos, double x, double y, double z) {
        return BlockUtils.mc.world.rayTraceBlocks(new Vec3d((double)pos.getX() + 0.5, pos.getY() + 1, (double)pos.getZ() + 0.5), new Vec3d(x, y + 0.2, z), false, true, false) == null;
    }

    public static boolean isArmor(EntityPlayer entityIn, armorElementSlot slot) {
        return entityIn.inventory.armorItemInSlot(slot.slot).getItem() instanceof ItemArmor;
    }

    public static Vec3d getEntityVec3dPos(Entity entity) {
        return entity == null ? Vec3d.ZERO : new Vec3d(entity.posX, entity.posY, entity.posZ);
    }

    public static int feetCrackPosesCount(EntityLivingBase target, boolean l1l13lplus) {
        int balls = 0;
        CopyOnWriteArrayList<BlockPos> linears = new CopyOnWriteArrayList<BlockPos>();
        BlockPos getEPOS = BlockUtils.getEntityBlockPos(target);
        for (int i = 0; i < 3; ++i) {
            linears.add(getEPOS.east(i));
            linears.add(getEPOS.west(i));
            linears.add(getEPOS.south(i));
            linears.add(getEPOS.north(i));
        }
        for (BlockPos pos : linears) {
            if (!BlockUtils.mc.world.isObsidOrBdBlock(pos.down()) || !BlockUtils.mc.world.isAirBlock(pos) || !l1l13lplus && !BlockUtils.mc.world.isAirBlock(pos.up())) continue;
            ++balls;
        }
        return balls;
    }

    public static boolean canAttackFeetEntity(EntityLivingBase baseIn, boolean l1l13lplus) {
        return BlockUtils.feetCrackPosesCount(baseIn, l1l13lplus) > 0;
    }

    public static boolean canPosBeSeenEntity(BlockPos pos, Entity entityIn, bodyElement bodyElement2) {
        double w = entityIn.width / 3.3f;
        double x = entityIn.posX;
        double y = entityIn.posY + (double)entityIn.height * bodyElement2.height;
        double z = entityIn.posZ;
        return BlockUtils.canPosBeSeenCoord(pos, x, y, z) || BlockUtils.canPosBeSeenCoord(pos, x + w, y, z + w) || BlockUtils.canPosBeSeenCoord(pos, x - w, y, z - w) || BlockUtils.canPosBeSeenCoord(pos, x + w, y, z - w) || BlockUtils.canPosBeSeenCoord(pos, x - w, y, z + w);
    }

    public static boolean canPosBeSeenEntityWithCustomVec(BlockPos pos, Entity entityIn, Vec3d entityVec, bodyElement bodyElement2) {
        double w = entityIn.width / 3.3f;
        double x = entityVec.xCoord;
        double y = entityVec.yCoord + (double)entityIn.height * bodyElement2.height;
        double z = entityVec.zCoord;
        return BlockUtils.canPosBeSeenCoord(pos, x, y, z) || BlockUtils.canPosBeSeenCoord(pos, x + w, y, z + w) || BlockUtils.canPosBeSeenCoord(pos, x - w, y, z - w) || BlockUtils.canPosBeSeenCoord(pos, x + w, y, z - w) || BlockUtils.canPosBeSeenCoord(pos, x - w, y, z + w);
    }

    public static double getDistanceAtPosToPos(BlockPos first, BlockPos second) {
        return Math.sqrt((first.getX() - second.getX()) * (first.getX() - second.getX()) + (first.getY() - second.getY()) * (first.getY() - second.getY()) + (first.getZ() - second.getZ()) * (first.getZ() - second.getZ()));
    }

    public static double getDistanceAtPosToVec(BlockPos first, Vec3d second) {
        return Math.sqrt(((double)first.getX() - second.xCoord) * ((double)first.getX() - second.xCoord) + ((double)first.getY() - second.yCoord) * ((double)first.getY() - second.yCoord) + ((double)first.getZ() - second.zCoord) * ((double)first.getZ() - second.zCoord));
    }

    public static final double getDistanceAtVecToVec(Vec3d first, Vec3d second) {
        return Math.sqrt((first.xCoord - second.xCoord) * (first.xCoord - second.xCoord) + (first.yCoord - second.yCoord) * (first.yCoord - second.yCoord) + (first.zCoord - second.zCoord) * (first.zCoord - second.zCoord));
    }

    public static armorElementSlot armorElementByInt(int i) {
        return i == 0 ? armorElementSlot.FEET : (i == 1 ? armorElementSlot.LEGS : (i == 2 ? armorElementSlot.CHEST : armorElementSlot.HEAD));
    }

    public static boolean canPosBeSeenCoord(Vec3d pos, double x, double y, double z) {
        return Minecraft.getMinecraft().world.rayTraceBlocks(new Vec3d(pos.xCoord, pos.yCoord + 1.0, pos.zCoord), new Vec3d(x, y, z), false, true, false) == null;
    }

    public static boolean canPosBeSeenEntity(Vec3d pos, Entity entityIn, bodyElement bodyElement2) {
        double w = entityIn.width / 2.0f;
        double x = entityIn.posX;
        double y = entityIn.posY + (double)entityIn.height * bodyElement2.height;
        double z = entityIn.posZ;
        return BlockUtils.canPosBeSeenCoord(pos, x, y, z) || BlockUtils.canPosBeSeenCoord(pos, x + w, y, z + w) || BlockUtils.canPosBeSeenCoord(pos, x - w, y, z - w) || BlockUtils.canPosBeSeenCoord(pos, x + w, y, z - w) || BlockUtils.canPosBeSeenCoord(pos, x - w, y, z + w);
    }

    public static boolean canPosBeSeenEntityWithCustomVec(Vec3d pos, Entity entityIn, Vec3d entityVirtPos, bodyElement bodyElement2) {
        double w = entityIn.width / 2.0f;
        double x = entityVirtPos.xCoord;
        double y = entityVirtPos.yCoord + (double)entityIn.height * bodyElement2.height;
        double z = entityVirtPos.zCoord;
        return BlockUtils.canPosBeSeenCoord(pos, x, y, z) || BlockUtils.canPosBeSeenCoord(pos, x + w, y, z + w) || BlockUtils.canPosBeSeenCoord(pos, x - w, y, z - w) || BlockUtils.canPosBeSeenCoord(pos, x + w, y, z - w) || BlockUtils.canPosBeSeenCoord(pos, x - w, y, z + w);
    }

    public static EnumFacing getPlaceableSide(BlockPos pos) {
        for (EnumFacing facing : EnumFacing.values()) {
            IBlockState state;
            BlockPos offset = pos.offset(facing);
            if (!BlockUtils.mc.world.getBlockState(offset).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(offset), false) || (state = BlockUtils.mc.world.getBlockState(offset)).getMaterial().isReplaceable()) continue;
            return facing;
        }
        return null;
    }

    public static EnumFacing getPlaceableSideSeen(BlockPos pos, Entity self) {
        for (EnumFacing facing : EnumFacing.values()) {
            Vec3d placeFaceVec;
            IBlockState state;
            BlockPos offset = pos.offset(facing);
            if (!BlockUtils.mc.world.getBlockState(offset).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(offset), false) || (state = BlockUtils.mc.world.getBlockState(offset)).getMaterial().isReplaceable() || !self.canEntityBeSeenVec3d(placeFaceVec = new Vec3d(pos).addVector(0.5, 0.5, 0.5).addVector((double)facing.getFrontOffsetX() * 0.499, (double)facing.getFrontOffsetY() * 0.499, (double)facing.getFrontOffsetZ() * 0.499))) continue;
            return facing;
        }
        return null;
    }

    public static BlockPos getEntityBlockPos(Entity entity) {
        return entity == null ? BlockPos.ORIGIN : new BlockPos(entity.posX, entity.posY, entity.posZ);
    }

    public static enum armorElementSlot {
        HEAD(3),
        CHEST(2),
        LEGS(1),
        FEET(0);

        public int slot;

        private armorElementSlot(int slot) {
            this.slot = slot;
        }
    }

    public static enum bodyElement {
        HEAD(0.9),
        CHEST(0.65),
        LEGS(0.35),
        FEET(0.15);

        public double height;

        private bodyElement(double height) {
            this.height = height;
        }
    }
}


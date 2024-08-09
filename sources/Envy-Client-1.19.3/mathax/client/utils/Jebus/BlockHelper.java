package mathax.client.utils.Jebus;

import mathax.client.utils.Utils;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.Rotations;
import mathax.client.utils.world.CardinalDirection;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static mathax.client.MatHax.mc;

public class BlockHelper {


    public enum BlockListType {
        Web,
        Surround,
        DoubleSurround,
        SelfTrap
    }

    public static class BlockPosExtended {
        private BlockPos pos;

        public BlockPosExtended() {}
        public BlockPosExtended(BlockPos p) { this.set(p);}

        public void set(BlockPos p) { this.pos = p; }
        public void offset(CardinalDirection d) {this.set(this.pos.offset(d.toDirection()));}
        public void offset(Direction d) {this.set(this.pos.offset(d));}
        public void copy(BlockPosExtended source) {this.set(source.getPos());}
        public void paste(BlockPosExtended destination) {destination.set(this.pos);}
        public boolean nullCheck() {return this.pos != null;}

        public Block getBlock() {return BlockHelper.getBlock(this.pos);}
        public BlockPos getPos() {return this.pos;}
        public Vec3d getBestHitPos() {return bestHitPos(this.pos);}
        public BlockHitResult getHitResult() {return new BlockHitResult(vec3d(this.pos), Direction.UP, this.pos, false);}
        public BlockHitResult getBestHitResult() {return new BlockHitResult(this.getBestHitPos(), Direction.UP, this.pos, false);}
        public double getYaw() {return Rotations.getYaw(this.pos);}
        public double getPitch() {return Rotations.getPitch(this.pos);}
        public double getDistance() {return distanceTo(this.pos);}

        public boolean isAnvil() {return isAnvilBlock(this.pos);}
        public boolean isObsidian() {return isObby(this.pos);}
        public boolean isBedrock() {return BlockHelper.isBedrock(this.pos);}
        public boolean isBlastResistant() {return BlockHelper.isBlastRes(this.pos);}
        public boolean isSolid() {return BlockHelper.isSolid(this.pos);}
        public boolean isTrapBlock() {return BlockHelper.isTrapBlock(this.pos);}
        public boolean isAntiBedBlock() {return isWeb(this.pos);}

        public boolean isHole() {
            int i = 0;
            for (CardinalDirection c : CardinalDirection.values()) if (BlockHelper.isTrapBlock(this.pos.offset(c.toDirection()))) i++;
            return i >= 4;
        }

        public boolean isGreenHole() {
            int i = 0;
            for (CardinalDirection c : CardinalDirection.values()) if (BlockHelper.isBedrock(this.pos.offset(c.toDirection()))) i++;
            return i >= 4;
        }

        public boolean canPlace() {return BlockHelper.canPlace(this.pos);}
        public boolean canReplace() {return isReplacable(this.pos);}
        public boolean canBreak() {return !isBedrock() && this.getBlock() != Blocks.BARRIER;}
        public boolean canSee() {return BlockHelper.canSee(mc.player, this.pos);}

        public Item asItem() {return this.getBlock().asItem();}
        public Vector3d asVec3() {return vec3(this.pos);}
        public Vec3d asVec3d() {return vec3d(this.pos);}
        public Vec3i asVec3i() {return vec3i(this.pos);}
    }

    public static Block getBlock(BlockPos p) {return mc.world.getBlockState(p).getBlock();}
    public static BlockState getState(BlockPos p) {return mc.world.getBlockState(p);}
    public static boolean isAir(BlockPos p) {return getBlock(p) == Blocks.AIR;}
    public static boolean isSolid(BlockPos pos) {return getState(pos).isSolidBlock(mc.world, pos);}
    public static boolean isLiquid(BlockPos pos) {return getBlock(pos) == Blocks.LAVA || getBlock(pos) == Blocks.WATER;}
    public static boolean isReplacable(BlockPos pos) {return getState(pos).getMaterial().isReplaceable();}
    public static boolean isBlastRes(BlockPos pos) {return mc.world.getBlockState(pos).getBlock().getBlastResistance() >= 600;}
    public static boolean isAnvilBlock(BlockPos pos) {return getBlock(pos) == Blocks.ANVIL || getBlock(pos) == Blocks.CHIPPED_ANVIL || getBlock(pos) == Blocks.DAMAGED_ANVIL;}
    public static boolean isWeb(BlockPos pos) {return getBlock(pos) == Blocks.COBWEB || getBlock(pos) == Block.getBlockFromItem(Items.STRING);}
    public static boolean isCobweb(BlockPos pos) {return getBlock(pos) == Blocks.COBWEB;}
    public static boolean isTrapBlock(BlockPos pos) {return isObby(pos) || isEchest(pos) || isAnchor(pos);}
    public static boolean isBurrowBlock(BlockPos pos) {return isTrapBlock(pos) || isAnvilBlock(pos);}
    public static boolean isObby(BlockPos pos) {return getBlock(pos) == Blocks.OBSIDIAN || getBlock(pos) == Blocks.CRYING_OBSIDIAN;}
    public static boolean isBedrock(BlockPos pos) {return getBlock(pos) == Blocks.BEDROCK;}
    public static boolean isEchest(BlockPos pos) {return getBlock(pos) == Blocks.ENDER_CHEST;}
    public static boolean isAnchor(BlockPos pos) {return getBlock(pos) == Blocks.RESPAWN_ANCHOR;}
    public static Vector3d vec3(BlockPos pos) {
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }
    public static Vec3d vec3d(BlockPos pos) {return new Vec3d(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);}
    public static Vec3i vec3i(BlockPos pos) {return new Vec3i(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);}
    public static Vec3i vec3i(Vec3d pos) {return new Vec3i((int) pos.getX(), (int) pos.getY(), (int) pos.getZ());}

    public static boolean isHole(BlockPos p) {
        for (CardinalDirection cd : CardinalDirection.values()) if (isAir(p.offset(cd.toDirection()))) return false;
        return true;
    }

    public static boolean isOurSurroundBlock(BlockPos bp) {
        BlockPos ppos = mc.player.getBlockPos();
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP || direction == Direction.DOWN) continue;
            BlockPos pos = ppos.offset(direction);
            if (pos.equals(bp)) return true;
        }
        return false;
    }

    public static Vec3d bestHitPos(BlockPos pos) {
        if (pos == null) return new Vec3d(0.0, 0.0, 0.0);
        double x = MathHelper.clamp((mc.player.getX() - pos.getX()), 0.0, 1.0);
        double y = MathHelper.clamp((mc.player.getY() - pos.getY()), 0.0, 0.6);
        double z = MathHelper.clamp((mc.player.getZ() - pos.getZ()), 0.0, 1.0);
        return new Vec3d(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
    }

    public static boolean canPlace(BlockPos pos) {
        if (pos == null) return false;
        if (isSolid(pos) || !World.isValid(pos) || !isReplacable(pos)) return false;
        if (!mc.world.canPlace(mc.world.getBlockState(pos), pos, ShapeContext.absent())) return false;
        return mc.world.getBlockState(pos).isAir() || mc.world.getBlockState(pos).getFluidState().getFluid() instanceof FlowableFluid;
    }

    public static void place(BlockPos pos, FindItemResult item, boolean rotate, boolean packet) {
        boolean swap = false;
        if (item == null || !item.found() || !item.isHotbar() || !BlockHelper.canPlace(pos)) return;
        if (!Interactions.isHolding(item)) {
            Interactions.setSlot(item.slot() , false);
            swap = true;
        }
        Direction side = getPlaceSide(pos);
        if (side == null) PacketManager.sendInteract(item.getHand(), item, new BlockHitResult(vec3d(pos), Direction.UP, pos, false), rotate, false);
        else PacketManager.sendInteract(item.getHand(), item, new BlockHitResult(vec3d(pos).add((double) side.getOffsetX() * 0.5D, (double) side.getOffsetY() * 0.5D, (double) side.getOffsetZ() * 0.5D), side, pos.offset(side.getOpposite()), false), rotate, packet);
        if (swap) Interactions.swapBack();
    }

    public static Direction getPlaceSide(BlockPos blockPos) {
        for (Direction side : Direction.values()) {
            BlockPos neighbor = blockPos.offset(side);
            Direction side2 = side.getOpposite();

            BlockState state = mc.world.getBlockState(neighbor);

            // Check if neighbour isn't empty
            if (state.isAir() || isClickable(state.getBlock())) continue;

            // Check if neighbour is a fluid
            if (!state.getFluidState().isEmpty()) continue;

            return side2;
        }

        return null;
    }

    public static boolean isClickable(Block block) {
        return block == Blocks.CRAFTING_TABLE || block instanceof AnvilBlock || block instanceof ButtonBlock || block instanceof AbstractPressurePlateBlock || block instanceof BlockWithEntity || block instanceof BedBlock || block instanceof FenceGateBlock || block instanceof DoorBlock || block instanceof NoteBlock || block instanceof TrapdoorBlock;
    }

    public static List<BlockPos> getSphere(BlockPos centerPos, int radius, int height) {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        for (int i = centerPos.getX() - radius; i < centerPos.getX() + radius; i++) {
            for (int j = centerPos.getY() - height; j < centerPos.getY() + height; j++) {
                for (int k = centerPos.getZ() - radius; k < centerPos.getZ() + radius; k++) {
                    BlockPos pos = new BlockPos(i, j, k);
                    if (distanceBetween(centerPos, pos) <= radius && !blocks.contains(pos)) blocks.add(pos);
                }
            }
        }
        return blocks;
    }

    public static ArrayList<BlockPos> getBlockList(BlockPos center, BlockListType type) {
        if (center == null) return null;
        BlockPos up = center.up();
        final ArrayList<BlockPos> blocks = new ArrayList<>();
        if (type == BlockListType.SelfTrap) blocks.add(center.up(2));
        switch (type) {
            case Web -> IntStream.rangeClosed(0, 3).boxed().sorted(Collections.reverseOrder()).forEach(i -> blocks.add(center.up(i)));
            case Surround -> { for (CardinalDirection dir : CardinalDirection.values()) blocks.add(center.offset(dir.toDirection())); }
            case SelfTrap -> { for (CardinalDirection dir : CardinalDirection.values()) blocks.add(up.offset(dir.toDirection())); }
            case DoubleSurround -> {
                for (CardinalDirection dir : CardinalDirection.values()) blocks.add(center.offset(dir.toDirection()));
                for (CardinalDirection dir : CardinalDirection.values()) blocks.add(up.offset(dir.toDirection()));
            }
        }
        return blocks;
    }

    public static ArrayList<BlockPos> addLegacyPositions(ArrayList<BlockPos> blocks) {
        ArrayList<BlockPos> posi = new ArrayList<>();
        blocks.forEach(blockPos -> {
            BlockPos down = blockPos.down();
            if (!isSolid(down)) posi.add(down);
        });
        return posi;
    }


    public static ArrayList<BlockPos> getSurroundBlocks(PlayerEntity player) { return getBlockList(player.getBlockPos(), BlockListType.Surround); }
    public static ArrayList<BlockPos> getDoubleSurroundBlocks(PlayerEntity player) { return getBlockList(player.getBlockPos(), BlockListType.DoubleSurround); }
    public static ArrayList<BlockPos> getSelfTrapBlocks(PlayerEntity player) { return getBlockList(player.getBlockPos(), BlockListType.SelfTrap); }
    public static ArrayList<BlockPos> getWebBlocks(PlayerEntity player) { return getBlockList(player.getBlockPos(), BlockListType.Web); }


    public static String getBlockDirectionFromPlayer(BlockPos pos) {

        double posX = Math.floor(mc.player.getX());
        double posZ = Math.floor(mc.player.getZ());

        double x = posX - pos.getX();
        double z = posZ - pos.getZ();

        switch (mc.player.getHorizontalFacing()) {
            case SOUTH:
                if (x == 1) {
                    return "right";
                } else if (x == -1) {
                    return "left";
                } else if (z == 1) {
                    return "back";
                } else if (z == -1) {
                    return "front";
                }
                break;
            case WEST:
                if (x == 1) {
                    return "front";
                } else if (x == -1) {
                    return "back";
                } else if (z == 1) {
                    return "right";
                } else if (z == -1) {
                    return "left";
                }
                break;
            case NORTH:
                if (x == 1) {
                    return "left";
                } else if (x == -1) {
                    return "right";
                } else if (z == 1) {
                    return "front";
                } else if (z == -1) {
                    return "back";
                }
                break;
            case EAST:
                if (x == 1) {
                    return "back";
                } else if (x == -1) {
                    return "front";
                } else if (z == 1) {
                    return "left";
                } else if (z == -1) {
                    return "right";
                }
                break;
            default:
                return "undetermined";
        }

        return null;

    }


    // Distance Calculation
    public static double distanceTo(BlockPos pos) {
        return distanceBetween(mc.player.getBlockPos(), pos);
    }

    public static double distanceTo(Vec3d vec) {
        return distance(Utils.vec3d(mc.player.getBlockPos()), vec);
    }

    public static double distanceBetween(BlockPos pos1, BlockPos pos2) {
        double d = pos1.getX() - pos2.getX();
        double e = pos1.getY() - pos2.getY();
        double f = pos1.getZ() - pos2.getZ();
        return MathHelper.sqrt((float) (d * d + e * e + f * f));
    }

    public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dX = x2 - x1;
        double dY = y2 - y1;
        double dZ = z2 - z1;
        return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
    }

    public static double distance(double y1, double y2) {
        double dY = y2 - y1;
        return Math.sqrt(dY * dY);
    }

    public static double distance(Vec3d vec1, Vec3d vec2) {
        double dX = vec2.x - vec1.x;
        double dY = vec2.y - vec1.y;
        double dZ = vec2.z - vec1.z;
        return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
    }

    // Raytracing
    public static Vec3d getEyesPos(Entity entity) {return entity.getPos().add(0, entity.getEyeHeight(entity.getPose()), 0);}

    public static boolean canSee(Entity entity, BlockPos blockPos) {
        Vec3d vec3d = new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ());
        Vec3d vec3d2 = new Vec3d(blockPos.getX(), blockPos.getY() + 0.5f, blockPos.getZ());
        return mc.world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity)).getType() == HitResult.Type.MISS;
    }

    public static float[] calculateAngle(Vec3d from, Vec3d to) {
        double difX = to.x - from.x;
        double difY = (to.y - from.y) * -1.0;
        double difZ = to.z - from.z;
        double dist = MathHelper.sqrt((float) (difX * difX + difZ * difZ));
        float yD = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0);
        float pD = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist)));
        if (pD > 90F) {
            pD = 90F;
        } else if (pD < -90F) {
            pD = -90F;
        }
        return new float[]{yD, pD};
    }

    // Item Lists
    public static ArrayList<Item> wools = new ArrayList<>() {{add(Items.WHITE_WOOL);add(Items.ORANGE_WOOL);add(Items.MAGENTA_WOOL);add(Items.LIGHT_BLUE_WOOL);add(Items.YELLOW_WOOL);add(Items.LIME_WOOL);add(Items.PINK_WOOL);add(Items.GRAY_WOOL);add(Items.LIGHT_GRAY_WOOL);add(Items.CYAN_WOOL);add(Items.PURPLE_WOOL);add(Items.BLUE_WOOL);add(Items.BROWN_WOOL);add(Items.GREEN_WOOL);add(Items.RED_WOOL);add(Items.BLACK_WOOL);}};
    public static ArrayList<Item> planks = new ArrayList<>() {{add(Items.OAK_PLANKS); add(Items.SPRUCE_PLANKS); add(Items.BIRCH_PLANKS); add(Items.JUNGLE_PLANKS); add(Items.ACACIA_PLANKS); add(Items.DARK_OAK_PLANKS);}};
    public static ArrayList<Item> shulkers = new ArrayList<>() {{ add(Items.SHULKER_BOX); add(Items.BLACK_SHULKER_BOX);add(Items.BLUE_SHULKER_BOX); add(Items.BROWN_SHULKER_BOX); add(Items.GREEN_SHULKER_BOX); add(Items.RED_SHULKER_BOX);add(Items.WHITE_SHULKER_BOX); add(Items.LIGHT_BLUE_SHULKER_BOX); add(Items.LIGHT_GRAY_SHULKER_BOX); add(Items.LIME_SHULKER_BOX);add(Items.MAGENTA_SHULKER_BOX); add(Items.ORANGE_SHULKER_BOX); add(Items.PINK_SHULKER_BOX); add(Items.CYAN_SHULKER_BOX);add(Items.GRAY_SHULKER_BOX); add(Items.PURPLE_SHULKER_BOX); add(Items.YELLOW_SHULKER_BOX);}};
    public static ArrayList<Item> saplings = new ArrayList<>() {{add(Items.ACACIA_SAPLING); add(Items.BIRCH_SAPLING); add(Items.SPRUCE_SAPLING); add(Items.JUNGLE_SAPLING); add(Items.DARK_OAK_SAPLING); add(Items.OAK_SAPLING);}};
    public static ArrayList<Item> flowers = new ArrayList<>() {{add(Items.POPPY);add(Items.DANDELION);add(Items.BLUE_ORCHID);add(Items.ALLIUM);add(Items.AZURE_BLUET);add(Items.RED_TULIP);add(Items.PINK_TULIP);add(Items.ORANGE_TULIP);add(Items.WHITE_TULIP);add(Items.OXEYE_DAISY);add(Items.CORNFLOWER);add(Items.LILY_OF_THE_VALLEY);add(Items.WITHER_ROSE);add(Items.SUNFLOWER);add(Items.LILAC);add(Items.ROSE_BUSH);add(Items.PEONY);}};

    // Old hole shit from Ion - todo refactor
    public static List<BlockPos> getHoles(BlockPos startingPos, int rangeH, int rangeV) {
        ArrayList<BlockPos> holes = new ArrayList<>();
        List<BlockPos> blocks = BlockHelper.getSphere(startingPos, rangeH, rangeV);
        blocks.removeIf(b -> BlockHelper.getBlock(b) != Blocks.AIR); // only want air blocks
        blocks.removeIf(block -> BlockHelper.getBlock(block.down()) == Blocks.AIR); // make sure there is a block below it
        blocks.removeIf(block -> !BlockHelper.isHole(block)); // remove any non-hole position
        blocks.removeIf(block -> mc.player.getBlockPos().equals(block)); // remove our own position
        if (!blocks.isEmpty()) holes.addAll(blocks);
        return holes;
    }


    public static BlockPos getHoleNearPlayer(PlayerEntity player, int rangeH, int rangeV) {
        if (player == null) return null;
        List<BlockPos> holes = getHoles(player.getBlockPos(), rangeH, rangeV);
        holes.removeIf(BlockHelper::isHoleObstructed); // remove obstructed holes
        holes.removeIf(hole -> mc.player.getBlockPos().equals(hole)); // remove our own hole
        if (holes.isEmpty()) return null;
        return holes.get(0);
    }



    public static boolean isHoleObstructed(BlockPos pos) {
        return BlockHelper.getBlock(pos.up()) != Blocks.AIR || BlockHelper.getBlock(pos.up(2)) != Blocks.AIR;
    }


}

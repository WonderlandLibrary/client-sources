package com.alan.clients.util.player;

import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.util.vector.Vector2i;
import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * This is a player util which can be used to do various things related to the player
 */
@UtilityClass
public class PlayerUtil implements Accessor {
    private final HashMap<Integer, Integer> GOOD_POTIONS = new HashMap<Integer, Integer>() {{
        put(6, 1); // Instant Health
        put(10, 2); // Regeneration
        put(11, 3); // Resistance
        put(21, 4); // Health Boost
        put(22, 5); // Absorption
        put(23, 6); // Saturation
        put(5, 7); // Strength
        put(1, 8); // Speed
        put(12, 9); // Fire Resistance
        put(14, 10); // Invisibility
        put(3, 11); // Haste
        put(13, 12); // Water Breathing
    }};

    /**
     * Gets the block at a position
     *
     * @return block
     */
    public Block block(final double x, final double y, final double z) {
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    /**
     * Gets the block at a position
     *
     * @return block
     */
    public Block block(final BlockPos blockPos) {
        return mc.theWorld.getBlockState(blockPos).getBlock();
    }

    public Block block(final Vec3i pos) {
        return block(new BlockPos(pos));
    }

    public Block block(final com.alan.clients.util.vector.Vector3d pos) {
        return block(pos.getX(), pos.getY(), pos.getZ());
    }

    public Block block(final Vector3d pos) {
        return block(new BlockPos(new Vec3i(pos.field_181059_a, pos.field_181060_b, pos.field_181061_c)));
    }

    /**
     * Gets the distance between 2 positions
     *
     * @return distance
     */
    public double distance(final BlockPos pos1, final BlockPos pos2) {
        final double x = pos1.getX() - pos2.getX();
        final double y = pos1.getY() - pos2.getY();
        final double z = pos1.getZ() - pos2.getZ();
        return x * x + y * y + z * z;
    }

    public boolean shouldStep() {
        return shouldStep(0);
    }

    public boolean shouldStep(int offset) {
        if (!mc.thePlayer.onGround || !mc.thePlayer.isCollidedHorizontally) {
            return false;
        }

        Vector2i[] steps = {new Vector2i(0, 1), new Vector2i(1, 0), new Vector2i(0, -1), new Vector2i(-1, 0)};

        for (Vector2i step : steps) {
            if (!(PlayerUtil.blockRelativeToPlayer(step.x, offset, step.y) instanceof BlockAir) && PlayerUtil.blockRelativeToPlayer(step.x, 1 + offset, step.y) instanceof BlockAir) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the block relative to the player from the offset
     *
     * @return block relative to the player
     */
    public Block blockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return block(mc.thePlayer.posX + offsetX, mc.thePlayer.posY + offsetY, mc.thePlayer.posZ + offsetZ);
    }

    public Block blockAheadOfPlayer(final double offsetXZ, final double offsetY) {
        return blockRelativeToPlayer(-Math.sin(MoveUtil.direction()) * offsetXZ, offsetY, Math.cos(MoveUtil.direction()) * offsetXZ);
    }

    /**
     * Gets another players' username without any formatting
     *
     * @return players username
     */
    public String name(final EntityPlayer player) {
        return player.getCommandSenderName();
    }

    /**
     * Gets the players' username without any formatting
     *
     * @return players username
     */
    public String name() {
        return mc.thePlayer.getCommandSenderName();
    }

    /**
     * Checks if another players' team is the same as the players' team
     *
     * @return same team
     */
    public boolean sameTeam(final EntityLivingBase player) {
        if (player.getTeam() != null && mc.thePlayer.getTeam() != null) {
            final char c1 = player.getDisplayName().getFormattedText().charAt(1);
            final char c2 = mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
            return c1 == c2;
        }
        return false;
    }

    /**
     * Checks if there is a block under the player
     *
     * @return block under
     */
    public boolean isBlockUnder(final double height) {
        return isBlockUnder(height, true);
    }

    public boolean isBlockUnder(final double height, final boolean boundingBox) {
        if (boundingBox) {
            final AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, -height, 0);

            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
                return true;
            }
        } else {
            for (int offset = 0; offset < height; offset++) {
                if (PlayerUtil.blockRelativeToPlayer(0, -offset, 0).isFullBlock()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isBlockOver(final double height, final boolean boundingBox) {
        final AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, height / 2f, 0).expand(0, height - mc.thePlayer.height, 0);

        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
            return true;
        }

        return false;
    }

    public boolean isBlockUnder() {
        return isBlockUnder(10);
    }

    public double distanceToBlockUnder() {
        double distance = 0;

        for (int i = 0; i < 256; i++) {
            if (blockRelativeToPlayer(0, -i, 0).isFullBlock()) {
                distance = i;
                break;
            }
        }

        return distance;
    }

    /**
     * Checks if a potion is good
     *
     * @return good potion
     */
    public boolean goodPotion(final int id) {
        return GOOD_POTIONS.containsKey(id);
    }

    /**
     * Gets a potions ranking
     *
     * @return potion ranking
     */
    public int potionRanking(final int id) {
        return GOOD_POTIONS.getOrDefault(id, -1);
    }

    /**
     * Checks if the player is in a liquid
     *
     * @return in liquid
     */
    public boolean inLiquid() {
        return mc.thePlayer.isInWater() || mc.thePlayer.isInLava();
    }

    /**
     * Fake damages the player
     */
    public void fakeDamage() {
        mc.thePlayer.handleHealthUpdate((byte) 2);
        mc.ingameGUI.healthUpdateCounter = mc.ingameGUI.updateCounter + 20;
    }

    /**
     * Checks if the player is near a block
     *
     * @return block near
     */
    public boolean blockNear(final int range) {
        for (int x = -range; x <= range; ++x) {
            for (int y = -range; y <= range; ++y) {
                for (int z = -range; z <= range; ++z) {
                    final Block block = blockRelativeToPlayer(x, y, z);

                    if (!(block instanceof BlockAir)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean blockNear(final int range, final Material material) {
        for (int x = -range; x <= range; ++x) {
            for (int y = -range; y <= range; ++y) {
                for (int z = -range; z <= range; ++z) {
                    final Block block = blockRelativeToPlayer(x, y, z);

                    if (block.getMaterial().equals(material)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checks if the player is inside a block
     *
     * @return inside block
     */
    public boolean insideBlock() {
        if (mc.thePlayer.ticksExisted < 5) {
            return false;
        }

        final EntityPlayerSP player = PlayerUtil.mc.thePlayer;
        final WorldClient world = PlayerUtil.mc.theWorld;
        final AxisAlignedBB bb = player.getEntityBoundingBox();
        for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(bb.minY); y < MathHelper.floor_double(bb.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; ++z) {
                    final Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    final AxisAlignedBB boundingBox;
                    if (block != null && !(block instanceof BlockAir) && (boundingBox = block.getCollisionBoundingBox(world, new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)))) != null && player.getEntityBoundingBox().intersectsWith(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Sends a click to Minecraft legitimately
     */
    public void sendClick(final int button, final boolean state) {
        final int keyBind = button == 0 ? mc.gameSettings.keyBindAttack.getKeyCode() : mc.gameSettings.keyBindUseItem.getKeyCode();

        KeyBinding.setKeyBindState(keyBind, state);

        if (state) {
            KeyBinding.onTick(keyBind);
        }
    }

    public static boolean onLiquid() {
        boolean onLiquid = false;
        final AxisAlignedBB playerBB = PlayerUtil.mc.thePlayer.getEntityBoundingBox();
        final WorldClient world = PlayerUtil.mc.theWorld;
        final int y = (int) playerBB.offset(0.0, -0.01, 0.0).minY;
        for (int x = MathHelper.floor_double(playerBB.minX); x < MathHelper.floor_double(playerBB.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(playerBB.minZ); z < MathHelper.floor_double(playerBB.maxZ) + 1; ++z) {
                final Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }

    public EnumFacingOffset getEnumFacing(final Vec3 position) {
        return getEnumFacing(position, false);
    }

    public EnumFacingOffset getEnumFacing(final Vec3 position, boolean downwards) {
        List<EnumFacingOffset> possibleFacings = new ArrayList<>();
        for (int z2 = -1; z2 <= 1; z2 += 2) {
            if (!(PlayerUtil.block(position.xCoord, position.yCoord, position.zCoord + z2).isReplaceable(mc.theWorld, new BlockPos(position.xCoord, position.yCoord, position.zCoord + z2)))) {
                if (z2 < 0) {
                    possibleFacings.add(new EnumFacingOffset(EnumFacing.SOUTH, new Vec3(0, 0, z2)));
                } else {
                    possibleFacings.add(new EnumFacingOffset(EnumFacing.NORTH, new Vec3(0, 0, z2)));
                }
            }
        }

        for (int x2 = -1; x2 <= 1; x2 += 2) {
            if (!(PlayerUtil.block(position.xCoord + x2, position.yCoord, position.zCoord).isReplaceable(mc.theWorld, new BlockPos(position.xCoord + x2, position.yCoord, position.zCoord)))) {
                if (x2 > 0) {
                    possibleFacings.add(new EnumFacingOffset(EnumFacing.WEST, new Vec3(x2, 0, 0)));
                } else {
                    possibleFacings.add(new EnumFacingOffset(EnumFacing.EAST, new Vec3(x2, 0, 0)));
                }
            }
        }

        possibleFacings.sort(Comparator.comparingDouble(enumFacing -> {
            double enumFacingRotations = Math.toDegrees(Math.atan2(enumFacing.getOffset().zCoord,
                    enumFacing.getOffset().xCoord)) % 360;
            double rotations = RotationComponent.rotations.x % 360 + 90;

            return Math.abs(MathUtil.wrappedDifference(enumFacingRotations, rotations));
        }));

        if (!possibleFacings.isEmpty()) return possibleFacings.get(0);

        for (int y2 = -1; y2 <= 1; y2 += 2) {
            if (!(PlayerUtil.block(position.xCoord, position.yCoord + y2, position.zCoord).isReplaceable(mc.theWorld, new BlockPos(position.xCoord, position.yCoord + y2, position.zCoord)))) {
                if (y2 < 0) {
                    return new EnumFacingOffset(EnumFacing.UP, new Vec3(0, y2, 0));
                } else if (downwards) {
                    return new EnumFacingOffset(EnumFacing.DOWN, new Vec3(0, y2, 0));
                }
            }
        }

        return null;
    }

    public Vec3 getPlacePossibility(double offsetX, double offsetY, double offsetZ) {
        return getPlacePossibility(offsetX, offsetY, offsetZ, null);
    }

    // This methods purpose is to get block placement possibilities, blocks are 1 unit thick so please don't change it to 0.5 it causes bugs.
    public Vec3 getPlacePossibility(double offsetX, double offsetY, double offsetZ, Integer plane) {

        final List<Vec3> possibilities = new ArrayList<>();
        final int range = (int) (5 + (Math.abs(offsetX) + Math.abs(offsetZ)));

        for (int x = -range; x <= range; ++x) {
            for (int y = -range; y <= range; ++y) {
                for (int z = -range; z <= range; ++z) {
                    final Block block = PlayerUtil.blockRelativeToPlayer(x, y, z);

                    if (!block.isReplaceable(mc.theWorld, new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z))) {
                        for (int x2 = -1; x2 <= 1; x2 += 2)
                            possibilities.add(new Vec3(mc.thePlayer.posX + x + x2, mc.thePlayer.posY + y, mc.thePlayer.posZ + z));

                        for (int y2 = -1; y2 <= 1; y2 += 2)
                            possibilities.add(new Vec3(mc.thePlayer.posX + x, mc.thePlayer.posY + y + y2, mc.thePlayer.posZ + z));

                        for (int z2 = -1; z2 <= 1; z2 += 2)
                            possibilities.add(new Vec3(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z + z2));
                    }
                }
            }
        }

        possibilities.removeIf(vec3 -> mc.thePlayer.getDistance(vec3.xCoord, vec3.yCoord, vec3.zCoord) > 5 || !(PlayerUtil.block(vec3.xCoord, vec3.yCoord, vec3.zCoord).isReplaceable(mc.theWorld, new BlockPos(vec3.xCoord, vec3.yCoord, vec3.zCoord))));

        if (possibilities.isEmpty()) return null;

        if (plane != null) {
            possibilities.removeIf(vec3 -> Math.floor(vec3.yCoord + 1) != plane);
        }

        possibilities.sort(Comparator.comparingDouble(vec3 -> {

            final double d0 = (mc.thePlayer.posX + offsetX) - vec3.xCoord;
            final double d1 = (mc.thePlayer.posY - 1 + offsetY) - vec3.yCoord;
            final double d2 = (mc.thePlayer.posZ + offsetZ) - vec3.zCoord;
            return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);

        }));

        return possibilities.isEmpty() ? null : possibilities.getFirst();
    }


    // Yes this is gay, yes I should use the clone method, but I'm doing it this way anyway
    public EntityOtherPlayerMP getCopyOfPlayer(EntityOtherPlayerMP entityLivingBase) {
        EntityOtherPlayerMP entity = new EntityOtherPlayerMP(entityLivingBase.getEntityWorld(),
                entityLivingBase.getGameProfile());

        entity.motionX = entityLivingBase.motionX;
        entity.motionY = entityLivingBase.motionY;
        entity.motionZ = entityLivingBase.motionZ;
        entity.rotationYaw = entityLivingBase.rotationYaw;
        entity.setEntityId(entityLivingBase.getEntityId());

        entity.lastTickPosX = entityLivingBase.lastTickPosX;
        entity.lastTickPosY = entityLivingBase.lastTickPosY;
        entity.lastTickPosZ = entityLivingBase.lastTickPosZ;

        entity.setPosition(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ);

        return entity;
    }

    public double calculatePerfectRangeToEntity(Entity entity) {
        double range = 1000;
        Vec3 eyes = mc.thePlayer.getPositionEyes(1);
        Vector2f rotations = RotationUtil.calculate(entity);
        final Vec3 rotationVector = mc.thePlayer.getVectorForRotation(rotations.getY(), rotations.getX());
        MovingObjectPosition movingObjectPosition = entity.getEntityBoundingBox().expand(0.1, 0.1, 0.1).calculateIntercept(eyes,
                eyes.addVector(rotationVector.xCoord * range, rotationVector.yCoord * range, rotationVector.zCoord * range));

        return movingObjectPosition.hitVec.distanceTo(eyes);
    }
}
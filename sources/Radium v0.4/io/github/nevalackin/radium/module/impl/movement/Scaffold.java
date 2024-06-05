package io.github.nevalackin.radium.module.impl.movement;

import io.github.nevalackin.radium.event.impl.player.SafeWalkEvent;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.event.impl.render.Render2DEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.utils.InventoryUtils;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.TimerUtil;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.LockedResolution;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.api.annotations.Priority;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.awt.*;

// TODO: Scaffold
@ModuleInfo(label = "Scaffold", category = ModuleCategory.MOVEMENT)
public final class Scaffold extends Module {

    private final Property<Boolean> swingProperty = new Property<>("Swing", false);
    private final Property<Boolean> towerProperty = new Property<>("Tower", true);
    private final Property<Boolean> keepYProperty = new Property<>("Keep Y", true);
    private final Property<Boolean> safeWalkProperty = new Property<>("Safe Walk", false);
    private final DoubleProperty blockSlotProperty = new DoubleProperty("Block Slot", 9, 1, 9, 1);

    private final TimerUtil towerTimer = new TimerUtil();

    private final BlockPos[] blockPositions;
    private final EnumFacing[] facings;

    private int bestBlockStack;
    private BlockData data;
    private int blockCount;

    private double keepY;

    public Scaffold() {
        this.blockPositions = new BlockPos[]{
                new BlockPos(-1, 0, 0),
                new BlockPos(1, 0, 0),
                new BlockPos(0, 0, -1),
                new BlockPos(0, 0, 1)};

        this.facings = new EnumFacing[]{
                EnumFacing.EAST,
                EnumFacing.WEST,
                EnumFacing.SOUTH,
                EnumFacing.NORTH};
    }

    private static int findBestBlockStack() {
        int bestSlot = -1;
        int blockCount = -1;

        for (int i = InventoryUtils.END - 1; i >= InventoryUtils.EXCLUDE_ARMOR_BEGIN; --i) {
            ItemStack stack = Wrapper.getStackInSlot(i);

            if (stack != null &&
                    stack.getItem() instanceof ItemBlock &&
                    InventoryUtils.isGoodBlockStack(stack)) {
                if (stack.stackSize > blockCount) {
                    bestSlot = i;
                    blockCount = stack.stackSize;
                }
            }
        }

        return bestSlot;
    }

    @Listener
    public void onSafeWalkEvent(SafeWalkEvent event) {
        event.setCancelled(safeWalkProperty.getValue());
    }

    @Override
    public void onEnable() {
        towerTimer.reset();
        blockCount = 0;
        data = null;
        this.keepY = Wrapper.getPlayer().posY - 1.0D;

    }

    private BlockPos getBlockUnder(boolean keepY) {
        return new BlockPos(
                Wrapper.getPlayer().posX,
                keepY ? this.keepY : Wrapper.getPlayer().posY - 1.0D,
                Wrapper.getPlayer().posZ);
    }


    @Listener(Priority.HIGH)
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            updateBlockCount();

            this.data = null;

            if ((bestBlockStack = findBestBlockStack()) != -1) {
                if (bestBlockStack < 36) {
                    int blockSlot = blockSlotProperty.getValue().intValue() - 1;
                    for (int i = InventoryUtils.END; i >= InventoryUtils.ONLY_HOT_BAR_BEGIN; i--) {
                        ItemStack stack = Wrapper.getStackInSlot(i);

                        if (!InventoryUtils.isValid(stack)) {
                            InventoryUtils.windowClick(bestBlockStack, blockSlot,
                                    InventoryUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
                            bestBlockStack = blockSlot + 36;
                            break;
                        }
                    }
                }

                boolean isKeepY = keepYProperty.getValue();
                BlockPos blockUnder = getBlockUnder(isKeepY);
                BlockData data = this.getBlockData(blockUnder);

                if (isKeepY && (Math.abs(Wrapper.getPlayer().posY - keepY) > 2.0D || data == null))
                    keepY = Wrapper.getPlayer().posY - 1.0D;

                if (data == null) {
                    if (isKeepY)
                        blockUnder = getBlockUnder(true);
                    data = this.getBlockData(blockUnder.offset(EnumFacing.DOWN));
                }

                if (data != null) {
                    // TODO: Tower collision check: 0.0625D
                    if (towerProperty.getValue() && Wrapper.getGameSettings().keyBindJump.isKeyDown() && !MovementUtils.isMoving() &&
                            !Wrapper.getWorld().getCollidingBoundingBoxes(
                                    Wrapper.getPlayer(),
                                    Wrapper.getPlayer().getEntityBoundingBox().offset(0, -0.0625D, 0)).isEmpty()) {
                        Wrapper.getPlayer().motionX = 0.0D;
                        Wrapper.getPlayer().motionY = MovementUtils.getJumpHeight(MovementUtils.VANILLA_JUMP_HEIGHT);
                        Wrapper.getPlayer().motionZ = 0.0D;

                        if (towerTimer.hasElapsed(1500)) {
                            Wrapper.getPlayer().motionY = -0.28D;
                            towerTimer.reset();
                        }

                        event.setPitch(90.0F);
                    } else {
                        float[] rotations = getRotationsToBlockData(data, event);
                        event.setYaw(rotations[0]);
                        event.setPitch(rotations[1]);
                        towerTimer.reset();
                    }
                }

                this.data = data;
            }
        } else {
            if (bestBlockStack != -1 && bestBlockStack >= 36 && data != null &&
                    (validateReplaceable(data) || MovementUtils.isMoving()) &&
                    rayTrace(event.getYaw(), event.getPitch(), data)) {
                int hotBarSlot = bestBlockStack - 36;
                int oldSlot = Wrapper.getPlayer().inventory.currentItem;
                if (oldSlot != hotBarSlot) {
                    Wrapper.getPlayer().inventory.currentItem = hotBarSlot;
                    Wrapper.sendPacketDirect(new C09PacketHeldItemChange(hotBarSlot));
                }
                if (Wrapper.getPlayerController().onPlayerRightClick(Wrapper.getPlayer(), Wrapper.getWorld(),
                        Wrapper.getPlayer().getCurrentEquippedItem(), data.pos, data.face, data.getHitVec())) {
                    if (swingProperty.getValue())
                        Wrapper.getPlayer().swingItem();
                    else
                        Wrapper.sendPacket(new C0APacketAnimation());
                }
                if (oldSlot != hotBarSlot) {
                    Wrapper.sendPacketDirect(new C09PacketHeldItemChange(oldSlot));
                    Wrapper.getPlayer().inventory.currentItem = oldSlot;
                }
            }
        }
    }

    private boolean rayTrace(float yaw,
                             float pitch,
                             BlockData data) {
        Vec3 src = Wrapper.getPlayer().getPositionEyes(1.0F);
        Vec3 rotationVec = Entity.getVectorForRotation(pitch, yaw);
        Vec3 dest = src.addVector(rotationVec.xCoord * 4.0F, rotationVec.yCoord * 4.0F, rotationVec.zCoord * 4.0F);
        IBlockState blockState = Wrapper.getWorld().getBlockState(data.pos);
        AxisAlignedBB bb = blockState.getBlock().getCollisionBoundingBox(Wrapper.getWorld(), data.pos, blockState);
        return bb.calculateIntercept(src, dest) != null;
    }

    private float[] getRotationsToBlockData(BlockData data, UpdatePositionEvent ev) {
        EntityPlayerSP entity = Wrapper.getPlayer();
        Vec3 hitVec = data.getHitVec();
        double x = (hitVec.xCoord - entity.posX);
        double y = (hitVec.yCoord - (entity.posY + entity.getEyeHeight()));
        double z = (hitVec.zCoord - entity.posZ);

        double fDist = MathHelper.sqrt_double(x * x + z * z);

        float yaw = interpolateRotation(ev.getPrevYaw(), (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F);
        float pitch = interpolateRotation(ev.getPrevPitch(), (float) (-(Math.atan2(y, fDist) * 180.0D / Math.PI)));

        return new float[]{yaw, MathHelper.clamp_float(pitch, -90.0f, 90.0f)};
    }

    private float interpolateRotation(float p_70663_1_,
                                      float p_70663_2_) {
        float maxTurn = 45.0F;
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);

        if (var4 > maxTurn) {
            var4 = maxTurn;
        }

        if (var4 < -maxTurn) {
            var4 = -maxTurn;
        }

        return p_70663_1_ + var4;
    }


    private boolean validateDataRange(BlockData data) {
        Vec3 hitVec = data.getHitVec();
        EntityPlayerSP entity = Wrapper.getPlayer();
        double x = (hitVec.xCoord - entity.posX);
        double y = (hitVec.yCoord - (entity.posY + entity.getEyeHeight()));
        double z = (hitVec.zCoord - entity.posZ);
        return Math.sqrt(x * x + y * y + z * z) <= 4.0D;
    }

    private boolean validateReplaceable(BlockData data) {
        BlockPos pos = data.pos.offset(data.face);
        World world = Wrapper.getWorld();
        return world.getBlockState(pos)
                .getBlock()
                .isReplaceable(world, pos);
    }

    private BlockData getBlockData(BlockPos pos) {
        BlockPos[] blockPositions = this.blockPositions;
        EnumFacing[] facings = this.facings;
        WorldClient world = Wrapper.getWorld();

        // 1 of the 4 directions around player
        for (int i = 0; i < blockPositions.length; i++) {
            BlockPos blockPos = pos.add(blockPositions[i]);
            if (InventoryUtils.isValidBlock(world.getBlockState(blockPos).getBlock(), false)) {
                BlockData data = new BlockData(blockPos, facings[i]);
                if (validateDataRange(data))
                    return data;
            }
        }

        // 2 Blocks Under e.g. When jumping
        BlockPos posBelow = new BlockPos(0, -1, 0);
        if (InventoryUtils.isValidBlock(world.getBlockState(pos.add(posBelow)).getBlock(), false)) {
            BlockData data = new BlockData(pos.add(posBelow), EnumFacing.UP);
            if (validateDataRange(data))
                return data;
        }

        // 2 Block extension & diagonal
        for (BlockPos blockPosition : blockPositions) {
            BlockPos blockPos = pos.add(blockPosition);
            for (int i = 0; i < blockPositions.length; i++) {
                BlockPos blockPos1 = blockPos.add(blockPositions[i]);
                if (InventoryUtils.isValidBlock(world.getBlockState(blockPos1).getBlock(), false)) {
                    BlockData data = new BlockData(blockPos1, facings[i]);
                    if (validateDataRange(data))
                        return data;
                }
            }
        }

        // 3 Block extension
        for (BlockPos blockPosition : blockPositions) {
            BlockPos blockPos = pos.add(blockPosition);
            for (BlockPos position : blockPositions) {
                BlockPos blockPos1 = blockPos.add(position);
                for (int i = 0; i < blockPositions.length; i++) {
                    BlockPos blockPos2 = blockPos1.add(blockPositions[i]);
                    if (InventoryUtils.isValidBlock(world.getBlockState(blockPos2).getBlock(), false)) {
                        BlockData data = new BlockData(blockPos2, facings[i]);
                        if (validateDataRange(data))
                            return data;
                    }
                }
            }
        }

        return null;
    }

    @Listener
    public void onRender2DEvent(Render2DEvent event) {
        LockedResolution resolution = event.getResolution();
        float x = resolution.getWidth() / 2.0F;
        float y = resolution.getHeight() / 2.0F + 15;

        float percentage = Math.min(1, this.blockCount / 128.0F);

        float width = 80.0F;
        float half = width / 2;

        int color = RenderingUtils.getColorFromPercentage(percentage);

        Gui.drawRect(x - half - 0.5F, y - 2, x + half + 0.5F, y + 2, 0x78000000);
        Gui.drawGradientRect(x - half, y - 1.5F, x - half + (width * percentage), y + 1.5F,
                color, new Color(color).darker().getRGB());

    }

    private void updateBlockCount() {
        blockCount = 0;

        Wrapper.forEachInventorySlot(InventoryUtils.EXCLUDE_ARMOR_BEGIN, InventoryUtils.END, (slot, stack) -> {
            if (stack.getItem() instanceof ItemBlock && InventoryUtils.isGoodBlockStack(stack))
                blockCount += stack.stackSize;
        });
    }

    private static class BlockData {
        private final BlockPos pos;
        private final EnumFacing face;

        public BlockData(BlockPos pos, EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }

        private Vec3 getHitVec() {
            Vec3i directionVec = face.getDirectionVec();
            double x = directionVec.getX() * 0.5D;
            double y = directionVec.getY() * 0.5D;
            double z = directionVec.getZ() * 0.5D;

            return new Vec3(pos)
                    .addVector(0.5D, 0.5D, 0.5D)
                    .addVector(x, y, z);
        }
    }
}

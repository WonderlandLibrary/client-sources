package me.finz0.osiris.module.modules.misc;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.modules.combat.AutoFeetPlace;
import me.finz0.osiris.module.modules.combat.Surround;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class AutoNomadHut extends Module {
    public AutoNomadHut() {
        super("AutoNomadHut", Category.MISC, "Builds a nomad hut around you");
        this.offsetStep = 0;
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }

    private BlockPos basePos;
    private int offsetStep;
    private int playerHotbarSlot;
    private int lastHotbarSlot;
    Setting bpt;
    Vec3d[] surroundTargets = new Vec3d[]{new Vec3d(0.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 0.0D, -1.0D), new Vec3d(-1.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, -1.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(2.0D, 0.0D, 1.0D), new Vec3d(2.0D, 0.0D, -1.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(-2.0D, 0.0D, 1.0D), new Vec3d(-2.0D, 0.0D, -1.0D), new Vec3d(0.0D, 0.0D, 2.0D), new Vec3d(1.0D, 0.0D, 2.0D), new Vec3d(-1.0D, 0.0D, 2.0D), new Vec3d(0.0D, 0.0D, -2.0D), new Vec3d(-1.0D, 0.0D, -2.0D), new Vec3d(1.0D, 0.0D, -2.0D), new Vec3d(2.0D, 1.0D, -1.0D), new Vec3d(2.0D, 1.0D, 1.0D), new Vec3d(-2.0D, 1.0D, 0.0D), new Vec3d(-2.0D, 1.0D, 1.0D), new Vec3d(-2.0D, 1.0D, -1.0D), new Vec3d(0.0D, 1.0D, 2.0D), new Vec3d(1.0D, 1.0D, 2.0D), new Vec3d(-1.0D, 1.0D, 2.0D), new Vec3d(0.0D, 1.0D, -2.0D), new Vec3d(1.0D, 1.0D, -2.0D), new Vec3d(-1.0D, 1.0D, -2.0D), new Vec3d(2.0D, 2.0D, -1.0D), new Vec3d(2.0D, 2.0D, 1.0D), new Vec3d(-2.0D, 2.0D, 1.0D), new Vec3d(-2.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 2.0D), new Vec3d(-1.0D, 2.0D, 2.0D), new Vec3d(1.0D, 2.0D, -2.0D), new Vec3d(-1.0D, 2.0D, -2.0D), new Vec3d(2.0D, 3.0D, 0.0D), new Vec3d(2.0D, 3.0D, -1.0D), new Vec3d(2.0D, 3.0D, 1.0D), new Vec3d(-2.0D, 3.0D, 0.0D), new Vec3d(-2.0D, 3.0D, 1.0D), new Vec3d(-2.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 2.0D), new Vec3d(1.0D, 3.0D, 2.0D), new Vec3d(-1.0D, 3.0D, 2.0D), new Vec3d(0.0D, 3.0D, -2.0D), new Vec3d(1.0D, 3.0D, -2.0D), new Vec3d(-1.0D, 3.0D, -2.0D), new Vec3d(0.0D, 4.0D, 0.0D), new Vec3d(1.0D, 4.0D, 0.0D), new Vec3d(-1.0D, 4.0D, 0.0D), new Vec3d(0.0D, 4.0D, 1.0D), new Vec3d(0.0D, 4.0D, -1.0D), new Vec3d(1.0D, 4.0D, 1.0D), new Vec3d(-1.0D, 4.0D, 1.0D), new Vec3d(-1.0D, 4.0D, -1.0D), new Vec3d(1.0D, 4.0D, -1.0D), new Vec3d(2.0D, 4.0D, 0.0D), new Vec3d(2.0D, 4.0D, 1.0D), new Vec3d(2.0D, 4.0D, -1.0D)};

    public void setup(){
        AuroraMod.getInstance().settingsManager.rSetting(bpt = new Setting("BlocksPerTick", this, 8, 1, 20, true, "AutoNomadHutBPT"));
    }

    public void onUpdate() {
        if (!isEnabled() || AutoNomadHut.mc.player == null ) {
            return;
        }
        if (this.offsetStep == 0) {
            this.basePos = new BlockPos(AutoNomadHut.mc.player.getPositionVector()).down();
            this.playerHotbarSlot = mc.player.inventory.currentItem;
        }
        for (int i = 0; i < (int)Math.floor(bpt.getValDouble()); ++i) {
            if (this.offsetStep >= this.surroundTargets.length) {
                this.endLoop();
                return;
            }
            final Vec3d offset = this.surroundTargets[this.offsetStep];
            this.placeBlock(new BlockPos((Vec3i)this.basePos.add(offset.x, offset.y, offset.z)));
            ++this.offsetStep;
        }
    }

    protected void onEnable() {
        if (AutoNomadHut.mc.player == null) {
            this.disable();
            return;
        }
        this.playerHotbarSlot = mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
    }

    protected void onDisable() {
        if (AutoNomadHut.mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }

    private void endLoop() {
        this.offsetStep = 0;
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            mc.player.inventory.currentItem = this.playerHotbarSlot;
            this.lastHotbarSlot = this.playerHotbarSlot;
        }
            //this.disable();
    }

    private void placeBlock(final BlockPos blockPos) {
        if (!mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            return;
        }
        if (!AutoFeetPlace.hasNeighbour(blockPos)) {
            return;
        }
        this.placeBlockExecute(blockPos);
    }

    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockObsidian) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }

    public void placeBlockExecute(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (!canBeClicked(neighbor)) {
            }
            else {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) > 18.0625) {
                }
                else {
                    boolean needSneak = false;
                    final Block blockBelow = AutoNomadHut.mc.world.getBlockState(neighbor).getBlock();
                    //if (BlockInteractionHelper.blackList.contains(blockBelow) || BlockInteractionHelper.shulkerList.contains(blockBelow)) {
                        needSneak = true;
                    //}
                    if (needSneak) {
                        AutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoNomadHut.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    final int obiSlot = this.findObiInHotbar();
                    if (obiSlot == -1) {
                        this.disable();
                        return;
                    }
                    if (this.lastHotbarSlot != obiSlot) {
                        mc.player.inventory.currentItem = obiSlot;
                        this.lastHotbarSlot = obiSlot;
                    }
                    AutoNomadHut.mc.playerController.processRightClickBlock(mc.player, AutoNomadHut.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    AutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                    if (needSneak) {
                        AutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoNomadHut.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    return;
                }
            }
        }
    }

    private static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }

    private static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }

    private static IBlockState getState(final BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    private static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getLegitRotations(vec);
        mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));
    }

    private static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch) };
    }

    private static Vec3d getEyesPos() {
        return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
    }


}

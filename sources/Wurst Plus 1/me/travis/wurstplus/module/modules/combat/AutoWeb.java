package me.travis.wurstplus.module.modules.combat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.Module.Info;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.Friends;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

// made by travis :P

@Info(name = "AutoWeb", category = Module.Category.COMBAT)
public class AutoWeb extends Module {
    BlockPos head;
    BlockPos feet;
    private Setting<Integer> delay = this.register(Settings.integerBuilder("Delay").withRange(0, 10).withValue(3).build());
    int d;
    public static EntityPlayer target;
    public static List<EntityPlayer> targets;
    public static float yaw;
    public static float pitch;

    public boolean isInBlockRange(Entity target) {
        return (target.getDistance(mc.player) <= 4.0F);
    }

    public static boolean canBeClicked(BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock().canCollideCheck(mc.world.getBlockState(pos),
                false);
    }

    public boolean isValid(EntityPlayer entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer animal = entity;
            if (isInBlockRange(animal) && animal.getHealth() > 0.0F && !animal.isDead
                    && !animal.getName().startsWith("Body #") && !Friends.isFriend(animal.getName())) {
                return true;
            }
        }
        return false;
    }

    public void loadTargets() {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (!(player instanceof net.minecraft.client.entity.EntityPlayerSP)) {
                EntityPlayer p = player;
                if (isValid(p)) {
                    targets.add(p);
                    continue;
                }
                if (!targets.contains(p)) {
                    continue;
                }
                targets.remove(p);
            }
        }
    }

    private boolean isStackObby(ItemStack stack) {
        return (stack != null && stack.getItem() == Item.getItemById(30));
    }

    private boolean doesHotbarHaveWeb() {
        for (int i = 36; i < 45; i++) {
            ItemStack stack = mc.player.inventoryContainer.getSlot(i).getStack();
            if (stack != null && isStackObby(stack)) {
                return true;
            }
        }
        return false;
    }

    public static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }

    public static IBlockState getState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    public static boolean placeBlockLegit(BlockPos pos) {
        Vec3d eyesPos = new Vec3d(mc.player.posX,
                mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
        Vec3d posVec = (new Vec3d(pos)).add(0.5D, 0.5D, 0.5D);
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            if (canBeClicked(neighbor)) {
                Vec3d hitVec = posVec.add((new Vec3d(side.getDirectionVec())).scale(0.5D));
                if (eyesPos.squareDistanceTo(hitVec) <= 36.0D) {
                    mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor,
                            side.getOpposite(), hitVec, EnumHand.MAIN_HAND);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void onUpdate() {
        if (mc.player.isHandActive()) {
            return;
        }
        if (!isValid(target) || target == null) {
            updateTarget();
        }
        for (EntityPlayer player : mc.world.playerEntities) {
            if (!(player instanceof net.minecraft.client.entity.EntityPlayerSP)) {
                EntityPlayer e = player;
                if (isValid(e) && e.getDistance(mc.player) < target.getDistance(mc.player)) {
                    target = e;

                    return;
                }
            }
        }
        if (isValid(target) && mc.player.getDistance(target) < 4.0F) {
            trap(target);
        } else {
            this.d = 0;
        }
    }

    public void onEnable() {
        if (mc.player == null) {
            this.disable();
            return;
        }
    }

    private void trap(EntityPlayer player) {
        if (player.moveForward == 0.0D && player.moveStrafing == 0.0D
                && player.moveForward == 0.0D) {
            this.d++;
        }
        if (player.moveForward != 0.0D || player.moveStrafing != 0.0D
                || player.moveForward != 0.0D) {
            this.d = 0;
        }
        if (!doesHotbarHaveWeb()) {
            this.d = 0;
        }
        if (this.d == this.delay.getValue() && doesHotbarHaveWeb()) {
            this.head = new BlockPos(player.posX, player.posY + 1.0D, player.posZ);
            this.feet = new BlockPos(player.posX, player.posY, player.posZ);

            for (int i = 36; i < 45; i++) {
                ItemStack stack = mc.player.inventoryContainer.getSlot(i).getStack();
                if (stack != null && isStackObby(stack)) {
                    int oldSlot = mc.player.inventory.currentItem;
                    if (mc.world.getBlockState(this.head).getMaterial().isReplaceable()
                            || mc.world.getBlockState(this.feet).getMaterial().isReplaceable()) {
                        mc.player.inventory.currentItem = i - 36;
                        if (player.moveForward == 0.0D || player.moveStrafing == 0.0D)
                        {
                            if (mc.world.getBlockState(this.head).getMaterial().isReplaceable()) {
                                placeBlockLegit(this.head);
                            }
                        }
                        else
                        {
                            if (mc.world.getBlockState(this.head).getMaterial().isReplaceable()) {
                                placeBlockLegit(this.head);
                            }
                            if (mc.world.getBlockState(this.feet).getMaterial().isReplaceable()) {
                                placeBlockLegit(this.feet);
                            }
                        }

                        mc.player.inventory.currentItem = oldSlot;
                        this.d = 0;
                        break;
                    }
                    this.d = 0;
                }
                this.d = 0;
            }
        }
    }

    public void onDisable() {
        this.d = 0;
        yaw = mc.player.rotationYaw;
        pitch = mc.player.rotationPitch;
        target = null;
    }

    public void updateTarget() {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (!(player instanceof net.minecraft.client.entity.EntityPlayerSP)) {
                EntityPlayer entity = player;
                if (entity instanceof net.minecraft.client.entity.EntityPlayerSP || !isValid(entity)) {
                    continue;
                }
                target = entity;
            }
        }
    }

    public EnumFacing getEnumFacing(float posX, float posY, float posZ) {
        return EnumFacing.getFacingFromVector(posX, posY, posZ);
    }

    public BlockPos getBlockPos(double x, double y, double z) {
        return new BlockPos(x, y, z);
    }
}
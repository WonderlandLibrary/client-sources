package in.momin5.cookieclient.client.modules.combat;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.player.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Surround extends Module {
    public Surround(){
        super("Surround", Category.COMBAT);
    }

    public SettingBoolean rotate = register(new SettingBoolean("Rotate", this, false));
    public SettingBoolean triggerable = register(new SettingBoolean("OnlyToggleWhenComplete", this, true));
    public SettingBoolean center = register(new SettingBoolean("Center",this,false));
    public SettingNumber tick_for_place = register(new SettingNumber("Blocks per tick",this, 2, 1, 8,1));
    public SettingNumber tick_timeout = register(new SettingNumber("Timeout",this, 20, 10,50,2));
    public SettingBoolean packet = register(new SettingBoolean("PacketPlace",this,false));

    private int y_level = 0;
    private int tick_runs = 0;
    private int offset_step = 0;

    private Vec3d center_block = Vec3d.ZERO;
    int oldSelection;

    Vec3d[] surround_targets = {
            new Vec3d(  1,   0,   0),
            new Vec3d(  0,   0,   1),
            new Vec3d(- 1,   0,   0),
            new Vec3d(  0,   0, - 1),
            new Vec3d(  1, - 1,   0),
            new Vec3d(  0, - 1,   1),
            new Vec3d(- 1, - 1,   0),
            new Vec3d(  0, - 1, - 1),
            new Vec3d(  0, - 1,   0)
    };

    Vec3d[] surround_targets_face = {
            new Vec3d(  1,   1,   0),
            new Vec3d(  0,   1,   1),
            new Vec3d(- 1,   1,   0),
            new Vec3d(  0,   1, - 1),
            new Vec3d(  1,   0,   0),
            new Vec3d(  0,   0,   1),
            new Vec3d(- 1,   0,   0),
            new Vec3d(  0,   0, - 1),
            new Vec3d(  1, - 1,   0),
            new Vec3d(  0, - 1,   1),
            new Vec3d(- 1, - 1,   0),
            new Vec3d(  0, - 1, - 1),
            new Vec3d(  0, - 1,   0)
    };

    @Override
    public void onEnable() {
        if(nullCheck())
            return;
        oldSelection = mc.player.inventory.currentItem;

        if (find_in_hotbar() == -1) {
            this.disable();
            return;
        }

        if (mc.player != null) {

            y_level = (int) Math.round(mc.player.posY);

            center_block = get_center(mc.player.posX, mc.player.posY, mc.player.posZ);

            if (center.isEnabled()) {
                mc.player.motionX = 0;
                mc.player.motionZ = 0;
            }
        }
    }

    @Override
    public void onUpdate() {

        if (mc.player != null) {

            if (center_block != Vec3d.ZERO && center.isEnabled()) {

                double x_diff = Math.abs(center_block.x - mc.player.posX);
                double z_diff = Math.abs(center_block.z - mc.player.posZ);

                if (x_diff <= 0.1 && z_diff <= 0.1) {
                    center_block = Vec3d.ZERO;
                } else {
                    double motion_x = center_block.x - mc.player.posX;
                    double motion_z = center_block.z - mc.player.posZ;

                    mc.player.motionX = motion_x / 2;
                    mc.player.motionZ = motion_z / 2;
                }

            }

            if ((int) Math.round(mc.player.posY) != y_level) {
                this.disable();
                return;
            }

            if (!this.triggerable.isEnabled() && this.tick_runs >= this.tick_timeout.getValue()) {
                this.tick_runs = 0;
                this.disable();
                return;
            }

            int blocks_placed = 0;

            while (blocks_placed < this.tick_for_place.getValue()) {

                if (this.offset_step >= (this.surround_targets.length)) {
                    this.offset_step = 0;
                    break;
                }

                BlockPos offsetPos = new BlockPos(this.surround_targets[offset_step]);
                BlockPos targetPos = new BlockPos(mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());

                boolean try_to_place = true;

                if (!mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
                    try_to_place = false;
                }

                for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
                    if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
                    try_to_place = false;
                    break;
                }

                switchToBlock();

                if (try_to_place && BlockUtils.placeBlock(targetPos, EnumHand.MAIN_HAND, rotate.isEnabled(), packet.isEnabled(), false)) {
                    blocks_placed++;
                }

                offset_step++;

                mc.player.inventory.currentItem = oldSelection;
                mc.playerController.updateController();
            }
            this.tick_runs++;

            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SLEEPING));
            mc.player.setSneaking(false);
        }
    }

    private void switchToBlock(){
        int newSelection = find_in_hotbar();
        if(newSelection != -1){
            mc.player.inventory.currentItem = newSelection;
            mc.playerController.syncCurrentPlayItem();
        }
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock) stack.getItem()).getBlock();
                if (block instanceof BlockEnderChest)
                    return i;
                else if (block instanceof BlockObsidian)
                    return i;
            }
        }
        return -1;
    }

    public Vec3d get_center(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D ;

        return new Vec3d(x, y, z);
    }
}

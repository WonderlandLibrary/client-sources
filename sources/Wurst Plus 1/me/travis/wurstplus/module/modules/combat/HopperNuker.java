package me.travis.wurstplus.module.modules.combat;

import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.BlockInteractionHelper;
import me.travis.wurstplus.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Module.Info(name = "HopperNuker", category = Module.Category.COMBAT)
public class HopperNuker extends Module
{
    private Setting<Double> range = this.register(Settings.doubleBuilder("Hit Range").withMinimum(1.0).withValue(4.0).withMaximum(6.0).build());
    private Setting<Boolean> pickswitch = this.register(Settings.b("Auto Switch", true));
    private int oldSlot;
    private boolean isMining;
    
    public HopperNuker() {
        this.oldSlot = -1;
        this.isMining = false;
    }
    
    @Override
    public void onUpdate() {
        final BlockPos pos = this.getNearestHopper();
        if (pos != null) {
            if (!this.isMining) {
                this.oldSlot = Wrapper.getPlayer().inventory.currentItem;
                this.isMining = true;
            }
            final float[] angle = BlockInteractionHelper.calcAngle(Wrapper.getPlayer().getPositionEyes(Wrapper.getMinecraft().getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() + 0.5f), (double)(pos.getZ() + 0.5f)));
            Wrapper.getPlayer().rotationYaw = angle[0];
            Wrapper.getPlayer().rotationYawHead = angle[0];
            Wrapper.getPlayer().rotationPitch = angle[1];
            if (this.canBreak(pos)) {
                if (this.pickswitch.getValue()) {
                    int newSlot = -1;
                    for (int i = 0; i < 9; ++i) {
                        final ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
                        if (stack != ItemStack.EMPTY) {
                            if (stack.getItem() instanceof ItemPickaxe) {
                                newSlot = i;
                                break;
                            }
                        }
                    }
                    if (newSlot != -1) {
                        Wrapper.getPlayer().inventory.currentItem = newSlot;
                    }
                }
                Wrapper.getMinecraft().playerController.onPlayerDamageBlock(pos, Wrapper.getPlayer().getHorizontalFacing());
                Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
            }
        }
        else if (this.pickswitch.getValue() && this.oldSlot != -1) {
            Wrapper.getPlayer().inventory.currentItem = this.oldSlot;
            this.oldSlot = -1;
            this.isMining = false;
        }
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = Wrapper.getWorld().getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, Wrapper.getWorld(), pos) != -1.0f;
    }
    
    private BlockPos getNearestHopper() {
        Double maxDist = this.range.getValue();
        BlockPos ret = null;
        for (Double x = maxDist; x >= -maxDist; --x) {
            for (Double y = maxDist; y >= -maxDist; --y) {
                for (Double z = maxDist; z >= -maxDist; --z) {
                    final BlockPos pos = new BlockPos(Wrapper.getPlayer().posX + x, Wrapper.getPlayer().posY + y, Wrapper.getPlayer().posZ + z);
                    final double dist = Wrapper.getPlayer().getDistance((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
                    if (dist <= maxDist && Wrapper.getWorld().getBlockState(pos).getBlock() == Blocks.HOPPER && this.canBreak(pos) && pos.getY() >= Wrapper.getPlayer().posY) {
                        maxDist = dist;
                        ret = pos;
                    }
                }
            }
        }
        return ret;
    }
}

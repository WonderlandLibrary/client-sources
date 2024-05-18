package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import wwe.modules.player.*;
import wwe.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.movement.*;
import wwe.modules.render.*;
import wwe.*;

@Mixin(value = { Block.class }, priority = 9999)
public class MixinBlock
{
    public MixinBlock() {
        super();
    }
    
    @Inject(method = { "addCollisionBoxToList" }, at = { @At("HEAD") }, cancellable = true)
    private void CollistionList(final IBlockState blockState, final World world, final BlockPos blockPos, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, @Nullable final Entity entity, final boolean b, final CallbackInfo callbackInfo) {
        try {
            if (vlwv.hNxW.qHPF((Class<Jesus>)KHPS.class).IuiN() && Wrapper.mc.player != null && blockState != null && blockState.getBlock() instanceof BlockLiquid && !(entity instanceof EntityBoat) && !Wrapper.mc.player.isSneaking() && Wrapper.mc.player.fallDistance < 3.0f && !Jesus.yPGD((Entity)Wrapper.mc.player) && (Jesus.FoCK((Entity)Wrapper.mc.player, false) || (Jesus.FoCK(Wrapper.mc.player.getRidingEntity(), false) && Wrapper.mc.player.getRidingEntity().fallDistance < 3.0f)) && Jesus.hVXg((Entity)Wrapper.mc.player, blockPos)) {
                final AxisAlignedBB offset = Jesus.RYee.offset(blockPos);
                if (axisAlignedBB.intersects(offset)) {
                    list.add(offset);
                }
                callbackInfo.cancel();
            }
        }
        catch (Exception ex) {}
    }
    
    @Inject(method = { "isFullCube" }, at = { @At("HEAD") }, cancellable = true)
    public void isFullCube(final IBlockState blockState, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        try {
            if (vlwv.hNxW.qHPF((Class<BoatPhase>)dDTQ.class).IuiN()) {
                callbackInfoReturnable.setReturnValue(false);
                callbackInfoReturnable.cancel();
            }
            if (vlwv.hNxW.qHPF((Class<Xray>)jCBS.class).IuiN()) {
                callbackInfoReturnable.setReturnValue(Xray.fNgC(Block.class.cast(this)));
                callbackInfoReturnable.cancel();
            }
        }
        catch (Exception ex) {}
    }
}

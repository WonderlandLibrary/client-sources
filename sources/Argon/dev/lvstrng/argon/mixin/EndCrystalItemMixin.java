// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.modules.impl.NoBounce;
import dev.lvstrng.argon.utils.CrystalUtil;
import dev.lvstrng.argon.utils.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EndCrystalItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EndCrystalItem.class})
public class EndCrystalItemMixin {
    @Unique
    private Vec3d getPlayerLookVec(final PlayerEntity p) {
        return RenderUtil.getPlayerLookVecInterpolated(p);
    }

    @Unique
    private Vec3d getClientLookVec() {
        assert Argon.mc.player != null;
        return this.getPlayerLookVec(Argon.mc.player);
    }

    @Unique
    private boolean isBlock(final Block b, final BlockPos p) {
        return this.getBlockState(p).getBlock() == b;
    }

    @Unique
    private BlockState getBlockState(final BlockPos p) {
        return Argon.mc.world.getBlockState(p);
    }

    @Unique
    private boolean canPlaceCrystalServer(final BlockPos blockPos) {
        final BlockState method_8320 = Argon.mc.world.getBlockState(blockPos);
        return (method_8320.getBlock().equals(Blocks.OBSIDIAN) || method_8320.getBlock().equals(Blocks.BEDROCK)) && CrystalUtil.hasNoEntityOnIt(blockPos);
    }

    @Inject(method = {"useOnBlock"}, at = {@At("HEAD")})
    private void onUse(final ItemUsageContext context, final CallbackInfoReturnable cir) {
        if (Argon.INSTANCE.getModuleManager().getModuleByClass(NoBounce.class).isEnabled() && Argon.INSTANCE != null && Argon.mc.player != null && Argon.mc.player.getMainHandStack().isOf(Items.END_CRYSTAL)) {
            final Vec3d method_33571 = Argon.mc.player.getEyePos();
            final BlockHitResult method_33572 = Argon.mc.world.raycast(new RaycastContext(method_33571, method_33571.add(this.getClientLookVec().multiply(4.5)), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, Argon.mc.player));
            if (this.isBlock(Blocks.OBSIDIAN, method_33572.getBlockPos()) || this.isBlock(Blocks.BEDROCK, method_33572.getBlockPos())) {
                final HitResult crosshairTarget = Argon.mc.crosshairTarget;
                if (crosshairTarget instanceof BlockHitResult && this.canPlaceCrystalServer(((BlockHitResult) crosshairTarget).getBlockPos())) {
                    context.getStack().decrement(-1);
                }
            }
        }
    }
}
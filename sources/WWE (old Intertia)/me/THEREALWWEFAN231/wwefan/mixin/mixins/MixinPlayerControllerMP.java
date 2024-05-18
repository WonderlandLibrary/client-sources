package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.player.*;
import wwe.modules.render.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.*;
import wwe.*;

@Mixin(value = { PlayerControllerMP.class }, priority = 9999)
public class MixinPlayerControllerMP
{
    public MixinPlayerControllerMP() {
        super();
    }
    
    @Inject(method = { "attackEntity" }, at = { @At("HEAD") }, cancellable = true)
    public void playerAttackEntity(final EntityPlayer entityPlayer, final Entity entity, final CallbackInfo callbackInfo) {
        hSNE.Seqo(new MUMg((Entity)entityPlayer, entity));
        if (vlwv.hNxW.qHPF((Class<BloodParticles>)XdIl.class).IuiN() && entity instanceof EntityLivingBase) {
            final Block blockById = Block.getBlockById(Block.getStateId(Blocks.REDSTONE_BLOCK.getDefaultState()) & 0xFFF);
            if (blockById.getDefaultState().getMaterial() != Material.AIR) {
                final SoundType soundType = blockById.getSoundType(Block.getStateById(Block.getStateId(Blocks.REDSTONE_BLOCK.getDefaultState())), (World)Minecraft.getMinecraft().world, new BlockPos(entity.posX, entity.posY, entity.posZ), (Entity)null);
                if (vlwv.hNxW.qHPF((Class<BloodParticles>)XdIl.class).sound.OnGi()) {
                    Minecraft.getMinecraft().world.playSound(new BlockPos(entity.posX, entity.posY, entity.posZ), soundType.getBreakSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0f) / 2.0f, soundType.getPitch() * 0.8f, false);
                }
            }
            Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(new BlockPos(entity.posX, entity.posY, entity.posZ), blockById.getStateFromMeta(Block.getStateId(Blocks.REDSTONE_BLOCK.getDefaultState()) >> 12 & 0xFF));
        }
    }
    
    @Inject(method = { "processRightClickBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void playerProcessRightClickBlock(final EntityPlayerSP entityPlayerSP, final WorldClient worldClient, final BlockPos blockPos, final EnumFacing enumFacing, final Vec3d vec3d, final EnumHand enumHand, final CallbackInfoReturnable<EnumActionResult> callbackInfoReturnable) {
        final CTFs.wwe/BnvE wwe/BnvE = new CTFs.wwe/BnvE(blockPos, enumHand);
        hSNE.Seqo(wwe/BnvE);
        if (wwe/BnvE.CEMd()) {
            callbackInfoReturnable.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") })
    public void OnPlayerDamageBlock(final BlockPos blockPos, final EnumFacing enumFacing, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final CTFs.wwe/MQil wwe/MQil = new CTFs.wwe/MQil(blockPos);
        hSNE.Seqo(wwe/MQil);
        if (wwe/MQil.CEMd()) {
            callbackInfoReturnable.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V") }, cancellable = true)
    public void onPlayerDestroyBlock(final BlockPos blockPos, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final CTFs.wwe/GJnV wwe/GJnV = new CTFs.wwe/GJnV(blockPos);
        hSNE.Seqo(wwe/GJnV);
        if (wwe/GJnV.CEMd()) {
            callbackInfoReturnable.cancel();
        }
    }
}

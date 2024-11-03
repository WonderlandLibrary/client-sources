package net.silentclient.client.mixin.mixins;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerControllerMP.class)
public class PlayerControllerMPMixin {
    @Redirect(method = "onPlayerRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetHandlerPlayClient;addToSendQueue(Lnet/minecraft/network/Packet;)V"))
    public void fixPacketSend(NetHandlerPlayClient instance, Packet p_147297_1_) {
        ItemStack itemstack = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem();
        if(itemstack != null && (itemstack.getItem() == Items.diamond_sword || itemstack.getItem() == Items.stone_sword || itemstack.getItem() == Items.golden_sword || itemstack.getItem() == Items.iron_sword || itemstack.getItem() == Items.wooden_sword)) {
            if(Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos pos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
                IBlockState state = Minecraft.getMinecraft().theWorld.getBlockState(pos);
                Block block = state.getBlock();
                if(!(block instanceof BlockContainer || block instanceof BlockAnvil || block instanceof BlockWorkbench || block instanceof BlockBed || block instanceof BlockDoor || block instanceof BlockTrapDoor || block instanceof BlockButton || block instanceof BlockLever || block instanceof BlockRedstoneDiode)) {
                    return;
                }
            } else {
                return;
            }
        }

        instance.addToSendQueue(p_147297_1_);
    }
}

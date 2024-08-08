package net.futureclient.client.modules.world.speedmine;

import net.futureclient.client.events.Event;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.ClickType;
import net.futureclient.loader.mixin.common.entity.living.player.wrapper.IPlayerControllerMP;
import net.futureclient.client.ZG;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.material.Material;
import net.futureclient.client.IG;
import net.futureclient.client.modules.world.Speedmine;
import net.futureclient.client.xF;
import net.futureclient.client.n;

public class Listener1 extends n<xF>
{
    public final Speedmine k;
    
    public Listener1(final Speedmine k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final xF xf) {
        if (IG.M(1.0).getBlock().equals(xf.M()) && Speedmine.e(this.k).M() && Speedmine.getMinecraft9().player.onGround) {
            final EntityPlayerSP player = Speedmine.getMinecraft7().player;
            --player.motionY;
        }
        final IBlockState blockState = Speedmine.getMinecraft3().world.getBlockState(xf.M());
        if (Speedmine.M(this.k).B().floatValue() > 0.0f && blockState.getMaterial() != Material.AIR) {
            final int n = 0;
            xf.M(xf.M() + blockState.getPlayerRelativeBlockHardness((EntityPlayer)Speedmine.getMinecraft1().player, (World)Speedmine.getMinecraft5().world, xf.M()) * Speedmine.M(this.k).B().floatValue());
            xf.M(n);
        }
        if (Speedmine.M(this.k).M()) {
            final byte m;
            if ((m = ZG.M(blockState)) == -1) {
                return;
            }
            if (m < 9) {
                Speedmine.getMinecraft4().player.inventory.currentItem = m;
                ((IPlayerControllerMP)Speedmine.getMinecraft2().playerController).syncCurrentPlayItemWrapper();
                return;
            }
            Speedmine.getMinecraft8().playerController.windowClick(0, (int)m, Speedmine.getMinecraft6().player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Speedmine.getMinecraft().player);
        }
    }
    
    public void M(final Event event) {
        this.M((xF)event);
    }
}

package com.client.glowclient.modules.player;

import net.minecraft.block.state.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import com.client.glowclient.events.*;
import com.client.glowclient.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import net.minecraft.init.*;
import com.client.glowclient.modules.*;

public class Nuker extends ModuleContainer
{
    public BlockPos f;
    private int M;
    public static BooleanValue selective;
    private IBlockState d;
    public static BooleanValue flat;
    public static boolean A;
    public static final NumberValue range;
    public static BooleanValue silent;
    
    @Override
    public String M() {
        return String.format("%.1f", Nuker.range.k());
    }
    
    static {
        range = ValueFactory.M("Nuker", "Range", "How far you nuke blocks", 4.5, 0.5, 1.0, 10.0);
        Nuker.flat = ValueFactory.M("Nuker", "Flat", "Nukes blocks above your y level", true);
        Nuker.selective = ValueFactory.M("Nuker", "Selective", "Nukes a specific block", false);
        Nuker.silent = ValueFactory.M("Nuker", "Silent", "Doesnt look at blocks being broken", true);
        Nuker.A = false;
    }
    
    @SubscribeEvent
    public void M(final MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == 1 && mouseEvent.isButtonstate() && Nuker.selective.M() && Wrapper.mc.objectMouseOver != null && Wrapper.mc.objectMouseOver.getBlockPos() != null && Wrapper.mc.objectMouseOver.typeOfHit == RayTraceResult$Type.BLOCK) {
            this.M = pB.M(Wrapper.mc.objectMouseOver.getBlockPos());
            this.d = pB.M(Wrapper.mc.objectMouseOver.getBlockPos());
            qd.D(new StringBuilder().insert(0, "Now nuking ").append(pB.M(Wrapper.mc.world.getBlockState(Wrapper.mc.objectMouseOver.getBlockPos()).getBlock())).append("(").append(pB.M(Wrapper.mc.objectMouseOver.getBlockPos())).append(")").toString());
        }
    }
    
    @Override
    public void D() {
        if (Nuker.selective.M()) {
            qd.D("Right click a block to start nuking.");
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void D(final EventUpdate eventUpdate) {
        this.f = null;
        Iterable<BlockPos> m = null;
        if (Nuker.selective.M()) {
            if (Nuker.flat.M()) {
                m = HB.M(Nuker.range.k(), false, this::M);
            }
            else {
                HB.M(Nuker.range.k(), false, this::D);
            }
        }
        else if (Nuker.flat.M()) {
            HB.M(Nuker.range.k(), false, fE::k);
        }
        else {
            HB.M(Nuker.range.k(), false, fE::A);
        }
        final Iterator<BlockPos> iterator;
        Nuker nuker2;
        if ((iterator = m.iterator()).hasNext()) {
            final BlockPos f = iterator.next();
            if (this.k() && f != null && ModuleManager.M("FastBreak").k() && FastBreak.fast.M()) {
                HB.M(f);
            }
            if (this.k() && f != null && ModuleManager.M("AutoTool").k()) {
                AutoTool.M(f);
            }
            Nuker nuker;
            if (Nuker.silent.M()) {
                final BlockPos blockPos = f;
                nuker = this;
                fa.M(blockPos, this);
            }
            else {
                nuker = this;
                final BlockPos blockPos2 = f;
                y.M(this);
                fa.D(blockPos2);
            }
            nuker.f = f;
            nuker2 = this;
        }
        else {
            nuker2 = this;
        }
        if (nuker2.f == null) {
            Wrapper.mc.playerController.resetBlockRemoving();
            y.M(this);
        }
    }
    
    private static boolean k(final BlockPos blockPos) {
        return blockPos.getY() >= MinecraftHelper.getPlayer().posY;
    }
    
    @Override
    public void E() {
        final int m = -1;
        final IBlockState blockState = null;
        y.M(this);
        Wrapper.mc.playerController.resetBlockRemoving();
        this.f = (BlockPos)blockState;
        this.d = blockState;
        this.M = m;
    }
    
    private static boolean A(final BlockPos blockPos) {
        return pB.M(blockPos) != Blocks.BEDROCK.getBlockState() && blockPos != new BlockPos(Wrapper.mc.player.getPosition().getX(), Wrapper.mc.player.getPosition().getY() - 1, Wrapper.mc.player.getPosition().getZ());
    }
    
    private boolean D(final BlockPos blockPos) {
        return this.M == pB.M(blockPos) && blockPos != new BlockPos(Wrapper.mc.player.getPosition().getX(), Wrapper.mc.player.getPosition().getY() - 1, Wrapper.mc.player.getPosition().getZ());
    }
    
    public Nuker() {
        final BlockPos f = null;
        super(Category.PLAYER, "Nuker", false, -1, "Destroy blocks around you");
        this.f = f;
    }
    
    private boolean M(final BlockPos blockPos) {
        return blockPos.getY() >= MinecraftHelper.getPlayer().posY && this.M == pB.M(blockPos);
    }
}

package com.client.glowclient.modules.player;

import net.minecraft.util.math.*;
import net.minecraft.init.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import com.client.glowclient.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;

public class SourceRemover extends ModuleContainer
{
    public int d;
    public static BooleanValue silent;
    public BlockPos A;
    public static nB source;
    public static final NumberValue range;
    
    private static boolean D(final BlockPos blockPos) {
        return Wrapper.mc.world.getBlockState(blockPos) == Blocks.WATER.getDefaultState() && fa.M(blockPos) && blockPos != Wrapper.mc.player.getPosition();
    }
    
    @Override
    public void E() {
        final BlockPos a = null;
        y.M(this);
        this.A = a;
    }
    
    public SourceRemover() {
        final int d = 0;
        final BlockPos a = null;
        super(Category.PLAYER, "SourceRemover", false, -1, "Places blocks in liquid sources around you");
        this.A = a;
        this.d = d;
    }
    
    @Override
    public String M() {
        return String.format("%.1f", SourceRemover.range.k());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void D(final EventUpdate eventUpdate) {
        final BlockPos a = null;
        ++this.d;
        this.A = a;
        Iterable<BlockPos> m = null;
        if (SourceRemover.source.e().equals("Water")) {
            m = fa.M(SourceRemover.range.M(), true, vE::D);
        }
        else {
            fa.M(SourceRemover.range.k(), true, vE::M);
        }
        final Iterator<BlockPos> iterator;
        SourceRemover sourceRemover3;
        if ((iterator = m.iterator()).hasNext()) {
            final BlockPos a2 = iterator.next();
            SourceRemover sourceRemover2 = null;
            Label_0171: {
                final ItemStack heldItemMainhand;
                if ((heldItemMainhand = Wrapper.mc.player.getHeldItemMainhand()) != null && heldItemMainhand.getItem() instanceof ItemBlock) {
                    if (this.d >= 4) {
                        SourceRemover sourceRemover;
                        if (SourceRemover.silent.M()) {
                            final BlockPos blockPos = a2;
                            final boolean b = true;
                            sourceRemover = this;
                            fa.M(blockPos, b, this);
                        }
                        else {
                            sourceRemover = this;
                            final BlockPos blockPos2 = a2;
                            final boolean b2 = true;
                            y.M(this);
                            fa.M(blockPos2, b2);
                        }
                        sourceRemover.d = 0;
                        sourceRemover2 = this;
                        break Label_0171;
                    }
                }
                else {
                    this.d = 0;
                }
                sourceRemover2 = this;
            }
            sourceRemover2.A = a2;
            sourceRemover3 = this;
        }
        else {
            sourceRemover3 = this;
        }
        if (sourceRemover3.A == null) {
            this.d = 0;
            y.M(this);
        }
    }
    
    private static boolean M(final BlockPos blockPos) {
        return Wrapper.mc.world.getBlockState(blockPos) == Blocks.LAVA.getDefaultState() && fa.M(blockPos) && blockPos != Wrapper.mc.player.getPosition();
    }
    
    static {
        range = ValueFactory.M("SourceRemover", "Range", "How far you nuke blocks", 4.5, 0.5, 1.0, 10.0);
        SourceRemover.silent = ValueFactory.M("SourceRemover", "Silent", "Doesn't look at blocks being broken", true);
        SourceRemover.source = ValueFactory.M("SourceRemover", "Source", "Source type", "Water", "Water", "Lava");
    }
}

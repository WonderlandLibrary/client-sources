package com.client.glowclient.modules.server;

import net.minecraft.util.math.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.entity.*;
import com.client.glowclient.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class SchematicaPrinter extends ModuleContainer
{
    public static final NumberValue delay;
    public static BooleanValue silent;
    private int L;
    public BlockPos A;
    public static BooleanValue rotationalBlocks;
    public static final NumberValue range;
    
    static {
        range = ValueFactory.M("SchematicaPrinter", "Range", "How far you nuke blocks", 4.5, 0.5, 1.0, 10.0);
        SchematicaPrinter.silent = ValueFactory.M("SchematicaPrinter", "Silent", "Doesn't look at blocks being broken", true);
        SchematicaPrinter.rotationalBlocks = ValueFactory.M("SchematicaPrinter", "RotationalBlocks", "Places rotational blocks (may cause them not to be in the direction chosen", true);
        final String s = "SchematicaPrinter";
        final String s2 = "Delay";
        final String s3 = "Delay update ticks before placing a block";
        final double n = 2.0;
        final double n2 = 1.0;
        delay = ValueFactory.M(s, s2, s3, n, n2, n2, 10.0);
    }
    
    public SchematicaPrinter() {
        final int l = -1;
        final BlockPos a = null;
        super(Category.SERVER, "SchematicaPrinter", false, -1, "Print blocks into a schematic");
        this.A = a;
        this.L = l;
    }
    
    @Override
    public void E() {
        final BlockPos a = null;
        y.M(this);
        this.A = a;
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void D(final EventUpdate eventUpdate) {
        final WorldClient world = SchematicaPrinter.B.world;
        final EntityPlayerSP player = SchematicaPrinter.B.player;
        final XA g = eb.g;
        if (world != null && player != null && g != null && g.d) {
            final wA b = wA.b;
            if (this.L-- < 0) {
                final wA wa = b;
                final WorldClient worldClient = world;
                this.L = SchematicaPrinter.delay.M();
                wa.M(worldClient, player);
            }
        }
        else {
            y.M(this);
        }
    }
}

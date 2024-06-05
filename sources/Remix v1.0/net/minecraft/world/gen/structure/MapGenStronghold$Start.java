package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import java.util.*;

public static class Start extends StructureStart
{
    public Start() {
    }
    
    public Start(final World worldIn, final Random p_i2067_2_, final int p_i2067_3_, final int p_i2067_4_) {
        super(p_i2067_3_, p_i2067_4_);
        StructureStrongholdPieces.prepareStructurePieces();
        final StructureStrongholdPieces.Stairs2 var5 = new StructureStrongholdPieces.Stairs2(0, p_i2067_2_, (p_i2067_3_ << 4) + 2, (p_i2067_4_ << 4) + 2);
        this.components.add(var5);
        var5.buildComponent(var5, this.components, p_i2067_2_);
        final List var6 = var5.field_75026_c;
        while (!var6.isEmpty()) {
            final int var7 = p_i2067_2_.nextInt(var6.size());
            final StructureComponent var8 = var6.remove(var7);
            var8.buildComponent(var5, this.components, p_i2067_2_);
        }
        this.updateBoundingBox();
        this.markAvailableHeight(worldIn, p_i2067_2_, 10);
    }
}

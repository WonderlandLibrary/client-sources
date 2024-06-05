package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import java.util.*;

public static class Start extends StructureStart
{
    public Start() {
    }
    
    public Start(final World worldIn, final Random p_i2040_2_, final int p_i2040_3_, final int p_i2040_4_) {
        super(p_i2040_3_, p_i2040_4_);
        final StructureNetherBridgePieces.Start var5 = new StructureNetherBridgePieces.Start(p_i2040_2_, (p_i2040_3_ << 4) + 2, (p_i2040_4_ << 4) + 2);
        this.components.add(var5);
        var5.buildComponent(var5, this.components, p_i2040_2_);
        final List var6 = var5.field_74967_d;
        while (!var6.isEmpty()) {
            final int var7 = p_i2040_2_.nextInt(var6.size());
            final StructureComponent var8 = var6.remove(var7);
            var8.buildComponent(var5, this.components, p_i2040_2_);
        }
        this.updateBoundingBox();
        this.setRandomHeight(worldIn, p_i2040_2_, 48, 70);
    }
}

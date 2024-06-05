package net.minecraft.client.renderer;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;

public abstract class ChunkRenderContainer
{
    protected List field_178009_a;
    protected boolean field_178007_b;
    private double field_178008_c;
    private double field_178005_d;
    private double field_178006_e;
    
    public ChunkRenderContainer() {
        this.field_178009_a = Lists.newArrayListWithCapacity(17424);
    }
    
    public void func_178004_a(final double p_178004_1_, final double p_178004_3_, final double p_178004_5_) {
        this.field_178007_b = true;
        this.field_178009_a.clear();
        this.field_178008_c = p_178004_1_;
        this.field_178005_d = p_178004_3_;
        this.field_178006_e = p_178004_5_;
    }
    
    public void func_178003_a(final RenderChunk p_178003_1_) {
        final BlockPos var2 = p_178003_1_.func_178568_j();
        GlStateManager.translate((float)(var2.getX() - this.field_178008_c), (float)(var2.getY() - this.field_178005_d), (float)(var2.getZ() - this.field_178006_e));
    }
    
    public void func_178002_a(final RenderChunk p_178002_1_, final EnumWorldBlockLayer p_178002_2_) {
        this.field_178009_a.add(p_178002_1_);
    }
    
    public abstract void func_178001_a(final EnumWorldBlockLayer p0);
}

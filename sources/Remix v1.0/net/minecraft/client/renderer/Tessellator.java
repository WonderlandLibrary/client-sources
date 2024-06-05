package net.minecraft.client.renderer;

public class Tessellator
{
    private static final Tessellator instance;
    private WorldRenderer worldRenderer;
    private WorldVertexBufferUploader field_178182_b;
    
    public Tessellator(final int p_i1250_1_) {
        this.field_178182_b = new WorldVertexBufferUploader();
        this.worldRenderer = new WorldRenderer(p_i1250_1_);
    }
    
    public static Tessellator getInstance() {
        return Tessellator.instance;
    }
    
    public int draw() {
        return this.field_178182_b.draw(this.worldRenderer, this.worldRenderer.draw());
    }
    
    public WorldRenderer getWorldRenderer() {
        return this.worldRenderer;
    }
    
    static {
        instance = new Tessellator(2097152);
    }
}

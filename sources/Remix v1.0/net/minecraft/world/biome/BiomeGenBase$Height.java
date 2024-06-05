package net.minecraft.world.biome;

public static class Height
{
    public float rootHeight;
    public float variation;
    
    public Height(final float p_i45371_1_, final float p_i45371_2_) {
        this.rootHeight = p_i45371_1_;
        this.variation = p_i45371_2_;
    }
    
    public Height attenuate() {
        return new Height(this.rootHeight * 0.8f, this.variation * 0.6f);
    }
}

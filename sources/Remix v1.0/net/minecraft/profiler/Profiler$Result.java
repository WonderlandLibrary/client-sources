package net.minecraft.profiler;

public static final class Result implements Comparable
{
    public double field_76332_a;
    public double field_76330_b;
    public String field_76331_c;
    
    public Result(final String p_i1554_1_, final double p_i1554_2_, final double p_i1554_4_) {
        this.field_76331_c = p_i1554_1_;
        this.field_76332_a = p_i1554_2_;
        this.field_76330_b = p_i1554_4_;
    }
    
    public int compareTo(final Result p_compareTo_1_) {
        return (p_compareTo_1_.field_76332_a < this.field_76332_a) ? -1 : ((p_compareTo_1_.field_76332_a > this.field_76332_a) ? 1 : p_compareTo_1_.field_76331_c.compareTo(this.field_76331_c));
    }
    
    public int func_76329_a() {
        return (this.field_76331_c.hashCode() & 0xAAAAAA) + 4473924;
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.compareTo((Result)p_compareTo_1_);
    }
}

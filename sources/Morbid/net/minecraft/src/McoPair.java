package net.minecraft.src;

public class McoPair
{
    private final Object field_98160_a;
    private final Object field_98159_b;
    
    protected McoPair(final Object par1Obj, final Object par2Obj) {
        this.field_98160_a = par1Obj;
        this.field_98159_b = par2Obj;
    }
    
    public static McoPair func_98157_a(final Object par0Obj, final Object par1Obj) {
        return new McoPair(par0Obj, par1Obj);
    }
    
    public Object func_100005_a() {
        return this.field_98160_a;
    }
    
    public Object func_100004_b() {
        return this.field_98159_b;
    }
}

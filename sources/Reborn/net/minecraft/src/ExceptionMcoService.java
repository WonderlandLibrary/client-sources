package net.minecraft.src;

public class ExceptionMcoService extends Exception
{
    public final int field_96392_a;
    public final String field_96391_b;
    
    public ExceptionMcoService(final int par1, final String par2Str) {
        super(par2Str);
        this.field_96392_a = par1;
        this.field_96391_b = par2Str;
    }
}

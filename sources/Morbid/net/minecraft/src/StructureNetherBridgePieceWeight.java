package net.minecraft.src;

class StructureNetherBridgePieceWeight
{
    public Class weightClass;
    public final int field_78826_b;
    public int field_78827_c;
    public int field_78824_d;
    public boolean field_78825_e;
    
    public StructureNetherBridgePieceWeight(final Class par1Class, final int par2, final int par3, final boolean par4) {
        this.weightClass = par1Class;
        this.field_78826_b = par2;
        this.field_78824_d = par3;
        this.field_78825_e = par4;
    }
    
    public StructureNetherBridgePieceWeight(final Class par1Class, final int par2, final int par3) {
        this(par1Class, par2, par3, false);
    }
    
    public boolean func_78822_a(final int par1) {
        return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
    }
    
    public boolean func_78823_a() {
        return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
    }
}

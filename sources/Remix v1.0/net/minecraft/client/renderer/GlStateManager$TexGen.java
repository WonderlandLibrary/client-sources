package net.minecraft.client.renderer;

public enum TexGen
{
    S("S", 0, "S", 0), 
    T("T", 1, "T", 1), 
    R("R", 2, "R", 2), 
    Q("Q", 3, "Q", 3);
    
    private static final TexGen[] $VALUES;
    
    private TexGen(final String p_i46378_1_, final int p_i46378_2_, final String p_i46255_1_, final int p_i46255_2_) {
    }
    
    static {
        $VALUES = new TexGen[] { TexGen.S, TexGen.T, TexGen.R, TexGen.Q };
    }
}

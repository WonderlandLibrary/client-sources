package net.minecraft.client.stream;

public enum AuthFailureReason
{
    ERROR("ERROR", 0), 
    INVALID_TOKEN("INVALID_TOKEN", 1);
    
    private static final AuthFailureReason[] $VALUES;
    
    private AuthFailureReason(final String p_i1014_1_, final int p_i1014_2_) {
    }
    
    static {
        $VALUES = new AuthFailureReason[] { AuthFailureReason.ERROR, AuthFailureReason.INVALID_TOKEN };
    }
}

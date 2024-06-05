package net.minecraft.client.renderer.chunk;

public enum Status
{
    PENDING("PENDING", 0, "PENDING", 0), 
    COMPILING("COMPILING", 1, "COMPILING", 1), 
    UPLOADING("UPLOADING", 2, "UPLOADING", 2), 
    DONE("DONE", 3, "DONE", 3);
    
    private static final Status[] $VALUES;
    
    private Status(final String p_i46385_1_, final int p_i46385_2_, final String p_i46207_1_, final int p_i46207_2_) {
    }
    
    static {
        $VALUES = new Status[] { Status.PENDING, Status.COMPILING, Status.UPLOADING, Status.DONE };
    }
}

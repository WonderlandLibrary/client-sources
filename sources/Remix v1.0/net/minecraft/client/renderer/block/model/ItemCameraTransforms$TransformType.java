package net.minecraft.client.renderer.block.model;

public enum TransformType
{
    NONE("NONE", 0), 
    THIRD_PERSON("THIRD_PERSON", 1), 
    FIRST_PERSON("FIRST_PERSON", 2), 
    HEAD("HEAD", 3), 
    GUI("GUI", 4);
    
    private static final TransformType[] $VALUES;
    
    private TransformType(final String p_i46212_1_, final int p_i46212_2_) {
    }
    
    static {
        $VALUES = new TransformType[] { TransformType.NONE, TransformType.THIRD_PERSON, TransformType.FIRST_PERSON, TransformType.HEAD, TransformType.GUI };
    }
}

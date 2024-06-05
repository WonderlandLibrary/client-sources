package net.minecraft.client.stream;

public enum EnumEmoticonMode
{
    None("None", 0), 
    Url("Url", 1), 
    TextureAtlas("TextureAtlas", 2);
    
    private static final EnumEmoticonMode[] $VALUES;
    
    private EnumEmoticonMode(final String p_i46060_1_, final int p_i46060_2_) {
    }
    
    static {
        $VALUES = new EnumEmoticonMode[] { EnumEmoticonMode.None, EnumEmoticonMode.Url, EnumEmoticonMode.TextureAtlas };
    }
}

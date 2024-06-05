package net.minecraft.client.gui;

enum Icon
{
    LOCKED("LOCKED", 0, 0, 146), 
    LOCKED_HOVER("LOCKED_HOVER", 1, 0, 166), 
    LOCKED_DISABLED("LOCKED_DISABLED", 2, 0, 186), 
    UNLOCKED("UNLOCKED", 3, 20, 146), 
    UNLOCKED_HOVER("UNLOCKED_HOVER", 4, 20, 166), 
    UNLOCKED_DISABLED("UNLOCKED_DISABLED", 5, 20, 186);
    
    private static final Icon[] $VALUES;
    private final int field_178914_g;
    private final int field_178920_h;
    
    private Icon(final String p_i45537_1_, final int p_i45537_2_, final int p_i45537_3_, final int p_i45537_4_) {
        this.field_178914_g = p_i45537_3_;
        this.field_178920_h = p_i45537_4_;
    }
    
    public int func_178910_a() {
        return this.field_178914_g;
    }
    
    public int func_178912_b() {
        return this.field_178920_h;
    }
    
    static {
        $VALUES = new Icon[] { Icon.LOCKED, Icon.LOCKED_HOVER, Icon.LOCKED_DISABLED, Icon.UNLOCKED, Icon.UNLOCKED_HOVER, Icon.UNLOCKED_DISABLED };
    }
}

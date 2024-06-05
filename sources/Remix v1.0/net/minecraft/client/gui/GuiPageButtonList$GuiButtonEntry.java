package net.minecraft.client.gui;

public static class GuiButtonEntry extends GuiListEntry
{
    private final boolean field_178941_a;
    
    public GuiButtonEntry(final int p_i45535_1_, final String p_i45535_2_, final boolean p_i45535_3_, final boolean p_i45535_4_) {
        super(p_i45535_1_, p_i45535_2_, p_i45535_3_);
        this.field_178941_a = p_i45535_4_;
    }
    
    public boolean func_178940_a() {
        return this.field_178941_a;
    }
}

package net.minecraft.client.gui;

import com.google.common.base.*;

public static class EditBoxEntry extends GuiListEntry
{
    private final Predicate field_178951_a;
    
    public EditBoxEntry(final int p_i45534_1_, final String p_i45534_2_, final boolean p_i45534_3_, final Predicate p_i45534_4_) {
        super(p_i45534_1_, p_i45534_2_, p_i45534_3_);
        this.field_178951_a = (Predicate)Objects.firstNonNull((Object)p_i45534_4_, (Object)Predicates.alwaysTrue());
    }
    
    public Predicate func_178950_a() {
        return this.field_178951_a;
    }
}

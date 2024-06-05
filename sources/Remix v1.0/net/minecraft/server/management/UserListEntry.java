package net.minecraft.server.management;

import com.google.gson.*;

public class UserListEntry
{
    private final Object value;
    
    public UserListEntry(final Object p_i1146_1_) {
        this.value = p_i1146_1_;
    }
    
    protected UserListEntry(final Object p_i1147_1_, final JsonObject p_i1147_2_) {
        this.value = p_i1147_1_;
    }
    
    Object getValue() {
        return this.value;
    }
    
    boolean hasBanExpired() {
        return false;
    }
    
    protected void onSerialization(final JsonObject data) {
    }
}

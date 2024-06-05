package net.minecraft.client.main;

import net.minecraft.util.*;
import com.mojang.authlib.properties.*;
import java.net.*;

public static class UserInformation
{
    public final Session field_178752_a;
    public final PropertyMap field_178750_b;
    public final Proxy field_178751_c;
    
    public UserInformation(final Session p_i45486_1_, final PropertyMap p_i45486_2_, final Proxy p_i45486_3_) {
        this.field_178752_a = p_i45486_1_;
        this.field_178750_b = p_i45486_2_;
        this.field_178751_c = p_i45486_3_;
    }
}

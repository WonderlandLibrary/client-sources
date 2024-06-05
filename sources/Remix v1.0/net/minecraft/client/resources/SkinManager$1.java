package net.minecraft.client.resources;

import com.google.common.cache.*;
import com.mojang.authlib.*;
import java.util.*;
import net.minecraft.client.*;

class SkinManager$1 extends CacheLoader {
    public Map func_152786_a(final GameProfile p_152786_1_) {
        return Minecraft.getMinecraft().getSessionService().getTextures(p_152786_1_, false);
    }
    
    public Object load(final Object p_load_1_) {
        return this.func_152786_a((GameProfile)p_load_1_);
    }
}
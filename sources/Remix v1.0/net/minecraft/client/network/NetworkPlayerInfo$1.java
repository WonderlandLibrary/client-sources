package net.minecraft.client.network;

import net.minecraft.client.resources.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.util.*;

class NetworkPlayerInfo$1 implements SkinManager.SkinAvailableCallback {
    @Override
    public void func_180521_a(final MinecraftProfileTexture.Type p_180521_1_, final ResourceLocation p_180521_2_, final MinecraftProfileTexture p_180521_3_) {
        switch (SwitchType.field_178875_a[p_180521_1_.ordinal()]) {
            case 1: {
                NetworkPlayerInfo.access$002(NetworkPlayerInfo.this, p_180521_2_);
                NetworkPlayerInfo.access$102(NetworkPlayerInfo.this, p_180521_3_.getMetadata("model"));
                if (NetworkPlayerInfo.access$100(NetworkPlayerInfo.this) == null) {
                    NetworkPlayerInfo.access$102(NetworkPlayerInfo.this, "default");
                    break;
                }
                break;
            }
            case 2: {
                NetworkPlayerInfo.access$202(NetworkPlayerInfo.this, p_180521_2_);
                break;
            }
        }
    }
}
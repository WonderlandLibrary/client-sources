package net.minecraft.server.management;

import net.minecraft.server.*;
import java.util.*;
import com.mojang.authlib.*;

static final class PreYggdrasilConverter$2 implements ProfileLookupCallback {
    final /* synthetic */ MinecraftServer val$var1;
    final /* synthetic */ ArrayList val$var3;
    
    public void onProfileLookupSucceeded(final GameProfile p_onProfileLookupSucceeded_1_) {
        this.val$var1.getPlayerProfileCache().func_152649_a(p_onProfileLookupSucceeded_1_);
        this.val$var3.add(p_onProfileLookupSucceeded_1_);
    }
    
    public void onProfileLookupFailed(final GameProfile p_onProfileLookupFailed_1_, final Exception p_onProfileLookupFailed_2_) {
        PreYggdrasilConverter.access$000().warn("Could not lookup user whitelist entry for " + p_onProfileLookupFailed_1_.getName(), (Throwable)p_onProfileLookupFailed_2_);
    }
}
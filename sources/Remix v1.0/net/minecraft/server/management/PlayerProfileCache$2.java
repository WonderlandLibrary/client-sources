package net.minecraft.server.management;

import com.mojang.authlib.*;

static final class PlayerProfileCache$2 implements ProfileLookupCallback {
    final /* synthetic */ GameProfile[] val$var2;
    
    public void onProfileLookupSucceeded(final GameProfile p_onProfileLookupSucceeded_1_) {
        this.val$var2[0] = p_onProfileLookupSucceeded_1_;
    }
    
    public void onProfileLookupFailed(final GameProfile p_onProfileLookupFailed_1_, final Exception p_onProfileLookupFailed_2_) {
        this.val$var2[0] = null;
    }
}
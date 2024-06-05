package net.minecraft.client.resources;

import java.util.*;
import com.mojang.authlib.minecraft.*;

class SkinManager$3$1 implements Runnable {
    final /* synthetic */ HashMap val$var1;
    
    @Override
    public void run() {
        if (this.val$var1.containsKey(MinecraftProfileTexture.Type.SKIN)) {
            Runnable.this.this$0.loadSkin(this.val$var1.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, Runnable.this.val$p_152790_2_);
        }
        if (this.val$var1.containsKey(MinecraftProfileTexture.Type.CAPE)) {
            Runnable.this.this$0.loadSkin(this.val$var1.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, Runnable.this.val$p_152790_2_);
        }
    }
}
package net.minecraft.client.resources;

import com.mojang.authlib.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import java.util.*;
import com.mojang.authlib.minecraft.*;

class SkinManager$3 implements Runnable {
    final /* synthetic */ GameProfile val$p_152790_1_;
    final /* synthetic */ boolean val$p_152790_3_;
    final /* synthetic */ SkinAvailableCallback val$p_152790_2_;
    
    @Override
    public void run() {
        final HashMap var1 = Maps.newHashMap();
        try {
            var1.putAll(SkinManager.access$000(SkinManager.this).getTextures(this.val$p_152790_1_, this.val$p_152790_3_));
        }
        catch (InsecureTextureException ex) {}
        if (var1.isEmpty() && this.val$p_152790_1_.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
            var1.putAll(SkinManager.access$000(SkinManager.this).getTextures(SkinManager.access$000(SkinManager.this).fillProfileProperties(this.val$p_152790_1_, false), false));
        }
        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
            @Override
            public void run() {
                if (var1.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                    SkinManager.this.loadSkin(var1.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, Runnable.this.val$p_152790_2_);
                }
                if (var1.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                    SkinManager.this.loadSkin(var1.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, Runnable.this.val$p_152790_2_);
                }
            }
        });
    }
}
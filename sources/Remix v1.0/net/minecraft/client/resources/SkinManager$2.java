package net.minecraft.client.resources;

import net.minecraft.client.renderer.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.util.*;
import java.awt.image.*;

class SkinManager$2 implements IImageBuffer {
    final /* synthetic */ ImageBufferDownload val$var8;
    final /* synthetic */ SkinAvailableCallback val$p_152789_3_;
    final /* synthetic */ MinecraftProfileTexture.Type val$p_152789_2_;
    final /* synthetic */ ResourceLocation val$var4;
    final /* synthetic */ MinecraftProfileTexture val$p_152789_1_;
    
    @Override
    public BufferedImage parseUserSkin(BufferedImage p_78432_1_) {
        if (this.val$var8 != null) {
            p_78432_1_ = this.val$var8.parseUserSkin(p_78432_1_);
        }
        return p_78432_1_;
    }
    
    @Override
    public void func_152634_a() {
        if (this.val$var8 != null) {
            this.val$var8.func_152634_a();
        }
        if (this.val$p_152789_3_ != null) {
            this.val$p_152789_3_.func_180521_a(this.val$p_152789_2_, this.val$var4, this.val$p_152789_1_);
        }
    }
}
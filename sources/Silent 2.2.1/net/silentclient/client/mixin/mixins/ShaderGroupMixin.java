package net.silentclient.client.mixin.mixins;

import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.silentclient.client.mods.util.IMixinShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ShaderGroup.class)
public abstract class ShaderGroupMixin implements IMixinShaderGroup {
    @Shadow private Framebuffer mainFramebuffer;

    @Override
    @Accessor
    public abstract List<Shader> getListShaders();

    @Override
    public Object getMainFramebuffer() {
        return this.mainFramebuffer;
    }
}

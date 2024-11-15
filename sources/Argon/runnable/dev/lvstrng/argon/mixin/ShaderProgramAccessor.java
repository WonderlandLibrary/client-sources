// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin({ShaderProgram.class})
public interface ShaderProgramAccessor {
    @Accessor("loadedUniforms")
    Map getLoadedUniforms();
}

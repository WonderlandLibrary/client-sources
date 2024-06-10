package me.kaimson.melonclient.mixins.accessor;

import org.spongepowered.asm.mixin.*;
import java.util.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ blr.class })
public interface ShaderGroupAccessor
{
    @Accessor
    List<bls> getListShaders();
}

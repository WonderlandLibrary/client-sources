// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({Mouse.class})
public interface MouseHandlerAccessor {
    @Invoker("onMouseButton")
    void press(final long p0, final int p1, final int p2, final int p3);
}

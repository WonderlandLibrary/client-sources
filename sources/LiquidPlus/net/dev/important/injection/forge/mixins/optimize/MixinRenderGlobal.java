/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderGlobal
 */
package net.dev.important.injection.forge.mixins.optimize;

import net.dev.important.injection.access.IMixinWorldAccess;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={RenderGlobal.class})
public abstract class MixinRenderGlobal
implements IMixinWorldAccess {
    @Shadow
    protected abstract void func_72725_b(int var1, int var2, int var3, int var4, int var5, int var6);

    @Override
    public void notifyLightSet(int n, int n2, int n3) {
        this.func_72725_b(n - 1, n2 - 1, n3 - 1, n + 1, n2 + 1, n3 + 1);
    }
}


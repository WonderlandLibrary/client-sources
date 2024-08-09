/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.optifine.EmissiveTextures;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DualBrightnessCallback<S extends TileEntity>
implements TileEntityMerger.ICallback<S, Int2IntFunction> {
    @Override
    public Int2IntFunction func_225539_a_(S s, S s2) {
        return arg_0 -> DualBrightnessCallback.lambda$func_225539_a_$0(s, s2, arg_0);
    }

    @Override
    public Int2IntFunction func_225538_a_(S s) {
        return DualBrightnessCallback::lambda$func_225538_a_$1;
    }

    @Override
    public Int2IntFunction func_225537_b_() {
        return DualBrightnessCallback::lambda$func_225537_b_$2;
    }

    @Override
    public Object func_225537_b_() {
        return this.func_225537_b_();
    }

    @Override
    public Object func_225538_a_(Object object) {
        return this.func_225538_a_((S)((TileEntity)object));
    }

    @Override
    public Object func_225539_a_(Object object, Object object2) {
        return this.func_225539_a_((S)((TileEntity)object), (S)((TileEntity)object2));
    }

    private static int lambda$func_225537_b_$2(int n) {
        return n;
    }

    private static int lambda$func_225538_a_$1(int n) {
        return n;
    }

    private static int lambda$func_225539_a_$0(TileEntity tileEntity, TileEntity tileEntity2, int n) {
        if (EmissiveTextures.isRenderEmissive()) {
            return LightTexture.MAX_BRIGHTNESS;
        }
        int n2 = WorldRenderer.getCombinedLight(tileEntity.getWorld(), tileEntity.getPos());
        int n3 = WorldRenderer.getCombinedLight(tileEntity2.getWorld(), tileEntity2.getPos());
        int n4 = LightTexture.getLightBlock(n2);
        int n5 = LightTexture.getLightBlock(n3);
        int n6 = LightTexture.getLightSky(n2);
        int n7 = LightTexture.getLightSky(n3);
        return LightTexture.packLight(Math.max(n4, n5), Math.max(n6, n7));
    }
}


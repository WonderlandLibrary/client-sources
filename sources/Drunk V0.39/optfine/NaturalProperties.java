/*
 * Decompiled with CFR 0.152.
 */
package optfine;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import optfine.Config;

public class NaturalProperties {
    public int rotation = 1;
    public boolean flip = false;
    private Map[] quadMaps = new Map[8];

    public NaturalProperties(String p_i44_1_) {
        if (p_i44_1_.equals("4")) {
            this.rotation = 4;
            return;
        }
        if (p_i44_1_.equals("2")) {
            this.rotation = 2;
            return;
        }
        if (p_i44_1_.equals("F")) {
            this.flip = true;
            return;
        }
        if (p_i44_1_.equals("4F")) {
            this.rotation = 4;
            this.flip = true;
            return;
        }
        if (p_i44_1_.equals("2F")) {
            this.rotation = 2;
            this.flip = true;
            return;
        }
        Config.warn("NaturalTextures: Unknown type: " + p_i44_1_);
    }

    public boolean isValid() {
        if (this.rotation == 2) return true;
        if (this.rotation == 4) return true;
        boolean bl = this.flip;
        return bl;
    }

    public synchronized BakedQuad getQuad(BakedQuad p_getQuad_1_, int p_getQuad_2_, boolean p_getQuad_3_) {
        BakedQuad bakedquad;
        int i = p_getQuad_2_;
        if (p_getQuad_3_) {
            i = p_getQuad_2_ | 4;
        }
        if (i <= 0) return p_getQuad_1_;
        if (i >= this.quadMaps.length) return p_getQuad_1_;
        IdentityHashMap<BakedQuad, BakedQuad> map = this.quadMaps[i];
        if (map == null) {
            this.quadMaps[i] = map = new IdentityHashMap<BakedQuad, BakedQuad>(1);
        }
        if ((bakedquad = (BakedQuad)map.get(p_getQuad_1_)) != null) return bakedquad;
        bakedquad = this.makeQuad(p_getQuad_1_, p_getQuad_2_, p_getQuad_3_);
        map.put(p_getQuad_1_, bakedquad);
        return bakedquad;
    }

    private BakedQuad makeQuad(BakedQuad p_makeQuad_1_, int p_makeQuad_2_, boolean p_makeQuad_3_) {
        int[] aint = p_makeQuad_1_.getVertexData();
        int i = p_makeQuad_1_.getTintIndex();
        EnumFacing enumfacing = p_makeQuad_1_.getFace();
        TextureAtlasSprite textureatlassprite = p_makeQuad_1_.getSprite();
        aint = this.fixVertexData(aint, p_makeQuad_2_, p_makeQuad_3_);
        return new BakedQuad(aint, i, enumfacing, textureatlassprite);
    }

    private int[] fixVertexData(int[] p_fixVertexData_1_, int p_fixVertexData_2_, boolean p_fixVertexData_3_) {
        int[] aint = new int[p_fixVertexData_1_.length];
        for (int i = 0; i < p_fixVertexData_1_.length; ++i) {
            aint[i] = p_fixVertexData_1_[i];
        }
        int i1 = 4 - p_fixVertexData_2_;
        if (p_fixVertexData_3_) {
            i1 += 3;
        }
        i1 %= 4;
        int j = 0;
        while (j < 4) {
            int k = j * 7;
            int l = i1 * 7;
            aint[l + 4] = p_fixVertexData_1_[k + 4];
            aint[l + 4 + 1] = p_fixVertexData_1_[k + 4 + 1];
            if (p_fixVertexData_3_) {
                if (--i1 < 0) {
                    i1 = 3;
                }
            } else if (++i1 > 3) {
                i1 = 0;
            }
            ++j;
        }
        return aint;
    }
}


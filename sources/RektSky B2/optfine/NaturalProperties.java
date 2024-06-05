package optfine;

import net.minecraft.client.renderer.block.model.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class NaturalProperties
{
    public int rotation;
    public boolean flip;
    private Map[] quadMaps;
    
    public NaturalProperties(final String p_i44_1_) {
        this.rotation = 1;
        this.flip = false;
        this.quadMaps = new Map[8];
        if (p_i44_1_.equals("4")) {
            this.rotation = 4;
        }
        else if (p_i44_1_.equals("2")) {
            this.rotation = 2;
        }
        else if (p_i44_1_.equals("F")) {
            this.flip = true;
        }
        else if (p_i44_1_.equals("4F")) {
            this.rotation = 4;
            this.flip = true;
        }
        else if (p_i44_1_.equals("2F")) {
            this.rotation = 2;
            this.flip = true;
        }
        else {
            Config.warn("NaturalTextures: Unknown type: " + p_i44_1_);
        }
    }
    
    public boolean isValid() {
        return this.rotation == 2 || this.rotation == 4 || this.flip;
    }
    
    public synchronized BakedQuad getQuad(final BakedQuad p_getQuad_1_, final int p_getQuad_2_, final boolean p_getQuad_3_) {
        int i = p_getQuad_2_;
        if (p_getQuad_3_) {
            i = (p_getQuad_2_ | 0x4);
        }
        if (i > 0 && i < this.quadMaps.length) {
            Map map = this.quadMaps[i];
            if (map == null) {
                map = new IdentityHashMap(1);
                this.quadMaps[i] = map;
            }
            BakedQuad bakedquad = map.get(p_getQuad_1_);
            if (bakedquad == null) {
                bakedquad = this.makeQuad(p_getQuad_1_, p_getQuad_2_, p_getQuad_3_);
                map.put(p_getQuad_1_, bakedquad);
            }
            return bakedquad;
        }
        return p_getQuad_1_;
    }
    
    private BakedQuad makeQuad(final BakedQuad p_makeQuad_1_, final int p_makeQuad_2_, final boolean p_makeQuad_3_) {
        int[] aint = p_makeQuad_1_.getVertexData();
        final int i = p_makeQuad_1_.getTintIndex();
        final EnumFacing enumfacing = p_makeQuad_1_.getFace();
        final TextureAtlasSprite textureatlassprite = p_makeQuad_1_.getSprite();
        aint = this.fixVertexData(aint, p_makeQuad_2_, p_makeQuad_3_);
        final BakedQuad bakedquad = new BakedQuad(aint, i, enumfacing, textureatlassprite);
        return bakedquad;
    }
    
    private int[] fixVertexData(final int[] p_fixVertexData_1_, final int p_fixVertexData_2_, final boolean p_fixVertexData_3_) {
        final int[] aint = new int[p_fixVertexData_1_.length];
        for (int i = 0; i < p_fixVertexData_1_.length; ++i) {
            aint[i] = p_fixVertexData_1_[i];
        }
        int i2 = 4 - p_fixVertexData_2_;
        if (p_fixVertexData_3_) {
            i2 += 3;
        }
        i2 %= 4;
        for (int j = 0; j < 4; ++j) {
            final int k = j * 7;
            final int l = i2 * 7;
            aint[l + 4] = p_fixVertexData_1_[k + 4];
            aint[l + 4 + 1] = p_fixVertexData_1_[k + 4 + 1];
            if (p_fixVertexData_3_) {
                if (--i2 < 0) {
                    i2 = 3;
                }
            }
            else if (++i2 > 3) {
                i2 = 0;
            }
        }
        return aint;
    }
}

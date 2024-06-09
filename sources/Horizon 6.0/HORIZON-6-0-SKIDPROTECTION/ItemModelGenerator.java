package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import java.util.List;

public class ItemModelGenerator
{
    public static final List HorizonCode_Horizon_È;
    private static final String Â = "CL_00002488";
    
    static {
        HorizonCode_Horizon_È = Lists.newArrayList((Object[])new String[] { "layer0", "layer1", "layer2", "layer3", "layer4" });
    }
    
    public ModelBlock HorizonCode_Horizon_È(final TextureMap p_178392_1_, final ModelBlock p_178392_2_) {
        final HashMap var3 = Maps.newHashMap();
        final ArrayList var4 = Lists.newArrayList();
        for (int var5 = 0; var5 < ItemModelGenerator.HorizonCode_Horizon_È.size(); ++var5) {
            final String var6 = ItemModelGenerator.HorizonCode_Horizon_È.get(var5);
            if (!p_178392_2_.Â(var6)) {
                break;
            }
            final String var7 = p_178392_2_.Ý(var6);
            var3.put(var6, var7);
            final TextureAtlasSprite var8 = p_178392_1_.HorizonCode_Horizon_È(new ResourceLocation_1975012498(var7).toString());
            var4.addAll(this.HorizonCode_Horizon_È(var5, var6, var8));
        }
        if (var4.isEmpty()) {
            return null;
        }
        var3.put("particle", p_178392_2_.Â("particle") ? p_178392_2_.Ý("particle") : var3.get("layer0"));
        return new ModelBlock(var4, var3, false, false, new ItemCameraTransforms(p_178392_2_.à(), p_178392_2_.Ø(), p_178392_2_.áŒŠÆ(), p_178392_2_.áˆºÑ¢Õ()));
    }
    
    private List HorizonCode_Horizon_È(final int p_178394_1_, final String p_178394_2_, final TextureAtlasSprite p_178394_3_) {
        final HashMap var4 = Maps.newHashMap();
        var4.put(EnumFacing.Ø­áŒŠá, new BlockPartFace(null, p_178394_1_, p_178394_2_, new BlockFaceUV(new float[] { 0.0f, 0.0f, 16.0f, 16.0f }, 0)));
        var4.put(EnumFacing.Ý, new BlockPartFace(null, p_178394_1_, p_178394_2_, new BlockFaceUV(new float[] { 16.0f, 0.0f, 0.0f, 16.0f }, 0)));
        final ArrayList var5 = Lists.newArrayList();
        var5.add(new BlockPart(new Vector3f(0.0f, 0.0f, 7.5f), new Vector3f(16.0f, 16.0f, 8.5f), var4, null, true));
        var5.addAll(this.HorizonCode_Horizon_È(p_178394_3_, p_178394_2_, p_178394_1_));
        return var5;
    }
    
    private List HorizonCode_Horizon_È(final TextureAtlasSprite p_178397_1_, final String p_178397_2_, final int p_178397_3_) {
        final float var4 = p_178397_1_.Ý();
        final float var5 = p_178397_1_.Ø­áŒŠá();
        final ArrayList var6 = Lists.newArrayList();
        for (final HorizonCode_Horizon_È var8 : this.HorizonCode_Horizon_È(p_178397_1_)) {
            float var9 = 0.0f;
            float var10 = 0.0f;
            float var11 = 0.0f;
            float var12 = 0.0f;
            float var13 = 0.0f;
            float var14 = 0.0f;
            float var15 = 0.0f;
            float var16 = 0.0f;
            float var17 = 0.0f;
            float var18 = 0.0f;
            final float var19 = var8.Â();
            final float var20 = var8.Ý();
            final float var21 = var8.Ø­áŒŠá();
            final Â var22 = var8.HorizonCode_Horizon_È();
            switch (Ý.HorizonCode_Horizon_È[var22.ordinal()]) {
                case 1: {
                    var13 = var19;
                    var9 = var19;
                    var14 = (var11 = var20 + 1.0f);
                    var15 = var21;
                    var10 = var21;
                    var16 = var21;
                    var12 = var21;
                    var17 = 16.0f / var4;
                    var18 = 16.0f / (var5 - 1.0f);
                    break;
                }
                case 2: {
                    var16 = var21;
                    var15 = var21;
                    var13 = var19;
                    var9 = var19;
                    var14 = (var11 = var20 + 1.0f);
                    var10 = var21 + 1.0f;
                    var12 = var21 + 1.0f;
                    var17 = 16.0f / var4;
                    var18 = 16.0f / (var5 - 1.0f);
                    break;
                }
                case 3: {
                    var13 = var21;
                    var9 = var21;
                    var14 = var21;
                    var11 = var21;
                    var16 = var19;
                    var10 = var19;
                    var15 = (var12 = var20 + 1.0f);
                    var17 = 16.0f / (var4 - 1.0f);
                    var18 = 16.0f / var5;
                    break;
                }
                case 4: {
                    var14 = var21;
                    var13 = var21;
                    var9 = var21 + 1.0f;
                    var11 = var21 + 1.0f;
                    var16 = var19;
                    var10 = var19;
                    var15 = (var12 = var20 + 1.0f);
                    var17 = 16.0f / (var4 - 1.0f);
                    var18 = 16.0f / var5;
                    break;
                }
            }
            final float var23 = 16.0f / var4;
            final float var24 = 16.0f / var5;
            var9 *= var23;
            var11 *= var23;
            var10 *= var24;
            var12 *= var24;
            var10 = 16.0f - var10;
            var12 = 16.0f - var12;
            var13 *= var17;
            var14 *= var17;
            var15 *= var18;
            var16 *= var18;
            final HashMap var25 = Maps.newHashMap();
            var25.put(var22.HorizonCode_Horizon_È(), new BlockPartFace(null, p_178397_3_, p_178397_2_, new BlockFaceUV(new float[] { var13, var15, var14, var16 }, 0)));
            switch (Ý.HorizonCode_Horizon_È[var22.ordinal()]) {
                default: {
                    continue;
                }
                case 1: {
                    var6.add(new BlockPart(new Vector3f(var9, var10, 7.5f), new Vector3f(var11, var10, 8.5f), var25, null, true));
                    continue;
                }
                case 2: {
                    var6.add(new BlockPart(new Vector3f(var9, var12, 7.5f), new Vector3f(var11, var12, 8.5f), var25, null, true));
                    continue;
                }
                case 3: {
                    var6.add(new BlockPart(new Vector3f(var9, var10, 7.5f), new Vector3f(var9, var12, 8.5f), var25, null, true));
                    continue;
                }
                case 4: {
                    var6.add(new BlockPart(new Vector3f(var11, var10, 7.5f), new Vector3f(var11, var12, 8.5f), var25, null, true));
                    continue;
                }
            }
        }
        return var6;
    }
    
    private List HorizonCode_Horizon_È(final TextureAtlasSprite p_178393_1_) {
        final int var2 = p_178393_1_.Ý();
        final int var3 = p_178393_1_.Ø­áŒŠá();
        final ArrayList var4 = Lists.newArrayList();
        for (int var5 = 0; var5 < p_178393_1_.ÂµÈ(); ++var5) {
            final int[] var6 = p_178393_1_.HorizonCode_Horizon_È(var5)[0];
            for (int var7 = 0; var7 < var3; ++var7) {
                for (int var8 = 0; var8 < var2; ++var8) {
                    final boolean var9 = !this.HorizonCode_Horizon_È(var6, var8, var7, var2, var3);
                    this.HorizonCode_Horizon_È(ItemModelGenerator.Â.HorizonCode_Horizon_È, var4, var6, var8, var7, var2, var3, var9);
                    this.HorizonCode_Horizon_È(ItemModelGenerator.Â.Â, var4, var6, var8, var7, var2, var3, var9);
                    this.HorizonCode_Horizon_È(ItemModelGenerator.Â.Ý, var4, var6, var8, var7, var2, var3, var9);
                    this.HorizonCode_Horizon_È(ItemModelGenerator.Â.Ø­áŒŠá, var4, var6, var8, var7, var2, var3, var9);
                }
            }
        }
        return var4;
    }
    
    private void HorizonCode_Horizon_È(final Â p_178396_1_, final List p_178396_2_, final int[] p_178396_3_, final int p_178396_4_, final int p_178396_5_, final int p_178396_6_, final int p_178396_7_, final boolean p_178396_8_) {
        final boolean var9 = this.HorizonCode_Horizon_È(p_178396_3_, p_178396_4_ + p_178396_1_.Â(), p_178396_5_ + p_178396_1_.Ý(), p_178396_6_, p_178396_7_) && p_178396_8_;
        if (var9) {
            this.HorizonCode_Horizon_È(p_178396_2_, p_178396_1_, p_178396_4_, p_178396_5_);
        }
    }
    
    private void HorizonCode_Horizon_È(final List p_178395_1_, final Â p_178395_2_, final int p_178395_3_, final int p_178395_4_) {
        HorizonCode_Horizon_È var5 = null;
        for (final HorizonCode_Horizon_È var7 : p_178395_1_) {
            if (var7.HorizonCode_Horizon_È() == p_178395_2_) {
                final int var8 = p_178395_2_.Ø­áŒŠá() ? p_178395_4_ : p_178395_3_;
                if (var7.Ø­áŒŠá() == var8) {
                    var5 = var7;
                    break;
                }
                continue;
            }
        }
        final int var9 = p_178395_2_.Ø­áŒŠá() ? p_178395_4_ : p_178395_3_;
        final int var10 = p_178395_2_.Ø­áŒŠá() ? p_178395_3_ : p_178395_4_;
        if (var5 == null) {
            p_178395_1_.add(new HorizonCode_Horizon_È(p_178395_2_, var10, var9));
        }
        else {
            var5.HorizonCode_Horizon_È(var10);
        }
    }
    
    private boolean HorizonCode_Horizon_È(final int[] p_178391_1_, final int p_178391_2_, final int p_178391_3_, final int p_178391_4_, final int p_178391_5_) {
        return p_178391_2_ < 0 || p_178391_3_ < 0 || p_178391_2_ >= p_178391_4_ || p_178391_3_ >= p_178391_5_ || (p_178391_1_[p_178391_3_ * p_178391_4_ + p_178391_2_] >> 24 & 0xFF) == 0x0;
    }
    
    enum Â
    {
        HorizonCode_Horizon_È("UP", 0, "UP", 0, EnumFacing.Â, 0, -1), 
        Â("DOWN", 1, "DOWN", 1, EnumFacing.HorizonCode_Horizon_È, 0, 1), 
        Ý("LEFT", 2, "LEFT", 2, EnumFacing.Ó, -1, 0), 
        Ø­áŒŠá("RIGHT", 3, "RIGHT", 3, EnumFacing.Âµá€, 1, 0);
        
        private final EnumFacing Âµá€;
        private final int Ó;
        private final int à;
        private static final Â[] Ø;
        private static final String áŒŠÆ = "CL_00002485";
        
        static {
            áˆºÑ¢Õ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá };
            Ø = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá };
        }
        
        private Â(final String s, final int n, final String p_i46215_1_, final int p_i46215_2_, final EnumFacing p_i46215_3_, final int p_i46215_4_, final int p_i46215_5_) {
            this.Âµá€ = p_i46215_3_;
            this.Ó = p_i46215_4_;
            this.à = p_i46215_5_;
        }
        
        public EnumFacing HorizonCode_Horizon_È() {
            return this.Âµá€;
        }
        
        public int Â() {
            return this.Ó;
        }
        
        public int Ý() {
            return this.à;
        }
        
        private boolean Ø­áŒŠá() {
            return this == Â.Â || this == Â.HorizonCode_Horizon_È;
        }
    }
    
    static class HorizonCode_Horizon_È
    {
        private final Â HorizonCode_Horizon_È;
        private int Â;
        private int Ý;
        private final int Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002486";
        
        public HorizonCode_Horizon_È(final Â p_i46216_1_, final int p_i46216_2_, final int p_i46216_3_) {
            this.HorizonCode_Horizon_È = p_i46216_1_;
            this.Â = p_i46216_2_;
            this.Ý = p_i46216_2_;
            this.Ø­áŒŠá = p_i46216_3_;
        }
        
        public void HorizonCode_Horizon_È(final int p_178382_1_) {
            if (p_178382_1_ < this.Â) {
                this.Â = p_178382_1_;
            }
            else if (p_178382_1_ > this.Ý) {
                this.Ý = p_178382_1_;
            }
        }
        
        public Â HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public int Â() {
            return this.Â;
        }
        
        public int Ý() {
            return this.Ý;
        }
        
        public int Ø­áŒŠá() {
            return this.Ø­áŒŠá;
        }
    }
    
    static final class Ý
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002487";
        
        static {
            HorizonCode_Horizon_È = new int[ItemModelGenerator.Â.values().length];
            try {
                Ý.HorizonCode_Horizon_È[ItemModelGenerator.Â.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Ý.HorizonCode_Horizon_È[ItemModelGenerator.Â.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Ý.HorizonCode_Horizon_È[ItemModelGenerator.Â.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                Ý.HorizonCode_Horizon_È[ItemModelGenerator.Â.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
